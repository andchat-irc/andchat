// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

public class AuthPicker extends Activity
{

    private ServerProfile mEditingProfile;

    public AuthPicker()
    {
    }

    public void onCreate(final Bundle passwordCheckbox)
    {
        super.onCreate(passwordCheckbox);
        setContentView(0x7f03000c);
        mEditingProfile = (ServerProfile)getIntent().getParcelableExtra("server_profile");
        final EditText passwordEntry;
        final CheckBox nickservCheckbox;
        final EditText nickservPassword;
        final CheckBox saslCheckbox;
        final EditText saslUsername;
        final EditText saslPassword;
        android.widget.CompoundButton.OnCheckedChangeListener oncheckedchangelistener;
        if (mEditingProfile != null)
        {
            if (mEditingProfile.usesEncryption())
            {
                mEditingProfile.decryptSelf(Utils.getCrypt(this));
            }
        } else
        {
            mEditingProfile = new ServerProfile();
        }
        passwordCheckbox = (CheckBox)findViewById(0x7f080037);
        passwordEntry = (EditText)findViewById(0x7f080038);
        nickservCheckbox = (CheckBox)findViewById(0x7f080039);
        nickservPassword = (EditText)findViewById(0x7f08003a);
        saslCheckbox = (CheckBox)findViewById(0x7f08003b);
        saslUsername = (EditText)findViewById(0x7f08003c);
        saslPassword = (EditText)findViewById(0x7f08003d);
        oncheckedchangelistener = new android.widget.CompoundButton.OnCheckedChangeListener() {

            final AuthPicker this$0;
            private final EditText val$nickservPassword;
            private final EditText val$passwordEntry;
            private final EditText val$saslPassword;
            private final EditText val$saslUsername;

            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag3)
            {
                switch (compoundbutton.getId())
                {
                case 2131230776: 
                case 2131230778: 
                default:
                    return;

                case 2131230775: 
                    passwordEntry.setEnabled(flag3);
                    if (flag3)
                    {
                        passwordEntry.requestFocus();
                        return;
                    } else
                    {
                        passwordEntry.setError(null);
                        return;
                    }

                case 2131230777: 
                    nickservPassword.setEnabled(flag3);
                    if (flag3)
                    {
                        nickservPassword.requestFocus();
                        return;
                    } else
                    {
                        nickservPassword.setError(null);
                        return;
                    }

                case 2131230779: 
                    saslUsername.setEnabled(flag3);
                    saslPassword.setEnabled(flag3);
                    break;
                }
                if (flag3)
                {
                    saslUsername.requestFocus();
                    return;
                } else
                {
                    saslUsername.setError(null);
                    saslPassword.setError(null);
                    return;
                }
            }

            
            {
                this$0 = AuthPicker.this;
                passwordEntry = edittext;
                nickservPassword = edittext1;
                saslUsername = edittext2;
                saslPassword = edittext3;
                super();
            }
        };
        passwordCheckbox.setOnCheckedChangeListener(oncheckedchangelistener);
        nickservCheckbox.setOnCheckedChangeListener(oncheckedchangelistener);
        saslCheckbox.setOnCheckedChangeListener(oncheckedchangelistener);
        if (mEditingProfile != null)
        {
            ServerProfile serverprofile = mEditingProfile;
            int i = serverprofile.getAuthModes();
            boolean flag = Utils.isBitSet(i, net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_PASSWORD);
            boolean flag1 = Utils.isBitSet(i, net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_NICKSERV);
            boolean flag2 = Utils.isBitSet(i, net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_SASL);
            if (!flag)
            {
                passwordCheckbox.setChecked(false);
            } else
            {
                passwordCheckbox.setChecked(true);
                passwordEntry.setText(serverprofile.getServerPassword());
            }
            if (!flag1)
            {
                nickservCheckbox.setChecked(false);
            } else
            {
                nickservCheckbox.setChecked(true);
                nickservPassword.setText(serverprofile.getNickservPassword());
            }
            if (!flag2)
            {
                saslCheckbox.setChecked(false);
            } else
            {
                saslCheckbox.setChecked(true);
                saslUsername.setText(serverprofile.getSASLUsername());
                saslPassword.setText(serverprofile.getSASLPassword());
            }
        }
        findViewById(0x7f080067).setOnClickListener(new android.view.View.OnClickListener() {

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
                view = passwordEntry.getText().toString();
                String s = nickservPassword.getText().toString();
                String s1 = saslUsername.getText().toString();
                String s2 = saslPassword.getText().toString();
                if (passwordCheckbox.isChecked() && TextUtils.isEmpty(view))
                {
                    passwordEntry.setError(getString(0x7f0a0159));
                    passwordEntry.requestFocus();
                    return;
                }
                if (nickservCheckbox.isChecked() && TextUtils.isEmpty(s))
                {
                    nickservPassword.setError(getString(0x7f0a0159));
                    nickservPassword.requestFocus();
                    return;
                }
                if (saslCheckbox.isChecked())
                {
                    if (TextUtils.isEmpty(s1))
                    {
                        saslUsername.setError(getString(0x7f0a015a));
                        saslUsername.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(s2))
                    {
                        saslPassword.setError(getString(0x7f0a0159));
                        saslPassword.requestFocus();
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                if (passwordCheckbox.isChecked())
                {
                    mEditingProfile.addAuthMode(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_PASSWORD);
                    mEditingProfile.setServerPassword(view);
                } else
                {
                    mEditingProfile.removeAuthMode(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_PASSWORD);
                }
                if (nickservCheckbox.isChecked())
                {
                    mEditingProfile.addAuthMode(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_NICKSERV);
                    mEditingProfile.setNickServDetails(s);
                } else
                {
                    mEditingProfile.removeAuthMode(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_NICKSERV);
                }
                if (saslCheckbox.isChecked())
                {
                    mEditingProfile.addAuthMode(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_SASL);
                    mEditingProfile.setSASLDetails(s1, s2);
                } else
                {
                    mEditingProfile.removeAuthMode(net.andchat.donate.Misc.ServerProfile.AuthenticationModes.MODE_SASL);
                }
                bundle.putParcelable("server_profile", mEditingProfile);
                setResult(-1, (new Intent()).putExtras(bundle));
                finish();
            }

            
            {
                this$0 = AuthPicker.this;
                passwordEntry = edittext;
                nickservPassword = edittext1;
                saslUsername = edittext2;
                saslPassword = edittext3;
                passwordCheckbox = checkbox;
                nickservCheckbox = checkbox1;
                saslCheckbox = checkbox2;
                super();
            }
        });
        findViewById(0x7f080068).setOnClickListener(new android.view.View.OnClickListener() {

            final AuthPicker this$0;

            public void onClick(View view)
            {
                setResult(0);
                finish();
            }

            
            {
                this$0 = AuthPicker.this;
                super();
            }
        });
    }

}
