// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.DialogInterface;

// Referenced classes of package net.andchat.donate.View:
//            MultiChoicePreference

class this._cls0
    implements android.content.hoiceClickListener
{

    final MultiChoicePreference this$0;

    public void onClick(DialogInterface dialoginterface, int i, boolean flag)
    {
        MultiChoicePreference.access$0(MultiChoicePreference.this)[i] = flag;
    }

    ckListener()
    {
        this$0 = MultiChoicePreference.this;
        super();
    }
}
