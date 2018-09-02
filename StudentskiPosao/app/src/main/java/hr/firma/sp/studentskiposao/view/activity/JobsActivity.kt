package hr.firma.sp.studentskiposao.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.TypedValue
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.adapter.JobsAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        initActivity()

    }

    fun initActivity() {
        initJobsRecyclerView()
        initSearch()
        initCompanyLogo()
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
        query?.let {
            var jobs = searchAlgorithm.searchJobs(jobs, query)
            sortAlgorithm.quickSort(jobs, "price")
            jobsAdapter.setItems(jobs.map { it as JobData }.toMutableList())
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

        for (i in 0..99) {
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
