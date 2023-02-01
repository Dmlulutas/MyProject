package com.example.myapplication.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.models.Character
import com.example.myapplication.domain.repositories.CharacterRepository
import retrofit2.HttpException
import retrofit2.Response

class CharactersPagingSource(
    private val repository: CharacterRepository ,
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getCharacters(currentPage)
            val data = response.results
            val responseData = mutableListOf<Character>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return null
    }


}