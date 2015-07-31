// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Sessions.SessionManager;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class this._cls0 extends Thread
{

    final ChatWindow this$0;

    public void run()
    {
        mService.stopReconnecting(pId, 1);
        runOnUiThread(new Runnable() {

            final ChatWindow._cls5 this$1;

            public void run()
            {
                String as[] = pSessionManager.getSessionList();
                int j = as.length;
                int i = 0;
                do
                {
                    if (i >= j)
                    {
                        return;
                    }
                    String s = as[i];
                    ChatWindow.access$17(this$0, getString(0x7f0a01b2), s, 1);
                    i++;
                } while (true);
            }

            
            {
                this$1 = ChatWindow._cls5.this;
                super();
            }
        });
    }


    _cls1.this._cls1()
    {
        this$0 = ChatWindow.this;
        super();
    }
}
