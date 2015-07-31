// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.widget.Toast;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Preferences, PasswordActivity

public static final class  extends PreferenceParent
    implements android.preference.ickListener
{

    private int actType;
    private boolean wasChecked;

    protected void onActivityResult(int i, int j, Intent intent)
    {
        boolean flag;
        flag = true;
        super.onActivityResult(i, j, intent);
        i;
        JVM INSTR tableswitch 0 1: default 32
    //                   0 208
    //                   1 33;
           goto _L1 _L2 _L3
_L1:
        return;
_L3:
        if (actType != 0)
        {
            continue; /* Loop/switch isn't completed */
        }
        if (j == -1)
        {
            ((CheckBoxPreference)findPreference(getString(0x7f0a001d))).setChecked(false);
            i = Utils.getIRCDb(this).decryptAll();
            ((IRCApp)getApplication()).clearCrypt();
            ((CheckBoxPreference)findPreference(getString(0x7f0a001e))).setChecked(false);
            if (i == 0)
            {
                Toast.makeText(this, 0x7f0a019b, 0).show();
                return;
            } else
            {
                Toast.makeText(this, getResources().getQuantityString(0x7f0e0004, i, new Object[] {
                    Integer.valueOf(i)
                }), 0).show();
                return;
            }
        }
        continue; /* Loop/switch isn't completed */
        if (actType != 1 || j != -1) goto _L1; else goto _L4
_L4:
        i = Utils.getIRCDb(this).encryptAll();
        if (i == 0)
        {
            Toast.makeText(this, 0x7f0a019a, 0).show();
            return;
        } else
        {
            Toast.makeText(this, getResources().getQuantityString(0x7f0e0003, i, new Object[] {
                Integer.valueOf(i)
            }), 0).show();
            return;
        }
_L2:
        if (j != -1)
        {
            flag = false;
        }
        ((CheckBoxPreference)findPreference(getString(0x7f0a001d))).setChecked(flag);
        if (flag)
        {
            Toast.makeText(this, 0x7f0a019c, 0).show();
            return;
        }
        if (true) goto _L1; else goto _L5
_L5:
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(0x7f050004);
        findPreference(getString(0x7f0a001d)).setOnPreferenceClickListener(this);
        findPreference("trigger").setOnPreferenceClickListener(this);
        super.setTitle(0x7f0a0083);
    }

    public boolean onPreferenceClick(Preference preference)
    {
        String s = preference.getKey();
        if (!s.equals(getString(0x7f0a001d))) goto _L2; else goto _L1
_L1:
        preference = (CheckBoxPreference)preference;
        boolean flag;
        if (preference.isChecked())
        {
            flag = false;
        } else
        {
            flag = true;
        }
        wasChecked = flag;
        if (!wasChecked) goto _L4; else goto _L3
_L3:
        preference.setChecked(wasChecked);
        actType = 0;
        preference = (new StringBuilder()).append(getString(0x7f0a011e));
        if (wasChecked)
        {
            preference.append(getString(0x7f0a0198));
        }
        preference.append(":");
        startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 1).putExtra("message", preference.toString()), 1);
_L6:
        return false;
_L4:
        preference.setChecked(wasChecked);
        startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 0).putExtra("message", getString(0x7f0a0199)), 0);
        return false;
_L2:
        if (s.equals("trigger"))
        {
            if (!Utils.getCrypt(this).correctPass())
            {
                actType = 1;
                startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 1).putExtra("message", (new StringBuilder(String.valueOf(getString(0x7f0a011e)))).append(":").toString()), 1);
                return false;
            }
            int i = Utils.getIRCDb(this).encryptAll();
            if (i == 0)
            {
                Toast.makeText(this, 0x7f0a019a, 0).show();
                return false;
            } else
            {
                Toast.makeText(this, getResources().getQuantityString(0x7f0e0003, i, new Object[] {
                    Integer.valueOf(i)
                }), 0).show();
                return false;
            }
        }
        if (true) goto _L6; else goto _L5
_L5:
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        actType = bundle.getInt("acttype");
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putInt("acttype", actType);
    }

    public ()
    {
    }
}
