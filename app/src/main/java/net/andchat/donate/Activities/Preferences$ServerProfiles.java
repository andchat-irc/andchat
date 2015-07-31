// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Preferences, CharsetPicker

public static final class r extends PreferenceParent
    implements android.content.ener, android.content.ceClickListener, android.preference.kListener
{

    private int mApplyToMask;
    private Preference mEncoding;
    private final String mKeys[] = new String[2];

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        i;
        JVM INSTR tableswitch 5 5: default 28
    //                   5 29;
           goto _L1 _L2
_L1:
        return;
_L2:
        if (j != 0)
        {
            intent = intent.getStringExtra("selection");
            mEncoding.setTitle(intent);
            Utils.getPrefs(this).edit().tString(mKeys[0], intent).mmit();
            return;
        }
        if (true) goto _L1; else goto _L3
_L3:
    }

    public void onClick(DialogInterface dialoginterface, int i)
    {
        switch (i)
        {
        default:
            return;

        case -1: 
            int j = mApplyToMask;
            dialoginterface = Utils.getIRCDb(this);
            List list = dialoginterface.getList();
            int k = list.size();
            if (j == 0)
            {
                Toast.makeText(this, 0x7f0a0196, 0).show();
                return;
            }
            if (k == 0)
            {
                Toast.makeText(this, 0x7f0a0197, 0).show();
                return;
            }
            String s = Utils.getDefaultServerProfileValue(0x7f0a0001, this);
            String s1 = Utils.getDefaultServerProfileValue(0x7f0a0002, this);
            String s2 = Utils.getDefaultServerProfileValue(0x7f0a0003, this);
            String s3 = Utils.getDefaultServerProfileValue(0x7f0a0004, this);
            String s4 = Utils.getDefaultServerProfileValue(0x7f0a0005, this);
            String s5 = Utils.getDefaultServerProfileValue(0x7f0a0006, this);
            ArrayList arraylist = new ArrayList(k);
            i = 0;
            do
            {
                if (i >= k)
                {
                    dialoginterface.updateMultiple(arraylist);
                    Toast.makeText(this, getResources().getQuantityString(0x7f0e0002, k, new Object[] {
                        Integer.valueOf(k)
                    }), 0).show();
                    return;
                }
                ServerProfile serverprofile = dialoginterface.getDetailsForId(dialoginterface.getId((String)list.get(i)));
                if (Utils.isBitSet(j, 1))
                {
                    serverprofile.setNick(1, s);
                }
                if (Utils.isBitSet(j, 2))
                {
                    serverprofile.setNick(2, s1);
                }
                if (Utils.isBitSet(j, 4))
                {
                    serverprofile.setNick(3, s2);
                }
                if (Utils.isBitSet(j, 8))
                {
                    serverprofile.setRealname(s3);
                }
                if (Utils.isBitSet(j, 16))
                {
                    serverprofile.setUsername(s4);
                }
                if (Utils.isBitSet(j, 32))
                {
                    serverprofile.setCharset(s5);
                }
                arraylist.add(serverprofile);
                i++;
            } while (true);

        case -2: 
            dialoginterface.dismiss();
            return;
        }
    }

    public void onClick(DialogInterface dialoginterface, int i, boolean flag)
    {
        switch (i)
        {
        default:
            return;

        case 0: // '\0'
            if (flag)
            {
                i = mApplyToMask | 1;
            } else
            {
                i = mApplyToMask & -2;
            }
            mApplyToMask = i;
            return;

        case 1: // '\001'
            if (flag)
            {
                i = mApplyToMask | 2;
            } else
            {
                i = mApplyToMask & -3;
            }
            mApplyToMask = i;
            return;

        case 2: // '\002'
            if (flag)
            {
                i = mApplyToMask | 4;
            } else
            {
                i = mApplyToMask & -5;
            }
            mApplyToMask = i;
            return;

        case 3: // '\003'
            if (flag)
            {
                i = mApplyToMask | 8;
            } else
            {
                i = mApplyToMask & -9;
            }
            mApplyToMask = i;
            return;

        case 4: // '\004'
            if (flag)
            {
                i = mApplyToMask | 0x10;
            } else
            {
                i = mApplyToMask & 0xffffffef;
            }
            mApplyToMask = i;
            return;

        case 5: // '\005'
            break;
        }
        if (flag)
        {
            i = mApplyToMask | 0x20;
        } else
        {
            i = mApplyToMask & 0xffffffdf;
        }
        mApplyToMask = i;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(0x7f05000a);
        bundle = getString(0x7f0a000f);
        mKeys[0] = bundle;
        mEncoding = findPreference(bundle);
        Preference preference = mEncoding;
        preference.setTitle(Utils.getPrefs(this).getString(bundle, getString(0x7f0a0006)));
        preference.setOnPreferenceClickListener(this);
        bundle = getString(0x7f0a002d);
        mKeys[1] = bundle;
        findPreference(bundle).setOnPreferenceClickListener(this);
        super.setTitle(0x7f0a0084);
    }

    protected Dialog onCreateDialog(int i)
    {
        switch (i)
        {
        default:
            return null;

        case 0: // '\0'
            return (new android.app.Listener(this)).(0x108009b).iChoiceItems(0x7f0b0000, null, this).e(0x7f0a0194).tiveButton(0x7f0a0195, this).tiveButton(0x7f0a01d2, this).Listener();
        }
    }

    public boolean onPreferenceClick(Preference preference)
    {
        String as[] = mKeys;
        preference = preference.getKey();
        if (preference.equals(as[0]))
        {
            startActivityForResult(new Intent(this, net/andchat/donate/Activities/CharsetPicker), 5);
            return true;
        }
        if (preference.equals(as[1]))
        {
            showDialog(0);
            return true;
        } else
        {
            return false;
        }
    }

    public r()
    {
    }
}
