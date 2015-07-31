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
        editable = editable.toString();
        if (editable.length() <= 0)
        {
            break MISSING_BLOCK_LABEL_126;
        }
        int i = Integer.parseInt(editable);
        if (i == 0)
        {
            try
            {
                ServerDetailsActivity.access$2(ServerDetailsActivity.this).setError(getString(0x7f0a0146));
                return;
            }
            // Misplaced declaration of an exception variable
            catch (Editable editable)
            {
                ServerDetailsActivity.access$2(ServerDetailsActivity.this).setError(getString(0x7f0a0149));
            }
            break MISSING_BLOCK_LABEL_87;
        }
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_88;
        }
        ServerDetailsActivity.access$2(ServerDetailsActivity.this).setError(getString(0x7f0a0147));
        return;
        return;
        if (i <= 65535)
        {
            break MISSING_BLOCK_LABEL_114;
        }
        ServerDetailsActivity.access$2(ServerDetailsActivity.this).setError(getString(0x7f0a0148));
        return;
        ServerDetailsActivity.access$2(ServerDetailsActivity.this).setError(null);
        return;
        ServerDetailsActivity.access$2(ServerDetailsActivity.this).setError(null);
        return;
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
