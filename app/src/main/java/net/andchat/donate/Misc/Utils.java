// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.IRCApp;

// Referenced classes of package net.andchat.donate.Misc:
//            ServerProfile, Colours

public final class Utils
{
    public static class ChannelSpan extends URLSpan
    {

        private String mText;

        public void onClick(View view)
        {
            view = view.getContext();
            if (view instanceof CommandParser.Helper)
            {
                ((CommandParser.Helper)view).handleJoin(mText);
            }
        }

        public ChannelSpan(String s)
        {
            super(s);
            mText = s;
        }
    }

    public static abstract class Detector extends android.view.GestureDetector.SimpleOnGestureListener
    {

        private static int $SWITCH_TABLE$net$andchat$donate$Misc$Utils$Detector$Y_DEVIATION[];
        private final int REL_SWIPE_MAX_OFF_PATH;
        private final int REL_SWIPE_THRESHOLD_VELOCITY;
        private MotionEvent event1;
        private boolean mHaveSwipe;

        static int[] $SWITCH_TABLE$net$andchat$donate$Misc$Utils$Detector$Y_DEVIATION()
        {
            int ai[] = $SWITCH_TABLE$net$andchat$donate$Misc$Utils$Detector$Y_DEVIATION;
            if (ai != null)
            {
                return ai;
            }
            ai = new int[Y_DEVIATION.values().length];
            try
            {
                ai[Y_DEVIATION.RELAXED.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) { }
            try
            {
                ai[Y_DEVIATION.STRICT.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) { }
            $SWITCH_TABLE$net$andchat$donate$Misc$Utils$Detector$Y_DEVIATION = ai;
            return ai;
        }

        public boolean haveSwipeAction()
        {
            return mHaveSwipe;
        }

        public final boolean onDown(MotionEvent motionevent)
        {
            mHaveSwipe = false;
            event1 = motionevent;
            return super.onDown(motionevent);
        }

        public final boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
        {
            MotionEvent motionevent2 = motionevent;
            if (motionevent == null)
            {
                motionevent2 = event1;
            }
            int i;
            if (motionevent2 == null || motionevent1 == null ? Math.abs(f1) > 600F : (i = (int)Math.abs(motionevent2.getY() - motionevent1.getY())) < 0 || i > REL_SWIPE_MAX_OFF_PATH)
            {
                return false;
            }
            if (f > 0.0F && f > (float)REL_SWIPE_THRESHOLD_VELOCITY)
            {
                mHaveSwipe = true;
                onLeftToRightSwipe();
                return true;
            }
            if (f < 0.0F && Math.abs(f) > (float)REL_SWIPE_THRESHOLD_VELOCITY)
            {
                mHaveSwipe = true;
                onRightToLeftSwipe();
                return true;
            } else
            {
                mHaveSwipe = false;
                return false;
            }
        }

        public abstract void onLeftToRightSwipe();

        public abstract void onRightToLeftSwipe();

        public final void onShowPress(MotionEvent motionevent)
        {
            mHaveSwipe = false;
            event1 = motionevent;
            super.onShowPress(motionevent);
        }

        public Detector(Context context)
        {
            this(context, Y_DEVIATION.RELAXED);
        }

        public Detector(Context context, Y_DEVIATION y_deviation)
        {
            $SWITCH_TABLE$net$andchat$donate$Misc$Utils$Detector$Y_DEVIATION()[y_deviation.ordinal()];
            JVM INSTR tableswitch 1 1: default 32
        //                       1 69;
               goto _L1 _L2
_L1:
            char c = '\226';
_L4:
            REL_SWIPE_MAX_OFF_PATH = (int)((float)(context.getResources().getDisplayMetrics().densityDpi * c) / 160F);
            REL_SWIPE_THRESHOLD_VELOCITY = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
            return;
_L2:
            c = '2';
            if (true) goto _L4; else goto _L3
_L3:
        }
    }

    public static final class Detector.Y_DEVIATION extends Enum
    {

        private static final Detector.Y_DEVIATION ENUM$VALUES[];
        public static final Detector.Y_DEVIATION RELAXED;
        public static final Detector.Y_DEVIATION STRICT;

        public static Detector.Y_DEVIATION valueOf(String s)
        {
            return (Detector.Y_DEVIATION)Enum.valueOf(net/andchat/donate/Misc/Utils$Detector$Y_DEVIATION, s);
        }

        public static Detector.Y_DEVIATION[] values()
        {
            Detector.Y_DEVIATION ay_deviation[] = ENUM$VALUES;
            int i = ay_deviation.length;
            Detector.Y_DEVIATION ay_deviation1[] = new Detector.Y_DEVIATION[i];
            System.arraycopy(ay_deviation, 0, ay_deviation1, 0, i);
            return ay_deviation1;
        }

        static 
        {
            STRICT = new Detector.Y_DEVIATION("STRICT", 0);
            RELAXED = new Detector.Y_DEVIATION("RELAXED", 1);
            ENUM$VALUES = (new Detector.Y_DEVIATION[] {
                STRICT, RELAXED
            });
        }

        private Detector.Y_DEVIATION(String s, int i)
        {
            super(s, i);
        }
    }

    public static interface ListSwipeListener
    {

        public abstract void onLeftToRightSwipe(Fragment fragment);

        public abstract void onRightToLeftSwipe(Fragment fragment);
    }

    public static class StringCompare
        implements Comparator
    {

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((String)obj, (String)obj1);
        }

        public int compare(String s, String s1)
        {
            boolean flag = s.equalsIgnoreCase("Status");
            boolean flag1 = s1.equalsIgnoreCase("Status");
            if (flag && flag1)
            {
                return 0;
            }
            if (flag && !flag1)
            {
                return -1;
            }
            if (!flag && flag1)
            {
                return 1;
            } else
            {
                return s.compareToIgnoreCase(s1);
            }
        }

        private StringCompare()
        {
        }

        StringCompare(StringCompare stringcompare)
        {
            this();
        }
    }


