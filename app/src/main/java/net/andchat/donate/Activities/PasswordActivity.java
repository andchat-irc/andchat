// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import com.markupartist.android.widget.ActionBar;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;

public class PasswordActivity extends Activity
    implements android.content.DialogInterface.OnClickListener, com.markupartist.android.widget.ActionBar.ActionBarItemClickHandler
{

    private int mAction;
    private ActionBar mActions;
    private Animation mBounce;
    private EditText mPass1;
    private EditText mPass2;

    public PasswordActivity()
    {
    }

    private void handleCancel()
    {
        setResult(0);
        finish();
    }

    private void handleOk()
    {
        Object obj;
        String s;
        s = mPass1.getText().toString().trim();
        obj = mPass2.getText().toString().trim();
        mAction;
        JVM INSTR tableswitch 0 1: default 60
    //                   0 118
    //                   1 61;
           goto _L1 _L2 _L3
_L1:
        return;
_L3:
        if (s.length() == 0)
        {
            setResult(0);
            finish();
            return;
        }
        if (Utils.getCrypt(this).decryptMaster(s))
        {
            setResult(-1);
            finish();
            return;
        } else
        {
            setResult(3);
            mPass1.setError(getString(0x7f0a0120));
            return;
        }
_L2:
        boolean flag;
        int i;
        int j;
        flag = true;
        i = s.length();
        j = ((String) (obj)).length();
        if (i != 0) goto _L5; else goto _L4
_L4:
        mPass1.requestFocus();
        mPass1.setError(getString(0x7f0a0119));
        flag = false;
_L6:
        if (flag)
        {
            obj = Utils.getCrypt(this);
            EditText edittext;
            if (((Crypt) (obj)).storeMaster(s))
            {
                ((Crypt) (obj)).decryptMaster(s);
                setResult(-1);
                finish();
                return;
            } else
            {
                showDialog(0);
                return;
            }
        }
        if (true) goto _L1; else goto _L5
_L5:
        if (j == 0)
        {
            mPass2.requestFocus();
            mPass2.setError(getString(0x7f0a011a));
            flag = false;
        } else
        if (i <= 4 || j <= 4)
        {
            flag = false;
            if (i <= 4)
            {
                obj = mPass1;
            } else
            {
                obj = mPass2;
            }
            ((EditText) (obj)).requestFocus();
            ((EditText) (obj)).setError(getString(0x7f0a011b));
        } else
        if (!s.equals(obj))
        {
            if (mPass1.hasFocus())
            {
                obj = mPass1;
            } else
            if (mPass2.hasFocus())
            {
                obj = mPass2;
            } else
            {
                obj = null;
            }
            edittext = ((EditText) (obj));
            if (obj == null)
            {
                edittext = mPass2;
                edittext.requestFocus();
            }
            edittext.setError(getString(0x7f0a011c));
            flag = false;
        }
          goto _L6
    }

    public void onActionBarItemClicked(com.markupartist.android.widget.ActionBar.GenericAction genericaction, View view)
    {
        switch (genericaction.getDrawable())
        {
        default:
            throw new RuntimeException("Unhandled action image");

        case 2130837568: 
            handleOk();
            return;

        case 2130837543: 
            handleCancel();
            return;
        }
    }

    public void onClick(DialogInterface dialoginterface, int i)
    {
        dialoginterface.dismiss();
    }

    protected void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        super.onCreate(bundle);
        setContentView(0x7f03000b);
        bundle = getIntent();
        int i = bundle.getIntExtra("action", -1);
        if (i < 0)
        {
            throw new IllegalArgumentException("Unspecified action");
        }
        mAction = i;
        bundle = bundle.getStringExtra("message");
        if (bundle != null)
        {
            TextView textview = (TextView)findViewById(0x7f080034);
            textview.setVisibility(0);
            textview.setText(bundle);
        }
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            bundle = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
            (new net.andchat.donate.Misc.AbstractMenuInflator.ActionBarMenu(bundle, this)).addActionsFromXML(this, 0x7f0f0002);
            bundle.setOnActionClickListener(this);
            mActions = bundle;
        }
        mPass1 = (EditText)findViewById(0x7f080035);
        mPass2 = (EditText)findViewById(0x7f080036);
        bundle = new android.view.View.OnKeyListener() {

            final PasswordActivity this$0;

            public boolean onKey(View view, int j, KeyEvent keyevent)
            {
                if (j == 66 && keyevent.getAction() == 1)
                {
                    handleOk();
                    return true;
                } else
                {
                    return false;
                }
            }

            
            {
                this$0 = PasswordActivity.this;
                super();
            }
        };
        mPass1.setOnKeyListener(bundle);
        mPass2.setOnKeyListener(bundle);
        if (i != 0)
        {
            mPass2.setVisibility(8);
        } else
        {
            mPass1.setNextFocusDownId(0x7f080036);
        }
        switch (i)
        {
        default:
            return;

        case 0: // '\0'
            setTitle(0x7f0a0090);
            return;

        case 1: // '\001'
            setTitle(0x7f0a0091);
            break;
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(0x7f0a01d7);
        builder.setMessage(0x7f0a0118);
        builder.setIcon(0x1080027);
        builder.setNeutralButton(0x7f0a01d0, this);
        return builder.create();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!IRCApp.LEGACY_VERSION)
        {
            getMenuInflater().inflate(0x7f0f0002, menu);
            return super.onCreateOptionsMenu(menu);
        } else
        {
            return true;
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2131230842 2131230843: default 28
    //                   2131230842 41
    //                   2131230843 34;
           goto _L1 _L2 _L3
_L1:
        return super.onOptionsItemSelected(menuitem);
_L3:
        handleOk();
        continue; /* Loop/switch isn't completed */
_L2:
        handleCancel();
        if (true) goto _L1; else goto _L4
_L4:
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            Animation animation = mBounce;
            menu = animation;
            if (animation == null)
            {
                menu = AnimationUtils.loadAnimation(this, 0x7f040000);
                mBounce = menu;
            }
            mActions.startAnimation(menu);
        }
        return true;
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        mAction = bundle.getInt("action");
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putInt("action", mAction);
    }

    public void setTitle(CharSequence charsequence)
    {
        if (mActions != null)
        {
            mActions.setTitle(charsequence);
            return;
        } else
        {
            super.setTitle(charsequence);
            return;
        }
    }

}
