// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            Session

protected static class setLimit
{

    final List mEntries;
    int mIdx;
    int mLimit;
    CharSequence mSavedLine;

    public void add(CharSequence charsequence)
    {
        if (mLimit == 0)
        {
            return;
        }
        List list = mEntries;
        if (list.size() > mLimit)
        {
            list.remove(0);
        }
        list.add(charsequence);
        mIdx = list.size();
    }

    public CharSequence getDown(CharSequence charsequence)
    {
        if (mLimit != 0)
        {
            List list = mEntries;
            int k = list.size();
            if (k != 0)
            {
                int i = mIdx;
                if (i >= 0 && i < k)
                {
                    CharSequence charsequence1 = (CharSequence)list.get(i);
                    if (charsequence.length() > 0 && !charsequence1.equals(charsequence))
                    {
                        list.set(i, charsequence);
                    }
                }
                int j = i + 1;
                i = j;
                if (j >= k + 1)
                {
                    i = j;
                    if (charsequence.length() > 0)
                    {
                        add(charsequence);
                        i = j + 1;
                    }
                }
                mIdx = i;
                if (i < 0 || i >= k)
                {
                    charsequence = null;
                } else
                {
                    charsequence = (CharSequence)list.get(i);
                }
                return charsequence;
            }
        }
        return charsequence;
    }

    public CharSequence getUp(CharSequence charsequence)
    {
        if (mLimit != 0) goto _L2; else goto _L1
_L1:
        return charsequence;
_L2:
        List list;
        int l;
        list = mEntries;
        l = list.size();
        if (l == 0) goto _L1; else goto _L3
_L3:
        int k;
        int i = mIdx;
        if (i >= 0 && i < l)
        {
            CharSequence charsequence1 = (CharSequence)list.get(i);
            if (charsequence.length() > 0 && !charsequence1.equals(charsequence))
            {
                list.set(i, charsequence);
            }
        }
        k = i - 1;
        if (k >= l - 1 && charsequence.length() > 0 && !((CharSequence)list.get(l - 1)).equals(charsequence))
        {
            add(charsequence);
        }
        if (k != l) goto _L5; else goto _L4
_L4:
        int j = k - 1;
_L7:
        k = j;
        if (j < 0)
        {
            k = 0;
        }
        mIdx = k;
        return (CharSequence)list.get(k);
_L5:
        j = k;
        if (k > l)
        {
            j = l - 1;
        }
        if (true) goto _L7; else goto _L6
_L6:
    }

    public void setLimit(int i)
    {
        mLimit = i;
        trimTo(i);
    }

    public void trimTo(int i)
    {
        List list = mEntries;
        if (i == 0)
        {
            list.clear();
            mIdx = 0;
            return;
        }
        for (; list.size() > i; list.remove(0)) { }
        mIdx = list.size();
    }

    public _cls9(int i)
    {
        mEntries = new ArrayList(i);
        setLimit(i);
    }
}
