// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

// Referenced classes of package net.andchat.donate.Activities:
//            ServerDetailsActivity

class this._cls0
    implements TextWatcher
{

    final ServerDetailsActivity this$0;

    public void afterTextChanged(Editable editable)
    {
        if (editable.length() > 0)
        {
            ServerDetailsActivity.access$1(ServerDetailsActivity.this).setError(null);
            return;
        } else
        {
            ServerDetailsActivity.access$1(ServerDetailsActivity.this).setError(getString(0x7f0a0145));
            return;
        }
    }

    public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
    {
    }

    public void onTextChanged(CharSequence charsequence, int i, int j, int k)
    {
    }

    ()
    {
        this$0 = ServerDetailsActivity.this;
        super();
    }
}
