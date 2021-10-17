package com.mashup.lastgarden.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mashup.base.autoCleared
import com.mashup.base.extensions.showToast
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentUploadBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.upload.perfume.PerfumeSelectedItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@AndroidEntryPoint
class UploadFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentUploadBinding>()
    private val editorViewModel by activityViewModels<UploadViewModel>()

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

        setUiOfToolBar()
        setUiOfSection()
        setUiOfButton()
    }

    override fun onBindViewModelsOnViewCreated() {
        super.onBindViewModelsOnViewCreated()

        editorViewModel.editedImage.observe(viewLifecycleOwner) { imageBitmap ->
            binding.editedImageView.setImageBitmap(imageBitmap)
        }

        editorViewModel.isEnabledUploadButton.observe(viewLifecycleOwner) { isEnabled ->
            binding.uploadButton.isEnabled = isEnabled
        }

        editorViewModel.tagList.observe(viewLifecycleOwner) { tagList ->
            binding.tagSection.selectedValue = tagList.joinToString()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            editorViewModel.selectedPerfume.collectLatest { perfume ->
                if (perfume is PerfumeSelectedItem) {
                    binding.perfumeSection.selectedValue = perfume.name
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            editorViewModel.onStorySaveSuccess.collectLatest { isSuccess ->
                if (isSuccess) {
                    showToast(R.string.upload_success_story_save)
                    requireActivity().finish()
                } else {
                    showToast(R.string.upload_failed_story_save_success)
                }
            }
        }
    }

    private fun setUiOfToolBar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun setUiOfSection() {
        binding.perfumeSection.apply {
            title = getString(R.string.upload_perfume_section_title)
            setOnClickListener {
                findNavController().navigate(R.id.actionUploadFragmentToPerfumeSelectFragment)
            }
        }

        binding.tagSection.apply {
            title = getString(R.string.upload_tag_section_title)
            setOnClickListener {
                findNavController().navigate(R.id.actionUploadFragmentToTagInputFragment)
            }
        }
    }

    private fun setUiOfButton() {
        binding.uploadButton.setOnClickListener {
            editorViewModel.uploadStory()
        }
    }

}