// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;


// Referenced classes of package android.support.v4.app:
//            FragmentManagerImpl

class this._cls0
    implements Runnable
{

    final FragmentManagerImpl this$0;

    public void run()
    {
        execPendingActions();
    }

    ()
    {
        this$0 = FragmentManagerImpl.this;
        super();
    }
}
