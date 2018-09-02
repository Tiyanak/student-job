package hr.firma.sp.studentskiposao.adapter.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.viewHolder.CompanyViewHolder
import hr.firma.sp.studentskiposao.model.CompanyData

class CompanyAdapter(val context: Context) : RecyclerView.Adapter<CompanyViewHolder>() {

    private var companies: MutableList<CompanyData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.company_card, parent, false)

        return CompanyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companies.get(position)

        Glide.with(context).load(company.image).into(holder.image)
        holder.name.text = company.name
    }

    fun setItems(inCompanies: MutableList<CompanyData>) {
        companies = inCompanies
        notifyDataSetChanged()
    }

    fun getItems() : MutableList<CompanyData> {
        return companies
    }

}
