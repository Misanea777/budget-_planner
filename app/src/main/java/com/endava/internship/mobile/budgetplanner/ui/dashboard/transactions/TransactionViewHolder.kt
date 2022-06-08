package com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions

import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.databinding.TransactionCategoryItemBinding

class TransactionViewHolder(private val binding: TransactionCategoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TransactionModel) {
            with(binding) {
                iconImage.setImageResource(model.icon)
                iconImage.background.colorFilter = model.getBackgroundColor()
                titleText.text = model.name
                transactionsText.text = "${model.numberOfTransactions} transactions"
            }
        }
}
