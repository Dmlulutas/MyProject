package com.example.myapplication.domain.repositories

import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getCharListApiData(page:Int): Flow<CharResponse> = flow {
        emit(apiServiceImpl.getCharacters(page))
    }.flowOn(Dispatchers.IO)

}