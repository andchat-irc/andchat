// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LimitedSizeQueue extends ConcurrentLinkedQueue
{

    private final int mLimit;
    private final int mNumToRemove;
    private int mSize;

    public LimitedSizeQueue(int i, int j)
    {
        mLimit = i;
        mNumToRemove = j;
    }

    private void check()
    {
    }

    public boolean add(Object obj)
    {
        if (mSize > mLimit)
        {
            removeWithLimit(mNumToRemove);
        }
        boolean flag = super.add(obj);
        mSize = mSize + 1;
        check();
        return flag;
    }

    public int getSize()
    {
        check();
        return mSize;
    }

    public void removeAll()
    {
        int i;
        int j = mSize;
        i = j;
        if (j == 0)
        {
            return;
        }
          goto _L1
_L3:
        int k;
        i = k;
        if (poll() != null) goto _L1; else goto _L2
_L2:
        mSize = 0;
        check();
        return;
_L1:
        k = i - 1;
        if (i >= 0) goto _L3; else goto _L2
    }

    public void removeWithLimit(int i)
    {
        int j;
        j = mSize;
        if (j == 0)
        {
            return;
        }
        if (i > j)
        {
            removeAll();
            mSize = 0;
            return;
        }
        j = i;
        if (i >= 0) goto _L2; else goto _L1
_L1:
        removeAll();
        mSize = 0;
_L3:
        check();
        return;
_L4:
        if (poll() == null)
        {
            break; /* Loop/switch isn't completed */
        }
        mSize = mSize - 1;
        j = i;
_L2:
        i = j - 1;
        if (j >= 0) goto _L4; else goto _L3
    }
}