    private static final Matcher EMAIL = Pattern.compile((new StringBuilder(98)).append("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}").append("\\@").append("[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}").append("(").append("\\.").append("[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}").append(")+").toString()).matcher("");
    public static final StringCompare STRING_COMPARE = new StringCompare(null);
    private static final Matcher WEB = Pattern.compile((new StringBuilder(1987)).append("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)").append("\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_").append("\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?").append("((?:(?:[").append("a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF").append("][").append("a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF").append("\\-]{0,64}\\.)+").append("(?:").append("(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])").append("|(?:biz|b[abdefghijmnorstvwyz])").append("|(?:cat|com|coop|c[acdfghiklmnoruvxyz])").append("|d[ejkmoz]").append("|(?:edu|e[cegrstu])").append("|f[ijkmor]").append("|(?:gov|g[abdefghilmnpqrstuwy])").append("|h[kmnrtu]").append("|(?:info|int|i[delmnoqrst])").append("|(?:jobs|j[emop])").append("|k[eghimnprwyz]").append("|l[abcikrstuvy]").append("|(?:mil|mobi|museum|m[acdeghklmnopqrstuvwxyz])").append("|(?:name|net|n[acefgilopruz])").append("|(?:org|om)").append("|(?:pro|p[aefghklmnrstwy])").append("|qa").append("|r[eosuw]").append("|s[abcdeghijklmnortuvyz]").append("|(?:tel|travel|t[cdfghjklmnoprtvwz])").append("|u[agksyz]").append("|v[aceginu]").append("|w[fs]").append("|(?:\u03B4\u03BF\u03BA\u03B9\u03BC\u03AE|").append("\u0438\u0441\u043F\u044B\u0442\u0430\u043D").append("\u0438\u0435|\u0440\u0444|\u0441\u0440\u0431|").append("\u05D8\u05E2\u05E1\u05D8|\u0622\u0632\u0645\u0627").append("\u06CC\u0634\u06CC|\u0625\u062E\u062A\u0628\u0627").append("\u0631|\u0627\u0644\u0627\u0631\u062F\u0646|\u0627").append("\u0644\u062C\u0632\u0627\u0626\u0631|\u0627\u0644\u0633").append("\u0639\u0648\u062F\u064A\u0629|\u0627\u0644\u0645\u063A").append("\u0631\u0628|\u0627\u0645\u0627\u0631\u0627\u062A|\u0628").append("\u06BE\u0627\u0631\u062A|\u062A\u0648\u0646\u0633|\u0633").append("\u0648\u0631\u064A\u0629|\u0641\u0644\u0633\u0637\u064A\u0646|").append("\u0642\u0637\u0631|\u0645\u0635\u0631|\u092A\u0930\u0940\u0915").append("\u094D\u0937\u093E|\u092D\u093E\u0930\u0924|\u09AD\u09BE\u09B0").append("\u09A4|\u0A2D\u0A3E\u0A30\u0A24|\u0AAD\u0ABE\u0AB0\u0AA4|\u0B87").append("\u0BA8\u0BCD\u0BA4\u0BBF\u0BAF\u0BBE|\u0B87\u0BB2\u0B99\u0BCD").append("\u0B95\u0BC8|\u0B9A\u0BBF\u0B99\u0BCD\u0B95\u0BAA\u0BCD\u0BAA\u0BC2").append("\u0BB0\u0BCD|\u0BAA\u0BB0\u0BBF\u0B9F\u0BCD\u0B9A\u0BC8|\u0C2D\u0C3E").append("\u0C30\u0C24\u0C4D|\u0DBD\u0D82\u0D9A\u0DCF|\u0E44\u0E17\u0E22|\u30C6").append("\u30B9\u30C8|\u4E2D\u56FD|\u4E2D\u570B|\u53F0\u6E7E|\u53F0\u7063|\u65B0").append("\u52A0\u5761|\u6D4B\u8BD5|\u6E2C\u8A66|\u9999\u6E2F|\uD14C\uC2A4\uD2B8|").append("\uD55C\uAD6D|xn\\-\\-0zwm56d|xn\\-\\-11b5bs3a9aj6g|xn\\-\\-3e0b707e|").append("xn\\-\\-45brj9c|xn\\-\\-80akhbyknj4f|xn\\-\\-90a3ac|xn\\-\\-9t4b11yi5a|").append("xn\\-\\-clchc0ea0b2g2a9gcd|xn\\-\\-deba0ad|xn\\-\\-fiqs8s|xn\\-\\-fiqz9s|").append("xn\\-\\-fpcrj9c3d|xn\\-\\-fzc2c9e2c|xn\\-\\-g6w251d|xn\\-\\-gecrj9c|").append("xn\\-\\-h2brj9c|xn\\-\\-hgbk6aj7f53bba|xn\\-\\-hlcj6aya9esc7a|xn\\-\\-j6w193g|").append("xn\\-\\-jxalpdlp|xn\\-\\-kgbechtv|xn\\-\\-kprw13d|xn\\-\\-kpry57d|xn\\-\\-lgbbat1ad8j|").append("xn\\-\\-mgbaam7a8h|xn\\-\\-mgbayh7gpa|xn\\-\\-mgbbh1a71e|xn\\-\\-mgbc0a9azcg|").append("xn\\-\\-mgberp4a5d4ar|xn\\-\\-o3cw4h|xn\\-\\-ogbpf8fl|xn\\-\\-p1ai|xn\\-\\-pgbs0dh|").append("xn\\-\\-s9brj9c|xn\\-\\-wgbh1c|xn\\-\\-wgbl6a|xn\\-\\-xkc2al3hye2a|xn\\-\\-xkc2dl3a5ee0h|").append("xn\\-\\-yfro4i67o|xn\\-\\-ygbi2ammx|xn\\-\\-zckzah|xxx)").append("|y[et]").append("|z[amw]))").append("|(?:(?:25[0-5]|2[0-4]").append("[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]").append("|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]").append("[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}").append("|[1-9][0-9]|[0-9])))").append("(?:\\:\\d{1,5})?)").append("(\\/(?:(?:[").append("a-zA-Z0-9\240-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF").append("\\;\\/\\?\\:\\@\\&\\=\\#\\~").append("\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?").append("(?:\\b|$)").toString()).matcher("");
    private static final char escapees[] = {
        '|', ']', '[', '^', '{', '}', '\\', '+', '?', '*', 
        '.', '(', ')'
    };
    private static final Object sLock = new Object();
    private static final Pattern sSplitter = Pattern.compile(" ");
    private static final StringBuilder sb = new StringBuilder();

