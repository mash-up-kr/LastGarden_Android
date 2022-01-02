package com.mashup.lastgarden.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.mashup.lastgarden.R
import com.mashup.lastgarden.ui.main.MainContainer
import com.mashup.lastgarden.ui.sign.SignActivity

abstract class BaseViewModelFragment : Fragment() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreated(savedInstanceState)
        onBindViewModelsOnCreate()
    }

    protected open fun onCreated(savedInstanceState: Bundle?) {}

    protected open fun onBindViewModelsOnCreate() {}

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupViews(view)
        onBindViewModelsOnViewCreated()
    }

    protected open fun onSetupViews(view: View) {
        view.findViewById<Toolbar>(R.id.toolbar)?.let(::setActionBar)
    }

    private fun setActionBar(toolbar: Toolbar) {
        (activity as? MainContainer)?.setMainActionBar(toolbar)
        onToolbarSetup(toolbar)
    }

    fun showAssignUserAskDialog(
        onClickPositiveButton: (() -> Unit)? = null,
        onClickNegativeButton: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.assign_user_ask_dialog_title)
            setMessage(R.string.assign_user_ask_dialog_message)
            setPositiveButton(R.string.assign_user_ask_dialog_positive) { _, _ ->
                onClickPositiveButton?.invoke()
                requireActivity().run {
                    startActivity(
                        Intent(this, SignActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    )
                }
            }
            setNegativeButton(R.string.assign_user_ask_dialog_negative) { _, _ ->
                onClickNegativeButton?.invoke()
            }
        }.show()
    }

    protected open fun onToolbarSetup(toolbar: Toolbar) {}

    protected open fun onBindViewModelsOnViewCreated() {}
}