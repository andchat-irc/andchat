// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.view.MotionEvent;

// Referenced classes of package android.support.v4.view:
//            MotionEventCompat

static interface 
{

    public abstract int findPointerIndex(MotionEvent motionevent, int i);

    public abstract int getPointerId(MotionEvent motionevent, int i);

    public abstract float getX(MotionEvent motionevent, int i);

    public abstract float getY(MotionEvent motionevent, int i);
}
