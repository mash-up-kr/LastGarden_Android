package com.mashup.lastgarden.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TutorialViewPagerAdapter(
    onBoardingActivity: OnBoardingActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(onBoardingActivity) {

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]

}