// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListFragment

private class mItems extends ArrayAdapter
{

    private final List mItems;
    final UserListFragment this$0;

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        viewgroup = super.getView(i, view, viewgroup);
        view = (mItems)viewgroup.getTag();
        if (view == null)
        {
            view = (TextView)viewgroup.findViewById(0x7f080004);
            viewgroup.setTag(new init>(view));
        } else
        {
            view = ccess._mth0(view);
        }
        view.setTextSize(UserListFragment.access$0(UserListFragment.this));
        view.setTypeface(UserListFragment.access$1(UserListFragment.this));
        return viewgroup;
    }

    public (Context context, int i, int j, List list)
    {
        this$0 = UserListFragment.this;
        super(context, i, j, list);
        mItems = list;
    }
}
