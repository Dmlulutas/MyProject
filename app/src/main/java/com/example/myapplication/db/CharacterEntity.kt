package com.example.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.data.models.enums.Gender
import com.example.myapplication.data.models.enums.Species

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val date: String,
    @ColumnInfo(name = "gender")
    val title: Gender,
    @ColumnInfo(name = "species")
    val content: Species
)