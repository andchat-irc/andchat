// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListFragment

class val.gd
    implements android.view.r
{

    final UserListFragment this$0;
    private final GestureDetector val$gd;

    public boolean onTouch(View view, MotionEvent motionevent)
    {
        return val$gd.onTouchEvent(motionevent);
    }

    ()
    {
        this$0 = final_userlistfragment;
        val$gd = GestureDetector.this;
        super();
    }
}
