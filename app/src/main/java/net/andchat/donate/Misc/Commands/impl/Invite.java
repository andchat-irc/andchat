// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class Invite extends Command
{

    public Invite(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        s = helper.getCurrentSession();
        if (s1 == null || s.getType() != 1)
        {
            return;
        } else
        {
            helper.writeToServer((new StringBuilder("INVITE ")).append(s1).append(" ").append(s.getSessionName()).append("\r\n").toString());
            return;
        }
    }
}
