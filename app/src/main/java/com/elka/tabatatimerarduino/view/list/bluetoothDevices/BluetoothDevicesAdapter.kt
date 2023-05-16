package com.elka.tabatatimerarduino.view.list.bluetoothDevices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elka.tabatatimerarduino.databinding.BluetoothDeviceItemBinding
import com.elka.tabatatimerarduino.service.models.BluetoothDevice
import com.elka.tabatatimerarduino.view.BaseAdapter

class BluetoothDevicesAdapter(private val listener: BluetoothDeviceViewHolder.Companion.Listener) :
  BaseAdapter<BluetoothDevice>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val binding =
      BluetoothDeviceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return BluetoothDeviceViewHolder(binding, listener)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = items[position]
    (holder as BluetoothDeviceViewHolder).bind(item)
  }
}