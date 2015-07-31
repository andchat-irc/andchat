// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;
import net.andchat.donate.Misc.Backup;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            Step3

class val.b extends Handler
{

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
            i = val$b.getStats().goodServers;
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

    ()
    {
        this$0 = final_step3;
        val$b = Backup.this;
        super();
    }
}
