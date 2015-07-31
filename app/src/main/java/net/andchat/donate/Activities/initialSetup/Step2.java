// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.markupartist.android.widget.ActionBar;
import net.andchat.donate.Activities.CharsetPicker;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            StepParent, Step3

public class Step2 extends StepParent
{

    private Button mCharset;
    private CheckBox mLogs;
    private EditText mNick1;
    private EditText mNick2;
    private EditText mNick3;
    private EditText mRealName;
    private EditText mUserName;

    public Step2()
    {
    }

    private void saveStuff()
    {
        String s = mNick1.getText().toString().trim();
        String s1 = mNick2.getText().toString().trim();
        String s2 = mNick3.getText().toString().trim();
        String s3 = mUserName.getText().toString().trim();
        String s4 = mRealName.getText().toString().trim();
        String s5 = mCharset.getText().toString();
        boolean flag = mLogs.isChecked();
        android.content.SharedPreferences.Editor editor = Utils.getPrefs(this).edit();
        editor.putString(getString(0x7f0a000a), s);
        editor.putString(getString(0x7f0a000b), s1);
        editor.putString(getString(0x7f0a000c), s2);
        editor.putString(getString(0x7f0a000d), s3);
        editor.putString(getString(0x7f0a000e), s4);
        editor.putString(getString(0x7f0a000f), s5);
        editor.putBoolean(getString(0x7f0a0027), flag);
        editor.commit();
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if (j == 0)
        {
            return;
        }
        switch (i)
        {
        default:
            return;

        case 2: // '\002'
            intent = intent.getStringExtra("selection");
            break;
        }
        mCharset.setText(intent);
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR lookupswitch 3: default 40
    //                   2131230797: 245
    //                   2131230833: 240
    //                   2131230835: 41;
           goto _L1 _L2 _L3 _L4
_L1:
        return;
_L4:
        boolean flag;
        flag = false;
        view = getString(0x7f0a016c);
        if (!TextUtils.isEmpty(mNick1.getText()))
        {
            break; /* Loop/switch isn't completed */
        }
        mNick1.setError(view);
        mNick1.requestFocus();
        flag = true;
_L6:
        if (!flag)
        {
            saveStuff();
            startActivity(new Intent(this, net/andchat/donate/Activities/initialSetup/Step3));
            return;
        }
        if (true) goto _L1; else goto _L5
_L5:
        if (TextUtils.isEmpty(mNick2.getText()))
        {
            mNick2.setError(view);
            mNick2.requestFocus();
            flag = true;
        } else
        if (TextUtils.isEmpty(mNick3.getText()))
        {
            mNick3.setError(view);
            mNick3.requestFocus();
            flag = true;
        } else
        if (TextUtils.isEmpty(mUserName.getText()))
        {
            mUserName.setError(view);
            mUserName.requestFocus();
            flag = true;
        } else
        if (TextUtils.isEmpty(mRealName.getText()))
        {
            mRealName.setError(view);
            mRealName.requestFocus();
            flag = true;
        }
          goto _L6
          goto _L1
_L3:
        finish();
        return;
_L2:
        startActivityForResult(new Intent(this, net/andchat/donate/Activities/CharsetPicker), 2);
        return;
    }

    public void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        setContentView(0x7f03000f);
        super.onCreate(bundle);
        if (!mUseDrawables)
        {
            super.removeDrawables();
        }
        bundle = Utils.getPrefs(this);
        TextWatcher textwatcher = new TextWatcher() {

            final Step2 this$0;

            public void afterTextChanged(Editable editable)
            {
                boolean flag;
                if (mNick1.getText().toString().trim().length() > 0 && mNick2.getText().toString().trim().length() > 0 && mNick3.getText().toString().trim().length() > 0 && mUserName.getText().toString().trim().length() > 0 && mRealName.getText().toString().trim().length() > 0)
                {
                    flag = true;
                } else
                {
                    flag = false;
                }
                mNext.setEnabled(flag);
            }

            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
            {
            }

            public void onTextChanged(CharSequence charsequence, int i, int j, int k)
            {
            }

            
            {
                this$0 = Step2.this;
                super();
            }
        };
        mNick1 = (EditText)findViewById(0x7f080042);
        mNick2 = (EditText)findViewById(0x7f080043);
        mNick3 = (EditText)findViewById(0x7f080044);
        mUserName = (EditText)findViewById(0x7f080045);
        mRealName = (EditText)findViewById(0x7f080046);
        mCharset = (Button)findViewById(0x7f08004d);
        mCharset.setText(Utils.getDefaultServerProfileValue(0x7f0a0006, this));
        mCharset.setOnClickListener(this);
        mLogs = (CheckBox)findViewById(0x7f08004a);
        mLogs.setChecked(bundle.getBoolean(getString(0x7f0a0027), false));
        mNick1.addTextChangedListener(textwatcher);
        mNick2.addTextChangedListener(textwatcher);
        mNick3.addTextChangedListener(textwatcher);
        mUserName.addTextChangedListener(textwatcher);
        mRealName.addTextChangedListener(textwatcher);
        mNick1.setText(Utils.getDefaultServerProfileValue(0x7f0a0001, this));
        mNick2.setText(Utils.getDefaultServerProfileValue(0x7f0a0002, this));
        mNick3.setText(Utils.getDefaultServerProfileValue(0x7f0a0003, this));
        mUserName.setText(Utils.getDefaultServerProfileValue(0x7f0a0005, this));
        mRealName.setText(Utils.getDefaultServerProfileValue(0x7f0a0004, this));
        mNick1.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

            final Step2 this$0;

            public void onFocusChange(View view, boolean flag)
            {
                if (!flag)
                {
                    view = ((EditText)view).getText().toString().trim();
                    if (view.length() > 0)
                    {
                        if (mNick2.getText().toString().length() == 0)
                        {
                            mNick2.setText(view);
                            mNick2.append("|");
                        }
                        if (mNick3.getText().toString().length() == 0)
                        {
                            mNick3.setText(view);
                            mNick3.append("-");
                        }
                    }
                    if (mRealName.getText().toString().length() == 0)
                    {
                        mRealName.setText(view);
                    }
                    if (mUserName.getText().toString().length() == 0)
                    {
                        mUserName.setText(view);
                    }
                }
            }

            
            {
                this$0 = Step2.this;
                super();
            }
        });
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            mActionBar = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
        }
        super.setTitle(0x7f0a008b);
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        bundle = bundle.getString("charset");
        mCharset.setText(bundle);
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putString("charset", mCharset.getText().toString());
    }

    public volatile void setTitle(int i)
    {
        super.setTitle(i);
    }





}
