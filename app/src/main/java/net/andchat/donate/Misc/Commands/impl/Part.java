// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Commands.Command;

public class Part extends Command
{

    public Part(String s)
    {
        super(s);
    }

    private void cleanup(ServerConnection serverconnection, SessionManager sessionmanager, String s)
    {
        sessionmanager.removeSession(s);
        if (serverconnection != null)
        {
            serverconnection.closeLog(s);
        }
    }

    protected void close(net.andchat.donate.Misc.CommandParser.Helper helper, Session session)
    {
        ServerConnection serverconnection;
        SessionManager sessionmanager;
        String s;
        serverconnection = helper.getServerConnection();
        boolean flag;
        if (serverconnection != null && serverconnection.getConnectionState() == 1)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        sessionmanager = helper.getSessionManager();
        s = session.getSessionName();
        if (!sessionmanager.haveSessionFor(s)) goto _L2; else goto _L1
_L1:
        if (session.getType() != 1) goto _L4; else goto _L3
_L3:
        if (flag && sessionmanager.getActive(s))
        {
            helper.writeToServer((new StringBuilder("PART ")).append(s).append(" :").append(helper.getPartReason()).append("\r\n").toString());
        } else
        {
            cleanup(serverconnection, sessionmanager, s);
        }
_L6:
        helper.sendFlaggedMessage(4, s);
_L2:
        return;
_L4:
        if (session.hasCapability(6))
        {
            cleanup(serverconnection, sessionmanager, s);
        }
        if (true) goto _L6; else goto _L5
_L5:
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        if (s1 != null) goto _L2; else goto _L1
_L1:
        s = helper.getCurrentSession();
_L4:
        close(helper, s);
        return;
_L2:
        s1 = helper.getSessionManager().getSession(s1);
        s = s1;
        if (s1 == null)
        {
            s = helper.getCurrentSession();
        }
        if (true) goto _L4; else goto _L3
_L3:
    }
}
