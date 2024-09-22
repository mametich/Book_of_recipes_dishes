package com.example.burgershop.ui.category

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.burgershop.R
import com.example.burgershop.databinding.ItemCategoryBinding
import com.example.burgershop.model.Category
import kotlinx.coroutines.withContext


class CategoriesListAdapter(
    private var dataset: List<Category> = emptyList()
) : RecyclerView.Adapter<CategoriesListAdapter.CategoriesViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(newDataset: List<Category>) {
        dataset = newDataset
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(categoryFromList: Category)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageCategory)
        val titleTextView: TextView = view.findViewById(R.id.tvTitleCategory)
        val descriptionTextView: TextView = view.findViewById(R.id.tvTextDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoriesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = dataset[position]

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(category)
        }

        holder.titleTextView.text = category.title
        holder.descriptionTextView.text = category.description

        Glide.with(holder.imageView.context)
            .load("$URL_FOR_IMAGE${category.imgUrl}"  )
            .error(R.drawable.img_error)
            .placeholder(R.drawable.img_placeholder)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = dataset.size

    companion object {
        private const val URL_FOR_IMAGE = "https://recipes.androidsprint.ru/api/images/"
    }
}
