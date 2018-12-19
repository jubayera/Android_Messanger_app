// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.maestro.database.dto.EventSummaryDto;
import com.maestro.database.dto.LoginDTO;
import com.maestro.database.dto.SnoozeActivityDto;
import com.maestro.database.dto.TaskNotificationDto;
import com.maestro.database.sqLite.SQLiteSyncDAO;
import com.maestro.hangout.controllers.SwipeDismissListViewTouchListener;
import com.maestro.hangout.controllers.UndoBarController;
import com.maestro.hangout.models.RowItem;
import com.maestro.hangout.models.UndoItem;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.maestro.hangout:
//            CustomListViewAdapter, Globals, DetailFragment, ContentLoader

public class MyListFragment extends ListFragment
    implements com.maestro.hangout.controllers.UndoBarController.UndoListener
{
    public static interface ListFragmentItemClickListener
    {

        public abstract void onListFragmentItemClick(View view, int i);
    }

    private class StableArrayAdapter extends ArrayAdapter
    {

        HashMap mIdMap;
        final MyListFragment this$0;

        public long getItemId(int i)
        {
            String s = (String)getItem(i);
            return (long)((Integer)mIdMap.get(s)).intValue();
        }

        public boolean hasStableIds()
        {
            return true;
        }

        public StableArrayAdapter(Context context, int i, List list)
        {
            this$0 = MyListFragment.this;
            super(context, i, list);
            mIdMap = new HashMap();
            i = 0;
            do
            {
                if (i >= list.size())
                {
                    return;
                }
                mIdMap.put((String)list.get(i), Integer.valueOf(i));
                i++;
            } while (true);
        }
    }

    private class SummaryItem
    {

        private String appr_req;
        private String comp;
        private long dept_id;
        private String heading;
        private String high;
        private String op;
        private String pending;
        private long role_id;
        private String st_new;
        final MyListFragment this$0;
        private String unseen;

        public String getAppr_req()
        {
            return appr_req;
        }

        public String getComp()
        {
            return comp;
        }

        public long getDept_id()
        {
            return dept_id;
        }

        public String getHeading()
        {
            return heading;
        }

        public String getHigh()
        {
            return high;
        }

        public String getOp()
        {
            return op;
        }

        public String getPending()
        {
            return pending;
        }

        public long getRole_id()
        {
            return role_id;
        }

        public String getSt_new()
        {
            return st_new;
        }

        public String getUnseen()
        {
            return unseen;
        }

        public void setAppr_req(String s)
        {
            appr_req = s;
        }

        public void setComp(String s)
        {
            comp = s;
        }

        public void setDept_id(long l)
        {
            dept_id = l;
        }

        public void setHeading(String s)
        {
            heading = s;
        }

        public void setHigh(String s)
        {
            high = s;
        }

        public void setOp(String s)
        {
            op = s;
        }

        public void setPending(String s)
        {
            pending = s;
        }

        public void setRole_id(long l)
        {
            role_id = l;
        }

        public void setSt_new(String s)
        {
            st_new = s;
        }

        public void setUnseen(String s)
        {
            unseen = s;
        }

        public String toString()
        {
            return (new StringBuilder(String.valueOf(heading))).append("\n").append(high).toString();
        }

        public SummaryItem(String s, String s1, String s2, String s3, String s4, String s5, 
                String s6, String s7, long l, long l1)
        {
            this$0 = MyListFragment.this;
            super();
            heading = s;
            high = s1;
            st_new = s2;
            op = s3;
            comp = s4;
            unseen = s6;
            appr_req = s7;
            role_id = l;
            dept_id = l1;
            pending = s5;
        }
    }

    public class SummaryListViewAdapter extends ArrayAdapter
    {

        Context context;
        private HashMap mIdMap;
        final MyListFragment this$0;

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            SummaryItem summaryitem = (SummaryItem)getItem(i);
            viewgroup = (LayoutInflater)context.getSystemService("layout_inflater");
            String as[];
            if (view == null)
            {
                view = viewgroup.inflate(0x7f03000b, null);
                viewgroup = new ViewSummaryHolder(null);
                viewgroup.heading = (TextView)view.findViewById(0x7f0a0034);
                viewgroup.high = (TextView)view.findViewById(0x7f0a0035);
                viewgroup.st_new = (TextView)view.findViewById(0x7f0a0036);
                viewgroup.pending = (TextView)view.findViewById(0x7f0a0039);
                viewgroup.op = (TextView)view.findViewById(0x7f0a0037);
                viewgroup.unseen = (TextView)view.findViewById(0x7f0a0017);
                viewgroup.appr_req = (TextView)view.findViewById(0x7f0a003a);
                viewgroup.comp = (TextView)view.findViewById(0x7f0a0038);
                view.setTag(viewgroup);
            } else
            {
                viewgroup = (ViewSummaryHolder)view.getTag();
            }
            ((ViewSummaryHolder) (viewgroup)).heading.setText(summaryitem.getHeading());
            ((ViewSummaryHolder) (viewgroup)).high.setText(summaryitem.getHigh());
            ((ViewSummaryHolder) (viewgroup)).st_new.setText(summaryitem.getSt_new());
            ((ViewSummaryHolder) (viewgroup)).pending.setText(summaryitem.getPending());
            ((ViewSummaryHolder) (viewgroup)).op.setText(summaryitem.getOp());
            ((ViewSummaryHolder) (viewgroup)).unseen.setText(summaryitem.getUnseen());
            ((ViewSummaryHolder) (viewgroup)).appr_req.setText(summaryitem.getAppr_req());
            ((ViewSummaryHolder) (viewgroup)).comp.setText(summaryitem.getComp());
            as = new String[4];
            if (summaryitem.getRole_id() > 0L)
            {
                as[0] = (new StringBuilder(String.valueOf(summaryitem.getRole_id()))).toString();
                as[1] = "role";
                as[2] = summaryitem.getHeading();
            } else
            if (summaryitem.getDept_id() > 0L)
            {
                as[0] = (new StringBuilder(String.valueOf(summaryitem.getDept_id()))).toString();
                as[1] = "dept";
                as[2] = summaryitem.getHeading();
            } else
            {
                as[2] = summaryitem.getHeading();
            }
            ((ViewSummaryHolder) (viewgroup)).heading.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).heading.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    view = (String[])view.getTag();
                    DetailFragment detailfragment = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    detailfragment.showTypeWiseEventDetails(view[0], view[1], view[2], null);
                    ContentLoader.mSlidingLayout.closePane();
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).high.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).high.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).st_new.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).st_new.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).op.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).op.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).comp.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).comp.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).pending.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).pending.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).appr_req.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).appr_req.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).unseen.setTag(as);
            ((ViewSummaryHolder) (viewgroup)).unseen.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    String as[];
                    as = (String[])view.getTag();
                    view = (String)((TextView)view).getText();
                    if (view == null || view.length() <= 0) goto _L2; else goto _L1