    public static SpannableStringBuilder addColour(boolean flag, SpannableStringBuilder spannablestringbuilder, int i, int j, int k)
    {
        int i1 = j;
        int l = k;
        if (flag)
        {
            i1 = j + (ServerConnection.sTimeLength + 3);
            l = k + (ServerConnection.sTimeLength + 3);
        }
        spannablestringbuilder.setSpan(new ForegroundColorSpan(i), i1, l, 33);
        return spannablestringbuilder;
    }

    public static SpannableStringBuilder addColourAndBold(boolean flag, SpannableStringBuilder spannablestringbuilder, int i, int j, int k)
    {
        addColour(flag, spannablestringbuilder, i, j, k);
        return makeBold(flag, spannablestringbuilder, j, k);
    }

    public static SpannableStringBuilder addColourAndBold(boolean flag, CharSequence charsequence, int i, int j, int k)
    {
        SpannableStringBuilder spannablestringbuilder;
        if (charsequence instanceof SpannableStringBuilder)
        {
            spannablestringbuilder = (SpannableStringBuilder)charsequence;
        } else
        {
            spannablestringbuilder = new SpannableStringBuilder();
        }
        if (flag)
        {
            getStyledTimeStamp(spannablestringbuilder, ServerConnection.getTime());
        }
        spannablestringbuilder.append(charsequence);
        makeBold(flag, spannablestringbuilder, j, k);
        addColour(flag, spannablestringbuilder, i, j, k);
        return spannablestringbuilder;
    }

