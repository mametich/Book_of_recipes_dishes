package com.example.burgershop.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.burgershop.R
import com.example.burgershop.RecipesApplication
import com.example.burgershop.databinding.FragmentListFavoritesBinding
import com.example.burgershop.di.FavoriteListViewModelFactory
import com.example.burgershop.ui.recipes.listOfRecipes.RecipesListAdapter

class FavoritesListFragment : Fragment() {

    private var _binding: FragmentListFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    private val favoritesListAdapter = RecipesListAdapter()
    private lateinit var favoritesListViewModel: FavoritesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (requireActivity().application as RecipesApplication).appContainer
        favoritesListViewModel = appContainer.favoriteListViewModelFactory.create()
    }

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
            if (newFavoritesListState.listOfFavoriteRecipes != null) {
                    favoritesListAdapter.updateDataset(newFavoritesListState.listOfFavoriteRecipes)
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
            val action =
                FavoritesListFragmentDirections.actionFavoritesListFragmentToRecipeFragment(recipeId)
            findNavController().navigate(action)
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }