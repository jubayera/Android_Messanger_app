// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.maestro.database.dto.NotificationRecipientsDto;
import com.maestro.database.sqLite.SQLiteSyncDAO;
import com.maestro.hangout.models.RowItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

// Referenced classes of package com.maestro.hangout:
//            MyListFragment, Globals

public class CustomListViewAdapter extends ArrayAdapter
{
    private class ViewHolder
    {

        ImageView high;
        ImageView imageView;
        ImageView medium;
        ImageView priority;
        ImageView seenImg;
        final CustomListViewAdapter this$0;
        TextView txtDesc;
        TextView txtSeenBy;
        ImageView vhigh;
        ImageView vvhigh;

        private ViewHolder()
        {
            this$0 = CustomListViewAdapter.this;
            super();
        }

        ViewHolder(ViewHolder viewholder)
        {
            this();
        }
    }


    Context context;
    private HashMap mSelection;

    public CustomListViewAdapter(Context context1, int i, List list)
    {
        super(context1, i, list);
        mSelection = new HashMap();
        context = context1;
    }

    public void clearSelection()
    {
        mSelection = new HashMap();
        MyListFragment.mAdapter.notifyDataSetChanged();
    }

    public Set getCurrentCheckedPosition()
    {
        return mSelection.keySet();
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        RowItem rowitem = (RowItem)getItem(i);
        Object obj = (LayoutInflater)context.getSystemService("layout_inflater");
        if (view == null)
        {
            view = ((LayoutInflater) (obj)).inflate(0x7f030007, null);
            obj = new ViewHolder(null);
            obj.txtDesc = (TextView)view.findViewById(0x7f0a001e);
            obj.priority = (ImageView)view.findViewById(0x7f0a0023);
            obj.txtSeenBy = (TextView)view.findViewById(0x7f0a0025);
            obj.imageView = (ImageView)view.findViewById(0x7f0a001d);
            obj.seenImg = (ImageView)view.findViewById(0x7f0a0024);
            view.setTag(obj);
        } else
        {
            obj = (ViewHolder)view.getTag();
        }
        ((ViewHolder) (obj)).priority.setImageResource(rowitem.getPriority());
        ((ViewHolder) (obj)).txtDesc.setText(rowitem.getTitle());
        ((ViewHolder) (obj)).imageView.setImageResource(rowitem.getImageId());
        ((ViewHolder) (obj)).txtSeenBy.setText(rowitem.getTxtSeenBy());
        ((ViewHolder) (obj)).seenImg.setImageResource(rowitem.getSeenImg());
        ((ViewHolder) (obj)).priority.setTag(rowitem);
        if (rowitem.getIsArchived().equals("Y"))
        {
            ((ViewHolder) (obj)).txtDesc.setTextColor(Color.parseColor("#4D4D4D"));
        }
        if (rowitem.getSeenImg() == 0x7f02001e)
        {
            ((ViewHolder) (obj)).seenImg.setId(rowitem.getId());
            ((ViewHolder) (obj)).seenImg.setTag(((ViewHolder) (obj)).txtSeenBy);
            ((ViewHolder) (obj)).seenImg.setOnClickListener(new android.view.View.OnClickListener() {

                final CustomListViewAdapter this$0;

                public void onClick(View view1)
                {
                    Object obj1;
                    int j;
                    j = view1.getId();
                    obj1 = "";
                    OutputStreamWriter outputstreamwriter;
                    Object obj3;
                    obj3 = (new URL("http://billing.maestrocomms.com/task/android/add_seen_status.jsp")).openConnection();
                    Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_seen_status.jsp?login_omsid=")).append(Globals.getInstance().getOms_id()).append("&bnmid=").append(j).toString());
                    ((URLConnection) (obj3)).setDoOutput(true);
                    outputstreamwriter = new OutputStreamWriter(((URLConnection) (obj3)).getOutputStream());
                    outputstreamwriter.write((new StringBuilder("login_omsid=")).append(Globals.getInstance().getOms_id()).append("&bnmid=").append(j).toString());
                    outputstreamwriter.flush();
                    outputstreamwriter.close();
                    obj3 = new BufferedReader(new InputStreamReader(((URLConnection) (obj3)).getInputStream()));
_L1:
                    String s1 = ((BufferedReader) (obj3)).readLine();
                    if (s1 != null)
                    {
                        break MISSING_BLOCK_LABEL_318;
                    }
                    obj1 = ((String) (obj1)).trim();
                    outputstreamwriter.close();
                    ((BufferedReader) (obj3)).close();
                    if (((String) (obj1)).equals("successfull"))
                    {
                        ((ImageView)view1).setImageResource(0x7f02001d);
                        obj1 = new NotificationRecipientsDto();
                        ((NotificationRecipientsDto) (obj1)).setNotifId(j);
                        ((NotificationRecipientsDto) (obj1)).setSeen("Y");
                        Object obj2 = new SQLiteSyncDAO(view1.getContext());
                        ((SQLiteSyncDAO) (obj2)).update(((NotificationRecipientsDto) (obj1)), Globals.getInstance().getOms_id());
                        ((SQLiteSyncDAO) (obj2)).close();
                        obj1 = (TextView)view1.getTag();
                        obj2 = new SQLiteSyncDAO(view1.getContext());
                        String s = ((SQLiteSyncDAO) (obj2)).getNotificationSeenBy(j);
                        ((SQLiteSyncDAO) (obj2)).close();
                        obj2 = new SpannableString((new StringBuilder("seen by: ")).append(s).toString());
                        ((SpannableString) (obj2)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj2)).length(), 0);
                        ((TextView) (obj1)).setText(((CharSequence) (obj2)));
                        return;
                    }
                    break MISSING_BLOCK_LABEL_341;
                    obj1 = (new StringBuilder(String.valueOf(obj1))).append(s1).toString();
                      goto _L1
                    try
                    {
                        Toast.makeText(view1.getContext(), "Could not update seen status", 0).show();
                        return;
                    }
                    catch (Exception exception)
                    {
                        Toast.makeText(view1.getContext(), "Could not update seen status", 0).show();
                    }
                    return;
                }

            
            {
                this$0 = CustomListViewAdapter.this;
                super();
            }
            });
        }
        view.setBackgroundColor(viewgroup.getResources().getColor(0x106000f));
        if (mSelection.get(Integer.valueOf(i)) != null)
        {
            view.setBackgroundColor(viewgroup.getResources().getColor(0x1060012));
        }
        return view;
    }

    public boolean isPositionChecked(int i)
    {
        Boolean boolean1 = (Boolean)mSelection.get(Integer.valueOf(i));
        if (boolean1 == null)
        {
            return false;
        } else
        {
            return boolean1.booleanValue();
        }
    }

    public void removeSelection(int i)
    {
        mSelection.remove(Integer.valueOf(i));
        notifyDataSetChanged();
    }

    public void setNewSelection(int i, boolean flag)
    {
        mSelection.put(Integer.valueOf(i), Boolean.valueOf(flag));
        notifyDataSetChanged();
    }
}
