@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface.add

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherApp.databinding.AddRecipeBinding
import androidx.fragment.app.activityViewModels
import com.example.weatherApp.weather_data.weather_models.Item
import com.example.weatherApp.R
import com.example.weatherApp.weather_user_interface.itemViewModel
import com.example.weatherApp.utils.ValidationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.weatherApp.databinding.UpdateDialogBinding

class AddItemFragment : Fragment() {

    private var _binding: AddRecipeBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private var itemToEdit: Item? = null

    private val viewModel: itemViewModel by activityViewModels()

    private val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                Glide.with(this) // שימוש ב-Glide להצגת תמונה
                    .load(it)
                    .into(binding.foodImagePreview)

                requireActivity().contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = it
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemToEdit = arguments?.getParcelable("item", Item::class.java)
        binding.btnAddFood.text = if (itemToEdit == null) getString(R.string.add_recipe) else getString(R.string.update_recipe)
        binding.headerTitle.text = if (itemToEdit == null) getString(R.string.add_recipe) else getString(R.string.update_recipe)
        itemToEdit?.let { item ->
            binding.foodNameInput.setText(item.foodName)
            binding.authorNameInput.setText(item.authorName)
            binding.foodDescriptionInput.setText(item.description)
            binding.ingredientsDescriptionInput.setText(item.ingredients)
            imageUri = item.imageUri?.let { Uri.parse(it) }
            Glide.with(this) // שימוש ב-Glide להצגת תמונה במצב עריכה
                .load(imageUri)
                .into(binding.foodImagePreview)
        }

        binding.btnAddFood.setOnClickListener {
            if (itemToEdit == null) {
                saveItem()
            } else {
                showCustomDialog()
            }
        }

        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }
    }

    private fun showCustomDialog() {
        val binding2 = UpdateDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding2.root)
            .setCancelable(false)
            .create()

        binding2.dialogNoButton.setOnClickListener {
            dialog.dismiss()
        }

        binding2.dialogYesButton.setOnClickListener {
            if (ValidationUtils.validateFields(requireContext(), binding, imageUri)) {
                val updatedItem = Item(
                    binding.foodNameInput.text.toString(),
                    binding.authorNameInput.text.toString(),
                    binding.foodDescriptionInput.text.toString(),
                    binding.ingredientsDescriptionInput.text.toString(),
                    imageUri?.toString()
                )

                itemToEdit?.let { oldItem ->
                    viewModel.deleteItem(oldItem)
                }
                viewModel.addItem(updatedItem)

                Toast.makeText(requireContext(), getString(R.string.item_updated), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
            }
        }
        dialog.show()
    }

    private fun saveItem() {
        if (ValidationUtils.validateFields(requireContext(), binding, imageUri)) {
            val newItem = Item(
                binding.foodNameInput.text.toString(),
                binding.authorNameInput.text.toString(),
                binding.foodDescriptionInput.text.toString(),
                binding.ingredientsDescriptionInput.text.toString(),
                imageUri?.toString()
            )

            viewModel.addItem(newItem)

            Toast.makeText(requireContext(), getString(R.string.item_added), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

