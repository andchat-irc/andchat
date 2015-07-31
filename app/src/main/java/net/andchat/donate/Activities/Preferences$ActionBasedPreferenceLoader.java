// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import net.andchat.donate.IRCApp;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Preferences

public static final class  extends PreferenceParent
{

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        bundle = getIntent().getAction();
        int i;
        int j;
        if (bundle.equals("net.andchat.donate.Prefs.Messages_Rooms"))
        {
            j = 0x7f050008;
            i = 0x7f0a0080;
        } else
        if (bundle.equals("net.andchat.donate.Prefs.Interface"))
        {
            j = 0x7f050006;
            i = 0x7f0a0081;
        } else
        if (bundle.equals("net.andchat.donate.Prefs.Connection"))
        {
            j = 0x7f050002;
            i = 0x7f0a0088;
        } else
        if (bundle.equals("net.andchat.donate.Prefs.Notifications"))
        {
            j = 0x7f050009;
            i = 0x7f0a0082;
        } else
        if (bundle.equals("net.andchat.donate.Prefs.DEBUG"))
        {
            j = 0x7f050005;
            i = 0x7f0a0089;
        } else
        {
            throw new RuntimeException("missing action");
        }
        addPreferencesFromResource(j);
        if (i > 0)
        {
            super.setTitle(i);
        }
        if (j == 0x7f050006 && IRCApp.LEGACY_VERSION)
        {
            getPreferenceScreen().removePreference(findPreference(getString(0x7f0a0033)));
        }
    }

    public ()
    {
    }
}
