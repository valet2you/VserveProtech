package com.viralops.touchlessfoodordering.Acitities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Mobile.AYS.AYSMain_Mobile;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Restaurant.RestaurantMain;
import com.viralops.touchlessfoodordering.Mobile.Spa.Spa_Mobile;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Tablet.AYS.AYSMainActivity;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Laundry.LaundryMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Restaurant.Resturant_Tablet_MainActivity;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;
import com.viralops.touchlessfoodordering.Tablet.combine.MainFragmentcombine;

public class Splash_Screen extends AppCompatActivity {
    private TextView v;
    private TextView serve;
    private TextView power;
    private TextView by;
    private TextView viral;
    private SessionManager sessionManager;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v.setLetterSpacing((float) 0.6);
            serve.setLetterSpacing((float) 0.7);
            power.setLetterSpacing((float) 0.5);
            by.setLetterSpacing((float) 0.5);
            viral.setLetterSpacing((float) 0.5);
        }
        sessionManager=new SessionManager(Splash_Screen.this);
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
                                }else if(sessionManager.getNAME().equals("ird_manager")) {
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

    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
