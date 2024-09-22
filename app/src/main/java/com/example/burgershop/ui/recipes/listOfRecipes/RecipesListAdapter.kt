package com.example.burgershop.ui.recipes.listOfRecipes

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.burgershop.R
import com.example.burgershop.databinding.ItemRecipeBinding
import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe

class RecipesListAdapter(
    private var dataset: List<Recipe> = emptyList()
) : RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(newDataset: List<Recipe>) {
        dataset = newDataset
        notifyDataSetChanged()
    }

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

        Glide.with(holder.imageView.context)
            .load("$URL_FOR_IMAGE${recipe.imageUrl}")
            .error(R.drawable.img_error)
            .placeholder(R.drawable.img_placeholder)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = dataset.size

    companion object {
        private const val URL_FOR_IMAGE = "https://recipes.androidsprint.ru/api/images/"
    }
}
