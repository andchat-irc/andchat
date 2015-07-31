// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;
import org.xmlpull.v1.XmlSerializer;

// Referenced classes of package net.andchat.donate.Backend:
//            Crypt, Ignores

public final class IRCDb
{

    private static final String CREATE_TABLE_IGNORE = (new StringBuilder("CREATE TABLE IF NOT EXISTS ")).append("ignore").append(" (_id INTEGER ").append("PRIMARY KEY AUTOINCREMENT, ").append("Server_Id").append(" INTEGER NOT NULL, ").append("Nick").append(" TEXT NOT NULL, ").append("Ident").append(" TEXT NOT NULL, ").append("Ignore_Mask").append(" INTEGER NOT NULL, ").append("Hostname").append(" TEXT NOT NULL)").toString();
    private static final String CREATE_TABLE_IRC = (new StringBuilder("CREATE TABLE IF NOT EXISTS ")).append("irc").append(" (_id INTEGER ").append("PRIMARY KEY AUTOINCREMENT, ").append("Label").append(" TEXT NOT NULL UNIQUE, ").append("Addr").append(" TEXT NOT NULL, ").append("SSL").append(" INTEGER NOT NULL, ").append("Port").append(" INTEGER, ").append("Pass").append(" TEXT, ").append("Nick1").append(" TEXT, ").append("Nick2").append(" TEXT, ").append("Nick3").append(" TEXT, ").append("User").append(" TEXT, ").append("RealName").append(" TEXT, ").append("Autojoin").append(" TEXT, ").append("Autorun").append(" TEXT, ").append("Encrypted").append(" INTEGER, ").append("Charset").append(" TEXT, ").append("LoggingEnabled").append(" INTEGER, ").append("AuthMode").append(" INTEGER, ").append("Nickserv_Password").append(" TEXT, ").append("SASL_Username").append(" TEXT, ").append("SASL_Password").append(" TEXT)").toString();
    private static final String DEFAULT_ORDER = (new StringBuilder("LOWER(")).append("Label").append(") ASC").toString();
    private Map cache;
    private final IRCApp mCtx;
    private SQLiteDatabase mDb;

    public IRCDb(IRCApp ircapp)
    {
        cache = new HashMap();
        mCtx = ircapp;
        ensureOpen();
    }

