package com.example.myapplication.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.CharResponse
import com.example.myapplication.data.models.Character
import com.example.myapplication.domain.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()

    val characters: LiveData<List<Character>>
        get() = _characters

    var list = emptyList<Character>()
    private val _characterFlow by lazy { MutableStateFlow(list) }

    fun getCharacters(page: Int) {
        viewModelScope.launch {
            characterRepository.getCharacters(page).let {
                val charResponse: CharResponse = it.body()!!
                val characters: List<Character> = charResponse.results
                _characters.postValue(characters)
                _characterFlow.value = characters
            }
        }
    }
}