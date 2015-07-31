// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import java.util.concurrent.ArrayBlockingQueue;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.IRCMessage;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

public class this._cls0 extends Handler
{

    final ChatWindow this$0;

    public void handleMessage(Message message)
    {
        message.what;
        JVM INSTR tableswitch 0 1: default 28
    //                   0 29
    //                   1 100;
           goto _L1 _L2 _L3
_L1:
        return;
_L2:
        message = (IRCMessage)blocker.poll();
_L5:
        String s;
        int i;
        if (message == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        i = ((IRCMessage) (message)).val;
        Session session = pCurrentSession;
        s = ((IRCMessage) (message)).target;
        if (session.getSessionName().equalsIgnoreCase(s))
        {
            pSessionManager.addText(message, true);
            ChatWindow.access$1(ChatWindow.this).ifyDataSetChanged();
            return;
        }
        break; /* Loop/switch isn't completed */
_L3:
        message = (IRCMessage)message.obj;
        if (true) goto _L5; else goto _L4
_L4:
        boolean flag1;
        int j;
        pSessionManager.addText(message, false);
        flag1 = false;
        j = 0;
        switch (i)
        {
        default:
            break;

        case 3: // '\003'
            break; /* Loop/switch isn't completed */

        case 2: // '\002'
            break;
        }
        break MISSING_BLOCK_LABEL_196;
_L7:
        if (flag1)
        {
            ChatWindow.access$4(ChatWindow.this, s, j, true);
        }
        if (pSessionManager.needsRefresh())
        {
            ChatWindow.access$5(ChatWindow.this, true);
            return;
        }
        if (true) goto _L1; else goto _L6
_L6:
        flag1 = true;
          goto _L7
        boolean flag;
        if (s.equals("Status"))
        {
            flag = false;
        } else
        {
            flag = true;
        }
        if (!flag && (flag || ChatWindow.access$2(ChatWindow.this).getVisibility() != 0 && ChatWindow.access$3(ChatWindow.this).getVisibility() != 0))
        {
            flag = false;
        } else
        {
            flag = true;
        }
        flag1 = flag;
        if (flag)
        {
            if (s.equals("Status") || Utils.isChannelPrefix(s.charAt(0)))
            {
                flag1 = true;
            } else
            {
                flag1 = false;
            }
            j = ((flag1) ? 1 : 0);
            flag1 = flag;
        }
          goto _L7
    }

    public ()
    {
        this$0 = ChatWindow.this;
        super();
    }
}
