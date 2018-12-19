// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.widget.DatePicker;
import java.util.Calendar;

// Referenced classes of package com.maestro.hangout:
//            DateDialogFragment

class this._cls0
    implements android.app.teSetListener
{

    final DateDialogFragment this$0;

    public void onDateSet(DatePicker datepicker, int i, int j, int k)
    {
        datepicker = Calendar.getInstance();
        datepicker.set(i, j, k);
        DateDialogFragment.sListener.dateDialogFragmentDateSet(datepicker);
    }

    teDialogFragmentListener()
    {
        this$0 = DateDialogFragment.this;
        super();
    }
}
