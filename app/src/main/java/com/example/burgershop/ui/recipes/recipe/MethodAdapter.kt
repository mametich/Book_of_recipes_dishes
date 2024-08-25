package com.example.burgershop.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.burgershop.R
import com.example.burgershop.databinding.ItemMethodBinding
import com.example.burgershop.model.Recipe

class MethodAdapter(
    var dataSet: List<String> = emptyList(),
    var recipe: Recipe? = null,
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
        val listOfMethod = recipe?.method

        val listMethodIndex = listOfMethod?.mapIndexed { index, method ->
            "${index + 1}. $method"
        }
        val method = listMethodIndex?.get(position)
        holder.textView.text = method
    }

    override fun getItemCount(): Int = dataSet.size
}