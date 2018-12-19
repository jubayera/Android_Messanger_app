// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.maestro.hangout;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.maestro.database.dto.ActionListDto;
import com.maestro.database.dto.EventRelatedUsersDto;
import com.maestro.database.dto.EventSummaryDto;
import com.maestro.database.dto.EventsDto;
import com.maestro.database.dto.NotificationRecipientsDto;
import com.maestro.database.dto.RoleRegardingDto;
import com.maestro.database.dto.TaskNotificationDto;
import com.maestro.database.sqLite.SQLiteSyncDAO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.maestro.hangout:
//            Globals

public class DetailFragment extends Fragment
{
    public static interface DateDetailFragmentButtonListener
    {

        public abstract void onSetDateButtonClicked(Calendar calendar);
    }

    private class StableArrayAdapter extends ArrayAdapter
    {

        HashMap mIdMap;
        final DetailFragment this$0;

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
            this$0 = DetailFragment.this;
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

        private int add_comment;
        private int add_reg;
        private boolean apprReq;
        private String background_color;
        private long eventid;
        private String foruser;
        private String product;
        private String regarding;
        private String role;
        private String summary;
        final DetailFragment this$0;
        private String unseen;
        private int view_all;

        public int getAdd_comment()
        {
            return add_comment;
        }

        public int getAdd_reg()
        {
            return add_reg;
        }

        public boolean getApprReg()
        {
            return apprReq;
        }

        public String getBackground_color()
        {
            return background_color;
        }

        public long getEventid()
        {
            return eventid;
        }

        public String getForuser()
        {
            return foruser;
        }

        public String getProduct()
        {
            return product;
        }

        public String getRegarding()
        {
            return regarding;
        }

        public String getRole()
        {
            return role;
        }

        public String getSummary()
        {
            return summary;
        }

        public String getUnseen()
        {
            return unseen;
        }

        public int getView_all()
        {
            return view_all;
        }

        public void setAdd_comment(int i)
        {
            add_comment = i;
        }

        public void setAdd_reg(int i)
        {
            add_reg = i;
        }

        public void setApprReq(boolean flag)
        {
            apprReq = flag;
        }

        public void setBackground_color(String s)
        {
            background_color = s;
        }

        public void setEventid(long l)
        {
            eventid = l;
        }

        public void setForuser(String s)
        {
            foruser = s;
        }

        public void setProduct(String s)
        {
            product = s;
        }

        public void setRegarding(String s)
        {
            regarding = s;
        }

        public void setRole(String s)
        {
            role = s;
        }

        public void setSummary(String s)
        {
            summary = s;
        }

        public void setUnseen(String s)
        {
            unseen = s;
        }

        public void setView_all(int i)
        {
            view_all = i;
        }

        public String toString()
        {
            return (new StringBuilder(String.valueOf(role))).append("\n").append(summary).toString();
        }

        public SummaryItem(String s, String s1, String s2, String s3, String s4, String s5, 
                int i, int j, int k, String s6, String s7)
        {
            this$0 = DetailFragment.this;
            super();
            role = s;
            summary = s1;
            regarding = s2;
            product = s3;
            foruser = s4;
            unseen = s5;
            add_comment = i;
            add_reg = j;
            view_all = k;
            background_color = s6;
            if (s7 != null && s7.equals("Y"))
            {
                apprReq = true;
            }
        }
    }

    public class SummaryListViewAdapter extends ArrayAdapter
    {

        Context context;
        private HashMap mIdMap;
        final DetailFragment this$0;

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            SummaryItem summaryitem = (SummaryItem)getItem(i);
            viewgroup = (LayoutInflater)context.getSystemService("layout_inflater");
            if (view == null)
            {
                view = viewgroup.inflate(0x7f030004, null);
                viewgroup = new ViewSummaryHolder(null);
                viewgroup.role = (TextView)view.findViewById(0x7f0a0011);
                viewgroup.summary = (TextView)view.findViewById(0x7f0a0016);
                viewgroup.regarding = (TextView)view.findViewById(0x7f0a0012);
                viewgroup.product = (TextView)view.findViewById(0x7f0a0013);
                viewgroup.foruser = (TextView)view.findViewById(0x7f0a0014);
                viewgroup.unseen = (TextView)view.findViewById(0x7f0a0017);
                viewgroup.add_comment = (ImageView)view.findViewById(0x7f0a0019);
                viewgroup.view_all = (ImageView)view.findViewById(0x7f0a001a);
                ((TableLayout)view.findViewById(0x7f0a000f)).setBackgroundColor(Color.parseColor(summaryitem.getBackground_color()));
                view.setTag(viewgroup);
            } else
            {
                viewgroup = (ViewSummaryHolder)view.getTag();
            }
            ((ViewSummaryHolder) (viewgroup)).role.setText(summaryitem.getRole());
            ((ViewSummaryHolder) (viewgroup)).summary.setText(summaryitem.getSummary());
            ((ViewSummaryHolder) (viewgroup)).regarding.setText(summaryitem.getRegarding());
            ((ViewSummaryHolder) (viewgroup)).product.setText(summaryitem.getProduct());
            ((ViewSummaryHolder) (viewgroup)).foruser.setText(summaryitem.getForuser());
            ((ViewSummaryHolder) (viewgroup)).unseen.setText(summaryitem.getUnseen());
            ((ViewSummaryHolder) (viewgroup)).add_comment.setImageResource(summaryitem.getAdd_comment());
            ((ViewSummaryHolder) (viewgroup)).view_all.setImageResource(summaryitem.getView_all());
            ((ViewSummaryHolder) (viewgroup)).summary.setId((int)summaryitem.getEventid());
            ((ViewSummaryHolder) (viewgroup)).summary.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    showEventDetails(view.getId());
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).view_all.setId((int)summaryitem.getEventid());
            ((ViewSummaryHolder) (viewgroup)).view_all.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    Globals.current_report = 6;
                    showEventDetails(view.getId());
                }

            
            {
                this$1 = SummaryListViewAdapter.this;
                super();
            }
            });
            ((ViewSummaryHolder) (viewgroup)).add_comment.setId((int)summaryitem.getEventid());
            ((ViewSummaryHolder) (viewgroup)).add_comment.setOnClickListener(new android.view.View.OnClickListener() {

                final SummaryListViewAdapter this$1;

                public void onClick(View view)
                {
                    Globals.current_report = 5;
                    showEventDetails2(view.getId());
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
            this$0 = DetailFragment.this;
            super(context1, i, list);
            mIdMap = new HashMap();
            context = context1;
        }
    }

    private class SummaryListViewAdapter.ViewSummaryHolder
    {

        ImageView add_comment;
        TextView foruser;
        TextView product;
        TextView regarding;
        TextView role;
        TextView summary;
        final SummaryListViewAdapter this$1;
        TextView unseen;
        ImageView view_all;

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

    private class webClient extends WebViewClient
    {

        final DetailFragment this$0;

        public boolean shouldOverrideUrlLoading(WebView webview1, String s)
        {
            return false;
        }

        private webClient()
        {
            this$0 = DetailFragment.this;
            super();
        }
    }


    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    public static TextView back = null;
    public static LinearLayout detailContainer = null;
    public static String isRoleOrDept = null;
    public static String role_dept_id = null;
    public static String type = null;
    public static String type_value = null;
    public static WebView webview = null;
    int actionRadioGpId;
    int actionSchDateId;
    private RadioButton actions_radio[];
    long activeUserId;
    int commentsEditTextId;
    Dialog dialog;
    DateDetailFragmentButtonListener mButtonListener;
    Calendar mDate;
    long mDateId;
    private android.app.DatePickerDialog.OnDateSetListener mDateSetListener;
    private int mDay;
    private int mMonth;
    private android.app.TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private int mYear;
    private String message;
    private int mhour;
    private int mminute;
    int regardingRadioGpId;
    private RadioButton regarding_radio[];
    private Hashtable selected_usernames;
    private Hashtable selected_users;
    String todayDate;
    private CheckBox users_checkbox[];

    public DetailFragment()
    {
        commentsEditTextId = 999;
        regardingRadioGpId = 9999;
        actionRadioGpId = 0x1869f;
        actionSchDateId = 0xf423f;
        selected_users = new Hashtable();
        selected_usernames = new Hashtable();
        todayDate = "";
        mDateSetListener = new android.app.DatePickerDialog.OnDateSetListener() {

            final DetailFragment this$0;

            public void onDateSet(DatePicker datepicker, int i, int j, int k)
            {
                mYear = i;
                mMonth = j;
                mDay = k;
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        };
        mTimeSetListener = new android.app.TimePickerDialog.OnTimeSetListener() {

            final DetailFragment this$0;

            public void onTimeSet(TimePicker timepicker, int i, int j)
            {
                mhour = i;
                mminute = j;
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        };
    }

    private void showDateDialog()
    {
        mButtonListener.onSetDateButtonClicked(mDate);
    }

    private void updateUi(Calendar calendar)
    {
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mButtonListener = (DateDetailFragmentButtonListener)activity;
            return;
        }
        catch (ClassCastException classcastexception)
        {
            throw new ClassCastException((new StringBuilder(String.valueOf(activity.toString()))).append(" must implement DateDetailFragmentButtonListener in Activity").toString());
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuinflater)
    {
        menuinflater.inflate(0x7f090001, menu);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        layoutinflater = layoutinflater.inflate(0x7f030003, viewgroup, false);
        detailContainer = (LinearLayout)layoutinflater.findViewById(0x7f0a000b);
        back = (TextView)layoutinflater.findViewById(0x7f0a000a);
        back.setVisibility(4);
        back.setHeight(0);
        setHasOptionsMenu(true);
        webview = (WebView)layoutinflater.findViewById(0x7f0a000d);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        return layoutinflater;
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
    }

    public void setDate(Calendar calendar)
    {
        mDate = calendar;
    }

    public void showEventDetails(long l)
    {
        detailContainer.removeAllViews();
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        Object obj = new TableLayout(getActivity().getBaseContext());
        ((TableLayout) (obj)).setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        ((TableLayout) (obj)).setStretchAllColumns(true);
        ((TableLayout) (obj)).setShrinkAllColumns(true);
        Globals.curr_event_id = l;
        Object obj1 = Globals.getInstance();
        obj = ((Globals) (obj1)).getLogin();
        obj1 = (new StringBuilder()).append(((Globals) (obj1)).getOms_id()).toString();
        back.setVisibility(0);
        back.setHeight(40);
        back.setPadding(2, 2, 2, 2);
        back.setGravity(5);
        back.setTextSize(18F);
        back.setTextColor(0xff0000ff);
        SpannableString spannablestring = new SpannableString("<Back");
        spannablestring.setSpan(new UnderlineSpan(), 0, spannablestring.length(), 0);
        back.setText(spannablestring);
        back.setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                showTypeWiseEventDetails(DetailFragment.role_dept_id, DetailFragment.isRoleOrDept, DetailFragment.type, DetailFragment.type_value);
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-2, -2));
        webview.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl((new StringBuilder("http://billing.maestrocomms.com/task/get_event_details_edit_mode.jsp?username=")).append(((String) (obj))).append("&eventid=").append(l).append("&login_omsid=").append(((String) (obj1))).toString());
    }

    public void showEventDetails2(final long eventId)
    {
        LinearLayout linearlayout;
        TableLayout tablelayout;
        SQLiteSyncDAO sqlitesyncdao;
        Iterator iterator;
        long l;
        Globals globals = Globals.getInstance();
        globals.setEventId2(eventId);
        Globals.curr_event_id = eventId;
        l = 0L;
        detailContainer.removeAllViews();
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        Object obj1 = new ScrollView(getActivity().getBaseContext());
        linearlayout = new LinearLayout(getActivity().getBaseContext());
        linearlayout.setOrientation(1);
        ((ScrollView) (obj1)).addView(linearlayout);
        detailContainer.addView(((View) (obj1)));
        tablelayout = new TableLayout(getActivity().getBaseContext());
        tablelayout.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        tablelayout.setStretchAllColumns(true);
        tablelayout.setShrinkAllColumns(true);
        back.setVisibility(0);
        back.setHeight(40);
        back.setPadding(2, 2, 2, 2);
        back.setGravity(5);
        back.setTextSize(18F);
        back.setTextColor(0xff0000ff);
        obj1 = new SpannableString("<Back");
        ((SpannableString) (obj1)).setSpan(new UnderlineSpan(), 0, ((SpannableString) (obj1)).length(), 0);
        back.setText(((CharSequence) (obj1)));
        back.setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                showTypeWiseEventDetails(DetailFragment.role_dept_id, DetailFragment.isRoleOrDept, DetailFragment.type, DetailFragment.type_value);
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        obj1 = sqlitesyncdao.getEventDetails(eventId);
        activeUserId = globals.getOms_id();
        iterator = ((List) (obj1)).iterator();
_L17:
        if (iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Object obj;
        Object obj2;
        Object obj3;
        int i;
        obj2 = new TableRow(getActivity().getBaseContext());
        tablelayout.addView(((View) (obj2)));
        obj = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj)).setOrientation(0);
        ((LinearLayout) (obj)).setBackgroundColor(Color.parseColor("#E6E6E6"));
        ((TableRow) (obj2)).addView(((View) (obj)));
        obj2 = new TextView(getActivity().getBaseContext());
        ((TextView) (obj2)).setText("Add Actions:");
        ((TextView) (obj2)).setTextColor(0xff000000);
        ((LinearLayout) (obj)).addView(((View) (obj2)));
        obj2 = new TableRow(getActivity().getBaseContext());
        tablelayout.addView(((View) (obj2)));
        obj = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj)).setOrientation(0);
        ((LinearLayout) (obj)).setBackgroundColor(Color.parseColor("#E6E6E6"));
        ((TableRow) (obj2)).addView(((View) (obj)));
        obj2 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj2)).setOrientation(1);
        ((LinearLayout) (obj)).addView(((View) (obj2)));
        obj3 = new EditText(getActivity().getBaseContext());
        ((EditText) (obj3)).setHorizontallyScrolling(false);
        ((EditText) (obj3)).setBackgroundColor(Color.parseColor("#FFFFFF"));
        ((EditText) (obj3)).setTextColor(0xff000000);
        ((EditText) (obj3)).setMaxLines(100);
        ((EditText) (obj3)).setLines(4);
        ((EditText) (obj3)).requestFocus();
        ((EditText) (obj3)).setId(commentsEditTextId);
        ((EditText) (obj3)).setGravity(48);
        ((EditText) (obj3)).setWidth(300);
        ((EditText) (obj3)).setPadding(0, 0, 10, 0);
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
        obj2 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj2)).setOrientation(1);
        ((LinearLayout) (obj)).addView(((View) (obj2)));
        obj3 = sqlitesyncdao.getEventRelatedUsers(eventId);
        users_checkbox = new CheckBox[((List) (obj3)).size()];
        i = 0;
        obj3 = ((List) (obj3)).iterator();
