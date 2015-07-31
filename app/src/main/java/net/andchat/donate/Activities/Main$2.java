// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.Toast;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Backend.IRCService;

// Referenced classes of package net.andchat.donate.Activities:
//            Main

class val.label
    implements android.content.nterface.OnClickListener
{

    final Main this$0;
    private final String val$label;

    public void onClick(DialogInterface dialoginterface, int i)
    {
        final int id = pDb.getId(val$label);
        boolean flag = false;
        i = ((flag) ? 1 : 0);
        if (pService != null)
        {
            i = pService.getServerState(id);
            if (i != 0 && i != 4)
            {
                i = 1;
                (new Thread() {

                    final Main._cls2 this$1;
                    private final int val$id;

                    public void run()
                    {
                        net.andchat.donate.Backend.ServerConnection serverconnection = pService.getServer(id, false, null, null);
                        pService.stopConnection(id, 1);
                        pService.remove(serverconnection);
                        pService.cleanUpIfRequired(id);
                    }

            
            {
                this$1 = Main._cls2.this;
                id = i;
                super();
            }
                }).start();
            } else
            {
                dialoginterface = pService.getServer(id, false, null, null);
                if (dialoginterface != null)
                {
                    pService.remove(dialoginterface);
                    i = ((flag) ? 1 : 0);
                } else
                {
                    pService.cleanUpIfRequired(id);
                    i = ((flag) ? 1 : 0);
                }
            }
        }
        if (pDb.deleteServer(val$label))
        {
            if (i != 0)
            {
                dialoginterface = getString(0x7f0a0128, new Object[] {
                    val$label
                });
            } else
            {
                dialoginterface = getString(0x7f0a0129, new Object[] {
                    val$label
                });
            }
            Toast.makeText(Main.this, dialoginterface, 0).show();
            pAdapter.remove(val$label);
            if (pAdapter.getCount() == 0)
            {
                mListView.setAdapter(getAdapter());
            }
            return;
        } else
        {
            Toast.makeText(Main.this, getString(0x7f0a012d, new Object[] {
                val$label
            }), 0).show();
            return;
        }
    }


    _cls1.val.id()
    {
        this$0 = final_main;
        val$label = String.this;
        super();
    }
}
