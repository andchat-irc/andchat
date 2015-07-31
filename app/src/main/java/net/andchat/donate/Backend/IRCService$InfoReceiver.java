// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import java.util.Calendar;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            IRCService, ServerConnection

private class <init> extends BroadcastReceiver
{

    private int initialType;
    final IRCService this$0;
    private boolean wasAirplaneMode;

    private void reconnectLocked(SparseArray sparsearray, String s)
    {
        int i = sparsearray.size();
        boolean flag = false;
        int j = i - 1;
        do
        {
            if (j < 0)
            {
                return;
            }
            final ServerConnection sc = (ServerConnection)sparsearray.valueAt(j);
            boolean flag1 = flag;
            if (!sc.isReconnecting())
            {
                flag1 = flag;
                if (sc.pState == 1)
                {
                    flag1 = flag;
                    if (!flag)
                    {
                        Log.i("IRCService", "Reconnecting all connected servers due to connectivity change");
                        Log.i("IRCService", (new StringBuilder("New information: ")).append(s).toString());
                        flag1 = true;
                    }
                    (new Thread() {

                        final IRCService.InfoReceiver this$1;
                        private final ServerConnection val$sc;

                        public void run()
                        {
                            sc.sendConnectionChangedMessage();
                            ServerConnection serverconnection = sc;
                            serverconnection.pReconnectCount = serverconnection.pReconnectCount - 1;
                            sc.stopConnection(0);
                        }

            
            {
                this$1 = IRCService.InfoReceiver.this;
                sc = serverconnection;
                super();
            }
                    }).start();
                }
            }
            j--;
            flag = flag1;
        } while (true);
    }

