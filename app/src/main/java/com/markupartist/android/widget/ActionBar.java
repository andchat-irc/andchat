// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.markupartist.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActionBar extends RelativeLayout
    implements android.view.View.OnClickListener, android.view.View.OnLongClickListener
{
    public static interface ActionBarItemClickHandler
    {

        public abstract void onActionBarItemClicked(GenericAction genericaction, View view);
    }

    public static final class GenericAction
    {

        private String mDescription;
        private int mDrawable;

        public String getDescription()
        {
            return mDescription;
        }

        public int getDrawable()
        {
            return mDrawable;
        }



        public GenericAction(int i, String s)
        {
            mDrawable = i;
            mDescription = s;
        }
    }


    private ActionBarItemClickHandler mActionClickListener;
    private LinearLayout mActionsView;
    private RelativeLayout mBarView;
    private LayoutInflater mInflater;
    private TextView mTitleView;

    public ActionBar(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        mBarView = (RelativeLayout)mInflater.inflate(R.layout.actionbar, null);
        addView(mBarView);
        mTitleView = (TextView)mBarView.findViewById(R.id.actionbar_title);
        mActionsView = (LinearLayout)mBarView.findViewById(R.id.actionbar_actions);
        context = context.obtainStyledAttributes(attributeset, R.styleable.ActionBar);
        attributeset = context.getString(0);
        if (attributeset != null)
        {
            setTitle(attributeset);
        }
        context.recycle();
    }

    private View get(int i)
    {
        return mActionsView.getChildAt(i);
    }

    private View inflateAction(GenericAction genericaction)
    {
        View view = mInflater.inflate(R.layout.actionbar_item, mActionsView, false);
        ImageButton imagebutton = (ImageButton)view.findViewById(R.id.actionbar_item);
        if (genericaction.getDrawable() != -1)
        {
            imagebutton.setImageResource(genericaction.getDrawable());
        }
        view.setTag(genericaction);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return view;
    }

    public void addAction(GenericAction genericaction)
    {
        addAction(genericaction, mActionsView.getChildCount());
    }

    public void addAction(GenericAction genericaction, int i)
    {
        mActionsView.addView(inflateAction(genericaction), i);
    }

    public void onClick(View view)
    {
        Object obj = view.getTag();
        if (obj instanceof GenericAction)
        {
            obj = (GenericAction)obj;
            if (mActionClickListener != null)
            {
                mActionClickListener.onActionBarItemClicked(((GenericAction) (obj)), view);
            }
        }
    }

    public boolean onLongClick(View view)
    {
label0:
        {
            view = ((View) (view.getTag()));
            if (view instanceof GenericAction)
            {
                view = ((GenericAction)view).getDescription();
                if (view != null)
                {
                    break label0;
                }
            }
            return false;
        }
        Toast.makeText(getContext(), view, 0).show();
        return true;
    }

    public void setActionDescription(int i, int j)
    {
        setActionDescription(i, getContext().getString(j));
    }

    public void setActionDescription(int i, String s)
    {
        ((GenericAction)get(i).getTag()).mDescription = s;
    }

    public void setActionDrawable(int i, int j)
    {
        ImageButton imagebutton = (ImageButton)get(i).findViewById(R.id.actionbar_item);
        imagebutton.setImageResource(j);
        ((GenericAction)imagebutton.getTag()).mDrawable = j;
    }

    public void setActionEnabled(int i, boolean flag)
    {
        get(i).setEnabled(flag);
    }

    public void setActionVisibility(int i, int j)
    {
        get(i).setVisibility(j);
    }

    public void setOnActionClickListener(ActionBarItemClickHandler actionbaritemclickhandler)
    {
        mActionClickListener = actionbaritemclickhandler;
    }

    public void setTitle(int i)
    {
        mTitleView.setText(i);
    }

    public void setTitle(CharSequence charsequence)
    {
        mTitleView.setText(charsequence);
    }
}
