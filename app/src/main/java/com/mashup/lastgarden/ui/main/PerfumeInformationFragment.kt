package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentPerfumeInformationBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment

class PerfumeInformationFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentPerfumeInformationBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeInformationBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}