// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.util.Log;

public class Globals
{

    public static final int ACTION_NOTIFICATION_CLICKED = 7;
    public static final int REPORT_ARCHIEVE_NOTIFICATION = 3;
    public static final int REPORT_EVENT_DETAILS = 5;
    public static final int REPORT_EVENT_DETAILS_WEB = 6;
    public static final int REPORT_EVENT_SUMMARY = 2;
    public static final int REPORT_NOTIFICATION_LIST = 1;
    public static final int REPORT_TYPEWISE_EVENTS = 4;
    public static long curr_event_id;
    public static long curr_noti_id;
    public static int current_report;
    private static Globals instance;
    public static String isRoleOrDept;
    public static String role_dept_id;
    public static String type;
    public static String type_value;
    private long eventId2;
    private boolean is_owner;
    private String login;
    private long oms_id;
    private String sname;
    private boolean syncing;

    private Globals()
    {
        sname = "";
        login = "";
        oms_id = -1L;
        is_owner = false;
    }

    public static Globals getInstance()
    {
        com/maestro/hangout/Globals;
        JVM INSTR monitorenter ;
        Globals globals;
        if (instance == null)
        {
            instance = new Globals();
        }
        Log.e("---Global OMS ID ", (new StringBuilder()).append(instance.getOms_id()).toString());
        if (instance.getOms_id() != -1L);
        globals = instance;
        com/maestro/hangout/Globals;
        JVM INSTR monitorexit ;
        return globals;
        Exception exception;
        exception;
        throw exception;
    }

    public static void setInstance(Globals globals)
    {
        instance = globals;
    }

    public int getCurrent_report()
    {
        return current_report;
    }

    public long getEventId2()
    {
        return eventId2;
    }

    public boolean getIs_owner()
    {
        return is_owner;
    }

    public String getLogin()
    {
        return login;
    }

    public long getOms_id()
    {
        return oms_id;
    }

    public String getSname()
    {
        return sname;
    }

    public boolean isSyncing()
    {
        return syncing;
    }

    public void setCurrent_report(int i)
    {
        current_report = i;
    }

    public void setEventId2(long l)
    {
        eventId2 = l;
    }

    public void setIs_owner(boolean flag)
    {
        is_owner = flag;
    }

    public void setLogin(String s)
    {
        login = s;
    }

    public void setOms_id(long l)
    {
        oms_id = l;
    }

    public void setSname(String s)
    {
        sname = s;
    }

    public void setSyncing(boolean flag)
    {
        syncing = flag;
    }
}
