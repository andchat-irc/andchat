// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class MultiChoicePreference extends ListPreference
{

    private boolean mClickedDialogEntryIndices[];

    public MultiChoicePreference(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mClickedDialogEntryIndices = new boolean[getEntries().length];
    }

    public static String[] parseStoredValue(CharSequence charsequence)
    {
        charsequence = charsequence.toString();
        if (charsequence.length() == 0)
        {
            return null;
        } else
        {
            return charsequence.split("###");
        }
    }

    private void restoreCheckedEntries()
    {
        CharSequence acharsequence[];
        String as[];
        acharsequence = getEntryValues();
        as = parseStoredValue(getValue());
        if (as == null) goto _L2; else goto _L1
_L1:
        int i;
        int k;
        int l;
        k = as.length;
        l = acharsequence.length;
        i = 0;
_L5:
        if (i < k) goto _L3; else goto _L2
_L2:
        return;
_L3:
        String s = as[i].trim();
        int j = 0;
        do
        {
            if (j < l)
            {
label0:
                {
                    if (!acharsequence[j].equals(s))
                    {
                        break label0;
                    }
                    mClickedDialogEntryIndices[j] = true;
                }
            }
            i++;
            if (true)
            {
                continue;
            }
            j++;
        } while (true);
        if (true) goto _L5; else goto _L4
_L4:
    }

    protected void onDialogClosed(boolean flag)
    {
        Object obj = getEntryValues();
        if (!flag || obj == null) goto _L2; else goto _L1
_L1:
        Object obj1;
        int i;
        int j;
        obj1 = new StringBuilder();
        j = obj.length;
        i = 0;
_L6:
        if (i < j) goto _L4; else goto _L3
_L3:
        if (callChangeListener(obj1))
        {
            obj1 = ((StringBuilder) (obj1)).toString();
            obj = obj1;
            if (((String) (obj1)).length() > 0)
            {
                obj = ((String) (obj1)).substring(0, ((String) (obj1)).length() - "###".length());
            }
            setValue(((String) (obj)));
        }
_L2:
        return;
_L4:
        if (mClickedDialogEntryIndices[i])
        {
            ((StringBuilder) (obj1)).append(obj[i]).append("###");
        }
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    protected void onPrepareDialogBuilder(android.app.AlertDialog.Builder builder)
    {
        CharSequence acharsequence[] = getEntries();
        CharSequence acharsequence1[] = getEntryValues();
        if (acharsequence == null || acharsequence1 == null || acharsequence.length != acharsequence1.length)
        {
            throw new IllegalStateException("ListPreference requires an entries array and an entryValues array which are both the same length");
        } else
        {
            restoreCheckedEntries();
            builder.setMultiChoiceItems(acharsequence, mClickedDialogEntryIndices, new android.content.DialogInterface.OnMultiChoiceClickListener() {

                final MultiChoicePreference this$0;

                public void onClick(DialogInterface dialoginterface, int i, boolean flag)
                {
                    mClickedDialogEntryIndices[i] = flag;
                }

            
            {
                this$0 = MultiChoicePreference.this;
                super();
            }
            });
            return;
        }
    }

    public void setEntries(CharSequence acharsequence[])
    {
        super.setEntries(acharsequence);
        mClickedDialogEntryIndices = new boolean[acharsequence.length];
    }

}
