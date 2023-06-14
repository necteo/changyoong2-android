package com.example.presentation.model

import com.example.presentation.ui.exerciselist.ExercisePartType

data class ExercisesSearchRequest(
    var isEquipment: Boolean,
    val partNames: MutableList<ExercisePartType>
)
