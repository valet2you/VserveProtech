package com.viralops.touchlessfoodordering.Mobile.Restaurant;

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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.Logout;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;


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

public class RestaurantMain extends AppCompatActivity implements View.OnClickListener{
    public static Animation shake;

    public static LinearLayout homelayout,shiftlayout,orderslayout,dispatchedlayout;
    public static ImageView homeicon,shufticon,ordersicon,profileicon;
    public static TextView home,shift,orders,dispatched;
    public static TextView newcount,newcountevent,newcountdispatched;
    public View tab,tab1,tab2;
    Logout logout;
    private SessionManager sessionManager;
    private SessionManagerFCM sessionManagerFCM;
    private TextView porchname;
    static public boolean isvisisble=true;
    ArrayList<Restaurant_Category> irdcategorylist=new ArrayList<>();
    ArrayList<Resturant_Data_data> irddataenulist=new ArrayList<>();
    Dialog menudialog;
    ArrayList<Restaurant_Category> irdmenuslist=new ArrayList<>();
    ShimmerRecyclerView shimmerRecyclerView;
    ShimmerRecyclerView shimmerRecyclerViewcategory;
    ShimmerRecyclerView shimmerRecyclerView1;
    IRDCAtegoryAdapter   irdcAtegoryAdapter;
    IRDAdapter   irdAdapter;
    RecyclerView recyclerView;
    Typeface font;
    Typeface font1;
    ArrayList<RestaurantApp_Dashboard.Data> queuelist=new ArrayList<>();
    ArrayList<RestaurantApp_Dashboard.Data> queuelistnew=new ArrayList<>();
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
        sessionManager=new SessionManager(RestaurantMain.this);
        sessionManagerFCM=new SessionManagerFCM(RestaurantMain.this);
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
        tab2=findViewById(R.id.tab2);
        tab2.setOnClickListener(this);
        tab1.setOnClickListener(this);
        newcount=findViewById(R.id.newcount);
        newcountevent=findViewById(R.id.newcountevent);


        orderslayout=findViewById(R.id.eventlayout);
        orderslayout.setOnClickListener(this);
        dispatchedlayout=findViewById(R.id.dispatchedlayout);
        dispatchedlayout.setOnClickListener(this);




        homeicon=findViewById(R.id.favicon);




        home=findViewById(R.id.home);
        home.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcountevent.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        orders=findViewById(R.id.event);
        orders.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        newcountdispatched=findViewById(R.id.newcountdispatched);
        newcountdispatched.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dispatched=findViewById(R.id.dispatched);

        dispatched.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


        if (savedInstanceState == null) {
            home.setTextColor(RestaurantMain.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            dispatched.setTextColor(Color.parseColor("#6E7E7E"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            dispatchedlayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tab2.setBackgroundColor(getResources().getColor(R.color.white));

            Fragment fragmentmanager = new Restaurant_Dashboard();
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
            home.setTextColor(RestaurantMain.this.getResources().getColor(R.color.darkblue));
            orders.setTextColor(Color.parseColor("#707070"));
            dispatched.setTextColor(Color.parseColor("#707070"));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            dispatchedlayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.golddark));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tab2.setBackgroundColor(getResources().getColor(R.color.white));
            Fragment fragmentmanager = new Restaurant_Dashboard();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

        }
        if(view.getId()== R.id.eventlayout){
            isvisisble=false;

            Fragment fragmentmanager = new Restaurant_Dashboard_Clearance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

            orders.setTextColor(RestaurantMain.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            dispatched.setTextColor(Color.parseColor("#6E7E7E"));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            dispatchedlayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tab2.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.golddark));

        }
   if(view.getId()== R.id.dispatchedlayout){
            isvisisble=false;

            Fragment fragmentmanager = new Restaurant_Dashboard_Dispached();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rootLayout, fragmentmanager, fragmentmanager.getClass().getSimpleName()).addToBackStack(null).commit();

