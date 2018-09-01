package hr.firma.sp.studentskiposao.adapter.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.adapter.viewHolder.JobViewHolder
import hr.firma.sp.studentskiposao.model.JobData
import hr.firma.sp.studentskiposao.view.activity.JobApplyActivity

class JobsAdapter(val context: Context) : RecyclerView.Adapter<JobViewHolder>() {

    private val jobs: MutableList<JobData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.job_card, parent, false)

        return JobViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {

        val job = jobs.get(position)

        holder.company.text = job.company
        holder.desc.text = job.description
        holder.dateTo.text = job.dateTo
        holder.price.text = job.price.toString()
        holder.timeWork.text = job.workTime

        Glide.with(context).load(job.image).into(holder.jobImg)

        setJobCardListener(job, holder)

    }

    fun addAndNotify(job: JobData) {
        jobs.add(job)
        notifyItemInserted(jobs.size - 1)
    }

    fun add(job: JobData) {
        jobs.add(job)
    }

    fun setItems(jobs: MutableList<JobData>) {
        jobs.clear()
        jobs.addAll(jobs)
        notifyDataSetChanged()
    }

    fun setJobCardListener(job: JobData, holder: JobViewHolder) {

        holder.cardView.setOnClickListener {
            val intent = createIntentWithExtras(job)
            context.startActivity(intent)
        }

    }

    fun createIntentWithExtras(job: JobData) : Intent {

        val intent = Intent(context, JobApplyActivity::class.java)

        intent.putExtra("id", job.id)
        intent.putExtra("title", job.title)
        intent.putExtra("description", job.description)
        intent.putExtra("image", job.image)
        intent.putExtra("company", job.company)
        intent.putExtra("category", job.category)
        intent.putExtra("dateTo", job.dateTo)
        intent.putExtra("price", job.price)
        intent.putExtra("workTime", job.workTime)
        intent.putExtra("location", job.location)

        return intent
    }

}