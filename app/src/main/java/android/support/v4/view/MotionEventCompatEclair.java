// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.view.MotionEvent;

class MotionEventCompatEclair
{

    public static int findPointerIndex(MotionEvent motionevent, int i)
    {
        return motionevent.findPointerIndex(i);
    }

    public static int getPointerId(MotionEvent motionevent, int i)
    {
        return motionevent.getPointerId(i);
    }

    public static float getX(MotionEvent motionevent, int i)
    {
        return motionevent.getX(i);
    }

    public static float getY(MotionEvent motionevent, int i)
    {
        return motionevent.getY(i);
    }
}
