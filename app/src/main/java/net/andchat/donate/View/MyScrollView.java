// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public final class MyScrollView extends ScrollView
{

    public MyScrollView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        super.onSizeChanged(i, j, k, l);
        if (k == 0)
        {
            return;
        } else
        {
            scrollTo(0, getScrollY() + (l - j));
            return;
        }
    }
}
