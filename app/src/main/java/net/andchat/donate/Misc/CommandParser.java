// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.content.res.Resources;
import java.util.ArrayList;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Commands.impl.Away;
import net.andchat.donate.Misc.Commands.impl.CM;
import net.andchat.donate.Misc.Commands.impl.CS;
import net.andchat.donate.Misc.Commands.impl.CTCP;
import net.andchat.donate.Misc.Commands.impl.Clear;
import net.andchat.donate.Misc.Commands.impl.ClearAll;
import net.andchat.donate.Misc.Commands.impl.Cycle;
import net.andchat.donate.Misc.Commands.impl.Ignore;
import net.andchat.donate.Misc.Commands.impl.Invite;
import net.andchat.donate.Misc.Commands.impl.Join;
import net.andchat.donate.Misc.Commands.impl.Kick;
import net.andchat.donate.Misc.Commands.impl.MOTD;
import net.andchat.donate.Misc.Commands.impl.Me;
import net.andchat.donate.Misc.Commands.impl.Msg;
import net.andchat.donate.Misc.Commands.impl.Names;
import net.andchat.donate.Misc.Commands.impl.Nick;
import net.andchat.donate.Misc.Commands.impl.Notice;
import net.andchat.donate.Misc.Commands.impl.OpActions;
import net.andchat.donate.Misc.Commands.impl.Part;
import net.andchat.donate.Misc.Commands.impl.Ping;
import net.andchat.donate.Misc.Commands.impl.Quit;
import net.andchat.donate.Misc.Commands.impl.Raw;
import net.andchat.donate.Misc.Commands.impl.Topic;
import net.andchat.donate.Misc.Commands.impl.UM;
import net.andchat.donate.Misc.Commands.impl.Unignore;
import net.andchat.donate.Misc.Commands.impl.UnknownCommand;
import net.andchat.donate.Misc.Commands.impl.WC;
import net.andchat.donate.Misc.Commands.impl.Who;

// Referenced classes of package net.andchat.donate.Misc:
//            IRCMessage

public class CommandParser
{
    public static interface Helper
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


    private static final ArrayList sMap;
    private static final Command sUnknownCommand = new UnknownCommand("_unknown");

    public static void handleCommand(String s, Helper helper)
    {
        Object obj;
        Object obj1;
        int i;
        obj = null;
        i = s.indexOf(' ');
        boolean flag;
        int j;
        if (i != -1)
        {
            String s1 = s.substring(i + 1);
            obj = s.substring(1, i);
            s = s1;
        } else
        {
            obj1 = s.substring(1);
            s = ((String) (obj));
            obj = obj1;
        }
        obj1 = sMap;
        j = ((ArrayList) (obj1)).size();
        flag = false;
        i = 0;
_L6:
        if (i < j) goto _L2; else goto _L1
_L1:
        i = ((flag) ? 1 : 0);
_L4:
        if (i == 0)
        {
            sUnknownCommand.addAlias(((String) (obj)));
            sUnknownCommand.run(((String) (obj)), s, helper);
        }
        return;
_L2:
        if (!((Command)((ArrayList) (obj1)).get(i)).run(((String) (obj)), s, helper))
        {
            break; /* Loop/switch isn't completed */
        }
        i = 1;
        if (true) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    static 
    {
        sMap = new ArrayList();
        sMap.add((new Join("join")).addAlias("j"));
        sMap.add(new Me("me"));
        sMap.add(new Nick("nick"));
        sMap.add((new Msg("msg")).addAlias("m"));
        sMap.add(new Part("part"));
        sMap.add(new CTCP("ctcp"));
        sMap.add(new Ignore("ignore"));
        sMap.add(new Clear("clear"));
        sMap.add(new Away("away"));
        sMap.add(new Notice("notice"));
        sMap.add((new Raw("raw")).addAlias("quote"));
        OpActions opactions = new OpActions("_opactions");
        opactions.addAliases(new String[] {
            "op", "deop", "voice", "v", "devoice", "dv", "kb", "ban", "b", "unban", 
            "ub", "hop", "dehop"
        });
        sMap.add(opactions);
        sMap.add((new Kick("kick")).addAlias("k"));
        sMap.add(new ClearAll("clearall"));
        sMap.add(new CM("cm"));
        sMap.add(new CS("cs"));
        sMap.add(new Cycle("cycle"));
        sMap.add(new Invite("invite"));
        sMap.add(new MOTD("motd"));
        sMap.add(new Names("names"));
        sMap.add(new Ping("ping"));
        sMap.add(new Quit("quit"));
        sMap.add((new Topic("topic")).addAlias("t"));
        sMap.add(new UM("um"));
        sMap.add(new Unignore("unignore"));
        sMap.add(new Who("who"));
        sMap.add(new WC("wc"));
    }
}
