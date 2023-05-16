package com.elka.tabatatimerarduino.view.ui

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elka.tabatatimerarduino.R
import com.elka.tabatatimerarduino.TabataTimerApplication
import com.elka.tabatatimerarduino.other.Work

open class BaseFragment: Fragment() {
  val navController by lazy {
    requireActivity().findNavController(R.id.fragmentContainerView)
  }
  fun getHasLoads(w1: List<Work>, works: List<Work>): Boolean = when {
    w1.isEmpty() -> false
    else -> w1.map { item -> if (works.contains(item)) 1 else 0 }.reduce { a, b -> a + b } > 0
  }

  val bluetoothWorker by lazy { (requireActivity().application as TabataTimerApplication).bluetoothWorker }
}