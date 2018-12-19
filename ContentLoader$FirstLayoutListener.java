// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.support.v4.widget.SlidingPaneLayout;
import android.view.ViewTreeObserver;

// Referenced classes of package com.maestro.hangout:
//            ContentLoader

private class <init>
    implements android.view.tener
{

    final ContentLoader this$0;

    public void onGlobalLayout()
    {
        if (ContentLoader.mSlidingLayout.isSlideable() && !ContentLoader.mSlidingLayout.isOpen())
        {
            ContentLoader.access$1(ContentLoader.this);
        } else
        {
            ContentLoader.access$0(ContentLoader.this);
        }
        if (android.os.Listener.this._fld0 >= 16)
        {
            ContentLoader.mSlidingLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            return;
        } else
        {
            ContentLoader.mSlidingLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            return;
        }
    }

    private ()
    {
        this$0 = ContentLoader.this;
        super();
    }

    this._cls0(this._cls0 _pcls0)
    {
        this();
    }
}
