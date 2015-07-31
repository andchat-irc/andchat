// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivityHelper

private static class <init>
    implements <init>
{

    public void handleViewClick(View view)
    {
        PopupMenu popupmenu1 = (PopupMenu)view.getTag();
        PopupMenu popupmenu = popupmenu1;
        if (popupmenu1 == null)
        {
            final Context c = view.getContext();
            popupmenu = new PopupMenu(c, view);
            popupmenu.getMenuInflater().inflate(0x7f0f0001, popupmenu.getMenu());
            popupmenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {

                final IgnoresActivityHelper.Current this$1;
                private final Context val$c;

                public boolean onMenuItemClick(MenuItem menuitem)
                {
                    return ((IgnoresActivityHelper.PopupMenuCallback)c).onMenuItemClick(menuitem);
                }

            
            {
                this$1 = IgnoresActivityHelper.Current.this;
                c = context;
                super();
            }
            });
            view.setTag(popupmenu);
        }
        popupmenu.show();
    }

    public void initializeForView(View view)
    {
        if (!(view.getContext() instanceof uCallback))
        {
            throw new RuntimeException("Context of view must implement PopupMenuCallback");
        } else
        {
            return;
        }
    }

    private _cls1.val.c()
    {
    }

    _cls1.val.c(_cls1.val.c c)
    {
        this();
    }
}
