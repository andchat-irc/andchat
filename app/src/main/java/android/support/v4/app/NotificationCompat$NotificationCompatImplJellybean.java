// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.Notification;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package android.support.v4.app:
//            NotificationCompat, NotificationCompatJellybean

static class 
    implements 
{

    public Notification build( )
    {
        NotificationCompatJellybean notificationcompatjellybean;
        notificationcompatjellybean = new NotificationCompatJellybean(., ., ., ., ., ., ., ., ., ., ., ., ., ., ., .);
         1;
        for (Iterator iterator = ..iterator(); iterator.hasNext(); notificationcompatjellybean.addAction(1., 1., 1.))
        {
            1 = ()iterator.next();
        }

        if (. == null) goto _L2; else goto _L1
_L1:
        if (!(. instanceof )) goto _L4; else goto _L3
_L3:
         = ().;
        notificationcompatjellybean.addBigTextStyle((() ())., (() ())., (() ())., (() ()).);
_L2:
        return notificationcompatjellybean.build();
_L4:
        if (. instanceof )
        {
             = ().;
            notificationcompatjellybean.addInboxStyle((() ())., (() ())., (() ())., (() ()).);
        } else
        if (. instanceof )
        {
             = ().;
            notificationcompatjellybean.addBigPictureStyle((() ())., (() ())., (() ())., (() ()).);
        }
        if (true) goto _L2; else goto _L5
_L5:
    }

    ()
    {
    }
}
