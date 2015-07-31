// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

// Referenced classes of package net.andchat.donate.Misc:
//            Utils, ServerProfile

public final class Backup
{
    public static final class BackupOp extends Thread
    {

        private final Backup mBackup;
        private Activity mCtx;
        private File mFile;
        public Handler mHandler;
        private InputStream mIs;
        public final AtomicBoolean mRunning;
        public final int opType;
        public boolean showDialog;

        public void run()
        {
            Message.obtain(mHandler, 0).sendToTarget();
            mRunning.set(true);
            opType;
            JVM INSTR tableswitch 0 1: default 44
        //                       0 142
        //                       1 92;
               goto _L1 _L2 _L3
_L1:
            mCtx = null;
            if (showDialog)
            {
                Message.obtain(mHandler, 1).sendToTarget();
            }
            Message.obtain(mHandler, 2).sendToTarget();
            mHandler = null;
            mRunning.set(false);
            return;
_L3:
            if (mFile != null)
            {
                mBackup.importData(mCtx, mFile);
            } else
            if (mIs != null)
            {
                mBackup.importData(mCtx, mIs);
            }
            continue; /* Loop/switch isn't completed */
_L2:
            mBackup.exportData(mCtx);
            if (true) goto _L1; else goto _L4
_L4:
        }

        private BackupOp(int i, Activity activity, Backup backup, Handler handler)
        {
            mCtx = activity;
            opType = i;
            mRunning = new AtomicBoolean(false);
            showDialog = true;
            mHandler = handler;
            mBackup = backup;
        }

        public BackupOp(int i, Activity activity, Backup backup, File file, Handler handler)
        {
            this(i, activity, backup, handler);
            mFile = file;
            mIs = null;
        }

        public BackupOp(int i, Activity activity, Backup backup, InputStream inputstream, Handler handler)
        {
            this(i, activity, backup, handler);
            mIs = inputstream;
        }
    }

    private static final class Preference
    {

        public String key;
        public Class type;
        public String value;

        private Preference()
        {
        }

        Preference(Preference preference)
        {
            this();
        }
    }

    public static final class Stats
    {

        public boolean crypt;
        public Exception exception;
        public String fileName;
        public int goodPreferences;
        public int goodServers;
        public int opType;
        public int totalPreferences;
        public int totalServers;

        public String toString()
        {
            return super.toString();
        }

        private Stats()
        {
        }

        Stats(Stats stats)
        {
            this();
        }
    }


    private Stats mStats;

    public Backup()
    {
    }

