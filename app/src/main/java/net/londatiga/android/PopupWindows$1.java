// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.londatiga.android;

import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

// Referenced classes of package net.londatiga.android:
//            PopupWindows

class this._cls0
    implements android.view.tener
{

    final PopupWindows this$0;

    public boolean onTouch(View view, MotionEvent motionevent)
    {
        if (motionevent.getAction() == 4)
        {
            mWindow.dismiss();
            return true;
        } else
        {
            return false;
        }
    }

    ()
    {
        this$0 = PopupWindows.this;
        super();
    }
}
