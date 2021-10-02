package com.mashup.lastgarden.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentScentListBinding

class ScentListFragment : Fragment() {

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