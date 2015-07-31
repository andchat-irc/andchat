// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.view.MenuItem;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivityHelper

class val.c
    implements android.widget.r
{

    final allback.onMenuItemClick this$1;
    private final Context val$c;

    public boolean onMenuItemClick(MenuItem menuitem)
    {
        return ((allback)val$c).onMenuItemClick(menuitem);
    }

    allback()
    {
        this$1 = final_allback;
        val$c = Context.this;
        super();
    }
}
