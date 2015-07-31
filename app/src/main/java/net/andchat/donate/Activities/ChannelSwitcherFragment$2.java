// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.os.Handler;
import android.widget.ListView;

// Referenced classes of package net.andchat.donate.Activities:
//            ChannelSwitcherFragment

class this._cls0
    implements Runnable
{

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
            if (ChannelSwitcherFragment.access$1(ChannelSwitcherFragment.this) != null)
            {
                ChannelSwitcherFragment.access$1(ChannelSwitcherFragment.this).postDelayed(this, 1000L);
            }
            return;
        }
        break; /* Loop/switch isn't completed */
_L2:
        listview = listview1;
        ChannelSwitcherFragment.access$1(ChannelSwitcherFragment.this).removeCallbacks(this);
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
            ((annelListAdapter)listadapter).notifyDataSetInvalidated();
        }
        listview.setSelectionFromTop(ChannelSwitcherFragment.sIdx, ChannelSwitcherFragment.sPos);
        return;
    }

    annelListAdapter()
    {
        this$0 = ChannelSwitcherFragment.this;
        super();
    }
}
