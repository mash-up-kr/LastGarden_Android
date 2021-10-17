package com.mashup.lastgarden.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mashup.lastgarden.databinding.ViewSectionSelectedLabelBinding

class SectionSelectedLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: ViewSectionSelectedLabelBinding =
        ViewSectionSelectedLabelBinding.inflate(LayoutInflater.from(context), this)

    var title: String?
        get() = binding.leftTextView.text.toString()
        set(value) {
            binding.leftTextView.text = value
        }

    var selectedValue: String?
        get() = binding.rightTextView.text.toString()
        set(value) {
            binding.rightTextView.text = value
        }
}