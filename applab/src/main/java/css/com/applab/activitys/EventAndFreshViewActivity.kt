package css.com.applab.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import css.com.applab.R
import kotlinx.android.synthetic.main.activity_event_and_fresh_view.*

class EventAndFreshViewActivity : AppCompatActivity() {
    private var vi = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_and_fresh_view)

        button.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                println("MotionEvent.ACTION_DOWN")
            } else if (event.action == MotionEvent.ACTION_UP) {
                println("MotionEvent.ACTION_UP")
            } else if (event.action == MotionEvent.ACTION_CANCEL) {
                println("MotionEvent.ACTION_CANCEL")
            }
            true
        }


        Thread {
            Thread.sleep(5*1000)
            while (true) {
                Thread.sleep(160)
                runOnUiThread {
                    if (vi) {
//                        button.visibility = View.GONE
                        root.removeView(button)
                    } else {
//                        button.visibility = View.VISIBLE
                        root.addView(button)
                    }
                    vi = !vi
                    println("fresh")
                }
            }
        }.start()
    }
}