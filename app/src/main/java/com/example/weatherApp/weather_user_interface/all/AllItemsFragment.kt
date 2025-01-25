@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
package com.example.weatherApp.weather_user_interface.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weatherApp.R
import com.example.weatherApp.weather_user_interface.add.add_button_animation
import com.example.weatherApp.weather_user_interface.itemViewModel
import com.example.weatherApp.databinding.AllRecipeListBinding
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.weatherApp.weather_data.weather_models.Item
import com.example.weatherApp.databinding.DeleteDialogBinding

class AllItemsFragment : Fragment() {

    private var _binding: AllRecipeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: itemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AllRecipeListBinding.inflate(inflater, container, false)

        binding.menuIcon.setOnClickListener { view ->
            add_button_animation.scaleView(view) {
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemAdapter(
            onEdit = { item ->
                val bundle = Bundle().apply { putParcelable("item", item) }
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment, bundle)
            },
            onDelete = { item ->
                showDeleteDialog(item)
            },
            onDetails = { item ->
                val bundle = Bundle().apply { putParcelable("item", item) }
                findNavController().navigate(R.id.action_allItemsFragment_to_recipeDetailsFragment2, bundle)
            }
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1) // עיצוב טור אחד.

        viewModel.items?.observe(viewLifecycleOwner) { itemList ->
            adapter.submitList(itemList)
        }
    }




    //----------------------------------------------------
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
            Toast.makeText(requireContext(), getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
//------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
