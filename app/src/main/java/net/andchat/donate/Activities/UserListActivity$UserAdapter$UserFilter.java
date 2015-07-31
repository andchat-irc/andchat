// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.widget.Filter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            UserListActivity

private class originals extends Filter
{

    private final List originals;
    final etInvalidated this$1;

    protected android.widget.ter.UserFilter performFiltering(CharSequence charsequence)
    {
        android.widget.ter ter = new android.widget.ter();
        Object obj = originals;
        if (charsequence == null || charsequence.length() == 0)
        {
            ter.originals = ((List) (obj)).size();
            ter.originals = obj;
            return ter;
        }
        boolean flag = Utils.isStatusPrefix(originals(this._cls1.this).getStatusMap(), charsequence.charAt(0));
        ArrayList arraylist;
        byte byte0;
        if (flag)
        {
            charsequence = Pattern.compile((new StringBuilder("(?i)^")).append(Utils.escape(charsequence)).toString()).matcher("");
        } else
        {
            charsequence = Pattern.compile((new StringBuilder("(?i)^[")).append(Utils.escape(this._mth1(this._cls1.this).getStatusMap().())).append("]?").append(Utils.escape(charsequence)).toString()).matcher("");
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
                    ter.getPrefixes = arraylist;
                    ter.getPrefixes = arraylist.size();
                    return ter;
                }
                s = (String)((Iterator) (obj)).next();
            } while (!charsequence.reset(s).find());
            arraylist.add(s);
        } while (true);
    }

    protected void publishResults(CharSequence charsequence, android.widget.ter.UserFilter userfilter)
    {
        es = (List)userfilter.mItems;
        if (userfilter.mItems > 0)
        {
            etChanged();
            return;
        } else
        {
            etInvalidated();
            return;
        }
    }

    public (List list)
    {
        this$1 = this._cls1.this;
        super();
        originals = list;
    }
}
