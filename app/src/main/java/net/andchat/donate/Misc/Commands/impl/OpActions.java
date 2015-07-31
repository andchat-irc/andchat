// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import android.text.TextUtils;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class OpActions extends Command
{

    public OpActions(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 != null && helper.getCurrentSession().getType() == 1)
        {
            s1 = s1.trim();
            if (s.equalsIgnoreCase("op"))
            {
                helper.handleOpAction(s1, '+', 'o');
                return;
            }
            if (s.equalsIgnoreCase("deop"))
            {
                helper.handleOpAction(s1, '-', 'o');
                return;
            }
            if (s.equalsIgnoreCase("hop"))
            {
                helper.handleOpAction(s1, '+', 'h');
                return;
            }
            if (s.equalsIgnoreCase("dehop"))
            {
                helper.handleOpAction(s1, '-', 'h');
                return;
            }
            if (s.equalsIgnoreCase("voice") || s.equalsIgnoreCase("v"))
            {
                helper.handleOpAction(s1, '+', 'v');
                return;
            }
            if (s.equalsIgnoreCase("devoice") || s.equalsIgnoreCase("dv"))
            {
                helper.handleOpAction(s1, '-', 'v');
                return;
            }
            if (s.equalsIgnoreCase("unban") || s.equalsIgnoreCase("ub"))
            {
                helper.handleOpAction(s1, '-', 'b');
                return;
            }
            if (s.equalsIgnoreCase("ban") || s.equalsIgnoreCase("b"))
            {
                helper.handleBan(s1);
                return;
            }
            if (s.equalsIgnoreCase("kb") && !TextUtils.isEmpty(s1))
            {
                helper.handleBan(s1);
                helper.handleKick(s1);
                return;
            }
        }
    }
}
