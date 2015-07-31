// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import com.glaforge.i18n.io.CharsetToolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Base64;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.CommandParser;
import net.andchat.donate.Misc.IRCMessage;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            IRCService, Ignores, MessageSender, Parser

public final class ServerConnection
    implements net.andchat.donate.Misc.CommandParser.Helper
{
    private static final class CharsetUtil
    {

        private final byte mBuffer[] = new byte[1024];
        private final String mCharset;
        private final boolean mGuessCharset;

        private String StringFromBuffer(byte abyte0[])
            throws UnsupportedEncodingException
        {
            if (abyte0[0] != -1) goto _L2; else goto _L1
_L1:
            int i;
            int j;
            j = abyte0.length;
            i = 0;
_L5:
            if (i < j) goto _L4; else goto _L3
_L3:
            return null;
_L4:
            if (abyte0[i] != -1)
            {
                byte abyte1[] = new byte[j - i];
                System.arraycopy(abyte0, i, abyte1, 0, abyte1.length);
                return StringFromBuffer(abyte1);
            }
            i++;
              goto _L5
_L2:
            if (abyte0[abyte0.length - 1] == 10)
            {
                int k = abyte0.length;
                i = k;
                if (abyte0[k - 2] == 13)
                {
                    i = k - 2;
                }
                String s1;
                String s4;
                if (mGuessCharset)
                {
                    s1 = CharsetToolkit.guessEncoding(abyte0, i, mCharset);
                } else
                {
                    s1 = mCharset;
                }
                s4 = s1;
                if (s1 == null)
                {
                    s4 = mCharset;
                }
                return new String(abyte0, 0, i, s4);
            }
            if (abyte0[0] != -1 && abyte0[abyte0.length - 1] != -1)
            {
                String s2;
                String s5;
                if (mGuessCharset)
                {
                    s2 = CharsetToolkit.guessEncoding(abyte0, abyte0.length, mCharset);
                } else
                {
                    s2 = mCharset;
                }
                s5 = s2;
                if (s2 == null)
                {
                    s5 = mCharset;
                }
                return new String(abyte0, 0, abyte0.length, s5);
            }
            i = abyte0.length - 1;
_L6:
            if (i >= 0)
            {
label0:
                {
                    if (abyte0[i] == -1)
                    {
                        break label0;
                    }
                    int l = i;
                    if (i != 0)
                    {
                        l = i;
                        if (abyte0[i - 1] == 13)
                        {
                            l = i - 1;
                        }
                    }
                    if (l != 0)
                    {
                        String s3;
                        String s6;
                        if (mGuessCharset)
                        {
                            s3 = CharsetToolkit.guessEncoding(abyte0, l, mCharset);
                        } else
                        {
                            s3 = mCharset;
                        }
                        s6 = s3;
                        if (s3 == null)
                        {
                            s6 = mCharset;
                        }
                        return new String(abyte0, 0, l, s6);
                    }
                }
            }
              goto _L3
            i--;
              goto _L6
        }

        public String readLine(InputStream inputstream)
            throws IOException
        {
            byte abyte0[];
            int i;
            abyte0 = mBuffer;
            Arrays.fill(abyte0, (byte)-1);
            i = 0;
_L6:
            boolean flag;
            boolean flag1;
            int j;
            if (i >= 1024)
            {
                return StringFromBuffer(abyte0);
            }
            j = inputstream.read();
            if (j == -1)
            {
                return StringFromBuffer(abyte0);
            }
            flag1 = false;
            flag = flag1;
            j;
            JVM INSTR tableswitch 10 13: default 84
        //                       10 120
        //                       11 88
        //                       12 88
        //                       13 105;
               goto _L1 _L2 _L3 _L3 _L4
_L1:
            flag = flag1;
_L3:
            abyte0[i] = (byte)j;
            if (flag)
            {
                return StringFromBuffer(abyte0);
            }
            break; /* Loop/switch isn't completed */
_L4:
            flag = flag1;
            if (i > 0) goto _L3; else goto _L5
_L5:
            i++;
              goto _L6
_L2:
            flag = true;
            if (i > 1) goto _L3; else goto _L5
        }

        public CharsetUtil(String s1)
        {
            mCharset = s1;
            boolean flag;
            if (s1.equalsIgnoreCase("UTF-8"))
            {
                flag = false;
            } else
            {
                flag = true;
            }
            mGuessCharset = flag;
        }
    }

    private class Connection extends Thread
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
            if (mUserCommandsHT != null)
            {
                mUserCommandsHT.quit();
                mUserCommandsH = null;
            }
        }

        public void run()
        {
            if (!mReconnecting.get()) goto _L2; else goto _L1
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
                mReconnecting.set(false);
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
                String s1;
                SpannableStringBuilder spannablestringbuilder;
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
                flag1 = ServerConnection.shouldShowTimeStamps();
                j = mColours.getColourForEvent(0x7f0b002a);
                obj2 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ((CharSequence) (obj1)));
                Utils.addColour(flag1, ((SpannableStringBuilder) (obj2)), j, 0, ((StringBuilder) (obj1)).length());
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
            flag1 = ServerConnection.shouldShowTimeStamps();
            obj1 = Utils.getConnectionMessage(mService, true, mOptions);
            if (pReconnectCount <= 0) goto _L5; else goto _L4
_L4:
            if (pReconnectCount <= 9) goto _L7; else goto _L6
_L6:
            obj2 = String.valueOf(pReconnectCount).toCharArray();
            ((StringBuilder) (obj1)).insert(0, "[").insert(1, ((char []) (obj2)), 0, obj2.length).insert(obj2.length + 1, "] ");
_L5:
            sendMessage("Status", Utils.createMessage(flag1, ((CharSequence) (obj1))), 1);
            pState = 3;
            Message.obtain(mService.mHandler, 0, 3, 0, mServ).sendToTarget();
_L2:
            obj3 = mOptions;
            obj2 = ((ServerProfile) (obj3)).getCharset();
            obj1 = obj2;
            if (TextUtils.isEmpty(((CharSequence) (obj2))))
            {
                obj1 = mService.prefs.getString(mService.getString(0x7f0a000f), "UTF-8");
            }
            mSock.connect(new InetSocketAddress(((ServerProfile) (obj3)).getAddress(), ((ServerProfile) (obj3)).getPort()), ServerConnection.sTimeout * 1000);
            mIn = mSock.getInputStream();
            mOut = new BufferedWriter(new OutputStreamWriter(mSock.getOutputStream(), ((String) (obj1))), 1024);
            j = ((ServerProfile) (obj3)).getAuthModes();
            obj2 = new StringBuilder(20);
            if (Utils.isBitSet(j, net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_PASSWORD))
            {
                s1 = ((ServerProfile) (obj3)).getServerPassword();
                if (!TextUtils.isEmpty(s1))
                {
                    write(((StringBuilder) (obj2)).append("PASS ").append(s1).append("\r\n").toString());
                }
                ((StringBuilder) (obj2)).setLength(0);
            }
            if (Utils.isBitSet(j, net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_SASL))
            {
                j = mColours.getColourForEvent(0x7f0b002c);
                s1 = s(0x7f0a0052);
                spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
                sendMessage("Status", Utils.addColour(ServerConnection.shouldShowTimeStamps(), spannablestringbuilder, j, 0, s1.length()), 1);
                write("CAP REQ :sasl\r\n");
            }
            write(((StringBuilder) (obj2)).append("NICK ").append(((ServerProfile) (obj3)).getNick(1)).append("\r\n").toString());
            ((StringBuilder) (obj2)).setLength(0);
            write(((StringBuilder) (obj2)).append("USER ").append(((ServerProfile) (obj3)).getUsername()).append(" 0 ").append(((ServerProfile) (obj3)).getAddress()).append(" :").append(((ServerProfile) (obj3)).getRealname()).append("\r\n").toString());
            j = 0;
            obj2 = mIn;
            obj3 = mServ.pParser;
            obj1 = new CharsetUtil(((String) (obj1)));
_L12:
            s1 = ((CharsetUtil) (obj1)).readLine(((InputStream) (obj2)));
            if (s1 == null) goto _L9; else goto _L8
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
            Message.obtain(mService.mHandler, 0, 3, 1, mServ).sendToTarget();
            flag = true;
            pConnectTime = System.currentTimeMillis();
            j = ((flag) ? 1 : 0);
            if (s1.length() == 0) goto _L12; else goto _L11
