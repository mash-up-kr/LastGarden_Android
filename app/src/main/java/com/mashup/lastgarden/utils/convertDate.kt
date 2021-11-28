package com.mashup.lastgarden.utils

import android.content.res.Resources
import com.mashup.lastgarden.R

fun convertDate(resources: Resources, date: String): String {
    return if (date.contains("T") && date.isNotBlank()) {
        val dateInfo = date.split("T")[0]
        val splittedDate = dateInfo.split("-")
        splittedDate[0].substring(2, 4) +
                resources.getString(R.string.year) + splittedDate[1] +
                resources.getString(R.string.month) + splittedDate[2] +
                resources.getString(R.string.date)
    } else {
        ""
    }
}