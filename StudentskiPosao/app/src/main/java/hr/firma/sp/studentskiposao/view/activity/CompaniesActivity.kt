package hr.firma.sp.studentskiposao.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.adapter.CompanyAdapter
import hr.firma.sp.studentskiposao.algorithm.RedBlackBST.RedBlackBST
import hr.firma.sp.studentskiposao.algorithm.Search
import hr.firma.sp.studentskiposao.algorithm.Sort
import hr.firma.sp.studentskiposao.model.AbstractData
import hr.firma.sp.studentskiposao.model.CompanyData
import kotlinx.android.synthetic.main.activity_companies.*
import kotlinx.android.synthetic.main.content_companies.*

object SearchAlgorithms {
    val REDBLACKBST = "redblackbst"
    val SEQ = "seq"
}

class CompaniesActivity : AppCompatActivity() {

    private lateinit var companyAdapter: CompanyAdapter
    private var companies: MutableList<AbstractData> = ArrayList()
    private val searchAlgorithm: Search = Search()
    private val sortAlgorithm: Sort = Sort()
    private var treeCompanies: MutableMap<String, MutableList<Int>> = HashMap()
    private var redBlackBST: RedBlackBST<String, MutableList<Int>> = RedBlackBST()
    private var searchField: String = "name"
    private var searchAlgorithmPicked: String = SearchAlgorithms.REDBLACKBST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_companies)

        initActivity()

    }

    private fun initActivity() {
        initCompaniesRecyclerView()
        initSearch()
        initRedBlackBST()
    }

    fun initRedBlackBST() {

        treeCompanies.clear()
        redBlackBST = RedBlackBST()

        for (companyIndex in 0..(companies.size - 1)) {
            var c = companies.get(companyIndex)

            if (treeCompanies.get(c.getValueForField(searchField)) != null) {
                treeCompanies.get(c.getValueForField(searchField))?.add(companyIndex)
            } else {
                treeCompanies.put(c.getValueForField(searchField), arrayListOf(companyIndex))
            }
        }

        for ((key, value) in treeCompanies) {
            redBlackBST.put(key, value)
        }

    }

    private fun initSearch() {
        companies_search.isActivated = true
        companies_search.onActionViewExpanded()
        companies_search.isIconified = false
        companies_search.clearFocus()

        setSearchListener()

    }

    private fun initCompaniesRecyclerView() {

        companyAdapter = CompanyAdapter(this)

        val gridManager = GridLayoutManager(this, 1)

        companies_rv.layoutManager = gridManager
        companies_rv.itemAnimator = DefaultItemAnimator()
        companies_rv.adapter = companyAdapter

        companies = generateCompanies()
        companyAdapter.setItems(companies.map { it as CompanyData }.toMutableList())

    }

    private fun setSearchListener() {
        companies_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                filterJobs(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterJobs(newText)
                return true
            }

        })
    }

    fun filterJobs(query: String?) {
        query?.let {
            var filCompanies: MutableList<AbstractData> = ArrayList()
            if (searchAlgorithmPicked == SearchAlgorithms.REDBLACKBST) {
                var companyIndexes: MutableList<Int>? = redBlackBST.get(query)
                if (companyIndexes != null) {
                    for (item in companyIndexes) {
                        filCompanies.add(companies.get(item))
                    }
                }
            } else {
                filCompanies.addAll(searchAlgorithm.searchCompanies(companies, query))
            }
            sortAlgorithm.quickSort(filCompanies, "name")
            companyAdapter.setItems(filCompanies.map { it as CompanyData }.toMutableList())
        }
    }

    private fun generateCompanies() : MutableList<AbstractData> {
        val genCompanies: MutableList<AbstractData> = ArrayList()
        for (i in 0..2) {
            genCompanies.add(CompanyData(
                    i.toLong(),
                    names.get(i),
                    descs.get(i),
                    images.get(i)
            ))
        }

        return genCompanies
    }

    private val names = arrayListOf("Konzum", "Polleo", "Ledo")
    private val descs = arrayListOf("Lanac supermarketa u Hrvatskoj s preko 700 dućana.",
            "Centar zdrave hrane i opreme za Sport, Fitness, Pilates i  Bodybuilding.",
            "Najveći hrvatski proizvođač sladoleda i  smrznute hrane.")
    private val images = arrayListOf(R.drawable.konzum, R.drawable.polleo, R.drawable.ledo)

}
