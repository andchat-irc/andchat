// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.widget.CompoundButton;
import android.widget.EditText;

// Referenced classes of package net.andchat.donate.Activities:
//            AuthPicker

class val.saslPassword
    implements android.widget.n.OnCheckedChangeListener
{

    final AuthPicker this$0;
    private final EditText val$nickservPassword;
    private final EditText val$passwordEntry;
    private final EditText val$saslPassword;
    private final EditText val$saslUsername;

    public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
    {
        switch (compoundbutton.getId())
        {
        case 2131230776: 
        case 2131230778: 
        default:
            return;

        case 2131230775: 
            val$passwordEntry.setEnabled(flag);
            if (flag)
            {
                val$passwordEntry.requestFocus();
                return;
            } else
            {
                val$passwordEntry.setError(null);
                return;
            }

        case 2131230777: 
            val$nickservPassword.setEnabled(flag);
            if (flag)
            {
                val$nickservPassword.requestFocus();
                return;
            } else
            {
                val$nickservPassword.setError(null);
                return;
            }

        case 2131230779: 
            val$saslUsername.setEnabled(flag);
            val$saslPassword.setEnabled(flag);
            break;
        }
        if (flag)
        {
            val$saslUsername.requestFocus();
            return;
        } else
        {
            val$saslUsername.setError(null);
            val$saslPassword.setError(null);
            return;
        }
    }

    geListener()
    {
        this$0 = final_authpicker;
        val$passwordEntry = edittext;
        val$nickservPassword = edittext1;
        val$saslUsername = edittext2;
        val$saslPassword = EditText.this;
        super();
    }
}
