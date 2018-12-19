// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.maestro.hangout.services.MaestroService;

public class AutoStartNotifyReceiver extends BroadcastReceiver
{

    private final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";

    public AutoStartNotifyReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        Log.e("receiving broadcast", "receiving broadcast");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            context.startService(new Intent(context, com/maestro/hangout/services/MaestroService));
        }
    }
}
