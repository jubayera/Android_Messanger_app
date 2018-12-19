// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.view.View;

// Referenced classes of package com.maestro.hangout:
//            ContentLoader

private class <init> extends android.support.v4.widget.SlideListener
{

    final ContentLoader this$0;

    public void onPanelClosed(View view)
    {
        ContentLoader.access$1(ContentLoader.this);
    }

    public void onPanelOpened(View view)
    {
        ContentLoader.access$0(ContentLoader.this);
    }

    public void onPanelSlide(View view, float f)
    {
    }

    private ePanelSlideListener()
    {
        this$0 = ContentLoader.this;
        super();
    }

    ePanelSlideListener(ePanelSlideListener epanelslidelistener)
    {
        this();
    }
}
