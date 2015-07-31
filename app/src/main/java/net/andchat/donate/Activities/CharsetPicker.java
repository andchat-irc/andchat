// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.markupartist.android.widget.ActionBar;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.SortedMap;
import net.andchat.donate.IRCApp;

public class CharsetPicker extends ListActivity
    implements android.widget.AdapterView.OnItemClickListener, com.markupartist.android.widget.ActionBar.ActionBarItemClickHandler
{

    private boolean mHaveKeyboard;

    public CharsetPicker()
    {
    }

    private void initiateSearch()
    {
        if (mHaveKeyboard)
        {
            Toast.makeText(this, 0x7f0a01bc, 0).show();
            return;
        } else
        {
            ((InputMethodManager)getSystemService("input_method")).showSoftInput(getListView(), 1);
            Toast.makeText(this, 0x7f0a01bc, 0).show();
            return;
        }
    }

    public void onActionBarItemClicked(com.markupartist.android.widget.ActionBar.GenericAction genericaction, View view)
    {
        initiateSearch();
    }

    protected void onCreate(Bundle bundle)
    {
        boolean flag1 = true;
        if (IRCApp.LEGACY_VERSION)
        {
            requestWindowFeature(7);
        }
        super.onCreate(bundle);
        setListAdapter(new ArrayAdapter(this, 0x1090003, Charset.availableCharsets().keySet().toArray()));
        bundle = getListView();
        bundle.setOnItemClickListener(this);
        bundle.setTextFilterEnabled(true);
        bundle.setFastScrollEnabled(true);
        bundle = getResources().getConfiguration();
        boolean flag = flag1;
        if (((Configuration) (bundle)).keyboard == 1)
        {
            flag = flag1;
            if (((Configuration) (bundle)).hardKeyboardHidden == 2)
            {
                flag = false;
            }
        }
        mHaveKeyboard = flag;
        if (IRCApp.LEGACY_VERSION)
        {
            getWindow().setFeatureInt(7, 0x7f030006);
            bundle = (ActionBar)getWindow().getDecorView().findViewById(0x7f080012);
            bundle.setTitle(0x7f0a007e);
            (new net.andchat.donate.Misc.AbstractMenuInflator.ActionBarMenu(bundle, this)).addActionsFromXML(this, 0x7f0f0000);
            bundle.setOnActionClickListener(this);
            return;
        } else
        {
            setTitle(0x7f0a007e);
            return;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!IRCApp.LEGACY_VERSION)
        {
            getMenuInflater().inflate(0x7f0f0000, menu);
            return true;
        } else
        {
            return false;
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        adapterview = ((TextView)view).getText().toString();
        setResult(-1, (new Intent()).putExtra("selection", adapterview));
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(getListView().getWindowToken(), 1);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        initiateSearch();
        return true;
    }

    public boolean onSearchRequested()
    {
        initiateSearch();
        return true;
    }
}
