// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            Step2

class this._cls0
    implements TextWatcher
{

    final Step2 this$0;

    public void afterTextChanged(Editable editable)
    {
        boolean flag;
        if (Step2.access$1(Step2.this).getText().toString().trim().length() > 0 && Step2.access$2(Step2.this).getText().toString().trim().length() > 0 && Step2.access$3(Step2.this).getText().toString().trim().length() > 0 && Step2.access$4(Step2.this).getText().toString().trim().length() > 0 && Step2.access$5(Step2.this).getText().toString().trim().length() > 0)
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

    ()
    {
        this$0 = Step2.this;
        super();
    }
}
