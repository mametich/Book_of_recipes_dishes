package com.example.burgershop

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.databinding.ItemIngredientBinding
import java.text.NumberFormat

class IngredientsAdapter(
    private val dataSet: List<Ingredient>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var quantity = 1

    fun updateIngredients(progress: Int) {
        quantity = progress
    }

    class IngredientViewHolder(
        private val binding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient, quantity: Int) {
            val countOfQuantity = ingredient.quantity.toDouble() * quantity
            val textOfCountOfQuantity = if (countOfQuantity.rem(1).equals(0.0)) {
                countOfQuantity.toInt().toString()
            } else {
                NumberFormat.getInstance().format(countOfQuantity).toString()
            }

            binding.apply {
                ingredientName.text = ingredient.description
                ingredientUnitOfMeasure.text = ingredient.unitOfMeasure
                ingredientQuantity.text = textOfCountOfQuantity
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