package com.elka.tabatatimerarduino

import android.app.Application
import com.elka.tabatatimerarduino.other.BluetoothWorker

class TabataTimerApplication: Application() {
  val bluetoothWorker by lazy { BluetoothWorker(this) }
}