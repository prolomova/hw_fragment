package com.example.hw_fragment.ui.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.hw_fragment.R
import com.example.hw_fragment.ui.add.AddNewItemFragment
import com.example.hw_fragment.ui.infrastructure.HomeAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*


class HomeFragment : Fragment() {
    private val viewModel: HabitListViewModel by activityViewModels()


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager_home_fragment.adapter =
            HomeAdapter(this)
        FAB.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.from_home_to_add_new_item_fragment)
        )
        tab_layout.setBackgroundColor(resources.getColor(R.color.zeroAlpha))
        TabLayoutMediator(tab_layout, view_pager_home_fragment) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.bad)
                else -> getString(R.string.good)
            }
            tab.icon = when (position) {
                0 -> context?.getDrawable(android.R.drawable.ic_delete)
                else -> context?.getDrawable(android.R.drawable.star_big_on)
            }
        }.attach()

        nameSubstr.addTextChangedListener {
            viewModel.updateNameFilter(it.toString())
        }

        sort.setOnClickListener {
            viewModel.sortDate()
            sort.setBackgroundColor(resources.getColor(R.color.purpleBtnOn))
            sort_descending.setBackgroundColor(resources.getColor(R.color.purpleBtnOff))
        }

        sort_descending.setOnClickListener {
            viewModel.sortDateDescending()
            sort.setBackgroundColor(resources.getColor(R.color.purpleBtnOff))
            sort_descending.setBackgroundColor(resources.getColor(R.color.purpleBtnOn))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.home_fragment, container, false)
}
