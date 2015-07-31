// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

// Referenced classes of package android.support.v4.app:
//            Fragment

public static class mState
    implements Parcelable
{

    public static final android.os..SavedState.mState CREATOR = new android.os.Parcelable.Creator() {

        public Fragment.SavedState createFromParcel(Parcel parcel)
        {
            return new Fragment.SavedState(parcel, null);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public Fragment.SavedState[] newArray(int i)
        {
            return new Fragment.SavedState[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    };
    final Bundle mState;

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeBundle(mState);
    }


    _cls1(Parcel parcel, ClassLoader classloader)
    {
        mState = parcel.readBundle();
        if (classloader != null && mState != null)
        {
            mState.setClassLoader(classloader);
        }
    }
}
