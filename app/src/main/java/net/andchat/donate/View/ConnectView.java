// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class ConnectView extends ImageView
    implements android.view.View.OnClickListener, android.view.View.OnLongClickListener
{
    public static interface ConnectViewCallback
    {

        public abstract boolean onMenuItemClick(MenuItem menuitem);

        public abstract void onProviderClicked();
    }


    private String mDescription;
    private PopupMenu mPopupMenu;
    private boolean mShowMenu;

    public ConnectView(Context context)
    {
        this(context, null);
    }

    public ConnectView(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, 0x10102d8);
    }

    public ConnectView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init();
    }

    private void init()
    {
        android.app.ActionBar.LayoutParams layoutparams = new android.app.ActionBar.LayoutParams(-2, -1);
        layoutparams.gravity = 17;
        setLayoutParams(layoutparams);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    public void onClick(View view)
    {
        if (!mShowMenu)
        {
            ((ConnectViewCallback)getContext()).onProviderClicked();
            return;
        } else
        {
            view = new PopupMenu(getContext(), view);
            view.getMenuInflater().inflate(0x7f0f0001, view.getMenu());
            view.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {

                final ConnectView this$0;

                public boolean onMenuItemClick(MenuItem menuitem)
                {
                    return ((ConnectViewCallback)getContext()).onMenuItemClick(menuitem);
                }

            
            {
                this$0 = ConnectView.this;
                super();
            }
            });
            view.show();
            mPopupMenu = view;
            return;
        }
    }

    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (mPopupMenu != null)
        {
            mPopupMenu.dismiss();
            mPopupMenu = null;
        }
    }

    public boolean onLongClick(View view)
    {
        view = new int[2];
        Rect rect = new Rect();
        getLocationOnScreen(view);
        getWindowVisibleDisplayFrame(rect);
        Object obj = getContext();
        int i = getWidth();
        int j = getHeight();
        int k = view[1];
        int l = j / 2;
        int i1 = ((Context) (obj)).getResources().getDisplayMetrics().widthPixels;
        obj = Toast.makeText(((Context) (obj)), mDescription, 0);
        if (k + l < rect.height())
        {
            ((Toast) (obj)).setGravity(0x800035, i1 - view[0] - i / 2, j);
        } else
        {
            ((Toast) (obj)).setGravity(81, 0, j);
        }
        ((Toast) (obj)).show();
        return true;
    }

    public void setDescription(int i)
    {
        mDescription = getResources().getString(i);
    }

    public void setShowMenu(boolean flag)
    {
        mShowMenu = flag;
    }
}
