package com.mashup.lastgarden.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mashup.base.autoCleared
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.FragmentUploadBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UploadFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentUploadBinding>()
    private val editorViewModel by activityViewModels<EditorViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        observeViewModel()

        binding.uploadButton.setOnClickListener {
            //TODO: upload 로직 추가
        }
    }

    private fun observeViewModel() {
        editorViewModel.imageUrl.observe(this) { uri ->
            binding.imageView.loadImage(
                glideRequests,
                uri.toString()
            )
        }
    }
}