package com.viralops.touchlessfoodordering.Mobile.Spa;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.squareup.picasso.Picasso;
import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.BuildConfig;

import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.Logout;
import com.viralops.touchlessfoodordering.Model.Spa_Categories;
import com.viralops.touchlessfoodordering.Model.Spa_Data;
import com.viralops.touchlessfoodordering.Model.Spa_dashboard;
import com.viralops.touchlessfoodordering.Model.Spa_withoutCategory;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;

import com.viralops.touchlessfoodordering.database.AlarmReceiver1;

import com.viralops.touchlessfoodordering.database.DbHelper1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Spa_Mobile extends AppCompatActivity implements View.OnClickListener{
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
    ArrayList<Spa_Categories> spa_categoriesArrayList=new ArrayList<>();
    ArrayList<Spa_Categories> spamenulist=new ArrayList<>();
    ArrayList<Spa_Data> spadataenulist=new ArrayList<>();
    Dialog menudialog;
    
    ShimmerRecyclerView shimmerRecyclerView;
    ShimmerRecyclerView shimmerRecyclerViewcategory;
    ShimmerRecyclerView shimmerRecyclerView1;
    SpaCAtegoryAdapter   spaCartegoryAdapter;
    SpaAdapter spaAdapter;

    RecyclerView recyclerView;
    Typeface font;
    Typeface font1;
    ArrayList<Spa_dashboard.Data> queuelist=new ArrayList<>();
    ArrayList<Spa_dashboard.Data> queuelistnew=new ArrayList<>();
    ArrayList<Spa_dashboard.Data> localarray=new ArrayList<>();

    SQLiteDatabase db;
    DbHelper1 mDbHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#091F42"));

        }
        // Set up the login form.
        setContentView(R.layout.associate_main1);
        mDbHelper = new DbHelper1(Spa_Mobile.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#091F42"));
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.mipmap.moreicon);
        toolbar.setOverflowIcon(drawable);
        sessionManager=new SessionManager(Spa_Mobile.this);
        sessionManagerFCM=new SessionManagerFCM(Spa_Mobile.this);
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
            home.setTextColor(Spa_Mobile.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));

            Fragment fragmentmanager = new Spa_Mobile_Dashboard();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();

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
            home.setTextColor(Spa_Mobile.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#707070"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            Fragment fragmentmanager = new Spa_Mobile_Dashboard();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();

        }
        if(view.getId()== R.id.eventlayout){
            isvisisble=false;

            Fragment fragmentmanager = new Spa_Dashboard_Clearance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();

            orders.setTextColor(Spa_Mobile.this.getResources().getColor(R.color.darkblue));
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
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId() ;
        switch (id){
            case R.id.menu:
                if (Network.isNetworkAvailable(Spa_Mobile.this)) {
                    new  SpaDatamenu().execute();

//

                } else if (Network.isNetworkAvailable2(Spa_Mobile.this)) {
                    new  SpaDatamenu().execute();



                }
                else{
           /* if (sessionManager.getIsINternet().equals("false")) {
                Intent intent = new Intent(Spa_Mobile.this, Internetconnection.class);
                startActivity(intent);

                sessionManager.setIsINternet("true");
                finish();

            } else {

            }*/
                }


              /*  final Dialog dialog1 = new Dialog(Spa_Mobile.this);
                // Include dialog.xml file

                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.menulist);
                int width12 = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                int height12 = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);
                dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                dialog1.getWindow().setLayout(width12, height12);

                dialog1.setCancelable(false);
                // Set dialog title
                dialog1.setTitle("");
                dialog1.show();
                shimmerRecyclerView = dialog1.findViewById(R.id.recyclerview);
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(Spa_Mobile.this, LinearLayoutManager.VERTICAL, false));
                TextView title = dialog1.findViewById(R.id.hotel);
                title.setTypeface(font);
                title.setText("SPA Menu");
                final MaterialButton menubutton = dialog1.findViewById(R.id.menubutton);
                final MaterialButton backbutton = dialog1.findViewById(R.id.closebutton);
                spaCartegoryAdapter = new SpaCAtegoryAdapter(spamenulist, Spa_Mobile.this);
                shimmerRecyclerView.setAdapter(spaCartegoryAdapter);
                EditText searchtext = dialog1.findViewById(R.id.searchtext);

                registerForContextMenu(menubutton);
                ImageView close = dialog1.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                menubutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menudialog = new Dialog(Spa_Mobile.this);
                        // Include dialog.xml file
                        menudialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        menudialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        menudialog.setContentView(R.layout.categorypopup);
                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);

                        menudialog.getWindow().setLayout(width, height);
                        menudialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

                        // dialog.setCancelable(true);
                        menudialog.setCanceledOnTouchOutside(true);
                        // setFinishOnTouchOutside(true);
                        // Set dialog title
                        menudialog.setTitle("Select Category");
                        menudialog.show();
                        recyclerView = menudialog.findViewById(R.id.recycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Spa_Mobile.this, LinearLayoutManager.VERTICAL, false));
                        SpaCartegoryAdapter menupopupadapeter = new SpaCartegoryAdapter(spa_categoriesArrayList, Spa_Mobile.this);
                        recyclerView.setAdapter(menupopupadapeter);
                        ImageView close = menudialog.findViewById(R.id.close);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                menudialog.dismiss();
                            }
                        });

                    }
                });*/


                break;
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
                        .replace(R.id.rootLayout, Spa_Mobile_History.newInstance())
                        .commitNow();
                break;

            case R.id.navigation_dashboard:
                final Dialog dialog = new Dialog(Spa_Mobile.this);
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
                        if(Network.isNetworkAvailable(Spa_Mobile.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(Spa_Mobile.this)){
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
        final Dialog dialog = new Dialog(Spa_Mobile.this);
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
                Spa_Mobile.this.finish();
                dialog.dismiss();
            }
        });

    }
    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
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
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

                Fragment fragment1 = new Spa_Mobile_Dashboard();


                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
            }
            else {
                AnimateBell();
                if(Network.isNetworkAvailable(Spa_Mobile.this)){
                    GetMenu();
                }
                else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                    GetMenu();

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

         shake = AnimationUtils.loadAnimation(Spa_Mobile.this, R.anim.shakeanimation);
        homeicon.setAnimation(shake);

    }

    // -------------Spa------------------------------//
    public class SpaDatamenu extends AsyncTask<String, String, String> {
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait..."); // set message
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();


            try {
                String credentials = Credentials.basic("admin", "LetsValet2You");

                Request request = new Request.Builder()
                        .url(BuildConfig.BASE_URL + BuildConfig.spa_menu)
                        .addHeader("Authorization", "Bearer " + sessionManager.getACCESSTOKEN())
                        .get()
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            spadataenulist.clear();
            if(progressDialog!=null){
                progressDialog.dismiss();
            }

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);
                        Spa_Data ird_data = new Spa_Data();
                        ird_data.setId(data.getString("id"));
                        ird_data.setName(data.getString("name"));
                        ird_data.setHotel_id(data.getString("hotel_id"));
                        ird_data.setDescription(data.getString("description"));
                        ird_data.setEnabled(data.getString("enabled"));
                        ird_data.setCreated_at(data.getString("created_at"));
                        ird_data.setUpdated_at(data.getString("updated_at"));
                        JSONArray jsonArray1 = data.getJSONArray("categories");
                        ird_data.setCategories(jsonArray1);

                       /* for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                            IRD_Category ird_category = new IRD_Category();
                            ird_category.setId(jsonObject1.getString("id"));
                            ird_category.setCreated_at(jsonObject1.getString("created_at"));
                            ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                            ird_category.setMenu_id(jsonObject1.getString("menu_id"));
                            ird_category.setDescription(jsonObject1.getString("description"));
                            ird_category.setEnabled(jsonObject1.getString("enabled"));
                            ird_category.setName(jsonObject1.getString("name"));
                            ird_category.setTags(jsonObject1.getString("tags"));
                            ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                            ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                            irdmenuslist.add(ird_category);
                            ird_data.setCategories(irdmenuslist);

                        }*/
                        spadataenulist.add(ird_data);


                    }
                    final Dialog dialog1 = new Dialog(Spa_Mobile.this);
                    // Include dialog.xml file

                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.menu_popuplist);
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                    int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);
                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog1.getWindow().setLayout(width, height);

                    dialog1.setCancelable(false);
                    // Set dialog title
                    dialog1.setTitle("");
                    dialog1.show();
                    shimmerRecyclerView = dialog1.findViewById(R.id.recyclerview);
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(Spa_Mobile.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog1.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("MENU");
                    spaAdapter = new SpaAdapter(spadataenulist, Spa_Mobile.this);
                    shimmerRecyclerView.setAdapter(spaAdapter);
                    /*EditText searchtext = dialog.findViewById(R.id.searchtext);
                    searchtext.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            filter1(s.toString());
                        }
                    });*/
                    //  registerForContextMenu(menubutton);
                    ImageView close = dialog1.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            if (Network.isNetworkAvailable(Spa_Mobile.this)) {
                               // new  SpaDatamenu().execute();

//

                            } else if (Network.isNetworkAvailable2(Spa_Mobile.this)) {
                              //  new  SpaDatamenu().execute();



                            }
                            else{
           /* if (sessionManager.getIsINternet().equals("false")) {
                Intent intent = new Intent(Spa_Mobile.this, Internetconnection.class);
                startActivity(intent);

                sessionManager.setIsINternet("true");
                finish();

            } else {

            }*/
                            }

                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public class SpaDatamenu1 extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();


            try {
                String credentials = Credentials.basic("admin", "LetsValet2You");

                Request request = new Request.Builder()
                        .url(BuildConfig.BASE_URL + BuildConfig.spa_menu)
                        .addHeader("Authorization", "Bearer " + sessionManager.getACCESSTOKEN())
                        .get()
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           // spamenulist.clear();
            spadataenulist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);
                        Spa_Data ird_data = new Spa_Data();
                        ird_data.setId(data.getString("id"));
                        ird_data.setName(data.getString("name"));
                        ird_data.setHotel_id(data.getString("hotel_id"));
                        ird_data.setDescription(data.getString("description"));
                        ird_data.setEnabled(data.getString("enabled"));
                        ird_data.setCreated_at(data.getString("created_at"));
                        ird_data.setUpdated_at(data.getString("updated_at"));
                        JSONArray jsonArray1 = data.getJSONArray("categories");
                        ird_data.setCategories(jsonArray1);

                       /* for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                            IRD_Category ird_category = new IRD_Category();
                            ird_category.setId(jsonObject1.getString("id"));
                            ird_category.setCreated_at(jsonObject1.getString("created_at"));
                            ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                            ird_category.setMenu_id(jsonObject1.getString("menu_id"));
                            ird_category.setDescription(jsonObject1.getString("description"));
                            ird_category.setEnabled(jsonObject1.getString("enabled"));
                            ird_category.setName(jsonObject1.getString("name"));
                            ird_category.setTags(jsonObject1.getString("tags"));
                            ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                            ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                            irdmenuslist.add(ird_category);
                            ird_data.setCategories(irdmenuslist);

                        }*/
                        spadataenulist.add(ird_data);


                    }
                   spaAdapter.notifyDataSetChanged();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public class SPAenu extends AsyncTask<String, String, String> {
        String id="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... params) {
            id=params[0];
            String result = "";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();


            try {
                String credentials = Credentials.basic("admin", "LetsValet2You");

                Request request = new Request.Builder()
                        .url(BuildConfig.BASE_URL + BuildConfig.spa_menu)
                        .addHeader("Authorization", "Bearer " + sessionManager.getACCESSTOKEN())
                        .get()
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            spamenulist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray data=jsonObject.getJSONArray("data");

                    for(int j=0;j<data.length();j++) {
                        JSONObject jsonObject2 = data.getJSONObject(j);
                        if(jsonObject2.getString("id").equals(id)){

                            for (int i = 0; i < jsonObject2.getJSONArray("categories").length(); i++) {
                                JSONObject jsonObject1 = jsonObject2.getJSONArray("categories").getJSONObject(i);

                                Spa_Categories ird_category = new Spa_Categories();
                                ird_category.setId(jsonObject1.getString("id"));
                                ird_category.setCreated_at(jsonObject1.getString("created_at"));
                                ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                                ird_category.setSpa_id(jsonObject1.getString("spa_id"));
                                ird_category.setDescription(jsonObject1.getString("description"));
                                ird_category.setEnabled(jsonObject1.getString("enabled"));
                                ird_category.setName(jsonObject1.getString("name"));
                                ird_category.setTags(jsonObject1.getString("tags"));
                                ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                                ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                                spamenulist.add(ird_category);

                                spa_categoriesArrayList.add(ird_category);
                            }


                        }
                        spaCartegoryAdapter.notifyDataSetChanged();



                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

   /* public class SpaIRDenu extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();


            try {
                String credentials = Credentials.basic("admin", "LetsValet2You");

                Request request = new Request.Builder()
                        .url(BuildConfig.BASE_URL + BuildConfig.spa_menu)
                        .addHeader("Authorization", "Bearer " + sessionManager.getACCESSTOKEN())
                        .get()
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            spamenulist.clear();
            spa_categoriesArrayList.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray data=jsonObject.getJSONArray("data");
                    for(int j=0;j<data.length();j++) {
                        JSONObject jsonObject2=data.getJSONObject(j);
                        Spa_Data ird_data = new Spa_Data();
                        ird_data.setId(jsonObject2.getString("id"));
                        ird_data.setName(jsonObject2.getString("name"));
                        ird_data.setHotel_id(jsonObject2.getString("hotel_id"));
                        ird_data.setDescription(jsonObject2.getString("description"));
                        ird_data.setEnabled(jsonObject2.getString("enabled"));
                        ird_data.setCreated_at(jsonObject2.getString("created_at"));
                        ird_data.setUpdated_at(jsonObject2.getString("updated_at"));
                        JSONArray jsonArray = jsonObject2.getJSONArray("categories");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Spa_Categories ird_category = new Spa_Categories();
                            ird_category.setId(jsonObject1.getString("id"));
                            ird_category.setCreated_at(jsonObject1.getString("created_at"));
                            ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                            ird_category.setSpa_id(jsonObject1.getString("spa_id"));
                            ird_category.setDescription(jsonObject1.getString("description"));
                            ird_category.setEnabled(jsonObject1.getString("enabled"));
                            ird_category.setName(jsonObject1.getString("name"));
                            ird_category.setTags(jsonObject1.getString("tags"));
                            ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                            ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                            spamenulist.add(ird_category);
                            spa_categoriesArrayList.add(ird_category);
                        }
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public class SpaIRDenunew extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();


            try {
                String credentials = Credentials.basic("admin", "LetsValet2You");

                Request request = new Request.Builder()
                        .url(BuildConfig.BASE_URL + BuildConfig.spa_menu)
                        .addHeader("Authorization", "Bearer " + sessionManager.getACCESSTOKEN())
                        .get()
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            spamenulist.clear();
            spa_categoriesArrayList.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray data=jsonObject.getJSONArray("data");
                    for(int j=0;j<data.length();j++) {
                        JSONObject jsonObject2=data.getJSONObject(j);
                        Spa_Data ird_data = new Spa_Data();
                        ird_data.setId(jsonObject2.getString("id"));
                        ird_data.setName(jsonObject2.getString("name"));
                        ird_data.setHotel_id(jsonObject2.getString("hotel_id"));
                        ird_data.setDescription(jsonObject2.getString("description"));
                        ird_data.setEnabled(jsonObject2.getString("enabled"));
                        ird_data.setCreated_at(jsonObject2.getString("created_at"));
                        ird_data.setUpdated_at(jsonObject2.getString("updated_at"));
                        JSONArray jsonArray = jsonObject2.getJSONArray("categories");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Spa_Categories ird_category = new Spa_Categories();
                            ird_category.setId(jsonObject1.getString("id"));
                            ird_category.setCreated_at(jsonObject1.getString("created_at"));
                            ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                            ird_category.setSpa_id(jsonObject1.getString("spa_id"));
                            ird_category.setDescription(jsonObject1.getString("description"));
                            ird_category.setEnabled(jsonObject1.getString("enabled"));
                            ird_category.setName(jsonObject1.getString("name"));
                            ird_category.setTags(jsonObject1.getString("tags"));
                            ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                            ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                            spamenulist.add(ird_category);
                            spa_categoriesArrayList.add(ird_category);
                        }
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }*/
    private void SpaGetMenuitemenab(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemEnabledspa("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.Spa_item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Spa_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void SpaGetMenuitemdisable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemDisabledspa("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.spa_disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Spa_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void SpaGetMenucategoryenable(final String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategoryEnabledspa("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.spa_enable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Spa_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void SpaGetMenucategorydisable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategorydisabledspa("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.spa_disable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Spa_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        new SPAenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

    //------------End of Laundry--------------------------------------//
    //-----------------LaundryAdpater-----------------------------------//

    public class SpaCAtegoryAdapter extends  RecyclerView.Adapter<SpaCAtegoryAdapter.ViewHolder>{
        ArrayList<Spa_Categories> order_items;
        Context context;
        String enabled;
        ArrayList<Spa_withoutCategory> iRd_subCategories;
        String id;

        public SpaCAtegoryAdapter(ArrayList<Spa_Categories> order_items, Context context,String id) {
            this.order_items = order_items;
            this.context = context;
            this.id=id;
        }

        @NonNull
        @Override
        public SpaCAtegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenuparent, parent, false);
            return new SpaCAtegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Spa_Categories> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final SpaCAtegoryAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            if(!holder.mitem.getDescription().equals("null")){
                holder.descriprtion.setText(holder.mitem.getDescription());

            }
            else{
                holder.descriprtion.setText("");

            }

            if(holder.mitem.getEnabled().equals("1")){
                holder.toggle.setOnCheckedChangeListener(null);
                holder.toggle.setChecked(true);
                enabled="1";
            }
            else{
                holder.toggle.setOnCheckedChangeListener(null);
                holder.toggle.setChecked(false);
                enabled="0";

            }
            holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if (Network.isNetworkAvailable(context)) {
                            SpaGetMenucategoryenable(holder.mitem.getId(),id);
                            enabled="1";
                        }  else if (Network.isNetworkAvailable2(context)) {
                            SpaGetMenucategoryenable(holder.mitem.getId(),id);
                            enabled="1";

                        }
                        else{

                        }
                    }
                    else{
                        if (Network.isNetworkAvailable(context)) {
                            SpaGetMenucategorydisable(holder.mitem.getId(),id);
                            enabled="0";

                        }  else if (Network.isNetworkAvailable2(context)) {
                            SpaGetMenucategorydisable(holder.mitem.getId(),id);
                            enabled="0";

                        }
                        else{

                        }
                    }
                }
            });
            iRd_subCategories=new ArrayList<>();
            iRd_subCategories.clear();
          /*  if(holder.mitem.getWithout_sub_category_items().length()!=0) {


                IRd_SubCategory iRd_subCategory = new IRd_SubCategory();
                iRd_subCategory.setId("");
                iRd_subCategory.setMenu_category_id("");
                iRd_subCategory.setName("");
                iRd_subCategory.setDescription("");
                iRd_subCategory.setMenu_category_id("");
                iRd_subCategory.setEnabled("1");
                iRd_subCategory.setCreated_at("");
                iRd_subCategory.setUpdated_at("");
                iRd_subCategory.setItems(holder.mitem.getWithout_sub_category_items());
                iRd_subCategories.add(iRd_subCategory);


            }
            if(holder.mitem.getSub_categories().length()!=0) {

                for (int i = 0; i < holder.mitem.getSub_categories().length(); i++) {
                    try {
                        JSONObject jsonObject = holder.mitem.getSub_categories().getJSONObject(i);
                        IRd_SubCategory iRd_subCategory = new IRd_SubCategory();
                        iRd_subCategory.setId(jsonObject.getString("id"));
                        iRd_subCategory.setMenu_category_id(jsonObject.getString("menu_category_id"));
                        iRd_subCategory.setName(jsonObject.getString("name"));
                        iRd_subCategory.setDescription(jsonObject.getString("description"));
                        iRd_subCategory.setMenu_category_id(jsonObject.getString("tags"));
                        iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                        iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                        iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                        iRd_subCategory.setItems(jsonObject.getJSONArray("items"));
                        iRd_subCategories.add(iRd_subCategory);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


            MinibarIRD_SubCATegoryAdapter order_itemAdapterdetail = new MinibarIRD_SubCATegoryAdapter(iRd_subCategories, context, holder.mitem.getName(), enabled);
            holder.shimmerRecyclerView.setAdapter(order_itemAdapterdetail);
 */         /*  iRd_subCategories=new ArrayList<>();
            iRd_subCategories.clear();
            if(holder.mitem.getSub_categories().length()!=0) {

                for (int i = 0; i < holder.mitem.getSub_categories().length(); i++) {
                    try {
                        JSONObject jsonObject = holder.mitem.getSub_categories().getJSONObject(i);
                        IRd_SubCategory iRd_subCategory = new IRd_SubCategory();
                        iRd_subCategory.setId(jsonObject.getString("id"));
                        iRd_subCategory.setMenu_category_id(jsonObject.getString("menu_category_id"));
                        iRd_subCategory.setName(jsonObject.getString("name"));
                        iRd_subCategory.setDescription(jsonObject.getString("description"));
                        iRd_subCategory.setMenu_category_id(jsonObject.getString("tags"));
                        iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                        iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                        iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                        iRd_subCategory.setItems(jsonObject.getJSONArray("items"));
                        iRd_subCategories.add(iRd_subCategory);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                IRD_SubCATegoryAdapter order_itemAdapterdetail = new IRD_SubCATegoryAdapter(iRd_subCategories, context, holder.mitem.getName(), enabled);
                holder.shimmerRecyclerView.setAdapter(order_itemAdapterdetail);



            }
            iRd_withCategories=new ArrayList<>();
            iRd_withCategories.clear();

            if(holder.mitem.getWithout_sub_category_items().length()!=0) {

                for (int i = 0; i < holder.mitem.getWithout_sub_category_items().length(); i++) {
                    try {
                        JSONObject jsonObject = holder.mitem.getWithout_sub_category_items().getJSONObject(i);
                        IRD_Item iRd_subCategory=new IRD_Item();
                        iRd_subCategory.setId(jsonObject.getString("id"));
                        iRd_subCategory.setMenu_category_id(jsonObject.getString("menu_category_id"));
                        iRd_subCategory.setName(jsonObject.getString("name"));
                        iRd_subCategory.setDescription(jsonObject.getString("description"));
                        iRd_subCategory.setTags(jsonObject.getString("tags"));
                        iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                        iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                        iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                        iRd_subCategory.setType(jsonObject.getString("type"));
                        iRd_subCategory.setPrice(jsonObject.getString("price"));
                        iRd_subCategory.setThumbnail(jsonObject.getString("thumbnail"));
                        iRd_subCategory.setAddons(jsonObject.getJSONArray("addons"));
                        iRd_subCategory.setMenu_sub_category_id(jsonObject.getString("menu_sub_category_id"));
                        iRd_withCategories.add(iRd_subCategory);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                IRD_ItemsWithoutAdapter order_itemAdapterdetail = new IRD_ItemsWithoutAdapter(iRd_withCategories, context, holder.mitem.getName(), enabled);
                holder.shimmerRecyclerView.setAdapter(order_itemAdapterdetail);

            }
*/
            for(int i=0;i<holder.mitem.getWithout_sub_category_items().length();i++){
                try {
                    JSONObject jsonObject=holder.mitem.getWithout_sub_category_items().getJSONObject(i);
                    Spa_withoutCategory iRd_subCategory=new Spa_withoutCategory();
                    iRd_subCategory.setId(jsonObject.getString("id"));
                    iRd_subCategory.setSpa_category_id(jsonObject.getString("spa_category_id"));
                    iRd_subCategory.setName(jsonObject.getString("name"));
                    iRd_subCategory.setDescription(jsonObject.getString("description"));
                    iRd_subCategory.setTags(jsonObject.getString("tags"));
                    iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                    iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                    iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                    iRd_subCategory.setPrice(jsonObject.getString("price"));
                    iRd_subCategory.setThumbnail(jsonObject.getString("thumbnail"));
                    iRd_subCategory.setSpa_id(jsonObject.getString("spa_id"));
                    iRd_subCategory.setSpa_sub_category_id(jsonObject.getString("spa_sub_category_id"));
                    iRd_subCategories.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            SpaIRD_ItemsAdapter order_itemAdapterdetail=new SpaIRD_ItemsAdapter(iRd_subCategories,context,holder.mitem.getName(),enabled,id);
            holder.shimmerRecyclerView.setAdapter(order_itemAdapterdetail);



        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ToggleButton toggle;
            ShimmerRecyclerView shimmerRecyclerView;
            TextView descriprtion;


            Spa_Categories mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.category);
                descriprtion=itemView.findViewById(R.id.descriprtion);

                toggle=itemView.findViewById(R.id.toggleButton1);
                shimmerRecyclerView=itemView.findViewById(R.id.rv_child);
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

                final Typeface font = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Regular.ttf");
                Typeface font1 = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Thin.ttf");

                title.setTypeface(font);
                descriprtion.setTypeface(font);

            }
        }



    }
    public class SpaIRD_ItemsAdapter extends  RecyclerView.Adapter<SpaIRD_ItemsAdapter.ViewHolder>{
        ArrayList<Spa_withoutCategory> order_items;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String menuid;

        public SpaIRD_ItemsAdapter(ArrayList<Spa_withoutCategory> order_items, Context context,String category,String enabled,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.menuid=menuid;
        }

        @NonNull
        @Override
        public SpaIRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spa_item, parent, false);
            return new SpaIRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Spa_withoutCategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final SpaIRD_ItemsAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            holder.price.setText(getResources().getString(R.string.Rs));
            holder.price1.setText(holder.mitem.getPrice());

            if(!holder.mitem.getDescription().equals("null")){
                holder.ingredinets.setText(holder.mitem.getDescription());

            }
            else{
                holder.ingredinets.setText("");

            }

            if(enabled.equals("1")){
                holder.toggleButton1.setEnabled(true);
                holder.toggleButton1.setClickable(true);
                if (holder.mitem.getEnabled().equals("1")) {
                    holder.toggleButton1.setChecked(true);
                    holder.gradeout.setVisibility(View.GONE);
                } else {
                    holder.toggleButton1.setChecked(false);
                    holder.gradeout.setVisibility(View.VISIBLE);

                }


            }
            else{

                holder.toggleButton1.setChecked(false);
                holder.toggleButton1.setEnabled(false);
                holder.toggleButton1.setClickable(false);
                holder.gradeout.setVisibility(View.VISIBLE);
            }



            if (!holder.mitem.getThumbnail().equals("null")) {
                // byte[] decodedString = Base64.decode(holder.mitem.getImage(), Base64.DEFAULT);
             /*   String base64Image = holder.mitem.getThumbnail().split(",")[1];

                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                holder.dish.setImageBitmap(decodedByte);*/
                Picasso.with(context).load(BuildConfig.imageurl+holder.mitem.getThumbnail()).into(holder.dish);
                //holder.dish.setVisibility(View.VISIBLE);
                holder.dish.setVisibility(View.VISIBLE);

            }
            else{
                holder.dish.setVisibility(View.GONE);
            }
            holder.category.setVisibility(View.GONE);

            if (!holder.mitem.getTags().equals("null")) {
                String strings=holder.mitem.getTags().replaceAll("/","").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"","");

                //Split string with comma

                String[] namesArray = strings.split(",");
                Arrays.asList(namesArray);
                LaundryTagsAdapter tagsAdapter = new LaundryTagsAdapter(namesArray, context);
                holder.spicy.setAdapter(tagsAdapter);
                holder.spicy.setVisibility(View.VISIBLE);

                // byte[] decodedString = Base64.decode(holder.mitem.getImage(), Base64.DEFAULT);
            }
            else{
                holder.spicy.setVisibility(View.GONE);
            }


           /* if(holder.mitem.getImage().toString().equals(null)||holder.mitem.getImage().toString().equals("")){
            }
            else {
                holder.dish.setVisibility(View.VISIBLE);
            } */
            holder.toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        holder.gradeout.setVisibility(View.GONE);
                        if (Network.isNetworkAvailable(context)){
                            SpaGetMenuitemenab(holder.mitem.getId(),menuid);
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            SpaGetMenuitemenab(holder.mitem.getId(),menuid);

                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            SpaGetMenuitemdisable(holder.mitem.getId(),menuid);

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            SpaGetMenuitemdisable(holder.mitem.getId(),menuid);


                        }
                        else{

                        }
                    }
                }
            });



        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView category1;
            TextView price1;
            TextView price;
            TextView customise;
            TextView ingredinets;
            ImageView dish;
            ImageView category;
            RecyclerView spicy;
            RecyclerView customiselist;
            LinearLayout gradeout;
            ToggleButton toggleButton1;


            Spa_withoutCategory mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                category1=itemView.findViewById(R.id.category1);
                category=itemView.findViewById(R.id.category);
                price=itemView.findViewById(R.id.price);
                price1=itemView.findViewById(R.id.price1);
                ingredinets=itemView.findViewById(R.id.ingredinets);
                spicy=itemView.findViewById(R.id.spicy);
                spicy.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
                dish=itemView.findViewById(R.id.dish);
                gradeout=itemView.findViewById(R.id.gradeout);
                toggleButton1=itemView.findViewById(R.id.toggleButton1);

                final Typeface font = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Regular.ttf");
                Typeface font1 = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Thin.ttf");

                title.setTypeface(font1);
                category1.setTypeface(font);
                price1.setTypeface(font);
                price.setTypeface(font);
                ingredinets.setTypeface(font1);
            }
        }



    }



    public class SpaTagsAdapter extends  RecyclerView.Adapter<SpaTagsAdapter.ViewHolder>{
        String[] order_items;
        Context context;
        String enabled;

        public SpaTagsAdapter(String[] order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public SpaTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout, parent, false);
            return new SpaTagsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SpaTagsAdapter.ViewHolder holder, int position) {


            holder.title.setText(capitailizeWord(order_items[position]));
            if(order_items[position].equals("Non-veg")||order_items[position].equals("non-veg")||order_items[position].equals("NON-VEG")){
                holder.title.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

            }
            else {
                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                holder.title.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }

        }

        @Override
        public int getItemCount() {
            return order_items.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Chip title;
            String[] mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.chipentry);


                final Typeface font = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Regular.ttf");
                Typeface font1 = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Thin.ttf");

                title.setTypeface(font);

            }
        }



    }

    public class SpaCartegoryAdapter extends  RecyclerView.Adapter<SpaCartegoryAdapter.ViewHolder>{
        ArrayList<Spa_Categories> order_items;
        Context context;

        public SpaCartegoryAdapter(ArrayList<Spa_Categories> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public SpaCartegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item, parent, false);
            return new SpaCartegoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SpaCartegoryAdapter.ViewHolder holder, final int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(String.valueOf(holder.mitem.getWithout_sub_category_items().length()));


        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView category1;



            Spa_Categories mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                category1=itemView.findViewById(R.id.count);

                final Typeface font = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Regular.ttf");
                Typeface font1 = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Thin.ttf");

                title.setTypeface(font);
                category1.setTypeface(font);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shimmerRecyclerViewcategory.smoothScrollToPosition(getAdapterPosition());
                        menudialog.dismiss();

                    }
                });

            }
        }



    }
    public class SpaAdapter extends  RecyclerView.Adapter<SpaAdapter.ViewHolder>{
        ArrayList<Spa_Data> order_items;
        Context context;
        String enabled;

        public SpaAdapter(ArrayList<Spa_Data> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public SpaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenu, parent, false);
            return new SpaAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Spa_Data> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            if(!holder.mitem.getDescription().equals("null")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.descriprtion.setText(Html.fromHtml(holder.mitem.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                }
                else{
                    holder.descriprtion.setText(Html.fromHtml(holder.mitem.getDescription()));

                }

            }
            else{
                holder.descriprtion.setText("");

            }
            if(holder.mitem.getEnabled().equals("1")){
                holder.toggle.setOnCheckedChangeListener(null);
                holder.toggle.setChecked(true);
                enabled="1";
                holder.gradeout.setVisibility(View.GONE);

            }
            else{
                holder.toggle.setOnCheckedChangeListener(null);
                holder.toggle.setChecked(false);
                enabled="0";
                holder.gradeout.setVisibility(View.VISIBLE);


            }
            holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if (Network.isNetworkAvailable(context)) {
                            GetMenuenable(holder.mitem.getId());
                            enabled="1";
                            holder.gradeout.setVisibility(View.GONE);
                        }  else if (Network.isNetworkAvailable2(context)) {
                            GetMenuenable(holder.mitem.getId());
                            enabled="1";
                            holder.gradeout.setVisibility(View.GONE);


                        }
                        else{

                        }
                    }
                    else{
                        if (Network.isNetworkAvailable(context)) {
                            GetMenudisable(holder.mitem.getId());
                            enabled="0";
                            holder.gradeout.setVisibility(View.VISIBLE);


                        }  else if (Network.isNetworkAvailable2(context)) {
                            GetMenudisable(holder.mitem.getId());
                            holder.gradeout.setVisibility(View.VISIBLE);

                            enabled="0";

                        }
                        else{

                        }
                    }
                }
            });




            /*irdcAtegoryAdapter  = new IRDCAtegoryAdapter(holder.mitem.getCategories(), context);
            holder.shimmerRecyclerView.setAdapter(irdcAtegoryAdapter);
*/



        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ToggleButton toggle;
            ShimmerRecyclerView shimmerRecyclerView;
            TextView descriprtion;
            LinearLayout gradeout;
            Spa_Data mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.category);
                descriprtion=itemView.findViewById(R.id.descriprtion);
                gradeout=itemView.findViewById(R.id.gradeout);

                toggle=itemView.findViewById(R.id.toggleButton1);
                shimmerRecyclerView=itemView.findViewById(R.id.rv_child);
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

                final Typeface font = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Regular.ttf");
                Typeface font1 = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Thin.ttf");

                title.setTypeface(font);
                descriprtion.setTypeface(font);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spamenulist.clear();
                        for (int j = 0; j < order_items.get(getAdapterPosition()).getCategories().length(); j++) {
                            JSONObject jsonObject1 = null;
                            try {
                                jsonObject1 = order_items.get(getAdapterPosition()).getCategories().getJSONObject(j);
                                Spa_Categories ird_category = new Spa_Categories();
                                ird_category.setId(jsonObject1.getString("id"));
                                ird_category.setCreated_at(jsonObject1.getString("created_at"));
                                ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                                ird_category.setSpa_id(jsonObject1.getString("spa_id"));
                                ird_category.setDescription(jsonObject1.getString("description"));
                                ird_category.setEnabled(jsonObject1.getString("enabled"));
                                ird_category.setName(jsonObject1.getString("name"));
                                ird_category.setTags(jsonObject1.getString("tags"));
                                ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                                ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                                spamenulist.add(ird_category);
                                spa_categoriesArrayList.add(ird_category);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                     /*   if(Network.isNetworkAvailable(context)){
                            new IRDenu().execute(order_items.get(getAdapterPosition()).getId());
                        } else if (Network.isNetworkAvailable2(context)) {

                            new IRDenu().execute(order_items.get(getAdapterPosition()).getId());

                        }
                        else{

                        }*/
                        if(spamenulist.size()!=0){
                            if(order_items.get(getAdapterPosition()).getEnabled().equals("1")) {
                                final Dialog dialog = new Dialog(Spa_Mobile.this);
                                // Include dialog.xml file

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.menulist);
                                int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                                int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);
                                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog.getWindow().setLayout(width1, height1);

                                dialog.setCancelable(false);
                                // Set dialog title
                                dialog.setTitle("");
                                dialog.show();
                                shimmerRecyclerViewcategory = dialog.findViewById(R.id.recyclerview);
                                shimmerRecyclerViewcategory.setLayoutManager(new LinearLayoutManager(Spa_Mobile.this, LinearLayoutManager.VERTICAL, false));
                                TextView title = dialog.findViewById(R.id.hotel);
                                title.setTypeface(font);
                                title.setText(order_items.get(getAdapterPosition()).getName());
                                final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                                final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                                spaCartegoryAdapter = new SpaCAtegoryAdapter(spamenulist, Spa_Mobile.this, order_items.get(getAdapterPosition()).getId());
                                shimmerRecyclerViewcategory.setAdapter(spaCartegoryAdapter);
                                registerForContextMenu(menubutton);
                                ImageView close = dialog.findViewById(R.id.close);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        if (Network.isNetworkAvailable(Spa_Mobile.this)) {
                                            // new IRDenu().execute();
                                            // dialog.dismiss();

                                        } else if (Network.isNetworkAvailable2(Spa_Mobile.this)) {
                                            //  new IRDenu().execute();

                                        } else {

                                        }
                                    }
                                });

                                menubutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        menudialog = new Dialog(Spa_Mobile.this);
                                        // Include dialog.xml file
                                        menudialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                        menudialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                                        menudialog.setContentView(R.layout.categorypopup);
                                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                                        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);

                                        menudialog.getWindow().setLayout(width, height);
                                        menudialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

                                        // dialog.setCancelable(true);
                                        menudialog.setCanceledOnTouchOutside(true);
                                        // setFinishOnTouchOutside(true);
                                        // Set dialog title
                                        menudialog.setTitle("Select Category");
                                        menudialog.show();
                                        recyclerView = menudialog.findViewById(R.id.recycler);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(Spa_Mobile.this, LinearLayoutManager.VERTICAL, false));

                                        SpaCartegoryAdapter   menupopupadapeter = new SpaCartegoryAdapter(spamenulist, Spa_Mobile.this);
                                        recyclerView.setAdapter(menupopupadapeter);
                                        ImageView close = menudialog.findViewById(R.id.close);
                                        close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                menudialog.dismiss();
                                            }
                                        });

                                    }
                                });

                            }
                            else{
                                Toast.makeText(context,"Menu is disabled",Toast.LENGTH_SHORT).show();

                            }

                        }
                        else{
                            Toast.makeText(context,"No items available",Toast.LENGTH_SHORT).show();
                        }




                    }
                });

            }
        }



    }
    private void GetMenuenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getEnabledspa("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.spa_menu_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Spa_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        new SpaDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        new SpaDatamenu1().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenudisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Spa_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getDisablespa("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.spa_menu_disable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Spa_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        new SpaDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        new SpaDatamenu1().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    //-------------End id Spaadapter------------------------------//

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

        (RetrofitClientInstance.getApiService().Spa_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header  login = response.body();
                    newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                    newcountevent.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");


                }
                else if(response.code()==401){
                    Header login = response.body();
                    Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(Spa_Mobile.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Header> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
    public class LaundryTagsAdapter extends  RecyclerView.Adapter<LaundryTagsAdapter.ViewHolder>{
        String[] order_items;
        Context context;
        String enabled;

        public LaundryTagsAdapter(String[] order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public LaundryTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout, parent, false);
            return new LaundryTagsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final LaundryTagsAdapter.ViewHolder holder, int position) {


            holder.title.setText(capitailizeWord(order_items[position]));
            if(order_items[position].equals("Non-veg")||order_items[position].equals("non-veg")||order_items[position].equals("NON-VEG")){
                holder.title.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));

            }
            else {
                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                holder.title.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            }

        }

        @Override
        public int getItemCount() {
            return order_items.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Chip title;
            String[] mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.chipentry);


                final Typeface font = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Regular.ttf");
                Typeface font1 = Typeface.createFromAsset(
                        getAssets(),
                        "font/Roboto-Thin.ttf");

                title.setTypeface(font);

            }
        }



    }
    private void setAlarm1(Spa_dashboard.Data laundry_dashboard) {
        mDbHelper = new DbHelper1(Spa_Mobile.this);

        db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(mDbHelper.TITLE, laundry_dashboard.getPrimises().getPremise_no());
        cv.put(mDbHelper.O_ID,laundry_dashboard.getOrder_detail().getOrder_id());
        cv.put(mDbHelper.DETAIL, laundry_dashboard.getOrder_spa_items().toString());
        cv.put(mDbHelper.TYPE, "Urgent");
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            Date date=formatter1.parse(laundry_dashboard.getDatestring());

            Calendar calender = Calendar.getInstance();
            calender.clear();
            calender.setTime(date);

            System.out.println("time"+String.valueOf(laundry_dashboard.getMonth())+" "+String.valueOf(laundry_dashboard.getYear())+" "+laundry_dashboard.getDate());

            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
            String timeString = formatter.format(new Date(calender.getTimeInMillis()));
            SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = dateformatter.format(new Date(calender.getTimeInMillis()));
            System.out.println("time1 "+timeString+" "+dateString);
            int alarmid=0;
            try{
                alarmid=Integer.parseInt(laundry_dashboard.getOrder_detail().getOrder_id());
            }catch (Exception e){

            }

            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(Spa_Mobile.this, AlarmReceiver1.class);
            long time=60*1000;

            //String alertTitle = mTitleText.getText().toString();
            intent.putExtra("title", laundry_dashboard.getPrimises().getPremise_no());
            intent.putExtra("time", timeString);
            intent.putExtra("alramid",String.valueOf(alarmid));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(Spa_Mobile.this, alarmid, intent, 0);

            alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

            cv.put(mDbHelper.TIME, timeString);
            cv.put(mDbHelper.DATE, dateString);
            db.insert(mDbHelper.TABLE_NAME, null, cv);

        }catch (Exception e){

        }

    }
    private void GetMenu() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Spa_getOrders("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Spa_dashboard>() {
            @Override
            public void onResponse(@NonNull Call<Spa_dashboard> call, @NonNull Response<Spa_dashboard> response) {

                if(response.code()==201||response.code()==200){
                    Spa_dashboard  login = response.body();
                    queuelist=new ArrayList<>();
                    queuelistnew=new ArrayList<>();
                    localarray.clear();
                    db= mDbHelper.getWritableDatabase();
                    localarray=mDbHelper.getAllCotacts();
                    System.out.println("this is a local array"+localarray.size());

                    queuelist=login.getData();
                    if(queuelist.size()!=0){
                        for(int i=0;i<queuelist.size();i++){
                            if(queuelist.get(i).getStatus().equals("new_order")){
                                Spa_dashboard.Data data=new Spa_dashboard.Data();
                                data.setCreated_at(queuelist.get(i).getCreated_at());
                                data.setDescription(queuelist.get(i).getDescription());
                                data.setHotel_id(queuelist.get(i).getHotel_id());
                                data.setId(queuelist.get(i).getId());
                                data.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                data.setGuest(queuelist.get(i).getGuest());
                                data.setOrder_detail(queuelist.get(i).getOrder_detail());
                                data.setOrder_spa_items(queuelist.get(i).getOrder_spa_items());
                                data.setPayment_status(queuelist.get(i).getPayment_status());
                                data.setPrimises(queuelist.get(i).getPrimises());
                                data.setPrimises_id(queuelist.get(i).getPrimises_id());
                                data.setStatus(queuelist.get(i).getStatus());
                                data.setType(queuelist.get(i).getType());
                                data.setUpdated_at(queuelist.get(i).getUpdated_at());
                                queuelistnew.add(data);
                            }
                        }


                        if(localarray.size()==0) {
                            for (int i = 0; i < queuelist.size(); i++) {

                                long timeDiff = 0;
                                Date date1 = null;
                                Date date2 = null;
                                final Calendar calendar = Calendar.getInstance();
                                String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                                System.out.println("current date" + timeStamp + " " + queuelist.get(i).getOrder_detail().getRequest_schedule_at());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                                long epoch = 0;

                                try {
                                    dt = dateFormat.parse(queuelist.get(i).getOrder_detail().getRequest_schedule_at());
                                    epoch = dt.getTime();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(epoch);
                                String date23 = DateFormat.format("dd MMM yyyy hh:mm a", dt).toString();
                                try {
                                    date1 = formatter.parse(timeStamp);
                                    date2 = formatter.parse(date23);
                                    System.out.println("current datem 2 " + date1 + " " + date2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    System.out.println("current datem 2 " + e.toString());

                                }  //  timeDiff = date1.getTime() - date2.getTime();

                                timeDiff = (date2.getTime() - date1.getTime());
                                System.out.println(" time diff"
                                        + timeDiff);

                                Calendar cal1 = Calendar.getInstance();
                                cal1.setTime(date2);
                                int hour = cal1.get(Calendar.HOUR);
                                int min = cal1.get(Calendar.MINUTE);
                                int date = cal1.get(Calendar.DAY_OF_MONTH);
                                int month = cal1.get(Calendar.MONTH);
                                int year = cal1.get(Calendar.YEAR);
                                Spa_dashboard.Data alarm = new Spa_dashboard.Data();
                                alarm.setDate(date);
                                alarm.setMonth(month);
                                alarm.setYear(year);
                                alarm.setHour(hour);
                                alarm.setMinute(min);
                                alarm.setDatestring(date23);
                                alarm.setO_id(queuelist.get(i).getOrder_detail().getOrder_id());
                                alarm.setCreated_at(queuelist.get(i).getCreated_at());
                                alarm.setGuest(queuelist.get(i).getGuest());
                                alarm.setDescription(queuelist.get(i).getDescription());
                                alarm.setHotel_id(queuelist.get(i).getHotel_id());
                                alarm.setId(queuelist.get(i).getId());
                                alarm.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                alarm.setOrder_detail(queuelist.get(i).getOrder_detail());
                                alarm.setOrder_spa_items(queuelist.get(i).getOrder_spa_items());
                                alarm.setPayment_status(queuelist.get(i).getPayment_status());
                                alarm.setPrimises_id(queuelist.get(i).getPrimises_id());
                                alarm.setStatus(queuelist.get(i).getStatus());
                                alarm.setType(queuelist.get(i).getType());
                                alarm.setUpdated_at(queuelist.get(i).getUpdated_at());
                                alarm.setPrimises(queuelist.get(i).getPrimises());
                                setAlarm1(alarm);

                            }
                        }
                        else{
                            ArrayList<Spa_dashboard.Data> datalist=new ArrayList<>();
                            for(int j=0;j<localarray.size();j++){
                                for(int i=0;i<queuelist.size();i++){
                                    if(localarray.get(j).getO_id().contains(queuelist.get(i).getOrder_detail().getOrder_id())){
                                        queuelist.remove(i);


                                    }

                                }
                            }

                            datalist=queuelist;

                            System.out.println("data "+datalist.size());
                            for(int i=0;i<datalist.size();i++){
                                long timeDiff = 0;
                                Date date1 = null;
                                Date date2 = null;
                                final Calendar calendar = Calendar.getInstance();
                                String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                                System.out.println("current date" + timeStamp + " " + datalist.get(i).getOrder_detail().getRequest_schedule_at());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                                long epoch = 0;

                                try {
                                    dt = dateFormat.parse(datalist.get(i).getOrder_detail().getRequest_schedule_at());
                                    epoch = dt.getTime();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(epoch);
                                String date23 = DateFormat.format("dd MMM yyyy hh:mm a", dt).toString();
                                try {
                                    date1 = formatter.parse(timeStamp);
                                    date2 = formatter.parse(date23);
                                    System.out.println("current datem 2 " + date1 + " " + date2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    System.out.println("current datem 2 " + e.toString());

                                }  //  timeDiff = date1.getTime() - date2.getTime();

                                timeDiff = (date2.getTime() - date1.getTime());
                                System.out.println(" time diff"
                                        + timeDiff);
                                String[] dateformat=date23.split(" ");
                                Calendar cal1 = Calendar.getInstance();
                                cal1.setTime(date2);
                                int hour = cal1.get(Calendar.HOUR);
                                int min = cal1.get(Calendar.MINUTE);
                                int date = cal1.get(Calendar.DAY_OF_MONTH);
                                int month = cal1.get(Calendar.MONTH+1);
                                int year = cal1.get(Calendar.YEAR);
                                Spa_dashboard.Data alarm = new Spa_dashboard.Data();
                                alarm.setDate(date);
                                alarm.setMonth(month);
                                alarm.setYear(year);
                                alarm.setHour(hour);
                                alarm.setMinute(min);
                                alarm.setDatestring(date23);
                                alarm.setO_id(datalist.get(i).getOrder_detail().getOrder_id());
                                alarm.setCreated_at(datalist.get(i).getCreated_at());
                                alarm.setGuest(datalist.get(i).getGuest());
                                alarm.setDescription(datalist.get(i).getDescription());
                                alarm.setHotel_id(datalist.get(i).getHotel_id());
                                alarm.setId(datalist.get(i).getId());
                                alarm.setNo_of_guest(datalist.get(i).getNo_of_guest());
                                alarm.setOrder_detail(datalist.get(i).getOrder_detail());
                                alarm.setOrder_spa_items(datalist.get(i).getOrder_spa_items());
                                alarm.setPayment_status(datalist.get(i).getPayment_status());
                                alarm.setPrimises_id(datalist.get(i).getPrimises_id());
                                alarm.setStatus(datalist.get(i).getStatus());
                                alarm.setType(datalist.get(i).getType());
                                alarm.setUpdated_at(datalist.get(i).getUpdated_at());
                                alarm.setPrimises(datalist.get(i).getPrimises());
                                setAlarm1(alarm);
                            }





                        }

                       /* if(getArguments().getString("visible").equals("1")){
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                           // recyclerView.smoothScrollToPosition(queuelistnew.size() - 1);

                            norecord.setVisibility(View.GONE);
                            Spa_Mobile.homeicon.setVisibility(View.GONE);
                            Spa_Mobile.homeicon.setVisibility(View.GONE);

                           *//* LinearLayout layoutManager = LinearLayout.class.cast(recyclerView.getLayoutManager());
                            int lastItem = homeAdapter.getItemCount() - 1;
                            tryAnimation(layoutManager.findViewByPosition(lastItem));*//*

                        }
                        else {
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                            norecord.setVisibility(View.GONE);
                        }*/
                    }
                    else{

                    }



                    if(Network.isNetworkAvailable(Spa_Mobile.this)){
                        GetHeader();
                    }else if(Network.isNetworkAvailable2(Spa_Mobile.this)){
                        GetHeader();
                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Spa_dashboard login = response.body();
                  //  Toast.makeText(Spa_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                   // Toast.makeText(Spa_Mobile.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Spa_dashboard> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }

}
