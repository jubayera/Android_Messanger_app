// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.view.View;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

class this._cls0
    implements android.view.er
{

    final DetailFragment this$0;

    public void onClick(View view)
    {
        view = (String[])view.getTag();
        showTypeWiseEventDetails(view[0], view[1], view[2], "comp");
    }

    ()
    {
        this$0 = DetailFragment.this;
        super();
    }
}
