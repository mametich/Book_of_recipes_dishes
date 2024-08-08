package com.example.burgershop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.burgershop.databinding.FragmentListFavoritesBinding

class FavoritesListFragment : Fragment() {

    private var _binding: FragmentListFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

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
        initRecycler()
        initUI()
    }

    private fun initRecycler() {
        val setOfIds = getFavorites()
        val favoriteListAdapter = RecipesListAdapter(STUB.getRecipesByIds(setOfIds))

        favoriteListAdapter.setOnRecipeClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
        if (setOfIds.isNotEmpty()) {
            binding.rvFavorites.adapter = favoriteListAdapter
        } else {
            binding.rvFavorites.visibility = View.GONE
            binding.tvYorNotAddRecipe.visibility = View.VISIBLE
        }
    }

    private fun initUI() {
        binding.ivFavorites.setImageResource(R.drawable.bcg_favorites)
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref = requireContext().getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPref?.getStringSet(SET_ID, HashSet<String>()) ?: mutableSetOf())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}