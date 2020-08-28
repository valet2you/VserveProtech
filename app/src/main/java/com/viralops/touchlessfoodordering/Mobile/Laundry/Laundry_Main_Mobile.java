package com.viralops.touchlessfoodordering.Mobile.Laundry;

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
import android.widget.EditText;
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
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.IRD_Data;
import com.viralops.touchlessfoodordering.Model.Laundry_Category;
import com.viralops.touchlessfoodordering.Model.Laundry_item;
import com.viralops.touchlessfoodordering.Model.Logout;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.database.AlarmReceiver;
import com.viralops.touchlessfoodordering.database.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class Laundry_Main_Mobile extends AppCompatActivity implements View.OnClickListener{
   public static  Animation shake;

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
    ShimmerRecyclerView shimmerRecyclerView;
    ShimmerRecyclerView shimmerRecyclerViewcategory;
    ShimmerRecyclerView shimmerRecyclerView1;
    ArrayList<Laundry_Category> laundrymenulist=new ArrayList<>();
    ArrayList<Laundry_Category> laundrycategorylistlist=new ArrayList<>();
    RecyclerView recyclerView;
    Typeface font;
    Typeface font1;
    LaundryCAtegoryAdapter   laundryCartegoryAdapter;
    ArrayList<Laundry_Dashboard1.Data> queuelist=new ArrayList<>();
    ArrayList<Laundry_Dashboard1.Data> queuelistnew=new ArrayList<>();
    ArrayList<Laundry_Dashboard1.Data> localarray=new ArrayList<>();

    SQLiteDatabase db;
    DbHelper mDbHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#091F42"));

        }
        // Set up the login form.
        setContentView(R.layout.associate_main);
        mDbHelper = new DbHelper(Laundry_Main_Mobile.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#091F42"));
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                R.mipmap.moreicon);
        toolbar.setOverflowIcon(drawable);
        sessionManager=new SessionManager(Laundry_Main_Mobile.this);
        sessionManagerFCM=new SessionManagerFCM(Laundry_Main_Mobile.this);
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
            home.setTextColor(Laundry_Main_Mobile.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));

            Fragment fragmentmanager = new Laundry_Mobile_Dashboard();
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
            home.setTextColor(Laundry_Main_Mobile.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#707070"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            Fragment fragmentmanager = new Laundry_Mobile_Dashboard();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        if(view.getId()== R.id.eventlayout){
            isvisisble=false;

            Fragment fragmentmanager = new Laundry_Dashboard_Clearance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

            orders.setTextColor(Laundry_Main_Mobile.this.getResources().getColor(R.color.darkblue));
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
                // Include dialog.xml file
                if (Network.isNetworkAvailable(Laundry_Main_Mobile.this)) {
                    new LaundryIRDenu().execute();

//

                } else if (Network.isNetworkAvailable2(Laundry_Main_Mobile.this)) {
                    new LaundryIRDenu().execute();



                }
                else{
           /* if (sessionManager.getIsINternet().equals("false")) {
                Intent intent = new Intent(Laundry_Main_Mobile.this, Internetconnection.class);
                startActivity(intent);

                sessionManager.setIsINternet("true");
                finish();

            } else {

            }*/
                }

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
                        .replace(R.id.rootLayout, Laundry_History.newInstance())
                        .commitNow();
                break;

            case R.id.navigation_dashboard:
                final Dialog dialog1 = new Dialog(Laundry_Main_Mobile.this);
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
                        if(Network.isNetworkAvailable(Laundry_Main_Mobile.this)){
                            Logout();
                            dialog1.dismiss();
                        } if(Network.isNetworkAvailable2(Laundry_Main_Mobile.this)){
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
        final Dialog dialog = new Dialog(Laundry_Main_Mobile.this);
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
                Laundry_Main_Mobile.this.finish();
                dialog.dismiss();
            }
        });

    }
    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Laundry_Main_Mobile.this);
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
                    Toast.makeText(Laundry_Main_Mobile.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

           // unregisterReceiver(mMessageReceiver);

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

                Fragment fragment1 = new Laundry_Mobile_Dashboard();


                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
            }
            else {
                AnimateBell();
                if(Network.isNetworkAvailable(Laundry_Main_Mobile.this)){
                    GetMenu();
                }
                else if(Network.isNetworkAvailable2(Laundry_Main_Mobile.this)){
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

         shake = AnimationUtils.loadAnimation(Laundry_Main_Mobile.this, R.anim.shakeanimation);
        homeicon.setAnimation(shake);

    }

    //-------------Laundry------------------------------//

    public class LaundryIRDenu extends AsyncTask<String, String, String> {

        final ProgressDialog progressDialog = new ProgressDialog(Laundry_Main_Mobile.this);

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
                        .url(BuildConfig.BASE_URL + BuildConfig.laundry_menu)
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
            laundrymenulist.clear();
            laundrycategorylistlist.clear();
            if(progressDialog!=null){
                progressDialog.dismiss();
            }

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONObject data=jsonObject.getJSONObject("data");
                    IRD_Data ird_data=new IRD_Data();
                    ird_data.setId(data.getString("id"));
                    ird_data.setName(data.getString("name"));
                    ird_data.setHotel_id(data.getString("hotel_id"));
                    ird_data.setDescription(data.getString("description"));
                    ird_data.setEnabled(data.getString("enabled"));
                    ird_data.setCreated_at(data.getString("created_at"));
                    ird_data.setUpdated_at(data.getString("updated_at"));
                    JSONArray jsonArray=data.getJSONArray("categories");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        Laundry_Category ird_category=new Laundry_Category();
                        ird_category.setId(jsonObject1.getString("id"));
                        ird_category.setCreated_at(jsonObject1.getString("created_at"));
                        ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                        ird_category.setLaundry_id(jsonObject1.getString("laundry_id"));
                        ird_category.setDescription(jsonObject1.getString("description"));
                        ird_category.setEnabled(jsonObject1.getString("enabled"));
                        ird_category.setName(jsonObject1.getString("name"));
                        ird_category.setTags(jsonObject1.getString("tags"));
                        ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                        ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                        laundrymenulist.add(ird_category);
                        laundrycategorylistlist.add(ird_category);
                    }
                    final Dialog dialog = new Dialog(Laundry_Main_Mobile.this);

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
                    shimmerRecyclerView = dialog.findViewById(R.id.recyclerview);
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(Laundry_Main_Mobile.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("Laundry Menu");
                    final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                    final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                    laundryCartegoryAdapter = new LaundryCAtegoryAdapter(laundrymenulist, Laundry_Main_Mobile.this);
                    shimmerRecyclerView.setAdapter(laundryCartegoryAdapter);
                    EditText searchtext = dialog.findViewById(R.id.searchtext);

                    registerForContextMenu(menubutton);
                    ImageView close = dialog.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    menubutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            menudialog = new Dialog(Laundry_Main_Mobile.this);
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
                            recyclerView.setLayoutManager(new LinearLayoutManager(Laundry_Main_Mobile.this, LinearLayoutManager.VERTICAL, false));
                            LaundryCartegoryAdapter menupopupadapeter = new LaundryCartegoryAdapter(laundrycategorylistlist, Laundry_Main_Mobile.this);
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
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public class LaundryIRDenunew extends AsyncTask<String, String, String> {


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
                        .url(BuildConfig.BASE_URL + BuildConfig.laundry_menu)
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
            laundrymenulist.clear();
            laundrycategorylistlist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONObject data=jsonObject.getJSONObject("data");
                    IRD_Data ird_data=new IRD_Data();
                    ird_data.setId(data.getString("id"));
                    ird_data.setName(data.getString("name"));
                    ird_data.setHotel_id(data.getString("hotel_id"));
                    ird_data.setDescription(data.getString("description"));
                    ird_data.setEnabled(data.getString("enabled"));
                    ird_data.setCreated_at(data.getString("created_at"));
                    ird_data.setUpdated_at(data.getString("updated_at"));
                    JSONArray jsonArray=data.getJSONArray("categories");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        Laundry_Category ird_category=new Laundry_Category();
                        ird_category.setId(jsonObject1.getString("id"));
                        ird_category.setCreated_at(jsonObject1.getString("created_at"));
                        ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                        ird_category.setLaundry_id(jsonObject1.getString("laundry_id"));
                        ird_category.setDescription(jsonObject1.getString("description"));
                        ird_category.setEnabled(jsonObject1.getString("enabled"));
                        ird_category.setName(jsonObject1.getString("name"));
                        ird_category.setTags(jsonObject1.getString("tags"));
                        ird_category.setWithout_sub_category_items(jsonObject1.getJSONArray("without_sub_category_items"));
                        ird_category.setSub_categories(jsonObject1.getJSONArray("sub_categories"));
                        laundrymenulist.add(ird_category);
                        laundrycategorylistlist.add(ird_category);
                    }
                    laundryCartegoryAdapter.notifyDataSetChanged();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    private void LaundryGetMenuitemenab(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Laundry_Main_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemEnabledLaundry("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.laundry_item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenuitemdisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Laundry_Main_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemDisabledLaundry("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.laundry_disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenucategoryenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Laundry_Main_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategoryEnabledLaundry("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.laundry_enable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenucategorydisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Laundry_Main_Mobile.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategorydisabledLaundry("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.laundry_disable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(Laundry_Main_Mobile.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

    public class LaundryCAtegoryAdapter extends  RecyclerView.Adapter<LaundryCAtegoryAdapter.ViewHolder>{
        ArrayList<Laundry_Category> order_items;
        Context context;
        String enabled;
        ArrayList<Laundry_item> iRd_subCategories;


        public LaundryCAtegoryAdapter(ArrayList<Laundry_Category> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public LaundryCAtegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenuparent, parent, false);
            return new LaundryCAtegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Laundry_Category> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final LaundryCAtegoryAdapter.ViewHolder holder, int position) {
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
                            LaundryGetMenucategoryenable(holder.mitem.getId());
                            enabled="1";
                        }  else if (Network.isNetworkAvailable2(context)) {
                            LaundryGetMenucategoryenable(holder.mitem.getId());
                            enabled="1";

                        }
                        else{

                        }
                    }
                    else{
                        if (Network.isNetworkAvailable(context)) {
                            LaundryGetMenucategorydisable(holder.mitem.getId());
                            enabled="0";

                        }  else if (Network.isNetworkAvailable2(context)) {
                            LaundryGetMenucategorydisable(holder.mitem.getId());
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
                    Laundry_item iRd_subCategory=new Laundry_item();
                    iRd_subCategory.setId(jsonObject.getString("id"));
                    iRd_subCategory.setLaundry_id(jsonObject.getString("laundry_category_id"));
                    iRd_subCategory.setName(jsonObject.getString("name"));
                    iRd_subCategory.setDescription(jsonObject.getString("description"));
                    iRd_subCategory.setTags(jsonObject.getString("tags"));
                    iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                    iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                    iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                    iRd_subCategory.setIs_express_delivery(jsonObject.getString("is_express_delivery"));
                    iRd_subCategory.setExpress_delivery_price(jsonObject.getString("express_delivery_price"));
                    iRd_subCategory.setPrice(jsonObject.getString("price"));
                    iRd_subCategory.setThumbnail(jsonObject.getString("thumbnail"));
                    iRd_subCategory.setLaundry_id(jsonObject.getString("laundry_id"));
                    iRd_subCategory.setLaundry_sub_category_id(jsonObject.getString("laundry_sub_category_id"));
                    iRd_subCategories.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            LaundryIRD_ItemsAdapter order_itemAdapterdetail=new LaundryIRD_ItemsAdapter(iRd_subCategories,context,holder.mitem.getName(),enabled);
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


            Laundry_Category mitem;

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
    public class LaundryIRD_ItemsAdapter extends  RecyclerView.Adapter<LaundryIRD_ItemsAdapter.ViewHolder>{
        ArrayList<Laundry_item> order_items;

        Context context;
        String category;
        String enabled;
        String enabled1;

        public LaundryIRD_ItemsAdapter(ArrayList<Laundry_item> order_items, Context context,String category,String enabled) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
        }

        @NonNull
        @Override
        public LaundryIRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.laundry_item, parent, false);
            return new LaundryIRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Laundry_item> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final LaundryIRD_ItemsAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            holder.price.setText(getResources().getString(R.string.Rs));
            holder.price1.setText(holder.mitem.getPrice());
            holder.expressprice.setText(getResources().getString(R.string.Rs));
            holder.expressprice1.setText(holder.mitem.getExpress_delivery_price());
            if(!holder.mitem.getDescription().equals("null")){
                holder.ingredinets.setText(holder.mitem.getDescription());

            }
            else{
                holder.ingredinets.setText("");

            }
            if(holder.mitem.getIs_express_delivery().equals("1")){
                holder.express.setVisibility(View.VISIBLE);
                holder.expressprice1.setVisibility(View.VISIBLE);
                holder.expressprice.setVisibility(View.VISIBLE);

            }
            else{
                holder.express.setVisibility(View.GONE);
                holder.expressprice1.setVisibility(View.GONE);
                holder.expressprice.setVisibility(View.GONE);
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
                            LaundryGetMenuitemenab(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            LaundryGetMenuitemenab(holder.mitem.getId());

                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            LaundryGetMenuitemdisable(holder.mitem.getId());

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            LaundryGetMenuitemdisable(holder.mitem.getId());


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
            TextView standard;
            TextView express;
            TextView expressprice1;
            TextView expressprice;
            TextView customise;
            TextView ingredinets;
            ImageView dish;
            ImageView category;
            RecyclerView spicy;
            RecyclerView customiselist;
            LinearLayout gradeout;
            ToggleButton toggleButton1;


            Laundry_item mitem;

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
                express=itemView.findViewById(R.id.express);
                standard=itemView.findViewById(R.id.standard);
                expressprice1=itemView.findViewById(R.id.expressprice1);
                expressprice=itemView.findViewById(R.id.expressprice);
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
                expressprice.setTypeface(font);
                expressprice1.setTypeface(font);
                ingredinets.setTypeface(font1);
            }
        }



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

    public class LaundryCartegoryAdapter extends  RecyclerView.Adapter<LaundryCartegoryAdapter.ViewHolder>{
        ArrayList<Laundry_Category> order_items;
        Context context;

        public LaundryCartegoryAdapter(ArrayList<Laundry_Category> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public LaundryCartegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item, parent, false);
            return new LaundryCartegoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LaundryCartegoryAdapter.ViewHolder holder, final int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
           // holder.category1.setText(String.valueOf(holder.mitem.getSub_categories().length()));


        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView category1;



            Laundry_Category mitem;

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
                        shimmerRecyclerView.smoothScrollToPosition(getAdapterPosition());
                        menudialog.dismiss();

                    }
                });

            }
        }



    }

    //-------------End id Laundaryadapter------------------------------//
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
/*
    private void GetMenu() {
        // display a progress dialog
        //recyclerView.showShimmer();
*/
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*//*


        (RetrofitClientInstance.getApiService().Laundry_getOrders("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Laundry_Dashboard1>() {
            @Override
            public void onResponse(@NonNull Call<Laundry_Dashboard1> call, @NonNull Response<Laundry_Dashboard1> response) {

                if(response.code()==201||response.code()==200){
                    Laundry_Dashboard1  login = response.body();
                    queuelist=new ArrayList<>();
                    queuelistnew=new ArrayList<>();

                    queuelist=login.getData();
                    if(queuelist.size()!=0){
                        for(int i=0;i<queuelist.size();i++){
                            if(queuelist.get(i).getStatus().equals("new_order")){
                                Laundry_Dashboard1.Data data=new Laundry_Dashboard1.Data();
                                data.setCreated_at(queuelist.get(i).getCreated_at());
                                data.setDescription(queuelist.get(i).getDescription());
                                data.setHotel_id(queuelist.get(i).getHotel_id());
                                data.setId(queuelist.get(i).getId());
                                data.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                data.setOrder_detail(queuelist.get(i).getOrder_detail());
                                data.setOrder_laundry_items(queuelist.get(i).getOrder_laundry_items());
                                data.setPayment_status(queuelist.get(i).getPayment_status());
                                data.setPrimises_id(queuelist.get(i).getPrimises_id());
                                data.setStatus(queuelist.get(i).getStatus());
                                data.setType(queuelist.get(i).getType());
                                data.setUpdated_at(queuelist.get(i).getUpdated_at());
                                data.setPrimises(queuelist.get(i).getPrimises());

                                data.setStatus("New Order");
                                queuelistnew.add(data);
                            }
                        }
                        Laundry_Main_Mobile.newcount.setText("( "+String.valueOf(queuelistnew.size())+" )");
                        Laundry_Main_Mobile.newcountevent.setText("( "+String.valueOf(queuelist.size()-queuelistnew.size())+" )");


                       */
/* if(getArguments().getString("visible").equals("1")){
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                           // recyclerView.smoothScrollToPosition(queuelistnew.size() - 1);

                            norecord.setVisibility(View.GONE);
                            Laundry_Main_Mobile.homeicon.setVisibility(View.GONE);
                            Laundry_Main_Mobile.homeicon.setVisibility(View.GONE);

                           *//*
*/
/* LinearLayout layoutManager = LinearLayout.class.cast(recyclerView.getLayoutManager());
                            int lastItem = homeAdapter.getItemCount() - 1;
                            tryAnimation(layoutManager.findViewByPosition(lastItem));*//*
*/
/*

                        }
                        else {
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                            norecord.setVisibility(View.GONE);
                        }*//*

                    }
                    else{

                        Laundry_Main_Mobile.newcountevent.setText("( 0 )");
                        Laundry_Main_Mobile.newcount.setText("( 0 )");
                    }






                }
                else if(response.code()==401){
                    Laundry_Dashboard1 login = response.body();
                    Toast.makeText(Laundry_Main_Mobile.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    Toast.makeText(Laundry_Main_Mobile.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Laundry_Dashboard1> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
*/

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

    private void GetMenu() {
        // display a progress dialog
       // recyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_getOrders1("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Laundry_Dashboard1>() {
            @Override
            public void onResponse(@NonNull Call<Laundry_Dashboard1> call, @NonNull Response<Laundry_Dashboard1> response) {

                if(response.code()==201||response.code()==200){
                    Laundry_Dashboard1  login = response.body();
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
                                Laundry_Dashboard1.Data data=new Laundry_Dashboard1.Data();
                                data.setCreated_at(queuelist.get(i).getCreated_at());
                                data.setGuest(queuelist.get(i).getGuest());
                                data.setDescription(queuelist.get(i).getDescription());
                                data.setHotel_id(queuelist.get(i).getHotel_id());
                                data.setId(queuelist.get(i).getId());
                                data.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                data.setOrder_detail(queuelist.get(i).getOrder_detail());
                                data.setOrder_laundry_items(queuelist.get(i).getOrder_laundry_items());
                                data.setPayment_status(queuelist.get(i).getPayment_status());
                                data.setPrimises_id(queuelist.get(i).getPrimises_id());
                                data.setStatus(queuelist.get(i).getStatus());
                                data.setType(queuelist.get(i).getType());
                                data.setUpdated_at(queuelist.get(i).getUpdated_at());
                                data.setPrimises(queuelist.get(i).getPrimises());

                                data.setStatus("New Order");
                                queuelistnew.add(data);
                            }




                        }
                        Laundry_Main_Mobile.newcount.setText("( "+String.valueOf(queuelistnew.size())+" )");
                        Laundry_Main_Mobile.newcountevent.setText("( "+String.valueOf(queuelist.size()-queuelistnew.size())+" )");


                        if(localarray.size()==0) {
                            for (int i = 0; i < queuelist.size(); i++) {

                                long timeDiff = 0;
                                Date date1 = null;
                                Date date2 = null;
                                final Calendar calendar = Calendar.getInstance();
                                String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                                System.out.println("current date" + timeStamp + " " + queuelist.get(i).getOrder_detail().getRequested_pickup_at());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                                long epoch = 0;

                                try {
                                    dt = dateFormat.parse(queuelist.get(i).getOrder_detail().getRequested_pickup_at());
                                    epoch = dt.getTime();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(epoch);
                                String date23 = android.text.format.DateFormat.format("dd MMM yyyy hh:mm a", dt).toString();
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
                                Laundry_Dashboard1.Data alarm = new Laundry_Dashboard1.Data();
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
                                alarm.setOrder_laundry_items(queuelist.get(i).getOrder_laundry_items());
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
                            ArrayList<Laundry_Dashboard1.Data> datalist=new ArrayList<>();
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
                                System.out.println("current date" + timeStamp + " " + datalist.get(i).getOrder_detail().getRequested_pickup_at());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                                long epoch = 0;

                                try {
                                    dt = dateFormat.parse(datalist.get(i).getOrder_detail().getRequested_pickup_at());
                                    epoch = dt.getTime();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(epoch);
                                String date23 = android.text.format.DateFormat.format("dd MMM yyyy hh:mm a", dt).toString();
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
                                Laundry_Dashboard1.Data alarm = new Laundry_Dashboard1.Data();
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
                                alarm.setOrder_laundry_items(datalist.get(i).getOrder_laundry_items());
                                alarm.setPayment_status(datalist.get(i).getPayment_status());
                                alarm.setPrimises_id(datalist.get(i).getPrimises_id());
                                alarm.setStatus(datalist.get(i).getStatus());
                                alarm.setType(datalist.get(i).getType());
                                alarm.setUpdated_at(datalist.get(i).getUpdated_at());
                                alarm.setPrimises(datalist.get(i).getPrimises());
                                setAlarm1(alarm);
                            }




                   /*         ArrayList<Laundry_Dashboard1.Data> union = new ArrayList(localarray);
                            union.addAll(queuelist);
// Intersection is only those in both.
                            ArrayList<Laundry_Dashboard1.Data> intersection = new ArrayList(localarray);
                            intersection.retainAll(queuelist);
// Symmetric difference is all except those in both.
                            ArrayList<Laundry_Dashboard1.Data> symmetricDifference = new ArrayList(union);
                            symmetricDifference.removeAll(intersection);
                            System.out.println("union: " + union);
                            System.out.println("intersection: " + intersection);
                            System.out.println("**symmetricDifference: " + symmetricDifference.size()+"**");
*/


                                   /* else{
                                        long timeDiff = 0;
                                        Date date1 = null;
                                        Date date2 = null;
                                        final Calendar calendar = Calendar.getInstance();
                                        String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                                        System.out.println("current date" + timeStamp + " " + queuelist.get(i).getOrder_detail().getRequested_pickup_at());
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                        Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                                        long epoch = 0;

                                        try {
                                            dt = dateFormat.parse(queuelist.get(i).getOrder_detail().getRequested_pickup_at());
                                            epoch = dt.getTime();

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                        cal.setTimeInMillis(epoch);
                                        String date23 = android.text.format.DateFormat.format("dd MMM yyyy hh:mm a", dt).toString();
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

                                        int hour = date2.getHours();
                                        int min = date2.getMinutes();
                                        int date = date2.getDay();
                                        int month = date2.getMonth();
                                        int year = date2.getYear();
                                        Laundry_Dashboard1.Data alarm = new Laundry_Dashboard1.Data();
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
                                        alarm.setOrder_laundry_items(queuelist.get(i).getOrder_laundry_items());
                                        alarm.setPayment_status(queuelist.get(i).getPayment_status());
                                        alarm.setPrimises_id(queuelist.get(i).getPrimises_id());
                                        alarm.setStatus(queuelist.get(i).getStatus());
                                        alarm.setType(queuelist.get(i).getType());
                                        alarm.setUpdated_at(queuelist.get(i).getUpdated_at());
                                        alarm.setPrimises(queuelist.get(i).getPrimises());
                                        setAlarm1(alarm);
                                    }*/


                        }


                       /* if(getArguments().getString("visible").equals("1")){
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                           // recyclerView.smoothScrollToPosition(queuelistnew.size() - 1);

                            norecord.setVisibility(View.GONE);
                            Laundry_Main_Mobile.homeicon.setVisibility(View.GONE);
                            Laundry_Main_Mobile.homeicon.setVisibility(View.GONE);

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

                        Laundry_Main_Mobile.newcountevent.setText("( 0 )");
                        Laundry_Main_Mobile.newcount.setText("( 0 )");
                    }




                }
                else if(response.code()==401){
                    Laundry_Dashboard1 login = response.body();
                  //  Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                //    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }

               // recyclerView.hideShimmer();



            }

            @Override
            public void onFailure(@NonNull Call<Laundry_Dashboard1> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
             //   recyclerView.hideShimmer();

            }
        });

    }
    private void setAlarm1(Laundry_Dashboard1.Data laundry_dashboard) {
        mDbHelper = new DbHelper(Laundry_Main_Mobile.this);

        db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(mDbHelper.TITLE, laundry_dashboard.getPrimises().getPremise_no());
        cv.put(mDbHelper.O_ID,laundry_dashboard.getOrder_detail().getOrder_id());
        cv.put(mDbHelper.DETAIL, laundry_dashboard.getOrder_laundry_items().toString());
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
            Intent intent = new Intent(Laundry_Main_Mobile.this, AlarmReceiver.class);
            long time=60*1000;

            //String alertTitle = mTitleText.getText().toString();
            intent.putExtra("title", laundry_dashboard.getPrimises().getPremise_no());
            intent.putExtra("time", timeString);
            intent.putExtra("alramid",String.valueOf(alarmid));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(Laundry_Main_Mobile.this, alarmid, intent, 0);

            alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

            cv.put(mDbHelper.TIME, timeString);
            cv.put(mDbHelper.DATE, dateString);
            db.insert(mDbHelper.TABLE_NAME, null, cv);

        }catch (Exception e){

        }

    }

}
