package css.com.resourceapi

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.graphics.drawable.LevelListDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import css.com.applab.R
import kotlinx.android.synthetic.main.activity_resource_api.*

class ResourceApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_api)
        rootView.setOnClickListener {
//            sampleTv.startAnimation(AnimationUtils.loadAnimation(this,R.anim.hyperspace_jump))
            val set: AnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.property_animator)
                    .apply {
                        setTarget(sampleTv)
                        start()
                    } as AnimatorSet

        }
    }
}