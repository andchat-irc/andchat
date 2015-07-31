// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import com.markupartist.android.widget.ActionBar;
import java.io.IOException;
import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;
import org.xmlpull.v1.XmlPullParserException;

// Referenced classes of package net.andchat.donate.Misc:
//            Utils

public abstract class AbstractMenuInflator
{
    public static class ActionBarMenu extends AbstractMenuInflator
    {

        private boolean haveOverflow;
        private final ActionBar mActionBar;
        private final Context mContext;

        public void addItem(String s, int i, int j, int k)
        {
            if (k == 0)
            {
                s = new com.markupartist.android.widget.ActionBar.GenericAction(0x7f020039, mContext.getString(0x7f0a009c));
                mActionBar.addAction(s);
                return;
            } else
            {
                s = new com.markupartist.android.widget.ActionBar.GenericAction(i, s);
                mActionBar.addAction(s);
                return;
            }
        }

        public boolean shouldParseItem(int i)
        {
            if (i == 0 && !haveOverflow)
            {
                haveOverflow = true;
                return true;
            } else
            {
                return false | Utils.isBitSet(i, 2) | Utils.isBitSet(i, 1);
            }
        }

        public ActionBarMenu(ActionBar actionbar, Context context)
        {
            mActionBar = actionbar;
            mContext = context;
        }
    }

    public static class QuickActionsMenu extends AbstractMenuInflator
    {

        private final QuickAction mQuickActions;

        public void addItem(String s, int i, int j, int k)
        {
            mQuickActions.addActionItem(new ActionItem(j, s));
        }

        public boolean shouldParseItem(int i)
        {
            return i == 0;
        }

        public QuickActionsMenu(QuickAction quickaction)
        {
            mQuickActions = quickaction;
        }
    }

    public static class SlideInMenu extends AbstractMenuInflator
    {

        private final int mButtonResource;
        private final android.view.View.OnClickListener mListener;
        private final ViewGroup mRoot;

        public void addItem(String s, int i, int j, int k)
        {
            ViewGroup viewgroup = mRoot;
            Button button = (Button)LayoutInflater.from(viewgroup.getContext()).inflate(mButtonResource, viewgroup, false);
            button.setId(j);
            button.setText(s);
            button.setOnClickListener(mListener);
            viewgroup.addView(button);
        }

        public boolean shouldParseItem(int i)
        {
            return false | Utils.isBitSet(i, 2) | Utils.isBitSet(i, 1);
        }

        public SlideInMenu(ViewGroup viewgroup, int i, android.view.View.OnClickListener onclicklistener)
        {
            mRoot = viewgroup;
            mButtonResource = i;
            mListener = onclicklistener;
        }
    }


    public AbstractMenuInflator()
    {
    }

    public void addActionsFromXML(Context context, int i)
    {
        Context context1;
        Context context2;
        Context context3;
        String s;
        String s1;
        Object obj;
        s1 = null;
        obj = null;
        s = null;
        context2 = s;
        context1 = s1;
        context3 = obj;
        Resources resources = context.getResources();
        context2 = s;
        context1 = s1;
        context3 = obj;
        context = resources.getXml(i);
        context2 = context;
        context1 = context;
        context3 = context;
        int j = context.getEventType();
        i = 0;
_L4:
        if (i != 0)
        {
            if (context != null)
            {
                context.close();
            }
            return;
        }
        j;
        JVM INSTR tableswitch 1 2: default 100
    //                   1 413
    //                   2 129;
           goto _L1 _L2 _L3
_L2:
        break MISSING_BLOCK_LABEL_413;
_L1:
        j = i;
_L6:
        context2 = context;
        context1 = context;
        context3 = context;
        int k = context.next();
        i = j;
        j = k;
          goto _L4
_L3:
        j = i;
        context2 = context;
        context1 = context;
        context3 = context;
        if (!context.getName().equals("item")) goto _L6; else goto _L5
_L5:
        context2 = context;
        context1 = context;
        context3 = context;
        k = context.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "id", 0);
        j = i;
        if (k == 0) goto _L6; else goto _L7
_L7:
        context2 = context;
        context1 = context;
        context3 = context;
        int l = context.getAttributeIntValue("http://schemas.android.com/apk/res/android", "showAsAction", -1);
        j = i;
        context2 = context;
        context1 = context;
        context3 = context;
        if (!shouldParseItem(l)) goto _L6; else goto _L8
_L8:
        context2 = context;
        context1 = context;
        context3 = context;
        j = context.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "icon", -1);
        context2 = context;
        context1 = context;
        context3 = context;
        s1 = context.getAttributeValue("http://schemas.android.com/apk/res/android", "title");
        s = s1;
        context2 = context;
        context1 = context;
        context3 = context;
        if (s1.length() <= 0) goto _L10; else goto _L9
_L9:
        s = s1;
        context2 = context;
        context1 = context;
        context3 = context;
        if (s1.charAt(0) != '@') goto _L10; else goto _L11
_L11:
        context2 = context;
        context1 = context;
        context3 = context;
        int i1 = context.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "title", -1);
        if (i1 != -1) goto _L13; else goto _L12
_L12:
        s = "";
_L10:
        context2 = context;
        context1 = context;
        context3 = context;
        addItem(s, j, k, l);
        j = i;
          goto _L6
        context;
        context1 = context2;
        throw new InflateException("Error inflating menu XML", context);
        context;
        if (context1 != null)
        {
            context1.close();
        }
        throw context;
_L13:
        context2 = context;
        context1 = context;
        context3 = context;
        s = resources.getString(i1);
          goto _L10
        j = 1;
          goto _L6
        context;
        context1 = context3;
        throw new InflateException("Error inflating menu XML", context);
          goto _L4
    }

    public abstract void addItem(String s, int i, int j, int k);

    public abstract boolean shouldParseItem(int i);
}
