package com.example.domain.repository

import com.example.domain.entity.ExerciseEntity
import com.example.domain.entity.ExercisesSearchRequestEntity
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ExerciseSearchRepository {

    fun searchExerciseList(
        exercisesSearchRequestEntity: ExercisesSearchRequestEntity
    ): Flow<Result<List<ExerciseEntity>>>
}