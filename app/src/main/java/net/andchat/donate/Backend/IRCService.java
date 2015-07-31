// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import net.andchat.donate.Activities.ChatWindow;
import net.andchat.donate.Activities.Main;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.CommandParser;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;
import net.andchat.donate.View.MultiChoicePreference;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection, MessageSender, IRCDb

public class IRCService extends Service
    implements net.andchat.donate.IRCApp.PreferenceChangeWatcher
{
    public class IRCServiceBinder extends Binder
    {

        final IRCService this$0;

        public IRCService getService()
        {
            return IRCService.this;
        }

        public IRCServiceBinder()
        {
            this$0 = IRCService.this;
            super();
        }
    }

    private class InfoReceiver extends BroadcastReceiver
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
                ServerConnection serverconnection = (ServerConnection)sparsearray.valueAt(j);
                boolean flag1 = flag;
                if (!serverconnection.isReconnecting())
                {
                    flag1 = flag;
                    if (serverconnection.pState == 1)
                    {
                        flag1 = flag;
                        if (!flag)
                        {
                            Log.i("IRCService", "Reconnecting all connected servers due to connectivity change");
                            Log.i("IRCService", (new StringBuilder("New information: ")).append(s).toString());
                            flag1 = true;
                        }
                        (serverconnection. new Thread() {

                            final InfoReceiver this$1;
                            private final ServerConnection val$sc;

                            public void run()
                            {
                                sc.sendConnectionChangedMessage();
                                ServerConnection serverconnection = sc;
                                serverconnection.pReconnectCount = serverconnection.pReconnectCount - 1;
                                sc.stopConnection(0);
                            }

            
            {
                this$1 = final_inforeceiver;
                sc = ServerConnection.this;
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
            obj = mPinger;
            j = 0;
            k;
            JVM INSTR tableswitch 1 1: default 68
        //                       1 213;
               goto _L3 _L4
_L3:
            int i;
            i = j;
            if (obj != null)
            {
                i = j;
                if (((Pinger) (obj)).sleepTime != 0x493e0)
                {
                    obj.sleepTime = 0x493e0;
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
            if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_DETECT_CONNECTION)) goto _L8; else goto _L7
_L7:
            NetworkInfo networkinfo;
            obj = context.getState();
            networkinfo = ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo();
            if (initialType == -1)
            {
                initialType = k;
            }
            if (obj != android.net.NetworkInfo.State.CONNECTED || !wasAirplaneMode) goto _L10; else goto _L9
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
                if (((Pinger) (obj)).sleepTime != 0x1d4c0)
                {
                    obj.sleepTime = 0x1d4c0;
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
            if (obj == android.net.NetworkInfo.State.CONNECTED && k == 1 && initialType == 0 || intent.getBooleanExtra("noConnectivity", false) || networkinfo == null)
            {
                wasAirplaneMode = false;
                if (android.os.Build.VERSION.SDK_INT >= 17)
                {
                    boolean flag;
                    if (android.provider.Settings.Global.getInt(getContentResolver(), "airplane_mode_on", 0) == 1)
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
                    if (android.provider.Settings.System.getInt(getContentResolver(), "airplane_mode_on", 0) == 1)
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
            if (obj != android.net.NetworkInfo.State.DISCONNECTED) goto _L8; else goto _L12
_L12:
            k;
            JVM INSTR tableswitch 0 1: default 436
        //                       0 437
        //                       1 494;
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
                wakeLock.acquire(5000L);
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
            if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_LOGS_MODE_YEAR_MONTH_DAY)) goto _L23; else goto _L24
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

        private InfoReceiver()
        {
            this$0 = IRCService.this;
            super();
            initialType = -1;
        }

        InfoReceiver(InfoReceiver inforeceiver)
        {
            this();
        }
    }

    private class Pinger extends Thread
    {

        boolean interrupted;
        final StringBuilder sb;
        int sleepTime;
        final IRCService this$0;

        public void run()
        {
            setName("PingerThread");
            sb.append("PING :");
_L5:
            if (interrupted)
            {
                return;
            }
            this;
            JVM INSTR monitorenter ;
            Object obj;
            Object obj1;
            int i;
            int j;
            try
            {
                wait(sleepTime);
            }
            catch (InterruptedException interruptedexception) { }
            this;
            JVM INSTR monitorexit ;
            obj = mServers;
            obj;
            JVM INSTR monitorenter ;
            i = mServers.size();
            sb.append(System.currentTimeMillis()).append("\r\n");
            obj1 = sb.toString();
            i--;
_L6:
            if (i >= 0) goto _L2; else goto _L1
_L1:
            sb.setLength(6);
            obj;
            JVM INSTR monitorexit ;
            obj = mListeners;
            obj;
            JVM INSTR monitorenter ;
            obj1 = mListeners;
            i = ((List) (obj1)).size() - 1;
_L7:
            if (i >= 0) goto _L4; else goto _L3
_L3:
            obj;
            JVM INSTR monitorexit ;
              goto _L5
            obj1;
            obj;
            JVM INSTR monitorexit ;
            throw obj1;
            obj;
            this;
            JVM INSTR monitorexit ;
            throw obj;
_L2:
            ((ServerConnection)mServers.valueAt(i)).writeToServer(((String) (obj1)));
            i--;
              goto _L6
            obj1;
            obj;
            JVM INSTR monitorexit ;
            throw obj1;
_L4:
            j = i;
            if (((List) (obj1)).get(i) != null)
            {
                break MISSING_BLOCK_LABEL_203;
            }
            ((List) (obj1)).remove(i);
            j = i - 1;
            i = j - 1;
              goto _L7
        }

        private Pinger()
        {
            this$0 = IRCService.this;
            super();
            sb = new StringBuilder(20);
            interrupted = false;
            sleepTime = 0x493e0;
        }

        Pinger(Pinger pinger)
        {
            this();
        }
    }

    public static final class PrefsHolder
    {

        Uri pDefaultTone;
        public int pMainOptions;
        int pNotificationEvents;
        int pReconnectLimit;
        int pSoundOptions;
        int pVibrateOptions;
        public String partReason;
        public String quitReason;

        public PrefsHolder()
        {
        }
    }

    public static interface ServerStateListener
    {

        public abstract void onAllStopped();

        public abstract void onServerStateChanged(int i);
    }


    private static String ALL_MODES;
    private static final long NO_VIBRATE_PATTERN[] = new long[1];
    private static final int PREFS_DETECT_CONNECTION;
    static final int PREFS_ENABLE_CHATLOGS;
    static final int PREFS_HIDE_MESSAGES;
    private static final int PREFS_LOGS_MODE_YEAR_MONTH;
    static final int PREFS_LOGS_MODE_YEAR_MONTH_DAY;
    static final int PREFS_PLAY_SOUND;
    private static final int PREFS_RECONNECT;
    static final int PREFS_REJOIN_ON_KICK;
    static final int PREFS_SHOW_NOTIFS;
    static final int PREFS_SHOW_TIMESTAMPS;
    static final int PREFS_SKIP_MOTD;
    static final int PREFS_VIBRATE;
    static final int PREF_DEBUG_LOG_ALL_MESSAGES;
    static final int PREF_DEBUG_LOG_UNPARSEABLE;
    static final int PREF_DEBUG_SHOW_IGNORED;
    private static final long VIBRATE_PATTERN[] = {
        100L, 1000L
    };
    private static int increment;
    public static PrefsHolder sPreferences = new PrefsHolder();
    private final Binder binder = new IRCServiceBinder();
    private List mFinder;
    protected Handler mHandler;
    final List mListeners = new Vector();
    final SparseArray mManagers = new SparseArray();
    NotificationManager mNm;
    private Notification mNotification;
    private RemoteViews mNotificationView;
    private Pinger mPinger;
    private BroadcastReceiver mReceiver;
    final SparseArray mSenders = new SparseArray();
    final SparseArray mServers = new SparseArray();
    private TelephonyManager mTm;
    private android.net.wifi.WifiManager.WifiLock mWiFiLock;
    private WifiManager mWifiManager;
    SharedPreferences prefs;
    boolean screenOn;
    android.os.PowerManager.WakeLock wakeLock;

    public IRCService()
    {
    }

    private ServerConnection createServer(int i, ServerProfile serverprofile, MessageSender messagesender)
    {
        serverprofile = new ServerConnection(i, serverprofile, messagesender, getSessionManagerForId(i), this);
        mServers.put(i, serverprofile);
        fillFinder();
        return serverprofile;
    }

    private void doStartForeground()
    {
        startForeground(0x7fffffff, mNotification);
    }

    private void fillFinder()
    {
        SparseArray sparsearray = mServers;
        int i = sparsearray.size();
        if (mFinder == null)
        {
            mFinder = new Vector();
        }
        List list = mFinder;
        list.clear();
        i--;
        do
        {
            if (i < 0)
            {
                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                return;
            }
            list.add(((ServerConnection)sparsearray.valueAt(i)).pLabel);
            i--;
        } while (true);
    }

    private void loadOptions()
    {
        Object obj;
        Object obj1;
        Object obj2;
        StringBuilder stringbuilder;
        int i;
        int j;
        int k;
        int i1;
        j = 0;
        obj = prefs;
        boolean flag = ((SharedPreferences) (obj)).getBoolean(getString(0x7f0a001f), true);
        boolean flag1 = ((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0020), true);
        boolean flag2 = ((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0018), true);
        boolean flag3 = ((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0021), false);
        boolean flag4 = ((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0024), false);
        obj1 = new PrefsHolder();
        obj2 = ((SharedPreferences) (obj)).getString(getString(0x7f0a0025), null);
        int l;
        if (obj2 != null)
        {
            obj1.pDefaultTone = Uri.parse(((String) (obj2)));
        } else
        {
            obj1.pDefaultTone = null;
        }
        obj2 = getString(0x7f0a0026);
        obj1.pReconnectLimit = Utils.parseInt(((SharedPreferences) (obj)).getString(((String) (obj2)), "3"), 3, ((SharedPreferences) (obj)), ((String) (obj2)), "IRCService");
        if (flag2)
        {
            j = 0 | PREFS_RECONNECT;
        }
        i = j;
        if (flag)
        {
            i = j | PREFS_PLAY_SOUND;
        }
        j = i;
        if (flag1)
        {
            j = i | PREFS_VIBRATE;
        }
        k = j;
        if (flag4)
        {
            k = j | PREFS_DETECT_CONNECTION;
        }
        obj2 = ((SharedPreferences) (obj)).getString(getString(0x7f0a0028), "0");
        if (!((String) (obj2)).equals("0")) goto _L2; else goto _L1
_L1:
        i = k | PREFS_LOGS_MODE_YEAR_MONTH;
_L12:
        k = parseNotificationEvents(((SharedPreferences) (obj)).getString(getString(0x7f0a0029), null));
        l = parseNotificationEvents(((SharedPreferences) (obj)).getString(getString(0x7f0a002a), null));
        if (ALL_MODES != null) goto _L4; else goto _L3
_L3:
        obj2 = getResources().getStringArray(0x7f0b0005);
        i1 = obj2.length;
        stringbuilder = new StringBuilder(i1 * 10 + "###".length() * 5);
        j = 0;
_L9:
        if (j < i1) goto _L6; else goto _L5
_L5:
        ALL_MODES = stringbuilder.toString();
_L4:
        obj1.pNotificationEvents = parseNotificationEvents(((SharedPreferences) (obj)).getString(getString(0x7f0a002b), ALL_MODES));
        obj1.pSoundOptions = k;
        obj1.pVibrateOptions = l;
        obj2 = getString(0x7f0a0010);
        j = Utils.parseInt(((SharedPreferences) (obj)).getString(((String) (obj2)), "10"), 10, ((SharedPreferences) (obj)), ((String) (obj2)), "IRCService");
        l = j;
        if (j <= 0)
        {
            l = 10;
        }
        j = i;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a001c), false))
        {
            j = i | PREFS_SHOW_TIMESTAMPS;
        }
        i = j;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a001b), true))
        {
            i = j | PREFS_SHOW_NOTIFS;
        }
        j = i;
        if (flag)
        {
            j = i | PREFS_PLAY_SOUND;
        }
        i = j;
        if (flag1)
        {
            i = j | PREFS_VIBRATE;
        }
        j = i;
        if (flag3)
        {
            j = i | PREFS_HIDE_MESSAGES;
        }
        k = j;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0017), false))
        {
            k = j | PREFS_REJOIN_ON_KICK;
        }
        i = k;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a001a), false))
        {
            i = k | PREFS_SKIP_MOTD;
        }
        k = 1;
        j = i;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0027), false))
        {
            j = i | PREFS_ENABLE_CHATLOGS;
            k = 0;
        }
        i = j;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0036), false))
        {
            i = j | PREF_DEBUG_LOG_ALL_MESSAGES;
        }
        j = i;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0037), false))
        {
            j = i | PREF_DEBUG_LOG_UNPARSEABLE;
        }
        i = j;
        if (((SharedPreferences) (obj)).getBoolean(getString(0x7f0a0038), false))
        {
            i = j | PREF_DEBUG_SHOW_IGNORED;
        }
        obj1.quitReason = prefs.getString(getString(0x7f0a0012), "Bye");
        obj1.partReason = prefs.getString(getString(0x7f0a0011), "Leaving");
        obj1.pMainOptions = i;
        sPreferences = ((PrefsHolder) (obj1));
        obj = mServers;
        obj;
        JVM INSTR monitorenter ;
        obj1 = mServers;
        j = ((SparseArray) (obj1)).size();
        ServerConnection.sTimeout = l;
        i = 0;
