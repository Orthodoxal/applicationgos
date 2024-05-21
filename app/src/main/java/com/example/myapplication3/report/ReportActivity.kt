package com.example.myapplication3.report

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication3.databinding.ActivityReportBinding
import kotlinx.coroutines.launch

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding

    private val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.state.collect(::renderScreen)
            }
        }

        binding.createReport.setOnClickListener {
            viewModel.createReport()
        }
    }

    private fun renderScreen(reportScreenState: ReportScreenState) {
        with(binding) {
            when (val resultFile = reportScreenState.resultFile) {
                null -> {
                    report.visibility = View.GONE
                    createReport.visibility = View.VISIBLE
                }
                else -> {
                    createReport.visibility = View.GONE
                    report.visibility = View.VISIBLE
                    report.text = resultFile
                }
            }
        }
    }
}