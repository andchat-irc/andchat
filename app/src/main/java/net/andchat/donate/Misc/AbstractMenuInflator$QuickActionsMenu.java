// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

// Referenced classes of package net.andchat.donate.Misc:
//            AbstractMenuInflator

public static class mQuickActions extends AbstractMenuInflator
{

    private final QuickAction mQuickActions;

    public void addItem(String s, int i, int j, int k)
    {
        mQuickActions.addActionItem(new ActionItem(j, s));
    }

    public boolean shouldParseItem(int i)
    {
        return i == 0;
    }

    public (QuickAction quickaction)
    {
        mQuickActions = quickaction;
    }
}