_L19:
        if (((Iterator) (obj3)).hasNext()) goto _L4; else goto _L3
_L3:
        Object obj4;
        obj2 = new Button(getActivity().getBaseContext());
        obj3 = new SpannableString("Save");
        ((SpannableString) (obj3)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj3)).length(), 0);
        ((Button) (obj2)).setText(((CharSequence) (obj3)));
        ((Button) (obj2)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;
            private final long val$eventId;

            public void onClick(View view)
            {
                Object obj6;
                Object obj7;
                Object obj8;
                int k;
                obj8 = selected_users.keys();
                obj6 = "";
                obj7 = "";
                k = 0;
_L51:
                if (((Enumeration) (obj8)).hasMoreElements()) goto _L2; else goto _L1
_L1:
                EditText edittext;
                edittext = (EditText)getActivity().findViewById(commentsEditTextId);
                view = (RadioGroup)getActivity().findViewById(regardingRadioGpId);
                view = (RadioGroup)getActivity().findViewById(actionRadioGpId);
                view = null;
                k = 0;
_L24:
                if (k < regarding_radio.length) goto _L4; else goto _L3
_L3:
                Object obj9;
                obj9 = null;
                obj8 = null;
                k = 0;
_L25:
                if (k < actions_radio.length) goto _L6; else goto _L5
_L5:
                if (view == null || view.equalsIgnoreCase("null") || obj9 == null || ((String) (obj9)).equalsIgnoreCase("null")) goto _L8; else goto _L7
_L7:
                Toast.makeText(getActivity().getBaseContext(), "You can not select both Regarding and Action. Please select one", 1).show();
                k = 0;
_L26:
                if (k < regarding_radio.length) goto _L10; else goto _L9
_L9:
                k = 0;
_L27:
                if (k < actions_radio.length) goto _L12; else goto _L11
_L11:
                if (((String) (obj6)).length() <= 0 || edittext.getText().length() <= 0) goto _L14; else goto _L13
_L13:
                view = "";
                obj8 = (new URL("http://billing.maestrocomms.com/task/android/gen_user_specific_notifications.jsp")).openConnection();
                Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/gen_user_specific_notifications.jsp?user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&for_user_ids=").append(((String) (obj6))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                ((URLConnection) (obj8)).setDoOutput(true);
                obj7 = new OutputStreamWriter(((URLConnection) (obj8)).getOutputStream());
                ((OutputStreamWriter) (obj7)).write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&for_user_ids=").append(((String) (obj6))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                ((OutputStreamWriter) (obj7)).flush();
                ((OutputStreamWriter) (obj7)).close();
                obj8 = new BufferedReader(new InputStreamReader(((URLConnection) (obj8)).getInputStream()));
_L46:
                obj9 = ((BufferedReader) (obj8)).readLine();
                if (obj9 != null) goto _L16; else goto _L15
_L15:
                ((OutputStreamWriter) (obj7)).close();
                ((BufferedReader) (obj8)).close();
                obj7 = view;
                if (view == null)
                {
                    break MISSING_BLOCK_LABEL_473;
                }
                obj7 = view.trim();
                Log.e("--- response comments save -->", ((String) (obj7)));
                if (!((String) (obj7)).equals("noti gen successfull")) goto _L18; else goto _L17
_L17:
                Toast.makeText(getActivity().getBaseContext(), "Notification(s) generated successfully", 0).show();
                k = 0;
_L47:
                if (k < users_checkbox.length) goto _L20; else goto _L19
_L19:
                edittext.setText("");
_L14:
                if (((String) (obj6)).length() != 0 || edittext.getText().length() <= 0)
                {
                    break MISSING_BLOCK_LABEL_2243;
                }
                view = "";
                obj8 = (new URL("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp")).openConnection();
                Log.e("sending comments only...", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp?user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&for_user_ids=").append(((String) (obj6))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                ((URLConnection) (obj8)).setDoOutput(true);
                obj7 = new OutputStreamWriter(((URLConnection) (obj8)).getOutputStream());
                ((OutputStreamWriter) (obj7)).write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&for_user_ids=").append(((String) (obj6))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                ((OutputStreamWriter) (obj7)).flush();
                ((OutputStreamWriter) (obj7)).close();
                obj6 = new BufferedReader(new InputStreamReader(((URLConnection) (obj8)).getInputStream()));
_L48:
                obj8 = ((BufferedReader) (obj6)).readLine();
                if (obj8 != null) goto _L22; else goto _L21
_L21:
                ((OutputStreamWriter) (obj7)).close();
                ((BufferedReader) (obj6)).close();
                obj6 = view;
                if (view == null)
                {
                    break MISSING_BLOCK_LABEL_787;
                }
                obj6 = view.trim();
                Log.e("--- response comments save -->", ((String) (obj6)));
                if (!((String) (obj6)).equals("successfull"))
                {
                    break MISSING_BLOCK_LABEL_2201;
                }
                Toast.makeText(getActivity().getBaseContext(), "Comments Sent successfully", 0).show();
                k = 0;
_L49:
                if (k >= users_checkbox.length)
                {
                    edittext.setText("");
                    return;
                }
                  goto _L23
_L2:
                long l2 = ((Long)((Enumeration) (obj8)).nextElement()).longValue();
                if (((String)selected_users.get(Long.valueOf(l2))).equals("1"))
                {
                    if (k == 0)
                    {
                        view = (new StringBuilder(String.valueOf(obj6))).append(l2).toString();
                        obj6 = (new StringBuilder(String.valueOf(obj7))).append(selected_usernames.get(Long.valueOf(l2))).toString();
                    } else
                    {
                        view = (new StringBuilder(String.valueOf(obj6))).append("`").append(l2).toString();
                        obj6 = (new StringBuilder(String.valueOf(obj7))).append(",").append(selected_usernames.get(Long.valueOf(l2))).toString();
                    }
                    k++;
                    obj7 = obj6;
                    obj6 = view;
                }
                continue; /* Loop/switch isn't completed */
_L4:
                if (regarding_radio[k].isChecked())
                {
                    view = (new StringBuilder(String.valueOf(regarding_radio[k].getId()))).toString();
                }
                k++;
                  goto _L24
_L6:
                if (actions_radio[k].isChecked())
                {
                    obj9 = (new StringBuilder(String.valueOf(actions_radio[k].getId()))).toString();
                    obj8 = actions_radio[k].getText().toString();
                }
                k++;
                  goto _L25
_L10:
                regarding_radio[k].setChecked(false);
                k++;
                  goto _L26
_L12:
                actions_radio[k].setChecked(false);
                k++;
                  goto _L27
_L8:
                if (view == null || view.equalsIgnoreCase("null")) goto _L29; else goto _L28
_L28:
                obj7 = "";
                obj9 = (new URL("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp")).openConnection();
                Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp?user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&for_user_ids=").append(((String) (obj6))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                ((URLConnection) (obj9)).setDoOutput(true);
                obj8 = new OutputStreamWriter(((URLConnection) (obj9)).getOutputStream());
                ((OutputStreamWriter) (obj8)).write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&regarding=").append(view).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                ((OutputStreamWriter) (obj8)).flush();
                ((OutputStreamWriter) (obj8)).close();
                obj9 = new BufferedReader(new InputStreamReader(((URLConnection) (obj9)).getInputStream()));
                view = ((View) (obj7));
_L34:
                obj7 = ((BufferedReader) (obj9)).readLine();
                if (obj7 != null) goto _L31; else goto _L30
_L30:
                view = view.trim();
                ((OutputStreamWriter) (obj8)).close();
                ((BufferedReader) (obj9)).close();
                if (!view.equals("successfull"))
                {
                    break MISSING_BLOCK_LABEL_1574;
                }
                Toast.makeText(getActivity().getBaseContext(), "Event generated successfully", 0).show();
                k = 0;
_L35:
                if (k < regarding_radio.length) goto _L33; else goto _L32
_L32:
                edittext.setText("");
                  goto _L11
_L31:
                view = (new StringBuilder(String.valueOf(view))).append(((String) (obj7))).toString();
                  goto _L34
_L33:
                regarding_radio[k].setChecked(false);
                k++;
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
                if (obj9 == null || ((String) (obj9)).equalsIgnoreCase("null")) goto _L11; else goto _L36
_L36:
                view = "";
                OutputStreamWriter outputstreamwriter;
                URLConnection urlconnection = (new URL("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp")).openConnection();
                Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_comments_regarding_actions.jsp?user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&for_user_ids=").append(((String) (obj6))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                urlconnection.setDoOutput(true);
                outputstreamwriter = new OutputStreamWriter(urlconnection.getOutputStream());
                outputstreamwriter.write((new StringBuilder("user_id=")).append(activeUserId).append("&event_id=").append(eventId).append("&action=").append(((String) (obj9))).append("&action_t=").append(((String) (obj8))).append("&action_from=").append(((String) (obj6))).append("&action_from_username=").append(((String) (obj7))).append("&message=").append(URLDecoder.decode(edittext.getText().toString(), "utf8")).toString());
                outputstreamwriter.flush();
                outputstreamwriter.close();
                obj7 = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
_L43:
                obj8 = ((BufferedReader) (obj7)).readLine();
                if (obj8 != null) goto _L38; else goto _L37
_L37:
                outputstreamwriter.close();
                ((BufferedReader) (obj7)).close();
                if (!view.equals("successfull"))
                {
                    break MISSING_BLOCK_LABEL_2039;
                }
                Toast.makeText(getActivity().getBaseContext(), "Action inserted successfully", 0).show();
                k = 0;
_L44:
                if (k < actions_radio.length) goto _L40; else goto _L39
_L39:
                k = 0;
_L45:
                if (k < users_checkbox.length) goto _L42; else goto _L41
_L41:
                edittext.setText("");
                  goto _L11
_L38:
                view = (new StringBuilder(String.valueOf(view))).append(((String) (obj8))).toString();
                  goto _L43
_L40:
                actions_radio[k].setChecked(false);
                k++;
                  goto _L44
_L42:
                users_checkbox[k].setChecked(false);
                k++;
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
                view = (new StringBuilder(String.valueOf(view))).append(((String) (obj9))).toString();
                  goto _L46
_L20:
                users_checkbox[k].setChecked(false);
                k++;
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
                view = (new StringBuilder(String.valueOf(view))).append(((String) (obj8))).toString();
                  goto _L48
_L23:
                users_checkbox[k].setChecked(false);
                k++;
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

            
            {
                this$0 = DetailFragment.this;
                eventId = l;
                super();
            }
        });
        ((LinearLayout) (obj)).addView(((View) (obj2)));
        obj2 = new TableRow(getActivity().getBaseContext());
        tablelayout.addView(((View) (obj2)));
        obj = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj)).setOrientation(0);
        ((TableRow) (obj2)).addView(((View) (obj)));
        obj2 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj2)).setOrientation(1);
        ((LinearLayout) (obj)).addView(((View) (obj2)));
        obj3 = new RadioGroup(getActivity().getBaseContext());
        ((RadioGroup) (obj3)).setOrientation(1);
        ((RadioGroup) (obj3)).setId(regardingRadioGpId);
        obj4 = sqlitesyncdao.getEventRegardings(l);
        regarding_radio = new RadioButton[((List) (obj4)).size()];
        i = 0;
        obj4 = ((List) (obj4)).iterator();
