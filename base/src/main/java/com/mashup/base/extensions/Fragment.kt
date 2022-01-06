package com.mashup.base.extensions

import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mashup.base.R

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

fun Fragment.showAssignUserAskDialog(
    onClickPositiveButton: (() -> Unit)? = null,
    onClickNegativeButton: (() -> Unit)? = null
) {
    AlertDialog.Builder(requireContext()).apply {
        setTitle(R.string.assign_user_ask_dialog_title)
        setMessage(R.string.assign_user_ask_dialog_message)
        setPositiveButton(R.string.assign_user_ask_dialog_positive) { _, _ ->
            onClickPositiveButton?.invoke()
        }
        setNegativeButton(R.string.assign_user_ask_dialog_negative) { _, _ ->
            onClickNegativeButton?.invoke()
        }
    }.show()
}

inline fun Fragment.share(text: () -> String) {
    startActivity(getShareIntent(text()))
}