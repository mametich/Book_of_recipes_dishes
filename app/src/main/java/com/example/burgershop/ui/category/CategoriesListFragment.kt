package com.example.burgershop.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            categoriesListAdapter.dataset = newCategoryListUiState.listOfCategory
        }

        categoriesListAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
        binding.rvCategories.adapter = categoriesListAdapter
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val action = CategoriesListFragmentDirections.actionCategoriesListFragmentToRecipesListFragment(categoryId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

