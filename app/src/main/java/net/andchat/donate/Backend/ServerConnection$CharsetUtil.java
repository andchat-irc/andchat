// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import com.glaforge.i18n.io.CharsetToolkit;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

private static final class mGuessCharset
{

    private final byte mBuffer[] = new byte[1024];
    private final String mCharset;
    private final boolean mGuessCharset;

    private String StringFromBuffer(byte abyte0[])
        throws UnsupportedEncodingException
    {
        if (abyte0[0] != -1) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        j = abyte0.length;
        i = 0;
_L5:
        if (i < j) goto _L4; else goto _L3
_L3:
        return null;
_L4:
        if (abyte0[i] != -1)
        {
            byte abyte1[] = new byte[j - i];
            System.arraycopy(abyte0, i, abyte1, 0, abyte1.length);
            return StringFromBuffer(abyte1);
        }
        i++;
          goto _L5
_L2:
        if (abyte0[abyte0.length - 1] == 10)
        {
            int k = abyte0.length;
            i = k;
            if (abyte0[k - 2] == 13)
            {
                i = k - 2;
            }
            String s;
            String s3;
            if (mGuessCharset)
            {
                s = CharsetToolkit.guessEncoding(abyte0, i, mCharset);
            } else
            {
                s = mCharset;
            }
            s3 = s;
            if (s == null)
            {
                s3 = mCharset;
            }
            return new String(abyte0, 0, i, s3);
        }
        if (abyte0[0] != -1 && abyte0[abyte0.length - 1] != -1)
        {
            String s1;
            String s4;
            if (mGuessCharset)
            {
                s1 = CharsetToolkit.guessEncoding(abyte0, abyte0.length, mCharset);
            } else
            {
                s1 = mCharset;
            }
            s4 = s1;
            if (s1 == null)
            {
                s4 = mCharset;
            }
            return new String(abyte0, 0, abyte0.length, s4);
        }
        i = abyte0.length - 1;
_L6:
        if (i >= 0)
        {
label0:
            {
                if (abyte0[i] == -1)
                {
                    break label0;
                }
                int l = i;
                if (i != 0)
                {
                    l = i;
                    if (abyte0[i - 1] == 13)
                    {
                        l = i - 1;
                    }
                }
                if (l != 0)
                {
                    String s2;
                    String s5;
                    if (mGuessCharset)
                    {
                        s2 = CharsetToolkit.guessEncoding(abyte0, l, mCharset);
                    } else
                    {
                        s2 = mCharset;
                    }
                    s5 = s2;
                    if (s2 == null)
                    {
                        s5 = mCharset;
                    }
                    return new String(abyte0, 0, l, s5);
                }
            }
        }
          goto _L3
        i--;
          goto _L6
    }

    public String readLine(InputStream inputstream)
        throws IOException
    {
        byte abyte0[];
        int i;
        abyte0 = mBuffer;
        Arrays.fill(abyte0, (byte)-1);
        i = 0;
_L6:
        boolean flag;
        boolean flag1;
        int j;
        if (i >= 1024)
        {
            return StringFromBuffer(abyte0);
        }
        j = inputstream.read();
        if (j == -1)
        {
            return StringFromBuffer(abyte0);
        }
        flag1 = false;
        flag = flag1;
        j;
        JVM INSTR tableswitch 10 13: default 84
    //                   10 120
    //                   11 88
    //                   12 88
    //                   13 105;
           goto _L1 _L2 _L3 _L3 _L4
_L1:
        flag = flag1;
_L3:
        abyte0[i] = (byte)j;
        if (flag)
        {
            return StringFromBuffer(abyte0);
        }
        break; /* Loop/switch isn't completed */
_L4:
        flag = flag1;
        if (i > 0) goto _L3; else goto _L5
_L5:
        i++;
          goto _L6
_L2:
        flag = true;
        if (i > 1) goto _L3; else goto _L5
    }

    public (String s)
    {
        mCharset = s;
        boolean flag;
        if (s.equalsIgnoreCase("UTF-8"))
        {
            flag = false;
        } else
        {
            flag = true;
        }
        mGuessCharset = flag;
    }
}
