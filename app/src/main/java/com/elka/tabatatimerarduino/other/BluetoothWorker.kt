package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.util.Log
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import java.util.*


class BluetoothWorker(private val context: Context) {
  private val bluetoothManager by lazy {
    context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
  }

  private val bluetoothAdapter by lazy { bluetoothManager.adapter }
  private val bluetoothController by lazy { BluetoothController(bluetoothAdapter) }
  val isOn: Boolean get() = bluetoothAdapter.isEnabled

  @SuppressLint("MissingPermission")
  fun sendRequestToEnableBluetooth(activity: Activity) {
    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    activity.startActivityForResult(intent, 1)
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

  fun connectWith(device: BluetoothDevice, listener: BluetoothController.Companion.Listener) {
    bluetoothController.connect(device, listener)
  }
}

