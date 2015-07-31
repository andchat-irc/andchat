// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import net.andchat.donate.Misc.Utils;

public class TextStyleDialog extends DialogPreference
    implements android.widget.AdapterView.OnItemSelectedListener, android.widget.SeekBar.OnSeekBarChangeListener
{

    private static final int KEYS[] = {
        0x7f0a002f, 0x7f0a0030, 0x7f0a0031, 0x7f0a0032
    };
    private static final int SPINNER_IDS[] = {
        0x7f08005b, 0x7f08005e, 0x7f080077, 0x7f080054
    };
    public static final Typeface TYPES[];
    private int mChannelListSize;
    private TextView mChannelListSizePreview;
    private Object mFormatArgs[];
    private boolean mHaveExtraRows;
    private int mInputSize;
    private TextView mInputSizePreview;
    private int mMsgSize;
    private TextView mMsgSizePreview;
    private String mSampleMsg[];
    private final Spinner mSpinners[];
    private int mUserlistSize;
    private TextView mUserlistSizePreview;

    public TextStyleDialog(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mSpinners = new Spinner[4];
    }

    public TextStyleDialog(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mSpinners = new Spinner[4];
    }

    private int corrected(int i)
    {
        return i - 10;
    }

    protected View onCreateDialogView()
    {
        View view = super.onCreateDialogView();
        Object obj = getContext();
        SharedPreferences sharedpreferences = Utils.getPrefs(((Context) (obj)));
        mSampleMsg = new String[4];
        String as[] = mSampleMsg;
        as[0] = ((Context) (obj)).getString(0x7f0a01a2);
        as[1] = ((Context) (obj)).getString(0x7f0a01a3);
        as[2] = ((Context) (obj)).getString(0x7f0a01a4);
        as[3] = ((Context) (obj)).getString(0x7f0a01a5);
        mFormatArgs = new Object[1];
        mMsgSizePreview = (TextView)view.findViewById(0x7f080059);
        SeekBar seekbar = (SeekBar)view.findViewById(0x7f08005a);
        mInputSizePreview = (EditText)view.findViewById(0x7f08005c);
        SeekBar seekbar1 = (SeekBar)view.findViewById(0x7f08005d);
        mUserlistSizePreview = (TextView)view.findViewById(0x7f080075);
        SeekBar seekbar2 = (SeekBar)view.findViewById(0x7f080076);
        mChannelListSizePreview = (TextView)view.findViewById(0x7f080052);
        SeekBar seekbar3 = (SeekBar)view.findViewById(0x7f080053);
        int i;
        int j;
        int k;
        int l;
        boolean flag;
        if (mUserlistSizePreview != null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        i = (int)Utils.pixelsToSp(((Context) (obj)), mInputSizePreview.getTextSize());
        i = Utils.parseInt(sharedpreferences.getString(((Context) (obj)).getString(0x7f0a0014), Integer.toString(i)), i);
        j = Utils.parseInt(sharedpreferences.getString(((Context) (obj)).getString(0x7f0a0013), "13"), 13);
        k = Utils.parseInt(sharedpreferences.getString(((Context) (obj)).getString(0x7f0a0015), "18"), 18);
        l = Utils.parseInt(sharedpreferences.getString(((Context) (obj)).getString(0x7f0a0016), "18"), 18);
        seekbar1.setOnSeekBarChangeListener(this);
        seekbar.setOnSeekBarChangeListener(this);
        if (flag)
        {
            seekbar2.setOnSeekBarChangeListener(this);
            seekbar3.setOnSeekBarChangeListener(this);
        }
        seekbar1.setProgress(corrected(i));
        seekbar.setProgress(corrected(j));
        if (flag)
        {
            seekbar2.setProgress(corrected(k));
            seekbar3.setProgress(corrected(l));
        }
        if (corrected(i) == 0)
        {
            onProgressChanged(seekbar1, 0, false);
        }
        if (corrected(j) == 0)
        {
            onProgressChanged(seekbar, 0, false);
        }
        if (flag)
        {
            if (corrected(k) == 0)
            {
                onProgressChanged(seekbar2, 0, false);
            }
            if (corrected(l) == 0)
            {
                onProgressChanged(seekbar2, 0, false);
            }
        }
        mHaveExtraRows = flag;
        obj = new ArrayAdapter(((Context) (obj)), 0x1090008, ((Context) (obj)).getResources().getStringArray(0x7f0b0009));
        ((ArrayAdapter) (obj)).setDropDownViewResource(0x1090009);
        i = 0;
        do
        {
            if (i >= SPINNER_IDS.length)
            {
                return view;
            }
            Spinner spinner = (Spinner)view.findViewById(SPINNER_IDS[i]);
            if (spinner != null)
            {
                spinner.setAdapter(((android.widget.SpinnerAdapter) (obj)));
                spinner.setOnItemSelectedListener(this);
                mSpinners[i] = spinner;
                spinner.setSelection(sharedpreferences.getInt(getContext().getString(KEYS[i]), 0));
            }
            i++;
        } while (true);
    }

    protected void onDialogClosed(boolean flag)
    {
        super.onDialogClosed(flag);
        if (!flag) goto _L2; else goto _L1
_L1:
        Context context;
        android.content.SharedPreferences.Editor editor;
        Spinner aspinner[];
        int i;
        context = getContext();
        i = mMsgSize;
        editor = getSharedPreferences().edit();
        editor.putString(context.getString(0x7f0a0013), Integer.toString(i));
        i = mInputSize;
        editor.putString(context.getString(0x7f0a0014), Integer.toString(i));
        if (mHaveExtraRows)
        {
            i = mUserlistSize;
            editor.putString(context.getString(0x7f0a0015), Integer.toString(i));
            i = mChannelListSize;
            editor.putString(context.getString(0x7f0a0016), Integer.toString(i));
        }
        aspinner = mSpinners;
        i = 0;
_L6:
        if (i < aspinner.length) goto _L4; else goto _L3
_L3:
        editor.commit();
_L2:
        return;
_L4:
        Spinner spinner = aspinner[i];
        if (spinner != null)
        {
            int j = spinner.getSelectedItemPosition();
            if (j != -1)
            {
                editor.putInt(context.getString(KEYS[i]), j);
            }
        }
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public void onItemSelected(AdapterView adapterview, View view, int i, long l)
    {
        switch (adapterview.getId())
        {
        default:
            return;

        case 2131230811: 
            mMsgSizePreview.setTypeface(TYPES[i]);
            return;

        case 2131230814: 
            mInputSizePreview.setTypeface(TYPES[i]);
            return;

        case 2131230839: 
            mUserlistSizePreview.setTypeface(TYPES[i]);
            return;

        case 2131230804: 
            mChannelListSizePreview.setTypeface(TYPES[i]);
            return;
        }
    }

    public void onNothingSelected(AdapterView adapterview)
    {
    }

    public void onProgressChanged(SeekBar seekbar, int i, boolean flag)
    {
        TextView textview;
        Object obj;
        i += 10;
        textview = null;
        obj = null;
        seekbar.getId();
        JVM INSTR lookupswitch 4: default 56
    //                   2131230803: 159
    //                   2131230810: 96
    //                   2131230813: 117
    //                   2131230838: 138;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        seekbar = obj;
_L7:
        if (textview != null)
        {
            textview.setTextSize(2, i);
            mFormatArgs[0] = Integer.valueOf(i);
            textview.setText(String.format(seekbar, mFormatArgs));
        }
        return;
_L3:
        mMsgSize = i;
        seekbar = mSampleMsg[0];
        textview = mMsgSizePreview;
        continue; /* Loop/switch isn't completed */
_L4:
        mInputSize = i;
        seekbar = mSampleMsg[1];
        textview = mInputSizePreview;
        continue; /* Loop/switch isn't completed */
_L5:
        mUserlistSize = i;
        seekbar = mSampleMsg[2];
        textview = mUserlistSizePreview;
        continue; /* Loop/switch isn't completed */
_L2:
        mChannelListSize = i;
        seekbar = mSampleMsg[3];
        textview = mChannelListSizePreview;
        if (true) goto _L7; else goto _L6
_L6:
    }

    public void onStartTrackingTouch(SeekBar seekbar)
    {
    }

    public void onStopTrackingTouch(SeekBar seekbar)
    {
    }

    static 
    {
        TYPES = (new Typeface[] {
            Typeface.SANS_SERIF, Typeface.MONOSPACE, Typeface.SERIF
        });
    }
}
