package com.example.data.datasource.remote.service

import com.example.data.model.request.ExercisesSearchRequest
import com.example.data.model.response.ExerciseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ExerciseService {

    @GET("exercise/all")
    suspend fun findAll(): Response<List<ExerciseResponse>>?

    @POST("exercise/search")
    suspend fun searchExercises(
        @Body exercisesSearchRequest: ExercisesSearchRequest
    ): Response<List<ExerciseResponse>>?
}