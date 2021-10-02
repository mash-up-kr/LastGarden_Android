package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentScentListBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment

class ScentListFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentScentListBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScentListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}