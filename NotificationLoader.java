// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

// Referenced classes of package com.maestro.hangout:
//            ContentLoader, Globals

public class NotificationLoader extends Activity
{

    public NotificationLoader()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        finish();
    }

    public void onDestroy()
    {
        super.onDestroy();
        Intent intent = new Intent(this, com/maestro/hangout/ContentLoader);
        intent.setFlags(0x24000000);
        ContentLoader.load_notification = true;
        Globals.current_report = 7;
        startActivity(intent);
    }
}
