package com.mashup.lastgarden.ui.upload

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.DialogAddTextBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextEditorDialog : DialogFragment() {

    companion object {
        private val TAG = TextEditorDialog::class.java.simpleName
        const val EXTRA_INPUT_TEXT = "extra_input_text"
        const val EXTRA_COLOR = "extra_color"
    }

    private var binding by autoCleared<DialogAddTextBinding>()

    private var mInputMethodManager: InputMethodManager? = null
    private var onCompleteEditText: ((String, Int?) -> Unit)? = null

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            window?.setLayout(width, height)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddTextBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiOfEditText()

        binding.addTextDone.setOnClickListener {
            mInputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            val textColor = arguments?.getInt(EXTRA_COLOR)
            val editedText = binding.addEditText.text.toString()
            if (!TextUtils.isEmpty(editedText)) {
                onCompleteEditText?.invoke(editedText, textColor)
            }
            dismiss()
        }
    }

    fun setOnTextEditorListener(listener: (String, Int?) -> Unit) {
        onCompleteEditText = listener
    }

    private fun setUiOfEditText() = with(binding.addEditText) {
        mInputMethodManager =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)

        setText(arguments?.getString(EXTRA_INPUT_TEXT))
        setTextColor(arguments?.getInt(EXTRA_COLOR) ?: 0)
    }
}