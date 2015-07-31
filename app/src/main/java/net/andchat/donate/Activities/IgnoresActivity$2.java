// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.DialogInterface;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivity

class this._cls0
    implements android.content.MultiChoiceClickListener
{

    final IgnoresActivity this$0;

    public void onClick(DialogInterface dialoginterface, int i, boolean flag)
    {
        boolean flag1 = false;
        i;
        JVM INSTR tableswitch 0 3: default 36
    //                   0 59
    //                   1 64
    //                   2 69
    //                   3 74;
           goto _L1 _L2 _L3 _L4 _L5
_L5:
        break MISSING_BLOCK_LABEL_74;
_L1:
        i = ((flag1) ? 1 : 0);
_L6:
        if (flag)
        {
            dialoginterface = IgnoresActivity.this;
            IgnoresActivity.access$4(dialoginterface, IgnoresActivity.access$3(dialoginterface) | i);
            return;
        } else
        {
            dialoginterface = IgnoresActivity.this;
            IgnoresActivity.access$4(dialoginterface, IgnoresActivity.access$3(dialoginterface) & ~i);
            return;
        }
_L2:
        i = 1;
          goto _L6
_L3:
        i = 2;
          goto _L6
_L4:
        i = 4;
          goto _L6
        i = 8;
          goto _L6
    }

    ckListener()
    {
        this$0 = IgnoresActivity.this;
        super();
    }
}
