// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import android.util.Log;
import java.util.Arrays;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            SessionManager

public static final class <init>
{

    private final int mLevels[];
    private final char mModes[];
    private final char mPrefixes[];

    private int getLevel(char c, boolean flag)
    {
        char ac[];
        int i;
        int j;
        if (flag)
        {
            ac = mPrefixes;
        } else
        {
            ac = mModes;
        }
        j = ac.length;
        i = 0;
        do
        {
            if (i >= j)
            {
                return -1;
            }
            if (c == ac[i])
            {
                return mLevels[i];
            }
            i++;
        } while (true);
    }

    public String convertLevelToPrefix(int i)
    {
        int ai[] = mLevels;
        int k = ai.length;
        int j = Arrays.binarySearch(ai, i);
        if (j > 0)
        {
            return String.valueOf(mPrefixes[j]);
        }
        j = 0;
        do
        {
            if (j >= k)
            {
                return "";
            }
            if ((ai[j] & i) == ai[j])
            {
                return String.valueOf(mPrefixes[j]);
            }
            j++;
        } while (true);
    }

    public int getModeLevel(char c)
    {
        return getLevel(c, false);
    }

    public int getPrefixLevel(char c)
    {
        return getLevel(c, true);
    }

    public String getPrefixes()
    {
        return new String(mPrefixes);
    }

    public boolean isMode(char c)
    {
        char ac[] = mModes;
        int j = ac.length;
        int i = 0;
        do
        {
            if (i >= j)
            {
                return false;
            }
            if (c == ac[i])
            {
                return true;
            }
            i++;
        } while (true);
    }

    public boolean isPrefix(char c)
    {
        char ac[] = mPrefixes;
        int j = ac.length;
        int i = 0;
        do
        {
            if (i >= j)
            {
                return false;
            }
            if (c == ac[i])
            {
                return true;
            }
            i++;
        } while (true);
    }

    public char mapModeToPrefix(char c)
    {
        char ac[] = mModes;
        int j = ac.length;
        int i = 0;
        do
        {
            if (i >= j)
            {
                return '?';
            }
            if (c == ac[i])
            {
                return mPrefixes[i];
            }
            i++;
        } while (true);
    }

    private (String s, String s1)
    {
        String s3 = s;
        String s2 = s1;
        if (s.length() != s1.length())
        {
            s3 = "ov";
            s2 = "@+";
            Log.w("SessionManager", "Mismatch between modes.length & prefixes.length. Falling back to basic support");
        }
        mModes = s3.toCharArray();
        mPrefixes = s2.toCharArray();
        int j = mModes.length;
        mLevels = new int[j];
        int i = 0;
        do
        {
            if (i >= j)
            {
                return;
            }
            mLevels[i] = 1 << i;
            i++;
        } while (true);
    }

    mLevels(String s, String s1, mLevels mlevels)
    {
        this(s, s1);
    }
}
