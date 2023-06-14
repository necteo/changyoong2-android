package com.example.presentation.ui.exerciselist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ExerciseFindAllUseCase
import com.example.domain.usecase.ExerciseSearchUseCase
import com.example.domain.util.Result
import com.example.presentation.mapper.toEntity
import com.example.presentation.mapper.toUiModel
import com.example.presentation.model.Exercise
import com.example.presentation.model.ExercisesSearchRequest
import com.example.presentation.ui.base.BaseViewModel
import com.example.presentation.util.INTERNET_CONNECTION_ERROR
import com.example.presentation.util.SERVER_CONNECTION_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseSearchUseCase: ExerciseSearchUseCase,
    private val exerciseFindAllUseCase: ExerciseFindAllUseCase
) : BaseViewModel() {

    private val _exerciseList = MutableStateFlow<List<ExerciseListUiModel>>(emptyList())
    val exerciseList = _exerciseList.asStateFlow()

    private val _exerciseAllList = MutableStateFlow<List<ExerciseListUiModel>>(emptyList())
    val exerciseAllList = _exerciseAllList.asStateFlow()

    fun findAllExercise() = viewModelScope.launch {
        exerciseFindAllUseCase()
            .onEach { result ->
                setLoading(result is Result.Loading)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> return@collect
                    is Result.Success -> {
                        setInternetError(false)
                        Log.d("findAllExercise", "result[0]: ${result.data[0]}")
                        setExerciseAllList(result.data.toUiModel())
                    }
                    is Result.Error -> {
                        when (result.throwable.message) {
                            SERVER_CONNECTION_ERROR -> {
                                showToast("SERVER_CONNECTION_ERROR")
                            }
                            INTERNET_CONNECTION_ERROR -> {
                                setInternetError(true)
                            }
                            else -> showToast("fail")
                        }
                    }
                }
            }
    }

    fun searchExerciseList(
        exercisesSearchRequest: ExercisesSearchRequest
    ) = viewModelScope.launch {
        exerciseSearchUseCase(exercisesSearchRequest.toEntity())
            .onEach { result ->
                setLoading(result is Result.Loading)
            }.collect { result ->
                when (result) {
                    is Result.Loading -> return@collect
                    is Result.Success -> {
                        setInternetError(false)
                        Log.d("viewModel->searchExerciseList", "result[0]: ${result.data[0]}")
                        setExerciseList(result.data.toUiModel())
                    }
                    is Result.Error -> {
                        when (result.throwable.message) {
                            SERVER_CONNECTION_ERROR -> {
                                showToast("SERVER_CONNECTION_ERROR")
                            }
                            INTERNET_CONNECTION_ERROR -> {
                                setInternetError(true)
                            }
                            else -> showToast("fail")
                        }
                    }
                }
            }
    }

    private fun setExerciseAllList(result: List<Exercise>) {
        _exerciseAllList.update {
            if (result.isEmpty()) {
                listOf(ExerciseListUiModel.ExerciseEmptyList)
            } else {
                result.map { exercise ->
                    ExerciseListUiModel.ExerciseListNameItem(exercise)
                }.toMutableList().apply {
                    return@apply
                }
            }
        }
        Log.d("setExerciseList", "_exerciseLIst.value[0]: ${_exerciseList.value[0]}")
    }

    private fun setExerciseList(result: List<Exercise>) {
        _exerciseList.update {
            if (result.isEmpty()) {
                listOf(ExerciseListUiModel.ExerciseEmptyList)
            } else {
                result.map { exercise ->
                    ExerciseListUiModel.ExerciseListNameItem(exercise)
                }.toMutableList().apply {
                    return@apply
                }
            }
        }
        Log.d("setExerciseList", "_exerciseLIst.value[0]: ${_exerciseList.value[0]}")
    }
}