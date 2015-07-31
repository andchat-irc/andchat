// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Misc.Commands.Command;

public class UnknownCommand extends Command
{

    public UnknownCommand(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        String s2 = s;
        if (s1 != null)
        {
            s2 = (new StringBuilder(String.valueOf(s))).append(" ").append(s1).toString();
        }
        helper.writeToServer((new StringBuilder(String.valueOf(s2))).append("\r\n").toString());
    }
}
