// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection, IRCService

class FIND
{
    private final class Logger
    {

        final String mChan;
        final BufferedWriter mWriter;
        final ServerConnection.LogManager this$1;

        private void flush(BufferedWriter bufferedwriter)
        {
            try
            {
                bufferedwriter.flush();
                return;
            }
            // Misplaced declaration of an exception variable
            catch (BufferedWriter bufferedwriter)
            {
                Log.e("AndChat::Logs", "Error while force flushing buffer", bufferedwriter);
            }
        }

        private StringBuilder getFolderName()
        {
            StringBuilder stringbuilder = new StringBuilder(10);
            stringbuilder.append(Utils.getMonth(ServerConnection.sCal.get(2), false));
            stringbuilder.append("-");
            stringbuilder.append(ServerConnection.sCal.get(1));
            if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_LOGS_MODE_YEAR_MONTH_DAY))
            {
                stringbuilder.append(File.separator).append(Utils.addPadding(ServerConnection.sCal.get(5)));
            }
            return stringbuilder;
        }

        public void close()
        {
            this;
            JVM INSTR monitorenter ;
            BufferedWriter bufferedwriter = mWriter;
            if (bufferedwriter != null) goto _L2; else goto _L1
_L1:
            this;
            JVM INSTR monitorexit ;
            return;
_L2:
            Exception exception;
            try
            {
                mWriter.close();
                continue; /* Loop/switch isn't completed */
            }
            catch (IOException ioexception) { }
            finally
            {
                this;
            }
            Log.e("AndChat::Logs", (new StringBuilder("Couldn't close writer for ")).append(mChan).toString());
            if (true) goto _L1; else goto _L3
_L3:
            throw exception;
        }

        public void log(char ac[], boolean flag)
        {
            this;
            JVM INSTR monitorenter ;
            Object obj = mWriter;
            if (obj == null) goto _L2; else goto _L1
_L1:
            int j = ac.length;
            int i = 0;
_L10:
            if (i < j) goto _L4; else goto _L3
_L3:
            if (!flag) goto _L2; else goto _L5
_L5:
            flush(((BufferedWriter) (obj)));
_L2:
            this;
            JVM INSTR monitorexit ;
            return;
_L4:
            char c;
            c = ac[i];
            if (c != '\uFFFF')
            {
                break MISSING_BLOCK_LABEL_128;
            }
            if (ac[i - 1] == '\n') goto _L7; else goto _L6
_L6:
            ((BufferedWriter) (obj)).write(10);
_L7:
            if (!flag) goto _L2; else goto _L8
_L8:
            flush(((BufferedWriter) (obj)));
              goto _L2
            obj;
            Log.e("AndChat::Logs", (new StringBuilder(String.valueOf(mChan))).append(": couldn't write message to log. Message was ").append(new String(ac)).toString(), ((Throwable) (obj)));
              goto _L2
            ac;
            throw ac;
            ((BufferedWriter) (obj)).write(c);
            if (i != j - 1 || ac[j - 1] == '\n')
            {
                break MISSING_BLOCK_LABEL_160;
            }
            ((BufferedWriter) (obj)).write(10);
            i++;
            if (true) goto _L10; else goto _L9
_L9:
        }

        public String toString()
        {
            return super.toString();
        }

        public Logger(String s)
        {
            Object obj;
            File file1;
            this$1 = ServerConnection.LogManager.this;
            super();
            mChan = s;
            File file = new File(Environment.getExternalStorageDirectory(), (new StringBuilder(File.separator)).append("net.andchat.donate").append(File.separator).append(pLabel).append(File.separator).append(getFolderName()).toString());
            if (!file.exists() || !file.isDirectory())
            {
                file.mkdirs();
            }
            file1 = new File(file, (new StringBuilder(String.valueOf(s))).append(".txt").toString());
            obj = null;
            String s2 = pOptions.getCharset();
            String s1 = s2;
            try
            {
                if (TextUtils.isEmpty(s2))
                {
                    s1 = ServerConnection.access$2(this$0).prefs.getString(ServerConnection.access$2(this$0).getString(0x7f0a000f), "UTF-8");
                }
                logmanager = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1, true), s1), 512);
            }
            // Misplaced declaration of an exception variable
            catch (ServerConnection.LogManager logmanager)
            {
                Log.e("AndChat::Logs", (new StringBuilder("Couldn't create log file for ")).append(s).toString());
                logmanager = obj;
            }
            mWriter = ServerConnection.LogManager.this;
            return;
        }
    }


    private final char FIND[];
    private final List mLoggers = new ArrayList();
    final ServerConnection this$0;

    private Logger find(String s)
    {
        String s1 = sanitizeChan(s);
        s = mLoggers;
        s;
        JVM INSTR monitorenter ;
        List list;
        int i;
        list = mLoggers;
        i = list.size() - 1;
_L2:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_37;
        }
        s;
        JVM INSTR monitorexit ;
        return null;
        Logger logger = (Logger)list.get(i);
        int j;
        j = i;
        if (logger != null)
        {
            break MISSING_BLOCK_LABEL_74;
        }
        list.remove(i);
        j = i - 1;
        if (!logger.mChan.equalsIgnoreCase(s1))
        {
            break MISSING_BLOCK_LABEL_96;
        }
        s;
        JVM INSTR monitorexit ;
        return logger;
        Exception exception;
        exception;
        s;
        JVM INSTR monitorexit ;
        throw exception;
        i = j - 1;
        if (true) goto _L2; else goto _L1
