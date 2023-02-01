package com.example.myapplication.domain.repositories

import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.domain.services.IApiInterface
import javax.inject.Inject
import retrofit2.Response

class CharacterRepository
@Inject constructor(private val apiInterface: IApiInterface) {
    suspend fun getCharacters(page: Int): Response<CharResponse> = apiInterface.getCharacters(page)
}