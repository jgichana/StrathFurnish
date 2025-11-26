// In ui/search/SearchFragment.kt
package com.example.strathfurnish.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.strathfurnish.databinding.FragmentSearchBinding
import com.example.strathfurnish.ui.home.FurnitureAdapter // We reuse the adapter from the home package!
import androidx.fragment.app.activityViewModels
import com.example.strathfurnish.ui.wishlist.WishlistViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel
    private val wishlistViewModel: WishlistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchViewModel.filteredFurnitureItems.observe(viewLifecycleOwner) { furnitureList ->

            wishlistViewModel.wishlistItems.observe(viewLifecycleOwner) { wishlistIds ->

                furnitureList.forEach { item ->
                    item.isFavorited = wishlistIds.contains(item.id)
                }
                // Pass the list and the click handler to the adapter
                binding.recyclerViewSearch.adapter =
                    FurnitureAdapter(furnitureList) { clickedItem ->
                        wishlistViewModel.toggleWishlist(clickedItem)
                    }
            }
        }
        // Add a listener to the search bar to react to text changes.
        binding.searchEditText.addTextChangedListener { text ->
            // Call the search function in the ViewModel every time the text changes.
            searchViewModel.searchFurniture(text.toString())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
