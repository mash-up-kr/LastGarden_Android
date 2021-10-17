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

        setListenerOfSection()
        setUiOfButton()
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        editorViewModel.imageUrl.observe(viewLifecycleOwner) { uri ->
            binding.editedImageView.loadImage(
                glideRequests,
                uri.toString()
            )
        }

        editorViewModel.isEnabledUploadButton.observe(viewLifecycleOwner) { isEnabled ->
            binding.uploadButton.isEnabled = isEnabled
        }

        editorViewModel.tagList.observe(viewLifecycleOwner) { tagList ->
            binding.tagSection.selectedValue = tagList.joinToString()
        }
    }

    private fun setUiOfButton() {
        binding.uploadButton.setOnClickListener {
            //TODO: upload 로직 추가
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setListenerOfSection() {
        binding.perfumeSection.setOnClickListener {
            //TODO: 향수 검색
        }

        binding.tagSection.setOnClickListener {
            findNavController().navigate(R.id.actionUploadFragmentToTagInputFragment)
        }
    }
}