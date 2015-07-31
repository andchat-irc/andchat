// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.DialogInterface;
import net.andchat.donate.Backend.Ignores;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivity

class val.ii
    implements android.content.ClickListener
{

    final IgnoresActivity this$0;
    private final Ignores val$i;
    private final net.andchat.donate.Backend. val$ii;

    public void onClick(DialogInterface dialoginterface, int j)
    {
        val$i.addOrUpdateIgnore(val$ii.nick, val$ii.ident, val$ii.hostname, IgnoresActivity.access$3(IgnoresActivity.this));
        dialoginterface.dismiss();
        removeDialog(0);
    }

    ()
    {
        this$0 = final_ignoresactivity;
        val$i = ignores;
        val$ii = net.andchat.donate.Backend..this;
        super();
    }
}
