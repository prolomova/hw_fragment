package com.example.hw_fragment.ui.infrastructure

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.ui.home.HabitListFragment

class HomeAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val tabNumber = 2

    override fun getItemCount(): Int {
        return tabNumber
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HabitListFragment.newInstance(
                HabitType.BAD)
            else -> HabitListFragment.newInstance(
                HabitType.GOOD)
        }
    }
}