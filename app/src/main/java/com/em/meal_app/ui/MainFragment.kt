package com.em.meal_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.em.meal_app.R
import com.em.meal_app.data.model.Meal
import com.em.meal_app.databinding.FragmentMainBinding
import com.em.meal_app.presentation.MainViewModel
import com.em.meal_app.core.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: FragmentMainBinding
    private val meals = mutableListOf<Meal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter(meals)
        setupRecyclerViewManager()
        setupCategoriesSpinner()
        updateData()
    }

    private fun setupCategoriesSpinner() {
        binding.mealsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setMealData(binding.mealsSpinner.selectedItem.toString())
                binding.searchView.setQuery("", false)
                binding.searchView.clearFocus()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateData() {
        viewModel.fetchListOfMeals.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    meals.clear()
                    meals.addAll(result.data)

                    setupRecyclerViewAdapter(meals)
                    setupSearchView()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "An error occurred while loading the data ${ result.exception }",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.fetchCategories.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.mealsSpinner.adapter =
                        CustomSpinnerAdapter(requireContext(), result.data)
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "An error occurred while loading the data ${ result.exception }",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.onQueryTextChanged { newText ->
            if (meals.isEmpty()) return@onQueryTextChanged

            if (newText.isNotBlank() && newText.isNotEmpty()) {
                setupRecyclerViewAdapter(meals.filter { meal ->
                    meal.description.removeSpaces()
                        .startsWith(newText.removeSpaces(), ignoreCase = true)
                })
            } else setupRecyclerViewAdapter(meals)
        }
    }

    private fun setupRecyclerViewManager() {
        binding.mealsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.mealsRv.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupRecyclerViewAdapter(filter: List<Meal>) {
        fun onMealClick() = object : MainAdapter.OnItemClickListener {
            override fun onMealClick(meal: Meal) {
                val bundle = Bundle()
                bundle.putParcelable("meal", meal)
                findNavController().navigate(
                    R.id.action_mainFragment_to_mealDetailsFragment,
                    bundle
                )
            }
        }
        binding.mealsRv.adapter = MainAdapter(requireContext(), filter, onMealClick())
    }

    private inline fun SearchView.onQueryTextChanged(crossinline onQueryTextChanged: (String) -> Unit) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onQueryTextChanged(newText!!)
                return false
            }
        })
    }

    private fun String.removeSpaces(): String = replace(" ", "")
}