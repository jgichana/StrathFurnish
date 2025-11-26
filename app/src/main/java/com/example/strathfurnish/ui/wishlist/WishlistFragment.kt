// In ui/wishlist/WishlistFragment.kt
package com.example.strathfurnish.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.strathfurnish.data.FurnitureItem
import com.example.strathfurnish.databinding.FragmentWishlistBinding
import com.example.strathfurnish.ui.home.FurnitureAdapter
import com.example.strathfurnish.ui.search.SearchViewModel

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel for the wishlist, scoped to the Activity
    private val wishlistViewModel: WishlistViewModel by activityViewModels()

    // ViewModel to get the master list of all items
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // We need the SearchViewModel to get the full list of items to filter from.
        // In a real app, this might come from a central repository.
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        // Observe the wishlist for any changes
        wishlistViewModel.wishlistItems.observe(viewLifecycleOwner) { wishlistIds ->
            // Get the full list of items from the SearchViewModel
            val allItems = searchViewModel.getAllFurniture() // We'll add this helper method

            // Filter the full list to get only the items whose IDs are in the wishlist
            val likedItems = allItems.filter { item -> wishlistIds.contains(item.id) }

            // Update the empty state text visibility
            binding.textEmptyWishlist.isVisible = likedItems.isEmpty()

            // Update the isFavorited flag for each item (they are all favorited here)
            likedItems.forEach { it.isFavorited = true }

            // Set up the adapter with the liked items
            val adapter = FurnitureAdapter(likedItems) { clickedItem ->
                // When the heart is clicked on the wishlist page, toggle it
                wishlistViewModel.toggleWishlist(clickedItem)
            }
            binding.recyclerViewWishlist.adapter = adapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}