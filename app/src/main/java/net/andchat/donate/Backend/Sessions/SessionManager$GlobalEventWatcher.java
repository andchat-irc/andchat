// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;


// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            SessionManager

public static interface 
{

    public abstract void onCurrentSessionChanged(int i, String s);

    public abstract void onMessageReceived(String s, int i, int j);

    public abstract void onSessionActivated(String s, int i);

    public abstract void onSessionAdded(String s, int i, int j);

    public abstract void onSessionDeactivated(String s, int i);

    public abstract void onSessionManagerDeleted(int i);

    public abstract void onSessionRemoved(String s, int i);

    public abstract void onSessionRenamed(String s, String s1);

    public abstract void onSessionTextStateCleared();
}
