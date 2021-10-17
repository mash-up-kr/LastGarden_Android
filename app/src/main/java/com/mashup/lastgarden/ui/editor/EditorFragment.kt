package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentEditorBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import ja.burhanrashid52.photoeditor.PhotoEditor
import javax.inject.Inject

@AndroidEntryPoint
class EditorFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentEditorBinding>()
    private val editorViewModel by activityViewModels<EditorViewModel>()

    private var editor: PhotoEditor? = null

    @Inject
    lateinit var glideRequests: GlideRequests

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
        observeViewModel()

        binding.closeButton.setOnClickListener {
            requireActivity().finish()
        }
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(
                R.id.actionEditorFragmentToEditorUploadFragment
            )
        }
    }

    private fun initEditorView() {
        editor = PhotoEditor.Builder(requireContext(), binding.photoEditorView)
            .setPinchTextScalable(true)
            .setClipSourceImage(true)
            .build()
    }

    private fun observeViewModel() {
        editorViewModel.imageUrl.observe(this) { imageUrl ->
            setImageUsingFileUrl(imageUrl)
        }
    }

    private fun setImageUsingFileUrl(url: String) {
        binding.photoEditorView.source.loadImage(
            glideRequests = glideRequests,
            imageUrl = url
        )
    }
}