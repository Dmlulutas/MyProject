package com.example.myapplication.data.models
import android.os.Parcelable
import com.example.myapplication.data.models.enums.Gender
import com.example.myapplication.data.models.enums.Species
import kotlinx.parcelize.Parcelize
@Parcelize
data class Character(
    val id: Int,
    val name :String,
    val image : String,
    val gender : Gender,
    val species: Species?
) : Parcelable
