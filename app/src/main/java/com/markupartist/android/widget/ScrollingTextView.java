// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.markupartist.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollingTextView extends TextView
{

    public ScrollingTextView(Context context)
    {
        super(context);
    }

    public ScrollingTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public ScrollingTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public boolean isFocused()
    {
        return true;
    }

    protected void onFocusChanged(boolean flag, int i, Rect rect)
    {
        if (flag)
        {
            super.onFocusChanged(flag, i, rect);
        }
    }

    public void onWindowFocusChanged(boolean flag)
    {
        if (flag)
        {
            super.onWindowFocusChanged(flag);
        }
    }
}
