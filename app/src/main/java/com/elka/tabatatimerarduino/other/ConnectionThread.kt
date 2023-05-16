package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import java.util.*


@SuppressLint("MissingPermission")
class ConnectionThread(
  private val listener: BluetoothController.Companion.Listener,
  bluetoothDevice: BluetoothDevice,
  uuid: UUID
) : Thread() {
  private var mSocket: BluetoothSocket? = null

  init {
    try {
      mSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid)
    } catch (e: java.lang.Exception) {
    }
  }

  override fun run() {
    try {
      listener.onStartConnection()
      mSocket?.connect()
      listener.onConnected()
      readMessage()
    } catch (e: java.lang.Exception) {
      listener.onFailConnect()
    }
  }

  private fun readMessage() {
    val buffer = ByteArray(256)
    while (true) {
      try {
        val length = mSocket?.inputStream?.read(buffer)
        val message = String(buffer, 0, length ?: 0)
        listener.onFetchMessage(message)
      } catch (e: java.lang.Exception) {
        break
      }
    }
  }

  fun sendMessage(message: String) {
    try {
      mSocket?.outputStream?.write(message.toByteArray())
    } catch (_: Exception) {

    }
  }
  fun closeConnection() {
    try {
      mSocket?.close()
      listener.onCloseConnection()
    } catch (e: java.lang.Exception) {
    }
  }
}