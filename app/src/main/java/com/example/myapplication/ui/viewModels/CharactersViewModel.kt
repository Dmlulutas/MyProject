package com.example.myapplication.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.data.models.Character
import com.example.myapplication.domain.paging.CharactersPagingSource
import com.example.myapplication.domain.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charRepository: CharacterRepository) :
    ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val charList = Pager(PagingConfig(1)) {
        CharactersPagingSource(charRepository)
    }.flow.cachedIn(viewModelScope)

   /* //Api
    val detailsMovie = MutableLiveData<MovieDetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }*/
}