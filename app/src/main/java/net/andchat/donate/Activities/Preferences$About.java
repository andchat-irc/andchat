// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import net.andchat.donate.IRCApp;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Preferences

public static final class _cls1.this._cls1 extends PreferenceParent
    implements android.preference.renceClickListener
{

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(0x7f050000);
        super.setTitle(0x7f0a0085);
        findPreference("about").setOnPreferenceClickListener(this);
        findPreference("changelog").setOnPreferenceClickListener(this);
        findPreference("credits").setOnPreferenceClickListener(this);
        bundle = (new StringBuilder("AndChat v")).append("1.4.3.2").append(" (");
        if (IRCApp.DONATE)
        {
            bundle.append("Donate");
        } else
        {
            bundle.append("Free");
        }
        bundle.append(")");
        findPreference("versioninfo").setTitle(bundle);
    }

    public Dialog onCreateDialog(int i)
    {
        Object obj;
        Object obj1;
        obj1 = "";
        obj = "";
        i;
        JVM INSTR tableswitch 0 2: default 32
    //                   0 126
    //                   1 146
    //                   2 163;
           goto _L1 _L2 _L3 _L4
_L1:
        obj1 = (new android.app.r(this)).setTitle(((CharSequence) (obj1))).setIcon(0x108009b);
        View view = getLayoutInflater().inflate(0x7f030019, null);
        TextView textview = (TextView)view.findViewById(0x7f08005f);
        textview.setTextSize(18F);
        textview.setText(((CharSequence) (obj)));
        textview.setMovementMethod(new LinkMovementMethod());
        ((android.app.r) (obj1)).setView(view);
        ((android.app.r) (obj1)).setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

            final Preferences.About this$1;

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            
            {
                this$1 = Preferences.About.this;
                super();
            }
        });
        return ((android.app.r) (obj1)).create();
_L2:
        obj1 = getString(0x7f0a01aa);
        obj = Html.fromHtml(getString(0x7f0a01ab));
        continue; /* Loop/switch isn't completed */
_L3:
        obj1 = getString(0x7f0a01ad);
        obj = getString(0x7f0a01ae);
        continue; /* Loop/switch isn't completed */
_L4:
        obj1 = getString(0x7f0a01af);
        obj = Html.fromHtml(getString(0x7f0a01b0));
        if (true) goto _L1; else goto _L5
_L5:
    }

    public boolean onPreferenceClick(Preference preference)
    {
        preference = preference.getKey();
        if (preference.equalsIgnoreCase("about"))
        {
            showDialog(0);
            return true;
        }
        if (preference.equalsIgnoreCase("changelog"))
        {
            showDialog(1);
            return true;
        }
        if (preference.equalsIgnoreCase("credits"))
        {
            showDialog(2);
            return true;
        } else
        {
            return false;
        }
    }

    public _cls1.this._cls1()
    {
    }
}
