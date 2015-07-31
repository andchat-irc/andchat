// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.markupartist.android.widget.ActionBar;
import net.andchat.donate.Activities.Main;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            StepParent

public class Step4 extends StepParent
{

    public Step4()
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        case 2131230834: 
        default:
            return;

        case 2131230835: 
            view = Utils.getPrefs(this);
            if (!view.getBoolean("showed_help", false))
            {
                view.edit().putBoolean("showed_help", true).commit();
            }
            startActivity((new Intent(this, net/andchat/donate/Activities/Main)).setFlags(0x4000000));
            return;

        case 2131230833: 
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
        setContentView(0x7f030011);
        super.onCreate(bundle);
        if (!mUseDrawables)
        {
            super.removeDrawables();
        }
        bundle = (TextView)findViewById(0x7f08004e);
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(getString(0x7f0a016d));
        stringbuilder.append("<br /><br />");
        Utils.getHelpText(this, stringbuilder);
        bundle.setText(Html.fromHtml(stringbuilder.toString()));
        bundle.setMovementMethod(new LinkMovementMethod());
        mNext.setText(0x7f0a017e);
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            mActionBar = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
        }
        super.setTitle(0x7f0a008d);
    }

    public volatile void setTitle(int i)
    {
        super.setTitle(i);
    }
}