    public static Spannable addLinks(Spannable spannable)
    {
        Object obj2 = sLock;
        obj2;
        JVM INSTR monitorenter ;
        Matcher matcher = WEB.reset(spannable);
_L6:
        if (matcher.find()) goto _L2; else goto _L1
_L1:
        Object obj = EMAIL.reset(spannable);
_L8:
        if (((Matcher) (obj)).find())
        {
            break MISSING_BLOCK_LABEL_202;
        }
        return spannable;
_L2:
        int l;
        int i1;
        sb.setLength(0);
        l = matcher.start();
        i1 = matcher.end();
        boolean flag = true;
        int j;
        obj = matcher.group();
        j = ((String) (obj)).indexOf("//");
        if (j == -1) goto _L4; else goto _L3
_L3:
        j += 2;
_L7:
        if (((String) (obj)).length() < j) goto _L6; else goto _L5
_L5:
        Object obj1;
        obj1 = obj;
        if (!flag)
        {
            break MISSING_BLOCK_LABEL_147;
        }
        sb.setLength(0);
        obj1 = ((String) (obj)).substring(0, j).toLowerCase();
        obj1 = sb.append(((String) (obj1))).append(((String) (obj)).substring(j)).toString();
        spannable.setSpan(new URLSpan(((String) (obj1))), l, i1, 33);
          goto _L6
        spannable;
        obj2;
        JVM INSTR monitorexit ;
        throw spannable;
_L4:
        flag = false;
        obj = sb.append("http://").append(((String) (obj))).toString();
        j = 7;
          goto _L7
        sb.setLength(0);
        int i = ((Matcher) (obj)).start();
        int k = ((Matcher) (obj)).end();
        spannable.setSpan(new URLSpan(sb.append("mailto:").append(((Matcher) (obj)).group()).toString()), i, k, 33);
          goto _L8
    }

    public static String addPadding(int i)
    {
        if (i <= 9)
        {
            return (new StringBuilder("0")).append(i).toString();
        } else
        {
            return String.valueOf(i);
        }
    }

    private static String buildModeString(String s, char c, char c1)
    {
        int i;
        int j;
        int k;
        j = s.indexOf(' ');
        if (j == -1)
        {
            return (new StringBuilder(String.valueOf(String.valueOf(c)))).append(String.valueOf(c1)).toString();
        }
        k = s.length();
        i = 1;
_L4:
        if (j + 1 >= k) goto _L2; else goto _L1
_L1:
        j = s.indexOf(' ', j + 1);
        if (j != -1) goto _L3; else goto _L2
_L2:
        s = new StringBuilder(k + i + 1);
        s.append(c);
        j = 0;
_L5:
        if (j > i)
        {
            return s.toString();
        }
        break MISSING_BLOCK_LABEL_115;
_L3:
        i++;
          goto _L4
        s.append(String.valueOf(c1));
        j++;
          goto _L5
    }

    public static String buildOpAction(String s, char c, char c1, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("MODE ").append(s1).append(" ").append(buildModeString(s, c, c1)).append(" ").append(stripCommas(s)).append("\r\n");
        return stringbuilder.toString();
    }

    public static SpannableStringBuilder createMessage(boolean flag, CharSequence charsequence)
    {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder();
        if (flag)
        {
            getStyledTimeStamp(spannablestringbuilder, ServerConnection.getTime());
        }
        spannablestringbuilder.append(charsequence);
        return spannablestringbuilder;
    }

