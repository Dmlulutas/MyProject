package com.example.myapplication.network

import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.data.models.Character
import com.example.myapplication.domain.services.IApiInterface
import javax.inject.Inject


class ApiServiceImpl @Inject constructor(private val apiInterface: IApiInterface) {

    suspend fun getCharacters(page: Int): CharResponse = apiInterface.getCharacters(page)
}