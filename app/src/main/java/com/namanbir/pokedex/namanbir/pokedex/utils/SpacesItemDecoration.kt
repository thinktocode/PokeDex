

package com.namanbir.pokedex.namanbir.pokedex.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    if (parent.getChildAdapterPosition(view) != 0) {
      outRect.left = 32
    }
  }
}
