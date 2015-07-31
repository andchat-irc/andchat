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
import java.util.Calendar;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection, IRCService

private final class mWriter
{

    final String mChan;
    final BufferedWriter mWriter;
    final mChan this$1;

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
        if (Utils.isBitSet(IRCService.sPreferences., IRCService.PREFS_LOGS_MODE_YEAR_MONTH_DAY))
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

    public (String s)
    {
        Object obj;
        File file1;
        this$1 = this._cls1.this;
        super();
        mChan = s;
        File file = new File(Environment.getExternalStorageDirectory(), (new StringBuilder(File.separator)).append("net.andchat.donate").append(File.separator).append(_mth0(_cls0.this).pLabel).append(File.separator).append(getFolderName()).toString());
        if (!file.exists() || !file.isDirectory())
        {
            file.mkdirs();
        }
        file1 = new File(file, (new StringBuilder(String.valueOf(s))).append(".txt").toString());
        obj = null;
        String s2 = _mth0(_cls0.this).pOptions.getCharset();
        String s1 = s2;
        try
        {
            if (TextUtils.isEmpty(s2))
            {
                s1 = ServerConnection.access$2(_mth0(_cls0.this)).prefs.getString(ServerConnection.access$2(_mth0(_cls0.this)).getString(0x7f0a000f), "UTF-8");
            }
             = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1, true), s1), 512);
        }
        // Misplaced declaration of an exception variable
        catch ( )
        {
            Log.e("AndChat::Logs", (new StringBuilder("Couldn't create log file for ")).append(s).toString());
             = obj;
        }
        mWriter = mWriter.this;
        return;
    }
}
