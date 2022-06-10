package com.endava.internship.mobile.budgetplanner.ui.dashboard.expenses

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentCardExpensesBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.util.asDollars
import com.endava.internship.mobile.budgetplanner.util.minusInFront
import com.endava.internship.mobile.budgetplanner.util.toFancyNumberFormat

class CardExpensesFragment :
    BaseFragment<FragmentCardExpensesBinding>(FragmentCardExpensesBinding::inflate) {

    private val expensesViewModel by activityViewModels<ExpensesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expensesViewModel.transactionsGeneralInfo.observe(viewLifecycleOwner) { transactionsGeneralInfo ->
            val amount = transactionsGeneralInfo.sumExpenseCategories
            if(amount != 0.0) binding.root.alpha = 1f
            binding.cardImage.findViewById<TextView>(R.id.card_image_text).text =
                amount.toFancyNumberFormat().asDollars().minusInFront()
        }
    }
}
