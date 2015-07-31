// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import java.io.File;
import java.util.Comparator;

// Referenced classes of package net.andchat.donate.Activities:
//            Preferences

class 
    implements Comparator
{

    public int compare(File file, File file1)
    {
        long l = file.lastModified();
        long l1 = file1.lastModified();
        if (l > l1)
        {
            return -1;
        }
        return l >= l1 ? 0 : 1;
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((File)obj, (File)obj1);
    }

    ()
    {
    }
}
