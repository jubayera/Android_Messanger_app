// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;

// Referenced classes of package com.maestro.hangout:
//            MyListFragment, DetailFragment, Globals, ContentLoader

class this._cls1
    implements android.view.pter._cls1
{

    final this._cls1 this$1;

    public void onClick(View view)
    {
        view = (String[])view.getTag();
        DetailFragment detailfragment = (DetailFragment)cess._mth0(this._cls1.this).getFragmentManager().findFragmentById(0x7f0a0002);
        Globals.current_report = 4;
        detailfragment.showTypeWiseEventDetails(view[0], view[1], view[2], null);
        ContentLoader.mSlidingLayout.closePane();
    }

    A()
    {
        this$1 = this._cls1.this;
        super();
    }
}
