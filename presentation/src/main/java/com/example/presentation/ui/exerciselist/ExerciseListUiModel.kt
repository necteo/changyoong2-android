package com.example.presentation.ui.exerciselist

import com.example.presentation.model.Exercise

sealed class ExerciseListUiModel {
    object ExerciseEmptyList : ExerciseListUiModel()

    data class ExerciseListNameItem(val name: Exercise) : ExerciseListUiModel()
}