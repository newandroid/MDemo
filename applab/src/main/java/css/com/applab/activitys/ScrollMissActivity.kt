package css.com.applab.activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import css.com.applab.R
import kotlinx.android.synthetic.main.activity_scroll_miss.*


class ScrollMissActivity : AppCompatActivity() {
    var startY = 0f
    var moveY = 0f
    private var enable = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_miss)
//        View的background会判断scrollX和ScrollY是否为零，然后把canvas调整为原来的的位置
//        testScrollBy.setOnClickListener {
//            operatorRoot.scrollBy(0, 100)
//        }
        testScrollBy.setOnClickListener {
            enable = !enable
        }
        recycleView.setOnTouchListener { v, ev ->
            if (enable){
                true
            }
            when (ev.getAction()) {
                MotionEvent.ACTION_DOWN -> {
                    println("ACTION_DOWN")
                    startY = ev.getY()
                }
                MotionEvent.ACTION_MOVE -> {

                    moveY = ev.getY() - startY
                    println("ACTION_MOVE moveY:${-moveY.toInt()}")
                    operatorRoot.scrollBy(0, -moveY.toInt())
                    startY = ev.getY()
                    if (operatorRoot.getScrollY() > 0) {
                        operatorRoot.scrollTo(0, 0)
                    }
                }
                MotionEvent.ACTION_UP -> {
//                    if (operatorRoot.getScrollY() < -this.window.attributes.height / 4 && moveY > 0) {
//                        this.dismiss()
//                    }
                    println("ACTION_UP")
                    operatorRoot.scrollTo(0, 0)
                }
            }
            true
        }

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter
        recycleView.verticalScrollbarPosition
    }

    private class Vh(itemView: Button) : RecyclerView.ViewHolder(itemView)

    private val adapter = object : RecyclerView.Adapter<Vh>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
            val btn = Button(parent.context)
            return Vh(btn)
        }

        override fun onBindViewHolder(holder: Vh, position: Int) {
            (holder.itemView as Button).setText("$position")
        }

        override fun getItemCount(): Int {
            return 100
        }

    }
}