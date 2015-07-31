// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            PasswordActivity, CharsetPicker, AuthPicker, Main

public class ServerDetailsActivity extends Activity
    implements android.view.View.OnClickListener, com.markupartist.android.widget.ActionBar.ActionBarItemClickHandler
{

    private static boolean madeReq;
    private static boolean shouldClick;
    private int actType;
    private com.markupartist.android.widget.ActionBar mActions;
    private EditText mAddr;
    private boolean mAmEditing;
    private Button mAuthPicker;
    private EditText mAutojoin;
    private EditText mAutorun;
    private Animation mBounce;
    private Button mCharsetPicker;
    private IRCDb mDb;
    private ServerProfile mDetails;
    private Button mEnableLogging;
    private String mEncoding;
    private boolean mGlobalLoggingEnabled;
    private boolean mIsNew;
    private EditText mLabel;
    private CheckBox mLogs;
    private EditText mNick1;
    private EditText mNick2;
    private EditText mNick3;
    private EditText mPort;
    private ServerProfile mProfileCopy;
    private EditText mRealName;
    private CheckBox mSSL;
    private String mServLabel;
    private boolean mUseEncryption;
    private EditText mUserName;

    public ServerDetailsActivity()
    {
        mDb = null;
    }

    private void addServer()
    {
        String s = mLabel.getText().toString().trim();
        String s1 = mAddr.getText().toString().trim();
        if (s.equals(""))
        {
            mLabel.requestFocus();
            mLabel.setError(getString(0x7f0a0144));
            return;
        }
        if (s1.equals(""))
        {
            mAddr.requestFocus();
            mAddr.setError(getString(0x7f0a0145));
            return;
        } else
        {
            mProfileCopy.setServerDetails(s, s1, Utils.parseInt(mPort.getText().toString(), -1), mSSL.isChecked());
            s = mNick1.getText().toString().trim();
            s1 = mNick2.getText().toString().trim();
            String s2 = mNick3.getText().toString().trim();
            String s3 = mUserName.getText().toString().trim();
            String s4 = mRealName.getText().toString().trim();
            mProfileCopy.setUserDetails(s, s1, s2, s3, s4);
            s = mAutojoin.getText().toString().trim();
            s1 = mAutorun.getText().toString().trim();
            mProfileCopy.setOtherDetails(s, s1, mEncoding, mLogs.isChecked());
            commit(mProfileCopy);
            return;
        }
    }

    private void cancelAdd()
    {
        setResult(0);
        finish();
    }

    private void commit(ServerProfile serverprofile)
    {
        if (!mIsNew) goto _L2; else goto _L1
_L1:
        if (!mUseEncryption) goto _L4; else goto _L3
_L3:
        if (serverprofile.getAuthModes() <= 0) goto _L6; else goto _L5
_L5:
        if (Utils.getCrypt(this).correctPass()) goto _L8; else goto _L7
_L7:
        shouldClick = true;
        serverprofile = new Intent(this, net/andchat/donate/Activities/PasswordActivity);
        serverprofile.putExtra("action", 1);
        serverprofile.putExtra("message", getString(0x7f0a011d, new Object[] {
            getString(0x7f0a0121)
        }));
        actType = 1;
        startActivityForResult(serverprofile, 1);
_L11:
        return;
_L8:
        serverprofile.encryptSelf(Utils.getCrypt(this));
_L4:
        int i;
        serverprofile.setUsesEncryption(mUseEncryption);
        serverprofile.fillDefaults(this);
        serverprofile.fillPort(this);
        i = mDb.addServer(serverprofile);
        if (i == -2)
        {
            mLabel.requestFocus();
            mLabel.setError(getString(0x7f0a014a));
            return;
        }
        break; /* Loop/switch isn't completed */
_L6:
        mUseEncryption = false;
        if (true) goto _L4; else goto _L9
_L9:
        if (i != -1)
        {
            Toast.makeText(this, 0x7f0a014c, 0).show();
            setResult(-1, (new Intent()).putExtra("label", serverprofile.getName()));
            finish();
            return;
        } else
        {
            showDialog(3);
            return;
        }
_L2:
        if (mAmEditing)
        {
            int j;
            if (mUseEncryption)
            {
                if (serverprofile.getAuthModes() > 0)
                {
                    if (!Utils.getCrypt(this).correctPass())
                    {
                        shouldClick = true;
                        serverprofile = new Intent(this, net/andchat/donate/Activities/PasswordActivity);
                        serverprofile.putExtra("action", 1);
                        serverprofile.putExtra("message", getString(0x7f0a011d, new Object[] {
                            getString(0x7f0a0121)
                        }));
                        actType = 1;
                        startActivityForResult(serverprofile, 1);
                        return;
                    }
                    serverprofile.encryptSelf(Utils.getCrypt(this));
                } else
                {
                    mUseEncryption = false;
                }
            }
            serverprofile.setUsesEncryption(mUseEncryption);
            serverprofile.fillDefaults(this);
            serverprofile.fillPort(this);
            j = mDb.editServer(mServLabel, serverprofile);
            if (j == -2)
            {
                mLabel.requestFocus();
                mLabel.setError(getString(0x7f0a014a));
                return;
            }
            if (j > 0)
            {
                Toast.makeText(this, 0x7f0a014d, 0).show();
                setResult(-1, (new Intent()).putExtra("label", mServLabel));
                finish();
                return;
            } else
            {
                showDialog(3);
                return;
            }
        }
        if (true) goto _L11; else goto _L10
_L10:
    }

    private boolean shouldConfirmExit()
    {
        if (!mIsNew) goto _L2; else goto _L1
_L1:
        Object obj = Utils.getPrefs(this);
        if (mLabel.length() == 0 && mAddr.length() == 0 && mPort.length() == 0 && (mNick1.length() == 0 || mNick1.getText().toString().equals(((SharedPreferences) (obj)).getString(getString(0x7f0a000a), null))) && (mNick2.length() == 0 || mNick2.getText().toString().equals(((SharedPreferences) (obj)).getString(getString(0x7f0a000b), null))) && (mNick3.length() == 0 || mNick3.getText().toString().equals(((SharedPreferences) (obj)).getString(getString(0x7f0a000c), null))) && (mUserName.length() == 0 || mUserName.getText().toString().equals(((SharedPreferences) (obj)).getString(getString(0x7f0a000d), null))) && (mRealName.length() == 0 || mRealName.getText().toString().equals(((SharedPreferences) (obj)).getString(getString(0x7f0a000e), null))) && mAutojoin.length() == 0 && mAutorun.length() == 0 && mProfileCopy.getAuthModes() <= 0) goto _L4; else goto _L3
_L3:
        return true;
_L2:
        if (!mAmEditing)
        {
            break; /* Loop/switch isn't completed */
        }
        obj = mDetails;
        if (!mLabel.getText().toString().equals(((ServerProfile) (obj)).getName()) || !mAddr.getText().toString().equals(((ServerProfile) (obj)).getAddress()) || !mPort.getText().toString().equals(String.valueOf(((ServerProfile) (obj)).getPort())) || !mAutojoin.getText().toString().equals(((ServerProfile) (obj)).getAutojoinList()) || !mAutorun.getText().toString().equals(((ServerProfile) (obj)).getAutorunList()) || !mNick1.getText().toString().equals(((ServerProfile) (obj)).getNick(1)) || !mNick2.getText().toString().equals(((ServerProfile) (obj)).getNick(2)) || !mNick3.getText().toString().equals(((ServerProfile) (obj)).getNick(3)) || !mUserName.getText().toString().equals(((ServerProfile) (obj)).getUsername()) || !mRealName.getText().toString().equals(((ServerProfile) (obj)).getRealname())) goto _L3; else goto _L5
_L5:
        boolean flag;
        if (mSSL.isChecked())
        {
            flag = false;
        } else
        {
            flag = true;
        }
        if (flag == ((ServerProfile) (obj)).usesSSL()) goto _L3; else goto _L6
_L6:
        if (mLogs.isChecked())
        {
            flag = false;
        } else
        {
            flag = true;
        }
        if (flag == ((ServerProfile) (obj)).isLoggingEnabled() || !mCharsetPicker.getText().toString().equals(((ServerProfile) (obj)).getCharset()) || mProfileCopy.getAuthModes() != ((ServerProfile) (obj)).getAuthModes() || !mProfileCopy.getServerPassword().equals(((ServerProfile) (obj)).getServerPassword()) || !mProfileCopy.getNickservPassword().equals(((ServerProfile) (obj)).getNickservPassword()) || !mProfileCopy.getSASLUsername().equals(((ServerProfile) (obj)).getSASLUsername()) || !mProfileCopy.getSASLPassword().equals(((ServerProfile) (obj)).getSASLPassword())) goto _L3; else goto _L4
_L4:
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 4 && keyevent.getAction() == 0 && shouldConfirmExit())
        {
            showDialog(1);
            return true;
        } else
        {
            return super.dispatchKeyEvent(keyevent);
        }
    }

    public void onActionBarItemClicked(com.markupartist.android.widget.ActionBar.GenericAction genericaction, View view)
    {
        switch (genericaction.getDrawable())
        {
        default:
            throw new RuntimeException("Unhandled drawable");

        case 2130837553: 
            addServer();
            return;

        case 2130837543: 
            if (shouldConfirmExit())
            {
                showDialog(1);
                return;
            } else
            {
                cancelAdd();
                return;
            }

        case 2130837550: 
            showDialog(2);
            return;
        }
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        i;
        JVM INSTR tableswitch 1 7: default 52
    //                   1 53
    //                   2 52
    //                   3 52
    //                   4 52
    //                   5 229
    //                   6 256
    //                   7 279;
           goto _L1 _L2 _L1 _L1 _L1 _L3 _L4 _L5
_L1:
        return;
_L2:
        madeReq = false;
        actType;
        JVM INSTR tableswitch 0 1: default 84
    //                   0 85
    //                   1 202;
           goto _L6 _L7 _L8
_L6:
        return;
_L7:
        if (j == -1)
        {
            break; /* Loop/switch isn't completed */
        }
        if (j == 3)
        {
            Toast.makeText(this, 0x7f0a0120, 0).show();
        }
        if (mAmEditing)
        {
            finish();
            return;
        }
        if (true) goto _L1; else goto _L9
_L9:
        if (mDetails != null)
        {
            intent = Utils.getCrypt(this);
            if (!mDetails.isDecrypted())
            {
                mDetails.decryptSelf(intent);
                mProfileCopy.decryptSelf(intent);
            }
            mAddr.setText(mDetails.getAddress());
            mPort.setText(String.valueOf(mDetails.getPort()));
        }
        if (shouldClick)
        {
            shouldClick = false;
            addServer();
            return;
        }
        continue; /* Loop/switch isn't completed */
_L8:
        if (j == -1)
        {
            addServer();
            return;
        }
        if (j == 3)
        {
            Toast.makeText(this, 0x7f0a0120, 0).show();
            return;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        if (j != 0)
        {
            mEncoding = intent.getStringExtra("selection");
            mCharsetPicker.setText(mEncoding);
            return;
        }
        continue; /* Loop/switch isn't completed */
_L4:
        if (j == -1)
        {
            mProfileCopy.cloneAuthDetailsFrom((ServerProfile)intent.getParcelableExtra("server_profile"));
            return;
        }
        if (true) goto _L1; else goto _L5
_L5:
        intent = getString(0x7f0a0027);
        mGlobalLoggingEnabled = Utils.getPrefs(this).getBoolean(intent, false);
        if (mGlobalLoggingEnabled)
        {
            mEnableLogging.setVisibility(8);
            mLogs.setEnabled(true);
            mLogs.setVisibility(0);
            mLogs.setChecked(true);
            findViewById(0x7f080049).setVisibility(0);
            return;
        }
        if (true) goto _L1; else goto _L10
_L10:
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131230797: 
            startActivityForResult(new Intent(this, net/andchat/donate/Activities/CharsetPicker), 5);
            return;

        case 2131230796: 
            view.requestFocus();
            view = new Intent(this, net/andchat/donate/Activities/AuthPicker);
            view.putExtra("server_profile", mProfileCopy);
            startActivityForResult(view, 6);
            return;

        case 2131230795: 
            startActivityForResult(new Intent(this, net/andchat/donate/Activities/Preferences$ChatLogs), 7);
            return;
        }
    }

    protected void onCreate(Bundle bundle)
    {
        int i;
        boolean flag;
        boolean flag1;
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        } else
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        super.onCreate(bundle);
        mDb = Utils.getIRCDb(this);
        setContentView(0x7f03000d);
        bundle = Utils.getPrefs(this);
        if (!bundle.getBoolean("add_server_help_shown", false))
        {
            showDialog(2);
            bundle.edit().putBoolean("add_server_help_shown", true).commit();
        }
        mLabel = (EditText)findViewById(0x7f08003e);
        i = mLabel.getWidth();
        mLabel.setMaxWidth(i);
        mAddr = (EditText)findViewById(0x7f08003f);
        mAddr.setMaxWidth(i);
        mPort = (EditText)findViewById(0x7f080040);
        mPort.setMaxWidth(i);
        mSSL = (CheckBox)findViewById(0x7f080041);
        mLogs = (CheckBox)findViewById(0x7f08004a);
        mNick1 = (EditText)findViewById(0x7f080042);
        mNick1.setMaxWidth(i);
        mNick2 = (EditText)findViewById(0x7f080043);
        mNick2.setMaxWidth(i);
        mNick3 = (EditText)findViewById(0x7f080044);
        mNick3.setMaxWidth(i);
        mUserName = (EditText)findViewById(0x7f080045);
        mUserName.setMaxWidth(i);
        mRealName = (EditText)findViewById(0x7f080046);
        mRealName.setMaxWidth(i);
        mAutojoin = (EditText)findViewById(0x7f080047);
        mAutojoin.setMaxWidth(i);
        mAutorun = (EditText)findViewById(0x7f080048);
        mAutorun.setMaxWidth(i);
        mCharsetPicker = (Button)findViewById(0x7f08004d);
        mCharsetPicker.setOnClickListener(this);
        mAuthPicker = (Button)findViewById(0x7f08004c);
        mAuthPicker.setOnClickListener(this);
        mEnableLogging = (Button)findViewById(0x7f08004b);
        mEnableLogging.setOnClickListener(this);
        bundle = getIntent();
        flag = bundle.getBooleanExtra("new", false);
        flag1 = bundle.getBooleanExtra("edit", false);
        if (!flag && !flag1)
        {
            throw new IllegalArgumentException("No action specified!");
        }
        SharedPreferences sharedpreferences = Utils.getPrefs(this);
        mUseEncryption = sharedpreferences.getBoolean(getString(0x7f0a001d), false);
        mGlobalLoggingEnabled = sharedpreferences.getBoolean(getString(0x7f0a0027), false);
        if (!mGlobalLoggingEnabled)
        {
            mEnableLogging.setVisibility(0);
            mLogs.setVisibility(8);
            findViewById(0x7f080049).setVisibility(8);
        }
        mIsNew = flag;
        mAmEditing = flag1;
        if (flag1)
        {
            int j = bundle.getIntExtra("id", -1);
            mDetails = mDb.getDetailsForId(j);
            mProfileCopy = mDb.getDetailsForId(j);
            if (mDetails == null)
            {
                Toast.makeText(this, 0x7f0a01d8, 1).show();
                finish();
                return;
            }
            if (mDetails.usesEncryption())
            {
                bundle = Utils.getCrypt(this);
                String s;
                EditText edittext;
                if (!bundle.correctPass())
                {
                    if (!madeReq)
                    {
                        madeReq = true;
                        shouldClick = false;
                        bundle = new Intent(this, net/andchat/donate/Activities/PasswordActivity);
                        bundle.putExtra("action", 1);
                        bundle.putExtra("message", getString(0x7f0a011d, new Object[] {
                            getString(0x7f0a0122)
                        }));
                        actType = 0;
                        startActivityForResult(bundle, 1);
                    }
                } else
                {
                    mDetails.decryptSelf(bundle);
                    mProfileCopy.decryptSelf(bundle);
                }
            }
            mAddr.setText(mDetails.getAddress());
            mLabel.setText(mDetails.getName());
            mServLabel = mDetails.getName();
            mPort.setText(String.valueOf(mDetails.getPort()));
            mSSL.setChecked(mDetails.usesSSL());
            mLogs.setChecked(mDetails.isLoggingEnabled());
            s = mDetails.getNick(1);
            edittext = mNick1;
            bundle = s;
            if (TextUtils.isEmpty(s))
            {
                bundle = sharedpreferences.getString(getString(0x7f0a000a), null);
            }
            edittext.setText(bundle);
            s = mDetails.getNick(2);
            edittext = mNick2;
            bundle = s;
            if (TextUtils.isEmpty(s))
            {
                bundle = sharedpreferences.getString(getString(0x7f0a000b), null);
            }
            edittext.setText(bundle);
            s = mDetails.getNick(3);
            edittext = mNick3;
            bundle = s;
            if (TextUtils.isEmpty(s))
            {
                bundle = sharedpreferences.getString(getString(0x7f0a000c), null);
            }
            edittext.setText(bundle);
            s = mDetails.getUsername();
            edittext = mUserName;
            bundle = s;
            if (TextUtils.isEmpty(s))
            {
                bundle = sharedpreferences.getString(getString(0x7f0a000e), null);
            }
            edittext.setText(bundle);
            s = mDetails.getRealname();
            edittext = mRealName;
            bundle = s;
            if (TextUtils.isEmpty(s))
            {
                bundle = sharedpreferences.getString(getString(0x7f0a000d), null);
            }
            edittext.setText(bundle);
            mAutojoin.setText(mDetails.getAutojoinList());
            mAutorun.setText(mDetails.getAutorunList());
            s = mDetails.getCharset();
            bundle = s;
            if (TextUtils.isEmpty(s))
            {
                bundle = sharedpreferences.getString(getString(0x7f0a000f), "UTF-8");
            }
            mEncoding = bundle;
            mCharsetPicker.setText(bundle);
        } else
        {
            mProfileCopy = new ServerProfile();
            mSSL.setChecked(sharedpreferences.getBoolean(getString(0x7f0a0009), false));
            mNick1.setText(sharedpreferences.getString(getString(0x7f0a000a), null));
            mNick2.setText(sharedpreferences.getString(getString(0x7f0a000b), null));
            mNick3.setText(sharedpreferences.getString(getString(0x7f0a000c), null));
            mUserName.setText(sharedpreferences.getString(getString(0x7f0a000d), null));
            mRealName.setText(sharedpreferences.getString(getString(0x7f0a000e), null));
            mCharsetPicker.setText(sharedpreferences.getString(getString(0x7f0a000f), "UTF-8"));
            mLogs.setChecked(mGlobalLoggingEnabled);
        }
        mLabel.addTextChangedListener(new TextWatcher() {

            final ServerDetailsActivity this$0;

            public void afterTextChanged(Editable editable)
            {
                if (editable.length() > 0)
                {
                    mLabel.setError(null);
                    return;
                } else
                {
                    mLabel.setError(getString(0x7f0a0144));
                    return;
                }
            }

            public void beforeTextChanged(CharSequence charsequence, int k, int l, int i1)
            {
            }

            public void onTextChanged(CharSequence charsequence, int k, int l, int i1)
            {
            }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
        });
        mAddr.addTextChangedListener(new TextWatcher() {

            final ServerDetailsActivity this$0;

            public void afterTextChanged(Editable editable)
            {
                if (editable.length() > 0)
                {
                    mAddr.setError(null);
                    return;
                } else
                {
                    mAddr.setError(getString(0x7f0a0145));
                    return;
                }
            }

            public void beforeTextChanged(CharSequence charsequence, int k, int l, int i1)
            {
            }

            public void onTextChanged(CharSequence charsequence, int k, int l, int i1)
            {
            }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
        });
        mPort.addTextChangedListener(new TextWatcher() {

            final ServerDetailsActivity this$0;

            public void afterTextChanged(Editable editable)
            {
                editable = editable.toString();
                if (editable.length() <= 0)
                {
                    break MISSING_BLOCK_LABEL_126;
                }
                int k = Integer.parseInt(editable);
                if (k == 0)
                {
                    try
                    {
                        mPort.setError(getString(0x7f0a0146));
                        return;
                    }
                    // Misplaced declaration of an exception variable
                    catch (Editable editable)
                    {
                        mPort.setError(getString(0x7f0a0149));
                    }
                    break MISSING_BLOCK_LABEL_87;
                }
                if (k >= 0)
                {
                    break MISSING_BLOCK_LABEL_88;
                }
                mPort.setError(getString(0x7f0a0147));
                return;
                return;
                if (k <= 65535)
                {
                    break MISSING_BLOCK_LABEL_114;
                }
                mPort.setError(getString(0x7f0a0148));
                return;
                mPort.setError(null);
                return;
                mPort.setError(null);
                return;
            }

            public void beforeTextChanged(CharSequence charsequence, int k, int l, int i1)
            {
            }

            public void onTextChanged(CharSequence charsequence, int k, int l, int i1)
            {
            }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
        });
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            bundle = (com.markupartist.android.widget.ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
            (new net.andchat.donate.Misc.AbstractMenuInflator.ActionBarMenu(bundle, this)).addActionsFromXML(this, 0x7f0f0006);
            bundle.setOnActionClickListener(this);
            mActions = bundle;
        }
        if (flag)
        {
            setTitle(0x7f0a0092);
            return;
        } else
        {
            setTitle(getString(0x7f0a0093, new Object[] {
                mServLabel
            }));
            return;
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        android.app.AlertDialog.Builder builder2;
        switch (i)
        {
        default:
            return super.onCreateDialog(i);

        case 1: // '\001'
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle(0x7f0a014e);
            builder.setIcon(0x1080027);
            builder.setMessage(0x7f0a014f);
            builder.setPositiveButton(0x7f0a01d3, new android.content.DialogInterface.OnClickListener() {

                final ServerDetailsActivity this$0;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                    finish();
                }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
            });
            builder.setNegativeButton(0x7f0a01d4, new android.content.DialogInterface.OnClickListener() {

                final ServerDetailsActivity this$0;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
            });
            return builder.create();

        case 2: // '\002'
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
            builder1.setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final ServerDetailsActivity this$0;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
            });
            builder1.setTitle(0x7f0a0150);
            builder1.setIcon(0x108009b);
            View view = getLayoutInflater().inflate(0x7f030019, null);
            TextView textview = (TextView)view.findViewById(0x7f08005f);
            textview.setTextSize(18F);
            textview.setText(getString(0x7f0a0151));
            builder1.setView(view);
            return builder1.create();

        case 3: // '\003'
            builder2 = new android.app.AlertDialog.Builder(this);
            break;
        }
        builder2.setTitle(0x7f0a01d7);
        builder2.setIcon(0x1080027);
        builder2.setMessage(0x7f0a014b);
        builder2.setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

            final ServerDetailsActivity this$0;

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            
            {
                this$0 = ServerDetailsActivity.this;
                super();
            }
        });
        return builder2.create();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!IRCApp.LEGACY_VERSION)
        {
            getMenuInflater().inflate(0x7f0f0006, menu);
            return super.onCreateOptionsMenu(menu);
        } else
        {
            return true;
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR lookupswitch 4: default 48
    //                   16908332: 54
    //                   2131230737: 130
    //                   2131230853: 108
    //                   2131230854: 101;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        if (shouldConfirmExit())
        {
            showDialog(1);
        } else
        {
            Intent intent = new Intent(this, net/andchat/donate/Activities/Main);
            intent.addFlags(0x14000000);
            startActivity(intent);
            finish();
        }
        continue; /* Loop/switch isn't completed */
_L5:
        addServer();
        continue; /* Loop/switch isn't completed */
_L4:
        if (shouldConfirmExit())
        {
            showDialog(1);
        } else
        {
            cancelAdd();
        }
        continue; /* Loop/switch isn't completed */
_L3:
        showDialog(2);
        if (true) goto _L1; else goto _L6
_L6:
    }

    protected void onPrepareDialog(int i, Dialog dialog)
    {
        i;
        JVM INSTR tableswitch 1 1: default 20
    //                   1 21;
           goto _L1 _L2
_L1:
        return;
_L2:
        if ((dialog = dialog.findViewById(0x102001a)) != null)
        {
            dialog.requestFocus();
            return;
        }
        if (true) goto _L1; else goto _L3
_L3:
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
        mProfileCopy = (ServerProfile)bundle.getParcelable("profileCopy");
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("profileCopy", mProfileCopy);
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
