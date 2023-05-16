package com.elka.tabatatimerarduino.other.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import com.elka.tabatatimerarduino.other.ConnectionThread
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import java.util.*

@SuppressLint("MissingPermission")
class BluetoothController(private val bluetoothAdapter: BluetoothAdapter) {
  private lateinit var mConnectionThread: ConnectionThread

  fun connect( device: BluetoothDevice) {
    if (!bluetoothAdapter.isEnabled) return

    val bluetoothDevice = bluetoothAdapter.getRemoteDevice(device.mac)
    val uuid = bluetoothDevice.uuids[0].uuid

    mConnectionThread = ConnectionThread(bluetoothDevice, uuid)
    mConnectionThread.start()
  }
}