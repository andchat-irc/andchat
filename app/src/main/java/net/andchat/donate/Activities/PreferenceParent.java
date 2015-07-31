// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import net.andchat.donate.IRCApp;

public class PreferenceParent extends PreferenceActivity
{

    public PreferenceParent()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        super.onCreate(bundle);
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            return;
        } else
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            return;
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        if (menuitem.getItemId() == 0x102002c)
        {
            finish();
        }
        return super.onOptionsItemSelected(menuitem);
    }

    public void setTitle(int i)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            ((com.markupartist.android.widget.ActionBar)getWindow().getDecorView().findViewById(0x7f080012)).setTitle(i);
            return;
        } else
        {
            super.setTitle(i);
            return;
        }
    }

    public void setTitle(CharSequence charsequence)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            ((com.markupartist.android.widget.ActionBar)getWindow().getDecorView().findViewById(0x7f080012)).setTitle(charsequence);
            return;
        } else
        {
            super.setTitle(charsequence);
            return;
        }
    }
}
