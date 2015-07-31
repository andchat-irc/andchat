// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.widget.Filter;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package net.andchat.donate.Activities:
//            Main

private class <init> extends Filter
{

    private ArrayList originals;
    final etInvalidated this$1;

    protected android.widget.ter.ItemFilter performFiltering(CharSequence charsequence)
    {
        String s;
        android.widget.ter.ItemFilter itemfilter;
        s = charsequence.toString().toUpperCase();
        itemfilter = new android.widget.();
        if (originals == null)
        {
            synchronized (this._cls1.this.originals)
            {
                originals = new ArrayList(this._cls1.this.originals);
            }
        }
        obj = originals;
        if (charsequence == null || charsequence.length() == 0)
        {
            itemfilter.originals = originals.size();
            itemfilter. = originals;
            return itemfilter;
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
                    itemfilter. = charsequence;
                    itemfilter. = charsequence.size();
                    return itemfilter;
                }
                s1 = (String)iterator.next();
            } while (!s1.toUpperCase().contains(s));
            charsequence.add(s1);
        } while (true);
    }

    protected void publishResults(CharSequence charsequence, android.widget.ter.ItemFilter itemfilter)
    {
        this._cls1.this.originals = (ArrayList)itemfilter.;
        if (itemfilter. > 0)
        {
            etChanged();
            return;
        } else
        {
            etInvalidated();
            return;
        }
    }

    private ()
    {
        this$1 = this._cls1.this;
        super();
        originals = null;
    }

    originals(originals originals2)
    {
        this();
    }
}
