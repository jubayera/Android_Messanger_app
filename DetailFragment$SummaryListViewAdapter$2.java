// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.view.View;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment, Globals

class this._cls1
    implements android.view.pter._cls2
{

    final this._cls1 this$1;

    public void onClick(View view)
    {
        Globals.current_report = 6;
        cess._mth0(this._cls1.this).showEventDetails(view.getId());
    }

    A()
    {
        this$1 = this._cls1.this;
        super();
    }
}
