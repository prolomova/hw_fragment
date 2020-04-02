package com.example.hw_fragment.ui.infrastructure

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_fragment.R
import com.example.hw_fragment.internal.Habit


class HabitListAdapter(val habits: List<Habit>)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
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
        val habitDescription = holder.listItemView.findViewById(R.id.habitDescription) as TextView
        val habitType = holder.listItemView.findViewById(R.id.habitType) as TextView
        val habitPriority = holder.listItemView.findViewById(R.id.habitPriority) as TextView
        val habitPeriod = holder.listItemView.findViewById(R.id.habitPeriod) as TextView
        val habitQuantity = holder.listItemView.findViewById(R.id.habitQuantity) as TextView

        habitTitle.text = habits[position].name
        habitDescription.text = habits[position].description
        habitType.text = habits[position].type.toString()
        habitPriority.text = habits[position].priority.toString()
        habitPeriod.text = habits[position].periodicity.toString()
        habitQuantity.text = habits[position].quantity.toString()
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val listItemView: LinearLayout = view.findViewById(R.id.list_item)
}