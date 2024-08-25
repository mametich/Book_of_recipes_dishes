package com.example.burgershop.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.databinding.ItemIngredientBinding
import com.example.burgershop.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    var dataSet: List<Ingredient> = emptyList(),
    var quantity: Int = 0,
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    class IngredientViewHolder(
        private val binding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient, quantity: Int) {
            val bigDecimalQuantity = BigDecimal(quantity)
            var countOfQuantity = BigDecimal(ingredient.quantity).multiply(bigDecimalQuantity)
            if (countOfQuantity.scale() > 0) {
                countOfQuantity = countOfQuantity.setScale(1, RoundingMode.HALF_UP)
            }

            binding.apply {
                ingredientName.text = ingredient.description
                ingredientUnitOfMeasure.text = ingredient.unitOfMeasure
                ingredientQuantity.text = countOfQuantity.toPlainString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredientFromRecipe = dataSet[position]
        holder.bind(ingredientFromRecipe, quantity)
    }

    override fun getItemCount(): Int = dataSet.size
}