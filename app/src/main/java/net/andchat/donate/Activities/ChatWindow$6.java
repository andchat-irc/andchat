// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.IRCService;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class this._cls0 extends Thread
{

    final ChatWindow this$0;

    public void run()
    {
        mService.stopConnection(pId, 1);
    }

    ()
    {
        this$0 = ChatWindow.this;
        super();
    }
}