_L20:
        if (((Iterator) (obj4)).hasNext()) goto _L6; else goto _L5
_L5:
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
        obj2 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj2)).setOrientation(1);
        ((LinearLayout) (obj)).addView(((View) (obj2)));
        obj = new RadioGroup(getActivity().getBaseContext());
        ((RadioGroup) (obj)).setPadding(0, 0, 0, 0);
        ((RadioGroup) (obj)).setOrientation(1);
        ((RadioGroup) (obj)).setId(actionRadioGpId);
        obj3 = sqlitesyncdao.getActionList();
        actions_radio = new RadioButton[((List) (obj3)).size()];
        i = 0;
        obj3 = ((List) (obj3)).iterator();
_L21:
        if (!((Iterator) (obj3)).hasNext())
        {
            ((LinearLayout) (obj2)).addView(((View) (obj)));
            obj = Calendar.getInstance();
            mYear = ((Calendar) (obj)).get(1);
            mMonth = ((Calendar) (obj)).get(2);
            mDay = ((Calendar) (obj)).get(5);
            mhour = ((Calendar) (obj)).get(11);
            mminute = ((Calendar) (obj)).get(12);
            obj = new TextView(getActivity().getBaseContext());
            ((TextView) (obj)).setId(actionSchDateId);
            ((TextView) (obj)).setTextColor(0xff000000);
            ((TextView) (obj)).setOnClickListener(new android.view.View.OnClickListener() {

                final DetailFragment this$0;

                public void onClick(View view)
                {
                    showDateDialog();
                }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
            });
            ((LinearLayout) (obj2)).addView(((View) (obj)));
            linearlayout.addView(tablelayout);
            sqlitesyncdao.close();
            return;
        }
        break MISSING_BLOCK_LABEL_3398;
_L2:
        Object obj5;
        int j;
        long l1;
        obj5 = (EventsDto)iterator.next();
        l1 = l;
        if (((EventsDto) (obj5)).getParent_event() == 0L)
        {
            l1 = ((EventsDto) (obj5)).getRole_id();
            ((EventsDto) (obj5)).getSchedule_date();
        }
        obj2 = ((EventsDto) (obj5)).getStatus_t();
        obj = "";
        obj2 = ((String) (obj2)).split(" ");
        j = obj2.length;
        i = 0;
