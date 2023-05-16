package com.elka.tabatatimerarduino.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elka.tabatatimerarduino.databinding.InformDialogBinding

class InformDialog(context: Context) : Dialog(context) {
  private lateinit var binding: InformDialogBinding

  init {
    initDialog()
  }

  private fun initDialog() {
    binding = InformDialogBinding.inflate(LayoutInflater.from(context))
    setContentView(binding.root)

    window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    setCancelable(true)
  }

  fun open(
    title: String,
    message: String,
    isCancelable: Boolean = false,
    onButtonListener: (() -> Unit)? = null,
  ) {
    setCancelable(isCancelable)
    binding.textViewTitle.text = title
    binding.textViewMessage.text = message
    onButtonListener?.apply {
      binding.button.setOnClickListener {
        onButtonListener()
        dismiss()
      }
    }
    show()
  }

  fun close() {
    binding.button.setOnClickListener(null)
    binding.textViewTitle.text = ""
    binding.textViewMessage.text = ""
    dismiss()
  }

}