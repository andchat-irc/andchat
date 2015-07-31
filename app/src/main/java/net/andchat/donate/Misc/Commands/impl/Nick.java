// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Misc.Commands.Command;

public class Nick extends Command
{

    public Nick(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        boolean flag = true;
        if (s1 != null)
        {
            s = helper.getServerConnection();
            if (s == null || s.getConnectionState() != 1)
            {
                flag = false;
            }
            if (flag)
            {
                helper.writeToServer((new StringBuilder("NICK ")).append(s1.trim()).append("\r\n").toString());
                return;
            }
        }
    }
}
