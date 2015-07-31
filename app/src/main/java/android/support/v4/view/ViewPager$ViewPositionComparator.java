// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.view.View;
import java.util.Comparator;

// Referenced classes of package android.support.v4.view:
//            ViewPager

static class 
    implements Comparator
{

    public int compare(View view, View view1)
    {
        view = ()view.getLayoutParams();
        view1 = ()view1.getLayoutParams();
        if ((() (view)). != (() (view1)).)
        {
            return !(() (view)). ? -1 : 1;
        } else
        {
            return (() (view)). - (() (view1)).;
        }
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((View)obj, (View)obj1);
    }

    ()
    {
    }
}
