package com.elka.tabatatimerarduino.view.ui.training

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.databinding.TrainingSettingFragmentBinding
import com.elka.tabatatimerarduino.service.models.TrainingState

class TrainingSettingFragment : TrainingConnection() {
  private lateinit var binding: TrainingSettingFragmentBinding
  private val touchListener = object: View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
      val tag = view?.tag.toString()
      val action = event?.action ?: return false

      val tagsParts = tag.split(".")
      val dir = tagsParts[0]

      when (action) {
        MotionEvent.ACTION_DOWN -> {
          val res = when(dir) {
            "back" -> R.drawable.triangle_left_activate
            "up" -> R.drawable.triangle_right_activate
            else -> null
          }
          res?.let { (view as ImageView).setImageResource(res) }
          currentChangeableProperty = tag
        }

        MotionEvent.ACTION_UP -> {
          val res = when(dir) {
            "back" -> R.drawable.triangle_left
            "up" -> R.drawable.triangle_right
            else -> null
          }
          res?.let { (view as ImageView).setImageResource(res) }
          currentChangeableProperty = null
        }

        else -> return false
      }
      return false
    }
  }

  private val handler = Handler(Looper.getMainLooper())

  private var isCheckButtonsStatus = false
  private var currentChangeableProperty: String? = null
  private fun startCheckButtonStatus() {
    isCheckButtonsStatus = true
    checkButtonStatus()
  }

  private fun stopCheckButtonsStatus() {
    isCheckButtonsStatus = false
  }
  private fun checkButtonStatus() {
    if (!isCheckButtonsStatus) return
    when (currentChangeableProperty) {
      "back.cycles" -> viewModel.setCycles(viewModel.cycles.value!! - 1)
      "up.cycles" -> viewModel.setCycles(viewModel.cycles.value!! + 1)
      "back.sets" -> viewModel.setSets(viewModel.sets.value!! - 1)
      "up.sets" -> viewModel.setSets(viewModel.sets.value!! + 1)
      "back.work" -> viewModel.setWorkCycle(viewModel.workCycle.value!! - 1)
      "up.work" -> viewModel.setWorkCycle(viewModel.workCycle.value!! + 1)
      "back.rest" -> viewModel.setRest(viewModel.rest.value!! - 1)
      "up.rest" -> viewModel.setRest(viewModel.rest.value!! + 1)
      "back.rest_between_sets" -> viewModel.setRestBetweenSets(viewModel.restBetweenSets.value!! - 1)
      "up.rest_between_sets" -> viewModel.setRestBetweenSets(viewModel.restBetweenSets.value!! + 1)
      else -> Unit
    }
    handler.postDelayed({ checkButtonStatus() }, 100)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    binding = TrainingSettingFragmentBinding.inflate(layoutInflater, container, false)
    binding.apply {
      master = this@TrainingSettingFragment
      lifecycleOwner = viewLifecycleOwner
      viewModel = this@TrainingSettingFragment.viewModel
    }
    return binding.root
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        disconnect()
      }
    })

    binding.cyclesBack.setOnTouchListener(touchListener)
    binding.cyclesUp.setOnTouchListener(touchListener)
    binding.setsBack.setOnTouchListener(touchListener)
    binding.setsUp.setOnTouchListener(touchListener)
    binding.workBack.setOnTouchListener(touchListener)
    binding.workUp.setOnTouchListener(touchListener)
    binding.restBack.setOnTouchListener(touchListener)
    binding.restUp.setOnTouchListener(touchListener)
    binding.restBetweenSetsBack.setOnTouchListener(touchListener)
    binding.restBetweenSetsUp.setOnTouchListener(touchListener)
  }

  override fun onResume() {
    super.onResume()
    startCheckButtonStatus()
  }

  override fun onStop() {
    super.onStop()
    stopCheckButtonsStatus()
  }

  fun startTrain() {
    val cycles = viewModel.cycles.value!!
    val sets = viewModel.sets.value!!
    val work = viewModel.workCycle.value!!
    val rest = viewModel.rest.value!!
    val restBetweenSets = viewModel.restBetweenSets.value!!
    sendMessageToStartTrain(cycles, sets, work, rest, restBetweenSets)
    findNavController().navigate(R.id.action_trainingSettingFragment_to_trainingProcessFragment)
    viewModel.setTrainingState(TrainingState.TRAINING)
  }
}