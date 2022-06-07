package com.endava.internship.mobile.budgetplanner.ui.transaction

import com.endava.internship.mobile.budgetplanner.databinding.LayoutChipChoiceBinding
import com.google.android.material.chip.Chip

class TransactionCategoryViewHolder(private val binding: LayoutChipChoiceBinding) :
    TransactionChipGroupViewAdapter.ViewHolder(binding.root) {

    fun bind(model: TransactionCategoryModel) {
        with(binding.root.findViewById<Chip>(model.id)) {
            text = model.name
            chipBackgroundColor = model.backgroundColorStateList
        }
    }
}