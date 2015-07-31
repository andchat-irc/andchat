// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.app.KeyguardManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Base64;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.CommandParser;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            Parser, ServerConnection, IRCService, MessageSender, 
//            Ignores

private class <init> extends Parser
{

    private int m433Count;
    final StringBuilder reusableSB;
    final ServerConnection this$0;

    private void sendCount(final String chan)
    {
        (new Thread() {

            final ServerConnection.ParserImpl this$1;
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
                boolean flag = ServerConnection.access$8();
                net.andchat.donate.Backend.Sessions.Session.UserCount usercount = pSessionManager.getUserCount(chan);
                StringBuilder stringbuilder = new StringBuilder(100);
                stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0071, new Object[] {
                    Integer.valueOf(usercount.total)
                }));
                if (usercount.owner > 0)
                {
                    stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0072, new Object[] {
                        Integer.valueOf(usercount.owner)
                    }));
                }
                if (usercount.admin > 0)
                {
                    stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0073, new Object[] {
                        Integer.valueOf(usercount.admin)
                    }));
                }
                if (usercount.op > 0)
                {
                    stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0074, new Object[] {
                        Integer.valueOf(usercount.op)
                    }));
                }
                if (usercount.hop > 0)
                {
                    stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0075, new Object[] {
                        Integer.valueOf(usercount.hop)
                    }));
                }
                if (usercount.voice > 0)
                {
                    stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0076, new Object[] {
                        Integer.valueOf(usercount.voice)
                    }));
                }
                stringbuilder.append(ServerConnection.access$0(this$0, 0x7f0a0077, new Object[] {
                    Integer.valueOf(usercount.normal)
                }));
                int i = stringbuilder.length();
                if (usercount.total > 0)
                {
                    int j = ServerConnection.access$1(this$0).getColourForEvent(0x7f0b0028);
                    SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), stringbuilder);
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
                this$1 = ServerConnection.ParserImpl.this;
                chan = s;
                super();
            }
        }).start();
    }

    private boolean shouldNotify()
    {
        return ((KeyguardManager)ServerConnection.access$2(ServerConnection.this).getSystemService("keyguard")).inKeyguardRestrictedInputMode() || !mMessageSender.haveUi() || !ServerConnection.access$2(ServerConnection.this).screenOn;
    }

    public boolean isChannelPrefix(char c)
    {
        return Utils.isChannelPrefix(c);
    }

    public boolean isStatusPrefix(char c)
    {
        net.andchat.donate.Backend.Sessions.n n = pSessionManager.getStatusMap();
        if (n == null)
        {
            return false;
        } else
        {
            return Utils.isStatusPrefix(n, c);
        }
    }

    public void on001(String s)
    {
        int i;
        if (pNick != null)
        {
            pSessionManager.updateNick(pNick, s, pSessionManager.getSessionList());
        }
        pNick = s;
        sendFlaggedMessage(3, s);
        buildHilightRegexp(Utils.escape(s));
        boolean flag2 = Utils.isBitSet(pOptions.getAuthModes(), net.andchat.donate.Misc.Modes.MODE_NICKSERV);
        String s1 = pOptions.getAutojoinList();
        StringBuilder stringbuilder = new StringBuilder();
        if (flag2)
        {
            i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002c);
            Object obj = ServerConnection.access$9(ServerConnection.this, 0x7f0a0053);
            sendMessage("Status", Utils.addColour(ServerConnection.access$8(), Utils.createMessage(ServerConnection.access$8(), ((CharSequence) (obj))), i, 0, ((String) (obj)).length()), 1);
            obj = pOptions.getNickservPassword();
            stringbuilder.append("PRIVMSG NickServ :identify ");
            stringbuilder.append(((String) (obj))).append("\r\n");
            ServerConnection.access$5(ServerConnection.this, stringbuilder.toString());
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
        if (s1 != null && s1.length() > 0)
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
            catch (String s) { }
        }
        if (i != 0)
        {
            stringbuilder.setLength(0);
            stringbuilder.append("/j ").append(s1);
            CommandParser.handleCommand(stringbuilder.toString(), ServerConnection.this);
        }
        if (!checkFlagActive(4)) goto _L7; else goto _L6
