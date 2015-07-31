// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Utils;

public class Me extends Command
{

    public Me(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 != null)
        {
            Object obj = helper.getCurrentNick();
            s = helper.getCurrentSession();
            if (s.getType() != 0)
            {
                StringBuilder stringbuilder = new StringBuilder("PRIVMSG ");
                stringbuilder.append(s.getSessionName()).append(" :\001ACTION").append(" ").append(s1).append("\001").append("\r\n");
                helper.writeToServer(stringbuilder.toString());
                stringbuilder.setLength(0);
                stringbuilder.append("* ").append(((String) (obj))).append(" ").append(s1);
                obj = Utils.addColourAndBold(helper.isShowingTimestamps(), stringbuilder, Colours.getInstance().getColourForEvent(0x7f0b0010), 2, ((String) (obj)).length() + 2);
                if (s1.length() >= 4)
                {
                    Utils.addLinks(((android.text.Spannable) (obj)));
                }
                sendMessage(s.getSessionName(), ((CharSequence) (obj)), 2, helper);
                return;
            }
        }
    }
}
