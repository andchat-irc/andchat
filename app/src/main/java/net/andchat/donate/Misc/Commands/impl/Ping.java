// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Misc.Commands.Command;

public class Ping extends Command
{

    public Ping(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 == null)
        {
            s = Long.toString(System.currentTimeMillis());
        } else
        {
            s = s1;
        }
        s1 = helper.getServerConnection();
        if (s1 != null)
        {
            s1.addServerConnnectionFlag(8);
        }
        helper.writeToServer((new StringBuilder("PING ")).append(s).append("\r\n").toString());
    }
}
