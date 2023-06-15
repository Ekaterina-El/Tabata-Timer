package com.elka.tabatatimerarduino.view.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.elka.tabatatimerarduino.databinding.TrainingProcessFragmentBinding
import com.elka.tabatatimerarduino.service.models.TrainingState
import com.elka.tabatatimerarduino.viewModel.TrainingViewModel

class TrainingProcessFragment: TrainingConnection() {
  private lateinit var binding: TrainingProcessFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = TrainingProcessFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      master = this@TrainingProcessFragment
      viewModel = this@TrainingProcessFragment.viewModel
    }
  }

  fun changeStatus() {
    val state = viewModel.trainingState.value
    if (state == TrainingState.TRAINING) {
      viewModel.setTrainingState(TrainingState.PAUSE_PHONE)
      sendMessageToPause()
    } else if (state == TrainingState.PAUSE_PHONE) {
      viewModel.setTrainingState(TrainingState.TRAINING)
      sendMessageToResume()
    }
  }
}