package com.em.meal_app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.em.meal_app.R
import com.em.meal_app.core.BaseViewHolder
import com.em.meal_app.data.model.Meal
import com.em.meal_app.databinding.MealRecyclerviewRowBinding

class MainAdapter(
    private val context: Context,
    private var meals: List<Meal>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    lateinit var binding: MealRecyclerviewRowBinding

    interface OnItemClickListener {
        fun onMealClick(meal: Meal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.meal_recyclerview_row, parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> {
                holder.bind(meals[position], position)
            }
        }
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Meal>(itemView) {
        override fun bind(item: Meal, position: Int) {
            Glide.with(context).load(item.image).centerCrop().into(binding.imgMeal)
            binding.txtDescription.text = item.description
            itemView.setOnClickListener { itemClickListener.onMealClick(item) }
        }
    }
}