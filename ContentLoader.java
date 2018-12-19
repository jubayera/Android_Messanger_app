// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.maestro.database.dto.LoginDTO;
import com.maestro.database.sqLite.SQLiteSyncDAO;
import com.maestro.hangout.dao.SampleDatapopulator;
import com.maestro.hangout.services.MaestroService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.maestro.hangout:
//            Starter, Globals, SyncNotifyReceiver, DetailFragment, 
//            MyListFragment, DateDialogFragment, CustomListViewAdapter

public class ContentLoader extends Activity
    implements MyListFragment.ListFragmentItemClickListener, DetailFragment.DateDetailFragmentButtonListener
{
    private class FirstLayoutListener
        implements android.view.ViewTreeObserver.OnGlobalLayoutListener
    {

        final ContentLoader this$0;

        public void onGlobalLayout()
        {
            if (ContentLoader.mSlidingLayout.isSlideable() && !ContentLoader.mSlidingLayout.isOpen())
            {
                panelClosed();
            } else
            {
                panelOpened();
            }
            if (android.os.Build.VERSION.SDK_INT >= 16)
            {
                ContentLoader.mSlidingLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                return;
            } else
            {
                ContentLoader.mSlidingLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                return;
            }
        }

        private FirstLayoutListener()
        {
            this$0 = ContentLoader.this;
            super();
        }

        FirstLayoutListener(FirstLayoutListener firstlayoutlistener)
        {
            this();
        }
    }

    private class SliderListener extends android.support.v4.widget.SlidingPaneLayout.SimplePanelSlideListener
    {

        final ContentLoader this$0;

        public void onPanelClosed(View view)
        {
            panelClosed();
        }

        public void onPanelOpened(View view)
        {
            panelOpened();
        }

        public void onPanelSlide(View view, float f)
        {
        }

        private SliderListener()
        {
            this$0 = ContentLoader.this;
            super();
        }

        SliderListener(SliderListener sliderlistener)
        {
            this();
        }
    }


    public static final String BUNDLE_KEY_DATE_ID = "date_id";
    private static final String DATE_PICKER_TAG = "date_picker_dialog_fragment";
    public static boolean load_notification = false;
    public static SlidingPaneLayout mSlidingLayout;
    Button btnLogin;
    Button btnPopulateDept;
    EditText etLogin;
    EditText etPin;
    private ActionBar mActionBar;
    List selectedRowItems;
    String sort_type;
    TextView tvLogin;

    public ContentLoader()
    {
        btnLogin = null;
        btnPopulateDept = null;
        tvLogin = null;
        etLogin = null;
        etPin = null;
        sort_type = null;
    }

    private void logIn()
    {
        startActivity(new Intent(this, com/maestro/hangout/Starter));
    }

    private void panelClosed()
    {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        getFragmentManager().findFragmentById(0x7f0a0002).setHasOptionsMenu(true);
        getFragmentManager().findFragmentById(0x7f0a0001).setHasOptionsMenu(false);
    }

    private void panelOpened()
    {
        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        if (mSlidingLayout.isSlideable())
        {
            getFragmentManager().findFragmentById(0x7f0a0002).setHasOptionsMenu(false);
            getFragmentManager().findFragmentById(0x7f0a0001).setHasOptionsMenu(true);
            return;
        } else
        {
            getFragmentManager().findFragmentById(0x7f0a0002).setHasOptionsMenu(true);
            getFragmentManager().findFragmentById(0x7f0a0001).setHasOptionsMenu(false);
            return;
        }
    }

    public static Map parse(JSONObject jsonobject, Map map)
        throws JSONException
    {
        Iterator iterator = jsonobject.keys();
        do
        {
            if (!iterator.hasNext())
            {
                return map;
            }
            String s1 = (String)iterator.next();
            String s = null;
            try
            {
                parse(jsonobject.getJSONObject(s1), map);
            }
            catch (Exception exception)
            {
                exception = jsonobject.getString(s1);
            }
            if (s != null)
            {
                map.put(s1, s);
                Log.e("json", (new StringBuilder(String.valueOf(s1))).append(" , ").append(s).toString());
            } else
            {
                map.put("eventid", s1);
                Log.e("json", (new StringBuilder("eventid , ")).append(s1).toString());
            }
        } while (true);
    }

    private static String readUrl(String s)
        throws Exception
    {
        Object obj = null;
        s = new BufferedReader(new InputStreamReader((new URL(s)).openStream()));
        char ac[];
        obj = new StringBuffer();
        ac = new char[1024];
_L1:
        int i = s.read(ac);
        if (i != -1)
        {
            break MISSING_BLOCK_LABEL_68;
        }
        obj = ((StringBuffer) (obj)).toString();
        if (s != null)
        {
            s.close();
        }
        return ((String) (obj));
        ((StringBuffer) (obj)).append(ac, 0, i);
          goto _L1
        obj;
_L3:
        if (s != null)
        {
            s.close();
        }
        throw obj;
        Exception exception;
        exception;
        s = ((String) (obj));
        obj = exception;
        if (true) goto _L3; else goto _L2
_L2:
    }

    public void onBackPressed()
    {
        if (!mSlidingLayout.isOpen())
        {
            mSlidingLayout.openPane();
            return;
        } else
        {
            super.onBackPressed();
            return;
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        Log.e("notified starting ", (new StringBuilder("notified ")).append(Globals.current_report).append(" , ").append(load_notification).toString());
        bundle = new IntentFilter("com.maestro.hangout.ContentLoader");
        registerReceiver(new SyncNotifyReceiver(), bundle);
        startService(new Intent(this, com/maestro/hangout/services/MaestroService));
        if (!(new SampleDatapopulator(getApplicationContext())).isOldUser())
        {
            logIn();
            return;
        }
        setContentView(0x7f030000);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.setThreadPolicy((new android.os.StrictMode.ThreadPolicy.Builder()).permitAll().build());
        }
        long l = getIntent().getLongExtra("date_id", 0L);
        ((DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002)).updateDateId(l);
        mActionBar = getActionBar();
        mSlidingLayout = (SlidingPaneLayout)findViewById(0x7f0a0000);
        bundle = (ListFragment)getFragmentManager().findFragmentById(0x7f0a0001);
        bundle.getListView().setChoiceMode(3);
        bundle.getListView().setMultiChoiceModeListener(new android.widget.AbsListView.MultiChoiceModeListener() {

            private int nr;
            final ContentLoader this$0;

            public boolean onActionItemClicked(ActionMode actionmode, MenuItem menuitem)
            {
                switch (menuitem.getItemId())
                {
                default:
                    return false;

                case 2131361851: 
                    menuitem = MyListFragment.mAdapter.getCurrentCheckedPosition().iterator();
                    Log.e("checked", (new StringBuilder(String.valueOf(MyListFragment.mAdapter.getCurrentCheckedPosition().size()))).toString());
                    int i = 0;
                    do
                    {
                        if (!menuitem.hasNext())
                        {
                            MyListFragment.mAdapter.clearSelection();
                            nr = 0;
                            actionmode.finish();
                            return false;
                        }
                        Log.e("checked", (new StringBuilder()).append(menuitem.next()).toString());
                        i++;
                    } while (true);

                case 2131361854: 
                    nr = 0;
                    actionmode.finish();
                    return false;

                case 2131361852: 
                    nr = 0;
                    actionmode.finish();
                    return false;

                case 2131361853: 
                    nr = 0;
                    actionmode.finish();
                    return false;
                }
            }

            public boolean onCreateActionMode(ActionMode actionmode, Menu menu)
            {
                nr = 0;
                getMenuInflater().inflate(0x7f090000, menu);
                return true;
            }

            public void onDestroyActionMode(ActionMode actionmode)
            {
                MyListFragment.mAdapter.clearSelection();
            }

            public void onItemCheckedStateChanged(ActionMode actionmode, int i, long l1, boolean flag)
            {
                if (flag)
                {
                    nr = nr + 1;
                    MyListFragment.mAdapter.setNewSelection(i, flag);
                } else
                {
                    nr = nr - 1;
                    MyListFragment.mAdapter.removeSelection(i);
                }
                actionmode.setTitle((new StringBuilder(String.valueOf(nr))).append(" selected").toString());
            }

            public boolean onPrepareActionMode(ActionMode actionmode, Menu menu)
            {
                return false;
            }

            
            {
                this$0 = ContentLoader.this;
                super();
                nr = 0;
            }
        });
        mSlidingLayout.setPanelSlideListener(new SliderListener(null));
        mSlidingLayout.openPane();
        mSlidingLayout.getViewTreeObserver().addOnGlobalLayoutListener(new FirstLayoutListener(null));
        try
        {
            Log.e("json length", (new StringBuilder(String.valueOf((new JSONObject("{'events': {\t\t\t'id-1234': \t\t\t\t{'amount': '20',\t\t\t\t 'comments': 'test',               'who': 'a',\t\t\t\t 'history':{'amount': '30','comments': 'test2','who': 'b'}\t\t\t\t},\t\t\t 'id-5678':\t\t\t\t {'amount': '30',\t\t\t\t  'comments': 'test2',\t\t\t\t  'who': 'b',\t\t\t\t  'history':{'amount': '30','comments': 'test2','who': 'b'}\t\t\t\t }\t\t\t}}")).getJSONObject("events").length()))).toString());
            new HashMap();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (Bundle bundle)
        {
            bundle.printStackTrace();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (Bundle bundle)
        {
            bundle.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f090003, menu);
        return true;
    }

    public void onListFragmentItemClick(View view, int i)
    {
        Log.e("list item clicked", (new StringBuilder(String.valueOf(view.getId()))).toString());
        mActionBar.setTitle(MyListFragment.titles[i]);
        SQLiteSyncDAO sqlitesyncdao = new SQLiteSyncDAO(getApplicationContext());
        Object obj = sqlitesyncdao.selectAllActiveLogin();
        String s = "";
        view = "";
        obj = ((List) (obj)).iterator();
        do
        {
            if (!((Iterator) (obj)).hasNext())
            {
                sqlitesyncdao.close();
                ((DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002)).updateUrl((new StringBuilder("http://billing.maestrocomms.com/task/get_event_details_edit_mode.jsp?username=")).append(s).append("&eventid=").append(MyListFragment.event_ids[i]).append("&bnmid=").append(MyListFragment.notification_id[i]).append("&login_omsid=").append(view).toString());
                getFragmentManager().findFragmentById(0x7f0a0001).getView().setRight(400);
                Log.e("slider width", (new StringBuilder(String.valueOf(getFragmentManager().findFragmentById(0x7f0a0002).getView().getWidth()))).append(" ").toString());
                Log.e("list width", (new StringBuilder(String.valueOf(getFragmentManager().findFragmentById(0x7f0a0001).getView().getWidth()))).toString());
                mSlidingLayout.closePane();
                return;
            }
            view = (LoginDTO)((Iterator) (obj)).next();
            s = view.getUserName();
            view = (new StringBuilder(String.valueOf(view.getId()))).toString();
        } while (true);
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        if (menuitem.getItemId() == 0x102002c && !mSlidingLayout.isOpen())
        {
            mSlidingLayout.openPane();
            return true;
        }
        if (menuitem.getItemId() == 0x7f0a003f && !mSlidingLayout.isOpen())
        {
            ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).showEventSummary();
            mSlidingLayout.openPane();
            return true;
        }
        if (menuitem.getItemId() == 0x7f0a0040)
        {
            menuitem = new PopupMenu(this, findViewById(0x7f0a0040));
            menuitem.getMenu().add("Sort by Role");
            menuitem.getMenu().add("Sort by Priority");
            menuitem.getMenu().add("Notifications");
            menuitem.getMenu().add("Event Summary");
            menuitem.getMenu().add("Archieve List");
            menuitem.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {

                final ContentLoader this$0;

                public boolean onMenuItemClick(MenuItem menuitem1)
                {
                    if (menuitem1.getTitle().toString().equals("Notifications"))
                    {
                        Globals.current_report = 1;
                        ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList(null);
                        return true;
                    }
                    if (menuitem1.getTitle().toString().equals("Event Summary"))
                    {
                        Globals.current_report = 2;
                        ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).showEventSummary();
                        return true;
                    }
                    if (menuitem1.getTitle().toString().equals("Archieve List"))
                    {
                        Globals.current_report = 3;
                        ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList("archaive");
                        return true;
                    }
                    sort_type = menuitem1.getTitle().toString();
                    if (!sort_type.equals("Sort by Role")) goto _L2; else goto _L1
_L1:
                    sort_type = "role";
_L4:
                    Globals.current_report = 1;
                    ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList(sort_type);
                    return true;
_L2:
                    if (sort_type.equals("Sort by Priority"))
                    {
                        sort_type = "priority";
                    }
                    if (true) goto _L4; else goto _L3
_L3:
                }

            
            {
                this$0 = ContentLoader.this;
                super();
            }
            });
            menuitem.show();
            return true;
        } else
        {
            return super.onOptionsItemSelected(menuitem);
        }
    }

    protected void onResume()
    {
        Globals globals;
        super.onResume();
        Log.e("notified resuming ", (new StringBuilder("notified ")).append(Globals.current_report).append(" , ").append(load_notification).toString());
        globals = Globals.getInstance();
        if (globals.getOms_id() != -1L) goto _L2; else goto _L1
_L1:
        Iterator iterator = (new SQLiteSyncDAO(getBaseContext())).selectAllActiveLogin().iterator();
_L9:
        if (iterator.hasNext()) goto _L3; else goto _L2
_L2:
        if (Globals.current_report != 7 && Globals.current_report != 1) goto _L5; else goto _L4
_L4:
        Globals.current_report = 1;
        ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).loadNotificationList(null);
        mSlidingLayout.openPane();
        load_notification = false;
