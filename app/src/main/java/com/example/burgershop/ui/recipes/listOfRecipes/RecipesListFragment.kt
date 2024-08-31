package com.example.burgershop.ui.recipes.listOfRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.burgershop.ARG_CATEGORY_ID
import com.example.burgershop.ARG_CATEGORY_IMAGE_URL
import com.example.burgershop.ARG_CATEGORY_NAME
import com.example.burgershop.ARG_RECIPE
import com.example.burgershop.R
import com.example.burgershop.data.STUB
import com.example.burgershop.databinding.FragmentListRecipesBinding
import com.example.burgershop.model.Recipe
import com.example.burgershop.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment() {

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListRecipesBinding must not be null")

    private var categoryId: Int = 0
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
        requireArguments().let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
        }
        recipesListViewModel.loadListOfRecipes(categoryId)
        initUI()
    }

    private fun initUI() {
        recipesListViewModel.listOfRecipesUiState.observe(viewLifecycleOwner) { newRecipeListState ->
            recipesListAdapter.dataset = newRecipeListState.listOfRecipes
            binding.apply {
                imageViewRecipes.setImageDrawable(newRecipeListState.categoryImage)
                titleOfRecipes.text = newRecipeListState.titleOfCategories
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
        val bundle = bundleOf(ARG_RECIPE to recipeId)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }
}