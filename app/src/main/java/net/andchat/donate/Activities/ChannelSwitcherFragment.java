// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Arrays;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;
import net.andchat.donate.View.TextStyleDialog;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

public class ChannelSwitcherFragment extends ListFragment
    implements android.widget.AbsListView.OnScrollListener, android.widget.AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemLongClickListener, net.andchat.donate.Backend.Sessions.SessionManager.GlobalEventWatcher, net.andchat.donate.IRCApp.PreferenceChangeWatcher
{
    private class ChannelListAdapter extends BaseAdapter
    {

        private final Drawable mIndicator;
        private final SessionManager mManagers[];
        private final int mSessionCounts[];
        private final String mSessionNames[];
        final ChannelSwitcherFragment this$0;

        public SessionManager findManagerForPosition(int i)
        {
            SessionManager asessionmanager[] = mManagers;
            int l = asessionmanager.length;
            int k = 0;
            int j = 0;
            do
            {
                if (j >= l)
                {
                    return null;
                }
                k += mSessionCounts[j];
                if (i < k)
                {
                    return asessionmanager[j];
                }
                j++;
            } while (true);
        }

        public int getCount()
        {
            return mSessionNames.length;
        }

        public volatile Object getItem(int i)
        {
            return getItem(i);
        }

        public String getItem(int i)
        {
            return mSessionNames[i];
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            SessionManager sessionmanager = findManagerForPosition(i);
            Object obj;
            StringBuilder stringbuilder;
            boolean flag;
            boolean flag1;
            boolean flag2;
            int j;
            if (view == null)
            {
                view = (TextView)LayoutInflater.from(mChat).inflate(0x7f030015, viewgroup, false);
            } else
            {
                view = (TextView)view;
            }
            flag = false;
            obj = sessionmanager.getSession(mSessionNames[i]);
            viewgroup = ((ViewGroup) (obj));
            if (obj == null)
            {
                viewgroup = sessionmanager.getDefaultSession();
            }
            if (viewgroup.getType() == 0)
            {
                flag = true;
                obj = sessionmanager.getProfile().getName();
            } else
            {
                obj = mSessionNames[i];
            }
            j = viewgroup.getCurrentTextType();
            if (viewgroup.isActive())
            {
                flag1 = false;
            } else
            {
                flag1 = true;
            }
            stringbuilder = new StringBuilder();
            if (viewgroup == mChat.pCurrentSession)
            {
                flag2 = true;
            } else
            {
                flag2 = false;
            }
            if (!flag2)
            {
                if (!flag)
                {
                    stringbuilder.append("  ");
                }
            } else
            if (flag)
            {
                stringbuilder.append(" ");
            } else
            {
                stringbuilder.append("  ");
            }
            i = 1;
            if (mChat.getSessionManager() == sessionmanager)
            {
                if (mChat.pCurrentSession != viewgroup)
                {
                    i = 1;
                } else
                {
                    i = 0;
                }
            }
            if (flag1)
            {
                stringbuilder.append("(");
            }
            stringbuilder.append(((String) (obj)));
            if (flag1)
            {
                stringbuilder.append(")");
            }
            viewgroup = new SpannableStringBuilder(stringbuilder);
            if (i == 0) goto _L2; else goto _L1
_L1:
            j;
            JVM INSTR tableswitch 1 3: default 252
        //                       1 371
        //                       2 393
        //                       3 415;
               goto _L3 _L4 _L5 _L6
_L3:
            break; /* Loop/switch isn't completed */
_L6:
            break MISSING_BLOCK_LABEL_415;
_L2:
            if (flag2)
            {
                view.setCompoundDrawablesWithIntrinsicBounds(mIndicator, null, null, null);
            } else
            {
                view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            view.setText(viewgroup);
            view.setTypeface(mFont);
            if (flag)
            {
                i = mTextSize + 7;
            } else
            {
                i = mTextSize;
            }
            view.setTextSize(i);
            return view;
_L4:
            Utils.addColour(false, viewgroup, Colours.getInstance().getColourForEvent(0x7f0b000a), 0, viewgroup.length());
              goto _L2
_L5:
            Utils.addColour(false, viewgroup, Colours.getInstance().getColourForEvent(0x7f0b000c), 0, viewgroup.length());
              goto _L2
            Utils.addColour(false, viewgroup, Colours.getInstance().getColourForEvent(0x7f0b000b), 0, viewgroup.length());
              goto _L2
        }

        public ChannelListAdapter(IRCService ircservice)
        {
            SessionManager asessionmanager[];
            int ai[];
            int ai1[];
            int i;
            int j;
            int k;
            this$0 = ChannelSwitcherFragment.this;
            super();
            ai1 = ircservice.getActiveServerIds();
            k = ai1.length;
            asessionmanager = new SessionManager[k];
            ai = new int[k];
            j = 0;
            i = 0;
_L5:
            if (i < k) goto _L2; else goto _L1
_L1:
            Arrays.sort(asessionmanager);
            i = 0;
_L6:
            if (i < k) goto _L4; else goto _L3
_L3:
            ircservice = new String[j];
            j = 0;
            k = asessionmanager.length;
            i = 0;
_L7:
            if (i >= k)
            {
                mManagers = asessionmanager;
                mSessionCounts = ai;
                mSessionNames = ircservice;
                mIndicator = getResources().getDrawable(0x7f020035);
                return;
            }
            break MISSING_BLOCK_LABEL_166;
_L2:
            asessionmanager[i] = ircservice.getSessionManagerForId(ai1[i]);
            i++;
              goto _L5
_L4:
            int l = asessionmanager[i].getSessionCount();
            ai[i] = l;
            j += l;
            i++;
              goto _L6
            String as[] = asessionmanager[i].getSessionList();
            Arrays.sort(as, Utils.STRING_COMPARE);
            System.arraycopy(as, 0, ircservice, j, as.length);
            j += as.length;
            i++;
              goto _L7
        }
    }

    class RunCommand
    {

        public final String command;
        public final int id;
        public final String params;
        final ChannelSwitcherFragment this$0;

        public RunCommand(int i, String s, String s1)
        {
            this$0 = ChannelSwitcherFragment.this;
            super();
            id = i;
            params = s1;
            command = s;
        }
    }

    class SwitchChannelInfo
    {

        public final int id;
        public final SessionManager manager;
        public final String sessionName;
        final ChannelSwitcherFragment this$0;

        public SwitchChannelInfo(SessionManager sessionmanager, String s, int i)
        {
            this$0 = ChannelSwitcherFragment.this;
            super();
            manager = sessionmanager;
            sessionName = s;
            id = i;
        }
    }


    private static final String TAG = net/andchat/donate/Activities/ChannelSwitcherFragment.getName();
    static int sIdx;
    static int sPos;
    private ChatWindow mChat;
    private int mCurPos;
    private net.andchat.donate.Misc.Utils.Detector mDetector;
    private Typeface mFont;
    private Handler mHandler;
    private final Runnable mNotifyDatasetInvalidated = new Runnable() {

        final ChannelSwitcherFragment this$0;

        public void run()
        {
            ListView listview;
            boolean flag;
            flag = false;
            listview = null;
            ListView listview1 = getListView();
            if (listview1 != null) goto _L2; else goto _L1
_L1:
            flag = true;
            listview = listview1;
_L4:
            if (flag)
            {
                if (mHandler != null)
                {
                    mHandler.postDelayed(this, 1000L);
                }
                return;
            }
            break; /* Loop/switch isn't completed */
_L2:
            listview = listview1;
            mHandler.removeCallbacks(this);
            listview = listview1;
            continue; /* Loop/switch isn't completed */
            IllegalStateException illegalstateexception;
            illegalstateexception;
            flag = true;
            if (true) goto _L4; else goto _L3
_L3:
            android.widget.ListAdapter listadapter = getListAdapter();
            if (listadapter != null)
            {
                ((ChannelListAdapter)listadapter).notifyDataSetInvalidated();
            }
            listview.setSelectionFromTop(ChannelSwitcherFragment.sIdx, ChannelSwitcherFragment.sPos);
            return;
        }

            
            {
                this$0 = ChannelSwitcherFragment.this;
                super();
            }
    };
    private final Runnable mSetAdapter = new Runnable() {

        final ChannelSwitcherFragment this$0;

        public void run()
        {
            if (mChat == null)
            {
                return;
            } else
            {
                setListAdapter(new ChannelListAdapter(mChat.mService));
                getListView().setSelectionFromTop(ChannelSwitcherFragment.sIdx, ChannelSwitcherFragment.sPos);
                return;
            }
        }

            
            {
                this$0 = ChannelSwitcherFragment.this;
                super();
            }
    };
    private int mTextSize;

    public ChannelSwitcherFragment()
    {
        mHandler = new Handler();
    }

    private void init()
    {
        if (mChat == null)
        {
            return;
        }
        SessionManager.addEventWatcher(this);
        ListView listview;
        try
        {
            listview = getListView();
        }
        catch (IllegalStateException illegalstateexception)
        {
            return;
        }
        registerForContextMenu(listview);
        listview.setFastScrollEnabled(true);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
        listview.setOnScrollListener(this);
        mDetector = new net.andchat.donate.Misc.Utils.Detector(mChat, net.andchat.donate.Misc.Utils.Detector.Y_DEVIATION.STRICT) {

            final ChannelSwitcherFragment this$0;

            public void onLeftToRightSwipe()
            {
                ((net.andchat.donate.Misc.Utils.ListSwipeListener)mChat).onLeftToRightSwipe(ChannelSwitcherFragment.this);
            }

            public void onRightToLeftSwipe()
            {
                ((net.andchat.donate.Misc.Utils.ListSwipeListener)mChat).onRightToLeftSwipe(ChannelSwitcherFragment.this);
            }

            
            {
                this$0 = ChannelSwitcherFragment.this;
                super(context, y_deviation);
            }
        };
        listview.setOnTouchListener(new android.view.View.OnTouchListener() {

            final ChannelSwitcherFragment this$0;
            private final GestureDetector val$gd;

            public boolean onTouch(View view, MotionEvent motionevent)
            {
                return gd.onTouchEvent(motionevent);
            }

            
            {
                this$0 = ChannelSwitcherFragment.this;
                gd = gesturedetector;
                super();
            }
        });
        post(mSetAdapter, 0);
    }

    private void post(Runnable runnable, int i)
    {
        if (mHandler == null)
        {
            return;
        }
        if (i > 0)
        {
            mHandler.postDelayed(runnable, i);
            return;
        } else
        {
            mHandler.post(runnable);
            return;
        }
    }

    void firstRun()
    {
        init();
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        SessionManager sessionmanager;
        String s;
        ListView listview = getListView();
        sessionmanager = ((ChannelListAdapter)listview.getAdapter()).findManagerForPosition(mCurPos);
        s = (String)listview.getItemAtPosition(mCurPos);
        menuitem.getItemId();
        JVM INSTR tableswitch 2131230855 2131230856: default 60
    //                   2131230855 70
    //                   2131230856 111;
           goto _L1 _L2 _L3
_L1:
        throw new RuntimeException("Unknown command");
_L2:
        Object obj = "/clear";
_L5:
        obj = new RunCommand(sessionmanager.getId(), ((String) (obj)), s);
        Message.obtain(mChat.pFragmentBridge, menuitem.getItemId(), obj).sendToTarget();
        return true;
_L3:
        obj = "/wc";
        if (true) goto _L5; else goto _L4
_L4:
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        contextmenuinfo = (ListView)view;
        view = ((ChannelListAdapter)contextmenuinfo.getAdapter()).findManagerForPosition(mCurPos);
        contextmenuinfo = (String)contextmenuinfo.getItemAtPosition(mCurPos);
        getActivity().getMenuInflater().inflate(0x7f0f0007, contextmenu);
        boolean flag = contextmenuinfo.equalsIgnoreCase("Status");
        contextmenu.setHeaderTitle((new StringBuilder(String.valueOf(contextmenuinfo))).append(" /").append(view.getProfile().getName()).toString());
        contextmenu = contextmenu.findItem(0x7f080088);
        if (flag)
        {
            contextmenu.setEnabled(false);
            return;
        } else
        {
            contextmenu.setEnabled(true);
            return;
        }
    }

    public void onCurrentSessionChanged(int i, String s)
    {
        post(mNotifyDatasetInvalidated, 0);
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        SessionManager.removeEventWatcher(this);
        mHandler.removeCallbacks(mSetAdapter);
        mHandler.removeCallbacks(mNotifyDatasetInvalidated);
        mHandler = null;
        ((IRCApp)mChat.getApplication()).removeWatcher(this);
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        if (mDetector.haveSwipeAction())
        {
            return;
        } else
        {
            adapterview = (ChannelListAdapter)adapterview.getAdapter();
            view = adapterview.findManagerForPosition(i);
            adapterview = new SwitchChannelInfo(view, adapterview.getItem(i), view.getId());
            Message.obtain(mChat.pFragmentBridge, 8, adapterview).sendToTarget();
            return;
        }
    }

    public boolean onItemLongClick(AdapterView adapterview, View view, int i, long l)
    {
        mCurPos = i;
        return mDetector.haveSwipeAction();
    }

    public void onMessageReceived(String s, int i, int j)
    {
        if (j != mChat.pId || !s.equalsIgnoreCase(mChat.pCurrentSession.getSessionName()))
        {
            if ((s = mChat.mService.getSessionManagerForId(j).getSession(s)) == null || s.getCurrentTextType() != i)
            {
                post(mNotifyDatasetInvalidated, 0);
                return;
            }
        }
    }

    public void onPreferencesChanged()
    {
        int i = Utils.parseInt(Utils.getPrefs(getActivity()).getString(getString(0x7f0a0016), "18"), 18);
        int j = Utils.getPrefs(getActivity()).getInt(getString(0x7f0a0032), 0);
        Typeface typeface = TextStyleDialog.TYPES[j];
        if (i != mTextSize || typeface != mFont)
        {
            mTextSize = i;
            mFont = typeface;
            mHandler.post(new Runnable() {

                final ChannelSwitcherFragment this$0;

                public void run()
                {
                    android.widget.ListAdapter listadapter = getListAdapter();
                    if (listadapter != null)
                    {
                        ((ChannelListAdapter)listadapter).notifyDataSetChanged();
                    }
                }

            
            {
                this$0 = ChannelSwitcherFragment.this;
                super();
            }
            });
        }
    }

    public void onScroll(AbsListView abslistview, int i, int j, int k)
    {
        k = 0;
        if (j == 0)
        {
            return;
        }
        sIdx = i;
        abslistview = getListView().getChildAt(0);
        if (abslistview == null)
        {
            i = k;
        } else
        {
            i = abslistview.getTop();
        }
        sPos = i;
    }

    public void onScrollStateChanged(AbsListView abslistview, int i)
    {
    }

    public void onSessionActivated(String s, int i)
    {
        post(mNotifyDatasetInvalidated, 0);
    }

    public void onSessionAdded(String s, int i, int j)
    {
        post(mSetAdapter, 0);
    }

    public void onSessionDeactivated(String s, int i)
    {
        post(mNotifyDatasetInvalidated, 0);
    }

    public void onSessionManagerDeleted(int i)
    {
        post(mSetAdapter, 500);
    }

    public void onSessionRemoved(String s, int i)
    {
        post(mSetAdapter, 0);
    }

    public void onSessionRenamed(String s, String s1)
    {
        post(mSetAdapter, 0);
    }

    public void onSessionTextStateCleared()
    {
        post(mNotifyDatasetInvalidated, 0);
    }

    public void onStart()
    {
        super.onStart();
        mChat = (ChatWindow)getActivity();
        if (!(mChat instanceof net.andchat.donate.Misc.Utils.ListSwipeListener))
        {
            throw new RuntimeException((new StringBuilder("Activity (")).append(mChat).append(") must implement ListSwipeListener").toString());
        } else
        {
            ((IRCApp)mChat.getApplication()).addWatcher(this);
            onPreferencesChanged();
            return;
        }
    }





}
