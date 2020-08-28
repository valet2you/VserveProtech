package com.viralops.touchlessfoodordering.Mobile.AYS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Connect_Header;
import com.viralops.touchlessfoodordering.Model.Logout;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;


import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AYSMain_Mobile extends AppCompatActivity implements View.OnClickListener{
    public static Animation shake;

    public static LinearLayout homelayout,shiftlayout,orderslayout,profilelayout;
    public static ImageView homeicon,shufticon,ordersicon,profileicon;
    public static TextView home,shift,orders,profile;
    public static TextView newcount,newcountevent;
    public View tab,tab1;
    Logout logout;
    private SessionManager sessionManager;
    private SessionManagerFCM sessionManagerFCM;
    private TextView porchname;
    static public boolean isvisisble=true;

    Dialog menudialog;
  
    Typeface font;
    Typeface font1;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#091F42"));

        }
        // Set up the login form.
        setContentView(R.layout.associate_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#091F42"));
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.mipmap.moreicon);
        toolbar.setOverflowIcon(drawable);
        sessionManager=new SessionManager(AYSMain_Mobile.this);
        sessionManagerFCM=new SessionManagerFCM(AYSMain_Mobile.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Crashlytics.setUserIdentifier(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        FirebaseCrashlytics.getInstance().setUserId(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(sessionManager.getPorchName());
        mFirebaseAnalytics.setUserProperty("Id",sessionManager.getPorchName()+" "+sessionManager.getNAME());

        porchname=findViewById(R.id.porchname);
        porchname.setText(sessionManager.getPorchName());
         font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
        font1 = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Thin.ttf");
        homelayout=findViewById(R.id.homelayout);
        homelayout.setOnClickListener(this);
        tab=findViewById(R.id.tab);
        tab.setOnClickListener(this);
        tab1=findViewById(R.id.tab1);
        tab1.setOnClickListener(this);
        newcount=findViewById(R.id.newcount);
        newcountevent=findViewById(R.id.newcountevent);


        orderslayout=findViewById(R.id.eventlayout);
        orderslayout.setOnClickListener(this);




        homeicon=findViewById(R.id.favicon);




        home=findViewById(R.id.home);
        home.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        orders=findViewById(R.id.event);
        orders.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));



        if (savedInstanceState == null) {
            home.setTextColor(AYSMain_Mobile.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));

            Fragment fragmentmanager = new AYS_Dashboard();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }


    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.homelayout){
            isvisisble=true;
            homeicon.setVisibility(View.GONE);
            if(shake!=null){
                shake.cancel();
                homeicon.setVisibility(View.GONE);

            }
            home.setTextColor(AYSMain_Mobile.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#707070"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            Fragment fragmentmanager = new AYS_Dashboard();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();

        }
        if(view.getId()== R.id.eventlayout){
            isvisisble=false;

            Fragment fragmentmanager = new AYs_Dashboard_Clearance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

            orders.setTextColor(AYSMain_Mobile.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.golddark));

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_ays, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId() ;
        switch (id){

            case R.id.history:
                isvisisble=false;
                homeicon.setVisibility(View.GONE);
                orders.setTextColor(Color.parseColor("#6E7E7E"));
                home.setTextColor(Color.parseColor("#6E7E7E"));
                orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                tab.setBackgroundColor(getResources().getColor(R.color.white));
                tab1.setBackgroundColor(getResources().getColor(R.color.white));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootLayout, AYS_History.newInstance())
                        .commitNow();
                break;

            case R.id.navigation_dashboard:
                final Dialog dialog = new Dialog(AYSMain_Mobile.this);
                // Include dialog.xml file

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delet_dialog);
                int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                dialog.getWindow().setLayout(width1, height1);

                dialog.setCancelable(false);
                // Set dialog title
                dialog.setTitle("");
                dialog.show();
                TextView textView=(TextView)dialog.findViewById(R.id.text) ;
                // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                TextView confirm=dialog.findViewById(R.id.confirm) ;
                TextView cancel1=dialog.findViewById(R.id.cancel);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(AYSMain_Mobile.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(AYSMain_Mobile.this)){
                            Logout();
                            dialog.dismiss();

                        } else{
                        }
                    }
                });
                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        final Dialog dialog = new Dialog(AYSMain_Mobile.this);
        // Include dialog.xml file

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delet_dialog);
        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

        dialog.getWindow().setLayout(width1, height1);

        dialog.setCancelable(false);
        // Set dialog title
        dialog.setTitle("");
        dialog.show();
        TextView textView=dialog.findViewById(R.id.text) ;
        textView.setText("Are you sure you want to exit ?");
        // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
        TextView confirm=dialog.findViewById(R.id.cancel) ;
        TextView cancel1=dialog.findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AYSMain_Mobile.this.finish();
                dialog.dismiss();
            }
        });

    }
    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(AYSMain_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().logout( "Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==201||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(AYSMain_Mobile.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(AYSMain_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();
                }
                else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }

                progressDialog.dismiss();



            }

            @Override
            public void onFailure(@NonNull Call<Action> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                progressDialog.dismiss();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {

            registerReceiver(mMessageReceiver, new IntentFilter("com.viralops.touchlessfoodordering"));


        }
        catch (Exception e)
        {
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            registerReceiver(mMessageReceiver, new IntentFilter("com.viralops.touchlessfoodordering"));

            unregisterReceiver(mMessageReceiver);

        }
        catch (Exception e)
        {
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isvisisble==true) {
                AnimateBell();

                Fragment fragment1 = new AYS_Dashboard();


                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
            }
            else {
                AnimateBell();
                if(Network.isNetworkAvailable(AYSMain_Mobile.this)){
                    GetHeader();
                }
                else if(Network.isNetworkAvailable2(AYSMain_Mobile.this)){
                    GetHeader();

                }
                else{

                }

                isvisisble=false;
            }



        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        try {

            registerReceiver(mMessageReceiver, new IntentFilter("com.viralops.touchlessfoodordering"));


        }
        catch (Exception e)
        {
        }

    }

    public void AnimateBell() {
        homeicon.setVisibility(View.VISIBLE);

         shake = AnimationUtils.loadAnimation(AYSMain_Mobile.this, R.anim.shakeanimation);
        homeicon.setAnimation(shake);

    }

    static String capitailizeWord(String str) {
        StringBuffer s = new StringBuffer();

        // Declare a character of space
        // To identify that the next character is the starting
        // of a new word
        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {

            // If previous character is space and current
            // character is not space then it shows that
            // current letter is the starting of the word
            if (ch == ' ' && str.charAt(i) != ' ')
                s.append(Character.toUpperCase(str.charAt(i)));
            else
                s.append(str.charAt(i));
            ch = str.charAt(i);
        }

        // Return the string with trimming
        return s.toString().trim();
    }
    private void GetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Connect_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Connect_Header>() {
            @Override
            public void onResponse(@NonNull Call<Connect_Header> call, @NonNull Response<Connect_Header> response) {

                if(response.code()==202||response.code()==200){
                    Connect_Header  login = response.body();
                    newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                    newcountevent.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");


                }
                else if(response.code()==401){
                    Connect_Header login = response.body();
                    Toast.makeText(AYSMain_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(AYSMain_Mobile.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Connect_Header> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }

}
