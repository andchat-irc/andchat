// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.DialogInterface;
import android.widget.ListView;

// Referenced classes of package net.andchat.donate.Activities:
//            Main

class this._cls0
    implements android.content.nterface.OnClickListener
{

    final Main this$0;

    public void onClick(DialogInterface dialoginterface, int i)
    {
        dialoginterface.dismiss();
        if (i != Main.sFilter)
        {
            Main.sFilter = i;
            mListView.clearTextFilter();
            setListAdapter(getAdapter());
            setTitle();
        }
    }

    ckListener()
    {
        this$0 = Main.this;
        super();
    }
}
