package hr.firma.sp.studentskiposao.adapter.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import hr.firma.sp.studentskiposao.R

class CompanyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    val image: ImageView = itemView!!.findViewById(R.id.company_iv)
    val name: TextView = itemView!!.findViewById(R.id.company_name_tv)

}