package com.example.burgershop

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.databinding.ItemCategoryBinding
import java.io.InputStream


class CategoriesListAdapter(
    private val dataset: List<Category>
) : RecyclerView.Adapter<CategoriesListAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categoryItem = dataset[position]
        holder.bind(categoryItem)
    }

    inner class CategoriesViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val inputStream: InputStream? =
            itemView.context?.assets?.open(dataset[adapterPosition].imgUrl)
        private val drawable = Drawable.createFromStream(inputStream, null)

        fun bind(category: Category) {
            binding.apply {
                tvTitleCategory.text = category.title
                tvTextDescription.text = category.description
                imageCategory.setImageDrawable(drawable)
            }
        }
    }
}