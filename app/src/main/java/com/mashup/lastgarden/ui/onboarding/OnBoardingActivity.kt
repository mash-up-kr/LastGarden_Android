package com.mashup.lastgarden.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.lastgarden.databinding.ActivityOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()

        binding.vpOnboarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (binding.vpOnboarding.currentItem) {
                    3 -> binding.btnNext.text = "시작하기"
                    else -> binding.btnNext.text = "다음"

                }
            }
        })
    }

    private fun setupViewPager() {
        binding.vpOnboarding.adapter = TutorialViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.vpOnboarding) { tab, _ ->
        }.attach()

        binding.btnNext.setOnClickListener {
            binding.vpOnboarding.currentItem++
        }
    }

    override fun onBackPressed() {
        if (binding.vpOnboarding.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.vpOnboarding.currentItem = binding.vpOnboarding.currentItem - 1
        }
    }
}