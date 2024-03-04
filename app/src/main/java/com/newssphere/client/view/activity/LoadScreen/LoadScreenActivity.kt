package com.newssphere.client.view.activity.LoadScreen

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.newssphere.client.R
import com.newssphere.client.databinding.ActivityLoadscreenBinding
import com.newssphere.client.view.activity.Home.HomeActivity

class LoadScreenActivity : Activity() {
    private lateinit var binding: ActivityLoadscreenBinding
    private val TAG = LoadScreenActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{false}
        actionBar?.hide()
        setUpView()
    }

    private fun setUpView(){
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            startActivity(HomeActivity.newIntent(this@LoadScreenActivity))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, 4000)
    }
}