_L18:
        if (i < j)
        {
            break MISSING_BLOCK_LABEL_2790;
        }
        obj2 = obj;
        if (((String) (obj)).equals("O"))
        {
            obj2 = "Op";
        }
        ((EventsDto) (obj5)).setStatus_t(((String) (obj2)));
        obj = "";
        LinearLayout linearlayout1;
        if (((EventsDto) (obj5)).getPriority() == 2L)
        {
            obj = "#7FFFD4";
        } else
        if (((EventsDto) (obj5)).getPriority() == 3L)
        {
            obj = "#FFE5B4";
        } else
        if (((EventsDto) (obj5)).getPriority() == 4L)
        {
            obj = "#FF9966";
        }
        obj2 = new TableRow(getActivity().getBaseContext());
        tablelayout.addView(((View) (obj2)));
        linearlayout1 = new LinearLayout(getActivity().getBaseContext());
        linearlayout1.setOrientation(0);
        linearlayout1.setBackgroundColor(Color.parseColor(((String) (obj))));
        ((TableRow) (obj2)).addView(linearlayout1);
        obj3 = ((EventsDto) (obj5)).getSchedule_date();
        if (obj3 == null || ((String) (obj3)).equals("null")) goto _L8; else goto _L7
_L7:
        obj2 = obj3;
        if (((String) (obj3)).length() != 0) goto _L9; else goto _L8
_L8:
        obj2 = ((EventsDto) (obj5)).getStart_date();
_L9:
        if (obj2 == null || ((String) (obj2)).equals("null") || ((String) (obj2)).length() == 0)
        {
            ((EventsDto) (obj5)).getEntry_date();
        }
        obj2 = new TextView(getActivity().getBaseContext());
        ((TextView) (obj2)).setPadding(2, 2, 2, 2);
        ((TextView) (obj2)).setTextSize(14F);
        ((TextView) (obj2)).setTextColor(0xff000000);
        obj3 = new SpannableString((new StringBuilder(String.valueOf(((EventsDto) (obj5)).getBy_desig_t()))).append("\n").append(((EventsDto) (obj5)).getSchedule_date()).append("\n[").append(((EventsDto) (obj5)).getStatus_t()).append("]").toString());
        ((SpannableString) (obj3)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj3)).length(), 0);
        ((TextView) (obj2)).setText(((CharSequence) (obj3)));
        linearlayout1.addView(((View) (obj2)));
        obj3 = ((EventsDto) (obj5)).getRole_t();
        if (obj3 != null && !((String) (obj3)).equalsIgnoreCase("null") && ((String) (obj3)).length() > 0)
        {
            obj2 = obj3;
            if (((EventsDto) (obj5)).getRegarding_t() != null)
            {
                obj2 = obj3;
                if (!((EventsDto) (obj5)).getRegarding_t().equalsIgnoreCase("null"))
                {
                    obj2 = obj3;
                    if (((EventsDto) (obj5)).getRegarding_t().length() > 0)
                    {
                        obj2 = (new StringBuilder(String.valueOf(obj3))).append(", ").append(((EventsDto) (obj5)).getRegarding_t()).toString();
                    }
                }
            }
        } else
        {
            obj2 = ((EventsDto) (obj5)).getRegarding_t();
        }
        obj4 = ((EventsDto) (obj5)).getFor_user_t();
        if (obj4 != null && !((String) (obj4)).equalsIgnoreCase("null") && ((String) (obj4)).length() > 0)
        {
            obj3 = obj4;
            if (((EventsDto) (obj5)).getFor_product_t() != null)
            {
                obj3 = obj4;
                if (!((EventsDto) (obj5)).getFor_product_t().equalsIgnoreCase("null"))
                {
                    obj3 = obj4;
                    if (((EventsDto) (obj5)).getFor_product_t().length() > 0)
                    {
                        obj3 = (new StringBuilder(String.valueOf(obj4))).append(", ").append(((EventsDto) (obj5)).getFor_product_t()).toString();
                    }
                }
            }
        } else
        if (((EventsDto) (obj5)).getFor_product_t() != null)
        {
            obj3 = ((EventsDto) (obj5)).getFor_product_t();
        } else
        {
            obj3 = "";
        }
        obj4 = obj2;
        if (obj4 != null && !((String) (obj4)).equalsIgnoreCase("null") && ((String) (obj4)).length() > 0)
        {
            obj2 = obj4;
            if (obj3 != null)
            {
                obj2 = obj4;
                if (!((String) (obj3)).equalsIgnoreCase("null"))
                {
                    obj2 = obj4;
                    if (((String) (obj3)).length() > 0)
                    {
                        obj2 = (new StringBuilder(String.valueOf(obj4))).append(", ").append(((String) (obj3))).toString();
                    }
                }
            }
        } else
        {
            obj2 = obj3;
        }
        obj3 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj3)).setOrientation(1);
        ((LinearLayout) (obj3)).setBackgroundColor(Color.parseColor(((String) (obj))));
        linearlayout1.addView(((View) (obj3)));
        obj = new TextView(getActivity().getBaseContext());
        ((TextView) (obj)).setPadding(2, 2, 2, 2);
        ((TextView) (obj)).setTextSize(13F);
        ((TextView) (obj)).setTextColor(0xff000000);
        obj2 = new SpannableString(((CharSequence) (obj2)));
        ((SpannableString) (obj2)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj2)).length(), 0);
        ((TextView) (obj)).setText(((CharSequence) (obj2)));
        ((LinearLayout) (obj3)).addView(((View) (obj)));
        obj = ((EventsDto) (obj5)).getInstructions();
        if (obj == null) goto _L11; else goto _L10
_L10:
        obj2 = obj;
        if (!((String) (obj)).equalsIgnoreCase("null")) goto _L12; else goto _L11
_L11:
        obj2 = ((EventsDto) (obj5)).getComments();
_L12:
        if (obj2 == null) goto _L14; else goto _L13
_L13:
        obj = obj2;
        if (!((String) (obj2)).equalsIgnoreCase("null")) goto _L15; else goto _L14
_L14:
        obj = "";
_L15:
        if (((EventsDto) (obj5)).getRole_details() != null)
        {
            obj2 = ((EventsDto) (obj5)).getRole_details();
        } else
        {
            obj2 = "";
        }
        if (((String) (obj2)).length() > 0)
        {
            obj = (new StringBuilder(String.valueOf(obj2))).append("\n").append(((String) (obj))).toString();
        }
        obj2 = new TextView(getActivity().getBaseContext());
        ((TextView) (obj2)).setSingleLine(false);
        ((TextView) (obj2)).setPadding(2, 2, 5, 2);
        ((TextView) (obj2)).setTextSize(12F);
        ((TextView) (obj2)).setTextColor(Color.parseColor("#0000A0"));
        obj = new SpannableString(((CharSequence) (obj)));
        ((SpannableString) (obj)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj)).length(), 0);
        ((TextView) (obj2)).setText(((CharSequence) (obj)));
        ((LinearLayout) (obj3)).addView(((View) (obj2)));
        obj4 = sqlitesyncdao.getEventNotifications(((EventsDto) (obj5)).getAttr_parent()).iterator();
        l = l1;
        if (!((Iterator) (obj4)).hasNext()) goto _L17; else goto _L16
