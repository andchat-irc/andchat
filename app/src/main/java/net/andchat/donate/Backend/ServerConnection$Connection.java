// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection, IRCService, Parser

private class mServ extends Thread
{

    private InputStream mIn;
    private final ServerProfile mOptions;
    private BufferedWriter mOut;
    private int mReconnectDelay;
    private final ServerConnection mServ;
    private final Socket mSock;
    private boolean mWasStopped;
    final ServerConnection this$0;

    private void stopCommandQ()
    {
        if (ServerConnection.access$4(ServerConnection.this) != null)
        {
            ServerConnection.access$4(ServerConnection.this).quit();
            ServerConnection.access$6(ServerConnection.this, null);
        }
    }

    public void run()
    {
        if (!ServerConnection.access$7(ServerConnection.this).get()) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorenter ;
        int k = mReconnectDelay * 1000;
        int i;
        if (k > 30000)
        {
            i = 30000;
        } else
        {
            i = k;
            if (k <= 0)
            {
                i = 5000;
            }
        }
        wait(i);
_L3:
        this;
        JVM INSTR monitorexit ;
        Object obj;
        Object obj2;
        try
        {
            ServerConnection.access$7(ServerConnection.this).set(false);
            if (mWasStopped)
            {
                stopCommandQ();
                return;
            }
        }
        // Misplaced declaration of an exception variable
        catch (Object obj2)
        {
            Object obj1;
            Object obj3;
            String s;
            android.text.SpannableStringBuilder spannablestringbuilder;
            int j;
            boolean flag;
            boolean flag1;
            if (pConnectTime != 0L && System.currentTimeMillis() > pConnectTime + 60000L)
            {
                j = 1;
            } else
            {
                j = 0;
            }
            if (j != 0)
            {
                pReconnectCount = 0;
                pConnectTime = 0L;
            }
            obj1 = new StringBuilder();
            if (obj2 instanceof SocketTimeoutException)
            {
                ((StringBuilder) (obj1)).append("Disconnected: Connection timed out.");
            } else
            if (obj2 instanceof NoRouteToHostException)
            {
                ((StringBuilder) (obj1)).append("Disconnected: No route to host.");
            } else
            if ((obj2 instanceof SocketException) || (obj2 instanceof UnknownHostException))
            {
                ((StringBuilder) (obj1)).append("Disconnected: ").append(((IOException) (obj2)).getMessage());
            } else
            {
                obj2 = ((IOException) (obj2)).getMessage();
                if (obj2 != null && ((String) (obj2)).length() > 0)
                {
                    ((StringBuilder) (obj1)).append("Disconnected: ").append(((String) (obj2)));
                } else
                {
                    ((StringBuilder) (obj1)).append("Disconnected");
                }
            }
            if (mOptions.usesSSL())
            {
                j = ((StringBuilder) (obj1)).indexOf("You should never see this");
                if (j != -1)
                {
                    ((StringBuilder) (obj1)).replace(j, "You should never see this".length() + j, "");
                }
            }
            flag1 = ServerConnection.access$8();
            j = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002a);
            obj2 = Utils.createMessage(ServerConnection.access$8(), ((CharSequence) (obj1)));
            Utils.addColour(flag1, ((android.text.SpannableStringBuilder) (obj2)), j, 0, ((StringBuilder) (obj1)).length());
            sendToAll(((CharSequence) (obj2)), 1);
            if (pState == 1 || pState == 3)
            {
                stopConn(0);
            }
            deactivateAllAndUpdateUi(true);
            return;
        }
        break MISSING_BLOCK_LABEL_304;
        obj;
        ((InterruptedException) (obj)).printStackTrace();
          goto _L3
        obj;
        this;
        JVM INSTR monitorexit ;
        throw obj;
        removeServerConnectionFlag(16);
        flag1 = ServerConnection.access$8();
        obj1 = Utils.getConnectionMessage(ServerConnection.access$2(ServerConnection.this), true, mOptions);
        if (pReconnectCount <= 0) goto _L5; else goto _L4
_L4:
        if (pReconnectCount <= 9) goto _L7; else goto _L6
_L6:
        obj2 = String.valueOf(pReconnectCount).toCharArray();
        ((StringBuilder) (obj1)).insert(0, "[").insert(1, ((char []) (obj2)), 0, obj2.length).insert(obj2.length + 1, "] ");
_L5:
        sendMessage("Status", Utils.createMessage(flag1, ((CharSequence) (obj1))), 1);
        pState = 3;
        Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, 3, 0, mServ).sendToTarget();
