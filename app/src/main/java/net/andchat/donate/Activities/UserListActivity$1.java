// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.text.Editable;
import android.text.TextWatcher;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListActivity

class this._cls0
    implements TextWatcher
{

    final UserListActivity this$0;

    public void afterTextChanged(Editable editable)
    {
        if (editable.length() == 0)
        {
            UserListActivity.access$0(UserListActivity.this);
        } else
        {
            editable = editable.toString().trim();
            int i = editable.indexOf(',');
            if (i != -1 && i + 1 >= editable.length())
            {
                UserListActivity.access$1(UserListActivity.this, false);
                return;
            }
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
        this$0 = UserListActivity.this;
        super();
    }
}
