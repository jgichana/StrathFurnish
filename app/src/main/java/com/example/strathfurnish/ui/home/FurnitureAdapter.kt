package com.example.strathfurnish.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.strathfurnish.R
//import androidx.compose.ui.semantics.text
import androidx.recyclerview.widget.RecyclerView
import com.example.strathfurnish.data.FurnitureItem
import com.example.strathfurnish.databinding.ItemFurnitureBinding
import java.text.NumberFormat
import java.util.Locale

class FurnitureAdapter(
    private var items: List<FurnitureItem>,
    private val onFavoriteClick: (FurnitureItem) -> Unit
) :
    RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {

    // Describes an item view and its metadata within the RecyclerView.
    inner class FurnitureViewHolder(private val binding: ItemFurnitureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FurnitureItem) {
            binding.itemName.text = item.name

            if (item.isFavorited) {
                binding.iconFavorite.setImageResource(R.drawable.favoritefilled)
            } else {
                binding.iconFavorite.setImageResource(R.drawable.wishlist)
            }
            binding.iconFavorite.setOnClickListener{
                onFavoriteClick(item)
            }
            val format = NumberFormat.getCurrencyInstance(Locale("en", "KE")) // For KES
            binding.itemPrice.text = format.format(item.price)
            val context = binding.root.context
            binding.itemCondition.text = context.getString(R.string.Condition_placeholder, item.condition)
            binding.itemLocation.text = item.location
            binding.itemImage.setImageResource(item.imageResId)
        }

    }
    fun updateData(newItems: List<FurnitureItem>) {
        this.items = newItems // Update the internal list
        notifyDataSetChanged() // Tell the RecyclerView to redraw itself
    }

    // Creates new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val binding = ItemFurnitureBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FurnitureViewHolder(binding)
    }

    // Replaces the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Returns the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}