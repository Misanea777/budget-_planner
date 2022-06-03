package com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.databinding.TransactionCategoryItemBinding

class TransactionRecycleViewAdapter(var dataSet: Array<TransactionModel>) :
    RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = TransactionCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: Array<TransactionModel>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}
