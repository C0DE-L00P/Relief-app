package com.stem.relief;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

public class broadcastNR extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Close Notification Button Receiver

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(1);
    }
}
