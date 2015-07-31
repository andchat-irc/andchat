// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.londatiga.android;


public class ActionItem
{

    private int actionId;
    private String title;

    public ActionItem()
    {
        this(-1, null);
    }

    public ActionItem(int i, String s)
    {
        actionId = -1;
        title = s;
        actionId = i;
    }

    public int getActionId()
    {
        return actionId;
    }

    public String getTitle()
    {
        return title;
    }
}
