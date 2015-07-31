// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.view.View;
import android.widget.AdapterView;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivity

class this._cls0
    implements android.widget.ClickListener
{

    final IgnoresActivity this$0;

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        IgnoresActivity.access$2(IgnoresActivity.this, i);
        showDialog(0);
    }

    ()
    {
        this$0 = IgnoresActivity.this;
        super();
    }
}
