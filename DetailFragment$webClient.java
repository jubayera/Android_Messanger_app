// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.webkit.WebView;
import android.webkit.WebViewClient;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

private class this._cls0 extends WebViewClient
{

    final DetailFragment this$0;

    public boolean shouldOverrideUrlLoading(WebView webview, String s)
    {
        return false;
    }

    private ()
    {
        this$0 = DetailFragment.this;
        super();
    }
}
