package com.example.myapplication.domain.services

import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.data.models.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiInterface {

    @GET("character")
    suspend fun getCharacters(@Query("page") query: Int): CharResponse

    @GET("episode")
    suspend fun getEpisodes(): Response<List<Episode>>

}