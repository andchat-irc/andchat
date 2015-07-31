// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.util;


public class DebugUtils
{

    public static void buildShortClassTag(Object obj, StringBuilder stringbuilder)
    {
        String s;
label0:
        {
            if (obj == null)
            {
                stringbuilder.append("null");
                return;
            }
            String s1 = obj.getClass().getSimpleName();
            if (s1 != null)
            {
                s = s1;
                if (s1.length() > 0)
                {
                    break label0;
                }
            }
            s1 = obj.getClass().getName();
            int i = s1.lastIndexOf('.');
            s = s1;
            if (i > 0)
            {
                s = s1.substring(i + 1);
            }
        }
        stringbuilder.append(s);
        stringbuilder.append('{');
        stringbuilder.append(Integer.toHexString(System.identityHashCode(obj)));
    }
}
