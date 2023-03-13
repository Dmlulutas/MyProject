package com.example.myapplication.ui.viewModels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.domain.repositories.CharacterRepository
import com.example.myapplication.domain.repositories.MainRepository
import com.example.myapplication.domain.sources.CharactersPagingSource
import com.example.myapplication.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charRepository: CharacterRepository,
    private val mainRepository: MainRepository,
) :
    ViewModel() {


    val charList = Pager(PagingConfig(1)) {
        CharactersPagingSource(charRepository)
    }.flow.cachedIn(viewModelScope)



    val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    fun getCharList(page: Int) = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getCharListApiData(page)
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                postStateFlow.value = ApiState.Success(data.results)
            }
    }

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