_L7:
        return;
_L3:
        LoginDTO logindto = (LoginDTO)iterator.next();
        globals.setOms_id(logindto.getId());
        globals.setLogin(logindto.getUserName());
        continue; /* Loop/switch isn't completed */
_L5:
        if (Globals.current_report == 2)
        {
            ((MyListFragment)getFragmentManager().findFragmentById(0x7f0a0001)).showEventSummary();
            mSlidingLayout.openPane();
            return;
        }
        if (Globals.current_report == 4)
        {
            ((DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002)).showTypeWiseEventDetails(Globals.role_dept_id, Globals.isRoleOrDept, Globals.type, Globals.type_value);
            mSlidingLayout.closePane();
            return;
        }
        if (Globals.current_report == 5)
        {
            ((DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002)).showEventDetails2(Globals.curr_event_id);
            mSlidingLayout.closePane();
            return;
        }
        if (Globals.current_report != 6) goto _L7; else goto _L6
_L6:
        ((DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002)).showEventDetails(Globals.curr_event_id);
        mSlidingLayout.closePane();
        return;
        if (true) goto _L9; else goto _L8
_L8:
    }

    public void onSetDateButtonClicked(Calendar calendar)
    {
        calendar = DateDialogFragment.newInstance(this, 0x7f060003, calendar);
        calendar.setDateDialogFragmentListener(new DateDialogFragment.DateDialogFragmentListener() {

            final ContentLoader this$0;

            public void dateDialogFragmentDateSet(Calendar calendar1)
            {
                ((DetailFragment)getFragmentManager().findFragmentById(0x7f0a0002)).updateDate(calendar1);
            }

            
            {
                this$0 = ContentLoader.this;
                super();
            }
        });
        calendar.show(getFragmentManager(), "date_picker_dialog_fragment");
    }

    public void updateList(String s)
    {
    }



}
