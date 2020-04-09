package com.example.hw_fragment.ui.add

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType
import com.example.hw_fragment.R
import com.example.hw_fragment.databinding.AddNewItemFragmentBinding
import com.example.hw_fragment.internal.Storage
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.add_new_item_fragment.*
import java.util.*


class AddNewItemFragment : Fragment() {

    companion object {
        const val INDEX = "INDEX"
    }
    
    private val defaultHabit: String = "New Habit"
    lateinit var addNewItemViewModel: AddNewItemViewModel

    private var selectedColor: Int = ColorSheet.NO_COLOR
    private var noColorOption = false

    private var index: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments!!.getInt(INDEX)
        addNewItemViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AddNewItemViewModel(index) as T
            }
        }).get(AddNewItemViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_new_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = resources.getIntArray(R.array.colors)
        name.hint = defaultHabit + " " + Storage.index.toString()
        name.text = SpannableStringBuilder(defaultHabit + " " + Storage.index.toString())
        if (arguments!!.containsKey(INDEX)) {
            index = arguments!!.getInt(INDEX)
            val editedHabit = Storage.get(index)

            addNewItemViewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
                habit?.let { fillFields(editedHabit) }
            })
        }

        add.setOnClickListener { v: View ->
            if (!isFilled()) {
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
            view.findNavController().popBackStack()
        }

        cancel.setOnClickListener { v: View ->
            view.findNavController().popBackStack()
        }
        val selectedColorImageView = iv_selected_color

        iv_selected_color.setOnClickListener{
            ColorSheet().cornerRadius(8)
                .colorPicker(
                    colors = colors,
                    noColorOption = noColorOption,
                    selectedColor = selectedColor,
                    listener = { color ->
                        selectedColor = color
                        ImageViewCompat.setImageTintList(
                            selectedColorImageView,
                            ColorStateList.valueOf(selectedColor)
                        )
                    })
                .show(activity!!.supportFragmentManager)
        }
    }

    private fun isFilled() : Boolean {
        return !(name.text.toString().isEmpty() ||
                periodicity.text.toString().isEmpty() ||
                quantity.text.toString().isEmpty() ||
                type.checkedRadioButtonId == -1 ||
                priority.progress == 0)
    }

    private fun fillFields(editedHabit: Habit?) {
        if (editedHabit != null) {
            name.text = SpannableStringBuilder(editedHabit.name)
            description.text = SpannableStringBuilder(editedHabit.description)
            priority.progress = editedHabit.priority
            type.check(if (editedHabit.type == HabitType.GOOD) typeGoodBtn.id else typeBadBtn.id)
            quantity.text = SpannableStringBuilder(editedHabit.quantity.toString())
            periodicity.text = SpannableStringBuilder(editedHabit.periodicity.toString())
            iv_selected_color.imageTintList = editedHabit.color
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
            date = Date(),
            color = iv_selected_color.imageTintList
        )
    }
}
