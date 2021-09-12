package com.mashup.base.extensions

import android.content.res.TypedArray
import java.util.*

operator fun TypedArray.iterator(): Iterator<Int> = object : Iterator<Int> {

    private var index = 0

    override fun hasNext(): Boolean = index < indexCount

    override fun next(): Int {
        if (!hasNext()) throw NoSuchElementException()
        return getIndex(index++)
    }
}

fun TypedArray.forEach(action: (Int) -> Unit) {
    for (attr in this) action(attr)
}