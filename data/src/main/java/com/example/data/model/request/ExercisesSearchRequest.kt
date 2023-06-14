package com.example.data.model.request

import com.example.domain.entity.ExercisePartTypeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExercisesSearchRequest(
    @SerialName("isEquipment")
    val isEquipment: Boolean,
    @SerialName("partNames")
    val partNames: List<ExercisePartTypeEntity>
)