_L16:
        obj5 = (TaskNotificationDto)((Iterator) (obj4)).next();
        obj = new TableRow(getActivity().getBaseContext());
        tablelayout.addView(((View) (obj)));
        LinearLayout linearlayout2 = new LinearLayout(getActivity().getBaseContext());
        linearlayout2.setOrientation(0);
        linearlayout2.setBackgroundColor(Color.parseColor("#ffffff"));
        ((TableRow) (obj)).addView(linearlayout2);
        obj = null;
        if ((new StringBuilder()).append(((TaskNotificationDto) (obj5)).getSeenPersonsIds()).toString().indexOf((new StringBuilder()).append(activeUserId).toString()) > 0)
        {
            obj2 = new ImageView(getActivity().getBaseContext());
            ((ImageView) (obj2)).setImageResource(0x7f02001d);
            linearlayout2.addView(((View) (obj2)));
        } else
        {
            obj = new ImageView(getActivity().getBaseContext());
            ((ImageView) (obj)).setImageResource(0x7f02001e);
            ((ImageView) (obj)).setId((int)((TaskNotificationDto) (obj5)).getId());
            linearlayout2.addView(((View) (obj)));
            ((ImageView) (obj)).setOnClickListener(new android.view.View.OnClickListener() {

                final DetailFragment this$0;

                public void onClick(View view)
                {
                    Object obj6;
                    int k;
                    k = view.getId();
                    obj6 = "";
                    OutputStreamWriter outputstreamwriter;
                    Object obj8;
                    obj8 = (new URL("http://billing.maestrocomms.com/task/android/add_seen_status.jsp")).openConnection();
                    Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_seen_status.jsp?login_omsid=")).append(activeUserId).append("&bnmid=").append(k).toString());
                    ((URLConnection) (obj8)).setDoOutput(true);
                    outputstreamwriter = new OutputStreamWriter(((URLConnection) (obj8)).getOutputStream());
                    outputstreamwriter.write((new StringBuilder("login_omsid=")).append(activeUserId).append("&bnmid=").append(k).toString());
                    outputstreamwriter.flush();
                    outputstreamwriter.close();
                    obj8 = new BufferedReader(new InputStreamReader(((URLConnection) (obj8)).getInputStream()));
_L1:
                    String s = ((BufferedReader) (obj8)).readLine();
                    if (s != null)
                    {
                        break MISSING_BLOCK_LABEL_309;
                    }
                    obj6 = ((String) (obj6)).trim();
                    outputstreamwriter.close();
                    ((BufferedReader) (obj8)).close();
                    if (((String) (obj6)).equals("successfull"))
                    {
                        ((ImageView)view).setImageResource(0x7f02001d);
                        Object obj7 = new NotificationRecipientsDto();
                        ((NotificationRecipientsDto) (obj7)).setNotifId(k);
                        ((NotificationRecipientsDto) (obj7)).setSeen("Y");
                        obj6 = new SQLiteSyncDAO(getActivity().getBaseContext());
                        ((SQLiteSyncDAO) (obj6)).update(((NotificationRecipientsDto) (obj7)), activeUserId);
                        view = (TextView)view.getTag();
                        obj7 = ((SQLiteSyncDAO) (obj6)).getNotificationSeenBy(k);
                        ((SQLiteSyncDAO) (obj6)).close();
                        obj6 = new SpannableString((new StringBuilder("seen by: ")).append(((String) (obj7))).toString());
                        ((SpannableString) (obj6)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj6)).length(), 0);
                        view.setText(((CharSequence) (obj6)));
                        return;
                    }
                    break MISSING_BLOCK_LABEL_332;
                    obj6 = (new StringBuilder(String.valueOf(obj6))).append(s).toString();
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

            
            {
                this$0 = DetailFragment.this;
                super();
            }
            });
        }
        obj2 = new TextView(getActivity().getBaseContext());
        ((TextView) (obj2)).setPadding(2, 2, 2, 2);
        ((TextView) (obj2)).setTextSize(13F);
        ((TextView) (obj2)).setTextColor(0xff000000);
        obj3 = new SpannableString((new StringBuilder(String.valueOf(((TaskNotificationDto) (obj5)).getEntry_uname()))).append("\n").append(((TaskNotificationDto) (obj5)).getEntry_date()).toString());
        ((SpannableString) (obj3)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj3)).length(), 0);
        ((TextView) (obj2)).setText(((CharSequence) (obj3)));
        linearlayout2.addView(((View) (obj2)));
        obj3 = ((TaskNotificationDto) (obj5)).getAttr_name();
        obj2 = obj3;
        if (((TaskNotificationDto) (obj5)).getSummary() != null)
        {
            obj2 = obj3;
            if (((TaskNotificationDto) (obj5)).getSummary().substring(0, 1).equals("*"))
            {
                obj2 = "New";
            }
        }
        if (obj2 != null)
        {
            if (!((String) (obj2)).equalsIgnoreCase("null"));
        }
        obj2 = new TextView(getActivity().getBaseContext());
        ((TextView) (obj2)).setPadding(2, 2, 2, 2);
        ((TextView) (obj2)).setTextSize(13F);
        ((TextView) (obj2)).setTextColor(0xff000000);
        ((TextView) (obj2)).setText(new SpannableString(((TaskNotificationDto) (obj5)).getSummary()));
        linearlayout2.addView(((View) (obj2)));
        obj3 = new TableRow(getActivity().getBaseContext());
        tablelayout.addView(((View) (obj3)));
        obj2 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj2)).setOrientation(0);
        ((LinearLayout) (obj2)).setBackgroundColor(Color.parseColor("#ffffff"));
        ((TableRow) (obj3)).addView(((View) (obj2)));
        Log.e((new StringBuilder("-seen list->")).append(((TaskNotificationDto) (obj5)).getId()).toString(), (new StringBuilder("-")).append(((TaskNotificationDto) (obj5)).getSeenPersons()).toString());
        if (((TaskNotificationDto) (obj5)).getSeenPersons() != null)
        {
            obj3 = new TextView(getActivity().getBaseContext());
            ((TextView) (obj3)).setPadding(2, 2, 2, 2);
            ((TextView) (obj3)).setTextSize(9F);
            ((TextView) (obj3)).setTag((new StringBuilder("seen")).append(((TaskNotificationDto) (obj5)).getId()).toString());
            ((TextView) (obj3)).setTextColor(Color.parseColor("#008000"));
            obj5 = new SpannableString((new StringBuilder("seen by: ")).append(((TaskNotificationDto) (obj5)).getSeenPersons()).toString());
            ((SpannableString) (obj5)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj5)).length(), 0);
            ((TextView) (obj3)).setText(((CharSequence) (obj5)));
            ((LinearLayout) (obj2)).addView(((View) (obj3)));
            if (obj != null)
            {
                ((ImageView) (obj)).setTag(obj3);
            }
        } else
        {
            obj3 = new TextView(getActivity().getBaseContext());
            ((TextView) (obj3)).setPadding(2, 2, 2, 2);
            ((TextView) (obj3)).setTextSize(9F);
            ((TextView) (obj3)).setTag((new StringBuilder("seen")).append(((TaskNotificationDto) (obj5)).getId()).toString());
            ((TextView) (obj3)).setTextColor(Color.parseColor("#008000"));
            ((LinearLayout) (obj2)).addView(((View) (obj3)));
            if (obj != null)
            {
                ((ImageView) (obj)).setTag(obj3);
            }
        }
        break MISSING_BLOCK_LABEL_2122;
          goto _L17
        obj3 = obj2[i];
        obj = (new StringBuilder(String.valueOf(obj))).append(((String) (obj3)).substring(0, 1)).toString();
        i++;
          goto _L18
_L4:
        obj4 = (EventRelatedUsersDto)((Iterator) (obj3)).next();
        CheckBox checkbox = new CheckBox(getActivity().getBaseContext());
        checkbox.setText(((EventRelatedUsersDto) (obj4)).getUsername());
        checkbox.setId((int)((EventRelatedUsersDto) (obj4)).getOms_id());
        checkbox.setTextColor(0xff000000);
        checkbox.setTextSize(14F);
        android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(-2, -2);
        layoutparams.setMargins(0, 0, 0, 0);
        checkbox.setLayoutParams(layoutparams);
        checkbox.setTop(0);
        checkbox.setPadding(55, 0, 0, 0);
        checkbox.setBottom(0);
        ((LinearLayout) (obj2)).addView(checkbox);
        checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

            final DetailFragment this$0;

            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
            {
                long l2 = compoundbutton.getId();
                if (flag)
                {
                    selected_users.put(Long.valueOf(l2), "1");
                    return;
                } else
                {
                    selected_users.put(Long.valueOf(l2), "");
                    return;
                }
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        selected_users.put(Long.valueOf(((EventRelatedUsersDto) (obj4)).getOms_id()), "0");
        selected_usernames.put(Long.valueOf(((EventRelatedUsersDto) (obj4)).getOms_id()), ((EventRelatedUsersDto) (obj4)).getUsername());
        users_checkbox[i] = checkbox;
        i++;
          goto _L19
_L6:
        RoleRegardingDto roleregardingdto = (RoleRegardingDto)((Iterator) (obj4)).next();
        RadioButton radiobutton1 = new RadioButton(getActivity().getBaseContext());
        radiobutton1.setText(roleregardingdto.getRegarding_t());
        radiobutton1.setTextSize(14F);
        android.widget.LinearLayout.LayoutParams layoutparams2 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        layoutparams2.setMargins(0, 0, 0, 0);
        radiobutton1.setLayoutParams(layoutparams2);
        radiobutton1.setTop(0);
        radiobutton1.setPadding(55, 0, 0, 0);
        radiobutton1.setBottom(0);
        radiobutton1.setId(roleregardingdto.getRegarding());
        radiobutton1.setTextColor(0xff000000);
        ((RadioGroup) (obj3)).addView(radiobutton1);
        regarding_radio[i] = radiobutton1;
        i++;
          goto _L20
        ActionListDto actionlistdto = (ActionListDto)((Iterator) (obj3)).next();
        RadioButton radiobutton = new RadioButton(getActivity().getBaseContext());
        radiobutton.setText(actionlistdto.getAName());
        radiobutton.setTextSize(14F);
        android.widget.LinearLayout.LayoutParams layoutparams1 = new android.widget.LinearLayout.LayoutParams(-2, -2);
        layoutparams1.setMargins(0, 0, 0, 0);
        radiobutton.setLayoutParams(layoutparams1);
        radiobutton.setTop(0);
        radiobutton.setPadding(55, 0, 0, 0);
        radiobutton.setBottom(0);
        radiobutton.setId((int)actionlistdto.getId());
        radiobutton.setTextColor(0xff000000);
        ((RadioGroup) (obj)).addView(radiobutton);
        actions_radio[i] = radiobutton;
        i++;
          goto _L21
    }

    public void showEventSummary()
    {
        SQLiteSyncDAO sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        TableLayout tablelayout;
        Object obj1;
        detailContainer.removeAllViews();
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        tablelayout = new TableLayout(getActivity().getBaseContext());
        tablelayout.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        obj1 = new TableRow(getActivity().getBaseContext());
        detailContainer.addView(tablelayout);
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        tablelayout.addView(((View) (obj1)));
        detailContainer.setOrientation(1);
        obj1 = sqlitesyncdao.getEventSummary(Globals.getInstance().getOms_id()).iterator();
_L31:
        boolean flag = ((Iterator) (obj1)).hasNext();
        if (!flag)
        {
            sqlitesyncdao.close();
            return;
        }
        EventSummaryDto eventsummarydto;
        TableRow tablerow;
        Object obj2;
        Object obj3;
        eventsummarydto = (EventSummaryDto)((Iterator) (obj1)).next();
        tablerow = new TableRow(getActivity().getBaseContext());
        obj2 = new TextView(getActivity().getBaseContext());
        obj3 = new SpannableString(eventsummarydto.getName());
        ((SpannableString) (obj3)).setSpan(new StyleSpan(1), 0, ((SpannableString) (obj3)).length(), 0);
        ((TextView) (obj2)).setText(((CharSequence) (obj3)));
        ((TextView) (obj2)).setPadding(2, 2, 10, 2);
        ((TextView) (obj2)).setTextSize(18F);
        ((TextView) (obj2)).setTextColor(0xff0000ff);
        ((TextView) (obj2)).setTextSize(18F);
        ((TextView) (obj2)).setGravity(3);
        obj3 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L2; else goto _L1
_L1:
        obj3[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj3[1] = "role";
        obj3[2] = eventsummarydto.getName();
_L32:
        ((TextView) (obj2)).setTag(obj3);
        ((TextView) (obj2)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], null);
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        tablerow.addView(((View) (obj2)));
        tablerow.setBackgroundColor(Color.parseColor("#D6FF5C"));
        tablelayout.addView(tablerow);
        tablerow = new TableRow(getActivity().getBaseContext());
        tablerow.setBackgroundColor(Color.parseColor("#ffffff"));
        obj2 = new LinearLayout(getActivity().getBaseContext());
        ((LinearLayout) (obj2)).setOrientation(0);
        tablerow.addView(((View) (obj2)));
        if (eventsummarydto.getHigh() <= 0L) goto _L4; else goto _L3
_L3:
        Object obj4;
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getHigh()))).append("H").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L6; else goto _L5
_L5:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L33:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "high");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L4:
        if (eventsummarydto.getSt_new() <= 0L) goto _L8; else goto _L7
