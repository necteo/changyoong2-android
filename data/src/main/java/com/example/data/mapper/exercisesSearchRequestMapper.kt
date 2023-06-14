package com.example.data.mapper

import com.example.data.model.request.ExercisesSearchRequest
import com.example.domain.entity.ExercisesSearchRequestEntity

internal fun ExercisesSearchRequestEntity.toModel() = ExercisesSearchRequest(
    isEquipment = isEquipment,
    partNames = partNames
)