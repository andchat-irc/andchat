// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import net.andchat.donate.Backend.Sessions.SessionManager;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

class val.names extends Thread
{

    final val.names this$1;
    private final String val$channel;
    private final String val$names;

    public void run()
    {
        cess._mth1(this._cls1.this).pSessionManager.addNewNames(cess._mth1(this._cls1.this).pNick, val$channel, val$names);
    }

    ()
    {
        this$1 = final_;
        val$channel = s;
        val$names = String.this;
        super();
    }
}
