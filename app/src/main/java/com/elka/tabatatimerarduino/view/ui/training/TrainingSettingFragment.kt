package com.elka.tabatatimerarduino.view.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.elka.tabatatimerarduino.databinding.TrainingSettingFragmentBinding

class TrainingSettingFragment : TrainingConnection() {
  private lateinit var binding: TrainingSettingFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = TrainingSettingFragmentBinding.inflate(layoutInflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
//      master = this@TrainingSettingFragment
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        disconnect()
      }
    })
/*
    val args = TrainingFragmentArgs.fromBundle(requireArguments())
    bluetoothWorker.connectWith(args.device, bluetoothConnectionListener)*/
  }

}