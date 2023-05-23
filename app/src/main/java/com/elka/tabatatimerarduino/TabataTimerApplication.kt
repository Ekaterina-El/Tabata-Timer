package com.elka.tabatatimerarduino

import android.app.Application
import android.bluetooth.BluetoothManager
import android.content.Context
import com.elka.tabatatimerarduino.other.BluetoothWorker
import com.elka.tabatatimerarduino.other.bluetooth.BluetoothController

class TabataTimerApplication : Application() {
  val bluetoothWorker by lazy { BluetoothWorker(this) }

  val bluetoothManager by lazy { getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
  val bluetoothAdapter by lazy { bluetoothManager.adapter }
  val connection by lazy { BluetoothController(bluetoothAdapter) }
  val isOn: Boolean get() = bluetoothAdapter.isEnabled

}