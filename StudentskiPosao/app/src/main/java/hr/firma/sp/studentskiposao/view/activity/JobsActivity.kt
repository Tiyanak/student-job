package hr.firma.sp.studentskiposao.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.adapter.JobsAdapter
import hr.firma.sp.studentskiposao.customView.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.content_jobs.*
import android.util.TypedValue
import hr.firma.sp.studentskiposao.model.JobData
import java.util.*


class JobsActivity : AppCompatActivity() {

    private lateinit var jobsAdapter: JobsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        initActivity()

        generateJobs()

    }

    fun initActivity() {
        initJobsRecyclerView()
    }

    fun initJobsRecyclerView() {

        jobsAdapter = JobsAdapter(this)

        val gridManager = GridLayoutManager(this, 2)

        jobs_rv.layoutManager = gridManager
//        jobs_rv.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(6), true))
        jobs_rv.itemAnimator = DefaultItemAnimator()
        jobs_rv.adapter = jobsAdapter
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    private fun generateJobs() {

        val r = Random()

        for (i in 0..100) {

            val companyId = r.nextInt(3)
            val job = JobData(
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
            )

            jobsAdapter.add(job)

        }

        jobsAdapter.notifyDataSetChanged()

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
