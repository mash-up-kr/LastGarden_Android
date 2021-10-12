package com.mashup.lastgarden.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TutorialViewPagerAdapter(
    onBoardingActivity: OnBoardingActivity,
    private val fragmentList: List<Fragment>
) :
    FragmentStateAdapter(onBoardingActivity) {

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragmentList[0]
            1 -> fragmentList[1]
            else -> fragmentList[2]
        }
    }

}