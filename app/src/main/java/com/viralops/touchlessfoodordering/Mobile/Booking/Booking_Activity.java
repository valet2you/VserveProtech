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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

import com.google.android.material.tabs.TabLayout;
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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Booking_Activity extends AppCompatActivity implements View.OnClickListener {
SessionManager sessionManager;
    public static Animation shake;
    public static Animation shake1;
    public static Animation shake2;
    public static Animation shake3;
    public static Animation shake4;
    public static Animation shake5;
    public static Animation shake6;

ArrayList services=new ArrayList();
SessionManagerFCM sessionManagerFCM;
FirebaseAnalytics mFirebaseAnalytics;
TextView porchname;
    //public static LinearLayout homelayout,shiftlayout,orderslayout,onelayout,twolayout,threelayout,fourlayout,fivelayout;
  ////  public static ImageView homeicon,shufticon,ordersicon,profileicon,oneicon,trwoicon,threeicon,fouricon,fiveicon;
  //  public static TextView home,shift,orders,profile,orders1,one,two,three,four,five;
  //  public static TextView newcount,newcountevent,newcountevent1,onecountevent,twocountevent,threecountevent,fourcountevent
   //         ,fivecountevent;
   // public View tab,tab1,tabone,tabtwo,tabthree,tabfour,tabfive;
    Typeface font;
    Typeface font1;
    Spinner servicesspinner;
    ViewPager pager;
    public static int isVisible=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#091F42"));

        }
        // Set up the login form.
        setContentView(R.layout.activity_booking_week);

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
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        pager=findViewById(R.id.pager);

        font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
        font1 = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Thin.ttf");
        servicesspinner=findViewById(R.id.servicesspinner);
        //homelayout=findViewById(R.id.homelayout);
       // homelayout.setOnClickListener(this);
       /* onelayout=findViewById(R.id.onelayout);
        onelayout.setOnClickListener(this);
        twolayout=findViewById(R.id.twolayout);
        twolayout.setOnClickListener(this);
        threelayout=findViewById(R.id.threelayout);
        threelayout.setOnClickListener(this);
        fourlayout=findViewById(R.id.fourlayout);
        fourlayout.setOnClickListener(this);
        fivelayout=findViewById(R.id.fivelayout);
        fivelayout.setOnClickListener(this);*/
      //  tab=findViewById(R.id.tab);
      //  tab.setOnClickListener(this);
     /*   tabone=findViewById(R.id.tabone);
        tabone.setOnClickListener(this);
        tabtwo=findViewById(R.id.tabtwo);
        tabtwo.setOnClickListener(this);
        tabthree=findViewById(R.id.tabthree);
        tabthree.setOnClickListener(this);
        tabfour=findViewById(R.id.tabfour);
        tabfour.setOnClickListener(this);
        tabfive=findViewById(R.id.tabfive);*/
       // tabfive.setOnClickListener(this);
       // tab1=findViewById(R.id.tab1);
       // tab1.setOnClickListener(this);
      //  newcount=findViewById(R.id.newcount);
      //  newcountevent=findViewById(R.id.newcountevent);
   /*     onecountevent=findViewById(R.id.onecountevent);
        twocountevent=findViewById(R.id.twocountevent);
        threecountevent=findViewById(R.id.threecountevent);
        fourcountevent=findViewById(R.id.fourcountevent);
        fivecountevent=findViewById(R.id.fivecountevent);
*/

        //orderslayout=findViewById(R.id.eventlayout);
       // orderslayout.setOnClickListener(this);






        //homeicon=findViewById(R.id.favicon);
      //  shufticon=findViewById(R.id.df);
     /*   oneicon=findViewById(R.id.one);
        trwoicon=findViewById(R.id.two);
        threeicon=findViewById(R.id.three);
        fouricon=findViewById(R.id.four);
        fiveicon=findViewById(R.id.five);*/




       // home=findViewById(R.id.home);
       // home.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
       // newcountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
       // newcount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
       // orders=findViewById(R.id.event);
       // orders.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
