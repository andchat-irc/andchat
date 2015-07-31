// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.text.style.URLSpan;
import android.view.View;

// Referenced classes of package net.andchat.donate.Misc:
//            Utils

public static class mText extends URLSpan
{

    private String mText;

    public void onClick(View view)
    {
        view = view.getContext();
        if (view instanceof er)
        {
            ((er)view).handleJoin(mText);
        }
    }

    public er(String s)
    {
        super(s);
        mText = s;
    }
}