_L11:
            ((Parser) (obj3)).parse(s1);
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
            Message.obtain(mService.mHandler, 0, 3, 0, mServ).sendToTarget();
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
                Message.obtain(mService.mHandler, 0, i, 0, mServ).sendToTarget();
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
                Message.obtain(mService.mHandler, 0, i, 0, mServ).sendToTarget();
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
            Message.obtain(mService.mHandler, 0, i, 0, mServ).sendToTarget();
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
            Message.obtain(mService.mHandler, 0, i, 0, mServ).sendToTarget();
            stopCommandQ();
            throw obj;
        }

        public void write(String s1)
        {
            if (mOut == null)
            {
                return;
            }
            try
            {
                mOut.write(s1);
                mOut.flush();
                return;
            }
            // Misplaced declaration of an exception variable
            catch (String s1)
            {
                return;
            }
            // Misplaced declaration of an exception variable
            catch (String s1)
            {
                return;
            }
        }






        public Connection(ServerConnection serverconnection1, int i)
        {
            this$0 = ServerConnection.this;
            super();
            mReconnectDelay = i;
            mServ = serverconnection1;
            mOptions = serverconnection1.pOptions;
            serverconnection1 = null;
            if (!mOptions.usesSSL()) goto _L2; else goto _L1
_L1:
            Object obj = new TrustAllSSL(null);
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            SecureRandom securerandom = new SecureRandom();
            sslcontext.init(null, new TrustManager[] {
                obj
            }, securerandom);
            obj = sslcontext.getSocketFactory().createSocket();
            serverconnection1 = ((ServerConnection) (obj));
_L4:
            mSock = serverconnection1;
            setUncaughtExceptionHandler(new _cls1());
            mUserCommandsHT = new HandlerThread("CommandsQ");
            mUserCommandsHT.start();
            mUserCommandsH = new _cls2(mUserCommandsHT.getLooper());
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

    class LogManager
    {

        private final char FIND[];
        private final List mLoggers = new ArrayList();
        final ServerConnection this$0;

        private Logger find(String s1)
        {
            String s2 = sanitizeChan(s1);
            s1 = mLoggers;
            s1;
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
            s1;
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
            if (!logger.mChan.equalsIgnoreCase(s2))
            {
                break MISSING_BLOCK_LABEL_96;
            }
            s1;
            JVM INSTR monitorexit ;
            return logger;
            Exception exception;
            exception;
            s1;
            JVM INSTR monitorexit ;
            throw exception;
            i = j - 1;
            if (true) goto _L2; else goto _L1
_L1:
        }

        private Logger newLogger(String s1)
        {
            Logger logger;
            if (s1.length() == 0)
            {
                s1 = "Status";
            } else
            {
                s1 = sanitizeChan(s1);
            }
            logger = new Logger(s1);
            synchronized (mLoggers)
            {
                mLoggers.add(logger);
                StringBuilder stringbuilder = new StringBuilder();
                stringbuilder.append(s(0x7f0a0049, new Object[] {
                    ServerConnection.format(System.currentTimeMillis())
                }));
                logger.log(stringbuilder.toString().toCharArray(), false);
            }
            return logger;
            exception;
            s1;
            JVM INSTR monitorexit ;
            throw exception;
        }

        private String sanitizeChan(String s1)
        {
            int j = FIND.length;
            int i = 0;
            do
            {
                if (i >= j)
                {
                    return s1;
                }
                s1 = s1.replace(FIND[i], '_');
                i++;
            } while (true);
        }

        public void close(String s1)
        {
            s1 = find(sanitizeChan(s1));
            if (s1 != null)
            {
                s1.close();
                mLoggers.remove(s1);
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

        public void log(String s1, char ac[], boolean flag)
        {
            s1 = s1.trim();
            Logger logger;
            Logger logger1;
            if (s1.length() == 0 || s1.charAt(0) == '$')
            {
                s1 = "Status";
            } else
            {
                s1 = sanitizeChan(s1);
            }
            logger1 = find(s1);
            logger = logger1;
            if (logger1 == null)
            {
                logger = newLogger(s1);
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


        public LogManager()
        {
            this$0 = ServerConnection.this;
            super();
            FIND = (new char[] {
                ':', File.separatorChar
            });
        }
    }

    private final class LogManager.Logger
    {

        final String mChan;
        final BufferedWriter mWriter;
        final LogManager this$1;

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

        public LogManager.Logger(String s1)
        {
            Object obj;
            File file1;
            this$1 = LogManager.this;
            super();
            mChan = s1;
            File file = new File(Environment.getExternalStorageDirectory(), (new StringBuilder(File.separator)).append("net.andchat.donate").append(File.separator).append(pLabel).append(File.separator).append(getFolderName()).toString());
            if (!file.exists() || !file.isDirectory())
            {
                file.mkdirs();
            }
            file1 = new File(file, (new StringBuilder(String.valueOf(s1))).append(".txt").toString());
            obj = null;
            String s3 = pOptions.getCharset();
            String s2 = s3;
            try
            {
                if (TextUtils.isEmpty(s3))
                {
                    s2 = mService.prefs.getString(mService.getString(0x7f0a000f), "UTF-8");
                }
                logmanager = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1, true), s2), 512);
            }
            // Misplaced declaration of an exception variable
            catch (LogManager logmanager)
            {
                Log.e("AndChat::Logs", (new StringBuilder("Couldn't create log file for ")).append(s1).toString());
                logmanager = obj;
            }
            mWriter = LogManager.this;
            return;
        }
    }

    private class ParserImpl extends Parser
    {

        private int m433Count;
        final StringBuilder reusableSB;
        final ServerConnection this$0;

        private void sendCount(String s1)
        {
            (s1. new Thread() {

                final ParserImpl this$1;
                private final String val$chan;

                public void run()
                {
                    this;
                    JVM INSTR monitorenter ;
                    Exception exception;
                    try
                    {
                        wait(500L);
                    }
                    catch (InterruptedException interruptedexception) { }
                    this;
                    JVM INSTR monitorexit ;
                    boolean flag = ServerConnection.shouldShowTimeStamps();
                    net.andchat.donate.Backend.Sessions.Session.UserCount usercount = pSessionManager.getUserCount(chan);
                    StringBuilder stringbuilder = new StringBuilder(100);
                    stringbuilder.append(s(0x7f0a0071, new Object[] {
                        Integer.valueOf(usercount.total)
                    }));
                    if (usercount.owner > 0)
                    {
                        stringbuilder.append(s(0x7f0a0072, new Object[] {
                            Integer.valueOf(usercount.owner)
                        }));
                    }
                    if (usercount.admin > 0)
                    {
                        stringbuilder.append(s(0x7f0a0073, new Object[] {
                            Integer.valueOf(usercount.admin)
                        }));
                    }
                    if (usercount.op > 0)
                    {
                        stringbuilder.append(s(0x7f0a0074, new Object[] {
                            Integer.valueOf(usercount.op)
                        }));
                    }
                    if (usercount.hop > 0)
                    {
                        stringbuilder.append(s(0x7f0a0075, new Object[] {
                            Integer.valueOf(usercount.hop)
                        }));
                    }
                    if (usercount.voice > 0)
                    {
                        stringbuilder.append(s(0x7f0a0076, new Object[] {
                            Integer.valueOf(usercount.voice)
                        }));
                    }
                    stringbuilder.append(s(0x7f0a0077, new Object[] {
                        Integer.valueOf(usercount.normal)
                    }));
                    int i = stringbuilder.length();
                    if (usercount.total > 0)
                    {
                        int j = mColours.getColourForEvent(0x7f0b0028);
                        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), stringbuilder);
                        Utils.addColour(flag, spannablestringbuilder, j, 0, i);
                        sendMessage(chan, spannablestringbuilder, 1);
                    }
                    return;
                    exception;
                    this;
                    JVM INSTR monitorexit ;
                    throw exception;
                }

            
            {
                this$1 = final_parserimpl;
                chan = String.this;
                super();
            }
            }).start();
        }

        private boolean shouldNotify()
        {
            return ((KeyguardManager)mService.getSystemService("keyguard")).inKeyguardRestrictedInputMode() || !mMessageSender.haveUi() || !mService.screenOn;
        }

        public boolean isChannelPrefix(char c)
        {
            return Utils.isChannelPrefix(c);
        }

        public boolean isStatusPrefix(char c)
        {
            net.andchat.donate.Backend.Sessions.SessionManager.StatusMap statusmap = pSessionManager.getStatusMap();
            if (statusmap == null)
            {
                return false;
            } else
            {
                return Utils.isStatusPrefix(statusmap, c);
            }
        }

        public void on001(String s1)
        {
            int i;
            if (pNick != null)
            {
                pSessionManager.updateNick(pNick, s1, pSessionManager.getSessionList());
            }
            pNick = s1;
            sendFlaggedMessage(3, s1);
            buildHilightRegexp(Utils.escape(s1));
            boolean flag2 = Utils.isBitSet(pOptions.getAuthModes(), net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_NICKSERV);
            String s2 = pOptions.getAutojoinList();
            StringBuilder stringbuilder = new StringBuilder();
            if (flag2)
            {
                i = mColours.getColourForEvent(0x7f0b002c);
                Object obj = s(0x7f0a0053);
                sendMessage("Status", Utils.addColour(ServerConnection.shouldShowTimeStamps(), Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ((CharSequence) (obj))), i, 0, ((String) (obj)).length()), 1);
                obj = pOptions.getNickservPassword();
                stringbuilder.append("PRIVMSG NickServ :identify ");
                stringbuilder.append(((String) (obj))).append("\r\n");
                writeToServerInternal(stringbuilder.toString());
                stringbuilder.setLength(0);
                Object obj1;
                Object obj2;
                Pattern pattern;
                String as[];
                int j;
                boolean flag1;
                int k;
                int l;
                try
                {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedexception) { }
            }
            obj2 = pOptions.getAutorunList();
            if (obj2 != null && ((String) (obj2)).length() > 1)
            {
                flag1 = true;
            } else
            {
                flag1 = false;
            }
            if (!flag1) goto _L2; else goto _L1
_L1:
            pattern = Pattern.compile("\\$nick");
            l = "sleep ".length();
            if (((String) (obj2)).indexOf("\n") == -1) goto _L4; else goto _L3
_L3:
            as = ((String) (obj2)).split("\n");
            j = as.length;
            i = 0;
_L12:
            if (i < j) goto _L5; else goto _L2
_L2:
            if (s2 != null && s2.length() > 0)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            if (flag1 && i != 0)
            {
                try
                {
                    Thread.sleep(1000L);
                }
                // Misplaced declaration of an exception variable
                catch (String s1) { }
            }
            if (i != 0)
            {
                stringbuilder.setLength(0);
                stringbuilder.append("/j ").append(s2);
                CommandParser.handleCommand(stringbuilder.toString(), ServerConnection.this);
            }
            if (!checkFlagActive(4)) goto _L7; else goto _L6
_L6:
            removeServerConnectionFlag(4);
            s1 = pSessionManager.getSessionList();
            j = s1.length;
            if (j <= 0) goto _L7; else goto _L8
_L8:
            if (i != 0 || flag1)
            {
                try
                {
                    Thread.sleep(250L);
                }
                catch (InterruptedException interruptedexception1) { }
            }
            stringbuilder.setLength(0);
            stringbuilder.append("JOIN ");
            i = j - 1;
            if (i >= 0) goto _L9; else goto _L7
_L7:
            return;
_L5:
            obj2 = as[i];
            if (!TextUtils.isEmpty(((CharSequence) (obj2)))) goto _L11; else goto _L10
_L10:
            i++;
              goto _L12
_L11:
            if (!((String) (obj2)).startsWith("sleep "))
            {
                break MISSING_BLOCK_LABEL_528;
            }
            k = Utils.parseInt(((String) (obj2)).substring(l), -1);
label0:
            {
                if (k != -1)
                {
                    break label0;
                }
                StringIndexOutOfBoundsException stringindexoutofboundsexception;
                boolean flag;
                try
                {
                    Log.w("ServerConnection", "sleep value not an int, skipping");
                }
                catch (InterruptedException interruptedexception2) { }
                // Misplaced declaration of an exception variable
                catch (Object obj1) { }
            }
              goto _L10
            Thread.sleep(k);
              goto _L10
            obj1 = obj2;
            if (((String) (obj2)).charAt(0) != '/')
            {
                obj1 = stringbuilder.append("/").append(((String) (obj2))).toString();
            }
            obj2 = pattern.matcher(((CharSequence) (obj1)));
            if (((Matcher) (obj2)).find())
            {
                obj1 = ((Matcher) (obj2)).replaceAll(s1);
            }
            CommandParser.handleCommand(((String) (obj1)), ServerConnection.this);
            stringbuilder.setLength(0);
              goto _L10
_L4:
            k = 1;
            flag = true;
            i = 1;
            if (!((String) (obj2)).startsWith("sleep ")) goto _L14; else goto _L13
_L13:
            i = k;
            l = Utils.parseInt(((String) (obj2)).substring(l), -1);
            i = 0;
            flag = false;
            k = 0;
            if (l == -1) goto _L16; else goto _L15
_L15:
            Thread.sleep(l);
            i = k;
_L14:
            if (i != 0)
            {
                obj1 = obj2;
                if (((String) (obj2)).charAt(0) != '/')
                {
                    obj1 = stringbuilder.append("/").append(((String) (obj2))).toString();
                }
                obj2 = pattern.matcher(((CharSequence) (obj1)));
                if (((Matcher) (obj2)).find())
                {
                    obj1 = ((Matcher) (obj2)).replaceAll(s1);
                }
                CommandParser.handleCommand(((String) (obj1)), ServerConnection.this);
            }
              goto _L2
_L16:
            Log.w("ServerConnection", "sleep value not an int, skipping");
            i = k;
              goto _L14
            obj1;
              goto _L14
_L9:
            obj1 = s1[i];
            if (((String) (obj1)).length() != 0 && Utils.isChannelPrefix(((String) (obj1)).charAt(0)))
            {
                obj2 = pSessionManager.getKey(((String) (obj1)));
                stringbuilder.append(((String) (obj1)));
                if (obj2 != null)
                {
                    stringbuilder.append(" ").append(((String) (obj2)));
                }
                writeToServerInternal(stringbuilder.append("\r\n").toString());
                stringbuilder.setLength(5);
            }
            i--;
            break MISSING_BLOCK_LABEL_441;
            stringindexoutofboundsexception;
            i = ((flag) ? 1 : 0);
              goto _L14
        }

        public void on221(String s1)
        {
            s1 = s(0x7f0a0069, new Object[] {
                s1
            });
            sendMessage(getCurrentSession().getSessionName(), Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1), 1);
        }

        public void on324(String s1, String s2)
        {
            boolean flag = ServerConnection.shouldShowTimeStamps();
            if (pSessionManager.haveSessionFor(s1))
            {
                pSessionManager.getMetadata(s1).modes = s2;
            }
            String s3 = s(0x7f0a006e, new Object[] {
                s2
            });
            int i = mColours.getColourForEvent(0x7f0b0017);
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s3);
            Utils.addColour(flag, spannablestringbuilder, i, 0, s3.length());
            pSessionManager.checkForModes(s1, s2, pNick);
            sendMessage(s1, spannablestringbuilder, 1);
        }

        public void on329(String s1, long l)
        {
            boolean flag = ServerConnection.shouldShowTimeStamps();
            if (pSessionManager.haveSessionFor(s1))
            {
                pSessionManager.getMetadata(s1).creationTime = l;
            }
            String s2 = s(0x7f0a006d, new Object[] {
                ServerConnection.format(l)
            });
            int i = mColours.getColourForEvent(0x7f0b0018);
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            Utils.addColour(flag, spannablestringbuilder, i, 0, s2.length());
            sendMessage(s1, spannablestringbuilder, 1);
        }

        public void on332(String s1, String s2, boolean flag)
        {
            boolean flag1 = ServerConnection.shouldShowTimeStamps();
            String s3;
            SpannableStringBuilder spannablestringbuilder;
            int i;
            if (flag)
            {
                s3 = s(0x7f0a006b, new Object[] {
                    s1, s2
                });
            } else
            {
                s3 = s(0x7f0a006a, new Object[] {
                    s1
                });
            }
            i = mColours.getColourForEvent(0x7f0b0016);
            spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s3);
            Utils.addColour(flag1, spannablestringbuilder, i, 0, s3.length());
            if (flag && s2.length() >= 4)
            {
                Utils.addLinks(spannablestringbuilder);
            }
            sendMessage(s1, spannablestringbuilder, 1);
        }

        public void on333(String s1, String s2, long l)
        {
            boolean flag = ServerConnection.shouldShowTimeStamps();
            s2 = s(0x7f0a006c, new Object[] {
                s2, ServerConnection.format(l)
            });
            int i = mColours.getColourForEvent(0x7f0b0016);
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            Utils.addColour(flag, spannablestringbuilder, i, 0, s2.length());
            sendMessage(s1, spannablestringbuilder, 1);
        }

        public void on433()
        {
            Object obj;
            String s1;
            s1 = "";
            obj = null;
            m433Count;
            JVM INSTR tableswitch 0 2: default 36
        //                       0 107
        //                       1 151
        //                       2 195;
               goto _L1 _L2 _L3 _L4
_L1:
            sendMessage("Status", Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ((CharSequence) (obj))), 1);
            if (s1.length() > 0)
            {
                writeToServerInternal((new StringBuilder("NICK ")).append(s1).append("\r\n").toString());
                pNick = s1;
                sendFlaggedMessage(3, s1);
            }
            return;