    public static String escape(CharSequence charsequence)
    {
        Object obj;
        char ac[];
        int i;
        int j = 0;
        int k;
        if (charsequence instanceof String)
        {
            obj = (String)charsequence;
        } else
        {
            obj = charsequence.toString();
        }
        ac = escapees;
        k = ac.length;
        i = 0;
_L5:
        if (i >= k)
        {
            i = j;
        } else
        {
label0:
            {
                if (((String) (obj)).indexOf(ac[i]) == -1)
                {
                    break label0;
                }
                i = 1;
            }
        }
        if (i == 0) goto _L2; else goto _L1
_L1:
        j = charsequence.length();
        obj = new StringBuilder(j);
        i = 0;
_L6:
        if (i < j) goto _L4; else goto _L3
_L3:
        obj = ((StringBuilder) (obj)).toString();
_L2:
        return ((String) (obj));
        i++;
          goto _L5
_L4:
        char c;
        c = charsequence.charAt(i);
        switch (c)
        {
        default:
            ((StringBuilder) (obj)).append(c);
            break;

        case 40: // '('
        case 41: // ')'
        case 42: // '*'
        case 43: // '+'
        case 46: // '.'
        case 63: // '?'
        case 91: // '['
        case 92: // '\\'
        case 93: // ']'
        case 94: // '^'
        case 123: // '{'
        case 124: // '|'
        case 125: // '}'
            break MISSING_BLOCK_LABEL_255;
        }
_L7:
        i++;
          goto _L6
        ((StringBuilder) (obj)).append("\\").append(c);
          goto _L7
    }

    public static StringBuilder getConnectionMessage(Context context, boolean flag, ServerProfile serverprofile)
    {
        StringBuilder stringbuilder = new StringBuilder();
        int i;
        if (flag)
        {
            i = 0x7f0a004c;
        } else
        {
            i = 0x7f0a004b;
        }
        stringbuilder.append(context.getString(i, new Object[] {
            serverprofile.getAddress()
        }));
        stringbuilder.append(":");
        if (serverprofile.usesSSL())
        {
            stringbuilder.append("+");
        }
        stringbuilder.append(serverprofile.getPort());
        return stringbuilder;
    }

    public static Crypt getCrypt(Context context)
    {
        if (context instanceof IRCApp)
        {
            return ((IRCApp)context).getCrypt();
        }
        if (context instanceof Activity)
        {
            return ((IRCApp)((Activity)context).getApplication()).getCrypt();
        } else
        {
            throw new IllegalArgumentException();
        }
    }

    public static CharSequence getDay(int i, boolean flag)
    {
        switch (i)
        {
        default:
            return null;

        case 1: // '\001'
            if (flag)
            {
                return "Sun";
            } else
            {
                return "Sunday";
            }

        case 2: // '\002'
            if (flag)
            {
                return "Mon";
            } else
            {
                return "Monday";
            }

        case 3: // '\003'
            if (flag)
            {
                return "Tue";
            } else
            {
                return "Tuesday";
            }

        case 4: // '\004'
            if (flag)
            {
                return "Wed";
            } else
            {
                return "Wednesday";
            }

        case 5: // '\005'
            if (flag)
            {
                return "Thu";
            } else
            {
                return "Thursday";
            }

        case 6: // '\006'
            if (flag)
            {
                return "Fri";
            } else
            {
                return "Friday";
            }

        case 7: // '\007'
            break;
        }
        if (flag)
        {
            return "Sat";
        } else
        {
            return "Saturday";
        }
    }

