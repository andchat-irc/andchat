// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;


// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            SessionManager

public static interface 
{

    public abstract void onNickAdded(String s, String s1);

    public abstract void onNickChanged(String s, String s1, String s2);

    public abstract void onNickModeChanged(String s, String s1, String s2);

    public abstract void onNickRemoved(String s, String s1);

    public abstract void onNicksReceived(String s);
}
