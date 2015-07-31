// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Arrays;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            ChannelSwitcherFragment, ChatWindow

private class mIndicator extends BaseAdapter
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
            view = (TextView)LayoutInflater.from(ChannelSwitcherFragment.access$0(ChannelSwitcherFragment.this)).inflate(0x7f030015, viewgroup, false);
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
        if (viewgroup == ChannelSwitcherFragment.access$0(ChannelSwitcherFragment.this).pCurrentSession)
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
        if (ChannelSwitcherFragment.access$0(ChannelSwitcherFragment.this).getSessionManager() == sessionmanager)
        {
            if (ChannelSwitcherFragment.access$0(ChannelSwitcherFragment.this).pCurrentSession != viewgroup)
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
    //                   1 371
    //                   2 393
    //                   3 415;
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
        view.setTypeface(ChannelSwitcherFragment.access$2(ChannelSwitcherFragment.this));
        if (flag)
        {
            i = ChannelSwitcherFragment.access$3(ChannelSwitcherFragment.this) + 7;
        } else
        {
            i = ChannelSwitcherFragment.access$3(ChannelSwitcherFragment.this);
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

    public (IRCService ircservice)
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