_L10:
        if (i < j) goto _L8; else goto _L7
_L7:
        obj;
        JVM INSTR monitorexit ;
        return;
_L2:
        i = k;
        if (((String) (obj2)).equals("1"))
        {
            i = k | PREFS_LOGS_MODE_YEAR_MONTH_DAY;
        }
        continue; /* Loop/switch isn't completed */
_L6:
        stringbuilder.append(obj2[j]);
        if (j != i1 - 1)
        {
            stringbuilder.append("###");
        }
        j++;
          goto _L9
_L8:
        obj2 = (ServerConnection)((SparseArray) (obj1)).valueAt(i);
        if (k == 0)
        {
            break MISSING_BLOCK_LABEL_900;
        }
        ((ServerConnection) (obj2)).pLogs.closeAll();
        ((ServerConnection) (obj2)).buildHilightRegexp(Utils.escape(((ServerConnection) (obj2)).pNick));
        i++;
          goto _L10
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        if (true) goto _L12; else goto _L11
_L11:
    }

    private int parseNotificationEvents(String s)
    {
        if (s != null) goto _L2; else goto _L1
_L1:
        int i = 11;
_L4:
        return i;
_L2:
        int j = 0;
        s = MultiChoicePreference.parseStoredValue(s);
        if (s == null)
        {
            return 11;
        }
        int k = s.length - 1;
        do
        {
            i = j;
            if (k < 0)
            {
                continue;
            }
            String s1 = s[k];
            if (s1.equals("HI"))
            {
                i = j | 1;
            } else
            if (s1.equals("PM"))
            {
                i = j | 2;
            } else
            if (s1.equals("CN"))
            {
                i = j | 4;
            } else
            if (s1.equals("SN"))
            {
                i = j | 0x10;
            } else
            {
                i = j;
                if (s1.equals("DC"))
                {
                    i = j | 8;
                }
            }
            k--;
            j = i;
        } while (true);
        if (true) goto _L4; else goto _L3
_L3:
    }

    private void startBackgroundWork()
    {
        if (mPinger == null)
        {
            mPinger = new Pinger(null);
            mPinger.start();
        }
    }

    private void stopBackgroundWork()
    {
        if (mPinger != null)
        {
            synchronized (mPinger)
            {
                mPinger.interrupted = true;
                mPinger.notify();
                mPinger.interrupt();
                mPinger = null;
            }
            return;
        } else
        {
            return;
        }
        exception;
        pinger;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void acquireOrReleaseWifiLock()
    {
        boolean flag;
        boolean flag1;
        flag = areServersAlive();
        flag1 = isWifiConnected();
        if (!flag1 || mServers.size() <= 0) goto _L2; else goto _L1
_L1:
        flag1 = mWiFiLock.isHeld();
        if (flag1 || !flag) goto _L4; else goto _L3
_L3:
        mWiFiLock.acquire();
_L6:
        return;
_L4:
        if (flag1 && !flag)
        {
            mWiFiLock.release();
            return;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        if (!flag1 && mWiFiLock.isHeld() || mServers.size() == 0 && mWiFiLock.isHeld())
        {
            mWiFiLock.release();
            return;
        }
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void addStateListener(ServerStateListener serverstatelistener)
    {
        List list;
        int i;
        int j;
        list = mListeners;
        j = list.size();
        i = j;
_L6:
        if (i < j) goto _L2; else goto _L1
_L1:
        list.add(serverstatelistener);
_L4:
        return;
_L2:
        if (list.get(i) == serverstatelistener) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public boolean areServersAlive()
    {
        return getActiveServerCount() > 0;
    }

    public void cleanUpIfRequired(int i)
    {
        ServerConnection serverconnection = (ServerConnection)mServers.get(i);
        if (serverconnection != null) goto _L2; else goto _L1
_L1:
        mManagers.delete(i);
        SessionManager.notifyPostDelete(i);
        mSenders.delete(i);
_L4:
        acquireOrReleaseWifiLock();
        if (mServers.size() == 0)
        {
            stopBackgroundWork();
            doStopForeground(false);
        }
        return;
_L2:
        if (serverconnection.pState == 4)
        {
            SessionManager.notifyPostDelete(i);
            serverconnection.pLogs.closeAll();
            mServers.delete(i);
            fillFinder();
            mManagers.delete(i);
            mSenders.delete(i);
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void doStopForeground(boolean flag)
    {
        stopForeground(flag);
        ((NotificationManager)getSystemService("notification")).cancel(0x7fffffff);
    }

    public int findNextActive(int i, boolean flag)
    {
        SparseArray sparsearray;
        int l;
        sparsearray = mServers;
        l = sparsearray.size();
        if (l != 1) goto _L2; else goto _L1
_L1:
        int k;
        return i;
_L2:
        if ((k = sparsearray.indexOfKey(i)) < 0 || !flag && k + 1 > l)
        {
            continue; /* Loop/switch isn't completed */
        }
        if (l != 2) goto _L4; else goto _L3
_L3:
        k;
        JVM INSTR tableswitch 0 1: default 76
    //                   0 265
    //                   1 277;
           goto _L4 _L5 _L6
_L4:
        String s;
        Object obj;
        String s2;
        int j;
        boolean flag1;
        s = ((ServerConnection)sparsearray.valueAt(k)).pLabel;
        obj = mFinder;
        if (((String)((List) (obj)).get(0)).equals(s) && flag)
        {
            j = 1;
        } else
        {
            j = 0;
        }
        if (!flag && j == 0 && ((String)((List) (obj)).get(l - 1)).equals(s))
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        if (j == 0) goto _L8; else goto _L7
_L7:
        s2 = (String)((List) (obj)).get(l - 1);
        j = l - 1;
_L16:
        if (j >= 0) goto _L10; else goto _L9
_L9:
        flag1 = false;
        j = l - 1;
_L19:
        if (j >= 0) goto _L12; else goto _L11
_L11:
        j = ((flag1) ? 1 : 0);
_L17:
        if (!flag)
        {
            continue; /* Loop/switch isn't completed */
        }
        if (j <= 0 || j >= l)
        {
            continue; /* Loop/switch isn't completed */
        }
        s = (String)((List) (obj)).get(j - 1);
        j = l - 1;
_L15:
        if (j < 0)
        {
            continue; /* Loop/switch isn't completed */
        }
        if (j != k) goto _L14; else goto _L13
_L13:
        j--;
          goto _L15
_L5:
        return ((ServerConnection)sparsearray.valueAt(1)).id;
_L6:
        return ((ServerConnection)sparsearray.valueAt(0)).id;
_L10:
        ServerConnection serverconnection1 = (ServerConnection)sparsearray.valueAt(j);
        if (serverconnection1.pLabel.equals(s2))
        {
            return serverconnection1.id;
        }
        j--;
          goto _L16
_L8:
        if (flag1)
        {
            String s3 = (String)((List) (obj)).get(0);
            j = 0;
            while (j < l) 
            {
                ServerConnection serverconnection2 = (ServerConnection)sparsearray.valueAt(j);
                if (serverconnection2.pLabel.equals(s3))
                {
                    return serverconnection2.id;
                }
                j++;
            }
        }
          goto _L9
_L12:
        if (!((String)((List) (obj)).get(j)).equals(s)) goto _L18; else goto _L17
_L18:
        j--;
          goto _L19
_L14:
        if (!((ServerConnection) (obj = (ServerConnection)sparsearray.valueAt(j))).pLabel.equals(s)) goto _L13; else goto _L20
_L20:
        return ((ServerConnection) (obj)).id;
        if (j + 1 >= l) goto _L1; else goto _L21
_L21:
        String s1 = (String)((List) (obj)).get(j + 1);
        j = 0;
        while (j < l) 
        {
            ServerConnection serverconnection;
            if (j != k)
            {
                if ((serverconnection = (ServerConnection)sparsearray.valueAt(j)).pLabel.equals(s1))
                {
                    return serverconnection.id;
                }
            }
            j++;
        }
        if (true) goto _L1; else goto _L22
_L22:
    }

    public int getActiveServerCount()
    {
        int i = 0;
        SparseArray sparsearray = mServers;
        sparsearray;
        JVM INSTR monitorenter ;
        SparseArray sparsearray1;
        int j;
        sparsearray1 = mServers;
        j = sparsearray1.size() - 1;
_L2:
        if (j >= 0)
        {
            break MISSING_BLOCK_LABEL_33;
        }
        sparsearray;
        JVM INSTR monitorexit ;
        return i;
        ServerConnection serverconnection = (ServerConnection)sparsearray1.valueAt(j);
        if (serverconnection.isReconnecting())
        {
            i++;
            break MISSING_BLOCK_LABEL_93;
        }
        Exception exception;
        switch (serverconnection.pState)
        {
        default:
            break;

        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            break; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_93;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        j--;
        if (true) goto _L2; else goto _L1
_L1:
        i++;
        if (true) goto _L4; else goto _L3
_L3:
        if (true) goto _L2; else goto _L5
_L5:
    }

    public int[] getActiveServerIds()
    {
        SparseArray sparsearray = mServers;
        sparsearray;
        JVM INSTR monitorenter ;
        SparseArray sparsearray1;
        int ai[];
        int j;
        sparsearray1 = mServers;
        j = sparsearray1.size();
        ai = new int[j];
        int i = 0;
_L2:
        if (i < j)
        {
            break MISSING_BLOCK_LABEL_37;
        }
        sparsearray;
        JVM INSTR monitorexit ;
        return ai;
        ai[i] = ((ServerConnection)sparsearray1.valueAt(i)).id;
        i++;
        if (true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int getFirstActiveId()
    {
        SparseArray sparsearray = mServers;
        sparsearray;
        JVM INSTR monitorenter ;
        SparseArray sparsearray1;
        int j;
        sparsearray1 = mServers;
        j = sparsearray1.size();
        int i = 0;
_L4:
        if (i < j)
        {
            break MISSING_BLOCK_LABEL_32;
        }
        sparsearray;
        JVM INSTR monitorexit ;
        return -1;
        ServerConnection serverconnection;
        serverconnection = (ServerConnection)sparsearray1.valueAt(i);
        if (!serverconnection.isReconnecting())
        {
            break MISSING_BLOCK_LABEL_65;
        }
        i = serverconnection.id;
        sparsearray;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        sparsearray;
        JVM INSTR monitorexit ;
        throw exception;
        serverconnection.pState;
        JVM INSTR tableswitch 1 3: default 107
    //                   1 96
    //                   2 96
    //                   3 96;
           goto _L1 _L2 _L2 _L2
_L2:
        i = serverconnection.id;
        sparsearray;
        JVM INSTR monitorexit ;
        return i;
_L1:
        i++;
        if (true) goto _L4; else goto _L3
_L3:
    }

    public MessageSender getSenderForId(int i)
    {
        MessageSender messagesender1 = (MessageSender)mSenders.get(i);
        MessageSender messagesender = messagesender1;
        if (messagesender1 == null)
        {
            messagesender = new MessageSender(i);
            mSenders.put(i, messagesender);
        }
        return messagesender;
    }

    public ServerConnection getServer(int i, boolean flag, ServerProfile serverprofile, ChatWindow chatwindow)
    {
        ServerConnection serverconnection = (ServerConnection)mServers.get(i);
        MessageSender messagesender = getSenderForId(i);
        if (serverconnection != null)
        {
            if (chatwindow != null)
            {
                messagesender.setUi(chatwindow);
                serverconnection.setMediator(messagesender);
            }
            return serverconnection;
        }
        if (flag)
        {
            doStartForeground();
            return createServer(i, serverprofile, messagesender);
        } else
        {
            return null;
        }
    }

    public int getServerState(int i)
    {
        ServerConnection serverconnection = (ServerConnection)mServers.get(i);
        if (serverconnection != null)
        {
            return serverconnection.pState;
        } else
        {
            return 4;
        }
    }

    public SessionManager getSessionManagerForId(int i)
    {
        SessionManager sessionmanager1 = (SessionManager)mManagers.get(i);
        SessionManager sessionmanager = sessionmanager1;
        if (sessionmanager1 == null)
        {
            sessionmanager = new SessionManager(i, Utils.getIRCDb(this).getDetailsForId(i));
            mManagers.put(i, sessionmanager);
            getSenderForId(i).setSessionManager(sessionmanager);
        }
        return sessionmanager;
    }

    boolean isWifiConnected()
    {
        WifiInfo wifiinfo = mWifiManager.getConnectionInfo();
        return wifiinfo != null && wifiinfo.getBSSID() != null && wifiinfo.getNetworkId() != -1;
    }

    public IBinder onBind(Intent intent)
    {
        return binder;
    }

    public void onCreate()
    {
        super.onCreate();
        screenOn = true;
        prefs = Utils.getPrefs(this);
        loadOptions();
        wakeLock = ((PowerManager)getSystemService("power")).newWakeLock(1, "IRCService");
        wakeLock.setReferenceCounted(false);
        Object obj = new RemoteViews(getPackageName(), 0x7f030024);
        ((RemoteViews) (obj)).setTextViewText(0x7f08006f, getString(0x7f0a01bd));
        Notification notification = new Notification();
        notification.contentView = ((RemoteViews) (obj));
        notification.icon = 0x7f02000b;
        notification.flags = 34;
        Intent intent = new Intent(this, net/andchat/donate/Activities/Main);
        intent.addFlags(0x14000000);
        intent.setAction("net.andchat.donate.FROM_NOTIFICATION");
        notification.contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mNotification = notification;
        mNm = (NotificationManager)getSystemService("notification");
        mNotificationView = ((RemoteViews) (obj));
        mHandler = new Handler() {

            final IRCService this$0;

            private void handleServConnUpdate(Message message)
            {
                obj1 = (ServerConnection)message.obj;
                message.arg1;
                JVM INSTR tableswitch 0 3: default 44
            //                           0 45
            //                           1 125
            //                           2 145
            //                           3 279;
                   goto _L1 _L2 _L3 _L4 _L5
_L1:
                return;
_L2:
                notifyStateChange(((ServerConnection) (obj1)).id);
                if (((ServerConnection) (obj1)).pConnectionChanged || Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_RECONNECT) && ((ServerConnection) (obj1)).pReconnectCount <= IRCService.sPreferences.pReconnectLimit)
                {
                    (((_cls1) (obj1)). new Thread() {

                        final _cls1 this$1;
                        private final ServerConnection val$sc;

                        public void run()
                        {
                            setName((new StringBuilder("Reconnecting ")).append(sc.pLabel).toString());
                            sc.reconnect();
                        }

            
            {
                this$1 = final__pcls1;
                sc = ServerConnection.this;
                super();
            }
                    }).start();
                }
                if (!areServersAlive())
                {
                    stopBackgroundWork();
                }
                updateNotification();
                return;
_L3:
                if (obj1 != null)
                {
                    notifyStateChange(((ServerConnection) (obj1)).id);
                }
                updateNotification();
                return;
_L4:
                if (obj1 != null)
                {
                    synchronized (mServers)
                    {
                        ((ServerConnection) (obj1)).pLogs.closeAll();
                        mServers.delete(((ServerConnection) (obj1)).id);
                    }
                }
                if (mServers.size() == 0)
                {
                    stopBackgroundWork();
                    mManagers.clear();
                    mSenders.clear();
                    message = mListeners;
                    int j = message.size();
                    int i = 0;
                    while (i < j) 
                    {
                        obj1 = (ServerStateListener)message.get(i);
                        if (obj1 != null)
                        {
                            ((ServerStateListener) (obj1)).onAllStopped();
                        }
                        i++;
                    }
                }
                if (false)
                {
                }
                continue; /* Loop/switch isn't completed */
                obj1;
                message;
                JVM INSTR monitorexit ;
                throw obj1;
_L5:
                updateNotification();
                notifyStateChange(((ServerConnection) (obj1)).id);
                if (message.arg2 == 1)
                {
                    startBackgroundWork();
                    return;
                }
                if (true) goto _L1; else goto _L6
_L6:
            }

            private void notifyStateChange(int i)
            {
                List list = mListeners;
                int k = list.size();
                int j = 0;
                do
                {
                    if (j >= k)
                    {
                        return;
                    }
                    ServerStateListener serverstatelistener = (ServerStateListener)list.get(j);
                    if (serverstatelistener != null)
                    {
                        serverstatelistener.onServerStateChanged(i);
                    }
                    j++;
                } while (true);
            }

            public void handleMessage(Message message)
            {
                message.what;
                JVM INSTR tableswitch 0 0: default 24
            //                           0 32;
                   goto _L1 _L2
_L1:
                acquireOrReleaseWifiLock();
                return;
_L2:
                handleServConnUpdate(message);
                if (true) goto _L1; else goto _L3
_L3:
            }

            
            {
                this$0 = IRCService.this;
                super();
            }
        };
        mWifiManager = (WifiManager)getSystemService("wifi");
        mWiFiLock = mWifiManager.createWifiLock("IRCService");
        mWiFiLock.setReferenceCounted(false);
        mTm = (TelephonyManager)getSystemService("phone");
        mReceiver = new InfoReceiver(null);
        obj = new IntentFilter();
        ((IntentFilter) (obj)).addAction("android.net.conn.CONNECTIVITY_CHANGE");
        ((IntentFilter) (obj)).addAction("android.intent.action.SCREEN_OFF");
        ((IntentFilter) (obj)).addAction("android.intent.action.SCREEN_ON");
        ((IntentFilter) (obj)).addAction("android.intent.action.TIME_TICK");
        registerReceiver(mReceiver, ((IntentFilter) (obj)));
        ((IRCApp)getApplication()).addWatcher(this);
    }

    public void onDestroy()
    {
        doStopForeground(true);
        mNm.cancel(0x7fffffff);
        super.onDestroy();
        if (mReceiver != null)
        {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        stopBackgroundWork();
        acquireOrReleaseWifiLock();
        ((IRCApp)getApplication()).removeWatcher(this);
    }

    public void onPreferencesChanged()
    {
        loadOptions();
        SparseArray sparsearray = mManagers;
        int k = sparsearray.size();
        String s = getString(0x7f0a0019);
        int j = Utils.parseInt(prefs.getString(s, "20"), 20, prefs, s, "IRCService");
        int i = j;
        if (j > 50)
        {
            i = 50;
            prefs.edit().putString(s, "50").commit();
        }
        j = 0;
        do
        {
            if (j >= k)
            {
                return;
            }
            ((SessionManager)sparsearray.valueAt(j)).setInputLimit(i);
            j++;
        } while (true);
    }

    public int onStartCommand(Intent intent, int i, int j)
    {
        super.onStartCommand(intent, i, j);
        return 1;
    }

    public void remove(ServerConnection serverconnection)
    {
        if (serverconnection.isReconnecting())
        {
            serverconnection.wakeAndStop();
        }
        serverconnection.pLogs.closeAll();
        mServers.delete(serverconnection.id);
        fillFinder();
        updateNotification();
    }

    public void removeStateListener(ServerStateListener serverstatelistener)
    {
        List list = mListeners;
        int j = list.size();
        int i = 0;
        do
        {
            if (i >= j)
            {
                return;
            }
            if (list.get(i) == serverstatelistener)
            {
                list.remove(i);
                return;
            }
            i++;
        } while (true);
    }

    public void runOnAnotherProfile(int i, String s)
    {
        ServerConnection serverconnection = getServer(i, false, null, null);
        if (serverconnection == null)
        {
            return;
        } else
        {
            CommandParser.handleCommand(s, serverconnection);
            return;
        }
    }

    boolean shouldPlayNotification()
    {
        while (mTm == null || mTm.getCallState() == 0) 
        {
            return true;
        }
        return false;
    }

    public void stop()
    {
        if (mServers.size() == 0)
        {
            mManagers.clear();
            Main.sFilter = 0;
            stopSelf();
        }
    }

    public void stopAll()
    {
        int i;
        i = mServers.size();
        if (i <= 0)
        {
            break MISSING_BLOCK_LABEL_61;
        }
        this;
        JVM INSTR monitorenter ;
        SparseArray sparsearray = mServers;
        i--;
_L2:
        if (i >= 0)
        {
            break MISSING_BLOCK_LABEL_30;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        (new Thread() {

            final IRCService this$0;
            private final ServerConnection val$s;

            public void run()
            {
                s.stopConnection(2);
            }

            
            {
                this$0 = IRCService.this;
                s = serverconnection;
                super();
            }
        }).start();
        i--;
        if (true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        List list = mListeners;
        int k = list.size();
        for (int j = 0; j < k; j++)
        {
            ServerStateListener serverstatelistener = (ServerStateListener)list.get(j);
            if (serverstatelistener != null)
            {
                serverstatelistener.onAllStopped();
            }
        }

        return;
    }

    public void stopConnection(int i, int j)
    {
        ServerConnection serverconnection = (ServerConnection)mServers.get(i);
        if (serverconnection == null)
        {
            return;
        } else
        {
            serverconnection.stopConnection(j);
            return;
        }
    }

    public void stopReconnecting(int i, int j)
    {
        ServerConnection serverconnection = (ServerConnection)mServers.get(i);
        if (serverconnection != null)
        {
            serverconnection.pState = 4;
        }
        stopConnection(i, j);
    }

    void updateNotification()
    {
        Object obj;
        int i;
        int j;
        int k;
        int l;
        int i1;
        l = 0;
        k = 0;
        j = 0;
        obj = mServers;
        i1 = ((SparseArray) (obj)).size();
        i = 0;
_L5:
        if (i >= i1)
        {
            obj = mNotificationView;
            Object obj1 = new StringBuilder();
            if (l > 0 || j > 0 || k > 0)
            {
                ((StringBuilder) (obj1)).append("Servers: ");
            }
            if (l > 0 || j > 0)
            {
                ((StringBuilder) (obj1)).append(l + j).append(" Active");
            }
            if (k > 0)
            {
                if (l > 0 || j > 0)
                {
                    ((StringBuilder) (obj1)).append(" | ");
                }
                ((StringBuilder) (obj1)).append(k).append(" Error");
                if (k > 1)
                {
                    ((StringBuilder) (obj1)).append("s");
                }
            }
            if (((StringBuilder) (obj1)).length() == 0)
            {
                ((StringBuilder) (obj1)).append(getString(0x7f0a01bd));
            }
            ((RemoteViews) (obj)).setTextViewText(0x7f08006f, ((StringBuilder) (obj1)).toString());
            obj1 = mNotification;
            obj1.flags = 34;
            boolean flag;
            if (l + k + j == 0)
            {
                obj1.icon = 0x7f02000b;
            } else
            if (l > 0 && j + k == 0)
            {
                obj1.icon = 0x7f02000b;
            } else
            if (k > 0 && j + l == 0)
            {
                obj1.icon = 0x7f02000c;
            } else
            if (j > 0 && (l > 0 || k > 0))
            {
                obj1.icon = 0x7f02000d;
            } else
            if (j > 0 && l + k == 0)
            {
                obj1.icon = 0x7f02000d;
            } else
            if ((j > 0 || l > 0) && k > 0)
            {
                obj1.icon = 0x7f02000d;
            }
            flag = shouldPlayNotification();
            if (k > 0 && !screenOn)
            {
                boolean flag1 = Utils.isBitSet(sPreferences.pNotificationEvents, 8);
                if (flag1 && Utils.isBitSet(sPreferences.pMainOptions, PREFS_VIBRATE) && flag && Utils.isBitSet(sPreferences.pVibrateOptions, 8))
                {
                    obj1.vibrate = VIBRATE_PATTERN;
                } else
                {
                    obj1.vibrate = NO_VIBRATE_PATTERN;
                }
                if (flag1 && Utils.isBitSet(sPreferences.pMainOptions, PREFS_PLAY_SOUND) && flag && Utils.isBitSet(sPreferences.pSoundOptions, 8))
                {
                    if (sPreferences.pDefaultTone != null)
                    {
                        obj1.sound = sPreferences.pDefaultTone;
                        obj1.defaults = ((Notification) (obj1)).defaults & -2;
                    } else
                    {
                        obj1.sound = null;
                        obj1.defaults = ((Notification) (obj1)).defaults | 1;
                    }
                } else
                {
                    obj1.sound = null;
                    obj1.defaults = ((Notification) (obj1)).defaults & -2;
                }
                obj1.ledARGB = 0xffff0000;
                obj1.ledOnMS = 1;
                obj1.ledOffMS = 0;
                obj1.flags = 1;
            } else
            {
                obj1.sound = null;
                obj1.vibrate = NO_VIBRATE_PATTERN;
                obj1.ledARGB = 0;
                obj1.defaults = 0;
            }
            obj1.flags = ((Notification) (obj1)).flags | 0x22;
            mNotificationView = ((RemoteViews) (obj));
            mNotification = ((Notification) (obj1));
            if (k > 0 && !screenOn)
            {
                wakeLock.acquire(2000L);
            }
            mNm.notify(0x7fffffff, mNotification);
            return;
        }
        ((ServerConnection)((SparseArray) (obj)).valueAt(i)).pState;
        JVM INSTR tableswitch 0 3: default 464
    //                   0 471
    //                   1 480
    //                   2 489
    //                   3 489;
           goto _L1 _L2 _L3 _L4 _L4
_L1:
        break; /* Loop/switch isn't completed */
_L4:
        break MISSING_BLOCK_LABEL_489;
_L6:
        i++;
          goto _L5
_L2:
        k++;
          goto _L6
_L3:
        l++;
          goto _L6
        j++;
          goto _L6
    }

    static 
    {
        increment = 0;
        int i = increment;
        increment = i + 1;
        PREFS_SHOW_TIMESTAMPS = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_SHOW_NOTIFS = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_REJOIN_ON_KICK = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_SKIP_MOTD = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_PLAY_SOUND = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_VIBRATE = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_HIDE_MESSAGES = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_RECONNECT = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_DETECT_CONNECTION = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_ENABLE_CHATLOGS = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_LOGS_MODE_YEAR_MONTH = 1 << i;
        i = increment;
        increment = i + 1;
        PREFS_LOGS_MODE_YEAR_MONTH_DAY = 1 << i;
        i = increment;
        increment = i + 1;
        PREF_DEBUG_LOG_ALL_MESSAGES = 1 << i;
        i = increment;
        increment = i + 1;
        PREF_DEBUG_LOG_UNPARSEABLE = 1 << i;
        i = increment;
        increment = i + 1;
        PREF_DEBUG_SHOW_IGNORED = 1 << i;
    }





}
