package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.FragmentMainBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentMainBinding>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        binding.test.setSourceImage(
            glideRequests,
            "https://img.lovepik.com/element/40032/9065.png_860.png"
        )
        binding.test.setUserImage(
            glideRequests,
            "https://img.lovepik.com/element/40032/9065.png_860.png"
        )
    }
}