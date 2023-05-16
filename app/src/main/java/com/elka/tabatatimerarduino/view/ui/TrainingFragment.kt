package com.elka.tabatatimerarduino.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elka.tabatatimerarduino.databinding.TrainingFragmentBinding

class TrainingFragment : BaseFragment() {
  private lateinit var binding: TrainingFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = TrainingFragmentBinding.inflate(layoutInflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      master = this@TrainingFragment
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val args = TrainingFragmentArgs.fromBundle(requireArguments())
    bluetoothWorker.connectWith(args.device)
  }
}