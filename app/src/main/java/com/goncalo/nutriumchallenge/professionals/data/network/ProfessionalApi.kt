package com.goncalo.nutriumchallenge.professionals.data.network

import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.domain.model.ProfessionalListOutput
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfessionalApi {
    @GET("/professionals/search")
    suspend fun getProfessionalsList(
        @Query("sort_by") sortType: String,
        @Query("offset") offset: String
    ): Response<ProfessionalListOutput>

    @GET("/professionals/{id}")
    suspend fun getProfessionalById(@Path("id") id: String) : Response<Professional>
}