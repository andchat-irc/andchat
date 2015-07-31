// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.IRCMessage;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class val.m
    implements Runnable
{

    final ChatWindow this$0;
    private final IRCMessage val$m;

    public void run()
    {
        pSessionManager.addText(val$m, false);
    }

    Manager()
    {
        this$0 = final_chatwindow;
        val$m = IRCMessage.this;
        super();
    }
}
