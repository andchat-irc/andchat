// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import java.io.File;

// Referenced classes of package net.andchat.donate.Activities:
//            Preferences

class val.d
    implements android.view.ces.DataBackup._cls4
{

    final this._cls1 this$1;
    private final Dialog val$d;

    public void onClick(View view)
    {
        view = (EditText)val$d.findViewById(0x7f080058);
        cess._mth0(this._cls1.this, ndPreference("import_trigger"), 1);
        view = view.getText().toString();
        val$d.dismiss();
        cess._mth2(this._cls1.this, new net.andchat.donate.Misc.kup(1, this._cls1.this, cess._mth1(this._cls1.this), new File(view), andler));
        cess._mth3(this._cls1.this).kup();
    }

    ()
    {
        this$1 = final_;
        val$d = Dialog.this;
        super();
    }
}
