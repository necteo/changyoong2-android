package com.example.domain.entity

data class ExercisesSearchRequestEntity (
    val isEquipment: Boolean,
    val partNames: List<ExercisePartTypeEntity>
)