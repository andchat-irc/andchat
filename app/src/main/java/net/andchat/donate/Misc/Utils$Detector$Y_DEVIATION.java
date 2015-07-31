// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;


// Referenced classes of package net.andchat.donate.Misc:
//            Utils

public static final class  extends Enum
{

    private static final RELAXED ENUM$VALUES[];
    public static final RELAXED RELAXED;
    public static final RELAXED STRICT;

    public static  valueOf(String s)
    {
        return ()Enum.valueOf(net/andchat/donate/Misc/Utils$Detector$Y_DEVIATION, s);
    }

    public static [] values()
    {
         a[] = ENUM$VALUES;
        int i = a.length;
         a1[] = new ENUM.VALUES[i];
        System.arraycopy(a, 0, a1, 0, i);
        return a1;
    }

    static 
    {
        STRICT = new <init>("STRICT", 0);
        RELAXED = new <init>("RELAXED", 1);
        ENUM$VALUES = (new ENUM.VALUES[] {
            STRICT, RELAXED
        });
    }

    private (String s, int i)
    {
        super(s, i);
    }
}
