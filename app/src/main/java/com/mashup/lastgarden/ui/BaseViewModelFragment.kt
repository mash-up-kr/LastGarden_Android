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

    protected open fun onToolbarSetup(toolbar: Toolbar) {}

    protected open fun onBindViewModelsOnViewCreated() {}
}