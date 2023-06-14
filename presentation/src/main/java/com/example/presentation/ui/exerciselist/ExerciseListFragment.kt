package com.example.presentation.ui.exerciselist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.adapter.ExerciseListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : Fragment() {

    private val viewModel: ExerciseViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<*>
    private var totalItems: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("exercise list fragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("exercise list fragment", "onCreateView")
        val view = inflater.inflate(R.layout.exercise_list, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.list)
        recyclerView.setHasFixedSize(true)

        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d("exercise list fragment", "onStart")

        for (name in viewModel.exerciseList.value) {
            when (name) {
                is ExerciseListUiModel.ExerciseListNameItem -> totalItems.add(name.name.name)
                else -> totalItems.add("")
            }
        }

        adapter = ExerciseListAdapter(totalItems)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

}