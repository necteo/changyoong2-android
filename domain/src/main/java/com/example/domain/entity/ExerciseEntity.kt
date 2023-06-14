package com.example.domain.entity

data class ExerciseEntity(
    val id: Long,
    val name: String,
    val isEquipment: Boolean,
    val img: String,
    val info: String,
    val parts: List<ExercisePartTypeEntity>
)