// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

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
//            CustomListViewAdapter, Globals

class this._cls0
    implements android.view.pter._cls1
{

    final CustomListViewAdapter this$0;

    public void onClick(View view)
    {
        Object obj;
        int i;
        i = view.getId();
        obj = "";
        OutputStreamWriter outputstreamwriter;
        Object obj2;
        obj2 = (new URL("http://billing.maestrocomms.com/task/android/add_seen_status.jsp")).openConnection();
        Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_seen_status.jsp?login_omsid=")).append(Globals.getInstance().getOms_id()).append("&bnmid=").append(i).toString());
        ((URLConnection) (obj2)).setDoOutput(true);
        outputstreamwriter = new OutputStreamWriter(((URLConnection) (obj2)).getOutputStream());
        outputstreamwriter.write((new StringBuilder("login_omsid=")).append(Globals.getInstance().getOms_id()).append("&bnmid=").append(i).toString());
        outputstreamwriter.flush();
        outputstreamwriter.close();
        obj2 = new BufferedReader(new InputStreamReader(((URLConnection) (obj2)).getInputStream()));
_L1:
        String s1 = ((BufferedReader) (obj2)).readLine();
        if (s1 != null)
        {
            break MISSING_BLOCK_LABEL_318;
        }
        obj = ((String) (obj)).trim();
        outputstreamwriter.close();
        ((BufferedReader) (obj2)).close();
        if (((String) (obj)).equals("successfull"))
        {
            ((ImageView)view).setImageResource(0x7f02001d);
            obj = new NotificationRecipientsDto();
            ((NotificationRecipientsDto) (obj)).setNotifId(i);
            ((NotificationRecipientsDto) (obj)).setSeen("Y");
            Object obj1 = new SQLiteSyncDAO(view.getContext());
            ((SQLiteSyncDAO) (obj1)).update(((NotificationRecipientsDto) (obj)), Globals.getInstance().getOms_id());
            ((SQLiteSyncDAO) (obj1)).close();
            obj = (TextView)view.getTag();
            obj1 = new SQLiteSyncDAO(view.getContext());
            String s = ((SQLiteSyncDAO) (obj1)).getNotificationSeenBy(i);
            ((SQLiteSyncDAO) (obj1)).close();
            obj1 = new SpannableString((new StringBuilder("seen by: ")).append(s).toString());
            ((SpannableString) (obj1)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj1)).length(), 0);
            ((TextView) (obj)).setText(((CharSequence) (obj1)));
            return;
        }
        break MISSING_BLOCK_LABEL_341;
        obj = (new StringBuilder(String.valueOf(obj))).append(s1).toString();
          goto _L1
        try
        {
            Toast.makeText(view.getContext(), "Could not update seen status", 0).show();
            return;
        }
        catch (Exception exception)
        {
            Toast.makeText(view.getContext(), "Could not update seen status", 0).show();
        }
        return;
    }

    ntsDto()
    {
        this$0 = CustomListViewAdapter.this;
        super();
    }
}
