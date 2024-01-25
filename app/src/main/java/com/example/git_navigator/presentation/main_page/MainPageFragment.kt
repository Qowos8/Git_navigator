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
import androidx.lifecycle.Observer
import com.example.git_navigator.R
import com.example.git_navigator.data.network.Commit
import com.example.git_navigator.databinding.FragmentBeginPageBinding
import com.example.git_navigator.presentation.repository_list.RepositoriesListFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.navigation.NavigationView


class MainPageFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentBeginPageBinding
    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var pref: SharedPreferences
    private lateinit var toggle: ActionBarDrawerToggle

    private val login = viewModel.getUserName()
    private val token = viewModel.getUserToken()
    private var sizes: MutableList<Int> = mutableListOf()
    private var repoNames: MutableList<String> = emptyList<String>().toMutableList()

    private var languages: MutableList<String> = emptyList<String>().toMutableList()
    private var langCount: MutableMap<String, Int> = emptyMap<String, Int>().toMutableMap()

    private var commits: List<Commit> = emptyList<Commit>()
    private var commitsCount: MutableMap<String, Int> = emptyMap<String, Int>().toMutableMap()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentBeginPageBinding.inflate(inflater, container, false)
        //pref = requireContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        buildCharts()

        clickOnSideMenu()

        binding.redBlock.setOnClickListener {
            openRepos()
        }
        return binding.root
    }

    private fun buildCharts() {
        getStats()
        getCommit()
        buildSizeChart()
        buildLangPie()
        buildCommitsChart()
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


    private fun buildSizeChart() {
        val sizeBarChart: BarChart = binding.sizeBar
        val description = Description()
        var entries = sizes.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value.toFloat())
        }
        val set = BarDataSet(entries, "")
        val data = BarData(set)

        setSizeChartData(sizeBarChart, entries, data)
        setSizeChartDescription(sizeBarChart, description)
        setSizeChartText(sizeBarChart)
        setSizeChartColor(sizeBarChart, description, entries, set)

        sizeBarChart.invalidate()
        sizes.clear()
    }
    private fun setSizeChartDescription(sizeBarChart: BarChart, description: Description){
        description.text = "Статистика размеров"
        description.textSize = 16f
        sizeBarChart.description = description
        sizeBarChart.description.isEnabled = true
        description.setPosition(3f, 3f)
    }
    private fun setSizeChartColor(sizeBarChart: BarChart, description: Description, entries: List<BarEntry>, set: BarDataSet){
        sizeBarChart.setBackgroundColor(Color.parseColor("#1A222D"))
        sizeBarChart.xAxis.textColor = Color.WHITE
        sizeBarChart.axisLeft.textColor = Color.WHITE
        description.textColor = Color.WHITE
        val dynamicColors: MutableList<Int> = mutableListOf()
        for (i in entries.indices) {
            val color = generateDynamicColor(i)
            dynamicColors.add(color)
        }
        set.barBorderColor = Color.parseColor("#4788C7")
        set.colors = dynamicColors
    }
    private fun setSizeChartText(sizeBarChart: BarChart){
        val xAxisValues = getXAxisValues(repoNames)
        sizeBarChart.legend.isEnabled = false
        sizeBarChart.setFitBars(true)
        sizeBarChart.xAxis.setLabelCount(xAxisValues.size, false)
        sizeBarChart.xAxis.textSize = 7F
        sizeBarChart.xAxis.granularity = 0f
        sizeBarChart.xAxis.position = XAxisPosition.BOTTOM
        sizeBarChart.xAxis.setCenterAxisLabels(true)
        sizeBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        sizeBarChart.xAxis.setDrawGridLines(false)
        sizeBarChart.axisRight.isEnabled = false
    }
    private fun setSizeChartData(sizeBarChart: BarChart, entries: List<BarEntry>, data: BarData){
        val set = BarDataSet(entries, "")
        set.barBorderWidth = 5f
        sizeBarChart.data = data
        sizeBarChart.animateY(3000)
    }


    private fun buildLangPie() {
        val pieChart = binding.langPie
        val entries: List<PieEntry> = langCount.map { entry ->
            PieEntry(entry.value.toFloat(), entry.key)
        }
        val pieSet = PieDataSet(entries, "")
        val data = PieData(pieSet)
        setLangPieColor(pieChart, pieSet, entries)
        setLangPieData(pieChart, data)

    }
    private fun setLangPieColor(pieChart: PieChart, pieSet: PieDataSet, entries: List<PieEntry>){
        val dynamicColors: MutableList<Int> = mutableListOf()
        for (i in entries.indices) {
            val color = generateDynamicColor(i)
            dynamicColors.add(color)
        }
        //pieChart.setHoleColor(Color.parseColor("#1A222D"))
        pieSet.colors = dynamicColors
        pieChart.description.textColor = Color.WHITE
        pieChart.legend.textColor = Color.WHITE
    }
    private fun setLangPieData(pieChart: PieChart, data: PieData){
        pieChart.holeRadius = 0f
        pieChart.data = data
        pieChart.animateY(3000)
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.invalidate()
    }

    private fun buildCommitsChart() {
        val commitsBarChart: BarChart = binding.commitsChart
        Log.d("commits", "$commitsCount")
        val entries = commitsCount.map { entry ->
            BarEntry(entry.value.toFloat(), entry.key.toFloat())
        }
        val set = BarDataSet(entries, "")
        val data = BarData(set)
        val description = Description()

        setCommitChartDescription(commitsBarChart, description)
        setCommitChartText(commitsBarChart)
        setCommitChartColor(commitsBarChart, set, description, entries)
        setCommitChartOther(set, commitsBarChart, data)
        commitsBarChart.invalidate()
    }
    private fun setCommitChartDescription(commitsBarChart: BarChart, description: Description){
        description.text = "Статистика размеров"
        description.textSize = 16f
        commitsBarChart.description = description
        commitsBarChart.description.isEnabled = true
        description.setPosition(3f, 3f)
    }
    private fun setCommitChartText(commitsBarChart: BarChart){
        commitsBarChart.legend.isEnabled = false
        commitsBarChart.setFitBars(true)
        commitsBarChart.xAxis.setLabelCount(getXAxisValues(repoNames).size, false)
        commitsBarChart.xAxis.textSize = 7F
        commitsBarChart.xAxis.granularity = 0f
        commitsBarChart.xAxis.position = XAxisPosition.BOTTOM
        commitsBarChart.xAxis.setCenterAxisLabels(true)
        commitsBarChart.xAxis.valueFormatter = IndexAxisValueFormatter(getXAxisValues(repoNames))
        commitsBarChart.xAxis.setDrawGridLines(false)
        commitsBarChart.axisRight.isEnabled = false
    }
    private fun setCommitChartColor(commitsBarChart: BarChart, set: BarDataSet, description: Description, entries: List<BarEntry>){
        commitsBarChart.setBackgroundColor(Color.parseColor("#1A222D"))
        commitsBarChart.xAxis.textColor = Color.WHITE
        commitsBarChart.axisLeft.textColor = Color.WHITE
        description.textColor = Color.WHITE
        set.barBorderColor = Color.parseColor("#4788C7")
        val dynamicColors: MutableList<Int> = mutableListOf()
        for (i in entries.indices) {
            val color = generateDynamicColor(i)
            dynamicColors.add(color)
        }
        set.colors = dynamicColors
    }
    private fun setCommitChartOther(set: BarDataSet, commitsBarChart: BarChart, data: BarData){
        set.barBorderWidth = 5f
        commitsBarChart.data = data
        commitsBarChart.animateY(3000)
    }


    private fun generateDynamicColor(index: Int): Int {
        val hue = (index * 30) % 360
        return Color.HSVToColor(floatArrayOf(hue.toFloat(), 1f, 1f))
    }


    private fun clickOnSideMenu() {
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

    private fun getStats() {
        viewModel.repos.observe(viewLifecycleOwner, Observer { repos ->
            for (repo in repos) {
                sizes.add(repo.size)
                repoNames.add(repo.name)
                languages.add(repo.language!!)
                langCount = viewModel.getLanguages(languages)
            }

        })
    }

    private fun getCommit() {
        for (i in 0..4){
            viewModel.requestCommits(login, token, repoNames[i])
        }
        viewModel.commits.observe(viewLifecycleOwner, Observer { commit ->
            for (i in 0..4) {
                val commitsList: MutableList<Commit> = emptyList<Commit>().toMutableList()
                commitsList.add(commit[i])
                commitsCount = viewModel.getCommitCount(commitsList)
            }

        })
    }

    private fun openRepos() {
        val fragment = RepositoriesListFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.auth_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}