package com.savaliscodes.basicworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        val message = inputData.getString("WORK_MESSAGE")
//        Thread.sleep(10000)
        WorkStatusSingleton.workComplete = true
        if (message != null) {
            WorkStatusSingleton.workMessage = message
        }
        return Result.success()
    }
}