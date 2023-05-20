package com.em.meal_app.domain

import com.em.meal_app.data.model.Category
import com.em.meal_app.data.model.Meal
import com.em.meal_app.data.remote.DataSource
import com.em.meal_app.core.Resource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dataSource: DataSource) : Repository {
    override suspend fun getMealsListByCategory(category: String): Resource<List<Meal>> {
        return dataSource.getMealsByCategory(category)
    }

    override suspend fun getAllCategories(): Resource<List<Category>> {
        return dataSource.getAllCategories()
    }

    override suspend fun getMealById(id: String): Resource<Meal> {
        return dataSource.getMealById(id)
    }
}