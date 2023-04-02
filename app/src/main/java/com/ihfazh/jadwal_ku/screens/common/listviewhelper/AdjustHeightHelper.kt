package com.ihfazh.jadwal_ku.screens.common.listviewhelper

import android.widget.ListView
import javax.inject.Inject

class AdjustHeightHelper @Inject constructor() {
     fun <LIST_ITEM_TYPE> adjustListViewHeight(items: List<LIST_ITEM_TYPE>, parent: ListView){
        val adapter = parent.adapter ?: return

        // hack
        val itemHeights = List(items.size) { index ->
            val itemView = adapter.getView(index, null, parent)
            itemView.measure(0, 0)
            itemView.measuredHeight
        }
        val totalHeight = itemHeights.sum()

        val layoutParams = parent.layoutParams
        layoutParams.height = totalHeight + (parent.dividerHeight * (items.size - 1))
        parent.layoutParams = layoutParams
    }
}