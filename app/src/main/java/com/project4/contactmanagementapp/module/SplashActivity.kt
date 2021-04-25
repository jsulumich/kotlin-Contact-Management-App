package com.project4.contactmanagementapp.module

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.project4.contactmanagementapp.Constants.DELAY_SPLASH
import com.project4.contactmanagementapp.R
import com.project4.contactmanagementapp.module.contact.HomeActivity

class SplashActivity : AppCompatActivity() {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed({
            goToHomeActivity()
        },DELAY_SPLASH)
    }

    /**
     * This method is used to navigate on home screen
     */
    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
