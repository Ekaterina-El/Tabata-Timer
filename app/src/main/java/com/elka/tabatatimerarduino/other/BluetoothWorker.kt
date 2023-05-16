package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import com.elka.tabatatimerarduino.service.models.BluetoothDevice

class BluetoothWorker(context: Context) {
  private val bluetoothManager by lazy {
    context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
  }

  private val bluetoothAdapter by lazy {
    bluetoothManager.adapter
  }

  @SuppressLint("MissingPermission")
  fun getBoundedDevices(): List<BluetoothDevice> {
    return bluetoothAdapter.bondedDevices.mapNotNull {
      BluetoothDevice(
        name = it.name,
        mac = it.address,
      )
    }
  }
}