package com.mashup.base.extensions

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(text, duration)

fun Fragment.showToast(@StringRes textId: Int, duration: Int = Toast.LENGTH_SHORT) =
    context?.showToast(textId, duration)

fun Fragment.createGalleryIntent(
    forceChooser: Boolean = false,
    chooserTitle: String? = null,
    isMultiple: Boolean = false
): Intent = requireContext().createGalleryIntent(forceChooser, chooserTitle, isMultiple)

fun Fragment.createFileBrowserIntent(
    forceChooser: Boolean = false,
    title: String? = null,
    isMultiple: Boolean = false
): Intent = requireContext().createFileBrowserIntent(forceChooser, title, isMultiple)

inline fun Fragment.share(text: () -> String) {
    startActivity(getShareIntent(text()))
}