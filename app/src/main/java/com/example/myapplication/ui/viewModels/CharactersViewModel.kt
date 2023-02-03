package com.example.myapplication.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.domain.sources.CharactersPagingSource
import com.example.myapplication.domain.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charRepository: CharacterRepository) :
    ViewModel() {

    val loading = MutableLiveData<Boolean>()

    val charList = Pager(PagingConfig(1)) {
        CharactersPagingSource(charRepository)
    }.flow.cachedIn(viewModelScope)

   /* //Api
    val detailsChar = MutableLiveData<CharDetailsResponse>()
    fun loadDetailsMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getCharDetails(id)
        if (response.isSuccessful) {
            detailsChar.postValue(response.body())
        }
        loading.postValue(false)
    }*/
}