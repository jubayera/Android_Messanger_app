// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;


// Referenced classes of package com.maestro.hangout:
//            DetailFragment

private class apprReq
{

    private int add_comment;
    private int add_reg;
    private boolean apprReq;
    private String background_color;
    private long eventid;
    private String foruser;
    private String product;
    private String regarding;
    private String role;
    private String summary;
    final DetailFragment this$0;
    private String unseen;
    private int view_all;

    public int getAdd_comment()
    {
        return add_comment;
    }

    public int getAdd_reg()
    {
        return add_reg;
    }

    public boolean getApprReg()
    {
        return apprReq;
    }

    public String getBackground_color()
    {
        return background_color;
    }

    public long getEventid()
    {
        return eventid;
    }

    public String getForuser()
    {
        return foruser;
    }

    public String getProduct()
    {
        return product;
    }

    public String getRegarding()
    {
        return regarding;
    }

    public String getRole()
    {
        return role;
    }

    public String getSummary()
    {
        return summary;
    }

    public String getUnseen()
    {
        return unseen;
    }

    public int getView_all()
    {
        return view_all;
    }

    public void setAdd_comment(int i)
    {
        add_comment = i;
    }

    public void setAdd_reg(int i)
    {
        add_reg = i;
    }

    public void setApprReq(boolean flag)
    {
        apprReq = flag;
    }

    public void setBackground_color(String s)
    {
        background_color = s;
    }

    public void setEventid(long l)
    {
        eventid = l;
    }

    public void setForuser(String s)
    {
        foruser = s;
    }

    public void setProduct(String s)
    {
        product = s;
    }

    public void setRegarding(String s)
    {
        regarding = s;
    }

    public void setRole(String s)
    {
        role = s;
    }

    public void setSummary(String s)
    {
        summary = s;
    }

    public void setUnseen(String s)
    {
        unseen = s;
    }

    public void setView_all(int i)
    {
        view_all = i;
    }

    public String toString()
    {
        return (new StringBuilder(String.valueOf(role))).append("\n").append(summary).toString();
    }

    public (String s, String s1, String s2, String s3, String s4, String s5, 
            int i, int j, int k, String s6, String s7)
    {
        this$0 = DetailFragment.this;
        super();
        role = s;
        summary = s1;
        regarding = s2;
        product = s3;
        foruser = s4;
        unseen = s5;
        add_comment = i;
        add_reg = j;
        view_all = k;
        background_color = s6;
        if (s7 != null && s7.equals("Y"))
        {
            apprReq = true;
        }
    }
}
