package com.railways.ecsoket.Utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class EcSocketUtils {

    private static NetworkInfo getNetworkInfo(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(final Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
}
