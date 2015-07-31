// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.os.Message;
import java.util.concurrent.ArrayBlockingQueue;
import net.andchat.donate.Activities.ChatWindow;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.IRCMessage;

public class MessageSender
{

    private final int mId;
    private SessionManager mManager;
    private ChatWindow mUi;

    public MessageSender(int i)
    {
        mId = i;
    }

    public boolean haveUi()
    {
        return mUi != null;
    }

    public void sendFlaggedMessage(int i, Object obj)
    {
        if (mUi != null)
        {
            Message.obtain(mUi.flagHandler, i, obj).sendToTarget();
        }
    }

    public void sendMessage(String s, CharSequence charsequence, int i)
    {
        String s1 = s;
        if (s.length() == 0)
        {
            s1 = "Status";
        }
        s = IRCMessage.obtain();
        s.set(s1, charsequence, i);
        if (mUi == null || mUi.messageHandler == null)
        {
            mManager.addText(s, false);
        } else
        {
            try
            {
                mUi.blocker.put(s);
                mUi.messageHandler.sendEmptyMessage(0);
            }
            // Misplaced declaration of an exception variable
            catch (String s) { }
            // Misplaced declaration of an exception variable
            catch (String s) { }
        }
        if (i != 1)
        {
            mManager.setMarked(s1, true);
        }
    }

    public void setSessionManager(SessionManager sessionmanager)
    {
        mManager = sessionmanager;
    }

    public void setUi(ChatWindow chatwindow)
    {
        mUi = chatwindow;
    }
}
