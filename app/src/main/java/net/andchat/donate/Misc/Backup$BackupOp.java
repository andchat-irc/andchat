// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

// Referenced classes of package net.andchat.donate.Misc:
//            Backup

public static final class mIs extends Thread
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
    //                   0 142
    //                   1 92;
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
            Backup.access$0(mBackup, mCtx, mFile);
        } else
        if (mIs != null)
        {
            Backup.access$1(mBackup, mCtx, mIs);
        }
        continue; /* Loop/switch isn't completed */
_L2:
        Backup.access$2(mBackup, mCtx);
        if (true) goto _L1; else goto _L4
_L4:
    }

    private n(int i, Activity activity, Backup backup, Handler handler)
    {
        mCtx = activity;
        opType = i;
        mRunning = new AtomicBoolean(false);
        showDialog = true;
        mHandler = handler;
        mBackup = backup;
    }

    public mBackup(int i, Activity activity, Backup backup, File file, Handler handler)
    {
        this(i, activity, backup, handler);
        mFile = file;
        mIs = null;
    }

    public mIs(int i, Activity activity, Backup backup, InputStream inputstream, Handler handler)
    {
        this(i, activity, backup, handler);
        mIs = inputstream;
    }
}
