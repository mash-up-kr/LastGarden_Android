package com.mashup.lastgarden.utils

import java.text.DecimalFormat

fun formatNumber(number: Long): String {
    val formatter = DecimalFormat("###,###")
    return formatter.format(number)
}