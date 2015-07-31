// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.widget.Gallery;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

private final class this._cls0 extends net.andchat.donate.Misc.ector
{

    final ChatWindow this$0;

    public void onLeftToRightSwipe()
    {
        int i = pGallery.getSelectedItemPosition();
        if (i > 0)
        {
            i--;
        } else
        {
            i = ChatWindow.access$7(ChatWindow.this).Count() - 1;
        }
        selectAndClick(i, true);
    }

    public void onRightToLeftSwipe()
    {
        int i = pGallery.getSelectedItemPosition();
        if (i == ChatWindow.access$7(ChatWindow.this).Count() - 1)
        {
            i = 0;
        } else
        {
            i++;
        }
        selectAndClick(i, true);
    }

    public (Context context)
    {
        this$0 = ChatWindow.this;
        super(context);
    }
}
