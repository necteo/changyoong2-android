package com.example.data.datasource.remote

import com.example.data.datasource.local.preferences.PreferenceManager
import com.example.data.datasource.remote.service.ExerciseService
import com.example.data.mapper.toEntity
import com.example.data.mapper.toModel
import com.example.data.util.RESPONSE_NULL_ERROR
import com.example.data.util.handleExceptions
import com.example.data.util.handleResponse
import com.example.domain.entity.ExerciseEntity
import com.example.domain.entity.ExercisesSearchRequestEntity
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExerciseRemoteDataSourceImpl @Inject constructor(
    private val exerciseService: ExerciseService,
    private val preferences: PreferenceManager
) : ExerciseRemoteDataSource {

    override fun findAll(): Flow<Result<List<ExerciseEntity>>> = flow {
        emit(Result.Loading)
        val response = exerciseService.findAll()
            ?: run {
                emit(Result.Error(Exception(RESPONSE_NULL_ERROR)))
                return@flow
            }
        emit(response.handleResponse(preferences) {
            it.body()?.toEntity() ?: emptyList<ExerciseEntity>()
        })
    }.handleExceptions()

    override fun searchExerciseList(
        exercisesSearchRequestEntity: ExercisesSearchRequestEntity
    ): Flow<Result<List<ExerciseEntity>>> = flow {
        emit(Result.Loading)
        val response = exerciseService.searchExercises(exercisesSearchRequestEntity.toModel())
            ?: run {
                emit(Result.Error(Exception(RESPONSE_NULL_ERROR)))
                return@flow
            }
        emit(response.handleResponse(preferences) {
            it.body()?.toEntity() ?: emptyList<ExerciseEntity>()
        })
    }.handleExceptions()
}