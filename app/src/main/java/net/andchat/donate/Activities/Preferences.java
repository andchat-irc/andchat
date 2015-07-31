// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.andchat.donate.Activities.initialSetup.Step1;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Backup;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Main, PasswordActivity, CharsetPicker

public class Preferences extends PreferenceParent
    implements android.preference.Preference.OnPreferenceClickListener
{
    public static final class About extends PreferenceParent
        implements android.preference.Preference.OnPreferenceClickListener
    {

        protected void onCreate(Bundle bundle)
        {
            super.onCreate(bundle);
            addPreferencesFromResource(0x7f050000);
            super.setTitle(0x7f0a0085);
            findPreference("about").setOnPreferenceClickListener(this);
            findPreference("changelog").setOnPreferenceClickListener(this);
            findPreference("credits").setOnPreferenceClickListener(this);
            bundle = (new StringBuilder("AndChat v")).append("1.4.3.2").append(" (");
            if (IRCApp.DONATE)
            {
                bundle.append("Donate");
            } else
            {
                bundle.append("Free");
            }
            bundle.append(")");
            findPreference("versioninfo").setTitle(bundle);
        }

        public Dialog onCreateDialog(int i)
        {
            Object obj;
            Object obj1;
            obj1 = "";
            obj = "";
            i;
            JVM INSTR tableswitch 0 2: default 32
        //                       0 126
        //                       1 146
        //                       2 163;
               goto _L1 _L2 _L3 _L4
_L1:
            obj1 = (new android.app.AlertDialog.Builder(this)).setTitle(((CharSequence) (obj1))).setIcon(0x108009b);
            View view = getLayoutInflater().inflate(0x7f030019, null);
            TextView textview = (TextView)view.findViewById(0x7f08005f);
            textview.setTextSize(18F);
            textview.setText(((CharSequence) (obj)));
            textview.setMovementMethod(new LinkMovementMethod());
            ((android.app.AlertDialog.Builder) (obj1)).setView(view);
            ((android.app.AlertDialog.Builder) (obj1)).setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final About this$1;

                public void onClick(DialogInterface dialoginterface, int i)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$1 = About.this;
                super();
            }
            });
            return ((android.app.AlertDialog.Builder) (obj1)).create();
_L2:
            obj1 = getString(0x7f0a01aa);
            obj = Html.fromHtml(getString(0x7f0a01ab));
            continue; /* Loop/switch isn't completed */
_L3:
            obj1 = getString(0x7f0a01ad);
            obj = getString(0x7f0a01ae);
            continue; /* Loop/switch isn't completed */
_L4:
            obj1 = getString(0x7f0a01af);
            obj = Html.fromHtml(getString(0x7f0a01b0));
            if (true) goto _L1; else goto _L5
