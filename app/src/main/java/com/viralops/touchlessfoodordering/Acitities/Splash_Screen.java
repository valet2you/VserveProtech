package com.viralops.touchlessfoodordering.Acitities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Mobile.AYS.AYSMain_Mobile;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Restaurant.RestaurantMain;
import com.viralops.touchlessfoodordering.Mobile.Spa.Spa_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Supervisor.Supervisor_mainactivity;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Tablet.AYS.AYSMainActivity;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Laundry.LaundryMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Restaurant.Resturant_Tablet_MainActivity;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;
import com.viralops.touchlessfoodordering.Tablet.combine.MainFragmentcombine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Splash_Screen extends AppCompatActivity {
    private TextView v;
    private TextView serve;
    private TextView power;
    private TextView by;
    private TextView viral;
    private SessionManager sessionManager;
    String currentVersion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);
        v=findViewById(R.id.v);
        serve=findViewById(R.id.serve);
        power=findViewById(R.id.power);
        by=findViewById(R.id.by);
        viral=findViewById(R.id.viral);
        try {
            currentVersion = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

            Log.e("Current Version","::"+currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
         if(Network.isNetworkAvailable(Splash_Screen.this)){
             new GetVersionCode().execute();

         }else if(Network.isNetworkAvailable2(Splash_Screen.this)){
             new GetVersionCode().execute();

         }
         else{
             Toast.makeText(Splash_Screen.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
         }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v.setLetterSpacing((float) 0.6);
            serve.setLetterSpacing((float) 0.7);
            power.setLetterSpacing((float) 0.5);
            by.setLetterSpacing((float) 0.5);
            viral.setLetterSpacing((float) 0.5);
        }
        sessionManager=new SessionManager(Splash_Screen.this);


    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (onlineVersion.equals(currentVersion)) {
                    Thread logoTimer = new Thread() {
                        public void run(){
                            try{
                                int logoTimer = 0;
                                while(logoTimer < 1000){
                                    sleep(100);
                                    logoTimer = logoTimer +100;
                                };
                                try {

                                    if (sessionManager.getPorchName().equals("")) {
                                        startActivity(new Intent(Splash_Screen.this, Login_Activity.class));
                                        finish();
                                    } else {
                                        if(isTablet(Splash_Screen.this)){
                                            if(sessionManager.getNAME().equals("ird_manager")){
                                                Intent intent=new Intent(Splash_Screen.this, IRdMainActivity.class);
                                                intent.putExtra("openvalue","dashboard");
                                                startActivity(intent);
                                                finish();
                                            }  else if(sessionManager.getNAME().equals("hotel_admin")){
                                                Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                                                intent.putExtra("openvalue","dashboard");
                                                startActivity(intent);
                                                finish();

                                            }

                                            else if(sessionManager.getNAME().equals("restaurant_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, Resturant_Tablet_MainActivity.class);
                                                intent.putExtra("openvalue", "dashboard");
                                                startActivity(intent);
                                                finish();
                                                //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                                //  startActivity(intent);
                                            } else if(sessionManager.getNAME().equals("laundry_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, LaundryMainActivity.class);
                                                intent.putExtra("openvalue","dashboard");

                                                startActivity(intent);
                                                finish();
                                            } else if(sessionManager.getNAME().equals("spa_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, SpaMainActivitytablet.class);
                                                intent.putExtra("openvalue","dashboard");

                                                startActivity(intent);
                                                finish();
                                            } else if(sessionManager.getNAME().equals("connect_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, AYSMainActivity.class);
                                                intent.putExtra("openvalue","dashboard");

                                                startActivity(intent);
                                                finish();
                                            }

                                            else if(sessionManager.getNAME().equals("mini_bar_manager")){
                                                // Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                                                //  startActivity(intent);
                                            }

                                        }
                                        else{
                                            if(sessionManager.getNAME().equals("restaurant_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, RestaurantMain.class);
                                                intent.putExtra("openvalue", "dashboard");
                                                startActivity(intent);
                                                finish();
                                            }
                                            else if(sessionManager.getNAME().equals("global_supervisor")){
                                                Intent intent = new Intent(Splash_Screen.this, Supervisor_mainactivity.class);
                                                intent.putExtra("openvalue", "dashboard");
                                                startActivity(intent);
                                                finish();

                                            }

                                            else if(sessionManager.getNAME().equals("global_supervisor")){
                                                Intent intent = new Intent(Splash_Screen.this, Supervisor_mainactivity.class);
                                                intent.putExtra("openvalue", "dashboard");
                                                startActivity(intent);
                                                finish();
                                            }

                                            else if(sessionManager.getNAME().equals("ird_manager")) {
                                                Intent intent = new Intent(Splash_Screen.this, MainActivity_Mobile.class);
                                                intent.putExtra("openvalue", "dashboard");
                                                startActivity(intent);
                                                finish();
                                            }
                                            //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                            //  startActivity(intent);
                                            else if(sessionManager.getNAME().equals("laundry_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, Laundry_Main_Mobile.class);
                                                intent.putExtra("openvalue","dashboard");

                                                startActivity(intent);
                                                finish();
                                            } else if(sessionManager.getNAME().equals("spa_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, Spa_Mobile.class);
                                                intent.putExtra("openvalue","dashboard");

                                                startActivity(intent);
                                                finish();
                                            } else if(sessionManager.getNAME().equals("connect_manager")){
                                                Intent intent = new Intent(Splash_Screen.this, AYSMain_Mobile.class);
                                                intent.putExtra("openvalue","dashboard");

                                                startActivity(intent);
                                                finish();
                                            }

                                            else if(sessionManager.getNAME().equals("mini_bar_manager")){
                                                //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                                //  startActivity(intent);
                                            }
                                        }

                                    }
                                }
                                catch (Exception e){
                                    startActivity(new Intent(Splash_Screen.this, Login_Activity.class));
                                    finish();
                                }
                            }

                            catch (InterruptedException e) {
                                //  Auto-generated catc3h block
                                e.printStackTrace();
                            }

                            finally{
                                finish();
                            }
                        }
                    };

                    logoTimer.start();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Splash_Screen.this).create();
                    alertDialog.setTitle("UPDATE");
                    alertDialog.setIcon(R.mipmap.launcherbluebig);
                    alertDialog.setMessage("A new version of VServe Pro-Tech is available.\n\nPlease update for a seamless experience.");

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                            }
                        }
                    });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }
    }
}
