// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

// Referenced classes of package com.maestro.hangout:
//            MyListFragment, CustomListViewAdapter

class this._cls0
    implements android.widget.mLongClickListener
{

    final MyListFragment this$0;

    public boolean onItemLongClick(AdapterView adapterview, View view, int i, long l)
    {
        adapterview = getListView();
        boolean flag;
        if (MyListFragment.mAdapter.isPositionChecked(i))
        {
            flag = false;
        } else
        {
            flag = true;
        }
        adapterview.setItemChecked(i, flag);
        Log.e("long press", (new StringBuilder()).append(i).toString());
        return false;
    }

    pter()
    {
        this$0 = MyListFragment.this;
        super();
    }
}
