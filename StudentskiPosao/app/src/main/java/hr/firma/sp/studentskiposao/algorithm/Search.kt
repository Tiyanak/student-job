package hr.firma.sp.studentskiposao.algorithm

import hr.firma.sp.studentskiposao.model.AbstractData
import hr.firma.sp.studentskiposao.model.CompanyData
import hr.firma.sp.studentskiposao.model.JobData
import java.util.Locale.filter

class Search {

    fun searchJobs(items: MutableList<AbstractData>, query: String) : MutableList<AbstractData> {

        var searchedItems: MutableList<AbstractData> = items.filter {
            search((it as JobData).category, query) or
            search((it as JobData).company, query) or
            search((it as JobData).description, query) or
            search((it as JobData).location, query) or
            search((it as JobData).title, query) or
            search((it as JobData).price.toString(), query)
        }.toMutableList()

        return searchedItems

    }

    fun searchCompanies(items: MutableList<AbstractData>, query: String) : MutableList<AbstractData> {

        var searchedCompanies: MutableList<AbstractData> = items.filter {
            search((it as CompanyData).name, query)
        }.toMutableList()

        return searchedCompanies

    }

    fun search(text: String, query: String) : Boolean {
        return text.toLowerCase().contains(query.toLowerCase())
    }

}