_L2:
        obj3 = mOptions;
        obj2 = ((ServerProfile) (obj3)).getCharset();
        obj1 = obj2;
        if (TextUtils.isEmpty(((CharSequence) (obj2))))
        {
            obj1 = ServerConnection.access$2(ServerConnection.this).prefs.getString(ServerConnection.access$2(ServerConnection.this).getString(0x7f0a000f), "UTF-8");
        }
        mSock.connect(new InetSocketAddress(((ServerProfile) (obj3)).getAddress(), ((ServerProfile) (obj3)).getPort()), ServerConnection.sTimeout * 1000);
        mIn = mSock.getInputStream();
        mOut = new BufferedWriter(new OutputStreamWriter(mSock.getOutputStream(), ((String) (obj1))), 1024);
        j = ((ServerProfile) (obj3)).getAuthModes();
        obj2 = new StringBuilder(20);
        if (Utils.isBitSet(j, net.andchat.donate.Misc.Modes.MODE_PASSWORD))
        {
            s = ((ServerProfile) (obj3)).getServerPassword();
            if (!TextUtils.isEmpty(s))
            {
                write(((StringBuilder) (obj2)).append("PASS ").append(s).append("\r\n").toString());
            }
            ((StringBuilder) (obj2)).setLength(0);
        }
        if (Utils.isBitSet(j, net.andchat.donate.Misc.Modes.MODE_SASL))
        {
            j = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002c);
            s = ServerConnection.access$9(ServerConnection.this, 0x7f0a0052);
            spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s);
            sendMessage("Status", Utils.addColour(ServerConnection.access$8(), spannablestringbuilder, j, 0, s.length()), 1);
            write("CAP REQ :sasl\r\n");
        }
        write(((StringBuilder) (obj2)).append("NICK ").append(((ServerProfile) (obj3)).getNick(1)).append("\r\n").toString());
        ((StringBuilder) (obj2)).setLength(0);
        write(((StringBuilder) (obj2)).append("USER ").append(((ServerProfile) (obj3)).getUsername()).append(" 0 ").append(((ServerProfile) (obj3)).getAddress()).append(" :").append(((ServerProfile) (obj3)).getRealname()).append("\r\n").toString());
        j = 0;
        obj2 = mIn;
        obj3 = mServ.pParser;
        obj1 = new (((String) (obj1)));
_L12:
        s = (() (obj1)).readLine(((InputStream) (obj2)));
        if (s == null) goto _L9; else goto _L8
_L8:
        if (pState != 0) goto _L10; else goto _L9
_L9:
        throw new IOException();
_L7:
        ((StringBuilder) (obj1)).insert(0, "[").insert(1, pReconnectCount).insert(2, "] ");
          goto _L5
_L10:
        flag = j;
        if (j != 0)
        {
            break MISSING_BLOCK_LABEL_942;
        }
        mServ.pState = 1;
        Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, 3, 1, mServ).sendToTarget();
        flag = true;
        pConnectTime = System.currentTimeMillis();
        j = ((flag) ? 1 : 0);
        if (s.length() == 0) goto _L12; else goto _L11
