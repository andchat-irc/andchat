// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.widget.ListView;

// Referenced classes of package net.andchat.donate.Activities:
//            ChannelSwitcherFragment, ChatWindow

class this._cls0
    implements Runnable
{

    final ChannelSwitcherFragment this$0;

    public void run()
    {
        if (ChannelSwitcherFragment.access$0(ChannelSwitcherFragment.this) == null)
        {
            return;
        } else
        {
            setListAdapter(new annelListAdapter(ChannelSwitcherFragment.this, ChannelSwitcherFragment.access$0(ChannelSwitcherFragment.this).mService));
            getListView().setSelectionFromTop(ChannelSwitcherFragment.sIdx, ChannelSwitcherFragment.sPos);
            return;
        }
    }

    annelListAdapter()
    {
        this$0 = ChannelSwitcherFragment.this;
        super();
    }
}