_L7:
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getSt_new()))).append("N").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L10; else goto _L9
_L9:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L34:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "new");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L8:
        if (eventsummarydto.getSt_op() <= 0L) goto _L12; else goto _L11
_L11:
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getSt_op()))).append("OP").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L14; else goto _L13
_L13:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L35:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "op");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L12:
        if (eventsummarydto.getSt_comp() <= 0L) goto _L16; else goto _L15
_L15:
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getSt_comp()))).append("C").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L18; else goto _L17
_L17:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L36:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "comp");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L16:
        if (eventsummarydto.getSt_pending() <= 0L) goto _L20; else goto _L19
_L19:
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getSt_pending()))).append("P").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L22; else goto _L21
_L21:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L37:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "pending");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L20:
        if (eventsummarydto.getUnseen() <= 0L) goto _L24; else goto _L23
_L23:
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getUnseen()))).append("U").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L26; else goto _L25
_L25:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L38:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "unseen");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L24:
        Log.e("--- APPR -->", (new StringBuilder()).append(eventsummarydto.getAppr_req()).toString());
        if (eventsummarydto.getAppr_req() <= 0L) goto _L28; else goto _L27
_L27:
        obj3 = new TextView(getActivity().getBaseContext());
        obj4 = new SpannableString((new StringBuilder(String.valueOf(eventsummarydto.getAppr_req()))).append("A").toString());
        ((SpannableString) (obj4)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj4)).length(), 0);
        ((TextView) (obj3)).setText(((CharSequence) (obj4)));
        ((TextView) (obj3)).setPadding(2, 2, 20, 2);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setTextColor(0xff000000);
        ((TextView) (obj3)).setTextSize(18F);
        ((TextView) (obj3)).setGravity(3);
        obj4 = new String[4];
        if (eventsummarydto.getRole_id() <= 0L) goto _L30; else goto _L29
_L29:
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getRole_id()))).toString();
        obj4[1] = "role";
        obj4[2] = eventsummarydto.getName();
_L39:
        ((TextView) (obj3)).setTag(obj4);
        ((TextView) (obj3)).setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                view = (String[])view.getTag();
                showTypeWiseEventDetails(view[0], view[1], view[2], "appr");
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        ((LinearLayout) (obj2)).addView(((View) (obj3)));
_L28:
        tablelayout.addView(tablerow);
          goto _L31
        Object obj;
        obj;
        Log.e("Exception ... ", ((Exception) (obj)).getMessage());
        sqlitesyncdao.close();
        return;
