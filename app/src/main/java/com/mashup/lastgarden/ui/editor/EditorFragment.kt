package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentEditorBinding
import com.mashup.lastgarden.databinding.FragmentMainBinding
import com.mashup.lastgarden.databinding.FragmentPerfumeDetailBinding
import com.mashup.lastgarden.databinding.FragmentScentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditorFragment : Fragment() {

    private var binding by autoCleared<FragmentEditorBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditorBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}