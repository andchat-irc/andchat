// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import java.util.List;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection, IRCService

class n extends Thread
{

    final n.reconnect this$1;
    private final ServerConnection val$sc;

    public void run()
    {
        setName((new StringBuilder("Reconnecting ")).append(val$sc.pLabel).toString());
        val$sc.reconnect();
    }

    n()
    {
        this$1 = final_n;
        val$sc = ServerConnection.this;
        super();
    }

    // Unreferenced inner class net/andchat/donate/Backend/IRCService$1

/* anonymous class */
    class IRCService._cls1 extends Handler
    {

        final IRCService this$0;

        private void handleServConnUpdate(Message message)
        {
            obj = (ServerConnection)message.obj;
            message.arg1;
            JVM INSTR tableswitch 0 3: default 44
        //                       0 45
        //                       1 125
        //                       2 145
        //                       3 279;
               goto _L1 _L2 _L3 _L4 _L5
_L1:
            return;
_L2:
            notifyStateChange(((ServerConnection) (obj)).id);
            if (((ServerConnection) (obj)).pConnectionChanged || Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.access$2()) && ((ServerConnection) (obj)).pReconnectCount <= IRCService.sPreferences.pReconnectLimit)
            {
                (((IRCService._cls1._cls1) (obj)). new IRCService._cls1._cls1()).start();
            }
            if (!areServersAlive())
            {
                IRCService.access$3(IRCService.this);
            }
            updateNotification();
            return;
_L3:
            if (obj != null)
            {
                notifyStateChange(((ServerConnection) (obj)).id);
            }
            updateNotification();
            return;
_L4:
            if (obj != null)
            {
                synchronized (mServers)
                {
                    ((ServerConnection) (obj)).pLogs.closeAll();
                    mServers.delete(((ServerConnection) (obj)).id);
                }
            }
            if (mServers.size() == 0)
            {
                IRCService.access$3(IRCService.this);
                mManagers.clear();
                mSenders.clear();
                message = mListeners;
                int j = message.size();
                int i = 0;
                while (i < j) 
                {
                    obj = (IRCService.ServerStateListener)message.get(i);
                    if (obj != null)
                    {
                        ((IRCService.ServerStateListener) (obj)).onAllStopped();
                    }
                    i++;
                }
            }
            if (false)
            {
            }
            continue; /* Loop/switch isn't completed */
            obj;
            message;
            JVM INSTR monitorexit ;
            throw obj;
_L5:
            updateNotification();
            notifyStateChange(((ServerConnection) (obj)).id);
            if (message.arg2 == 1)
            {
                IRCService.access$4(IRCService.this);
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
                IRCService.ServerStateListener serverstatelistener = (IRCService.ServerStateListener)list.get(j);
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
        //                       0 32;
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
    }

}
