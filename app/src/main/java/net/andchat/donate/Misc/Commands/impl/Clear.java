// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Commands.Command;

public class Clear extends Command
{

    public Clear(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 == null)
        {
            s = helper.getCurrentSession().getSessionName();
        } else
        {
            s = s1;
        }
        helper.getSessionManager().deleteText(s, -1);
        helper.sendFlaggedMessage(7, s);
    }
}