_L6:
        removeServerConnectionFlag(4);
        s = pSessionManager.getSessionList();
        j = s.length;
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
            obj1 = ((Matcher) (obj2)).replaceAll(s);
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
                obj1 = ((Matcher) (obj2)).replaceAll(s);
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
        obj1 = s[i];
        if (((String) (obj1)).length() != 0 && Utils.isChannelPrefix(((String) (obj1)).charAt(0)))
        {
            obj2 = pSessionManager.getKey(((String) (obj1)));
            stringbuilder.append(((String) (obj1)));
            if (obj2 != null)
            {
                stringbuilder.append(" ").append(((String) (obj2)));
            }
            ServerConnection.access$5(ServerConnection.this, stringbuilder.append("\r\n").toString());
            stringbuilder.setLength(5);
        }
        i--;
        break MISSING_BLOCK_LABEL_441;
        stringindexoutofboundsexception;
        i = ((flag) ? 1 : 0);
          goto _L14
    }

    public void on221(String s)
    {
        s = ServerConnection.access$0(ServerConnection.this, 0x7f0a0069, new Object[] {
            s
        });
        sendMessage(getCurrentSession().getSessionName(), Utils.createMessage(ServerConnection.access$8(), s), 1);
    }

    public void on324(String s, String s1)
    {
        boolean flag = ServerConnection.access$8();
        if (pSessionManager.haveSessionFor(s))
        {
            pSessionManager.getMetadata(s).r = s1;
        }
        String s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a006e, new Object[] {
            s1
        });
        int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0017);
        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s2);
        Utils.addColour(flag, spannablestringbuilder, i, 0, s2.length());
        pSessionManager.checkForModes(s, s1, pNick);
        sendMessage(s, spannablestringbuilder, 1);
    }

    public void on329(String s, long l)
    {
        boolean flag = ServerConnection.access$8();
        if (pSessionManager.haveSessionFor(s))
        {
            pSessionManager.getMetadata(s).e = l;
        }
        String s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a006d, new Object[] {
            ServerConnection.format(l)
        });
        int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0018);
        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s1);
        Utils.addColour(flag, spannablestringbuilder, i, 0, s1.length());
        sendMessage(s, spannablestringbuilder, 1);
    }

    public void on332(String s, String s1, boolean flag)
    {
        boolean flag1 = ServerConnection.access$8();
        String s2;
        SpannableStringBuilder spannablestringbuilder;
        int i;
        if (flag)
        {
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a006b, new Object[] {
                s, s1
            });
        } else
        {
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a006a, new Object[] {
                s
            });
        }
        i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0016);
        spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s2);
        Utils.addColour(flag1, spannablestringbuilder, i, 0, s2.length());
        if (flag && s1.length() >= 4)
        {
            Utils.addLinks(spannablestringbuilder);
        }
        sendMessage(s, spannablestringbuilder, 1);
    }

    public void on333(String s, String s1, long l)
    {
        boolean flag = ServerConnection.access$8();
        s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a006c, new Object[] {
            s1, ServerConnection.format(l)
        });
        int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0016);
        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s1);
        Utils.addColour(flag, spannablestringbuilder, i, 0, s1.length());
        sendMessage(s, spannablestringbuilder, 1);
    }

    public void on433()
    {
        Object obj;
        String s;
        s = "";
        obj = null;
        m433Count;
        JVM INSTR tableswitch 0 2: default 36
    //                   0 107
    //                   1 151
    //                   2 195;
           goto _L1 _L2 _L3 _L4
_L1:
        sendMessage("Status", Utils.createMessage(ServerConnection.access$8(), ((CharSequence) (obj))), 1);
        if (s.length() > 0)
        {
            ServerConnection.access$5(ServerConnection.this, (new StringBuilder("NICK ")).append(s).append("\r\n").toString());
            pNick = s;
            sendFlaggedMessage(3, s);
        }
        return;
_L2:
        s = pOptions.getNick(2);
        obj = ServerConnection.access$0(ServerConnection.this, 0x7f0a006f, new Object[] {
            s
        });
        m433Count = m433Count + 1;
        continue; /* Loop/switch isn't completed */
_L3:
        s = pOptions.getNick(3);
        obj = ServerConnection.access$0(ServerConnection.this, 0x7f0a006f, new Object[] {
            s
        });
        m433Count = m433Count + 1;
        continue; /* Loop/switch isn't completed */
_L4:
        obj = ServerConnection.access$9(ServerConnection.this, 0x7f0a0070);
        if (true) goto _L1; else goto _L5
_L5:
    }

    public void onChannelPrivmsg(String s, String s1, String s2, String s3, String s4, int i)
    {
        StringBuilder stringbuilder;
        boolean flag;
        int k;
        int l;
        boolean flag1;
        k = 0;
        flag = false;
        l = s1.length();
        flag1 = ServerConnection.access$8();
        stringbuilder = reusableSB;
        stringbuilder.setLength(0);
        i;
        JVM INSTR tableswitch 0 2: default 56
    //                   0 446
    //                   1 57
    //                   2 692;
           goto _L1 _L2 _L3 _L4
_L1:
        return;
_L3:
        boolean flag2;
        flag2 = Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_SHOW_IGNORED);
        i = 0;
        if (!ServerConnection.access$10(ServerConnection.this).shouldIgnore(s2, s3, 1))
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
        s2 = pSessionManager.getNickStatus(s1, s);
        int k1 = pSessionManager.getNickColor(s1, s);
        stringbuilder.ensureCapacity(s1.length() + 2 + 2 + s4.length() + 1);
        stringbuilder.append("<").append(s2).append(s1).append("> ").append(s4);
        s3 = Utils.createMessage(ServerConnection.access$8(), stringbuilder);
        k = s2.length() + l;
        if (pHilightMatcher.reset(s4).find())
        {
            flag = true;
            Utils.addColourAndBold(flag1, s3, ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0014), 1, k + 1);
            int i1 = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0013);
            Utils.addColour(flag1, s3, i1, 0, 1);
            Utils.addColour(flag1, s3, i1, k + 1, k + 2);
        } else
        {
            int j1 = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0013);
            Utils.addColour(flag1, s3, j1, 0, 1);
            Utils.addColour(flag1, s3, j1, k + 1, k + 2);
            Utils.addColour(flag1, s3, k1, 1, s2.length() + 1 + s1.length());
        }
        if (s4.length() >= 4)
        {
            Utils.addLinks(s3);
        }
        if (flag && shouldNotify())
        {
            makeNotif(s3.toString(), s, s, 1);
        }
        if (flag2 && i != 0)
        {
            s3.append(" [IGNORED]");
        }
        s1 = ServerConnection.this;
        if (flag)
        {
            i = 3;
        } else
        {
            i = 2;
        }
        s1.sendMessage(s, s3, i);
        return;
