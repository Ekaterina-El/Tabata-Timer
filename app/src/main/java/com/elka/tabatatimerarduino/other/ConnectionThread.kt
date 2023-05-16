package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.util.*


@SuppressLint("MissingPermission")
class ConnectionThread(private val bluetoothDevice: BluetoothDevice, uuid: UUID) :
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
      Log.d(TAG, "Connecting...")
      mSocket?.connect()
      Log.d(TAG, "Connected")
    } catch (e: java.lang.Exception) {
      Log.d(TAG, "No conndected")
    }
  }

  fun closeConnection() {
    try {
      mSocket?.close()
    } catch (e: java.lang.Exception) {}
  }

  companion object {
    const val TAG = "ConnectionThread"
  }
}