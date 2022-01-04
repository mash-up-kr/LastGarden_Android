package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentMainContainerBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.account.MyAccountFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainContainerFragment : BaseViewModelFragment() {

    private lateinit var tabLayoutMediator: TabLayoutMediator

    private var binding by autoCleared<FragmentMainContainerBinding>()

    private val viewModel: MainContainerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSetupViews(view: View) {
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = PagerAdapter(this@MainContainerFragment)
            setCurrentItem(0, false)
            isUserInputEnabled = false
        }
    }

    private fun setupTabLayout() {
        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    1 -> tab.setIcon(R.drawable.ic_profile_38dp)
                    else -> tab.setIcon(R.drawable.ic_home_38dp)
                }
            }
        tabLayoutMediator.attach()
    }

    override fun onBindViewModelsOnViewCreated() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.mainContainerPosition.collectLatest { position ->
                position.let {
                    binding.viewPager.setCurrentItem(position, false)
                }
            }
        }
    }

    private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when (position) {
            MainContainerPagerType.ACCOUNT.position -> MyAccountFragment()
            else -> MainFragment()
        }
    }

    enum class MainContainerPagerType(val position: Int) {
        MAIN(0), ACCOUNT(1)
    }
}