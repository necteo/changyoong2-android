package com.example.presentation.model

import com.example.presentation.ui.exerciselist.ExercisePartType

data class Exercise(
    val id: Long,
    val name: String,
    val isEquipment: Boolean,
    val img: String,
    val info: String,
    val parts: List<ExercisePartType>
)