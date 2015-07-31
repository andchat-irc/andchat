// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Misc.Utils;

abstract class Parser
{

    private static final Matcher sNetworkMatcher = Pattern.compile("NETWORK=([^ ]+)").matcher("");
    protected final Matcher mColourRemover = Pattern.compile("\\x03\\d?\\d?(?:,\\d?\\d?)?|\\x02|\\x1f|\\x16|\\x06|\\x07/").matcher("");
    private boolean mConnected;

    protected Parser()
    {
    }

    private boolean isIntStr(String s)
    {
        if (s != null) goto _L2; else goto _L1
_L1:
        int i;
        return false;
_L2:
        if ((i = s.length()) == 0)
        {
            continue;
        }
        i--;
        do
        {
            if (i < 0)
            {
                return true;
            }
            if (!Character.isDigit(s.charAt(i)))
            {
                continue;
            }
            i--;
        } while (true);
        if (true) goto _L1; else goto _L3
_L3:
    }

    private String snip(String s, int i)
    {
        if (i >= s.length())
        {
            return s;
        }
        if (s.charAt(i) == ':')
        {
            s = s.substring(i + 1);
        } else
        {
            s = s.substring(i);
        }
        return s;
    }

    public abstract boolean isChannelPrefix(char c);

    public abstract boolean isStatusPrefix(char c);

    public abstract void on001(String s);

    public abstract void on221(String s);

    public abstract void on324(String s, String s1);

    public abstract void on329(String s, long l);

    public abstract void on332(String s, String s1, boolean flag);

    public abstract void on333(String s, String s1, long l);

    public abstract void on433();

    public abstract void onChannelPrivmsg(String s, String s1, String s2, String s3, String s4, int i);

    public abstract void onCtcpRequest(String s, String s1, String s2, String s3);

    public abstract void onError(CharSequence charsequence);

    public abstract void onISupport(String s);

    public abstract void onInvite(String s, String s1, String s2);

    public abstract void onInviteSent(String s, String s1);

    public abstract void onKill(String s, String s1, String s2, String s3);

    public abstract void onMessageReceived(String s);

    public abstract void onModeChanged(String s, String s1, String s2);

    public abstract void onMotdEnd(String s);

    public abstract void onMotdLine(String s);

    public abstract void onMotdStart(String s);

    public abstract void onNamesList(String s, String s1);

    public abstract void onNetworkName(String s);

    public abstract void onNickChanged(String s, String s1, String s2, String s3);

    public abstract void onNotice(String s, String s1, String s2, String s3, String s4, String s5);

    public abstract void onNumericMessage(String s, CharSequence charsequence, int i);

    public abstract void onPing(String s);

    public abstract void onPong(String s);

    public abstract void onPrivatePrivmsg(String s, String s1, String s2, String s3, int i);

    public abstract void onSaslMessage(String s, String s1, String as[]);

    public abstract void onTopicChanged(String s, String s1, String s2);

    public abstract void onUnknownMessage(CharSequence charsequence);

    public abstract void onUnparsableMessage(String s);

    public abstract void onUserJoined(String s, String s1, String s2, String s3);

    public abstract void onUserKicked(String s, String s1, String s2, String s3);

    public abstract void onUserParted(String s, String s1, String s2, String s3);

    public abstract void onUserQuit(String s, String s1, String s2);

    public abstract void onWallops(String s, String s1, String s2);

