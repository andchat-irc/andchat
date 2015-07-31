// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import android.text.SpannableStringBuilder;
import java.util.ArrayList;
import java.util.List;
import net.andchat.donate.Misc.LimitedSizeQueue;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            Session, SessionManager

abstract class BaseSession extends Session
{

    protected boolean mAmActive;
    private Session.History mHistory;
    private boolean mInvalidated;
    private int mLastType;
    private final SessionManager mManager;
    private boolean mMarked;
    protected final Session.MetaData mMeta = new Session.MetaData();
    private String mName;
    protected String mNick;
    private LimitedSizeQueue mText;
    private int mTextType;
    private int mType;

    BaseSession(String s, String s1, int i, SessionManager sessionmanager)
    {
        mName = s1;
        mType = i;
        mText = new LimitedSizeQueue(1000, 100);
        mManager = sessionmanager;
        mNick = s;
        mAmActive = true;
    }

    public void addHistoricText(CharSequence charsequence)
    {
        if (mHistory == null)
        {
            mHistory = new Session.History(getSessionManager().getInputLimit());
        }
        mHistory.add(charsequence);
    }

    public void addMultipleNicks(String s)
    {
    }

    public void addSingleNick(String s, String s1, String s2)
    {
    }

    public final void addText(CharSequence charsequence, boolean flag, int i)
    {
        mText.add(charsequence);
        if (!flag)
        {
            if (i > mTextType)
            {
                mTextType = i;
            }
        } else
        {
            markValidated();
        }
        if (mLastType != mTextType || !flag) goto _L2; else goto _L1
_L1:
        mInvalidated = false;
_L4:
        mLastType = mTextType;
        return;
_L2:
        if (!flag && mLastType != mTextType)
        {
            mInvalidated = true;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void changeNick(String s, String s1)
    {
        if (s.equalsIgnoreCase(mNick))
        {
            mNick = s1;
        }
    }

    public void clearAllNicks()
    {
    }

    public final void clearTextTypeInfo()
    {
        boolean flag;
        if (mTextType == 0 && mLastType == 0 && !isInvalidated())
        {
            flag = false;
        } else
        {
            flag = true;
        }
        mTextType = 0;
        mLastType = 0;
        markValidated();
        if (flag)
        {
            SessionManager.notifyPostClear();
        }
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((Session)obj);
    }

    public int compareTo(Session session)
    {
        return Utils.STRING_COMPARE.compare(getSessionName(), session.getSessionName());
    }

    public boolean equals(Object obj)
    {
        if (this != obj)
        {
            if (!(obj instanceof Session))
            {
                return false;
            }
            obj = (Session)obj;
            if (getType() != ((Session) (obj)).getType() || !getSessionName().equals(((Session) (obj)).getSessionName()))
            {
                return false;
            }
        }
        return true;
    }

    public final int getCurrentTextType()
    {
        return mTextType;
    }

    public final CharSequence getHistoricDownText(CharSequence charsequence)
    {
        CharSequence charsequence1 = charsequence;
        if (mHistory != null)
        {
            charsequence1 = mHistory.getDown(charsequence);
        }
        return charsequence1;
    }

    public final CharSequence getHistoricUpText(CharSequence charsequence)
    {
        CharSequence charsequence1 = charsequence;
        if (mHistory != null)
        {
            charsequence1 = mHistory.getUp(charsequence);
        }
        return charsequence1;
    }

    public int getMaskForNick(String s)
    {
        return 0;
    }

    public final Session.MetaData getMetaData()
    {
        return mMeta;
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

    int getOwnStatusMask()
    {
        return 0;
    }

    public final CharSequence getSavedInputText()
    {
        if (mHistory != null)
        {
            return mHistory.mSavedLine;
        } else
        {
            return null;
        }
    }

    protected SessionManager getSessionManager()
    {
        return mManager;
    }

    public final LimitedSizeQueue getSessionMessages()
    {
        return mText;
    }

    public final String getSessionName()
    {
        return mName;
    }

    public final int getType()
    {
        return mType;
    }

    public Session.UserCount getUserCount()
    {
        return new Session.UserCount();
    }

    public final boolean isActive()
    {
        return mAmActive;
    }

    public boolean isInvalidated()
    {
        return mInvalidated;
    }

    public boolean isMarked()
    {
        return mMarked;
    }

    public boolean isNickInSession(String s)
    {
        return false;
    }

    public void markValidated()
    {
        mInvalidated = false;
    }

    public final void putSavedInputText(CharSequence charsequence)
    {
        Session.History history = mHistory;
        if (history != null)
        {
            Object obj = charsequence;
            if (charsequence instanceof SpannableStringBuilder)
            {
                obj = new SpannableStringBuilder(charsequence);
            }
            history.mSavedLine = ((CharSequence) (obj));
        } else
        if (charsequence.length() != 0)
        {
            Session.History history1 = new Session.History(getSessionManager().getInputLimit());
            Object obj1 = charsequence;
            if (charsequence instanceof SpannableStringBuilder)
            {
                obj1 = new SpannableStringBuilder(charsequence);
            }
            history1.mSavedLine = ((CharSequence) (obj1));
            mHistory = history1;
            return;
        }
    }

    public boolean removeNick(String s)
    {
        return false;
    }

    public final void renameSession(String s)
    {
        mName = s;
    }

    public void setActive(boolean flag)
    {
    }

    public void setAsMarked(boolean flag)
    {
        mMarked = flag;
    }

    final void setInputHistoryLimit(int i)
    {
        if (mHistory != null)
        {
            mHistory.setLimit(i);
        }
    }

    public void setNickInfo(String s, String s1, String s2)
    {
    }

    public String toString()
    {
        return getSessionName();
    }

    public final void trimInputHistory(int i)
    {
        int j = i;
        if (i < 0)
        {
            j = 0;
        }
        if (mHistory != null)
        {
            mHistory.trimTo(j);
        }
    }

    public void updateNickStatus(String s, int i)
    {
    }
}
