package com.elka.tabatatimerarduino.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.databinding.TrainingFragmentBinding
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import com.elka.tabatatimerarduino.view.dialog.ConnectionDialog

class TrainingFragment : BaseFragment() {
  private lateinit var binding: TrainingFragmentBinding
  private lateinit var connection: BluetoothController

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = TrainingFragmentBinding.inflate(layoutInflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      master = this@TrainingFragment
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        disconnect()
      }
    })

    val args = TrainingFragmentArgs.fromBundle(requireArguments())
    bluetoothWorker.connectWith(args.device, bluetoothConnectionListener)
  }

  private val bluetoothConnectionListener by lazy {
    object : BluetoothController.Companion.Listener {
      override fun onStartConnection() {
        activity?.runOnUiThread {
          showConnectionDialog()
          Toast.makeText(requireContext(), "Connection...", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onConnected() {
        activity?.runOnUiThread {
          hideConnectionDialog()
          Toast.makeText(requireContext(), "Connected", Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFetchMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
      }

      override fun onFailConnect() {
        activity?.runOnUiThread {
          Toast.makeText(requireContext(), "Fail connect", Toast.LENGTH_SHORT).show()
          showFailConnectionDialog()
        }
      }

      override fun onCloseConnection() {
        activity?.runOnUiThread {
          Toast.makeText(requireContext(), "Close connection", Toast.LENGTH_SHORT).show()
          goBack()
        }
      }
    }
  }

  private val connectionDialog by lazy { ConnectionDialog(requireContext()) }
  fun showConnectionDialog() {
    connectionDialog.show()
  }

  fun hideConnectionDialog() {
    connectionDialog.dismiss()
  }

  private fun goBack() {
    navController.popBackStack()
  }

  fun showFailConnectionDialog() {
    connectionDialog.dismiss()
    informDialog.open(getString(R.string.error), getString(R.string.connection_fail), false) {
      goBack()
    }
  }

  fun disconnect() {
    connection.disconnect()
  }

  fun sendMessage() {
    connection.sendMessage("1, 5, 3, 20, 5, 45")
  }
}