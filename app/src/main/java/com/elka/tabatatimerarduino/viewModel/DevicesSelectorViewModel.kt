package com.elka.tabatatimerarduino.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elka.tabatatimerarduino.other.Work
import com.elka.tabatatimerarduino.service.models.BluetoothDevice

class DevicesSelectorViewModel(application: Application) : BaseViewModel(application) {
  companion object {
    val bluetoothDevicesLoadWork = Work.LOAD_BLUETOOTH_DEVICES
  }

  private val _bluetoothDevices = MutableLiveData<List<BluetoothDevice>>(listOf())
  val bluetoothDevices: LiveData<List<BluetoothDevice>> get() = _bluetoothDevices

  fun setBluetoothDevices(boundedDevices: List<BluetoothDevice>) {
    _bluetoothDevices.value = boundedDevices
    removeWork(bluetoothDevicesLoadWork)
  }

  fun startLoadBluetoothDevices() {
    addWork(bluetoothDevicesLoadWork)
  }
}