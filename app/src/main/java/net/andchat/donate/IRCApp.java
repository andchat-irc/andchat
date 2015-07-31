// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.List;
import java.util.Vector;
import net.andchat.donate.Activities.CurrentUI;
import net.andchat.donate.Activities.LegacyUI;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Misc.Colours;

public class IRCApp extends Application
{
    public static interface PreferenceChangeWatcher
    {

        public abstract void onPreferencesChanged();
    }


    public static final Class CHAT_CLASS;
    public static final boolean DONATE;
    public static final boolean LEGACY_VERSION;
    private Crypt mCrypt;
    private IRCDb mDb;
    private SharedPreferences mPrefs;
    private final List mWatchers = new Vector(3);

    public IRCApp()
    {
    }

    private void initCrypt()
    {
        if (mCrypt == null)
        {
            mCrypt = new Crypt(this);
        }
    }

    private void initDb()
    {
        if (mDb == null)
        {
            mDb = new IRCDb(this);
        }
    }

    private void initPrefs()
    {
        if (mPrefs == null)
        {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        }
    }

    public void addWatcher(PreferenceChangeWatcher preferencechangewatcher)
    {
        List list;
        int i;
        list = mWatchers;
        i = list.size() - 1;
_L6:
        if (i >= 0) goto _L2; else goto _L1
_L1:
        list.add(preferencechangewatcher);
_L4:
        return;
_L2:
        if (list.get(i) == preferencechangewatcher) goto _L4; else goto _L3
_L3:
        i--;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void clearCrypt()
    {
        if (mCrypt != null)
        {
            mCrypt.clear();
            mCrypt = null;
        }
    }

    public void closeDb()
    {
        if (mDb != null)
        {
            mDb.shutdown();
            mDb = null;
        }
    }

    public Crypt getCrypt()
    {
        initCrypt();
        return mCrypt;
    }

    public IRCDb getDb()
    {
        initDb();
        return mDb;
    }

    public SharedPreferences getPrefs()
    {
        initPrefs();
        return mPrefs;
    }

    public void notifyWatchers()
    {
        List list = mWatchers;
        int i = list.size() - 1;
        do
        {
            if (i < 0)
            {
                return;
            }
            PreferenceChangeWatcher preferencechangewatcher = (PreferenceChangeWatcher)list.get(i);
            if (preferencechangewatcher == null)
            {
                list.remove(i);
                i--;
            } else
            {
                preferencechangewatcher.onPreferencesChanged();
            }
            i--;
        } while (true);
    }

    public void onCreate()
    {
        super.onCreate();
        Colours.init(this);
    }

    public void onTerminate()
    {
        super.onTerminate();
        closeDb();
        clearCrypt();
        mPrefs = null;
        Colours.getInstance().clear();
        Ignores.clear();
    }

    public void removeWatcher(PreferenceChangeWatcher preferencechangewatcher)
    {
        List list = mWatchers;
        int i = list.size() - 1;
        do
        {
            if (i < 0)
            {
                return;
            }
            if (list.get(i) == preferencechangewatcher)
            {
                list.remove(i);
                return;
            }
            i--;
        } while (true);
    }

    static 
    {
        boolean flag1 = true;
        Object obj;
        boolean flag;
        if (android.os.Build.VERSION.SDK_INT < 11)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        LEGACY_VERSION = flag;
        if (LEGACY_VERSION)
        {
            obj = net/andchat/donate/Activities/LegacyUI;
        } else
        {
            obj = net/andchat/donate/Activities/CurrentUI;
        }
        CHAT_CLASS = ((Class) (obj));
        if (net/andchat/donate/IRCApp.getPackage().toString().indexOf("donate") > -1)
        {
            flag = flag1;
        } else
        {
            flag = false;
        }
        DONATE = flag;
    }
}
