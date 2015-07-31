// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

class this._cls1 extends Handler
{

    final this._cls1 this$1;

    public void handleMessage(Message message)
    {
        ServerConnection.access$5(cess._mth1(this._cls1.this), (String)message.obj);
    }

    (Looper looper)
    {
        this$1 = this._cls1.this;
        super(looper);
    }
}