/*        one=findViewById(R.id.oneevent);
        one.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        onecountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        two=findViewById(R.id.twoevent);
        two.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        twocountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        three=findViewById(R.id.threeevent);
        three.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        threecountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        four=findViewById(R.id.fourevent);
        four.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        fourcountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        five=findViewById(R.id.fiveevent);
        five.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        fivecountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));*/
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_YEAR, 2);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DAY_OF_YEAR, 3);
    Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DAY_OF_YEAR, 4);
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.DAY_OF_YEAR, 5);
   Calendar calendar6 = Calendar.getInstance();
        calendar6.add(Calendar.DAY_OF_YEAR, 6);
        Date one1 = calendar2.getTime();
        Date two1 = calendar3.getTime();
        Date three1 = calendar4.getTime();
        Date four1 = calendar5.getTime();
        Date five1 = calendar6.getTime();

        SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        SimpleDateFormat df1 = new SimpleDateFormat("EEEE", Locale.getDefault());
        String formattedDate = df.format(c);
        String formattedDate01 = df1.format(c);
       // home.setText(formattedDate01);
        String formattedDate1 = df.format(tomorrow);
        String formattedDate10 = df1.format(tomorrow);
        //orders.setText(formattedDate10);
        String foratteddate2=df.format(one1);
        String foratteddate20=df1.format(one1);
        //one.setText(foratteddate20);
        String foratteddate3=df.format(two1);
        String foratteddate30=df1.format(two1);
       // two.setText(foratteddate30);
        String foratteddate4=df.format(three1);
        String foratteddate40=df1.format(three1);
       // three.setText(foratteddate40);
        String foratteddate5=df.format(four1);
        String foratteddate50=df1.format(four1);
       // four.setText(foratteddate50);
        String foratteddate6=df.format(five1);
        String foratteddate60=df1.format(five1);
       // five.setText(foratteddate60);

       /* tabLayout.addTab(tabLayout.newTab().setText(formattedDate01));
        tabLayout.addTab(tabLayout.newTab().setText(formattedDate10));
        tabLayout.addTab(tabLayout.newTab().setText(foratteddate20));
        tabLayout.addTab(tabLayout.newTab().setText(foratteddate30));
        tabLayout.addTab(tabLayout.newTab().setText(foratteddate40));
        tabLayout.addTab(tabLayout.newTab().setText(foratteddate50));
        tabLayout.addTab(tabLayout.newTab().setText(foratteddate60));*/
       // tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(getResources().getColor(R.color.drakgrey),
                getResources().getColor(R.color.mogiya));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.golddark));


       /* newcount.setText(formattedDate);
        newcountevent.setText(formattedDate1);
        onecountevent.setText(foratteddate2);
        twocountevent.setText(foratteddate3);
        threecountevent.setText(foratteddate4);
        fourcountevent.setText(foratteddate5);
        fivecountevent.setText(foratteddate6);*/
        tabLayout.setupWithViewPager(pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment(), formattedDate);
        adapter.addFragment(new SecondFragment(), formattedDate1);
        adapter.addFragment(new thirdFragment(), foratteddate2);
        adapter.addFragment(new fourthFragment(), foratteddate3);
        adapter.addFragment(new fifthFragment(), foratteddate4);
        adapter.addFragment(new sixthFragment(), foratteddate5);
        adapter.addFragment(new SeventhFragment(), foratteddate6);
        pager.setAdapter(adapter);
