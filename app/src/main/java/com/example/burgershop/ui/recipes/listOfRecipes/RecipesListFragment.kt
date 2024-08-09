package com.example.burgershop.ui.recipes.listOfRecipes

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.burgershop.ARG_CATEGORY_ID
import com.example.burgershop.ARG_CATEGORY_IMAGE_URL
import com.example.burgershop.ARG_CATEGORY_NAME
import com.example.burgershop.ARG_RECIPE
import com.example.burgershop.R
import com.example.burgershop.data.STUB
import com.example.burgershop.databinding.FragmentListRecipesBinding
import com.example.burgershop.ui.recipes.recipe.RecipesListAdapter
import com.example.burgershop.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment() {

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListRecipesBinding must not be null")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryUrlImage: String? = null

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
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryUrlImage = it.getString(ARG_CATEGORY_IMAGE_URL)
        }
        initRecyclerViewRecipes()
        initUI()
    }

    private fun initRecyclerViewRecipes() {
        val recipeListAdapter = categoryId?.let { STUB.getRecipesByCategoryId(it) }
            ?.let { RecipesListAdapter(it) }

        recipeListAdapter?.setOnRecipeClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onItemClick(recipeId: Int) {
               openRecipeByRecipeId(recipeId)
            }
        })
        binding.rvRecipes.adapter = recipeListAdapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    private fun initUI() {
        val drawable = Drawable.createFromStream(categoryUrlImage?.let {
            requireContext().assets?.open(it)
        },null)
        binding.imageViewRecipes.setImageDrawable(drawable)
    }
}