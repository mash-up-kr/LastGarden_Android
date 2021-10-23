package com.mashup.lastgarden.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.base.autoCleared
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentPerfumeDetailBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfumeDetailFragment : BaseViewModelFragment() {

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
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)
        initToolbar()
        initTabLayout()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.navigationIcon?.setTint(Color.BLACK)
    }

    private fun initTabLayout() {
        viewPagerAdapter = PerfumeDetailPagerAdapter(this)
        viewPagerAdapter.fragments = listOf(
            PerfumeInformationFragment(),
            ScentListFragment()
        )
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_perfume)
                1 -> tab.setIcon(R.drawable.ic_grid)
            }
        }.attach()
    }
}