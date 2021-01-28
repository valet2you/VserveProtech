package com.viralops.touchlessfoodordering.Mobile.Booking;

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

import com.crashlytics.android.Crashlytics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.GetChars;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;

import com.viralops.touchlessfoodordering.Mobile.IRD.Associate_Dashboard;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Booking_Activity extends AppCompatActivity implements View.OnClickListener {
SessionManager sessionManager;
    public static Animation shake;
    public static Animation shake1;

ArrayList services=new ArrayList();
SessionManagerFCM sessionManagerFCM;
FirebaseAnalytics mFirebaseAnalytics;
TextView porchname;
    public static LinearLayout homelayout,shiftlayout,orderslayout,profilelayout,dispachedlayout;
    public static ImageView homeicon,shufticon,ordersicon,profileicon;
    public static TextView home,shift,orders,profile,orders1;
    public static TextView newcount,newcountevent,newcountevent1;
    public View tab,tab1,tab2;
    Typeface font;
    Typeface font1;
    Spinner servicesspinner;
    public static boolean isVisible=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#091F42"));

        }
        // Set up the login form.
        setContentView(R.layout.activity_booking_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#091F42"));
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.mipmap.moreicon);
        toolbar.setOverflowIcon(drawable);
        sessionManager=new SessionManager(Booking_Activity.this);
        sessionManagerFCM=new SessionManagerFCM(Booking_Activity.this);
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
        servicesspinner=findViewById(R.id.servicesspinner);
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
        shufticon=findViewById(R.id.df);




        home=findViewById(R.id.home);
        home.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        orders=findViewById(R.id.event);
        orders.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        String formattedDate = df.format(c);
        String formattedDate1 = df.format(tomorrow);

        newcount.setText(formattedDate);
        newcountevent.setText(formattedDate1);
        if(Network.isNetworkAvailable(Booking_Activity.this)){
            GetServices();
        }else if(Network.isNetworkAvailable2(Booking_Activity.this)){
            GetServices();

        }
        else{

        }


      servicesspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              String text = parent.getItemAtPosition(position).toString();
          //    Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
              if(text.equals("Fitness Center Booking")){
                  sessionManager.setFilterValue("Fitness Center Booking");

              }
              else if(text.equals("Pool Booking")){
                  sessionManager.setFilterValue("Pool Booking");

              } else if(text.equals("Meeting Booking")){
                  sessionManager.setFilterValue("Meeting Booking");

              }else if(text.equals("Conference Booking")){
                  sessionManager.setFilterValue("Conference Booking");

              }
              if(isVisible==true){
                  home.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tab.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  shufticon.setVisibility(View.GONE);
                  if(shake1!=null){
                      shake1.cancel();
                      shufticon.setVisibility(View.GONE);

                  }
                  homeicon.setVisibility(View.GONE);
                  if(shake!=null){
                      shake.cancel();
                      homeicon.setVisibility(View.GONE);

                  }

                  Fragment fragmentmanager = new FirstFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

              }
              else{
                  orders.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  home.setTextColor(Color.parseColor("#6E7E7E"));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tab1.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab.setBackgroundColor(getResources().getColor(R.color.white));

                  shufticon.setVisibility(View.GONE);
                  if(shake1!=null){
                      shake1.cancel();
                      shufticon.setVisibility(View.GONE);

                  }
                  homeicon.setVisibility(View.GONE);
                  if(shake!=null){
                      shake.cancel();
                      homeicon.setVisibility(View.GONE);

                  }
                  Fragment fragmentmanager = new SecondFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
        if (savedInstanceState == null) {
            home.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));


            Fragment fragmentmanager = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.homelayout){
            home.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=true;
            shufticon.setVisibility(View.GONE);
            if(shake1!=null){
                shake1.cancel();
                shufticon.setVisibility(View.GONE);

            }
            homeicon.setVisibility(View.GONE);
            if(shake!=null){
                shake.cancel();
                homeicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(v.getId()==R.id.eventlayout){
            orders.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=false;
            shufticon.setVisibility(View.GONE);
            if(shake1!=null){
                shake1.cancel();
                shufticon.setVisibility(View.GONE);

            }
            homeicon.setVisibility(View.GONE);
            if(shake!=null){
                shake.cancel();
                homeicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new SecondFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }

    }

    private void GetServices() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Booking_services("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Services>() {
            @Override
            public void onResponse(@NonNull Call<Services> call, @NonNull Response<Services> response) {

                if(response.code()==202||response.code()==200){
                    Services  login = response.body();


                    for(int i=0;i<login.getData().length;i++){
                        String s=login.getData()[i];
                        if(s.equals("gym")){
                            s="Fitness Center Booking";
                        }
                         else if(s.equals("pool")){
                            s="Pool Booking";
                        } else if(s.equals("meeting")){
                            s="Meeting Booking";
                        } else if(s.equals("conference")){
                            s="Conference Booking";
                        }
                        services.add(s);

                    }
                    Collections.reverse(services);
                    sessionManager.setFilterValue(services.get(0).toString());

                    servicesspinner.setAdapter(new ArrayAdapter(Booking_Activity.this, R.layout.spinner_item, services));


                }
                else if(response.code()==401){
                    Services login = response.body();
                    Toast.makeText(Booking_Activity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(Booking_Activity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Services> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Booking_Activity.this);
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
                    Toast.makeText(Booking_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Booking_Activity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_supervisor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId() ;
        switch (id){


            case R.id.navigation_dashboard:
                final Dialog dialog1 = new Dialog(Booking_Activity.this);
                // Include dialog.xml file

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.delet_dialog);
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.80);
                dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                dialog1.getWindow().setLayout(width, height);

                dialog1.setCancelable(false);
                // Set dialog title
                dialog1.setTitle("");
                dialog1.show();
                TextView textView=(TextView)dialog1.findViewById(R.id.text) ;
                // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                TextView confirm=dialog1.findViewById(R.id.confirm) ;
                TextView cancel1=dialog1.findViewById(R.id.cancel);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(Booking_Activity.this)){
                            Logout();
                            dialog1.dismiss();
                        } if(Network.isNetworkAvailable2(Booking_Activity.this)){
                            Logout();
                            dialog1.dismiss();

                        } else{
                        }
                    }
                });
                cancel1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        final Dialog dialog = new Dialog(Booking_Activity.this);
        // Include dialog.xml file

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delet_dialog);
        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.80);
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
                Booking_Activity.this.finish();
                dialog.dismiss();
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
            if(sessionManager.getFilterValue().equals("Fitness Center Booking")){
                if(sessionManager.getDesign_Style().equals("gym")){
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df.format(tomorrow);

                     String time=getDate1(sessionManager.getSLOT1());
                     if(time.equals(formattedDate)){
                         if(isVisible==true) {
                             AnimateBell();

                             Fragment fragment1 = new FirstFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                         }
                         else {
                             AnimateBell();
                         }

                     }
                     else if(time.equals(formattedDate1)){
                         if(isVisible==true) {
                             AnimateBell1();

                                       }
                         else {
                             AnimateBell1();

                             Fragment fragment1 = new SecondFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }





                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                }
                else if(sessionManager.getDesign_Style().equals("meeting")){

                }
                else{

                }

            }
            else if(sessionManager.getFilterValue().equals("Pool Booking")){
                if(sessionManager.getDesign_Style().equals("pool")){
                    {
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        Date tomorrow = calendar.getTime();

                        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);
                        String formattedDate1 = df.format(tomorrow);

                        String time=getDate1(sessionManager.getSLOT1());
                        if(time.equals(formattedDate)){
                            if(isVisible==true) {
                                AnimateBell();

                                Fragment fragment1 = new FirstFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                            }
                            else {
                                AnimateBell();
                            }

                        }
                        else if(time.equals(formattedDate1)){
                            if(isVisible==true) {
                                AnimateBell1();

                            }
                            else {
                                AnimateBell1();

                                Fragment fragment1 = new SecondFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }





                    }


                }
                else if(sessionManager.getDesign_Style().equals("gym")){

                }
                else if(sessionManager.getDesign_Style().equals("meeting")){

                }
                else{

                }


            } else if(sessionManager.getFilterValue().equals("Meeting Booking")){
                if(sessionManager.getDesign_Style().equals("meeting")){
                    {
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        Date tomorrow = calendar.getTime();

                        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);
                        String formattedDate1 = df.format(tomorrow);

                        String time=getDate1(sessionManager.getSLOT1());
                        if(time.equals(formattedDate)){
                            if(isVisible==true) {
                                AnimateBell();

                                Fragment fragment1 = new FirstFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                            }
                            else {
                                AnimateBell();
                            }

                        }
                        else if(time.equals(formattedDate1)){
                            if(isVisible==true) {
                                AnimateBell1();

                            }
                            else {
                                AnimateBell1();

                                Fragment fragment1 = new SecondFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }





                    }
                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                }
                else if(sessionManager.getDesign_Style().equals("gym")){

                }
                else{

                }


            }else if(sessionManager.getFilterValue().equals("Conference Booking")){
                if(sessionManager.getDesign_Style().equals("gym")){


                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                }
                else if(sessionManager.getDesign_Style().equals("meeting")){

                }
                else{
                    {
                        Date c = Calendar.getInstance().getTime();
                        System.out.println("Current time => " + c);
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        Date tomorrow = calendar.getTime();

                        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);
                        String formattedDate1 = df.format(tomorrow);

                        String time=getDate1(sessionManager.getSLOT1());
                        if(time.equals(formattedDate)){
                            if(isVisible==true) {
                                AnimateBell();

                                Fragment fragment1 = new FirstFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                            }
                            else {
                                AnimateBell();
                            }

                        }
                        else if(time.equals(formattedDate1)){
                            if(isVisible==true) {
                                AnimateBell1();

                            }
                            else {
                                AnimateBell1();

                                Fragment fragment1 = new SecondFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }





                    }

                }


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
        shufticon.setVisibility(View.INVISIBLE);

        shake = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
        homeicon.setAnimation(shake);

    } public void AnimateBell1() {
        homeicon.setVisibility(View.INVISIBLE);
        shufticon.setVisibility(View.VISIBLE);

        shake1 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
        shufticon.setAnimation(shake1);

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
