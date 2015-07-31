// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.util.SparseArray;
import java.util.List;

// Referenced classes of package net.andchat.donate.Backend:
//            IRCService, ServerConnection

private class <init> extends Thread
{

    boolean interrupted;
    final StringBuilder sb;
    int sleepTime;
    final IRCService this$0;

    public void run()
    {
        setName("PingerThread");
        sb.append("PING :");
_L5:
        if (interrupted)
        {
            return;
        }
        this;
        JVM INSTR monitorenter ;
        Object obj;
        Object obj1;
        int i;
        int j;
        try
        {
            wait(sleepTime);
        }
        catch (InterruptedException interruptedexception) { }
        this;
        JVM INSTR monitorexit ;
        obj = mServers;
        obj;
        JVM INSTR monitorenter ;
        i = mServers.size();
        sb.append(System.currentTimeMillis()).append("\r\n");
        obj1 = sb.toString();
        i--;
_L6:
        if (i >= 0) goto _L2; else goto _L1
_L1:
        sb.setLength(6);
        obj;
        JVM INSTR monitorexit ;
        obj = mListeners;
        obj;
        JVM INSTR monitorenter ;
        obj1 = mListeners;
        i = ((List) (obj1)).size() - 1;
_L7:
        if (i >= 0) goto _L4; else goto _L3
_L3:
        obj;
        JVM INSTR monitorexit ;
          goto _L5
        obj1;
        obj;
        JVM INSTR monitorexit ;
        throw obj1;
        obj;
        this;
        JVM INSTR monitorexit ;
        throw obj;
_L2:
        ((ServerConnection)mServers.valueAt(i)).writeToServer(((String) (obj1)));
        i--;
          goto _L6
        obj1;
        obj;
        JVM INSTR monitorexit ;
        throw obj1;
_L4:
        j = i;
        if (((List) (obj1)).get(i) != null)
        {
            break MISSING_BLOCK_LABEL_203;
        }
        ((List) (obj1)).remove(i);
        j = i - 1;
        i = j - 1;
          goto _L7
    }

    private ()
    {
        this$0 = IRCService.this;
        super();
        sb = new StringBuilder(20);
        interrupted = false;
        sleepTime = 0x493e0;
    }

    sleepTime(sleepTime sleeptime)
    {
        this();
    }
}
