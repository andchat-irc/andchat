// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;


// Referenced classes of package net.andchat.donate.Misc:
//            Base64

private static class <init>
{

    static final boolean $assertionsDisabled;
    private static final byte ENCODE[] = {
        65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
        75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
        85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
        101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
        111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
        121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
        56, 57, 43, 47
    };
    private final byte alphabet[];
    private int count;
    private final boolean do_cr;
    private final boolean do_newline;
    private final boolean do_padding;
    public byte output[];
    private final byte tail[];
    private int tailLen;

    private boolean process(byte abyte0[], int i, int j, boolean flag)
    {
        byte abyte1[];
        byte abyte4[];
        int k;
        int l;
        int l2;
        int i4;
        int j4;
        abyte1 = alphabet;
        abyte4 = output;
        l2 = 0;
        i4 = count;
        k = i;
        j4 = j + i;
        j = -1;
        i = k;
        l = j;
        tailLen;
        JVM INSTR tableswitch 0 2: default 68
    //                   0 74
    //                   1 442
    //                   2 518;
           goto _L1 _L2 _L3 _L4
_L2:
        break; /* Loop/switch isn't completed */
_L1:
        l = j;
        i = k;
_L12:
        j = i4;
        k = l2;
        l2 = i;
        if (l == -1) goto _L6; else goto _L5
_L5:
        int i3;
        k = 0 + 1;
        abyte4[0] = abyte1[l >> 18 & 0x3f];
        j = k + 1;
        abyte4[k] = abyte1[l >> 12 & 0x3f];
        k = j + 1;
        abyte4[j] = abyte1[l >> 6 & 0x3f];
        i3 = k + 1;
        abyte4[k] = abyte1[l & 0x3f];
        l = i4 - 1;
        j = l;
        k = i3;
        l2 = i;
        if (l != 0) goto _L6; else goto _L7
_L7:
        j = i3;
        if (do_cr)
        {
            abyte4[i3] = 13;
            j = i3 + 1;
        }
        k = j + 1;
        abyte4[j] = 10;
        l = 19;
        j = k;
        k = l;
_L14:
        if (i + 3 <= j4) goto _L9; else goto _L8
_L8:
        if (!flag) goto _L11; else goto _L10
_L3:
        i = k;
        l = j;
        if (k + 2 <= j4)
        {
            j = tail[0];
            l = k + 1;
            k = abyte0[k];
            i = l + 1;
            l = (j & 0xff) << 16 | (k & 0xff) << 8 | abyte0[l] & 0xff;
            tailLen = 0;
        }
          goto _L12
_L4:
        i = k;
        l = j;
        if (k + 1 <= j4)
        {
            l = (tail[0] & 0xff) << 16 | (tail[1] & 0xff) << 8 | abyte0[k] & 0xff;
            tailLen = 0;
            i = k + 1;
        }
          goto _L12
_L9:
        l = (abyte0[i] & 0xff) << 16 | (abyte0[i + 1] & 0xff) << 8 | abyte0[i + 2] & 0xff;
        abyte4[j] = abyte1[l >> 18 & 0x3f];
        abyte4[j + 1] = abyte1[l >> 12 & 0x3f];
        abyte4[j + 2] = abyte1[l >> 6 & 0x3f];
        abyte4[j + 3] = abyte1[l & 0x3f];
        i += 3;
        l = j + 4;
        j3 = k - 1;
        j = j3;
        k = l;
        l2 = i;
        if (j3 != 0) goto _L6; else goto _L13
_L13:
        j = l;
        if (do_cr)
        {
            abyte4[l] = 13;
            j = l + 1;
        }
        l = j + 1;
        abyte4[j] = 10;
        k = 19;
        j = l;
          goto _L14
_L10:
        if (i - tailLen == j4 - 1)
        {
            l = 0;
            int j3;
            if (tailLen > 0)
            {
                l2 = tail[0];
                l = 0 + 1;
            } else
            {
                int k3 = i + 1;
                l2 = abyte0[i];
                i = k3;
            }
            l2 = (l2 & 0xff) << 4;
            tailLen = tailLen - l;
            j3 = j + 1;
            abyte4[j] = abyte1[l2 >> 6 & 0x3f];
            l = j3 + 1;
            abyte4[j3] = abyte1[l2 & 0x3f];
            j = l;
            if (do_padding)
            {
                l2 = l + 1;
                abyte4[l] = 61;
                j = l2 + 1;
                abyte4[l2] = 61;
            }
            l = j;
            j = i;
            if (do_newline)
            {
                j = l;
                if (do_cr)
                {
                    abyte4[l] = 13;
                    j = l + 1;
                }
                abyte4[j] = 10;
                j = i;
            }
        } else
        if (i - tailLen == j4 - 2)
        {
            int i1 = 0;
            int l3;
            if (tailLen > 1)
            {
                l2 = tail[0];
                i1 = 0 + 1;
            } else
            {
                l3 = i + 1;
                l2 = abyte0[i];
                i = l3;
            }
            if (tailLen > 0)
            {
                l3 = tail[i1];
                i1++;
            } else
            {
                l3 = abyte0[i];
                i++;
            }
            l2 = (l2 & 0xff) << 10 | (l3 & 0xff) << 2;
            tailLen = tailLen - i1;
            i1 = j + 1;
            abyte4[j] = abyte1[l2 >> 12 & 0x3f];
            l3 = i1 + 1;
            abyte4[i1] = abyte1[l2 >> 6 & 0x3f];
            j = l3 + 1;
            abyte4[l3] = abyte1[l2 & 0x3f];
            i1 = j;
            if (do_padding)
            {
                abyte4[j] = 61;
                i1 = j + 1;
            }
            j = i;
            if (do_newline)
            {
                j = i1;
                if (do_cr)
                {
                    abyte4[i1] = 13;
                    j = i1 + 1;
                }
                abyte4[j] = 10;
                j = i;
            }
        } else
        {
            int j1 = j;
            if (do_newline)
            {
                int k1 = j;
                if (j > 0)
                {
                    int l1 = j;
                    if (k != 19)
                    {
                        if (do_cr)
                        {
                            int i2 = j + 1;
                            abyte4[j] = 13;
                            j = i2;
                        }
                        int j2 = j + 1;
                        abyte4[j] = 10;
                    }
                }
            }
            j = i;
        }
        if (!$assertionsDisabled && tailLen != 0)
        {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && j != j4)
        {
            throw new AssertionError();
        }
          goto _L15
_L11:
        if (i != j4 - 1) goto _L17; else goto _L16
_L16:
        byte abyte2[] = tail;
        j = tailLen;
        tailLen = j + 1;
        abyte2[j] = abyte0[i];
_L15:
        count = k;
        return true;
_L17:
        if (i == j4 - 2)
        {
            byte abyte3[] = tail;
            j = tailLen;
            tailLen = j + 1;
            abyte3[j] = abyte0[i];
            abyte3 = tail;
            j = tailLen;
            tailLen = j + 1;
            abyte3[j] = abyte0[i + 1];
        }
        if (true) goto _L15; else goto _L6
_L6:
        i = l2;
        int k2 = k;
        k = j;
        j = k2;
          goto _L14
    }

    static 
    {
        boolean flag;
        if (!net/andchat/donate/Misc/Base64.desiredAssertionStatus())
        {
            flag = true;
        } else
        {
            flag = false;
        }
        $assertionsDisabled = flag;
    }





    private (int i, byte abyte0[])
    {
        boolean flag = true;
        super();
        output = abyte0;
        do_padding = true;
        do_newline = true;
        if ((i & 4) == 0)
        {
            flag = false;
        }
        do_cr = flag;
        alphabet = ENCODE;
        tail = new byte[2];
        tailLen = 0;
        if (do_newline)
        {
            i = 19;
        } else
        {
            i = -1;
        }
        count = i;
    }

    count(int i, byte abyte0[], count count1)
    {
        this(i, abyte0);
    }
}
