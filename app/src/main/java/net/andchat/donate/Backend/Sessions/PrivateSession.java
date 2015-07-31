// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;


// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            BaseSession, SessionManager

class PrivateSession extends BaseSession
{

    PrivateSession(String s, String s1, int i, SessionManager sessionmanager)
    {
        super(s, s1, i, sessionmanager);
    }

    public boolean hasCapability(int i)
    {
        switch (i)
        {
        default:
            return false;

        case 6: // '\006'
            return true;
        }
    }
}
