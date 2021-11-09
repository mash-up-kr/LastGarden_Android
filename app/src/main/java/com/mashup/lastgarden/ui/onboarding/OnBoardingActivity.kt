package com.mashup.lastgarden.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mashup.lastgarden.R
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

        binding.onBoardingViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (binding.onBoardingViewPager.currentItem) {
                        2 -> binding.nextButton.text = getString(R.string.tutorial_btn_start)
                        else -> binding.nextButton.text = getString(R.string.tutorial_btn_next)
                    }
                }
            })
    }

    private fun setupViewPager() {
        val fragmentList: MutableList<Fragment> = arrayListOf()
        for (page in 0 until 3) {
            fragmentList.add(TutorialFragment.createInstance(page))
        }

        binding.onBoardingViewPager.adapter = TutorialViewPagerAdapter(this, fragmentList)
        TabLayoutMediator(binding.tabLayout, binding.onBoardingViewPager) { _, _ -> }.attach()

        binding.nextButton.setOnClickListener {
            val tutorialSize = binding.onBoardingViewPager.adapter?.itemCount ?: 0
            if (tutorialSize - 1 > binding.onBoardingViewPager.currentItem) {
                binding.onBoardingViewPager.currentItem++
            } else {
                moveSignActivity()
            }
        }
    }

    private fun moveSignActivity() {
        startActivity(Intent(this, SignActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        if (binding.onBoardingViewPager.currentItem <= 0) {
            super.onBackPressed()
        } else {
            binding.onBoardingViewPager.currentItem =
                max(binding.onBoardingViewPager.currentItem - 1, 0)
        }
    }
}