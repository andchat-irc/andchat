// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.IRCService;

// Referenced classes of package net.andchat.donate.Activities:
//            Main

class val.id extends Thread
{

    final Main this$0;
    private final int val$id;

    public void run()
    {
        net.andchat.donate.Backend.ServerConnection serverconnection = pService.getServer(val$id, false, null, null);
        pService.stopConnection(val$id, 1);
        if (serverconnection != null)
        {
            pService.remove(serverconnection);
        }
        pService.cleanUpIfRequired(val$id);
    }

    ()
    {
        this$0 = final_main;
        val$id = I.this;
        super();
    }
}
