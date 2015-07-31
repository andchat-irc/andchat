// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import java.util.ArrayList;
import java.util.List;
import net.andchat.donate.Misc.LimitedSizeQueue;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            Session

class NullSession extends Session
{

    NullSession()
    {
    }

    public void addHistoricText(CharSequence charsequence)
    {
    }

    public void addMultipleNicks(String s)
    {
    }

    public void addSingleNick(String s, String s1, String s2)
    {
    }

    public void addText(CharSequence charsequence, boolean flag, int i)
    {
    }

    public void changeNick(String s, String s1)
    {
    }

    public void clearAllNicks()
    {
    }

    public void clearTextTypeInfo()
    {
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((Session)obj);
    }

    public int compareTo(Session session)
    {
        return 0;
    }

    public int getCurrentTextType()
    {
        return 0;
    }

    public CharSequence getHistoricDownText(CharSequence charsequence)
    {
        return null;
    }

    public CharSequence getHistoricUpText(CharSequence charsequence)
    {
        return null;
    }

    public int getMaskForNick(String s)
    {
        return 0;
    }

    public Session.MetaData getMetaData()
    {
        return new Session.MetaData();
    }

    public int getNickColour(String s)
    {
        return 0;
    }

    public Session.NickInfo getNickInfo(String s)
    {
        return null;
    }

    public List getNickList()
    {
        return new ArrayList(1);
    }

    public String getNickStatus(String s)
    {
        return "";
    }

    public List getNickSuggestions(String s)
    {
        return null;
    }

    public String getOwnStatus()
    {
        return "";
    }

    public int getOwnStatusMask()
    {
        return 0;
    }

    public CharSequence getSavedInputText()
    {
        return null;
    }

    public LimitedSizeQueue getSessionMessages()
    {
        return new LimitedSizeQueue(1, 1);
    }

    public String getSessionName()
    {
        return "null";
    }

    public int getType()
    {
        return 0;
    }

    public Session.UserCount getUserCount()
    {
        return new Session.UserCount();
    }

    public boolean hasCapability(int i)
    {
        return false;
    }

    public boolean isActive()
    {
        return false;
    }

    public boolean isInvalidated()
    {
        return false;
    }

    public boolean isMarked()
    {
        return false;
    }

    public boolean isNickInSession(String s)
    {
        return false;
    }

    public void markValidated()
    {
    }

    public void putSavedInputText(CharSequence charsequence)
    {
    }

    public boolean removeNick(String s)
    {
        return false;
    }

    public void renameSession(String s)
    {
    }

    public void setActive(boolean flag)
    {
    }

    public void setAsMarked(boolean flag)
    {
    }

    public void setInputHistoryLimit(int i)
    {
    }

    public void setNickInfo(String s, String s1, String s2)
    {
    }

    public String toString()
    {
        return getSessionName();
    }

    public void trimInputHistory(int i)
    {
    }

    public void updateNickStatus(String s, int i)
    {
    }
}
