package com.example.presentation.mapper

import com.example.domain.entity.ExerciseEntity
import com.example.presentation.model.Exercise
import com.example.presentation.ui.exerciselist.ExercisePartType

internal fun List<ExerciseEntity>.toUiModel(): List<Exercise> = map {
    Exercise(
        id = it.id,
        name = it.name,
        isEquipment = it.isEquipment,
        img = it.img,
        info = it.info,
        parts = it.parts.map { exercisePartTypeEntity ->
            ExercisePartType.valueOf(exercisePartTypeEntity.name)
        }
    )
}