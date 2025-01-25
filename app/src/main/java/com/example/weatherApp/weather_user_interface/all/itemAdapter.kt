@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherApp.databinding.RecipeCardBinding
import com.bumptech.glide.Glide
import com.example.weatherApp.R
import com.example.weatherApp.weather_data.weather_models.Item
import com.example.weatherApp.utils.showFullscreenImage

class ItemAdapter(
    private val onEdit: (Item) -> Unit,
    private val onDelete: (Item) -> Unit,
    private val onDetails: (Item) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    class ItemViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, onEdit: (Item) -> Unit, onDelete: (Item) -> Unit, onDetails: (Item) -> Unit) {
            val context = binding.root.context
            binding.foodName.text = item.foodName
            val author = item.authorName.ifEmpty { R.string.Unknown_message }
            binding.authorName.text = context.getString(R.string.author_unknown_card, author)

            Glide.with(binding.root.context)
                .load(item.imageUri)
                .into(binding.foodImage)


            binding.foodImage.setOnClickListener {
                item.imageUri?.let { uri ->
                    showFullscreenImage(context, uri)
                }
            }


            binding.btnEdit.setOnClickListener {
                onEdit(item)
            }

            binding.btnDelete.setOnClickListener {
                onDelete(item)
            }

            binding.btnViewDetails.setOnClickListener {
                onDetails(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            RecipeCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), onEdit, onDelete, onDetails)
    }
}
