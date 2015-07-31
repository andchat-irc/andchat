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

class val.id extends Thread
{

    final val.id this$1;
    private final int val$id;

    public void run()
    {
        net.andchat.donate.Backend.ServerConnection serverconnection = pService.getServer(val$id, false, null, null);
        pService.stopConnection(val$id, 1);
        pService.remove(serverconnection);
        pService.cleanUpIfRequired(val$id);
    }

    l.label()
    {
        this$1 = final_label;
        val$id = I.this;
        super();
    }

    // Unreferenced inner class net/andchat/donate/Activities/Main$2

/* anonymous class */
    class Main._cls2
        implements android.content.DialogInterface.OnClickListener
    {

        final Main this$0;
        private final String val$label;

        public void onClick(DialogInterface dialoginterface, int i)
        {
            int j = pDb.getId(label);
            boolean flag = false;
            i = ((flag) ? 1 : 0);
            if (pService != null)
            {
                i = pService.getServerState(j);
                if (i != 0 && i != 4)
                {
                    i = 1;
                    (j. new Main._cls2._cls1()).start();
                } else
                {
                    dialoginterface = pService.getServer(j, false, null, null);
                    if (dialoginterface != null)
                    {
                        pService.remove(dialoginterface);
                        i = ((flag) ? 1 : 0);
                    } else
                    {
                        pService.cleanUpIfRequired(j);
                        i = ((flag) ? 1 : 0);
                    }
                }
            }
            if (pDb.deleteServer(label))
            {
                if (i != 0)
                {
                    dialoginterface = getString(0x7f0a0128, new Object[] {
                        label
                    });
                } else
                {
                    dialoginterface = getString(0x7f0a0129, new Object[] {
                        label
                    });
                }
                Toast.makeText(Main.this, dialoginterface, 0).show();
                pAdapter.remove(label);
                if (pAdapter.getCount() == 0)
                {
                    mListView.setAdapter(getAdapter());
                }
                return;
            } else
            {
                Toast.makeText(Main.this, getString(0x7f0a012d, new Object[] {
                    label
                }), 0).show();
                return;
            }
        }


            
            {
                this$0 = final_main;
                label = String.this;
                super();
            }
    }

}
