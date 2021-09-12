package com.mashup.base.extensions

import android.content.Intent

fun getShareIntent(text: String): Intent = Intent()
    .setAction(Intent.ACTION_SEND)
    .putExtra(Intent.EXTRA_TEXT, text)
    .setType("text/plain")
