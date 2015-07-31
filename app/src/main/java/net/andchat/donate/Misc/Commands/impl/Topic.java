// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class Topic extends Command
{

    public Topic(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        s = helper.getCurrentSession();
        if (s.getType() != 1)
        {
            return;
        }
        if (s1 == null)
        {
            helper.writeToServer((new StringBuilder("TOPIC ")).append(s.getSessionName()).append("\r\n").toString());
            return;
        } else
        {
            StringBuilder stringbuilder = new StringBuilder(s.getSessionName().length() + 5 + 1 + 2 + s1.length() + 2);
            stringbuilder.append("TOPIC ").append(s.getSessionName()).append(" :").append(s1).append("\r\n");
            helper.writeToServer(stringbuilder.toString());
            return;
        }
    }
}
