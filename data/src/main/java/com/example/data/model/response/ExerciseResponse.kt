package com.example.data.model.response

import com.example.domain.entity.ExercisePartTypeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("isEquipment")
    val isEquipment: Boolean,
    @SerialName("img")
    val img: String,
    @SerialName("info")
    val info: String,
    @SerialName("parts")
    val parts: List<ExercisePartTypeEntity>
)
