package com.mashup.lastgarden.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mashup.base.autoCleared
import com.mashup.base.image.GlideRequests
import com.mashup.base.image.transformation.RoundedTransformation
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentMyAccountBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.MeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MyAccountFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentMyAccountBinding>()

    private val meViewModel by activityViewModels<MeViewModel>()

    @Inject
    lateinit var glideRequests: GlideRequests

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        binding.editProfileView.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
        binding.toolbarSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            meViewModel.profileImage.collectLatest {
                glideRequests.load(it)
                    .placeholder(R.drawable.ic_profile_80dp)
                    .error(R.drawable.ic_profile_80dp)
                    .transform(
                        CircleCrop(),
                        RoundedTransformation(
                            isOval = true,
                            borderWidthDp = 1f,
                            borderColor = resources.getColor(R.color.white, null)
                        )
                    )
                    .into(binding.userProfileImage)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            meViewModel.name.collectLatest { userName ->
                binding.userNameView.text = userName
            }
        }
    }
}