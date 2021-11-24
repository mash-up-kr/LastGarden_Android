package com.mashup.lastgarden.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.widget.doOnTextChanged
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ViewSearchingEditTextBinding

class SearchEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: ViewSearchingEditTextBinding =
        ViewSearchingEditTextBinding.inflate(LayoutInflater.from(context), this)

    private var onSearchButtonClick: ((String) -> Unit)? = null

    init {
        binding.root.setBackgroundResource(R.drawable.bg_searching_edit_text)

        binding.editText.doOnTextChanged { text, _, _, _ ->
            binding.clearButton.isEnabled = !text.isNullOrBlank()
            binding.clearButton.isInvisible = text.isNullOrBlank()
            binding.searchButton.isEnabled = !text.isNullOrBlank()
        }

        binding.clearButton.setOnClickListener {
            binding.editText.setText("")
        }

        binding.searchButton.setOnClickListener {
            onSearchButtonClick?.invoke(text)
            binding.editText.setText("")
        }
    }

    var text: String
        get() = binding.editText.text.toString()
        set(value) {
            binding.editText.setText(value)
        }

    fun setOnSearchButtonClick(action: (String) -> Unit) {
        onSearchButtonClick = action
    }
}