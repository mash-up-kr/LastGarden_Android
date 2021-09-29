package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentPerfumeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfumeDetailFragment : Fragment() {

    private var binding by autoCleared<FragmentPerfumeDetailBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}