// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.content.res.Resources;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;

// Referenced classes of package net.andchat.donate.Misc:
//            CommandParser, IRCMessage

public static interface nager
{

    public abstract String getCurrentNick();

    public abstract Session getCurrentSession();

    public abstract Ignores getIgnoreList();

    public abstract String getPartReason();

    public abstract String getQuitReason();

    public abstract Resources getResources();

    public abstract ServerConnection getServerConnection();

    public abstract SessionManager getSessionManager();

    public abstract void handleAddMessage(IRCMessage ircmessage);

    public abstract void handleBan(String s);

    public abstract void handleJoin(String s);

    public abstract void handleKick(String s);

    public abstract void handleOpAction(String s, char c, char c1);

    public abstract void handleQuit(String s);

    public abstract void handleStartPm(String s);

    public abstract boolean isShowingTimestamps();

    public abstract void sendFlaggedMessage(int i, Object obj);

    public abstract void writeToServer(String s);
}
