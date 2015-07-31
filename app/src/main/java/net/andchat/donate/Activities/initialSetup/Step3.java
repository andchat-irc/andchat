// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.markupartist.android.widget.ActionBar;
import java.io.IOException;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Backup;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            StepParent, Step4

public class Step3 extends StepParent
{

    public Step3()
    {
    }

    public void onClick(final View b)
    {
        switch (b.getId())
        {
        default:
            return;

        case 2131230834: 
            if (mNext.getTag() != null)
            {
                startActivity(new Intent(this, net/andchat/donate/Activities/initialSetup/Step4));
                return;
            }
            b = new Backup();
            try
            {
                b = new net.andchat.donate.Misc.Backup.BackupOp(1, this, b, getAssets().open("servers.xml"), new Handler() {

                    final Step3 this$0;
                    private final Backup val$b;

                    public void handleMessage(Message message)
                    {
                        int i;
                        switch (message.what)
                        {
                        case 1: // '\001'
                        default:
                            return;

                        case 0: // '\0'
                            ((TextView)findViewById(0x7f08004e)).append(getString(0x7f0a0169));
                            return;

                        case 2: // '\002'
                            i = b.getStats().goodServers;
                            break;
                        }
                        message = (TextView)findViewById(0x7f08004e);
                        message.append(getResources().getQuantityString(0x7f0e0001, i, new Object[] {
                            Integer.valueOf(i)
                        }));
                        message.append(" ");
                        message.append(getString(0x7f0a016a));
                        mNext.setEnabled(true);
                        mNext.setText(0x7f0a017c);
                        mNext.setTag(new Object());
                        if (mUseDrawables)
                        {
                            mNext.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(0x7f020017), null);
                        }
                        mSkip.setVisibility(8);
                    }

            
            {
                this$0 = Step3.this;
                b = backup;
                super();
            }
                });
                mNext.setEnabled(false);
                b.start();
                return;
            }
            // Misplaced declaration of an exception variable
            catch (final View b)
            {
                ((TextView)findViewById(0x7f08004e)).append(getString(0x7f0a016b, new Object[] {
                    b.getMessage()
                }));
            }
            mNext.setEnabled(true);
            mNext.setText(0x7f0a017c);
            mNext.setTag(new Object());
            if (mUseDrawables)
            {
                mNext.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(0x7f020017), null);
            }
            mSkip.setVisibility(8);
            b.printStackTrace();
            return;

        case 2131230833: 
            finish();
            return;

        case 2131230835: 
            startActivity(new Intent(this, net/andchat/donate/Activities/initialSetup/Step4));
            return;
        }
    }

    public void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        setContentView(0x7f030010);
        super.onCreate(bundle);
        mSkip.setVisibility(0);
        mSkip.setText(0x7f0a01d3);
        mNext.setText(0x7f0a01d4);
        if (!mUseDrawables)
        {
            super.removeDrawables();
        } else
        {
            mNext.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            mActionBar = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
        }
        super.setTitle(0x7f0a008c);
    }

    public volatile void setTitle(int i)
    {
        super.setTitle(i);
    }
}
