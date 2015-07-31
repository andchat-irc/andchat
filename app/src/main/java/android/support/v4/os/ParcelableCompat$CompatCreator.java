// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.os;

import android.os.Parcel;

// Referenced classes of package android.support.v4.os:
//            ParcelableCompat, ParcelableCompatCreatorCallbacks

static class mCallbacks
    implements android.os.patCreator
{

    final ParcelableCompatCreatorCallbacks mCallbacks;

    public Object createFromParcel(Parcel parcel)
    {
        return mCallbacks.createFromParcel(parcel, null);
    }

    public Object[] newArray(int i)
    {
        return mCallbacks.newArray(i);
    }

    public s(ParcelableCompatCreatorCallbacks parcelablecompatcreatorcallbacks)
    {
        mCallbacks = parcelablecompatcreatorcallbacks;
    }
}
