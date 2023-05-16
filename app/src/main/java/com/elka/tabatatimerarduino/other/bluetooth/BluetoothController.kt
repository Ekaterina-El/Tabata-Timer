package com.elka.tabatatimerarduino.other.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import com.elka.tabatatimerarduino.other.ConnectionThread
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import java.util.*

@SuppressLint("MissingPermission")
class BluetoothController(
  private val bluetoothAdapter: BluetoothAdapter,
) {
  private var mConnectionThread: ConnectionThread? = null

  fun connect(device: BluetoothDevice, listener: Listener) {
    if (!bluetoothAdapter.isEnabled) return

    val bluetoothDevice = bluetoothAdapter.getRemoteDevice(device.mac)
    val uuid = bluetoothDevice.uuids[0].uuid

    mConnectionThread = ConnectionThread(listener, bluetoothDevice, uuid)
    mConnectionThread?.start()
  }

  fun sendMessage(message: String) {
    mConnectionThread?.sendMessage(message)
  }

  fun disconnect() {
    mConnectionThread?.closeConnection()
  }

  companion object {
    interface Listener {
      fun onStartConnection()
      fun onConnected()
      fun onFailConnect()
      fun onCloseConnection()

      fun onFetchMessage(message: String)
    }
  }
}