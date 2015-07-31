// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.graphics.Paint;
import android.view.View;

// Referenced classes of package android.support.v4.view:
//            ViewCompat

static class 
    implements 
{

    public boolean canScrollHorizontally(View view, int i)
    {
        return false;
    }

    long getFrameTime()
    {
        return 10L;
    }

    public int getOverScrollMode(View view)
    {
        return 2;
    }

    public void postInvalidateOnAnimation(View view)
    {
        view.postInvalidateDelayed(getFrameTime());
    }

    public void postOnAnimation(View view, Runnable runnable)
    {
        view.postDelayed(runnable, getFrameTime());
    }

    public void setLayerType(View view, int i, Paint paint)
    {
    }

    ()
    {
    }
}
