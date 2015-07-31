// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.io.File;

// Referenced classes of package net.andchat.donate.Activities:
//            PreferenceParent, Preferences

public static final class _cls1.this._cls1 extends PreferenceParent
    implements android.preference.ceClickListener
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
        android.app.nClickListener nclicklistener;
        switch (i)
        {
        default:
            return null;

        case 0: // '\0'
            nclicklistener = new android.app.init>(this);
            break;
        }
        nclicklistener.etTitle(0x7f0a019d);
        nclicklistener.etIcon(0x108009b);
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
        nclicklistener.etView(((View) (obj)));
        nclicklistener.etNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

            final Preferences.ChatLogs this$1;

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            
            {
                this$1 = Preferences.ChatLogs.this;
                super();
            }
        });
        return nclicklistener.reate();
    }

    public boolean onPreferenceClick(Preference preference)
    {
        showDialog(0);
        return true;
    }

    public _cls1.this._cls1()
    {
    }
}
