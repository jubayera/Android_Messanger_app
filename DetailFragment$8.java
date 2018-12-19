// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.widget.CompoundButton;
import java.util.Hashtable;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

class this._cls0
    implements android.widget.CheckedChangeListener
{

    final DetailFragment this$0;

    public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
    {
        long l = compoundbutton.getId();
        if (flag)
        {
            DetailFragment.access$5(DetailFragment.this).put(Long.valueOf(l), "1");
            return;
        } else
        {
            DetailFragment.access$5(DetailFragment.this).put(Long.valueOf(l), "");
            return;
        }
    }

    edChangeListener()
    {
        this$0 = DetailFragment.this;
        super();
    }
}
