package com.elka.tabatatimerarduino.view.ui

import androidx.fragment.app.Fragment
import com.elka.tabatatimerarduino.other.Work

open class BaseFragment: Fragment() {
  fun getHasLoads(w1: List<Work>, works: List<Work>): Boolean = when {
    w1.isEmpty() -> false
    else -> w1.map { item -> if (works.contains(item)) 1 else 0 }.reduce { a, b -> a + b } > 0
  }
}