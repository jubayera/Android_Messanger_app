// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// Referenced classes of package com.maestro.hangout:
//            Globals, ContentLoader

public class SyncNotifyReceiver extends BroadcastReceiver
{

    public SyncNotifyReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        Log.e("notified", (new StringBuilder("notified ")).append(Globals.current_report).toString());
        intent = new Intent(context, com/maestro/hangout/ContentLoader);
        intent.setFlags(0x24000000);
        context.startActivity(intent);
    }
}
