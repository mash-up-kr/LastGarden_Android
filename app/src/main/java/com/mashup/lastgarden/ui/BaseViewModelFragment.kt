package com.mashup.lastgarden.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

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

    protected open fun onSetupViews(view: View) {}

    protected open fun onBindViewModelsOnViewCreated() {}
}