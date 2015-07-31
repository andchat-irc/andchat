// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;
import net.andchat.donate.View.TextStyleDialog;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow, UserListActivity

public class UserListFragment extends ListFragment
    implements android.view.ActionMode.Callback, Runnable, net.andchat.donate.Backend.Sessions.SessionManager.NickWatcher, net.andchat.donate.IRCApp.PreferenceChangeWatcher
{
    private class Adapter extends ArrayAdapter
    {

        private final List mItems;
        final UserListFragment this$0;

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            viewgroup = super.getView(i, view, viewgroup);
            view = (Holder)viewgroup.getTag();
            if (view == null)
            {
                view = (TextView)viewgroup.findViewById(0x7f080004);
                viewgroup.setTag(new Holder(view));
            } else
            {
                view = ((Holder) (view)).tv;
            }
            view.setTextSize(mTextSize);
            view.setTypeface(mFont);
            return viewgroup;
        }

        public Adapter(Context context, int i, int j, List list)
        {
            this$0 = UserListFragment.this;
            super(context, i, j, list);
            mItems = list;
        }
    }

    private static class Holder
    {

        private final TextView tv;


        public Holder(TextView textview)
        {
            tv = textview;
        }
    }


    private int mActionBarMode;
    private ActionMode mActionMode;
    ChatWindow mChat;
    private net.andchat.donate.Misc.Utils.Detector mDetector;
    private Typeface mFont;
    private Handler mHandler;
    public boolean mSaveScrollPosition;
    private int mTextSize;
    private boolean mUpdatesQueued;
    SessionManager mWindows;

    public UserListFragment()
    {
        mHandler = new Handler();
    }

    private void init()
    {
        if (mChat != null)
        {
            mWindows = mChat.pSessionManager;
            ListView listview = tryGetList();
            if (listview != null)
            {
                mWindows.addNickWatcher(this);
                listview.setFastScrollEnabled(true);
                listview.setChoiceMode(2);
                listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() );
                mDetector = new net.andchat.donate.Misc.Utils.Detector(getActivity(), net.andchat.donate.Misc.Utils.Detector.Y_DEVIATION.STRICT) {

                    final UserListFragment this$0;

                    public void onLeftToRightSwipe()
                    {
                        ((net.andchat.donate.Misc.Utils.ListSwipeListener)mChat).onLeftToRightSwipe(UserListFragment.this);
                    }

                    public void onRightToLeftSwipe()
                    {
                        ((net.andchat.donate.Misc.Utils.ListSwipeListener)mChat).onRightToLeftSwipe(UserListFragment.this);
                    }

            
            {
                this$0 = UserListFragment.this;
                super(context, y_deviation);
            }
                };
                listview.setOnTouchListener(new android.view.View.OnTouchListener() {

                    final UserListFragment this$0;
                    private final GestureDetector val$gd;

                    public boolean onTouch(View view, MotionEvent motionevent)
                    {
                        return gd.onTouchEvent(motionevent);
                    }

            
            {
                this$0 = UserListFragment.this;
                gd = gesturedetector;
                super();
            }
                });
                initAdapter();
                return;
            }
        }
    }

    private ListView tryGetList()
    {
        ListView listview;
        try
        {
            listview = getListView();
        }
        catch (IllegalStateException illegalstateexception)
        {
            return null;
        }
        return listview;
    }

    public void firstRun()
    {
        init();
    }

    public void hideActionBar()
    {
        if (mActionMode != null)
        {
            mActionMode.finish();
        }
    }

    public void initAdapter()
    {
        mUpdatesQueued = false;
        if (mActionBarMode == 1)
        {
            mUpdatesQueued = true;
            return;
        } else
        {
            mHandler.post(this);
            return;
        }
    }

    public void initAdapter(String s)
    {
        if (s.equalsIgnoreCase(mChat.pCurrentSession.getSessionName()))
        {
            initAdapter();
        }
    }

    public boolean onActionItemClicked(ActionMode actionmode, MenuItem menuitem)
    {
        int j = getListAdapter().getCount();
        StringBuilder stringbuilder = new StringBuilder();
        ListView listview = getListView();
        int i = 0;
        do
        {
            if (i >= j)
            {
                if (stringbuilder.charAt(stringbuilder.length() - 1) == ' ')
                {
                    stringbuilder.deleteCharAt(stringbuilder.length() - 1);
                }
                if (stringbuilder.charAt(stringbuilder.length() - 1) == ',')
                {
                    stringbuilder.deleteCharAt(stringbuilder.length() - 1);
                }
                Message.obtain(mChat.pFragmentBridge, menuitem.getItemId(), stringbuilder.toString()).sendToTarget();
                i = menuitem.getItemId();
                if (i == 0x7f080089 || i == 0x7f08008a)
                {
                    actionmode.finish();
                }
                return true;
            }
            if (listview.isItemChecked(i))
            {
                stringbuilder.append(UserListActivity.cleanUp(mWindows.getStatusMap(), (String)listview.getItemAtPosition(i)));
                stringbuilder.append(',').append(' ');
            }
            i++;
        } while (true);
    }

    public boolean onCreateActionMode(ActionMode actionmode, Menu menu)
    {
        actionmode.getMenuInflater().inflate(0x7f0f0008, menu);
        return true;
    }

    public void onDestroyActionMode(ActionMode actionmode)
    {
        mActionBarMode = 0;
        mActionMode = null;
        actionmode = getListView();
        int j = actionmode.getCount();
        int i = 0;
        do
        {
            if (i >= j)
            {
                if (mUpdatesQueued)
                {
                    mUpdatesQueued = false;
                    mHandler.post(this);
                }
                return;
            }
            actionmode.setItemChecked(i, false);
            i++;
        } while (true);
    }

    public void onDestroyView()
    {
        super.onDestroyView();
        ((IRCApp)mChat.getApplication()).removeWatcher(this);
        mHandler.removeCallbacks(this);
        if (mWindows != null)
        {
            mWindows.removeNickWatcher(this);
            mWindows = null;
        }
    }

    public void onNickAdded(String s, String s1)
    {
        initAdapter(s);
    }

    public void onNickChanged(String s, String s1, String s2)
    {
        initAdapter(s);
    }

    public void onNickModeChanged(String s, String s1, String s2)
    {
        initAdapter(s);
    }

    public void onNickRemoved(String s, String s1)
    {
        initAdapter(s);
    }

    public void onNicksReceived(String s)
    {
        initAdapter(s);
    }

    public void onPreferencesChanged()
    {
        mTextSize = Utils.parseInt(Utils.getPrefs(getActivity()).getString(getString(0x7f0a0015), "18"), 18);
        int i = Utils.getPrefs(getActivity()).getInt(getString(0x7f0a0031), 0);
        mFont = TextStyleDialog.TYPES[i];
        ListAdapter listadapter = getListAdapter();
        if (listadapter != null)
        {
            ((Adapter)listadapter).notifyDataSetChanged();
        }
    }

    public boolean onPrepareActionMode(ActionMode actionmode, Menu menu)
    {
        boolean flag = false;
        int i = getListView().getCheckedItemCount();
        if (i == 0)
        {
            actionmode.finish();
            return true;
        }
        actionmode.setTitle(getResources().getQuantityString(0x7f0e0005, i, new Object[] {
            Integer.valueOf(i)
        }));
        if (i == 1)
        {
            flag = true;
        }
        menu.findItem(0x7f080005).setVisible(flag);
        menu.findItem(0x7f080006).setVisible(flag);
        return true;
    }

    public void onStart()
    {
        super.onStart();
        mChat = (ChatWindow)getActivity();
        mActionBarMode = 0;
        ((IRCApp)mChat.getApplication()).addWatcher(this);
        onPreferencesChanged();
    }

    public void run()
    {
        if (mChat != null)
        {
            SessionManager sessionmanager = mWindows;
            Object obj = sessionmanager;
            if (sessionmanager == null)
            {
                obj = mChat.pSessionManager;
            }
            obj = ((SessionManager) (obj)).getNickList(mChat.pCurrentSession.getSessionName());
            if (obj != null)
            {
                sort(((List) (obj)));
                int j = 0;
                int i = 0;
                ListView listview = tryGetList();
                if (listview != null)
                {
                    if (mSaveScrollPosition)
                    {
                        j = listview.getFirstVisiblePosition();
                        View view = listview.getChildAt(0);
                        if (view == null)
                        {
                            i = 0;
                        } else
                        {
                            i = view.getTop();
                        }
                    }
                    setListAdapter(new Adapter(mChat, 0x7f03001b, 0x7f080004, ((List) (obj))));
                    if (mSaveScrollPosition)
                    {
                        listview.setSelectionFromTop(j, i);
                        return;
                    } else
                    {
                        mSaveScrollPosition = true;
                        return;
                    }
                }
            }
        }
    }

    void sort(List list)
    {
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        try
        {
            Collections.sort(list, new UserListActivity.NameComparer(mWindows.getStatusMap()));
            return;
        }
        // Misplaced declaration of an exception variable
        catch (List list)
        {
            return;
        }
    }







}
