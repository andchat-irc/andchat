// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import net.andchat.donate.Backend.Ignores;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivity, IgnoresActivityHelper

private class mInflator extends BaseAdapter
    implements android.view.gnoreListAdapter
{

    private final LayoutInflater mInflator;
    final IgnoresActivity this$0;

    public int getCount()
    {
        return IgnoresActivity.access$1(IgnoresActivity.this).size();
    }

    public Object getItem(int i)
    {
        return IgnoresActivity.access$1(IgnoresActivity.this).get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        net.andchat.donate.Backend.er er;
        if (view == null)
        {
            viewgroup = mInflator.inflate(0x7f03001d, viewgroup, false);
            view = (TextView)viewgroup.findViewById(0x7f080061);
            ImageView imageview = (ImageView)viewgroup.findViewById(0x7f080062);
            ImageView imageview1 = (ImageView)viewgroup.findViewById(0x7f080063);
            viewgroup.setTag(new mInflator(view));
            imageview.setOnClickListener(this);
            imageview1.setOnClickListener(this);
            IgnoresActivityHelper.sInstance.ializeForView(imageview1);
        } else
        {
            viewgroup = view;
            view = ((ializeForView)viewgroup.getTag()).ializeForView;
        }
        er = (net.andchat.donate.Backend.nitializeForView)IgnoresActivity.access$1(IgnoresActivity.this).get(i);
        view.setText((new StringBuilder(er._fld0)).append(" (").append(er._fld0).append('@').append(er._fld0).append(")"));
        return viewgroup;
    }

    public void onClick(View view)
    {
        IgnoresActivity.access$2(IgnoresActivity.this, getListView().getPositionForView((View)view.getParent()));
        switch (view.getId())
        {
        default:
            return;

        case 2131230818: 
            ((Activity)view.getContext()).showDialog(0);
            return;

        case 2131230819: 
            IgnoresActivityHelper.sInstance.leViewClick(view);
            break;
        }
    }

    public (LayoutInflater layoutinflater, Ignores ignores)
    {
        this$0 = IgnoresActivity.this;
        super();
        IgnoresActivity.access$0(IgnoresActivity.this, ignores.getAllIgnores());
        mInflator = layoutinflater;
    }
}