_L2:
        flag2 = Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_SHOW_IGNORED);
        i = 0;
        if (!ServerConnection.access$10(ServerConnection.this).shouldIgnore(s2, s3, 1))
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
        stringbuilder.append("* ").append(s1).append(" ").append(s4);
        s2 = Utils.createMessage(ServerConnection.access$8(), stringbuilder);
        int j;
        if (pHilightMatcher.reset(s4).find())
        {
            k = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0014);
            j = 1;
            Utils.addColourAndBold(flag1, s2, k, 2, s1.length() + 2);
        } else
        {
            Utils.addColour(flag1, s2, pSessionManager.getNickColor(s1, s), 2, s1.length() + 2);
            j = k;
        }
        if (s4.length() >= 4)
        {
            Utils.addLinks(s2);
        }
        if (j != 0 && shouldNotify())
        {
            makeNotif(s2.toString(), s, s, 1);
        }
        if (flag2 && i != 0)
        {
            s2.append(" [IGNORED]");
        }
        s1 = ServerConnection.this;
        if (j != 0)
        {
            i = 3;
        } else
        {
            i = 2;
        }
        s1.sendMessage(s, s2, i);
        return;
_L4:
        s2 = s1;
        if (s1 == null)
        {
            s2 = "*";
        }
        stringbuilder.append(s2).append(" ").append(s4);
        s1 = Utils.createMessage(ServerConnection.access$8(), stringbuilder);
        if (s4.length() >= 4)
        {
            Utils.addLinks(s1);
        }
        sendMessage(s, s1, 2);
        return;
    }

    public void onCtcpRequest(String s, String s1, String s2, String s3)
    {
label0:
        {
            boolean flag1 = Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_SHOW_IGNORED);
            boolean flag = false;
            if (ServerConnection.access$10(ServerConnection.this).shouldIgnore(s1, s2, 8))
            {
                if (!flag1)
                {
                    break label0;
                }
                flag = true;
            }
            boolean flag2 = ServerConnection.access$8();
            s1 = s3.toUpperCase();
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0066, new Object[] {
                s1, s
            });
            int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0027);
            s3 = Utils.createMessage(flag2, s2);
            Utils.addColour(flag2, s3, i, 0, s2.length());
            if (flag && flag1)
            {
                s3.append(" [IGNORED]");
            }
            sendMessage(getCurrentSession().getSessionName(), s3, 1);
            if (s1.equals("VERSION"))
            {
                s1 = new StringBuilder();
                ServerConnection.access$5(ServerConnection.this, s1.append("NOTICE ").append(s).append(" :\001VERSION ").append("AndChat").append(" ").append("1.4.3.2").append(" http://www.andchat.net\001\r\n").toString());
            }
        }
    }

    public void onError(CharSequence charsequence)
    {
        sendMessage("Status", Utils.createMessage(ServerConnection.access$8(), charsequence), 1);
    }

    public void onISupport(String s)
    {
        int i = s.indexOf("PREFIX=");
        if (i == -1)
        {
            return;
        } else
        {
            String s1 = s.substring(i + 8, s.indexOf(" ", i));
            i = s1.indexOf(')');
            s = s1.substring(0, i);
            s1 = s1.substring(i + 1);
            pSessionManager.buildStatusMap(s, s1);
            return;
        }
    }

    public void onInvite(String s, String s1, String s2)
    {
        boolean flag = ServerConnection.access$8();
        s = ServerConnection.access$0(ServerConnection.this, 0x7f0a0057, new Object[] {
            s, s1, s2
        });
        int k = s.length();
        s1 = ServerConnection.access$9(ServerConnection.this, 0x7f0a0058);
        int l = s1.length();
        int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b001b);
        s = (new StringBuilder(s)).append(s1);
        s1 = Utils.createMessage(ServerConnection.access$8(), s);
        Utils.addColour(flag, s1, i, 0, s.length() - l);
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
        s1.setSpan(new net.andchat.donate.Misc.gth(s2), i, j, 33);
        sendMessage(getCurrentSession().getSessionName(), s1, 1);
    }

    public void onInviteSent(String s, String s1)
    {
        boolean flag = ServerConnection.access$8();
        s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0056, new Object[] {
            s1, s
        });
        int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b001c);
        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s1);
        Utils.addColour(flag, spannablestringbuilder, i, 0, s1.length());
        sendMessage(s, spannablestringbuilder, 1);
    }

    public void onKill(String s, String s1, String s2, String s3)
    {
        if (s.equals(pNick))
        {
            boolean flag = ServerConnection.access$8();
            s = ServerConnection.access$0(ServerConnection.this, 0x7f0a0067, new Object[] {
                s1, s3
            });
            int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002b);
            s1 = Utils.createMessage(flag, s);
            Utils.addColour(flag, s1, i, 0, s.length());
            sendToAll(s1, 1);
            deactivateAllAndUpdateUi(false);
            return;
        } else
        {
            onUserQuit(s, null, ServerConnection.access$0(ServerConnection.this, 0x7f0a0068, new Object[] {
                s1, s3
            }));
            return;
        }
    }

    public void onMessageReceived(String s)
    {
        if (Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_LOG_ALL_MESSAGES))
        {
            ServerConnection.access$11(ServerConnection.this, "_DEBUG_", s, true);
        }
    }

    public void onModeChanged(String s, String s1, String s2)
    {
        if (s2 == null)
        {
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a005a, new Object[] {
                s1, s
            });
            sendMessage("Status", Utils.createMessage(ServerConnection.access$8(), s2), 1);
        } else
        {
            boolean flag = ServerConnection.access$8();
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a005b, new Object[] {
                s1, s2
            });
            int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b001d);
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s2);
            Utils.addColour(flag, spannablestringbuilder, i, 0, s2.length());
            sendMessage(s, spannablestringbuilder, 1);
        }
        pSessionManager.checkForModes(s, s1, pNick);
    }

    public void onMotdEnd(String s)
    {
        if (!Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_SKIP_MOTD) || checkFlagActive(2))
        {
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s);
            if (s.length() >= 4)
            {
                Utils.addLinks(spannablestringbuilder);
            }
            sendMessage("Status", spannablestringbuilder, 1);
            (new Thread(removeMotdFlag)).start();
        }
    }

    public void onMotdLine(String s)
    {
        if (!Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_SKIP_MOTD) || checkFlagActive(2))
        {
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s);
            if (s.length() >= 4)
            {
                Utils.addLinks(spannablestringbuilder);
            }
            sendMessage("Status", spannablestringbuilder, 1);
        }
    }

    public void onMotdStart(String s)
    {
        if (!Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_SKIP_MOTD) || checkFlagActive(2))
        {
            SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), s);
            if (s.length() >= 4)
            {
                Utils.addLinks(spannablestringbuilder);
            }
            sendMessage("Status", spannablestringbuilder, 1);
            return;
        } else
        {
            boolean flag = ServerConnection.access$8();
            int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002c);
            s = ServerConnection.access$9(ServerConnection.this, 0x7f0a004f);
            SpannableStringBuilder spannablestringbuilder1 = Utils.createMessage(ServerConnection.access$8(), s);
            Utils.addColour(flag, spannablestringbuilder1, i, 0, s.length());
            sendMessage("Status", spannablestringbuilder1, 1);
            return;
        }
    }

    public void onNamesList(final String channel, final String names)
    {
        if (!checkFlagActive(1) && pSessionManager.haveSessionFor(channel))
        {
            (new Thread() {

                final ServerConnection.ParserImpl this$1;
                private final String val$channel;
                private final String val$names;

                public void run()
                {
                    pSessionManager.addNewNames(pNick, channel, names);
                }

            
            {
                this$1 = ServerConnection.ParserImpl.this;
                channel = s;
                names = s1;
                super();
            }
            }).start();
            return;
        }
        Object obj = new StringBuilder(ServerConnection.access$0(ServerConnection.this, 0x7f0a0059, new Object[] {
            channel
        }));
        if (names.length() > 0)
        {
            ((StringBuilder) (obj)).append(" ").append(names);
        }
        names = Utils.createMessage(ServerConnection.access$8(), ((CharSequence) (obj)));
        obj = ServerConnection.this;
        if (!pSessionManager.haveSessionFor(channel))
        {
            channel = "Status";
        }
        ((ServerConnection) (obj)).sendMessage(channel, names, 1);
    }

    public void onNetworkName(String s)
    {
    }

    public void onNickChanged(String s, String s1, String s2, String s3)
    {
        boolean flag = s.equals(pNick);
        if (flag)
        {
            pNick = s1;
            buildHilightRegexp(Utils.escape(s1));
            sendFlaggedMessage(3, s1);
        }
        ServerConnection.access$10(ServerConnection.this).nickChanged(s, s1, s2, s3);
        s3 = pSessionManager.getUserSessions(s);
        SpannableStringBuilder spannablestringbuilder;
        int i;
        boolean flag1;
        if (flag)
        {
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0064, new Object[] {
                s1
            });
        } else
        {
            s2 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0065, new Object[] {
                s, s1
            });
        }
        flag1 = ServerConnection.access$8();
        spannablestringbuilder = Utils.createMessage(flag1, s2);
        if (flag)
        {
            i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0025);
        } else
        {
            i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0026);
        }
        Utils.addColour(flag1, spannablestringbuilder, i, 0, s2.length());
        i = s3.length - 1;
        do
        {
            if (i < 0)
            {
                pSessionManager.updateNick(s, s1, s3);
                if (flag)
                {
                    sendMessage("Status", spannablestringbuilder, 1);
                }
                if (pSessionManager.updateSessionName(s, s1))
                {
                    sendFlaggedMessage(6, null);
                }
                if (!Utils.isChannelPrefix(s.charAt(0)) && pSessionManager.haveSessionFor(s1))
                {
                    s = Utils.createMessage(ServerConnection.access$8(), s2);
                    Utils.addColour(flag1, s, ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0026), 0, s2.length());
                    sendMessage(s1, s, 1);
                }
                return;
            }
            String s4 = s3[i];
            sendMessage(s4, new SpannableString(spannablestringbuilder), 1);
            i--;
        } while (true);
    }

    public void onNotice(String s, String s1, String s2, String s3, String s4, String s5)
    {
        if (s1 != null) goto _L2; else goto _L1
_L1:
        s1 = null;
        s = s1;
        if (s5 != null)
        {
            s = s1;
            if (Utils.isChannelPrefix(s5.charAt(0)))
            {
                s = s5;
            }
        }
        s2 = ServerConnection.this;
        s1 = s;
        if (s == null)
        {
            s1 = "Status";
        }
        s2.sendMessage(s1, Utils.createMessage(ServerConnection.access$8(), s4), 1);
_L4:
        return;
_L2:
        boolean flag;
        boolean flag1;
        if ((s == null || s != null && s.equals(pNick)) && s5 == null)
        {
            flag1 = Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_SHOW_IGNORED);
            flag = false;
            if (!ServerConnection.access$10(ServerConnection.this).shouldIgnore(s2, s3, 4))
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
        int j = s4.length();
        int i = s1.length();
        boolean flag2 = ServerConnection.access$8();
        s = new StringBuilder(j + 5 + i);
        s.append("-").append(s1).append("-: ").append(s4);
        j = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0019);
        s = Utils.createMessage(ServerConnection.access$8(), s);
        Utils.addColour(flag2, s, j, 0, 1);
        Utils.addColour(flag2, s, j, i + 1, i + 4);
        if (s4.length() >= 4)
        {
            Utils.addLinks(s);
        }
        if (shouldNotify())
        {
            makeNotif(s.toString(), s1, getCurrentSession().getSessionName(), 16);
        }
        if (flag && flag1)
        {
            s.append(" [IGNORED]");
        }
        sendMessage(getCurrentSession().getSessionName(), s, 2);
        return;
        flag1 = Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_SHOW_IGNORED);
        flag = false;
        if (!ServerConnection.access$10(ServerConnection.this).shouldIgnore(s2, s3, 4))
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
        boolean flag3 = ServerConnection.access$8();
        int j1 = s4.length();
        int l = s1.length();
        int i1 = s.length();
        byte byte0;
        int k;
        if (s5 != null)
        {
            byte0 = 1;
        } else
        {
            byte0 = 0;
        }
        if (byte0 != 0)
        {
            k = s5.length();
        } else
        {
            k = 0;
        }
        s2 = new StringBuilder(k + (l + 5 + i1 + j1));
        s2.append("-").append(s1).append("/");
        if (byte0 != 0)
        {
            s2.append(s5);
        }
        s2.append(s).append("-: ").append(s4);
        k = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b001a);
        s3 = Utils.createMessage(ServerConnection.access$8(), s2);
        Utils.addColour(flag3, s3, k, 1, s2.length() - j1 - 3);
        j1 = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0019);
        Utils.addColour(flag3, s3, j1, 0, 1);
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
        Utils.addColour(flag3, s3, j1, k, k + byte0);
        if (s4.length() >= 4)
        {
            Utils.addLinks(s3);
        }
        if (shouldNotify())
        {
            makeNotif(s3.toString(), s1, s, 4);
        }
        if (flag && flag1)
        {
            s3.append(" [IGNORED]");
        }
        sendMessage(s, s3, 2);
        return;
    }

    public void onNumericMessage(String s, CharSequence charsequence, int i)
    {
        boolean flag1;
        boolean flag2;
        flag1 = false;
        flag2 = false;
        i;
        JVM INSTR lookupswitch 17: default 152
    //                   315: 220
    //                   352: 269
    //                   366: 518
    //                   401: 783
    //                   471: 658
    //                   472: 680
    //                   473: 658
    //                   474: 658
    //                   475: 658
    //                   476: 680
    //                   477: 680
    //                   478: 680
    //                   482: 680
    //                   900: 859
    //                   903: 859
    //                   904: 859
    //                   905: 859;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L6 _L6 _L6 _L7 _L7 _L7 _L7 _L8 _L8 _L8 _L8
_L1:
        int j;
        boolean flag;
        flag = flag2;
        j = ((flag1) ? 1 : 0);
_L19:
        SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), charsequence);
        if (flag)
        {
            Utils.addColour(ServerConnection.access$8(), spannablestringbuilder, j, 0, charsequence.length());
        }
        charsequence = ServerConnection.this;
        if (s == null || s.length() == 0)
        {
            s = "Status";
        }
        charsequence.sendMessage(s, spannablestringbuilder, 1);
