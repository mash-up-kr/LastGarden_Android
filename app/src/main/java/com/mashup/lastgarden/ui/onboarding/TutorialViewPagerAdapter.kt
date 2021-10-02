package com.mashup.lastgarden.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TutorialViewPagerAdapter(onBoardingActivity: OnBoardingActivity) :
    FragmentStateAdapter(onBoardingActivity) {

    companion object {
        private const val NUM_PAGES = 4
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TutorialFirstFragment()
            1 -> TutorialSecondFragment()
            2 -> TutorialThirdFragment()
            else -> TutorialFourthFragment()
        }
    }

}