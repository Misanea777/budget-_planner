package com.endava.internship.mobile.budgetplanner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.endava.internship.mobile.budgetplanner.R
import com.endava.internship.mobile.budgetplanner.databinding.ActivityMainBinding
import com.endava.internship.mobile.budgetplanner.ui.base.IOnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
