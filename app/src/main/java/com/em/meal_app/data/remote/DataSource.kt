package com.em.meal_app.data.remote

import com.em.meal_app.data.model.Category
import com.em.meal_app.data.model.Meal
import com.em.meal_app.core.Resource

interface DataSource {
    suspend fun getMealsByCategory(category: String): Resource<List<Meal>>
    suspend fun getAllCategories(): Resource<List<Category>>
    suspend fun getMealById(id: String): Resource<Meal>
}