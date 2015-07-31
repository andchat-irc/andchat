// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Sessions.SessionManager;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class this._cls1
    implements Runnable
{

    final tring this$1;

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
            ChatWindow.access$17(_fld0, getString(0x7f0a01b2), s, 1);
            i++;
        } while (true);
    }

    is._cls0()
    {
        this$1 = this._cls1.this;
        super();
    }

    // Unreferenced inner class net/andchat/donate/Activities/ChatWindow$5

/* anonymous class */
    class ChatWindow._cls5 extends Thread
    {

        final ChatWindow this$0;

        public void run()
        {
            mService.stopReconnecting(pId, 1);
            runOnUiThread(new ChatWindow._cls5._cls1());
        }


            
            {
                this$0 = ChatWindow.this;
                super();
            }
    }

}
