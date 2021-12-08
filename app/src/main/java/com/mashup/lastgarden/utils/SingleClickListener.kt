package com.mashup.lastgarden.utils

import android.view.View

class SingleClickListener(
    private val intervalTime: Int = 600,
    private val action: (View) -> Unit
) : View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(view: View) {
        val systemTime = System.currentTimeMillis()
        if (systemTime - lastClickTime > intervalTime) {
            lastClickTime = systemTime
            action.invoke(view)
        }
    }
}

fun View.setOnSingleClickListener(onClick: (View) -> Unit) {
    val singleClick = SingleClickListener {
        onClick(it)
    }
    setOnClickListener(singleClick)
}