            dispatched.setTextColor(RestaurantMain.this.getResources().getColor(R.color.darkblue));
            home.setTextColor(Color.parseColor("#6E7E7E"));
            orders.setTextColor(Color.parseColor("#6E7E7E"));
            homelayout.setBackgroundColor(getResources().getColor(R.color.white));
            dispatchedlayout.setBackgroundColor(getResources().getColor(R.color.white));
            orderslayout.setBackgroundColor(getResources().getColor(R.color.white));
            tab.setBackgroundColor(getResources().getColor(R.color.white));
            tab1.setBackgroundColor(getResources().getColor(R.color.white));
            tab2.setBackgroundColor(getResources().getColor(R.color.golddark));

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
                if (Network.isNetworkAvailable(RestaurantMain.this)) {
                    new  IRDDatamenu().execute();

//

                } else if (Network.isNetworkAvailable2(RestaurantMain.this)) {
                    new IRDDatamenu().execute();



                }
                else{
           /* if (sessionManager.getIsINternet().equals("false")) {
                Intent intent = new Intent(RestaurantMain.this, Internetconnection.class);
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
                        .replace(R.id.rootLayout, Resraurant_History.newInstance())
                        .commitNow();
                break;

            case R.id.navigation_dashboard:
                final Dialog dialog = new Dialog(RestaurantMain.this);
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
                TextView textView=(TextView)dialog.findViewById(R.id.text) ;
                // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                TextView confirm=dialog.findViewById(R.id.confirm) ;
                TextView cancel1=dialog.findViewById(R.id.cancel);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(RestaurantMain.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(RestaurantMain.this)){
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
        final Dialog dialog = new Dialog(RestaurantMain.this);
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
                RestaurantMain.this.finish();
                dialog.dismiss();
            }
        });

    }
    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
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
                    Toast.makeText(RestaurantMain.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
                try {
                    progressDialog.dismiss();
                }
                catch (Exception e){

                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        try {

            registerReceiver(mMessageReceiver, new IntentFilter("com.viralops.touchlessfoodordering"));

            //unregisterReceiver(mMessageReceiver);

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

                Fragment fragment1 = new Restaurant_Dashboard();


                getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
            }
            else {
                AnimateBell();
                if(Network.isNetworkAvailable(RestaurantMain.this)){
                    GetMenu();
                }
                else if(Network.isNetworkAvailable2(RestaurantMain.this)){
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

         shake = AnimationUtils.loadAnimation(RestaurantMain.this, R.anim.shakeanimation);
        homeicon.setAnimation(shake);

    }

    //------------------IRD MENU--------------------------//



    public class IRDAdapter extends  RecyclerView.Adapter<IRDAdapter.ViewHolder>{
        ArrayList<Resturant_Data_data> order_items;
        Context context;
        String enabled;
        ArrayList<Restaurant_SubCategory> iRd_subCategories;
        ArrayList<Restaurant_Item> iRd_withCategories;

        public IRDAdapter(ArrayList<Resturant_Data_data> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public IRDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenu, parent, false);
            return new IRDAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Resturant_Data_data> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull final IRDAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            if(!holder.mitem.getDescription().equals("null")){
                holder.descriprtion.setText(Html.fromHtml(holder.mitem.getDescription(), Html.FROM_HTML_MODE_LEGACY));

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
            Resturant_Data_data mitem;

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
                        irdmenuslist.clear();
                        for (int j = 0; j < order_items.get(getAdapterPosition()).getCategories().length(); j++) {
                            JSONObject jsonObject1 = null;
                            try {
                                jsonObject1 = order_items.get(getAdapterPosition()).getCategories().getJSONObject(j);
                                Restaurant_Category ird_category = new Restaurant_Category();
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
                        if(irdmenuslist.size()!=0){
                            if(order_items.get(getAdapterPosition()).getEnabled().equals("1")) {
                                final Dialog dialog = new Dialog(RestaurantMain.this);
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
                                shimmerRecyclerViewcategory.setLayoutManager(new LinearLayoutManager(RestaurantMain.this, LinearLayoutManager.VERTICAL, false));
                                TextView title = dialog.findViewById(R.id.hotel);
                                title.setTypeface(font);
                                title.setText(order_items.get(getAdapterPosition()).getName());
                                final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                                final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                                irdcAtegoryAdapter = new IRDCAtegoryAdapter(irdmenuslist, RestaurantMain.this, order_items.get(getAdapterPosition()).getId());
                                shimmerRecyclerViewcategory.setAdapter(irdcAtegoryAdapter);
                                registerForContextMenu(menubutton);
                                ImageView close = dialog.findViewById(R.id.close);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        if (Network.isNetworkAvailable(RestaurantMain.this)) {
                                            // new IRDenu().execute();
                                            // dialog.dismiss();

                                        } else if (Network.isNetworkAvailable2(RestaurantMain.this)) {
                                            //  new IRDenu().execute();

                                        } else {

                                        }
                                    }
                                });

                                menubutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        menudialog = new Dialog(RestaurantMain.this);
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
                                        recyclerView.setLayoutManager(new LinearLayoutManager(RestaurantMain.this, LinearLayoutManager.VERTICAL, false));

                                        CartegoryAdapter   menupopupadapeter = new CartegoryAdapter(irdmenuslist, RestaurantMain.this);
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
                                Toast.makeText(context,"Menu is diabled",Toast.LENGTH_SHORT).show();

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
    public class IRDCAtegoryAdapter extends  RecyclerView.Adapter<IRDCAtegoryAdapter.ViewHolder>{
        ArrayList<Restaurant_Category> order_items;
        Context context;
        String enabled;
        String id;
        ArrayList<Restaurant_SubCategory> iRd_subCategories;
        ArrayList<Restaurant_Item> iRd_withCategories;
        IRD_SubCATegoryAdapter order_itemAdapterdetail;
        public IRDCAtegoryAdapter(ArrayList<Restaurant_Category> order_items, Context context,String id) {
            this.order_items = order_items;
            this.context = context;
            this.id=id;
        }

        @NonNull
        @Override
        public IRDCAtegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenuparent, parent, false);
            return new IRDCAtegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Restaurant_Category> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRDCAtegoryAdapter.ViewHolder holder, int position) {
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
                            GetMenucategoryenable(holder.mitem.getId(),id);
                            enabled="1";
                            //order_itemAdapterdetail.notifyDataSetChanged();
                        }  else if (Network.isNetworkAvailable2(context)) {
                            GetMenucategoryenable(holder.mitem.getId(),id);
                            enabled="1";
                            // order_itemAdapterdetail.notifyDataSetChanged();


                        }
                        else{

                        }
                    }
                    else{
                        if (Network.isNetworkAvailable(context)) {
                            GetMenucategorydisable(holder.mitem.getId(),id);
                            enabled="0";
                            //order_itemAdapterdetail.notifyDataSetChanged();


                        }  else if (Network.isNetworkAvailable2(context)) {
                            GetMenucategorydisable(holder.mitem.getId(),id);
                            enabled="0";
                            //  order_itemAdapterdetail.notifyDataSetChanged();


                        }
                        else{

                        }
                    }
                }
            });
            iRd_subCategories=new ArrayList<>();
            iRd_subCategories.clear();
            if(holder.mitem.getWithout_sub_category_items().length()!=0) {


                Restaurant_SubCategory iRd_subCategory = new Restaurant_SubCategory();
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
                        Restaurant_SubCategory iRd_subCategory = new Restaurant_SubCategory();
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


            order_itemAdapterdetail = new IRD_SubCATegoryAdapter(iRd_subCategories, context, holder.mitem.getName(), enabled,id);
            holder.shimmerRecyclerView.setAdapter(order_itemAdapterdetail);
          /*  iRd_subCategories=new ArrayList<>();
            iRd_subCategories.clear();
            if(holder.mitem.getSub_categories().length()!=0) {

                for (int i = 0; i < holder.mitem.getSub_categories().length(); i++) {
                    try {
                        JSONObject jsonObject = holder.mitem.getSub_categories().getJSONObject(i);
                        Restaurant_SubCategory iRd_subCategory = new Restaurant_SubCategory();
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
                        Restaurant_Item iRd_subCategory=new Restaurant_Item();
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


            Restaurant_Category mitem;

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
    public class IRD_SubCATegoryAdapter extends  RecyclerView.Adapter<IRD_SubCATegoryAdapter.ViewHolder>{
        ArrayList<Restaurant_SubCategory> order_items;
        ArrayList<Restaurant_SubCategory> continentList;
        String menuid;
        Context context;
        String category;
        String enabled;
        String enabled1;
        ArrayList<Restaurant_Item> ird_itemArrayList;

        public IRD_SubCATegoryAdapter(ArrayList<Restaurant_SubCategory> order_items, Context context,String category,String enabled,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.menuid=menuid;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public IRD_SubCATegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemsub, parent, false);
            return new IRD_SubCATegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Restaurant_SubCategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRD_SubCATegoryAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            if(holder.mitem.getName().equals("")){
                holder.parent.setVisibility(View.GONE);
            }
            else{
                holder.parent.setVisibility(View.VISIBLE);
                holder.title.setText(holder.mitem.getName());


            }

            if(!holder.mitem.getDescription().toString().equals("null")) {
                holder.descriprtion.setText(holder.mitem.getDescription());
            }
            else{
                holder.descriprtion.setText("");

            }

            if(enabled.equals("1")){
                holder.toggleButton1.setEnabled(true);
                holder.toggleButton1.setClickable(true);
                if(holder.mitem.getEnabled().equals("1")){
                    holder.toggleButton1.setChecked(true);
                    enabled1="1";
                }
                else{
                    holder.toggleButton1.setChecked(false);
                    enabled1="0";


                }
            }
            else{
                enabled1="0";
                holder.toggleButton1.setChecked(false);
                holder.toggleButton1.setEnabled(false);
                holder.toggleButton1.setClickable(false);
            }




            holder.toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        if (Network.isNetworkAvailable(context)){
                            GetMenuSUbcategoryenable(holder.mitem.getId(),menuid);
                            enabled1="1";

                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            GetMenuSUbcategoryenable(holder.mitem.getId(),menuid);
                            enabled1="1";

                        }
                        else{

                        }
                    }
                    else{


                        if (Network.isNetworkAvailable(context)){
                            GetMenuSUbcategorydisable(holder.mitem.getId(),menuid);
                            enabled1="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            GetMenuSUbcategorydisable(holder.mitem.getId(),menuid);
                            enabled1="0";


                        }
                        else{

                        }
                    }
                }
            });
            ird_itemArrayList=new ArrayList<>();

            for(int i=0;i<holder.mitem.getItems().length();i++){
                try {
                    JSONObject jsonObject=holder.mitem.getItems().getJSONObject(i);
                    Restaurant_Item iRd_subCategory=new Restaurant_Item();
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
                    iRd_subCategory.setSub_addons(jsonObject.getJSONArray("sub_addons"));
                    iRd_subCategory.setWithout_subaddon_addons(jsonObject.getJSONArray("without_subaddon_addons"));
                    iRd_subCategory.setMenu_sub_category_id(jsonObject.getString("menu_sub_category_id"));
                    ird_itemArrayList.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            IRD_ItemsAdapter order_itemAdapterdetail=new IRD_ItemsAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1,menuid);
            holder.rv_child.setAdapter(order_itemAdapterdetail);

        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView descriprtion;
            RecyclerView rv_child;
            ToggleButton toggleButton1;
            LinearLayout parent;

            Restaurant_SubCategory mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.category);
                descriprtion=itemView.findViewById(R.id.descriprtion);
                parent=itemView.findViewById(R.id.parent);
                rv_child=itemView.findViewById(R.id.rv_child);
                rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                toggleButton1=itemView.findViewById(R.id.toggleButton1);

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
    public class IRD_ItemsAdapter extends  RecyclerView.Adapter<IRD_ItemsAdapter.ViewHolder>{
        ArrayList<Restaurant_Item> order_items;
        ArrayList<Restaurant_Addons> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String menuid;

        public IRD_ItemsAdapter(ArrayList<Restaurant_Item> order_items, Context context,String category,String enabled,String enabled1,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.menuid=menuid;
        }

        @NonNull
        @Override
        public IRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new IRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Restaurant_Item> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRD_ItemsAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            enabled2=holder.mitem.getEnabled();
            if(holder.mitem.getPrice().equals("0")){
              holder.price.setVisibility(View.GONE);
              holder.price1.setVisibility(View.GONE);
            }
            else{
                holder.price.setVisibility(View.VISIBLE);
                holder.price1.setVisibility(View.VISIBLE);
                holder.price1.setText(holder.mitem.getPrice());
                holder.price.setText(getResources().getString(R.string.Rs));

            }
            if(!holder.mitem.getDescription().equals("null")){
                holder.ingredinets.setText(holder.mitem.getDescription());

            }
            else{
                holder.ingredinets.setText("");

            }
            if(enabled.equals("1")){
                if(enabled1.equals("1")) {
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
            }
            else{

                holder.toggleButton1.setChecked(false);
                holder.toggleButton1.setEnabled(false);
                holder.toggleButton1.setClickable(false);
                holder.gradeout.setVisibility(View.VISIBLE);
            }

            if(holder.mitem.getType().equals("veg")){
                holder.category.setVisibility(View.VISIBLE);

                holder.category.setImageResource(R.mipmap.veggg);
            }
            else if(holder.mitem.getType().equals("non_veg")){
                holder.category.setVisibility(View.VISIBLE);

                holder.category.setImageResource(R.mipmap.nonveggg);

            }
            else{
                holder.category.setVisibility(View.GONE);

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

            if (!holder.mitem.getTags().equals("null")) {
                String strings=holder.mitem.getTags().replaceAll("/","").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"","");

                //Split string with comma

                String[] namesArray = strings.split(",");
                Arrays.asList(namesArray);
                TagsAdapter tagsAdapter = new TagsAdapter(namesArray, context);
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
                            GetMenuitemenab(holder.mitem.getId(),menuid);
                            enabled2="1";
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            GetMenuitemenab(holder.mitem.getId(),menuid);
                            enabled2="1";

                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            GetMenuitemdisable(holder.mitem.getId(),menuid);
                            enabled2="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            GetMenuitemdisable(holder.mitem.getId(),menuid);
                            enabled2="0";


                        }
                        else{

                        }
                    }
                }
            });
            if(holder.mitem.getSub_addons().length()!=0){
                holder.customise.setVisibility(View.VISIBLE);


            }
            else{
                holder.customise.setVisibility(View.GONE);
            }
            continentList=new ArrayList<>();

            for(int i=0;i<holder.mitem.getSub_addons().length();i++){
                try {
                    JSONObject jsonObject=holder.mitem.getSub_addons().getJSONObject(i);
                    Restaurant_Addons iRd_subCategory=new Restaurant_Addons();
                    iRd_subCategory.setId(jsonObject.getString("id"));
                    iRd_subCategory.setMenu_item_id(jsonObject.getString("menu_item_id"));
                    iRd_subCategory.setName(jsonObject.getString("name"));
                    iRd_subCategory.setDescription(jsonObject.getString("description"));
                    iRd_subCategory.setTags(jsonObject.getString("tags"));
                    iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                    iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                    iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                    iRd_subCategory.setType(jsonObject.getString("type"));
                    iRd_subCategory.setMax_addon_per_order(jsonObject.getString("max_addon_per_order"));
                    iRd_subCategory.setMin_addon_per_order(jsonObject.getString("min_addon_per_order"));
                    iRd_subCategory.setAddons(jsonObject.getJSONArray("addons"));
                    continentList.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(holder.mitem.getWithout_subaddon_addons().length()!=0) {
                try {
                    Restaurant_Addons iRd_subCategory=new Restaurant_Addons();
                    iRd_subCategory.setId("");
                    iRd_subCategory.setMenu_item_id("");
                    iRd_subCategory.setName("");
                    iRd_subCategory.setDescription("");
                    iRd_subCategory.setTags("");
                    iRd_subCategory.setEnabled("1");
                    iRd_subCategory.setCreated_at("");
                    iRd_subCategory.setUpdated_at("");
                    iRd_subCategory.setType("");
                    iRd_subCategory.setMax_addon_per_order("");
                    iRd_subCategory.setMin_addon_per_order("");
                    iRd_subCategory.setAddons(holder.mitem.getWithout_subaddon_addons());
                    continentList.add(iRd_subCategory);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            IRD_SubAddonAdapter order_itemAdapterdetail=new IRD_SubAddonAdapter(continentList,context,holder.mitem.getName(),enabled,enabled1,enabled2,menuid);
            holder.customiselist.setAdapter(order_itemAdapterdetail);

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


            Restaurant_Item mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                category1=itemView.findViewById(R.id.category1);
                category=itemView.findViewById(R.id.category);
                price=itemView.findViewById(R.id.price);
                price1=itemView.findViewById(R.id.price1);
                ingredinets=itemView.findViewById(R.id.ingredinets);
                spicy=itemView.findViewById(R.id.spicy);
                customise=itemView.findViewById(R.id.customise);
                customiselist=itemView.findViewById(R.id.customiselist);
                spicy.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
                customiselist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
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
    public class IRD_SubAddonAdapter extends  RecyclerView.Adapter<IRD_SubAddonAdapter.ViewHolder>{
        ArrayList<Restaurant_Addons> order_items;
        ArrayList<Restaurant_AddonsItem> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        String menuid;
        ArrayList<Restaurant_AddonsItem> ird_itemArrayList;

        public IRD_SubAddonAdapter(ArrayList<Restaurant_Addons> order_items, Context context,String category,String enabled,String enabled1,String enabled2,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            this.menuid=menuid;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public IRD_SubAddonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenusubaddon, parent, false);
            return new IRD_SubAddonAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Restaurant_Addons> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRD_SubAddonAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            if(holder.mitem.getName().equals("")){
                holder.parent.setVisibility(View.GONE);
            }
            else{
                holder.parent.setVisibility(View.VISIBLE);
                holder.title.setText(holder.mitem.getName());


            }
            enabled3=holder.mitem.getEnabled();

            if(!holder.mitem.getDescription().toString().equals("null")) {
                holder.descriprtion.setText(holder.mitem.getDescription());
            }
            else{
                holder.descriprtion.setText("");

            }

            if(enabled.equals("1")){
                if(enabled1.equals("1")) {
                    if(enabled2.equals("1")) {
                        holder.toggleButton1.setEnabled(true);
                        holder.toggleButton1.setClickable(true);
                        if (holder.mitem.getEnabled().equals("1")) {
                            holder.toggleButton1.setChecked(true);
                        } else {
                            holder.toggleButton1.setChecked(false);

                        }
                    }
                    else{
                        holder.toggleButton1.setChecked(false);
                        holder.toggleButton1.setEnabled(false);
                        holder.toggleButton1.setClickable(false);
                    }
                }
                else{

                    holder.toggleButton1.setChecked(false);
                    holder.toggleButton1.setEnabled(false);
                    holder.toggleButton1.setClickable(false);
                }
            }
            else{

                holder.toggleButton1.setChecked(false);
                holder.toggleButton1.setEnabled(false);
                holder.toggleButton1.setClickable(false);
            }



            holder.toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        if (Network.isNetworkAvailable(context)){
                            GetMenuaddoncategoryenable(holder.mitem.getId(),menuid);
                            enabled3="1";

                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            GetMenuaddoncategoryenable(holder.mitem.getId(),menuid);
                            enabled3="1";

                        }
                        else{

                        }
                    }
                    else{


                        if (Network.isNetworkAvailable(context)){
                            GetMenuaddoncategorydisable(holder.mitem.getId(),menuid);
                            enabled3="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            GetMenuaddoncategorydisable(holder.mitem.getId(),menuid);
                            enabled3="0";


                        }
                        else{

                        }
                    }
                }
            });
            ird_itemArrayList=new ArrayList<>();

            for(int i=0;i<holder.mitem.getAddons().length();i++){
                try {
                    JSONObject jsonObject=holder.mitem.getAddons().getJSONObject(i);
                    Restaurant_AddonsItem iRd_subCategory=new Restaurant_AddonsItem();
                    iRd_subCategory.setId(jsonObject.getString("id"));
                    iRd_subCategory.setMenu_item_id(jsonObject.getString("menu_item_id"));
                    iRd_subCategory.setName(jsonObject.getString("name"));
                    iRd_subCategory.setDescription(jsonObject.getString("description"));
                    iRd_subCategory.setTags(jsonObject.getString("tags"));
                    iRd_subCategory.setEnabled(jsonObject.getString("enabled"));
                    iRd_subCategory.setCreated_at(jsonObject.getString("created_at"));
                    iRd_subCategory.setUpdated_at(jsonObject.getString("updated_at"));
                    iRd_subCategory.setType(jsonObject.getString("type"));
                    iRd_subCategory.setPrice(jsonObject.getString("price"));
                    iRd_subCategory.setMax_per_order(jsonObject.getString("max_per_order"));
                    iRd_subCategory.setMin_per_order(jsonObject.getString("min_per_order"));
                    iRd_subCategory.setThumbnail(jsonObject.getString("thumbnail"));
                    iRd_subCategory.setMenu_item_subaddon_id(jsonObject.getString("menu_item_subaddon_id"));
                    ird_itemArrayList.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            IRD_ItemsWithoutAdapter order_itemAdapterdetail=new IRD_ItemsWithoutAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1,enabled2,enabled3,menuid);
            holder.rv_child.setAdapter(order_itemAdapterdetail);

        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView descriprtion;
            RecyclerView rv_child;
            ToggleButton toggleButton1;
            LinearLayout parent;

            Restaurant_Addons mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.category);
                descriprtion=itemView.findViewById(R.id.descriprtion);
                parent=itemView.findViewById(R.id.parent);
                rv_child=itemView.findViewById(R.id.rv_child);
                rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                toggleButton1=itemView.findViewById(R.id.toggleButton1);

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

    public class IRD_ItemsWithoutAdapter extends  RecyclerView.Adapter<IRD_ItemsWithoutAdapter.ViewHolder>{
        ArrayList<Restaurant_AddonsItem> order_items;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        String enabled4;
        String menuid;

        public IRD_ItemsWithoutAdapter(ArrayList<Restaurant_AddonsItem> order_items, Context context,String category,String enabled,String enabled1,String enabled2,String enabled3,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            this.enabled3=enabled3;
            this.menuid=menuid;
        }

        @NonNull
        @Override
        public IRD_ItemsWithoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.addon_item, parent, false);
            return new IRD_ItemsWithoutAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Restaurant_AddonsItem> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRD_ItemsWithoutAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            holder.price.setText(getResources().getString(R.string.Rs));
            if(holder.mitem.getPrice().equals("0")){
                holder.price1.setVisibility(View.GONE);
                holder.price.setVisibility(View.GONE);
            }
            else {
                holder.price1.setText(holder.mitem.getPrice());
                holder.price.setText(getResources().getString(R.string.Rs));
                holder.price1.setVisibility(View.VISIBLE);
                holder.price.setVisibility(View.VISIBLE);

            }                 if(!holder.mitem.getDescription().equals("null")){
                holder.ingredinets.setText(holder.mitem.getDescription());
            }
            else{
                holder.ingredinets.setText("");
                holder.ingredinets.setVisibility(View.GONE);

            }
            if(enabled.equals("1")){
                if(enabled1.equals("1")) {
                    if(enabled2.equals("1")) {
                        if(enabled3.equals("1")) {
                            holder.toggleButton1.setEnabled(true);
                            holder.toggleButton1.setClickable(true);
                            if (holder.mitem.getEnabled().equals("1")) {
                                holder.toggleButton1.setChecked(true);
                            } else {
                                holder.toggleButton1.setChecked(false);

                            }
                        }
                        else{
                            holder.toggleButton1.setChecked(false);
                            holder.toggleButton1.setEnabled(false);
                            holder.toggleButton1.setClickable(false);
                        }
                    }
                    else{
                        holder.toggleButton1.setChecked(false);
                        holder.toggleButton1.setEnabled(false);
                        holder.toggleButton1.setClickable(false);
                    }
                }
                else{

                    holder.toggleButton1.setChecked(false);
                    holder.toggleButton1.setEnabled(false);
                    holder.toggleButton1.setClickable(false);
                }
            }
            else{

                holder.toggleButton1.setChecked(false);
                holder.toggleButton1.setEnabled(false);
                holder.toggleButton1.setClickable(false);
            }
            if(holder.mitem.getType().equals("veg")){
                holder.category.setImageResource(R.mipmap.veggg);
            }
            else{
                holder.category.setImageResource(R.mipmap.nonveggg);

            }

            if (!holder.mitem.getThumbnail().equals("null") ) {
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
            holder.toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){

                        holder.gradeout.setVisibility(View.GONE);
                        if (Network.isNetworkAvailable(context)){
                            GetMenuaddenab(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            GetMenuaddenab(holder.mitem.getId());
                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            GetMenuadddisable(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            GetMenuadddisable(holder.mitem.getId());

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
            TextView ingredinets;
            ImageView dish;
            ImageView category;
            LinearLayout gradeout;
            ToggleButton toggleButton1;


            Restaurant_AddonsItem mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                category1=itemView.findViewById(R.id.category1);
                category=itemView.findViewById(R.id.category);
                price=itemView.findViewById(R.id.price);
                price1=itemView.findViewById(R.id.price1);
                ingredinets=itemView.findViewById(R.id.ingredinets);
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

    public class IRDDatamenu extends AsyncTask<String, String, String> {
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);


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
                        .url(BuildConfig.BASE_URL + BuildConfig.restaurant_menu)
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
            irdmenuslist.clear();
            irddataenulist.clear();
            if(progressDialog!=null){
                progressDialog.dismiss();
            }

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);
                        Resturant_Data_data ird_data = new Resturant_Data_data();
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
                            Restaurant_Category ird_category = new Restaurant_Category();
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
                        irddataenulist.add(ird_data);


                    }
                    final Dialog dialog1 = new Dialog(RestaurantMain.this);
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
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(RestaurantMain.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog1.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("MENU");
                    irdAdapter = new IRDAdapter(irddataenulist, RestaurantMain.this);
                    shimmerRecyclerView.setAdapter(irdAdapter);
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

                            if (Network.isNetworkAvailable(RestaurantMain.this)) {
                                //  new IRDDatamenu().execute();

                            } else if (Network.isNetworkAvailable2(RestaurantMain.this)) {
                                // new IRDDatamenu().execute();
                                dialog1.dismiss();

                            } else {

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
    public class IRDDatamenu1 extends AsyncTask<String, String, String> {


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
                        .url(BuildConfig.BASE_URL + BuildConfig.restaurant_menu)
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
            irdmenuslist.clear();
            irddataenulist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);
                        Resturant_Data_data ird_data = new Resturant_Data_data();
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
                            Restaurant_Category ird_category = new Restaurant_Category();
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
                        irddataenulist.add(ird_data);


                    }
                   irdAdapter.notifyDataSetChanged();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public class IRDDatamenunew extends AsyncTask<String, String, String> {


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
                        .url(BuildConfig.BASE_URL + BuildConfig.ird_menu)
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
            irdmenuslist.clear();
            irdcategorylist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);
                        Resturant_Data_data ird_data = new Resturant_Data_data();
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
                            Restaurant_Category ird_category = new Restaurant_Category();
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
                        irddataenulist.add(ird_data);
                        irdAdapter =new IRDAdapter(irddataenulist,RestaurantMain.this);
                        shimmerRecyclerView.setAdapter(irdAdapter);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public class IRDenu extends AsyncTask<String, String, String> {
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
                        .url(BuildConfig.BASE_URL + BuildConfig.restaurant_menu)
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
            irdmenuslist.clear();
            irdcategorylist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray data=jsonObject.getJSONArray("data");

                    for(int j=0;j<data.length();j++) {
                        JSONObject jsonObject2 = data.getJSONObject(j);
                        if(jsonObject2.getString("id").equals(id)){

                            for (int i = 0; i < jsonObject2.getJSONArray("categories").length(); i++) {
                                JSONObject jsonObject1 = jsonObject2.getJSONArray("categories").getJSONObject(i);
                                Restaurant_Category ird_category = new Restaurant_Category();
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
                                irdcategorylist.add(ird_category);
                            }
                            irdcAtegoryAdapter.notifyDataSetChanged();

                        }




                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public class IRDenunew extends AsyncTask<String, String, String> {


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
                        .url(BuildConfig.BASE_URL + BuildConfig.ird_menu)
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
            irdmenuslist.clear();
            irdcategorylist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONObject data=jsonObject.getJSONObject("data");
                    Restaurant_Data ird_data=new Restaurant_Data();
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
                        Restaurant_Category ird_category=new Restaurant_Category();
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
                        irdcategorylist.add(ird_category);
                    }

                    // irdcAtegoryAdapter =new IRDCAtegoryAdapter(irdcategorylist,RestaurantMain.this,);
                    //  shimmerRecyclerView.setAdapter(irdcAtegoryAdapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    private void GetMenuitemenab(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetItemEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuitemdisable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetItemDisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_menu_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDDatamenu1().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Restaurantgetdisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_disable_menu+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDDatamenu1().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenucategoryenable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetcategoryEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_enable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenucategorydisable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Restaurantgetcategorydisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_disable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuaddenab(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetSubaddonitemenable("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_addon_itemenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuadddisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetSubaddonitemdisable("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_addon_itemdisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuaddoncategoryenable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetSubaddonenable("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_addon_categoryenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuaddoncategorydisable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetSubaddondisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_addon_categorydisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuSUbcategoryenable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetSubcategoryEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_enable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetMenuSUbcategorydisable(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMain.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetSubcategorydisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.restaurant_disable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(RestaurantMain.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(RestaurantMain.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    public class TagsAdapter extends  RecyclerView.Adapter<TagsAdapter.ViewHolder>{
        String[] order_items;
        Context context;
        String enabled;

        public TagsAdapter(String[] order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout, parent, false);
            return new TagsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final TagsAdapter.ViewHolder holder, int position) {


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

    public class CartegoryAdapter extends  RecyclerView.Adapter<CartegoryAdapter.ViewHolder>{
        ArrayList<Restaurant_Category> order_items;
        Context context;

        public CartegoryAdapter(ArrayList<Restaurant_Category> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public CartegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item, parent, false);
            return new CartegoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartegoryAdapter.ViewHolder holder, final int position) {
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



            Restaurant_Category mitem;

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

    //-----------------END OF IRD MENU-------------------//
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

        (RetrofitClientInstance.getApiService().RestaurantgetHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header login = response.body();
                    newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                    newcountevent.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");
                    newcountdispatched.setText("( "+String.valueOf(login.getData().getDispatched_order())+" )");


                }
                else if(response.code()==401){
                    Header login = response.body();
                    Toast.makeText(RestaurantMain.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(RestaurantMain.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void GetMenu() {
        // display a progress dialog
      //  recyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().RestaurantgetOrders("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<RestaurantApp_Dashboard>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantApp_Dashboard> call, @NonNull Response<RestaurantApp_Dashboard> response) {

                if(response.code()==201||response.code()==200){
                    RestaurantApp_Dashboard  login = response.body();
                    queuelist=new ArrayList<>();
                    queuelistnew=new ArrayList<>();

                    queuelist=login.getData();
                    if(queuelist.size()!=0){
                        for(int i=0;i<queuelist.size();i++){
                            if(queuelist.get(i).getStatus().equals("new_order")&&queuelist.get(i).getOrder_detail()!=null){
                                RestaurantApp_Dashboard.Data data=new RestaurantApp_Dashboard.Data();
                                data.setCreated_at(queuelist.get(i).getCreated_at());
                                data.setDescription(queuelist.get(i).getDescription());
                                data.setHotel_id(queuelist.get(i).getHotel_id());
                                data.setId(queuelist.get(i).getId());
                                data.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                data.setOrder_detail(queuelist.get(i).getOrder_detail());
                                data.setOrder_menu_items(queuelist.get(i).getOrder_menu_items());
                                data.setPayment_status(queuelist.get(i).getPayment_status());
                                data.setTable(queuelist.get(i).getTable());
                                data.setPrimises_id(queuelist.get(i).getPrimises_id());
                                data.setStatus(queuelist.get(i).getStatus());
                                data.setType(queuelist.get(i).getType());
                                data.setUpdated_at(queuelist.get(i).getUpdated_at());
                                long timeDiff = 0;
                                Date date1 = null;
                                Date date2 = null;
                                final Calendar calendar = Calendar.getInstance();
                                String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                                System.out.println("current date" + timeStamp + " " + queuelist.get(i).getCreated_at());
                                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                                long epoch = 0;

                                try {
                                    dt = dateFormat.parse(queuelist.get(i).getCreated_at());
                                    epoch = dt.getTime();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }




                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                cal.setTimeInMillis(epoch);
                                String date23 = android.text.format.DateFormat.format("dd MMM yyyy hh:mm a",dt ).toString();
                                try {
                                    date1 = formatter.parse(timeStamp);
                                    date2 = formatter.parse(date23);
                                    System.out.println("current datem 2 " + date1 + " " + date2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    System.out.println("current datem 2 " + e.toString());

                                }  //  timeDiff = date1.getTime() - date2.getTime();
                                timeDiff = (date1.getTime() - date2.getTime()) - SystemClock.elapsedRealtime();
                                System.out.println(" time diff"
                                        + timeDiff);

                                data.setTimedifference(timeDiff);
                                data.setStatus("New Order");
                                queuelistnew.add(data);
                            }
                        }

                       /* if(getArguments().getString("visible").equals("1")){
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                           // recyclerView.smoothScrollToPosition(queuelistnew.size() - 1);

                            norecord.setVisibility(View.GONE);
                            RestaurantMain.homeicon.setVisibility(View.GONE);
                            RestaurantMain.homeicon.setVisibility(View.GONE);

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

                    newcount.setText("( "+String.valueOf(queuelistnew.size())+" )");





                }
                else if(response.code()==401){
                    RestaurantApp_Dashboard login = response.body();
               //     Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    //Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }

             //   recyclerView.hideShimmer();



            }

            @Override
            public void onFailure(@NonNull Call<RestaurantApp_Dashboard> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                //recyclerView.hideShimmer();

            }
        });

    }

}
