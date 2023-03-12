package com.project.mathg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.mathg.databinding.GridItemsBinding
import com.project.mathg.model.ListItem

@Suppress("DEPRECATION")
class RecyclerViewAdapter(
    private var list: ArrayList<ListItem>, private val itemChecker: ItemChecker
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding = GridItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bindTo(list[position], itemChecker)
    }

    override fun getItemCount(): Int {
        return 16
    }

    inner class RecyclerViewViewHolder(private var binding: GridItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(listItem: ListItem, itemChecker: ItemChecker) {
            binding.btnUnknown.id = listItem.id
            binding.btnUnknown.isClickable = listItem.isClickable
            binding.btnUnknown.text = listItem.answer
            android.os.Handler().postDelayed({
                listItem.isClickable = true
                listItem.isCardBack = true
                binding.btnUnknown.text = "?"
            }, 5000)
            binding.btnUnknown.setOnClickListener {
                if (listItem.isCardBack && listItem.isClickable && !listItem.isMatch) {
                    binding.btnUnknown.text = listItem.answer
                    itemChecker.clicked(listItem, adapterPosition)
                }
            }
        }
    }

    interface ItemChecker {
        fun clicked(listItem: ListItem, position: Int)
    }
}