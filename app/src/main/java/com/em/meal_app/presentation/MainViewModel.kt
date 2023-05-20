package com.em.meal_app.presentation

import android.util.Log
import androidx.lifecycle.*
import com.em.meal_app.domain.Repository
import com.em.meal_app.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var mealData = MutableLiveData<String>()
    private var mealId = MutableLiveData<String>()

    private var mealName: String = "Beef"

    fun setMealData(data: String) {
        mealName = data
        mealData.value = data
    }

    fun setMealId(data: String) {
        mealId.value = data
    }

    val fetchListOfMeals = mealData.distinctUntilChanged().switchMap { mealName ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repository.getMealsListByCategory(mealName))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    val fetchCategories = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getAllCategories())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    val fetchSingleMeal = mealId.distinctUntilChanged().switchMap { mealId ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repository.getMealById(mealId))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}