/*
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });*/

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

              }else if(text.equals("Breakfast Booking")){
                  sessionManager.setFilterValue("Breakfast Booking");

              }
              if(isVisible==0){
                /*  home.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  one.setTextColor(Color.parseColor("#6E7E7E"));
                  two.setTextColor(Color.parseColor("#6E7E7E"));
                  three.setTextColor(Color.parseColor("#6E7E7E"));
                  four.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tab.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.white));

                  shufticon.setVisibility(View.INVISIBLE);*/
                /*  if(shake1!=null){
                      shake1.cancel();
                      shake1.reset();
                      shake1.setAnimationListener(null);
                      shufticon.clearAnimation();

                      shufticon.setVisibility(View.GONE);

                  }
                  homeicon.setVisibility(View.GONE);
                  if(shake!=null){
                      shake.cancel();
                      homeicon.setVisibility(View.GONE);

                  }
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/
              //  FirstFragment firstFragment=new FirstFragment();

                /*  Fragment fragmentmanager = new FirstFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/
              }
              else if(isVisible==1){
          /*        orders.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  home.setTextColor(Color.parseColor("#6E7E7E"));
                  one.setTextColor(Color.parseColor("#6E7E7E"));
                  two.setTextColor(Color.parseColor("#6E7E7E"));
                  three.setTextColor(Color.parseColor("#6E7E7E"));
                  four.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tab1.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.white));

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
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/

                /*  Fragment fragmentmanager = new SecondFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/
              }if(isVisible==2){
                 /* one.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  home.setTextColor(Color.parseColor("#6E7E7E"));
                  two.setTextColor(Color.parseColor("#6E7E7E"));
                  three.setTextColor(Color.parseColor("#6E7E7E"));
                  four.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  tab.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.white));

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
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/

               /*   Fragment fragmentmanager = new thirdFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/
              }if(isVisible==3){
                 /* two.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  one.setTextColor(Color.parseColor("#6E7E7E"));
                  home.setTextColor(Color.parseColor("#6E7E7E"));
                  three.setTextColor(Color.parseColor("#6E7E7E"));
                  four.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.white));
                  tab.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.white));

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
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/
/*

                  Fragment fragmentmanager = new fourthFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/

              }if(isVisible==4){
            /*      three.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  one.setTextColor(Color.parseColor("#6E7E7E"));
                  two.setTextColor(Color.parseColor("#6E7E7E"));
                  home.setTextColor(Color.parseColor("#6E7E7E"));
                  four.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
                  tab.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.white));

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
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/
               /*   Fragment fragmentmanager = new fifthFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/
              }if(isVisible==5){
                 /* four.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  one.setTextColor(Color.parseColor("#6E7E7E"));
                  two.setTextColor(Color.parseColor("#6E7E7E"));
                  three.setTextColor(Color.parseColor("#6E7E7E"));
                  home.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.white));
                  tab.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.white));

                  if(shake1!=null){
                      shake1.cancel();
                      shufticon.setVisibility(View.GONE);

                  }
                  homeicon.setVisibility(View.GONE);
                  if(shake!=null){
                      shake.cancel();
                      homeicon.setVisibility(View.GONE);

                  }
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/

                /*  Fragment fragmentmanager = new sixthFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

           */   }
              else{
                  /*five.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
                  orders.setTextColor(Color.parseColor("#6E7E7E"));
                  one.setTextColor(Color.parseColor("#6E7E7E"));
                  two.setTextColor(Color.parseColor("#6E7E7E"));
                  three.setTextColor(Color.parseColor("#6E7E7E"));
                  four.setTextColor(Color.parseColor("#6E7E7E"));
                  five.setTextColor(Color.parseColor("#6E7E7E"));
                  orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
                  homelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  onelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  twolayout.setBackgroundColor(getResources().getColor(R.color.white));
                  threelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
                  fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfive.setBackgroundColor(getResources().getColor(R.color.golddark));
                  tab1.setBackgroundColor(getResources().getColor(R.color.white));
                  tabone.setBackgroundColor(getResources().getColor(R.color.white));
                  tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
                  tabthree.setBackgroundColor(getResources().getColor(R.color.white));
                  tabfour.setBackgroundColor(getResources().getColor(R.color.white));
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
                  oneicon.setVisibility(View.GONE);
                  if(shake2!=null){
                      shake2.cancel();
                      oneicon.setVisibility(View.GONE);

                  }
                  trwoicon.setVisibility(View.GONE);
                  if(shake3!=null){
                      shake3.cancel();
                      trwoicon.setVisibility(View.GONE);

                  }
                  threeicon.setVisibility(View.GONE);
                  if(shake4!=null){
                      shake4.cancel();
                      threeicon.setVisibility(View.GONE);

                  }
                  fouricon.setVisibility(View.GONE);
                  if(shake5!=null){
                      shake5.cancel();
                      fouricon.setVisibility(View.GONE);

                  }
                  fiveicon.setVisibility(View.GONE);
                  if(shake6!=null){
                      shake6.cancel();
                      fiveicon.setVisibility(View.GONE);

                  }*/
                /*  Fragment fragmentmanager = new SeventhFragment();
                  getSupportFragmentManager().beginTransaction()
                          .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
        if (savedInstanceState == null) {
           /* home.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));
*/


           /* Fragment fragmentmanager = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();
*/
        }


    }

    @Override
    public void onClick(View v) {
      /*  if(v.getId()==R.id.homelayout){
            home.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=0;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new FirstFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(v.getId()==R.id.eventlayout){
            orders.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=1;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new SecondFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }*/
   /*     else if(v.getId()==R.id.onelayout){
            one.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));


            isVisible=2;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new thirdFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(v.getId()==R.id.twolayout){
            two.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=3;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new fourthFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(v.getId()==R.id.threelayout){
            three.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=4;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new fifthFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(v.getId()==R.id.fourlayout){
            four.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            five.setTextColor(Color.parseColor("#6E7E7E"));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=5;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new sixthFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        else if(v.getId()==R.id.fivelayout){
            five.setTextColor(Booking_Activity.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            one.setTextColor(Color.parseColor("#6E7E7E"));
            two.setTextColor(Color.parseColor("#6E7E7E"));
            three.setTextColor(Color.parseColor("#6E7E7E"));
            four.setTextColor(Color.parseColor("#6E7E7E"));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            fivelayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            onelayout.setBackgroundColor(getResources().getColor(R.color.white));
            twolayout.setBackgroundColor(getResources().getColor(R.color.white));
            threelayout.setBackgroundColor(getResources().getColor(R.color.white));
            fourlayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            tabfive.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tabone.setBackgroundColor(getResources().getColor(R.color.white));
            tabtwo.setBackgroundColor(getResources().getColor(R.color.white));
            tabthree.setBackgroundColor(getResources().getColor(R.color.white));
            tabfour.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));

            isVisible=6;
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
            oneicon.setVisibility(View.GONE);
            if(shake2!=null){
                shake2.cancel();
                oneicon.setVisibility(View.GONE);

            }
            trwoicon.setVisibility(View.GONE);
            if(shake3!=null){
                shake3.cancel();
                trwoicon.setVisibility(View.GONE);

            }
            threeicon.setVisibility(View.GONE);
            if(shake4!=null){
                shake4.cancel();
                threeicon.setVisibility(View.GONE);

            }
            fouricon.setVisibility(View.GONE);
            if(shake5!=null){
                shake5.cancel();
                fouricon.setVisibility(View.GONE);

            }
            fiveicon.setVisibility(View.GONE);
            if(shake6!=null){
                shake6.cancel();
                fiveicon.setVisibility(View.GONE);

            }
            Fragment fragmentmanager = new SeventhFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }*/

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
                         else if(s.equals("breakfast")){
                             s="Breakfast Booking";
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

 Calendar calendar1 = Calendar.getInstance();
                    calendar1.add(Calendar.DAY_OF_YEAR, 2);
                    Date tomorrow1 = calendar1.getTime();
                    String formattedDate2 = df.format(tomorrow1);

               Calendar calendar2 = Calendar.getInstance();
                    calendar2.add(Calendar.DAY_OF_YEAR, 3);
                    Date tomorrow2 = calendar2.getTime();
                    String formattedDate3 = df.format(tomorrow2);

   Calendar calendar3 = Calendar.getInstance();
                    calendar3.add(Calendar.DAY_OF_YEAR, 4);
                    Date tomorrow3 = calendar3.getTime();
                    String formattedDate4 = df.format(tomorrow3);

Calendar calendar4 = Calendar.getInstance();
                    calendar4.add(Calendar.DAY_OF_YEAR, 5);
                    Date tomorrow4 = calendar4.getTime();
                    String formattedDate5 = df.format(tomorrow4);

Calendar calendar5 = Calendar.getInstance();
                    calendar5.add(Calendar.DAY_OF_YEAR, 6);
                    Date tomorrow5 = calendar5.getTime();
                    String formattedDate6 = df.format(tomorrow5);



                     String time=getDate1(sessionManager.getSLOT1());
                     if(time.equals(formattedDate)){
                         if(isVisible==0) {
                           //  AnimateBell();

                             Fragment fragment1 = new FirstFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                         }
                         else {
                           //  AnimateBell();
                         }

                     }
                     else if(time.equals(formattedDate1)){
                         if(isVisible==1) {
                           //  AnimateBell1();

                                       }
                         else {
                            // AnimateBell1();

                             Fragment fragment1 = new SecondFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }
                     else if(time.equals(formattedDate2)){
                         if(isVisible==2) {
                            // AnimateBell2();

                                       }
                         else {
                            // AnimateBell2();

                             Fragment fragment1 = new thirdFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }
                     else if(time.equals(formattedDate3)){
                         if(isVisible==3) {
                            // AnimateBell3();

                                       }
                         else {
                            // AnimateBell3();

                             Fragment fragment1 = new fourthFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }
                     else if(time.equals(formattedDate4)){
                         if(isVisible==4) {
                             //AnimateBell4();

                                       }
                         else {
                            // AnimateBell4();

                             Fragment fragment1 = new fifthFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }
                     else if(time.equals(formattedDate5)){
                         if(isVisible==5) {
                            // AnimateBell5();

                                       }
                         else {
                            // AnimateBell5();

                             Fragment fragment1 = new sixthFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }
                     else if(time.equals(formattedDate6)){
                         if(isVisible==6) {
                            // AnimateBell6();

                                       }
                         else {
                            // AnimateBell6();

                             Fragment fragment1 = new SeventhFragment();


                             getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                         }

                     }





                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                }
                else if(sessionManager.getDesign_Style().equals("breakfast")){

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

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.add(Calendar.DAY_OF_YEAR, 2);
                        Date tomorrow1 = calendar1.getTime();
                        String formattedDate2 = df.format(tomorrow1);

                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.add(Calendar.DAY_OF_YEAR, 3);
                        Date tomorrow2 = calendar2.getTime();
                        String formattedDate3 = df.format(tomorrow2);

                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.add(Calendar.DAY_OF_YEAR, 4);
                        Date tomorrow3 = calendar3.getTime();
                        String formattedDate4 = df.format(tomorrow3);

                        Calendar calendar4 = Calendar.getInstance();
                        calendar4.add(Calendar.DAY_OF_YEAR, 5);
                        Date tomorrow4 = calendar4.getTime();
                        String formattedDate5 = df.format(tomorrow4);

                        Calendar calendar5 = Calendar.getInstance();
                        calendar5.add(Calendar.DAY_OF_YEAR, 6);
                        Date tomorrow5 = calendar5.getTime();
                        String formattedDate6 = df.format(tomorrow5);

                        String time=getDate1(sessionManager.getSLOT1());
                        if(time.equals(formattedDate)){
                            if(isVisible==0) {
                                //AnimateBell();

                                Fragment fragment1 = new FirstFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                            }
                            else {
                                //AnimateBell();
                            }

                        }
                        else if(time.equals(formattedDate1)){
                            if(isVisible==1) {
                                //AnimateBell1();

                            }
                            else {
                              //  AnimateBell1();

                                Fragment fragment1 = new SecondFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate2)){
                            if(isVisible==2) {
                              //  AnimateBell2();

                            }
                            else {
                               // AnimateBell2();

                                Fragment fragment1 = new thirdFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate3)){
                            if(isVisible==3) {
                              //  AnimateBell3();

                            }
                            else {
                               // AnimateBell3();

                                Fragment fragment1 = new fourthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate4)){
                            if(isVisible==4) {
                              //  AnimateBell4();

                            }
                            else {
                               // AnimateBell4();

                                Fragment fragment1 = new fifthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate5)){
                            if(isVisible==5) {
                               // AnimateBell5();

                            }
                            else {
                              //  AnimateBell5();

                                Fragment fragment1 = new sixthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate6)){
                            if(isVisible==6) {
                               // AnimateBell6();

                            }
                            else {
                               // AnimateBell6();

                                Fragment fragment1 = new SeventhFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }





                    }


                }
                else if(sessionManager.getDesign_Style().equals("gym")){

                }
                else if(sessionManager.getDesign_Style().equals("meeting")){

                }
                else if(sessionManager.getDesign_Style().equals("breakfast")){

                }
                else{

                }


            }
            else if(sessionManager.getFilterValue().equals("Meeting Booking")){
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

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.add(Calendar.DAY_OF_YEAR, 2);
                        Date tomorrow1 = calendar1.getTime();
                        String formattedDate2 = df.format(tomorrow1);

                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.add(Calendar.DAY_OF_YEAR, 3);
                        Date tomorrow2 = calendar2.getTime();
                        String formattedDate3 = df.format(tomorrow2);

                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.add(Calendar.DAY_OF_YEAR, 4);
                        Date tomorrow3 = calendar3.getTime();
                        String formattedDate4 = df.format(tomorrow3);

                        Calendar calendar4 = Calendar.getInstance();
                        calendar4.add(Calendar.DAY_OF_YEAR, 5);
                        Date tomorrow4 = calendar4.getTime();
                        String formattedDate5 = df.format(tomorrow4);

                        Calendar calendar5 = Calendar.getInstance();
                        calendar5.add(Calendar.DAY_OF_YEAR, 6);
                        Date tomorrow5 = calendar5.getTime();
                        String formattedDate6 = df.format(tomorrow5);

                        String time=getDate1(sessionManager.getSLOT1());
                        if(time.equals(formattedDate)){
                            if(isVisible==0) {
                              //  AnimateBell();

                                Fragment fragment1 = new FirstFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                            }
                            else {
                              //  AnimateBell();
                            }

                        }
                        else if(time.equals(formattedDate1)){
                            if(isVisible==1) {
                             //   AnimateBell1();

                            }
                            else {
                               // AnimateBell1();

                                Fragment fragment1 = new SecondFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate2)){
                            if(isVisible==2) {
                               // AnimateBell2();

                            }
                            else {
                               // AnimateBell2();

                                Fragment fragment1 = new thirdFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate3)){
                            if(isVisible==3) {
                               // AnimateBell3();

                            }
                            else {
                               // AnimateBell3();

                                Fragment fragment1 = new fourthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate4)){
                            if(isVisible==4) {
                               // AnimateBell4();

                            }
                            else {
                              //  AnimateBell4();

                                Fragment fragment1 = new fifthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate5)){
                            if(isVisible==5) {
                              //  AnimateBell5();

                            }
                            else {
                               // AnimateBell5();

                                Fragment fragment1 = new sixthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate6)){
                            if(isVisible==6) {
                             //   AnimateBell6();

                            }
                            else {
                               // AnimateBell6();

                                Fragment fragment1 = new SeventhFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }





                    }
                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                }
                else if(sessionManager.getDesign_Style().equals("gym")){

                }
                else if(sessionManager.getDesign_Style().equals("breakfast")){

                }
                else{

                }


            }
            else if(sessionManager.getFilterValue().equals("Conference Booking")){
                if(sessionManager.getDesign_Style().equals("gym")){


                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                } else if(sessionManager.getDesign_Style().equals("breakfast")){

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

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.add(Calendar.DAY_OF_YEAR, 2);
                        Date tomorrow1 = calendar1.getTime();
                        String formattedDate2 = df.format(tomorrow1);

                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.add(Calendar.DAY_OF_YEAR, 3);
                        Date tomorrow2 = calendar2.getTime();
                        String formattedDate3 = df.format(tomorrow2);

                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.add(Calendar.DAY_OF_YEAR, 4);
                        Date tomorrow3 = calendar3.getTime();
                        String formattedDate4 = df.format(tomorrow3);

                        Calendar calendar4 = Calendar.getInstance();
                        calendar4.add(Calendar.DAY_OF_YEAR, 5);
                        Date tomorrow4 = calendar4.getTime();
                        String formattedDate5 = df.format(tomorrow4);

                        Calendar calendar5 = Calendar.getInstance();
                        calendar5.add(Calendar.DAY_OF_YEAR, 6);
                        Date tomorrow5 = calendar5.getTime();
                        String formattedDate6 = df.format(tomorrow5);
                        String time=getDate1(sessionManager.getSLOT1());
                        if(time.equals(formattedDate)){
                            if(isVisible==0) {
                              //  AnimateBell();

                                Fragment fragment1 = new FirstFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                            }
                            else {
                              ///  AnimateBell();
                            }

                        }
                        else if(time.equals(formattedDate1)){
                            if(isVisible==1) {
                              //  AnimateBell1();

                            }
                            else {
                              //  AnimateBell1();

                                Fragment fragment1 = new SecondFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate2)){
                            if(isVisible==2) {
                             //   AnimateBell2();

                            }
                            else {
                               // AnimateBell2();

                                Fragment fragment1 = new thirdFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate3)){
                            if(isVisible==3) {
                             //  AnimateBell3();

                            }
                            else {
                             //   AnimateBell3();

                                Fragment fragment1 = new fourthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate4)){
                            if(isVisible==4) {
                              //  AnimateBell4();

                            }
                            else {
                              //  AnimateBell4();

                                Fragment fragment1 = new fifthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate5)){
                            if(isVisible==5) {
                             //   AnimateBell5();

                            }
                            else {
                              //  AnimateBell5();

                                Fragment fragment1 = new sixthFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }
                        else if(time.equals(formattedDate6)){
                            if(isVisible==6) {
                              //  AnimateBell6();

                            }
                            else {
                               // AnimateBell6();

                                Fragment fragment1 = new SeventhFragment();


                                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                            }

                        }





                    }

                }


            }
            if(sessionManager.getFilterValue().equals("Breakfast Booking")){
                if(sessionManager.getDesign_Style().equals("breakfast")){
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df.format(tomorrow);


                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.add(Calendar.DAY_OF_YEAR, 2);
                    Date tomorrow1 = calendar1.getTime();
                    String formattedDate2 = df.format(tomorrow1);

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.add(Calendar.DAY_OF_YEAR, 3);
                    Date tomorrow2 = calendar2.getTime();
                    String formattedDate3 = df.format(tomorrow2);

                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.add(Calendar.DAY_OF_YEAR, 4);
                    Date tomorrow3 = calendar3.getTime();
                    String formattedDate4 = df.format(tomorrow3);

                    Calendar calendar4 = Calendar.getInstance();
                    calendar4.add(Calendar.DAY_OF_YEAR, 5);
                    Date tomorrow4 = calendar4.getTime();
                    String formattedDate5 = df.format(tomorrow4);

                    Calendar calendar5 = Calendar.getInstance();
                    calendar5.add(Calendar.DAY_OF_YEAR, 6);
                    Date tomorrow5 = calendar5.getTime();
                    String formattedDate6 = df.format(tomorrow5);

                    String time=getDate1(sessionManager.getSLOT1());
                    if(time.equals(formattedDate)){
                        if(isVisible==0) {
                           // AnimateBell();

                            Fragment fragment1 = new FirstFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                        }
                        else {
                           // AnimateBell();
                        }

                    }
                    else if(time.equals(formattedDate1)){
                        if(isVisible==1) {
                          //  AnimateBell1();

                        }
                        else {
                           // AnimateBell1();

                            Fragment fragment1 = new SecondFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                        }

                    }
                    else if(time.equals(formattedDate2)){
                        if(isVisible==2) {
                          //  AnimateBell2();

                        }
                        else {
                          //  AnimateBell2();

                            Fragment fragment1 = new thirdFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                        }

                    }
                    else if(time.equals(formattedDate3)){
                        if(isVisible==3) {
                           // AnimateBell3();

                        }
                        else {
                           //AnimateBell3();

                            Fragment fragment1 = new fourthFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                        }

                    }
                    else if(time.equals(formattedDate4)){
                        if(isVisible==4) {
                          //  AnimateBell4();

                        }
                        else {
                           // AnimateBell4();

                            Fragment fragment1 = new fifthFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                        }

                    }
                    else if(time.equals(formattedDate5)){
                        if(isVisible==5) {
                           // AnimateBell5();

                        }
                        else {
                           // AnimateBell5();

                            Fragment fragment1 = new sixthFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                        }

                    }
                    else if(time.equals(formattedDate6)){
                        if(isVisible==6) {
                           // AnimateBell6();

                        }
                        else {
                          //  AnimateBell6();

                            Fragment fragment1 = new SeventhFragment();


                            getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();


                        }

                    }





                }
                else if(sessionManager.getDesign_Style().equals("pool")){

                }  else if(sessionManager.getDesign_Style().equals("gym")){

                }
                else if(sessionManager.getDesign_Style().equals("meeting")){

                }
                else{

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

//    public void AnimateBell() {
//        homeicon.setVisibility(View.VISIBLE);
//        shufticon.setVisibility(View.INVISIBLE);
//        oneicon.setVisibility(View.INVISIBLE);
//        trwoicon.setVisibility(View.INVISIBLE);
//        threeicon.setVisibility(View.INVISIBLE);
//        fouricon.setVisibility(View.INVISIBLE);
//        fiveicon.setVisibility(View.INVISIBLE);
//
//
//        shake = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        homeicon.setAnimation(shake);
//
//    }
//    public void AnimateBell1() {
//        homeicon.setVisibility(View.INVISIBLE);
//        shufticon.setVisibility(View.VISIBLE);
//        oneicon.setVisibility(View.INVISIBLE);
//        trwoicon.setVisibility(View.INVISIBLE);
//        threeicon.setVisibility(View.INVISIBLE);
//        fouricon.setVisibility(View.INVISIBLE);
//        fiveicon.setVisibility(View.INVISIBLE);
//
//
//        shake1 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        shufticon.setAnimation(shake1);
//
//    }
//    public void AnimateBell2() {
//        homeicon.setVisibility(View.INVISIBLE);
//        shufticon.setVisibility(View.INVISIBLE);
//        oneicon.setVisibility(View.VISIBLE);
//        trwoicon.setVisibility(View.INVISIBLE);
//        threeicon.setVisibility(View.INVISIBLE);
//        fouricon.setVisibility(View.INVISIBLE);
//        fiveicon.setVisibility(View.INVISIBLE);
//
//
//        shake2 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        oneicon.setAnimation(shake2);
//
//    }
//    public void AnimateBell3() {
//        homeicon.setVisibility(View.INVISIBLE);
//        shufticon.setVisibility(View.INVISIBLE);
//        oneicon.setVisibility(View.INVISIBLE);
//        trwoicon.setVisibility(View.VISIBLE);
//        threeicon.setVisibility(View.INVISIBLE);
//        fouricon.setVisibility(View.INVISIBLE);
//        fiveicon.setVisibility(View.INVISIBLE);
//
//
//        shake3 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        trwoicon.setAnimation(shake3);
//
//    }
//    public void AnimateBell4() {
//        homeicon.setVisibility(View.INVISIBLE);
//        shufticon.setVisibility(View.INVISIBLE);
//        oneicon.setVisibility(View.INVISIBLE);
//        trwoicon.setVisibility(View.INVISIBLE);
//        threeicon.setVisibility(View.VISIBLE);
//        fouricon.setVisibility(View.INVISIBLE);
//        fiveicon.setVisibility(View.INVISIBLE);
//
//
//        shake4 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        threeicon.setAnimation(shake4);
//
//    }
//    public void AnimateBell5() {
//        homeicon.setVisibility(View.INVISIBLE);
//        shufticon.setVisibility(View.INVISIBLE);
//        oneicon.setVisibility(View.INVISIBLE);
//        trwoicon.setVisibility(View.INVISIBLE);
//        threeicon.setVisibility(View.INVISIBLE);
//        fouricon.setVisibility(View.VISIBLE);
//        fiveicon.setVisibility(View.INVISIBLE);
//
//
//        shake5 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        fouricon.setAnimation(shake5);
//
//    }
//    public void AnimateBell6() {
//        homeicon.setVisibility(View.INVISIBLE);
//        shufticon.setVisibility(View.INVISIBLE);
//        oneicon.setVisibility(View.INVISIBLE);
//        trwoicon.setVisibility(View.INVISIBLE);
//        threeicon.setVisibility(View.INVISIBLE);
//        fouricon.setVisibility(View.INVISIBLE);
//        fiveicon.setVisibility(View.VISIBLE);
//
//
//        shake6 = AnimationUtils.loadAnimation(Booking_Activity.this, R.anim.shakeanimation);
//        fiveicon.setAnimation(shake6);
//
//    }
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
    public class PagerAdapter extends FragmentStatePagerAdapter {

        int mNumOfTabs;
        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FirstFragment tab1 = new FirstFragment();
                    return tab1;
                case 1:
                    SecondFragment tab2 = new SecondFragment();
                    return tab2;
                case 2:
                    thirdFragment tab3 = new thirdFragment();
                    return tab3;
                    case 3:
                    fourthFragment tab4 = new fourthFragment();
                    return tab4;
                    case 4:
                    fifthFragment tab5 = new fifthFragment();
                    return tab5;
                    case 5:
                    sixthFragment tab6= new sixthFragment();
                    return tab6;
                    case 6:
                    SeventhFragment tab7 = new SeventhFragment();
                    return tab7;

                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
