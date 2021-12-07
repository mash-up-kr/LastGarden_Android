package com.mashup.lastgarden.utils

import android.content.Context
import com.mashup.lastgarden.R

fun formatPageCount(context: Context, page: Int, size: Int): String{
    val countFormat = context.getString(R.string.page_count)
    return String.format(countFormat, page + 1, formatNumber(size.toLong()))
}