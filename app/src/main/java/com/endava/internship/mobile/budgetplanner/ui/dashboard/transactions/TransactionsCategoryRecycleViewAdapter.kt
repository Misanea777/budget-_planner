package com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.databinding.TransactionCategoryItemBinding

class TransactionsCategoryRecycleViewAdapter(var dataSet: Array<TransactionCategoryModel>) :
    RecyclerView.Adapter<TransactionCategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionCategoryViewHolder {
        val binding = TransactionCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionCategoryViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: Array<TransactionCategoryModel>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}