_L2:
            s1 = pOptions.getNick(2);
            obj = s(0x7f0a006f, new Object[] {
                s1
            });
            m433Count = m433Count + 1;
            continue; /* Loop/switch isn't completed */
_L3:
            s1 = pOptions.getNick(3);
            obj = s(0x7f0a006f, new Object[] {
                s1
            });
            m433Count = m433Count + 1;
            continue; /* Loop/switch isn't completed */
_L4:
            obj = s(0x7f0a0070);
            if (true) goto _L1; else goto _L5
_L5:
        }

        public void onChannelPrivmsg(String s1, String s2, String s3, String s4, String s5, int i)
        {
            StringBuilder stringbuilder;
            boolean flag;
            int k;
            int l;
            boolean flag1;
            k = 0;
            flag = false;
            l = s2.length();
            flag1 = ServerConnection.shouldShowTimeStamps();
            stringbuilder = reusableSB;
            stringbuilder.setLength(0);
            i;
            JVM INSTR tableswitch 0 2: default 56
        //                       0 446
        //                       1 57
        //                       2 692;
               goto _L1 _L2 _L3 _L4
_L1:
            return;
_L3:
            boolean flag2;
            flag2 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_SHOW_IGNORED);
            i = 0;
            if (!mIgnores.shouldIgnore(s3, s4, 1))
            {
                break; /* Loop/switch isn't completed */
            }
            if (!flag2)
            {
                continue; /* Loop/switch isn't completed */
            }
            i = 1;
            break; /* Loop/switch isn't completed */
            if (true) goto _L1; else goto _L5
_L5:
            s3 = pSessionManager.getNickStatus(s2, s1);
            int k1 = pSessionManager.getNickColor(s2, s1);
            stringbuilder.ensureCapacity(s2.length() + 2 + 2 + s5.length() + 1);
            stringbuilder.append("<").append(s3).append(s2).append("> ").append(s5);
            s4 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), stringbuilder);
            k = s3.length() + l;
            if (pHilightMatcher.reset(s5).find())
            {
                flag = true;
                Utils.addColourAndBold(flag1, s4, mColours.getColourForEvent(0x7f0b0014), 1, k + 1);
                int i1 = mColours.getColourForEvent(0x7f0b0013);
                Utils.addColour(flag1, s4, i1, 0, 1);
                Utils.addColour(flag1, s4, i1, k + 1, k + 2);
            } else
            {
                int j1 = mColours.getColourForEvent(0x7f0b0013);
                Utils.addColour(flag1, s4, j1, 0, 1);
                Utils.addColour(flag1, s4, j1, k + 1, k + 2);
                Utils.addColour(flag1, s4, k1, 1, s3.length() + 1 + s2.length());
            }
            if (s5.length() >= 4)
            {
                Utils.addLinks(s4);
            }
            if (flag && shouldNotify())
            {
                makeNotif(s4.toString(), s1, s1, 1);
            }
            if (flag2 && i != 0)
            {
                s4.append(" [IGNORED]");
            }
            s2 = ServerConnection.this;
            if (flag)
            {
                i = 3;
            } else
            {
                i = 2;
            }
            s2.sendMessage(s1, s4, i);
            return;
_L2:
            flag2 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_SHOW_IGNORED);
            i = 0;
            if (!mIgnores.shouldIgnore(s3, s4, 1))
            {
                break; /* Loop/switch isn't completed */
            }
            if (!flag2)
            {
                continue; /* Loop/switch isn't completed */
            }
            i = 1;
            break; /* Loop/switch isn't completed */
            if (true) goto _L1; else goto _L6
_L6:
            stringbuilder.append("* ").append(s2).append(" ").append(s5);
            s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), stringbuilder);
            int j;
            if (pHilightMatcher.reset(s5).find())
            {
                k = mColours.getColourForEvent(0x7f0b0014);
                j = 1;
                Utils.addColourAndBold(flag1, s3, k, 2, s2.length() + 2);
            } else
            {
                Utils.addColour(flag1, s3, pSessionManager.getNickColor(s2, s1), 2, s2.length() + 2);
                j = k;
            }
            if (s5.length() >= 4)
            {
                Utils.addLinks(s3);
            }
            if (j != 0 && shouldNotify())
            {
                makeNotif(s3.toString(), s1, s1, 1);
            }
            if (flag2 && i != 0)
            {
                s3.append(" [IGNORED]");
            }
            s2 = ServerConnection.this;
            if (j != 0)
            {
                i = 3;
            } else
            {
                i = 2;
            }
            s2.sendMessage(s1, s3, i);
            return;
