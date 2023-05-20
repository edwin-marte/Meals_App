package com.em.meal_app.data.remote

import com.em.meal_app.data.model.CategoriesList
import com.em.meal_app.data.model.MealsList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("filter.php")
    suspend fun getMealByCategory(@Query(value = "c") category: String): MealsList

    @GET("categories.php")
    suspend fun getAllCategories(): CategoriesList

    @GET("lookup.php")
    suspend fun getMealById(@Query(value = "i") id: String): MealsList
}