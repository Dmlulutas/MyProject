package com.example.myapplication.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterDBRepository @Inject constructor (private val characterDao: CharacterDao) {
    val getCharacterList: Flow<List<CharacterEntity>> = characterDao.getCharacters()

    @WorkerThread
    suspend fun insert(characterEntity: CharacterEntity) = withContext(Dispatchers.IO){
        characterDao.addCharacter(characterEntity)
    }

    @WorkerThread
    suspend fun delete(characterEntity: CharacterEntity) = withContext(Dispatchers.IO){
        characterDao.deleteCharacter(characterEntity)
    }
}