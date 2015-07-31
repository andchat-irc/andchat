// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import java.util.List;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;

// Referenced classes of package net.andchat.donate.Misc.Commands.impl:
//            Part

public class WC extends Part
{

    public WC(String s)
    {
        super(s);
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 == null)
        {
            super.execute(s, s1, helper);
        } else
        if (s1.equalsIgnoreCase("-a"))
        {
            s = helper.getSessionManager().getSessionObjects();
            int j = s.size();
            int i = 0;
            while (i < j) 
            {
                super.close(helper, (Session)s.get(i));
                i++;
            }
        } else
        {
            super.execute(s, s1, helper);
            return;
        }
    }
}
