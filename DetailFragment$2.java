// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.widget.TimePicker;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

class this._cls0
    implements android.app.OnTimeSetListener
{

    final DetailFragment this$0;

    public void onTimeSet(TimePicker timepicker, int i, int j)
    {
        DetailFragment.access$3(DetailFragment.this, i);
        DetailFragment.access$4(DetailFragment.this, j);
    }

    tListener()
    {
        this$0 = DetailFragment.this;
        super();
    }
}
