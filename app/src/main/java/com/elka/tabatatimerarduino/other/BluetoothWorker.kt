package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elka.tabatatimerarduino.TabataTimerApplication
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import java.util.*


class BluetoothWorker(private val context: Context) {
  @SuppressLint("MissingPermission")
  fun sendRequestToEnableBluetooth(activity: Activity) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    activity.startActivityForResult(intent, 1)
  }

  @SuppressLint("MissingPermission")
  fun getBoundedDevices(): List<BluetoothDevice> {
    return (context.applicationContext as TabataTimerApplication).bluetoothAdapter.bondedDevices.mapNotNull {
      BluetoothDevice(
        name = it.name,
        mac = it.address,
      )
    }
  }

  fun connectWith(device: BluetoothDevice, listener: BluetoothController.Companion.Listener) {
    (context.applicationContext as TabataTimerApplication).connection.connect(device, listener)
  }
}

