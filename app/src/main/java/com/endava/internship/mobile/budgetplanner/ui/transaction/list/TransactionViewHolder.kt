package com.endava.internship.mobile.budgetplanner.ui.transaction.list

import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.databinding.TransactionItemBinding

class TransactionViewHolder(private val binding: TransactionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        val root = binding.root
        binding.frameLayout.setOnClickListener {
            if (root.scrollX != 0) {
                root.scrollTo(0, 0)
            }
        }
    }

    fun bind(model: TransactionModel) {
        binding.titleText.text = model.name
        binding.transactionsDateText.text = model.date
        binding.transactionValueText.text = model.getTransactionValue()
        binding.deleteButton.setOnClickListener {
            model.onDelete.invoke(adapterPosition, model.id)
        }
    }

    fun updateView() {
        binding.root.scrollTo(0, 0)
    }
}