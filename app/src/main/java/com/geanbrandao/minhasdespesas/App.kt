package com.geanbrandao.minhasdespesas

import android.app.Application
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initAppCenter()
    }

    private fun initAppCenter() {
        AppCenter.start(
            this, "a38f7e8c-8261-4366-a24b-62e194daef7b",
            Analytics::class.java, Crashes::class.java
        )
    }
}