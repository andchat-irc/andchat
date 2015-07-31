// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;

import android.graphics.Color;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            BaseSession, SessionManager

class ChannelSession extends BaseSession
{

    private static final int DEFAULT_COLOUR = Color.rgb(115, 175, 204);
    private static final Comparator sComparator = new Comparator() {

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((Session.NickInfo)obj, (Session.NickInfo)obj1);
        }

        public int compare(Session.NickInfo nickinfo, Session.NickInfo nickinfo1)
        {
            return nickinfo.name.compareToIgnoreCase(nickinfo1.name);
        }

    };
    private Random mBlue;
    private final Session.NickInfo mDummyNick = new Session.NickInfo();
    private Random mGreen;
    private Vector mNames;
    private Random mRed;
    private int mStatusMask;

    ChannelSession(String s, String s1, int i, SessionManager sessionmanager)
    {
        super(s, s1, i, sessionmanager);
        mNames = new Vector();
        mBlue = new Random();
        mGreen = new Random();
        mRed = new Random();
    }

    private Session.NickInfo findNick(String s)
    {
        Vector vector = mNames;
        Session.NickInfo nickinfo = mDummyNick;
        nickinfo.name = s;
        int i = Collections.binarySearch(vector, nickinfo, sComparator);
        if (i < 0)
        {
            return null;
        } else
        {
            return (Session.NickInfo)vector.get(i);
        }
    }

    private int nextColour()
    {
        return Color.rgb(mRed.nextInt(128) + 128, mGreen.nextInt(128) + 128, mBlue.nextInt(128) + 128);
    }

    private void sort()
    {
        synchronized (mNames)
        {
            Collections.sort(mNames, sComparator);
        }
        return;
        exception;
        vector;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void addMultipleNicks(String s)
    {
        String as[];
        Vector vector;
        int i;
        int k;
        int i1;
        as = Utils.split(s);
        vector = mNames;
        i = 0;
        i1 = as.length;
        k = 0;
_L2:
        String s1;
        int l;
        if (k >= i1)
        {
            sort();
            return;
        }
        s1 = as[k];
        l = 0;
        if (s1.length() != 0)
        {
            break; /* Loop/switch isn't completed */
        }
_L3:
        k++;
        if (true) goto _L2; else goto _L1
_L1:
        char c;
        Session.NickInfo nickinfo;
        int j;
        nickinfo = new Session.NickInfo();
        c = s1.charAt(0);
        j = 0;
        s = getSessionManager().getStatusMap();
_L4:
label0:
        {
            if (Utils.isStatusPrefix(s, c))
            {
                break label0;
            }
            s = s1;
            if (j > 0)
            {
                s = s1.substring(j);
            }
            j = i;
            if (i == 0)
            {
                j = i;
                if (s.equals(mNick))
                {
                    mStatusMask = l;
                    getSessionManager().pServ.sendFlaggedMessage(5, getSessionName());
                    j = 1;
                }
            }
            nickinfo.status = l;
            nickinfo.name = s;
            nickinfo.colour = nextColour();
            vector.add(nickinfo);
            i = j;
        }
          goto _L3
        l |= s.getPrefixLevel(c);
        j++;
        c = s1.charAt(j);
          goto _L4
    }

    public void addSingleNick(String s, String s1, String s2)
    {
        if (findNick(s) == null)
        {
            Session.NickInfo nickinfo = new Session.NickInfo();
            nickinfo.name = s;
            nickinfo.ident = s1;
            nickinfo.hostname = s2;
            nickinfo.colour = nextColour();
            mNames.add(nickinfo);
            sort();
        }
    }

    public void changeNick(String s, String s1)
    {
        boolean flag = s.equalsIgnoreCase(mNick);
        super.changeNick(s, s1);
        s = findNick(s);
        if (s != null)
        {
            s.name = s1;
        }
        if (flag)
        {
            if (s != null)
            {
                mStatusMask = ((Session.NickInfo) (s)).status;
            } else
            {
                mStatusMask = 0;
            }
        }
        sort();
    }

    public void clearAllNicks()
    {
        mNames.clear();
    }

    public int getMaskForNick(String s)
    {
        s = findNick(s);
        if (s == null)
        {
            return 0;
        } else
        {
            return ((Session.NickInfo) (s)).status;
        }
    }

    public int getNickColour(String s)
    {
        s = findNick(s);
        if (s == null)
        {
            return DEFAULT_COLOUR;
        } else
        {
            return ((Session.NickInfo) (s)).colour;
        }
    }

    public Session.NickInfo getNickInfo(String s)
    {
        s = findNick(s);
        if (s != null)
        {
            return s;
        } else
        {
            return null;
        }
    }


// JavaClassFileOutputException: Prev chain is broken

    public String getNickStatus(String s)
    {
        int i = getMaskForNick(s);
        return getSessionManager().getStatusMap().convertLevelToPrefix(i);
    }

    public List getNickSuggestions(String s)
    {
        s = Pattern.compile((new StringBuilder("(?i)^")).append(Utils.escape(s)).append("(?:.+)?").toString()).matcher("");
        Vector vector = mNames;
        int j = vector.size();
        ArrayList arraylist = new ArrayList(10);
        int i = 0;
        do
        {
            if (i >= j)
            {
                return arraylist;
            }
            String s1 = ((Session.NickInfo)vector.get(i)).name;
            if (s.reset(s1).matches())
            {
                arraylist.add(s1);
            }
            i++;
        } while (true);
    }

    public String getOwnStatus()
    {
        return getSessionManager().getStatusMap().convertLevelToPrefix(mStatusMask);
    }

    public int getOwnStatusMask()
    {
        return mStatusMask;
    }

    public Session.UserCount getUserCount()
    {
        Vector vector = mNames;
        vector;
        JVM INSTR monitorenter ;
        int i;
        int j;
        int l;
        int i1;
        int j1;
        int k1;
        j = 0;
        k1 = 0;
        l = 0;
        j1 = 0;
        i = 0;
        i1 = 0;
        Object obj;
        int l1;
        int i2;
        int j2;
        int k2;
        int l2;
        int i3;
        obj = mNames;
        l1 = ((Vector) (obj)).size();
        SessionManager sessionmanager = getSessionManager();
        i2 = sessionmanager.getStatusMap().getModeLevel('q');
        j2 = sessionmanager.getStatusMap().getModeLevel('a');
        k2 = sessionmanager.getStatusMap().getModeLevel('o');
        l2 = sessionmanager.getStatusMap().getModeLevel('h');
        i3 = sessionmanager.getStatusMap().getModeLevel('v');
        int k = 0;
_L2:
        if (k < l1)
        {
            break MISSING_BLOCK_LABEL_168;
        }
        obj = new Session.UserCount();
        obj.owner = j;
        obj.admin = k1;
        obj.op = l;
        obj.hop = j1;
        obj.voice = i;
        obj.normal = i1;
        obj.total = l1;
        mMeta.userCount = ((Session.UserCount) (obj));
        vector;
        JVM INSTR monitorexit ;
        return ((Session.UserCount) (obj));
        int j3 = ((Session.NickInfo)((Vector) (obj)).get(k)).status;
        if (Utils.isBitSet(j3, i2))
        {
            j++;
            break MISSING_BLOCK_LABEL_282;
        }
        if (Utils.isBitSet(j3, j2))
        {
            k1++;
            break MISSING_BLOCK_LABEL_282;
        }
        if (Utils.isBitSet(j3, k2))
        {
            l++;
            break MISSING_BLOCK_LABEL_282;
        }
        if (Utils.isBitSet(j3, l2))
        {
            j1++;
            break MISSING_BLOCK_LABEL_282;
        }
        Exception exception;
        if (Utils.isBitSet(j3, i3))
        {
            i++;
        } else
        {
            i1++;
        }
        break MISSING_BLOCK_LABEL_282;
        exception;
        vector;
        JVM INSTR monitorexit ;
        throw exception;
        k++;
        if (true) goto _L2; else goto _L1
_L1:
    }

    public boolean hasCapability(int i)
    {
        switch (i)
        {
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        default:
            return false;

        case 1: // '\001'
        case 5: // '\005'
        case 6: // '\006'
            return true;
        }
    }

    public boolean isNickInSession(String s)
    {
        return findNick(s) != null;
    }

    public boolean removeNick(String s)
    {
        s = findNick(s);
        if (s != null)
        {
            mNames.remove(s);
            return true;
        } else
        {
            return false;
        }
    }

    public void setActive(boolean flag)
    {
        mAmActive = flag;
    }

    public void setNickInfo(String s, String s1, String s2)
    {
        Session.NickInfo nickinfo = findNick(s);
        if (nickinfo == null)
        {
            Log.e(getSessionName(), (new StringBuilder("Unable to find info for ")).append(s).toString());
            return;
        } else
        {
            nickinfo.ident = s1;
            nickinfo.hostname = s2;
            return;
        }
    }

    public void updateNickStatus(String s, int i)
    {
        if (s.equals(mNick))
        {
            mStatusMask = i;
            getSessionManager().pServ.sendFlaggedMessage(5, getSessionName());
        }
        s = findNick(s);
        if (s != null)
        {
            s.status = i;
        }
    }

}
