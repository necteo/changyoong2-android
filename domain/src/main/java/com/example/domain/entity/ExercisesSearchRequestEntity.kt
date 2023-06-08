package com.example.domain.entity

data class ExerciseSearchRequestEntity (
    val equipment: Boolean,
    val partName: List<ExercisePartTypeEntity>
)