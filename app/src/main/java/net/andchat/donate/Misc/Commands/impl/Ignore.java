// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc.Commands.impl;

import android.content.res.Resources;
import java.util.List;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Commands.Command;
import net.andchat.donate.Misc.Utils;

public class Ignore extends Command
{

    public Ignore(String s)
    {
        super(s);
    }

    private String ignore(String s, String s1, Session session, net.andchat.donate.Misc.CommandParser.Helper helper, Ignores ignores, int i)
    {
        Resources resources = helper.getResources();
        if (s.equalsIgnoreCase(s1))
        {
            sendMessage(session.getSessionName(), resources.getString(0x7f0a01ca), 1, helper);
            return null;
        }
        s1 = session.getNickInfo(s);
        if (s1 == null)
        {
            sendMessage(session.getSessionName(), resources.getString(0x7f0a01cb, new Object[] {
                s
            }), 1, helper);
            return null;
        } else
        {
            ignores.addOrUpdateIgnore(s, ((net.andchat.donate.Backend.Sessions.Session.NickInfo) (s1)).ident, ((net.andchat.donate.Backend.Sessions.Session.NickInfo) (s1)).hostname, i);
            return s;
        }
    }

    private void ignoreUsage(net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        Resources resources = helper.getResources();
        sendMessage(helper.getCurrentSession().getSessionName(), resources.getString(0x7f0a01bf), 1, helper);
        sendMessage(helper.getCurrentSession().getSessionName(), resources.getString(0x7f0a01c0), 1, helper);
    }

    private void printIgnoreList(net.andchat.donate.Misc.CommandParser.Helper helper, String s, Ignores ignores)
    {
        Resources resources;
        StringBuilder stringbuilder;
        int i;
        resources = helper.getResources();
        ignores = ignores.getAllIgnores();
        int j = ignores.size();
        if (j > 0)
        {
            sendMessage(s, resources.getString(0x7f0a01c1), 1, helper);
            stringbuilder = new StringBuilder();
            i = 0;
            break MISSING_BLOCK_LABEL_52;
        } else
        {
            sendMessage(s, resources.getString(0x7f0a01c2), 1, helper);
            sendMessage(s, resources.getString(0x7f0a01c3), 1, helper);
            return;
        }
_L2:
        net.andchat.donate.Backend.Ignores.IgnoreInfo ignoreinfo;
        if (i >= j)
        {
            return;
        }
        stringbuilder.delete(0, stringbuilder.length());
        ignoreinfo = (net.andchat.donate.Backend.Ignores.IgnoreInfo)ignores.get(i);
        if (!Utils.isBitSet(ignoreinfo.mask, 15))
        {
            break; /* Loop/switch isn't completed */
        }
        stringbuilder.append(ignoreinfo.ident).append(": ").append(resources.getString(0x7f0a01c4));
_L3:
        sendMessage(s, stringbuilder.toString(), 1, helper);
        sendMessage(s, resources.getString(0x7f0a01c3), 1, helper);
        i++;
        if (true) goto _L2; else goto _L1
_L1:
        stringbuilder.append(ignoreinfo.ident).append(": ");
        boolean flag1 = false;
        if (Utils.isBitSet(ignoreinfo.mask, 1))
        {
            flag1 = true;
            stringbuilder.append(resources.getString(0x7f0a01c5));
        }
        boolean flag = flag1;
        if (Utils.isBitSet(ignoreinfo.mask, 8))
        {
            if (flag1)
            {
                stringbuilder.append(", ");
            }
            stringbuilder.append(resources.getString(0x7f0a01c6));
            flag = true;
        }
        flag1 = flag;
        if (Utils.isBitSet(ignoreinfo.mask, 4))
        {
            if (flag)
            {
                stringbuilder.append(", ");
            }
            stringbuilder.append(resources.getString(0x7f0a01c7));
            flag1 = true;
        }
        if (Utils.isBitSet(ignoreinfo.mask, 2))
        {
            if (flag1)
            {
                stringbuilder.append(", ");
            }
            stringbuilder.append(resources.getString(0x7f0a01c8));
        }
          goto _L3
        if (true) goto _L2; else goto _L4
_L4:
    }

