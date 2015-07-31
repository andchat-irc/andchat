// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import net.andchat.donate.IRCApp;

// Referenced classes of package net.andchat.donate.Activities:
//            Preferences, Main

class val.export
    implements android.content.stener
{

    final this._cls1 this$1;
    private final boolean val$export;
    private final boolean val$failed;

    public void onClick(DialogInterface dialoginterface, int i)
    {
        dialoginterface.dismiss();
        if (!val$failed && !val$export)
        {
            ((IRCApp)tApplication()).clearCrypt();
            artActivity((new Intent(this._cls1.this, net/andchat/donate/Activities/Main)).setFlags(0x4000000));
        }
    }

    ()
    {
        this$1 = final_;
        val$failed = flag;
        val$export = Z.this;
        super();
    }
}