_L2:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_1983;
        }
        obj3[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj3[1] = "dept";
        obj3[2] = eventsummarydto.getName();
          goto _L32
        obj;
        sqlitesyncdao.close();
        throw obj;
        obj3[2] = eventsummarydto.getName();
          goto _L32
_L6:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2046;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L33
        obj4[2] = eventsummarydto.getName();
          goto _L33
_L10:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2109;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L34
        obj4[2] = eventsummarydto.getName();
          goto _L34
_L14:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2172;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L35
        obj4[2] = eventsummarydto.getName();
          goto _L35
_L18:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2235;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L36
        obj4[2] = eventsummarydto.getName();
          goto _L36
_L22:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2298;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L37
        obj4[2] = eventsummarydto.getName();
          goto _L37
_L26:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2361;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L38
        obj4[2] = eventsummarydto.getName();
          goto _L38
_L30:
        if (eventsummarydto.getDept_id() <= 0L)
        {
            break MISSING_BLOCK_LABEL_2424;
        }
        obj4[0] = (new StringBuilder(String.valueOf(eventsummarydto.getDept_id()))).toString();
        obj4[1] = "dept";
        obj4[2] = eventsummarydto.getName();
          goto _L39
        obj4[2] = eventsummarydto.getName();
          goto _L39
    }

    public void showTypeWiseEventDetails(String s, String s1, String s2, String s3)
    {
        String as[];
        ArrayList arraylist;
        SQLiteSyncDAO sqlitesyncdao;
        ListView listview;
        int i;
        role_dept_id = s;
        isRoleOrDept = s1;
        type = s2;
        type_value = s3;
        Globals.role_dept_id = s;
        Globals.isRoleOrDept = s1;
        Globals.type = s2;
        Globals.type_value = s3;
        sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        detailContainer.removeAllViews();
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        back.setVisibility(4);
        back.setHeight(0);
        listview = new ListView(getActivity().getBaseContext());
        detailContainer.addView(listview);
        as = new String[23];
        as[0] = "Android";
        as[1] = "iPhone";
        as[2] = "WindowsMobile";
        as[3] = "Blackberry";
        as[4] = "WebOS";
        as[5] = "Ubuntu";
        as[6] = "Windows7";
        as[7] = "Max OS X";
        as[8] = "Linux";
        as[9] = "OS/2";
        as[10] = "Ubuntu";
        as[11] = "Windows7";
        as[12] = "Max OS X";
        as[13] = "Linux";
        as[14] = "OS/2";
        as[15] = "Ubuntu";
        as[16] = "Windows7";
        as[17] = "Max OS X";
        as[18] = "Linux";
        as[19] = "OS/2";
        as[20] = "Android";
        as[21] = "iPhone";
        as[22] = "WindowsMobile";
        arraylist = new ArrayList();
        i = 0;
_L3:
        if (i < as.length) goto _L2; else goto _L1
_L1:
        ArrayList arraylist1;
        Iterator iterator;
        new StableArrayAdapter(getActivity().getBaseContext(), 0x1090003, arraylist);
        arraylist1 = new ArrayList();
        s = sqlitesyncdao.getSelectedEvents(s, s1, s2, s3);
        i = 0;
        iterator = s.iterator();
_L4:
        if (!iterator.hasNext())
        {
            listview.setAdapter(new SummaryListViewAdapter(getActivity().getBaseContext(), 0x7f030004, arraylist1));
            sqlitesyncdao.close();
            return;
        }
        break MISSING_BLOCK_LABEL_427;
_L2:
        arraylist.add(as[i]);
        i++;
          goto _L3
label0:
        {
            EventsDto eventsdto = (EventsDto)iterator.next();
            s1 = "#98FF98";
            Log.e("dto.getPriority()", (new StringBuilder(String.valueOf(eventsdto.getPriority()))).toString());
            String s4;
            String s5;
            String s6;
            if (eventsdto.getPriority() == 2L)
            {
                s1 = "#7FFFD4";
            } else
            if (eventsdto.getPriority() == 3L)
            {
                s1 = "#66FF66";
            } else
            if (eventsdto.getPriority() == 4L)
            {
                s1 = "#FF9966";
            }
            Log.e((new StringBuilder("---APPRRRRRR -")).append(eventsdto.getAttr_parent()).append("->").toString(), (new StringBuilder()).append(eventsdto.getIsAppr_req()).toString());
            s3 = "";
            s2 = "";
            s = "";
            s4 = s3;
            if (eventsdto.getRegarding_t() != null)
            {
                s4 = s3;
                if (eventsdto.getRegarding_t().length() > 0)
                {
                    s4 = eventsdto.getRegarding_t();
                }
            }
            s5 = s2;
            if (eventsdto.getFor_product_t() != null)
            {
                s5 = s2;
                if (eventsdto.getFor_product_t().length() > 0)
                {
                    s5 = eventsdto.getFor_product_t();
                }
            }
            s3 = s;
            if (eventsdto.getFor_user_t() != null)
            {
                s3 = s;
                if (eventsdto.getFor_user_t().length() > 0)
                {
                    s3 = eventsdto.getFor_user_t();
                }
            }
            s2 = s5;
            s = s3;
            if (s5.length() == 0)
            {
                s2 = s5;
                s = s3;
                if (s3.length() > 0)
                {
                    s2 = s3;
                    s = "";
                }
            }
            s3 = s4;
            s5 = s2;
            s6 = s;
            if (s4.length() != 0)
            {
                break label0;
            }
            if (s2.length() != 0)
            {
                s3 = s4;
                s5 = s2;
                s6 = s;
                if (s.length() <= 0)
                {
                    break label0;
                }
            }
            if (s2.length() > 0)
            {
                s6 = "";
                s5 = s;
                s3 = s2;
            } else
            {
                s5 = "";
                s6 = "";
                s3 = s;
            }
        }
        s4 = "";
        s = "";
        if (eventsdto.getUnseen() > 0L)
        {
            s = (new StringBuilder()).append(eventsdto.getUnseen()).append(" Unseen").toString();
        }
        s2 = s4;
        if (eventsdto.getIsAppr_req() != null)
        {
            s2 = s4;
            if (eventsdto.getIsAppr_req().equals("Y"))
            {
                s2 = " , Approval Req";
            }
        }
        s = new SummaryItem(eventsdto.getRole_t(), (new StringBuilder(String.valueOf(eventsdto.getComments()))).append(" ").append(eventsdto.getAttr_parent()).toString(), s3, s5, s6, (new StringBuilder(String.valueOf(s))).append(s2).toString(), 0x7f020051, 0x7f020011, 0x7f020064, s1, eventsdto.getIsAppr_req());
        s.setEventid(eventsdto.getAttr_parent());
        i++;
        arraylist1.add(s);
          goto _L4
    }

    public void updateDate(Calendar calendar)
    {
        Log.e("date updated", calendar.toString());
        setDate(calendar);
        Date date = new Date(calendar.getTimeInMillis());
        ((TextView)getActivity().findViewById(actionSchDateId)).setText(SQLiteSyncDAO.mediumDateFormat3.format(date));
        updateUi(calendar);
    }

    public void updateDateId(long l)
    {
        mDateId = l;
        setDate(Calendar.getInstance());
    }

    public void updateEventDetails(long l)
    {
        TableLayout tablelayout;
        SQLiteSyncDAO sqlitesyncdao;
        Iterator iterator;
        Globals globals = Globals.getInstance();
        globals.setEventId2(l);
        Globals.curr_event_id = l;
        detailContainer.removeAllViews();
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        Object obj1 = new ScrollView(getActivity().getBaseContext());
        LinearLayout linearlayout = new LinearLayout(getActivity().getBaseContext());
        linearlayout.setOrientation(1);
        ((ScrollView) (obj1)).addView(linearlayout);
        detailContainer.addView(((View) (obj1)));
        tablelayout = new TableLayout(getActivity().getBaseContext());
        tablelayout.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        tablelayout.setStretchAllColumns(true);
        tablelayout.setShrinkAllColumns(true);
        back.setVisibility(0);
        back.setHeight(40);
        back.setPadding(2, 2, 2, 2);
        back.setGravity(5);
        back.setTextSize(18F);
        back.setTextColor(0xff0000ff);
        obj1 = new SpannableString("<Back");
        ((SpannableString) (obj1)).setSpan(new UnderlineSpan(), 0, ((SpannableString) (obj1)).length(), 0);
        back.setText(((CharSequence) (obj1)));
        back.setOnClickListener(new android.view.View.OnClickListener() {

            final DetailFragment this$0;

            public void onClick(View view)
            {
                showTypeWiseEventDetails(DetailFragment.role_dept_id, DetailFragment.isRoleOrDept, DetailFragment.type, DetailFragment.type_value);
            }

            
            {
                this$0 = DetailFragment.this;
                super();
            }
        });
        sqlitesyncdao = new SQLiteSyncDAO(getActivity().getBaseContext());
        obj1 = sqlitesyncdao.getEventDetails(l);
        activeUserId = globals.getOms_id();
        iterator = ((List) (obj1)).iterator();
_L2:
        Object obj;
        Object obj2;
        Object obj5;
        int i;
        int j;
        if (!iterator.hasNext())
        {
            return;
        }
        obj5 = (EventsDto)iterator.next();
        if (((EventsDto) (obj5)).getParent_event() == 0L)
        {
            ((EventsDto) (obj5)).getRole_id();
            ((EventsDto) (obj5)).getSchedule_date();
        }
        obj2 = ((EventsDto) (obj5)).getStatus_t();
        obj = "";
        obj2 = ((String) (obj2)).split(" ");
        j = obj2.length;
        i = 0;
_L3:
label0:
        {
            Object obj3;
label1:
            {
                if (i < j)
                {
                    break label0;
                }
                obj2 = obj;
                if (((String) (obj)).equals("O"))
                {
                    obj2 = "Op";
                }
                ((EventsDto) (obj5)).setStatus_t(((String) (obj2)));
                obj = "";
                Object obj4;
                LinearLayout linearlayout1;
                if (((EventsDto) (obj5)).getPriority() == 2L)
                {
                    obj = "#7FFFD4";
                } else
                if (((EventsDto) (obj5)).getPriority() == 3L)
                {
                    obj = "#FFE5B4";
                } else
                if (((EventsDto) (obj5)).getPriority() == 4L)
                {
                    obj = "#FF9966";
                }
                obj2 = new TableRow(getActivity().getBaseContext());
                tablelayout.addView(((View) (obj2)));
                linearlayout1 = new LinearLayout(getActivity().getBaseContext());
                linearlayout1.setOrientation(0);
                linearlayout1.setBackgroundColor(Color.parseColor(((String) (obj))));
                ((TableRow) (obj2)).addView(linearlayout1);
                obj3 = ((EventsDto) (obj5)).getSchedule_date();
                if (obj3 != null && !((String) (obj3)).equals("null"))
                {
                    obj2 = obj3;
                    if (((String) (obj3)).length() != 0)
                    {
                        break label1;
                    }
                }
                obj2 = ((EventsDto) (obj5)).getStart_date();
            }
label2:
            {
                if (obj2 == null || ((String) (obj2)).equals("null") || ((String) (obj2)).length() == 0)
                {
                    ((EventsDto) (obj5)).getEntry_date();
                }
                obj2 = new TextView(getActivity().getBaseContext());
                ((TextView) (obj2)).setPadding(2, 2, 2, 2);
                ((TextView) (obj2)).setTextSize(14F);
                ((TextView) (obj2)).setTextColor(0xff000000);
                obj3 = new SpannableString((new StringBuilder(String.valueOf(((EventsDto) (obj5)).getBy_desig_t()))).append("\n").append(((EventsDto) (obj5)).getSchedule_date()).append("\n[").append(((EventsDto) (obj5)).getStatus_t()).append("]").toString());
                ((SpannableString) (obj3)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj3)).length(), 0);
                ((TextView) (obj2)).setText(((CharSequence) (obj3)));
                linearlayout1.addView(((View) (obj2)));
                obj3 = ((EventsDto) (obj5)).getRole_t();
                if (obj3 != null && !((String) (obj3)).equalsIgnoreCase("null") && ((String) (obj3)).length() > 0)
                {
                    obj2 = obj3;
                    if (((EventsDto) (obj5)).getRegarding_t() != null)
                    {
                        obj2 = obj3;
                        if (!((EventsDto) (obj5)).getRegarding_t().equalsIgnoreCase("null"))
                        {
                            obj2 = obj3;
                            if (((EventsDto) (obj5)).getRegarding_t().length() > 0)
                            {
                                obj2 = (new StringBuilder(String.valueOf(obj3))).append(", ").append(((EventsDto) (obj5)).getRegarding_t()).toString();
                            }
                        }
                    }
                } else
                {
                    obj2 = ((EventsDto) (obj5)).getRegarding_t();
                }
                obj4 = ((EventsDto) (obj5)).getFor_user_t();
                if (obj4 != null && !((String) (obj4)).equalsIgnoreCase("null") && ((String) (obj4)).length() > 0)
                {
                    obj3 = obj4;
                    if (((EventsDto) (obj5)).getFor_product_t() != null)
                    {
                        obj3 = obj4;
                        if (!((EventsDto) (obj5)).getFor_product_t().equalsIgnoreCase("null"))
                        {
                            obj3 = obj4;
                            if (((EventsDto) (obj5)).getFor_product_t().length() > 0)
                            {
                                obj3 = (new StringBuilder(String.valueOf(obj4))).append(", ").append(((EventsDto) (obj5)).getFor_product_t()).toString();
                            }
                        }
                    }
                } else
                if (((EventsDto) (obj5)).getFor_product_t() != null)
                {
                    obj3 = ((EventsDto) (obj5)).getFor_product_t();
                } else
                {
                    obj3 = "";
                }
                obj4 = obj2;
                if (obj4 != null && !((String) (obj4)).equalsIgnoreCase("null") && ((String) (obj4)).length() > 0)
                {
                    obj2 = obj4;
                    if (obj3 != null)
                    {
                        obj2 = obj4;
                        if (!((String) (obj3)).equalsIgnoreCase("null"))
                        {
                            obj2 = obj4;
                            if (((String) (obj3)).length() > 0)
                            {
                                obj2 = (new StringBuilder(String.valueOf(obj4))).append(", ").append(((String) (obj3))).toString();
                            }
                        }
                    }
                } else
                {
                    obj2 = obj3;
                }
                obj3 = new LinearLayout(getActivity().getBaseContext());
                ((LinearLayout) (obj3)).setOrientation(1);
                ((LinearLayout) (obj3)).setBackgroundColor(Color.parseColor(((String) (obj))));
                linearlayout1.addView(((View) (obj3)));
                obj = new TextView(getActivity().getBaseContext());
                ((TextView) (obj)).setPadding(2, 2, 2, 2);
                ((TextView) (obj)).setTextSize(13F);
                ((TextView) (obj)).setTextColor(0xff000000);
                obj2 = new SpannableString(((CharSequence) (obj2)));
                ((SpannableString) (obj2)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj2)).length(), 0);
                ((TextView) (obj)).setText(((CharSequence) (obj2)));
                ((LinearLayout) (obj3)).addView(((View) (obj)));
                obj = ((EventsDto) (obj5)).getInstructions();
                if (obj != null)
                {
                    obj2 = obj;
                    if (!((String) (obj)).equalsIgnoreCase("null"))
                    {
                        break label2;
                    }
                }
                obj2 = ((EventsDto) (obj5)).getComments();
            }
