// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import net.andchat.donate.Backend.ServerConnection;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class val.message
    implements Runnable
{

    final ChatWindow this$0;
    private final String val$message;

    public void run()
    {
        ChatWindow.access$18(ChatWindow.this).writeToServer(val$message);
    }

    ()
    {
        this$0 = final_chatwindow;
        val$message = String.this;
        super();
    }
}
