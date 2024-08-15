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
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.ARG_RECIPE
import com.example.burgershop.R
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP

import com.example.burgershop.databinding.FragmentRecipeBinding
import com.example.burgershop.model.Recipe
import com.google.android.material.divider.MaterialDividerItemDecoration

private const val INFO = "!!!"

class RecipeFragment : Fragment() {

    private val recipeViewModel: RecipeViewModel by viewModels()

    private var recipe: Recipe? = null
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

        if (getRecipe() != null) {
            initRecycler()
            initUI()
        }
    }

    private fun getRecipe(): Recipe? {
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_RECIPE, Recipe::class.java) as Recipe
        } else {
            requireArguments().getParcelable(ARG_RECIPE)
        }
        return recipe
    }

    private fun initRecycler() {
        val ingredientsAdapter = recipe?.let { IngredientsAdapter(it.ingredients) }
        val methodAdapter = recipe?.let { MethodAdapter(it.method, recipe!!) }

        val dividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        dividerItemDecoration.apply {
            isLastItemDecorated = false
            setDividerInsetStartResource(requireContext(), R.dimen.margin_12)
            setDividerInsetEndResource(requireContext(), R.dimen.margin_12)
            dividerColor
        }

        binding.apply {
            rvIngredients.adapter = ingredientsAdapter
            rvIngredients.addItemDecoration(dividerItemDecoration)
            rvMethod.adapter = methodAdapter
            rvMethod.addItemDecoration(dividerItemDecoration)
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    ingredientsAdapter?.updateIngredients(progress)
                    ingredientsAdapter?.notifyDataSetChanged()
                    countOfPortion.text = progress.toString()
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

    private fun initUI() {

        recipeViewModel.recipeUiSt.observe(viewLifecycleOwner) { newRecipeUiState ->
            val drawable =
                Drawable.createFromStream(
                    newRecipeUiState.recipe?.imageUrl?.let { requireContext().assets.open(it.imageUrl) },
                    null
                )
            binding.apply {
                imageViewRecipes.setImageDrawable(drawable)
                titleOfRecipe.text = newRecipeUiState.recipe?.title ?: ""
                ivHeartFavourites.setOnClickListener {
                    recipeViewModel.onFavoritesClicked()
                }
            }
        }

//        if (getFavorites().contains(recipe?.id.toString())) {
//            binding.ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites)
//        } else {
//            binding.ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites_default)
//        }
//    }

}
