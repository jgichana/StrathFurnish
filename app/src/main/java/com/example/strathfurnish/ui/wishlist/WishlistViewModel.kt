// In ui/wishlist/WishlistViewModel.kt
package com.example.strathfurnish.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import androidx.preference.contains
import com.example.strathfurnish.data.FurnitureItem

class WishlistViewModel : ViewModel() {

    // This holds the set of IDs of items in the wishlist. A Set is efficient for checking existence.
    private val _wishlistItems = MutableLiveData<Set<String>>(emptySet())
    val wishlistItems: LiveData<Set<String>> = _wishlistItems

    fun isItemInWishlist(itemId: String): Boolean {
        return _wishlistItems.value?.contains(itemId) ?: false
    }

    fun toggleWishlist(item: FurnitureItem) {
        val currentWishlist = _wishlistItems.value?.toMutableSet() ?: mutableSetOf()
        if (currentWishlist.contains(item.id)) {
            currentWishlist.remove(item.id)
        } else {
            currentWishlist.add(item.id)
        }
        _wishlistItems.value = currentWishlist
    }
}