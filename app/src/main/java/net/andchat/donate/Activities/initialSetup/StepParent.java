// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import com.markupartist.android.widget.ActionBar;
import net.andchat.donate.IRCApp;

abstract class StepParent extends Activity
    implements android.view.View.OnClickListener
{

    protected ActionBar mActionBar;
    Button mNext;
    Button mPrev;
    Button mSkip;
    boolean mUseDrawables;

    StepParent()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mUseDrawables = IRCApp.LEGACY_VERSION;
        mPrev = (Button)findViewById(0x7f080071);
        mNext = (Button)findViewById(0x7f080073);
        mSkip = (Button)findViewById(0x7f080072);
        mPrev.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mSkip.setOnClickListener(this);
    }

    void removeDrawables()
    {
        mNext.setCompoundDrawables(null, null, null, null);
        mPrev.setCompoundDrawables(null, null, null, null);
        mSkip.setCompoundDrawables(null, null, null, null);
    }

    public void setTitle(int i)
    {
        if (mActionBar != null)
        {
            mActionBar.setTitle(i);
            return;
        } else
        {
            super.setTitle(i);
            return;
        }
    }
}
