package com.example.domain.usecase

import com.example.domain.entity.ExerciseEntity
import com.example.domain.entity.ExercisesSearchRequestEntity
import com.example.domain.repository.ExerciseRepository
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseSearchUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    operator fun invoke(
        exercisesSearchRequestEntity: ExercisesSearchRequestEntity
    ) : Flow<Result<List<ExerciseEntity>>> {
        return exerciseRepository.searchExerciseList(exercisesSearchRequestEntity)
    }
}