// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Backup;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Preferences, Main

public static final class _cls6.this._cls1 extends PreferenceParent
    implements android.preference.ClickListener
{

    private static final Comparator sFileComparator = new Comparator() {

        public int compare(File file, File file1)
        {
            long l = file.lastModified();
            long l1 = file1.lastModified();
            if (l > l1)
            {
                return -1;
            }
            return l >= l1 ? 0 : 1;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((File)obj, (File)obj1);
        }

    };
    private Backup mBackup;
    public final Handler mHandler = new Handler() {

        final Preferences.DataBackup this$1;

        public void handleMessage(Message message)
        {
            switch (message.what)
            {
            default:
                return;

            case 1: // '\001'
            case 2: // '\002'
                showDialog(1);
                return;

            case 0: // '\0'
                removeDialog(1);
                return;
            }
        }

            
            {
                this$1 = Preferences.DataBackup.this;
                super();
            }
    };
    private net.andchat.donate.Misc.ackup mTask;

    private void disablePreference(Preference preference, int i)
    {
        i;
        JVM INSTR tableswitch 0 1: default 24
    //                   0 30
    //                   1 39;
           goto _L1 _L2 _L3
_L1:
        preference.setEnabled(false);
        return;
_L2:
        preference.setTitle(0x7f0a0190);
        continue; /* Loop/switch isn't completed */
_L3:
        preference.setTitle(0x7f0a0191);
        if (true) goto _L1; else goto _L4
_L4:
    }

    private void enablePreference(Preference preference, int i)
    {
        i;
        JVM INSTR tableswitch 0 1: default 24
    //                   0 30
    //                   1 39;
           goto _L1 _L2 _L3
_L1:
        preference.setEnabled(true);
        return;
_L2:
        preference.setTitle(0x7f0a00d1);
        continue; /* Loop/switch isn't completed */
_L3:
        preference.setTitle(0x7f0a00d0);
        if (true) goto _L1; else goto _L4
_L4:
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if (i == 0 && j == -1)
        {
            disablePreference(findPreference("import_trigger"), 1);
            mTask = new net.andchat.donate.Misc.ackup.mTask(1, this, mBackup, new File(intent.getData().getPath()), mHandler);
            mTask.mTask();
        }
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(0x7f050003);
        findPreference("export_trigger").setOnPreferenceClickListener(this);
        findPreference("import_trigger").setOnPreferenceClickListener(this);
        Object aobj[] = (Object[])getLastNonConfigurationInstance();
        if (aobj != null)
        {
            if (aobj[0] == null)
            {
                bundle = new Backup();
            } else
            {
                bundle = (Backup)aobj[0];
            }
            mBackup = bundle;
            if (aobj[1] == null)
            {
                bundle = null;
            } else
            {
                bundle = (net.andchat.donate.Misc.ackup.mBackup)aobj[1];
            }
            mTask = bundle;
            if (mTask != null && mTask.g.get())
            {
                mTask.r = mHandler;
                if (mTask.mTask == 0)
                {
                    bundle = "export_trigger";
                } else
                {
                    bundle = "import_trigger";
                }
                disablePreference(findPreference(bundle), mTask.mTask);
            }
        }
        super.setTitle(0x7f0a0087);
    }

    protected Dialog onCreateDialog(int i)
    {
        switch (i)
        {
        default:
            return null;

        case 0: // '\0'
            final Dialog d = new Dialog(this);
            d.setTitle(0x7f0a017f);
            d.setContentView(0x7f030017);
            android.view.ences.DataBackup databackup = new android.view.View.OnClickListener() {

                final Preferences.DataBackup this$1;
                private final Dialog val$d;

                public void onClick(View view)
                {
                    switch (view.getId())
                    {
                    default:
                        return;

                    case 2131230807: 
                        d.dismiss();
                        return;

                    case 2131230806: 
                        d.dismiss();
                        view = new Intent("android.intent.action.GET_CONTENT");
                        view.setType("text/*");
                        view.addFlags(0x80000);
                        break;
                    }
                    if (getPackageManager().queryIntentActivities(view, 0x10000).size() > 0)
                    {
                        startActivityForResult(Intent.createChooser(view, getString(0x7f0a0180)), 0);
                        return;
                    } else
                    {
                        showDialog(2);
                        return;
                    }
                }

            
            {
                this$1 = Preferences.DataBackup.this;
                d = dialog;
                super();
            }
            };
            d.findViewById(0x7f080057).setOnClickListener(databackup);
            d.findViewById(0x7f080056).setOnClickListener(databackup);
            d.findViewById(0x7f080055).setOnClickListener(new android.view.View.OnClickListener() {

                final Preferences.DataBackup this$1;
                private final Dialog val$d;

                public void onClick(View view)
                {
                    view = (EditText)d.findViewById(0x7f080058);
                    disablePreference(findPreference("import_trigger"), 1);
                    view = view.getText().toString();
                    d.dismiss();
                    mTask = new net.andchat.donate.Misc.Backup.BackupOp(1, Preferences.DataBackup.this, mBackup, new File(view), mHandler);
                    mTask.start();
                }

            
            {
                this$1 = Preferences.DataBackup.this;
                d = dialog;
                super();
            }
            });
            return d;

        case 1: // '\001'
            android.app.rences.DataBackup databackup1 = new android.app.it>(this);
            net.andchat.donate.Misc.ackup ackup = mBackup.getStats();
            Object obj;
            final boolean export;
            final boolean failed;
            if (ackup.mBackup == 0)
            {
                export = true;
            } else
            {
                export = false;
            }
            if (ackup.mBackup != null)
            {
                failed = true;
            } else
            {
                failed = false;
            }
            if (export)
            {
                obj = "export_trigger";
            } else
            {
                obj = "import_trigger";
            }
            obj = findPreference(((CharSequence) (obj)));
            if (export)
            {
                i = 0;
            } else
            {
                i = 1;
            }
            enablePreference(((Preference) (obj)), i);
            databackup1.NeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final Preferences.DataBackup this$1;
                private final boolean val$export;
                private final boolean val$failed;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                    if (!failed && !export)
                    {
                        ((IRCApp)getApplication()).clearCrypt();
                        startActivity((new Intent(Preferences.DataBackup.this, net/andchat/donate/Activities/Main)).setFlags(0x4000000));
                    }
                }

            
            {
                this$1 = Preferences.DataBackup.this;
                failed = flag;
                export = flag1;
                super();
            }
            });
            if (export)
            {
                i = 0x7f0a0188;
            } else
            {
                i = 0x7f0a0187;
            }
            databackup1.Title(i);
            if (ackup.ackup != null)
            {
                if (export)
                {
                    i = 0x7f0a0184;
                } else
                {
                    i = 0x7f0a0183;
                }
                databackup1.Title(i);
                databackup1.Message((new StringBuilder(String.valueOf(getString(0x7f0a0186)))).append(" ").append(ackup.getString.getClass().getCanonicalName()).append("\n\n").append(getString(0x7f0a0185)).append(" ").append(ackup.getString.getMessage()).toString());
                databackup1.Icon(0x1080027);
                return databackup1.ate();
            }
            databackup1.Icon(0x108009b);
            StringBuilder stringbuilder;
            if (export)
            {
                i = 0x7f0a0182;
            } else
            {
                i = 0x7f0a0181;
            }
            databackup1.Title(i);
            if (export)
            {
                obj = "\n";
            } else
            {
                obj = "<br />";
            }
            stringbuilder = new StringBuilder();
            if (export)
            {
                i = 0x7f0a0188;
            } else
            {
                i = 0x7f0a0187;
            }
            stringbuilder.append(getString(i)).append(((String) (obj)));
            stringbuilder.append(getString(0x7f0a018c, new Object[] {
                Integer.valueOf(ackup.s), Integer.valueOf(ackup.rs)
            })).append(((String) (obj)));
            stringbuilder.append(getString(0x7f0a018d, new Object[] {
                Integer.valueOf(ackup.ences), Integer.valueOf(ackup.rences)
            })).append(((String) (obj)));
            if (ackup.rences)
            {
                if (export)
                {
                    i = 0x7f0a0193;
                } else
                {
                    i = 0x7f0a0192;
                }
                stringbuilder.append(getString(0x7f0a018e, new Object[] {
                    getString(i)
                })).append(((String) (obj)));
            }
            if (export)
            {
                stringbuilder.append(((String) (obj))).append(getString(0x7f0a018f, new Object[] {
                    ackup.getString
                }));
            } else
            {
                stringbuilder.append(((String) (obj))).append((new StringBuilder("<b>")).append(getString(0x7f0a0189)).append("</b>").toString());
            }
            if (export)
            {
                obj = stringbuilder;
            } else
            {
                obj = Html.fromHtml(stringbuilder.toString());
            }
            databackup1.Message(((CharSequence) (obj)));
            mTask = null;
            return databackup1.ate();

        case 2: // '\002'
            android.app.lickListener licklistener = new android.app.it>(this);
            licklistener.Icon(0x1080027);
            licklistener.Title(0x7f0a018a);
            licklistener.Message(0x7f0a018b);
            licklistener.NeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final Preferences.DataBackup this$1;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$1 = Preferences.DataBackup.this;
                super();
            }
            });
            return licklistener.ate();
        }
    }

    public boolean onPreferenceClick(Preference preference)
    {
        String s;
        mBackup = new Backup();
        s = preference.getKey();
        if (!s.equals("export_trigger")) goto _L2; else goto _L1
_L1:
        disablePreference(preference, 0);
        mTask = new net.andchat.donate.Misc.ackup.mTask(0, this, mBackup, new File(""), mHandler);
        mTask.mTask();
_L4:
        return true;
_L2:
        if (s.equals("import_trigger"))
        {
            showDialog(0);
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    protected void onPrepareDialog(int i, Dialog dialog)
    {
        EditText edittext;
        switch (i)
        {
        default:
            return;

        case 0: // '\0'
            edittext = (EditText)dialog.findViewById(0x7f080058);
            break;
        }
        if (mBackup != null && mBackup.getStats() != null)
        {
            dialog = mBackup.getStats().mBackup;
            if (dialog != null)
            {
                edittext.setText(dialog);
            } else
            {
                edittext.setText((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append(File.separator).toString());
            }
        } else
        {
            Object obj = null;
            File file = new File((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append(File.separator).append("net.andchat.donate").append(File.separator).append("Backups").toString());
            dialog = (File[])null;
            dialog = obj;
            if (file.isDirectory())
            {
                File afile[] = file.listFiles();
                dialog = obj;
                if (afile != null)
                {
                    if (afile.length > 1)
                    {
                        Arrays.sort(afile, sFileComparator);
                    }
                    if (afile.length > 0)
                    {
                        dialog = afile[0].toString();
                    } else
                    {
                        dialog = null;
                    }
                }
            }
            if (dialog != null)
            {
                edittext.setText(dialog);
            } else
            {
                edittext.setText((new StringBuilder()).append(Environment.getExternalStorageDirectory()).append(File.separator).toString());
            }
        }
        edittext.setSelection(edittext.length());
    }

    public Object onRetainNonConfigurationInstance()
    {
        if (mBackup == null && mTask == null)
        {
            return null;
        } else
        {
            return ((Object) (new Object[] {
                mBackup, mTask
            }));
        }
    }

    protected void onStart()
    {
        super.onStart();
        if (mTask != null)
        {
            mTask.log = true;
        }
    }

    protected void onStop()
    {
        super.onStop();
        if (mTask != null)
        {
            mTask.log = false;
        }
    }






    public _cls6.this._cls1()
    {
    }
}