    public static String getDefaultServerProfileValue(int i, Context context)
    {
        Object obj;
        Object obj1;
        int j;
        obj1 = getPrefs(context);
        int k = ((SharedPreferences) (obj1)).getInt(context.getString(0x7f0a002e), -1);
        j = k;
        if (k <= 0)
        {
            j = 4096;
        }
        obj = null;
        i;
        JVM INSTR tableswitch 2131361793 2131361798: default 76
    //                   2131361793 115
    //                   2131361794 133
    //                   2131361795 151
    //                   2131361796 187
    //                   2131361797 169
    //                   2131361798 205;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        if (obj == null) goto _L9; else goto _L8
_L8:
        obj1 = obj;
        if (!TextUtils.isEmpty(((CharSequence) (obj)))) goto _L10; else goto _L9
_L9:
        obj1 = (new StringBuilder(String.valueOf(context.getString(i)))).append(j).toString();
_L10:
        return ((String) (obj1));
_L2:
        obj = ((SharedPreferences) (obj1)).getString(context.getString(0x7f0a000a), null);
          goto _L1
_L3:
        obj = ((SharedPreferences) (obj1)).getString(context.getString(0x7f0a000b), null);
          goto _L1
_L4:
        obj = ((SharedPreferences) (obj1)).getString(context.getString(0x7f0a000c), null);
          goto _L1
_L6:
        obj = ((SharedPreferences) (obj1)).getString(context.getString(0x7f0a000d), null);
          goto _L1
_L5:
        obj = ((SharedPreferences) (obj1)).getString(context.getString(0x7f0a000e), null);
          goto _L1
_L7:
        return ((SharedPreferences) (obj1)).getString(context.getString(0x7f0a000f), context.getString(i));
    }

    public static void getHelpText(Activity activity, StringBuilder stringbuilder)
    {
        stringbuilder.append(activity.getString(0x7f0a01d9));
        if (IRCApp.LEGACY_VERSION)
        {
            stringbuilder.append(activity.getString(0x7f0a01db));
        } else
        {
            stringbuilder.append(activity.getString(0x7f0a01da));
        }
        stringbuilder.append(activity.getString(0x7f0a01dc));
    }

    public static IRCDb getIRCDb(Context context)
    {
        if (context instanceof IRCApp)
        {
            return ((IRCApp)context).getDb();
        }
        if (context instanceof Activity)
        {
            return ((IRCApp)((Activity)context).getApplication()).getDb();
        }
        if (context instanceof IRCService)
        {
            return ((IRCApp)((IRCService)context).getApplication()).getDb();
        } else
        {
            throw new IllegalArgumentException();
        }
    }

    public static CharSequence getMonth(int i, boolean flag)
    {
        switch (i)
        {
        default:
            return null;

        case 0: // '\0'
            if (flag)
            {
                return "Jan";
            } else
            {
                return "January";
            }

        case 1: // '\001'
            if (flag)
            {
                return "Feb";
            } else
            {
                return "February";
            }

        case 2: // '\002'
            if (flag)
            {
                return "Mar";
            } else
            {
                return "March";
            }

        case 3: // '\003'
            if (flag)
            {
                return "Apr";
            } else
            {
                return "April";
            }

        case 4: // '\004'
            return "May";

        case 5: // '\005'
            if (flag)
            {
                return "Jun";
            } else
            {
                return "June";
            }

        case 6: // '\006'
            if (flag)
            {
                return "Jul";
            } else
            {
                return "July";
            }

        case 7: // '\007'
            if (flag)
            {
                return "Aug";
            } else
            {
                return "August";
            }

        case 8: // '\b'
            if (flag)
            {
                return "Sept";
            } else
            {
                return "September";
            }

        case 9: // '\t'
            if (flag)
            {
                return "Oct";
            } else
            {
                return "October";
            }

        case 10: // '\n'
            if (flag)
            {
                return "Nov";
            } else
            {
                return "November";
            }

        case 11: // '\013'
            break;
        }
        if (flag)
        {
            return "Dec";
        } else
        {
            return "December";
        }
    }

    public static SpannableStringBuilder getPmStart(Context context, String s, boolean flag)
    {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder();
        if (flag)
        {
            getStyledTimeStamp(spannablestringbuilder, ServerConnection.getTime());
        }
        spannablestringbuilder.append(context.getString(0x7f0a004a, new Object[] {
            s
        }));
        int i = 0;
        if (flag)
        {
            i = 0 + (ServerConnection.sTimeLength + 3);
        }
        addColourAndBold(false, spannablestringbuilder, Colours.getInstance().getColourForEvent(0x7f0b0031), i, spannablestringbuilder.length());
        return spannablestringbuilder;
    }

    public static int getPositionForSection(int i, String as[], List list, SparseIntArray sparseintarray)
    {
        int j = i;
        if (i >= as.length)
        {
            j = as.length - 1;
        }
        j = as[j].charAt(0);
        i = sparseintarray.get(j, -1);
        if (i != -1)
        {
            return i;
        }
        int k = list.size();
        i = 0;
        do
        {
            if (i >= k)
            {
                return 0;
            }
            if (Character.toUpperCase(((String)list.get(i)).charAt(0)) == j)
            {
                sparseintarray.put(j, i);
                return i;
            }
            i++;
        } while (true);
    }

