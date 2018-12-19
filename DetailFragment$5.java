// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.maestro.database.dto.NotificationRecipientsDto;
import com.maestro.database.sqLite.SQLiteSyncDAO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

class this._cls0
    implements android.view.ner
{

    final DetailFragment this$0;

    public void onClick(View view)
    {
        Object obj;
        int i;
        i = view.getId();
        obj = "";
        OutputStreamWriter outputstreamwriter;
        Object obj2;
        obj2 = (new URL("http://billing.maestrocomms.com/task/android/add_seen_status.jsp")).openConnection();
        Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_seen_status.jsp?login_omsid=")).append(activeUserId).append("&bnmid=").append(i).toString());
        ((URLConnection) (obj2)).setDoOutput(true);
        outputstreamwriter = new OutputStreamWriter(((URLConnection) (obj2)).getOutputStream());
        outputstreamwriter.write((new StringBuilder("login_omsid=")).append(activeUserId).append("&bnmid=").append(i).toString());
        outputstreamwriter.flush();
        outputstreamwriter.close();
        obj2 = new BufferedReader(new InputStreamReader(((URLConnection) (obj2)).getInputStream()));
_L1:
        String s = ((BufferedReader) (obj2)).readLine();
        if (s != null)
        {
            break MISSING_BLOCK_LABEL_309;
        }
        obj = ((String) (obj)).trim();
        outputstreamwriter.close();
        ((BufferedReader) (obj2)).close();
        if (((String) (obj)).equals("successfull"))
        {
            ((ImageView)view).setImageResource(0x7f02001d);
            Object obj1 = new NotificationRecipientsDto();
            ((NotificationRecipientsDto) (obj1)).setNotifId(i);
            ((NotificationRecipientsDto) (obj1)).setSeen("Y");
            obj = new SQLiteSyncDAO(getActivity().getBaseContext());
            ((SQLiteSyncDAO) (obj)).update(((NotificationRecipientsDto) (obj1)), activeUserId);
            view = (TextView)view.getTag();
            obj1 = ((SQLiteSyncDAO) (obj)).getNotificationSeenBy(i);
            ((SQLiteSyncDAO) (obj)).close();
            obj = new SpannableString((new StringBuilder("seen by: ")).append(((String) (obj1))).toString());
            ((SpannableString) (obj)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj)).length(), 0);
            view.setText(((CharSequence) (obj)));
            return;
        }
        break MISSING_BLOCK_LABEL_332;
        obj = (new StringBuilder(String.valueOf(obj))).append(s).toString();
          goto _L1
        try
        {
            Toast.makeText(getActivity().getBaseContext(), "Could not update seen status", 0).show();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Toast.makeText(getActivity().getBaseContext(), "Could not update seen status", 0).show();
        }
        return;
    }

    cDAO()
    {
        this$0 = DetailFragment.this;
        super();
    }
}
