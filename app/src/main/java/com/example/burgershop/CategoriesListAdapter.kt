package com.example.burgershop

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.databinding.ItemCategoryBinding


class CategoriesListAdapter(
    private val dataset: List<Category>
) : RecyclerView.Adapter<CategoriesListAdapter.CategoriesViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
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
            itemClickListener?.onItemClick(category.id)
        }

        holder.titleTextView.text = category.title
        holder.descriptionTextView.text = category.description

        val drawable = try {
            Drawable.createFromStream(
                holder.imageView.context.assets.open(category.imgUrl), null
            )
        } catch (e: Exception) {
            Log.d("!!!", "Image not found: ${category.imgUrl}")
            null
        }
        holder.imageView.setImageDrawable(drawable)
    }

    override fun getItemCount(): Int = dataset.size
}
