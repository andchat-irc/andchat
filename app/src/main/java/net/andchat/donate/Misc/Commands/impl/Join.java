// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Utils;

public class Join extends Command
{

    public Join(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 != null)
        {
            if ((s1 = s1.trim()).length() != 0)
            {
                s = s1;
                if (!Utils.isChannelPrefix(s1.charAt(0)))
                {
                    s = (new StringBuilder("#")).append(s1).toString();
                }
                helper.handleJoin(s);
                return;
            }
        }
    }
}
