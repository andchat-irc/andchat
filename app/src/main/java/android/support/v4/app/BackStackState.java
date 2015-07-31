// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

// Referenced classes of package android.support.v4.app:
//            BackStackRecord, Fragment, FragmentManagerImpl

final class BackStackState
    implements Parcelable
{

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public BackStackState createFromParcel(Parcel parcel)
        {
            return new BackStackState(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public BackStackState[] newArray(int i)
        {
            return new BackStackState[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    };
    final int mBreadCrumbShortTitleRes;
    final CharSequence mBreadCrumbShortTitleText;
    final int mBreadCrumbTitleRes;
    final CharSequence mBreadCrumbTitleText;
    final int mIndex;
    final String mName;
    final int mOps[];
    final int mTransition;
    final int mTransitionStyle;

    public BackStackState(Parcel parcel)
    {
        mOps = parcel.createIntArray();
        mTransition = parcel.readInt();
        mTransitionStyle = parcel.readInt();
        mName = parcel.readString();
        mIndex = parcel.readInt();
        mBreadCrumbTitleRes = parcel.readInt();
        mBreadCrumbTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        mBreadCrumbShortTitleRes = parcel.readInt();
        mBreadCrumbShortTitleText = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public BackStackState(FragmentManagerImpl fragmentmanagerimpl, BackStackRecord backstackrecord)
    {
        int i = 0;
        for (fragmentmanagerimpl = backstackrecord.mHead; fragmentmanagerimpl != null;)
        {
            int j = i;
            if (((BackStackRecord.Op) (fragmentmanagerimpl)).removed != null)
            {
                j = i + ((BackStackRecord.Op) (fragmentmanagerimpl)).removed.size();
            }
            fragmentmanagerimpl = ((BackStackRecord.Op) (fragmentmanagerimpl)).next;
            i = j;
        }

        mOps = new int[backstackrecord.mNumOp * 7 + i];
        if (!backstackrecord.mAddToBackStack)
        {
            throw new IllegalStateException("Not on back stack");
        }
        fragmentmanagerimpl = backstackrecord.mHead;
        i = 0;
        while (fragmentmanagerimpl != null) 
        {
            int ai[] = mOps;
            int k = i + 1;
            ai[i] = ((BackStackRecord.Op) (fragmentmanagerimpl)).cmd;
            ai = mOps;
            int i1 = k + 1;
            int k1;
            if (((BackStackRecord.Op) (fragmentmanagerimpl)).fragment != null)
            {
                i = ((BackStackRecord.Op) (fragmentmanagerimpl)).fragment.mIndex;
            } else
            {
                i = -1;
            }
            ai[k] = i;
            ai = mOps;
            i = i1 + 1;
            ai[i1] = ((BackStackRecord.Op) (fragmentmanagerimpl)).enterAnim;
            ai = mOps;
            k = i + 1;
            ai[i] = ((BackStackRecord.Op) (fragmentmanagerimpl)).exitAnim;
            ai = mOps;
            i = k + 1;
            ai[k] = ((BackStackRecord.Op) (fragmentmanagerimpl)).popEnterAnim;
            ai = mOps;
            k1 = i + 1;
            ai[i] = ((BackStackRecord.Op) (fragmentmanagerimpl)).popExitAnim;
            if (((BackStackRecord.Op) (fragmentmanagerimpl)).removed != null)
            {
                int j1 = ((BackStackRecord.Op) (fragmentmanagerimpl)).removed.size();
                mOps[k1] = j1;
                int l = 0;
                for (i = k1 + 1; l < j1; i++)
                {
                    mOps[i] = ((Fragment)((BackStackRecord.Op) (fragmentmanagerimpl)).removed.get(l)).mIndex;
                    l++;
                }

            } else
            {
                int ai1[] = mOps;
                i = k1 + 1;
                ai1[k1] = 0;
            }
            fragmentmanagerimpl = ((BackStackRecord.Op) (fragmentmanagerimpl)).next;
        }
        mTransition = backstackrecord.mTransition;
        mTransitionStyle = backstackrecord.mTransitionStyle;
        mName = backstackrecord.mName;
        mIndex = backstackrecord.mIndex;
        mBreadCrumbTitleRes = backstackrecord.mBreadCrumbTitleRes;
        mBreadCrumbTitleText = backstackrecord.mBreadCrumbTitleText;
        mBreadCrumbShortTitleRes = backstackrecord.mBreadCrumbShortTitleRes;
        mBreadCrumbShortTitleText = backstackrecord.mBreadCrumbShortTitleText;
    }

    public int describeContents()
    {
        return 0;
    }

    public BackStackRecord instantiate(FragmentManagerImpl fragmentmanagerimpl)
    {
        BackStackRecord backstackrecord = new BackStackRecord(fragmentmanagerimpl);
        int i = 0;
        for (int k = 0; i < mOps.length; k++)
        {
            BackStackRecord.Op op = new BackStackRecord.Op();
            int ai[] = mOps;
            int j = i + 1;
            op.cmd = ai[i];
            if (FragmentManagerImpl.DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("Instantiate ").append(backstackrecord).append(" op #").append(k).append(" base fragment #").append(mOps[j]).toString());
            }
            ai = mOps;
            i = j + 1;
            j = ai[j];
            int l;
            int j1;
            if (j >= 0)
            {
                op.fragment = (Fragment)fragmentmanagerimpl.mActive.get(j);
            } else
            {
                op.fragment = null;
            }
            ai = mOps;
            j = i + 1;
            op.enterAnim = ai[i];
            ai = mOps;
            i = j + 1;
            op.exitAnim = ai[j];
            ai = mOps;
            j = i + 1;
            op.popEnterAnim = ai[i];
            ai = mOps;
            l = j + 1;
            op.popExitAnim = ai[j];
            ai = mOps;
            i = l + 1;
            j1 = ai[l];
            j = i;
            if (j1 > 0)
            {
                op.removed = new ArrayList(j1);
                int i1 = 0;
                do
                {
                    j = i;
                    if (i1 >= j1)
                    {
                        break;
                    }
                    if (FragmentManagerImpl.DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("Instantiate ").append(backstackrecord).append(" set remove fragment #").append(mOps[i]).toString());
                    }
                    Fragment fragment = (Fragment)fragmentmanagerimpl.mActive.get(mOps[i]);
                    op.removed.add(fragment);
                    i1++;
                    i++;
                } while (true);
            }
            i = j;
            backstackrecord.addOp(op);
        }

        backstackrecord.mTransition = mTransition;
        backstackrecord.mTransitionStyle = mTransitionStyle;
        backstackrecord.mName = mName;
        backstackrecord.mIndex = mIndex;
        backstackrecord.mAddToBackStack = true;
        backstackrecord.mBreadCrumbTitleRes = mBreadCrumbTitleRes;
        backstackrecord.mBreadCrumbTitleText = mBreadCrumbTitleText;
        backstackrecord.mBreadCrumbShortTitleRes = mBreadCrumbShortTitleRes;
        backstackrecord.mBreadCrumbShortTitleText = mBreadCrumbShortTitleText;
        backstackrecord.bumpBackStackNesting(1);
        return backstackrecord;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeIntArray(mOps);
        parcel.writeInt(mTransition);
        parcel.writeInt(mTransitionStyle);
        parcel.writeString(mName);
        parcel.writeInt(mIndex);
        parcel.writeInt(mBreadCrumbTitleRes);
        TextUtils.writeToParcel(mBreadCrumbTitleText, parcel, 0);
        parcel.writeInt(mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel(mBreadCrumbShortTitleText, parcel, 0);
    }

}
