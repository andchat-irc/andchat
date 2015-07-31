// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.os.Message;
import android.util.Log;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection, IRCService

class this._cls1
    implements r
{

    final this._cls1 this$1;

    public void uncaughtException(Thread thread, Throwable throwable)
    {
        Log.e("ServerConnection", "Exception", throwable);
        thread = ServerConnection.access$0(cess._mth4(this._cls1.this), 0x7f0a0043, new Object[] {
            cess._mth0(this._cls1.this).getAddress()
        });
        throwable = Utils.createMessage(cess._mth4(this._cls1.this).isShowingTimestamps(), thread);
        Utils.addColour(cess._mth4(this._cls1.this).isShowingTimestamps(), throwable, ServerConnection.access$1(cess._mth4(this._cls1.this)).getColourForEvent(0x7f0b002c), 0, thread.length());
        Utils.addLinks(throwable);
        cess._mth4(this._cls1.this).sendToAll(throwable, 3);
        cess._mth4(this._cls1.this).pState = 0;
        Message.obtain(ServerConnection.access$2(cess._mth4(this._cls1.this)).mHandler, 0, 3, 0, cess._mth1(this._cls1.this)).sendToTarget();
        cess._mth2(this._cls1.this);
    }

    ()
    {
        this$1 = this._cls1.this;
        super();
    }
}
