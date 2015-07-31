// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.TextKeyListener;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Backend.MessageSender;
import net.andchat.donate.Backend.ServerConnection;
import net.andchat.donate.Backend.Sessions.Session;
import net.andchat.donate.Backend.Sessions.SessionManager;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.CommandParser;
import net.andchat.donate.Misc.IRCMessage;
import net.andchat.donate.Misc.LimitedSizeQueue;
import net.andchat.donate.Misc.ServerProfile;
import net.andchat.donate.Misc.Utils;
import net.andchat.donate.View.ConnectView;
import net.andchat.donate.View.InterceptingEditText;
import net.andchat.donate.View.InterceptingLinearLayout;
import net.andchat.donate.View.TextStyleDialog;
import net.londatiga.android.QuickAction;

// Referenced classes of package net.andchat.donate.Activities:
//            Preferences, CopyTextActivity, PasswordActivity, IgnoresActivity, 
//            Main

public abstract class ChatWindow extends Activity
    implements ServiceConnection, android.view.View.OnClickListener, android.view.View.OnKeyListener, android.widget.AbsListView.OnScrollListener, android.widget.AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemSelectedListener, com.markupartist.android.widget.ActionBar.ActionBarItemClickHandler, net.andchat.donate.Backend.IRCService.ServerStateListener, net.andchat.donate.IRCApp.PreferenceChangeWatcher, net.andchat.donate.Misc.CommandParser.Helper, net.andchat.donate.View.ConnectView.ConnectViewCallback, net.andchat.donate.View.InterceptingEditText.PreImeListener, net.londatiga.android.QuickAction.OnQuickActionItemClickListener
{
    private static final class EmptyAdapter extends BaseAdapter
    {

        public int getCount()
        {
            return 0;
        }

        public Object getItem(int i)
        {
            return null;
        }

        public long getItemId(int i)
        {
            return 0L;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            return null;
        }

        public EmptyAdapter()
        {
        }
    }

    private class FlagHandler extends Handler
    {

        final ChatWindow this$0;

        public void handleMessage(Message message)
        {
            message.what;
            JVM INSTR tableswitch 1 7: default 48
        //                       1 49
        //                       2 78
        //                       3 163
        //                       4 94
        //                       5 186
        //                       6 277
        //                       7 318;
               goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
            return;
_L2:
            addSession(message.obj.toString(), true);
            if (!IRCApp.LEGACY_VERSION)
            {
                refreshMenu();
                return;
            }
            continue; /* Loop/switch isn't completed */
_L3:
            addSession(message.obj.toString(), false);
            return;
_L5:
            int i;
label0:
            {
                message = message.obj.toString();
                i = pAdapter.getPositionOf(message);
                if (i == -1)
                {
                    continue; /* Loop/switch isn't completed */
                }
                setTabAdapter(false);
                int k = i - 1;
                if (k >= 0)
                {
                    i = k;
                    if (k < pAdapter.getCount())
                    {
                        break label0;
                    }
                }
                i = 0;
            }
            selectAndClick(i, true);
            return;
_L4:
            pNick = message.obj.toString();
            updateTitle(true);
            return;
_L6:
            if (message.obj.toString().equals(pCurrentSession.getSessionName()) && pCurrentSession.getType() == 1)
            {
                message = pCurrentSession.getOwnStatus();
                if (message.length() > 0 && pStatus.getVisibility() != 0)
                {
                    pStatus.setVisibility(0);
                }
                pStatus.setText(message);
                return;
            }
            if (true) goto _L1; else goto _L7
_L7:
            setTabAdapter(true);
            int j = pGallery.getSelectedItemPosition();
            pCurrentSession = (Session)pAdapter.getItem(j);
            return;
_L8:
            if (pCurrentSession.getSessionName().equalsIgnoreCase(message.obj.toString()))
            {
                mTextAdapter.notifyDataSetChanged();
                return;
            }
            if (true) goto _L1; else goto _L9
_L9:
        }

        private FlagHandler()
        {
            this$0 = ChatWindow.this;
            super();
        }

        FlagHandler(FlagHandler flaghandler)
        {
            this();
        }
    }

    class FragmentBridge extends Handler
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
        //                       8: 52
        //                       2131230855: 52
        //                       2131230856: 52;
               goto _L1 _L2 _L2 _L2
_L1:
            obj = (String)message.obj;
_L2:
            i;
            JVM INSTR lookupswitch 16: default 192
        //                       8: 570
        //                       2131230725: 230
        //                       2131230726: 269
        //                       2131230727: 278
        //                       2131230728: 304
        //                       2131230729: 356
        //                       2131230730: 365
        //                       2131230731: 291
        //                       2131230732: 317
        //                       2131230733: 330
        //                       2131230734: 343
        //                       2131230735: 374
        //                       2131230855: 676
        //                       2131230856: 676
        //                       2131230857: 391
        //                       2131230858: 542;
               goto _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L16 _L17 _L18
_L3:
            Log.w("IRCChatWindow", (new StringBuilder("unknown command, m.what=")).append(message.what).append(", obj=").append(message.obj).toString());
            return;
_L5:
            int j = pAdapter.getPositionOf(((String) (obj)));
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
            sendWhois(((String) (obj)));
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
            InterceptingEditText interceptingedittext;
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
            handleCommand((new StringBuilder("/ignore ")).append(message.obj).toString(), false);
            return;
_L4:
            message = (ChannelSwitcherFragment.SwitchChannelInfo)message.obj;
            if (((ChannelSwitcherFragment.SwitchChannelInfo) (message)).manager == pSessionManager)
            {
                selectAndClick(pAdapter.getPositionOf(((ChannelSwitcherFragment.SwitchChannelInfo) (message)).sessionName), true);
                return;
            }
            if (pGallery.getVisibility() == 8 && pAdapter.getPositionOf(pCurrentSession) >= 0)
            {
                pCurrentSession.clearTextTypeInfo();
            }
            startNewChat(((ChannelSwitcherFragment.SwitchChannelInfo) (message)).id, ((ChannelSwitcherFragment.SwitchChannelInfo) (message)).sessionName);
            return;
_L16:
            message = (ChannelSwitcherFragment.RunCommand)message.obj;
            mService.runOnAnotherProfile(((ChannelSwitcherFragment.RunCommand) (message)).id, (new StringBuilder(String.valueOf(((ChannelSwitcherFragment.RunCommand) (message)).command))).append(" ").append(((ChannelSwitcherFragment.RunCommand) (message)).params).toString());
            return;
        }

        FragmentBridge()
        {
            this$0 = ChatWindow.this;
            super();
        }
    }

    public class MessageHandler extends Handler
    {

        final ChatWindow this$0;

        public void handleMessage(Message message)
        {
            message.what;
            JVM INSTR tableswitch 0 1: default 28
        //                       0 29
        //                       1 100;
               goto _L1 _L2 _L3
_L1:
            return;
_L2:
            message = (IRCMessage)blocker.poll();
_L5:
            String s;
            int i;
            if (message == null)
            {
                continue; /* Loop/switch isn't completed */
            }
            i = ((IRCMessage) (message)).val;
            Session session = pCurrentSession;
            s = ((IRCMessage) (message)).target;
            if (session.getSessionName().equalsIgnoreCase(s))
            {
                pSessionManager.addText(message, true);
                mTextAdapter.notifyDataSetChanged();
                return;
            }
            break; /* Loop/switch isn't completed */
_L3:
            message = (IRCMessage)message.obj;
            if (true) goto _L5; else goto _L4
_L4:
            boolean flag1;
            int j;
            pSessionManager.addText(message, false);
            flag1 = false;
            j = 0;
            switch (i)
            {
            default:
                break;

            case 3: // '\003'
                break; /* Loop/switch isn't completed */

            case 2: // '\002'
                break;
            }
            break MISSING_BLOCK_LABEL_196;
_L7:
            if (flag1)
            {
                setUpArrows(s, j, true);
            }
            if (pSessionManager.needsRefresh())
            {
                setTabAdapter(true);
                return;
            }
            if (true) goto _L1; else goto _L6
_L6:
            flag1 = true;
              goto _L7
            boolean flag;
            if (s.equals("Status"))
            {
                flag = false;
            } else
            {
                flag = true;
            }
            if (!flag && (flag || pArrowLeft.getVisibility() != 0 && pArrowRight.getVisibility() != 0))
            {
                flag = false;
            } else
            {
                flag = true;
            }
            flag1 = flag;
            if (flag)
            {
                if (s.equals("Status") || Utils.isChannelPrefix(s.charAt(0)))
                {
                    flag1 = true;
                } else
                {
                    flag1 = false;
                }
                j = ((flag1) ? 1 : 0);
                flag1 = flag;
            }
              goto _L7
        }

        public MessageHandler()
        {
            this$0 = ChatWindow.this;
            super();
        }
    }

    private final class SwipeDetector extends net.andchat.donate.Misc.Utils.Detector
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
                i = pAdapter.getCount() - 1;
            }
            selectAndClick(i, true);
        }

        public void onRightToLeftSwipe()
        {
            int i = pGallery.getSelectedItemPosition();
            if (i == pAdapter.getCount() - 1)
            {
                i = 0;
            } else
            {
                i++;
            }
            selectAndClick(i, true);
        }

        public SwipeDetector(Context context)
        {
            this$0 = ChatWindow.this;
            super(context);
        }
    }

    private class TabAdapter extends BaseAdapter
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
                view = newTextView(false);
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
        //                       0 188
        //                       1 223
        //                       2 247
        //                       3 271;
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

        public TabAdapter(Context context, List list)
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

    private class TextAdapter extends BaseAdapter
    {

        private LayoutInflater mInflator;
        private final LinkMovementMethod mMethod = new LinkMovementMethod();
        private LimitedSizeQueue mText;
        final ChatWindow this$0;

        private CharSequence get(int i)
        {
            Iterator iterator = mText.iterator();
            int j = 0;
            do
            {
                if (!iterator.hasNext())
                {
                    return null;
                }
                if (j == i)
                {
                    return (CharSequence)iterator.next();
                }
                iterator.next();
                j++;
            } while (true);
        }

        public int getCount()
        {
            return mText.getSize();
        }

        public Object getItem(int i)
        {
            return get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            if (view == null)
            {
                view = (TextView)mInflator.inflate(0x7f030023, viewgroup, false);
                view.setMovementMethod(mMethod);
                view.setTextSize(mMsgSize);
            } else
            {
                view = (TextView)view;
            }
            view.setTypeface(TextStyleDialog.TYPES[mMsgFont]);
            view.setText(get(i));
            return view;
        }

        public TextAdapter(Context context, LimitedSizeQueue limitedsizequeue)
        {
            this$0 = ChatWindow.this;
            super();
            mInflator = LayoutInflater.from(context);
            mText = limitedsizequeue;
        }
    }


    private static final Integer ONE = Integer.valueOf(1);
    private static final Integer TWO = Integer.valueOf(2);
    private static boolean sMadeReq;
    private static boolean sUserReqHideCustomActionBar = false;
    public final ArrayBlockingQueue blocker = new ArrayBlockingQueue(5);
    public FlagHandler flagHandler;
    private ActionBar mActionBarL;
    private String mChangeToWindow;
    private GestureDetector mDetector;
    private boolean mDisconnected;
    private View mDivider;
    private Runnable mFastScrollHider;
    private Animation mGrowIn;
    private InputMethodManager mIm;
    private boolean mInOnResume;
    private boolean mInOnServiceConnect;
    private int mInputSize;
    private int mLastResLeft;
    private int mLastResRight;
    private MessageSender mMediator;
    private StringBuilder mMessageBuilder;
    private int mMsgFont;
    private int mMsgSize;
    private SharedPreferences mPrefs;
    ServerProfile mProfile;
    private QuickAction mQuickActions[];
    private boolean mReqContextMenu;
    private int mScrollBackLen;
    private ImageView mSearchIcon;
    private int mSelectedPos;
    private View mSendButton;
    private final ArrayList mServerRunnables = new ArrayList();
    protected IRCService mService;
    private final ArrayList mSessionRunnables = new ArrayList();
    private boolean mShouldReconnect;
    private boolean mShowTimeStamps;
    private Animation mShrinkOut;
    private Animation mSlideIn;
    private TextAdapter mTextAdapter;
    private int mVolKeysMode;
    public MessageHandler messageHandler;
    private TabAdapter pAdapter;
    private ImageView pArrowLeft;
    private ImageView pArrowRight;
    Session pCurrentSession;
    InterceptingEditText pEt;
    FragmentBridge pFragmentBridge;
    Gallery pGallery;
    int pId;
    private ListView pList;
    private String pNick;
    private ServerConnection pServ;
    SessionManager pSessionManager;
    private TextView pStatus;
    private LinearLayout pSuggest;

    public ChatWindow()
    {
        pCurrentSession = SessionManager.NULL_SESSION;
        mService = null;
        mDisconnected = false;
    }

    private void addSuggestions(LinearLayout linearlayout, List list)
    {
        int j = list.size();
        if (mSlideIn == null)
        {
            mSlideIn = AnimationUtils.loadAnimation(this, 0x7f04000e);
        }
        int i = 0;
        do
        {
            if (i >= j)
            {
                return;
            }
            TextView textview = newTextView(true);
            textview.setText((CharSequence)list.get(i));
            textview.setOnClickListener(this);
            linearlayout.addView(textview);
            if (i == 0)
            {
                linearlayout.startAnimation(mSlideIn);
            }
            i++;
        } while (true);
    }

    private boolean areSuggestionsShowing()
    {
        return pSuggest.getChildCount() > 0;
    }

    private void clearCountAndNotification()
    {
        if (pServ != null)
        {
            pServ.resetNotificationCount();
            if (pServ.getConnectionState() == 1)
            {
                ((NotificationManager)getSystemService("notification")).cancel(pId);
            }
        }
    }

    private void connectToServer()
    {
        switch (pServ.getConnectionState())
        {
        case 2: // '\002'
        default:
            return;

        case 0: // '\0'
        case 4: // '\004'
            if (pServ.isReconnecting())
            {
                pServ.forceConnect();
                return;
            } else
            {
                mService.remove(pServ);
                sendMessage(getConnectionMessage(true), "Status", 1);
                getObjects();
                pServ.prepareForReconnect();
                return;
            }

        case 1: // '\001'
        case 3: // '\003'
            (new Thread() {

                final ChatWindow this$0;

                public void run()
                {
                    mService.stopConnection(pId, 1);
                }

            
            {
                this$0 = ChatWindow.this;
                super();
            }
            }).start();
            return;
        }
    }

    private void doCommon()
    {
        CharSequence charsequence = pCurrentSession.getSavedInputText();
        if (charsequence == null) goto _L2; else goto _L1
_L1:
        int i = charsequence.length();
        if (i <= 0) goto _L2; else goto _L3
_L3:
        pEt.setText(charsequence);
        pEt.setSelection(i);
_L5:
        pEt.requestFocus();
        setTextAdapter(pCurrentSession);
        return;
_L2:
        TextKeyListener.clear(pEt.getText());
        pEt.setText(null);
        if (true) goto _L5; else goto _L4
_L4:
    }

    private StringBuilder getConnectionMessage(boolean flag)
    {
        return Utils.getConnectionMessage(this, flag, mProfile);
    }

    private void getObjects()
    {
        ArrayList arraylist;
        int i;
        int j;
        mMediator = mService.getSenderForId(pId);
        pSessionManager = mService.getSessionManagerForId(pId);
        pCurrentSession = pSessionManager.getDefaultSession();
        arraylist = mSessionRunnables;
        j = arraylist.size();
        i = 0;
_L3:
        if (i < j) goto _L2; else goto _L1
_L1:
        arraylist.clear();
        pServ = mService.getServer(pId, true, mProfile, this);
        refreshMenu();
        arraylist = mServerRunnables;
        j = arraylist.size();
        i = 0;
_L4:
        if (i >= j)
        {
            arraylist.clear();
            mMediator.setUi(this);
            pNick = pServ.getCurrentNick();
            if (pServ.getConnectionState() == 1)
            {
                updateTitle(true);
            }
            pSessionManager.setInputLimit(mScrollBackLen);
            if (mActionBarL != null)
            {
                updateLegacyActionBar();
            }
            performInitialization();
            return;
        }
        break MISSING_BLOCK_LABEL_190;
_L2:
        ((Runnable)arraylist.get(i)).run();
        i++;
          goto _L3
        ((Runnable)arraylist.get(i)).run();
        i++;
          goto _L4
    }

    private void getScrollbackTextDown()
    {
        InterceptingEditText interceptingedittext = pEt;
        interceptingedittext.setText(pCurrentSession.getHistoricDownText(interceptingedittext.getText()));
        interceptingedittext.setSelection(interceptingedittext.length());
        interceptingedittext.requestFocus();
    }

    private void getScrollbackTextUp()
    {
        InterceptingEditText interceptingedittext = pEt;
        interceptingedittext.setText(pCurrentSession.getHistoricUpText(interceptingedittext.getText()));
        interceptingedittext.setSelection(interceptingedittext.length());
        interceptingedittext.requestFocus();
    }

    private String getSuggestionString(String s)
    {
        StringBuilder stringbuilder;
        int i;
        int j = pEt.getSelectionEnd();
        stringbuilder = new StringBuilder(10);
        i = s.length() - 1;
        if (j <= i && j >= 0)
        {
            i = j - 1;
        }
_L5:
        if (i >= 0) goto _L2; else goto _L1
_L1:
        char c;
        return stringbuilder.toString();
_L2:
        if ((c = s.charAt(i)) == ' ') goto _L1; else goto _L3
_L3:
        stringbuilder.insert(0, c);
        i--;
        if (true) goto _L5; else goto _L4
_L4:
    }

    private void handleCommand(String s, boolean flag)
    {
        if (flag)
        {
            pCurrentSession.addHistoricText(s);
        }
        TextKeyListener.clear(pEt.getText());
        CommandParser.handleCommand(s, this);
    }

    private void handleSendMessageRequest()
    {
        InterceptingEditText interceptingedittext = pEt;
        Object obj = interceptingedittext.getText();
        int j = ((CharSequence) (obj)).length();
        if (j == 0 || j == 1 && ((CharSequence) (obj)).charAt(0) == '\n')
        {
            mIm.showSoftInput(interceptingedittext, 2);
            interceptingedittext.requestFocus();
            return;
        }
        Session session = pCurrentSession;
        String s1 = session.getSessionName();
        char c = ((CharSequence) (obj)).charAt(0);
        int i = pServ.getConnectionState();
        if (c == '/' && j > 1)
        {
            handleCommand(((CharSequence) (obj)).toString(), true);
            TextKeyListener.clear(interceptingedittext.getText());
            interceptingedittext.requestFocus();
            return;
        }
        if (i == 4 || i == 0)
        {
            sendMessage(getString(0x7f0a01b3), s1, 1);
            pCurrentSession.addHistoricText(new SpannableStringBuilder(((CharSequence) (obj))));
            TextKeyListener.clear(interceptingedittext.getText());
            interceptingedittext.requestFocus();
            return;
        }
        j = session.getType();
        if (j != 0)
        {
            if (i == 1)
            {
                if (!pSessionManager.haveSessionFor(s1) && j == 1)
                {
                    sendMessage(getString(0x7f0a01b4), s1, 2);
                    interceptingedittext.requestFocus();
                } else
                {
                    String s = ((CharSequence) (obj)).toString();
                    if (s.indexOf('\n') != -1)
                    {
                        obj = s.split("\n");
                        j = obj.length;
                        i = 0;
                        while (i < j) 
                        {
                            sendAMessage(obj[i]);
                            i++;
                        }
                    } else
                    {
                        sendAMessage(((CharSequence) (obj)));
                    }
                }
                TextKeyListener.clear(interceptingedittext.getText());
                interceptingedittext.requestFocus();
                return;
            }
            sendMessage(getString(0x7f0a01b3), "Status", 2);
            interceptingedittext.requestFocus();
        } else
        if (i == 1 && j == 0)
        {
            sendMessage(getString(0x7f0a01b5), "Status", 2);
            pCurrentSession.addHistoricText(new SpannableStringBuilder(((CharSequence) (obj))));
            TextKeyListener.clear(interceptingedittext.getText());
            interceptingedittext.requestFocus();
            return;
        }
        writeToServer((new StringBuilder()).append(obj).append("\r\n").toString());
        sendMessage(((CharSequence) (obj)), "Status", 2);
        TextKeyListener.clear(interceptingedittext.getText());
        interceptingedittext.requestFocus();
    }

    private void hideArrow(int i, ImageView imageview, boolean flag)
    {
        while (imageview.getVisibility() != 0 || pAdapter.findJumpTarget(i, flag, 3) >= 0) 
        {
            return;
        }
        imageview.setTag(null);
        imageview.setVisibility(8);
    }

    private void hideFullscreenIME()
    {
        InputMethodManager inputmethodmanager = mIm;
        if (inputmethodmanager.isFullscreenMode())
        {
            inputmethodmanager.hideSoftInputFromWindow(pEt.getWindowToken(), 0);
        }
    }

    private void hideSuggestions()
    {
        if (areSuggestionsShowing())
        {
            pSuggest.removeAllViews();
        }
    }

    private TextView newTextView(boolean flag)
    {
        TextView textview = new TextView(this);
        if (flag)
        {
            textview.setTextColor(-1);
            textview.setTextSize(2, 20F);
            textview.setPadding(5, 0, 5, 5);
        } else
        {
            textview.setBackgroundResource(0x7f02003f);
            textview.setTextColor(0xff000000);
            textview.setTextSize(2, 18F);
        }
        textview.setEllipsize(android.text.TextUtils.TruncateAt.MIDDLE);
        return textview;
    }

    private void openSettings()
    {
        startActivityForResult((new Intent(this, net/andchat/donate/Activities/Preferences)).putExtra("result", true), 0);
    }

    private void refreshMenu()
    {
        if (IRCApp.LEGACY_VERSION)
        {
            onPrepareOptionsMenu(null);
            return;
        } else
        {
            invalidateOptionsMenu();
            return;
        }
    }

    private void sendAMessage(CharSequence charsequence)
    {
        int i = charsequence.length();
        Session session = pCurrentSession;
        String s = session.getSessionName();
        int j = s.length();
        Object obj = mMessageBuilder;
        ((StringBuilder) (obj)).setLength(0);
        ((StringBuilder) (obj)).ensureCapacity(i + 12 + j);
        writeToServer(((StringBuilder) (obj)).append("PRIVMSG ").append(s).append(" :").append(charsequence).append("\r\n").toString());
        ((StringBuilder) (obj)).setLength(0);
        ((StringBuilder) (obj)).append("<");
        ((StringBuilder) (obj)).append(session.getOwnStatus());
        ((StringBuilder) (obj)).append(pNick).append("> ");
        j = ((StringBuilder) (obj)).length();
        ((StringBuilder) (obj)).append(charsequence);
        int k = Colours.getInstance().getColourForEvent(0x7f0b0012);
        obj = Utils.createMessage(mShowTimeStamps, ((CharSequence) (obj)));
        Utils.addColour(mShowTimeStamps, ((SpannableStringBuilder) (obj)), k, 0, 1);
        Utils.addColour(mShowTimeStamps, ((SpannableStringBuilder) (obj)), k, j - 2, j);
        if (i > 4)
        {
            Utils.addLinks(((android.text.Spannable) (obj)));
        }
        sendMessage(((CharSequence) (obj)), s, 2);
        session.addHistoricText(new SpannableStringBuilder(charsequence));
    }

    private void sendMessage(final CharSequence m, String s, int i)
    {
        String s1;
label0:
        {
            if (s != null)
            {
                s1 = s;
                if (s.length() != 0)
                {
                    break label0;
                }
            }
            s1 = "Status";
        }
        s = m;
        if (!(m instanceof SpannableStringBuilder))
        {
            s = m;
            if (mShowTimeStamps)
            {
                s = new SpannableStringBuilder();
                Utils.getStyledTimeStamp(s, ServerConnection.getTime());
                s.append(m);
            }
        }
        if (pCurrentSession.getSessionName().equals(s1))
        {
            m = IRCMessage.obtain();
            m.set(s1, s, i);
            Message.obtain(messageHandler, 1, m).sendToTarget();
            if (pServ != null)
            {
                pServ.logMessage(s1, s);
            }
        } else
        {
            m = IRCMessage.obtain();
            m.set(s1, s, i);
            if (pSessionManager == null)
            {
                mSessionRunnables.add(new Runnable() {

                    final ChatWindow this$0;
                    private final IRCMessage val$m;

                    public void run()
                    {
                        pSessionManager.addText(m, false);
                    }

            
            {
                this$0 = ChatWindow.this;
                m = ircmessage;
                super();
            }
                });
                return;
            }
            pSessionManager.addText(m, false);
            if (pSessionManager.needsRefresh())
            {
                setTabAdapter(true);
            }
            if (pServ != null)
            {
                pServ.logMessage(s1, s);
                return;
            }
        }
    }

    private void sendWhois(String s)
    {
        handleCommand((new StringBuilder("/WHOIS ")).append(s).append("\r\n").toString(), false);
        Toast.makeText(this, 0x7f0a01b6, 0).show();
    }

    private void setTabAdapter(boolean flag)
    {
        if (flag)
        {
            pAdapter.notifyDataSetChanged();
        } else
        if (pSessionManager != null)
        {
            pAdapter = new TabAdapter(this, pSessionManager.getSessionObjects());
            pGallery.setAdapter(pAdapter);
            return;
        }
    }

    private void setTextAdapter(Session session)
    {
        mTextAdapter = new TextAdapter(this, session.getSessionMessages());
        pList.setAdapter(mTextAdapter);
        session = pList;
        Runnable runnable = new Runnable() {

            final ChatWindow this$0;

            public void run()
            {
                int j = pList.getTranscriptMode();
                pList.setTranscriptMode(2);
                pList.setSelection(pList.getCount() - 1);
                pList.setTranscriptMode(j);
            }

            
            {
                this$0 = ChatWindow.this;
                super();
            }
        };
        int i;
        if (mInOnResume || mInOnServiceConnect)
        {
            i = 150;
        } else
        {
            i = 30;
        }
        session.postDelayed(runnable, i);
    }

    private void setUpArrows(int i)
    {
        if (i >= 0) goto _L2; else goto _L1
_L1:
        Session session;
        return;
_L2:
        byte byte0;
        if ((session = (Session)pAdapter.getItem(i)).getType() == 0)
        {
            continue; /* Loop/switch isn't completed */
        }
        byte0 = -1;
        if (pAdapter.getType(i) != 3 && session.getType() == 1)
        {
            break; /* Loop/switch isn't completed */
        }
        i = 0;
_L4:
        if (i >= 0)
        {
            setUpArrows(session.getSessionName(), i, false);
            return;
        }
        if (true) goto _L1; else goto _L3
_L3:
        i = byte0;
        if (session.isMarked())
        {
            i = 1;
        }
          goto _L4
        if (true) goto _L1; else goto _L5
_L5:
    }

    private void setUpArrows(String s, int i, boolean flag)
    {
        if (!s.equals("Status")) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int k;
        int l;
        int j = pGallery.getLastVisiblePosition();
        k = pGallery.getFirstVisiblePosition();
        l = pAdapter.getPositionOf(s);
        if (l <= j && (!flag || pArrowRight.getVisibility() != 0))
        {
            continue; /* Loop/switch isn't completed */
        }
        i;
        JVM INSTR tableswitch 0 1: default 84
    //                   0 85
    //                   1 141;
           goto _L3 _L4 _L5
_L3:
        return;
_L4:
        pArrowRight.setImageResource(0x7f020020);
        pArrowRight.setTag(ONE);
        mLastResRight = 0x7f020020;
_L7:
        if (Utils.isChannelPrefix(s.charAt(0)))
        {
            pSessionManager.setMarked(s, true);
        }
        pArrowRight.setVisibility(0);
        return;
_L5:
        if (pArrowRight.getVisibility() == 8)
        {
            pArrowRight.setImageResource(0x7f020016);
            mLastResRight = 0x7f020016;
            pArrowRight.setTag(TWO);
        }
        if (true) goto _L7; else goto _L6
_L6:
        if ((l == -1 || l >= k || l - 1 == k) && (!flag || pArrowLeft.getVisibility() != 0)) goto _L1; else goto _L8
_L8:
        i;
        JVM INSTR tableswitch 0 1: default 244
    //                   0 245
    //                   1 301;
           goto _L9 _L10 _L11
_L9:
        return;
_L10:
        pArrowLeft.setImageResource(0x7f02001f);
        pArrowLeft.setTag(ONE);
        mLastResLeft = 0x7f02001f;
_L13:
        if (Utils.isChannelPrefix(s.charAt(0)))
        {
            pSessionManager.setMarked(s, true);
        }
        pArrowLeft.setVisibility(0);
        return;
_L11:
        if (pArrowLeft.getVisibility() == 8)
        {
            pArrowLeft.setImageResource(0x7f020015);
            mLastResLeft = 0x7f020015;
            pArrowLeft.setTag(TWO);
        }
        if (true) goto _L13; else goto _L12
_L12:
    }

    private void showLegacyActionBar()
    {
        if (mActionBarL != null)
        {
            if (mActionBarL.getVisibility() == 8)
            {
                Animation animation1 = mGrowIn;
                Animation animation = animation1;
                if (animation1 == null)
                {
                    animation = AnimationUtils.loadAnimation(this, 0x7f040005);
                }
                mActionBarL.setAnimation(animation);
                mDivider.setAnimation(animation);
            }
            if (sUserReqHideCustomActionBar)
            {
                sUserReqHideCustomActionBar = false;
            }
            mDivider.setVisibility(0);
            mActionBarL.setVisibility(0);
        }
    }

    private void startCopy()
    {
        Intent intent = new Intent(this, net/andchat/donate/Activities/CopyTextActivity);
        intent.putExtra("window", pCurrentSession.getSessionName());
        Iterator iterator = pCurrentSession.getSessionMessages().iterator();
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder();
        do
        {
            if (!iterator.hasNext())
            {
                intent.putExtra("text", spannablestringbuilder);
                intent.putExtra("size", mMsgSize);
                startActivity(intent);
                return;
            }
            spannablestringbuilder.append((CharSequence)iterator.next());
            spannablestringbuilder.append('\n');
        } while (true);
    }

    private void startNewChat(int i, String s)
    {
        Intent intent = new Intent(this, IRCApp.CHAT_CLASS);
        intent.putExtra("id", i);
        if (s != null)
        {
            intent.putExtra("window", s);
        }
        intent.addFlags(0x14000000);
        startActivity(intent);
    }

    private void stopReconnect()
    {
        while (pServ == null || !pServ.isInReconnectMode()) 
        {
            return;
        }
        (new Thread() {

            final ChatWindow this$0;

            public void run()
            {
                mService.stopReconnecting(pId, 1);
                runOnUiThread(new Runnable() {

                    final _cls5 this$1;

                    public void run()
                    {
                        String as[] = pSessionManager.getSessionList();
                        int j = as.length;
                        int i = 0;
                        do
                        {
                            if (i >= j)
                            {
                                return;
                            }
                            String s = as[i];
                            sendMessage(getString(0x7f0a01b2), s, 1);
                            i++;
                        } while (true);
                    }

            
            {
                this$1 = _cls5.this;
                super();
            }
                });
            }


            
            {
                this$0 = ChatWindow.this;
                super();
            }
        }).start();
    }

    private void updateTitle(boolean flag)
    {
        SpannableStringBuilder spannablestringbuilder;
        boolean flag1;
        if (flag && pNick != null && pNick.length() > 0)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        spannablestringbuilder = new SpannableStringBuilder();
        spannablestringbuilder.append(mProfile.getName());
        if (flag1)
        {
            spannablestringbuilder.append(' ').append('/').append(pNick);
            spannablestringbuilder.setSpan(new TextAppearanceSpan(this, 0x1030046), mProfile.getName().length() + 1, spannablestringbuilder.length(), 33);
        }
        if (mActionBarL != null)
        {
            mActionBarL.setTitle(spannablestringbuilder);
            return;
        } else
        {
            super.setTitle(spannablestringbuilder);
            return;
        }
    }

    void addSession(final String tag, final boolean jumpToIt)
    {
        if (pSessionManager == null)
        {
            tag = new Runnable() {

                final ChatWindow this$0;
                private final boolean val$jumpToIt;
                private final String val$tag;

                public void run()
                {
                    if (pSessionManager != null)
                    {
                        addSession(tag, jumpToIt);
                    }
                }

            
            {
                this$0 = ChatWindow.this;
                tag = s;
                jumpToIt = flag;
                super();
            }
            };
            mSessionRunnables.add(tag);
            return;
        }
        Session session;
        int i;
        int k;
        if (Utils.isChannelPrefix(tag.charAt(0)))
        {
            i = 1;
        } else
        {
            i = 2;
        }
        pSessionManager.addSession(tag, pNick, i);
        i = pGallery.getSelectedItemPosition();
        session = (Session)pAdapter.getItem(i);
        setTabAdapter(false);
        k = pAdapter.getPositionOf(tag);
        if (jumpToIt && k != -1)
        {
            pAdapter.clear(i);
            selectAndClick(k, true);
            pAdapter.clear(k);
            return;
        } else
        {
            int j = pAdapter.getPositionOf(session);
            selectAndClick(j, false);
            pAdapter.clear(j);
            return;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        keyevent.getKeyCode();
        JVM INSTR tableswitch 82 82: default 24
    //                   82 30;
           goto _L1 _L2
_L1:
        return super.dispatchKeyEvent(keyevent);
_L2:
        if (keyevent.isLongPress())
        {
            return false;
        }
        if (mActionBarL != null)
        {
            if (keyevent.getAction() == 1)
            {
                return super.dispatchKeyEvent(keyevent);
            }
            if (mActionBarL.getVisibility() == 0)
            {
                Animation animation = mShrinkOut;
                keyevent = animation;
                if (animation == null)
                {
                    keyevent = AnimationUtils.loadAnimation(this, 0x7f040008);
                }
                mDivider.setAnimation(keyevent);
                mActionBarL.setAnimation(keyevent);
                mDivider.setVisibility(8);
                mActionBarL.setVisibility(8);
                sUserReqHideCustomActionBar = true;
                return true;
            } else
            {
                showLegacyActionBar();
                return true;
            }
        }
        if (true) goto _L1; else goto _L3
_L3:
    }

    public String getCurrentNick()
    {
        return pServ.getCurrentNick();
    }

    public Session getCurrentSession()
    {
        return pCurrentSession;
    }

    public Ignores getIgnoreList()
    {
        if (pServ == null)
        {
            return Ignores.getIgnores(pId, Utils.getIRCDb(this));
        } else
        {
            return pServ.getIgnoreList();
        }
    }

    public String getPartReason()
    {
        return IRCService.sPreferences.partReason;
    }

    public String getQuitReason()
    {
        return IRCService.sPreferences.quitReason;
    }

    public Resources getResources()
    {
        return super.getResources();
    }

    public ServerConnection getServerConnection()
    {
        return pServ;
    }

    public SessionManager getSessionManager()
    {
        return pSessionManager;
    }

    public void handleAddMessage(IRCMessage ircmessage)
    {
        sendMessage(ircmessage.message, ircmessage.target, ircmessage.val);
    }

    public void handleBan(String s)
    {
        Utils.handleBanHammer(s, this);
    }

    public void handleJoin(String s)
    {
        int i = pAdapter.getPositionOf(s);
        if (i != -1)
        {
            selectAndClick(i, true);
        }
        writeToServer((new StringBuilder("JOIN ")).append(s).append("\r\n").toString());
    }

    public void handleKick(String s)
    {
        Utils.handleKicking(s, this);
    }

    public void handleOpAction(String s, char c, char c1)
    {
        writeToServer(Utils.buildOpAction(s, c, c1, pCurrentSession.getSessionName()));
    }

    public void handleQuit(String s)
    {
        Utils.handleQuit(s, mService, pId);
    }

    public void handleStartPm(String s)
    {
        addSession(s, true);
        sendMessage(Utils.getPmStart(this, s, mShowTimeStamps), s, 2);
    }

    public boolean isShowingTimestamps()
    {
        return mShowTimeStamps;
    }

    protected void loadPrefs()
    {
        SharedPreferences sharedpreferences = mPrefs;
        mShowTimeStamps = sharedpreferences.getBoolean(getString(0x7f0a001c), false);
        mMsgSize = Utils.parseInt(sharedpreferences.getString(getString(0x7f0a0013), "13"), 13);
        mMsgFont = sharedpreferences.getInt(getString(0x7f0a002f), 0);
        mInputSize = Utils.parseInt(sharedpreferences.getString(getString(0x7f0a0014), Integer.toString(-1)), -1);
        int i = sharedpreferences.getInt(getString(0x7f0a0030), 0);
        pEt.setTypeface(TextStyleDialog.TYPES[i]);
        if (mInputSize > 0)
        {
            pEt.setTextSize(2, mInputSize);
        }
        if (mTextAdapter != null)
        {
            mTextAdapter.notifyDataSetChanged();
        }
        Object obj = getString(0x7f0a0019);
        mScrollBackLen = Utils.parseInt(sharedpreferences.getString(((String) (obj)), "20"), 20, sharedpreferences, ((String) (obj)), "IRCChatWindow");
        if (mScrollBackLen > 50)
        {
            mScrollBackLen = 50;
            sharedpreferences.edit().putString(((String) (obj)), "50").commit();
        }
        boolean flag = sharedpreferences.getBoolean(getString(0x7f0a0023), true);
        i = pEt.getInputType();
        if (flag)
        {
            i |= 0xc000;
        } else
        {
            i &= 0xffff3fff;
        }
        pEt.setInputType(i);
        mVolKeysMode = Integer.parseInt(sharedpreferences.getString(getString(0x7f0a002c), "-1"));
        flag = sharedpreferences.getBoolean(getString(0x7f0a0034), true);
        obj = mSendButton;
        if (flag)
        {
            i = 0;
        } else
        {
            i = 8;
        }
        ((View) (obj)).setVisibility(i);
        flag = sharedpreferences.getBoolean(getString(0x7f0a0035), false);
        pEt.setKeepScreenOn(flag);
    }

    public void onActionBarItemClicked(com.markupartist.android.widget.ActionBar.GenericAction genericaction, View view)
    {
        genericaction.getDrawable();
        JVM INSTR lookupswitch 6: default 64
    //                   2130837529: 115
    //                   2130837544: 149
    //                   2130837555: 105
    //                   2130837556: 126
    //                   2130837561: 131
    //                   2130837565: 110;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        Log.w("IRCChatWindow", (new StringBuilder("Unhandled action bar item: drawable = ")).append(genericaction.getDrawable()).append("description = ").append(genericaction.getDescription()).toString());
_L9:
        return;
_L4:
        stopReconnect();
        return;
_L7:
        connectToServer();
        return;
_L2:
        mQuickActions[0].show(view);
        return;
_L5:
        onUserlistMenuClicked();
        return;
_L6:
        if (mQuickActions == null) goto _L9; else goto _L8
_L8:
        mQuickActions[1].show(view);
        return;
_L3:
        startCopy();
        return;
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        i;
        JVM INSTR lookupswitch 3: default 44
    //                   0: 45
    //                   1: 561
    //                   10: 67;
           goto _L1 _L2 _L3 _L4
_L6:
        return;
_L2:
        ((IRCApp)getApplication()).notifyWatchers();
        Toast.makeText(this, 0x7f0a01cf, 0).show();
        return;
_L4:
        String s;
        if (j != -1 || intent == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        String s1 = intent.getCharSequenceExtra("selected").toString();
        i = s1.lastIndexOf(',');
        s = s1;
        if (i != -1)
        {
            s = s1.substring(0, i);
        }
        i = intent.getIntExtra("action", -1);
        if (i == -1)
        {
            break; /* Loop/switch isn't completed */
        }
        intent = intent.getStringExtra("names");
        switch (i)
        {
        default:
            return;

        case 2131230725: 
            if (intent != null)
            {
                handleStartPm(intent);
                return;
            }
            break;

        case 2131230726: 
            if (intent != null)
            {
                sendWhois(intent);
                return;
            }
            break;

        case 2131230858: 
            if (intent != null)
            {
                handleCommand((new StringBuilder("/ignore ")).append(intent).toString(), false);
                return;
            }
            break;

        case 2131230731: 
            handleOpAction(s, '-', 'o');
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230732: 
            handleOpAction(s, '-', 'v');
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230733: 
            handleOpAction(s, '+', 'h');
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230734: 
            handleOpAction(s, '-', 'h');
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230735: 
            handleBan(s);
            handleKick(s);
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230727: 
            handleOpAction(s, '+', 'o');
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230728: 
            handleOpAction(s, '+', 'v');
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230729: 
            handleKick(s);
            TextKeyListener.clear(pEt.getText());
            return;

        case 2131230730: 
            handleBan(s);
            TextKeyListener.clear(pEt.getText());
            return;
        }
_L1:
        if (true) goto _L6; else goto _L5
_L5:
        intent = intent.getCharSequenceExtra("oldtext");
        if (s != null && !s.equals(""))
        {
            if (intent != null && !intent.equals(""))
            {
                s = (new StringBuilder()).append(intent).append(s).toString();
            }
            pEt.setText(s);
            pEt.setSelection(s.length());
            return;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        sMadeReq = false;
        if (j != -1)
        {
            if (j == 3)
            {
                Toast.makeText(this, 0x7f0a0120, 0).show();
            }
            finish();
            return;
        }
        if (!mProfile.isDecrypted())
        {
            intent = Utils.getCrypt(this);
            mProfile.decryptSelf(intent);
            return;
        }
        if (true) goto _L6; else goto _L7
_L7:
    }

    public void onAllStopped()
    {
    }

    public void onClick(View view)
    {
        int i = view.getId();
        i;
        JVM INSTR lookupswitch 5: default 60
    //                   -1: 61
    //                   2131230762: 250
    //                   2131230765: 532
    //                   2131230766: 538
    //                   2131230767: 250;
           goto _L1 _L2 _L3 _L4 _L5 _L3
_L1:
        break; /* Loop/switch isn't completed */
_L5:
        break MISSING_BLOCK_LABEL_538;
_L6:
        return;
_L2:
        if (view instanceof TextView)
        {
            String s2 = ((TextView)view).getText().toString();
            hideSuggestions();
            InterceptingEditText interceptingedittext = pEt;
            String s1 = interceptingedittext.getText().toString();
            String s;
            Object obj;
            if (s1.length() > 1)
            {
                s = getSuggestionString(s1);
            } else
            {
                s = s1;
            }
            obj = null;
            view = obj;
            int j;
            boolean flag;
            if (interceptingedittext.getSelectionEnd() != s1.length())
            {
                try
                {
                    view = s1.substring(interceptingedittext.getSelectionEnd());
                }
                // Misplaced declaration of an exception variable
                catch (View view)
                {
                    view = obj;
                }
            }
            s = s1.substring(0, interceptingedittext.getSelectionEnd() - s.length());
            if (s.length() == 0)
            {
                s = (new StringBuilder(String.valueOf(s2))).append(": ").toString();
            } else
            {
                s = (new StringBuilder(String.valueOf(s))).append(s2).append(" ").toString();
            }
            interceptingedittext.setText(s);
            if (view != null)
            {
                interceptingedittext.append(view);
            }
            interceptingedittext.setSelection(s.length());
            return;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        if (pAdapter != null)
        {
            if (i == 0x7f08002a)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            if (flag)
            {
                view = pArrowLeft;
            } else
            {
                view = pArrowRight;
            }
            j = pAdapter.getPositionOf(pCurrentSession);
            if (j == -1)
            {
                view.setTag(null);
                view.setVisibility(8);
                return;
            }
            if (view.getTag() != null)
            {
                i = ((Integer)view.getTag()).intValue();
            } else
            {
                i = 3;
            }
            view.setTag(null);
            i = pAdapter.findJumpTarget(j, flag, i);
            if (i == -1)
            {
                view.setVisibility(8);
                return;
            }
            hideSuggestions();
            selectAndClick(i, true);
            j = pAdapter.findJumpTarget(i, flag, 3);
            if (j == -1 || i == j)
            {
                view.setVisibility(8);
                view.setTag(null);
                return;
            }
            s = ((Session)pAdapter.getItem(j)).getSessionName();
            if (pAdapter.getType(j) == 3 || !Utils.isChannelPrefix(s.charAt(0)))
            {
                if (flag)
                {
                    i = 0x7f02001f;
                } else
                {
                    i = 0x7f020020;
                }
                view.setImageResource(i);
                return;
            }
            if (pSessionManager.isMarked(s))
            {
                if (flag)
                {
                    i = 0x7f020015;
                } else
                {
                    i = 0x7f020016;
                }
                view.setImageResource(i);
                return;
            }
        }
        if (true) goto _L6; else goto _L4
_L4:
        onSearchRequested();
        return;
        hideSuggestions();
        handleSendMessageRequest();
        return;
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        int i;
        if (!mReqContextMenu)
        {
            return super.onContextItemSelected(menuitem);
        }
        i = pGallery.getSelectedItemPosition();
        if (i != mSelectedPos)
        {
            selectAndClick(mSelectedPos, true);
        }
        menuitem.getItemId();
        JVM INSTR tableswitch 2131230855 2131230856: default 68
    //                   2131230855 74
    //                   2131230856 99;
           goto _L1 _L2 _L3
_L1:
        return super.onContextItemSelected(menuitem);
_L2:
        handleCommand("/clear", false);
        if (i != mSelectedPos)
        {
            selectAndClick(i, true);
        }
        continue; /* Loop/switch isn't completed */
_L3:
        handleCommand("/wc", false);
        if (true) goto _L1; else goto _L4
_L4:
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mPrefs = Utils.getPrefs(this);
        setContentView(0x7f030007);
        pGallery = (Gallery)findViewById(0x7f080021);
        bundle = pGallery;
        bundle.setHorizontalFadingEdgeEnabled(true);
        bundle.setAnimationDuration(0);
        bundle.setOnItemClickListener(this);
        bundle.setCallbackDuringFling(false);
        bundle.setOnItemSelectedListener(this);
        registerForContextMenu(bundle);
        bundle = (InterceptingLinearLayout)findViewById(0x7f080023);
        pList = (ListView)bundle.findViewById(0x7f080026);
        pSuggest = (LinearLayout)findViewById(0x7f080001);
        Object obj = findViewById(0x7f080029);
        pStatus = (TextView)((View) (obj)).findViewById(0x7f08002b);
        pEt = (InterceptingEditText)((View) (obj)).findViewById(0x7f08002c);
        pEt.setOnKeyListener(this);
        registerForContextMenu(pEt);
        mSendButton = ((View) (obj)).findViewById(0x7f08002e);
        mSendButton.setOnClickListener(this);
        loadPrefs();
        pArrowLeft = (ImageView)((View) (obj)).findViewById(0x7f08002a);
        pArrowRight = (ImageView)((View) (obj)).findViewById(0x7f08002f);
        pArrowLeft.setOnClickListener(this);
        pArrowRight.setOnClickListener(this);
        Intent intent = getIntent();
        pId = intent.getIntExtra("id", -1);
        mShouldReconnect = getIntent().getBooleanExtra("reconnect", false);
        mChangeToWindow = intent.getStringExtra("window");
        mProfile = Utils.getIRCDb(this).getDetailsForId(pId);
        if (mProfile == null)
        {
            Toast.makeText(this, 0x7f0a01d8, 1).show();
            finish();
            return;
        }
        if (!IRCApp.LEGACY_VERSION)
        {
            pFragmentBridge = new FragmentBridge();
        }
        if (mProfile.usesEncryption())
        {
            mProfile.setDecrypted(false);
            Object obj1 = Utils.getCrypt(this);
            if (!sMadeReq && !((Crypt) (obj1)).correctPass())
            {
                sMadeReq = true;
                obj1 = new Intent(this, net/andchat/donate/Activities/PasswordActivity);
                ((Intent) (obj1)).putExtra("action", 1);
                ((Intent) (obj1)).putExtra("message", getString(0x7f0a011d, new Object[] {
                    getString(0x7f0a0123)
                }));
                startActivityForResult(((Intent) (obj1)), 1);
            } else
            if (!sMadeReq)
            {
                mProfile.decryptSelf(((Crypt) (obj1)));
            }
        }
        pNick = mProfile.getNick(1);
        messageHandler = new MessageHandler();
        flagHandler = new FlagHandler(null);
        mDetector = new GestureDetector(this, new SwipeDetector(this));
        mDetector.setIsLongpressEnabled(false);
        bundle.setViewAndDetector(pList, mDetector);
        setResult(-1);
        mMessageBuilder = new StringBuilder();
        mIm = (InputMethodManager)getSystemService("input_method");
        mSearchIcon = (ImageView)((View) (obj)).findViewById(0x7f08002d);
        mSearchIcon.setOnClickListener(this);
        ((IRCApp)getApplication()).addWatcher(this);
        if (IRCApp.LEGACY_VERSION)
        {
            bundle = (ActionBar)((ViewStub)findViewById(0x7f080013)).inflate();
            mQuickActions = new QuickAction[2];
            obj = new QuickAction(this);
            (new net.andchat.donate.Misc.AbstractMenuInflator.QuickActionsMenu(((QuickAction) (obj)))).addActionsFromXML(this, 0x7f0f0001);
            ((QuickAction) (obj)).setOnActionItemClickListener(this);
            mQuickActions[0] = ((QuickAction) (obj));
            obj = new QuickAction(this);
            (new net.andchat.donate.Misc.AbstractMenuInflator.QuickActionsMenu(((QuickAction) (obj)))).addActionsFromXML(this, 0x7f0f0004);
            mQuickActions[1] = ((QuickAction) (obj));
            ((QuickAction) (obj)).setOnActionItemClickListener(this);
            (new net.andchat.donate.Misc.AbstractMenuInflator.ActionBarMenu(bundle, this)).addActionsFromXML(this, 0x7f0f0004);
            bundle.setOnActionClickListener(this);
            mActionBarL = bundle;
            mDivider = findViewById(0x7f080010);
            bundle = getString(0x7f0a0042);
            if (!mPrefs.getBoolean(bundle, false))
            {
                Toast.makeText(this, 0x7f0a01b1, 1).show();
                mPrefs.edit().putBoolean(bundle, true).commit();
            }
            if (sUserReqHideCustomActionBar)
            {
                obj = mShrinkOut;
                bundle = ((Bundle) (obj));
                if (obj == null)
                {
                    bundle = AnimationUtils.loadAnimation(this, 0x7f040008);
                }
                mDivider.setAnimation(bundle);
                mActionBarL.setAnimation(bundle);
                mDivider.setVisibility(8);
                mActionBarL.setVisibility(8);
            } else
            {
                mDivider.setVisibility(0);
            }
        }
        updateTitle(false);
        if (!IRCApp.LEGACY_VERSION)
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (!IRCApp.LEGACY_VERSION)
        {
            mFastScrollHider = new Runnable() {

                final ChatWindow this$0;

                public void run()
                {
                    pList.setFastScrollEnabled(false);
                }

            
            {
                this$0 = ChatWindow.this;
                super();
            }
            };
        }
        pList.setOnScrollListener(this);
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        if (view instanceof Gallery)
        {
            getMenuInflater().inflate(0x7f0f0007, contextmenu);
            int i = ((android.widget.AdapterView.AdapterContextMenuInfo)contextmenuinfo).position;
            contextmenu.setHeaderTitle(((Session)pAdapter.getItem(i)).getSessionName());
            MenuItem menuitem = contextmenu.findItem(0x7f080088);
            boolean flag;
            if (i != 0)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            menuitem.setEnabled(flag);
            mSelectedPos = i;
            mReqContextMenu = true;
        } else
        {
            mReqContextMenu = false;
        }
        super.onCreateContextMenu(contextmenu, view, contextmenuinfo);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    protected void onDestroy()
    {
        if (mService != null)
        {
            mService.removeStateListener(this);
            unbindService(this);
            mService = null;
        }
        ((IRCApp)getApplication()).removeWatcher(this);
        super.onDestroy();
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        adapterview = (Session)pAdapter.getItem(i);
        if (adapterview.equals(pCurrentSession))
        {
            pGallery.setSelection(pAdapter.getPositionOf(adapterview));
        } else
        {
            hideSuggestions();
            pCurrentSession.putSavedInputText(pEt.getText());
            TextView textview;
            int j;
            boolean flag;
            if (adapterview.getType() == 1)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            flag = adapterview.hasCapability(1);
            textview = pStatus;
            if (i != 0)
            {
                j = 0;
            } else
            {
                j = 8;
            }
            textview.setVisibility(j);
            mSearchIcon.setEnabled(flag);
            if (i != 0)
            {
                pStatus.setText(adapterview.getOwnStatus());
            }
            pCurrentSession.clearTextTypeInfo();
            pCurrentSession = adapterview;
            if (mActionBarL != null)
            {
                flag = adapterview.hasCapability(5);
                mActionBarL.setActionEnabled(2, flag);
            }
            i = pAdapter.getPositionOf(adapterview);
            j = adapterview.getCurrentTextType();
            flag = adapterview.isMarked();
            if (flag || j == 3)
            {
                hideArrow(i, pArrowLeft, true);
                hideArrow(i, pArrowRight, false);
            }
            if (flag)
            {
                adapterview.setAsMarked(false);
            }
            refreshMenu();
            doCommon();
            adapterview = (TextView)view;
            if (adapterview != null)
            {
                adapterview.setText(adapterview.getText().toString());
            }
            pAdapter.clear(i);
            setTabAdapter(true);
            if (!IRCApp.LEGACY_VERSION)
            {
                refreshMenu();
            }
            if (pSessionManager != null)
            {
                pSessionManager.setCurrentSession(pCurrentSession);
                return;
            }
        }
    }

    public void onItemClick(QuickAction quickaction, int i, int j)
    {
        switch (j)
        {
        case 2131230842: 
        case 2131230843: 
        case 2131230844: 
        case 2131230845: 
        case 2131230846: 
        default:
            Log.w("IRCChatWindow", (new StringBuilder("Unhandled action bar item: id = ")).append(j).toString());
            return;

        case 2131230841: 
            connectToServer();
            return;

        case 2131230849: 
            openSettings();
            return;

        case 2131230847: 
            startActivity((new Intent(this, net/andchat/donate/Activities/IgnoresActivity)).putExtra("id", pId));
            return;

        case 2131230848: 
            startCopy();
            return;
        }
    }

    public void onItemSelected(AdapterView adapterview, View view, int i, long l)
    {
        onItemClick(adapterview, view, i, l);
    }

    public boolean onKey(View view, int i, KeyEvent keyevent)
    {
        boolean flag1 = true;
        i;
        JVM INSTR lookupswitch 9: default 88
    //                   4: 94
    //                   19: 140
    //                   20: 161
    //                   23: 109
    //                   24: 188
    //                   25: 188
    //                   61: 350
    //                   66: 109
    //                   67: 182;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L6 _L7 _L5 _L8
_L1:
        boolean flag = false;
_L10:
        return flag;
_L2:
        if (areSuggestionsShowing())
        {
            hideSuggestions();
            return true;
        } else
        {
            return false;
        }
_L5:
        hideSuggestions();
        if (keyevent.getAction() != 0)
        {
            pEt.requestFocus();
            return true;
        } else
        {
            handleSendMessageRequest();
            hideFullscreenIME();
            return true;
        }
_L3:
        hideSuggestions();
        flag = flag1;
        if (keyevent.getAction() == 0)
        {
            getScrollbackTextUp();
            return true;
        }
        continue; /* Loop/switch isn't completed */
_L4:
        hideSuggestions();
        flag = flag1;
        if (keyevent.getAction() == 0)
        {
            getScrollbackTextDown();
            return true;
        }
        continue; /* Loop/switch isn't completed */
_L8:
        hideSuggestions();
        return false;
_L6:
        switch (mVolKeysMode)
        {
        case 2: // '\002'
        default:
            return false;

        case 0: // '\0'
            break;

        case 1: // '\001'
            if (((AudioManager)getSystemService("audio")).isMusicActive())
            {
                return false;
            }
            break;

        case 3: // '\003'
            flag = flag1;
            if (keyevent.getAction() == 0)
            {
                if (i == 25)
                {
                    getScrollbackTextDown();
                    return true;
                }
                flag = flag1;
                if (i == 24)
                {
                    getScrollbackTextUp();
                    return true;
                }
            }
            continue; /* Loop/switch isn't completed */
        }
        flag = flag1;
        if (keyevent.getAction() == 1)
        {
            view = mService;
            int j = pId;
            if (i == 24)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            i = view.findNextActive(j, flag);
            flag = flag1;
            if (i != pId)
            {
                startNewChat(i, null);
                return true;
            }
        }
        continue; /* Loop/switch isn't completed */
_L7:
        flag = flag1;
        if (keyevent.getAction() != 0)
        {
            pEt.requestFocus();
            onSearchRequested();
            return true;
        }
        if (true) goto _L10; else goto _L9
_L9:
    }

    public boolean onMenuItemClick(MenuItem menuitem)
    {
        if (menuitem.getItemId() == 0x7f080079)
        {
            connectToServer();
            return true;
        } else
        {
            return false;
        }
    }

    public void onNothingSelected(AdapterView adapterview)
    {
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR lookupswitch 7: default 72
    //                   16908332: 78
    //                   2131230844: 110
    //                   2131230845: 117
    //                   2131230846: 131
    //                   2131230847: 145
    //                   2131230848: 138
    //                   2131230849: 124;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        Intent intent = new Intent(this, net/andchat/donate/Activities/Main);
        intent.addFlags(0x14000000);
        startActivity(intent);
        finish();
        continue; /* Loop/switch isn't completed */
_L3:
        stopReconnect();
        continue; /* Loop/switch isn't completed */
_L4:
        connectToServer();
        continue; /* Loop/switch isn't completed */
_L8:
        openSettings();
        continue; /* Loop/switch isn't completed */
_L5:
        onUserlistMenuClicked();
        continue; /* Loop/switch isn't completed */
_L7:
        startCopy();
        continue; /* Loop/switch isn't completed */
_L6:
        startActivity((new Intent(this, net/andchat/donate/Activities/IgnoresActivity)).putExtra("id", pId));
        if (true) goto _L1; else goto _L9
_L9:
    }

    protected void onPause()
    {
        QuickAction aquickaction[];
        if (mService != null)
        {
            mService.cleanUpIfRequired(pId);
        }
        if (mMediator != null)
        {
            mMediator.setUi(null);
        }
        pCurrentSession.putSavedInputText(pEt.getText());
        aquickaction = mQuickActions;
        if (aquickaction == null) goto _L2; else goto _L1
_L1:
        int i = 0;
_L5:
        if (i < aquickaction.length) goto _L3; else goto _L2
_L2:
        pEt.setCallback(null);
        super.onPause();
        return;
_L3:
        if (aquickaction[i] != null && aquickaction[i].isShowing())
        {
            aquickaction[i].dismiss();
        }
        i++;
        if (true) goto _L5; else goto _L4
_L4:
    }

    public boolean onPreImeKey(int i, KeyEvent keyevent)
    {
        if (i == 4 && areSuggestionsShowing())
        {
            hideSuggestions();
            return true;
        } else
        {
            return false;
        }
    }

    public void onPreferencesChanged()
    {
        loadPrefs();
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        return super.onPrepareOptionsMenu(menu);
    }

    public void onProviderClicked()
    {
        connectToServer();
    }

    protected void onRestoreInstanceState(Bundle bundle)
    {
        if (bundle.getBoolean("right"))
        {
            pArrowRight.setVisibility(0);
            int i = bundle.getInt("lastRight");
            pArrowRight.setImageResource(i);
            mLastResRight = i;
        }
        if (bundle.getBoolean("left"))
        {
            pArrowLeft.setVisibility(0);
            int j = bundle.getInt("lastLeft");
            pArrowLeft.setImageResource(j);
            mLastResLeft = j;
        }
        mDisconnected = bundle.getBoolean("disconnected", false);
        super.onRestoreInstanceState(bundle);
    }

    protected void onResume()
    {
        mInOnResume = true;
        if (pSessionManager != null)
        {
            doCommon();
        } else
        if (mProfile.usesEncryption())
        {
            if (Utils.getCrypt(this).correctPass())
            {
                bindService(new Intent(this, net/andchat/donate/Backend/IRCService), this, 0);
            }
        } else
        {
            bindService(new Intent(this, net/andchat/donate/Backend/IRCService), this, 0);
        }
        if (mMediator != null)
        {
            mMediator.setUi(this);
            clearCountAndNotification();
            boolean flag1 = true;
            boolean flag = flag1;
            if (pAdapter != null)
            {
                flag = flag1;
                if (pSessionManager != null)
                {
                    if (pAdapter.getCount() == pSessionManager.getSessionCount())
                    {
                        flag = true;
                    } else
                    {
                        flag = false;
                    }
                }
            }
            setTabAdapter(flag);
        }
        pEt.setCallback(this);
        super.onResume();
        mInOnResume = false;
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        if (pArrowLeft.getVisibility() == 0)
        {
            bundle.putBoolean("left", true);
            bundle.putInt("lastLeft", mLastResLeft);
        }
        if (pArrowRight.getVisibility() == 0)
        {
            bundle.putBoolean("right", true);
            bundle.putInt("lastRight", mLastResRight);
        }
        if (pServ != null)
        {
            int i = pServ.getConnectionState();
            if (i == 0 || i == 4)
            {
                bundle.putBoolean("disconnected", true);
            }
        }
        super.onSaveInstanceState(bundle);
    }

    public void onScroll(AbsListView abslistview, int i, int j, int k)
    {
        if (j == 0 || j == k || i + j == k)
        {
            if (abslistview.getTranscriptMode() != 2)
            {
                abslistview.setTranscriptMode(2);
            }
            if (!IRCApp.LEGACY_VERSION)
            {
                flagHandler.postDelayed(mFastScrollHider, 2500L);
            }
        } else
        {
            if (abslistview.getTranscriptMode() != 0)
            {
                abslistview.setTranscriptMode(0);
            }
            if (!IRCApp.LEGACY_VERSION)
            {
                flagHandler.removeCallbacks(mFastScrollHider);
                abslistview.setFastScrollEnabled(true);
                return;
            }
        }
    }

    public void onScrollStateChanged(AbsListView abslistview, int i)
    {
    }

    public boolean onSearchRequested()
    {
        if (pCurrentSession.hasCapability(1))
        {
            if (areSuggestionsShowing())
            {
                hideSuggestions();
                return false;
            }
            InterceptingEditText interceptingedittext = pEt;
            String s = interceptingedittext.getText().toString();
            Object obj1;
            if (s.length() > 1)
            {
                obj1 = getSuggestionString(s);
            } else
            {
                obj1 = s;
            }
            if (((String) (obj1)).length() != 0)
            {
                LinearLayout linearlayout = null;
                Object obj = linearlayout;
                if (interceptingedittext.getSelectionEnd() != s.length())
                {
                    try
                    {
                        obj = s.substring(interceptingedittext.getSelectionEnd());
                    }
                    catch (StringIndexOutOfBoundsException stringindexoutofboundsexception)
                    {
                        stringindexoutofboundsexception = linearlayout;
                    }
                }
                if (s.length() == 1)
                {
                    s = null;
                } else
                {
                    s = s.substring(0, interceptingedittext.getSelectionEnd() - ((String) (obj1)).length());
                }
                linearlayout = pSuggest;
                obj1 = pCurrentSession.getNickSuggestions(((String) (obj1)));
                if (obj1 != null)
                {
                    if (((List) (obj1)).size() == 1)
                    {
                        if (s == null || s.equals(""))
                        {
                            obj1 = (new StringBuilder(String.valueOf((String)((List) (obj1)).get(0)))).append(": ").toString();
                        } else
                        {
                            obj1 = (new StringBuilder(String.valueOf(s))).append((String)((List) (obj1)).get(0)).append(" ").toString();
                        }
                        interceptingedittext.setText(((CharSequence) (obj1)));
                        if (obj != null)
                        {
                            interceptingedittext.append(((CharSequence) (obj)));
                        }
                        interceptingedittext.setSelection(((String) (obj1)).length());
                        hideSuggestions();
                        return false;
                    } else
                    {
                        Collections.sort(((List) (obj1)), String.CASE_INSENSITIVE_ORDER);
                        addSuggestions(linearlayout, ((List) (obj1)));
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void onServerStateChanged(int i)
    {
        if (i == pId)
        {
            refreshMenu();
        }
    }

    public void onServiceConnected(ComponentName componentname, IBinder ibinder)
    {
        int i;
        int j;
        mInOnServiceConnect = true;
        mService = ((net.andchat.donate.Backend.IRCService.IRCServiceBinder)ibinder).getService();
        componentname = mService;
        componentname.addStateListener(this);
        if (componentname.getServerState(pId) == 4 && !mDisconnected)
        {
            sendMessage(getConnectionMessage(false), "Status", 1);
        }
        getObjects();
        setTextAdapter(pCurrentSession);
        if (pServ.getConnectionState() == 0 && mShouldReconnect)
        {
            componentname.remove(pServ);
            sendMessage(getConnectionMessage(true), "Status", 2);
            getObjects();
            pServ.prepareForReconnect();
        }
        setTabAdapter(false);
        j = 0;
        i = 0;
        pEt.setText(pCurrentSession.getSavedInputText());
        if (mChangeToWindow == null) goto _L2; else goto _L1
_L1:
        j = pAdapter.getPositionOf(mChangeToWindow);
        if (j != -1)
        {
            selectAndClick(j, true);
            setUpArrows(pAdapter.findJumpTarget(j, true, 3));
            setUpArrows(pAdapter.findJumpTarget(j, false, 3));
            i = 1;
        }
        getIntent().removeExtra("window");
_L4:
        clearCountAndNotification();
        if (i == 0)
        {
            doCommon();
        }
        mInOnServiceConnect = false;
        return;
_L2:
        componentname = pSessionManager.getCurrentSession();
        i = j;
        if (componentname != null)
        {
            int k = pAdapter.getPositionOf(componentname);
            i = j;
            if (k != -1)
            {
                selectAndClick(k, true);
                setUpArrows(pAdapter.findJumpTarget(k, true, 3));
                setUpArrows(pAdapter.findJumpTarget(k, false, 3));
                i = 1;
            }
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void onServiceDisconnected(ComponentName componentname)
    {
    }

    protected void onStop()
    {
        pList.setAdapter(new EmptyAdapter());
        super.onStop();
    }

    abstract void onUserlistMenuClicked();

    public void onWindowFocusChanged(boolean flag)
    {
        super.onWindowFocusChanged(flag);
        if (flag)
        {
            clearCountAndNotification();
        }
    }

    abstract void performInitialization();

    void selectAndClick(int i, boolean flag)
    {
        int k = pAdapter.getCount();
        int j = i;
        if (i > k)
        {
            j = k;
        }
        if (flag)
        {
            pGallery.setSelection(j, false);
        }
        View view = pGallery.getChildAt(j);
        pGallery.performItemClick(view, j, j);
    }

    public void sendFlaggedMessage(int i, Object obj)
    {
        Message.obtain(flagHandler, i, obj).sendToTarget();
    }

    public String toString()
    {
        return super.toString();
    }

    protected void updateLegacyActionBar()
    {
        byte byte0 = 8;
        if (pServ != null) goto _L2; else goto _L1
_L1:
        mActionBarL.setActionVisibility(0, 8);
        mActionBarL.setActionEnabled(1, false);
_L8:
        boolean flag = pCurrentSession.hasCapability(5);
        mActionBarL.setActionEnabled(2, flag);
        return;
_L2:
        pServ.getConnectionState();
        JVM INSTR tableswitch 0 4: default 88
    //                   0 148
    //                   1 114
    //                   2 203
    //                   3 114
    //                   4 148;
           goto _L3 _L4 _L5 _L6 _L5 _L4
_L6:
        break MISSING_BLOCK_LABEL_203;
_L3:
        break; /* Loop/switch isn't completed */
_L5:
        break; /* Loop/switch isn't completed */
_L9:
        ActionBar actionbar = mActionBarL;
        if (pServ.isInReconnectMode())
        {
            byte0 = 0;
        }
        actionbar.setActionVisibility(0, byte0);
        if (true) goto _L8; else goto _L7
_L7:
        mActionBarL.setActionDrawable(1, 0x7f020019);
        mActionBarL.setActionDescription(1, 0x7f0a00a0);
        mActionBarL.setActionEnabled(1, true);
          goto _L9
_L4:
        mActionBarL.setActionDrawable(1, 0x7f02003d);
        mActionBarL.setActionDescription(1, 0x7f0a00a1);
        mActionBarL.setActionEnabled(1, true);
        if (mQuickActions[0].isShowing())
        {
            mQuickActions[0].dismiss();
        }
          goto _L9
        mActionBarL.setActionEnabled(1, false);
          goto _L9
    }

    protected void updateMenu(Menu menu)
    {
        ConnectView connectview = (ConnectView)menu.findItem(0x7f08007d).getActionView();
        if (pServ != null) goto _L2; else goto _L1
_L1:
        connectview.setDescription(0x7f0a00bb);
        connectview.setEnabled(false);
        menu.findItem(0x7f08007c).setVisible(false);
        connectview.setShowMenu(false);
_L8:
        boolean flag = pCurrentSession.hasCapability(5);
        menu.findItem(0x7f08007e).setEnabled(flag);
        return;
_L2:
        pServ.getConnectionState();
        JVM INSTR tableswitch 0 4: default 124
    //                   0 176
    //                   1 149
    //                   2 203
    //                   3 149
    //                   4 176;
           goto _L3 _L4 _L5 _L6 _L5 _L4
_L6:
        break MISSING_BLOCK_LABEL_203;
_L3:
        break; /* Loop/switch isn't completed */
_L5:
        break; /* Loop/switch isn't completed */
_L9:
        menu.findItem(0x7f08007c).setVisible(pServ.isInReconnectMode());
        if (true) goto _L8; else goto _L7
_L7:
        connectview.setDescription(0x7f0a00a0);
        connectview.setEnabled(true);
        connectview.setImageResource(0x7f02002b);
        connectview.setShowMenu(true);
          goto _L9
_L4:
        connectview.setDescription(0x7f0a00a1);
        connectview.setEnabled(true);
        connectview.setImageResource(0x7f020030);
        connectview.setShowMenu(false);
          goto _L9
        connectview.setShowMenu(false);
        connectview.setEnabled(false);
        connectview.setDescription(0x7f0a00bc);
          goto _L9
    }

    public void writeToServer(final String message)
    {
        ServerConnection serverconnection = pServ;
        if (serverconnection == null)
        {
            mServerRunnables.add(new Runnable() {

                final ChatWindow this$0;
                private final String val$message;

                public void run()
                {
                    pServ.writeToServer(message);
                }

            
            {
                this$0 = ChatWindow.this;
                message = s;
                super();
            }
            });
            return;
        } else
        {
            serverconnection.writeToServer(message);
            return;
        }
    }




















}
