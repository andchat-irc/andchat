// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.andchat.donate.Activities.initialSetup.Step1;
import net.andchat.donate.Backend.Crypt;
import net.andchat.donate.Backend.IRCDb;
import net.andchat.donate.Backend.IRCService;
import net.andchat.donate.IRCApp;
import net.andchat.donate.Misc.Utils;
import net.andchat.donate.View.DelegatingRelativeLayout;
import net.londatiga.android.QuickAction;

// Referenced classes of package net.andchat.donate.Activities:
//            ServerDetailsActivity, Preferences, PasswordActivity

public class Main extends ListActivity
    implements android.content.DialogInterface.OnClickListener, ServiceConnection, android.view.View.OnClickListener, android.widget.AdapterView.OnItemClickListener, android.widget.AdapterView.OnItemLongClickListener, com.markupartist.android.widget.ActionBar.ActionBarItemClickHandler, net.andchat.donate.Backend.IRCService.ServerStateListener, net.londatiga.android.QuickAction.OnQuickActionItemClickListener
{
    private class RowAdapter extends ArrayAdapter
        implements Filterable
    {

        private android.view.View.OnClickListener mClickListener;
        private final Filter mFilter = new ItemFilter(null);
        private final boolean mHaveServers;
        private final LayoutInflater mInflator;
        private final net.andchat.donate.View.DelegatingRelativeLayout.EnlargedViewSpec mSpec = new net.andchat.donate.View.DelegatingRelativeLayout.EnlargedViewSpec(-1, -1, 12, -2);
        List pItems;
        final Main this$0;

        private int getState(String s)
        {
            checkDb();
            int i = pDb.getId(s);
            if (pService != null)
            {
                return pService.getServerState(i);
            } else
            {
                return 4;
            }
        }

        public int getCount()
        {
            return pItems.size();
        }

        public Filter getFilter()
        {
            return mFilter;
        }

        public volatile Object getItem(int i)
        {
            return getItem(i);
        }

        public String getItem(int i)
        {
            return (String)pItems.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            String s;
            if (view == null)
            {
                view = mInflator.inflate(0x7f03001e, viewgroup, false);
                viewgroup = new ViewHolder(null);
                viewgroup.more = (ImageView)view.findViewById(0x7f080064);
                ((ViewHolder) (viewgroup)).more.setOnClickListener(mClickListener);
                viewgroup.textview = (TextView)view.findViewById(0x7f080004);
                viewgroup.imageview = (ImageView)view.findViewById(0x7f080065);
                viewgroup.progressbar = (ProgressBar)view.findViewById(0x7f080066);
                ((DelegatingRelativeLayout)view).setEnlargedView(((ViewHolder) (viewgroup)).more, mSpec);
                view.setTag(viewgroup);
            } else
            {
                viewgroup = (ViewHolder)view.getTag();
            }
            s = (String)pItems.get(i);
            ((ViewHolder) (viewgroup)).textview.setText(s);
            if (!mHaveServers)
            {
                ((ViewHolder) (viewgroup)).imageview.setVisibility(8);
                ((ViewHolder) (viewgroup)).more.setVisibility(8);
                return view;
            }
            switch (getState(s))
            {
            default:
                if (((ViewHolder) (viewgroup)).progressbar.getVisibility() != 8)
                {
                    ((ViewHolder) (viewgroup)).progressbar.setVisibility(8);
                }
                if (((ViewHolder) (viewgroup)).imageview.getVisibility() != 0)
                {
                    ((ViewHolder) (viewgroup)).imageview.setVisibility(0);
                }
                ((ViewHolder) (viewgroup)).imageview.setImageResource(0x7f020021);
                ((ViewHolder) (viewgroup)).more.setVisibility(0);
                return view;

            case 2: // '\002'
            case 3: // '\003'
                if (((ViewHolder) (viewgroup)).imageview.getVisibility() != 8)
                {
                    ((ViewHolder) (viewgroup)).imageview.setVisibility(8);
                }
                if (((ViewHolder) (viewgroup)).progressbar.getVisibility() != 0)
                {
                    ((ViewHolder) (viewgroup)).progressbar.setVisibility(0);
                }
                ((ViewHolder) (viewgroup)).more.setVisibility(0);
                return view;

            case 1: // '\001'
                if (((ViewHolder) (viewgroup)).progressbar.getVisibility() != 8)
                {
                    ((ViewHolder) (viewgroup)).progressbar.setVisibility(8);
                }
                if (((ViewHolder) (viewgroup)).imageview.getVisibility() != 0)
                {
                    ((ViewHolder) (viewgroup)).imageview.setVisibility(0);
                }
                ((ViewHolder) (viewgroup)).imageview.setImageResource(0x7f02001e);
                ((ViewHolder) (viewgroup)).more.setVisibility(0);
                return view;

            case 0: // '\0'
                break;
            }
            if (((ViewHolder) (viewgroup)).progressbar.getVisibility() != 8)
            {
                ((ViewHolder) (viewgroup)).progressbar.setVisibility(8);
            }
            if (((ViewHolder) (viewgroup)).imageview.getVisibility() != 0)
            {
                ((ViewHolder) (viewgroup)).imageview.setVisibility(0);
            }
            ((ViewHolder) (viewgroup)).imageview.setImageResource(0x7f02003e);
            ((ViewHolder) (viewgroup)).more.setVisibility(0);
            return view;
        }

        public void refresh()
        {
            notifyDataSetChanged();
        }


        public RowAdapter(Context context, int i, int j, List list, boolean flag)
        {
            this$0 = Main.this;
            super(context, j, i, list);
            pItems = list;
            mInflator = LayoutInflater.from(context);
            mHaveServers = flag;
            mClickListener = new _cls1();
        }
    }

    private class RowAdapter.ItemFilter extends Filter
    {

        private ArrayList originals;
        final RowAdapter this$1;

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            String s;
            android.widget.Filter.FilterResults filterresults;
            s = charsequence.toString().toUpperCase();
            filterresults = new android.widget.Filter.FilterResults();
            if (originals == null)
            {
                synchronized (pItems)
                {
                    originals = new ArrayList(pItems);
                }
            }
            obj = originals;
            if (charsequence == null || charsequence.length() == 0)
            {
                filterresults.count = originals.size();
                filterresults.values = originals;
                return filterresults;
            }
            break MISSING_BLOCK_LABEL_104;
            charsequence;
            obj;
            JVM INSTR monitorexit ;
            throw charsequence;
            charsequence = new ArrayList();
            Iterator iterator = ((ArrayList) (obj)).iterator();
            do
            {
                String s1;
                do
                {
                    if (!iterator.hasNext())
                    {
                        filterresults.values = charsequence;
                        filterresults.count = charsequence.size();
                        return filterresults;
                    }
                    s1 = (String)iterator.next();
                } while (!s1.toUpperCase().contains(s));
                charsequence.add(s1);
            } while (true);
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            pItems = (ArrayList)filterresults.values;
            if (filterresults.count > 0)
            {
                notifyDataSetChanged();
                return;
            } else
            {
                notifyDataSetInvalidated();
                return;
            }
        }

        private RowAdapter.ItemFilter()
        {
            this$1 = RowAdapter.this;
            super();
            originals = null;
        }

        RowAdapter.ItemFilter(RowAdapter.ItemFilter itemfilter)
        {
            this();
        }
    }

    private static class ViewHolder
    {

        ImageView imageview;
        ImageView more;
        ProgressBar progressbar;
        TextView textview;

        private ViewHolder()
        {
        }

        ViewHolder(ViewHolder viewholder)
        {
            this();
        }
    }


    private static boolean madeReq;
    public static int sFilter = 0;
    private ActionBar mActions;
    private Animation mBounce;
    private Dialog mCurDialog;
    private int mCurPos;
    private boolean mFocused;
    protected ListView mListView;
    private QuickAction mQuick;
    RowAdapter pAdapter;
    IRCDb pDb;
    IRCService pService;

    public Main()
    {
        pDb = null;
    }

    private Intent createAddIntent()
    {
        return (new Intent(this, net/andchat/donate/Activities/ServerDetailsActivity)).putExtra("new", true);
    }

    private Intent createPrefsIntent()
    {
        return (new Intent(this, net/andchat/donate/Activities/Preferences)).putExtra("result", true);
    }

    private void exit()
    {
        ((IRCApp)getApplication()).clearCrypt();
        if (pService != null)
        {
            pService.stopAll();
            madeReq = false;
            return;
        } else
        {
            madeReq = false;
            onAllStopped();
            return;
        }
    }

    private void performUpdates(SharedPreferences sharedpreferences)
    {
        Object obj;
        StringBuilder stringbuilder;
        int j;
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        boolean flag = Utils.getIRCDb(this).performUpdates(sharedpreferences, editor);
        String s = getString(0x7f0a0039);
        if (!sharedpreferences.getBoolean(s, false))
        {
            int k = Utils.parseInt(sharedpreferences.getString(getString(0x7f0a0010), "10"), 10);
            int i = k;
            if (k >= 1000)
            {
                i = k / 1000;
            }
            editor.putString(getString(0x7f0a0010), String.valueOf(i));
            editor.putBoolean(s, true);
            flag = true;
        }
        s = getString(0x7f0a002e);
        if (sharedpreferences.getInt(s, -1) == -1)
        {
            int l = (new Random()).nextInt() >> 22;
            String s1;
            boolean flag1;
            if (l < 0)
            {
                j = Math.abs(l);
            } else
            {
                j = l;
                if (l == 0)
                {
                    j = 4096;
                }
            }
            l = j;
            if (j < 500)
            {
                l = j * (j + 1000);
            }
            editor.putInt(s, l);
            flag = true;
        }
        s = getString(0x7f0a003a);
        flag1 = flag;
        if (sharedpreferences.getBoolean(s, false)) goto _L2; else goto _L1
_L1:
        s1 = getString(0x7f0a0022);
        obj = sharedpreferences.getString(s1, null);
        flag1 = flag;
        if (obj == null) goto _L2; else goto _L3
_L3:
        obj = Utils.split(((CharSequence) (obj)));
        stringbuilder = new StringBuilder();
        j = 0;
_L7:
        if (j < obj.length) goto _L5; else goto _L4
_L4:
        if (stringbuilder.length() - 1 >= 0)
        {
            stringbuilder.deleteCharAt(stringbuilder.length() - 1);
        }
        editor.putString(s1, stringbuilder.toString());
        editor.putBoolean(s, true);
        flag1 = true;
_L2:
        s = getString(0x7f0a003b);
        if (!sharedpreferences.getBoolean(s, false))
        {
            s1 = getString(0x7f0a0028);
            obj = sharedpreferences.getString(s1, "0");
            sharedpreferences = "0";
            if (((String) (obj)).equals("Year and Month"))
            {
                sharedpreferences = "0";
            } else
            if (((String) (obj)).equals("Year, Month and Day"))
            {
                sharedpreferences = "1";
            }
            editor.putString(s1, sharedpreferences);
            editor.putBoolean(s, true);
            flag1 = true;
        }
        if (flag1)
        {
            editor.commit();
        }
        return;
_L5:
        stringbuilder.append(obj[j]).append(',');
        j++;
        if (true) goto _L7; else goto _L6
_L6:
    }

    private boolean showFirstRun()
    {
        SharedPreferences sharedpreferences = Utils.getPrefs(this);
        String s = getString(0x7f0a003d);
        if (!sharedpreferences.getBoolean(s, false))
        {
            startActivity(new Intent(this, net/andchat/donate/Activities/initialSetup/Step1));
            sharedpreferences.edit().putBoolean(s, true).commit();
            return true;
        } else
        {
            return false;
        }
    }

    private void showHelp()
    {
        SharedPreferences sharedpreferences = Utils.getPrefs(this);
        String s = getString(0x7f0a003c);
        if (!sharedpreferences.getBoolean(s, false))
        {
            showDialog(1);
            sharedpreferences.edit().putBoolean(s, true).commit();
        }
    }

    private void showWhatsNew()
    {
        SharedPreferences sharedpreferences;
        int i;
        int k;
        sharedpreferences = Utils.getPrefs(this);
        i = 0;
        k = sharedpreferences.getInt("andchat_version", -1);
        int j = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        i = j;
_L2:
        if (i > k)
        {
            sharedpreferences.edit().putInt("andchat_version", i).commit();
            showDialog(2);
        }
        return;
        android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
        namenotfoundexception;
        if (true) goto _L2; else goto _L1
_L1:
    }

    private Intent startNewChat(int i, boolean flag)
    {
        Intent intent = new Intent(this, IRCApp.CHAT_CLASS);
        intent.putExtra("id", i);
        intent.putExtra("reconnect", flag);
        intent.addFlags(0x4000000);
        return intent;
    }

    private void startService()
    {
        startService(new Intent(this, net/andchat/donate/Backend/IRCService));
        bindService(new Intent(this, net/andchat/donate/Backend/IRCService), this, 0);
    }

    void checkDb()
    {
        if (pDb == null)
        {
            pDb = Utils.getIRCDb(this);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 4 && keyevent.getAction() == 0 && pService != null && !pService.areServersAlive())
        {
            ((IRCApp)getApplication()).clearCrypt();
            madeReq = false;
        }
        return super.dispatchKeyEvent(keyevent);
    }

    protected RowAdapter getAdapter()
    {
        Object obj;
        Object obj1;
        Object obj2;
        int j;
        boolean flag;
        checkDb();
        obj1 = pDb.getList();
        boolean flag1;
        if (((List) (obj1)).size() > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        obj = obj1;
        flag1 = flag;
        if (!flag) goto _L2; else goto _L1
_L1:
        obj = obj1;
        flag1 = flag;
        if (sFilter <= 0) goto _L2; else goto _L3
_L3:
        j = ((List) (obj1)).size();
        obj2 = new ArrayList();
        sFilter;
        JVM INSTR tableswitch 1 2: default 88
    //                   1 232
    //                   2 288;
           goto _L4 _L5 _L6
_L4:
        int i;
        if (((ArrayList) (obj2)).size() > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        obj = obj1;
        flag1 = flag;
        if (flag)
        {
            obj = obj2;
            flag1 = flag;
        }
_L2:
        obj2 = mListView;
        if (flag1 || sFilter == 0)
        {
            break MISSING_BLOCK_LABEL_372;
        }
        obj1 = new ArrayList(1);
        obj = null;
        sFilter;
        JVM INSTR tableswitch 1 2: default 168
    //                   1 350
    //                   2 361;
           goto _L7 _L8 _L9
_L7:
        ((List) (obj1)).add(obj);
        unregisterForContextMenu(((View) (obj2)));
        ((ListView) (obj2)).setOnItemClickListener(null);
        ((ListView) (obj2)).setOnItemLongClickListener(null);
        obj = obj1;
_L10:
        ((ListView) (obj2)).setTextFilterEnabled(flag1);
        pAdapter = new RowAdapter(this, 0x7f03001e, 0x7f080004, ((List) (obj)), flag1);
        return pAdapter;
_L5:
        i = 0;
        while (i < j) 
        {
            obj = (String)((List) (obj1)).get(i);
            if (pService.getServerState(pDb.getId(((String) (obj)))) != 4)
            {
                ((ArrayList) (obj2)).add(obj);
            }
            i++;
        }
          goto _L4
_L6:
        i = 0;
        while (i < j) 
        {
            obj = (String)((List) (obj1)).get(i);
            if (pService.getServerState(pDb.getId(((String) (obj)))) == 4)
            {
                ((ArrayList) (obj2)).add(obj);
            }
            i++;
        }
          goto _L4
_L8:
        obj = getString(0x7f0a012a);
          goto _L7
_L9:
        obj = getString(0x7f0a012b);
          goto _L7
        registerForContextMenu(((View) (obj2)));
        ((ListView) (obj2)).setOnItemClickListener(this);
        ((ListView) (obj2)).setOnItemLongClickListener(this);
          goto _L10
    }

    String getCurPosItem()
    {
        return mListView.getItemAtPosition(mCurPos).toString();
    }

    public void onActionBarItemClicked(com.markupartist.android.widget.ActionBar.GenericAction genericaction, View view)
    {
        switch (genericaction.getDrawable())
        {
        default:
            throw new RuntimeException("Unhandled drawable");

        case 2130837540: 
            startActivityForResult(createAddIntent(), 2);
            return;

        case 2130837551: 
            startActivityForResult(createPrefsIntent(), 4);
            return;

        case 2130837549: 
            showDialog(0);
            return;

        case 2130837561: 
            mQuick.show(view);
            break;
        }
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        boolean flag;
        flag = false;
        super.onActivityResult(i, j, intent);
        i;
        JVM INSTR tableswitch 1 4: default 40
    //                   1 138
    //                   2 41
    //                   3 41
    //                   4 116;
           goto _L1 _L2 _L3 _L3 _L4
_L1:
        return;
_L3:
        if (j == -1)
        {
            mListView.setAdapter(getAdapter());
            if (intent != null)
            {
                intent = intent.getStringExtra("label");
                if (intent != null)
                {
                    ListView listview = mListView;
                    i = ((flag) ? 1 : 0);
                    if (pAdapter != null)
                    {
                        i = pAdapter.getPosition(intent);
                    }
                    listview.clearTextFilter();
                    listview.setSelection(i);
                    listview.setSelected(true);
                    return;
                }
            }
        }
        continue; /* Loop/switch isn't completed */
_L4:
        ((IRCApp)getApplication()).notifyWatchers();
        Toast.makeText(this, 0x7f0a01cf, 0).show();
        return;
_L2:
        madeReq = false;
        if (j != -1)
        {
            if (j == 3)
            {
                Toast.makeText(this, 0x7f0a0120, 0).show();
            }
            finish();
            return;
        }
        madeReq = true;
        if (!showFirstRun())
        {
            startService();
            showWhatsNew();
            showHelp();
            return;
        }
        if (true) goto _L1; else goto _L5
_L5:
    }

    public void onAllStopped()
    {
        finish();
    }

    public void onClick(DialogInterface dialoginterface, int i)
    {
        dialoginterface.dismiss();
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131230816: 
            startActivityForResult(createAddIntent(), 2);
            break;
        }
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 0 3: default 36
    //                   0 42
    //                   1 185
    //                   2 227
    //                   3 134;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return super.onContextItemSelected(menuitem);
_L2:
        final int id = pDb.getId(getCurPosItem());
        if (pService != null)
        {
            int j = pService.getServerState(id);
            if (j != 0 && j != 4)
            {
                (new Thread() {

                    final Main this$0;
                    private final int val$id;

                    public void run()
                    {
                        net.andchat.donate.Backend.ServerConnection serverconnection = pService.getServer(id, false, null, null);
                        pService.stopConnection(id, 1);
                        if (serverconnection != null)
                        {
                            pService.remove(serverconnection);
                        }
                        pService.cleanUpIfRequired(id);
                    }

            
            {
                this$0 = Main.this;
                id = i;
                super();
            }
                }).start();
            } else
            if (j != 2)
            {
                startActivity(startNewChat(id, true));
            }
        } else
        {
            startActivity(startNewChat(id, true));
        }
        continue; /* Loop/switch isn't completed */
_L5:
        int i = pDb.getId(getCurPosItem());
        pService.remove(pService.getServer(i, false, null, null));
        pService.cleanUpIfRequired(i);
        pAdapter.refresh();
        continue; /* Loop/switch isn't completed */
_L3:
        startActivityForResult((new Intent(this, net/andchat/donate/Activities/ServerDetailsActivity)).putExtra("edit", true).putExtra("id", pDb.getId(getCurPosItem())), 3);
        continue; /* Loop/switch isn't completed */
_L4:
        final String label = getCurPosItem();
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(0x7f0a0126, new Object[] {
            label
        }));
        builder.setMessage(0x7f0a0127);
        builder.setPositiveButton(0x7f0a01d3, new android.content.DialogInterface.OnClickListener() {

            final Main this$0;
            private final String val$label;

            public void onClick(DialogInterface dialoginterface, int k)
            {
                int l = pDb.getId(label);
                boolean flag = false;
                k = ((flag) ? 1 : 0);
                if (pService != null)
                {
                    k = pService.getServerState(l);
                    if (k != 0 && k != 4)
                    {
                        k = 1;
                        (l. new Thread() {

                            final _cls2 this$1;
                            private final int val$id;

                            public void run()
                            {
                                net.andchat.donate.Backend.ServerConnection serverconnection = pService.getServer(id, false, null, null);
                                pService.stopConnection(id, 1);
                                pService.remove(serverconnection);
                                pService.cleanUpIfRequired(id);
                            }

            
            {
                this$1 = final__pcls2;
                id = I.this;
                super();
            }
                        }).start();
                    } else
                    {
                        dialoginterface = pService.getServer(l, false, null, null);
                        if (dialoginterface != null)
                        {
                            pService.remove(dialoginterface);
                            k = ((flag) ? 1 : 0);
                        } else
                        {
                            pService.cleanUpIfRequired(l);
                            k = ((flag) ? 1 : 0);
                        }
                    }
                }
                if (pDb.deleteServer(label))
                {
                    if (k != 0)
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
                this$0 = Main.this;
                label = s;
                super();
            }
        });
        builder.setNegativeButton(0x7f0a01d4, this);
        mCurDialog = builder.show();
        if (true) goto _L1; else goto _L6
_L6:
    }

    public void onCreate(Bundle bundle)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        super.onCreate(bundle);
        setContentView(0x7f03000a);
        mFocused = true;
        bundle = Utils.getPrefs(this);
        performUpdates(bundle);
        mListView = getListView();
        if (!bundle.getBoolean(getString(0x7f0a001e), false) || madeReq) goto _L2; else goto _L1
_L1:
        bundle = Utils.getCrypt(this);
        madeReq = true;
        if (!bundle.correctPass())
        {
            startActivityForResult((new Intent(this, net/andchat/donate/Activities/PasswordActivity)).putExtra("action", 1).putExtra("message", getString(0x7f0a011f)), 1);
        }
_L4:
        if (android.os.Build.VERSION.SDK_INT >= 9)
        {
            StrictMode.setThreadPolicy(android.os.StrictMode.ThreadPolicy.LAX);
        }
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            bundle = getWindow().getDecorView().findViewById(0x7f080012);
            QuickAction quickaction = new QuickAction(this);
            (new net.andchat.donate.Misc.AbstractMenuInflator.QuickActionsMenu(quickaction)).addActionsFromXML(this, 0x7f0f0005);
            mQuick = quickaction;
            quickaction.setOnActionItemClickListener(this);
            bundle = (ActionBar)bundle;
            mActions = bundle;
            (new net.andchat.donate.Misc.AbstractMenuInflator.ActionBarMenu(bundle, this)).addActionsFromXML(this, 0x7f0f0005);
            bundle.setOnActionClickListener(this);
        }
        setTitle();
        findViewById(0x7f080060).setOnClickListener(this);
        return;
