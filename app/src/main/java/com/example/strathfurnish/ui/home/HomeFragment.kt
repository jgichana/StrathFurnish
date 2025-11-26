// In ui/home/HomeFragment.kt

package com.example.strathfurnish.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.strathfurnish.R
import com.example.strathfurnish.databinding.FragmentHomeBinding
import com.example.strathfurnish.ui.auth.AuthViewModel
import com.example.strathfurnish.ui.wishlist.WishlistViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private val wishlistViewModel: WishlistViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupCategories()
        setupFurnitureList() // <-- We'll move the logic into a new function

        return root
    }

    private fun setupFurnitureList() {
        // Create the adapter once.
        val furnitureAdapter = FurnitureAdapter(emptyList()) { clickedItem ->
            // Now, we check the login status *at the moment of the click*.
            // We use the LiveData's current value.
            val user = authViewModel.user.value
            if (user != null) {
                // User is logged in, toggle wishlist.
                wishlistViewModel.toggleWishlist(clickedItem)
            } else {
                // User is not logged in, navigate to login.
                Toast.makeText(requireContext(), "Please sign in to add to wishlist", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.navigation_login)
            }
        }
        binding.recyclerViewHome.adapter = furnitureAdapter

        // Observe furniture items from HomeViewModel
        homeViewModel.furnitureItems.observe(viewLifecycleOwner) { furnitureList ->
            // When the furniture list updates, update the adapter's data.
            // We need to combine it with the latest wishlist data.
            val wishlistIds = wishlistViewModel.wishlistItems.value ?: emptySet()
            furnitureList.forEach { item ->
                item.isFavorited = wishlistIds.contains(item.id)
            }
            furnitureAdapter.updateData(furnitureList)
        }

        // Observe wishlist items from WishlistViewModel
        wishlistViewModel.wishlistItems.observe(viewLifecycleOwner) { wishlistIds ->
            // When the wishlist updates, update the adapter's data.
            val currentList = homeViewModel.furnitureItems.value ?: emptyList()
            currentList.forEach { item ->
                item.isFavorited = wishlistIds.contains(item.id)
            }
            furnitureAdapter.updateData(currentList)
        }
    }


    private fun setupCategories() {
        // ... your existing setupCategories code
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}