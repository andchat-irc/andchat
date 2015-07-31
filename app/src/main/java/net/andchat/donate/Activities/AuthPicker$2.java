// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import net.andchat.donate.Misc.ServerProfile;

// Referenced classes of package net.andchat.donate.Activities:
//            AuthPicker

class val.saslCheckbox
    implements android.view.istener
{

    final AuthPicker this$0;
    private final CheckBox val$nickservCheckbox;
    private final EditText val$nickservPassword;
    private final CheckBox val$passwordCheckbox;
    private final EditText val$passwordEntry;
    private final CheckBox val$saslCheckbox;
    private final EditText val$saslPassword;
    private final EditText val$saslUsername;

    public void onClick(View view)
    {
        view = val$passwordEntry.getText().toString();
        String s = val$nickservPassword.getText().toString();
        String s1 = val$saslUsername.getText().toString();
        String s2 = val$saslPassword.getText().toString();
        if (val$passwordCheckbox.isChecked() && TextUtils.isEmpty(view))
        {
            val$passwordEntry.setError(getString(0x7f0a0159));
            val$passwordEntry.requestFocus();
            return;
        }
        if (val$nickservCheckbox.isChecked() && TextUtils.isEmpty(s))
        {
            val$nickservPassword.setError(getString(0x7f0a0159));
            val$nickservPassword.requestFocus();
            return;
        }
        if (val$saslCheckbox.isChecked())
        {
            if (TextUtils.isEmpty(s1))
            {
                val$saslUsername.setError(getString(0x7f0a015a));
                val$saslUsername.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(s2))
            {
                val$saslPassword.setError(getString(0x7f0a0159));
                val$saslPassword.requestFocus();
                return;
            }
        }
        Bundle bundle = new Bundle();
        if (val$passwordCheckbox.isChecked())
        {
            AuthPicker.access$0(AuthPicker.this).addAuthMode(net.andchat.donate.Misc..AuthenticationModes.MODE_PASSWORD);
            AuthPicker.access$0(AuthPicker.this).setServerPassword(view);
        } else
        {
            AuthPicker.access$0(AuthPicker.this).removeAuthMode(net.andchat.donate.Misc..AuthenticationModes.MODE_PASSWORD);
        }
        if (val$nickservCheckbox.isChecked())
        {
            AuthPicker.access$0(AuthPicker.this).addAuthMode(net.andchat.donate.Misc..AuthenticationModes.MODE_NICKSERV);
            AuthPicker.access$0(AuthPicker.this).setNickServDetails(s);
        } else
        {
            AuthPicker.access$0(AuthPicker.this).removeAuthMode(net.andchat.donate.Misc..AuthenticationModes.MODE_NICKSERV);
        }
        if (val$saslCheckbox.isChecked())
        {
            AuthPicker.access$0(AuthPicker.this).addAuthMode(net.andchat.donate.Misc..AuthenticationModes.MODE_SASL);
            AuthPicker.access$0(AuthPicker.this).setSASLDetails(s1, s2);
        } else
        {
            AuthPicker.access$0(AuthPicker.this).removeAuthMode(net.andchat.donate.Misc..AuthenticationModes.MODE_SASL);
        }
        bundle.putParcelable("server_profile", AuthPicker.access$0(AuthPicker.this));
        setResult(-1, (new Intent()).putExtras(bundle));
        finish();
    }

    nticationModes()
    {
        this$0 = final_authpicker;
        val$passwordEntry = edittext;
        val$nickservPassword = edittext1;
        val$saslUsername = edittext2;
        val$saslPassword = edittext3;
        val$passwordCheckbox = checkbox;
        val$nickservCheckbox = checkbox1;
        val$saslCheckbox = CheckBox.this;
        super();
    }
}
