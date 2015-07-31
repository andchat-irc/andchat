// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

class val.chan extends Thread
{

    final val.chan this$1;
    private final String val$chan;

    public void run()
    {
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        try
        {
            wait(500L);
        }
        catch (InterruptedException interruptedexception) { }
        this;
        JVM INSTR monitorexit ;
        boolean flag = ServerConnection.access$8();
        net.andchat.donate.Backend.Sessions.  = cess._mth1(this._cls1.this).pSessionManager.getUserCount(val$chan);
        StringBuilder stringbuilder = new StringBuilder(100);
        stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0071, new Object[] {
            Integer.valueOf(.)
        }));
        if (. > 0)
        {
            stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0072, new Object[] {
                Integer.valueOf(.)
            }));
        }
        if (. > 0)
        {
            stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0073, new Object[] {
                Integer.valueOf(.)
            }));
        }
        if (. > 0)
        {
            stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0074, new Object[] {
                Integer.valueOf(.)
            }));
        }
        if (. > 0)
        {
            stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0075, new Object[] {
                Integer.valueOf(.)
            }));
        }
        if (. > 0)
        {
            stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0076, new Object[] {
                Integer.valueOf(.)
            }));
        }
        stringbuilder.append(ServerConnection.access$0(cess._mth1(this._cls1.this), 0x7f0a0077, new Object[] {
            Integer.valueOf(.)
        }));
        int i = stringbuilder.length();
        if (. > 0)
        {
            int j = ServerConnection.access$1(cess._mth1(this._cls1.this)).getColourForEvent(0x7f0b0028);
            android.text.SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), stringbuilder);
            Utils.addColour(flag, spannablestringbuilder, j, 0, i);
            cess._mth1(this._cls1.this).sendMessage(val$chan, spannablestringbuilder, 1);
        }
        return;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    ()
    {
        this$1 = final_;
        val$chan = String.this;
        super();
    }
}