_L4:
            s3 = s2;
            if (s2 == null)
            {
                s3 = "*";
            }
            stringbuilder.append(s3).append(" ").append(s5);
            s2 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), stringbuilder);
            if (s5.length() >= 4)
            {
                Utils.addLinks(s2);
            }
            sendMessage(s1, s2, 2);
            return;
        }

        public void onCtcpRequest(String s1, String s2, String s3, String s4)
        {
label0:
            {
                boolean flag1 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_SHOW_IGNORED);
                boolean flag = false;
                if (mIgnores.shouldIgnore(s2, s3, 8))
                {
                    if (!flag1)
                    {
                        break label0;
                    }
                    flag = true;
                }
                boolean flag2 = ServerConnection.shouldShowTimeStamps();
                s2 = s4.toUpperCase();
                s3 = s(0x7f0a0066, new Object[] {
                    s2, s1
                });
                int i = mColours.getColourForEvent(0x7f0b0027);
                s4 = Utils.createMessage(flag2, s3);
                Utils.addColour(flag2, s4, i, 0, s3.length());
                if (flag && flag1)
                {
                    s4.append(" [IGNORED]");
                }
                sendMessage(getCurrentSession().getSessionName(), s4, 1);
                if (s2.equals("VERSION"))
                {
                    s2 = new StringBuilder();
                    writeToServerInternal(s2.append("NOTICE ").append(s1).append(" :\001VERSION ").append("AndChat").append(" ").append("1.4.3.2").append(" http://www.andchat.net\001\r\n").toString());
                }
            }
        }

        public void onError(CharSequence charsequence)
        {
            sendMessage("Status", Utils.createMessage(ServerConnection.shouldShowTimeStamps(), charsequence), 1);
        }

        public void onISupport(String s1)
        {
            int i = s1.indexOf("PREFIX=");
            if (i == -1)
            {
                return;
            } else
            {
                String s2 = s1.substring(i + 8, s1.indexOf(" ", i));
                i = s2.indexOf(')');
                s1 = s2.substring(0, i);
                s2 = s2.substring(i + 1);
                pSessionManager.buildStatusMap(s1, s2);
                return;
            }
        }

        public void onInvite(String s1, String s2, String s3)
        {
            boolean flag = ServerConnection.shouldShowTimeStamps();
            s1 = s(0x7f0a0057, new Object[] {
                s1, s2, s3
            });
            int k = s1.length();
            s2 = s(0x7f0a0058);
            int l = s2.length();
            int i = mColours.getColourForEvent(0x7f0b001b);
            s1 = (new StringBuilder(s1)).append(s2);
            s2 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
            Utils.addColour(flag, s2, i, 0, s1.length() - l);
            int j = k + 1;
            i = j;
            if (flag)
            {
                i = j + (ServerConnection.sTimeLength + 3);
            }
            k += l;
            j = k;
            if (flag)
            {
                j = k + (ServerConnection.sTimeLength + 3);
            }
            s2.setSpan(new net.andchat.donate.Misc.Utils.ChannelSpan(s3), i, j, 33);
            sendMessage(getCurrentSession().getSessionName(), s2, 1);
        }

        public void onInviteSent(String s1, String s2)
        {
            boolean flag = ServerConnection.shouldShowTimeStamps();
            s2 = s(0x7f0a0056, new Object[] {
                s2, s1
            });
            int i = mColours.getColourForEvent(0x7f0b001c);
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            Utils.addColour(flag, spannablestringbuilder, i, 0, s2.length());
            sendMessage(s1, spannablestringbuilder, 1);
        }

        public void onKill(String s1, String s2, String s3, String s4)
        {
            if (s1.equals(pNick))
            {
                boolean flag = ServerConnection.shouldShowTimeStamps();
                s1 = s(0x7f0a0067, new Object[] {
                    s2, s4
                });
                int i = mColours.getColourForEvent(0x7f0b002b);
                s2 = Utils.createMessage(flag, s1);
                Utils.addColour(flag, s2, i, 0, s1.length());
                sendToAll(s2, 1);
                deactivateAllAndUpdateUi(false);
                return;
            } else
            {
                onUserQuit(s1, null, s(0x7f0a0068, new Object[] {
                    s2, s4
                }));
                return;
            }
        }

        public void onMessageReceived(String s1)
        {
            if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_LOG_ALL_MESSAGES))
            {
                logMessage("_DEBUG_", s1, true);
            }
        }

        public void onModeChanged(String s1, String s2, String s3)
        {
            if (s3 == null)
            {
                s3 = s(0x7f0a005a, new Object[] {
                    s2, s1
                });
                sendMessage("Status", Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s3), 1);
            } else
            {
                boolean flag = ServerConnection.shouldShowTimeStamps();
                s3 = s(0x7f0a005b, new Object[] {
                    s2, s3
                });
                int i = mColours.getColourForEvent(0x7f0b001d);
                SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s3);
                Utils.addColour(flag, spannablestringbuilder, i, 0, s3.length());
                sendMessage(s1, spannablestringbuilder, 1);
            }
            pSessionManager.checkForModes(s1, s2, pNick);
        }

        public void onMotdEnd(String s1)
        {
            if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_SKIP_MOTD) || checkFlagActive(2))
            {
                SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
                if (s1.length() >= 4)
                {
                    Utils.addLinks(spannablestringbuilder);
                }
                sendMessage("Status", spannablestringbuilder, 1);
                (new Thread(removeMotdFlag)).start();
            }
        }

        public void onMotdLine(String s1)
        {
            if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_SKIP_MOTD) || checkFlagActive(2))
            {
                SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
                if (s1.length() >= 4)
                {
                    Utils.addLinks(spannablestringbuilder);
                }
                sendMessage("Status", spannablestringbuilder, 1);
            }
        }

        public void onMotdStart(String s1)
        {
            if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_SKIP_MOTD) || checkFlagActive(2))
            {
                SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
                if (s1.length() >= 4)
                {
                    Utils.addLinks(spannablestringbuilder);
                }
                sendMessage("Status", spannablestringbuilder, 1);
                return;
            } else
            {
                boolean flag = ServerConnection.shouldShowTimeStamps();
                int i = mColours.getColourForEvent(0x7f0b002c);
                s1 = s(0x7f0a004f);
                SpannableStringBuilder spannablestringbuilder1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
                Utils.addColour(flag, spannablestringbuilder1, i, 0, s1.length());
                sendMessage("Status", spannablestringbuilder1, 1);
                return;
            }
        }

        public void onNamesList(final String channel, String s1)
        {
            if (!checkFlagActive(1) && pSessionManager.haveSessionFor(channel))
            {
                (s1. new Thread() {

                    final ParserImpl this$1;
                    private final String val$channel;
                    private final String val$names;

                    public void run()
                    {
                        pSessionManager.addNewNames(pNick, channel, names);
                    }

            
            {
                this$1 = final_parserimpl;
                channel = s1;
                names = String.this;
                super();
            }
                }).start();
                return;
            }
            Object obj = new StringBuilder(s(0x7f0a0059, new Object[] {
                channel
            }));
            if (s1.length() > 0)
            {
                ((StringBuilder) (obj)).append(" ").append(s1);
            }
            s1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ((CharSequence) (obj)));
            obj = ServerConnection.this;
            if (!pSessionManager.haveSessionFor(channel))
            {
                channel = "Status";
            }
            ((ServerConnection) (obj)).sendMessage(channel, s1, 1);
        }

        public void onNetworkName(String s1)
        {
        }

        public void onNickChanged(String s1, String s2, String s3, String s4)
        {
            boolean flag = s1.equals(pNick);
            if (flag)
            {
                pNick = s2;
                buildHilightRegexp(Utils.escape(s2));
                sendFlaggedMessage(3, s2);
            }
            mIgnores.nickChanged(s1, s2, s3, s4);
            s4 = pSessionManager.getUserSessions(s1);
            SpannableStringBuilder spannablestringbuilder;
            int i;
            boolean flag1;
            if (flag)
            {
                s3 = s(0x7f0a0064, new Object[] {
                    s2
                });
            } else
            {
                s3 = s(0x7f0a0065, new Object[] {
                    s1, s2
                });
            }
            flag1 = ServerConnection.shouldShowTimeStamps();
            spannablestringbuilder = Utils.createMessage(flag1, s3);
            if (flag)
            {
                i = mColours.getColourForEvent(0x7f0b0025);
            } else
            {
                i = mColours.getColourForEvent(0x7f0b0026);
            }
            Utils.addColour(flag1, spannablestringbuilder, i, 0, s3.length());
            i = s4.length - 1;
            do
            {
                if (i < 0)
                {
                    pSessionManager.updateNick(s1, s2, s4);
                    if (flag)
                    {
                        sendMessage("Status", spannablestringbuilder, 1);
                    }
                    if (pSessionManager.updateSessionName(s1, s2))
                    {
                        sendFlaggedMessage(6, null);
                    }
                    if (!Utils.isChannelPrefix(s1.charAt(0)) && pSessionManager.haveSessionFor(s2))
                    {
                        s1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s3);
                        Utils.addColour(flag1, s1, mColours.getColourForEvent(0x7f0b0026), 0, s3.length());
                        sendMessage(s2, s1, 1);
                    }
                    return;
                }
                String s5 = s4[i];
                sendMessage(s5, new SpannableString(spannablestringbuilder), 1);
                i--;
            } while (true);
        }

        public void onNotice(String s1, String s2, String s3, String s4, String s5, String s6)
        {
            if (s2 != null) goto _L2; else goto _L1
_L1:
            s2 = null;
            s1 = s2;
            if (s6 != null)
            {
                s1 = s2;
                if (Utils.isChannelPrefix(s6.charAt(0)))
                {
                    s1 = s6;
                }
            }
            s3 = ServerConnection.this;
            s2 = s1;
            if (s1 == null)
            {
                s2 = "Status";
            }
            s3.sendMessage(s2, Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s5), 1);
_L4:
            return;
_L2:
            boolean flag;
            boolean flag1;
            if ((s1 == null || s1 != null && s1.equals(pNick)) && s6 == null)
            {
                flag1 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_SHOW_IGNORED);
                flag = false;
                if (!mIgnores.shouldIgnore(s3, s4, 4))
                {
                    break; /* Loop/switch isn't completed */
                }
                if (!flag1)
                {
                    continue; /* Loop/switch isn't completed */
                }
                flag = true;
                break; /* Loop/switch isn't completed */
            }
            break MISSING_BLOCK_LABEL_318;
            if (true) goto _L4; else goto _L3
_L3:
            int j = s5.length();
            int i = s2.length();
            boolean flag2 = ServerConnection.shouldShowTimeStamps();
            s1 = new StringBuilder(j + 5 + i);
            s1.append("-").append(s2).append("-: ").append(s5);
            j = mColours.getColourForEvent(0x7f0b0019);
            s1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1);
            Utils.addColour(flag2, s1, j, 0, 1);
            Utils.addColour(flag2, s1, j, i + 1, i + 4);
            if (s5.length() >= 4)
            {
                Utils.addLinks(s1);
            }
            if (shouldNotify())
            {
                makeNotif(s1.toString(), s2, getCurrentSession().getSessionName(), 16);
            }
            if (flag && flag1)
            {
                s1.append(" [IGNORED]");
            }
            sendMessage(getCurrentSession().getSessionName(), s1, 2);
            return;
            flag1 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_SHOW_IGNORED);
            flag = false;
            if (!mIgnores.shouldIgnore(s3, s4, 4))
            {
                break; /* Loop/switch isn't completed */
            }
            if (!flag1)
            {
                continue; /* Loop/switch isn't completed */
            }
            flag = true;
            break; /* Loop/switch isn't completed */
            if (true) goto _L4; else goto _L5
