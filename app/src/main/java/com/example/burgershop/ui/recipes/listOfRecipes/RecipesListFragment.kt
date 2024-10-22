package com.example.burgershop.ui.recipes.listOfRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.example.burgershop.R
import com.example.burgershop.databinding.FragmentListRecipesBinding
import com.example.burgershop.model.Constants

class RecipesListFragment : Fragment() {

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListRecipesBinding must not be null")

    private val args: RecipesListFragmentArgs by navArgs()
    private val recipesListViewModel: RecipesListViewModel by viewModels()
    private val recipesListAdapter = RecipesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRecipesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.categoryFromList
        recipesListViewModel.openRecipesByCategoryId(category)
        initUI()
    }

    private fun initUI() {
        recipesListViewModel.listOfRecipesUiState.observe(viewLifecycleOwner) { newRecipeListState ->
            if (newRecipeListState.listOfRecipes != null) {
                recipesListAdapter.updateDataset(newRecipeListState.listOfRecipes)
                binding.apply {
                    Glide.with(requireContext())
                        .load("${Constants.URL_FOR_IMAGE}${newRecipeListState.imageUrl}")
                        .error(R.drawable.img_error)
                        .placeholder(R.drawable.img_placeholder)
                        .into(imageViewRecipes)
                    titleOfRecipes.text = newRecipeListState.titleOfCategories
                }
            } else {
                Toast.makeText(context, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
            }
        }
        recipesListAdapter.setOnRecipeClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        binding.rvRecipes.adapter = recipesListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val action =
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId)
        findNavController().navigate(action)
    }
}
