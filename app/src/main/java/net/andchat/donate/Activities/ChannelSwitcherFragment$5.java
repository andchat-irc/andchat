// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;


// Referenced classes of package net.andchat.donate.Activities:
//            ChannelSwitcherFragment

class this._cls0
    implements Runnable
{

    final ChannelSwitcherFragment this$0;

    public void run()
    {
        android.widget.ListAdapter listadapter = getListAdapter();
        if (listadapter != null)
        {
            ((annelListAdapter)listadapter).notifyDataSetChanged();
        }
    }

    annelListAdapter()
    {
        this$0 = ChannelSwitcherFragment.this;
        super();
    }
}
