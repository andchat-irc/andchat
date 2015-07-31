// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;


// Referenced classes of package net.andchat.donate.Backend:
//            IRCService, ServerConnection

class ion extends Thread
{

    final IRCService this$0;
    private final ServerConnection val$s;

    public void run()
    {
        val$s.stopConnection(2);
    }

    ion()
    {
        this$0 = final_ircservice;
        val$s = ServerConnection.this;
        super();
    }
}
