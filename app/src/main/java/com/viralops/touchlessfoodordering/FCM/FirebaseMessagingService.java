package com.viralops.touchlessfoodordering.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    SessionManager sessionManager;
    public static Ringtone r;


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        SessionManagerFCM pref = new SessionManagerFCM(getApplicationContext());
        pref.setToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);
        updateMyActivity(this,"df");
        String target="";
        String message="";
        sessionManager=new SessionManager(this);
        try {
            message=object.getString("message");
            target=object.getString("target");
        } catch (JSONException e) {
            e.printStackTrace();
        }
         sessionManager.setDesignStyle(target);



           sendNotification(message,target);

    }
    private void sendNotification(String messageBody,String target) {

        Bitmap logo = null;
        try {

            Drawable myDrawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
            logo = ((BitmapDrawable) myDrawable).getBitmap();
        } catch (Exception ignored) {
        }
        PendingIntent pendingIntent;


            Intent intent = new Intent(FirebaseMessagingService.this, MainActivity.class);
            intent.putExtra("key", target);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        try {
            if (target.equals("ird")) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                if(r.isPlaying()){
                    r.stop();
                }
                else{
                    r.play();

                }
                Handler handler = new Handler(Looper.getMainLooper());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Run your task here
                        if (r.isPlaying()) {
                            r.stop();
                        }
                    }
                }, 10000);
            } else {
                int notifyID = 1;
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "VServe";// The user-visible name of the channel.
                Notification notification;
                int importance = NotificationManager.IMPORTANCE_HIGH;
                // Create a notification and set the notification channel.
                //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher).copy(Bitmap.Config.ARGB_8888, true);
                NotificationChannel mChannel = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

                    notification = new Notification.Builder(FirebaseMessagingService.this)
                            .setContentTitle("VServe ++ - New Request Found upar!!!")
                            .setContentText(messageBody)
                            .setLargeIcon(logo)
                            .setSmallIcon(R.mipmap.ic_launcher)

                            .setAutoCancel(true)
                            .setChannelId(CHANNEL_ID).setContentIntent(pendingIntent).build();
                }
                else
                {
                    notification = new Notification.Builder(FirebaseMessagingService.this)
                            .setContentTitle("VServe ++ - New Request Found !!!")
                            .setContentText(messageBody)
                            .setLargeIcon(logo)

                            .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                            .setContentIntent(pendingIntent).build();

                }
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mNotificationManager.createNotificationChannel(mChannel);
                    mNotificationManager.notify(notifyID , notification);
                    mNotificationManager.cancel(notifyID);
                    // startForeground(1,notification);

                }
                else{
                    mNotificationManager.notify(notifyID, notification);
                    mNotificationManager.cancel(notifyID);
                    //startForeground(1,notification);

                    //   mNotificationManager.cancelAll()
                }

            }


        } catch (Exception e) {

       /* Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "VServe";// The user-visible name of the channel.
        Notification notification;
        int importance = NotificationManager.IMPORTANCE_HIGH;
       // Create a notification and set the notification channel.
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher).copy(Bitmap.Config.ARGB_8888, true);
        NotificationChannel mChannel = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notification = new Notification.Builder(FirebaseMessagingService.this)
                    .setContentTitle("VServe ++ - New Request Found upar!!!")
                    .setContentText(messageBody)
                    .setLargeIcon(logo)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(uri)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID).setContentIntent(pendingIntent).build();
        }
        else
        {
            notification = new Notification.Builder(FirebaseMessagingService.this)
                    .setContentTitle("VServe ++ - New Request Found !!!")
                    .setContentText(messageBody)
                    .setLargeIcon(logo)
                    .setSound(uri)
                    .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                    .setContentIntent(pendingIntent).build();

        }*/


        }


/*

        int notifyID = 1;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "VServe";// The user-visible name of the channel.
        Notification notification;
        int importance = NotificationManager.IMPORTANCE_HIGH;
       // Create a notification and set the notification channel.
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher).copy(Bitmap.Config.ARGB_8888, true);
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notification = new Notification.Builder(FirebaseMessagingService.this)
                    .setContentTitle("VServe ++ - New Request Found upar!!!")
                    .setContentText(messageBody)
                    .setLargeIcon(logo)
                    .setSmallIcon(R.mipmap.ic_launcher)

                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID).setContentIntent(pendingIntent).build();
        }
        else
        {
            notification = new Notification.Builder(FirebaseMessagingService.this)
                    .setContentTitle("VServe ++ - New Request Found !!!")
                    .setContentText(messageBody)
                    .setLargeIcon(logo)

                    .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                    .setContentIntent(pendingIntent).build();

        }
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(notifyID , notification);
            mNotificationManager.cancel(notifyID);
            // startForeground(1,notification);

        }
        else{
            mNotificationManager.notify(notifyID, notification);
            mNotificationManager.cancel(notifyID);
            //startForeground(1,notification);

            //   mNotificationManager.cancelAll()
        }
*/

    }




    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("com.viralops.touchlessfoodordering");
        context.sendBroadcast(intent);
    }
}
