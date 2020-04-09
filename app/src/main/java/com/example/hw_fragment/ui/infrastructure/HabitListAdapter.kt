package com.example.hw_fragment.ui.infrastructure

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.Habit
import com.example.hw_fragment.internal.HabitType


class HabitListAdapter(val habits: List<Habit>)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.new_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habitTitle = holder.listItemView.findViewById(R.id.habitTitle) as TextView
        val habitPriority = holder.listItemView.findViewById(R.id.stars) as RatingBar
        val habitPeriod = holder.listItemView.findViewById(R.id.habitPeriod) as TextView
        val habitQuantity = holder.listItemView.findViewById(R.id.habitQuantity) as TextView
        val habitColor = holder.listItemView.findViewById(R.id.color) as ImageView

        habitTitle.text = habits[position].name
        habitPeriod.text = habits[position].periodicity.toString()
        habitQuantity.text = habits[position].quantity.toString()
        habitPriority.numStars = habits[position].priority
        habitColor.imageTintList = habits[position].color

        when (habits[position].type) {
            HabitType.BAD -> holder.listItemView.setBackgroundResource(R.drawable.oval_bad)
            else -> holder.listItemView.setBackgroundResource(R.drawable.oval_good)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val listItemView: ConstraintLayout = view.findViewById(R.id.new_list_item)
}