// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;


// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

class this._cls0
    implements Runnable
{

    final ServerConnection this$0;

    public void run()
    {
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        try
        {
            wait(600L);
        }
        catch (InterruptedException interruptedexception) { }
        this;
        JVM INSTR monitorexit ;
        removeServerConnectionFlag(1);
        return;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    ()
    {
        this$0 = ServerConnection.this;
        super();
    }
}
