package com.mashup.lastgarden.ui.scent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainStorySet(
    val set: Set<Pair<Int, Int>>? = null
) : Parcelable