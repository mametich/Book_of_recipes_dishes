package com.example.burgershop.ui.recipes.recipe

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.databinding.ItemRecipeBinding
import com.example.burgershop.model.Recipe

class RecipesListAdapter(
    private val dataset: List<Recipe>
) : RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder>() {

    interface OnRecipeClickListener {
        fun onItemClick(recipeId: Int)
    }

    private var recipeClickListener: OnRecipeClickListener? = null

    fun setOnRecipeClickListener(listener: OnRecipeClickListener) {
        recipeClickListener = listener
    }

    class RecipesViewHolder(
        binding: ItemRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.tvRecipeCategory
        val imageView: ImageView = binding.imageRecipe
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return RecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = dataset[position]

        holder.textView.text = recipe.title

        holder.itemView.setOnClickListener {
            recipeClickListener?.onItemClick(recipe.id)
        }

        val drawable = try {
            Drawable.createFromStream(
                holder.imageView.context.assets.open(recipe.imageUrl), null
            )
        } catch (e: Exception) {
            Log.d("!!!", "Image not found: ${recipe.imageUrl}")
            null
        }
        holder.imageView.setImageDrawable(drawable)
    }

    override fun getItemCount(): Int = dataset.size
}
