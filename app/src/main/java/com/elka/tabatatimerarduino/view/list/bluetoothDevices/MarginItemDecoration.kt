package com.elka.tabatatimerarduino.view.list.bluetoothDevices

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
  private val top: Int, private val bottom: Int, private val left: Int, private val right: Int
) : RecyclerView.ItemDecoration() {
  override fun getItemOffsets(
    rect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
  ) {
    if (parent.getChildAdapterPosition(view) == 0) rect.top = this.top
    rect.bottom = bottom
    rect.left = left
    rect.right = right
  }
}