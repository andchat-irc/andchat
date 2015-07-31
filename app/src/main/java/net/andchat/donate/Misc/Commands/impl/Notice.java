// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import android.text.TextUtils;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Utils;

public class Notice extends Command
{

    public Notice(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 != null)
        {
            if ((s = Utils.split(s1, 2)).length >= 2)
            {
                CharSequence charsequence = s[0];
                CharSequence charsequence1 = s[1];
                if (!TextUtils.isEmpty(charsequence) && !TextUtils.isEmpty(charsequence1))
                {
                    String s2 = helper.getCurrentNick();
                    s = charsequence;
                    if (charsequence.equalsIgnoreCase(s2))
                    {
                        s = s2;
                    }
                    s1 = new StringBuilder(s1.length());
                    s1.append("NOTICE ").append(s).append(" :").append(charsequence1).append("\r\n");
                    helper.writeToServer(s1.toString());
                    s1.setLength(0);
                    s1.ensureCapacity(s.length() + charsequence1.length() + 4 + s2.length());
                    s1.append('-').append(s2);
                    s1.append("/").append(s);
                    s1.append("-: ").append(charsequence1);
                    int i = Colours.getInstance().getColourForEvent(0x7f0b0011);
                    s1 = Utils.createMessage(helper.isShowingTimestamps(), s1);
                    Utils.addColour(helper.isShowingTimestamps(), s1, i, 0, s.length() + s2.length() + 4);
                    if (!helper.getSessionManager().haveSessionFor(s))
                    {
                        s = helper.getCurrentSession().getSessionName();
                    }
                    sendMessage(s, s1, 1, helper);
                    return;
                }
            }
        }
    }
}
