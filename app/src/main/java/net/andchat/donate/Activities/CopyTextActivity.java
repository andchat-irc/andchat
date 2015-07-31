// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.markupartist.android.widget.ActionBar;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;
import net.andchat.donate.View.TextStyleDialog;

public class CopyTextActivity extends Activity
    implements android.view.View.OnClickListener
{

    private ActionBar mActionBar;
    private EditText mEt;
    private boolean mHaveFormatting;
    private CharSequence mText;

    public CopyTextActivity()
    {
        mHaveFormatting = true;
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR tableswitch 2131230769 2131230769: default 24
    //                   2131230769 25;
           goto _L1 _L2
_L1:
        return;
_L2:
        int i = mEt.getSelectionStart();
        int j = mEt.getSelectionEnd();
        int k;
        if (mHaveFormatting)
        {
            mEt.setText(mText.toString());
            mHaveFormatting = false;
            ((Button)view).setText(0x7f0a015b);
        } else
        {
            mEt.setText(mText);
            mHaveFormatting = true;
            ((Button)view).setText(0x7f0a015c);
        }
        k = mEt.length();
        if (i <= k && j <= k)
        {
            mEt.setSelection(i, j);
            return;
        }
        if (true) goto _L1; else goto _L3
_L3:
    }

    protected void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        super.onCreate(bundle);
        setContentView(0x7f030008);
        bundle = getIntent();
        String s = bundle.getStringExtra("window");
        CharSequence charsequence = bundle.getCharSequenceExtra("text");
        EditText edittext = (EditText)findViewById(0x7f08002c);
        int i = Utils.getPrefs(this).getInt(getString(0x7f0a002f), 0);
        edittext.setTypeface(TextStyleDialog.TYPES[i]);
        edittext.setText(charsequence);
        edittext.setTextSize(bundle.getIntExtra("size", 13));
        mEt = edittext;
        mText = charsequence;
        findViewById(0x7f080031).setOnClickListener(this);
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            mActionBar = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
            (new net.andchat.donate.Misc.AbstractMenuInflator.ActionBarMenu(mActionBar, this)).addActionsFromXML(this, 0x7f0f0003);
            mActionBar.setOnActionClickListener(new com.markupartist.android.widget.ActionBar.ActionBarItemClickHandler() {

                final CopyTextActivity this$0;

                public void onActionBarItemClicked(com.markupartist.android.widget.ActionBar.GenericAction genericaction, View view)
                {
                    showDialog(0);
                }

            
            {
                this$0 = CopyTextActivity.this;
                super();
            }
            });
        } else
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(0x7f0a0094, new Object[] {
            s
        }));
    }

    public Dialog onCreateDialog(int i)
    {
        return (new android.app.AlertDialog.Builder(this)).setTitle(0x7f0a015e).setIcon(0x108009b).setMessage(0x7f0a015d).setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

            final CopyTextActivity this$0;

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            
            {
                this$0 = CopyTextActivity.this;
                super();
            }
        }).create();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f0f0003, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR lookupswitch 2: default 32
    //                   16908332: 34
    //                   2131230737: 41;
           goto _L1 _L2 _L3
_L1:
        return true;
_L2:
        finish();
        continue; /* Loop/switch isn't completed */
_L3:
        showDialog(0);
        if (true) goto _L1; else goto _L4
_L4:
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        mHaveFormatting = bundle.getBoolean("have_formatting", true);
        super.onRestoreInstanceState(bundle);
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        bundle.putBoolean("have_formatting", mHaveFormatting);
        super.onSaveInstanceState(bundle);
    }

    public void setTitle(CharSequence charsequence)
    {
        if (mActionBar != null)
        {
            mActionBar.setTitle(charsequence);
            return;
        } else
        {
            super.setTitle(charsequence);
            return;
        }
    }
}
