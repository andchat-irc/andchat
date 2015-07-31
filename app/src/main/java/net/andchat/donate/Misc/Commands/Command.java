// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands;

import java.util.ArrayList;
import java.util.List;
import net.andchat.donate.Misc.IRCMessage;

public abstract class Command
{

    private final List mAlias = new ArrayList();
    private final String mCommand;

    public Command(String s)
    {
        mCommand = transformStr(s);
    }

    public static String transformStr(String s)
    {
        return s.toLowerCase();
    }

    public final Command addAlias(String s)
    {
        if (!isAlias(s))
        {
            mAlias.add(transformStr(s));
        }
        return this;
    }

    public final transient Command addAliases(String as[])
    {
        int i = 0;
        do
        {
            if (i >= as.length)
            {
                return this;
            }
            addAlias(as[i]);
            i++;
        } while (true);
    }

    protected abstract void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper);

    public final boolean isAlias(String s)
    {
        return mAlias.contains(transformStr(s));
    }

    public final boolean run(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        boolean flag;
        if (!mCommand.equals(transformStr(s)) && !isAlias(transformStr(s)))
        {
            flag = false;
        } else
        {
            flag = true;
        }
        if (flag)
        {
            execute(s, s1, helper);
            return true;
        } else
        {
            return false;
        }
    }

    protected final void sendMessage(String s, CharSequence charsequence, int i, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        IRCMessage ircmessage = IRCMessage.obtain();
        ircmessage.set(s, charsequence, i);
        helper.handleAddMessage(ircmessage);
        ircmessage.recycle();
    }
}
