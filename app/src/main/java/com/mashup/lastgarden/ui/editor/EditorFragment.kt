package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mashup.base.autoCleared
import com.mashup.lastgarden.databinding.FragmentEditorBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import ja.burhanrashid52.photoeditor.PhotoEditor

@AndroidEntryPoint
class EditorFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentEditorBinding>()

    private var editor: PhotoEditor? = null

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

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)

        initEditorView()
    }

    private fun initEditorView() {
        editor = PhotoEditor.Builder(requireContext(), binding.photoEditorView)
            .setPinchTextScalable(true)
            .setClipSourceImage(true)
            .build();
    }
}