// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.TextView;

// Referenced classes of package com.maestro.hangout:
//            MyListFragment, DetailFragment, Globals, ContentLoader

class this._cls1
    implements android.view.pter._cls4
{

    final this._cls1 this$1;

    public void onClick(View view)
    {
        String as[];
        as = (String[])view.getTag();
        view = (String)((TextView)view).getText();
        if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
        if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
        as[3] = "high";
_L6:
        view = (DetailFragment)cess._mth0(this._cls1.this).getFragmentManager().findFragmentById(0x7f0a0002);
        Globals.current_report = 4;
        view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
        ContentLoader.mSlidingLayout.closePane();
_L2:
        return;
_L4:
        if (view.substring(view.length() - 1).equals("N"))
        {
            as[3] = "new";
        } else
        if (view.substring(view.length() - 1).equals("O"))
        {
            as[3] = "op";
        } else
        if (view.substring(view.length() - 1).equals("C"))
        {
            as[3] = "comp";
        } else
        if (view.substring(view.length() - 1).equals("P"))
        {
            as[3] = "pending";
        } else
        if (view.substring(view.length() - 1).equals("A"))
        {
            as[3] = "appr";
        } else
        if (view.substring(view.length() - 1).equals("U"))
        {
            as[3] = "unseen";
        }
        if (true) goto _L6; else goto _L5
_L5:
    }

    A()
    {
        this$1 = this._cls1.this;
        super();
    }
}
