// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.RelativeLayout;

public class DelegatingRelativeLayout extends RelativeLayout
{
    public static final class EnlargedViewSpec
    {

        int bottom;
        int left;
        int right;
        int top;

        public EnlargedViewSpec(int i, int j, int k, int l)
        {
            left = i;
            top = j;
            right = k;
            bottom = l;
        }
    }


    private View mEnlargedView;
    private Rect mExtendedBounds;
    private EnlargedViewSpec mSpec;

    public DelegatingRelativeLayout(Context context)
    {
        super(context);
    }

    public DelegatingRelativeLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public DelegatingRelativeLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    private void readSpec()
    {
        EnlargedViewSpec enlargedviewspec;
        android.util.DisplayMetrics displaymetrics;
        Rect rect;
        enlargedviewspec = mSpec;
        displaymetrics = getContext().getResources().getDisplayMetrics();
        rect = new Rect();
        mEnlargedView.getHitRect(rect);
        enlargedviewspec.left;
        JVM INSTR tableswitch -2 -2: default 56
    //                   -2 246;
           goto _L1 _L2
_L1:
        enlargedviewspec.top;
        JVM INSTR tableswitch -2 -2: default 80
    //                   -2 257;
           goto _L3 _L4
_L3:
        enlargedviewspec.right;
        JVM INSTR tableswitch -2 -2: default 104
    //                   -2 268;
           goto _L5 _L6
_L5:
        enlargedviewspec.bottom;
        JVM INSTR tableswitch -2 -2: default 128
    //                   -2 279;
           goto _L7 _L8
_L7:
        if (enlargedviewspec.left > 0)
        {
            rect.left = (int)((float)rect.left + TypedValue.applyDimension(1, enlargedviewspec.left, displaymetrics));
        } else
        {
            rect.left = 0;
        }
        if (enlargedviewspec.top > 0)
        {
            rect.top = (int)((float)rect.top + TypedValue.applyDimension(1, enlargedviewspec.top, displaymetrics));
        } else
        {
            rect.top = 0;
        }
        if (enlargedviewspec.right > 0)
        {
            rect.right = (int)((float)rect.right + TypedValue.applyDimension(1, enlargedviewspec.right, displaymetrics));
        } else
        {
            rect.right = 0;
        }
        if (enlargedviewspec.bottom > 0)
        {
            rect.bottom = (int)((float)rect.bottom + TypedValue.applyDimension(1, enlargedviewspec.bottom, displaymetrics));
        } else
        {
            rect.bottom = 0;
        }
        mExtendedBounds = rect;
        return;
_L2:
        enlargedviewspec.left = getLeft();
          goto _L1
_L4:
        enlargedviewspec.top = getTop();
          goto _L3
_L6:
        enlargedviewspec.right = getRight();
          goto _L5
_L8:
        enlargedviewspec.bottom = getHeight();
          goto _L7
    }

    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        super.onLayout(flag, i, j, k, l);
        if (isInEditMode())
        {
            return;
        } else
        {
            readSpec();
            setTouchDelegate(new TouchDelegate(mExtendedBounds, mEnlargedView));
            return;
        }
    }

    public void setEnlargedView(View view, EnlargedViewSpec enlargedviewspec)
    {
        mEnlargedView = view;
        mSpec = enlargedviewspec;
    }
}
