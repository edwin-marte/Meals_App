package com.em.meal_app.ui.mealdetails

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.em.meal_app.R
import com.em.meal_app.data.model.Meal
import com.em.meal_app.databinding.FragmentMealDetailsBinding
import com.em.meal_app.presentation.MainViewModel
import com.em.meal_app.core.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsFragment : Fragment() {
    private lateinit var meal: Meal
    private lateinit var binding: FragmentMealDetailsBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        meal = getMealValue()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_meal_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMealId(meal.id)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.fetchSingleMeal.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    clearUI()
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setupData(result.data)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "An error occurred while loading the data ${result.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun clearUI() {
        binding.mealImg.setImageResource(android.R.color.transparent)
        binding.mealTitle.text = ""
        binding.mealCategory.text = ""
        binding.mealInstructions.text = ""
    }

    private fun setupData(meal: Meal) {
        Glide.with(requireContext()).load(meal.image).centerCrop().into(binding.mealImg)
        binding.mealTitle.text = meal.description
        binding.mealCategory.text = meal.category
        binding.mealInstructions.text = meal.instructions
    }

    private fun getMealValue(): Meal {
        when {
            SDK_INT >= 33 -> {
                return requireArguments().getParcelable("meal", Meal::class.java)!!
            }
            else -> {
                @Suppress("DEPRECATION")
                return requireArguments().getParcelable("meal")!!
            }
        }
    }
}