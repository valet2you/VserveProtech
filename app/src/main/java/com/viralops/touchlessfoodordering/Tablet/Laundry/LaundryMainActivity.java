package com.viralops.touchlessfoodordering.Tablet.Laundry;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import androidx.cardview.widget.CardView;
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
import com.viralops.touchlessfoodordering.Model.Laundry_Header;
import com.viralops.touchlessfoodordering.Model.Laundry_item;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Internetconnection;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaundryMainActivity extends AppCompatActivity implements View.OnClickListener {



    static public TextView standardsrvice;
    static public TextView standtotal;
    static public TextView express;
    static public TextView totalexpress;
    static public TextView totallaudry;
    static public TextView totalorderslaundry;





    private TextView text;
    RecyclerView recyclerView;
     Dialog menudialog;
    private TextView text1;
    private CardView order;
    private CardView menu;
    private  TextView history;
    private  TextView laundry;
    private ImageView logout;
    private SessionManager sessionManager;
    private SessionManagerFCM sessionManagerFCM;


    ArrayList<Laundry_Category> laundrymenulist=new ArrayList<>();
    ArrayList<Laundry_Category> laundrycategorylistlist=new ArrayList<>();

     Dialog dialog1;
    ShimmerRecyclerView shimmerRecyclerView;
    ShimmerRecyclerView shimmerRecyclerView1;
    static public boolean isvisisble=true;
    static  public ImageView belllaundry;
    static public LinearLayout startndard;
     public String word="";
     LinearLayout hearderforlaundry;
     LinearLayout laundrylayout;
     LinearLayout layout;
    LaundryCAtegoryAdapter   laundryCartegoryAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;
    Typeface font;
    Typeface font1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.laundry_activity);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        sessionManager=new SessionManager(LaundryMainActivity.this);
        sessionManagerFCM=new SessionManagerFCM(LaundryMainActivity.this);
        sessionManager.setIsINternet("false");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Crashlytics.setUserIdentifier(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        FirebaseCrashlytics.getInstance().setUserId(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(sessionManager.getPorchName());
        mFirebaseAnalytics.setUserProperty("Id",sessionManager.getPorchName()+" "+sessionManager.getNAME());

        laundrylayout=  findViewById(R.id.laundrylayout);

         belllaundry=  findViewById(R.id.belllaundry);
         belllaundry.setImageResource(R.mipmap.calling);
         belllaundry.setVisibility(View.INVISIBLE);

        startndard=findViewById(R.id.startndard);
         font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
         font1 = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Thin.ttf");


        standardsrvice=findViewById(R.id.standardsrvice);
        standardsrvice.setTypeface(font);
        express=findViewById(R.id.express);
        express.setTypeface(font);
        totallaudry=findViewById(R.id.totallaudry);
        totallaudry.setTypeface(font);





       menu=findViewById(R.id.menu);


        standtotal=findViewById(R.id.standtotal);
        standtotal.setTypeface(font1);
        totalexpress=findViewById(R.id.totalexpress);
        totalexpress.setTypeface(font);
        totalorderslaundry=findViewById(R.id.totalorderslaundry);
        totalorderslaundry.setTypeface(font);


        order=findViewById(R.id.order);

        hearderforlaundry=findViewById(R.id.hearderforlaundry);

        text=findViewById(R.id.text);
        text.setTypeface(font);
        text1=findViewById(R.id.text1);
        text.setText(sessionManager.getPorchName());
        history=findViewById(R.id.history);
        laundry=findViewById(R.id.laundry);
        layout=findViewById(R.id.layout);
        laundrylayout.setOnClickListener(this);

        text1.setTypeface(font1);
        text1.setText("");
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LaundryMainActivity.this);
                // Include dialog.xml file

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.delet_dialog);
                int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
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
                        if(Network.isNetworkAvailable(LaundryMainActivity.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(LaundryMainActivity.this)){
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

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hearderforlaundry.setVisibility(View.VISIBLE);


                laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
                laundry.setTextColor(getResources().getColor(R.color.gray));
                layout.setBackgroundColor(getResources().getColor(R.color.blue));
                history.setTextColor(getResources().getColor(R.color.white));

                isvisisble=false;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_view, LaundryOrderHistory.newInstance())
                            .commitNow();

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(word.equals("Laundry")) {
                    if (Network.isNetworkAvailable(LaundryMainActivity.this)) {
                        new LaundryIRDenu().execute();


                    } else if (Network.isNetworkAvailable2(LaundryMainActivity.this)) {
                        new LaundryIRDenu().execute();}
                    else{
                        if (sessionManager.getIsINternet().equals("false")) {
                            Intent intent = new Intent(LaundryMainActivity.this, Internetconnection.class);
                            startActivity(intent);

                            sessionManager.setIsINternet("true");
                            finish();

                        } else {

                        }
                    }


                }



            }
        });
        if (savedInstanceState == null) {
            hearderforlaundry.setVisibility(View.VISIBLE);

            menu.setVisibility(View.VISIBLE);


            laundrylayout.setBackgroundColor(getResources().getColor(R.color.blue));
            laundry.setTextColor(getResources().getColor(R.color.white));
            layout.setBackgroundColor(getResources().getColor(R.color.white));
            history.setTextColor(getResources().getColor(R.color.gray));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, Laundry_fragment.newInstance())
                    .commitAllowingStateLoss();
            word="Laundry";

        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.laundrylayout){


            word="Laundry";
            hearderforlaundry.setVisibility(View.VISIBLE);
            LaundryMainActivity.belllaundry.setVisibility(View.GONE);

            menu.setVisibility(View.VISIBLE);

            isvisisble=true;


            laundrylayout.setBackgroundColor(getResources().getColor(R.color.blue));
            laundry.setTextColor(getResources().getColor(R.color.white));
            layout.setBackgroundColor(getResources().getColor(R.color.white));
            history.setTextColor(getResources().getColor(R.color.gray));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, Laundry_fragment.newInstance())
                    .commitAllowingStateLoss();
        }

    }


    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(LaundryMainActivity.this);
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
                    Toast.makeText(LaundryMainActivity.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(LaundryMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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





    // Showing the status in Snackbar

    // Method to manually check connection status



    @Override
    protected void onPause() {
        super.onPause();
        try {

            registerReceiver(mMessageReceiver, new IntentFilter("com.viralops.touchlessfoodordering"));

            unregisterReceiver(mMessageReceiver);

        }
        catch (Exception e)
        {
        }

    }

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
    public void onBackPressed() {

            //super.onBackPressed();
            final Dialog dialog = new Dialog(LaundryMainActivity.this);
            // Include dialog.xml file

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.delet_dialog);
            int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
            int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
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
                    LaundryMainActivity.this.finish();
                    dialog.dismiss();
                }
            });

        }

    public void AnimateBell() {
        Animation shake = AnimationUtils.loadAnimation(LaundryMainActivity.this, R.anim.shakeanimation);

     //   imgBell.setAnimation(shake);

    }
    // Method to convert the string
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


    //-------------Laundry------------------------------//

    public class LaundryIRDenu extends AsyncTask<String, String, String> {

        final ProgressDialog progressDialog = new ProgressDialog(LaundryMainActivity.this);

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
                    final Dialog dialog = new Dialog(LaundryMainActivity.this);
                    // Include dialog.xml file

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.menulist);
                    int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.55);
                    int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.95);
                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog.getWindow().setLayout(width1, height1);

                    dialog.setCancelable(false);
                    // Set dialog title
                    dialog.setTitle("");
                    dialog.show();
                    shimmerRecyclerView = dialog.findViewById(R.id.recyclerview);
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(LaundryMainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("Laundry Menu");
                    final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                    final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                    laundryCartegoryAdapter = new LaundryCAtegoryAdapter(laundrymenulist, LaundryMainActivity.this);
                    shimmerRecyclerView.setAdapter(laundryCartegoryAdapter);

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
                            menudialog = new Dialog(LaundryMainActivity.this);
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
                            recyclerView.setLayoutManager(new LinearLayoutManager(LaundryMainActivity.this, LinearLayoutManager.VERTICAL, false));
                            LaundryCartegoryAdapter menupopupadapeter = new LaundryCartegoryAdapter(laundrycategorylistlist, LaundryMainActivity.this);
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
        final ProgressDialog progressDialog = new ProgressDialog(LaundryMainActivity.this);
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
                    Toast.makeText(LaundryMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(LaundryMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(LaundryMainActivity.this);
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
                    Toast.makeText(LaundryMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(LaundryMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(LaundryMainActivity.this);
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
                    Toast.makeText(LaundryMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(LaundryMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(LaundryMainActivity.this);
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
                    Toast.makeText(LaundryMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(LaundryMainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(LaundryMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
            holder.title.setText(holder.mitem.getName()+" ( "+String.valueOf(holder.mitem.getSub_categories().length()+holder.mitem.getWithout_sub_category_items().length())+" )");
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isvisisble==true) {
                belllaundry.setVisibility(View.VISIBLE);
                AnimateBell();
                word="Laundry";

                Fragment fragment1 = new Laundry_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
            }
            else {
                isvisisble=false;
                belllaundry.setVisibility(View.VISIBLE);
                AnimateBell();
                if(Network.isNetworkAvailable(LaundryMainActivity.this)){
                    GetHeader();
                }
                else if(Network.isNetworkAvailable2(LaundryMainActivity.this)){
                    GetHeader();
                }
                else{

                }
            }






        }






    };
    private void GetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Laundry_Header>() {
            @Override
            public void onResponse(@NonNull Call<Laundry_Header> call, @NonNull Response<Laundry_Header> response) {

                if(response.code()==202||response.code()==200){
                    Laundry_Header  login = response.body();
                    standtotal.setText(String.valueOf(login.getData().getStandard_order()));
                    totalorderslaundry.setText(String.valueOf(login.getData().getToday_order()));
                    totalexpress.setText(String.valueOf(login.getData().getExpress_order()));


                }
                else if(response.code()==401){
                    Laundry_Header login = response.body();
                    Toast.makeText(LaundryMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(LaundryMainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Laundry_Header> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }

}
