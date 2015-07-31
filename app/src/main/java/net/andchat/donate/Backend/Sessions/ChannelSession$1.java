// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import java.util.Comparator;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            ChannelSession

class 
    implements Comparator
{

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((compare)obj, (compare)obj1);
    }

    public int compare(compare compare1, compare compare2)
    {
        return compare1.name.compareToIgnoreCase(compare2.name);
    }

    ()
    {
    }
}
