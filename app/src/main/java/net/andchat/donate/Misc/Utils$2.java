// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import net.andchat.donate.Backend.IRCService;

// Referenced classes of package net.andchat.donate.Misc:
//            Utils

class val.id extends Thread
{

    private final int val$id;
    private final String val$reason;
    private final IRCService val$service;

    public void run()
    {
        String s = IRCService.sPreferences.quitReason;
        if (val$reason != null)
        {
            IRCService.sPreferences.quitReason = val$reason;
        }
        val$service.stopConnection(val$id, 1);
        if (val$reason != null)
        {
            IRCService.sPreferences.quitReason = s;
        }
    }

    rvice()
    {
        val$reason = s;
        val$service = ircservice;
        val$id = i;
        super();
    }
}
