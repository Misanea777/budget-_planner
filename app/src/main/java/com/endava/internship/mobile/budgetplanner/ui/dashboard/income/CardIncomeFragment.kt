package com.endava.internship.mobile.budgetplanner.ui.dashboard.income

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.FragmentCardIncomeBinding
import com.endava.internship.mobile.budgetplanner.ui.base.BaseFragment
import com.endava.internship.mobile.budgetplanner.util.asDollars
import com.endava.internship.mobile.budgetplanner.util.plusInFront
import com.endava.internship.mobile.budgetplanner.util.toFancyNumberFormat

class CardIncomeFragment :
    BaseFragment<FragmentCardIncomeBinding>(FragmentCardIncomeBinding::inflate) {

    private val incomeViewModel by activityViewModels<IncomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        incomeViewModel.transactionsGeneralInfo.observe(viewLifecycleOwner) { transactionsGeneralInfo ->
            val amount = transactionsGeneralInfo.sumIncomeCategories
            binding.root.alpha = if(amount != 0.0)  1f else 0.2f
            binding.cardImage.findViewById<TextView>(R.id.card_image_text).text =
                amount.toFancyNumberFormat().asDollars()
                    .plusInFront()
        }
    }
}
