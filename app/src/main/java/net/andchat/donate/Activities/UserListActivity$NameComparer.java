// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import java.util.Comparator;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListActivity

static class mMap
    implements Comparator
{

    private final net.andchat.donate.Backend.Sessions. mMap;

    private int higher(char c, char c1)
    {
        boolean flag;
        byte byte0;
        char c2;
        char c3;
        char c4;
        char c5;
        char c6;
        flag = true;
        byte0 = -1;
        c5 = mMap.deToPrefix('q');
        c2 = mMap.deToPrefix('a');
        c3 = mMap.deToPrefix('o');
        c4 = mMap.deToPrefix('h');
        c6 = mMap.deToPrefix('v');
        if (c != c2) goto _L2; else goto _L1
_L1:
        if (c1 == c5)
        {
            c = flag;
        } else
        {
            c = '\uFFFF';
        }
_L4:
        return c;
_L2:
        if (c != c3)
        {
            break MISSING_BLOCK_LABEL_104;
        }
        if (c1 == c5)
        {
            break; /* Loop/switch isn't completed */
        }
        c = byte0;
        if (c1 != c2) goto _L4; else goto _L3
_L3:
        return 1;
        if (c != c4)
        {
            break MISSING_BLOCK_LABEL_133;
        }
        if (c1 == c5 || c1 == c2)
        {
            break; /* Loop/switch isn't completed */
        }
        c = byte0;
        if (c1 != c3) goto _L4; else goto _L5
_L5:
        return 1;
        if (c != c6)
        {
            break MISSING_BLOCK_LABEL_168;
        }
        if (c1 == c5 || c1 == c2 || c1 == c3)
        {
            break; /* Loop/switch isn't completed */
        }
        c = byte0;
        if (c1 != c4) goto _L4; else goto _L6
_L6:
        return 1;
        return 0;
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((String)obj, (String)obj1);
    }

    public int compare(String s, String s1)
    {
        char c = s.charAt(0);
        char c1 = s1.charAt(0);
        boolean flag = Utils.isStatusPrefix(mMap, c);
        boolean flag1 = Utils.isStatusPrefix(mMap, c1);
        if (flag || flag1)
        {
            if (flag && !flag1)
            {
                return -1;
            }
            if (!flag && flag1)
            {
                return 1;
            }
            if (flag && flag1 && c != c1)
            {
                if (c == mMap.deToPrefix('q') && c1 != mMap.deToPrefix('q'))
                {
                    return -1;
                } else
                {
                    return higher(c, c1);
                }
            }
        }
        return 0;
    }

    public (net.andchat.donate.Backend.Sessions. )
    {
        mMap = ;
    }
}
