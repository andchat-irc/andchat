// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.View.DelegatingRelativeLayout;

// Referenced classes of package net.andchat.donate.Activities:
//            Main

private class d extends ArrayAdapter
    implements Filterable
{
    private class ItemFilter extends Filter
    {

        private ArrayList originals;
        final Main.RowAdapter this$1;

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            String s;
            android.widget.Filter.FilterResults filterresults;
            s = charsequence.toString().toUpperCase();
            filterresults = new android.widget.Filter.FilterResults();
            if (originals == null)
            {
                synchronized (pItems)
                {
                    originals = new ArrayList(pItems);
                }
            }
            obj = originals;
            if (charsequence == null || charsequence.length() == 0)
            {
                filterresults.count = originals.size();
                filterresults.values = originals;
                return filterresults;
            }
            break MISSING_BLOCK_LABEL_104;
            charsequence;
            obj;
            JVM INSTR monitorexit ;
            throw charsequence;
            charsequence = new ArrayList();
            Iterator iterator = ((ArrayList) (obj)).iterator();
            do
            {
                String s1;
                do
                {
                    if (!iterator.hasNext())
                    {
                        filterresults.values = charsequence;
                        filterresults.count = charsequence.size();
                        return filterresults;
                    }
                    s1 = (String)iterator.next();
                } while (!s1.toUpperCase().contains(s));
                charsequence.add(s1);
            } while (true);
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            pItems = (ArrayList)filterresults.values;
            if (filterresults.count > 0)
            {
                notifyDataSetChanged();
                return;
            } else
            {
                notifyDataSetInvalidated();
                return;
            }
        }

        private ItemFilter()
        {
            this$1 = Main.RowAdapter.this;
            super();
            originals = null;
        }

        ItemFilter(ItemFilter itemfilter)
        {
            this();
        }
    }


    private android.view.ener mClickListener;
    private final Filter mFilter = new ItemFilter(null);
    private final boolean mHaveServers;
    private final LayoutInflater mInflator;
    private final net.andchat.donate.View.veLayout.EnlargedViewSpec mSpec = new net.andchat.donate.View.veLayout.EnlargedViewSpec(-1, -1, 12, -2);
    List pItems;
    final Main this$0;

    private int getState(String s)
    {
        checkDb();
        int i = pDb.getId(s);
        if (pService != null)
        {
            return pService.getServerState(i);
        } else
        {
            return 4;
        }
    }

    public int getCount()
    {
        return pItems.size();
    }

    public Filter getFilter()
    {
        return mFilter;
    }

    public volatile Object getItem(int i)
    {
        return getItem(i);
    }

    public String getItem(int i)
    {
        return (String)pItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        String s;
        if (view == null)
        {
            view = mInflator.inflate(0x7f03001e, viewgroup, false);
            viewgroup = new <init>(null);
            viewgroup.more = (ImageView)view.findViewById(0x7f080064);
            ((more) (viewgroup)).more.setOnClickListener(mClickListener);
            viewgroup.textview = (TextView)view.findViewById(0x7f080004);
            viewgroup.imageview = (ImageView)view.findViewById(0x7f080065);
            viewgroup.progressbar = (ProgressBar)view.findViewById(0x7f080066);
            ((DelegatingRelativeLayout)view).setEnlargedView(((ut.setEnlargedView) (viewgroup)).more, mSpec);
            view.setTag(viewgroup);
        } else
        {
            viewgroup = (mSpec)view.getTag();
        }
        s = (String)pItems.get(i);
        ((pItems) (viewgroup)).textview.setText(s);
        if (!mHaveServers)
        {
            ((mHaveServers) (viewgroup)).imageview.setVisibility(8);
            ((imageview) (viewgroup)).more.setVisibility(8);
            return view;
        }
        switch (getState(s))
        {
        default:
            if (((getState) (viewgroup)).progressbar.getVisibility() != 8)
            {
                ((progressbar) (viewgroup)).progressbar.setVisibility(8);
            }
            if (((progressbar) (viewgroup)).imageview.getVisibility() != 0)
            {
                ((imageview) (viewgroup)).imageview.setVisibility(0);
            }
            ((imageview) (viewgroup)).imageview.setImageResource(0x7f020021);
            ((imageview) (viewgroup)).more.setVisibility(0);
            return view;

        case 2: // '\002'
        case 3: // '\003'
            if (((more) (viewgroup)).imageview.getVisibility() != 8)
            {
                ((imageview) (viewgroup)).imageview.setVisibility(8);
            }
            if (((imageview) (viewgroup)).progressbar.getVisibility() != 0)
            {
                ((progressbar) (viewgroup)).progressbar.setVisibility(0);
            }
            ((progressbar) (viewgroup)).more.setVisibility(0);
            return view;

        case 1: // '\001'
            if (((more) (viewgroup)).progressbar.getVisibility() != 8)
            {
                ((progressbar) (viewgroup)).progressbar.setVisibility(8);
            }
            if (((progressbar) (viewgroup)).imageview.getVisibility() != 0)
            {
                ((imageview) (viewgroup)).imageview.setVisibility(0);
            }
            ((imageview) (viewgroup)).imageview.setImageResource(0x7f02001e);
            ((imageview) (viewgroup)).more.setVisibility(0);
            return view;

        case 0: // '\0'
            break;
        }
        if (((more) (viewgroup)).progressbar.getVisibility() != 8)
        {
            ((progressbar) (viewgroup)).progressbar.setVisibility(8);
        }
        if (((progressbar) (viewgroup)).imageview.getVisibility() != 0)
        {
            ((imageview) (viewgroup)).imageview.setVisibility(0);
        }
        ((imageview) (viewgroup)).imageview.setImageResource(0x7f02003e);
        ((imageview) (viewgroup)).more.setVisibility(0);
        return view;
    }

    public void refresh()
    {
        notifyDataSetChanged();
    }


    public ItemFilter(Context context, int i, int j, List list, boolean flag)
    {
        this$0 = Main.this;
        super(context, j, i, list);
        pItems = list;
        mInflator = LayoutInflater.from(context);
        mHaveServers = flag;
        mClickListener = new android.view.View.OnClickListener() {

            final Main.RowAdapter this$1;

            public void onClick(View view)
            {
                getListView().showContextMenuForChild((View)view.getParent());
            }

            
            {
                this$1 = Main.RowAdapter.this;
                super();
            }
        };
    }
}
