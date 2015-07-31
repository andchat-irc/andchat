// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow, UserListFragment, ChannelSwitcherFragment

public class CurrentUI extends ChatWindow
    implements android.view.View.OnClickListener, android.view.View.OnTouchListener, android.view.animation.Animation.AnimationListener, net.andchat.donate.Misc.Utils.ListSwipeListener
{

    private static final String KEY = net/andchat/donate/Activities/CurrentUI.getName();
    private static boolean mFirstTimeChannelList = true;
    private static boolean mFirstTimeUserlist = true;
    private static boolean sUserReqHideUserlist;
    private String PREF_CHANGES_DIALOG_SHOWN;
    private String PREF_CHANNEL_LIST_VISIBLE;
    private String PREF_USERLIST_VISIBLE;
    private boolean mAutoLoadChannelList;
    private boolean mAutoLoadUserlist;
    private GestureDetector mChannelListSwiper;
    private ImageView mChannelListToggle;
    private View mChannelListWrapper;
    private ChannelSwitcherFragment mChannelsFragment;
    private Animation mSlideInChannelList;
    private Animation mSlideInUserlist;
    private Animation mSlideOutChannelList;
    private Animation mSlideOutUserlist;
    private GestureDetector mUserListSwiper;
    private UserListFragment mUserlistFragment;
    private ImageView mUserlistToggle;
    private View mUserlistWrapper;
    private Object mViewBeingHidden;

    public CurrentUI()
    {
    }

    private void hideChannelList()
    {
        mViewBeingHidden = mChannelListWrapper;
        mChannelListWrapper.startAnimation(mSlideOutChannelList);
        mChannelListWrapper.setVisibility(8);
        mChannelListToggle.setImageResource(0x7f020014);
        Utils.getPrefs(this).edit().putBoolean(PREF_CHANNEL_LIST_VISIBLE, false).commit();
    }

    private void hideUserlist(boolean flag)
    {
        mViewBeingHidden = mUserlistWrapper;
        mUserlistFragment.hideActionBar();
        if (mUserlistWrapper.getVisibility() != 8)
        {
            break MISSING_BLOCK_LABEL_46;
        }
        getFragmentManager().beginTransaction().hide(mUserlistFragment).commitAllowingStateLoss();
_L1:
        return;
        sUserReqHideUserlist = flag;
        mUserlistWrapper.startAnimation(mSlideOutUserlist);
        mUserlistWrapper.setVisibility(8);
        mUserlistToggle.setImageResource(0x7f020011);
        if (flag)
        {
            Utils.getPrefs(this).edit().putBoolean(PREF_USERLIST_VISIBLE, false).commit();
            return;
        }
          goto _L1
        IllegalStateException illegalstateexception;
        illegalstateexception;
    }

    private void showChannelList(boolean flag)
    {
        if ((!mFirstTimeChannelList || mAutoLoadChannelList) && (flag || Utils.getPrefs(this).getBoolean(PREF_CHANNEL_LIST_VISIBLE, true)))
        {
            ChannelSwitcherFragment channelswitcherfragment = mChannelsFragment;
            if (channelswitcherfragment != null)
            {
                try
                {
                    getFragmentManager().beginTransaction().show(channelswitcherfragment).commitAllowingStateLoss();
                }
                catch (IllegalStateException illegalstateexception1)
                {
                    return;
                }
                try
                {
                    if (mChannelListWrapper.getVisibility() == 8)
                    {
                        mChannelListWrapper.startAnimation(mSlideInChannelList);
                        mChannelListWrapper.setVisibility(0);
                        mChannelListToggle.setImageResource(0x7f020011);
                    }
                    Utils.getPrefs(this).edit().putBoolean(PREF_CHANNEL_LIST_VISIBLE, true).commit();
                    return;
                }
                catch (IllegalStateException illegalstateexception)
                {
                    return;
                }
            }
        }
    }

    private void showUserlist(boolean flag)
    {
        if (mFirstTimeUserlist && !mAutoLoadUserlist)
        {
            sUserReqHideUserlist = true;
        } else
        if (pCurrentSession.hasCapability(5) && (flag || Utils.getPrefs(this).getBoolean(PREF_USERLIST_VISIBLE, true)))
        {
            mUserlistToggle.setVisibility(0);
            UserListFragment userlistfragment = mUserlistFragment;
            if (userlistfragment != null)
            {
                sUserReqHideUserlist = false;
                userlistfragment.mSaveScrollPosition = false;
                userlistfragment.hideActionBar();
                userlistfragment.initAdapter();
                try
                {
                    getFragmentManager().beginTransaction().show(mUserlistFragment).commitAllowingStateLoss();
                    if (mUserlistWrapper.getVisibility() == 8)
                    {
                        mUserlistWrapper.startAnimation(mSlideInUserlist);
                        mUserlistWrapper.setVisibility(0);
                        mUserlistToggle.setImageResource(0x7f020014);
                    }
                    Utils.getPrefs(this).edit().putBoolean(PREF_USERLIST_VISIBLE, true).commit();
                    return;
                }
                catch (IllegalStateException illegalstateexception)
                {
                    return;
                }
            }
        }
    }

    protected void loadPrefs()
    {
        byte byte0 = 8;
        super.loadPrefs();
        boolean flag = Utils.getPrefs(this).getBoolean(getString(0x7f0a0033), true);
        Object obj = super.pGallery;
        int i;
        if (flag)
        {
            i = 0;
        } else
        {
            i = 8;
        }
        ((Gallery) (obj)).setVisibility(i);
        obj = findViewById(0x7f080022);
        if (flag)
        {
            i = byte0;
        } else
        {
            i = 0;
        }
        ((View) (obj)).setVisibility(i);
    }

    public void onAnimationEnd(Animation animation)
    {
        if (mViewBeingHidden == mUserlistWrapper)
        {
            getFragmentManager().beginTransaction().hide(mUserlistFragment).commitAllowingStateLoss();
            return;
        }
        try
        {
            if (mViewBeingHidden == mChannelListWrapper)
            {
                getFragmentManager().beginTransaction().hide(mChannelsFragment).commitAllowingStateLoss();
                return;
            }
        }
        // Misplaced declaration of an exception variable
        catch (Animation animation) { }
        return;
    }

    public void onAnimationRepeat(Animation animation)
    {
    }

    public void onAnimationStart(Animation animation)
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        case 2131230758: 
        default:
            super.onClick(view);
            return;

        case 2131230759: 
            if (!sUserReqHideUserlist)
            {
                hideUserlist(true);
                sUserReqHideUserlist = true;
                return;
            } else
            {
                mFirstTimeUserlist = false;
                showUserlist(true);
                sUserReqHideUserlist = false;
                return;
            }

        case 2131230757: 
            break;
        }
        if (mChannelListWrapper.getVisibility() == 0)
        {
            hideChannelList();
            return;
        } else
        {
            mFirstTimeChannelList = false;
            showChannelList(true);
            return;
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        PREF_USERLIST_VISIBLE = getString(0x7f0a003e);
        PREF_CHANNEL_LIST_VISIBLE = getString(0x7f0a003f);
        PREF_CHANGES_DIALOG_SHOWN = getString(0x7f0a0040);
        mUserlistWrapper = findViewById(0x7f080028);
        mChannelListWrapper = findViewById(0x7f080024);
        mUserlistToggle = (ImageView)findViewById(0x7f080027);
        mUserlistToggle.setOnClickListener(this);
        mUserlistToggle.setOnTouchListener(this);
        mUserlistToggle.setVisibility(8);
        mChannelListToggle = (ImageView)findViewById(0x7f080025);
        mChannelListToggle.setOnClickListener(this);
        mChannelListToggle.setOnTouchListener(this);
        mChannelListToggle.setVisibility(0);
        mSlideInUserlist = AnimationUtils.loadAnimation(this, 0x7f040010);
        mSlideOutUserlist = AnimationUtils.loadAnimation(this, 0x7f040013);
        mSlideOutUserlist.setAnimationListener(this);
        mSlideInChannelList = AnimationUtils.loadAnimation(this, 0x7f04000f);
        mSlideOutChannelList = AnimationUtils.loadAnimation(this, 0x7f040012);
        mSlideOutChannelList.setAnimationListener(this);
        mAutoLoadUserlist = getResources().getBoolean(0x7f0c0001);
        mAutoLoadChannelList = getResources().getBoolean(0x7f0c0002);
        mChannelListSwiper = new GestureDetector(this, new net.andchat.donate.Misc.Utils.Detector(this, net.andchat.donate.Misc.Utils.Detector.Y_DEVIATION.STRICT) {

            final CurrentUI this$0;

            public void onLeftToRightSwipe()
            {
                showChannelList(true);
            }

            public void onRightToLeftSwipe()
            {
            }

            
            {
                this$0 = CurrentUI.this;
                super(context, y_deviation);
            }
        });
        mUserListSwiper = new GestureDetector(this, new net.andchat.donate.Misc.Utils.Detector(this, net.andchat.donate.Misc.Utils.Detector.Y_DEVIATION.STRICT) {

            final CurrentUI this$0;

            public void onLeftToRightSwipe()
            {
                mUserlistToggle.setPressed(false);
            }

            public void onRightToLeftSwipe()
            {
                mUserlistToggle.setPressed(false);
                showUserlist(true);
            }

            
            {
                this$0 = CurrentUI.this;
                super(context, y_deviation);
            }
        });
        boolean flag;
        if (Utils.getPrefs(this).getBoolean(PREF_USERLIST_VISIBLE, true))
        {
            flag = false;
        } else
        {
            flag = true;
        }
        sUserReqHideUserlist = flag;
    }

    protected Dialog onCreateDialog(int i)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(0x7f0a01b8);
        builder.setIcon(0x108009b);
        builder.setMessage(Html.fromHtml(getString(0x7f0a01b9)));
        builder.setNeutralButton(0x7f0a01d5, new android.content.DialogInterface.OnClickListener() {

            final CurrentUI this$0;

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            
            {
                this$0 = CurrentUI.this;
                super();
            }
        });
        return builder.create();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0f0004, menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        mFirstTimeUserlist = false;
        mFirstTimeChannelList = false;
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        super.onItemClick(adapterview, view, i, l);
        if (!pCurrentSession.hasCapability(5) || !pCurrentSession.isActive())
        {
            hideUserlist(false);
            mUserlistToggle.setVisibility(4);
        } else
        {
            mUserlistToggle.setVisibility(0);
            if (!sUserReqHideUserlist)
            {
                showUserlist(false);
                return;
            }
        }
    }

    public void onLeftToRightSwipe(Fragment fragment)
    {
        if (fragment instanceof UserListFragment)
        {
            hideUserlist(true);
            return;
        } else
        {
            boolean flag = fragment instanceof ChannelSwitcherFragment;
            return;
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.updateMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        sUserReqHideUserlist = bundle.getBoolean(KEY, false);
    }

    public void onRightToLeftSwipe(Fragment fragment)
    {
        if (!(fragment instanceof UserListFragment) && (fragment instanceof ChannelSwitcherFragment))
        {
            hideChannelList();
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(KEY, sUserReqHideUserlist);
    }

    protected void onStart()
    {
        super.onStart();
        mUserlistFragment = (UserListFragment)getFragmentManager().findFragmentById(0x7f080074);
        mChannelsFragment = (ChannelSwitcherFragment)getFragmentManager().findFragmentById(0x7f080051);
        if (!Utils.getPrefs(this).getBoolean(PREF_CHANGES_DIALOG_SHOWN, false))
        {
            showDialog(0);
            Utils.getPrefs(this).edit().putBoolean(PREF_CHANGES_DIALOG_SHOWN, true).commit();
        }
    }

    public boolean onTouch(View view, MotionEvent motionevent)
    {
        switch (view.getId())
        {
        case 2131230758: 
        default:
            return false;

        case 2131230757: 
            return mChannelListSwiper.onTouchEvent(motionevent);

        case 2131230759: 
            return mUserListSwiper.onTouchEvent(motionevent);
        }
    }

    void onUserlistMenuClicked()
    {
        if (!sUserReqHideUserlist)
        {
            hideUserlist(true);
            sUserReqHideUserlist = true;
            return;
        } else
        {
            mFirstTimeUserlist = false;
            showUserlist(true);
            sUserReqHideUserlist = false;
            return;
        }
    }

    void performInitialization()
    {
        if (mUserlistFragment != null)
        {
            mUserlistFragment.firstRun();
        }
        if (mChannelsFragment != null)
        {
            mChannelsFragment.firstRun();
            showChannelList(false);
        }
    }




}
