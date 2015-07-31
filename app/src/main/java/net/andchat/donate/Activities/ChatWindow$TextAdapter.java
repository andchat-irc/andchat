// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Iterator;
import net.andchat.donate.Misc.LimitedSizeQueue;
import net.andchat.donate.View.TextStyleDialog;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

private class mText extends BaseAdapter
{

    private LayoutInflater mInflator;
    private final LinkMovementMethod mMethod = new LinkMovementMethod();
    private LimitedSizeQueue mText;
    final ChatWindow this$0;

    private CharSequence get(int i)
    {
        Iterator iterator = mText.iterator();
        int j = 0;
        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }
            if (j == i)
            {
                return (CharSequence)iterator.next();
            }
            iterator.next();
            j++;
        } while (true);
    }

    public int getCount()
    {
        return mText.getSize();
    }

    public Object getItem(int i)
    {
        return get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        if (view == null)
        {
            view = (TextView)mInflator.inflate(0x7f030023, viewgroup, false);
            view.setMovementMethod(mMethod);
            view.setTextSize(ChatWindow.access$14(ChatWindow.this));
        } else
        {
            view = (TextView)view;
        }
        view.setTypeface(TextStyleDialog.TYPES[ChatWindow.access$15(ChatWindow.this)]);
        view.setText(get(i));
        return view;
    }

    public (Context context, LimitedSizeQueue limitedsizequeue)
    {
        this$0 = ChatWindow.this;
        super();
        mInflator = LayoutInflater.from(context);
        mText = limitedsizequeue;
    }
}
