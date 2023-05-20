package com.em.meal_app.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @Json(name = "strCategory")
    val description: String = "",
    @Json(name = "strCategoryThumb")
    val image: String = "",
) : Parcelable {
    override fun toString(): String = description
}

data class CategoriesList(
    @Json(name = "categories")
    val categoriesList: List<Category>
)