package hr.firma.sp.studentskiposao.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.widget.Toast
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.adapter.JobsAdapter
import hr.firma.sp.studentskiposao.algorithm.RedBlackBST.RedBlackBST
import hr.firma.sp.studentskiposao.algorithm.Search
import hr.firma.sp.studentskiposao.algorithm.Sort
import hr.firma.sp.studentskiposao.model.AbstractData
import hr.firma.sp.studentskiposao.model.JobData
import kotlinx.android.synthetic.main.activity_jobs.*
import kotlinx.android.synthetic.main.content_jobs.*
import java.util.*
import kotlin.collections.ArrayList

class JobsActivity : AppCompatActivity() {

    private lateinit var jobsAdapter: JobsAdapter
    private var jobs: MutableList<AbstractData> = ArrayList()
    private val searchAlgorithm: Search = Search()
    private val sortAlgorithm: Sort = Sort()
    private var treeJobs: MutableMap<String, MutableList<Int>> = HashMap()
    private var redBlackBST: RedBlackBST<String, MutableList<Int>> = RedBlackBST()
    private var searchField: String = "price"
    private var searchAlgorithmPicked: String = SearchAlgorithms.REDBLACKBST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        initActivity()

    }

    fun initActivity() {
        initJobsRecyclerView()
        initSearch()
        initCompanyLogo()
        initRedBlackBST()
    }

    fun initRedBlackBST() {

        treeJobs.clear()
        redBlackBST = RedBlackBST()

        for (jobIndex in 0..(jobs.size - 1)) {
            var j = jobs.get(jobIndex)

            if (treeJobs.get(j.getValueForField(searchField)) != null) {
                treeJobs.get(j.getValueForField(searchField))?.add(jobIndex)
            } else {
                treeJobs.put(j.getValueForField(searchField), arrayListOf(jobIndex))
            }
        }

        for ((key, value) in treeJobs) {
            redBlackBST.put(key, value)
        }

    }

    private fun initCompanyLogo() {
        companies_logo_iv.setOnClickListener {
            startActivity(Intent(this, CompaniesActivity::class.java))
        }
    }

    private fun initSearch() {
        jobs_search.isActivated = true
        jobs_search.onActionViewExpanded()
        jobs_search.isIconified = false
        jobs_search.clearFocus()

        setSearchListener()

    }

    private fun setSearchListener() {
        jobs_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                filterJobs(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterJobs(newText)
                return true
            }

        })
    }

    fun filterJobs(query: String?) {
        var start = Date().time
        query?.let {
            var filJobs: MutableList<AbstractData> = ArrayList()
            if (searchAlgorithmPicked == SearchAlgorithms.REDBLACKBST) {
                var jobIndexes: MutableList<Int>? = redBlackBST.get(query)
                if (jobIndexes != null) {
                    for (item in jobIndexes) {
                        filJobs.add(jobs.get(item))
                    }
                }
            } else {
                filJobs.addAll(searchAlgorithm.searchCompanies(jobs, query))
            }
            Toast.makeText(this, "Searched in " + ((Date().time - start) / 1000F) + " seconds", Toast.LENGTH_LONG).show()
            start = Date().time
            sortAlgorithm.quickSort(filJobs, "price")
            Toast.makeText(this, "Sorted in " + ((Date().time - start) / 1000F) + " seconds", Toast.LENGTH_LONG).show()
            jobsAdapter.setItems(filJobs.map { it as JobData }.toMutableList())
        }
    }

    fun initJobsRecyclerView() {

        jobsAdapter = JobsAdapter(this)

        val gridManager = GridLayoutManager(this, 2)

        jobs_rv.layoutManager = gridManager
        jobs_rv.itemAnimator = DefaultItemAnimator()
        jobs_rv.adapter = jobsAdapter

        jobs = generateJobs()
        jobsAdapter.setItems(jobs.map { it as JobData }.toMutableList())
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    private fun generateJobs() : MutableList<AbstractData> {

        val genJobs: MutableList<AbstractData> = ArrayList()
        val r = Random()

        for (i in 0..1000000) {
            val companyId = r.nextInt(3)
            genJobs.add(JobData(
                i.toLong(),
                jobTitles.get(r.nextInt(3)),
                jobDescs.get(r.nextInt(3)),
                jobImgs.get(companyId),
                "Kn/h",
                prices.get(r.nextInt(3)),
                locations.get(r.nextInt(3)),
                companies.get(companyId),
                categories.get(r.nextInt(3)),
                datesTo.get(r.nextInt(3))
            ))
        }

        return genJobs
    }

    val jobTitles = arrayListOf("Pakiranje sladoleda", "Rad u trgovini", "Blagajnik")
    val jobDescs = arrayListOf("Pakiranje sladoleda u kutije.", "Slaganje polica, skladištenje robe.",
            "Rad na blagajni i usluživanje kupaca.")
    val jobImgs = arrayListOf(R.drawable.ledo, R.drawable.konzum, R.drawable.polleo)
    val prices = arrayListOf(30.toDouble(), 25.toDouble(), 20.toDouble())
    val locations = arrayListOf("Zagreb, Savica", "Split, Savica", "Zagreb, Dubrava")
    val companies = arrayListOf("Ledo", "Konzum", "Polleo")
    val categories = arrayListOf("Skladište", "Trgovina", "Financije")
    val datesTo = arrayListOf("25.09.", "01.10.", "31.10.")

}
