// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class Names extends Command
{

    public Names(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        s = helper.getServerConnection();
        Session session = helper.getCurrentSession();
        if (session.getType() != 1)
        {
            return;
        }
        if (s != null)
        {
            s.addServerConnnectionFlag(1);
        }
        if (s1 == null)
        {
            helper.writeToServer((new StringBuilder("NAMES ")).append(session.getSessionName()).append("\r\n").toString());
            return;
        } else
        {
            helper.writeToServer((new StringBuilder("NAMES ")).append(s1).append("\r\n").toString());
            return;
        }
    }
}
