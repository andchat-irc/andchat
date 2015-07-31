// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.os.Handler;
import android.os.Message;
import android.widget.Gallery;
import android.widget.TextView;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.IRCApp;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

private class <init> extends Handler
{

    final ChatWindow this$0;

    public void handleMessage(Message message)
    {
        message.what;
        JVM INSTR tableswitch 1 7: default 48
    //                   1 49
    //                   2 78
    //                   3 163
    //                   4 94
    //                   5 186
    //                   6 277
    //                   7 318;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
        return;
_L2:
        addSession(message.obj.toString(), true);
        if (!IRCApp.LEGACY_VERSION)
        {
            ChatWindow.access$6(ChatWindow.this);
            return;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        addSession(message.obj.toString(), false);
        return;
_L5:
        int i;
label0:
        {
            message = message.obj.toString();
            i = ChatWindow.access$7(ChatWindow.this).etPositionOf(message);
            if (i == -1)
            {
                continue; /* Loop/switch isn't completed */
            }
            ChatWindow.access$5(ChatWindow.this, false);
            int k = i - 1;
            if (k >= 0)
            {
                i = k;
                if (k < ChatWindow.access$7(ChatWindow.this).etCount())
                {
                    break label0;
                }
            }
            i = 0;
        }
        selectAndClick(i, true);
        return;
_L4:
        ChatWindow.access$8(ChatWindow.this, message.obj.toString());
        ChatWindow.access$9(ChatWindow.this, true);
        return;
_L6:
        if (message.obj.toString().equals(pCurrentSession.getSessionName()) && pCurrentSession.getType() == 1)
        {
            message = pCurrentSession.getOwnStatus();
            if (message.length() > 0 && ChatWindow.access$10(ChatWindow.this).getVisibility() != 0)
            {
                ChatWindow.access$10(ChatWindow.this).setVisibility(0);
            }
            ChatWindow.access$10(ChatWindow.this).setText(message);
            return;
        }
        if (true) goto _L1; else goto _L7
_L7:
        ChatWindow.access$5(ChatWindow.this, true);
        int j = pGallery.getSelectedItemPosition();
        pCurrentSession = (Session)ChatWindow.access$7(ChatWindow.this).etItem(j);
        return;
_L8:
        if (pCurrentSession.getSessionName().equalsIgnoreCase(message.obj.toString()))
        {
            ChatWindow.access$1(ChatWindow.this).notifyDataSetChanged();
            return;
        }
        if (true) goto _L1; else goto _L9
_L9:
    }

    private ()
    {
        this$0 = ChatWindow.this;
        super();
    }

    this._cls0(this._cls0 _pcls0)
    {
        this();
    }
}
