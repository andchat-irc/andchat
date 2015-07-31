// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Intent;
import android.view.Menu;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.View.InterceptingEditText;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow, UserListActivity

public class LegacyUI extends ChatWindow
{

    public LegacyUI()
    {
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.updateLegacyActionBar();
        return false;
    }

    void onUserlistMenuClicked()
    {
        if (!pCurrentSession.hasCapability(5))
        {
            return;
        } else
        {
            Intent intent = new Intent(this, net/andchat/donate/Activities/UserListActivity);
            intent.putExtra("window", pCurrentSession.getSessionName());
            intent.putExtra("id", pId);
            intent.putExtra("text", pEt.getText());
            startActivityForResult(intent, 10);
            return;
        }
    }

    void performInitialization()
    {
    }
}
