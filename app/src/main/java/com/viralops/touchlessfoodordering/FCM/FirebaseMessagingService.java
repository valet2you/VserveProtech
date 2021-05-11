package com.viralops.touchlessfoodordering.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.google.firebase.messaging.RemoteMessage;

import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Mobile.AYS.AYSMain_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Booking.Booking_Activity;
import com.viralops.touchlessfoodordering.Mobile.Booking.Booking_Activity_new;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Restaurant.RestaurantMain;
import com.viralops.touchlessfoodordering.Mobile.Spa.Spa_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Supervisor.Supervisor_mainactivity;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.AYS.AYSMainActivity;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Laundry.LaundryMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Restaurant.Resturant_Tablet_MainActivity;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;
import com.viralops.touchlessfoodordering.Tablet.combine.LaundryOrderHistorycobine;
import com.viralops.touchlessfoodordering.Tablet.combine.Minibar_fragment_combine;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    SessionManager sessionManager;
    public static Ringtone r;
    String des="";

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
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
        updateMyActivity(this, "df");
        String target = "";
        String message = "";
        String picupslot="";
        String booking_service_type="";
        String booking_service_name="";
        sessionManager = new SessionManager(this);
        try {
            message = object.getString("message");
            target = object.getString("target");
            if(object.has("pickup_slot")){
                picupslot=object.getString("pickup_slot");
            }
            else{
                picupslot="";
            }

          if(object.has("booking_service_type")){
              booking_service_type=object.getString("booking_service_type");
            }
            else{
              booking_service_type="";
            }

            if(object.has("booking_service_name")){
              booking_service_name=object.getString("booking_service_name");
            }
            else{
                booking_service_name="";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (message.equals("New delayed order for acceptance")) {
            sessionManager.setDesignStyle(message);

        } else if (message.equals("New delayed order for dispatch")) {
            sessionManager.setDesignStyle(message);
        }else if (message.equals("New Booking Order")) {
            sessionManager.setDesignStyle(booking_service_name);
            sessionManager.setSLOT1(picupslot);        }

    else

    {
        sessionManager.setDesignStyle(target);

    }



           sendNotification(message,target,picupslot,booking_service_type,booking_service_name);

    }
    private void sendNotification(String messageBody,String target,String picupslot,String booking_service_type,String bookingname ) {

        Bitmap logo = null;
        try {

            Drawable myDrawable = ContextCompat.getDrawable(this, R.mipmap.launcherbluebig);
            logo = ((BitmapDrawable) myDrawable).getBitmap();
        } catch (Exception ignored) {
        }
        PendingIntent pendingIntent = null;
        if(sessionManager.getNAME()!=null) {

            if (isTablet(FirebaseMessagingService.this)) {
                if (sessionManager.getNAME().equals("ird_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, IRdMainActivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                }
                if (sessionManager.getNAME().equals("restaurant_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, Resturant_Tablet_MainActivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                }
                else if (sessionManager.getNAME().equals("hotel_admin")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, MainActivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("global_supervisor")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, Supervisor_mainactivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    //  startActivity(intent);
                } else if (sessionManager.getNAME().equals("laundry_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, LaundryMainActivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("spa_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, SpaMainActivitytablet.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                } else if (sessionManager.getNAME().equals("connect_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, AYSMainActivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                } else if (sessionManager.getNAME().equals("mini_bar_manager")) {


                }

            }
            else {
                if (sessionManager.getNAME().equals("restaurant_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, RestaurantMain.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("ird_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, MainActivity_Mobile.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("ird_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, MainActivity_Mobile.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("global_supervisor")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, Supervisor_mainactivity.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    //  startActivity(intent);
                }
                //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                //  startActivity(intent);
                else if (sessionManager.getNAME().equals("laundry_manager")) {

                    Intent intent = new Intent(FirebaseMessagingService.this, Laundry_Main_Mobile.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("spa_manager")) {

                    Intent intent = new Intent(FirebaseMessagingService.this, Spa_Mobile.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


                } else if (sessionManager.getNAME().equals("connect_manager")) {
                    Intent intent = new Intent(FirebaseMessagingService.this, AYSMain_Mobile.class);
                    intent.putExtra("key", target);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                }else if (sessionManager.getNAME().equals("booking_service_manager")) {
                    sessionManager.setDesignStyle(bookingname);
                    sessionManager.setSLOT1(picupslot);
                    Intent intent = new Intent(FirebaseMessagingService.this, Booking_Activity_new.class);
                    intent.putExtra("key", target);
                    intent.putExtra("pickupslaot",picupslot);
                    intent.putExtra("booking",booking_service_type);
                    intent.putExtra("bookingname",bookingname);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getActivity(FirebaseMessagingService.this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                } else if (sessionManager.getNAME().equals("mini_bar_manager")) {
                    //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    //  startActivity(intent);
                }
            }


            try {

                if (target.equals("ird")) {
                    if (isTablet(FirebaseMessagingService.this)) {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        if (r.isPlaying()) {
                            r.stop();
                        } else {
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
                        String message = "";
                        if (target.equals("ird")) {
                            message = "New Food Order Request";
                        } else if (target.equals("laundry")) {
                            message = "New Pickup Request For Laundry";

                        } else if (target.equals("spa")) {
                            message = "New Service Request For Spa";

                        } else if (target.equals("connect")) {
                            message = "New Service Request";

                        } else if (target.equals("global_supervisor")) {
                            if (messageBody.equals("New delayed order for acceptance")) {
                                message = "Alert! Delay in accepting order";
                            } else {
                                message = "Alert! Delay in dispatching order";

                            }
                        } else {
                            message = "New Food Order Request";

                        }
                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, default_notification_channel_id)

                                .setSmallIcon(R.mipmap.launcherbluesmall)
                                .setLargeIcon(logo)
                                .setContentTitle(message).
                                        setContentIntent(pendingIntent)
                       . setAutoCancel(true)

                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                //LED
                                //Sound

                                ;
                       if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBuilder.setSmallIcon(R.drawable.logotransparent);
                          //  mBuilder.setColor(Color.parseColor("#00FFFFFF"));
                        } else {
                            mBuilder.setSmallIcon(R.drawable.launcherbluebig);
                        }
                /*.setTicker("this is a item2")
                .setSubText("due date "+screen )
                .setContentInfo("this is a item1")
                .setContentText("this is a item1" );*/
                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.setBigContentTitle(message);
                        mBuilder.setStyle(bigText);
                        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel notificationChannel = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            notificationChannel = new
                                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            assert mNotificationManager != null;
                            mNotificationManager.createNotificationChannel(notificationChannel);
                        }

                        assert mNotificationManager != null;
                        mNotificationManager.notify((int) System.currentTimeMillis(),
                                mBuilder.build());

                    } else {
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                            String message = "";
                            if (target.equals("ird")) {
                                message = "New Food Order Request";
                            } else if (target.equals("laundry")) {
                                message = "New Pickup Request For Laundry";

                            } else if (target.equals("spa")) {
                                message = "New Service Request For Spa";

                            } else if (target.equals("connect")) {
                                message = "New Service Request";

                            } else if (target.equals("global_supervisor")) {
                                if (messageBody.equals("New delayed order for acceptance")) {
                                    message = "Alert! Delay in accepting order";
                                } else {
                                    message = "Alert! Delay in dispatching order";

                                }
                            } else {
                                message = "New Food Order Request";

                            }
                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, default_notification_channel_id)
                                    .setSmallIcon(R.mipmap.launcherbluesmall)
                                    .setLargeIcon(logo)
                                    .setContentTitle(message).
                                            setAutoCancel(true).
                                            setContentIntent(pendingIntent)

                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                                    //LED
                                   // .setLights(Color.RED, 3000, 3000);
                            //Sound
                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                mBuilder.setSmallIcon(R.drawable.logotransparent);
                                //  mBuilder.setColor(Color.parseColor("#00FFFFFF"));
                            } else {
                                mBuilder.setSmallIcon(R.drawable.launcherbluebig);
                            }
                /*.setTicker("this is a item2")
                .setSubText("due date "+screen )
                .setContentInfo("this is a item1")
                .setContentText("this is a item1" );*/
                            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                            bigText.setBigContentTitle(message);
                            mBuilder.setStyle(bigText);
                            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel notificationChannel = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                notificationChannel = new
                                        NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                                assert mNotificationManager != null;
                                mNotificationManager.createNotificationChannel(notificationChannel);
                            }

                            assert mNotificationManager != null;
                            mNotificationManager.notify((int) System.currentTimeMillis(),
                                    mBuilder.build());

                        }
                        catch (Exception e) {
                            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                            Notification n = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                n = new Notification.Builder(this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setSound(uri)
                                        .setChannelId("channel")
                                         .setAutoCancel(true)
                                        .setAutoCancel(true).build();
                            } else {
                                n = new Notification.Builder(this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setSound(uri)
                                        . setAutoCancel(true)
                                        .setAutoCancel(true).build();
                            }
                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.notify((int) System.currentTimeMillis(), n);

                            e.printStackTrace();
                        }

                    }


                }
                else {
                    if (isTablet(FirebaseMessagingService.this)) {
                        String message = "";
                        if (target.equals("ird")) {
                            message = "New Food Order Request";
                        } 
                        else if (target.equals("laundry")) {
                            message = "New Pickup Request For Laundry";

                        } 
                        else if (target.equals("spa")) {
                            message = "New Service Request For Spa";

                        } 
                        else if (target.equals("connect")) {
                            message = "New Service Request";

                        } 
                        else if (target.equals("global_supervisor")) {
                            if (messageBody.equals("New delayed order for acceptance")) {
                                message = "Alert! Delay in accepting order";
                            } else {
                                message = "Alert! Delay in dispatching order";

                            }
                        } else {
                            message = "New Food Order Request";

                        }
                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, default_notification_channel_id)
                                .setSmallIcon(R.mipmap.launcherbluesmall)
                                .setLargeIcon(logo)
                                .setContentTitle(message).
                                        setContentIntent(pendingIntent)
                                . setAutoCancel(true)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                //LED
                                //Sound
                                .setSound(uri);
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBuilder.setSmallIcon(R.drawable.logotransparent);
                            //  mBuilder.setColor(Color.parseColor("#00FFFFFF"));
                        } else {
                            mBuilder.setSmallIcon(R.drawable.launcherbluebig);
                        }
                /*.setTicker("this is a item2")
                .setSubText("due date "+screen )
                .setContentInfo("this is a item1")
                .setContentText("this is a item1" );*/
                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.setBigContentTitle(message);
                        mBuilder.setStyle(bigText);
                        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel notificationChannel = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            notificationChannel = new
                                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            assert mNotificationManager != null;
                            mNotificationManager.createNotificationChannel(notificationChannel);
                        }

                        assert mNotificationManager != null;
                        mNotificationManager.notify((int) System.currentTimeMillis(),
                                mBuilder.build());


                    }
                    else {
                        String message = "";

                        if (target.equals("ird")) {
                            message = "New Food Order Request";
                        }
                        else if (target.equals("laundry")) {
                            message = "New Pickup Request For Laundry";

                        }
                        else if (target.equals("spa")) {
                            message = "New Service Request For Spa";

                        }
                        else if (target.equals("connect")) {
                            message = "New Service Request";

                        }
                        else if (target.equals("global_supervisor"))
                        {
                            if (messageBody.equals("New delayed order for acceptance")) {
                                message = "Alert! Delay in accepting order";
                            } else {
                                message = "Alert! Delay in dispatching order";

                            }
                        }
                        else if (target.equals("booking")){
                            try {

                                message = "Alert! New " + bookingname + " Request for " + getDate1(picupslot);
                                des="New " + bookingname +" Request for " + getDate1(picupslot);
                            }
                            catch (Exception e){
                                message = "Alert! New Request for "+bookingname;
                                des="New " + bookingname +" Request for " + getDate1(picupslot);

                            }


                        }
                        else {
                            message = "New Food Order Request";


                        }
                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, default_notification_channel_id)
                                .setSmallIcon(R.mipmap.launcherbluesmall)
                                .setLargeIcon(logo)
                                .setContentTitle(message).
                                        setContentText(des).
                                        setContentIntent(pendingIntent)
                                . setAutoCancel(true)
                                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                //LED
                                //Sound
                                .setSound(uri);
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mBuilder.setSmallIcon(R.drawable.logotransparent);
                            //  mBuilder.setColor(Color.parseColor("#00FFFFFF"));
                        } else {
                            mBuilder.setSmallIcon(R.drawable.launcherbluebig);
                        }
                /*.setTicker("this is a item2")
                .setSubText("due date "+screen )
                .setContentInfo("this is a item1")
                .setContentText("this is a item1" );*/
                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.setBigContentTitle(message);
                        bigText.setSummaryText(des);
                        mBuilder.setStyle(bigText);
                        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel notificationChannel = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            notificationChannel = new
                                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            assert mNotificationManager != null;
                            mNotificationManager.createNotificationChannel(notificationChannel);
                        }

                        assert mNotificationManager != null;
                        mNotificationManager.notify((int) System.currentTimeMillis(),
                                mBuilder.build());

                  /*  try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    }
                    catch (Exception e) {
                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        Notification n = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            n = new Notification.Builder(this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setSound(uri)
                                    .setChannelId("channel")
                                    .setAutoCancel(true).build();
                        }
                        else{
                            n = new Notification.Builder(this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setSound(uri)
                                    .setAutoCancel(true).build();
                        }
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify((int) System.currentTimeMillis(), n);

                        e.printStackTrace();
                    }*/
                    }
                }

            } catch (Exception e) {


            }
        }



    }




    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("com.viralops.touchlessfoodordering");
        context.sendBroadcast(intent);
    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
    private String getDate1(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter.format(date);
        return dateStr;
    }
}
