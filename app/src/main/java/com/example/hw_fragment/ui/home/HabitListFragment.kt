package com.example.hw_fragment.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.findNavController
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.ui.add.AddNewItemFragment
import com.example.hw_fragment.ui.infrastructure.HabitListAdapter
import com.example.hw_fragment.ui.infrastructure.RecyclerItemClickListener
import kotlinx.android.synthetic.main.habit_list.*


class HabitListFragment : Fragment() {

    val viewModel: HabitListViewModel by activityViewModels()
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

        habit_recycler_view.adapter = HabitListAdapter(listOf())
        habit_recycler_view.layoutManager = LinearLayoutManager(context)
        habit_recycler_view.layoutAnimation = AnimationUtils.loadLayoutAnimation(habit_recycler_view.context, R.anim.layout_animation_waterfall)
        habit_recycler_view.addOnItemTouchListener(
            RecyclerItemClickListener(
                habit_recycler_view,
                object :
                    RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        val bundle = Bundle().apply {
                            putInt(AddNewItemFragment.INDEX,
                                (habit_recycler_view.adapter as HabitListAdapter).habits[position].id!!)
                        }
                        view.findNavController().navigate(
                            R.id.from_home_to_add_new_item_fragment,
                            bundle
                        )
                    }
                })
        )


        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            Log.d("habits size", habits.size.toString())

            (habit_recycler_view.adapter as HabitListAdapter).updateHabits(when (habitType) {
                HabitType.GOOD -> habits.filter { it.type == HabitType.GOOD }
                else -> habits.filter { it.type == HabitType.BAD }
            }
            )

            (habit_recycler_view.adapter as HabitListAdapter).notifyDataSetChanged()

        })
    }
}
