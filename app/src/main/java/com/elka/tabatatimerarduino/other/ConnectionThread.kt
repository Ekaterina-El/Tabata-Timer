package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import java.util.*


@SuppressLint("MissingPermission")
class ConnectionThread(
  private val listener: BluetoothController.Companion.Listener,
  bluetoothDevice: BluetoothDevice,
  uuid: UUID
) :
  Thread() {
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
    } catch (e: java.lang.Exception) {
      listener.onFailConnect()
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