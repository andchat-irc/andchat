// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListActivity

private class mSections extends ArrayAdapter
    implements Filterable, SectionIndexer
{
    private class UserFilter extends Filter
    {

        private final List originals;
        final UserListActivity.UserAdapter this$1;

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            android.widget.Filter.FilterResults filterresults = new android.widget.Filter.FilterResults();
            Object obj = originals;
            if (charsequence == null || charsequence.length() == 0)
            {
                filterresults.count = ((List) (obj)).size();
                filterresults.values = obj;
                return filterresults;
            }
            boolean flag = Utils.isStatusPrefix(mWindows.getStatusMap(), charsequence.charAt(0));
            ArrayList arraylist;
            byte byte0;
            if (flag)
            {
                charsequence = Pattern.compile((new StringBuilder("(?i)^")).append(Utils.escape(charsequence)).toString()).matcher("");
            } else
            {
                charsequence = Pattern.compile((new StringBuilder("(?i)^[")).append(Utils.escape(mWindows.getStatusMap().getPrefixes())).append("]?").append(Utils.escape(charsequence)).toString()).matcher("");
            }
            if (flag)
            {
                byte0 = 10;
            } else
            {
                byte0 = 15;
            }
            arraylist = new ArrayList(byte0);
            obj = ((List) (obj)).iterator();
            do
            {
                String s;
                do
                {
                    if (!((Iterator) (obj)).hasNext())
                    {
                        filterresults.values = arraylist;
                        filterresults.count = arraylist.size();
                        return filterresults;
                    }
                    s = (String)((Iterator) (obj)).next();
                } while (!charsequence.reset(s).find());
                arraylist.add(s);
            } while (true);
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            mItems = (List)filterresults.values;
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

        public UserFilter(List list)
        {
            this$1 = UserListActivity.UserAdapter.this;
            super();
            originals = list;
        }
    }


    private SparseIntArray mCache;
    private final Filter mFilter;
    List mItems;
    private final String mSections[];
    private final SessionManager mWindows;
    final UserListActivity this$0;

    public int getCount()
    {
        return mItems.size();
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
        return (String)mItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getPositionForSection(int i)
    {
        SparseIntArray sparseintarray1 = mCache;
        SparseIntArray sparseintarray = sparseintarray1;
        if (sparseintarray1 == null)
        {
            sparseintarray = new SparseIntArray();
            mCache = sparseintarray;
        }
        return Utils.getPositionForSection(i, mSections, mItems, sparseintarray);
    }

    public int getSectionForPosition(int i)
    {
        return Utils.getSectionForPosition(i, mItems, mSections);
    }

    public Object[] getSections()
    {
        return mSections;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        return super.getView(i, view, viewgroup);
    }


    public UserFilter.originals(Context context, int i, List list, SessionManager sessionmanager)
    {
        this$0 = UserListActivity.this;
        super(context, i, list);
        mFilter = new UserFilter(list);
        mItems = list;
        mWindows = sessionmanager;
        mSections = Utils.getSectionsFromItems(list);
    }
}
