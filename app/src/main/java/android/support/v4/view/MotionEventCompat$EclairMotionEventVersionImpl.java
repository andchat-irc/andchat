// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.view.MotionEvent;

// Referenced classes of package android.support.v4.view:
//            MotionEventCompat, MotionEventCompatEclair

static class 
    implements 
{

    public int findPointerIndex(MotionEvent motionevent, int i)
    {
        return MotionEventCompatEclair.findPointerIndex(motionevent, i);
    }

    public int getPointerId(MotionEvent motionevent, int i)
    {
        return MotionEventCompatEclair.getPointerId(motionevent, i);
    }

    public float getX(MotionEvent motionevent, int i)
    {
        return MotionEventCompatEclair.getX(motionevent, i);
    }

    public float getY(MotionEvent motionevent, int i)
    {
        return MotionEventCompatEclair.getY(motionevent, i);
    }

    ()
    {
    }
}
