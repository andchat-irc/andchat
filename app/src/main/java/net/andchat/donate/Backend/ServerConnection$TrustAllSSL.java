// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend;

import java.security.Principal;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.X509TrustManager;
import net.andchat.donate.Misc.Colours;
import net.andchat.donate.Misc.Utils;

// Referenced classes of package net.andchat.donate.Backend:
//            ServerConnection

private final class <init>
    implements X509TrustManager
{

    final ServerConnection this$0;

    public void checkClientTrusted(X509Certificate ax509certificate[], String s)
    {
    }

    public void checkServerTrusted(X509Certificate ax509certificate[], String s)
    {
        s = ax509certificate[0];
        ax509certificate = new StringBuilder(300);
        String s1 = ServerConnection.access$9(ServerConnection.this, 0x7f0a0044);
        ax509certificate.append(s1);
        ax509certificate.append(ServerConnection.access$0(ServerConnection.this, 0x7f0a0045, new Object[] {
            s.getSubjectDN().toString(), s.getIssuerDN().toString(), s.getNotBefore().toGMTString(), s.getNotAfter().toGMTString(), s.getSigAlgName().toString()
        }));
        boolean flag = ServerConnection.access$8();
        int i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002d);
        android.text.SpannableStringBuilder spannablestringbuilder = Utils.createMessage(ServerConnection.access$8(), ax509certificate);
        Utils.addColour(flag, spannablestringbuilder, i, 0, s1.length());
        sendMessage("Status", spannablestringbuilder, 1);
        try
        {
            s.checkValidity();
            return;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            ax509certificate.setLength(0);
            ax509certificate.append(ServerConnection.access$9(ServerConnection.this, 0x7f0a0046));
            i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002e);
            s = Utils.createMessage(ServerConnection.access$8(), ax509certificate);
            Utils.addColour(flag, s, i, 0, ax509certificate.length());
            sendMessage("Status", s, 1);
            return;
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            ax509certificate.setLength(0);
        }
        ax509certificate.append(ServerConnection.access$9(ServerConnection.this, 0x7f0a0047));
        i = ServerConnection.access$1(ServerConnection.this).getColourForEvent(0x7f0b002f);
        s = Utils.createMessage(ServerConnection.access$8(), ax509certificate);
        Utils.addColour(flag, s, i, 0, ax509certificate.length());
        sendMessage("Status", s, 1);
    }

    public X509Certificate[] getAcceptedIssuers()
    {
        return null;
    }

    private ()
    {
        this$0 = ServerConnection.this;
        super();
    }

    this._cls0(this._cls0 _pcls0)
    {
        this();
    }
}
