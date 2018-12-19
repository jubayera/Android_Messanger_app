// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.maestro.database.dto.LoginDTO;
import com.maestro.database.sqLite.SQLiteSyncDAO;
import com.maestro.hangout.dao.SampleDatapopulator;
import com.maestro.hangout.services.MaestroService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.maestro.hangout:
//            Starter, Globals

class this._cls0
    implements android.view.ckListener
{

    final Starter this$0;

    public void onClick(View view)
    {
        SampleDatapopulator sampledatapopulator = new SampleDatapopulator(getApplicationContext());
        if (!etLogin.getText().toString().equals("") || !etPin.getText().toString().equals("")) goto _L2; else goto _L1
_L1:
        Toast.makeText(getApplicationContext(), "UserName and Password can not be empty!", 1).show();
_L6:
        return;
_L2:
        Object obj;
        Object obj1;
        boolean flag;
        if (etLogin.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "UserName can not be empty!", 1).show();
            return;
        }
        if (etPin.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Password can not be empty!", 1).show();
            return;
        }
        flag = false;
        boolean flag1 = false;
        view = Globals.getInstance();
        if (view.getOms_id() != -1L)
        {
            continue; /* Loop/switch isn't completed */
        }
        obj = new SQLiteSyncDAO(getApplicationContext());
        obj1 = ((SQLiteSyncDAO) (obj)).selectAllLogin().iterator();
        flag = flag1;
_L9:
        if (((Iterator) (obj1)).hasNext()) goto _L4; else goto _L3
_L3:
        ((SQLiteSyncDAO) (obj)).close();
        if (flag) goto _L6; else goto _L5
_L5:
        Object obj2;
        obj2 = (new StringBuilder(String.valueOf(System.currentTimeMillis()))).append(android.provider.ecure.getString(getApplicationContext().getContentResolver(), "android_id")).toString();
        view = "";
        obj = view;
        Object obj3 = (new URL("http://billing.maestrocomms.com/task/clientsyncreq.jsp")).openConnection();
        obj = view;
        Log.e("sending request", (new StringBuilder(String.valueOf(((URLConnection) (obj3)).toString()))).append("pin=").append(etPin.getText().toString()).append("&login=").append(etLogin.getText().toString()).append("&appid=").append(((String) (obj2))).append("&type=login&data=").toString());
        obj = view;
        ((URLConnection) (obj3)).setDoOutput(true);
        obj = view;
        obj1 = new OutputStreamWriter(((URLConnection) (obj3)).getOutputStream());
        obj = view;
        ((OutputStreamWriter) (obj1)).write((new StringBuilder("pin=")).append(etPin.getText().toString()).append("&login=").append(etLogin.getText().toString()).append("&appid=").append(((String) (obj2))).append("&type=login&data=").toString());
        obj = view;
        ((OutputStreamWriter) (obj1)).flush();
        obj = view;
        obj2 = new BufferedReader(new InputStreamReader(((URLConnection) (obj3)).getInputStream()));
_L10:
        obj = view;
        obj3 = ((BufferedReader) (obj2)).readLine();
        if (obj3 != null) goto _L8; else goto _L7
_L7:
        obj = view;
        ((OutputStreamWriter) (obj1)).close();
        obj = view;
        boolean flag2;
        try
        {
            ((BufferedReader) (obj2)).close();
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Log.e("sending request", "error", view);
            view = ((View) (obj));
        }
        Log.e("receiving  request", view);
        Log.e("DEBG", view);
        if (sampledatapopulator.login(view))
        {
            Starter.access$0(Starter.this);
            return;
        } else
        {
            Toast.makeText(getApplicationContext(), "Invalid UserName or password!", 0).show();
            return;
        }
_L4:
        obj2 = (LoginDTO)((Iterator) (obj1)).next();
        Log.i("UPDATE LOGIN", "UPDATED STATUS");
        flag2 = flag;
        if (((LoginDTO) (obj2)).getUserName() != null)
        {
            flag2 = flag;
            if (((LoginDTO) (obj2)).getUserName().equals(etLogin.getText().toString()))
            {
                flag2 = flag;
                if (((LoginDTO) (obj2)).getPin() != null)
                {
                    flag2 = flag;
                    if (((LoginDTO) (obj2)).getPin().equals(etPin.getText().toString()))
                    {
                        ((LoginDTO) (obj2)).setActive("1");
                        ((SQLiteSyncDAO) (obj)).update(((LoginDTO) (obj2)));
                        flag2 = true;
                        view.setOms_id(((LoginDTO) (obj2)).getId());
                        view.setLogin(((LoginDTO) (obj2)).getUserName());
                        Starter.access$0(Starter.this);
                        obj2 = new Intent(Starter.this, com/maestro/hangout/services/MaestroService);
                        startService(((Intent) (obj2)));
                    }
                }
            }
        }
        Log.i("UPDATE LOGIN", "UPDATED STATUS");
        flag = flag2;
          goto _L9
_L8:
        obj = view;
        view = (new StringBuilder(String.valueOf(view))).append(((String) (obj3))).toString();
          goto _L10
    }

    aestroService()
    {
        this$0 = Starter.this;
        super();
    }
}