_L5:
            boolean flag3 = ServerConnection.shouldShowTimeStamps();
            int j1 = s5.length();
            int l = s2.length();
            int i1 = s1.length();
            byte byte0;
            int k;
            if (s6 != null)
            {
                byte0 = 1;
            } else
            {
                byte0 = 0;
            }
            if (byte0 != 0)
            {
                k = s6.length();
            } else
            {
                k = 0;
            }
            s3 = new StringBuilder(k + (l + 5 + i1 + j1));
            s3.append("-").append(s2).append("/");
            if (byte0 != 0)
            {
                s3.append(s6);
            }
            s3.append(s1).append("-: ").append(s5);
            k = mColours.getColourForEvent(0x7f0b001a);
            s4 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s3);
            Utils.addColour(flag3, s4, k, 1, s3.length() - j1 - 3);
            j1 = mColours.getColourForEvent(0x7f0b0019);
            Utils.addColour(flag3, s4, j1, 0, 1);
            if (byte0 != 0)
            {
                k = 3;
            } else
            {
                k = 2;
            }
            k = l + i1 + k;
            if (byte0 != 0)
            {
                byte0 = 3;
            } else
            {
                byte0 = 2;
            }
            Utils.addColour(flag3, s4, j1, k, k + byte0);
            if (s5.length() >= 4)
            {
                Utils.addLinks(s4);
            }
            if (shouldNotify())
            {
                makeNotif(s4.toString(), s2, s1, 4);
            }
            if (flag && flag1)
            {
                s4.append(" [IGNORED]");
            }
            sendMessage(s1, s4, 2);
            return;
        }

        public void onNumericMessage(String s1, CharSequence charsequence, int i)
        {
            boolean flag1;
            boolean flag2;
            flag1 = false;
            flag2 = false;
            i;
            JVM INSTR lookupswitch 17: default 152
        //                       315: 220
        //                       352: 269
        //                       366: 518
        //                       401: 783
        //                       471: 658
        //                       472: 680
        //                       473: 658
        //                       474: 658
        //                       475: 658
        //                       476: 680
        //                       477: 680
        //                       478: 680
        //                       482: 680
        //                       900: 859
        //                       903: 859
        //                       904: 859
        //                       905: 859;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L6 _L6 _L6 _L7 _L7 _L7 _L7 _L8 _L8 _L8 _L8
_L1:
            int j;
            boolean flag;
            flag = flag2;
            j = ((flag1) ? 1 : 0);
_L19:
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), charsequence);
            if (flag)
            {
                Utils.addColour(ServerConnection.shouldShowTimeStamps(), spannablestringbuilder, j, 0, charsequence.length());
            }
            charsequence = ServerConnection.this;
            if (s1 == null || s1.length() == 0)
            {
                s1 = "Status";
            }
            charsequence.sendMessage(s1, spannablestringbuilder, 1);
_L17:
            return;
_L2:
            if (checkFlagActive(4))
            {
                removeServerConnectionFlag(4);
                s1 = charsequence.toString();
                s1 = s1.substring(0, s1.indexOf(' '));
                j = ((flag1) ? 1 : 0);
                flag = flag2;
                continue; /* Loop/switch isn't completed */
            }
            continue; /* Loop/switch isn't completed */
_L3:
            String s2;
            String s5;
            String s6;
            String s8;
            s8 = charsequence.toString();
            s2 = null;
            s5 = null;
            s6 = null;
            i = s8.indexOf(' ');
            j = 0;
_L15:
            if (j >= 4)
            {
                pSessionManager.setIdentAndHost(s1, s2, s6, s5);
                if (checkFlagActive(4))
                {
                    j = ((flag1) ? 1 : 0);
                    flag = flag2;
                    continue; /* Loop/switch isn't completed */
                }
                continue; /* Loop/switch isn't completed */
            }
            j;
            JVM INSTR tableswitch 0 3: default 376
        //                       0 385
        //                       1 421
        //                       2 455
        //                       3 469;
               goto _L9 _L10 _L11 _L12 _L13
_L9:
            break; /* Loop/switch isn't completed */
_L10:
            break; /* Loop/switch isn't completed */
_L16:
            j++;
            if (true) goto _L15; else goto _L14
_L14:
            s1 = s8.substring(0, i);
            i = s8.indexOf(' ', i + 1);
            s6 = s8.substring(s1.length() + 1, i);
              goto _L16
_L11:
            s5 = s8.substring(i + 1, s8.indexOf(' ', i + 1));
            i = s8.indexOf(' ', i + 1);
              goto _L16
_L12:
            i = s8.indexOf(' ', i + 1);
              goto _L16
_L13:
            String s7 = s8.substring(i + 1, s8.indexOf(' ', i + 1));
            s2 = s7;
              goto _L16
            StringIndexOutOfBoundsException stringindexoutofboundsexception;
            stringindexoutofboundsexception;
            Log.e("ServerConnection", s8, stringindexoutofboundsexception);
            onUnparsableMessage(s8);
              goto _L16
_L4:
            s1 = charsequence.toString();
            s1 = s1.substring(0, s1.indexOf(' ', 1)).trim();
            boolean flag3 = checkFlagActive(1);
            if (flag3 || pSessionManager.haveSessionFor(s1))
            {
                sendCount(s1);
                writeToServerInternal((new StringBuilder("WHO ")).append(s1).append("\r\n").toString());
                if (flag3)
                {
                    (new Thread(removeNameFlag)).start();
                    return;
                }
            } else
            {
                if (!pSessionManager.haveSessionFor(s1))
                {
                    s1 = "Status";
                }
                j = ((flag1) ? 1 : 0);
                flag = flag2;
                continue; /* Loop/switch isn't completed */
            }
            if (true) goto _L17; else goto _L6
_L6:
            s1 = getCurrentSession().getSessionName();
            j = ((flag1) ? 1 : 0);
            flag = flag2;
            continue; /* Loop/switch isn't completed */
_L7:
            String s3 = charsequence.toString();
            j = s3.indexOf(' ', 1);
            if (j != -1)
            {
                s3 = s3.substring(0, j);
            }
            j = ((flag1) ? 1 : 0);
            flag = flag2;
            s1 = s3;
            if (i == 477)
            {
                j = ((flag1) ? 1 : 0);
                flag = flag2;
                s1 = s3;
                if (!pSessionManager.haveSessionFor(s3))
                {
                    s1 = getCurrentSession().getSessionName();
                    j = ((flag1) ? 1 : 0);
                    flag = flag2;
                }
            }
            continue; /* Loop/switch isn't completed */
_L5:
            String s4;
            if (charsequence instanceof String)
            {
                s4 = (String)charsequence;
            } else
            {
                s4 = charsequence.toString();
            }
            s4 = s4.substring(0, s4.indexOf(' '));
            j = ((flag1) ? 1 : 0);
            flag = flag2;
            if (pSessionManager.haveSessionFor(s4))
            {
                s1 = s4;
                j = ((flag1) ? 1 : 0);
                flag = flag2;
            }
            continue; /* Loop/switch isn't completed */
_L8:
            j = mColours.getColourForEvent(0x7f0b002c);
            flag = true;
            writeToServerInternal("CAP END\r\n");
            if (true) goto _L19; else goto _L18
_L18:
        }

        public void onPing(String s1)
        {
            writeToServerInternal((new StringBuilder("PONG :")).append(s1).append("\r\n").toString());
        }

        public void onPong(String s1)
        {
            if (!checkFlagActive(8))
            {
                return;
            } else
            {
                removeServerConnectionFlag(8);
                s1 = (new StringBuilder("Pong: ")).append(s1).toString();
                sendMessage("Status", Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s1), 1);
                return;
            }
        }

        public void onPrivatePrivmsg(String s1, String s2, String s3, String s4, int i)
        {
            boolean flag;
            boolean flag1;
            flag1 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_SHOW_IGNORED);
            flag = false;
            if (!mIgnores.shouldIgnore(s2, s3, 2)) goto _L2; else goto _L1
_L1:
            if (!flag1) goto _L4; else goto _L3
_L3:
            flag = true;
_L2:
            if (!pSessionManager.haveSessionFor(s1))
            {
                pSessionManager.addSession(s1, pNick, 2);
                sendFlaggedMessage(2, s1);
                boolean flag2 = ServerConnection.shouldShowTimeStamps();
                s2 = new SpannableStringBuilder();
                s2.append(Utils.getPmStart(mService, s1, flag2));
                if (flag && flag1)
                {
                    s2.append(" [IGNORED]");
                }
                sendMessage(s1, s2, 2);
            }
            pSessionManager.setMarked(s1, true);
            i;
            JVM INSTR tableswitch 0 1: default 180
        //                       0 349
        //                       1 181;
               goto _L4 _L5 _L6
_L4:
            return;
_L6:
            i = s1.length();
            int j = s4.length();
            boolean flag3 = ServerConnection.shouldShowTimeStamps();
            s2 = new StringBuilder(i + 4 + j);
            s2.append("<").append(s1).append("> ").append(s4);
            s2 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            int k = mColours.getColourForEvent(0x7f0b0013);
            Utils.addColour(flag3, s2, k, 0, 1);
            Utils.addColour(flag3, s2, k, i + 1, i + 2);
            if (j >= 4)
            {
                Utils.addLinks(s2);
            }
            if (shouldNotify())
            {
                makeNotif(s2.toString(), s1, s1, 2);
            }
            if (flag && flag1)
            {
                s2.append(" [IGNORED]");
            }
            sendMessage(s1, s2, 2);
            return;
_L5:
            s2 = new StringBuilder();
            s2.append("* ").append(s1).append(" ").append(s4);
            s2 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            if (s4.length() >= 4)
            {
                Utils.addLinks(s2);
            }
            if (flag && flag1)
            {
                s2.append(" [IGNORED]");
            }
            sendMessage(s1, s2, 2);
            if (shouldNotify())
            {
                makeNotif(s2.toString(), s1, s1, 2);
                return;
            }
            if (true) goto _L4; else goto _L7
