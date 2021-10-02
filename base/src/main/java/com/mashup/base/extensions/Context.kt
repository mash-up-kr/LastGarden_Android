package com.mashup.base.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import java.util.*

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Context.showToast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, textId, duration).show()

fun Context.createGalleryIntent(
    forceChooser: Boolean = false,
    chooserTitle: String? = null,
    isMultiple: Boolean = false
): Intent {
    val intent = if (Build.MANUFACTURER.lowercase(Locale.ENGLISH) == "samsung" && isMultiple) {
        Intent(Intent.ACTION_GET_CONTENT)
    } else {
        Intent(Intent.ACTION_PICK)
    }.setType("image/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMultiple)
    return if (forceChooser) Intent.createChooser(intent, chooserTitle) else intent
}

fun Context.createFileBrowserIntent(
    forceChooser: Boolean = false,
    chooserTitle: String? = null,
    isMultiple: Boolean = false
): Intent {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).setType("*/*")
        .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMultiple)
    return if (forceChooser) Intent.createChooser(intent, chooserTitle) else intent
}

fun Context.startFileViewerIntent(
    tag: String,
    url: String,
    mimeType: String,
    forceChooser: Boolean = false,
    title: String? = null,
    startIntent: (Intent) -> Unit
) {
    val packageManager = packageManager ?: return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val componentName = intent.resolveActivity(packageManager)
    if (componentName != null) {
        startIntent(if (forceChooser) Intent.createChooser(intent, title) else intent)
        Log.d(tag, "Open the file($url, $mimeType) in $componentName")
    } else {
        Log.e(tag, "Could not find any app to open the file($url, $mimeType)")
    }
}