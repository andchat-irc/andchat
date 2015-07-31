// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;


// Referenced classes of package android.support.v4.app:
//            Fragment

public abstract class FragmentTransaction
{

    public FragmentTransaction()
    {
    }

    public abstract FragmentTransaction add(int i, Fragment fragment, String s);

    public abstract FragmentTransaction attach(Fragment fragment);

    public abstract int commit();

    public abstract FragmentTransaction detach(Fragment fragment);
}
