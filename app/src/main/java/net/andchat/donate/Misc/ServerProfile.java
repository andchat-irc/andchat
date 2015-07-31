// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Misc;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import net.andchat.donate.Backend.Crypt;
import org.xmlpull.v1.XmlPullParser;

// Referenced classes of package net.andchat.donate.Misc:
//            Utils

public class ServerProfile
    implements Parcelable
{
    public static final class AuthenticationModes
    {

        public static final int MODE_NICKSERV;
        public static final int MODE_PASSWORD;
        public static final int MODE_SASL;
        private static int start;

        static 
        {
            start = 0;
            int i = start;
            start = i + 1;
            MODE_PASSWORD = 1 << i;
            i = start;
            start = i + 1;
            MODE_NICKSERV = 1 << i;
            i = start;
            start = i + 1;
            MODE_SASL = 1 << i;
        }
    }


    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public ServerProfile createFromParcel(Parcel parcel)
        {
            return new ServerProfile(parcel);
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

        public ServerProfile[] newArray(int i)
        {
            return new ServerProfile[i];
        }

    };
    private String mAddress;
    private int mAuthModes;
    private String mAutojoin;
    private String mAutorun;
    private String mCharset;
    private ArrayList mIgnores;
    private boolean mIsDecrypted;
    private String mLabel;
    private boolean mLogs;
    private String mNick1;
    private String mNick2;
    private String mNick3;
    private String mNickservPassword;
    private int mPort;
    private String mRealname;
    private boolean mRequiresEncryption;
    private String mSASL_Password;
    private String mSASL_Username;
    private boolean mSSL;
    private String mServerPassword;
    private String mUsername;

    public ServerProfile()
    {
        mIgnores = new ArrayList();
        mIsDecrypted = true;
        setEmptyAuthModes();
    }

    public ServerProfile(Parcel parcel)
    {
        boolean flag1 = true;
        super();
        mIgnores = new ArrayList();
        mLabel = parcel.readString();
        mAddress = parcel.readString();
        mPort = parcel.readInt();
        boolean flag;
        if (parcel.readInt() == 1)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        mSSL = flag;
        mNick1 = parcel.readString();
        mNick2 = parcel.readString();
        mNick3 = parcel.readString();
        mUsername = parcel.readString();
        mRealname = parcel.readString();
        mAutojoin = parcel.readString();
        mAutorun = parcel.readString();
        mAuthModes = parcel.readInt();
        if (mAuthModes > 0)
        {
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_PASSWORD))
            {
                mServerPassword = parcel.readString();
            }
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_NICKSERV))
            {
                mNickservPassword = parcel.readString();
            }
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_SASL))
            {
                mSASL_Username = parcel.readString();
                mSASL_Password = parcel.readString();
            }
        }
        mCharset = parcel.readString();
        if (parcel.readInt() == 1)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        mLogs = flag;
        if (parcel.readInt() == 1)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        mRequiresEncryption = flag;
        if (parcel.readInt() == 1)
        {
            flag = flag1;
        } else
        {
            flag = false;
        }
        mIsDecrypted = flag;
    }

    public static ServerProfile fromXml(XmlPullParser xmlpullparser, Activity activity)
    {
        ServerProfile serverprofile = new ServerProfile();
        serverprofile.mLabel = xmlpullparser.getAttributeValue(null, "name");
        if (serverprofile.mLabel == null)
        {
            return null;
        }
        serverprofile.mAddress = xmlpullparser.getAttributeValue(null, "address");
        if (serverprofile.mAddress == null)
        {
            return null;
        }
        serverprofile.mSSL = trueOrFalse(xmlpullparser.getAttributeValue(null, "ssl"), false);
        String s = xmlpullparser.getAttributeValue(null, "port");
        char c;
        if (serverprofile.mSSL)
        {
            c = '\u1B58';
        } else
        {
            c = '\u1A0B';
        }
        serverprofile.mPort = Utils.parseInt(s, c);
        serverprofile.mNick1 = xmlpullparser.getAttributeValue(null, "nick1");
        serverprofile.mNick2 = xmlpullparser.getAttributeValue(null, "nick2");
        serverprofile.mNick3 = xmlpullparser.getAttributeValue(null, "nick3");
        serverprofile.mRealname = xmlpullparser.getAttributeValue(null, "realname");
        serverprofile.mUsername = xmlpullparser.getAttributeValue(null, "username");
        serverprofile.mAutojoin = xmlpullparser.getAttributeValue(null, "autojoin");
        serverprofile.mAutorun = xmlpullparser.getAttributeValue(null, "autorun");
        serverprofile.mServerPassword = xmlpullparser.getAttributeValue(null, "password");
        serverprofile.mCharset = xmlpullparser.getAttributeValue(null, "charset");
        serverprofile.mRequiresEncryption = trueOrFalse(xmlpullparser.getAttributeValue(null, "encrypted"), false);
        serverprofile.mLogs = trueOrFalse(xmlpullparser.getAttributeValue(null, "logmessages"), true);
        serverprofile.mNickservPassword = xmlpullparser.getAttributeValue(null, "nickserv_password");
        serverprofile.mSASL_Username = xmlpullparser.getAttributeValue(null, "sasl_username");
        serverprofile.mSASL_Password = xmlpullparser.getAttributeValue(null, "sasl_password");
        serverprofile.mAuthModes = Utils.parseInt(xmlpullparser.getAttributeValue(null, "auth_modes"), 0);
        serverprofile.fillDefaults(activity);
        serverprofile.validateSelf();
        return serverprofile;
    }

    private static boolean trueOrFalse(String s, boolean flag)
    {
        if (s != null)
        {
            flag = s.equalsIgnoreCase("true");
        }
        return flag;
    }

    public void addAuthMode(int i)
    {
        mAuthModes = mAuthModes | i;
    }

    public void cloneAuthDetailsFrom(ServerProfile serverprofile)
    {
        int i = serverprofile.getAuthModes();
        if (Utils.isBitSet(i, AuthenticationModes.MODE_PASSWORD))
        {
            mServerPassword = serverprofile.getServerPassword();
        } else
        {
            mServerPassword = "";
        }
        if (Utils.isBitSet(i, AuthenticationModes.MODE_NICKSERV))
        {
            mNickservPassword = serverprofile.getNickservPassword();
        } else
        {
            mNickservPassword = "";
        }
        if (Utils.isBitSet(i, AuthenticationModes.MODE_SASL))
        {
            mSASL_Username = serverprofile.getSASLUsername();
            mSASL_Password = serverprofile.getSASLPassword();
        } else
        {
            mSASL_Username = "";
            mSASL_Password = "";
        }
        mAuthModes = i;
    }

    public void decryptSelf(Crypt crypt)
    {
        if (isDecrypted())
        {
            return;
        }
        mAddress = crypt.decrypt(mAddress);
        if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_PASSWORD))
        {
            mServerPassword = crypt.decrypt(mServerPassword);
        }
        if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_NICKSERV))
        {
            mNickservPassword = crypt.decrypt(mNickservPassword);
        }
        if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_SASL))
        {
            mSASL_Password = crypt.decrypt(mSASL_Password);
        }
        setDecrypted(true);
    }

    public int describeContents()
    {
        return 0;
    }

    public void encryptSelf(Crypt crypt)
    {
        if (isDecrypted())
        {
            mAddress = crypt.encrypt(mAddress);
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_PASSWORD))
            {
                mServerPassword = crypt.encrypt(mServerPassword);
            }
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_NICKSERV))
            {
                mNickservPassword = crypt.encrypt(mNickservPassword);
            }
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_SASL))
            {
                mSASL_Password = crypt.encrypt(mSASL_Password);
            }
            setDecrypted(false);
        }
    }

    public void fillDefaults(Activity activity)
    {
        if (TextUtils.isEmpty(mNick1))
        {
            mNick1 = Utils.getDefaultServerProfileValue(0x7f0a0001, activity);
        }
        if (TextUtils.isEmpty(mNick2))
        {
            mNick2 = Utils.getDefaultServerProfileValue(0x7f0a0002, activity);
        }
        if (TextUtils.isEmpty(mNick3))
        {
            mNick3 = Utils.getDefaultServerProfileValue(0x7f0a0003, activity);
        }
        if (TextUtils.isEmpty(mRealname))
        {
            mRealname = Utils.getDefaultServerProfileValue(0x7f0a0004, activity);
        }
        if (TextUtils.isEmpty(mUsername))
        {
            mUsername = Utils.getDefaultServerProfileValue(0x7f0a0005, activity);
        }
        if (TextUtils.isEmpty(mCharset))
        {
            mCharset = Utils.getDefaultServerProfileValue(0x7f0a0006, activity);
        }
        if (TextUtils.isEmpty(mNickservPassword))
        {
            mNickservPassword = "";
        }
        if (TextUtils.isEmpty(mSASL_Username))
        {
            mSASL_Username = "";
        }
        if (TextUtils.isEmpty(mSASL_Password))
        {
            mSASL_Password = "";
        }
        if (TextUtils.isEmpty(mServerPassword))
        {
            mServerPassword = "";
        }
    }

    public void fillPort(Activity activity)
    {
        if (mPort <= 0 || mPort > 65535)
        {
            int i;
            if (mSSL)
            {
                i = 7000;
            } else
            {
                i = 6667;
            }
            mPort = i;
        }
    }

    public String getAddress()
    {
        return mAddress;
    }

    public int getAuthModes()
    {
        return mAuthModes;
    }

    public String getAutojoinList()
    {
        return mAutojoin;
    }

    public String getAutorunList()
    {
        return mAutorun;
    }

    public String getCharset()
    {
        return mCharset;
    }

    public List getIgnoreList()
    {
        return mIgnores;
    }

    public String getName()
    {
        return mLabel;
    }

    public String getNick(int i)
    {
        switch (i)
        {
        default:
            throw new IllegalArgumentException((new StringBuilder(String.valueOf(i))).append(" is not valid, use 1/2/3").toString());

        case 1: // '\001'
            return mNick1;

        case 2: // '\002'
            return mNick2;

        case 3: // '\003'
            return mNick3;
        }
    }

    public String getNickservPassword()
    {
        return mNickservPassword;
    }

    public int getPort()
    {
        return mPort;
    }

    public String getRealname()
    {
        return mRealname;
    }

    public String getSASLPassword()
    {
        return mSASL_Password;
    }

    public String getSASLUsername()
    {
        return mSASL_Username;
    }

    public String getServerPassword()
    {
        return mServerPassword;
    }

    public String getUsername()
    {
        return mUsername;
    }

    public boolean isDecrypted()
    {
        return mIsDecrypted;
    }

    public boolean isLoggingEnabled()
    {
        return mLogs;
    }

    public void removeAuthMode(int i)
    {
        mAuthModes = mAuthModes & ~i;
    }

    public ServerProfile setAuthDetails(int i, String s, String s1, String s2, String s3)
    {
        mAuthModes = i;
        mServerPassword = s;
        mNickservPassword = s1;
        mSASL_Username = s2;
        mSASL_Password = s3;
        return this;
    }

    public void setCharset(String s)
    {
        mCharset = s;
    }

    public void setDecrypted(boolean flag)
    {
        mIsDecrypted = flag;
    }

    public void setEmptyAuthModes()
    {
        mServerPassword = "";
        mNickservPassword = "";
        mSASL_Username = "";
        mSASL_Password = "";
        mAuthModes = 0;
    }

    public void setNick(int i, String s)
    {
        switch (i)
        {
        default:
            throw new IllegalArgumentException((new StringBuilder(String.valueOf(i))).append(" is not valid, use 1/2/3").toString());

        case 1: // '\001'
            mNick1 = s;
            return;

        case 2: // '\002'
            mNick2 = s;
            return;

        case 3: // '\003'
            mNick3 = s;
            break;
        }
    }

    public void setNickServDetails(String s)
    {
        mNickservPassword = s;
    }

    public ServerProfile setOtherDetails(String s, String s1, String s2, boolean flag)
    {
        mAutojoin = s;
        mAutorun = s1;
        mCharset = s2;
        mLogs = flag;
        return this;
    }

    public void setRealname(String s)
    {
        mRealname = s;
    }

    public void setSASLDetails(String s, String s1)
    {
        mSASL_Username = s;
        mSASL_Password = s1;
    }

    public ServerProfile setServerDetails(String s, String s1, int i, boolean flag)
    {
        mLabel = s;
        mAddress = s1;
        mPort = i;
        mSSL = flag;
        return this;
    }

    public void setServerPassword(String s)
    {
        mServerPassword = s;
    }

    public ServerProfile setUserDetails(String s, String s1, String s2, String s3, String s4)
    {
        mNick1 = s;
        mNick2 = s1;
        mNick3 = s2;
        mUsername = s3;
        mRealname = s4;
        return this;
    }

    public void setUsername(String s)
    {
        mUsername = s;
    }

    public ServerProfile setUsesEncryption(boolean flag)
    {
        mRequiresEncryption = flag;
        return this;
    }

    public String toString()
    {
        return super.toString();
    }

    public boolean usesEncryption()
    {
        return mRequiresEncryption;
    }

    public boolean usesSSL()
    {
        return mSSL;
    }

    public void validateSelf()
    {
        int i = mPort;
        if (i > 65535 || i <= 0)
        {
            int j;
            if (mSSL)
            {
                j = 7000;
            } else
            {
                j = 6667;
            }
            mPort = j;
        }
        if (mAutojoin == null)
        {
            mAutojoin = "";
        }
        if (mAutorun == null)
        {
            mAutorun = "";
        }
        if (mServerPassword == null)
        {
            mServerPassword = "";
        }
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        boolean flag = true;
        parcel.writeString(mLabel);
        parcel.writeString(mAddress);
        parcel.writeInt(mPort);
        if (mSSL)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        parcel.writeInt(i);
        parcel.writeString(mNick1);
        parcel.writeString(mNick2);
        parcel.writeString(mNick3);
        parcel.writeString(mUsername);
        parcel.writeString(mRealname);
        parcel.writeString(mAutojoin);
        parcel.writeString(mAutorun);
        parcel.writeInt(mAuthModes);
        if (mAuthModes > 0)
        {
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_PASSWORD))
            {
                parcel.writeString(mServerPassword);
            }
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_NICKSERV))
            {
                parcel.writeString(mNickservPassword);
            }
            if (Utils.isBitSet(mAuthModes, AuthenticationModes.MODE_SASL))
            {
                parcel.writeString(mSASL_Username);
                parcel.writeString(mSASL_Password);
            }
        }
        parcel.writeString(mCharset);
        if (mLogs)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        parcel.writeInt(i);
        if (mRequiresEncryption)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        parcel.writeInt(i);
        if (mIsDecrypted)
        {
            i = ((flag) ? 1 : 0);
        } else
        {
            i = 0;
        }
        parcel.writeInt(i);
    }

}
