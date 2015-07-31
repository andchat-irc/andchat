// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import net.andchat.donate.IRCApp;
import org.xmlpull.v1.XmlSerializer;

public class Crypt
{

    private static final byte HEXCHARS[] = {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 
        97, 98, 99, 100, 101, 102
    };
    private boolean decrypted;
    private final Cipher mCipher;
    private Context mCtx;
    private final SharedPreferences mPrefs;
    private char master[];
    private char pass[];
    private final byte salt[];

    public Crypt(IRCApp ircapp)
    {
        mCtx = ircapp;
        mPrefs = mCtx.getSharedPreferences("crypt", 0);
        ircapp = getSalt();
        Cipher cipher;
        if (ircapp.length != 0)
        {
            salt = ircapp;
        } else
        {
            ircapp = new SecureRandom();
            salt = new byte[15];
            ircapp.nextBytes(salt);
            saveSalt(salt);
        }
        ircapp = null;
        cipher = Cipher.getInstance("PBEWithSHA1And256BitAES-CBC-BC", "BC");
        ircapp = cipher;
_L2:
        mCipher = ircapp;
        return;
        Object obj;
        obj;
        ((NoSuchAlgorithmException) (obj)).printStackTrace();
        continue; /* Loop/switch isn't completed */
        obj;
        ((NoSuchProviderException) (obj)).printStackTrace();
        continue; /* Loop/switch isn't completed */
        obj;
        ((NoSuchPaddingException) (obj)).printStackTrace();
        if (true) goto _L2; else goto _L1
_L1:
    }

    private String bytes2Hex(byte abyte0[])
    {
        int j = abyte0.length;
        StringBuilder stringbuilder = new StringBuilder(j * 2);
        int i = 0;
        do
        {
            if (i >= j)
            {
                return stringbuilder.toString();
            }
            int k = abyte0[i] & 0xff;
            stringbuilder.append((char)HEXCHARS[k >> 4]);
            stringbuilder.append((char)HEXCHARS[k & 0xf]);
            i++;
        } while (true);
    }

    private String decrypt(String s, char ac[])
    {
        if (s == null)
        {
            return null;
        }
        try
        {
            s = hex2Bytes(s);
            Object obj = new PBEKeySpec(ac);
            ac = new PBEParameterSpec(salt, 10);
            obj = SecretKeyFactory.getInstance("PBEWithSHA1And256BitAES-CBC-BC", "BC").generateSecret(((java.security.spec.KeySpec) (obj)));
            mCipher.init(2, ((java.security.Key) (obj)), ac);
            s = new String(mCipher.doFinal(s));
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            s.printStackTrace();
            return null;
        }
        return s;
    }

    private String encrypt(String s, char ac[])
    {
        Object obj;
        obj = new PBEKeySpec(ac);
        ac = new PBEParameterSpec(salt, 10);
        obj = SecretKeyFactory.getInstance("PBEWithSHA1And256BitAES-CBC-BC", "BC").generateSecret(((java.security.spec.KeySpec) (obj)));
        mCipher.init(1, ((java.security.Key) (obj)), ac);
        s = bytes2Hex(mCipher.doFinal(s.getBytes()));
        return s;
        s;
        s.printStackTrace();
_L2:
        return null;
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        continue; /* Loop/switch isn't completed */
        s;
        s.printStackTrace();
        if (true) goto _L2; else goto _L1
_L1:
    }

    private String getMaster()
    {
        if (master == null)
        {
            master = mPrefs.getString("master", "").toCharArray();
        }
        return new String(master);
    }

    private String getRawSalt()
    {
        return mPrefs.getString("salt", "");
    }

    private byte[] getSalt()
    {
        String s = getRawSalt();
        if (s != null && s.length() > 0)
        {
            return hex2Bytes(s);
        } else
        {
            return new byte[0];
        }
    }

    private byte[] hex2Bytes(String s)
    {
        byte abyte0[] = new byte[s.length() / 2];
        int j = abyte0.length;
        int i = 0;
        do
        {
            if (i >= j)
            {
                return abyte0;
            }
            int k = i * 2;
            abyte0[i] = (byte)Integer.parseInt(s.substring(k, k + 2), 16);
            i++;
        } while (true);
    }

    private void saveSalt(String s)
    {
        mPrefs.edit().putString("salt", s).commit();
    }

    private void saveSalt(byte abyte0[])
    {
        saveSalt(bytes2Hex(abyte0));
    }

    private void storeMasterInternal(String s)
    {
        mPrefs.edit().putString("master", s).commit();
    }

    public void clear()
    {
        pass = null;
        master = null;
        mCtx = null;
    }

    public boolean correctPass()
    {
        return pass != null && pass.length > 0 && decrypted;
    }

    public String decrypt(String s)
    {
        if (!correctPass())
        {
            throw new IllegalArgumentException("Must decrypt master before calling decrypt");
        } else
        {
            return decrypt(s, pass);
        }
    }

    public boolean decryptMaster(String s)
    {
        String s1 = decrypt(getMaster(), s.toCharArray());
        boolean flag;
        if (s1 == null)
        {
            s = new char[0];
        } else
        {
            s = s1.toCharArray();
        }
        pass = s;
        if (s1 != null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        decrypted = flag;
        return s1 != null;
    }

    public String encrypt(String s)
    {
        if (!correctPass())
        {
            throw new IllegalArgumentException("Must decrypt master before calling encrypt");
        } else
        {
            return encrypt(s, pass);
        }
    }

    public void fromXml(String s, String s1)
    {
        storeMasterInternal(s);
        saveSalt(s1);
    }

    public boolean storeMaster(String s)
    {
        s = encrypt(s, s.toCharArray());
        if (s != null)
        {
            storeMasterInternal(s);
            return true;
        } else
        {
            return false;
        }
    }

    public void toXml(XmlSerializer xmlserializer)
        throws IllegalArgumentException, IllegalStateException, IOException
    {
        String s = getMaster();
        String s1 = getRawSalt();
        xmlserializer.startTag(null, "crypt");
        xmlserializer.attribute(null, "m", s);
        xmlserializer.attribute(null, "s", s1);
        xmlserializer.endTag(null, "crypt");
    }

}