    public void onReceive(Context context, Intent intent)
    {
        context = intent.getAction();
        if (!context.equals("android.net.conn.CONNECTIVITY_CHANGE")) goto _L2; else goto _L1
_L1:
        Object obj;
        int j;
        int k;
        acquireOrReleaseWifiLock();
        context = (NetworkInfo)intent.getParcelableExtra("networkInfo");
        k = context.getType();
        obj = IRCService.access$0(IRCService.this);
        j = 0;
        k;
        JVM INSTR tableswitch 1 1: default 68
    //                   1 213;
           goto _L3 _L4
_L3:
        int i;
        i = j;
        if (obj != null)
        {
            i = j;
            if (((this._cls0) (obj)).ime != 0x493e0)
            {
                obj.ime = 0x493e0;
                i = 1;
            }
        }
_L11:
        if (obj == null || !i) goto _L6; else goto _L5
_L5:
        obj;
        JVM INSTR monitorenter ;
        obj.notify();
        obj;
        JVM INSTR monitorexit ;
_L6:
        if (!Utils.isBitSet(IRCService.sPreferences.MainOptions, IRCService.access$1())) goto _L8; else goto _L7
_L7:
        NetworkInfo networkinfo;
        obj = context.getState();
        networkinfo = ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo();
        if (initialType == -1)
        {
            initialType = k;
        }
        if (obj != android.net.TED || !wasAirplaneMode) goto _L10; else goto _L9
_L9:
        wasAirplaneMode = false;
        synchronized (mServers)
        {
            reconnectLocked(mServers, context.toString());
        }
_L8:
        return;
_L4:
        i = j;
        if (obj != null)
        {
            i = j;
            if (((reconnectLocked) (obj)).ime != 0x1d4c0)
            {
                obj.ime = 0x1d4c0;
                i = 1;
            }
        }
          goto _L11
        context;
        obj;
        JVM INSTR monitorexit ;
        throw context;
        context;
        intent;
        JVM INSTR monitorexit ;
        throw context;
_L10:
        if (obj == android.net.TED && k == 1 && initialType == 0 || intent.getBooleanExtra("noConnectivity", false) || networkinfo == null)
        {
            wasAirplaneMode = false;
            if (android.os.ce.InfoReceiver.wasAirplaneMode >= 17)
            {
                boolean flag;
                if (android.provider.e(getContentResolver(), "airplane_mode_on", 0) == 1)
                {
                    flag = true;
                } else
                {
                    flag = false;
                }
                wasAirplaneMode = flag;
            } else
            {
                boolean flag1;
                if (android.provider.oReceiver.wasAirplaneMode(getContentResolver(), "airplane_mode_on", 0) == 1)
                {
                    flag1 = true;
                } else
                {
                    flag1 = false;
                }
                wasAirplaneMode = flag1;
            }
            synchronized (mServers)
            {
                reconnectLocked(mServers, context.toString());
            }
            return;
        }
        continue; /* Loop/switch isn't completed */
        context;
        intent;
        JVM INSTR monitorexit ;
        throw context;
        if (obj != android.net.NECTED) goto _L8; else goto _L12
_L12:
        k;
        JVM INSTR tableswitch 0 1: default 436
    //                   0 437
    //                   1 494;
           goto _L13 _L14 _L15
_L15:
        continue; /* Loop/switch isn't completed */
_L13:
        return;
_L14:
        if (networkinfo == null || networkinfo.getType() != 1 || !isWifiConnected()) goto _L8; else goto _L16
_L16:
        synchronized (mServers)
        {
            reconnectLocked(mServers, context.toString());
        }
        return;
        context;
        intent;
        JVM INSTR monitorexit ;
        throw context;
        if (context.isAvailable()) goto _L8; else goto _L17
_L17:
        synchronized (mServers)
        {
            reconnectLocked(mServers, context.toString());
        }
        return;
        context;
        intent;
        JVM INSTR monitorexit ;
        throw context;
_L2:
        if (!context.equals("android.intent.action.SCREEN_OFF")) goto _L19; else goto _L18
_L18:
        screenOn = false;
        if (areServersAlive())
        {
            acquireOrReleaseWifiLock();
            wakeLock.quire(5000L);
            return;
        }
          goto _L8
_L19:
        if (context.equals("android.intent.action.SCREEN_ON"))
        {
            screenOn = true;
            return;
        }
        if (!context.equals("android.intent.action.TIME_TICK")) goto _L8; else goto _L20
_L20:
        net/andchat/donate/Backend/ServerConnection;
        JVM INSTR monitorenter ;
        long l = System.currentTimeMillis();
        intent = ServerConnection.sCal;
        i = intent.get(1);
        k = intent.get(2);
        j = intent.get(5);
        ServerConnection.timeChanged(l);
        context = (char[])null;
        if (j != intent.get(5))
        {
            context = (new StringBuilder()).append(Utils.getDay(intent.get(7), false)).append(" ").append(Utils.getMonth(intent.get(2), false)).append(" ").append(Utils.addPadding(intent.get(5))).append(" ").append(intent.get(1)).toString();
            obj = getString(0x7f0a0048, new Object[] {
                context
            });
            context = new char[((String) (obj)).length()];
            TextUtils.getChars(((CharSequence) (obj)), 0, ((String) (obj)).length(), context, 0);
        }
          goto _L21
_L27:
        if (i == intent.get(1) && k == intent.get(2))
        {
            i = 0;
        } else
        {
            i = 1;
        }
        if (!j) goto _L23; else goto _L22
_L22:
        if (!Utils.isBitSet(IRCService.sPreferences.MainOptions, IRCService.PREFS_LOGS_MODE_YEAR_MONTH_DAY)) goto _L23; else goto _L24
_L24:
        k = 1;
          goto _L25
_L34:
        intent = mServers;
        k = intent.size() - 1;
          goto _L26
_L36:
        net/andchat/donate/Backend/ServerConnection;
        JVM INSTR monitorexit ;
        return;
        context;
        net/andchat/donate/Backend/ServerConnection;
        JVM INSTR monitorexit ;
        throw context;
_L33:
        j = 0;
          goto _L27
_L23:
        k = 0;
          goto _L25
_L37:
        obj = (ServerConnection)intent.valueAt(k);
        if (!j) goto _L29; else goto _L28
_L28:
        ((ServerConnection) (obj)).pLogs.logAll(context);
_L29:
        if (!i) goto _L31; else goto _L30
_L30:
        ((ServerConnection) (obj)).pLogs.closeAll();
_L31:
        k--;
        continue; /* Loop/switch isn't completed */
_L21:
        if (context == null) goto _L33; else goto _L32
_L32:
        j = 1;
          goto _L27
_L25:
        if (k != 0)
        {
            i = 1;
        }
        if (!j && !i) goto _L35; else goto _L34
_L35:
        break; /* Loop/switch isn't completed */
_L26:
        if (k >= 0) goto _L37; else goto _L36
    }

    private _cls1.val.sc()
    {
        this$0 = IRCService.this;
        super();
        initialType = -1;
    }

    initialType(initialType initialtype)
    {
        this();
    }
}
