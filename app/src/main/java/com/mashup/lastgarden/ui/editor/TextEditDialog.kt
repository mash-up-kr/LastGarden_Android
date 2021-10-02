package com.mashup.lastgarden.ui.editor

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.mashup.lastgarden.databinding.DialogAddTextBinding


class TextEditorDialogFragment : DialogFragment() {

    private var binding: DialogAddTextBinding? = null

    private var mInputMethodManager: InputMethodManager? = null
    private var mColorCode = 0
    private var mTextEditor: TextEditor? = null

    interface TextEditor {
        fun onDone(inputText: String?, colorCode: Int)
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog

        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogAddTextBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInputMethodManager =
            requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        binding?.addEditText?.setText(arguments?.getString(EXTRA_INPUT_TEXT))
        mColorCode = arguments?.getInt(EXTRA_COLOR_CODE) ?: 0

        binding?.addEditText?.setTextColor(mColorCode)
        mInputMethodManager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        binding?.addTextDone?.setOnClickListener {
            mInputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            dismiss()
            val inputText = binding?.addEditText?.text.toString()
            if (!TextUtils.isEmpty(inputText) && mTextEditor != null) {
                mTextEditor?.onDone(inputText, mColorCode)
            }
        }
    }

    fun setOnTextEditorListener(textEditor: TextEditor?) {
        mTextEditor = textEditor
    }

    companion object {
        private val TAG = TextEditorDialogFragment::class.java.simpleName
        const val EXTRA_INPUT_TEXT = "extra_input_text"
        const val EXTRA_COLOR_CODE = "extra_color_code"
    }
}