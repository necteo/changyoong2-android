package com.example.presentation.mapper

import com.example.domain.entity.ExercisePartTypeEntity
import com.example.domain.entity.ExercisesSearchRequestEntity
import com.example.presentation.model.ExercisesSearchRequest

internal fun ExercisesSearchRequest.toEntity() = ExercisesSearchRequestEntity(
    isEquipment = isEquipment,
    partNames = partNames.map {
        ExercisePartTypeEntity.valueOf(it.name)
    }
)