_L11:
        ((Parser) (obj3)).parse(s);
        j = ((flag) ? 1 : 0);
          goto _L12
    }

    protected void stopConn(int i)
    {
        int j = 4;
        if (pState == 0 && pState == 4)
        {
            return;
        }
        pState = 2;
        Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, 3, 0, mServ).sendToTarget();
        ServerConnection serverconnection;
        try
        {
            write((new StringBuilder("QUIT :")).append(getQuitReason()).append("\r\n").toString());
        }
        catch (NullPointerException nullpointerexception)
        {
            mIn = null;
            mOut = null;
            ServerConnection serverconnection1;
            Object obj;
            ServerConnection serverconnection2;
            Object obj1;
            try
            {
                mSock.shutdownInput();
            }
            catch (IOException ioexception2) { }
            catch (UnsupportedOperationException unsupportedoperationexception1) { }
            try
            {
                mSock.shutdownOutput();
            }
            catch (IOException ioexception1) { }
            catch (UnsupportedOperationException unsupportedoperationexception) { }
            try
            {
                mSock.close();
            }
            catch (IOException ioexception) { }
            serverconnection1 = ServerConnection.this;
            if (i == 0)
            {
                j = 0;
            }
            serverconnection1.pState = j;
            Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, i, 0, mServ).sendToTarget();
            stopCommandQ();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (Object obj)
        {
            mIn = null;
            mOut = null;
            try
            {
                mSock.shutdownInput();
            }
            // Misplaced declaration of an exception variable
            catch (Object obj) { }
            // Misplaced declaration of an exception variable
            catch (Object obj) { }
            try
            {
                mSock.shutdownOutput();
            }
            // Misplaced declaration of an exception variable
            catch (Object obj) { }
            // Misplaced declaration of an exception variable
            catch (Object obj) { }
            try
            {
                mSock.close();
            }
            // Misplaced declaration of an exception variable
            catch (Object obj) { }
            obj = ServerConnection.this;
            if (i == 0)
            {
                j = 0;
            }
            obj.pState = j;
            Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, i, 0, mServ).sendToTarget();
            stopCommandQ();
            return;
        }
        finally
        {
            mIn = null;
        }
        mIn = null;
        mOut = null;
        try
        {
            mSock.shutdownInput();
        }
        // Misplaced declaration of an exception variable
        catch (Object obj) { }
        // Misplaced declaration of an exception variable
        catch (Object obj) { }
        try
        {
            mSock.shutdownOutput();
        }
        // Misplaced declaration of an exception variable
        catch (Object obj) { }
        // Misplaced declaration of an exception variable
        catch (Object obj) { }
        try
        {
            mSock.close();
        }
        // Misplaced declaration of an exception variable
        catch (Object obj) { }
        serverconnection = ServerConnection.this;
        if (i == 0)
        {
            j = 0;
        }
        serverconnection.pState = j;
        Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, i, 0, mServ).sendToTarget();
        stopCommandQ();
        return;
        mOut = null;
        try
        {
            mSock.shutdownInput();
        }
        // Misplaced declaration of an exception variable
        catch (Object obj1) { }
        // Misplaced declaration of an exception variable
        catch (Object obj1) { }
        try
        {
            mSock.shutdownOutput();
        }
        // Misplaced declaration of an exception variable
        catch (Object obj1) { }
        // Misplaced declaration of an exception variable
        catch (Object obj1) { }
        try
        {
            mSock.close();
        }
        // Misplaced declaration of an exception variable
        catch (Object obj1) { }
        serverconnection2 = ServerConnection.this;
        if (i == 0)
        {
            j = 0;
        }
        serverconnection2.pState = j;
        Message.obtain(ServerConnection.access$2(ServerConnection.this).mHandler, 0, i, 0, mServ).sendToTarget();
        stopCommandQ();
        throw obj;
    }

    public void write(String s)
    {
        if (mOut == null)
        {
            return;
        }
        try
        {
            mOut.write(s);
            mOut.flush();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return;
        }
    }






    public (ServerConnection serverconnection1, int i)
    {
        this$0 = ServerConnection.this;
        super();
        mReconnectDelay = i;
        mServ = serverconnection1;
        mOptions = serverconnection1.pOptions;
        serverconnection1 = null;
        if (!mOptions.usesSSL()) goto _L2; else goto _L1
_L1:
        Object obj = new (ServerConnection.this, null);
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        SecureRandom securerandom = new SecureRandom();
        sslcontext.init(null, new TrustManager[] {
            obj
        }, securerandom);
        obj = sslcontext.getSocketFactory().createSocket();
        serverconnection1 = ((ServerConnection) (obj));
_L4:
        mSock = serverconnection1;
        setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            final ServerConnection.Connection this$1;

            public void uncaughtException(Thread thread, Throwable throwable)
            {
                Log.e("ServerConnection", "Exception", throwable);
                thread = ServerConnection.access$0(this$0, 0x7f0a0043, new Object[] {
                    mOptions.getAddress()
                });
                throwable = Utils.createMessage(isShowingTimestamps(), thread);
                Utils.addColour(isShowingTimestamps(), throwable, ServerConnection.access$1(this$0).getColourForEvent(0x7f0b002c), 0, thread.length());
                Utils.addLinks(throwable);
                sendToAll(throwable, 3);
                pState = 0;
                Message.obtain(ServerConnection.access$2(this$0).mHandler, 0, 3, 0, mServ).sendToTarget();
                stopCommandQ();
            }

            
            {
                this$1 = ServerConnection.Connection.this;
                super();
            }
        });
        ServerConnection.access$3(ServerConnection.this, new HandlerThread("CommandsQ"));
        ServerConnection.access$4(ServerConnection.this).start();
        ServerConnection.access$6(ServerConnection.this, new Handler(ServerConnection.access$4(ServerConnection.this).getLooper()) {

            final ServerConnection.Connection this$1;

            public void handleMessage(Message message)
            {
                ServerConnection.access$5(mServ, (String)message.obj);
            }

            
            {
                this$1 = ServerConnection.Connection.this;
                super(looper);
            }
        });
        return;
_L2:
        serverconnection1 = new Socket();
        continue; /* Loop/switch isn't completed */
        Object obj1;
        obj1;
        continue; /* Loop/switch isn't completed */
        obj1;
        continue; /* Loop/switch isn't completed */
        obj1;
        if (true) goto _L4; else goto _L3
_L3:
    }
}
