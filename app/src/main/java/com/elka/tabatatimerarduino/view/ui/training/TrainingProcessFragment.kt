package com.elka.tabatatimerarduino.view.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.elka.tabatatimerarduino.databinding.TrainingProcessFragmentBinding
import com.elka.tabatatimerarduino.service.models.TrainingState

class TrainingProcessFragment : TrainingConnection() {
  private lateinit var binding: TrainingProcessFragmentBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          val state = viewModel.trainingState.value
          if (state == TrainingState.TRAINING_ENDED || state == TrainingState.CANCELED) goBack()
          else cancel()
        }
      })
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

  fun cancel() {
    viewModel.setTrainingState(TrainingState.CANCELED)
    sendMessageToCancel()
    goBack()
  }

  private fun goBack() {
    findNavController().popBackStack()
  }
}