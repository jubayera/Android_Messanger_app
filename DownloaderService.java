// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class DownloaderService extends Service
{
    public class LocalBinder extends Binder
    {

        final DownloaderService this$0;

        DownloaderService getService()
        {
            return DownloaderService.this;
        }

        public LocalBinder()
        {
            this$0 = DownloaderService.this;
            super();
        }
    }


    private static final String TAG = "OMSsyncInService";
    private static long UPDATE_INTERVAL = 0x493e0L;
    ProgressDialog dialog;
    private final IBinder mBinder = new LocalBinder();
    private Timer timer;

    public DownloaderService()
    {
        timer = new Timer();
        dialog = null;
    }

    private void pollForUpdates()
    {
        timer.scheduleAtFixedRate(new TimerTask() {

            final DownloaderService this$0;

            public void run()
            {
            }

            
            {
                this$0 = DownloaderService.this;
                super();
            }
        }, 0L, UPDATE_INTERVAL);
        Log.i(getClass().getSimpleName(), "Timer started....");
    }

    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    public void onCreate()
    {
        Log.d("OMSsyncInService", "onCreate");
        pollForUpdates();
    }

    public void onDestroy()
    {
        Log.d("SYNC IN SERVICE END", "SERVICE END");
        super.onDestroy();
        if (timer != null)
        {
            timer.cancel();
        }
        Log.i(getClass().getSimpleName(), "Timer stopped.");
    }

    public void onStart(Intent intent, int i)
    {
        Log.d("SYNC IN SERVICE START", "SERVICE START");
    }

}
