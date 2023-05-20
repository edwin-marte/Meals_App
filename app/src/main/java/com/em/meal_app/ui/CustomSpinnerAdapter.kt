package com.em.meal_app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.em.meal_app.R
import com.em.meal_app.data.model.Category
import com.em.meal_app.databinding.CustomSpinnerItemBinding

class CustomSpinnerAdapter(context: Context, var dataSource: List<Category>) : BaseAdapter() {
    private lateinit var binding: CustomSpinnerItemBinding

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val itemHolder: ItemHolder
        if (convertView == null) {
            binding = DataBindingUtil.inflate(
                inflater, R.layout.custom_spinner_item, parent, false
            )
            view = binding.root
            itemHolder = ItemHolder(binding)
            view.tag = itemHolder
        } else {
            view = convertView
            itemHolder = view.tag as ItemHolder
        }

        itemHolder.description.text = dataSource[position].toString()
        Glide.with(view.context).load(dataSource[position].image).into(itemHolder.image)

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ItemHolder(binding: CustomSpinnerItemBinding) {
        val description: TextView
        val image: ImageView

        init {
            description = binding.text
            image = binding.img
        }
    }
}