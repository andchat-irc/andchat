// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;
import net.londatiga.android.QuickAction;

public class UserListActivity extends Activity
    implements ServiceConnection, android.view.View.OnClickListener, android.widget.AdapterView.OnItemClickListener, net.londatiga.android.QuickAction.OnQuickActionItemClickListener
{
    static class NameComparer
        implements Comparator
    {

        private final net.andchat.donate.Backend.Sessions.SessionManager.StatusMap mMap;

        private int higher(char c, char c1)
        {
            boolean flag;
            byte byte0;
            char c2;
            char c3;
            char c4;
            char c5;
            char c6;
            flag = true;
            byte0 = -1;
            c5 = mMap.mapModeToPrefix('q');
            c2 = mMap.mapModeToPrefix('a');
            c3 = mMap.mapModeToPrefix('o');
            c4 = mMap.mapModeToPrefix('h');
            c6 = mMap.mapModeToPrefix('v');
            if (c != c2) goto _L2; else goto _L1
_L1:
            if (c1 == c5)
            {
                c = flag;
            } else
            {
                c = '\uFFFF';
            }
_L4:
            return c;
_L2:
            if (c != c3)
            {
                break MISSING_BLOCK_LABEL_104;
            }
            if (c1 == c5)
            {
                break; /* Loop/switch isn't completed */
            }
            c = byte0;
            if (c1 != c2) goto _L4; else goto _L3
_L3:
            return 1;
            if (c != c4)
            {
                break MISSING_BLOCK_LABEL_133;
            }
            if (c1 == c5 || c1 == c2)
            {
                break; /* Loop/switch isn't completed */
            }
            c = byte0;
            if (c1 != c3) goto _L4; else goto _L5
_L5:
            return 1;
            if (c != c6)
            {
                break MISSING_BLOCK_LABEL_168;
            }
            if (c1 == c5 || c1 == c2 || c1 == c3)
            {
                break; /* Loop/switch isn't completed */
            }
            c = byte0;
            if (c1 != c4) goto _L4; else goto _L6
_L6:
            return 1;
            return 0;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((String)obj, (String)obj1);
        }

        public int compare(String s, String s1)
        {
            char c = s.charAt(0);
            char c1 = s1.charAt(0);
            boolean flag = Utils.isStatusPrefix(mMap, c);
            boolean flag1 = Utils.isStatusPrefix(mMap, c1);
            if (flag || flag1)
            {
                if (flag && !flag1)
                {
                    return -1;
                }
                if (!flag && flag1)
                {
                    return 1;
                }
                if (flag && flag1 && c != c1)
                {
                    if (c == mMap.mapModeToPrefix('q') && c1 != mMap.mapModeToPrefix('q'))
                    {
                        return -1;
                    } else
                    {
                        return higher(c, c1);
                    }
                }
            }
            return 0;
        }

        public NameComparer(net.andchat.donate.Backend.Sessions.SessionManager.StatusMap statusmap)
        {
            mMap = statusmap;
        }
    }

    private class UserAdapter extends ArrayAdapter
        implements Filterable, SectionIndexer
    {

        private SparseIntArray mCache;
        private final Filter mFilter;
        List mItems;
        private final String mSections[];
        private final SessionManager mWindows;
        final UserListActivity this$0;

        public int getCount()
        {
            return mItems.size();
        }

        public Filter getFilter()
        {
            return mFilter;
        }

        public volatile Object getItem(int i)
        {
            return getItem(i);
        }

        public String getItem(int i)
        {
            return (String)mItems.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public int getPositionForSection(int i)
        {
            SparseIntArray sparseintarray1 = mCache;
            SparseIntArray sparseintarray = sparseintarray1;
            if (sparseintarray1 == null)
            {
                sparseintarray = new SparseIntArray();
                mCache = sparseintarray;
            }
            return Utils.getPositionForSection(i, mSections, mItems, sparseintarray);
        }

        public int getSectionForPosition(int i)
        {
            return Utils.getSectionForPosition(i, mItems, mSections);
        }

        public Object[] getSections()
        {
            return mSections;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            return super.getView(i, view, viewgroup);
        }


        public UserAdapter(Context context, int i, List list, SessionManager sessionmanager)
        {
            this$0 = UserListActivity.this;
            super(context, i, list);
            mFilter = new UserFilter(list);
            mItems = list;
            mWindows = sessionmanager;
            mSections = Utils.getSectionsFromItems(list);
        }
    }

    private class UserAdapter.UserFilter extends Filter
    {

        private final List originals;
        final UserAdapter this$1;

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            android.widget.Filter.FilterResults filterresults = new android.widget.Filter.FilterResults();
            Object obj = originals;
            if (charsequence == null || charsequence.length() == 0)
            {
                filterresults.count = ((List) (obj)).size();
                filterresults.values = obj;
                return filterresults;
            }
            boolean flag = Utils.isStatusPrefix(mWindows.getStatusMap(), charsequence.charAt(0));
            ArrayList arraylist;
            byte byte0;
            if (flag)
            {
                charsequence = Pattern.compile((new StringBuilder("(?i)^")).append(Utils.escape(charsequence)).toString()).matcher("");
            } else
            {
                charsequence = Pattern.compile((new StringBuilder("(?i)^[")).append(Utils.escape(mWindows.getStatusMap().getPrefixes())).append("]?").append(Utils.escape(charsequence)).toString()).matcher("");
            }
            if (flag)
            {
                byte0 = 10;
            } else
            {
                byte0 = 15;
            }
            arraylist = new ArrayList(byte0);
            obj = ((List) (obj)).iterator();
            do
            {
                String s;
                do
                {
                    if (!((Iterator) (obj)).hasNext())
                    {
                        filterresults.values = arraylist;
                        filterresults.count = arraylist.size();
                        return filterresults;
                    }
                    s = (String)((Iterator) (obj)).next();
                } while (!charsequence.reset(s).find());
                arraylist.add(s);
            } while (true);
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            mItems = (List)filterresults.values;
            if (filterresults.count > 0)
            {
                notifyDataSetChanged();
                return;
            } else
            {
                notifyDataSetInvalidated();
                return;
            }
        }

        public UserAdapter.UserFilter(List list)
        {
            this$1 = UserAdapter.this;
            super();
            originals = list;
        }
    }


    private StringBuilder mBuilder;
    private Comparator mComparator;
    private CharSequence mCurText;
    private Intent mIntent;
    private LinearLayout mLayout;
    private boolean mMultiSelection;
    private QuickAction mQa[];
    private Intent mResultIntent;
    private IRCService mService;
    private SessionManager mSessions;
    private Animation mSlideIn;
    private Animation mSlideOut;
    private MultiAutoCompleteTextView mTv;

    public UserListActivity()
    {
        mBuilder = new StringBuilder();
        mQa = new QuickAction[2];
    }

    static String cleanUp(net.andchat.donate.Backend.Sessions.SessionManager.StatusMap statusmap, String s)
    {
        int i = 0;
        char c = s.charAt(0);
        do
        {
            if (!Utils.isStatusPrefix(statusmap, c))
            {
                statusmap = s;
                if (i > 0)
                {
                    statusmap = s.substring(i);
                }
                return statusmap;
            }
            i++;
            c = s.charAt(i);
        } while (true);
    }

    private Intent getResultIntent()
    {
        return mResultIntent.putExtra("selected", mTv.getText()).putExtra("oldtext", mCurText);
    }

    private Animation getSlideInAnimation()
    {
        if (mSlideIn == null)
        {
            mSlideIn = AnimationUtils.loadAnimation(this, 0x7f04000e);
        }
        return mSlideIn;
    }

    private Animation getSlideOutAnimation()
    {
        if (mSlideOut == null)
        {
            mSlideOut = AnimationUtils.loadAnimation(this, 0x7f040011);
        }
        return mSlideOut;
    }

    private void hideActions()
    {
        if (mLayout == null)
        {
            mLayout = (LinearLayout)findViewById(0x7f080001);
        }
        if (mLayout.getVisibility() == 0)
        {
            mLayout.startAnimation(getSlideOutAnimation());
            mLayout.setVisibility(8);
        }
    }

    private void initActionsButtons(boolean flag)
    {
        LinearLayout linearlayout;
        LinearLayout linearlayout1;
        int i;
        if (flag)
        {
            i = 0x7f0f000a;
        } else
        {
            i = 0x7f0f0009;
        }
        linearlayout1 = mLayout;
        if (linearlayout1 != null) goto _L2; else goto _L1
_L1:
        linearlayout = (LinearLayout)findViewById(0x7f080001);
        mLayout = linearlayout;
        (new net.andchat.donate.Misc.AbstractMenuInflator.SlideInMenu(linearlayout, 0x7f030026, this)).addActionsFromXML(this, i);
        linearlayout.startAnimation(getSlideInAnimation());
        linearlayout.setVisibility(0);
_L4:
        if (linearlayout.getVisibility() == 8)
        {
            linearlayout.startAnimation(getSlideInAnimation());
            linearlayout.setVisibility(0);
        }
        return;
_L2:
        linearlayout = linearlayout1;
        if (mMultiSelection != flag)
        {
            mMultiSelection = flag;
            linearlayout1.removeAllViews();
            (new net.andchat.donate.Misc.AbstractMenuInflator.SlideInMenu(linearlayout1, 0x7f030026, this)).addActionsFromXML(this, i);
            linearlayout = linearlayout1;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        keyevent.getKeyCode();
        JVM INSTR tableswitch 4 4: default 24
    //                   4 30;
           goto _L1 _L2
_L1:
        return super.dispatchKeyEvent(keyevent);
_L2:
        if (mLayout != null && mLayout.getVisibility() == 0)
        {
            hideActions();
            return true;
        }
        setResult(-1, getResultIntent());
        if (true) goto _L1; else goto _L3
_L3:
    }

    public void onClick(View view)
    {
        int i = 1;
        view.getId();
        JVM INSTR lookupswitch 8: default 80
    //                   2131230725: 99
    //                   2131230726: 99
    //                   2131230727: 197
    //                   2131230728: 197
    //                   2131230729: 197
    //                   2131230730: 197
    //                   2131230820: 214
    //                   2131230858: 99;
           goto _L1 _L2 _L2 _L3 _L3 _L3 _L3 _L4 _L2
_L1:
        if (i != 0)
        {
            setResult(-1, getResultIntent());
            finish();
        }
        return;
_L2:
        String s = cleanUp(mSessions.getStatusMap(), mTv.getText().toString()).trim();
        Object obj = s;
        if (s.charAt(s.length() - 1) == ',')
        {
            obj = mBuilder;
            ((StringBuilder) (obj)).append(s);
            ((StringBuilder) (obj)).deleteCharAt(s.length() - 1);
            obj = ((StringBuilder) (obj)).toString();
        }
        mResultIntent.putExtra("names", ((String) (obj)));
        mResultIntent.putExtra("action", view.getId());
          goto _L5
_L3:
        mResultIntent.putExtra("action", view.getId());
          goto _L5
_L4:
        int j;
        boolean flag1;
        boolean flag = false;
        flag1 = false;
        QuickAction quickaction;
        net.andchat.donate.Misc.AbstractMenuInflator.QuickActionsMenu quickactionsmenu;
        if (mMultiSelection)
        {
            j = 1;
        } else
        {
            j = 0;
        }
        if (!mMultiSelection || mQa[j] != null) goto _L7; else goto _L6
_L6:
        i = 1;
_L8:
        if (i != 0)
        {
            quickaction = new QuickAction(this);
            quickactionsmenu = new net.andchat.donate.Misc.AbstractMenuInflator.QuickActionsMenu(quickaction);
            if (mMultiSelection)
            {
                i = 0x7f0f000a;
            } else
            {
                i = 0x7f0f0009;
            }
            quickactionsmenu.addActionsFromXML(this, i);
            quickaction.setOnActionItemClickListener(this);
            mQa[j] = quickaction;
        }
        mQa[j].show(view);
        i = ((flag) ? 1 : 0);
_L5:
        if (true) goto _L1; else goto _L7
_L7:
        i = ((flag1) ? 1 : 0);
        if (!mMultiSelection)
        {
            i = ((flag1) ? 1 : 0);
            if (mQa[j] == null)
            {
                i = 1;
            }
        }
          goto _L8
    }

    public void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        super.onCreate(bundle);
        bindService(new Intent(this, net/andchat/donate/Backend/IRCService), this, 0);
        setContentView(0x7f030012);
        mResultIntent = new Intent();
        mIntent = getIntent();
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            unbindService(this);
            return;
        }
        catch (Exception exception)
        {
            return;
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
label0:
        {
            if (view != null && (view instanceof TextView))
            {
                if (view.getId() == 0x7f080004)
                {
                    adapterview = cleanUp(mSessions.getStatusMap(), ((TextView)view).getText().toString());
                    mTv.append((new StringBuilder(String.valueOf(adapterview))).append(", ").toString());
                    mTv.requestFocus();
                } else
                {
                    adapterview = mTv.getText().toString();
                    view = ((TextView)view).getText().toString();
                    i = adapterview.lastIndexOf(view);
                    if (i <= 0)
                    {
                        adapterview = "";
                    } else
                    {
                        adapterview = adapterview.substring(0, i);
                    }
                    adapterview = (new StringBuilder(String.valueOf(adapterview))).append(cleanUp(mSessions.getStatusMap(), view)).append(", ").toString();
                    mTv.setText(adapterview);
                    mTv.setSelection(adapterview.length());
                }
                adapterview = mTv.getText().toString().trim();
                i = adapterview.indexOf(',');
                if (i == -1 || i + 1 >= adapterview.length())
                {
                    break label0;
                }
                initActionsButtons(true);
            }
            return;
        }
        initActionsButtons(false);
    }

    public void onItemClick(QuickAction quickaction, int i, int j)
    {
        switch (j)
        {
        default:
            return;

        case 2131230727: 
        case 2131230728: 
        case 2131230729: 
        case 2131230730: 
        case 2131230731: 
        case 2131230732: 
        case 2131230733: 
        case 2131230734: 
        case 2131230735: 
            mResultIntent.putExtra("action", j);
            break;
        }
        setResult(-1, getResultIntent());
        quickaction.dismiss();
        finish();
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        mMultiSelection = bundle.getBoolean("multi");
        if (bundle.getBoolean("actionsVisible"))
        {
            initActionsButtons(mMultiSelection);
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if (mLayout != null && mLayout.getVisibility() == 0)
        {
            bundle.putBoolean("actionsVisible", true);
        }
        bundle.putBoolean("multi", mMultiSelection);
    }

    public boolean onSearchRequested()
    {
        ((InputMethodManager)getSystemService("input_method")).showSoftInput(mTv, 1);
        return false;
    }

    public void onServiceConnected(ComponentName componentname, IBinder ibinder)
    {
        mService = ((net.andchat.donate.Backend.IRCService.IRCServiceBinder)ibinder).getService();
        ibinder = mIntent;
        int i = ibinder.getIntExtra("id", -1);
        mSessions = mService.getSessionManagerForId(i);
        componentname = ibinder.getStringExtra("window");
        mCurText = ibinder.getCharSequenceExtra("text");
        ibinder = mSessions.getNickList(componentname);
        mComparator = new NameComparer(mSessions.getStatusMap());
        Collections.sort(ibinder, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(ibinder, mComparator);
        i = ibinder.size();
        Object obj1 = new UserAdapter(this, 0x109000a, ibinder, mSessions);
        mTv = (MultiAutoCompleteTextView)findViewById(0x7f080050);
        Object obj = mTv;
        ((MultiAutoCompleteTextView) (obj)).setAdapter(((android.widget.ListAdapter) (obj1)));
        ((MultiAutoCompleteTextView) (obj)).setTokenizer(new android.widget.MultiAutoCompleteTextView.CommaTokenizer());
        ((MultiAutoCompleteTextView) (obj)).setOnItemClickListener(this);
        ((MultiAutoCompleteTextView) (obj)).requestFocus();
        ((MultiAutoCompleteTextView) (obj)).addTextChangedListener(new TextWatcher() {

            final UserListActivity this$0;

            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 0)
                {
                    hideActions();
                } else
                {
                    editable = editable.toString().trim();
                    int j = editable.indexOf(',');
                    if (j != -1 && j + 1 >= editable.length())
                    {
                        initActionsButtons(false);
                        return;
                    }
                }
            }

            public void beforeTextChanged(CharSequence charsequence, int j, int k, int l)
            {
            }

            public void onTextChanged(CharSequence charsequence, int j, int k, int l)
            {
            }

            
            {
                this$0 = UserListActivity.this;
                super();
            }
        });
        obj1 = (ListView)findViewById(0x102000a);
        ((ListView) (obj1)).setAdapter(new UserAdapter(this, 0x7f03001f, ibinder, mSessions));
        ((ListView) (obj1)).setOnItemClickListener(this);
        registerForContextMenu(((View) (obj)));
        setResult(-1, null);
        ibinder = Utils.getPrefs(this);
        obj = getString(0x7f0a0041);
        if (!ibinder.getBoolean(((String) (obj)), false))
        {
            Toast.makeText(this, 0x7f0a01bb, 1).show();
            ibinder.edit().putBoolean(((String) (obj)), true).commit();
        }
        setTitle(getResources().getQuantityString(0x7f0e0000, i, new Object[] {
            componentname, Integer.valueOf(i)
        }));
    }

    public void onServiceDisconnected(ComponentName componentname)
    {
    }

    public void setTitle(CharSequence charsequence)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            ((ActionBar)getWindow().getDecorView().findViewById(0x7f080012)).setTitle(charsequence);
            return;
        } else
        {
            super.setTitle(charsequence);
            return;
        }
    }


}
