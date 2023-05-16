package com.elka.tabatatimerarduino.view.list.bluetoothDevices

import androidx.recyclerview.widget.RecyclerView
import com.elka.tabatatimerarduino.databinding.BluetoothDeviceItemBinding
import com.elka.tabatatimerarduino.service.models.BluetoothDevice

class BluetoothDeviceViewHolder(
  private val binding: BluetoothDeviceItemBinding, val listener: Listener
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(device: BluetoothDevice) {
    binding.device = device
    binding.root.setOnClickListener { listener.onSelect(device) }
  }

  companion object {
    interface Listener {
      fun onSelect(device: BluetoothDevice) {}
    }
  }
}