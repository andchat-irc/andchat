// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;


// Referenced classes of package net.andchat.donate.Backend:
//            IRCService, ServerConnection

class val.sc extends Thread
{

    final ction this$1;
    private final ServerConnection val$sc;

    public void run()
    {
        val$sc.sendConnectionChangedMessage();
        ServerConnection serverconnection = val$sc;
        serverconnection.pReconnectCount = serverconnection.pReconnectCount - 1;
        val$sc.stopConnection(0);
    }

    ()
    {
        this$1 = final_;
        val$sc = ServerConnection.this;
        super();
    }
}
