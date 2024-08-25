package com.example.burgershop.ui.recipes.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.ARG_RECIPE
import com.example.burgershop.R

import com.example.burgershop.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private var emptyIngredientAdapter = IngredientsAdapter()
    private var emptyMethodAdapter = MethodAdapter()

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
        binding.ivHeartFavourites.setOnClickListener {
           addToFavorite()
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
            if (newRecipeUiState.recipe != null) {
                emptyIngredientAdapter.apply {
                    dataSet = newRecipeUiState.recipe.ingredients
                    quantity = newRecipeUiState.portionsCount
                }

                emptyMethodAdapter.apply {
                    dataSet = newRecipeUiState.recipe.method
                    recipe = newRecipeUiState.recipe
                }

                binding.apply {
                    imageViewRecipes.setImageDrawable(newRecipeUiState.recipeImage)
                    titleOfRecipe.text = newRecipeUiState.recipe.title
                    if (newRecipeUiState.isFavorite) {
                        ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites)
                    } else {
                        ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites_default)
                    }
                    rvIngredients.adapter = emptyIngredientAdapter
                    rvMethod.adapter = emptyMethodAdapter
                    countOfPortion.text = newRecipeUiState.portionsCount.toString()

                    seekBar.setOnSeekBarChangeListener(
                        PortionSeekBarListener { progress ->
                            recipeViewModel.updatedCountOfPortion(progress) }
                    )
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

    class PortionSeekBarListener(
        val onChangeIngredients: (Int) -> Unit
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onChangeIngredients(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) { }

        override fun onStopTrackingTouch(seekBar: SeekBar?) { }
    }

    private fun addToFavorite() {
        recipeViewModel.onFavoritesClicked()
    }
}



