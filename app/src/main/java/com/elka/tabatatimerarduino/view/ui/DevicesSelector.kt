package com.elka.tabatatimerarduino.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import com.elka.tabatatimerarduino.view.list.bluetoothDevices.BluetoothDeviceViewHolder
import com.elka.tabatatimerarduino.view.list.bluetoothDevices.BluetoothDevicesAdapter
import com.elka.tabatatimerarduino.viewModel.DevicesSelectorViewModel

class DevicesSelector : BaseFragment() {
  private lateinit var binding: DevicesSelectorFragmentBinding
  private lateinit var viewModel: DevicesSelectorViewModel

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
    val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener { reloadBluetoothDevices() }
    binding.refresher.setColorSchemeColors(refresherColor)
    binding.refresher.setOnRefreshListener(swipeRefreshListener)
  }

  override fun onResume() {
    super.onResume()
    if (viewModel.bluetoothDevices.value!!.isEmpty()) reloadBluetoothDevices()

    viewModel.bluetoothDevices.observe(this, bluetoothDevicesObserver)
  }

  override fun onStop() {
    super.onStop()

    viewModel.bluetoothDevices.removeObserver(bluetoothDevicesObserver)
  }

  @SuppressLint("MissingPermission")
  private fun reloadBluetoothDevices() {
    val boundedDevices =
      (requireContext().applicationContext as TabataTimerApplication).bluetoothWorker.getBoundedDevices()
    viewModel.setBluetoothDevices(boundedDevices)
  }
}