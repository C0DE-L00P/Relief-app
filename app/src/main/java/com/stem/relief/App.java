package com.stem.relief;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_1_ID = "channel 1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"Goals channel", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Sticky Notify Goals");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
