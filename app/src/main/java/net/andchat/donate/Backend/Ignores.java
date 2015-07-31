// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.util.SparseArray;
import java.util.List;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            IRCDb

public class Ignores
{
    public static final class IgnoreInfo
    {

        public final String hostname;
        public final String ident;
        public final int mask;
        public String nick;

        public IgnoreInfo(String s, String s1, String s2, int i)
        {
            nick = s;
            ident = s1;
            hostname = s2;
            mask = i;
        }
    }


    private static SparseArray sIgnores = new SparseArray();
    private final IRCDb mDb;
    private final int mId;
    private final List mIgnores;

    private Ignores(int i, IRCDb ircdb)
    {
        mIgnores = ircdb.getIgnores(i);
        mDb = ircdb;
        mId = i;
    }

    public static void clear()
    {
        sIgnores.clear();
    }

    private IgnoreInfo findWithDetails(String s, String s1)
    {
        String s2;
        List list;
        int i;
        int j;
        s2 = s;
        if (s == null)
        {
            s2 = "";
        }
        s = s1;
        if (s1 == null)
        {
            s = "";
        }
        list = mIgnores;
        j = list.size();
        i = 0;
_L6:
        if (i < j) goto _L2; else goto _L1
_L1:
        s1 = null;
_L4:
        return s1;
_L2:
        IgnoreInfo ignoreinfo;
        ignoreinfo = (IgnoreInfo)list.get(i);
        if (!ignoreinfo.ident.equalsIgnoreCase(s2))
        {
            break; /* Loop/switch isn't completed */
        }
        s1 = ignoreinfo;
        if (ignoreinfo.hostname.equalsIgnoreCase(s)) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    private IgnoreInfo findWithNick(String s)
    {
        List list;
        int i;
        int j;
        list = mIgnores;
        j = list.size();
        i = 0;
_L6:
        if (i < j) goto _L2; else goto _L1
_L1:
        IgnoreInfo ignoreinfo = null;
_L4:
        return ignoreinfo;
_L2:
        IgnoreInfo ignoreinfo1;
        ignoreinfo1 = (IgnoreInfo)list.get(i);
        ignoreinfo = ignoreinfo1;
        if (ignoreinfo1.nick.equalsIgnoreCase(s)) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public static Ignores getIgnores(int i, IRCDb ircdb)
    {
        Ignores ignores1 = (Ignores)sIgnores.get(i);
        Ignores ignores = ignores1;
        if (ignores1 == null)
        {
            ignores = new Ignores(i, ircdb);
            sIgnores.put(i, ignores);
        }
        return ignores;
    }

    public void addOrUpdateIgnore(String s, String s1, String s2, int i)
    {
        s = new IgnoreInfo(s, s1, s2, i);
        s1 = findWithDetails(s1, s2);
        i = -1;
        if (s1 != null)
        {
            i = mIgnores.indexOf(s1);
            mIgnores.remove(s1);
        }
        if (i != -1)
        {
            mIgnores.add(i, s);
        } else
        {
            mIgnores.add(s);
        }
        mDb.addOrUpdateIgnore(mId, s);
    }

    public List getAllIgnores()
    {
        return mIgnores;
    }

    public void nickChanged(String s, String s1, String s2, String s3)
    {
        s = findWithDetails(s2, s3);
        if (s != null)
        {
            removeIgnore(s2, s3);
            addOrUpdateIgnore(s1, ((IgnoreInfo) (s)).ident, ((IgnoreInfo) (s)).hostname, ((IgnoreInfo) (s)).mask);
        }
    }

    public boolean removeIgnore(String s)
    {
        s = findWithNick(s);
        if (s != null)
        {
            mIgnores.remove(s);
            mDb.removeFromIgnore(mId, s);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean removeIgnore(String s, String s1)
    {
        s = findWithDetails(s, s1);
        if (s != null)
        {
            mIgnores.remove(s);
            mDb.removeFromIgnore(mId, s);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean shouldIgnore(String s, String s1, int i)
    {
        s = findWithDetails(s, s1);
        if (s == null)
        {
            return false;
        } else
        {
            return Utils.isBitSet(((IgnoreInfo) (s)).mask, i);
        }
    }

}
