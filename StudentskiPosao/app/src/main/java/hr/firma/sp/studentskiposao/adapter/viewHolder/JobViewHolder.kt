package hr.firma.sp.studentskiposao.adapter.viewHolder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import hr.firma.sp.studentskiposao.R

public class JobViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var company: TextView = itemView!!.findViewById(R.id.company_tv)
    var desc: TextView = itemView!!.findViewById(R.id.job_desc_tv)
    var dateTo: TextView = itemView!!.findViewById(R.id.date_to_tv)
    var price: TextView = itemView!!.findViewById(R.id.job_price_tv)
    var timeWork: TextView = itemView!!.findViewById(R.id.job_time_work_tv)
    var jobImg: ImageView = itemView!!.findViewById(R.id.job_iv)
    var cardView: CardView = itemView!!.findViewById(R.id.job_card)

}