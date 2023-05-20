package com.em.meal_app.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    @Json(name = "idMeal")
    val id: String = "",
    @Json(name = "strMeal")
    val description: String = "",
    @Json(name = "strMealThumb")
    val image: String = "",
    @Json(name = "strCategory")
    val category: String = "",
    @Json(name = "strInstructions")
    val instructions: String = ""
) : Parcelable

data class MealsList(
    @Json(name = "meals")
    val mealsList: List<Meal>
)
