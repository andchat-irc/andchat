// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class InterceptingLinearLayout extends LinearLayout
{

    private GestureDetector mDetector;
    private View mInterestingView;
    private float mLastMotionX;
    private float mLastMotionY;
    private final Rect mRect;
    private int mSlop;
    private boolean mWantTouch;

    public InterceptingLinearLayout(Context context)
    {
        super(context);
        mRect = new Rect();
        getSlop(context);
    }

    public InterceptingLinearLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mRect = new Rect();
        getSlop(context);
    }

    private void getSlop(Context context)
    {
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        motionevent.getAction();
        JVM INSTR tableswitch 0 2: default 32
    //                   0 38
    //                   1 32
    //                   2 110;
           goto _L1 _L2 _L1 _L3
_L1:
        return super.onInterceptTouchEvent(motionevent);
_L2:
        float f = motionevent.getX();
        float f2 = motionevent.getY();
        Rect rect = mRect;
        mInterestingView.getHitRect(rect);
        if (!rect.contains((int)f, (int)f2))
        {
            mWantTouch = false;
        } else
        {
            mLastMotionX = f;
            mLastMotionY = f2;
            mDetector.onTouchEvent(motionevent);
            mWantTouch = true;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        if (mWantTouch)
        {
            float f1 = motionevent.getX();
            float f3 = Math.abs(f1 - mLastMotionX);
            float f4 = Math.abs(motionevent.getY() - mLastMotionY);
            if (f3 > (float)mSlop && f3 > f4)
            {
                mLastMotionX = f1;
                return true;
            }
        }
        if (true) goto _L1; else goto _L4
_L4:
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if (mDetector != null && mWantTouch)
        {
            mDetector.onTouchEvent(motionevent);
        }
        return super.onTouchEvent(motionevent);
    }

    public void setViewAndDetector(View view, GestureDetector gesturedetector)
    {
        mInterestingView = view;
        mDetector = gesturedetector;
    }
}
