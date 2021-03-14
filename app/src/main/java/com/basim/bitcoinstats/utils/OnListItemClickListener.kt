package com.basim.bitcoinstats.utils

import android.view.View

/**
 * List item click listener for recyclerview
 */
interface OnListItemClickListener {
    fun onListItemClick(view: View, obj: Any, index: Int)
}