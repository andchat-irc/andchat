// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.view.View;
import net.londatiga.android.QuickAction;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivityHelper

private static class <init>
    implements 
{

    public void handleViewClick(View view)
    {
        ((QuickAction)view.getTag()).show(view, true);
    }

    public void initializeForView(View view)
    {
        android.content.Context context = view.getContext();
        if (!(context instanceof net.londatiga.android.ClickListener))
        {
            throw new RuntimeException("Context of view must implement QuickAction.OnQuickActionItemClickListener");
        } else
        {
            QuickAction quickaction = new QuickAction(context);
            (new net.andchat.donate.Misc.ionsMenu(quickaction)).addActionsFromXML(context, 0x7f0f0001);
            quickaction.setOnActionItemClickListener((net.londatiga.android.ClickListener)context);
            view.setTag(quickaction);
            return;
        }
    }

    private nu()
    {
    }

    nu(nu nu)
    {
        this();
    }
}
