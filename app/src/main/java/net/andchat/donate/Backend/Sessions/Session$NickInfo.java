// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.andchat.donate.Backend.Sessions;


// Referenced classes of package net.andchat.donate.Backend.Sessions:
//            Session

public static final class 
{

    public int colour;
    public String hostname;
    public String ident;
    public String name;
    public int status;

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("Name = ").append(name).append(", ").append("host = ").append(hostname).append(", ").append(" status = ").append(status);
        return stringbuilder.toString();
    }

    protected ()
    {
    }
}
