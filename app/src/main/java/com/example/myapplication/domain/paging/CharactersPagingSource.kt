package com.example.myapplication.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.domain.repositories.CharacterRepository
import retrofit2.HttpException
import retrofit2.Response

class CharactersPagingSource(private val charRepository: CharacterRepository) :
    PagingSource<Int, Response<CharResponse>>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Response<CharResponse>> {
        return try {
            val currentPage = params.key ?: 1
            val response = charRepository.getCharacters(currentPage)
            val responseData = mutableListOf<Response<CharResponse>>()
            responseData.addAll(listOf(response))
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpError: HttpException) {
            LoadResult.Error(httpError)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Response<CharResponse>>): Int? {
        TODO("Not yet implemented")
    }
}