    public final void parse(String s)
    {
        int i;
        int i3;
        onMessageReceived(s);
        i = 0;
        i3 = s.indexOf(' ');
        if (i3 != -1) goto _L2; else goto _L1
_L1:
        onUnparsableMessage(s);
_L6:
        return;
_L2:
        Object obj;
        String s1;
        Object obj3;
        String s21;
        String s12;
        int i4;
        if (s.charAt(0) == ':')
        {
            obj = s.substring(1, i3);
        } else
        {
            obj = s.substring(0, i3);
        }
        obj1 = s.substring(i3 + 1);
        i3 = ((String) (obj1)).indexOf(' ');
        if (i3 == -1) goto _L4; else goto _L3
_L3:
        s21 = ((String) (obj1)).substring(0, i3);
        s1 = ((String) (obj1)).substring(i3 + 1);
        if (s21.equals("PRIVMSG"))
        {
            i3 = s1.indexOf(':');
            i = i3;
            if (i3 == -1)
            {
                i = s1.indexOf(' ') + 1;
            }
            obj1 = s1.substring(0, i - 1);
            obj3 = obj1;
            i3 = i;
            if (i < s1.length())
            {
                obj3 = obj1;
                i3 = i;
                if (s1.charAt(i - 1) != ' ')
                {
                    obj3 = obj1;
                    i3 = i;
                    if (s1.charAt(i) == ':')
                    {
                        i4 = s1.indexOf(':', i + 1);
                        obj3 = obj1;
                        i3 = i;
                        if (i4 > 0)
                        {
                            obj3 = s1.substring(0, i4 - 1);
                            i3 = i4;
                        }
                    }
                }
            }
            s12 = s1.substring(i3 + 1);
            i3 = ((String) (obj)).indexOf('!');
            String s22;
            if (i3 == -1)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            if (i != 0)
            {
                if (stripColours(((String) (obj))).equals(""))
                {
                    obj1 = "*";
                } else
                {
                    obj1 = obj;
                }
            } else
            {
                obj1 = ((String) (obj)).substring(0, i3);
            }
            s22 = stripColours(s12);
            i3 = s22.length();
            if (i3 > 2 && s22.charAt(0) == '\001' && s22.charAt(i3 - 1) == '\001')
            {
                String s19 = s22.substring(1, i3 - 1);
                if (s19.length() >= 7 && s19.substring(0, 7).equals("ACTION "))
                {
                    s22 = s19.substring(s19.indexOf(' ') + 1);
                    if (isChannelPrefix(((String) (obj3)).charAt(0)))
                    {
                        i = ((String) (obj)).indexOf('!');
                        i3 = ((String) (obj)).indexOf('@');
                        String s13;
                        if (i3 == -1)
                        {
                            s13 = "";
                        } else
                        {
                            s13 = ((String) (obj)).substring(i + 1, i3);
                        }
                        if (i3 == -1)
                        {
                            s19 = "";
                        } else
                        {
                            s19 = ((String) (obj)).substring(i3 + 1);
                        }
                        onChannelPrivmsg(((String) (obj3)), ((String) (obj1)), s13, s19, s22, 0);
                    } else
                    {
                        i = ((String) (obj)).indexOf('!');
                        i3 = ((String) (obj)).indexOf('@');
                        String s14;
                        if (i3 == -1)
                        {
                            obj3 = "";
                        } else
                        {
                            obj3 = ((String) (obj)).substring(i + 1, i3);
                        }
                        if (i3 == -1)
                        {
                            s14 = "";
                        } else
                        {
                            s14 = ((String) (obj)).substring(i3 + 1);
                        }
                        onPrivatePrivmsg(((String) (obj1)), ((String) (obj3)), s14, s22, 0);
                    }
                    i = 1;
                } else
                {
                    i = ((String) (obj)).indexOf('!');
                    i3 = ((String) (obj)).indexOf('@');
                    String s15;
                    if (i3 == -1)
                    {
                        obj3 = "";
                    } else
                    {
                        obj3 = ((String) (obj)).substring(i + 1, i3);
                    }
                    if (i3 == -1)
                    {
                        s15 = "";
                    } else
                    {
                        s15 = ((String) (obj)).substring(i3 + 1);
                    }
                    onCtcpRequest(((String) (obj1)), ((String) (obj3)), s15, s19);
                    i = 1;
                }
            } else
            if (isChannelPrefix(((String) (obj3)).charAt(0)))
            {
                i3 = ((String) (obj)).indexOf('@');
                int j4 = ((String) (obj)).indexOf('!');
                String s16;
                String s20;
                if (i3 == -1)
                {
                    s16 = "";
                } else
                {
                    s16 = ((String) (obj)).substring(j4 + 1, i3);
                }
                if (i3 == -1)
                {
                    s20 = "";
                } else
                {
                    s20 = ((String) (obj)).substring(i3 + 1);
                }
                if (i != 0)
                {
                    i = 2;
                } else
                {
                    i = 1;
                }
                onChannelPrivmsg(((String) (obj3)), ((String) (obj1)), s16, s20, s22, i);
                i = 1;
            } else
            {
                i = ((String) (obj)).indexOf('!');
                i3 = ((String) (obj)).indexOf('@');
                String s17;
                if (i3 == -1)
                {
                    obj3 = "";
                } else
                {
                    obj3 = ((String) (obj)).substring(i + 1, i3);
                }
                if (i3 == -1)
                {
                    s17 = "";
                } else
                {
                    s17 = ((String) (obj)).substring(i3 + 1);
                }
                onPrivatePrivmsg(((String) (obj1)), ((String) (obj3)), s17, s22, 1);
                i = 1;
            }
        }
_L40:
        if (i != 0) goto _L6; else goto _L5
_L5:
        obj1 = s1;
        if (s21.length() != 3)
        {
            break; /* Loop/switch isn't completed */
        }
        obj1 = s1;
        if (!isIntStr(s21))
        {
            break; /* Loop/switch isn't completed */
        }
        i = Integer.parseInt(s21);
        i;
        JVM INSTR lookupswitch 78: default 1136
    //                   1: 2924
    //                   5: 3238
    //                   221: 2999
    //                   305: 3025
    //                   306: 3025
    //                   315: 3209
    //                   321: 3086
    //                   322: 3054
    //                   324: 2457
    //                   329: 2513
    //                   331: 2637
    //                   332: 2680
    //                   333: 2747
    //                   337: 3025
    //                   341: 2611
    //                   346: 2056
    //                   348: 2056
    //                   352: 3180
    //                   353: 2173
    //                   366: 2397
    //                   367: 2056
    //                   372: 1968
    //                   375: 1968
    //                   376: 1968
    //                   401: 1911
    //                   402: 1911
    //                   403: 1911
    //                   404: 2871
    //                   405: 1911
    //                   406: 1911
    //                   407: 1911
    //                   408: 1911
    //                   409: 1911
    //                   411: 1911
    //                   412: 1911
    //                   413: 1911
    //                   414: 1911
    //                   415: 1911
    //                   421: 1911
    //                   422: 2111
    //                   423: 1911
    //                   424: 1911
    //                   431: 1911
    //                   432: 1940
    //                   433: 2832
    //                   436: 1911
    //                   437: 1911
    //                   441: 1911
    //                   442: 1911
    //                   443: 1911
    //                   444: 1911
    //                   445: 1911
    //                   446: 1911
    //                   451: 1911
    //                   461: 1911
    //                   462: 1911
    //                   463: 1911
    //                   464: 1911
    //                   465: 1911
    //                   466: 1911
    //                   467: 1911
    //                   471: 1911
    //                   472: 1911
    //                   473: 1911
    //                   474: 1911
    //                   475: 1911
    //                   476: 1911
    //                   477: 1911
    //                   478: 1911
    //                   481: 1911
    //                   482: 2871
    //                   483: 1911
    //                   484: 1911
    //                   485: 1911
    //                   491: 1911
    //                   501: 1911
    //                   502: 1911
    //                   972: 3114;
           goto _L7 _L8 _L9 _L10 _L11 _L11 _L12 _L13 _L14 _L15 _L16 _L17 _L18 _L19 _L11 _L20 _L21 _L21 _L22 _L23 _L24 _L21 _L25 _L25 _L25 _L26 _L26 _L26 _L27 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L28 _L26 _L26 _L26 _L29 _L30 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L26 _L27 _L26 _L26 _L26 _L26 _L26 _L26 _L31
_L7:
        if (s1.indexOf(':') != -1)
        {
            i3 = s1.indexOf(' ') + 1;
            s13 = snip(s1, i3);
            i4 = s1.indexOf(':');
            StringBuilder stringbuilder;
            long l4;
            long l5;
            if (i4 != -1 && i3 + 1 < i4)
            {
                obj3 = s1.substring(i3 + 1, i4 - 1);
                obj1 = obj3;
                if (((String) (obj3)).length() > 0)
                {
                    obj1 = obj3;
                    if (!isChannelPrefix(((String) (obj3)).charAt(0)))
                    {
                        obj3 = s1.substring(i3, i4 - 1);
                        obj1 = obj3;
                        if (((String) (obj3)).length() > 0)
                        {
                            obj1 = obj3;
                            if (!isChannelPrefix(((String) (obj3)).charAt(0)))
                            {
                                obj1 = null;
                            }
                        }
                    }
                }
            } else
            {
                obj1 = null;
            }
            onNumericMessage(((String) (obj1)), s13, i);
            obj1 = s1;
        } else
        {
            obj1 = snip(s1, s1.indexOf(' ') + 1);
            onNumericMessage(null, ((CharSequence) (obj1)), i);
        }
_L33:
        if (true) goto _L6; else goto _L32
_L4:
        if (((String) (obj)).equals("PING"))
        {
            s = ((String) (obj1));
            if (((String) (obj1)).charAt(0) == ':')
            {
                s = ((String) (obj1)).substring(1);
            }
            onPing(s);
            return;
        }
        if (((String) (obj)).equals("AUTHENTICATE"))
        {
            onSaslMessage(((String) (obj)), ((String) (obj1)), null);
            return;
        } else
        {
            onUnparsableMessage(s);
            return;
        }
_L26:
        obj1 = snip(s1, s1.indexOf(' ') + 1);
        onNumericMessage(null, ((CharSequence) (obj1)), i);
          goto _L33
_L29:
        onNumericMessage(null, s1.substring(s1.indexOf(' ') + 1), i);
        obj1 = s1;
          goto _L33
_L25:
        obj1 = snip(s1, s1.indexOf(' ') + 1);
        if (!mConnected || i == 375)
        {
            if (i == 375)
            {
                onMotdStart(((String) (obj1)));
            }
            mConnected = true;
        } else
        if (i == 372)
        {
            onMotdLine(((String) (obj1)));
        } else
        if (i == 376)
        {
            onMotdEnd(((String) (obj1)));
        }
          goto _L33
_L21:
        obj1 = s1.substring(s1.indexOf(' ') + 1);
        i3 = ((String) (obj1)).indexOf(' ');
        onNumericMessage(((String) (obj1)).substring(0, i3), ((String) (obj1)).substring(i3 + 1), i);
        obj1 = s1;
          goto _L33
_L28:
        if (!mConnected)
        {
            onNumericMessage(null, s1.substring(s1.indexOf(':') + 1), i);
            mConnected = true;
        }
        obj1 = snip(s1, s1.indexOf(' ') + 1);
        onNumericMessage(null, ((CharSequence) (obj1)), i);
          goto _L33
_L23:
        obj3 = new StringTokenizer(s1);
        i3 = 0;
        obj1 = new String();
        i = ((StringTokenizer) (obj3)).countTokens();
        if (i > 0)
        {
            i *= 2;
        } else
        {
            i = (i + 1) * 2;
        }
        stringbuilder = new StringBuilder(i);
        i = i3;
_L37:
        if (((StringTokenizer) (obj3)).hasMoreTokens()) goto _L35; else goto _L34
_L35:
        switch (i)
        {
        default:
            stringbuilder.append(((StringTokenizer) (obj3)).nextToken()).append(" ");
            break;

        case 0: // '\0'
        case 1: // '\001'
            ((StringTokenizer) (obj3)).nextToken();
            i++;
            break;

        case 2: // '\002'
            obj1 = ((StringTokenizer) (obj3)).nextToken();
            i++;
            break;

        case 3: // '\003'
            stringbuilder.append(((StringTokenizer) (obj3)).nextToken().substring(1)).append(" ");
            i++;
            break;
        }
        if (true) goto _L37; else goto _L36
_L36:
_L34:
        if (!isChannelPrefix(((String) (obj1)).charAt(0)))
        {
            onUnparsableMessage(s);
            return;
        }
        onNamesList(((String) (obj1)), stringbuilder.toString());
        obj1 = s1;
          goto _L33
_L24:
        i3 = s1.indexOf(' ');
        if (s1.charAt(i3 + 1) == ':')
        {
            obj1 = s1.substring(i3 + 2);
        } else
        {
            obj1 = s1.substring(i3 + 1);
        }
        onNumericMessage(null, ((CharSequence) (obj1)), i);
          goto _L33
_L15:
        obj1 = s1.substring(s1.indexOf(' ', 1)).trim();
        i = ((String) (obj1)).indexOf(' ');
        on324(((String) (obj1)).substring(0, i), ((String) (obj1)).substring(i).trim());
        obj1 = s1;
          goto _L33
_L16:
        i = s1.lastIndexOf(' ');
        obj3 = s1.substring(i + 1, s1.length());
        obj1 = s1.substring(s1.indexOf(' ', 1) + 1, i);
        l4 = -1L;
        l5 = Long.parseLong(((String) (obj3)));
        l4 = l5 * 1000L;
_L38:
        if (l4 != -1L)
        {
            on329(((String) (obj1)), l4);
        }
        obj1 = s1;
          goto _L33
        obj3;
        onUnparsableMessage(s);
          goto _L38
_L20:
        obj1 = Utils.split(s1);
        onInviteSent(obj1[2], obj1[1]);
        obj1 = s1;
          goto _L33
_L17:
        obj1 = s1.substring(s1.indexOf(' ')).trim();
        on332(((String) (obj1)).substring(0, ((String) (obj1)).indexOf(' ')), null, false);
        obj1 = s1;
          goto _L33
_L18:
        obj1 = s1.substring(s1.indexOf(' ', s1.indexOf(' '))).trim();
        on332(((String) (obj1)).substring(0, ((String) (obj1)).indexOf(' ')), stripColours(((String) (obj1)).substring(((String) (obj1)).indexOf(' ') + 2)), true);
        obj1 = s1;
          goto _L33
_L19:
        obj3 = s1.substring(s1.indexOf(' ', s1.indexOf(' '))).trim();
        i = ((String) (obj3)).indexOf(' ');
        obj1 = ((String) (obj3)).substring(0, i);
        obj3 = Utils.split(((String) (obj3)).substring(i + 1));
        on333(((String) (obj1)), obj3[0], Long.parseLong(obj3[1]) * 1000L);
        obj1 = s1;
          goto _L33
_L30:
        onNumericMessage(null, s1.substring(s1.indexOf(' ') + 1), i);
        if (!mConnected)
        {
            on433();
        }
        obj1 = s1;
          goto _L33
_L27:
        i3 = s1.indexOf(' ');
        obj1 = s1.substring(i3 + 1);
        onNumericMessage(s1.substring(i3 + 1, s1.indexOf(':') - 1), ((CharSequence) (obj1)), i);
        obj1 = s1;
          goto _L33
_L8:
        i3 = s1.indexOf(' ');
        on001(s1.substring(0, i3));
        if (s1.charAt(i3 + 1) == ':')
        {
            obj1 = s1.substring(i3 + 2);
        } else
        {
            obj1 = s1.substring(i3);
        }
        onNumericMessage(null, ((CharSequence) (obj1)), i);
        onNetworkName(((String) (obj)));
          goto _L33
_L10:
        on221(snip(s1, s1.indexOf(' ') + 1));
        obj1 = s1;
          goto _L33
_L11:
        obj1 = snip(s1, s1.indexOf(' ') + 1);
        onNumericMessage(null, ((CharSequence) (obj1)), i);
          goto _L33
_L14:
        onNumericMessage(null, stripColours(s1.substring(s1.indexOf(' ') + 1)), i);
        obj1 = s1;
          goto _L33
_L13:
        onNumericMessage(null, s1.substring(s1.indexOf(' ') + 1), i);
        obj1 = s1;
          goto _L33
_L31:
        if (s1.indexOf(':') != -1)
        {
            obj3 = s1.substring(s1.indexOf(' ') + 1);
            obj1 = obj3;
            if (((String) (obj3)).charAt(0) == ':')
            {
                obj1 = ((String) (obj3)).substring(1);
            }
            onNumericMessage(null, ((CharSequence) (obj1)), i);
        }
        obj1 = s1;
          goto _L33
_L22:
        obj1 = s1.substring(s1.indexOf(' ') + 1);
        onNumericMessage("", ((CharSequence) (obj1)), i);
          goto _L33
_L12:
        obj1 = s1.substring(s1.indexOf(' ') + 1);
        onNumericMessage("", ((CharSequence) (obj1)), i);
        if (true) goto _L33; else goto _L39
_L39:
          goto _L6
_L9:
        synchronized (sNetworkMatcher)
        {
            if (sNetworkMatcher.reset(s1).find())
            {
                onNetworkName(sNetworkMatcher.group(1));
            }
        }
        s1 = s1.substring(s1.indexOf(' ') + 1);
        onISupport(s1);
          goto _L7
        s;
        obj1;
        JVM INSTR monitorexit ;
        throw s;
_L32:
        if (s21.equals("JOIN"))
        {
            i = ((String) (obj)).indexOf('!');
            if (i == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, i);
            }
            if (i == -1)
            {
                obj3 = "";
            } else
            {
                obj3 = ((String) (obj)).substring(i + 1);
            }
            s13 = "";
            s19 = "";
            s1 = s13;
            obj = s19;
            if (((String) (obj3)).length() > 0)
            {
                i = ((String) (obj3)).indexOf('@');
                s1 = s13;
                obj = s19;
                if (i != -1)
                {
                    s1 = ((String) (obj3)).substring(0, i);
                    obj = ((String) (obj3)).substring(i + 1);
                }
            }
            if (((String) (obj1)).charAt(0) == ':')
            {
                obj1 = ((String) (obj1)).substring(1);
            }
            onUserJoined(((String) (obj1)), s, s1, ((String) (obj)));
            return;
        }
        if (s21.equals("PART"))
        {
            int j = ((String) (obj)).indexOf('!');
            Object obj2;
            String s9;
            if (j == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, j);
            }
            if (j == -1)
            {
                obj = "";
            } else
            {
                obj = ((String) (obj)).substring(j + 1);
            }
            j = ((String) (obj1)).indexOf(' ');
            if (j == -1)
            {
                obj2 = obj1;
            } else
            {
                obj2 = ((String) (obj1)).substring(0, j);
            }
            s9 = "";
            j = ((String) (obj1)).indexOf(':');
            if (j != -1)
            {
                s9 = stripColours(((String) (obj1)).substring(j + 1));
            }
            onUserParted(((String) (obj2)), s, ((String) (obj)), s9);
            return;
        }
        if (s21.equals("QUIT"))
        {
            int k = ((String) (obj)).indexOf('!');
            if (k == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, k);
            }
            if (((String) (obj1)).length() > 0)
            {
                obj = ((String) (obj1)).substring(1);
            } else
            {
                obj = "";
            }
            onUserQuit(s, null, stripColours(((String) (obj))));
            return;
        }
        if (s21.equals("NICK"))
        {
            int l = ((String) (obj)).indexOf('!');
            String s2;
            int j3;
            if (l != -1)
            {
                s = ((String) (obj)).substring(0, l);
            } else
            {
                s = ((String) (obj));
            }
            if (((String) (obj1)).charAt(0) == ':')
            {
                obj1 = ((String) (obj1)).substring(1);
            }
            j3 = ((String) (obj)).indexOf('@');
            if (l == -1 || j3 == -1)
            {
                s2 = "";
            } else
            {
                s2 = ((String) (obj)).substring(l + 1, j3);
            }
            if (l == -1 || j3 == -1)
            {
                obj = "";
            } else
            {
                obj = ((String) (obj)).substring(j3 + 1);
            }
            onNickChanged(s, ((String) (obj1)), s2, ((String) (obj)));
            return;
        }
        if (s21.equals("NOTICE") || s21.equals("AUTH") || ((String) (obj)).equals("NOTICE"))
        {
            int i1 = ((String) (obj)).indexOf('!');
            if (i1 == -1)
            {
                s = null;
                if (isStatusPrefix(((String) (obj1)).charAt(0)))
                {
                    s = ((String) (obj1)).substring(1, ((String) (obj1)).indexOf(' '));
                }
                onNotice(null, null, null, null, ((String) (obj1)).substring(((String) (obj1)).indexOf(':') + 1), s);
                return;
            }
            String s10 = ((String) (obj1)).substring(0, ((String) (obj1)).indexOf(' '));
            if (isChannelPrefix(s10.charAt(0)) && !isChannelPrefix(s10.charAt(1)))
            {
                int k3 = ((String) (obj)).indexOf('@');
                String s3;
                if (k3 == -1)
                {
                    s = "";
                } else
                {
                    s = ((String) (obj)).substring(i1 + 1, k3);
                }
                if (k3 == -1)
                {
                    s3 = "";
                } else
                {
                    s3 = ((String) (obj)).substring(k3 + 1);
                }
                onNotice(s10, ((String) (obj)).substring(0, i1), s, s3, stripColours(((String) (obj1)).substring(((String) (obj1)).indexOf(' ') + 2)), null);
                return;
            }
            char c = ((String) (obj1)).charAt(0);
            boolean flag = isStatusPrefix(c);
            s = s10;
            if (flag)
            {
                s = s10.substring(1);
            }
            int l3 = ((String) (obj)).indexOf('@');
            String s4;
            String s18;
            if (i1 == -1)
            {
                s4 = "";
            } else
            {
                s4 = ((String) (obj)).substring(i1 + 1, l3);
            }
            if (l3 == -1)
            {
                s10 = "";
            } else
            {
                s10 = ((String) (obj)).substring(l3 + 1);
            }
            s18 = ((String) (obj)).substring(0, i1);
            obj1 = stripColours(((String) (obj1)).substring(((String) (obj1)).indexOf(' ') + 2));
            if (flag)
            {
                obj = String.valueOf(c);
            } else
            {
                obj = null;
            }
            onNotice(s, s18, s4, s10, ((String) (obj1)), ((String) (obj)));
            return;
        }
        if (s21.equals("INVITE"))
        {
            int j1 = ((String) (obj)).indexOf('!');
            String s5;
            if (j1 == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, j1);
            }
            if (j1 == -1)
            {
                obj = "";
            } else
            {
                obj = ((String) (obj)).substring(j1 + 1);
            }
            s5 = ((String) (obj1)).substring(((String) (obj1)).indexOf(' ') + 1);
            obj1 = s5;
            if (s5.charAt(0) == ':')
            {
                obj1 = s5.substring(1);
            }
            onInvite(s, ((String) (obj)), ((String) (obj1)));
            return;
        }
        if (s21.equals("MODE"))
        {
            int k1 = ((String) (obj1)).indexOf(' ');
            if (k1 <= 0)
            {
                onUnparsableMessage(s);
                return;
            }
            String s6 = ((String) (obj1)).substring(0, k1);
            s = ((String) (obj1)).substring(k1 + 1);
            if (isChannelPrefix(s6.charAt(0)))
            {
                obj1 = s.trim();
                int l1 = ((String) (obj)).indexOf('!');
                s = ((String) (obj));
                if (l1 != -1)
                {
                    s = ((String) (obj)).substring(0, l1);
                }
                onModeChanged(s6, ((String) (obj1)), s);
                return;
            } else
            {
                onModeChanged(s6, s.substring(1), null);
                return;
            }
        }
        if (s21.equals("TOPIC"))
        {
            int i2 = ((String) (obj)).indexOf('!');
            if (i2 != -1)
            {
                obj = ((String) (obj)).substring(0, i2);
            }
            i2 = ((String) (obj1)).indexOf(' ');
            onTopicChanged(((String) (obj1)).substring(0, i2), stripColours(((String) (obj1)).substring(((String) (obj1)).indexOf(':', i2) + 1)), ((String) (obj)));
            return;
        }
        if (s21.equals("KICK"))
        {
            int j2 = ((String) (obj)).indexOf('!');
            String s7;
            String s11;
            if (j2 == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, j2);
            }
            s7 = ((String) (obj1)).substring(0, ((String) (obj1)).indexOf(' '));
            obj = "";
            s11 = Utils.split(((CharSequence) (obj1)))[1];
            j2 = ((String) (obj1)).indexOf(':');
            if (j2 != -1)
            {
                obj = ((String) (obj1)).substring(j2 + 1);
            }
            onUserKicked(s7, s11, s, ((String) (obj)));
            return;
        }
        if (((String) (obj)).equals("ERROR"))
        {
            obj = new StringBuilder();
            s = s21;
            if (s21.charAt(0) == ':')
            {
                s = s21.substring(1);
            }
            ((StringBuilder) (obj)).append(s);
            ((StringBuilder) (obj)).append(" ").append(((String) (obj1)));
            onError(((CharSequence) (obj)));
            return;
        }
        if (s21.equals("WALLOPS"))
        {
            int k2 = ((String) (obj)).indexOf('!');
            if (k2 == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, k2);
            }
            if (k2 == -1)
            {
                obj = "";
            } else
            {
                obj = ((String) (obj)).substring(k2 + 1, ((String) (obj)).length());
            }
            if (((String) (obj1)).charAt(0) == ':')
            {
                obj1 = ((String) (obj1)).substring(1);
            }
            onWallops(s, ((String) (obj)), ((String) (obj1)));
            return;
        }
        if (s21.equals("KILL"))
        {
            int l2 = ((String) (obj1)).indexOf(' ');
            String s8 = ((String) (obj1)).substring(0, l2);
            obj1 = ((String) (obj1)).substring(((String) (obj1)).indexOf(' ', l2 + 1) + 1, ((String) (obj1)).length());
            l2 = ((String) (obj)).indexOf('!');
            if (l2 == -1)
            {
                s = ((String) (obj));
            } else
            {
                s = ((String) (obj)).substring(0, l2);
            }
            if (l2 == -1)
            {
                obj = "";
            } else
            {
                obj = ((String) (obj)).substring(l2 + 1);
            }
            onKill(s8, s, ((String) (obj)), ((String) (obj1)));
            return;
        }
        if (s21.equals("PONG"))
        {
            onPong(((String) (obj1)).substring(((String) (obj1)).indexOf(' ') + 2));
            return;
        }
        if (s21.equals("CAP"))
        {
            s = Utils.split(((CharSequence) (obj1)));
            String as[] = new String[s.length - 1];
            System.arraycopy(s, 1, as, 0, as.length);
            onSaslMessage(s21, s[0], as);
            return;
        }
        onUnknownMessage(s);
        return;
          goto _L40
    }

    protected void reset()
    {
        mConnected = false;
    }

    public abstract String stripColours(String s);

}