    private void addLogColumn()
    {
        try
        {
            mDb.execSQL("ALTER TABLE irc ADD COLUMN LoggingEnabled INTEGER");
            mDb.execSQL("UPDATE irc SET LoggingEnabled = 1");
            return;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void ensureOpen()
    {
        if (mDb == null || !mDb.isOpen())
        {
            mDb = mCtx.openOrCreateDatabase("irc.db", 0, null);
            mDb.execSQL(CREATE_TABLE_IRC);
            mDb.execSQL(CREATE_TABLE_IGNORE);
            mDb.execSQL("CREATE TABLE IF NOT EXISTS crypt (m TEXT, s TEXT)");
            performUpdatesInternal();
        }
    }

    private String getMaster()
    {
        ensureOpen();
        Cursor cursor = mDb.query("crypt", new String[] {
            "m"
        }, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                String s = cursor.getString(cursor.getColumnIndex("m"));
                while (s != null || !cursor.moveToNext()) 
                {
                    cursor.close();
                    return s;
                }
            } while (true);
        } else
        {
            cursor.close();
            return "";
        }
    }

    private String getSalt()
    {
        ensureOpen();
        Cursor cursor = mDb.query("crypt", new String[] {
            "s"
        }, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            int i = cursor.getColumnIndex("s");
            do
            {
                String s = cursor.getString(i);
                while (s != null || !cursor.moveToNext()) 
                {
                    cursor.close();
                    return s;
                }
            } while (true);
        } else
        {
            cursor.close();
            return "";
        }
    }

    private boolean labelExists(String s)
    {
        ensureOpen();
        s = mDb.query("irc", new String[] {
            "Label"
        }, "Label = ?", new String[] {
            s
        }, null, null, null);
        int i = s.getCount();
        s.close();
        return i > 0;
    }

    private void performUpdatesInternal()
    {
        int j = mDb.getVersion();
        int i = j;
        if (j <= 1)
        {
            upgradeToV2();
            i = 2;
        }
        if (i > j)
        {
            mDb.setVersion(i);
        }
    }

    private void updateCharset()
    {
        try
        {
            mDb.execSQL("ALTER TABLE irc ADD COLUMN Charset TEXT");
            return;
        }
        catch (Exception exception)
        {
            return;
        }
    }

    private void upgradeToV2()
    {
        int i = "ALTER TABLE irc ADD COLUMN ".length();
        Object obj = new StringBuilder();
        ((StringBuilder) (obj)).append("ALTER TABLE irc ADD COLUMN ");
        mDb.beginTransaction();
        ((StringBuilder) (obj)).append("AuthMode").append(" INTEGER");
        mDb.execSQL(((StringBuilder) (obj)).toString());
        ((StringBuilder) (obj)).setLength(i);
        ((StringBuilder) (obj)).append("Nickserv_Password").append(" TEXT");
        mDb.execSQL(((StringBuilder) (obj)).toString());
        ((StringBuilder) (obj)).setLength(i);
        ((StringBuilder) (obj)).append("SASL_Username").append(" TEXT");
        mDb.execSQL(((StringBuilder) (obj)).toString());
        ((StringBuilder) (obj)).setLength(i);
        ((StringBuilder) (obj)).append("SASL_Password").append(" TEXT");
        mDb.execSQL(((StringBuilder) (obj)).toString());
        ((StringBuilder) (obj)).setLength(i);
        obj = new ContentValues();
        ((ContentValues) (obj)).put("Nickserv_Password", "");
        ((ContentValues) (obj)).put("SASL_Username", "");
        ((ContentValues) (obj)).put("SASL_Password", "");
        mDb.update("irc", ((ContentValues) (obj)), null, null);
        Cursor cursor = mDb.query("irc", new String[] {
            "_id", "Pass"
        }, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            ((ContentValues) (obj)).clear();
            do
            {
                int j = cursor.getInt(cursor.getColumnIndex("_id"));
                if (cursor.getString(cursor.getColumnIndex("Pass")).length() > 0)
                {
                    ((ContentValues) (obj)).put("AuthMode", Integer.valueOf(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_PASSWORD));
                    mDb.update("irc", ((ContentValues) (obj)), "_id = ?", new String[] {
                        Integer.toString(j)
                    });
                }
                ((ContentValues) (obj)).clear();
            } while (cursor.moveToNext());
        }
        cursor.close();
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
_L2:
        Object obj1 = new ContentValues();
        ((ContentValues) (obj1)).put("Nickserv_Password", "");
        ((ContentValues) (obj1)).put("SASL_Username", "");
        ((ContentValues) (obj1)).put("SASL_Password", "");
        mDb.update("irc", ((ContentValues) (obj1)), null, null);
        obj1 = getMaster();
        String s = getSalt();
        mCtx.getSharedPreferences("crypt", 0).edit().putString("master", ((String) (obj1))).putString("salt", s).commit();
        mDb.execSQL("DROP TABLE IF EXISTS crypt");
        return;
        Object obj2;
        obj2;
        Log.e("IRCDb", "", ((Throwable) (obj2)));
        mDb.endTransaction();
        if (true) goto _L2; else goto _L1
_L1:
        obj2;
        mDb.endTransaction();
        throw obj2;
    }

    public void addMultiple(List list, net.andchat.donate.Misc.Backup.Stats stats)
        throws SQLException
    {
        Object obj;
        SQLiteDatabase sqlitedatabase;
        obj = null;
        sqlitedatabase = mDb;
        int i;
        sqlitedatabase.beginTransaction();
        i = list.size() - 1;
_L11:
        if (i >= 0) goto _L2; else goto _L1
_L1:
        sqlitedatabase.setTransactionSuccessful();
        sqlitedatabase.endTransaction();
        list = obj;
_L9:
        Object obj1;
        int j;
        int k;
        int l;
        if (list != null)
        {
            throw list;
        } else
        {
            return;
        }
_L2:
        obj1 = (ServerProfile)list.get(i);
        if (obj1 != null) goto _L4; else goto _L3
_L4:
        j = addServer(((ServerProfile) (obj1)));
        j;
        JVM INSTR tableswitch -2 -1: default 204
    //                   -2 163
    //                   -1 163;
           goto _L5 _L6 _L6
_L5:
        stats.goodServers = stats.goodServers + 1;
_L8:
        obj1 = ((ServerProfile) (obj1)).getIgnoreList();
        l = ((List) (obj1)).size();
        k = 0;
_L7:
        if (k >= l)
        {
            break; /* Loop/switch isn't completed */
        }
        addOrUpdateIgnore(j, (Ignores.IgnoreInfo)((List) (obj1)).get(k));
        k++;
        if (true) goto _L7; else goto _L3
_L6:
        j = getId(((ServerProfile) (obj1)).getName());
          goto _L8
        list;
        sqlitedatabase.endTransaction();
          goto _L9
        list;
        sqlitedatabase.endTransaction();
        throw list;
_L3:
        i--;
        if (true) goto _L11; else goto _L10
_L10:
    }

    public void addOrUpdateIgnore(int i, Ignores.IgnoreInfo ignoreinfo)
    {
        SQLiteDatabase sqlitedatabase;
        ensureOpen();
        sqlitedatabase = mDb;
        sqlitedatabase.beginTransaction();
        removeFromIgnore(i, ignoreinfo);
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("Nick", ignoreinfo.nick);
        contentvalues.put("Ident", ignoreinfo.ident);
        contentvalues.put("Ignore_Mask", Integer.valueOf(ignoreinfo.mask));
        contentvalues.put("Hostname", ignoreinfo.hostname);
        contentvalues.put("Server_Id", Integer.valueOf(i));
        sqlitedatabase.insert("ignore", null, contentvalues);
        sqlitedatabase.setTransactionSuccessful();
        sqlitedatabase.endTransaction();
        return;
        ignoreinfo;
        sqlitedatabase.endTransaction();
        throw ignoreinfo;
    }

    public int addServer(ServerProfile serverprofile)
    {
        boolean flag = true;
        ensureOpen();
        if (labelExists(serverprofile.getName()))
        {
            return -2;
        }
        ContentValues contentvalues;
        int i;
        int j;
        if (serverprofile.usesSSL())
        {
            i = 1;
        } else
        {
            i = 0;
        }
        if (serverprofile.isLoggingEnabled())
        {
            j = 1;
        } else
        {
            j = 0;
        }
        contentvalues = new ContentValues();
        contentvalues.put("Label", serverprofile.getName());
        contentvalues.put("Addr", serverprofile.getAddress());
        contentvalues.put("SSL", Integer.valueOf(i));
        contentvalues.put("Port", Integer.valueOf(serverprofile.getPort()));
        contentvalues.put("Pass", serverprofile.getServerPassword());
        contentvalues.put("Nick1", serverprofile.getNick(1));
        contentvalues.put("Nick2", serverprofile.getNick(2));
        contentvalues.put("Nick3", serverprofile.getNick(3));
        contentvalues.put("User", serverprofile.getUsername());
        contentvalues.put("RealName", serverprofile.getRealname());
        contentvalues.put("Autojoin", serverprofile.getAutojoinList());
        contentvalues.put("Autorun", serverprofile.getAutorunList());
        if (serverprofile.usesEncryption())
        {
            i = ((flag) ? 1 : 0);
        } else
        {
            i = 0;
        }
        contentvalues.put("Encrypted", Integer.valueOf(i));
        contentvalues.put("Charset", serverprofile.getCharset());
        contentvalues.put("LoggingEnabled", Integer.valueOf(j));
        contentvalues.put("Nickserv_Password", serverprofile.getNickservPassword());
        contentvalues.put("SASL_Username", serverprofile.getSASLUsername());
        contentvalues.put("SASL_Password", serverprofile.getSASLPassword());
        contentvalues.put("AuthMode", Integer.valueOf(serverprofile.getAuthModes()));
        return (int)mDb.insert("irc", null, contentvalues);
    }

    public int decryptAll()
    {
        Crypt crypt = Utils.getCrypt(mCtx);
        ensureOpen();
        int i = 0;
        boolean flag = false;
        Cursor cursor = mDb.query("irc", new String[] {
            "Pass", "Label", "Addr", "Nickserv_Password", "SASL_Password"
        }, "Encrypted= ?", new String[] {
            "1"
        }, null, null, null);
        int j = 0;
        if (cursor.moveToFirst())
        {
            boolean flag1 = true;
            int k = cursor.getColumnIndex("Pass");
            int l = cursor.getColumnIndex("Addr");
            int i1 = cursor.getColumnIndex("Label");
            int j1 = cursor.getColumnIndex("Nickserv_Password");
            int k1 = cursor.getColumnIndex("SASL_Password");
            ContentValues contentvalues = new ContentValues();
            j = ((flag) ? 1 : 0);
            do
            {
                String s = cursor.getString(k);
                String s1 = cursor.getString(l);
                String s2 = cursor.getString(j1);
                String s3 = cursor.getString(k1);
                if (s.length() > 0)
                {
                    contentvalues.put("Pass", crypt.decrypt(s));
                }
                if (s2.length() > 0)
                {
                    contentvalues.put("Nickserv_Password", crypt.decrypt(s2));
                }
                if (s3.length() > 0)
                {
                    contentvalues.put("SASL_Password", crypt.decrypt(s3));
                }
                contentvalues.put("Addr", crypt.decrypt(s1));
                contentvalues.put("Encrypted", Integer.valueOf(0));
                mDb.update("irc", contentvalues, "Label = ?", new String[] {
                    cursor.getString(i1)
                });
                i = j + 1;
                contentvalues.clear();
                j = i;
            } while (cursor.moveToNext());
            cursor.close();
            j = ((flag1) ? 1 : 0);
        }
        cursor.close();
        if (j != 0)
        {
            mDb.delete("crypt", null, null);
        }
        return i;
    }

    public boolean deleteServer(String s)
    {
        ensureOpen();
        int i = getId(s);
        int j = mDb.delete("irc", "Label = ?", new String[] {
            s
        });
        cache.remove(s);
        mDb.delete("ignore", "Server_Id= ?", new String[] {
            String.valueOf(i)
        });
        return j > 0;
    }

    public int editServer(String s, ServerProfile serverprofile)
    {
        ensureOpen();
        if (!s.equalsIgnoreCase(serverprofile.getName()) && labelExists(serverprofile.getName()))
        {
            return -2;
        }
        ContentValues contentvalues;
        int i;
        int j;
        if (serverprofile.usesSSL())
        {
            j = 1;
        } else
        {
            j = 0;
        }
        if (serverprofile.isLoggingEnabled())
        {
            i = 1;
        } else
        {
            i = 0;
        }
        contentvalues = new ContentValues();
        contentvalues.put("Label", serverprofile.getName());
        contentvalues.put("Addr", serverprofile.getAddress());
        contentvalues.put("SSL", Integer.valueOf(j));
        contentvalues.put("Port", Integer.valueOf(serverprofile.getPort()));
        contentvalues.put("Pass", serverprofile.getServerPassword());
        contentvalues.put("Nick1", serverprofile.getNick(1));
        contentvalues.put("Nick2", serverprofile.getNick(2));
        contentvalues.put("Nick3", serverprofile.getNick(3));
        contentvalues.put("User", serverprofile.getUsername());
        contentvalues.put("RealName", serverprofile.getRealname());
        contentvalues.put("Autojoin", serverprofile.getAutojoinList());
        contentvalues.put("Autorun", serverprofile.getAutorunList());
        if (serverprofile.usesEncryption())
        {
            j = 1;
        } else
        {
            j = 0;
        }
        contentvalues.put("Encrypted", Integer.valueOf(j));
        contentvalues.put("Charset", serverprofile.getCharset());
        contentvalues.put("LoggingEnabled", Integer.valueOf(i));
        contentvalues.put("Nickserv_Password", serverprofile.getNickservPassword());
        contentvalues.put("SASL_Username", serverprofile.getSASLUsername());
        contentvalues.put("SASL_Password", serverprofile.getSASLPassword());
        contentvalues.put("AuthMode", Integer.valueOf(serverprofile.getAuthModes()));
        return mDb.update("irc", contentvalues, "Label = ?", new String[] {
            s
        });
    }

    public int encryptAll()
    {
        ensureOpen();
        Cursor cursor = mDb.query("irc", new String[] {
            "Pass", "Label", "Addr", "Nickserv_Password", "SASL_Password"
        }, "Encrypted= ?", new String[] {
            "0"
        }, null, null, null);
        int i = 0;
        int j = 0;
        if (cursor.moveToFirst())
        {
            Crypt crypt = Utils.getCrypt(mCtx);
            int l = cursor.getColumnIndex("Pass");
            int i1 = cursor.getColumnIndex("Addr");
            int j1 = cursor.getColumnIndex("Label");
            int k1 = cursor.getColumnIndex("Nickserv_Password");
            int l1 = cursor.getColumnIndex("SASL_Password");
            ContentValues contentvalues = new ContentValues();
            do
            {
                i = 0;
                String s = cursor.getString(l);
                String s1 = cursor.getString(i1);
                String s2 = cursor.getString(k1);
                String s3 = cursor.getString(l1);
                if (s.length() > 0)
                {
                    contentvalues.put("Pass", crypt.encrypt(s));
                    i = 1;
                }
                if (s2.length() > 0)
                {
                    contentvalues.put("Nickserv_Password", crypt.encrypt(s2));
                    i = 1;
                }
                int k = i;
                if (s3.length() > 0)
                {
                    contentvalues.put("SASL_Password", crypt.encrypt(s3));
                    k = 1;
                }
                i = j;
                if (k != 0)
                {
                    contentvalues.put("Addr", crypt.encrypt(s1));
                    contentvalues.put("Encrypted", Integer.valueOf(1));
                    mDb.update("irc", contentvalues, "Label = ?", new String[] {
                        cursor.getString(j1)
                    });
                    i = j + 1;
                }
                contentvalues.clear();
                j = i;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return i;
    }

    public ServerProfile getDetailsForId(int i)
    {
        ensureOpen();
        Cursor cursor = mDb.query("irc", null, "_id = ?", new String[] {
            String.valueOf(i)
        }, null, null, null);
        if (cursor.moveToFirst())
        {
            ServerProfile serverprofile = new ServerProfile();
            int i4 = cursor.getColumnIndex("Label");
            int j4 = cursor.getColumnIndex("Addr");
            i = cursor.getColumnIndex("SSL");
            int k4 = cursor.getColumnIndex("Port");
            int j = cursor.getColumnIndex("Pass");
            int k = cursor.getColumnIndex("Nick1");
            int l = cursor.getColumnIndex("Nick2");
            int i1 = cursor.getColumnIndex("Nick3");
            int j1 = cursor.getColumnIndex("User");
            int k1 = cursor.getColumnIndex("RealName");
            int l1 = cursor.getColumnIndex("Autojoin");
            int i2 = cursor.getColumnIndex("Autorun");
            int j2 = cursor.getColumnIndex("Encrypted");
            int k2 = cursor.getColumnIndex("Charset");
            int l2 = cursor.getColumnIndex("LoggingEnabled");
            int i3 = cursor.getColumnIndex("Nickserv_Password");
            int j3 = cursor.getColumnIndex("SASL_Username");
            int k3 = cursor.getColumnIndex("SASL_Password");
            int l3 = cursor.getColumnIndex("AuthMode");
            String s = cursor.getString(i4);
            String s1 = cursor.getString(j4);
            i4 = cursor.getInt(k4);
            String s2;
            boolean flag;
            if (cursor.getInt(i) == 1)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            serverprofile.setServerDetails(s, s1, i4, flag);
            serverprofile.setUserDetails(cursor.getString(k), cursor.getString(l), cursor.getString(i1), cursor.getString(j1), cursor.getString(k1));
            serverprofile.setAuthDetails(cursor.getInt(l3), cursor.getString(j), cursor.getString(i3), cursor.getString(j3), cursor.getString(k3));
            s = cursor.getString(l1);
            s1 = cursor.getString(i2);
            s2 = cursor.getString(k2);
            if (cursor.getInt(l2) == 1)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            serverprofile.setOtherDetails(s, s1, s2, flag);
            if (cursor.getInt(j2) == 1)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            serverprofile.setUsesEncryption(flag);
            if (serverprofile.usesEncryption())
            {
                serverprofile.setDecrypted(false);
            } else
            {
                serverprofile.setDecrypted(true);
            }
            cursor.close();
            return serverprofile;
        } else
        {
            cursor.close();
            return null;
        }
    }

    public int getId(String s)
    {
        Object obj = (Integer)cache.get(s);
        if (obj != null)
        {
            return ((Integer) (obj)).intValue();
        }
        ensureOpen();
        obj = mDb.query("irc", new String[] {
            "_id"
        }, "Label = ?", new String[] {
            s
        }, null, null, null);
        if (((Cursor) (obj)).moveToFirst())
        {
            int i = ((Cursor) (obj)).getInt(((Cursor) (obj)).getColumnIndex("_id"));
            ((Cursor) (obj)).close();
            cache.put(s, Integer.valueOf(i));
            return i;
        } else
        {
            ((Cursor) (obj)).close();
            return -1;
        }
    }

    public List getIgnores(int i)
    {
        ensureOpen();
        Cursor cursor = mDb.query("ignore", null, "Server_Id = ?", new String[] {
            String.valueOf(i)
        }, null, null, null);
        if (cursor.moveToFirst())
        {
            ArrayList arraylist = new ArrayList(cursor.getCount());
            i = cursor.getColumnIndex("Nick");
            int j = cursor.getColumnIndex("Ident");
            int k = cursor.getColumnIndex("Hostname");
            int l = cursor.getColumnIndex("Ignore_Mask");
            do
            {
                arraylist.add(new Ignores.IgnoreInfo(cursor.getString(i), cursor.getString(j), cursor.getString(k), cursor.getInt(l)));
            } while (cursor.moveToNext());
            cursor.close();
            return arraylist;
        } else
        {
            cursor.close();
            return new ArrayList();
        }
    }

    public List getList()
    {
        ensureOpen();
        Object obj = mDb;
        String s = DEFAULT_ORDER;
        obj = ((SQLiteDatabase) (obj)).query("irc", new String[] {
            "Label"
        }, null, null, null, null, s);
        if (((Cursor) (obj)).moveToFirst())
        {
            ArrayList arraylist = new ArrayList(((Cursor) (obj)).getCount());
            int i = ((Cursor) (obj)).getColumnIndex("Label");
            do
            {
                arraylist.add(((Cursor) (obj)).getString(i));
            } while (((Cursor) (obj)).moveToNext());
            ((Cursor) (obj)).close();
            return arraylist;
        } else
        {
            ((Cursor) (obj)).close();
            return new ArrayList();
        }
    }

    public boolean performUpdates(SharedPreferences sharedpreferences, android.content.SharedPreferences.Editor editor)
    {
        boolean flag = false;
        ensureOpen();
        if (!sharedpreferences.getBoolean("charsetcheck", false))
        {
            Log.i("IRCDb", "Attempting to update irc table for charset support");
            updateCharset();
            editor.putBoolean("charsetcheck", true);
            flag = true;
        }
        if (!sharedpreferences.getBoolean("logcheck", false))
        {
            Log.i("IRCDb", "Attempting to update table for per server logging option support");
            addLogColumn();
            editor.putBoolean("logcheck", true);
            flag = true;
        }
        return flag;
    }

    void removeFromIgnore(int i, Ignores.IgnoreInfo ignoreinfo)
    {
        ensureOpen();
        mDb.delete("ignore", "Ident= ?  AND Server_Id = ? AND Hostname = ? ", new String[] {
            ignoreinfo.ident, String.valueOf(i), ignoreinfo.hostname
        });
    }

    public void shutdown()
    {
        if (mDb.isOpen())
        {
            mDb.close();
        }
    }

    public int toXml(XmlSerializer xmlserializer, net.andchat.donate.Misc.Backup.Stats stats)
        throws IllegalArgumentException, IllegalStateException, IOException
    {
        List list;
        Iterator iterator;
        list = getList();
        if (list.size() == 0)
        {
            return 0;
        }
        iterator = list.iterator();
_L3:
        Object obj;
        List list1;
        int k;
        if (!iterator.hasNext())
        {
            return list.size();
        }
        obj = (String)iterator.next();
        xmlserializer.startTag(null, "server");
        int i = getId(((String) (obj)));
        obj = getDetailsForId(i);
        xmlserializer.attribute(null, "name", ((ServerProfile) (obj)).getName());
        xmlserializer.attribute(null, "address", ((ServerProfile) (obj)).getAddress());
        xmlserializer.attribute(null, "port", String.valueOf(((ServerProfile) (obj)).getPort()));
        xmlserializer.attribute(null, "ssl", String.valueOf(((ServerProfile) (obj)).usesSSL()));
        xmlserializer.attribute(null, "nick1", ((ServerProfile) (obj)).getNick(1));
        xmlserializer.attribute(null, "nick2", ((ServerProfile) (obj)).getNick(2));
        xmlserializer.attribute(null, "nick3", ((ServerProfile) (obj)).getNick(3));
        xmlserializer.attribute(null, "realname", ((ServerProfile) (obj)).getRealname());
        xmlserializer.attribute(null, "username", ((ServerProfile) (obj)).getUsername());
        xmlserializer.attribute(null, "autojoin", ((ServerProfile) (obj)).getAutojoinList());
        xmlserializer.attribute(null, "autorun", ((ServerProfile) (obj)).getAutorunList());
        xmlserializer.attribute(null, "password", ((ServerProfile) (obj)).getServerPassword());
        xmlserializer.attribute(null, "charset", ((ServerProfile) (obj)).getCharset());
        xmlserializer.attribute(null, "encrypted", String.valueOf(((ServerProfile) (obj)).usesEncryption()));
        xmlserializer.attribute(null, "logmessages", String.valueOf(((ServerProfile) (obj)).isLoggingEnabled()));
        xmlserializer.attribute(null, "nickserv_password", ((ServerProfile) (obj)).getNickservPassword());
        xmlserializer.attribute(null, "sasl_username", ((ServerProfile) (obj)).getSASLUsername());
        xmlserializer.attribute(null, "sasl_password", ((ServerProfile) (obj)).getSASLPassword());
        xmlserializer.attribute(null, "auth_modes", Integer.toString(((ServerProfile) (obj)).getAuthModes()));
        list1 = Ignores.getIgnores(i, this).getAllIgnores();
        k = list1.size();
        if (k <= 0) goto _L2; else goto _L1
_L1:
        int j;
        xmlserializer.startTag(null, "ignores");
        j = 0;
_L4:
        if (j < k)
        {
            break MISSING_BLOCK_LABEL_483;
        }
        xmlserializer.endTag(null, "ignores");
_L2:
        xmlserializer.endTag(null, "server");
        stats.goodServers = stats.goodServers + 1;
          goto _L3
        Ignores.IgnoreInfo ignoreinfo = (Ignores.IgnoreInfo)list1.get(j);
        xmlserializer.startTag(null, "ignore");
        xmlserializer.attribute(null, "label", ((ServerProfile) (obj)).getName());
        xmlserializer.attribute(null, "nick", ignoreinfo.nick);
        xmlserializer.attribute(null, "ident", ignoreinfo.ident);
        xmlserializer.attribute(null, "host", ignoreinfo.hostname);
        xmlserializer.attribute(null, "mask", String.valueOf(ignoreinfo.mask));
        xmlserializer.endTag(null, "ignore");
        j++;
          goto _L4
    }

    public void updateMultiple(List list)
    {
        SQLiteDatabase sqlitedatabase = mDb;
        int i;
        sqlitedatabase.beginTransaction();
        i = list.size() - 1;
_L2:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_33;
        }
        sqlitedatabase.setTransactionSuccessful();
        sqlitedatabase.endTransaction();
        return;
        ServerProfile serverprofile = (ServerProfile)list.get(i);
        if (serverprofile == null)
        {
            break MISSING_BLOCK_LABEL_98;
        }
        int j = editServer(serverprofile.getName(), serverprofile);
        switch (j)
        {
        }
        break MISSING_BLOCK_LABEL_98;
        list;
        sqlitedatabase.endTransaction();
        throw list;
        i--;
        if (true) goto _L2; else goto _L1
_L1:
    }

}
