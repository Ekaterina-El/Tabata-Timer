package com.elka.tabatatimerarduino.service.models

data class BluetoothDevice(
  val name: String,
  var mac: String
): java.io.Serializable