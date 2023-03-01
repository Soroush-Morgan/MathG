package com.project.mathg.adapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.mathg.databinding.GridItemsBinding
import com.project.mathg.model.ListItem

class RecyclerViewViewHolder(private var binding: GridItemsBinding) : ViewHolder(binding.root) {
    @SuppressLint("ResourceAsColor")
    fun bindTo(listItem: ListItem, position: Int) {
        var checker = true
        binding.tvExpressionItem.text = listItem.answer
        binding.tvExpressionItem.id = listItem.id
        binding.tvExpressionItem.text = "?"
        binding.tvExpressionItem.setOnClickListener {
            if (checker) {
                binding.tvExpressionItem.text = listItem.answer
                Log.e("Soroush", listItem.toString())
                checker = false
            } else {
                binding.tvExpressionItem.text = "?"
                Log.e("Soroush", listItem.id.toString())
                checker = true
            }
        }
    }
}
