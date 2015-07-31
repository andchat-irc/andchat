// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;

// Referenced classes of package net.andchat.donate.Activities:
//            CurrentUI

class EVIATION extends net.andchat.donate.Misc.or
{

    final CurrentUI this$0;

    public void onLeftToRightSwipe()
    {
        CurrentUI.access$0(CurrentUI.this, true);
    }

    public void onRightToLeftSwipe()
    {
    }

    EVIATION(Context context, net.andchat.donate.Misc.or.Y_DEVIATION y_deviation)
    {
        this$0 = CurrentUI.this;
        super(context, y_deviation);
    }
}