    protected void execute(String s, String s1, net.andchat.donate.Misc.CommandParser.Helper helper)
    {
        Session session;
        Ignores ignores;
        ignores = helper.getIgnoreList();
        session = helper.getCurrentSession();
        if (session.getType() != 0 && s1 != null) goto _L2; else goto _L1
_L1:
        printIgnoreList(helper, session.getSessionName(), ignores);
_L21:
        return;
_L2:
        byte byte0;
        int i1;
        int j1;
        j1 = s1.length();
        if (j1 == 0)
        {
            ignoreUsage(helper);
            return;
        }
        byte0 = 0;
        i1 = 0;
        if (s1.charAt(0) == '-') goto _L4; else goto _L3
_L3:
        int i;
        int k;
        i = 15;
        k = i1;
_L18:
        byte0 = i;
        if (i == 0)
        {
            byte0 = 15;
        }
        if (k >= s1.length())
        {
            ignoreUsage(helper);
            return;
        }
        break; /* Loop/switch isn't completed */
_L4:
        int j = 0;
_L15:
        i = byte0;
        k = i1;
        if (j >= j1)
        {
            continue; /* Loop/switch isn't completed */
        }
        i = byte0;
        s1.charAt(j);
        JVM INSTR lookupswitch 8: default 212
    //                   32: 282
    //                   45: 216
    //                   97: 235
    //                   99: 245
    //                   104: 229
    //                   110: 263
    //                   112: 254
    //                   116: 272;
           goto _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13
_L13:
        break MISSING_BLOCK_LABEL_272;
_L6:
        break MISSING_BLOCK_LABEL_282;
_L10:
        break; /* Loop/switch isn't completed */
_L7:
        break; /* Loop/switch isn't completed */
_L5:
        i = byte0;
_L16:
        j++;
        byte0 = i;
        if (true) goto _L15; else goto _L14
_L14:
        ignoreUsage(helper);
        return;
_L8:
        i = byte0 | 0xf;
          goto _L16
_L9:
        i = byte0 | 1;
          goto _L16
_L12:
        i = byte0 | 2;
          goto _L16
_L11:
        i = byte0 | 4;
          goto _L16
        i = byte0 | 8;
          goto _L16
        k = j + 1;
        i = byte0;
        if (true) goto _L18; else goto _L17
_L17:
        String s2;
        String s3;
        s2 = s1.substring(k);
        char c = ',';
        j = s2.indexOf(',');
        s = (String[])null;
        i = j;
        if (j == -1)
        {
            c = ' ';
            i = s2.indexOf(' ');
        }
        if (i != -1)
        {
            s = s2.split(Character.toString(c));
        }
        s3 = helper.getCurrentNick();
        j = 0;
        i = 0;
        s1 = new StringBuilder();
        if (s == null)
        {
            break MISSING_BLOCK_LABEL_512;
        }
        i1 = s.length;
        j = 0;
_L22:
        if (j < i1) goto _L20; else goto _L19
_L19:
        if (i > 0)
        {
            s = helper.getResources();
            sendMessage(session.getSessionName(), s.getString(0x7f0a01c9, new Object[] {
                s1
            }), 1, helper);
            return;
        }
          goto _L21
_L20:
        s2 = ignore(s[j].trim(), s3, session, helper, ignores, byte0);
        int l = i;
        if (s2 != null)
        {
            s1.append(s2);
            if (j != i1 - 1)
            {
                s1.append(", ");
            }
            l = i + 1;
        }
        j++;
        i = l;
          goto _L22
        s = ignore(s2, s3, session, helper, ignores, byte0);
        i = j;
        if (s != null)
        {
            s1.append(s);
            i = 0 + 1;
        }
          goto _L19
    }
}
