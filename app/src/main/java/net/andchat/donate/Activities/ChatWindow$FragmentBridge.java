// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Gallery;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Sessions.Session;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

class this._cls0 extends Handler
{

    final ChatWindow this$0;

    public void handleMessage(Message message)
    {
        Object obj;
        int i;
        obj = null;
        i = message.what;
        i;
        JVM INSTR lookupswitch 3: default 44
    //                   8: 52
    //                   2131230855: 52
    //                   2131230856: 52;
           goto _L1 _L2 _L2 _L2
_L1:
        obj = (String)message.obj;
_L2:
        i;
        JVM INSTR lookupswitch 16: default 192
    //                   8: 570
    //                   2131230725: 230
    //                   2131230726: 269
    //                   2131230727: 278
    //                   2131230728: 304
    //                   2131230729: 356
    //                   2131230730: 365
    //                   2131230731: 291
    //                   2131230732: 317
    //                   2131230733: 330
    //                   2131230734: 343
    //                   2131230735: 374
    //                   2131230855: 676
    //                   2131230856: 676
    //                   2131230857: 391
    //                   2131230858: 542;
           goto _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L16 _L17 _L18
_L3:
        Log.w("IRCChatWindow", (new StringBuilder("unknown command, m.what=")).append(message.what).append(", obj=").append(message.obj).toString());
        return;
_L5:
        int j = ChatWindow.access$7(ChatWindow.this).ositionOf(((String) (obj)));
        if (j != -1)
        {
            selectAndClick(j, true);
            return;
        } else
        {
            handleStartPm(((String) (obj)));
            return;
        }
_L6:
        ChatWindow.access$11(ChatWindow.this, ((String) (obj)));
        return;
_L7:
        handleOpAction(((String) (obj)), '+', 'o');
        return;
_L11:
        handleOpAction(((String) (obj)), '-', 'o');
        return;
_L8:
        handleOpAction(((String) (obj)), '+', 'v');
        return;
_L12:
        handleOpAction(((String) (obj)), '-', 'v');
        return;
_L13:
        handleOpAction(((String) (obj)), '+', 'h');
        return;
_L14:
        handleOpAction(((String) (obj)), '-', 'h');
        return;
_L9:
        handleKick(((String) (obj)));
        return;
_L10:
        handleBan(((String) (obj)));
        return;
_L15:
        handleBan(((String) (obj)));
        handleKick(((String) (obj)));
        return;
_L17:
        net.andchat.donate.View.InterceptingEditText interceptingedittext;
        String as[];
        int k;
        int l;
        interceptingedittext = pEt;
        boolean flag;
        if (interceptingedittext.length() > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (flag)
        {
            interceptingedittext.append(" ");
        }
        message = null;
        if (((String) (obj)).indexOf(',') == -1) goto _L20; else goto _L19
_L19:
        message = new StringBuilder();
        as = ((String) (obj)).split(",");
        l = as.length;
        k = 0;
_L22:
        if (k < l) goto _L21; else goto _L20
_L20:
        if (message != null)
        {
            obj = message;
        }
        interceptingedittext.append(((CharSequence) (obj)));
        if (flag)
        {
            interceptingedittext.append(" ");
            return;
        } else
        {
            interceptingedittext.append(": ");
            return;
        }
_L21:
        message.append(as[k]);
        if (k != l - 1)
        {
            message.append(',').append(' ');
        }
        k++;
          goto _L22
_L18:
        ChatWindow.access$12(ChatWindow.this, (new StringBuilder("/ignore ")).append(message.obj).toString(), false);
        return;
_L4:
        message = (itchChannelInfo)message.obj;
        if (((itchChannelInfo) (message)).manager == pSessionManager)
        {
            selectAndClick(ChatWindow.access$7(ChatWindow.this).ositionOf(((itchChannelInfo) (message)).sessionName), true);
            return;
        }
        if (pGallery.getVisibility() == 8 && ChatWindow.access$7(ChatWindow.this).ositionOf(pCurrentSession) >= 0)
        {
            pCurrentSession.clearTextTypeInfo();
        }
        ChatWindow.access$13(ChatWindow.this, ((itchChannelInfo) (message)).id, ((itchChannelInfo) (message)).sessionName);
        return;
_L16:
        message = (nCommand)message.obj;
        mService.runOnAnotherProfile(((nCommand) (message)).id, (new StringBuilder(String.valueOf(((nCommand) (message)).command))).append(" ").append(((nCommand) (message)).params).toString());
        return;
    }

    nCommand()
    {
        this$0 = ChatWindow.this;
        super();
    }
}
