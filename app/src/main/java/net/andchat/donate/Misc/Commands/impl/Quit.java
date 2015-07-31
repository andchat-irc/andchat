// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Misc.Commands.Command;

public class Quit extends Command
{

    public Quit(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        helper.getQuitReason();
        if (s1 != null)
        {
            helper.handleQuit(s1);
            return;
        } else
        {
            helper.handleQuit(null);
            return;
        }
    }
}
