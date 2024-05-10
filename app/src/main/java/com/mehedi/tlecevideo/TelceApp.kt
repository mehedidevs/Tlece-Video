package com.mehedi.tlecevideo

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mehedi.tlecevideo.work.RecurringAlarm
import com.mehedi.tlecevideo.work.RefreshDataWorker

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class TelceApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }


    private fun delayedInit() {
        applicationScope.launch {
            setupOneTimeWork()
            RecurringAlarm.setRecurringAlarm(this@TelceApp)
        }
    }


    private fun setupOneTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<RefreshDataWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(oneTimeRequest)
    }


}