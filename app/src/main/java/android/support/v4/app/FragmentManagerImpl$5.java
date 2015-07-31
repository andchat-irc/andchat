// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.view.animation.Animation;

// Referenced classes of package android.support.v4.app:
//            FragmentManagerImpl, Fragment

class val.fragment
    implements android.view.animation.tener
{

    final FragmentManagerImpl this$0;
    final Fragment val$fragment;

    public void onAnimationEnd(Animation animation)
    {
        if (val$fragment.mAnimatingAway != null)
        {
            val$fragment.mAnimatingAway = null;
            moveToState(val$fragment, val$fragment.mStateAfterAnimating, 0, 0, false);
        }
    }

    public void onAnimationRepeat(Animation animation)
    {
    }

    public void onAnimationStart(Animation animation)
    {
    }

    tener()
    {
        this$0 = final_fragmentmanagerimpl;
        val$fragment = Fragment.this;
        super();
    }
}
