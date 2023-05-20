package com.em.meal_app.domain

import com.em.meal_app.data.model.Category
import com.em.meal_app.data.model.Meal
import com.em.meal_app.core.Resource

interface Repository {
    suspend fun getMealsListByCategory(category: String): Resource<List<Meal>>
    suspend fun getAllCategories(): Resource<List<Category>>
    suspend fun getMealById(id: String): Resource<Meal>
}