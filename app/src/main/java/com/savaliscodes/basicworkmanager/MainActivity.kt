package com.douglasstarnes.basicworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val workManager = WorkManager.getInstance(this)
    lateinit var btnStartWork: Button
    lateinit var btnWorkStatus: Button
    lateinit var btnResetStatus: Button
    lateinit var btnWorkUIThread: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartWork = findViewById(R.id.btnStartWork)
        btnWorkStatus = findViewById(R.id.btnWorkStatus)
        btnResetStatus = findViewById(R.id.btnResetStatus)
        btnWorkUIThread = findViewById(R.id.btnWorkUIThread)

        btnStartWork.setOnClickListener {
//            val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
//            val data = Data.Builder()
//                .putString("WORK_MESSAGE", "Work Completed!")
//                .build()
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val data = workDataOf("WORK_MESSAGE" to "Work Completed!")
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setInputData(data)
                .setConstraints(constraints)
                .build()
            val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                5, TimeUnit.MINUTES,
                1, TimeUnit.MINUTES
            ).build()
            workManager.enqueue(workRequest)
        }

        btnWorkStatus.setOnClickListener {
            val toast = Toast.makeText(this, "The work status is: ${WorkStatusSingleton.workMessage}", Toast.LENGTH_SHORT)
            toast.show()
        }

        btnResetStatus.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }

        btnWorkUIThread.setOnClickListener {
            Thread.sleep(10000)
            WorkStatusSingleton.workComplete = true
        }
    }
}