label3:
            {
                if (obj2 != null)
                {
                    obj = obj2;
                    if (!((String) (obj2)).equalsIgnoreCase("null"))
                    {
                        break label3;
                    }
                }
                obj = "";
            }
            if (((EventsDto) (obj5)).getRole_details() != null)
            {
                obj2 = ((EventsDto) (obj5)).getRole_details();
            } else
            {
                obj2 = "";
            }
            if (((String) (obj2)).length() > 0)
            {
                obj = (new StringBuilder(String.valueOf(obj2))).append("\n").append(((String) (obj))).toString();
            }
            obj2 = new TextView(getActivity().getBaseContext());
            ((TextView) (obj2)).setSingleLine(false);
            ((TextView) (obj2)).setPadding(2, 2, 5, 2);
            ((TextView) (obj2)).setTextSize(12F);
            ((TextView) (obj2)).setTextColor(Color.parseColor("#0000A0"));
            obj = new SpannableString(((CharSequence) (obj)));
            ((SpannableString) (obj)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj)).length(), 0);
            ((TextView) (obj2)).setText(((CharSequence) (obj)));
            ((LinearLayout) (obj3)).addView(((View) (obj2)));
            obj4 = sqlitesyncdao.getEventNotifications(((EventsDto) (obj5)).getAttr_parent()).iterator();
            while (((Iterator) (obj4)).hasNext()) 
            {
                obj5 = (TaskNotificationDto)((Iterator) (obj4)).next();
                obj = new TableRow(getActivity().getBaseContext());
                tablelayout.addView(((View) (obj)));
                LinearLayout linearlayout2 = new LinearLayout(getActivity().getBaseContext());
                linearlayout2.setOrientation(0);
                linearlayout2.setBackgroundColor(Color.parseColor("#ffffff"));
                ((TableRow) (obj)).addView(linearlayout2);
                obj = null;
                if ((new StringBuilder()).append(((TaskNotificationDto) (obj5)).getSeenPersonsIds()).toString().indexOf((new StringBuilder()).append(activeUserId).toString()) > 0)
                {
                    obj2 = new ImageView(getActivity().getBaseContext());
                    ((ImageView) (obj2)).setImageResource(0x7f02001d);
                    linearlayout2.addView(((View) (obj2)));
                } else
                {
                    obj = new ImageView(getActivity().getBaseContext());
                    ((ImageView) (obj)).setImageResource(0x7f02001e);
                    ((ImageView) (obj)).setId((int)((TaskNotificationDto) (obj5)).getId());
                    linearlayout2.addView(((View) (obj)));
                    ((ImageView) (obj)).setOnClickListener(new android.view.View.OnClickListener() {

                        final DetailFragment this$0;

                        public void onClick(View view)
                        {
                            Object obj6;
                            int k;
                            k = view.getId();
                            obj6 = "";
                            OutputStreamWriter outputstreamwriter;
                            Object obj8;
                            obj8 = (new URL("http://billing.maestrocomms.com/task/android/add_seen_status.jsp")).openConnection();
                            Log.e("sending request", (new StringBuilder("http://billing.maestrocomms.com/task/android/add_seen_status.jsp?login_omsid=")).append(activeUserId).append("&bnmid=").append(k).toString());
                            ((URLConnection) (obj8)).setDoOutput(true);
                            outputstreamwriter = new OutputStreamWriter(((URLConnection) (obj8)).getOutputStream());
                            outputstreamwriter.write((new StringBuilder("login_omsid=")).append(activeUserId).append("&bnmid=").append(k).toString());
                            outputstreamwriter.flush();
                            outputstreamwriter.close();
                            obj8 = new BufferedReader(new InputStreamReader(((URLConnection) (obj8)).getInputStream()));
_L1:
                            String s = ((BufferedReader) (obj8)).readLine();
                            if (s != null)
                            {
                                break MISSING_BLOCK_LABEL_309;
                            }
                            obj6 = ((String) (obj6)).trim();
                            outputstreamwriter.close();
                            ((BufferedReader) (obj8)).close();
                            if (((String) (obj6)).equals("successfull"))
                            {
                                ((ImageView)view).setImageResource(0x7f02001d);
                                Object obj7 = new NotificationRecipientsDto();
                                ((NotificationRecipientsDto) (obj7)).setNotifId(k);
                                ((NotificationRecipientsDto) (obj7)).setSeen("Y");
                                obj6 = new SQLiteSyncDAO(getActivity().getBaseContext());
                                ((SQLiteSyncDAO) (obj6)).update(((NotificationRecipientsDto) (obj7)), activeUserId);
                                view = (TextView)view.getTag();
                                obj7 = ((SQLiteSyncDAO) (obj6)).getNotificationSeenBy(k);
                                ((SQLiteSyncDAO) (obj6)).close();
                                obj6 = new SpannableString((new StringBuilder("seen by: ")).append(((String) (obj7))).toString());
                                ((SpannableString) (obj6)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj6)).length(), 0);
                                view.setText(((CharSequence) (obj6)));
                                return;
                            }
                            break MISSING_BLOCK_LABEL_332;
                            obj6 = (new StringBuilder(String.valueOf(obj6))).append(s).toString();
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

            
            {
                this$0 = DetailFragment.this;
                super();
            }
                    });
                }
                obj2 = new TextView(getActivity().getBaseContext());
                ((TextView) (obj2)).setPadding(2, 2, 2, 2);
                ((TextView) (obj2)).setTextSize(13F);
                ((TextView) (obj2)).setTextColor(0xff000000);
                obj3 = new SpannableString((new StringBuilder(String.valueOf(((TaskNotificationDto) (obj5)).getEntry_uname()))).append("\n").append(((TaskNotificationDto) (obj5)).getEntry_date()).toString());
                ((SpannableString) (obj3)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj3)).length(), 0);
                ((TextView) (obj2)).setText(((CharSequence) (obj3)));
                linearlayout2.addView(((View) (obj2)));
                obj3 = ((TaskNotificationDto) (obj5)).getAttr_name();
                obj2 = obj3;
                if (((TaskNotificationDto) (obj5)).getSummary() != null)
                {
                    obj2 = obj3;
                    if (((TaskNotificationDto) (obj5)).getSummary().substring(0, 1).equals("*"))
                    {
                        obj2 = "New";
                    }
                }
                if (obj2 != null)
                {
                    if (!((String) (obj2)).equalsIgnoreCase("null"));
                }
                obj2 = new TextView(getActivity().getBaseContext());
                ((TextView) (obj2)).setPadding(2, 2, 2, 2);
                ((TextView) (obj2)).setTextSize(13F);
                ((TextView) (obj2)).setTextColor(0xff000000);
                ((TextView) (obj2)).setText(new SpannableString(((TaskNotificationDto) (obj5)).getSummary()));
                linearlayout2.addView(((View) (obj2)));
                obj3 = new TableRow(getActivity().getBaseContext());
                tablelayout.addView(((View) (obj3)));
                obj2 = new LinearLayout(getActivity().getBaseContext());
                ((LinearLayout) (obj2)).setOrientation(0);
                ((LinearLayout) (obj2)).setBackgroundColor(Color.parseColor("#ffffff"));
                ((TableRow) (obj3)).addView(((View) (obj2)));
                Log.e((new StringBuilder("-seen list->")).append(((TaskNotificationDto) (obj5)).getId()).toString(), (new StringBuilder("-")).append(((TaskNotificationDto) (obj5)).getSeenPersons()).toString());
                if (((TaskNotificationDto) (obj5)).getSeenPersons() != null)
                {
                    obj3 = new TextView(getActivity().getBaseContext());
                    ((TextView) (obj3)).setPadding(2, 2, 2, 2);
                    ((TextView) (obj3)).setTextSize(9F);
                    ((TextView) (obj3)).setTag((new StringBuilder("seen")).append(((TaskNotificationDto) (obj5)).getId()).toString());
                    ((TextView) (obj3)).setTextColor(Color.parseColor("#008000"));
                    obj5 = new SpannableString((new StringBuilder("seen by: ")).append(((TaskNotificationDto) (obj5)).getSeenPersons()).toString());
                    ((SpannableString) (obj5)).setSpan(new StyleSpan(0), 0, ((SpannableString) (obj5)).length(), 0);
                    ((TextView) (obj3)).setText(((CharSequence) (obj5)));
                    ((LinearLayout) (obj2)).addView(((View) (obj3)));
                    if (obj != null)
                    {
                        ((ImageView) (obj)).setTag(obj3);
                    }
                } else
                {
                    TextView textview = new TextView(getActivity().getBaseContext());
                    textview.setPadding(2, 2, 2, 2);
                    textview.setTextSize(9F);
                    textview.setTag((new StringBuilder("seen")).append(((TaskNotificationDto) (obj5)).getId()).toString());
                    textview.setTextColor(Color.parseColor("#008000"));
                    ((LinearLayout) (obj2)).addView(textview);
                    if (obj != null)
                    {
                        ((ImageView) (obj)).setTag(textview);
                    }
                }
            }
        }
        if (true) goto _L2; else goto _L1
_L1:
        obj3 = obj2[i];
        obj = (new StringBuilder(String.valueOf(obj))).append(((String) (obj3)).substring(0, 1)).toString();
        i++;
          goto _L3
    }

    public void updateUrl(String s)
    {
        detailContainer.removeAllViews();
        back.setVisibility(4);
        back.setHeight(0);
        detailContainer.setLayoutParams(new android.widget.TableLayout.LayoutParams(-2, -2));
        webview.setLayoutParams(new android.widget.TableLayout.LayoutParams(-1, -1));
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(s);
    }












}
