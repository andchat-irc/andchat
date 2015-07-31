// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.os.Handler;
import android.os.Message;

// Referenced classes of package net.andchat.donate.Activities:
//            Preferences

class this._cls1 extends Handler
{

    final moveDialog this$1;

    public void handleMessage(Message message)
    {
        switch (message.what)
        {
        default:
            return;

        case 1: // '\001'
        case 2: // '\002'
            owDialog(1);
            return;

        case 0: // '\0'
            moveDialog(1);
            return;
        }
    }

    ()
    {
        this$1 = this._cls1.this;
        super();
    }
}
