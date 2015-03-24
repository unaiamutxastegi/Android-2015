package com.unaiamutxastegi.intents.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {

    private final String RECEIVER ="RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "TelephonyReceiver onReceive()");
        Log.d(RECEIVER, intent.getAction());
        /**switch (intent.getAction()){
            case Intent.PHONE_CHANGED:
                Log.d(RECEIVER, "TelephonyReceiver onReceive()");
                Log.d(RECEIVER, intent.getAction());
                break;

            case ConnectivityManager.CONNECTIVITY_ACTION:
                Log.d(RECEIVER, "ConectionReceiver onReceive()");
                Log.d(RECEIVER, intent.getAction());
                break;
        }**/
    }
}
