// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListFragment, ChatWindow

class this._cls0
    implements android.widget.lickListener
{

    final UserListFragment this$0;

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        if (!UserListFragment.access$2(UserListFragment.this).SwipeAction())
        {
            switch (UserListFragment.access$3(UserListFragment.this))
            {
            default:
                UserListFragment.access$5(UserListFragment.this, 1);
                UserListFragment.access$6(UserListFragment.this, mChat.startActionMode(UserListFragment.this));
                return;

            case 1: // '\001'
                break;
            }
            if (UserListFragment.access$4(UserListFragment.this) != null)
            {
                UserListFragment.access$4(UserListFragment.this).invalidate();
                return;
            }
        }
    }

    ()
    {
        this$0 = UserListFragment.this;
        super();
    }
}
