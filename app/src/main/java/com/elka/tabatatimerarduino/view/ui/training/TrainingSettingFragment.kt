package com.elka.tabatatimerarduino.view.ui.training

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import com.elka.tabatatimerarduino.R
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

  private val touchListener = object: View.OnTouchListener {
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
      val tag = view?.tag.toString() ?: return false
      val action = event?.action ?: return false

      val tagsParts = tag.split(".")
      val dir = tagsParts[0]
      val type = tagsParts[1]

      when (action) {
        MotionEvent.ACTION_DOWN -> {
          val res = when(dir) {
            "back" -> R.drawable.triangle_left_activate
            "up" -> R.drawable.triangle_right_activate
            else -> null
          }
          res?.let { (view as ImageView).setImageResource(res) }
        }

        MotionEvent.ACTION_UP -> {
          val res = when(dir) {
            "back" -> R.drawable.triangle_left
            "up" -> R.drawable.triangle_right
            else -> null
          }
          res?.let { (view as ImageView).setImageResource(res) }
        }

        else -> return false
      }
      return false
    }

  }

}