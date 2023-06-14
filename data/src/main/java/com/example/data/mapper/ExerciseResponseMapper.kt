package com.example.data.mapper

import com.example.data.model.response.ExerciseResponse
import com.example.domain.entity.ExerciseEntity

internal fun List<ExerciseResponse>.toEntity() : List<ExerciseEntity> = map {
    ExerciseEntity(
        id = it.id,
        name = it.name,
        isEquipment = it.isEquipment,
        img = it.img,
        info = it.info,
        parts = it.parts
    )
}