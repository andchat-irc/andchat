// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;


// Referenced classes of package net.andchat.donate.Misc:
//            ServerProfile

public static final class 
{

    public static final int MODE_NICKSERV;
    public static final int MODE_PASSWORD;
    public static final int MODE_SASL;
    private static int start;

    static 
    {
        start = 0;
        int i = start;
        start = i + 1;
        MODE_PASSWORD = 1 << i;
        i = start;
        start = i + 1;
        MODE_NICKSERV = 1 << i;
        i = start;
        start = i + 1;
        MODE_SASL = 1 << i;
    }
}
