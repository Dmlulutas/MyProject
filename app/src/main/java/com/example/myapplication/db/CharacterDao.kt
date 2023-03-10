package com.example.myapplication.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun addCharacter(character: CharacterEntity)

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Delete
    fun deleteCharacter(character: CharacterEntity)

}