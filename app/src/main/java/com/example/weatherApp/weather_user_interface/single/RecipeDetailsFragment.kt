@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface.single

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherApp.weather_data.weather_models.Item
import com.example.weatherApp.R
import com.example.weatherApp.weather_user_interface.add.add_button_animation
import com.example.weatherApp.weather_user_interface.itemViewModel
import com.example.weatherApp.databinding.RecipeDetailsBinding
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherApp.databinding.DeleteDialogBinding
import com.example.weatherApp.utils.showFullscreenImage

@Suppress("DEPRECATION")
class RecipeDetailsFragment : Fragment() {

    private var _binding: RecipeDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: itemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item: Item? = arguments?.getParcelable("item")

        item?.let {
            binding.foodName.text = it.foodName

            val context = binding.root.context
            val author = item.authorName.ifEmpty { R.string.Unknown_message }
            binding.authorName.text = context.getString(R.string.author_unknown_card, author)

            binding.ingredients.setText(it.ingredients)
            binding.foodDescription.setText(it.description)

            Glide.with(this)
                .load(it.imageUri)
                .into(binding.foodImage)


            binding.foodImage.setOnClickListener { _ ->
                it.imageUri?.let { uri ->
                    showFullscreenImage(requireContext(), uri)
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            item?.let {
                showDeleteDialog(it)

            }
        }

        binding.btnEditDetails.setOnClickListener {
            val bundle = Bundle().apply { putParcelable("item", item) } // העברת הפריט לעריכה.
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_addItemFragment, bundle)
        }

        binding.btnBackToCard.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment)
        }



        binding.btnShare.setOnClickListener {view ->
            add_button_animation.scaleView(view)
            item?.let {
                val shareText = """
            🥘 ${it.foodName}
            
            👨‍🍳 ${getString(R.string.author_unknown_card, it.authorName)}
            
            📋 ${getString(R.string.ingredients_view)}: ${it.ingredients}
            
            📝 ${getString(R.string.description_view)}: ${it.description}
        """.trimIndent()

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
            }
        }
    }

    //-------------------------------------------
    private fun showDeleteDialog(item: Item) {
        val binding2 = DeleteDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding2.root)
            .setCancelable(false)
            .create()

        binding2.dialogNoButton.setOnClickListener {
            dialog.dismiss()
        }

        binding2.dialogYesButton.setOnClickListener {
            viewModel.deleteItem(item)
            Toast.makeText(requireContext(),getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_recipeDetailsFragment2_to_allItemsFragment) // חזרה לרשימת הפריטים.
            dialog.dismiss()
        }

        dialog.show()
    }
    //---------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}