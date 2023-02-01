package com.example.myapplication.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharResponse(
    val info: ResponseInfo,
    val results: List<Character>,
) : Parcelable