_L17:
        return;
_L2:
        if (checkFlagActive(4))
        {
            removeServerConnectionFlag(4);
            s = charsequence.toString();
            s = s.substring(0, s.indexOf(' '));
            j = ((flag1) ? 1 : 0);
            flag = flag2;
            continue; /* Loop/switch isn't completed */
        }
        continue; /* Loop/switch isn't completed */
_L3:
        String s1;
        String s4;
        String s5;
        String s7;
        s7 = charsequence.toString();
        s1 = null;
        s4 = null;
        s5 = null;
        i = s7.indexOf(' ');
        j = 0;
_L15:
        if (j >= 4)
        {
            pSessionManager.setIdentAndHost(s, s1, s5, s4);
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
    //                   0 385
    //                   1 421
    //                   2 455
    //                   3 469;
           goto _L9 _L10 _L11 _L12 _L13
_L9:
        break; /* Loop/switch isn't completed */
_L10:
        break; /* Loop/switch isn't completed */
_L16:
        j++;
        if (true) goto _L15; else goto _L14
_L14:
        s = s7.substring(0, i);
        i = s7.indexOf(' ', i + 1);
        s5 = s7.substring(s.length() + 1, i);
          goto _L16
_L11:
        s4 = s7.substring(i + 1, s7.indexOf(' ', i + 1));
        i = s7.indexOf(' ', i + 1);
          goto _L16
_L12:
        i = s7.indexOf(' ', i + 1);
          goto _L16
_L13:
        String s6 = s7.substring(i + 1, s7.indexOf(' ', i + 1));
        s1 = s6;
          goto _L16
        StringIndexOutOfBoundsException stringindexoutofboundsexception;
        stringindexoutofboundsexception;
        Log.e("ServerConnection", s7, stringindexoutofboundsexception);
        onUnparsableMessage(s7);
          goto _L16
_L4:
        s = charsequence.toString();
        s = s.substring(0, s.indexOf(' ', 1)).trim();
        boolean flag3 = checkFlagActive(1);
        if (flag3 || pSessionManager.haveSessionFor(s))
        {
            sendCount(s);
            ServerConnection.access$5(ServerConnection.this, (new StringBuilder("WHO ")).append(s).append("\r\n").toString());
            if (flag3)
            {
                (new Thread(removeNameFlag)).start();
                return;
            }
        } else
        {
            if (!pSessionManager.haveSessionFor(s))
            {
                s = "Status";
            }
            j = ((flag1) ? 1 : 0);
            flag = flag2;
            continue; /* Loop/switch isn't completed */
        }
        if (true) goto _L17; else goto _L6
_L6:
        s = getCurrentSession().getSessionName();
        j = ((flag1) ? 1 : 0);
        flag = flag2;
        continue; /* Loop/switch isn't completed */
_L7:
        String s2 = charsequence.toString();
        j = s2.indexOf(' ', 1);
        if (j != -1)
        {
            s2 = s2.substring(0, j);
        }
        j = ((flag1) ? 1 : 0);
        flag = flag2;
        s = s2;
        if (i == 477)
        {
            j = ((flag1) ? 1 : 0);
            flag = flag2;
            s = s2;
            if (!pSessionManager.haveSessionFor(s2))
            {
                s = getCurrentSession().getSessionName();
                j = ((flag1) ? 1 : 0);
                flag = flag2;
            }
        }
        continue; /* Loop/switch isn't completed */
_L5:
        String s3;
        if (charsequence instanceof String)
        {
            s3 = (String)charsequence;
        } else
        {
            s3 = charsequence.toString();
        }
        s3 = s3.substring(0, s3.indexOf(' '));
        j = ((flag1) ? 1 : 0);
        flag = flag2;
        if (pSessionManager.haveSessionFor(s3))
        {
            s = s3;
            j = ((flag1) ? 1 : 0);
            flag = flag2;
        }
        continue; /* Loop/switch isn't completed */
_L8:
        j = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002c);
        flag = true;
        ServerConnection.access$5(ServerConnection.this, "CAP END\r\n");
        if (true) goto _L19; else goto _L18
