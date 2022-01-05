package com.mashup.lastgarden.ui.sign

import android.content.Intent
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
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.User
import com.mashup.lastgarden.databinding.FragmentSignCompleteBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SignCompleteFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignCompleteBinding>()
    private val viewModel: SignViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignCompleteBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initToolbar()

        binding.nextButton.setOnClickListener {
            moveMainActivity()
        }
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userState.collectLatest { userState ->
                if (userState is User) {
                    binding.title.text = getString(R.string.sign_complete_title, userState.nickname)
                }
            }
        }
    }

    private fun moveMainActivity() {
        requireActivity().run {
            startActivity(
                Intent(requireContext(), MainActivity::class.java)
            )
            finish()
        }
    }

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }
}