// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Misc.IRCMessage;
import net.andchat.donate.Misc.LimitedSizeQueue;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            NullSession, Session

public final class SessionManager
    implements Comparable
{
    public static interface GlobalEventWatcher
    {

        public abstract void onCurrentSessionChanged(int i, String s);

        public abstract void onMessageReceived(String s, int i, int j);

        public abstract void onSessionActivated(String s, int i);

        public abstract void onSessionAdded(String s, int i, int j);

        public abstract void onSessionDeactivated(String s, int i);

        public abstract void onSessionManagerDeleted(int i);

        public abstract void onSessionRemoved(String s, int i);

        public abstract void onSessionRenamed(String s, String s1);

        public abstract void onSessionTextStateCleared();
    }

    public static interface NickWatcher
    {

        public abstract void onNickAdded(String s, String s1);

        public abstract void onNickChanged(String s, String s1, String s2);

        public abstract void onNickModeChanged(String s, String s1, String s2);

        public abstract void onNickRemoved(String s, String s1);

        public abstract void onNicksReceived(String s);
    }

    public static final class StatusMap
    {

        private final int mLevels[];
        private final char mModes[];
        private final char mPrefixes[];

        private int getLevel(char c, boolean flag)
        {
            char ac[];
            int i;
            int j;
            if (flag)
            {
                ac = mPrefixes;
            } else
            {
                ac = mModes;
            }
            j = ac.length;
            i = 0;
            do
            {
                if (i >= j)
                {
                    return -1;
                }
                if (c == ac[i])
                {
                    return mLevels[i];
                }
                i++;
            } while (true);
        }

        public String convertLevelToPrefix(int i)
        {
            int ai[] = mLevels;
            int k = ai.length;
            int j = Arrays.binarySearch(ai, i);
            if (j > 0)
            {
                return String.valueOf(mPrefixes[j]);
            }
            j = 0;
            do
            {
                if (j >= k)
                {
                    return "";
                }
                if ((ai[j] & i) == ai[j])
                {
                    return String.valueOf(mPrefixes[j]);
                }
                j++;
            } while (true);
        }

        public int getModeLevel(char c)
        {
            return getLevel(c, false);
        }

        public int getPrefixLevel(char c)
        {
            return getLevel(c, true);
        }

        public String getPrefixes()
        {
            return new String(mPrefixes);
        }

        public boolean isMode(char c)
        {
            char ac[] = mModes;
            int j = ac.length;
            int i = 0;
            do
            {
                if (i >= j)
                {
                    return false;
                }
                if (c == ac[i])
                {
                    return true;
                }
                i++;
            } while (true);
        }

        public boolean isPrefix(char c)
        {
            char ac[] = mPrefixes;
            int j = ac.length;
            int i = 0;
            do
            {
                if (i >= j)
                {
                    return false;
                }
                if (c == ac[i])
                {
                    return true;
                }
                i++;
            } while (true);
        }

        public char mapModeToPrefix(char c)
        {
            char ac[] = mModes;
            int j = ac.length;
            int i = 0;
            do
            {
                if (i >= j)
                {
                    return '?';
                }
                if (c == ac[i])
                {
                    return mPrefixes[i];
                }
                i++;
            } while (true);
        }

        private StatusMap(String s, String s1)
        {
            String s3 = s;
            String s2 = s1;
            if (s.length() != s1.length())
            {
                s3 = "ov";
                s2 = "@+";
                Log.w("SessionManager", "Mismatch between modes.length & prefixes.length. Falling back to basic support");
            }
            mModes = s3.toCharArray();
            mPrefixes = s2.toCharArray();
            int j = mModes.length;
            mLevels = new int[j];
            int i = 0;
            do
            {
                if (i >= j)
                {
                    return;
                }
                mLevels[i] = 1 << i;
                i++;
            } while (true);
        }

        StatusMap(String s, String s1, StatusMap statusmap)
        {
            this(s, s1);
        }
    }


    public static final Session NULL_SESSION = new NullSession();
    private static List sGlobalWatchers = new Vector();
    private Session mCurrentSession;
    private final int mId;
    private int mInputLimit;
    private StatusMap mMap;
    private List mNickWatchers;
    private final ServerProfile mProfile;
    private List mSessions;
    ServerConnection pServ;

    public SessionManager(int i, ServerProfile serverprofile)
    {
        mCurrentSession = NULL_SESSION;
        mSessions = new Vector();
        mNickWatchers = new Vector();
        Session session = Session.createSession("", "Status", 0, this);
        mSessions.add(session);
        mId = i;
        mProfile = serverprofile;
        notifySessionAdd("Status", 0, i);
    }

    public static void addEventWatcher(GlobalEventWatcher globaleventwatcher)
    {
        List list;
        int i;
        list = sGlobalWatchers;
        i = list.size() - 1;
_L6:
        if (i >= 0) goto _L2; else goto _L1
_L1:
        list.add(globaleventwatcher);
_L4:
        return;
_L2:
        if (list.get(i) == globaleventwatcher) goto _L4; else goto _L3
_L3:
        i--;
        if (true) goto _L6; else goto _L5
_L5:
    }

    private String filterModes(boolean flag, String s)
    {
        int j;
        int k;
        int l;
        k = 0;
        l = s.length();
        j = 1;
_L2:
        int i;
        if (j >= l)
        {
            return s.substring(k + 1);
        }
        char c = s.charAt(j);
        i = k;
        switch (c)
        {
        default:
            i = k;
            if (!mMap.isMode(c))
            {
                i = k + 1;
            }
            break;

        case 73: // 'I'
        case 97: // 'a'
        case 98: // 'b'
        case 101: // 'e'
        case 104: // 'h'
        case 107: // 'k'
        case 111: // 'o'
        case 113: // 'q'
        case 118: // 'v'
            break;

        case 108: // 'l'
            break; /* Loop/switch isn't completed */
        }
_L3:
        j++;
        k = i;
        if (true) goto _L2; else goto _L1
_L1:
        i = k;
        if (!flag)
        {
            i = k + 1;
        }
          goto _L3
        if (true) goto _L2; else goto _L4
_L4:
    }

    private Session get(String s)
    {
        List list;
        int i;
        int j;
        list = mSessions;
        j = list.size();
        i = 0;
_L6:
        if (i < j) goto _L2; else goto _L1
_L1:
        Session session = null;
_L4:
        return session;
_L2:
        Session session1;
        session1 = (Session)list.get(i);
        session = session1;
        if (session1.getSessionName().equalsIgnoreCase(s)) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    private int getMask(int i, int j, boolean flag)
    {
        if (flag)
        {
            return i | j;
        } else
        {
            return ~j & i;
        }
    }

    private static int getPlusMinusCount(String s)
    {
        int i;
        int j;
        int k;
        j = 0;
        k = s.length();
        i = 0;
_L5:
        if (i < k) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        s.charAt(i);
        JVM INSTR lookupswitch 3: default 56
    //                   32: 14
    //                   43: 63
    //                   45: 63;
           goto _L3 _L1 _L4 _L4
_L3:
        i++;
          goto _L5
_L4:
        j++;
          goto _L3
    }

    private void handleMultipleModeChanges(String s, String s1, String s2)
    {
        int i = s1.indexOf(' ');
        if (i != -1) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s3;
        Object obj;
        int i1;
        obj = s1.substring(i + 1);
        s3 = s1.substring(0, i);
        i1 = getPlusMinusCount(s1);
        i = 0;
_L6:
        if (i >= i1) goto _L1; else goto _L3
_L3:
        int j;
label0:
        {
            int k = s3.lastIndexOf('+');
            if (k != -1)
            {
                j = k;
                if (k != 0)
                {
                    break label0;
                }
            }
            j = s3.lastIndexOf('-');
        }
        boolean flag;
        if (j == -1)
        {
            Object obj1;
            String as[];
            int l;
            if (s3.charAt(0) == '+')
            {
                flag = true;
            } else
            {
                flag = false;
            }
            s1 = filterModes(flag, s3);
        } else
        if (j == 0)
        {
            if (s3.charAt(j) == '+')
            {
                flag = true;
            } else
            {
                flag = false;
            }
            s1 = filterModes(flag, s3);
        } else
        {
            s1 = s3.substring(j);
            if (s1.charAt(0) == '+')
            {
                flag = true;
            } else
            {
                flag = false;
            }
            s1 = filterModes(flag, s1);
        }
        obj1 = obj;
        if (s1.length() != 0)
        {
            l = s1.length();
            as = Utils.split(((CharSequence) (obj)));
            obj1 = as;
            if (as.length > l)
            {
                obj1 = new String[l];
                System.arraycopy(as, as.length - l, obj1, 0, l);
            }
            obj = ((String) (obj)).substring(0, ((String) (obj)).lastIndexOf(obj1[0])).trim();
            parseModes(flag, s, s1, ((String []) (obj1)), s2);
            obj1 = obj;
        }
        if (j == -1 || ((String) (obj1)).length() == 0) goto _L1; else goto _L4
_L4:
        s3 = s3.substring(0, j);
        if (s3.length() == 0) goto _L1; else goto _L5
_L5:
        i++;
        obj = obj1;
          goto _L6
    }

    private void handleSingleModeChange(String s, String s1, String s2)
    {
        boolean flag = false;
        int i = s1.indexOf(' ');
        if (i == -1)
        {
            return;
        }
        String s3 = s1.substring(0, i);
        if (s1.charAt(0) == '+')
        {
            flag = true;
        }
        parseModes(flag, s, filterModes(flag, s3), Utils.split(s1.substring(i + 1)), s2);
    }

    public static void notifyPostClear()
    {
        List list = sGlobalWatchers;
        int j = list.size();
        int i = 0;
        do
        {
            if (i >= j)
            {
                return;
            }
            GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)list.get(i);
            if (globaleventwatcher != null)
            {
                globaleventwatcher.onSessionTextStateCleared();
            }
            i++;
        } while (true);
    }

    public static void notifyPostDelete(int i)
    {
        List list = sGlobalWatchers;
        int k = list.size();
        int j = 0;
        do
        {
            if (j >= k)
            {
                return;
            }
            GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)list.get(j);
            if (globaleventwatcher != null)
            {
                globaleventwatcher.onSessionManagerDeleted(i);
            }
            j++;
        } while (true);
    }

    private static void notifySessionAdd(String s, int i, int j)
    {
        List list = sGlobalWatchers;
        int l = list.size();
        int k = 0;
        do
        {
            if (k >= l)
            {
                return;
            }
            GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)list.get(k);
            if (globaleventwatcher != null)
            {
                globaleventwatcher.onSessionAdded(s, i, j);
            }
            k++;
        } while (true);
    }

    private void parseModes(boolean flag, String s, String s1, String as[], String s2)
    {
        Session session = get(s);
        if (session != null) goto _L2; else goto _L1
_L1:
        int k;
        return;
_L2:
        if ((k = s1.length()) == 0) goto _L1; else goto _L3
_L3:
        List list;
        int j;
        list = mNickWatchers;
        j = 0;
_L10:
        if (j >= k) goto _L1; else goto _L4
_L4:
        char c = s1.charAt(j);
        c;
        JVM INSTR tableswitch 107 107: default 68
    //                   107 167;
           goto _L5 _L6
_L5:
        if (!mMap.isMode(c)) goto _L8; else goto _L7
_L7:
        String s3;
        int i;
        int l;
        s3 = as[j];
        l = getMask(session.getMaskForNick(s3), mMap.getModeLevel(c), flag);
        boolean flag1 = s3.equals(s2);
        session.updateNickStatus(s3, l);
        if (flag1)
        {
            pServ.sendFlaggedMessage(5, s);
        }
        i = list.size() - 1;
_L11:
        if (i >= 0) goto _L9; else goto _L8
_L8:
        j++;
          goto _L10
_L6:
        Session.MetaData metadata = session.getMetaData();
        if (flag)
        {
            s3 = as[j];
        } else
        {
            s3 = null;
        }
        metadata.channelKey = s3;
          goto _L8
_L9:
        NickWatcher nickwatcher = (NickWatcher)list.get(i);
        if (nickwatcher == null)
        {
            list.remove(i);
            i--;
        } else
        {
            nickwatcher.onNickModeChanged(s, s3, mMap.convertLevelToPrefix(l));
        }
        i--;
          goto _L11
    }

    public static void removeEventWatcher(GlobalEventWatcher globaleventwatcher)
    {
        List list = sGlobalWatchers;
        int i = list.size() - 1;
        do
        {
            if (i < 0)
            {
                return;
            }
            if (list.get(i) == globaleventwatcher)
            {
                list.remove(i);
                return;
            }
            i--;
        } while (true);
    }

    public void addName(String s, String s1, String s2, String s3)
    {
        Session session = get(s);
        if (session != null)
        {
            session.addSingleNick(s1, s2, s3);
            s2 = mNickWatchers;
            int i = s2.size() - 1;
            while (i >= 0) 
            {
                s3 = (NickWatcher)s2.get(i);
                if (s3 == null)
                {
                    s2.remove(i);
                    i--;
                } else
                {
                    s3.onNickAdded(s, s1);
                }
                i--;
            }
        }
    }

    public void addNewNames(String s, String s1, String s2)
    {
        Session session1 = get(s1);
        Session session = session1;
        if (session1 == null)
        {
            session = Session.createSession(s, s1, 1, this);
            mSessions.add(session);
        }
        session.addMultipleNicks(s2);
    }

    public void addNickWatcher(NickWatcher nickwatcher)
    {
        List list;
        int i;
        list = mNickWatchers;
        i = list.size() - 1;
_L6:
        if (i >= 0) goto _L2; else goto _L1
_L1:
        list.add(nickwatcher);
_L4:
        return;
_L2:
        if (list.get(i) == nickwatcher) goto _L4; else goto _L3
_L3:
        i--;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void addSession(String s, String s1, int i)
    {
        if (mMap == null)
        {
            buildStatusMap("qaohv", "~&@%+");
        }
        if (get(s) == null)
        {
            s1 = Session.createSession(s1, s, i, this);
            mSessions.add(s1);
            notifySessionAdd(s, i, mId);
            return;
        } else
        {
            setActive(s, true);
            return;
        }
    }

    public void addText(IRCMessage ircmessage, boolean flag)
    {
        Session session;
        Session session1 = get(ircmessage.target);
        session = session1;
        if (session1 == null)
        {
            session = get("Status");
            ircmessage.target = "Status";
        }
        if (session == null) goto _L2; else goto _L1
_L1:
        List list;
        int i;
        int j;
        list = sGlobalWatchers;
        j = list.size();
        i = 0;
_L6:
        if (i < j) goto _L4; else goto _L3
_L3:
        session.addText(ircmessage.message, flag, ircmessage.val);
_L2:
        ircmessage.recycle();
        return;
_L4:
        GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)list.get(i);
        if (globaleventwatcher != null)
        {
            globaleventwatcher.onMessageReceived(ircmessage.target, ircmessage.val, mId);
        }
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void buildStatusMap(String s, String s1)
    {
        mMap = new StatusMap(s, s1, null);
    }

    public void checkForModes(String s, String s1, String s2)
    {
        if (s.length() == 0 || !Utils.isChannelPrefix(s.charAt(0)))
        {
            return;
        }
        switch (getPlusMinusCount(s1))
        {
        default:
            handleMultipleModeChanges(s, s1, s2);
            return;

        case 1: // '\001'
            handleSingleModeChange(s, s1, s2);
            break;
        }
    }

    public void clearScrollbackForAll()
    {
        List list = mSessions;
        int j = mSessions.size();
        int i = 0;
        do
        {
            if (i >= j)
            {
                return;
            }
            ((Session)list.get(i)).trimInputHistory(0);
            i++;
        } while (true);
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((SessionManager)obj);
    }

    public int compareTo(SessionManager sessionmanager)
    {
        return mProfile.getName().compareToIgnoreCase(sessionmanager.mProfile.getName());
    }

    public void deleteText(String s, int i)
    {
        String s1 = s;
        if (s.length() == 0)
        {
            s1 = "Status";
        }
        s = get(s1);
        if (s != null)
        {
            s.getSessionMessages().removeWithLimit(i);
        }
    }

    public boolean getActive(String s)
    {
        s = get(s);
        if (s != null)
        {
            return s.isActive();
        } else
        {
            return false;
        }
    }

    public Session getCurrentSession()
    {
        return mCurrentSession;
    }

    public Session getDefaultSession()
    {
        return get("Status");
    }

    public int getId()
    {
        return mId;
    }

    int getInputLimit()
    {
        return mInputLimit;
    }

    public String getKey(String s)
    {
        if (Utils.isChannelPrefix(s.charAt(0)))
        {
            if ((s = get(s)) != null)
            {
                return s.getMetaData().channelKey;
            }
        }
        return null;
    }

    public Session.MetaData getMetadata(String s)
    {
        s = get(s);
        if (s != null)
        {
            return s.getMetaData();
        } else
        {
            return null;
        }
    }

    public int getNickColor(String s, String s1)
    {
        s1 = get(s1);
        if (s1 != null)
        {
            return s1.getNickColour(s);
        } else
        {
            return 0;
        }
    }

    public List getNickList(String s)
    {
        s = get(s);
        if (s != null)
        {
            return s.getNickList();
        } else
        {
            return Collections.emptyList();
        }
    }

    public String getNickStatus(String s, String s1)
    {
        s1 = get(s1);
        if (s1 != null)
        {
            return s1.getNickStatus(s);
        } else
        {
            return "";
        }
    }

    public String getOurStatus(String s)
    {
        s = get(s);
        if (s != null)
        {
            return mMap.convertLevelToPrefix(s.getOwnStatusMask());
        } else
        {
            return "";
        }
    }

    public ServerProfile getProfile()
    {
        return mProfile;
    }

    public String[] getQuitSessions(String s)
    {
        StringBuilder stringbuilder;
        List list;
        List list1;
        int j;
        int k;
        stringbuilder = new StringBuilder();
        list = mSessions;
        k = list.size();
        list1 = mNickWatchers;
        j = 0;
_L3:
        String s1;
        Session session;
        if (j >= k)
        {
            return Utils.split(stringbuilder.toString().trim());
        }
        session = (Session)list.get(j);
        s1 = session.getSessionName();
        if (!session.removeNick(s)) goto _L2; else goto _L1
_L1:
        int i;
        stringbuilder.append(" ").append(s1);
        i = list1.size() - 1;
_L4:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_115;
        }
_L2:
        j++;
          goto _L3
        NickWatcher nickwatcher = (NickWatcher)list1.get(i);
        if (nickwatcher == null)
        {
            list1.remove(i);
            i--;
        } else
        {
            nickwatcher.onNickRemoved(s1, s);
        }
        i--;
          goto _L4
    }

    public Session getSession(String s)
    {
        return get(s);
    }

    public int getSessionCount()
    {
        return mSessions.size();
    }

    public String[] getSessionList()
    {
        List list = mSessions;
        int j = list.size();
        String as[] = new String[j];
        int i = 0;
        do
        {
            if (i >= j)
            {
                return as;
            }
            as[i] = ((Session)list.get(i)).getSessionName();
            i++;
        } while (true);
    }

    public List getSessionObjects()
    {
        ArrayList arraylist;
        synchronized (mSessions)
        {
            arraylist = new ArrayList();
            arraylist.addAll(mSessions);
        }
        return arraylist;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public StatusMap getStatusMap()
    {
        return mMap;
    }

    public Session.UserCount getUserCount(String s)
    {
        Session session = get(s);
        List list = mNickWatchers;
        int i = list.size() - 1;
        do
        {
            NickWatcher nickwatcher;
            if (i < 0)
            {
                if (session != null)
                {
                    return session.getUserCount();
                } else
                {
                    return new Session.UserCount();
                }
            }
            nickwatcher = (NickWatcher)list.get(i);
            if (nickwatcher == null)
            {
                list.remove(i);
                i--;
            } else
            {
                nickwatcher.onNicksReceived(s);
            }
            i--;
        } while (true);
    }

    public String[] getUserSessions(String s)
    {
        StringBuilder stringbuilder = new StringBuilder();
        List list = mSessions;
        int j = list.size();
        int i = 0;
        do
        {
            Session session;
            String s1;
            if (i >= j)
            {
                switch (stringbuilder.length())
                {
                default:
                    return Utils.split(stringbuilder.toString().trim());

                case 0: // '\0'
                    return new String[0];
                }
            }
            session = (Session)list.get(i);
            s1 = session.getSessionName();
            if (session.isNickInSession(s))
            {
                stringbuilder.append(' ').append(s1);
            }
            i++;
        } while (true);
    }

    public boolean haveSessionFor(String s)
    {
        return get(s) != null;
    }

    public boolean isMarked(String s)
    {
        if (s.length() != 0)
        {
            if ((s = get(s)) != null)
            {
                return s.isMarked();
            }
        }
        return false;
    }

    public boolean needsRefresh()
    {
        List list = mSessions;
        int i = list.size() - 1;
        do
        {
            if (i < 0)
            {
                return false;
            }
            if (((Session)list.get(i)).isInvalidated())
            {
                return true;
            }
            i--;
        } while (true);
    }

    public void removeAllNames(String s)
    {
        s = get(s);
        if (s != null)
        {
            s.clearAllNicks();
        }
    }

    public void removeName(String s, String s1)
    {
        Object obj = get(s);
        if (obj != null)
        {
            ((Session) (obj)).removeNick(s1);
            obj = mNickWatchers;
            int i = ((List) (obj)).size() - 1;
            while (i >= 0) 
            {
                NickWatcher nickwatcher = (NickWatcher)((List) (obj)).get(i);
                if (nickwatcher == null)
                {
                    ((List) (obj)).remove(i);
                    i--;
                } else
                {
                    nickwatcher.onNickRemoved(s, s1);
                }
                i--;
            }
        }
    }

    public void removeNickWatcher(NickWatcher nickwatcher)
    {
        List list = mNickWatchers;
        int i = list.size() - 1;
        do
        {
            if (i < 0)
            {
                return;
            }
            if (list.get(i) == nickwatcher)
            {
                list.remove(i);
                return;
            }
            i--;
        } while (true);
    }

    public void removeSession(String s)
    {
        Object obj = get(s);
        if (obj == null)
        {
            Log.e("SessionManager", "Unpossible! Session not found");
        }
        if (((Session) (obj)).hasCapability(6))
        {
            mSessions.remove(obj);
            obj = sGlobalWatchers;
            int j = ((List) (obj)).size();
            int i = 0;
            while (i < j) 
            {
                GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)((List) (obj)).get(i);
                if (globaleventwatcher != null)
                {
                    globaleventwatcher.onSessionRemoved(s, mId);
                }
                i++;
            }
        }
    }

    public void setActive(String s, boolean flag)
    {
        Object obj;
        int i;
        int j;
        obj = get(s);
        if (obj != null)
        {
            ((Session) (obj)).setActive(flag);
        }
        obj = sGlobalWatchers;
        j = ((List) (obj)).size();
        i = 0;
_L2:
        GlobalEventWatcher globaleventwatcher;
        if (i >= j)
        {
            return;
        }
        globaleventwatcher = (GlobalEventWatcher)((List) (obj)).get(i);
        if (globaleventwatcher != null)
        {
            break; /* Loop/switch isn't completed */
        }
_L3:
        i++;
        if (true) goto _L2; else goto _L1
_L1:
        if (flag)
        {
            globaleventwatcher.onSessionActivated(s, mId);
        } else
        {
            globaleventwatcher.onSessionDeactivated(s, mId);
        }
          goto _L3
        if (true) goto _L2; else goto _L4
_L4:
    }

    public void setCurrentSession(Session session)
    {
        mCurrentSession = session;
        List list = sGlobalWatchers;
        int j = list.size();
        int i = 0;
        do
        {
            if (i >= j)
            {
                return;
            }
            GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)list.get(i);
            if (globaleventwatcher != null)
            {
                globaleventwatcher.onCurrentSessionChanged(mId, session.getSessionName());
            }
            i++;
        } while (true);
    }

    public void setIdentAndHost(String s, String s1, String s2, String s3)
    {
        s = get(s);
        if (s != null)
        {
            s.setNickInfo(s1, s2, s3);
        }
    }

    public void setInputLimit(int i)
    {
        mInputLimit = i;
        List list = mSessions;
        list;
        JVM INSTR monitorenter ;
        List list1;
        int k;
        list1 = mSessions;
        k = list1.size();
        int j = 0;
_L2:
        if (j < k)
        {
            break MISSING_BLOCK_LABEL_38;
        }
        list;
        JVM INSTR monitorexit ;
        return;
        ((Session)list1.get(j)).setInputHistoryLimit(i);
        j++;
        if (true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setMarked(String s, boolean flag)
    {
        if (s.length() != 0)
        {
            if ((s = get(s)) != null)
            {
                s.setAsMarked(flag);
                return;
            }
        }
    }

    public void setServ(ServerConnection serverconnection)
    {
        if (pServ == null)
        {
            pServ = serverconnection;
        }
        synchronized (pServ)
        {
            pServ = serverconnection;
        }
        return;
        serverconnection;
        serverconnection1;
        JVM INSTR monitorexit ;
        throw serverconnection;
    }

    public void updateNick(String s, String s1, String as[])
    {
        int i;
        int l;
        l = as.length;
        i = 0;
_L2:
        Session session;
        if (i >= l)
        {
            return;
        }
        session = get(as[i]);
        if (session != null)
        {
            break; /* Loop/switch isn't completed */
        }
_L3:
        i++;
        if (true) goto _L2; else goto _L1
_L1:
        List list;
        int j;
        int k;
        list = mNickWatchers;
        k = list.size();
        j = 0;
_L4:
label0:
        {
            if (j < k)
            {
                break label0;
            }
            session.changeNick(s, s1);
        }
          goto _L3
        NickWatcher nickwatcher = (NickWatcher)list.get(j);
        if (nickwatcher == null)
        {
            list.remove(j);
            k--;
        } else
        {
            nickwatcher.onNickChanged(session.getSessionName(), s, s1);
        }
        j++;
          goto _L4
    }

    public boolean updateSessionName(String s, String s1)
    {
        Object obj = get(s);
        if (obj != null)
        {
            ((Session) (obj)).renameSession(s1);
            obj = sGlobalWatchers;
            int j = ((List) (obj)).size();
            int i = 0;
            do
            {
                if (i >= j)
                {
                    return true;
                }
                GlobalEventWatcher globaleventwatcher = (GlobalEventWatcher)((List) (obj)).get(i);
                if (globaleventwatcher != null)
                {
                    globaleventwatcher.onSessionRenamed(s, s1);
                }
                i++;
            } while (true);
        } else
        {
            return false;
        }
    }

}
