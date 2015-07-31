// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import java.util.List;

// Referenced classes of package net.andchat.donate.Activities:
//            Preferences

class val.d
    implements android.view.ces.DataBackup._cls3
{

    final owDialog this$1;
    private final Dialog val$d;

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131230807: 
            val$d.dismiss();
            return;

        case 2131230806: 
            val$d.dismiss();
            view = new Intent("android.intent.action.GET_CONTENT");
            view.setType("text/*");
            view.addFlags(0x80000);
            break;
        }
        if (tPackageManager().queryIntentActivities(view, 0x10000).size() > 0)
        {
            artActivityForResult(Intent.createChooser(view, tString(0x7f0a0180)), 0);
            return;
        } else
        {
            owDialog(2);
            return;
        }
    }

    ()
    {
        this$1 = final_;
        val$d = Dialog.this;
        super();
    }
}
