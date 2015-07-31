// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.content.Context;
import com.markupartist.android.widget.ActionBar;

// Referenced classes of package net.andchat.donate.Misc:
//            AbstractMenuInflator, Utils

public static class mContext extends AbstractMenuInflator
{

    private boolean haveOverflow;
    private final ActionBar mActionBar;
    private final Context mContext;

    public void addItem(String s, int i, int j, int k)
    {
        if (k == 0)
        {
            s = new com.markupartist.android.widget.(0x7f020039, mContext.getString(0x7f0a009c));
            mActionBar.addAction(s);
            return;
        } else
        {
            s = new com.markupartist.android.widget.ar(i, s);
            mActionBar.addAction(s);
            return;
        }
    }

    public boolean shouldParseItem(int i)
    {
        if (i == 0 && !haveOverflow)
        {
            haveOverflow = true;
            return true;
        } else
        {
            return false | Utils.isBitSet(i, 2) | Utils.isBitSet(i, 1);
        }
    }

    public (ActionBar actionbar, Context context)
    {
        mActionBar = actionbar;
        mContext = context;
    }
}
