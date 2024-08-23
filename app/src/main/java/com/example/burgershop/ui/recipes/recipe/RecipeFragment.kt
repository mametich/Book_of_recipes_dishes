package com.example.burgershop.ui.recipes.recipe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.ARG_RECIPE
import com.example.burgershop.R
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
import com.example.burgershop.data.STUB

import com.example.burgershop.databinding.FragmentRecipeBinding
import com.example.burgershop.model.Recipe
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private var emptyIngredientAdapter: IngredientsAdapter? = null
    private var emptyMethodAdapter: MethodAdapter? = null

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentRecipeBinding must not be null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = arguments?.getInt(ARG_RECIPE)
        if (recipeId != null) {
            recipeViewModel.loadRecipe(recipeId)
            initUI()
        }
    }

    private fun initUI() {

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.apply {
            isLastItemDecorated = false
            setDividerInsetStartResource(requireContext(), R.dimen.margin_12)
            setDividerInsetEndResource(requireContext(), R.dimen.margin_12)
            dividerColor
        }
        binding.apply {
            rvIngredients.addItemDecoration(dividerItemDecoration)
            rvMethod.addItemDecoration(dividerItemDecoration)
        }

        recipeViewModel.recipeUiSt.observe(viewLifecycleOwner) { newRecipeUiState ->
            emptyIngredientAdapter =
                newRecipeUiState.recipe?.let { IngredientsAdapter(it.ingredients, newRecipeUiState.portionsCount) }
            emptyMethodAdapter =
                newRecipeUiState.recipe?.let { MethodAdapter(newRecipeUiState.recipe.method, it) }

            binding.apply {
                imageViewRecipes.setImageDrawable(newRecipeUiState.recipeImage)
                titleOfRecipe.text = newRecipeUiState.recipe?.title ?: ""
                if (newRecipeUiState.isFavorite) {
                    ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites)
                } else {
                    ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites_default)
                }
                ivHeartFavourites.setOnClickListener {
                    recipeViewModel.onFavoritesClicked()
                }
                rvIngredients.adapter = emptyIngredientAdapter
                rvMethod.adapter = emptyMethodAdapter
                countOfPortion.text = newRecipeUiState.portionsCount.toString()

                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        recipeViewModel.updatedCountOfPortion(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
                val sizeInDpTop = resources.getDimensionPixelSize(R.dimen.margin_6)
                val sizeInDpStartEndBottom = resources.getDimensionPixelSize(R.dimen.margin_0)
                seekBar.setPadding(
                    sizeInDpStartEndBottom,
                    sizeInDpTop,
                    sizeInDpStartEndBottom,
                    sizeInDpStartEndBottom
                )
            }
        }
    }
}
