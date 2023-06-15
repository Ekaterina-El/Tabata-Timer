package com.elka.tabatatimerarduino.view.ui.training

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.TabataTimerApplication
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import com.elka.tabatatimerarduino.view.dialog.ConnectionDialog
import com.elka.tabatatimerarduino.view.ui.BaseFragment
import com.elka.tabatatimerarduino.viewModel.TrainingViewModel

abstract class TrainingConnection: BaseFragment() {
  protected val viewModel by activityViewModels<TrainingViewModel>()

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
    (requireActivity().application as TabataTimerApplication).connection.disconnect()
  }

  fun sendMessage(message: String) {
    (requireActivity().application as TabataTimerApplication).connection.sendMessage(message)
  }

  fun sendMessageToStartTrain(cycles: Int, sets: Int, work: Int, rest: Int, restBetweenSets: Int) {
    val message = listOf(START_TRAIN_CODE.toInt(), cycles, sets, work, rest, restBetweenSets).joinToString(", ")
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    sendMessage(message)
  }

  fun sendMessageToResume() {
    sendMessage(RESUME_CODE)
  }

  fun sendMessageToPause() {
    sendMessage(PAUSE_CODE)
  }

  fun sendMessageToCancel() {
    sendMessage(CANCEL_CODE)
  }

  companion object {
    const val START_TRAIN_CODE = "1"
    const val PAUSE_CODE = "2"
    const val CANCEL_CODE = "3"
    const val RESUME_CODE = "4"
  }
}