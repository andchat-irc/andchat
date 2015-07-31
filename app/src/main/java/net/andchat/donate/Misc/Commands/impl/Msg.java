// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Utils;

public class Msg extends Command
{

    public Msg(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        int i;
        if (s1 != null)
        {
            if ((i = s1.indexOf(' ', 1)) != -1)
            {
                String s2 = s1.substring(0, i).trim();
                Object obj = helper.getCurrentNick();
                s = s2;
                if (s2.equalsIgnoreCase(((String) (obj))))
                {
                    s = ((String) (obj));
                }
                s1 = s1.substring(i + 1);
                if (s.length() != 0 && s1.length() != 0)
                {
                    if (!Utils.isChannelPrefix(s.charAt(0)) && !helper.getSessionManager().haveSessionFor(s))
                    {
                        helper.handleStartPm(s);
                    }
                    obj = (new StringBuilder("<")).append(helper.getSessionManager().getOurStatus(s)).append(((String) (obj))).append("> ");
                    int j = ((StringBuilder) (obj)).length();
                    ((StringBuilder) (obj)).append(s1);
                    int k = Colours.getInstance().getColourForEvent(0x7f0b0012);
                    android.text.SpannableStringBuilder spannablestringbuilder = Utils.createMessage(helper.isShowingTimestamps(), ((CharSequence) (obj)));
                    Utils.addColour(helper.isShowingTimestamps(), spannablestringbuilder, k, 0, 1);
                    Utils.addColour(helper.isShowingTimestamps(), spannablestringbuilder, k, j - 2, j);
                    sendMessage(s, spannablestringbuilder, 2, helper);
                    ((StringBuilder) (obj)).setLength(0);
                    ((StringBuilder) (obj)).append("PRIVMSG ").append(s).append(" :").append(s1).append("\r\n");
                    helper.writeToServer(((StringBuilder) (obj)).toString());
                    return;
                }
            }
        }
    }
}
