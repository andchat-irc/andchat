// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;


// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            Session

public static class 
    implements Comparable
{

    public int admin;
    public int hop;
    public int normal;
    public int op;
    public int owner;
    public int total;
    public int voice;

    public volatile int compareTo(Object obj)
    {
        return compareTo((compareTo)obj);
    }

    public int compareTo(compareTo compareto)
    {
        while (compareto == null || compareto.total != total || compareto.owner != owner || compareto.admin != admin || compareto.op != op || compareto.hop != hop || compareto.voice != voice || compareto.normal != normal) 
        {
            return -1;
        }
        return 0;
    }

    public ()
    {
    }
}
