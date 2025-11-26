package com.example.strathfurnish.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.strathfurnish.data.FurnitureItem
import com.example.strathfurnish.R

class HomeViewModel : ViewModel() {

    // This LiveData will hold the list of furniture items
    private val _furnitureItems = MutableLiveData<List<FurnitureItem>>()
    val furnitureItems: LiveData<List<FurnitureItem>> = _furnitureItems



    init {
        // Load some dummy data when the ViewModel is created
        loadFurniture()
    }

    private fun loadFurniture() {
        // In a real app, you would fetch this from a database or a network API
        val itemlist = listOf(
            FurnitureItem("1", "Leather Sofa", 15000.00, "New", "Highrise", R.drawable.sofa),
            FurnitureItem("2", "Wooden Coffee Table", 7500.00, "Used", "Nyayo", R.drawable.table),
            FurnitureItem("3", "King Size Bed Frame", 12000.00, "Used - Like New", "Highrise", R.drawable.bed2),
            FurnitureItem("4", "Mahogany Bed", 22000.00, "New", "Siwaka", R.drawable.bed3)
        )
        _furnitureItems.value = itemlist
    }
}