_L18:
    }

    public void onPing(String s)
    {
        ServerConnection.access$5(ServerConnection.this, (new StringBuilder("PONG :")).append(s).append("\r\n").toString());
    }

    public void onPong(String s)
    {
        if (!checkFlagActive(8))
        {
            return;
        } else
        {
            removeServerConnectionFlag(8);
            s = (new StringBuilder("Pong: ")).append(s).toString();
            sendMessage("Status", Utils.createMessage(ServerConnection.access$8(), s), 1);
            return;
        }
    }

    public void onPrivatePrivmsg(String s, String s1, String s2, String s3, int i)
    {
        boolean flag;
        boolean flag1;
        flag1 = Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_SHOW_IGNORED);
        flag = false;
        if (!ServerConnection.access$10(ServerConnection.this).shouldIgnore(s1, s2, 2)) goto _L2; else goto _L1
_L1:
        if (!flag1) goto _L4; else goto _L3
_L3:
        flag = true;
_L2:
        if (!pSessionManager.haveSessionFor(s))
        {
            pSessionManager.addSession(s, pNick, 2);
            sendFlaggedMessage(2, s);
            boolean flag2 = ServerConnection.access$8();
            s1 = new SpannableStringBuilder();
            s1.append(Utils.getPmStart(ServerConnection.access$2(ServerConnection.this), s, flag2));
            if (flag && flag1)
            {
                s1.append(" [IGNORED]");
            }
            sendMessage(s, s1, 2);
        }
        pSessionManager.setMarked(s, true);
        i;
        JVM INSTR tableswitch 0 1: default 180
    //                   0 349
    //                   1 181;
           goto _L4 _L5 _L6
