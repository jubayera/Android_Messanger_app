// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

private class mIdMap extends ArrayAdapter
{

    HashMap mIdMap;
    final DetailFragment this$0;

    public long getItemId(int i)
    {
        String s = (String)getItem(i);
        return (long)((Integer)mIdMap.get(s)).intValue();
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public (Context context, int i, List list)
    {
        this$0 = DetailFragment.this;
        super(context, i, list);
        mIdMap = new HashMap();
        i = 0;
        do
        {
            if (i >= list.size())
            {
                return;
            }
            mIdMap.put((String)list.get(i), Integer.valueOf(i));
            i++;
        } while (true);
    }
}
