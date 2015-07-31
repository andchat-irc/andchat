// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class Kick extends Command
{

    public Kick(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        Session session = helper.getCurrentSession();
        if (session.getType() != 1 || s1 == null)
        {
            return;
        }
        int i = s1.indexOf(' ');
        if (i == -1)
        {
            s = s1;
        } else
        {
            s = s1.substring(0, i);
        }
        if (i != -1)
        {
            s1 = s1.substring(i + 1);
        } else
        {
            s1 = null;
        }
        s = (new StringBuilder("KICK ")).append(session).append(" ").append(s);
        if (s1 != null && s1.length() > 0)
        {
            s.append(" :").append(s1);
        }
        s.append("\r\n");
        helper.writeToServer(s.toString());
    }
}
