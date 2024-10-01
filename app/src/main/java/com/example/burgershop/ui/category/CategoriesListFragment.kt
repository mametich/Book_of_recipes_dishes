package com.example.burgershop.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.burgershop.R
import com.example.burgershop.databinding.FragmentListCategoriesBinding
import com.example.burgershop.model.Category


class CategoriesListFragment : Fragment() {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListCategoriesBinding must not be null")

    private val categoriesListViewModel: CategoriesListViewModel by viewModels()
    private val categoriesListAdapter = CategoriesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesListViewModel.loadListOfCategory()
        initUI()
    }

    private fun initUI() {
        categoriesListViewModel.categoryListUiState.observe(viewLifecycleOwner) { newCategoryListUiState ->
            if (newCategoryListUiState.listOfCategory != null) {
                categoriesListAdapter.updateDataset(newCategoryListUiState.listOfCategory)
            } else {
                Toast.makeText(context, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
            }
        }

        categoriesListAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryFromList: Category) {
                openRecipesByCategoryId(categoryFromList)
            }
        })
        binding.rvCategories.adapter = categoriesListAdapter
    }

    fun openRecipesByCategoryId(categoryFromList: Category) {
        val action =
            CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(
                categoryFromList
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

