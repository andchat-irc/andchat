// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;


public class IRCMessage
{

    private static final Object lock = new Object();
    private static IRCMessage pool[];
    public CharSequence message;
    public String target;
    public int val;

    private IRCMessage()
    {
    }

    public static IRCMessage obtain()
    {
label0:
        {
            synchronized (lock)
            {
                if (pool != null)
                {
                    break label0;
                }
                pool = new IRCMessage[30];
            }
            return new IRCMessage();
        }
        obj1 = pool;
        int i = 29;
_L2:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_58;
        }
        obj1 = new IRCMessage();
        obj;
        JVM INSTR monitorexit ;
        return ((IRCMessage) (obj1));
        obj1;
        obj;
        JVM INSTR monitorexit ;
        throw obj1;
        IRCMessage ircmessage;
        if (obj1[i] == null)
        {
            break MISSING_BLOCK_LABEL_76;
        }
        ircmessage = obj1[i];
        obj1[i] = null;
        obj;
        JVM INSTR monitorexit ;
        return ircmessage;
        i--;
        if (true) goto _L2; else goto _L1
_L1:
    }

    public void recycle()
    {
        Object obj = lock;
        obj;
        JVM INSTR monitorenter ;
        Object obj1;
        target = null;
        message = null;
        obj1 = pool;
        int i = 29;
          goto _L1
_L5:
        obj;
        JVM INSTR monitorexit ;
        return;
        obj1;
        obj;
        JVM INSTR monitorexit ;
        throw obj1;
_L3:
        i--;
_L1:
        if (i < 0)
        {
            continue; /* Loop/switch isn't completed */
        }
        if (obj1[i] != null) goto _L3; else goto _L2
_L2:
        obj1[i] = this;
        if (true) goto _L5; else goto _L4
_L4:
    }

    public void set(String s, CharSequence charsequence, int i)
    {
        target = s;
        message = charsequence;
        val = i;
    }

}
