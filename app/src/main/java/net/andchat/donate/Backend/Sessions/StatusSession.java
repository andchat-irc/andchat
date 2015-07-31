// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;


// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            BaseSession, SessionManager

class StatusSession extends BaseSession
{

    StatusSession(String s, String s1, int i, SessionManager sessionmanager)
    {
        super(s, s1, i, sessionmanager);
    }

    public boolean hasCapability(int i)
    {
        return false;
    }
}
