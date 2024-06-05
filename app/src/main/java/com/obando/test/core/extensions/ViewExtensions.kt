package com.obando.test.core.extensions

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(invisible: Boolean = false) {
    visibility = if (invisible) View.INVISIBLE else View.GONE
}