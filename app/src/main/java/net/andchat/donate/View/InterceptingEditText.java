// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class InterceptingEditText extends EditText
{
    public static interface PreImeListener
    {

        public abstract boolean onPreImeKey(int i, KeyEvent keyevent);
    }


    private PreImeListener mPreImeKeyListener;

    public InterceptingEditText(Context context)
    {
        super(context);
    }

    public InterceptingEditText(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public InterceptingEditText(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public boolean onKeyPreIme(int i, KeyEvent keyevent)
    {
        if (mPreImeKeyListener != null)
        {
            return mPreImeKeyListener.onPreImeKey(i, keyevent) || super.onKeyPreIme(i, keyevent);
        } else
        {
            return super.onKeyPreIme(i, keyevent);
        }
    }

    public void setCallback(PreImeListener preimelistener)
    {
        mPreImeKeyListener = preimelistener;
    }
}
