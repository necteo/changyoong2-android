package com.example.data.repository

import com.example.data.datasource.remote.ExerciseRemoteDataSource
import com.example.domain.entity.ExerciseEntity
import com.example.domain.entity.ExercisesSearchRequestEntity
import com.example.domain.repository.ExerciseSearchRepository
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseSearchRepositoryImpl @Inject constructor(
    private val exerciseRemoteDataSource: ExerciseRemoteDataSource
) : ExerciseSearchRepository {

    override fun searchExerciseList(
        exercisesSearchRequestEntity: ExercisesSearchRequestEntity
    ): Flow<Result<List<ExerciseEntity>>> {
        return exerciseRemoteDataSource.searchExerciseList(exercisesSearchRequestEntity)
    }
}