package com.elka.tabatatimerarduino.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elka.tabatatimerarduino.other.Work
import com.elka.tabatatimerarduino.service.models.BluetoothDevice

class DevicesSelectorViewModel(application: Application) : BaseViewModel(application) {
  companion object {
    val bluetoothDevicesLoadWork = Work.LOAD_BLUETOOTH_DEVICES
  }

  private val _bluetoothDevices = MutableLiveData<List<BluetoothDevice>>(listOf())
  val bluetoothDevices get() = _bluetoothDevices

  fun setBluetoothDevices(boundedDevices: List<BluetoothDevice>) {
    _bluetoothDevices.value = boundedDevices
    removeWork(bluetoothDevicesLoadWork)
  }

  fun startLoadBluetoothDevices() {
    addWork(bluetoothDevicesLoadWork)
  }

  private val _bluetoothIsOn = MutableLiveData(false)
  val bluetoothIsOn get() = _bluetoothIsOn

  fun updateAdapterStatus(isOn: Boolean) {
    _bluetoothIsOn.value = isOn
  }
}