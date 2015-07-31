// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.database.DataSetObserver;

// Referenced classes of package android.support.v4.view:
//            ViewPager

private class <init> extends DataSetObserver
{

    final ViewPager this$0;

    public void onChanged()
    {
        dataSetChanged();
    }

    public void onInvalidated()
    {
        dataSetChanged();
    }

    private ()
    {
        this$0 = ViewPager.this;
        super();
    }

    this._cls0(this._cls0 _pcls0)
    {
        this();
    }
}
