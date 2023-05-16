package com.elka.tabatatimerarduino.view.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.TabataTimerApplication
import com.elka.tabatatimerarduino.databinding.DevicesSelectorFragmentBinding
import com.elka.tabatatimerarduino.other.Work
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import com.elka.tabatatimerarduino.view.list.bluetoothDevices.BluetoothDeviceViewHolder
import com.elka.tabatatimerarduino.view.list.bluetoothDevices.BluetoothDevicesAdapter
import com.elka.tabatatimerarduino.viewModel.DevicesSelectorViewModel

class DevicesSelector : BaseFragment() {
  private lateinit var binding: DevicesSelectorFragmentBinding
  private lateinit var viewModel: DevicesSelectorViewModel

  private val handler by lazy { Handler(Looper.getMainLooper()) }

  private var isListenBluetoothAdapter = false
  private val bluetoothStatusObserver = Observer<Boolean> {
    if (!it) bluetoothWorker.sendRequestToEnableBluetooth(requireActivity())
  }

  private val bluetoothDevicesAdapter by lazy {
    BluetoothDevicesAdapter(object : BluetoothDeviceViewHolder.Companion.Listener {
      override fun onSelect(device: BluetoothDevice) {
        Toast.makeText(requireContext(), "Mac: ${device.mac}", Toast.LENGTH_SHORT).show()
      }
    })
  }

  private val bluetoothDevicesObserver = Observer<List<BluetoothDevice>> {
    bluetoothDevicesAdapter.setItems(it)
  }

  private val workObserver = Observer<List<Work>> {
    if (it.contains(DevicesSelectorViewModel.bluetoothDevicesLoadWork)) loadBluetoothDevices()
    binding.refresher.isRefreshing = hasLoads
  }

  private val hasLoads: Boolean
    get() {
      val w1 = viewModel.work.value!!
      return getHasLoads(w1, works)
    }

  private val works = listOf(Work.LOAD_BLUETOOTH_DEVICES)


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    viewModel = ViewModelProvider(this)[DevicesSelectorViewModel::class.java]
    binding = DevicesSelectorFragmentBinding.inflate(layoutInflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      master = this@DevicesSelector
      viewModel = this@DevicesSelector.viewModel
      adapter = bluetoothDevicesAdapter
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Add divider decorator for list
    val dividerDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
    binding.devicesList.addItemDecoration(dividerDecorator)

    // Setting refresh layout
    val refresherColor = requireContext().getColor(R.color.accent)
    val swipeRefreshListener =
      SwipeRefreshLayout.OnRefreshListener { startReloadBluetoothDevices() }
    binding.refresher.setColorSchemeColors(refresherColor)
    binding.refresher.setOnRefreshListener(swipeRefreshListener)
  }

  override fun onResume() {
    super.onResume()

    if (viewModel.bluetoothDevices.value!!.isEmpty()) startReloadBluetoothDevices()
    startListenBluetoothStatus()

    viewModel.bluetoothDevices.observe(this, bluetoothDevicesObserver)
    viewModel.bluetoothIsOn.observe(this, bluetoothStatusObserver)
    viewModel.work.observe(this, workObserver)
  }

  override fun onStop() {
    super.onStop()

    stopListenBluetoothStatus()

    viewModel.bluetoothDevices.removeObserver(bluetoothDevicesObserver)
    viewModel.bluetoothIsOn.removeObserver(bluetoothStatusObserver)
    viewModel.work.removeObserver(workObserver)
  }

  private fun startReloadBluetoothDevices() {
    viewModel.startLoadBluetoothDevices()
  }

  private fun loadBluetoothDevices() {
    val isOn = checkBluetoothStatus()

    if (isOn) {
      val boundedDevices =
        (requireContext().applicationContext as TabataTimerApplication).bluetoothWorker.getBoundedDevices()
      viewModel.setBluetoothDevices(boundedDevices)
    } else viewModel.setBluetoothDevices(listOf())
  }


  private fun startListenBluetoothStatus() {
    isListenBluetoothAdapter = true

    handler.post(object : Runnable {
      override fun run() {
        if (!isListenBluetoothAdapter) return
        checkBluetoothStatus()
        handler.postDelayed(this, 300)
      }
    })
  }

  private fun stopListenBluetoothStatus() {
    isListenBluetoothAdapter = false
  }

  private fun checkBluetoothStatus(): Boolean {
    val isOn = bluetoothWorker.isOn
    val lastBluetoothAdapterStatus = viewModel.bluetoothIsOn.value
    if (isOn != lastBluetoothAdapterStatus) viewModel.updateAdapterStatus(isOn)
    return isOn
  }
}