_L5:
        }

        public boolean onPreferenceClick(Preference preference)
        {
            preference = preference.getKey();
            if (preference.equalsIgnoreCase("about"))
            {
                showDialog(0);
                return true;
            }
            if (preference.equalsIgnoreCase("changelog"))
            {
                showDialog(1);
                return true;
            }
            if (preference.equalsIgnoreCase("credits"))
            {
                showDialog(2);
                return true;
            } else
            {
                return false;
            }
        }

        public About()
        {
        }
    }

    public static final class ActionBasedPreferenceLoader extends PreferenceParent
    {

        protected void onCreate(Bundle bundle)
        {
            super.onCreate(bundle);
            bundle = getIntent().getAction();
            int i;
            int j;
            if (bundle.equals("net.andchat.donate.Prefs.Messages_Rooms"))
            {
                j = 0x7f050008;
                i = 0x7f0a0080;
            } else
            if (bundle.equals("net.andchat.donate.Prefs.Interface"))
            {
                j = 0x7f050006;
                i = 0x7f0a0081;
            } else
            if (bundle.equals("net.andchat.donate.Prefs.Connection"))
            {
                j = 0x7f050002;
                i = 0x7f0a0088;
            } else
            if (bundle.equals("net.andchat.donate.Prefs.Notifications"))
            {
                j = 0x7f050009;
                i = 0x7f0a0082;
            } else
            if (bundle.equals("net.andchat.donate.Prefs.DEBUG"))
            {
                j = 0x7f050005;
                i = 0x7f0a0089;
            } else
            {
                throw new RuntimeException("missing action");
            }
            addPreferencesFromResource(j);
            if (i > 0)
            {
                super.setTitle(i);
            }
            if (j == 0x7f050006 && IRCApp.LEGACY_VERSION)
            {
                getPreferenceScreen().removePreference(findPreference(getString(0x7f0a0033)));
            }
        }

        public ActionBasedPreferenceLoader()
        {
        }
    }

    public static final class ChatLogs extends PreferenceParent
        implements android.preference.Preference.OnPreferenceClickListener
    {

        private long getFolderSize(File file, int i)
        {
            long l = 0L;
            if (i > 25)
            {
                return 0L;
            }
            file = file.listFiles();
            int j = file.length - 1;
            do
            {
                if (j < 0)
                {
                    return l;
                }
                File file1 = file[j];
                if (file1.isDirectory())
                {
                    int k = i + 1;
                    l += getFolderSize(file1, i);
                    i = k;
                } else
                {
                    l += file1.length();
                }
                j--;
            } while (true);
        }

        protected void onCreate(Bundle bundle)
        {
            super.onCreate(bundle);
            addPreferencesFromResource(0x7f050001);
            findPreference("info").setOnPreferenceClickListener(this);
            super.setTitle(0x7f0a0086);
        }

        protected Dialog onCreateDialog(int i)
        {
            android.app.AlertDialog.Builder builder;
            switch (i)
            {
            default:
                return null;

            case 0: // '\0'
                builder = new android.app.AlertDialog.Builder(this);
                break;
            }
            builder.setTitle(0x7f0a019d);
            builder.setIcon(0x108009b);
            StringBuilder stringbuilder = new StringBuilder();
            Object obj = Environment.getExternalStorageDirectory();
            stringbuilder.append(getString(0x7f0a019e)).append(obj).append(File.separator).append("net.andchat.donate\n");
            stringbuilder.append(getString(0x7f0a019f));
            obj = new File(((File) (obj)), "net.andchat.donate");
            TextView textview;
            if (!((File) (obj)).exists())
            {
                stringbuilder.append(getString(0x7f0a01a0));
            } else
            {
                long l1 = getFolderSize(((File) (obj)), 0);
                long l = l1;
                if (l1 != 0L)
                {
                    l = l1 / 1024L;
                }
                stringbuilder.append(">");
                if (l >= 1024L)
                {
                    stringbuilder.append(l / 1024L).append(" MB \n");
                } else
                {
                    stringbuilder.append(l).append(" KB \n");
                }
            }
            stringbuilder.append(getString(0x7f0a01a1));
            stringbuilder.append(Environment.getExternalStorageState()).append("\n");
            obj = getLayoutInflater().inflate(0x7f030019, null);
            textview = (TextView)((View) (obj)).findViewById(0x7f08005f);
            textview.setTextSize(20F);
            textview.setText(stringbuilder);
            builder.setView(((View) (obj)));
            builder.setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final ChatLogs this$1;

                public void onClick(DialogInterface dialoginterface, int i)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$1 = ChatLogs.this;
                super();
            }
            });
            return builder.create();
        }

        public boolean onPreferenceClick(Preference preference)
        {
            showDialog(0);
            return true;
        }

        public ChatLogs()
        {
        }
    }

    public static final class DataBackup extends PreferenceParent
        implements android.preference.Preference.OnPreferenceClickListener
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
        public final Handler mHandler = new _cls2();
        private net.andchat.donate.Misc.Backup.BackupOp mTask;

        private void disablePreference(Preference preference, int i)
        {
            i;
            JVM INSTR tableswitch 0 1: default 24
        //                       0 30
        //                       1 39;
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
        //                       0 30
        //                       1 39;
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
                mTask = new net.andchat.donate.Misc.Backup.BackupOp(1, this, mBackup, new File(intent.getData().getPath()), mHandler);
                mTask.start();
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
                    bundle = (net.andchat.donate.Misc.Backup.BackupOp)aobj[1];
                }
                mTask = bundle;
                if (mTask != null && mTask.mRunning.get())
                {
                    mTask.mHandler = mHandler;
                    if (mTask.opType == 0)
                    {
                        bundle = "export_trigger";
                    } else
                    {
                        bundle = "import_trigger";
                    }
                    disablePreference(findPreference(bundle), mTask.opType);
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
                Dialog dialog = new Dialog(this);
                dialog.setTitle(0x7f0a017f);
                dialog.setContentView(0x7f030017);
                android.view.View.OnClickListener onclicklistener = dialog. new android.view.View.OnClickListener() {

                    final DataBackup this$1;
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
                this$1 = final_databackup;
                d = Dialog.this;
                super();
            }
                };
                dialog.findViewById(0x7f080057).setOnClickListener(onclicklistener);
                dialog.findViewById(0x7f080056).setOnClickListener(onclicklistener);
                dialog.findViewById(0x7f080055).setOnClickListener(dialog. new android.view.View.OnClickListener() {

                    final DataBackup this$1;
                    private final Dialog val$d;

                    public void onClick(View view)
                    {
                        view = (EditText)d.findViewById(0x7f080058);
                        disablePreference(findPreference("import_trigger"), 1);
                        view = view.getText().toString();
                        d.dismiss();
                        mTask = new net.andchat.donate.Misc.Backup.BackupOp(1, DataBackup.this, mBackup, new File(view), mHandler);
                        mTask.start();
                    }

            
            {
                this$1 = final_databackup;
                d = Dialog.this;
                super();
            }
                });
                return dialog;

            case 1: // '\001'
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
                net.andchat.donate.Misc.Backup.Stats stats = mBackup.getStats();
                Object obj;
                boolean flag;
                final boolean failed;
                if (stats.opType == 0)
                {
                    flag = true;
                } else
                {
                    flag = false;
                }
                if (stats.exception != null)
                {
                    failed = true;
                } else
                {
                    failed = false;
                }
                if (flag)
                {
                    obj = "export_trigger";
                } else
                {
                    obj = "import_trigger";
                }
                obj = findPreference(((CharSequence) (obj)));
                if (flag)
                {
                    i = 0;
                } else
                {
                    i = 1;
                }
                enablePreference(((Preference) (obj)), i);
                builder1.setNeutralButton(0x7f0a01d0, flag. new android.content.DialogInterface.OnClickListener() {

                    final DataBackup this$1;
                    private final boolean val$export;
                    private final boolean val$failed;

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                        if (!failed && !export)
                        {
                            ((IRCApp)getApplication()).clearCrypt();
                            startActivity((new Intent(DataBackup.this, net/andchat/donate/Activities/Main)).setFlags(0x4000000));
                        }
                    }

            
            {
                this$1 = final_databackup;
                failed = flag;
                export = Z.this;
                super();
            }
                });
                if (flag)
                {
                    i = 0x7f0a0188;
                } else
                {
                    i = 0x7f0a0187;
                }
                builder1.setTitle(i);
                if (stats.exception != null)
                {
                    if (flag)
                    {
                        i = 0x7f0a0184;
                    } else
                    {
                        i = 0x7f0a0183;
                    }
                    builder1.setTitle(i);
                    builder1.setMessage((new StringBuilder(String.valueOf(getString(0x7f0a0186)))).append(" ").append(stats.exception.getClass().getCanonicalName()).append("\n\n").append(getString(0x7f0a0185)).append(" ").append(stats.exception.getMessage()).toString());
                    builder1.setIcon(0x1080027);
                    return builder1.create();
                }
                builder1.setIcon(0x108009b);
                StringBuilder stringbuilder;
                if (flag)
                {
                    i = 0x7f0a0182;
                } else
                {
                    i = 0x7f0a0181;
                }
                builder1.setTitle(i);
                if (flag)
                {
                    obj = "\n";
                } else
                {
                    obj = "<br />";
                }
                stringbuilder = new StringBuilder();
                if (flag)
                {
                    i = 0x7f0a0188;
                } else
                {
                    i = 0x7f0a0187;
                }
                stringbuilder.append(getString(i)).append(((String) (obj)));
                stringbuilder.append(getString(0x7f0a018c, new Object[] {
                    Integer.valueOf(stats.goodServers), Integer.valueOf(stats.totalServers)
                })).append(((String) (obj)));
                stringbuilder.append(getString(0x7f0a018d, new Object[] {
                    Integer.valueOf(stats.goodPreferences), Integer.valueOf(stats.totalPreferences)
                })).append(((String) (obj)));
                if (stats.crypt)
                {
                    if (flag)
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
                if (flag)
                {
                    stringbuilder.append(((String) (obj))).append(getString(0x7f0a018f, new Object[] {
                        stats.fileName
                    }));
                } else
                {
                    stringbuilder.append(((String) (obj))).append((new StringBuilder("<b>")).append(getString(0x7f0a0189)).append("</b>").toString());
                }
                if (flag)
                {
                    obj = stringbuilder;
                } else
                {
                    obj = Html.fromHtml(stringbuilder.toString());
                }
                builder1.setMessage(((CharSequence) (obj)));
                mTask = null;
                return builder1.create();

            case 2: // '\002'
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setIcon(0x1080027);
                builder.setTitle(0x7f0a018a);
                builder.setMessage(0x7f0a018b);
                builder.setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                    final DataBackup this$1;

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

            
            {
                this$1 = DataBackup.this;
                super();
            }
                });
                return builder.create();
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
            mTask = new net.andchat.donate.Misc.Backup.BackupOp(0, this, mBackup, new File(""), mHandler);
            mTask.start();
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
                dialog = mBackup.getStats().fileName;
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
                mTask.showDialog = true;
            }
        }

        protected void onStop()
        {
            super.onStop();
            if (mTask != null)
            {
                mTask.showDialog = false;
            }
        }






        public DataBackup()
        {
        }
    }

    public static final class DataSecurity extends PreferenceParent
        implements android.preference.Preference.OnPreferenceClickListener
    {

        private int actType;
        private boolean wasChecked;

        protected void onActivityResult(int i, int j, Intent intent)
        {
            boolean flag;
            flag = true;
            super.onActivityResult(i, j, intent);
            i;
            JVM INSTR tableswitch 0 1: default 32
        //                       0 208
        //                       1 33;
               goto _L1 _L2 _L3
_L1:
            return;
_L3:
            if (actType != 0)
            {
                continue; /* Loop/switch isn't completed */
            }
            if (j == -1)
            {
                ((CheckBoxPreference)findPreference(getString(0x7f0a001d))).setChecked(false);
                i = Utils.getIRCDb(this).decryptAll();
                ((IRCApp)getApplication()).clearCrypt();
                ((CheckBoxPreference)findPreference(getString(0x7f0a001e))).setChecked(false);
                if (i == 0)
                {
                    Toast.makeText(this, 0x7f0a019b, 0).show();
                    return;
                } else
                {
                    Toast.makeText(this, getResources().getQuantityString(0x7f0e0004, i, new Object[] {
                        Integer.valueOf(i)
                    }), 0).show();
                    return;
                }
            }
            continue; /* Loop/switch isn't completed */
            if (actType != 1 || j != -1) goto _L1; else goto _L4
_L4:
            i = Utils.getIRCDb(this).encryptAll();
            if (i == 0)
            {
                Toast.makeText(this, 0x7f0a019a, 0).show();
                return;
            } else
            {
                Toast.makeText(this, getResources().getQuantityString(0x7f0e0003, i, new Object[] {
                    Integer.valueOf(i)
                }), 0).show();
                return;
            }
_L2:
            if (j != -1)
            {
                flag = false;
            }
            ((CheckBoxPreference)findPreference(getString(0x7f0a001d))).setChecked(flag);
            if (flag)
            {
                Toast.makeText(this, 0x7f0a019c, 0).show();
                return;
            }
            if (true) goto _L1; else goto _L5
_L5:
        }

        protected void onCreate(Bundle bundle)
        {
            super.onCreate(bundle);
            addPreferencesFromResource(0x7f050004);
            findPreference(getString(0x7f0a001d)).setOnPreferenceClickListener(this);
            findPreference("trigger").setOnPreferenceClickListener(this);
            super.setTitle(0x7f0a0083);
        }

        public boolean onPreferenceClick(Preference preference)
        {
            String s = preference.getKey();
            if (!s.equals(getString(0x7f0a001d))) goto _L2; else goto _L1
_L1:
            preference = (CheckBoxPreference)preference;
            boolean flag;
            if (preference.isChecked())
            {
                flag = false;
            } else
            {
                flag = true;
            }
            wasChecked = flag;
            if (!wasChecked) goto _L4; else goto _L3
_L3:
            preference.setChecked(wasChecked);
            actType = 0;
            preference = (new StringBuilder()).append(getString(0x7f0a011e));
            if (wasChecked)
            {
                preference.append(getString(0x7f0a0198));
            }
            preference.append(":");
            startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 1).putExtra("message", preference.toString()), 1);
