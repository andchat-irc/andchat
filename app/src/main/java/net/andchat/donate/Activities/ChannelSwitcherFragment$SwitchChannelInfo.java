// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.Sessions.SessionManager;

// Referenced classes of package net.andchat.donate.Activities:
//            ChannelSwitcherFragment

class id
{

    public final int id;
    public final SessionManager manager;
    public final String sessionName;
    final ChannelSwitcherFragment this$0;

    public (SessionManager sessionmanager, String s, int i)
    {
        this$0 = ChannelSwitcherFragment.this;
        super();
        manager = sessionmanager;
        sessionName = s;
        id = i;
    }
}
