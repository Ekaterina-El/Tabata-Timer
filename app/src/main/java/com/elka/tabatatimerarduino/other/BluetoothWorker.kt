package com.elka.tabatatimerarduino.other

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import com.elka.tabatatimerarduino.TabataTimerApplication
import com.elka.tabatatimerarduino.service.models.BluetoothDevice

class BluetoothWorker(private val context: Context) {
  private val bluetoothManager by lazy {
    context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
  }

  private val bluetoothAdapter by lazy { bluetoothManager.adapter }
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
}