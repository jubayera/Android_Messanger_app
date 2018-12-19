// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.FragmentManager;
import android.view.MenuItem;

// Referenced classes of package com.maestro.hangout:
//            ContentLoader, Globals, MyListFragment

class this._cls0
    implements android.widget.ItemClickListener
{

    final ContentLoader this$0;

    public boolean onMenuItemClick(MenuItem menuitem)
    {
        if (menuitem.getTitle().toString().equals("Notifications"))
        {
            Globals.current_report = 1;
            ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList(null);
            return true;
        }
        if (menuitem.getTitle().toString().equals("Event Summary"))
        {
            Globals.current_report = 2;
            ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).showEventSummary();
            return true;
        }
        if (menuitem.getTitle().toString().equals("Archieve List"))
        {
            Globals.current_report = 3;
            ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList("archaive");
            return true;
        }
        sort_type = menuitem.getTitle().toString();
        if (!sort_type.equals("Sort by Role")) goto _L2; else goto _L1
_L1:
        sort_type = "role";
_L4:
        Globals.current_report = 1;
        ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList(sort_type);
        return true;
_L2:
        if (sort_type.equals("Sort by Priority"))
        {
            sort_type = "priority";
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    lickListener()
    {
        this$0 = ContentLoader.this;
        super();
    }
}
