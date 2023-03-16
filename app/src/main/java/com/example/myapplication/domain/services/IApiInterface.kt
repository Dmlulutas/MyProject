package com.example.myapplication.domain.services

import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.data.models.Episode
import com.example.myapplication.data.models.enums.Url.CHARACTER_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiInterface {

    @GET(CHARACTER_URL)
    suspend fun getCharacters(@Query("page") query: Int): CharResponse

}