_L4:
        return;
_L6:
        i = s.length();
        int j = s3.length();
        boolean flag3 = ServerConnection.access$8();
        s1 = new StringBuilder(i + 4 + j);
        s1.append("<").append(s).append("> ").append(s3);
        s1 = Utils.createMessage(ServerConnection.access$8(), s1);
        int k = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0013);
        Utils.addColour(flag3, s1, k, 0, 1);
        Utils.addColour(flag3, s1, k, i + 1, i + 2);
        if (j >= 4)
        {
            Utils.addLinks(s1);
        }
        if (shouldNotify())
        {
            makeNotif(s1.toString(), s, s, 2);
        }
        if (flag && flag1)
        {
            s1.append(" [IGNORED]");
        }
        sendMessage(s, s1, 2);
        return;
_L5:
        s1 = new StringBuilder();
        s1.append("* ").append(s).append(" ").append(s3);
        s1 = Utils.createMessage(ServerConnection.access$8(), s1);
        if (s3.length() >= 4)
        {
            Utils.addLinks(s1);
        }
        if (flag && flag1)
        {
            s1.append(" [IGNORED]");
        }
        sendMessage(s, s1, 2);
        if (shouldNotify())
        {
            makeNotif(s1.toString(), s, s, 2);
            return;
        }
        if (true) goto _L4; else goto _L7
