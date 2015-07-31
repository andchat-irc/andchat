// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Activities:
//            ChatWindow

private class mItems extends BaseAdapter
{

    private StringBuilder labelBuilder;
    private final Vector mItems;
    private final int mLen;
    final ChatWindow this$0;

    public void clear(int i)
    {
        if (i < mItems.size())
        {
            ((Session)mItems.get(i)).clearTextTypeInfo();
        }
    }

    public int findJumpTarget(int i, boolean flag, int j)
    {
        Vector vector;
        boolean flag1;
        int l;
        flag1 = true;
        vector = mItems;
        l = vector.size();
        if (i <= l && i >= 0 && (i == 0 || i - 1 >= 0)) goto _L2; else goto _L1
_L1:
        j = -1;
_L8:
        return j;
_L2:
        boolean flag2;
        boolean flag3;
        flag3 = Utils.isBitSet(j, 1);
        flag2 = Utils.isBitSet(j, 2);
        if (!flag2 || !flag3)
        {
            flag1 = false;
        }
        if (flag1)
        {
            flag2 = false;
            flag3 = false;
        }
        if (!flag) goto _L4; else goto _L3
_L3:
        i--;
_L13:
        if (i >= 0) goto _L6; else goto _L5
_L5:
        return -1;
_L6:
        Session session;
        session = (Session)vector.get(i);
        if (!flag1)
        {
            break; /* Loop/switch isn't completed */
        }
        j = i;
        if (session.getCurrentTextType() == 3) goto _L8; else goto _L7
_L7:
        j = i;
        if (session.isMarked()) goto _L8; else goto _L9
_L9:
        if (!flag3)
        {
            break; /* Loop/switch isn't completed */
        }
        if (session.getType() != 2)
        {
            break; /* Loop/switch isn't completed */
        }
        j = i;
        if (session.isMarked()) goto _L8; else goto _L10
_L10:
        j = i;
        if (session.getCurrentTextType() == 3) goto _L8; else goto _L11
_L11:
        if (!flag2)
        {
            break; /* Loop/switch isn't completed */
        }
        j = i;
        if (session.isMarked()) goto _L8; else goto _L12
_L12:
        i--;
          goto _L13
_L4:
        int k = i;
_L18:
        if (k >= l) goto _L5; else goto _L14
_L14:
        session = (Session)vector.get(k);
        if (!flag1 || session.getCurrentTextType() != 3 && !session.isMarked()) goto _L16; else goto _L15
_L15:
        j = k;
        if (k != i) goto _L8; else goto _L17
_L17:
        k++;
          goto _L18
_L16:
        if (!flag3)
        {
            break; /* Loop/switch isn't completed */
        }
        if (session.getType() != 2)
        {
            break; /* Loop/switch isn't completed */
        }
        j = k;
        if (session.isMarked()) goto _L8; else goto _L19
_L19:
        j = k;
        if (session.getCurrentTextType() == 3) goto _L8; else goto _L20
_L20:
        if (!flag2 || !session.isMarked()) goto _L17; else goto _L21
_L21:
        return k;
    }

    public int getCount()
    {
        return mLen;
    }

    public Object getItem(int i)
    {
        return mItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getPositionOf(String s)
    {
        Vector vector;
        int i;
        int k;
        vector = mItems;
        k = vector.size();
        i = 0;
_L6:
        if (i < k) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        j = i;
        if (((Session)vector.get(i)).getSessionName().equals(s)) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public int getPositionOf(Session session)
    {
        Vector vector;
        int i;
        int k;
        vector = mItems;
        k = vector.size();
        i = 0;
_L6:
        if (i < k) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        j = i;
        if (((Session)vector.get(i)).equals(session)) goto _L4; else goto _L3
_L3:
        i++;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public int getType(int i)
    {
        if (i < mItems.size())
        {
            return ((Session)mItems.get(i)).getCurrentTextType();
        } else
        {
            return 0;
        }
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        Session session;
        SpannableStringBuilder spannablestringbuilder;
        String s;
        StringBuilder stringbuilder;
        int j;
        if (view != null)
        {
            view = (TextView)view;
        } else
        {
            view = ChatWindow.access$0(ChatWindow.this, false);
        }
        session = (Session)mItems.get(i);
        s = session.getSessionName();
        j = session.getCurrentTextType();
        spannablestringbuilder = new SpannableStringBuilder();
        viewgroup = labelBuilder;
        viewgroup;
        JVM INSTR monitorenter ;
        if (session.isActive())
        {
            i = 0;
        } else
        {
            i = 1;
        }
        stringbuilder = labelBuilder;
        stringbuilder.append(" ");
        if (i == 0)
        {
            break MISSING_BLOCK_LABEL_88;
        }
        stringbuilder.append("(");
        stringbuilder.append(s);
        if (i == 0)
        {
            break MISSING_BLOCK_LABEL_108;
        }
        stringbuilder.append(")");
        stringbuilder.append(" ");
        spannablestringbuilder.append(stringbuilder);
        stringbuilder.delete(0, stringbuilder.length());
        viewgroup;
        JVM INSTR monitorexit ;
        if (s.equals(pCurrentSession.getSessionName()))
        {
            break MISSING_BLOCK_LABEL_295;
        }
        j;
        JVM INSTR tableswitch 0 3: default 188
    //                   0 188
    //                   1 223
    //                   2 247
    //                   3 271;
           goto _L1 _L1 _L2 _L3 _L4
_L1:
        session.markValidated();
_L5:
        view.setText(spannablestringbuilder);
        return view;
        view;
        viewgroup;
        JVM INSTR monitorexit ;
        throw view;
_L2:
        Utils.addColour(false, spannablestringbuilder, Colours.getInstance().getColourForEvent(0x7f0b000d), 0, spannablestringbuilder.length());
          goto _L1
_L3:
        Utils.addColour(false, spannablestringbuilder, Colours.getInstance().getColourForEvent(0x7f0b000e), 0, spannablestringbuilder.length());
          goto _L1
_L4:
        Utils.addColour(false, spannablestringbuilder, Colours.getInstance().getColourForEvent(0x7f0b000f), 0, spannablestringbuilder.length());
          goto _L1
        session.clearTextTypeInfo();
          goto _L5
    }

    public _cls9(Context context, List list)
    {
        int i;
        int j;
        this$0 = ChatWindow.this;
        super();
        labelBuilder = new StringBuilder();
        j = list.size();
        mItems = new Vector(j);
        i = 0;
_L5:
        if (i < j) goto _L2; else goto _L1
_L1:
        Collections.sort(list);
        mItems.addAll(list);
        mLen = mItems.size();
        return;
_L2:
        chatwindow = (Session)list.get(i);
        if (!getSessionName().equals("Status"))
        {
            break; /* Loop/switch isn't completed */
        }
        mItems.add(0, ChatWindow.this);
        list.remove(ChatWindow.this);
        if (true) goto _L1; else goto _L3
_L3:
        i++;
        if (true) goto _L5; else goto _L4
_L4:
    }
}