    public static SharedPreferences getPrefs(Context context)
    {
        if (context instanceof Activity)
        {
            return ((IRCApp)((Activity)context).getApplication()).getPrefs();
        }
        if (context instanceof Service)
        {
            return ((IRCApp)((Service)context).getApplication()).getPrefs();
        } else
        {
            throw new IllegalStateException();
        }
    }

    public static int getSectionForPosition(int i, List list, String as[])
    {
        int j;
        j = i;
        if (i > list.size())
        {
            j = list.size() - 1;
        }
        list = Character.toString(Character.toUpperCase(((String)list.get(j)).charAt(0)));
        j = Arrays.binarySearch(as, list);
        if (j < 0) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        char c = list.charAt(0);
        i = 0;
        do
        {
label0:
            {
                if (i < as.length)
                {
                    break label0;
                }
                if (j < 0)
                {
                    return 0;
                }
            }
            if (true)
            {
                continue;
            }
            if (c == as[i].charAt(0))
            {
                return i;
            }
            i++;
        } while (true);
        if (true) goto _L1; else goto _L3
_L3:
    }

    public static String[] getSectionsFromItems(List list)
    {
        int k = list.size();
        if (k != 0) goto _L2; else goto _L1
_L1:
        list = new String[0];
_L4:
        return list;
_L2:
        Object obj;
        int i;
        char c3;
        char c = Character.toUpperCase(((String)list.get(0)).charAt(0));
        obj = new StringBuilder();
        ((StringBuilder) (obj)).append(c);
        i = 1;
        c3 = c;
_L6:
        String s;
        int j;
        if (i < k)
        {
            break MISSING_BLOCK_LABEL_116;
        }
        s = ((StringBuilder) (obj)).toString();
        j = s.length();
        obj = new String[j];
        i = 0;
_L5:
        list = ((List) (obj));
        if (i >= j) goto _L4; else goto _L3
_L3:
        obj[i] = Character.toString(s.charAt(i));
        i++;
          goto _L5
          goto _L4
        char c1 = Character.toUpperCase(((String)list.get(i)).charAt(0));
        char c2 = c3;
        if (c1 != c3)
        {
            ((StringBuilder) (obj)).append(c1);
            c2 = c1;
        }
        i++;
        c3 = c2;
          goto _L6
    }

    public static SpannableStringBuilder getStyledTimeStamp(SpannableStringBuilder spannablestringbuilder, CharSequence charsequence)
    {
        synchronized (sLock)
        {
            sb.setLength(0);
            sb.append("[").append(charsequence).append("] ");
            spannablestringbuilder.append(sb);
            int i = spannablestringbuilder.length();
            int j = Colours.getInstance().getColourForEvent(0x7f0b0030);
            spannablestringbuilder.setSpan(new ForegroundColorSpan(j), 0, 1, 33);
            spannablestringbuilder.setSpan(new ForegroundColorSpan(j), i - 2, i, 33);
        }
        return spannablestringbuilder;
        spannablestringbuilder;
        obj;
        JVM INSTR monitorexit ;
        throw spannablestringbuilder;
    }

