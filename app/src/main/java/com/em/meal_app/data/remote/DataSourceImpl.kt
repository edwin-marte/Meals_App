package com.em.meal_app.data.remote

import com.em.meal_app.data.model.Category
import com.em.meal_app.data.model.Meal
import com.em.meal_app.core.Resource
import javax.inject.Inject

class DataSourceImpl @Inject constructor() : DataSource {
    override suspend fun getMealsByCategory(category: String): Resource<List<Meal>> {
        return Resource.Success(RetrofitClient.webService.getMealByCategory(category).mealsList)
    }

    override suspend fun getAllCategories(): Resource<List<Category>> {
        return Resource.Success(RetrofitClient.webService.getAllCategories().categoriesList)
    }

    override suspend fun getMealById(id: String): Resource<Meal> {
        return Resource.Success(RetrofitClient.webService.getMealById(id).mealsList[0])
    }
}