package com.example.git_navigator.presentation.main_page

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.git_navigator.R
import com.example.git_navigator.databinding.FragmentBeginPageBinding
import com.example.git_navigator.presentation.repository_list.RepositoriesListFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MainPageFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentBeginPageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var pref: SharedPreferences
    private var sizes: MutableList<Int> = mutableListOf()
    private var repoNames: MutableList<String> = emptyList<String>().toMutableList()
    private val viewModel: MainPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentBeginPageBinding.inflate(inflater, container, false)
        pref = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        buildCharts()

        clickOnSideMenu()

        binding.redBlock.setOnClickListener {
            openRepos()
        }
        return binding.root
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item)
    }

    private fun getXAxisValues(namesList: List<String>): List<String> {
        val xAxis: MutableList<String> = emptyList<String>().toMutableList()
        if (namesList.isEmpty()) {
            xAxis.add("repo 1")
            xAxis.add("repo 2")
            xAxis.add("repo 3")
            xAxis.add("repo 4")
        } else {
            xAxis.add(namesList[0])
            xAxis.add(namesList[1])
            xAxis.add(namesList[2])
            xAxis.add(namesList[3])
        }
        return xAxis
    }

    private suspend fun getStats() {
        val token = pref.getString("token", "")
        val login = pref.getString("login", "")

        try {
            val repos = viewModel.requestData(login!!, token!!)

            for (repo in repos) {
                sizes.add(repo.size)
                repoNames.add(repo.name)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun buildSizeChart() {
        val barChart: BarChart = binding.barCommits
        Log.d("sizes", "$sizes")
        val entries = sizes.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value.toFloat())
        }
        val set = BarDataSet(entries, "")
        set.barBorderWidth = 5f
        set.barBorderColor = Color.parseColor("#4788C7")
        val data = BarData(set)
        barChart.data = data
        barChart.animateY(3000)

        val xAxisValues = getXAxisValues(repoNames)
        val xAxis = barChart.xAxis
        val yAxisL = binding.barCommits.axisLeft
        val yAxisR = binding.barCommits.axisRight
        //description
        val description = Description()
        description.text = "Статистика размеров"
        description.textSize = 16f
        barChart.description = description
        barChart.description.isEnabled = true
        description.setPosition(3f, 3f)
        //text-value
        barChart.legend.isEnabled = false
        barChart.setFitBars(true)
        xAxis.setLabelCount(xAxisValues.size, false)
        xAxis.textSize = 7F
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setCenterAxisLabels(true)
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        xAxis.setDrawGridLines(false)
        //color
        val colors: MutableList<Int> = mutableListOf(
            Color.parseColor("#98CCFD"),
            Color.WHITE
        )
        barChart.setBackgroundColor(Color.parseColor("#1A222D"))
        xAxis.textColor = Color.WHITE
        yAxisL.textColor = Color.WHITE
        yAxisR.textColor = Color.WHITE
        description.textColor = Color.WHITE
        set.colors = colors

        barChart.invalidate()
    }

    private fun buildCharts() {
        lifecycleScope.launch {
            try {
                getStats()
                buildSizeChart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun clickOnSideMenu(){
        binding.hamburger.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.sideMenu.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun openRepos() {
        val fragment = RepositoriesListFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.auth_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}