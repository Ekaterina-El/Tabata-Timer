package com.elka.tabatatimerarduino.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData

class TrainingViewModel(application: Application) : BaseViewModel(application) {
  private val _sets = MutableLiveData(3)
  val sets get() = _sets
  fun setSets(value: Int) {
    if (value in 1..99) _sets.value = value
  }

  private val _cycles = MutableLiveData(5)
  val cycles get() = _cycles
  fun setCycles(value: Int) {
    if (value in 1..99) _cycles.value = value
  }

  private val _work = MutableLiveData(25)
  val workCycle get() = _work
  fun setWorkCycle(value: Int) {
    if (value >= 1) _work.value = value
  }

  private val _rest = MutableLiveData(5)
  val rest get() = _rest
  fun setRest(value: Int) {
    if (value >= 1) _rest.value = value
  }

  private val _restBetweenSets = MutableLiveData(30)
  val restBetweenSets get() = _restBetweenSets
  fun setRestBetweenSets(value: Int) {
    if (value >= 1) _restBetweenSets.value = value
  }
}