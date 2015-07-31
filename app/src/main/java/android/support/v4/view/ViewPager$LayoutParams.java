// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

// Referenced classes of package android.support.v4.view:
//            ViewPager

public static class gravity extends android.view.youtParams
{

    int childIndex;
    public int gravity;
    public boolean isDecor;
    boolean needsMeasure;
    int position;
    float widthFactor;

    public ()
    {
        super(-1, -1);
        widthFactor = 0.0F;
    }

    public widthFactor(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        widthFactor = 0.0F;
        context = context.obtainStyledAttributes(attributeset, ViewPager.access$400());
        gravity = context.getInteger(0, 48);
        context.recycle();
    }
}
