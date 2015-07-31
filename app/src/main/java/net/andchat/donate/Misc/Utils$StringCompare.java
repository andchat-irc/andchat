// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import java.util.Comparator;

// Referenced classes of package net.andchat.donate.Misc:
//            Utils

public static class <init>
    implements Comparator
{

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((String)obj, (String)obj1);
    }

    public int compare(String s, String s1)
    {
        boolean flag = s.equalsIgnoreCase("Status");
        boolean flag1 = s1.equalsIgnoreCase("Status");
        if (flag && flag1)
        {
            return 0;
        }
        if (flag && !flag1)
        {
            return -1;
        }
        if (!flag && flag1)
        {
            return 1;
        } else
        {
            return s.compareToIgnoreCase(s1);
        }
    }

    private ()
    {
    }

    ( )
    {
        this();
    }
}
