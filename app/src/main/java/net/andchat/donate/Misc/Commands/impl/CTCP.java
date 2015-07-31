// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Utils;

public class CTCP extends Command
{

    public CTCP(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
label0:
        {
            if (s1 != null)
            {
                s = Utils.split(s1);
                if (s.length == 2)
                {
                    break label0;
                }
            }
            return;
        }
        helper.writeToServer((new StringBuilder("PRIVMSG ")).append(s[0]).append(" :\001").append(s[1]).append("\001").append("\r\n").toString());
    }
}
