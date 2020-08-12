package com.viralops.touchlessfoodordering.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viralops.touchlessfoodordering.R;


public class Alert extends Activity {
MediaPlayer mp;
int reso= R.raw.twin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp=MediaPlayer.create(getApplicationContext(),reso);
        mp.start();

        final Dialog dialog = new Dialog(this);
        // Include dialog.xml file

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_layout);
        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

        dialog.getWindow().setLayout(width1, height1);

        dialog.setCancelable(false);
        // Set dialog title
        dialog.setTitle("");
        dialog.show();
        TextView roomno=dialog.findViewById(R.id.room);
        TextView picktime=dialog.findViewById(R.id.time);
        LinearLayout bottom=dialog.findViewById(R.id.bottom);
        roomno.setText(getIntent().getExtras().getString("title"));
        picktime.setText(getIntent().getExtras().getString("time"));
        final int id=getIntent().getExtras().getInt("alarmid");
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Alert.this.finish();

            }
        });
      /*  // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        // add a button


//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        String msg = "Reminder :" + getIntent().getExtras().getString("title");
//        builder.setMessage(msg).setCancelable(
//                false).setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                        Alert.this.finish();
//                    }
//                });
        final AlertDialog alert = builder.create();
        alert.setCancelable(false);
        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
        alert.getWindow().setGravity(Gravity.CENTER_VERTICAL);

        alert.getWindow().setLayout(width1, height1);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alert.show();
        dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
                Alert.this.finish();
            }
        });*/
    }
    @Override

    public void onDestroy() {

        super.onDestroy();

        mp.release();

    }

}