    public static Dialog getWhatsNewDialog(Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(0x7f0a01ac, new Object[] {
            "1.4.3.2"
        }));
        builder.setIcon(0x108009b);
        activity = activity.getLayoutInflater().inflate(0x7f030019, null);
        TextView textview = (TextView)activity.findViewById(0x7f08005f);
        textview.setTextSize(20F);
        textview.setText(0x7f0a007d);
        builder.setView(activity);
        builder.setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.dismiss();
            }

        });
        return builder.create();
    }

    public static void handleBanHammer(String s, CommandParser.Helper helper)
    {
        s = s.trim();
        boolean flag;
        if (s.indexOf(',') == -1 && s.indexOf(' ') == -1)
        {
            flag = false;
        } else
        {
            flag = true;
        }
        if (flag)
        {
            helper.handleOpAction(s, '+', 'b');
            return;
        } else
        {
            helper.writeToServer((new StringBuilder("MODE ")).append(helper.getCurrentSession().getSessionName()).append(" +b ").append(s).append("\r\n").toString());
            return;
        }
    }

    public static void handleKicking(String s, CommandParser.Helper helper)
    {
        Object obj = s.trim();
        int i;
        if (((String) (obj)).indexOf(',') == -1 && ((String) (obj)).indexOf(' ') == -1)
        {
            i = 0;
        } else
        {
            i = 1;
        }
        s = helper.getCurrentSession().getSessionName();
        if (i != 0)
        {
            StringBuilder stringbuilder = new StringBuilder();
            obj = ((String) (obj)).split(",");
            int j = obj.length;
            stringbuilder.append("KICK ").append(s).append(" ").append("\r\n");
            int k = stringbuilder.length() - 2;
            i = 0;
            do
            {
                if (i >= j)
                {
                    return;
                }
                stringbuilder.insert(k, obj[i].trim());
                helper.writeToServer(stringbuilder.toString());
                stringbuilder.delete(k, stringbuilder.length() - 2);
                i++;
            } while (true);
        } else
        {
            helper.writeToServer((new StringBuilder("KICK ")).append(s).append(" ").append(((String) (obj))).append("\r\n").toString());
            return;
        }
    }

    public static void handleQuit(final String reason, final IRCService service, final int id)
    {
        (new Thread() {

            private final int val$id;
            private final String val$reason;
            private final IRCService val$service;

            public void run()
            {
                String s = IRCService.sPreferences.quitReason;
                if (reason != null)
                {
                    IRCService.sPreferences.quitReason = reason;
                }
                service.stopConnection(id, 1);
                if (reason != null)
                {
                    IRCService.sPreferences.quitReason = s;
                }
            }

            
            {
                reason = s;
                service = ircservice;
                id = i;
                super();
            }
        }).start();
    }

    public static boolean isBitSet(int i, int j)
    {
        return (i & j) == j;
    }

    public static boolean isChannelPrefix(char c)
    {
        switch (c)
        {
        default:
            return false;

        case 33: // '!'
        case 35: // '#'
        case 38: // '&'
        case 43: // '+'
        case 126: // '~'
            return true;
        }
    }

    public static boolean isStatusPrefix(net.andchat.donate.Backend.Sessions.SessionManager.StatusMap statusmap, char c)
    {
        return statusmap.isPrefix(c);
    }

    public static SpannableStringBuilder makeBold(boolean flag, SpannableStringBuilder spannablestringbuilder, int i, int j)
    {
        int l = i;
        int k = j;
        if (flag)
        {
            l = i + (ServerConnection.sTimeLength + 3);
            k = j + (ServerConnection.sTimeLength + 3);
        }
        spannablestringbuilder.setSpan(new StyleSpan(1), l, k, 33);
        return spannablestringbuilder;
    }

    public static int parseInt(String s, int i)
    {
        int j;
        try
        {
            j = Integer.parseInt(s);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return i;
        }
        return j;
    }

    public static int parseInt(String s, int i, SharedPreferences sharedpreferences, String s1, String s2)
    {
        int j;
        try
        {
            j = Integer.parseInt(s);
        }
        catch (NumberFormatException numberformatexception)
        {
            Log.e(s2, (new StringBuilder("Couldn't parse ")).append(s).append(" as int. Using ").append(i).append(" instead.").toString());
            sharedpreferences.edit().putString(s1, String.valueOf(i)).commit();
            return i;
        }
        return j;
    }

    public static float pixelsToSp(Context context, float f)
    {
        return f / context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static String[] split(CharSequence charsequence)
    {
        return split(charsequence, -1);
    }

    public static String[] split(CharSequence charsequence, int i)
    {
        Pattern pattern = sSplitter;
        pattern;
        JVM INSTR monitorenter ;
        if (i > 0)
        {
            break MISSING_BLOCK_LABEL_22;
        }
        charsequence = sSplitter.split(charsequence);
_L1:
        pattern;
        JVM INSTR monitorexit ;
        return charsequence;
        charsequence = sSplitter.split(charsequence, i);
          goto _L1
        charsequence;
        pattern;
        JVM INSTR monitorexit ;
        throw charsequence;
    }

    private static String stripCommas(String s)
    {
        int i = s.indexOf(',');
        if (i == -1)
        {
            return s;
        }
        s = new StringBuilder(s);
        int j = s.length();
        do
        {
            if (i >= j)
            {
                return s.toString();
            }
            int k = j;
            if (s.charAt(i) == ',')
            {
                s.deleteCharAt(i);
                k = j - 1;
            }
            i++;
            j = k;
        } while (true);
    }

}
