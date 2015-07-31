// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListFragment

class N extends net.andchat.donate.Misc.ent._cls2
{

    final UserListFragment this$0;

    public void onLeftToRightSwipe()
    {
        ((net.andchat.donate.Misc.ener)mChat).onLeftToRightSwipe(UserListFragment.this);
    }

    public void onRightToLeftSwipe()
    {
        ((net.andchat.donate.Misc.ener)mChat).onRightToLeftSwipe(UserListFragment.this);
    }

    N(Context context, net.andchat.donate.Misc.VIATION viation)
    {
        this$0 = UserListFragment.this;
        super(context, viation);
    }
}
