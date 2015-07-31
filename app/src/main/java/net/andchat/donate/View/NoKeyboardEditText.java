// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class NoKeyboardEditText extends EditText
{

    public NoKeyboardEditText(Context context)
    {
        super(context);
    }

    public NoKeyboardEditText(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public NoKeyboardEditText(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public boolean onCheckIsTextEditor()
    {
        return false;
    }
}