_L1:
                    if (!view.substring(view.length() - 1).equals("H")) goto _L4; else goto _L3
_L3:
                    as[3] = "high";
_L6:
                    view = (DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002);
                    Globals.current_report = 4;
                    view.showTypeWiseEventDetails(as[0], as[1], as[2], as[3]);
                    ContentLoader.mSlidingLayout.closePane();
_L2:
                    return;
_L4:
                    if (view.substring(view.length() - 1).equals("N"))
                    {
                        as[3] = "new";
                    } else
                    if (view.substring(view.length() - 1).equals("O"))
                    {
                        as[3] = "op";
                    } else
                    if (view.substring(view.length() - 1).equals("C"))
                    {
                        as[3] = "comp";
                    } else
                    if (view.substring(view.length() - 1).equals("P"))
                    {
                        as[3] = "pending";
                    } else
                    if (view.substring(view.length() - 1).equals("A"))
                    {
                        as[3] = "appr";
                    } else
                    if (view.substring(view.length() - 1).equals("U"))
                    {
                        as[3] = "unseen";
                    }
                    if (true) goto _L6; else goto _L5
_L5:
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            return view;
        }


        public SummaryListViewAdapter(Context context1, int i, List list)
        {
            this$0 = MyListFragment.this;
            super(context1, i, list);
            mIdMap = new HashMap();
            context = context1;
        }
    }

    private class SummaryListViewAdapter.ViewSummaryHolder
    {

        TextView appr_req;
        TextView comp;
        TextView heading;
        TextView high;
        TextView op;
        TextView pending;
        TextView st_new;
        final SummaryListViewAdapter this$1;
        TextView unseen;

        private SummaryListViewAdapter.ViewSummaryHolder()
        {
            this$1 = SummaryListViewAdapter.this;
            super();
        }

        SummaryListViewAdapter.ViewSummaryHolder(SummaryListViewAdapter.ViewSummaryHolder viewsummaryholder)
        {
            this();
        }
    }


    public static String event_ids[] = {
        "Indian Rupee", "Pakistani Rupee", "Sri Lankan Rupee", "Renminbi", "Bangladeshi Taka", "Nepalese Rupee", "Afghani", "North Korean Won", "South Korean Won", "Japanese Yen"
    };
    public static String event_priority[] = null;
    public static String event_status[] = null;
    public static CustomListViewAdapter mAdapter;
    public static String notification_id[] = null;
    public static String titles[] = {
        "My Task 1", "My Task 2", "My Task 3", "My Task 4", "My Task 5", "My Task 6", "My Task 7", "My Task 8", "My Task 9", "My Task 10"
    };
    ListFragmentItemClickListener iItemClickListener;
    int images[] = {
        0x7f020057, 0x7f020056, 0x7f020009, 0x7f020009, 0x7f020009, 0x7f020009, 0x7f020009, 0x7f020009, 0x7f020009, 0x7f020009
    };
    com.maestro.hangout.controllers.SwipeDismissListViewTouchListener.DismissCallbacks mCallback;
    private ListView mListView;
    private ListView mListView_static;
    private SwipeDismissListViewTouchListener mOnTouchListener;
    private UndoBarController mUndoBarController;
    List rowItems;

    public MyListFragment()
    {
        mCallback = new com.maestro.hangout.controllers.SwipeDismissListViewTouchListener.DismissCallbacks() {

            final MyListFragment this$0;

            public boolean canDismiss(int i)
            {
                return i <= MyListFragment.mAdapter.getCount() - 1;
            }

            public void onApproval(ListView listview, int i)
            {
                listview = (RowItem)MyListFragment.mAdapter.getItem(i);
                MyListFragment.mAdapter.remove(listview);
                MyListFragment.mAdapter.notifyDataSetChanged();
                listview.setImageId(0x7f02000a);
                MyListFragment.mAdapter.insert(listview, i);
                Log.e("approved", "approved");
                MyListFragment.mAdapter.notifyDataSetChanged();
            }

            public void onDismiss(ListView listview, int ai[])
            {
                Object obj;
                int ai1[];
                int j;
                obj = new RowItem[ai.length];
                ai1 = new int[ai.length];
                j = 0;
                if (mListView == null) goto _L2; else goto _L1
_L1:
                int i;
                int k;
                k = ai.length;
                i = 0;
_L6:
                if (i < k) goto _L4; else goto _L3
_L3:
                MyListFragment.mAdapter.notifyDataSetChanged();
                listview = new UndoItem(((RowItem []) (obj)), ai1);
                obj = getResources().getQuantityString(0x7f070000, ai.length, new Object[] {
                    Integer.valueOf(ai.length)
                });
                Log.e("undo message", (new StringBuilder(String.valueOf(obj))).append(ai.length).toString());
                mUndoBarController.showUndoBar(false, ((CharSequence) (obj)), listview);
_L2:
                return;
_L4:
                RowItem rowitem;
                SQLiteSyncDAO sqlitesyncdao;
                Object obj1;
                int l;
                l = ai[i];
                rowitem = (RowItem)MyListFragment.mAdapter.getItem(l);
                MyListFragment.mAdapter.remove(rowitem);
                Log.e("archived>>>>", MyListFragment.titles[l]);
                obj[j] = rowitem;
                ai1[j] = l;
                j++;
                sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
                obj1 = sqlitesyncdao.selectAllActiveLogin();
                listview = "";
_L7:
label0:
                {
                    for (obj1 = ((List) (obj1)).iterator(); ((Iterator) (obj1)).hasNext();)
                    {
                        break label0;
                    }

                    obj1 = new SnoozeActivityDto();
                    if (rowitem.getIsArchived().equals("N"))
                    {
                        ((SnoozeActivityDto) (obj1)).setPaused("Y");
                    } else
                    {
                        ((SnoozeActivityDto) (obj1)).setPaused("N");
                    }
                    ((SnoozeActivityDto) (obj1)).setPaused_by(Long.parseLong(listview));
                    ((SnoozeActivityDto) (obj1)).setTaskid(Long.parseLong(MyListFragment.notification_id[l]));
                    sqlitesyncdao.insert(((SnoozeActivityDto) (obj1)));
                    listview = new TaskNotificationDto();
                    listview.setId(Long.parseLong(MyListFragment.notification_id[l]));
                    if (rowitem.getIsArchived().equals("N"))
                    {
                        listview.setArchived("Y");
                    } else
                    {
                        listview.setArchived("N");
                    }
                    sqlitesyncdao.update(listview);
                    sqlitesyncdao.close();
                    listview = new ArrayList(Arrays.asList(MyListFragment.titles));
                    listview.remove(l);
                    MyListFragment.titles = new String[listview.size()];
                    listview.toArray(MyListFragment.titles);
                    listview = new ArrayList(Arrays.asList(MyListFragment.notification_id));
                    listview.remove(l);
                    MyListFragment.notification_id = new String[listview.size()];
                    listview.toArray(MyListFragment.notification_id);
                    listview = new ArrayList(Arrays.asList(MyListFragment.event_ids));
                    listview.remove(l);
                    MyListFragment.event_ids = new String[listview.size()];
                    listview.toArray(MyListFragment.event_ids);
                    listview = new ArrayList(Arrays.asList(MyListFragment.event_priority));
                    listview.remove(l);
                    MyListFragment.event_priority = new String[listview.size()];
                    listview.toArray(MyListFragment.event_priority);
                    listview = new ArrayList(Arrays.asList(MyListFragment.event_status));
                    listview.remove(l);
                    MyListFragment.event_status = new String[listview.size()];
                    listview.toArray(MyListFragment.event_status);
                    i++;
                }
                if (true) goto _L6; else goto _L5
_L5:
                listview = (new StringBuilder(String.valueOf(((LoginDTO)((Iterator) (obj1)).next()).getId()))).toString();
                  goto _L7
            }

            
            {
                this$0 = MyListFragment.this;
                super();
            }
        };
    }

    public static Map parse(JSONObject jsonobject, Map map)
        throws JSONException
    {
        Iterator iterator = jsonobject.keys();
_L2:
        String s;
        if (!iterator.hasNext())
        {
            return map;
        }
        s = (String)iterator.next();
        Object obj;
        obj = jsonobject.getJSONObject(s);
        if (((JSONObject) (obj)).getString("type").equals("sub"))
        {
            map.put(s, obj);
            continue; /* Loop/switch isn't completed */
        }
        if (((JSONObject) (obj)).getString("type").equals("id"))
        {
            map.put(s, obj);
            continue; /* Loop/switch isn't completed */
        }
        try
        {
            parse(((JSONObject) (obj)), map);
        }
        // Misplaced declaration of an exception variable
        catch (Object obj)
        {
            obj = jsonobject.getString(s);
            if (obj != null)
            {
                map.put(s, obj);
                System.out.println((new StringBuilder(String.valueOf(s))).append(" , ").append(((String) (obj))).toString());
            }
        }
        if (true) goto _L2; else goto _L1
_L1:
    }

    public String[] getUndoValueAddedArray(ArrayList arraylist, String s, int i)
    {
        String as[];
        int j;
        as = new String[arraylist.size() + 1];
        j = 0;
_L2:
        if (j >= arraylist.size() + 1)
        {
            return as;
        }
        if (j >= i)
        {
            break; /* Loop/switch isn't completed */
        }
        as[j] = (String)arraylist.get(j);
_L3:
        j++;
        if (true) goto _L2; else goto _L1
_L1:
        if (j == i)
        {
            as[j] = s;
        } else
        if (j > i)
        {
            as[j] = (String)arraylist.get(j - 1);
        }
          goto _L3
        if (true) goto _L2; else goto _L4
_L4:
    }

    public void loadNotificationList(String s)
    {
        int ai[];
        String as[];
        int ai1[];
        String as1[];
        int i;
        as1 = null;
        ai1 = null;
        i = 0;
        as = as1;
        ai = ai1;
        SQLiteSyncDAO sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        if (s == null) goto _L2; else goto _L1
_L1:
        as = as1;
        ai = ai1;
        if (!s.equals("archaive")) goto _L2; else goto _L3
_L3:
        as = as1;
        ai = ai1;
        Object obj1 = sqlitesyncdao.selectArchievedNotifications();
_L6:
        as = as1;
        ai = ai1;
        titles = new String[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        event_ids = new String[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        event_status = new String[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        event_priority = new String[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        notification_id = new String[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        ai1 = new int[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        as1 = new String[((List) (obj1)).size()];
        as = as1;
        ai = ai1;
        obj1 = ((List) (obj1)).iterator();
_L7:
        as = as1;
        ai = ai1;
        if (((Iterator) (obj1)).hasNext()) goto _L5; else goto _L4
_L4:
        as = as1;
        ai = ai1;
        sqlitesyncdao.close();
        ai = ai1;
        as = as1;
        break MISSING_BLOCK_LABEL_241;
_L2:
        as = as1;
        ai = ai1;
        obj1 = sqlitesyncdao.selectNotifications(s);
          goto _L6
_L5:
        as = as1;
        ai = ai1;
        tasknotificationdto = (TaskNotificationDto)((Iterator) (obj1)).next();
        as = as1;
        ai = ai1;
        titles[i] = tasknotificationdto.getSummary();
        as = as1;
        ai = ai1;
        event_ids[i] = tasknotificationdto.getEventId();
        as = as1;
        ai = ai1;
        notification_id[i] = (new StringBuilder(String.valueOf(tasknotificationdto.getId()))).toString();
        as = as1;
        ai = ai1;
        if (titles[i].length() <= 80)
        {
            break MISSING_BLOCK_LABEL_579;
        }
        as = as1;
        ai = ai1;
        titles[i] = (new StringBuilder(String.valueOf(titles[i].substring(0, 80)))).append("..").toString();
        as = as1;
        ai = ai1;
        event_status[i] = tasknotificationdto.getStatus();
        as = as1;
        ai = ai1;
        event_priority[i] = tasknotificationdto.getPriority();
        as = as1;
        ai = ai1;
        if (tasknotificationdto.getSeenPersons() == null)
        {
            break MISSING_BLOCK_LABEL_676;
        }
        as = as1;
        ai = ai1;
        if (tasknotificationdto.getSeenPersons().length() <= 0)
        {
            break MISSING_BLOCK_LABEL_676;
        }
        as = as1;
        ai = ai1;
        as1[i] = (new StringBuilder("seen by: ")).append(tasknotificationdto.getSeenPersons()).toString();
        as = as1;
        ai = ai1;
        if ((new StringBuilder()).append(tasknotificationdto.getSeenPersonsIds()).toString().indexOf((new StringBuilder()).append(Globals.getInstance().getOms_id()).toString()) > 0)
        {
            ai1[i] = 0x7f02001d;
        } else
        {
            ai1[i] = 0x7f02001e;
        }
        break MISSING_BLOCK_LABEL_1307;
        exception;
        exception.printStackTrace();
        rowItems = new ArrayList();
        int k = 0;
        do
        {
            Exception exception;
            TaskNotificationDto tasknotificationdto;
            if (k >= titles.length)
            {
                Log.e("loading list", "loading list");
                mAdapter = new CustomListViewAdapter(getActivity().getBaseContext(), 0x7f030007, rowItems);
                setListAdapter(mAdapter);
                mListView = getListView();
                if (mListView != null)
                {
                    mOnTouchListener = new SwipeDismissListViewTouchListener(mListView, mCallback);
                    mListView.setOnTouchListener(mOnTouchListener);
                    mListView.setOnScrollListener(mOnTouchListener.makeScrollListener());
                    getListView().setChoiceMode(3);
                    getListView().setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {

                        final MyListFragment this$0;

                        public boolean onItemLongClick(AdapterView adapterview, View view, int l, long l1)
                        {
                            adapterview = getListView();
                            boolean flag1;
                            if (MyListFragment.mAdapter.isPositionChecked(l))
                            {
                                flag1 = false;
                            } else
                            {
                                flag1 = true;
                            }
                            adapterview.setItemChecked(l, flag1);
                            Log.e("long press", (new StringBuilder()).append(l).toString());
                            return false;
                        }

            
            {
                this$0 = MyListFragment.this;
                super();
            }
                    });
                    if (mUndoBarController == null)
                    {
                        mUndoBarController = new UndoBarController(getActivity().findViewById(0x7f0a0020), this);
                    }
                }
                return;
            }
            int j = 0x7f02004b;
            boolean flag = false;
            Object obj;
            String s1;
            if (event_status[k] != null && event_status[k].equals("Followup"))
            {
                i = 0x7f020049;
            } else
            if (event_status[k] != null && event_status[k].equals("Completed"))
            {
                i = 0x7f02000a;
            } else
            if (event_status[k] != null && event_status[k].equals("Cancelled"))
            {
                i = 0x7f02000e;
            } else
            if (event_status[k] != null && event_status[k].equals("On Process"))
            {
                i = 0x7f02004d;
            } else
            if (event_status[k] != null && event_status[k].equals("New"))
            {
                i = 0x7f02004b;
            } else
            if (event_status[k] != null && event_status[k].equals("On Process"))
            {
                i = 0x7f02004d;
            } else
            if (event_status[k] != null && event_status[k].equals("Pending"))
            {
                i = 0x7f02004e;
            } else
            {
                i = j;
                if (event_status[k] != null)
                {
                    i = j;
                    if (event_status[k].equals("Postpond"))
                    {
                        i = 0x7f020009;
                    }
                }
            }
            if (event_priority[k] != null && event_priority[k].equals("Low"))
            {
                j = 0x7f020030;
            } else
            if (event_priority[k] != null && event_priority[k].equals("Medium"))
            {
                j = 0x7f020032;
            } else
            if (event_priority[k] != null && event_priority[k].equals("High"))
            {
                j = 0x7f020020;
            } else
            if (event_priority[k] != null && event_priority[k].equals("Very High"))
            {
                j = 0x7f020060;
            } else
            {
                j = ((flag) ? 1 : 0);
                if (event_priority[k] != null)
                {
                    j = ((flag) ? 1 : 0);
                    if (event_priority[k].equals("Very Very High"))
                    {
                        j = 0x7f020062;
                    }
                }
            }
            s1 = "N";
            obj = s1;
            if (s != null)
            {
                obj = s1;
                if (s.equals("archaive"))
                {
                    obj = "Y";
                }
            }
            obj = new RowItem(i, titles[k], j, as[k], ((String) (obj)), ai[k]);
            ((RowItem) (obj)).setId(Integer.parseInt(notification_id[k]));
            ((RowItem) (obj)).setEventId(event_ids[k]);
            ((RowItem) (obj)).setStatus(event_status[k]);
            rowItems.add(obj);
            k++;
        } while (true);
        i++;
          goto _L7
    }

    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);
        if (mUndoBarController != null)
        {
            mUndoBarController.onRestoreInstanceState(bundle);
        }
        showEventSummary();
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            iItemClickListener = (ListFragmentItemClickListener)activity;
            return;
        }
        catch (Exception exception)
        {
            Toast.makeText(activity.getBaseContext(), "Exception", 0).show();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuinflater)
    {
        menuinflater.inflate(0x7f090002, menu);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        setHasOptionsMenu(true);
        return layoutinflater.inflate(0x7f030006, viewgroup, false);
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        mAdapter.notifyDataSetChanged();
        iItemClickListener.onListFragmentItemClick(view, i);
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        if (mUndoBarController != null)
        {
            mUndoBarController.onSaveInstanceState(bundle);
        }
    }

    public void onUndo(Parcelable parcelable)
    {
        if (parcelable == null) goto _L2; else goto _L1
_L1:
        int ai[];
        UndoItem undoitem = (UndoItem)parcelable;
        parcelable = undoitem.itemString;
        ai = undoitem.itemPosition;
        if (parcelable == null || ai == null) goto _L2; else goto _L3
_L3:
        int i = parcelable.length - 1;
_L6:
        if (i >= 0) goto _L4; else goto _L2
_L2:
        return;
_L4:
        RowItem rowitem = parcelable[i];
        int j = ai[i];
        SQLiteSyncDAO sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        Object obj = new SnoozeActivityDto();
        if (rowitem.getIsArchived().equals("N"))
        {
            ((SnoozeActivityDto) (obj)).setPaused("N");
        } else
        {
            ((SnoozeActivityDto) (obj)).setPaused("Y");
        }
        ((SnoozeActivityDto) (obj)).setPaused_by(Globals.getInstance().getOms_id());
        ((SnoozeActivityDto) (obj)).setTaskid(rowitem.getId());
        sqlitesyncdao.insert(((SnoozeActivityDto) (obj)));
        obj = new TaskNotificationDto();
        ((TaskNotificationDto) (obj)).setId(rowitem.getId());
        if (rowitem.getIsArchived().equals("N"))
        {
            ((TaskNotificationDto) (obj)).setArchived("N");
        } else
        {
            ((TaskNotificationDto) (obj)).setArchived("Y");
        }
        sqlitesyncdao.update(((TaskNotificationDto) (obj)));
        sqlitesyncdao.close();
        titles = getUndoValueAddedArray(new ArrayList(Arrays.asList(titles)), rowitem.getTitle(), j);
        notification_id = getUndoValueAddedArray(new ArrayList(Arrays.asList(notification_id)), (new StringBuilder(String.valueOf(rowitem.getId()))).toString(), j);
        event_ids = getUndoValueAddedArray(new ArrayList(Arrays.asList(event_ids)), rowitem.getEventId(), j);
        event_priority = getUndoValueAddedArray(new ArrayList(Arrays.asList(event_priority)), (new StringBuilder(String.valueOf(rowitem.getPriority()))).toString(), j);
        event_status = getUndoValueAddedArray(new ArrayList(Arrays.asList(event_status)), rowitem.getStatus(), j);
        mAdapter.insert(rowitem, j);
        mAdapter.notifyDataSetChanged();
        i--;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void showEventSummary()
    {
        ArrayList arraylist;
        Iterator iterator;
        arraylist = new ArrayList();
        SQLiteSyncDAO sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        Object obj = sqlitesyncdao.selectAllActiveLogin();
        long l = 0L;
        obj = ((List) (obj)).iterator();
        do
        {
            if (!((Iterator) (obj)).hasNext())
            {
                iterator = sqlitesyncdao.getEventSummary(l).iterator();
                break MISSING_BLOCK_LABEL_64;
            }
            l = ((LoginDTO)((Iterator) (obj)).next()).getId();
        } while (true);
_L2:
        String s;
        String s1;
        String s2;
        String s3;
        String s4;
        String s5;
        String s6;
        EventSummaryDto eventsummarydto;
        String as[];
        int i;
        if (!iterator.hasNext())
        {
            sqlitesyncdao.close();
            setListAdapter(new SummaryListViewAdapter(getActivity().getBaseContext(), 0x7f030004, arraylist));
            mListView_static = getListView();
            mListView = null;
            if (mUndoBarController == null)
            {
                mUndoBarController = new UndoBarController(getActivity().findViewById(0x7f0a0020), this);
            }
            return;
        }
        eventsummarydto = (EventSummaryDto)iterator.next();
        s1 = "";
        s4 = "";
        s5 = "";
        s6 = "";
        s3 = "";
        s2 = "";
        String s7 = "";
        String s8 = "";
        if (eventsummarydto.getHigh() > 0L)
        {
            s1 = (new StringBuilder(String.valueOf(""))).append(":").append(eventsummarydto.getHigh()).append("H").toString();
        }
        s = s1;
        if (eventsummarydto.getSt_new() > 0L)
        {
            s = (new StringBuilder(String.valueOf(s1))).append(":").append(eventsummarydto.getSt_new()).append("N").toString();
        }
        s1 = s;
        if (eventsummarydto.getSt_op() > 0L)
        {
            s1 = (new StringBuilder(String.valueOf(s))).append(":").append(eventsummarydto.getSt_op()).append("O").toString();
        }
        s = s1;
        if (eventsummarydto.getSt_comp() > 0L)
        {
            s = (new StringBuilder(String.valueOf(s1))).append(":").append(eventsummarydto.getSt_comp()).append("C").toString();
        }
        s1 = s;
        if (eventsummarydto.getSt_pending() > 0L)
        {
            s1 = (new StringBuilder(String.valueOf(s))).append(":").append(eventsummarydto.getSt_pending()).append("P").toString();
        }
        s = s1;
        if (eventsummarydto.getAppr_req() > 0L)
        {
            s = (new StringBuilder(String.valueOf(s1))).append(":").append(eventsummarydto.getAppr_req()).append("A").toString();
        }
        s1 = s;
        if (eventsummarydto.getUnseen() > 0L)
        {
            s1 = (new StringBuilder(String.valueOf(s))).append(":").append(eventsummarydto.getUnseen()).append("U").toString();
        }
        as = s1.split(":");
        i = 1;
        s = s8;
        s1 = s7;
_L3:
label0:
        {
            if (i < as.length)
            {
                break label0;
            }
            arraylist.add(new SummaryItem(eventsummarydto.getName(), s4, s5, s6, s3, s2, s1, s, eventsummarydto.getRole_id(), eventsummarydto.getDept_id()));
        }
        if (true) goto _L2; else goto _L1
_L1:
        if (i == 1)
        {
            s4 = as[i];
        }
        if (i == 2)
        {
            s5 = as[i];
        }
        if (i == 3)
        {
            s6 = as[i];
        }
        if (i == 4)
        {
            s3 = as[i];
        }
        if (i == 5)
        {
            s2 = as[i];
        }
        if (i == 6)
        {
            s1 = as[i];
        }
        if (i == 7)
        {
            s = as[i];
        }
        i++;
          goto _L3
    }



}
