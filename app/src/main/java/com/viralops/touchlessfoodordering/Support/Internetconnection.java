package com.viralops.touchlessfoodordering.Support;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Mobile.AYS.AYSMain_Mobile;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Restaurant.RestaurantMain;
import com.viralops.touchlessfoodordering.Mobile.Spa.Spa_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Supervisor.Supervisor_mainactivity;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Tablet.AYS.AYSMainActivity;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Laundry.LaundryMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Restaurant.Resturant_Tablet_MainActivity;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;


public class Internetconnection extends AppCompatActivity {

TextView home;
     String value="";
     SessionManager sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isTablet(Internetconnection.this)){
        setRequestedOrientation(Build.VERSION.SDK_INT < 9 ?
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            setContentView(R.layout.internet_page);
        }
        else{
            setRequestedOrientation(Build.VERSION.SDK_INT < 9 ?
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

            setContentView(R.layout.internet_page1);

        }

       sessionManager=new SessionManager(Internetconnection.this);
        //final String value=getIntent().getStringExtra("openvalue");

        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTablet(Internetconnection.this)){

                    if(sessionManager.getNAME().equals("ird_manager")){

                        Intent intent = new Intent(Internetconnection.this, IRdMainActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(sessionManager.getNAME().equals("restaurant_manager")){

                        Intent intent = new Intent(Internetconnection.this, Resturant_Tablet_MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else if(sessionManager.getNAME().equals("hotel_admin")){

                        Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else if(sessionManager.getNAME().equals("laundry_manager")){


                        Intent intent = new Intent(Internetconnection.this, LaundryMainActivity.class);
                        startActivity(intent);
                        finish();

                    } else if(sessionManager.getNAME().equals("spa_manager")){

                        Intent intent = new Intent(Internetconnection.this, SpaMainActivitytablet.class);
                        startActivity(intent);
                        finish();

                    } else if(sessionManager.getNAME().equals("connect_manager")){

                        Intent intent = new Intent(Internetconnection.this, AYSMainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    else if(sessionManager.getNAME().equals("mini_bar_manager")){

                        //  Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                        //  startActivity(intent);
                    }


                }
                else{
                    if(sessionManager.getNAME().equals("restaurant_manager")){

                        Intent intent = new Intent(Internetconnection.this, RestaurantMain.class);
                        startActivity(intent);
                        finish();

                    } else if(sessionManager.getNAME().equals("global_supervisor")){

                        Intent intent = new Intent(Internetconnection.this, Supervisor_mainactivity.class);
                        startActivity(intent);
                        finish();

                    }else if(sessionManager.getNAME().equals("ird_manager")) {

                        Intent intent = new Intent(Internetconnection.this, MainActivity_Mobile.class);
                        startActivity(intent);
                        finish();

                    }
                    else if(sessionManager.getNAME().equals("hotel_admin")){

                        //      Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                        //    startActivity(intent);
                    } else if(sessionManager.getNAME().equals("laundry_manager")){

                        Intent intent = new Intent(Internetconnection.this, Laundry_Main_Mobile.class);
                        startActivity(intent);
                        finish();

                    } else if(sessionManager.getNAME().equals("spa_manager")){

                        Intent intent = new Intent(Internetconnection.this, Spa_Mobile.class);
                        startActivity(intent);
                        finish();

                    } else if(sessionManager.getNAME().equals("connect_manager")){

                        Intent intent = new Intent(Internetconnection.this, AYSMain_Mobile.class);
                        startActivity(intent);
                        finish();

                    }

                    else if(sessionManager.getNAME().equals("mini_bar_manager")){

                        //  Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                        //  startActivity(intent);
                    }

                }


            }
        });
      /*  IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
*/
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        if(isTablet(Internetconnection.this)){

            if(sessionManager.getNAME().equals("ird_manager")){

                Intent intent = new Intent(Internetconnection.this, IRdMainActivity.class);
                startActivity(intent);
                finish();
            }else if(sessionManager.getNAME().equals("restaurant_manager")){

                Intent intent = new Intent(Internetconnection.this, Resturant_Tablet_MainActivity.class);
                startActivity(intent);
                finish();

            }
            else if(sessionManager.getNAME().equals("hotel_admin")){

                Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else if(sessionManager.getNAME().equals("laundry_manager")){


                Intent intent = new Intent(Internetconnection.this, LaundryMainActivity.class);
                startActivity(intent);
                finish();

            } else if(sessionManager.getNAME().equals("spa_manager")){

                Intent intent = new Intent(Internetconnection.this, SpaMainActivitytablet.class);
                startActivity(intent);
                finish();

            } else if(sessionManager.getNAME().equals("connect_manager")){

                Intent intent = new Intent(Internetconnection.this, AYSMainActivity.class);
                startActivity(intent);
                finish();

            }

            else if(sessionManager.getNAME().equals("mini_bar_manager")){

                //  Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                //  startActivity(intent);
            }


        }
        else{
            if(sessionManager.getNAME().equals("restaurant_manager")){

                Intent intent = new Intent(Internetconnection.this, RestaurantMain.class);
                startActivity(intent);
                finish();

            } else if(sessionManager.getNAME().equals("global_supervisor")){

                Intent intent = new Intent(Internetconnection.this, Supervisor_mainactivity.class);
                startActivity(intent);
                finish();

            }else if(sessionManager.getNAME().equals("ird_manager")) {

                Intent intent = new Intent(Internetconnection.this, MainActivity_Mobile.class);
                startActivity(intent);
                finish();

            }
            else if(sessionManager.getNAME().equals("hotel_admin")){

                //      Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                //    startActivity(intent);
            } else if(sessionManager.getNAME().equals("laundry_manager")){

                Intent intent = new Intent(Internetconnection.this, Laundry_Main_Mobile.class);
                startActivity(intent);
                finish();

            } else if(sessionManager.getNAME().equals("spa_manager")){

                Intent intent = new Intent(Internetconnection.this, Spa_Mobile.class);
                startActivity(intent);
                finish();

            } else if(sessionManager.getNAME().equals("connect_manager")){

                Intent intent = new Intent(Internetconnection.this, AYSMain_Mobile.class);
                startActivity(intent);
                finish();

            }

            else if(sessionManager.getNAME().equals("mini_bar_manager")){

                //  Intent intent = new Intent(Internetconnection.this, MainActivity.class);
                //  startActivity(intent);
            }

        }
    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