_L7:
    }

    public void onSaslMessage(String s, String s1, String as[])
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
        if (!s.equals("CAP")) goto _L2; else goto _L1
_L1:
        flag = flag1;
        if (s1 == null) goto _L4; else goto _L3
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
            ServerConnection.access$5(ServerConnection.this, "AUTHENTICATE PLAIN\r\n");
        }
_L4:
        if (!flag)
        {
            ServerConnection.access$5(ServerConnection.this, "CAP END\r\n");
        }
        return;
_L9:
        i;
        JVM INSTR tableswitch 1 1: default 136
    //                   1 170;
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
        if (!s.equals("AUTHENTICATE")) goto _L4; else goto _L16
_L16:
        flag = flag1;
        if (!s1.equals("+")) goto _L4; else goto _L17
_L17:
        as = pOptions.getSASLUsername();
        s1 = pOptions.getCharset();
        s = (byte[])null;
        try
        {
            s = (new String((new StringBuilder(String.valueOf(as))).append("\0").append(as).append("\0").append(pOptions.getSASLPassword()).toString())).getBytes(s1);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s = (new String((new StringBuilder(String.valueOf(as))).append("\0").append(as).append("\0").append(pOptions.getSASLPassword()).toString())).getBytes();
        }
        s1 = new String(Base64.encode(s, 0, s.length, 4), s1);
        s = s1;
