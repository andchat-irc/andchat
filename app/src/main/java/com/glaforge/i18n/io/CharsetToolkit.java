// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.glaforge.i18n.io;


public class CharsetToolkit
{

    public static String guessEncoding(byte abyte0[], int i, String s)
    {
        if (!hasUTF8Bom(abyte0)) goto _L2; else goto _L1
_L1:
        abyte0 = "UTF-8";
_L5:
        return abyte0;
_L2:
        boolean flag1;
        int k;
        boolean flag2;
        flag1 = false;
        flag2 = true;
        k = 0;
_L8:
        if (k < i) goto _L4; else goto _L3
_L3:
        k = ((flag1) ? 1 : 0);
_L7:
        abyte0 = s;
        if (k != 0)
        {
            abyte0 = s;
            if (flag2)
            {
                return "UTF-8";
            }
        }
          goto _L5
_L4:
        byte byte5 = abyte0[k];
        byte byte0;
        byte byte1;
        byte byte2;
        byte byte3;
        byte byte4;
        boolean flag;
        int j;
        if (k + 1 >= i)
        {
            byte0 = -1;
        } else
        {
            byte0 = abyte0[k + 1];
        }
        if (k + 2 >= i)
        {
            byte1 = -1;
        } else
        {
            byte1 = abyte0[k + 2];
        }
        if (k + 3 >= i)
        {
            byte2 = -1;
        } else
        {
            byte2 = abyte0[k + 3];
        }
        if (k + 4 >= i)
        {
            byte3 = -1;
        } else
        {
            byte3 = abyte0[k + 4];
        }
        if (k + 5 >= i)
        {
            byte4 = -1;
        } else
        {
            byte4 = abyte0[k + 5];
        }
        j = k;
        flag = flag2;
        if (byte5 < 0)
        {
            flag1 = true;
            if (isTwoBytesSequence(byte5))
            {
                if (!isContinuationChar(byte0))
                {
                    flag = false;
                    j = k;
                } else
                {
                    j = k + 1;
                    flag = flag2;
                }
            } else
            if (isThreeBytesSequence(byte5))
            {
                if (!isContinuationChar(byte0) || !isContinuationChar(byte1))
                {
                    flag = false;
                    j = k;
                } else
                {
                    j = k + 2;
                    flag = flag2;
                }
            } else
            if (isFourBytesSequence(byte5))
            {
                if (!isContinuationChar(byte0) || !isContinuationChar(byte1) || !isContinuationChar(byte2))
                {
                    flag = false;
                    j = k;
                } else
                {
                    j = k + 3;
                    flag = flag2;
                }
            } else
            if (isFiveBytesSequence(byte5))
            {
                if (!isContinuationChar(byte0) || !isContinuationChar(byte1) || !isContinuationChar(byte2) || !isContinuationChar(byte3))
                {
                    flag = false;
                    j = k;
                } else
                {
                    j = k + 4;
                    flag = flag2;
                }
            } else
            if (isSixBytesSequence(byte5))
            {
                if (!isContinuationChar(byte0) || !isContinuationChar(byte1) || !isContinuationChar(byte2) || !isContinuationChar(byte3) || !isContinuationChar(byte4))
                {
                    flag = false;
                    j = k;
                } else
                {
                    j = k + 5;
                    flag = flag2;
                }
            } else
            {
                flag = false;
                j = k;
            }
        }
        k = ((flag1) ? 1 : 0);
        flag2 = flag;
        if (!flag) goto _L7; else goto _L6
_L6:
        k = j + 1;
        flag2 = flag;
          goto _L8
    }

    private static boolean hasUTF8Bom(byte abyte0[])
    {
        return abyte0[0] == -17 && abyte0[1] == -69 && abyte0[2] == -65;
    }

    private static boolean isContinuationChar(byte byte0)
    {
        return -128 <= byte0 && byte0 <= -65;
    }

    private static boolean isFiveBytesSequence(byte byte0)
    {
        return -8 <= byte0 && byte0 <= -5;
    }

    private static boolean isFourBytesSequence(byte byte0)
    {
        return -16 <= byte0 && byte0 <= -9;
    }

    private static boolean isSixBytesSequence(byte byte0)
    {
        return -4 <= byte0 && byte0 <= -3;
    }

    private static boolean isThreeBytesSequence(byte byte0)
    {
        return -32 <= byte0 && byte0 <= -17;
    }

    private static boolean isTwoBytesSequence(byte byte0)
    {
        return -64 <= byte0 && byte0 <= -33;
    }
}
