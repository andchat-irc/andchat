// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.SparseIntArray;
import net.andchat.donate.IRCApp;

public final class Colours
{

    private static Colours sInstance;
    private final SparseIntArray mColours = new SparseIntArray();
    private final Resources mResources;

    private Colours(Context context)
    {
        mResources = context.getResources();
    }

    public static Colours getInstance()
    {
        if (sInstance == null)
        {
            throw new RuntimeException("getInstance() called but not yet initialized");
        } else
        {
            return sInstance;
        }
    }

    public static void init(Context context)
    {
        if (!(context instanceof IRCApp))
        {
            throw new IllegalArgumentException("ctx must be an instance of IRCApp");
        }
        if (sInstance == null)
        {
            sInstance = new Colours(context);
        }
    }

    public void clear()
    {
        sInstance = null;
    }

    public int getColourForEvent(int i)
    {
        int k = mColours.get(i);
        int j = k;
        if (k == 0)
        {
            int ai[] = mResources.getIntArray(i);
            j = Color.rgb(ai[0], ai[1], ai[2]);
            mColours.put(i, j);
        }
        return j;
    }
}
