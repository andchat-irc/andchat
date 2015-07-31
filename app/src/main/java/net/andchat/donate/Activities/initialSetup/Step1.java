// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.markupartist.android.widget.ActionBar;
import net.andchat.donate.Activities.Main;
import net.andchat.donate.IRCApp;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            StepParent, Step2

public class Step1 extends StepParent
{

    public Step1()
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131230835: 
            startActivity(new Intent(this, net/andchat/donate/Activities/initialSetup/Step2));
            return;

        case 2131230834: 
            startActivity((new Intent(this, net/andchat/donate/Activities/Main)).setFlags(0x4000000));
            finish();
            return;
        }
    }

    public void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        setContentView(0x7f03000e);
        super.onCreate(bundle);
        mPrev.setVisibility(8);
        mSkip.setVisibility(0);
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            mActionBar = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
        }
        super.setTitle(0x7f0a008a);
        if (!mUseDrawables)
        {
            super.removeDrawables();
        }
    }

    public volatile void setTitle(int i)
    {
        super.setTitle(i);
    }
}
