package com.endava.internship.mobile.budgetplanner.ui.dashboard.transactions

import androidx.recyclerview.widget.RecyclerView
import com.endava.internship.mobile.budgetplanner.databinding.TransactionCategoryItemBinding

class TransactionCategoryViewHolder(private val binding: TransactionCategoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TransactionCategoryModel) {
            with(binding) {
                iconImage.setImageResource(model.icon)
                iconImage.background.colorFilter = model.getBackgroundColor()
                titleText.text = model.name
                transactionsText.text = model.getNumberOfTransactions()
                binding.root.setOnClickListener {
                    model.onClick?.invoke()
                }
            }
        }
}