_L7:
        }

        public void onSaslMessage(String s1, String s2, String as[])
        {
            int i;
            boolean flag;
            boolean flag1;
            flag1 = false;
            int j;
            if (as != null)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            if (!s1.equals("CAP")) goto _L2; else goto _L1
_L1:
            flag = flag1;
            if (s2 == null) goto _L4; else goto _L3
_L3:
            flag = flag1;
            if (i == 0) goto _L4; else goto _L5
_L5:
            flag = flag1;
            if (as.length < 2) goto _L4; else goto _L6
_L6:
            flag = flag1;
            if (!as[0].equals("ACK")) goto _L4; else goto _L7
_L7:
            j = as.length;
            flag = false;
            i = 1;
_L13:
            if (i < j) goto _L9; else goto _L8
_L8:
            if (flag)
            {
                writeToServerInternal("AUTHENTICATE PLAIN\r\n");
            }
_L4:
            if (!flag)
            {
                writeToServerInternal("CAP END\r\n");
            }
            return;
_L9:
            i;
            JVM INSTR tableswitch 1 1: default 136
        //                       1 170;
               goto _L10 _L11
_L10:
            flag1 = as[i].equals("sasl");
            flag = flag1;
            if (flag1) goto _L8; else goto _L12
_L12:
            flag = flag1;
_L15:
            i++;
              goto _L13
_L11:
            flag1 = as[i].equals(":sasl");
            flag = flag1;
            if (!flag1) goto _L15; else goto _L14
_L14:
            flag = flag1;
              goto _L8
_L2:
            flag = flag1;
            if (!s1.equals("AUTHENTICATE")) goto _L4; else goto _L16
_L16:
            flag = flag1;
            if (!s2.equals("+")) goto _L4; else goto _L17
_L17:
            as = pOptions.getSASLUsername();
            s2 = pOptions.getCharset();
            s1 = (byte[])null;
            try
            {
                s1 = (new String((new StringBuilder(String.valueOf(as))).append("\0").append(as).append("\0").append(pOptions.getSASLPassword()).toString())).getBytes(s2);
            }
            // Misplaced declaration of an exception variable
            catch (String s1)
            {
                s1 = (new String((new StringBuilder(String.valueOf(as))).append("\0").append(as).append("\0").append(pOptions.getSASLPassword()).toString())).getBytes();
            }
            s2 = new String(Base64.encode(s1, 0, s1.length, 4), s2);
            s1 = s2;
_L18:
            writeToServerInternal((new StringBuilder("AUTHENTICATE ")).append(s1).toString());
            flag = true;
              goto _L4
            s2;
            s1 = new String(Base64.encode(s1, 0, s1.length, 4));
              goto _L18
        }

        public void onTopicChanged(String s1, String s2, String s3)
        {
            int i;
            boolean flag;
            if (s3 != null && s3.length() > 0)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            if (i != 0)
            {
                s2 = s(0x7f0a0055, new Object[] {
                    s3, s2
                });
            } else
            {
                s2 = s(0x7f0a0054, new Object[] {
                    s2
                });
            }
            flag = ServerConnection.shouldShowTimeStamps();
            i = mColours.getColourForEvent(0x7f0b0015);
            s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            Utils.addColour(flag, s3, i, 0, s2.length());
            Utils.addLinks(s3);
            sendMessage(s1, s3, 1);
        }

        public void onUnknownMessage(CharSequence charsequence)
        {
            charsequence = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), charsequence);
            sendMessage("Status", charsequence, 1);
        }

        public void onUnparsableMessage(String s1)
        {
            if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREF_DEBUG_LOG_UNPARSEABLE))
            {
                logMessage("_unparseable_", s1, true);
            }
            s1 = s(0x7f0a007c, new Object[] {
                s1
            });
            SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(s1);
            Utils.addColour(false, spannablestringbuilder, Colours.getInstance().getColourForEvent(0x7f0b002c), 0, s1.length());
            onUnknownMessage(spannablestringbuilder);
        }

        public void onUserJoined(String s1, String s2, String s3, String s4)
        {
            if (s2.equals(pNick))
            {
                pSessionManager.addSession(s1, s2, 1);
                sendFlaggedMessage(1, s1);
                writeToServerInternal((new StringBuilder("MODE ")).append(s1).append("\r\n").toString());
                if (!pSessionManager.getActive(s1))
                {
                    pSessionManager.setActive(s1, true);
                    sendFlaggedMessage(6, null);
                }
            } else
            {
                pSessionManager.addName(s1, s2, s3, s4);
                if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_HIDE_MESSAGES))
                {
                    boolean flag = ServerConnection.shouldShowTimeStamps();
                    s2 = s(0x7f0a005c, new Object[] {
                        s2, s4
                    });
                    int i = mColours.getColourForEvent(0x7f0b001e);
                    s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
                    Utils.addColour(flag, s3, i, 0, s2.length());
                    sendMessage(s1, s3, 1);
                    return;
                }
            }
        }

        public void onUserKicked(String s1, String s2, String s3, String s4)
        {
            pSessionManager.removeName(s1, s2);
            boolean flag = ServerConnection.shouldShowTimeStamps();
            if (s2.equals(pNick))
            {
                pSessionManager.setActive(s1, false);
                sendFlaggedMessage(6, null);
                s2 = s(0x7f0a005f, new Object[] {
                    s1, s3, s4
                });
                int i = mColours.getColourForEvent(0x7f0b0020);
                s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
                Utils.addColour(flag, s3, i, 0, s2.length());
                sendMessage(s1, s3, 2);
                pSessionManager.setActive(s1, false);
                pSessionManager.removeAllNames(s1);
                sendFlaggedMessage(5, s1);
                if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_REJOIN_ON_KICK))
                {
                    s2 = s(0x7f0a0060);
                    int j = mColours.getColourForEvent(0x7f0b0021);
                    s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
                    Utils.addColour(flag, s3, j, 0, s2.length());
                    sendMessage(s1, s3, 1);
                    pLogs.close(s1);
                    (s1. new Thread() {

                        final ParserImpl this$1;
                        private final String val$chan;
                        private final String val$key;

                        public void run()
                        {
                            StringBuilder stringbuilder;
                            try
                            {
                                Thread.sleep(5000L);
                            }
                            catch (InterruptedException interruptedexception) { }
                            try
                            {
                                stringbuilder = new StringBuilder();
                                if (key != null)
                                {
                                    stringbuilder.append("JOIN ").append(chan).append(" ").append(key).append("\r\n");
                                    writeToServerInternal(stringbuilder.toString());
                                    return;
                                }
                            }
                            catch (Exception exception)
                            {
                                return;
                            }
                            writeToServerInternal(stringbuilder.append("JOIN ").append(chan).append("\r\n").toString());
                            return;
                        }

            
            {
                this$1 = final_parserimpl;
                key = s1;
                chan = String.this;
                super();
            }
                    }).start();
                }
                return;
            }
            if (s3.equals(pNick))
            {
                s2 = s(0x7f0a0061, new Object[] {
                    s2, s1, s4
                });
                int k = mColours.getColourForEvent(0x7f0b0022);
                s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
                Utils.addColour(flag, s3, k, 0, s2.length());
                sendMessage(s1, s3, 1);
                return;
            } else
            {
                s2 = s(0x7f0a0062, new Object[] {
                    s2, s3, s4
                });
                int l = mColours.getColourForEvent(0x7f0b0023);
                s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
                Utils.addColour(flag, s3, l, 0, s2.length());
                sendMessage(s1, s3, 1);
                return;
            }
        }

        public void onUserParted(String s1, String s2, String s3, String s4)
        {
            if (s2.equals(pNick))
            {
                pLogs.close(s1);
                pSessionManager.removeSession(s1);
                sendFlaggedMessage(4, s1);
            } else
            {
                pSessionManager.removeName(s1, s2);
                if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_HIDE_MESSAGES))
                {
                    int i;
                    if (s4 != null && s4.length() > 0)
                    {
                        i = 1;
                    } else
                    {
                        i = 0;
                    }
                    if (i != 0)
                    {
                        s2 = s(0x7f0a005e, new Object[] {
                            s2, s3, s4
                        });
                    } else
                    {
                        s2 = s(0x7f0a005d, new Object[] {
                            s2, s3
                        });
                    }
                    i = mColours.getColourForEvent(0x7f0b001f);
                    s3 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
                    Utils.addColour(ServerConnection.shouldShowTimeStamps(), s3, i, 0, s2.length());
                    sendMessage(s1, s3, 1);
                    return;
                }
            }
        }

        public void onUserQuit(String s1, String s2, String s3)
        {
            int i = 0;
            if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_HIDE_MESSAGES))
            {
                pSessionManager.getQuitSessions(s1);
            } else
            {
                if (s1.equals(pNick))
                {
                    deactivateAllAndUpdateUi(false);
                    return;
                }
                s2 = s(0x7f0a0063, new Object[] {
                    s1, s3
                });
                s1 = pSessionManager.getQuitSessions(s1);
                boolean flag = ServerConnection.shouldShowTimeStamps();
                int j = mColours.getColourForEvent(0x7f0b0024);
                s3 = Utils.createMessage(flag, s2);
                Utils.addColour(flag, s3, j, 0, s2.length());
                j = s1.length;
                while (i < j) 
                {
                    s2 = s1[i];
                    sendMessage(s2, new SpannableString(s3), 1);
                    i++;
                }
            }
        }

        public void onWallops(String s1, String s2, String s3)
        {
            boolean flag = ServerConnection.shouldShowTimeStamps();
            int i = s1.length();
            int j = s3.length();
            s2 = new StringBuilder(i + j + 12);
            s2.append("-").append(s1).append("/").append("Wallops-: ").append(s3);
            int k = s2.length();
            int l = Colours.getInstance().getColourForEvent(0x7f0b001a);
            s1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), s2);
            Utils.addColour(flag, s1, l, 0, k - j - 2);
            l = mColours.getColourForEvent(0x7f0b0013);
            Utils.addColour(flag, s1, l, 0, 1);
            Utils.addColour(flag, s1, l, i + 9, k - j - 2);
            sendMessage(getCurrentSession().getSessionName(), s1, 1);
        }

        protected void reset()
        {
            super.reset();
            m433Count = 0;
            reusableSB.setLength(0);
        }

        public String stripColours(String s1)
        {
            return mColourRemover.reset(s1).replaceAll("");
        }


        private ParserImpl()
        {
            this$0 = ServerConnection.this;
            super();
            m433Count = 0;
            reusableSB = new StringBuilder();
        }

        ParserImpl(ParserImpl parserimpl)
        {
            this();
        }
    }

    private final class TrustAllSSL
        implements X509TrustManager
    {

        final ServerConnection this$0;

        public void checkClientTrusted(X509Certificate ax509certificate[], String s1)
        {
        }

        public void checkServerTrusted(X509Certificate ax509certificate[], String s1)
        {
            s1 = ax509certificate[0];
            ax509certificate = new StringBuilder(300);
            String s2 = s(0x7f0a0044);
            ax509certificate.append(s2);
            ax509certificate.append(s(0x7f0a0045, new Object[] {
                s1.getSubjectDN().toString(), s1.getIssuerDN().toString(), s1.getNotBefore().toGMTString(), s1.getNotAfter().toGMTString(), s1.getSigAlgName().toString()
            }));
            boolean flag = ServerConnection.shouldShowTimeStamps();
            int i = mColours.getColourForEvent(0x7f0b002d);
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ax509certificate);
            Utils.addColour(flag, spannablestringbuilder, i, 0, s2.length());
            sendMessage("Status", spannablestringbuilder, 1);
            try
            {
                s1.checkValidity();
                return;
            }
            // Misplaced declaration of an exception variable
            catch (String s1)
            {
                ax509certificate.setLength(0);
                ax509certificate.append(s(0x7f0a0046));
                i = mColours.getColourForEvent(0x7f0b002e);
                s1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ax509certificate);
                Utils.addColour(flag, s1, i, 0, ax509certificate.length());
                sendMessage("Status", s1, 1);
                return;
            }
            // Misplaced declaration of an exception variable
            catch (String s1)
            {
                ax509certificate.setLength(0);
            }
            ax509certificate.append(s(0x7f0a0047));
            i = mColours.getColourForEvent(0x7f0b002f);
            s1 = Utils.createMessage(ServerConnection.shouldShowTimeStamps(), ax509certificate);
            Utils.addColour(flag, s1, i, 0, ax509certificate.length());
            sendMessage("Status", s1, 1);
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }

        private TrustAllSSL()
        {
            this$0 = ServerConnection.this;
            super();
        }

        TrustAllSSL(TrustAllSSL trustallssl)
        {
            this();
        }
    }


    static final Calendar sCal = Calendar.getInstance();
    private static final StringBuilder sTime;
    public static final int sTimeLength;
    static int sTimeout;
    public final int id;
    private Colours mColours;
    private Connection mConnection;
    private Ignores mIgnores;
    private long mLastNotif;
    private char mLogBuf[];
    private boolean mLogsEnabled;
    MessageSender mMessageSender;
    private int mNotifCount;
    private final AtomicBoolean mReconnecting = new AtomicBoolean(false);
    private final IRCService mService;
    private Handler mUserCommandsH;
    private HandlerThread mUserCommandsHT;
    long pConnectTime;
    boolean pConnectionChanged;
    int pFlags;
    Matcher pHilightMatcher;
    final String pLabel;
    final LogManager pLogs = new LogManager();
    String pNick;
    final ServerProfile pOptions;
    ParserImpl pParser;
    int pReconnectCount;
    final SessionManager pSessionManager;
    int pState;
    final Runnable removeMotdFlag = new Runnable() {

        final ServerConnection this$0;

        public void run()
        {
            this;
            JVM INSTR monitorenter ;
            Exception exception;
            try
            {
                wait(800L);
            }
            catch (InterruptedException interruptedexception) { }
            this;
            JVM INSTR monitorexit ;
            removeServerConnectionFlag(2);
            return;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
        }

            
            {
                this$0 = ServerConnection.this;
                super();
            }
    };
    final Runnable removeNameFlag = new Runnable() {

        final ServerConnection this$0;

        public void run()
        {
            this;
            JVM INSTR monitorenter ;
            Exception exception;
            try
            {
                wait(600L);
            }
            catch (InterruptedException interruptedexception) { }
            this;
            JVM INSTR monitorexit ;
            removeServerConnectionFlag(1);
            return;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
        }

            
            {
                this$0 = ServerConnection.this;
                super();
            }
    };

    ServerConnection(int i, ServerProfile serverprofile, MessageSender messagesender, SessionManager sessionmanager, IRCService ircservice)
    {
        pOptions = serverprofile;
        pLabel = serverprofile.getName();
        id = i;
        setMediator(messagesender);
        pSessionManager = sessionmanager;
        sessionmanager.setServ(this);
        mService = ircservice;
        mColours = Colours.getInstance();
        pParser = new ParserImpl(null);
        pReconnectCount = 0;
        pNick = serverprofile.getNick(1);
        mConnection = new Connection(this, 0);
        pFlags = 0;
        mLogsEnabled = serverprofile.isLoggingEnabled();
        logMessage("Status", Utils.createMessage(shouldShowTimeStamps(), Utils.getConnectionMessage(mService, false, serverprofile)));
        if (mLogsEnabled && !Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_ENABLE_CHATLOGS))
        {
            i = mColours.getColourForEvent(0x7f0b002d);
            serverprofile = s(0x7f0a004e);
            messagesender = Utils.createMessage(shouldShowTimeStamps(), serverprofile);
            Utils.addColour(shouldShowTimeStamps(), messagesender, i, 0, serverprofile.length());
            sendMessage("Status", messagesender, 1);
        }
        mIgnores = Ignores.getIgnores(id, Utils.getIRCDb(mService.getApplicationContext()));
        pState = 3;
        Message.obtain(mService.mHandler, 0, 3, 0, this).sendToTarget();
        mConnection.start();
    }

    static StringBuilder format(long l)
    {
        StringBuilder stringbuilder;
        synchronized (sCal)
        {
            sCal.setTimeInMillis(l);
            stringbuilder = (new StringBuilder(24)).append(Utils.getDay(sCal.get(7), true)).append(" ").append(Utils.getMonth(sCal.get(2), true)).append(" ").append(Utils.addPadding(sCal.get(5))).append(" ").append(sCal.get(1)).append(" ").append(Utils.addPadding(sCal.get(11))).append(":").append(Utils.addPadding(sCal.get(12))).append(":").append(Utils.addPadding(sCal.get(13)));
            sCal.setTimeInMillis(System.currentTimeMillis());
        }
        return stringbuilder;
        exception;
        calendar;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public static CharSequence getTime()
    {
        return sTime;
    }

    private void logMessage(String s1, CharSequence charsequence, boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        if (flag) goto _L2; else goto _L1
_L1:
        if (!mLogsEnabled) goto _L4; else goto _L3
_L3:
        boolean flag1 = Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_ENABLE_CHATLOGS);
        if (flag1) goto _L2; else goto _L4
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        char ac1[];
        int i;
        i = charsequence.length();
        if (mLogBuf == null)
        {
            mLogBuf = new char[i];
        }
        ac1 = mLogBuf;
        char ac[] = ac1;
        if (i > ac1.length)
        {
            ac = new char[i];
        }
        Arrays.fill(ac, '\uFFFF');
        TextUtils.getChars(charsequence, 0, i, ac, 0);
        pLogs.log(s1, ac, flag);
        if (true) goto _L4; else goto _L5
_L5:
        s1;
        throw s1;
    }

    private String s(int i)
    {
        return mService.getString(i);
    }

    private transient String s(int i, Object aobj[])
    {
        return mService.getString(i, aobj);
    }

    private static boolean shouldShowTimeStamps()
    {
        return Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_SHOW_TIMESTAMPS);
    }

    static void timeChanged(long l)
    {
        sCal.setTimeInMillis(l);
        sTime.setLength(0);
        sTime.append(Utils.addPadding(sCal.get(11))).append(":").append(Utils.addPadding(sCal.get(12)));
    }

    private void writeToServerInternal(String s1)
    {
        if (mConnection != null && pState != 0)
        {
            mConnection.write(s1);
        }
    }

    public void addServerConnnectionFlag(int i)
    {
        pFlags = pFlags | i;
    }

    void buildHilightRegexp(String s1)
    {
        Object obj;
        String s2;
        String s4;
        String as[];
        boolean flag;
        as = (String[])null;
        s4 = mService.prefs.getString(mService.getString(0x7f0a0022), null);
        flag = TextUtils.isEmpty(s4);
        s2 = s4;
        obj = as;
        if (flag) goto _L2; else goto _L1
_L1:
        if (s4.indexOf(',') != -1) goto _L4; else goto _L3
_L3:
        s2 = Utils.escape(s4.trim());
        obj = as;
_L2:
        s1 = (new StringBuilder()).append("(?i)").append("(?:").append("^").append("|[^\\w").append(":\\]\\[\\{\\}\\|\\-#=\\*\\^\\$\243\"'!`\\.,\\\\])").append("(?:").append(s1);
        String as1[];
        int i;
        int k;
        if (obj == null && !flag)
        {
            s1.append("|").append(s2);
        } else
        if (obj != null)
        {
            int l = obj.length;
            int j = 0;
            while (j < l) 
            {
                String s3 = obj[j];
                if (s3 != null)
                {
                    s1.append("|").append(s3);
                }
                j++;
            }
        }
        s1.append(")").append("(?:").append("[;:,'> !\\.\\?]").append("| \\-").append("|$)");
        pHilightMatcher = Pattern.compile(s1.toString()).matcher("");
        return;
_L4:
        as1 = s4.split(",");
        k = as1.length;
        i = 0;
_L6:
        s2 = s4;
        obj = as1;
        if (i >= k) goto _L2; else goto _L5
_L5:
        obj = as1[i];
        if (((String) (obj)).length() == 0)
        {
            as1[i] = null;
        } else
        {
            as1[i] = Utils.escape(((String) (obj)).trim());
        }
        i++;
          goto _L6
    }

    public boolean checkFlagActive(int i)
    {
        return Utils.isBitSet(pFlags, i);
    }

    public void closeLog(String s1)
    {
        pLogs.close(s1);
    }

    void deactivateAllAndUpdateUi(boolean flag)
    {
        SessionManager sessionmanager = pSessionManager;
        String as[] = sessionmanager.getSessionList();
        int i = as.length;
        boolean flag1 = false;
        i--;
        do
        {
            if (i < 0)
            {
                if (flag)
                {
                    pLogs.closeAll();
                }
                if (flag1)
                {
                    sendFlaggedMessage(6, null);
                }
                return;
            }
            String s1 = as[i];
            if (sessionmanager.getActive(s1))
            {
                flag1 = true;
                sessionmanager.setActive(s1, false);
                sessionmanager.removeAllNames(s1);
            }
            i--;
        } while (true);
    }

    public void forceConnect()
    {
        if (isReconnecting())
        {
            synchronized (mConnection)
            {
                mConnection.notify();
            }
            return;
        } else
        {
            return;
        }
        exception;
        connection;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int getConnectionState()
    {
        return pState;
    }

    public String getCurrentNick()
    {
        return pNick;
    }

    public Session getCurrentSession()
    {
        return pSessionManager.getCurrentSession();
    }

    public Ignores getIgnoreList()
    {
        return mIgnores;
    }

    public String getPartReason()
    {
        return IRCService.sPreferences.partReason;
    }

    public String getQuitReason()
    {
        return IRCService.sPreferences.quitReason;
    }

    public Resources getResources()
    {
        return mService.getResources();
    }

    public ServerConnection getServerConnection()
    {
        return this;
    }

    public SessionManager getSessionManager()
    {
        return pSessionManager;
    }

    public void handleAddMessage(IRCMessage ircmessage)
    {
        sendMessage(ircmessage.target, ircmessage.message, ircmessage.val);
    }

    public void handleBan(String s1)
    {
        Utils.handleBanHammer(s1, this);
    }

    public void handleJoin(String s1)
    {
        writeToServer((new StringBuilder("JOIN ")).append(s1).append("\r\n").toString());
    }

    public void handleKick(String s1)
    {
        Utils.handleKicking(s1, this);
    }

    public void handleOpAction(String s1, char c, char c1)
    {
        writeToServer(Utils.buildOpAction(s1, c, c1, getCurrentSession().getSessionName()));
    }

    public void handleQuit(String s1)
    {
        Utils.handleQuit(s1, mService, id);
    }

    public void handleStartPm(String s1)
    {
        if (!pSessionManager.haveSessionFor(s1))
        {
            pSessionManager.addSession(s1, pNick, 2);
            sendMessage(s1, Utils.getPmStart(mService, s1, shouldShowTimeStamps()), 2);
        }
    }

    public boolean isInReconnectMode()
    {
        return checkFlagActive(16);
    }

    public boolean isReconnecting()
    {
        return mConnection != null && mReconnecting.get();
    }

    public boolean isShowingTimestamps()
    {
        return shouldShowTimeStamps();
    }

    public void logMessage(String s1, CharSequence charsequence)
    {
        this;
        JVM INSTR monitorenter ;
        logMessage(s1, charsequence, false);
        this;
        JVM INSTR monitorexit ;
        return;
        s1;
        throw s1;
    }

    void makeNotif(String s1, String s2, String s3, int i)
    {
_L2:
        return;
        if (!Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_SHOW_NOTIFS) || !Utils.isBitSet(IRCService.sPreferences.pNotificationEvents, i)) goto _L2; else goto _L1
_L1:
        long l = System.currentTimeMillis();
        if (l - mLastNotif < 5000L) goto _L2; else goto _L3
_L3:
        String s4;
        NotificationManager notificationmanager;
        StringBuilder stringbuilder;
        IRCService ircservice;
        mLastNotif = l;
        ircservice = mService;
        notificationmanager = ircservice.mNm;
        stringbuilder = new StringBuilder();
        s4 = s3;
        if (s3.length() > 0)
        {
            s4 = s3;
            if (s3.charAt(0) == '$')
            {
                s4 = "Status";
            }
        }
        i;
        JVM INSTR lookupswitch 4: default 148
    //                   1: 149
    //                   2: 542
    //                   4: 566
    //                   16: 591;
           goto _L4 _L5 _L6 _L7 _L8
_L8:
        break MISSING_BLOCK_LABEL_591;
_L4:
        return;
_L5:
        stringbuilder.append(s(0x7f0a0078, new Object[] {
            s4
        }));
_L9:
        s2 = new android.support.v4.app.NotificationCompat.Builder(mService);
        s2.setSmallIcon(0x7f02000b).setTicker(s1).setWhen(l);
        int k = 0;
        s3 = new Intent(ircservice, IRCApp.CHAT_CLASS);
        s3.putExtra("id", id);
        s3.putExtra("window", s4);
        s3.setAction("net.andchat.donate.FROM_NOTIFICATION");
        s3.addFlags(0x14000000);
        s3.setData(Uri.parse((new StringBuilder("irc://")).append(l).toString()));
        s2.setAutoCancel(true);
        boolean flag = mService.shouldPlayNotification();
        int j = k;
        if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_PLAY_SOUND))
        {
            j = k;
            if (flag)
            {
                j = k;
                if (Utils.isBitSet(IRCService.sPreferences.pSoundOptions, i))
                {
                    if (IRCService.sPreferences.pDefaultTone != null)
                    {
                        s2.setSound(IRCService.sPreferences.pDefaultTone);
                        j = k;
                    } else
                    {
                        j = false | true;
                    }
                }
            }
        }
        k = j;
        if (Utils.isBitSet(IRCService.sPreferences.pMainOptions, IRCService.PREFS_VIBRATE))
        {
            k = j;
            if (flag)
            {
                k = j;
                if (Utils.isBitSet(IRCService.sPreferences.pVibrateOptions, i))
                {
                    k = j | 2;
                }
            }
        }
        s2.setDefaults(k);
        mNotifCount = mNotifCount + 1;
        s3 = PendingIntent.getActivity(ircservice, 0, s3, 0);
        if (mNotifCount > 0)
        {
            s2.setNumber(mNotifCount);
        }
        s2.setContentTitle(stringbuilder);
        s2.setContentText(s1);
        s2.setContentIntent(s3);
        s1 = s2.build();
        if (IRCApp.LEGACY_VERSION && mNotifCount > 0)
        {
            s1.number = mNotifCount;
        }
        s1.flags = ((Notification) (s1)).flags | 1;
        s1.ledARGB = 0xff00ffff;
        s1.ledOnMS = 1;
        s1.ledOffMS = 0;
        notificationmanager.notify(id, s1);
        return;
