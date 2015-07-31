// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import net.andchat.donate.IRCApp;
import net.londatiga.android.QuickAction;

public class IgnoresActivityHelper
{
    private static class Current
        implements IHelper
    {

        public void handleViewClick(View view)
        {
            PopupMenu popupmenu1 = (PopupMenu)view.getTag();
            PopupMenu popupmenu = popupmenu1;
            if (popupmenu1 == null)
            {
                Context context = view.getContext();
                popupmenu = new PopupMenu(context, view);
                popupmenu.getMenuInflater().inflate(0x7f0f0001, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(context. new android.widget.PopupMenu.OnMenuItemClickListener() {

                    final Current this$1;
                    private final Context val$c;

                    public boolean onMenuItemClick(MenuItem menuitem)
                    {
                        return ((PopupMenuCallback)c).onMenuItemClick(menuitem);
                    }

            
            {
                this$1 = final_current;
                c = Context.this;
                super();
            }
                });
                view.setTag(popupmenu);
            }
            popupmenu.show();
        }

        public void initializeForView(View view)
        {
            if (!(view.getContext() instanceof PopupMenuCallback))
            {
                throw new RuntimeException("Context of view must implement PopupMenuCallback");
            } else
            {
                return;
            }
        }

        private Current()
        {
        }

        Current(Current current)
        {
            this();
        }
    }

    public static interface IHelper
    {

        public abstract void handleViewClick(View view);

        public abstract void initializeForView(View view);
    }

    private static class Legacy
        implements IHelper
    {

        public void handleViewClick(View view)
        {
            ((QuickAction)view.getTag()).show(view, true);
        }

        public void initializeForView(View view)
        {
            Context context = view.getContext();
            if (!(context instanceof net.londatiga.android.QuickAction.OnQuickActionItemClickListener))
            {
                throw new RuntimeException("Context of view must implement QuickAction.OnQuickActionItemClickListener");
            } else
            {
                QuickAction quickaction = new QuickAction(context);
                (new net.andchat.donate.Misc.AbstractMenuInflator.QuickActionsMenu(quickaction)).addActionsFromXML(context, 0x7f0f0001);
                quickaction.setOnActionItemClickListener((net.londatiga.android.QuickAction.OnQuickActionItemClickListener)context);
                view.setTag(quickaction);
                return;
            }
        }

        private Legacy()
        {
        }

        Legacy(Legacy legacy)
        {
            this();
        }
    }

    public static interface PopupMenuCallback
    {

        public abstract boolean onMenuItemClick(MenuItem menuitem);
    }


    public static final IHelper sInstance;

    static 
    {
        Object obj;
        if (IRCApp.LEGACY_VERSION)
        {
            obj = new Legacy(null);
        } else
        {
            obj = new Current(null);
        }
        sInstance = ((IHelper) (obj));
    }
}
