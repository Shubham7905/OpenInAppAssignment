package com.openin.openinapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.openin.openinapp.adapter.LinksAdapter
import com.openin.openinapp.adapter.UserAdapter
import com.openin.openinapp.repository.LinksRepository
import com.openin.openinapp.viewmodel.LinksViewModel
import com.openin.openinapp.viewmodel.LinksViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: com.openin.openinapp.databinding.ActivityMainBinding
    private lateinit var viewModel: LinksViewModel
    private lateinit var linksAdapter: LinksAdapter
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val repository = LinksRepository()
        val factory = LinksViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[LinksViewModel::class.java]

        linksAdapter = LinksAdapter()
        binding.linksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.linksRecyclerView.adapter = linksAdapter
        binding.lifecycleOwner = this

        userAdapter = UserAdapter()
        binding.recyclerView1.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = userAdapter
        }

        viewModel.users.observe(this, Observer {
            userAdapter.setUsers(it)
        })

        viewModel.greeting.observe(this, Observer { greeting ->
            binding.greetingText.text = greeting
        })

        selectTab(binding.tabTop)

        binding.tabTop.setOnClickListener { selectTab(binding.tabTop) }
        binding.tabRecent.setOnClickListener { selectTab(binding.tabRecent) }

        viewModel.recentLinks.observe(this) { recentLinks ->
            if (binding.tabRecent.isSelected) {
                linksAdapter.setLinks(recentLinks)
            }
        }

        viewModel.topLinks.observe(this) { topLinks ->
            if (binding.tabTop.isSelected) {
                linksAdapter.setLinks(topLinks)
            }
        }

        viewModel.lineChartData.observe(this, Observer { entries ->
            setupLineChart(binding.overviewChart, entries)
        })

        viewModel.fetchLineChartData()

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
        viewModel.fetchLinks(token)
    }

    private fun selectTab(selectedTab: TextView) {
        binding.tabTop.isSelected = selectedTab == binding.tabTop
        binding.tabRecent.isSelected = selectedTab == binding.tabRecent

        binding.tabTop.setBackgroundResource(if (binding.tabTop.isSelected) R.drawable.tab_selected_background else R.color.unselectedBGColor)
        binding.tabRecent.setBackgroundResource(if (binding.tabRecent.isSelected) R.drawable.tab_selected_background else R.color.unselectedBGColor)

        binding.tabTop.setTextColor(if (binding.tabTop.isSelected) resources.getColor(R.color.white) else resources.getColor(R.color.unselectedColor))
        binding.tabRecent.setTextColor(if (binding.tabRecent.isSelected) resources.getColor(R.color.white) else resources.getColor(R.color.unselectedColor))

        if (binding.tabTop.isSelected) {
            viewModel.topLinks.value?.let { linksAdapter.setLinks(it) }
        } else if (binding.tabRecent.isSelected) {
            viewModel.recentLinks.value?.let { linksAdapter.setLinks(it) }
        }
    }

    private fun setupLineChart(lineChart: LineChart, entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "OverviewData")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK
        dataSet.lineWidth = 2f
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.CYAN
        dataSet.fillAlpha = 50

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"))

        val yAxisLeft: YAxis = lineChart.axisLeft
        yAxisLeft.setDrawGridLines(true)

        val yAxisRight: YAxis = lineChart.axisRight
        yAxisRight.isEnabled = false

        lineChart.legend.isEnabled = true
        lineChart.description.text = "Monthly Data"
        lineChart.description.textColor = Color.GRAY

        lineChart.animateX(1000)
        lineChart.invalidate()
    }
}