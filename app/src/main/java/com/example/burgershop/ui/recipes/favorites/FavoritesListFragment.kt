package com.example.burgershop.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.example.burgershop.ARG_RECIPE
import com.example.burgershop.R
import com.example.burgershop.data.STUB
import com.example.burgershop.databinding.FragmentListFavoritesBinding
import com.example.burgershop.model.Recipe
import com.example.burgershop.ui.recipes.recipe.RecipeFragment
import com.example.burgershop.ui.recipes.listOfRecipes.RecipesListAdapter

class FavoritesListFragment : Fragment() {

    private var _binding: FragmentListFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    private val favoritesListAdapter = RecipesListAdapter()
    private val favoritesListViewModel: FavoritesListViewModel by viewModels()
    private var listOfRecipe = emptyList<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesListViewModel.loadListOfRecipes()
        initUI()
    }

    private fun initUI() {

        favoritesListViewModel.favoritesUiState.observe(viewLifecycleOwner) { newFavoritesListState ->
            newFavoritesListState.let {
                favoritesListAdapter.dataset = it.listOfFavoriteRecipes
                listOfRecipe = it.listOfFavoriteRecipes
            }

            if (newFavoritesListState.listOfFavoriteRecipes.isNotEmpty()) {
                binding.rvFavorites.adapter = favoritesListAdapter
            } else {
                binding.rvFavorites.visibility = View.GONE
                binding.tvYorNotAddRecipe.visibility = View.VISIBLE
            }
        }

        favoritesListAdapter.setOnRecipeClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        binding.ivFavorites.setImageResource(R.drawable.bcg_favorites)
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val idOfRecipe = listOfRecipe[recipeId].id
        val bundle = bundleOf(ARG_RECIPE to idOfRecipe)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}