// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import net.andchat.donate.Backend.Ignores;
import net.andchat.donate.Misc.Utils;
import net.londatiga.android.QuickAction;

// Referenced classes of package net.andchat.donate.Activities:
//            IgnoresActivityHelper

public class IgnoresActivity extends ListActivity
    implements android.view.View.OnClickListener, IgnoresActivityHelper.PopupMenuCallback, net.londatiga.android.QuickAction.OnQuickActionItemClickListener
{
    private static final class Holder
    {

        final TextView tv;

        public Holder(TextView textview)
        {
            tv = textview;
        }
    }

    private class IgnoreListAdapter extends BaseAdapter
        implements android.view.View.OnClickListener
    {

        private final LayoutInflater mInflator;
        final IgnoresActivity this$0;

        public int getCount()
        {
            return mIgnoreList.size();
        }

        public Object getItem(int i)
        {
            return mIgnoreList.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            net.andchat.donate.Backend.Ignores.IgnoreInfo ignoreinfo;
            if (view == null)
            {
                viewgroup = mInflator.inflate(0x7f03001d, viewgroup, false);
                view = (TextView)viewgroup.findViewById(0x7f080061);
                ImageView imageview = (ImageView)viewgroup.findViewById(0x7f080062);
                ImageView imageview1 = (ImageView)viewgroup.findViewById(0x7f080063);
                viewgroup.setTag(new Holder(view));
                imageview.setOnClickListener(this);
                imageview1.setOnClickListener(this);
                IgnoresActivityHelper.sInstance.initializeForView(imageview1);
            } else
            {
                viewgroup = view;
                view = ((Holder)viewgroup.getTag()).tv;
            }
            ignoreinfo = (net.andchat.donate.Backend.Ignores.IgnoreInfo)mIgnoreList.get(i);
            view.setText((new StringBuilder(ignoreinfo.nick)).append(" (").append(ignoreinfo.ident).append('@').append(ignoreinfo.hostname).append(")"));
            return viewgroup;
        }

        public void onClick(View view)
        {
            mClickLocation = getListView().getPositionForView((View)view.getParent());
            switch (view.getId())
            {
            default:
                return;

            case 2131230818: 
                ((Activity)view.getContext()).showDialog(0);
                return;

            case 2131230819: 
                IgnoresActivityHelper.sInstance.handleViewClick(view);
                break;
            }
        }

        public IgnoreListAdapter(LayoutInflater layoutinflater, Ignores ignores)
        {
            this$0 = IgnoresActivity.this;
            super();
            mIgnoreList = ignores.getAllIgnores();
            mInflator = layoutinflater;
        }
    }


    private int mClickLocation;
    private int mIgnoreEditMask;
    private List mIgnoreList;
    private Ignores mIgnores;

    public IgnoresActivity()
    {
    }

    private void handleClick()
    {
        net.andchat.donate.Backend.Ignores.IgnoreInfo ignoreinfo = (net.andchat.donate.Backend.Ignores.IgnoreInfo)mIgnoreList.get(mClickLocation);
        mIgnores.removeIgnore(ignoreinfo.ident, ignoreinfo.hostname);
        ((BaseAdapter)getListAdapter()).notifyDataSetChanged();
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131230771: 
            showDialog(1);
            return;

        case 2131230770: 
            finish();
            return;
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030009);
        mIgnores = Ignores.getIgnores(getIntent().getIntExtra("id", -1), Utils.getIRCDb(this));
        setListAdapter(new IgnoreListAdapter(getLayoutInflater(), mIgnores));
        getListView().setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            final IgnoresActivity this$0;

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                mClickLocation = i;
                showDialog(0);
            }

            
            {
                this$0 = IgnoresActivity.this;
                super();
            }
        });
        findViewById(0x7f080033).setOnClickListener(this);
        findViewById(0x7f080032).setOnClickListener(this);
    }

    public Dialog onCreateDialog(int j)
    {
        switch (j)
        {
        default:
            return super.onCreateDialog(j);

        case 0: // '\0'
            final Ignores i = mIgnores;
            final net.andchat.donate.Backend.Ignores.IgnoreInfo ii = (net.andchat.donate.Backend.Ignores.IgnoreInfo)i.getAllIgnores().get(mClickLocation);
            j = ii.mask;
            boolean flag = Utils.isBitSet(j, 1);
            boolean flag1 = Utils.isBitSet(j, 2);
            boolean flag2 = Utils.isBitSet(j, 4);
            boolean flag3 = Utils.isBitSet(j, 8);
            mIgnoreEditMask = j;
            android.app.AlertDialog.Builder builder = (new android.app.AlertDialog.Builder(this)).setTitle(getString(0x7f0a0161, new Object[] {
                ii.nick
            }));
            android.content.DialogInterface.OnMultiChoiceClickListener onmultichoiceclicklistener = new android.content.DialogInterface.OnMultiChoiceClickListener() {

                final IgnoresActivity this$0;

                public void onClick(DialogInterface dialoginterface, int k, boolean flag4)
                {
                    boolean flag5 = false;
                    k;
                    JVM INSTR tableswitch 0 3: default 36
                //                               0 59
                //                               1 64
                //                               2 69
                //                               3 74;
                       goto _L1 _L2 _L3 _L4 _L5
_L5:
                    break MISSING_BLOCK_LABEL_74;
_L1:
                    k = ((flag5) ? 1 : 0);
_L6:
                    if (flag4)
                    {
                        dialoginterface = IgnoresActivity.this;
                        dialoginterface.mIgnoreEditMask = ((IgnoresActivity) (dialoginterface)).mIgnoreEditMask | k;
                        return;
                    } else
                    {
                        dialoginterface = IgnoresActivity.this;
                        dialoginterface.mIgnoreEditMask = ((IgnoresActivity) (dialoginterface)).mIgnoreEditMask & ~k;
                        return;
                    }
_L2:
                    k = 1;
                      goto _L6
_L3:
                    k = 2;
                      goto _L6
_L4:
                    k = 4;
                      goto _L6
                    k = 8;
                      goto _L6
                }

            
            {
                this$0 = IgnoresActivity.this;
                super();
            }
            };
            return builder.setMultiChoiceItems(0x7f0b0008, new boolean[] {
                flag, flag1, flag2, flag3
            }, onmultichoiceclicklistener).setPositiveButton(0x7f0a01d0, new android.content.DialogInterface.OnClickListener() {

                final IgnoresActivity this$0;
                private final Ignores val$i;
                private final net.andchat.donate.Backend.Ignores.IgnoreInfo val$ii;

                public void onClick(DialogInterface dialoginterface, int k)
                {
                    i.addOrUpdateIgnore(ii.nick, ii.ident, ii.hostname, mIgnoreEditMask);
                    dialoginterface.dismiss();
                    removeDialog(0);
                }

            
            {
                this$0 = IgnoresActivity.this;
                i = ignores;
                ii = ignoreinfo;
                super();
            }
            }).setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                final IgnoresActivity this$0;

                public void onCancel(DialogInterface dialoginterface)
                {
                    removeDialog(0);
                }

            
            {
                this$0 = IgnoresActivity.this;
                super();
            }
            }).create();

        case 1: // '\001'
            return (new android.app.AlertDialog.Builder(this)).setTitle(0x7f0a015f).setMessage(0x7f0a0160).setIcon(0x108009b).setNeutralButton(0x7f0a01d5, new android.content.DialogInterface.OnClickListener() {

                final IgnoresActivity this$0;

                public void onClick(DialogInterface dialoginterface, int k)
                {
                    dialoginterface.dismiss();
                }

            
            {
                this$0 = IgnoresActivity.this;
                super();
            }
            }).create();
        }
    }

    public void onItemClick(QuickAction quickaction, int i, int j)
    {
        handleClick();
    }

    public boolean onMenuItemClick(MenuItem menuitem)
    {
        handleClick();
        return true;
    }





}
