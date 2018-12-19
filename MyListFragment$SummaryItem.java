// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;


// Referenced classes of package com.maestro.hangout:
//            MyListFragment

private class pending
{

    private String appr_req;
    private String comp;
    private long dept_id;
    private String heading;
    private String high;
    private String op;
    private String pending;
    private long role_id;
    private String st_new;
    final MyListFragment this$0;
    private String unseen;

    public String getAppr_req()
    {
        return appr_req;
    }

    public String getComp()
    {
        return comp;
    }

    public long getDept_id()
    {
        return dept_id;
    }

    public String getHeading()
    {
        return heading;
    }

    public String getHigh()
    {
        return high;
    }

    public String getOp()
    {
        return op;
    }

    public String getPending()
    {
        return pending;
    }

    public long getRole_id()
    {
        return role_id;
    }

    public String getSt_new()
    {
        return st_new;
    }

    public String getUnseen()
    {
        return unseen;
    }

    public void setAppr_req(String s)
    {
        appr_req = s;
    }

    public void setComp(String s)
    {
        comp = s;
    }

    public void setDept_id(long l)
    {
        dept_id = l;
    }

    public void setHeading(String s)
    {
        heading = s;
    }

    public void setHigh(String s)
    {
        high = s;
    }

    public void setOp(String s)
    {
        op = s;
    }

    public void setPending(String s)
    {
        pending = s;
    }

    public void setRole_id(long l)
    {
        role_id = l;
    }

    public void setSt_new(String s)
    {
        st_new = s;
    }

    public void setUnseen(String s)
    {
        unseen = s;
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(heading))).append("\n").append(high).toString();
    }

    public (String s, String s1, String s2, String s3, String s4, String s5, 
            String s6, String s7, long l, long l1)
    {
        this$0 = MyListFragment.this;
        super();
        heading = s;
        high = s1;
        st_new = s2;
        op = s3;
        comp = s4;
        unseen = s6;
        appr_req = s7;
        role_id = l;
        dept_id = l1;
        pending = s5;
    }
}
