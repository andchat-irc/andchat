// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Commands.Command;

public class ClearAll extends Command
{

    public ClearAll(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        s = helper.getCurrentSession().getSessionName();
        s1 = helper.getSessionManager();
        String as[] = s1.getSessionList();
        int j = as.length;
        int i = 0;
        do
        {
            if (i >= j)
            {
                helper.sendFlaggedMessage(7, s);
                return;
            }
            s1.deleteText(as[i], -1);
            i++;
        } while (true);
    }
}
