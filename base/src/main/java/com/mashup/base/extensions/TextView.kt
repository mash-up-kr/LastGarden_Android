package com.mashup.base.extensions

import android.graphics.Paint
import android.widget.TextView

fun TextView.underLine() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.hideUnderLine() {
    paintFlags = paintFlags xor Paint.UNDERLINE_TEXT_FLAG
}