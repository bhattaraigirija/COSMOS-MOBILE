package com.mobile.cosmos

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class MyService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("My Service","Service is running.......")
        return START_NOT_STICKY
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.e("My Service", "Service Stopped....")
        super.onDestroy()
    }

}