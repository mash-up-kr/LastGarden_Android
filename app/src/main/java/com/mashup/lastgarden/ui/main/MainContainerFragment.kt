package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentMainContainerBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import com.mashup.lastgarden.ui.account.MyAccountFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainContainerFragment : BaseViewModelFragment() {

    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var pagerAdapter: PagerAdapter

    private var binding by autoCleared<FragmentMainContainerBinding>()

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        pagerAdapter = PagerAdapter(this)
    }

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
            adapter = pagerAdapter
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

    private class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when (position) {
            1 -> MyAccountFragment()
            else -> MainFragment()
        }
    }

}