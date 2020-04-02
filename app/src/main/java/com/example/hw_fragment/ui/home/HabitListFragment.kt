package com.example.hw_fragment.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.findNavController
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.internal.Storage
import com.example.hw_fragment.ui.add.AddNewItemFragment
import com.example.hw_fragment.ui.infrastructure.HabitListAdapter
import com.example.hw_fragment.ui.infrastructure.RecyclerItemClickListener
import kotlinx.android.synthetic.main.habit_list.*


class HabitListFragment : Fragment() {

    lateinit var habitAdapter: HabitListAdapter
    lateinit var viewModel: HabitListViewModel
    private var habitType = HabitType.NONE

    companion object {
        const val TYPE = "TYPE"

        fun newInstance(habitType: HabitType): HabitListFragment {

            return HabitListFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(TYPE, habitType)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            habitType = it.getSerializable(TYPE) as HabitType
        }

        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            habitAdapter = when (habitType) {
                HabitType.GOOD -> HabitListAdapter(
                    habits.filter { it.type == HabitType.GOOD })
                else -> HabitListAdapter(
                    habits.filter { it.type == HabitType.BAD })
            }
            habit_recycler_view.layoutManager = LinearLayoutManager(context)
            habit_recycler_view.adapter = habitAdapter
            (habit_recycler_view.adapter as HabitListAdapter).notifyDataSetChanged()
            habit_recycler_view.addOnItemTouchListener(
                RecyclerItemClickListener(
                    habit_recycler_view,
                    object :
                        RecyclerItemClickListener.OnItemClickListener {

                        override fun onItemClick(view: View, position: Int) {
                            val bundle = Bundle().apply {
                                putInt(AddNewItemFragment.INDEX, habitAdapter.habits[position].id!!)
                            }
                            view.findNavController().navigate(
                                R.id.from_home_to_add_new_item_fragment,
                                bundle
                            )
                        }
                    })
            )

            nameSubstr.addTextChangedListener {

                viewModel.nameSubstring.value = it.toString()
            }

            sort.setOnClickListener {
                viewModel.sortDate()
            }

            sort_descending.setOnClickListener {
                viewModel.sortDateDescending()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        habitAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = arguments?.getSerializable(TYPE) as HabitType
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitListViewModel { habit: Habit -> habit.type == type } as T
            }
        }).get(HabitListViewModel::class.java)
    }

}