_L18:
        ServerConnection.access$5(ServerConnection.this, (new StringBuilder("AUTHENTICATE ")).append(s).toString());
        flag = true;
          goto _L4
        s1;
        s = new String(Base64.encode(s, 0, s.length, 4));
          goto _L18
    }

    public void onTopicChanged(String s, String s1, String s2)
    {
        int i;
        boolean flag;
        if (s2 != null && s2.length() > 0)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        if (i != 0)
        {
            s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0055, new Object[] {
                s2, s1
            });
        } else
        {
            s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0054, new Object[] {
                s1
            });
        }
        flag = ServerConnection.access$8();
        i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0015);
        s2 = Utils.createMessage(ServerConnection.access$8(), s1);
        Utils.addColour(flag, s2, i, 0, s1.length());
        Utils.addLinks(s2);
        sendMessage(s, s2, 1);
    }

    public void onUnknownMessage(CharSequence charsequence)
    {
        charsequence = Utils.createMessage(ServerConnection.access$8(), charsequence);
        sendMessage("Status", charsequence, 1);
    }

    public void onUnparsableMessage(String s)
    {
        if (Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREF_DEBUG_LOG_UNPARSEABLE))
        {
            ServerConnection.access$11(ServerConnection.this, "_unparseable_", s, true);
        }
        s = ServerConnection.access$0(ServerConnection.this, 0x7f0a007c, new Object[] {
            s
        });
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(s);
        Utils.addColour(false, spannablestringbuilder, Colours.getInstance().getColourForEvent(0x7f0b002c), 0, s.length());
        onUnknownMessage(spannablestringbuilder);
    }

    public void onUserJoined(String s, String s1, String s2, String s3)
    {
        if (s1.equals(pNick))
        {
            pSessionManager.addSession(s, s1, 1);
            sendFlaggedMessage(1, s);
            ServerConnection.access$5(ServerConnection.this, (new StringBuilder("MODE ")).append(s).append("\r\n").toString());
            if (!pSessionManager.getActive(s))
            {
                pSessionManager.setActive(s, true);
                sendFlaggedMessage(6, null);
            }
        } else
        {
            pSessionManager.addName(s, s1, s2, s3);
            if (!Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_HIDE_MESSAGES))
            {
                boolean flag = ServerConnection.access$8();
                s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a005c, new Object[] {
                    s1, s3
                });
                int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b001e);
                s2 = Utils.createMessage(ServerConnection.access$8(), s1);
                Utils.addColour(flag, s2, i, 0, s1.length());
                sendMessage(s, s2, 1);
                return;
            }
        }
    }

    public void onUserKicked(String s, String s1, String s2, String s3)
    {
        pSessionManager.removeName(s, s1);
        boolean flag = ServerConnection.access$8();
        if (s1.equals(pNick))
        {
            pSessionManager.setActive(s, false);
            sendFlaggedMessage(6, null);
            s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a005f, new Object[] {
                s, s2, s3
            });
            int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0020);
            s2 = Utils.createMessage(ServerConnection.access$8(), s1);
            Utils.addColour(flag, s2, i, 0, s1.length());
            sendMessage(s, s2, 2);
            pSessionManager.setActive(s, false);
            pSessionManager.removeAllNames(s);
            sendFlaggedMessage(5, s);
            if (Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_REJOIN_ON_KICK))
            {
                s1 = ServerConnection.access$9(ServerConnection.this, 0x7f0a0060);
                int j = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0021);
                s2 = Utils.createMessage(ServerConnection.access$8(), s1);
                Utils.addColour(flag, s2, j, 0, s1.length());
                sendMessage(s, s2, 1);
                pLogs.close(s);
                (new Thread() {

                    final ServerConnection.ParserImpl this$1;
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
                                ServerConnection.access$5(this$0, stringbuilder.toString());
                                return;
                            }
                        }
                        catch (Exception exception)
                        {
                            return;
                        }
                        ServerConnection.access$5(this$0, stringbuilder.append("JOIN ").append(chan).append("\r\n").toString());
                        return;
                    }

            
            {
                this$1 = ServerConnection.ParserImpl.this;
                key = s;
                chan = s1;
                super();
            }
                }).start();
            }
            return;
        }
        if (s2.equals(pNick))
        {
            s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0061, new Object[] {
                s1, s, s3
            });
            int k = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0022);
            s2 = Utils.createMessage(ServerConnection.access$8(), s1);
            Utils.addColour(flag, s2, k, 0, s1.length());
            sendMessage(s, s2, 1);
            return;
        } else
        {
            s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0062, new Object[] {
                s1, s2, s3
            });
            int l = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0023);
            s2 = Utils.createMessage(ServerConnection.access$8(), s1);
            Utils.addColour(flag, s2, l, 0, s1.length());
            sendMessage(s, s2, 1);
            return;
        }
    }

    public void onUserParted(String s, String s1, String s2, String s3)
    {
        if (s1.equals(pNick))
        {
            pLogs.close(s);
            pSessionManager.removeSession(s);
            sendFlaggedMessage(4, s);
        } else
        {
            pSessionManager.removeName(s, s1);
            if (!Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_HIDE_MESSAGES))
            {
                int i;
                if (s3 != null && s3.length() > 0)
                {
                    i = 1;
                } else
                {
                    i = 0;
                }
                if (i != 0)
                {
                    s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a005e, new Object[] {
                        s1, s2, s3
                    });
                } else
                {
                    s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a005d, new Object[] {
                        s1, s2
                    });
                }
                i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b001f);
                s2 = Utils.createMessage(ServerConnection.access$8(), s1);
                Utils.addColour(ServerConnection.access$8(), s2, i, 0, s1.length());
                sendMessage(s, s2, 1);
                return;
            }
        }
    }

    public void onUserQuit(String s, String s1, String s2)
    {
        int i = 0;
        if (Utils.isBitSet(IRCService.sPreferences.Options, IRCService.PREFS_HIDE_MESSAGES))
        {
            pSessionManager.getQuitSessions(s);
        } else
        {
            if (s.equals(pNick))
            {
                deactivateAllAndUpdateUi(false);
                return;
            }
            s1 = ServerConnection.access$0(ServerConnection.this, 0x7f0a0063, new Object[] {
                s, s2
            });
            s = pSessionManager.getQuitSessions(s);
            boolean flag = ServerConnection.access$8();
            int j = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0024);
            s2 = Utils.createMessage(flag, s1);
            Utils.addColour(flag, s2, j, 0, s1.length());
            j = s.length;
            while (i < j) 
            {
                s1 = s[i];
                sendMessage(s1, new SpannableString(s2), 1);
                i++;
            }
        }
    }

    public void onWallops(String s, String s1, String s2)
    {
        boolean flag = ServerConnection.access$8();
        int i = s.length();
        int j = s2.length();
        s1 = new StringBuilder(i + j + 12);
        s1.append("-").append(s).append("/").append("Wallops-: ").append(s2);
        int k = s1.length();
        int l = Colours.getInstance().getColourForEvent(0x7f0b001a);
        s = Utils.createMessage(ServerConnection.access$8(), s1);
        Utils.addColour(flag, s, l, 0, k - j - 2);
        l = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b0013);
        Utils.addColour(flag, s, l, 0, 1);
        Utils.addColour(flag, s, l, i + 9, k - j - 2);
        sendMessage(getCurrentSession().getSessionName(), s, 1);
    }

    protected void reset()
    {
        super.reset();
        m433Count = 0;
        reusableSB.setLength(0);
    }

    public String stripColours(String s)
    {
        return mColourRemover.reset(s).replaceAll("");
    }


    private _cls3.val.chan()
    {
        this$0 = ServerConnection.this;
        super();
        m433Count = 0;
        reusableSB = new StringBuilder();
    }

    reusableSB(reusableSB reusablesb)
    {
        this();
    }
}