_L2:
        if (!showFirstRun())
        {
            showWhatsNew();
            startService();
            showHelp();
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        super.onCreateContextMenu(contextmenu, view, contextmenuinfo);
        view = getCurPosItem();
        contextmenu.setHeaderTitle(view);
        if (pService != null)
        {
            int i = pService.getServerState(pDb.getId(view));
            if (i != 4 && i != 0)
            {
                contextmenu.add(0, 0, 0, 0x7f0a00a0);
            } else
            if (i == 0)
            {
                contextmenu.add(0, 0, 0, 0x7f0a00a1);
                contextmenu.add(0, 3, 3, 0x7f0a00a2).setEnabled(true);
            } else
            {
                contextmenu.add(0, 0, 0, 0x7f0a009e);
            }
        } else
        {
            contextmenu.add(0, 0, 0, 0x7f0a009e);
        }
        contextmenu.add(0, 1, 1, 0x7f0a00a4);
        contextmenu.add(0, 2, 2, 0x7f0a00a3);
    }

    protected Dialog onCreateDialog(int i)
    {
        switch (i)
        {
        default:
            return super.onCreateDialog(i);

        case 0: // '\0'
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle(0x7f0a012c).setSingleChoiceItems(0x7f0b0001, sFilter, new android.content.DialogInterface.OnClickListener() {

                final Main this$0;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                    if (j != Main.sFilter)
                    {
                        Main.sFilter = j;
                        mListView.clearTextFilter();
                        setListAdapter(getAdapter());
                        setTitle();
                    }
                }

            
            {
                this$0 = Main.this;
                super();
            }
            });
            builder.setPositiveButton(0x7f0a01d2, new android.content.DialogInterface.OnClickListener() {

                final Main this$0;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$0 = Main.this;
                super();
            }
            });
            return builder.create();

        case 1: // '\001'
            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
            builder1.setTitle(0x7f0a012e);
            builder1.setIcon(0x108009b);
            View view = getLayoutInflater().inflate(0x7f030019, null);
            TextView textview = (TextView)view.findViewById(0x7f08005f);
            textview.setTextSize(18F);
            StringBuilder stringbuilder = new StringBuilder();
            Utils.getHelpText(this, stringbuilder);
            textview.setText(Html.fromHtml(stringbuilder.toString()));
            textview.setMovementMethod(new LinkMovementMethod());
            builder1.setView(view);
            builder1.setNeutralButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final Main this$0;

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$0 = Main.this;
                super();
            }
            });
            return builder1.create();

        case 2: // '\002'
            return Utils.getWhatsNewDialog(this);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!IRCApp.LEGACY_VERSION)
        {
            getMenuInflater().inflate(0x7f0f0005, menu);
            return super.onCreateOptionsMenu(menu);
        } else
        {
            return true;
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if (isFinishing())
        {
            ((IRCApp)getApplication()).closeDb();
        }
        if (pService != null)
        {
            pService.removeStateListener(this);
            pService.stop();
        }
        try
        {
            unbindService(this);
        }
        catch (Exception exception) { }
        if (mCurDialog != null)
        {
            mCurDialog.dismiss();
        }
        mListView.clearTextFilter();
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        mCurPos = i;
        startActivity(startNewChat(pDb.getId(getCurPosItem()), false));
    }

    public void onItemClick(QuickAction quickaction, int i, int j)
    {
        switch (j)
        {
        default:
            return;

        case 2131230737: 
            showDialog(1);
            return;

        case 2131230852: 
            quickaction.dismiss();
            break;
        }
        exit();
    }

    public boolean onItemLongClick(AdapterView adapterview, View view, int i, long l)
    {
        mCurPos = i;
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR lookupswitch 5: default 56
    //                   2131230737: 94
    //                   2131230849: 74
    //                   2131230850: 62
    //                   2131230851: 86
    //                   2131230852: 102;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        return super.onOptionsItemSelected(menuitem);
_L4:
        startActivityForResult(createAddIntent(), 2);
        continue; /* Loop/switch isn't completed */
_L3:
        startActivityForResult(createPrefsIntent(), 4);
        continue; /* Loop/switch isn't completed */
_L5:
        showDialog(0);
        continue; /* Loop/switch isn't completed */
_L2:
        showDialog(1);
        continue; /* Loop/switch isn't completed */
_L6:
        exit();
        if (true) goto _L1; else goto _L7
_L7:
    }

    protected void onPause()
    {
        super.onPause();
        if (pService != null && pService.getActiveServerCount() == 0)
        {
            pService.doStopForeground(true);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (IRCApp.LEGACY_VERSION)
        {
            Animation animation = mBounce;
            menu = animation;
            if (animation == null)
            {
                menu = AnimationUtils.loadAnimation(this, 0x7f040000);
                mBounce = menu;
            }
            mActions.startAnimation(menu);
        }
        return true;
    }

    protected void onRestart()
    {
        mFocused = true;
        checkDb();
        super.onRestart();
        int i = mListView.getSelectedItemPosition();
        if (pAdapter == null)
        {
            pAdapter = getAdapter();
        } else
        {
            pAdapter.refresh();
        }
        mListView.setSelection(i);
    }

    protected void onResume()
    {
        super.onResume();
    }

    public void onServerStateChanged(int i)
    {
        if (mFocused)
        {
            i = mListView.getSelectedItemPosition();
            if (sFilter == 0)
            {
                pAdapter.refresh();
            } else
            {
                setListAdapter(getAdapter());
            }
            mListView.setSelection(i);
        }
    }

    public void onServiceConnected(ComponentName componentname, IBinder ibinder)
    {
        pService = ((net.andchat.donate.Backend.IRCService.IRCServiceBinder)ibinder).getService();
        pService.addStateListener(this);
        pDb = Utils.getIRCDb(this);
        setListAdapter(getAdapter());
        componentname = getIntent();
        if (componentname != null && componentname.getAction() != null && componentname.getAction().equals("net.andchat.donate.FROM_NOTIFICATION") && pService.getActiveServerCount() == 1)
        {
            startActivity(startNewChat(pService.getFirstActiveId(), false));
        }
    }

    public void onServiceDisconnected(ComponentName componentname)
    {
    }

    protected void onStop()
    {
        mFocused = false;
        if (mQuick != null)
        {
            mQuick.dismiss();
        }
        super.onStop();
    }

    void setTitle()
    {
        switch (sFilter)
        {
        default:
            if (mActions != null)
            {
                mActions.setTitle(0x7f0a0000);
                return;
            } else
            {
                setTitle(0x7f0a0000);
                return;
            }

        case 1: // '\001'
            if (mActions != null)
            {
                mActions.setTitle(0x7f0a008e);
                return;
            } else
            {
                setTitle(0x7f0a008e);
                return;
            }

        case 2: // '\002'
            break;
        }
        if (mActions != null)
        {
            mActions.setTitle(0x7f0a008f);
            return;
        } else
        {
            setTitle(0x7f0a008f);
            return;
        }
    }


    // Unreferenced inner class net/andchat/donate/Activities/Main$RowAdapter$1

/* anonymous class */
    class RowAdapter._cls1
        implements android.view.View.OnClickListener
    {

        final RowAdapter this$1;

        public void onClick(View view)
        {
            getListView().showContextMenuForChild((View)view.getParent());
        }

            
            {
                this$1 = RowAdapter.this;
                super();
            }
    }

}
