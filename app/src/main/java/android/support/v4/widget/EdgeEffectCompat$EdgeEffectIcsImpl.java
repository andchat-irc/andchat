// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.graphics.Canvas;

// Referenced classes of package android.support.v4.widget:
//            EdgeEffectCompat, EdgeEffectCompatIcs

static class 
    implements 
{

    public boolean draw(Object obj, Canvas canvas)
    {
        return EdgeEffectCompatIcs.draw(obj, canvas);
    }

    public void finish(Object obj)
    {
        EdgeEffectCompatIcs.finish(obj);
    }

    public boolean isFinished(Object obj)
    {
        return EdgeEffectCompatIcs.isFinished(obj);
    }

    public boolean onPull(Object obj, float f)
    {
        return EdgeEffectCompatIcs.onPull(obj, f);
    }

    public boolean onRelease(Object obj)
    {
        return EdgeEffectCompatIcs.onRelease(obj);
    }

    public void setSize(Object obj, int i, int j)
    {
        EdgeEffectCompatIcs.setSize(obj, i, j);
    }

    ()
    {
    }
}
