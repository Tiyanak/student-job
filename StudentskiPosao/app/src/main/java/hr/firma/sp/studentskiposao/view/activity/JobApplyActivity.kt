package hr.firma.sp.studentskiposao.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hr.firma.sp.studentskiposao.R
import hr.firma.sp.studentskiposao.model.JobData
import kotlinx.android.synthetic.main.activity_job_apply.*

class JobApplyActivity : AppCompatActivity() {

    private var job: JobData = JobData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_apply)

        initActivity()

    }

    private fun initActivity() {

        getExtras()
        setJobViewValues()
        setListeners()

    }

    private fun setListeners() {
        setApplyButtonListener()
    }

    private fun setApplyButtonListener() {
        job_apply_btn.setOnClickListener {
            job_apply_btn.setBackgroundColor(resources.getColor(R.color.green))
            job_apply_btn.isClickable = false
        }
    }

    fun setJobViewValues() {
        company_tv.text = job.company
        job_desc_tv.text = job.description
        date_to_tv.text = job.dateTo
        job_price_tv.text = job.price.toString()
        job_time_work_tv.text = job.workTime
        category_tv.text = job.category
        location_tv.text = job.location
        Glide.with(this).load(job.image).into(job_iv)
    }

    fun getExtras() {
        job.id = this.intent.extras.getLong("id")
        job.title = this.intent.extras.getString("title")
        job.description = this.intent.extras.getString("description")
        job.image = this.intent.extras.getInt("image")
        job.workTime = this.intent.extras.getString("workTime")
        job.price = this.intent.extras.getDouble("price")
        job.location = this.intent.extras.getString("location")
        job.company = this.intent.extras.getString("company")
        job.category = this.intent.extras.getString("category")
        job.dateTo = this.intent.extras.getString("dateTo")
    }

}