_L6:
        stringbuilder.append(s(0x7f0a007b, new Object[] {
            s2
        }));
          goto _L9
_L7:
        stringbuilder.append(s(0x7f0a007a, new Object[] {
            s4
        }));
          goto _L9
        stringbuilder.append(s(0x7f0a0079, new Object[] {
            s2
        }));
          goto _L9
    }

    public void prepareForReconnect()
    {
        addServerConnnectionFlag(4);
    }

    void reconnect()
    {
        int j;
        if (mReconnecting.get())
        {
            return;
        }
        mReconnecting.set(true);
        addServerConnnectionFlag(16);
        Message.obtain(mService.mHandler, 0, 3, 0, this).sendToTarget();
        pConnectionChanged = false;
        pReconnectCount = pReconnectCount + 1;
        if (pReconnectCount > IRCService.sPreferences.pReconnectLimit)
        {
            String s1 = s(0x7f0a0050, new Object[] {
                Integer.valueOf(IRCService.sPreferences.pReconnectLimit)
            });
            sendMessage("Status", Utils.createMessage(shouldShowTimeStamps(), s1), 1);
            removeServerConnectionFlag(16);
            mReconnecting.set(false);
            return;
        }
        j = pReconnectCount * 5;
        if (j > 0) goto _L2; else goto _L1
_L1:
        int i = 5;
_L4:
        pParser.reset();
        mConnection = new Connection(this, i);
        String s2 = s(0x7f0a0051, new Object[] {
            Integer.valueOf(pReconnectCount), Integer.valueOf(i)
        });
        sendMessage("Status", Utils.createMessage(shouldShowTimeStamps(), s2), 1);
        mService.wakeLock.acquire(5500L);
        prepareForReconnect();
        mConnection.start();
        return;
_L2:
        i = j;
        if (j > 30)
        {
            i = 30;
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    void removeServerConnectionFlag(int i)
    {
        pFlags = pFlags & ~i;
    }

    public void resetNotificationCount()
    {
        if (mNotifCount > 0)
        {
            mNotifCount = 0;
        }
        mLastNotif = 0L;
    }

    void sendConnectionChangedMessage()
    {
        pConnectionChanged = true;
        boolean flag = shouldShowTimeStamps();
        String s1 = s(0x7f0a004d);
        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(shouldShowTimeStamps(), s1);
        Utils.addColour(flag, spannablestringbuilder, mColours.getColourForEvent(0x7f0b0029), 0, s1.length());
        sendToAll(spannablestringbuilder, 1);
    }

    public void sendFlaggedMessage(int i, Object obj)
    {
        mMessageSender.sendFlaggedMessage(i, obj);
    }

    void sendMessage(String s1, CharSequence charsequence, int i)
    {
        String s2 = s1;
        if (s1.length() == 0)
        {
            s2 = "Status";
        }
        logMessage(s2, charsequence);
        mMessageSender.sendMessage(s2, charsequence, i);
    }

    void sendToAll(CharSequence charsequence, int i)
    {
        String as[] = pSessionManager.getSessionList();
        int j = as.length - 1;
        do
        {
            if (j < 0)
            {
                return;
            }
            String s1 = as[j];
            SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder();
            spannablestringbuilder.append(charsequence);
            sendMessage(s1, spannablestringbuilder, i);
            j--;
        } while (true);
    }

    protected void setMediator(MessageSender messagesender)
    {
        mMessageSender = messagesender;
    }

    protected void stopConnection(int i)
    {
        removeServerConnectionFlag(16);
        int j = pState;
        if (j != 0 && j != 4)
        {
            pState = 2;
            mConnection.stopConn(i);
        } else
        {
            wakeAndStop();
            Message.obtain(mService.mHandler, 0, i, 0, this).sendToTarget();
            deactivateAllAndUpdateUi(true);
        }
        mService.mNm.cancel(id);
    }

    public String toString()
    {
        return super.toString();
    }

    void wakeAndStop()
    {
        if (isReconnecting())
        {
            mConnection.mWasStopped = true;
            forceConnect();
        }
    }

    public void writeToServer(String s1)
    {
        if (mUserCommandsH == null)
        {
            return;
        } else
        {
            Message.obtain(mUserCommandsH, 0, s1).sendToTarget();
            return;
        }
    }

    static 
    {
        sTime = new StringBuilder(5);
        timeChanged(System.currentTimeMillis());
        sTimeLength = sTime.length();
    }













    // Unreferenced inner class net/andchat/donate/Backend/ServerConnection$Connection$1

/* anonymous class */
    class Connection._cls1
        implements Thread.UncaughtExceptionHandler
    {

        final Connection this$1;

        public void uncaughtException(Thread thread, Throwable throwable)
        {
            Log.e("ServerConnection", "Exception", throwable);
            thread = s(0x7f0a0043, new Object[] {
                mOptions.getAddress()
            });
            throwable = Utils.createMessage(isShowingTimestamps(), thread);
            Utils.addColour(isShowingTimestamps(), throwable, mColours.getColourForEvent(0x7f0b002c), 0, thread.length());
            Utils.addLinks(throwable);
            sendToAll(throwable, 3);
            pState = 0;
            Message.obtain(mService.mHandler, 0, 3, 0, mServ).sendToTarget();
            stopCommandQ();
        }

            
            {
                this$1 = Connection.this;
                super();
            }
    }


    // Unreferenced inner class net/andchat/donate/Backend/ServerConnection$Connection$2

/* anonymous class */
    class Connection._cls2 extends Handler
    {

        final Connection this$1;

        public void handleMessage(Message message)
        {
            mServ.writeToServerInternal((String)message.obj);
        }

            
            {
                this$1 = Connection.this;
                super(looper);
            }
    }

}
