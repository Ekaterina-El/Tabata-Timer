package com.elka.tabatatimerarduino.view.databinding

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.service.models.TrainingState

@BindingAdapter("app:trainingState")
fun updateTrainingState(textView: TextView, trainingState: TrainingState) {
  val stateRes = when(trainingState) {
    TrainingState.NO_TRAINING -> null
    TrainingState.TRAINING -> R.string.training_session_is_active
    TrainingState.PAUSE_ARDUINO -> R.string.training_session_paused_by_arduino
    TrainingState.PAUSE_PHONE -> R.string.training_session_paused_by_phone
    TrainingState.TRAINING_ENDED -> R.string.training_session_ended
  }

  stateRes?.let { textView.setText(it) }
}

@BindingAdapter("app:sets", "app:cycles", requireAll = true)
fun setStateAndCycles(textView: TextView, sets: Int, cycles: Int) {
  val text = textView.context.getString(R.string.sets_cycles, sets, cycles)
  textView.text = text
}

@BindingAdapter("app:trainingStateBtn")
fun setTrainingStateBtn(button: Button, trainingState: TrainingState) {
  val stateRes = when(trainingState) {
    TrainingState.TRAINING -> R.string.pause_training_session
    TrainingState.PAUSE_PHONE -> R.string.resume_training_session
    TrainingState.PAUSE_ARDUINO,
    TrainingState.TRAINING_ENDED,
    TrainingState.NO_TRAINING -> null

  }

  if (stateRes == null) {
    button.visibility = View.INVISIBLE
  } else {
    button.visibility = View.VISIBLE
    button.setText(stateRes)
  }
}

@BindingAdapter("app:workTime", "app:restTime", "app:restBtwSetsTime", requireAll = true)
fun setTrainTimes(textView: TextView, workTime: Int, restTime: Int, restBtwSetsTime: Int) {
  val workTimeS = workTime.toTime()
  val restTimeS = restTime.toTime()
  val restBtwSetsTimeS = restBtwSetsTime.toTime()
  val text = textView.context.getString(R.string.train_times, workTimeS, restTimeS, restBtwSetsTimeS)
  textView.text = text
}

fun Int.toTime(): String {
  val m = this / 60
  val s = this - m * 60
  return "${m.addZero()}:${s.addZero()}"
}

fun Int.addZero(): String = if (this > 9) this.toString() else "0$this"