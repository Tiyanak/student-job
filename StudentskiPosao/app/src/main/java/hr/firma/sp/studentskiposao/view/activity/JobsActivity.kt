package hr.firma.sp.studentskiposao.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.adapter.JobsAdapter
import hr.firma.sp.studentskiposao.algorithm.QuickSort
import hr.firma.sp.studentskiposao.algorithm.RedBlackBST
import hr.firma.sp.studentskiposao.model.AbstractData
import hr.firma.sp.studentskiposao.model.JobData
import kotlinx.android.synthetic.main.activity_jobs.*
import kotlinx.android.synthetic.main.content_jobs.*
import kotlinx.android.synthetic.main.nav_header_jobs.*
import kotlinx.android.synthetic.main.nav_header_jobs.view.*
import java.util.*
import kotlin.collections.ArrayList

// ovo je znaci kotlin klasa koja definira view ekran aplikacije, specificno ova je povezana sa ekranom poslova
// slicno za kompanije, CompanyActivity -> lista kompanija
// isto kak je ovdje je i u companyActivity-u da ne pisem 2put komentare
class JobsActivity : AppCompatActivity() {

    private lateinit var jobsAdapter: JobsAdapter
    private var jobs: MutableList<AbstractData> = ArrayList()
    private var searchField: String = "price"
    private var sortField: String = "name"
    private var treeJobs: MutableMap<String, MutableList<Int>> = HashMap()
    private var redBlackBST: RedBlackBST<String, MutableList<Int>> = RedBlackBST()
    private lateinit var jobsSearchAdapter: ArrayAdapter<CharSequence>
    private lateinit var jobsSortAdapter: ArrayAdapter<CharSequence>

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
        initDrawer()
    }

    fun initFilterButton() {
        nav_view.getHeaderView(0)?.filter_jobs?.setOnClickListener {
            drawer_jobs.closeDrawers()
            initRedBlackBST()
            var filJobs: MutableList<AbstractData> = ArrayList()
            filJobs.addAll(jobs)
            QuickSort.sort(filJobs, sortField)
            jobsAdapter.setItems(filJobs.map { it as JobData }.toMutableList())
        }
    }

    fun initSpinner() {

        jobsSearchAdapter = ArrayAdapter.createFromResource(this, R.array.job_fields, android.R.layout.simple_spinner_item)
        jobsSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var isl = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                searchField = jobsSearchAdapter.getItem(p2).toString()
                println("search field " + searchField)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        if (search_field_spinner == null) {
            nav_view.getHeaderView(0).search_field_spinner?.adapter = jobsSearchAdapter
            nav_view.getHeaderView(0).search_field_spinner.onItemSelectedListener = isl
        } else {
            search_field_spinner?.adapter = jobsSearchAdapter
            search_field_spinner?.onItemSelectedListener = isl
        }

        jobsSortAdapter = ArrayAdapter.createFromResource(this, R.array.job_fields, android.R.layout.simple_spinner_item)
        jobsSortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var islSort = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sortField = jobsSortAdapter.getItem(p2).toString()
                println("search field " + sortField)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        if (sort_field_spinner == null) {
            nav_view.getHeaderView(0).sort_field_spinner?.adapter = jobsSortAdapter
            nav_view.getHeaderView(0).sort_field_spinner?.onItemSelectedListener = islSort
        } else {
            sort_field_spinner?.adapter = jobsSortAdapter
            sort_field_spinner?.onItemSelectedListener = islSort
        }

    }

    fun initFilterOptions() {
        initSpinner()
        initFilterButton()
    }

    fun initDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_jobs, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(R.drawable.ic_filter_list_black_24dp)
        toolbar.setNavigationOnClickListener {
            drawer_jobs.openDrawer(Gravity.START)
        }
        drawer_jobs.addDrawerListener(toggle)
        toggle.syncState()

        initFilterOptions()

    }

    override fun onBackPressed() {
        if (drawer_jobs.isDrawerOpen(GravityCompat.START)) {
            drawer_jobs.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // ovo je bitno, na pocetku kad se kreiraekran poslova (klikne se gumb prijavi se i odeovdje)
    // pozove se automatski ova metoda, koja napuni stablo RedBlackTree
    // bitna je specificna implementacija - stablo kao kljuceve Key drzi vrijednosti onoga sto pretrazujes,
    // a kao Value drzi listu indexa [0, 1, 2...] za  poslove u listi poslova jobs gore
    // znaci npr. za Key = "Konzum" Value jest npr = [0, 5, 10], jer su to u listi job (na pocetku definiranoj)
    // indeksi poslova od Konzuma (index = redni broj u listi krenuvsi od 0)
    // e tako, onda se gradi stablo, npr. za polje company ces imat stablo sa 3 kljuca = "Ledo", "Konzum", "Polleo"
    fun initRedBlackBST() {

        treeJobs.clear()
        redBlackBST = RedBlackBST()

        // kreiraj mapu sa kljucevima i listama, nije dio pravog algoritma, nego moja implementacija
        // kako bi se moglo lakse kreirat stablo
        for (jobIndex in 0..(jobs.size - 1)) {
            var j = jobs.get(jobIndex)

            if (treeJobs.get(j.getValueForField(searchField)) != null) {
                treeJobs.get(j.getValueForField(searchField))?.add(jobIndex)
            } else {
                treeJobs.put(j.getValueForField(searchField), arrayListOf(jobIndex))
            }
        }

        // kreira se red black tree
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

    // gore je definiran listener kojipokrece ovu metodu kadkorisnik upise znak ili potvrdi pretragu
    // prvo sortira tako da dohvati iz stabla vrijednosti za dani kljuc odnosno query
    // vrijednosti su indeksi liste, i dohvati sve poslove redBlackBST.get(query) iz gornje liste jobs sa tim nadenim indeksima
    // zatimsortiraj QuickSort.sort(pronadeniPoslovi, poljePretrazivanja)
    fun filterJobs(query: String?) {

        if (query == null || query.isEmpty()) jobsAdapter.setItems(jobs.map { it as JobData }.toMutableList())

        query?.let {
            var filJobs: MutableList<AbstractData> = ArrayList()
            var jobIndexes: MutableList<Int>? = redBlackBST.get(query)
            if (jobIndexes != null) {
                for (item in jobIndexes) {
                    filJobs.add(jobs.get(item))
                }
            }
            QuickSort.sort(filJobs, sortField)
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

    private fun generateJobs() : MutableList<AbstractData> {

        val genJobs: MutableList<AbstractData> = ArrayList()
        val r = Random()

        for (i in 0..50) {
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
    val prices = arrayListOf(20.0, 25.0, 30.0)
    val locations = arrayListOf("Zagreb, Savica", "Split, Savica", "Zagreb, Dubrava")
    val companies = arrayListOf("Ledo", "Konzum", "Polleo")
    val categories = arrayListOf("Skladište", "Trgovina", "Financije")
    val datesTo = arrayListOf("25.09.", "01.10.", "31.10.")

}
