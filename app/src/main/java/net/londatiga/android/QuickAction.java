// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.londatiga.android;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package net.londatiga.android:
//            PopupWindows, ActionItem

public class QuickAction extends PopupWindows
    implements android.widget.PopupWindow.OnDismissListener
{
    public static interface OnDismissListener
    {

        public abstract void onDismiss();
    }

    public static interface OnQuickActionItemClickListener
    {

        public abstract void onItemClick(QuickAction quickaction, int i, int j);
    }


    private List actionItems;
    private int mAnimStyle;
    private ImageView mArrowDown;
    private ImageView mArrowUp;
    private int mChildPos;
    private boolean mDidAction;
    private OnDismissListener mDismissListener;
    private LayoutInflater mInflater;
    private int mInsertPos;
    private OnQuickActionItemClickListener mItemClickListener;
    private int mOrientation;
    private View mRootView;
    private ScrollView mScroller;
    private ViewGroup mTrack;
    private int rootWidth;

    public QuickAction(Context context)
    {
        this(context, 1);
    }

    public QuickAction(Context context, int i)
    {
        super(context);
        actionItems = new ArrayList();
        rootWidth = 0;
        mOrientation = i;
        mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        if (mOrientation == 0)
        {
            setRootViewId(R.layout.popup_horizontal);
        } else
        {
            setRootViewId(R.layout.popup_vertical);
        }
        mAnimStyle = 5;
        mChildPos = 0;
    }

    private void setAnimationStyle(int i, int j, boolean flag)
    {
        j -= mArrowUp.getMeasuredWidth() / 2;
        switch (mAnimStyle)
        {
        default:
            return;

        case 5: // '\005'
            break;
        }
        if (j <= i / 4)
        {
            PopupWindow popupwindow = mWindow;
            if (flag)
            {
                i = R.style.Animations_PopUpMenu_Left;
            } else
            {
                i = R.style.Animations_PopDownMenu_Left;
            }
            popupwindow.setAnimationStyle(i);
            return;
        }
        if (j > i / 4 && j < (i / 4) * 3)
        {
            PopupWindow popupwindow1 = mWindow;
            if (flag)
            {
                i = R.style.Animations_PopUpMenu_Center;
            } else
            {
                i = R.style.Animations_PopDownMenu_Center;
            }
            popupwindow1.setAnimationStyle(i);
            return;
        }
        PopupWindow popupwindow2 = mWindow;
        if (flag)
        {
            i = R.style.Animations_PopUpMenu_Right;
        } else
        {
            i = R.style.Animations_PopDownMenu_Right;
        }
        popupwindow2.setAnimationStyle(i);
    }

    private void showArrow(int i, int j)
    {
        ImageView imageview;
        ImageView imageview1;
        if (i == R.id.qa_arrow_up)
        {
            imageview = mArrowUp;
        } else
        {
            imageview = mArrowDown;
        }
        if (i == R.id.qa_arrow_up)
        {
            imageview1 = mArrowDown;
        } else
        {
            imageview1 = mArrowUp;
        }
        i = mArrowUp.getMeasuredWidth();
        imageview.setVisibility(8);
        ((android.view.ViewGroup.MarginLayoutParams)imageview.getLayoutParams()).leftMargin = j - i / 2;
        imageview1.setVisibility(8);
    }

    public void addActionItem(ActionItem actionitem)
    {
        actionItems.add(actionitem);
        String s = actionitem.getTitle();
        View view;
        TextView textview;
        if (mOrientation == 0)
        {
            view = mInflater.inflate(R.layout.action_item_horizontal, null);
        } else
        {
            view = mInflater.inflate(R.layout.action_item_vertical, null);
        }
        textview = (TextView)view.findViewById(R.id.tv_title);
        if (s != null)
        {
            textview.setText(s);
        } else
        {
            textview.setVisibility(8);
        }
        view.setOnClickListener(new android.view.View.OnClickListener() {

            final QuickAction this$0;
            private final int val$actionId;
            private final int val$pos;

            public void onClick(View view1)
            {
                if (mItemClickListener != null)
                {
                    mItemClickListener.onItemClick(QuickAction.this, pos, actionId);
                }
                mDidAction = true;
                dismiss();
            }

            
            {
                this$0 = QuickAction.this;
                pos = i;
                actionId = j;
                super();
            }
        });
        view.setFocusable(true);
        view.setClickable(true);
        if (mOrientation == 0 && mChildPos != 0)
        {
            actionitem = mInflater.inflate(R.layout.horiz_separator, null);
            actionitem.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(-2, -1));
            actionitem.setPadding(5, 0, 5, 0);
            mTrack.addView(actionitem, mInsertPos);
            mInsertPos = mInsertPos + 1;
        }
        mTrack.addView(view, mInsertPos);
        mChildPos = mChildPos + 1;
        mInsertPos = mInsertPos + 1;
    }

    public boolean isShowing()
    {
        return mWindow != null && mWindow.isShowing();
    }

    public void onDismiss()
    {
        if (!mDidAction && mDismissListener != null)
        {
            mDismissListener.onDismiss();
        }
    }

    public void setOnActionItemClickListener(OnQuickActionItemClickListener onquickactionitemclicklistener)
    {
        mItemClickListener = onquickactionitemclicklistener;
    }

    public void setRootViewId(int i)
    {
        mRootView = (ViewGroup)mInflater.inflate(i, null);
        mTrack = (ViewGroup)mRootView.findViewById(R.id.tracks);
        mArrowDown = (ImageView)mRootView.findViewById(R.id.qa_arrow_down);
        mArrowUp = (ImageView)mRootView.findViewById(R.id.qa_arrow_up);
        mScroller = (ScrollView)mRootView.findViewById(R.id.scroller);
        mRootView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        setContentView(mRootView);
    }

    public void show(View view)
    {
        show(view, false);
    }

    public void show(View view, boolean flag)
    {
        preShow();
        mDidAction = false;
        int ai[] = new int[2];
        view.getLocationOnScreen(ai);
        Rect rect = new Rect(ai[0], ai[1], ai[0] + view.getWidth(), ai[1] + view.getHeight());
        mRootView.measure(-2, -2);
        int k1 = mRootView.getMeasuredHeight();
        if (rootWidth == 0)
        {
            rootWidth = mRootView.getMeasuredWidth();
        }
        int j1 = mWindowManager.getDefaultDisplay().getWidth();
        int i1 = mWindowManager.getDefaultDisplay().getHeight();
        int j;
        int k;
        int l;
        int l1;
        int i2;
        boolean flag1;
        if (rect.left + rootWidth > j1)
        {
            k = rect.left - (rootWidth - view.getWidth());
            int i = k;
            if (k < 0)
            {
                i = 0;
            }
            k = rect.centerX() - i;
            l = i;
        } else
        {
            if (view.getWidth() > rootWidth)
            {
                j = rect.centerX() - rootWidth / 2;
            } else
            {
                j = rect.left;
            }
            k = rect.centerX() - j;
            l = j;
        }
        l1 = rect.top;
        i2 = i1 - rect.bottom;
        if (l1 > i2)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        if (flag1)
        {
            if (k1 > l1)
            {
                j = 15;
                mScroller.getLayoutParams().height = l1 - view.getHeight();
            } else
            {
                j = rect.top - k1;
            }
        } else
        {
            i1 = rect.bottom;
            j = i1;
            if (k1 > i2)
            {
                mScroller.getLayoutParams().height = i2;
                j = i1;
            }
        }
        if (flag1)
        {
            i1 = R.id.qa_arrow_down;
        } else
        {
            i1 = R.id.qa_arrow_up;
        }
        showArrow(i1, k);
        setAnimationStyle(j1, rect.centerX(), flag1);
        if (flag)
        {
            mWindow.showAtLocation(view, 0, ai[0], ai[1]);
            return;
        } else
        {
            mWindow.showAtLocation(view, 0, l, j);
            return;
        }
    }


}
