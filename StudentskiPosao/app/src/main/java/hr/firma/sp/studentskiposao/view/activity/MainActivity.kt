package hr.firma.sp.studentskiposao.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hr.firma.sp.studentskiposao.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()
    }

    private fun setListeners() {
        setLoginListener()
    }

    private fun setLoginListener() {
        login_btn.setOnClickListener() {
            startActivity(Intent(this, JobsActivity::class.java))
        }
    }

}
