// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.view.KeyEvent;
import android.view.View;

// Referenced classes of package net.andchat.donate.Activities:
//            PasswordActivity

class this._cls0
    implements android.view.sswordActivity._cls1
{

    final PasswordActivity this$0;

    public boolean onKey(View view, int i, KeyEvent keyevent)
    {
        if (i == 66 && keyevent.getAction() == 1)
        {
            PasswordActivity.access$0(PasswordActivity.this);
            return true;
        } else
        {
            return false;
        }
    }

    ()
    {
        this$0 = PasswordActivity.this;
        super();
    }
}