_L1:
    }

    private Logger newLogger(String s)
    {
        Logger logger;
        if (s.length() == 0)
        {
            s = "Status";
        } else
        {
            s = sanitizeChan(s);
        }
        logger = new Logger(s);
        synchronized (mLoggers)
        {
            mLoggers.add(logger);
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(ServerConnection.access$0(ServerConnection.this, 0x7f0a0049, new Object[] {
                ServerConnection.format(System.currentTimeMillis())
            }));
            logger.log(stringbuilder.toString().toCharArray(), false);
        }
        return logger;
        exception;
        s;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private String sanitizeChan(String s)
    {
        int j = FIND.length;
        int i = 0;
        do
        {
            if (i >= j)
            {
                return s;
            }
            s = s.replace(FIND[i], '_');
            i++;
        } while (true);
    }

    public void close(String s)
    {
        s = find(sanitizeChan(s));
        if (s != null)
        {
            s.close();
            mLoggers.remove(s);
        }
    }

    public void closeAll()
    {
        List list = mLoggers;
        list;
        JVM INSTR monitorenter ;
        List list1;
        int i;
        list1 = mLoggers;
        i = list1.size() - 1;
_L2:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_34;
        }
        list1.clear();
        list;
        JVM INSTR monitorexit ;
        return;
        ((Logger)list1.get(i)).close();
        i--;
        if (true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        list;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void log(String s, char ac[], boolean flag)
    {
        s = s.trim();
        Logger logger;
        Logger logger1;
        if (s.length() == 0 || s.charAt(0) == '$')
        {
            s = "Status";
        } else
        {
            s = sanitizeChan(s);
        }
        logger1 = find(s);
        logger = logger1;
        if (logger1 == null)
        {
            logger = newLogger(s);
        }
        logger.log(ac, flag);
    }

    public void logAll(char ac[])
    {
        List list = mLoggers;
        list;
        JVM INSTR monitorenter ;
        List list1;
        int i;
        list1 = mLoggers;
        i = list1.size() - 1;
_L2:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_30;
        }
        list;
        JVM INSTR monitorexit ;
        return;
        ((Logger)list1.get(i)).log(ac, false);
        i--;
        if (true) goto _L2; else goto _L1
_L1:
        ac;
        list;
        JVM INSTR monitorexit ;
        throw ac;
    }


    public Logger.mWriter()
    {
        this$0 = ServerConnection.this;
        super();
        FIND = (new char[] {
            ':', File.separatorChar
        });
    }
}
