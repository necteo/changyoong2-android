package com.example.data.repository

import com.example.data.datasource.remote.ExerciseRemoteDataSource
import com.example.domain.entity.ExerciseEntity
import com.example.domain.entity.ExercisesSearchRequestEntity
import com.example.domain.repository.ExerciseRepository
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseRemoteDataSource: ExerciseRemoteDataSource
) : ExerciseRepository {

    override fun findAll(): Flow<Result<List<ExerciseEntity>>> {
        return exerciseRemoteDataSource.findAll()
    }

    override fun searchExerciseList(
        exercisesSearchRequestEntity: ExercisesSearchRequestEntity
    ): Flow<Result<List<ExerciseEntity>>> {
        return exerciseRemoteDataSource.searchExerciseList(exercisesSearchRequestEntity)
    }
}