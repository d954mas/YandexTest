package com.d954mas.android.yandextest.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.d954mas.android.yandextest.R;

import static android.content.Context.NOTIFICATION_SERVICE;


public class HeadsetReceiver extends BroadcastReceiver {
    private static final String TAG = "HeadsetReceiver";
    private static final String BUTTON_CLICK_ACTION = "BUTTON_CLICK_ACTION";
    private static final String MUSIC = "MUSIC";
    private static final String RADIO = "RADIO";
    private final Context context;
    private final NotificationManager nm;

    public HeadsetReceiver(Context context) {
        this.context = context;
        nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public void resume() {
        context.registerReceiver(this, new IntentFilter(BUTTON_CLICK_ACTION));
        context.registerReceiver(this, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    public void pause() {
        context.unregisterReceiver(this);
        hide();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_HEADSET_PLUG:
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d(TAG, "Headset is unplugged");
                        hide();
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");
                        show();
                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
                return;
            case BUTTON_CLICK_ACTION:
                String type = intent.getStringExtra("type");
                switch (type) {
                    case MUSIC:
                        Log.d(TAG, "music");
                        openApp("ru.yandex.music");
                        return;
                    case RADIO:
                        Log.d(TAG, "radio");
                        openApp("ru.yandex.radio");
                        return;
                }
                return;
        }
    }

    public void show() {
        createNotification();
    }

    public void hide() {
        nm.cancel(0);
    }

    private void createNotification() {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        Intent musicIntent = new Intent();
        musicIntent.setAction(BUTTON_CLICK_ACTION);
        musicIntent.putExtra("type", MUSIC);
        Intent radioIntent = new Intent();
        radioIntent.setAction(BUTTON_CLICK_ACTION);
        radioIntent.putExtra("type", RADIO);

        PendingIntent music = PendingIntent.getBroadcast(context, 1, musicIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent radio = PendingIntent.getBroadcast(context, 2, radioIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        contentView.setOnClickPendingIntent(R.id.music, music);
        contentView.setOnClickPendingIntent(R.id.radio, radio);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setContent(contentView);

        Notification notification = builder.build();
        nm.notify(0, notification);
    }

    private void openApp(String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        } else {
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
        }

    }
}
