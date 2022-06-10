package com.endava.internship.mobile.budgetplanner.ui.transaction.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.databinding.TransactionItemBinding

class TransactionsRecycleViewAdapter : RecyclerView.Adapter<TransactionViewHolder>() {

    private var dataSet = mutableListOf<TransactionModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {
        val binding = TransactionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.updateView()
    }

    override fun getItemCount() = dataSet.size

    fun updateDataSet(dataSet: MutableList<TransactionModel>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

}
