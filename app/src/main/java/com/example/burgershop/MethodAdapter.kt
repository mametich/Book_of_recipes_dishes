package com.example.burgershop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.databinding.ItemMethodBinding

class MethodAdapter(
    private val dataSet: List<String>,
    private val recipe: Recipe
) : RecyclerView.Adapter<MethodAdapter.MethodViewHolder>() {

    class MethodViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.tvStepMethod)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMethodBinding.inflate(layoutInflater, parent, false)
        return MethodViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {
        val listOfMethod = recipe.method
        val method = listOfMethod[position]
        holder.textView.text = method
    }

    override fun getItemCount(): Int = dataSet.size
}