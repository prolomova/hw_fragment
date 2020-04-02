package com.example.hw_fragment.ui.add

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.R
import com.example.hw_fragment.databinding.AddNewItemFragmentBinding
import com.example.hw_fragment.internal.Storage
import kotlinx.android.synthetic.main.add_new_item_fragment.*
import java.util.*


class AddNewItemFragment : Fragment() {

    companion object {
        const val INDEX = "INDEX"
    }
    
    private val defaultHabit: String = "NewHabit"
    lateinit var addNewItemViewModel: AddNewItemViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val index : Int = arguments!!.getInt(INDEX)
        addNewItemViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AddNewItemViewModel(Storage.get(index)) as T
            }
        }).get(AddNewItemViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = DataBindingUtil.inflate<AddNewItemFragmentBinding>(
            inflater, R.layout.add_new_item_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.addNewItemViewModel = addNewItemViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index: Int? = null
        name.hint = defaultHabit + Storage.index.toString()
        name.text = SpannableStringBuilder(defaultHabit + Storage.index.toString())
        if (arguments!!.containsKey(INDEX)) {
            index = arguments!!.getInt(INDEX)
            val editedHabit = Storage.get(index)

            addNewItemViewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
                habit?.let { fillFields(editedHabit) }
            })
        }

        add.setOnClickListener { v: View ->
            if (isFilled()) {
                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "Fill all params",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                return@setOnClickListener
            }
            addNewItemViewModel.save(fillHabit(index))
            v.findNavController().navigate(R.id.from_add_new_item_fragment_to_home)
        }

        cancel.setOnClickListener { v: View ->
            v.findNavController().navigate(R.id.from_add_new_item_fragment_to_home)
        }
    }

    private fun isFilled() : Boolean {
        return name.text.toString().isEmpty() ||
                periodicity.text.toString().isEmpty() ||
                quantity.text.toString().isEmpty() ||
                type.checkedRadioButtonId == -1
    }

    private fun fillFields(editedHabit: Habit?) {
        if (editedHabit != null) {
            name.text = SpannableStringBuilder(editedHabit.name)
            description.text = SpannableStringBuilder(editedHabit.description)
            priority.progress = editedHabit.priority
            type.check(if (editedHabit.type == HabitType.GOOD) typeGoodBtn.id else typeBadBtn.id)
            quantity.text = SpannableStringBuilder(editedHabit.quantity.toString())
            periodicity.text = SpannableStringBuilder(editedHabit.periodicity.toString())
        }
    }

    private fun fillHabit(index: Int?): Habit {
        return Habit(
            name = name.text.toString(),
            description = description.text.toString(),
            priority = priority.progress,
            type = if (typeBadBtn.id == type.checkedRadioButtonId) HabitType.BAD else HabitType.GOOD,
            quantity = quantity.text.toString().toInt(),
            periodicity = periodicity.text.toString().toInt(),
            id = index,
            date = Date()
        )
    }
}
