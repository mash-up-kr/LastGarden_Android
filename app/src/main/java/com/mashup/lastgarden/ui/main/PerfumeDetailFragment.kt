package com.mashup.lastgarden.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentPerfumeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfumeDetailFragment : Fragment() {

    private var binding by autoCleared<FragmentPerfumeDetailBinding>()
    private lateinit var viewPagerAdapter: PerfumeDetailPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfumeDetailBinding.inflate(
            inflater, container, false
        )
        initTabLayout()
        return binding.root
    }

    private fun initTabLayout() {
        val tabIconList = listOf(
            R.drawable.tab_icon_perfume_selector, R.drawable.tab_icon_squares_four_selector
        )
        viewPagerAdapter = PerfumeDetailPagerAdapter(requireActivity())
        viewPagerAdapter.fragments = listOf(
            PerfumeInformationFragment(),
            ScentListFragment()
        )
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setIcon(tabIconList[position])
        }.attach()
    }
}