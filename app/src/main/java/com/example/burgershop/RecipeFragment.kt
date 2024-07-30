package com.example.burgershop

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.blue
import androidx.recyclerview.widget.RecyclerView

import com.example.burgershop.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration



class RecipeFragment : Fragment() {

    private var isHeartVisible = false
    private var setOfId: Set<String> = emptySet()

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
        binding.ivHeartFavourites.setOnClickListener {
            chooseFavorites()
        }
    }

    private fun chooseFavorites() {
        if (isHeartVisible) {
            binding.ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites_default)
            isHeartVisible = false
        } else {
            binding.ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites)
            isHeartVisible = true
            saveFavorites(setOfId)
        }
    }

    private fun initRecycler(recipe: Recipe) {
        val ingredientsAdapter = IngredientsAdapter(recipe.ingredients)
        val methodAdapter = MethodAdapter(recipe.method, recipe)

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
                    ingredientsAdapter.updateIngredients(progress)
                    ingredientsAdapter.notifyDataSetChanged()
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

    private fun initUi(recipe: Recipe) {
        val drawable =
            Drawable.createFromStream(requireContext().assets.open(recipe.imageUrl), null)
        binding.apply {
            imageViewRecipes.setImageDrawable(drawable)
            titleOfRecipe.text = recipe.title

        }
        val setOfId = getFavorites()
        if(setOfId.contains(recipe.id.toString())) {
            binding.ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites)
        } else {
            binding.ivHeartFavourites.setImageResource(R.drawable.ic_heart_favourites_default)
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveFavorites(setId: Set<String>) {
        val sharedPref = requireContext().getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
       sharedPref.edit().putStringSet(SET_ID, setId).apply()
    }

    private fun getFavorites() : MutableSet<String> {
        val sharedPref = requireContext().getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        val setOfIdFromSharedPref = sharedPref.getStringSet(SET_ID, emptySet())
        val newSetOfId: MutableSet<String> = mutableSetOf()
            if (setOfIdFromSharedPref != null) {
            newSetOfId.addAll(setOfIdFromSharedPref)
        } else {
            emptySet<String>()
        }
        return newSetOfId
    }
}
