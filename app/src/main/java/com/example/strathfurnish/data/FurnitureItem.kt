package com.example.strathfurnish.data

import androidx.annotation.DrawableRes

data class FurnitureItem(
    val id: String,
    val name: String,
    val price: Double,
    val condition: String,
    val location: String,
    @DrawableRes val imageResId: Int,
    var isFavorited: Boolean = false
)