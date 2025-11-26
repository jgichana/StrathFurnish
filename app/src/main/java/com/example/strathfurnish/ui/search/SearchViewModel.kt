package com.example.strathfurnish.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.strathfurnish.R
import com.example.strathfurnish.data.FurnitureItem

class SearchViewModel : ViewModel() {

    // This holds the original, complete list of all items.
    private var fullFurnitureList: List<FurnitureItem> = listOf()

    // This LiveData will hold the list of items to be displayed (the filtered list).
    private val _filteredFurnitureItems = MutableLiveData<List<FurnitureItem>>()
    val filteredFurnitureItems: LiveData<List<FurnitureItem>> = _filteredFurnitureItems

    init {
        // Load the full list of items when the ViewModel is created.
        loadAllFurniture()
    }

    private fun loadAllFurniture() {
        // In a real app, you would fetch this from a database or a network API.
        // For now, we use the same dummy data as the home page.
        fullFurnitureList = listOf(
            FurnitureItem("1", "Modern Armchair", 15000.00, "New", "Nyayo", R.drawable.sofa),
            FurnitureItem("2", "Wooden Coffee Table", 10000.00, "Fair", "Highrise", R.drawable.table),
            FurnitureItem("3", "King Size Bed Frame", 20000.00, "Good", "Langata", R.drawable.bed2),
            FurnitureItem("4", "Office Desk", 12000.00, "New", "Siwaka", R.drawable.table),
            FurnitureItem("5", "Plush Sofa", 14000.00, "New", "Madaraka Est.", R.drawable.sofa),
            FurnitureItem("6", "Dining Table Set", 10000.00, "Fair", "Nyayo", R.drawable.table)
        )
        // Initially, the filtered list is the same as the full list.
        _filteredFurnitureItems.value = fullFurnitureList
    }


    fun getAllFurniture(): List<FurnitureItem> {
        return fullFurnitureList
    }

    fun searchFurniture(query: String) {
        if (query.isBlank()) {
            // If the search query is empty, show the full list.
            _filteredFurnitureItems.value = fullFurnitureList
        } else {
            // Otherwise, filter the list.
            val results = fullFurnitureList.filter { item ->
                item.name.contains(query, ignoreCase = true) ||
                        item.location.contains(query, ignoreCase = true)
            }
            _filteredFurnitureItems.value = results
        }
    }
}