// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.graphics.Canvas;

// Referenced classes of package android.support.v4.widget:
//            EdgeEffectCompat

static interface 
{

    public abstract boolean draw(Object obj, Canvas canvas);

    public abstract void finish(Object obj);

    public abstract boolean isFinished(Object obj);

    public abstract boolean onPull(Object obj, float f);

    public abstract boolean onRelease(Object obj);

    public abstract void setSize(Object obj, int i, int j);
}
