// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import java.util.ArrayList;
import java.util.List;
import net.andchat.donate.Misc.LimitedSizeQueue;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            StatusSession, PrivateSession, ChannelSession, SessionManager

public abstract class Session
    implements Comparable
{
    protected static class History
    {

        final List mEntries;
        int mIdx;
        int mLimit;
        CharSequence mSavedLine;

        public void add(CharSequence charsequence)
        {
            if (mLimit == 0)
            {
                return;
            }
            List list = mEntries;
            if (list.size() > mLimit)
            {
                list.remove(0);
            }
            list.add(charsequence);
            mIdx = list.size();
        }

        public CharSequence getDown(CharSequence charsequence)
        {
            if (mLimit != 0)
            {
                List list = mEntries;
                int k = list.size();
                if (k != 0)
                {
                    int i = mIdx;
                    if (i >= 0 && i < k)
                    {
                        CharSequence charsequence1 = (CharSequence)list.get(i);
                        if (charsequence.length() > 0 && !charsequence1.equals(charsequence))
                        {
                            list.set(i, charsequence);
                        }
                    }
                    int j = i + 1;
                    i = j;
                    if (j >= k + 1)
                    {
                        i = j;
                        if (charsequence.length() > 0)
                        {
                            add(charsequence);
                            i = j + 1;
                        }
                    }
                    mIdx = i;
                    if (i < 0 || i >= k)
                    {
                        charsequence = null;
                    } else
                    {
                        charsequence = (CharSequence)list.get(i);
                    }
                    return charsequence;
                }
            }
            return charsequence;
        }

        public CharSequence getUp(CharSequence charsequence)
        {
            if (mLimit != 0) goto _L2; else goto _L1
_L1:
            return charsequence;
_L2:
            List list;
            int l;
            list = mEntries;
            l = list.size();
            if (l == 0) goto _L1; else goto _L3
_L3:
            int k;
            int i = mIdx;
            if (i >= 0 && i < l)
            {
                CharSequence charsequence1 = (CharSequence)list.get(i);
                if (charsequence.length() > 0 && !charsequence1.equals(charsequence))
                {
                    list.set(i, charsequence);
                }
            }
            k = i - 1;
            if (k >= l - 1 && charsequence.length() > 0 && !((CharSequence)list.get(l - 1)).equals(charsequence))
            {
                add(charsequence);
            }
            if (k != l) goto _L5; else goto _L4
_L4:
            int j = k - 1;
_L7:
            k = j;
            if (j < 0)
            {
                k = 0;
            }
            mIdx = k;
            return (CharSequence)list.get(k);
_L5:
            j = k;
            if (k > l)
            {
                j = l - 1;
            }
            if (true) goto _L7; else goto _L6
_L6:
        }

        public void setLimit(int i)
        {
            mLimit = i;
            trimTo(i);
        }

        public void trimTo(int i)
        {
            List list = mEntries;
            if (i == 0)
            {
                list.clear();
                mIdx = 0;
                return;
            }
            for (; list.size() > i; list.remove(0)) { }
            mIdx = list.size();
        }

        public History(int i)
        {
            mEntries = new ArrayList(i);
            setLimit(i);
        }
    }

    public static class MetaData
    {

        public String channelKey;
        public long creationTime;
        public String modes;
        public UserCount userCount;

        public MetaData()
        {
        }
    }

    public static final class NickInfo
    {

        public int colour;
        public String hostname;
        public String ident;
        public String name;
        public int status;

        public String toString()
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("Name = ").append(name).append(", ").append("host = ").append(hostname).append(", ").append(" status = ").append(status);
            return stringbuilder.toString();
        }

        protected NickInfo()
        {
        }
    }

    public static class UserCount
        implements Comparable
    {

        public int admin;
        public int hop;
        public int normal;
        public int op;
        public int owner;
        public int total;
        public int voice;

        public volatile int compareTo(Object obj)
        {
            return compareTo((UserCount)obj);
        }

        public int compareTo(UserCount usercount)
        {
            while (usercount == null || usercount.total != total || usercount.owner != owner || usercount.admin != admin || usercount.op != op || usercount.hop != hop || usercount.voice != voice || usercount.normal != normal) 
            {
                return -1;
            }
            return 0;
        }

        public UserCount()
        {
        }
    }


    public Session()
    {
    }

    static Session createSession(String s, String s1, int i, SessionManager sessionmanager)
    {
        switch (i)
        {
        default:
            throw new IllegalArgumentException("type must be one of TYPE_{STATUS|PRIVATE|CHANNEL}");

        case 0: // '\0'
            return new StatusSession(s, s1, i, sessionmanager);

        case 2: // '\002'
            return new PrivateSession(s, s1, i, sessionmanager);

        case 1: // '\001'
            return new ChannelSession(s, s1, i, sessionmanager);
        }
    }

    public abstract void addHistoricText(CharSequence charsequence);

    public abstract void addMultipleNicks(String s);

    public abstract void addSingleNick(String s, String s1, String s2);

    public abstract void addText(CharSequence charsequence, boolean flag, int i);

    public abstract void changeNick(String s, String s1);

    public abstract void clearAllNicks();

    public abstract void clearTextTypeInfo();

    public abstract int getCurrentTextType();

    public abstract CharSequence getHistoricDownText(CharSequence charsequence);

    public abstract CharSequence getHistoricUpText(CharSequence charsequence);

    public abstract int getMaskForNick(String s);

    public abstract MetaData getMetaData();

    public abstract int getNickColour(String s);

    public abstract NickInfo getNickInfo(String s);

    public abstract List getNickList();

    public abstract String getNickStatus(String s);

    public abstract List getNickSuggestions(String s);

    public abstract String getOwnStatus();

    abstract int getOwnStatusMask();

    public abstract CharSequence getSavedInputText();

    public abstract LimitedSizeQueue getSessionMessages();

    public abstract String getSessionName();

    public abstract int getType();

    public abstract UserCount getUserCount();

    public abstract boolean hasCapability(int i);

    public abstract boolean isActive();

    public abstract boolean isInvalidated();

    public abstract boolean isMarked();

    public abstract boolean isNickInSession(String s);

    public abstract void markValidated();

    public abstract void putSavedInputText(CharSequence charsequence);

    public abstract boolean removeNick(String s);

    public abstract void renameSession(String s);

    public abstract void setActive(boolean flag);

    public abstract void setAsMarked(boolean flag);

    abstract void setInputHistoryLimit(int i);

    public abstract void setNickInfo(String s, String s1, String s2);

    public abstract void trimInputHistory(int i);

    public abstract void updateNickStatus(String s, int i);
}
