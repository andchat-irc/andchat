// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.view.MenuItem;

// Referenced classes of package net.andchat.donate.View:
//            ConnectView

class this._cls0
    implements android.widget.nuItemClickListener
{

    final ConnectView this$0;

    public boolean onMenuItemClick(MenuItem menuitem)
    {
        return ((nnectViewCallback)getContext()).onMenuItemClick(menuitem);
    }

    nnectViewCallback()
    {
        this$0 = ConnectView.this;
        super();
    }
}
