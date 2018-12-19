// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.Calendar;

public class DateDialogFragment extends DialogFragment
{
    public static interface DateDialogFragmentListener
    {

        public abstract void dateDialogFragmentDateSet(Calendar calendar);
    }


    public static String TAG = "DateDialogFragment";
    static Context sContext;
    static Calendar sDate;
    static DateDialogFragmentListener sListener;
    private android.app.DatePickerDialog.OnDateSetListener dateSetListener;

    public DateDialogFragment()
    {
        dateSetListener = new android.app.DatePickerDialog.OnDateSetListener() {

            final DateDialogFragment this$0;

            public void onDateSet(DatePicker datepicker, int i, int j, int k)
            {
                datepicker = Calendar.getInstance();
                datepicker.set(i, j, k);
                DateDialogFragment.sListener.dateDialogFragmentDateSet(datepicker);
            }

            
            {
                this$0 = DateDialogFragment.this;
                super();
            }
        };
    }

    public static DateDialogFragment newInstance(Context context, int i, Calendar calendar)
    {
        DateDialogFragment datedialogfragment = new DateDialogFragment();
        sContext = context;
        sDate = Calendar.getInstance();
        sDate = calendar;
        context = new Bundle();
        context.putInt("title", i);
        datedialogfragment.setArguments(context);
        return datedialogfragment;
    }

    public Dialog onCreateDialog(Bundle bundle)
    {
        return new DatePickerDialog(sContext, dateSetListener, sDate.get(1), sDate.get(2), sDate.get(5));
    }

    public void setDateDialogFragmentListener(DateDialogFragmentListener datedialogfragmentlistener)
    {
        sListener = datedialogfragmentlistener;
    }

}
