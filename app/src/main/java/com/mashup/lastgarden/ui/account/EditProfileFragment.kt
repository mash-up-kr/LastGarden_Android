package com.mashup.lastgarden.ui.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mashup.base.autoCleared
import com.mashup.base.extensions.createGalleryIntent
import com.mashup.base.image.GlideRequests
import com.mashup.base.image.transformation.RoundedTransformation
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.databinding.FragmentEditProfileBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.MeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : BaseViewModelFragment() {

    companion object {
        private const val TAG = "EditProfileFragment"
    }

    private val meViewModel by activityViewModels<MeViewModel>()
    private val viewModel by viewModels<EditProfileViewModel>()

    private var binding by autoCleared<FragmentEditProfileBinding>()

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        handleGalleryIntentInput(activityResult.resultCode, activityResult.data)
    }

    @Inject
    lateinit var glideRequests: GlideRequests

    @Inject
    lateinit var sharedPreferences: PerfumeSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        setupToolbar()
        listOf(binding.profileImage, binding.cameraButton).forEach {
            it.setOnClickListener {
                galleryLauncher.launch(createGalleryIntent())
            }
        }
        binding.nicknameEditView.doOnTextChanged { text, _, _, _ ->
            viewModel.setNickname(text.toString())
        }
        binding.editButton.setOnClickListener {
            viewModel.editProfile(requireContext())
        }
    }

    private fun handleGalleryIntentInput(resultCode: Int, intent: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            viewModel.setProfileUri(intent?.data)
            return
        }
    }

    private fun setupToolbar() {
        binding.toolbar.navigationIcon?.mutate()?.setTint(resources.getColor(R.color.point, null))
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.inputProfileUri.collectLatest {
                glideRequests.load(it)
                    .placeholder(R.drawable.ic_profile_92dp)
                    .error(R.drawable.ic_profile_92dp)
                    .transform(
                        CircleCrop(),
                        RoundedTransformation(
                            isOval = true,
                            borderWidthDp = 1f,
                            borderColor = resources.getColor(R.color.colorGrey5, null)
                        )
                    )
                    .into(binding.profileImage)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.inputNickname.collectLatest { nickname ->
                if (binding.nicknameEditView.text.toString() != nickname) {
                    binding.nicknameEditView.setText(nickname)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoading.collectLatest {
                binding.loadingView.isVisible = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isChanged.collectLatest {
                binding.editButton.isEnabled = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isFinished.collectLatest {
                findNavController().popBackStack()
            }
        }
    }
}