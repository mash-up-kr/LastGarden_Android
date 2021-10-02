package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.FragmentEditorUploadBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditorUploadFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentEditorUploadBinding>()
    private val activityViewModel by activityViewModels<EditorViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditorUploadBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        observeViewModel()

        binding.uploadButton.setOnClickListener {

        }
    }

    private fun observeViewModel() {
        activityViewModel.imageUri.observe(this) { uri ->
            binding.imageView.loadImage(
                glideRequests,
                uri.toString()
            )
        }
    }
}