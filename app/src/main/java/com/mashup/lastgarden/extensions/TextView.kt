package com.mashup.lastgarden.extensions

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mashup.lastgarden.R

//TODO custom view로 변경
fun btnThumbsUpDownSelector(
    upButton: TextView,
    downButton: TextView,
    likeState: Boolean,
    context: Context
) {
    if (likeState) {
        upButton.setBackgroundResource(R.drawable.btn_comment_checked_background)
        upButton.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_thumbs_up_filled,
            0,
            0,
            0
        )
        upButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.primaryColor
            )
        )

        downButton.setBackgroundResource(R.drawable.btn_comment_unchecked_background)
        downButton.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_thumbs_down,
            0,
            0,
            0
        )
        downButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorGrey4
            )
        )
    } else {
        upButton.setBackgroundResource(R.drawable.btn_comment_unchecked_background)
        upButton.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_thumbs_up,
            0,
            0,
            0
        )
        upButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorGrey4
            )
        )

        downButton.setBackgroundResource(R.drawable.btn_comment_checked_background)
        downButton.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_thumbs_down_filled,
            0,
            0,
            0
        )
        downButton.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.primaryColor
            )
        )
    }
}