// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;


// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

class val.chan extends Thread
{

    final val.chan this$1;
    private final String val$chan;
    private final String val$key;

    public void run()
    {
        StringBuilder stringbuilder;
        try
        {
            Thread.sleep(5000L);
        }
        catch (InterruptedException interruptedexception) { }
        try
        {
            stringbuilder = new StringBuilder();
            if (val$key != null)
            {
                stringbuilder.append("JOIN ").append(val$chan).append(" ").append(val$key).append("\r\n");
                ServerConnection.access$5(cess._mth1(this._cls1.this), stringbuilder.toString());
                return;
            }
        }
        catch (Exception exception)
        {
            return;
        }
        ServerConnection.access$5(cess._mth1(this._cls1.this), stringbuilder.append("JOIN ").append(val$chan).append("\r\n").toString());
        return;
    }

    ()
    {
        this$1 = final_;
        val$key = s;
        val$chan = String.this;
        super();
    }
}
