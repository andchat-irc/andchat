// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Misc.Commands.Command;

public class UM extends Command
{

    public UM(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        s = (new StringBuilder("MODE ")).append(helper.getCurrentNick());
        if (s1 != null)
        {
            s.append(" ").append(s1);
        }
        s.append("\r\n");
        helper.writeToServer(s.toString());
    }
}
