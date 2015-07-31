// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class Who extends Command
{

    public Who(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        s = helper.getServerConnection();
        if (s != null)
        {
            s.addServerConnnectionFlag(4);
        }
        if (s1 == null)
        {
            s = helper.getCurrentSession();
            if (s.getType() == 1)
            {
                helper.writeToServer((new StringBuilder("WHO ")).append(s.getSessionName()).append("\r\n").toString());
            }
        } else
        if (s1.length() >= 1)
        {
            helper.writeToServer((new StringBuilder("WHO ")).append(s1).append("\r\n").toString());
            return;
        }
    }
}
