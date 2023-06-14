package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R


class ExerciseListAdapter(
    private var exerciseList: ArrayList<String>
) : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseListAdapter.ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExerciseListAdapter.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.exercise_name).text = exerciseList[position]
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}