    private void exportData(Activity activity)
    {
        Object obj1;
        File file1;
        Stats stats;
        Object obj2;
        Object obj3;
        obj2 = Utils.getIRCDb(activity);
        obj3 = Utils.getPrefs(activity);
        File file = new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append(File.separator).append("net.andchat.donate").append(File.separator).append("Backups").toString());
        if (!file.exists() || !file.isDirectory())
        {
            file.mkdirs();
        }
        obj1 = Calendar.getInstance();
        ((Calendar) (obj1)).setTimeInMillis(System.currentTimeMillis());
        file1 = new File(file, (new StringBuilder("Backup-")).append(((Calendar) (obj1)).get(1)).append("-").append(Utils.getMonth(((Calendar) (obj1)).get(2), true)).append("-").append(Utils.addPadding(((Calendar) (obj1)).get(5))).append("_").append(Utils.addPadding(((Calendar) (obj1)).get(11))).append(Utils.addPadding(((Calendar) (obj1)).get(12))).append(Utils.addPadding(((Calendar) (obj1)).get(13))).append(".xml").toString());
        stats = new Stats(null);
        mStats = stats;
        stats.opType = 0;
        obj1 = null;
        XmlSerializer xmlserializer = XmlPullParserFactory.newInstance().newSerializer();
        Object obj;
        try
        {
            xmlserializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        }
        catch (IllegalStateException illegalstateexception) { }
        obj = new FileOutputStream(file1, true);
        stats.fileName = file1.getPath();
        xmlserializer.setOutput(((java.io.OutputStream) (obj)), "UTF-8");
        xmlserializer.startDocument("UTF-8", null);
        xmlserializer.startTag(null, "AndChat");
        xmlserializer.startTag(null, "servers");
        stats.totalServers = ((IRCDb) (obj2)).toXml(xmlserializer, stats);
        xmlserializer.endTag(null, "servers");
        if (((SharedPreferences) (obj3)).getBoolean(activity.getString(0x7f0a001d), false))
        {
            Utils.getCrypt(activity).toXml(xmlserializer);
            stats.crypt = true;
        }
        activity = ((SharedPreferences) (obj3)).getAll();
        obj1 = activity.keySet();
        stats.totalPreferences = activity.size();
        xmlserializer.startTag(null, "preferences");
        obj1 = ((Set) (obj1)).iterator();
_L2:
        if (!((Iterator) (obj1)).hasNext())
        {
            xmlserializer.endTag(null, "preferences");
            xmlserializer.endTag(null, "AndChat");
            xmlserializer.endDocument();
            ((FileOutputStream) (obj)).close();
            return;
        }
        obj2 = (String)((Iterator) (obj1)).next();
        obj3 = activity.get(obj2);
        xmlserializer.startTag(null, "preference");
        if (!(obj3 instanceof String))
        {
            break; /* Loop/switch isn't completed */
        }
        xmlserializer.attribute(null, "type", "string");
_L3:
        xmlserializer.attribute(null, "key", ((String) (obj2)));
        xmlserializer.attribute(null, "value", String.valueOf(obj3));
        xmlserializer.endTag(null, "preference");
        stats.goodPreferences = stats.goodPreferences + 1;
        if (true) goto _L2; else goto _L1
        activity;
_L4:
        stats.exception = activity;
        if (obj != null)
        {
            try
            {
                ((FileOutputStream) (obj)).close();
            }
            // Misplaced declaration of an exception variable
            catch (Activity activity) { }
            file1.delete();
            return;
        } else
        {
            return;
        }
_L1:
label0:
        {
            if (!(obj3 instanceof Boolean))
            {
                break label0;
            }
            xmlserializer.attribute(null, "type", "bool");
        }
          goto _L3
label1:
        {
            if (!(obj3 instanceof Integer))
            {
                break label1;
            }
            xmlserializer.attribute(null, "type", "int");
        }
          goto _L3
        Log.e("AndChat::Backup", (new StringBuilder("Unknown preference type: ")).append(obj3).toString());
          goto _L3
        activity;
        obj = obj1;
          goto _L4
    }

    private void importData(Activity activity, File file)
    {
        mStats = new Stats(null);
        mStats.opType = 1;
        try
        {
            file = new FileInputStream(file);
        }
        // Misplaced declaration of an exception variable
        catch (Activity activity)
        {
            mStats.exception = activity;
            return;
        }
        importData(activity, ((InputStream) (file)), mStats);
        file.close();
_L2:
        return;
        activity;
        if (true) goto _L2; else goto _L1
_L1:
    }

    private void importData(Activity activity, InputStream inputstream)
    {
        mStats = new Stats(null);
        importData(activity, inputstream, mStats);
    }

    private void importData(Activity activity, InputStream inputstream, Stats stats)
    {
        stats.opType = 1;
        android.content.SharedPreferences.Editor editor;
        IRCDb ircdb;
        XmlPullParser xmlpullparser;
        ArrayList arraylist;
        ArrayList arraylist1;
        ircdb = Utils.getIRCDb(activity);
        editor = Utils.getPrefs(activity).edit();
        xmlpullparser = XmlPullParserFactory.newInstance().newPullParser();
        xmlpullparser.setInput(inputstream, null);
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        Object obj;
        Object obj1;
        obj1 = null;
        obj = null;
        int i = xmlpullparser.getEventType();
_L8:
        if (i != 1) goto _L2; else goto _L1
_L1:
        stats.totalServers = arraylist.size();
        ircdb.addMultiple(arraylist, stats);
        i = arraylist1.size();
        stats.totalPreferences = i;
        inputstream = arraylist1.iterator();
_L20:
        if (inputstream.hasNext()) goto _L4; else goto _L3
_L3:
        if (i <= 0)
        {
            break MISSING_BLOCK_LABEL_142;
        }
        editor.commit();
        Object obj2;
        Object obj3;
        if (stats.crypt && obj1 != null && obj != null)
        {
            try
            {
                Utils.getCrypt(activity).fromXml(((String) (obj1)), ((String) (obj)));
                return;
            }
            // Misplaced declaration of an exception variable
            catch (Activity activity)
            {
                stats.exception = activity;
            }
            return;
        }
          goto _L5
_L2:
        inputstream = xmlpullparser.getName();
        if (inputstream != null) goto _L7; else goto _L6
_L6:
        i = xmlpullparser.next();
          goto _L8
_L9:
        i = xmlpullparser.next();
        obj1 = obj2;
        obj = obj3;
          goto _L8
_L22:
        if (!inputstream.equalsIgnoreCase("server"))
        {
            break MISSING_BLOCK_LABEL_257;
        }
        arraylist.add(ServerProfile.fromXml(xmlpullparser, activity));
        obj2 = obj1;
        obj3 = obj;
          goto _L9
        if (!inputstream.equals("ignore")) goto _L11; else goto _L10
_L10:
        Object obj4;
        String s;
        inputstream = xmlpullparser.getAttributeValue(null, "label");
        obj2 = xmlpullparser.getAttributeValue(null, "nick");
        obj3 = xmlpullparser.getAttributeValue(null, "ident");
        obj4 = xmlpullparser.getAttributeValue(null, "host");
        s = xmlpullparser.getAttributeValue(null, "mask");
        if (inputstream != null && obj3 != null && obj4 != null && s != null)
        {
            break MISSING_BLOCK_LABEL_371;
        }
        Log.e("AndChat::Backup", "Missing info for ignore, skipping...");
        obj2 = obj1;
        obj3 = obj;
          goto _L9
        int j = Integer.parseInt(s);
        i = arraylist.size();
        obj4 = new net.andchat.donate.Backend.Ignores.IgnoreInfo(((String) (obj2)), ((String) (obj3)), ((String) (obj4)), j);
        i--;
_L23:
        obj2 = obj1;
        obj3 = obj;
        if (i < 0) goto _L9; else goto _L12
_L12:
        obj2 = (ServerProfile)arraylist.get(i);
        if (!((ServerProfile) (obj2)).getName().equalsIgnoreCase(inputstream)) goto _L14; else goto _L13
_L13:
        ((ServerProfile) (obj2)).getIgnoreList().add(obj4);
        obj2 = obj1;
        obj3 = obj;
          goto _L9
        inputstream;
        Log.e("AndChat::Backup", "mask is not a number, skipping");
          goto _L8
_L11:
label0:
        {
            if (!inputstream.equalsIgnoreCase("crypt"))
            {
                break label0;
            }
            obj2 = xmlpullparser.getAttributeValue(null, "m");
            obj3 = xmlpullparser.getAttributeValue(null, "s");
            stats.crypt = true;
        }
          goto _L9
        obj2 = obj1;
        obj3 = obj;
        if (!inputstream.equalsIgnoreCase("preference")) goto _L9; else goto _L15
_L15:
        obj2 = xmlpullparser.getAttributeValue(null, "key");
        obj4 = xmlpullparser.getAttributeValue(null, "type");
        obj3 = xmlpullparser.getAttributeValue(null, "value");
        inputstream = null;
        if (!((String) (obj4)).equalsIgnoreCase("string")) goto _L17; else goto _L16
_L16:
        inputstream = new Preference(null);
        inputstream.key = ((String) (obj2));
        inputstream.type = java/lang/String;
        inputstream.value = ((String) (obj3));
_L19:
        obj2 = obj1;
        obj3 = obj;
        if (inputstream == null) goto _L9; else goto _L18
_L18:
        arraylist1.add(inputstream);
        obj2 = obj1;
        obj3 = obj;
          goto _L9
_L17:
label1:
        {
            if (!((String) (obj4)).equalsIgnoreCase("bool"))
            {
                break label1;
            }
            inputstream = new Preference(null);
            inputstream.key = ((String) (obj2));
            inputstream.type = java/lang/Boolean;
            inputstream.value = ((String) (obj3));
        }
          goto _L19
label2:
        {
            if (!((String) (obj4)).equalsIgnoreCase("int"))
            {
                break label2;
            }
            inputstream = new Preference(null);
            inputstream.key = ((String) (obj2));
            inputstream.type = java/lang/Integer;
            inputstream.value = ((String) (obj3));
        }
          goto _L19
        Log.e("AndChat::Backup", (new StringBuilder("Unknown preference type:")).append(((String) (obj4))).toString());
          goto _L19
_L4:
        obj2 = (Preference)inputstream.next();
        stats.goodPreferences = stats.goodPreferences + 1;
        obj3 = ((Preference) (obj2)).type;
        if (obj3 != java/lang/String)
        {
            break MISSING_BLOCK_LABEL_830;
        }
        editor.putString(((Preference) (obj2)).key, ((Preference) (obj2)).value);
          goto _L20
        if (obj3 != java/lang/Boolean)
        {
            break MISSING_BLOCK_LABEL_865;
        }
        editor.putBoolean(((Preference) (obj2)).key, Boolean.valueOf(((Preference) (obj2)).value).booleanValue());
          goto _L20
        if (obj3 != java/lang/Integer)
        {
            break MISSING_BLOCK_LABEL_948;
        }
        editor.putInt(((Preference) (obj2)).key, Integer.parseInt(((Preference) (obj2)).value));
          goto _L20
        obj3;
        Log.e("AndChat::Backup", (new StringBuilder("Unable to parse ")).append(((Preference) (obj2)).value).append(" as int").toString(), ((Throwable) (obj3)));
        stats.goodPreferences = stats.goodPreferences - 1;
          goto _L20
        Log.e("AndChat::Backup", (new StringBuilder("Unknown preference type: ")).append(obj3).toString());
        stats.goodPreferences = stats.goodPreferences - 1;
          goto _L20
_L5:
        stats.crypt = false;
        return;
_L7:
        i;
        JVM INSTR tableswitch 2 2: default 1012
    //                   2 215;
           goto _L21 _L22
_L21:
        obj2 = obj1;
        obj3 = obj;
          goto _L9
_L14:
        i--;
          goto _L23
    }

    public Stats getStats()
    {
        return mStats;
    }



}
