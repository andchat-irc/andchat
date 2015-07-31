// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.graphics.Canvas;
import android.widget.EdgeEffect;

class EdgeEffectCompatIcs
{

    public static boolean draw(Object obj, Canvas canvas)
    {
        return ((EdgeEffect)obj).draw(canvas);
    }

    public static void finish(Object obj)
    {
        ((EdgeEffect)obj).finish();
    }

    public static boolean isFinished(Object obj)
    {
        return ((EdgeEffect)obj).isFinished();
    }

    public static boolean onPull(Object obj, float f)
    {
        ((EdgeEffect)obj).onPull(f);
        return true;
    }

    public static boolean onRelease(Object obj)
    {
        obj = (EdgeEffect)obj;
        ((EdgeEffect) (obj)).onRelease();
        return ((EdgeEffect) (obj)).isFinished();
    }

    public static void setSize(Object obj, int i, int j)
    {
        ((EdgeEffect)obj).setSize(i, j);
    }
}
