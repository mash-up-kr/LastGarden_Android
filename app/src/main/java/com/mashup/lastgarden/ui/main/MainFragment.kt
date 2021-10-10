package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.FragmentMainBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.editor.EditorActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentMainBinding>()

    @Inject
    lateinit var glideRequests: GlideRequests

    private val cropImageLauncher = registerForActivityResult(
        CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            result.getUriFilePath(requireContext())?.let { imageUrl ->
                showEditorActivity(imageUrl)
            }
        }
    }

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

    private fun showImagePicker() {
        cropImageLauncher.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )
    }

    private fun showEditorActivity(imageUrl: String) {
        EditorActivity.newIntent(requireContext(), imageUrl).run {
            startActivity(this)
        }
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