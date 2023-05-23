package com.elka.tabatatimerarduino.view.ui.training

import android.widget.Toast
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import com.elka.tabatatimerarduino.view.dialog.ConnectionDialog
import com.elka.tabatatimerarduino.view.ui.BaseFragment

abstract class TrainingConnection: BaseFragment() {
  protected lateinit var connection: BluetoothController

  protected val bluetoothConnectionListener by lazy {
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
  protected val connectionDialog by lazy { ConnectionDialog(requireContext()) }
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