_L6:
            return false;
_L4:
            preference.setChecked(wasChecked);
            startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 0).putExtra("message", getString(0x7f0a0199)), 0);
            return false;
_L2:
            if (s.equals("trigger"))
            {
                if (!Utils.getCrypt(this).correctPass())
                {
                    actType = 1;
                    startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 1).putExtra("message", (new StringBuilder(String.valueOf(getString(0x7f0a011e)))).append(":").toString()), 1);
                    return false;
                }
                int i = Utils.getIRCDb(this).encryptAll();
                if (i == 0)
                {
                    Toast.makeText(this, 0x7f0a019a, 0).show();
                    return false;
                } else
                {
                    Toast.makeText(this, getResources().getQuantityString(0x7f0e0003, i, new Object[] {
                        Integer.valueOf(i)
                    }), 0).show();
                    return false;
                }
            }
            if (true) goto _L6; else goto _L5
_L5:
        }

        protected void onRestoreInstanceState(Bundle bundle)
        {
            super.onRestoreInstanceState(bundle);
            actType = bundle.getInt("acttype");
        }

        protected void onSaveInstanceState(Bundle bundle)
        {
            super.onSaveInstanceState(bundle);
            bundle.putInt("acttype", actType);
        }

        public DataSecurity()
        {
        }
    }

    public static final class ServerProfiles extends PreferenceParent
        implements android.content.DialogInterface.OnClickListener, android.content.DialogInterface.OnMultiChoiceClickListener, android.preference.Preference.OnPreferenceClickListener
    {

        private int mApplyToMask;
        private Preference mEncoding;
        private final String mKeys[] = new String[2];

        protected void onActivityResult(int i, int j, Intent intent)
        {
            super.onActivityResult(i, j, intent);
            i;
            JVM INSTR tableswitch 5 5: default 28
        //                       5 29;
               goto _L1 _L2
_L1:
            return;
_L2:
            if (j != 0)
            {
                intent = intent.getStringExtra("selection");
                mEncoding.setTitle(intent);
                Utils.getPrefs(this).edit().putString(mKeys[0], intent).commit();
                return;
            }
            if (true) goto _L1; else goto _L3
_L3:
        }

        public void onClick(DialogInterface dialoginterface, int i)
        {
            switch (i)
            {
            default:
                return;

            case -1: 
                int j = mApplyToMask;
                dialoginterface = Utils.getIRCDb(this);
                List list = dialoginterface.getList();
                int k = list.size();
                if (j == 0)
                {
                    Toast.makeText(this, 0x7f0a0196, 0).show();
                    return;
                }
                if (k == 0)
                {
                    Toast.makeText(this, 0x7f0a0197, 0).show();
                    return;
                }
                String s = Utils.getDefaultServerProfileValue(0x7f0a0001, this);
                String s1 = Utils.getDefaultServerProfileValue(0x7f0a0002, this);
                String s2 = Utils.getDefaultServerProfileValue(0x7f0a0003, this);
                String s3 = Utils.getDefaultServerProfileValue(0x7f0a0004, this);
                String s4 = Utils.getDefaultServerProfileValue(0x7f0a0005, this);
                String s5 = Utils.getDefaultServerProfileValue(0x7f0a0006, this);
                ArrayList arraylist = new ArrayList(k);
                i = 0;
                do
                {
                    if (i >= k)
                    {
                        dialoginterface.updateMultiple(arraylist);
                        Toast.makeText(this, getResources().getQuantityString(0x7f0e0002, k, new Object[] {
                            Integer.valueOf(k)
                        }), 0).show();
                        return;
                    }
                    ServerProfile serverprofile = dialoginterface.getDetailsForId(dialoginterface.getId((String)list.get(i)));
                    if (Utils.isBitSet(j, 1))
                    {
                        serverprofile.setNick(1, s);
                    }
                    if (Utils.isBitSet(j, 2))
                    {
                        serverprofile.setNick(2, s1);
                    }
                    if (Utils.isBitSet(j, 4))
                    {
                        serverprofile.setNick(3, s2);
                    }
                    if (Utils.isBitSet(j, 8))
                    {
                        serverprofile.setRealname(s3);
                    }
                    if (Utils.isBitSet(j, 16))
                    {
                        serverprofile.setUsername(s4);
                    }
                    if (Utils.isBitSet(j, 32))
                    {
                        serverprofile.setCharset(s5);
                    }
                    arraylist.add(serverprofile);
                    i++;
                } while (true);

            case -2: 
                dialoginterface.dismiss();
                return;
            }
        }

        public void onClick(DialogInterface dialoginterface, int i, boolean flag)
        {
            switch (i)
            {
            default:
                return;

            case 0: // '\0'
                if (flag)
                {
                    i = mApplyToMask | 1;
                } else
                {
                    i = mApplyToMask & -2;
                }
                mApplyToMask = i;
                return;

            case 1: // '\001'
                if (flag)
                {
                    i = mApplyToMask | 2;
                } else
                {
                    i = mApplyToMask & -3;
                }
                mApplyToMask = i;
                return;

            case 2: // '\002'
                if (flag)
                {
                    i = mApplyToMask | 4;
                } else
                {
                    i = mApplyToMask & -5;
                }
                mApplyToMask = i;
                return;

            case 3: // '\003'
                if (flag)
                {
                    i = mApplyToMask | 8;
                } else
                {
                    i = mApplyToMask & -9;
                }
                mApplyToMask = i;
                return;

            case 4: // '\004'
                if (flag)
                {
                    i = mApplyToMask | 0x10;
                } else
                {
                    i = mApplyToMask & 0xffffffef;
                }
                mApplyToMask = i;
                return;

            case 5: // '\005'
                break;
            }
            if (flag)
            {
                i = mApplyToMask | 0x20;
            } else
            {
                i = mApplyToMask & 0xffffffdf;
            }
            mApplyToMask = i;
        }

        protected void onCreate(Bundle bundle)
        {
            super.onCreate(bundle);
            addPreferencesFromResource(0x7f05000a);
            bundle = getString(0x7f0a000f);
            mKeys[0] = bundle;
            mEncoding = findPreference(bundle);
            Preference preference = mEncoding;
            preference.setTitle(Utils.getPrefs(this).getString(bundle, getString(0x7f0a0006)));
            preference.setOnPreferenceClickListener(this);
            bundle = getString(0x7f0a002d);
            mKeys[1] = bundle;
            findPreference(bundle).setOnPreferenceClickListener(this);
            super.setTitle(0x7f0a0084);
        }

        protected Dialog onCreateDialog(int i)
        {
            switch (i)
            {
            default:
                return null;

            case 0: // '\0'
                return (new android.app.AlertDialog.Builder(this)).setIcon(0x108009b).setMultiChoiceItems(0x7f0b0000, null, this).setTitle(0x7f0a0194).setPositiveButton(0x7f0a0195, this).setNegativeButton(0x7f0a01d2, this).create();
            }
        }

        public boolean onPreferenceClick(Preference preference)
        {
            String as[] = mKeys;
            preference = preference.getKey();
            if (preference.equals(as[0]))
            {
                startActivityForResult(new Intent(this, net/andchat/donate/Activities/CharsetPicker), 5);
                return true;
            }
            if (preference.equals(as[1]))
            {
                showDialog(0);
                return true;
            } else
            {
                return false;
            }
        }

        public ServerProfiles()
        {
        }
    }


    public Preferences()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        addPreferencesFromResource(0x7f050007);
        findPreference("first_run").setOnPreferenceClickListener(this);
        setResult(-1);
        super.setTitle(0x7f0a007f);
    }

    protected void onDestroy()
    {
        ((IRCApp)getApplication()).notifyWatchers();
        super.onDestroy();
    }

    public boolean onPreferenceClick(Preference preference)
    {
        if (preference.getKey().equals("first_run"))
        {
            startActivity(new Intent(this, net/andchat/donate/Activities/initialSetup/Step1));
            finish();
            return true;
        } else
        {
            return false;
        }
    }

    // Unreferenced inner class net/andchat/donate/Activities/Preferences$DataBackup$2

/* anonymous class */
    class DataBackup._cls2 extends Handler
    {

        final DataBackup this$1;

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
                this$1 = DataBackup.this;
                super();
            }
    }

}
