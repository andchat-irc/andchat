// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities.initialSetup;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

// Referenced classes of package net.andchat.donate.Activities.initialSetup:
//            Step2

class this._cls0
    implements android.view.ocusChangeListener
{

    final Step2 this$0;

    public void onFocusChange(View view, boolean flag)
    {
        if (!flag)
        {
            view = ((EditText)view).getText().toString().trim();
            if (view.length() > 0)
            {
                if (Step2.access$2(Step2.this).getText().toString().length() == 0)
                {
                    Step2.access$2(Step2.this).setText(view);
                    Step2.access$2(Step2.this).append("|");
                }
                if (Step2.access$3(Step2.this).getText().toString().length() == 0)
                {
                    Step2.access$3(Step2.this).setText(view);
                    Step2.access$3(Step2.this).append("-");
                }
            }
            if (Step2.access$5(Step2.this).getText().toString().length() == 0)
            {
                Step2.access$5(Step2.this).setText(view);
            }
            if (Step2.access$4(Step2.this).getText().toString().length() == 0)
            {
                Step2.access$4(Step2.this).setText(view);
            }
        }
    }

    ()
    {
        this$0 = Step2.this;
        super();
    }
}
