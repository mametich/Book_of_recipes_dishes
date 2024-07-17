package com.example.burgershop

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.recyclerview.widget.RecyclerView

import com.example.burgershop.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {

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
        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_RECIPE, Recipe::class.java) as Recipe
        } else {
            requireArguments().getParcelable(ARG_RECIPE)
        }
        if (recipe != null) {
            initRecycler(recipe)
            initUi(recipe)
        }
    }

    private fun initRecycler(recipe: Recipe) {
        val ingredientsAdapter = IngredientsAdapter(recipe.ingredients)
        val methodAdapter = MethodAdapter(recipe.method, recipe)

        val dividerItemDecoration = MaterialDividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
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
        }
    }

    private fun initUi(recipe: Recipe) {
        val drawable =
            Drawable.createFromStream(requireContext().assets.open(recipe.imageUrl), null)
        binding.apply {
            imageViewRecipes.setImageDrawable(drawable)
            titleOfRecipe.text = recipe.title
        }
    }
}