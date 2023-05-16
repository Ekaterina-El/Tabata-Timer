package com.elka.tabatatimerarduino.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.elka.tabatatimerarduino.databinding.ConnectionDialogBinding

class ConnectionDialog(context: Context) : Dialog(context) {
  private lateinit var binding: ConnectionDialogBinding

  init {
    initDialog()
  }

  private fun initDialog() {
    binding = ConnectionDialogBinding.inflate(LayoutInflater.from(context))
    setContentView(binding.root)

    window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    setCancelable(false)
  }
}