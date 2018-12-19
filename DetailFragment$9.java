// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;

// Referenced classes of package com.maestro.hangout:
//            DetailFragment

class val.eventId
    implements android.view.ner
{

    final DetailFragment this$0;
    private final long val$eventId;

    public void onClick(View view)
    {
        Object obj;
        Object obj1;
        Object obj2;
        int i;
        obj2 = DetailFragment.access$5(DetailFragment.this).keys();
        obj = "";
        obj1 = "";
        i = 0;
_L51:
        if (((Enumeration) (obj2)).hasMoreElements()) goto _L2; else goto _L1
_L1:
        EditText edittext;
        edittext = (EditText)getActivity().findViewById(commentsEditTextId);
        view = (RadioGroup)getActivity().findViewById(regardingRadioGpId);
        view = (RadioGroup)getActivity().findViewById(actionRadioGpId);
        view = null;
        i = 0;
_L24:
        if (i < DetailFragment.access$7(DetailFragment.this).length) goto _L4; else goto _L3
_L3:
        Object obj3;
        obj3 = null;
        obj2 = null;
        i = 0;
_L25:
        if (i < DetailFragment.access$8(DetailFragment.this).length) goto _L6; else goto _L5
_L5:
        if (view == null || view.equalsIgnoreCase("null") || obj3 == null || ((String) (obj3)).equalsIgnoreCase("null")) goto _L8; else goto _L7
_L7:
        Toast.makeText(getActivity().getBaseContext(), "You can not select both Regarding and Action. Please select one", 1).show();
        i = 0;
_L26:
        if (i < DetailFragment.access$7(DetailFragment.this).length) goto _L10; else goto _L9
_L9:
        i = 0;
_L27:
        if (i < DetailFragment.access$8(DetailFragment.this).length) goto _L12; else goto _L11
_L11:
        if (((String) (obj)).length() <= 0 || edittext.getText().length() <= 0) goto _L14; else goto _L13
_L13:
        view = "";
        obj2 = (new URL("http://billing.maestrocomms.com/task/android/gen_user_specific_notifications.jsp")).openConnection();
        Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/gen_user_specific_notifications.jsp?user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&for_user_ids=").append(((String) (obj))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        ((URLConnection) (obj2)).setDoOutput(true);
        obj1 = new OutputStreamWriter(((URLConnection) (obj2)).getOutputStream());
        ((OutputStreamWriter) (obj1)).write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&for_user_ids=").append(((String) (obj))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        ((OutputStreamWriter) (obj1)).flush();
        ((OutputStreamWriter) (obj1)).close();
        obj2 = new BufferedReader(new InputStreamReader(((URLConnection) (obj2)).getInputStream()));
_L46:
        obj3 = ((BufferedReader) (obj2)).readLine();
        if (obj3 != null) goto _L16; else goto _L15
_L15:
        ((OutputStreamWriter) (obj1)).close();
        ((BufferedReader) (obj2)).close();
        obj1 = view;
        if (view == null)
        {
            break MISSING_BLOCK_LABEL_473;
        }
        obj1 = view.trim();
        Log.e("--- response comments save -->", ((String) (obj1)));
        if (!((String) (obj1)).equals("noti gen successfull")) goto _L18; else goto _L17
_L17:
        Toast.makeText(getActivity().getBaseContext(), "Notification(s) generated successfully", 0).show();
        i = 0;
_L47:
        if (i < DetailFragment.access$9(DetailFragment.this).length) goto _L20; else goto _L19
_L19:
        edittext.setText("");
_L14:
        if (((String) (obj)).length() != 0 || edittext.getText().length() <= 0)
        {
            break MISSING_BLOCK_LABEL_2243;
        }
        view = "";
        obj2 = (new URL("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp")).openConnection();
        Log.e("sending comments only...", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp?user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&for_user_ids=").append(((String) (obj))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        ((URLConnection) (obj2)).setDoOutput(true);
        obj1 = new OutputStreamWriter(((URLConnection) (obj2)).getOutputStream());
        ((OutputStreamWriter) (obj1)).write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&for_user_ids=").append(((String) (obj))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        ((OutputStreamWriter) (obj1)).flush();
        ((OutputStreamWriter) (obj1)).close();
        obj = new BufferedReader(new InputStreamReader(((URLConnection) (obj2)).getInputStream()));
_L48:
        obj2 = ((BufferedReader) (obj)).readLine();
        if (obj2 != null) goto _L22; else goto _L21
_L21:
        ((OutputStreamWriter) (obj1)).close();
        ((BufferedReader) (obj)).close();
        obj = view;
        if (view == null)
        {
            break MISSING_BLOCK_LABEL_787;
        }
        obj = view.trim();
        Log.e("--- response comments save -->", ((String) (obj)));
        if (!((String) (obj)).equals("successfull"))
        {
            break MISSING_BLOCK_LABEL_2201;
        }
        Toast.makeText(getActivity().getBaseContext(), "Comments Sent successfully", 0).show();
        i = 0;
_L49:
        if (i >= DetailFragment.access$9(DetailFragment.this).length)
        {
            edittext.setText("");
            return;
        }
          goto _L23
_L2:
        long l = ((Long)((Enumeration) (obj2)).nextElement()).longValue();
        if (((String)DetailFragment.access$5(DetailFragment.this).get(Long.valueOf(l))).equals("1"))
        {
            if (i == 0)
            {
                view = (new StringBuilder(String.valueOf(obj))).append(l).toString();
                obj = (new StringBuilder(String.valueOf(obj1))).append(DetailFragment.access$6(DetailFragment.this).get(Long.valueOf(l))).toString();
            } else
            {
                view = (new StringBuilder(String.valueOf(obj))).append("`").append(l).toString();
                obj = (new StringBuilder(String.valueOf(obj1))).append(",").append(DetailFragment.access$6(DetailFragment.this).get(Long.valueOf(l))).toString();
            }
            i++;
            obj1 = obj;
            obj = view;
        }
        continue; /* Loop/switch isn't completed */
_L4:
        if (DetailFragment.access$7(DetailFragment.this)[i].isChecked())
        {
            view = (new StringBuilder(String.valueOf(DetailFragment.access$7(DetailFragment.this)[i].getId()))).toString();
        }
        i++;
          goto _L24
_L6:
        if (DetailFragment.access$8(DetailFragment.this)[i].isChecked())
        {
            obj3 = (new StringBuilder(String.valueOf(DetailFragment.access$8(DetailFragment.this)[i].getId()))).toString();
            obj2 = DetailFragment.access$8(DetailFragment.this)[i].getText().toString();
        }
        i++;
          goto _L25
_L10:
        DetailFragment.access$7(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L26
_L12:
        DetailFragment.access$8(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L27
_L8:
        if (view == null || view.equalsIgnoreCase("null")) goto _L29; else goto _L28
_L28:
        obj1 = "";
        obj3 = (new URL("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp")).openConnection();
        Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp?user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&for_user_ids=").append(((String) (obj))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        ((URLConnection) (obj3)).setDoOutput(true);
        obj2 = new OutputStreamWriter(((URLConnection) (obj3)).getOutputStream());
        ((OutputStreamWriter) (obj2)).write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&regarding=").append(view).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        ((OutputStreamWriter) (obj2)).flush();
        ((OutputStreamWriter) (obj2)).close();
        obj3 = new BufferedReader(new InputStreamReader(((URLConnection) (obj3)).getInputStream()));
        view = ((View) (obj1));
_L34:
        obj1 = ((BufferedReader) (obj3)).readLine();
        if (obj1 != null) goto _L31; else goto _L30
_L30:
        view = view.trim();
        ((OutputStreamWriter) (obj2)).close();
        ((BufferedReader) (obj3)).close();
        if (!view.equals("successfull"))
        {
            break MISSING_BLOCK_LABEL_1574;
        }
        Toast.makeText(getActivity().getBaseContext(), "Event generated successfully", 0).show();
        i = 0;
_L35:
        if (i < DetailFragment.access$7(DetailFragment.this).length) goto _L33; else goto _L32
_L32:
        edittext.setText("");
          goto _L11
_L31:
        view = (new StringBuilder(String.valueOf(view))).append(((String) (obj1))).toString();
          goto _L34
_L33:
        DetailFragment.access$7(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L35
        try
        {
            Toast.makeText(getActivity().getBaseContext(), "Event could not be sent", 0).show();
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Toast.makeText(getActivity().getBaseContext(), "Notification(s) could not be sent", 0).show();
        }
          goto _L11
_L29:
        if (obj3 == null || ((String) (obj3)).equalsIgnoreCase("null")) goto _L11; else goto _L36
_L36:
        view = "";
        OutputStreamWriter outputstreamwriter;
        URLConnection urlconnection = (new URL("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp")).openConnection();
        Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp?user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&for_user_ids=").append(((String) (obj))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        urlconnection.setDoOutput(true);
        outputstreamwriter = new OutputStreamWriter(urlconnection.getOutputStream());
        outputstreamwriter.write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(val$eventId).append("&action=").append(((String) (obj3))).append("&action_t=").append(((String) (obj2))).append("&action_from=").append(((String) (obj))).append("&action_from_username=").append(((String) (obj1))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
        outputstreamwriter.flush();
        outputstreamwriter.close();
        obj1 = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
_L43:
        obj2 = ((BufferedReader) (obj1)).readLine();
        if (obj2 != null) goto _L38; else goto _L37
_L37:
        outputstreamwriter.close();
        ((BufferedReader) (obj1)).close();
        if (!view.equals("successfull"))
        {
            break MISSING_BLOCK_LABEL_2039;
        }
        Toast.makeText(getActivity().getBaseContext(), "Action inserted successfully", 0).show();
        i = 0;
_L44:
        if (i < DetailFragment.access$8(DetailFragment.this).length) goto _L40; else goto _L39
_L39:
        i = 0;
_L45:
        if (i < DetailFragment.access$9(DetailFragment.this).length) goto _L42; else goto _L41
_L41:
        edittext.setText("");
          goto _L11
_L38:
        view = (new StringBuilder(String.valueOf(view))).append(((String) (obj2))).toString();
          goto _L43
_L40:
        DetailFragment.access$8(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L44
_L42:
        DetailFragment.access$9(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L45
        try
        {
            Toast.makeText(getActivity().getBaseContext(), "Action request could not be sent", 0).show();
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Toast.makeText(getActivity().getBaseContext(), "Notification(s) could not be sent", 0).show();
        }
          goto _L11
_L16:
        view = (new StringBuilder(String.valueOf(view))).append(((String) (obj3))).toString();
          goto _L46
_L20:
        DetailFragment.access$9(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L47
_L18:
        try
        {
            Toast.makeText(getActivity().getBaseContext(), "Notification(s) could not be sent", 0).show();
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Toast.makeText(getActivity().getBaseContext(), "Notification(s) could not be sent", 0).show();
        }
          goto _L14
_L22:
        view = (new StringBuilder(String.valueOf(view))).append(((String) (obj2))).toString();
          goto _L48
_L23:
        DetailFragment.access$9(DetailFragment.this)[i].setChecked(false);
        i++;
          goto _L49
        try
        {
            Toast.makeText(getActivity().getBaseContext(), "Comments Sent successfully", 0).show();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Toast.makeText(getActivity().getBaseContext(), "Comments could not be sent", 0).show();
        }
        return;
        Toast.makeText(getActivity().getBaseContext(), "Please enter comments and select user(s)", 0).show();
        return;
        if (true) goto _L51; else goto _L50
_L50:
    }

    ()
    {
        this$0 = final_detailfragment;
        val$eventId = J.this;
        super();
    }
}
