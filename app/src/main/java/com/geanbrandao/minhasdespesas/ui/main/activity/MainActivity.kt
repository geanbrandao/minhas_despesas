package com.geanbrandao.minhasdespesas.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.goToActivity
import com.geanbrandao.minhasdespesas.ui.home.HomeActivity
import com.geanbrandao.minhasdespesas.ui.splash_screen.SplashScreenActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToActivity(SplashScreenActivity::class.java)
        finish()

    }
}