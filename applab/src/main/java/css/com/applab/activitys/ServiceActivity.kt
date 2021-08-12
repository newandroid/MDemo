package css.com.applab.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import css.com.applab.R
import css.com.applab.service.MyService
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        button14.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }
    }
}