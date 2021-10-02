package com.mashup.lastgarden.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.lastgarden.databinding.ActivityOnBoardingBinding
import com.mashup.lastgarden.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max

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
        TabLayoutMediator(binding.tabLayout, binding.vpOnboarding) { _, _ ->
        }.attach()

        binding.btnNext.setOnClickListener {
            val tutorialSize = binding.vpOnboarding.adapter?.itemCount ?: 0
            if (tutorialSize - 1 > binding.vpOnboarding.currentItem) {
                binding.vpOnboarding.currentItem++
            } else {
                moveSignActivity()
            }
        }
    }

    private fun moveSignActivity() {
        startActivity(
            Intent(this, SignActivity::class.java)
        )
        finish()
    }

    override fun onBackPressed() {
        if (binding.vpOnboarding.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.vpOnboarding.currentItem =
                max(binding.vpOnboarding.currentItem - 1, 0)
        }
    }
}