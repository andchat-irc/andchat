// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import android.content.res.Resources;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;

public class Unignore extends Command
{

    public Unignore(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        Resources resources;
        Ignores ignores;
        int i;
        int j;
        boolean flag;
label0:
        {
            resources = helper.getResources();
            if (s1 != null)
            {
                s1 = s1.trim();
                if (s1.length() != 0)
                {
                    break label0;
                }
            }
            sendMessage(helper.getCurrentSession().getSessionName(), resources.getString(0x7f0a01cc), 1, helper);
            return;
        }
        i = s1.indexOf(',');
        s = (String[])null;
        if (i != -1)
        {
            s = s1.split(",");
        }
        ignores = helper.getIgnoreList();
        flag = false;
        if (s == null)
        {
            break MISSING_BLOCK_LABEL_170;
        }
        j = s.length;
        i = 0;
_L3:
        if (i < j) goto _L2; else goto _L1
_L1:
        if (flag)
        {
            i = 0x7f0a01cd;
        } else
        {
            i = 0x7f0a01ce;
        }
        s = resources.getString(i, new Object[] {
            s1
        });
        sendMessage(helper.getCurrentSession().getSessionName(), s, 1, helper);
        return;
_L2:
        flag |= ignores.removeIgnore(s[i].trim());
        i++;
          goto _L3
        flag = ignores.removeIgnore(s1);
          goto _L1
    }
}
