// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;


// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class val.jumpToIt
    implements Runnable
{

    final ChatWindow this$0;
    private final boolean val$jumpToIt;
    private final String val$tag;

    public void run()
    {
        if (pSessionManager != null)
        {
            addSession(val$tag, val$jumpToIt);
        }
    }

    ()
    {
        this$0 = final_chatwindow;
        val$tag = s;
        val$jumpToIt = Z.this;
        super();
    }
}
