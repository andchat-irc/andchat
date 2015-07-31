// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package android.support.v4.app:
//            NotificationCompatHoneycomb, NotificationCompatIceCreamSandwich, NotificationCompatJellybean

public class NotificationCompat
{
    public static class Action
    {

        public PendingIntent actionIntent;
        public int icon;
        public CharSequence title;
    }

    public static class BigPictureStyle extends Style
    {

        Bitmap mPicture;

        public BigPictureStyle()
        {
        }
    }

    public static class BigTextStyle extends Style
    {

        CharSequence mBigText;

        public BigTextStyle()
        {
        }
    }

    public static class Builder
    {

        ArrayList mActions;
        CharSequence mContentInfo;
        PendingIntent mContentIntent;
        CharSequence mContentText;
        CharSequence mContentTitle;
        Context mContext;
        PendingIntent mFullScreenIntent;
        Bitmap mLargeIcon;
        Notification mNotification;
        int mNumber;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Style mStyle;
        CharSequence mSubText;
        RemoteViews mTickerView;
        boolean mUseChronometer;

        private void setFlag(int i, boolean flag)
        {
            if (flag)
            {
                Notification notification = mNotification;
                notification.flags = notification.flags | i;
                return;
            } else
            {
                Notification notification1 = mNotification;
                notification1.flags = notification1.flags & ~i;
                return;
            }
        }

        public Notification build()
        {
            return NotificationCompat.IMPL.build(this);
        }

        public Builder setAutoCancel(boolean flag)
        {
            setFlag(16, flag);
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingintent)
        {
            mContentIntent = pendingintent;
            return this;
        }

        public Builder setContentText(CharSequence charsequence)
        {
            mContentText = charsequence;
            return this;
        }

        public Builder setContentTitle(CharSequence charsequence)
        {
            mContentTitle = charsequence;
            return this;
        }

        public Builder setDefaults(int i)
        {
            mNotification.defaults = i;
            if ((i & 4) != 0)
            {
                Notification notification = mNotification;
                notification.flags = notification.flags | 1;
            }
            return this;
        }

        public Builder setNumber(int i)
        {
            mNumber = i;
            return this;
        }

        public Builder setSmallIcon(int i)
        {
            mNotification.icon = i;
            return this;
        }

        public Builder setSound(Uri uri)
        {
            mNotification.sound = uri;
            mNotification.audioStreamType = -1;
            return this;
        }

        public Builder setTicker(CharSequence charsequence)
        {
            mNotification.tickerText = charsequence;
            return this;
        }

        public Builder setWhen(long l)
        {
            mNotification.when = l;
            return this;
        }

        public Builder(Context context)
        {
            mActions = new ArrayList();
            mNotification = new Notification();
            mContext = context;
            mNotification.when = System.currentTimeMillis();
            mNotification.audioStreamType = -1;
            mPriority = 0;
        }
    }

    public static class InboxStyle extends Style
    {

        ArrayList mTexts;

        public InboxStyle()
        {
            mTexts = new ArrayList();
        }
    }

    static interface NotificationCompatImpl
    {

        public abstract Notification build(Builder builder);
    }

    static class NotificationCompatImplBase
        implements NotificationCompatImpl
    {

        public Notification build(Builder builder)
        {
            Notification notification = builder.mNotification;
            notification.setLatestEventInfo(builder.mContext, builder.mContentTitle, builder.mContentText, builder.mContentIntent);
            if (builder.mPriority > 0)
            {
                notification.flags = notification.flags | 0x80;
            }
            return notification;
        }

        NotificationCompatImplBase()
        {
        }
    }

    static class NotificationCompatImplHoneycomb
        implements NotificationCompatImpl
    {

        public Notification build(Builder builder)
        {
            return NotificationCompatHoneycomb.add(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon);
        }

        NotificationCompatImplHoneycomb()
        {
        }
    }

    static class NotificationCompatImplIceCreamSandwich
        implements NotificationCompatImpl
    {

        public Notification build(Builder builder)
        {
            return NotificationCompatIceCreamSandwich.add(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate);
        }

        NotificationCompatImplIceCreamSandwich()
        {
        }
    }

    static class NotificationCompatImplJellybean
        implements NotificationCompatImpl
    {

        public Notification build(Builder builder)
        {
            NotificationCompatJellybean notificationcompatjellybean;
            notificationcompatjellybean = new NotificationCompatJellybean(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mUseChronometer, builder.mPriority, builder.mSubText);
            Action action;
            for (Iterator iterator = builder.mActions.iterator(); iterator.hasNext(); notificationcompatjellybean.addAction(action.icon, action.title, action.actionIntent))
            {
                action = (Action)iterator.next();
            }

            if (builder.mStyle == null) goto _L2; else goto _L1
_L1:
            if (!(builder.mStyle instanceof BigTextStyle)) goto _L4; else goto _L3
_L3:
            builder = (BigTextStyle)builder.mStyle;
            notificationcompatjellybean.addBigTextStyle(((BigTextStyle) (builder)).mBigContentTitle, ((BigTextStyle) (builder)).mSummaryTextSet, ((BigTextStyle) (builder)).mSummaryText, ((BigTextStyle) (builder)).mBigText);
_L2:
            return notificationcompatjellybean.build();
_L4:
            if (builder.mStyle instanceof InboxStyle)
            {
                builder = (InboxStyle)builder.mStyle;
                notificationcompatjellybean.addInboxStyle(((InboxStyle) (builder)).mBigContentTitle, ((InboxStyle) (builder)).mSummaryTextSet, ((InboxStyle) (builder)).mSummaryText, ((InboxStyle) (builder)).mTexts);
            } else
            if (builder.mStyle instanceof BigPictureStyle)
            {
                builder = (BigPictureStyle)builder.mStyle;
                notificationcompatjellybean.addBigPictureStyle(((BigPictureStyle) (builder)).mBigContentTitle, ((BigPictureStyle) (builder)).mSummaryTextSet, ((BigPictureStyle) (builder)).mSummaryText, ((BigPictureStyle) (builder)).mPicture);
            }
            if (true) goto _L2; else goto _L5
_L5:
        }

        NotificationCompatImplJellybean()
        {
        }
    }

    public static abstract class Style
    {

        CharSequence mBigContentTitle;
        CharSequence mSummaryText;
        boolean mSummaryTextSet;

        public Style()
        {
            mSummaryTextSet = false;
        }
    }


    private static final NotificationCompatImpl IMPL;

    static 
    {
        if (android.os.Build.VERSION.SDK_INT >= 16)
        {
            IMPL = new NotificationCompatImplJellybean();
        } else
        if (android.os.Build.VERSION.SDK_INT >= 14)
        {
            IMPL = new NotificationCompatImplIceCreamSandwich();
        } else
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            IMPL = new NotificationCompatImplHoneycomb();
        } else
        {
            IMPL = new NotificationCompatImplBase();
        }
    }

}
