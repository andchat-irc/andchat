// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.widget.ListView;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class this._cls0
    implements Runnable
{

    final ChatWindow this$0;

    public void run()
    {
        int i = ChatWindow.access$16(ChatWindow.this).getTranscriptMode();
        ChatWindow.access$16(ChatWindow.this).setTranscriptMode(2);
        ChatWindow.access$16(ChatWindow.this).setSelection(ChatWindow.access$16(ChatWindow.this).getCount() - 1);
        ChatWindow.access$16(ChatWindow.this).setTranscriptMode(i);
    }

    ()
    {
        this$0 = ChatWindow.this;
        super();
    }
}
