package com.example.presentation.ui.exerciselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.presentation.databinding.FragmentExerciseBinding
import com.example.presentation.model.ExercisesSearchRequest
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private val viewModel: ExerciseViewModel by activityViewModels()

    private lateinit var _binding: FragmentExerciseBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        val view = binding.root

        val exercisesSearchRequest = ExercisesSearchRequest(false, mutableListOf())

        val searchButton = view.findViewById<Button>(binding.buttonSearch.id)
        searchButton.setOnClickListener {
            when (binding.equipmentGroup.checkedRadioButtonId) {
                binding.radioButton2.id -> exercisesSearchRequest.isEquipment = true
                binding.radioButton.id -> exercisesSearchRequest.isEquipment = false
            }
            when (binding.partGroup.checkedRadioButtonId) {
                binding.radioButton5.id -> exercisesSearchRequest.partNames.add(ExercisePartType.BACK)
                binding.radioButton6.id -> {
                    exercisesSearchRequest.partNames.add(ExercisePartType.ARM)
                    exercisesSearchRequest.partNames.add(ExercisePartType.SHOULDER)
                }
                binding.radioButton7.id -> {
                    exercisesSearchRequest.partNames.add(ExercisePartType.CHEST)
                    exercisesSearchRequest.partNames.add(ExercisePartType.CORE)
                }
                binding.radioButton8.id -> exercisesSearchRequest.partNames.add(ExercisePartType.LOWER)
            }
            viewModel.searchExerciseList(exercisesSearchRequest)
//            navController.navigate(R.id.action_exerciseFragment_to_exerciseListFragment)
        }

        return view
    }
}
