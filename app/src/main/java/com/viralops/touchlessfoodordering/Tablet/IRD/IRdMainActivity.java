package com.viralops.touchlessfoodordering.Tablet.IRD;

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
import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.IRD_Addons;
import com.viralops.touchlessfoodordering.Model.IRD_AddonsItem;
import com.viralops.touchlessfoodordering.Model.IRD_Category;
import com.viralops.touchlessfoodordering.Model.IRD_Data;
import com.viralops.touchlessfoodordering.Model.IRD_Data_data;
import com.viralops.touchlessfoodordering.Model.IRD_Item;
import com.viralops.touchlessfoodordering.Model.IRd_SubCategory;
import com.viralops.touchlessfoodordering.Model.Menu;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Internetconnection;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.Restaurant.Resturant_Tablet_MainActivity;

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

public class IRdMainActivity extends AppCompatActivity implements View.OnClickListener {
    static public TextView busytray;
    static public TextView busytrolley;
    static public TextView busyassciate;
    static public TextView availabletray;
    static public TextView availabletrolley;
    static public TextView availableassciate;
    static public TextView totaltext;
    static public TextView totalorders;

    private TextView text;

    private TextView text1;
    private CardView menu;
    private  TextView history;
    private  TextView dashboard;
    private ImageView logout;
    private SessionManager sessionManager;
    private SessionManagerFCM sessionManagerFCM;
    RecyclerView recyclerView;
    Dialog menudialog;
    IRDAdapter   irdAdapter;
    ArrayList<IRD_Data_data> irddataenulist=new ArrayList<>();
    ShimmerRecyclerView shimmerRecyclerViewcategory;

    ArrayList<IRD_Category> categorylist=new ArrayList<>();
    ArrayList<IRD_Category> irdcategorylist=new ArrayList<>();
    ArrayList<Menu.menu_data> menuslist=new ArrayList<>();
    ArrayList<IRD_Category> irdmenuslist=new ArrayList<>();
    ArrayList<IRD_Category> minibarmenulist=new ArrayList<>();

     Dialog dialog1;

    ShimmerRecyclerView shimmerRecyclerView;
    ShimmerRecyclerView shimmerRecyclerView1;

    static public int isvisisble=1;
    static  public ImageView imgBell;


     public String word="";
     LinearLayout hearderforird;


     LinearLayout irdlayout;
     LinearLayout layout;
    public static  LinearLayout neworderss;
     Typeface font;
    Typeface font1;


    IRDCAtegoryAdapter   irdcAtegoryAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.ird_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        sessionManager=new SessionManager(IRdMainActivity.this);
        sessionManagerFCM=new SessionManagerFCM(IRdMainActivity.this);
        sessionManager.setIsINternet("false");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Crashlytics.setUserIdentifier(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        FirebaseCrashlytics.getInstance().setUserId(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(sessionManager.getPorchName());
        mFirebaseAnalytics.setUserProperty("Id",sessionManager.getPorchName()+" "+sessionManager.getNAME());

        irdlayout=  findViewById(R.id.irdlayout);
        layout=  findViewById(R.id.layout);
        neworderss=  findViewById(R.id.neworderss);

        imgBell=  findViewById(R.id.bell);
         imgBell.setImageResource(R.mipmap.calling);
         imgBell.setVisibility(View.INVISIBLE);

         font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
         font1 = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Thin.ttf");


        busytray=findViewById(R.id.busytray);
        busytray.setTypeface(font);
        busytrolley=findViewById(R.id.busytrolley);
        busytrolley.setTypeface(font);
        busyassciate=findViewById(R.id.busyassociate);
        busyassciate.setTypeface(font);




        menu=findViewById(R.id.menu);


        availabletray=findViewById(R.id.avalbletray);
        availabletray.setTypeface(font1);
        availabletrolley=findViewById(R.id.avalbletrollet);
        availabletrolley.setTypeface(font1);
        availableassciate=findViewById(R.id.avalbleassociate);
        availableassciate.setTypeface(font1);
        totaltext=findViewById(R.id.total);
        totaltext.setTypeface(font);
        totalorders=findViewById(R.id.totalorders);
        hearderforird=findViewById(R.id.hearderforird);

        totalorders.setTypeface(font1);
        text=findViewById(R.id.text);
        text.setTypeface(font);
        text1=findViewById(R.id.text1);
        text.setText(sessionManager.getPorchName());
        history=findViewById(R.id.history);

        dashboard=findViewById(R.id.dashboard);
        irdlayout.setOnClickListener(this);
        irdlayout.setOnClickListener(this);

        text1.setTypeface(font1);
        text1.setText("");
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(IRdMainActivity.this);
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
                        if(Network.isNetworkAvailable(IRdMainActivity.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(IRdMainActivity.this)){
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




                irdlayout.setBackgroundColor(getResources().getColor(R.color.white));
                dashboard.setTextColor(getResources().getColor(R.color.gray));
                layout.setBackgroundColor(getResources().getColor(R.color.blue));
                history.setTextColor(getResources().getColor(R.color.white));



                isvisisble=4;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_view, OrderHistory.newInstance())
                            .commitNow();

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(word.equals("IRD")) {

                    if (Network.isNetworkAvailable(IRdMainActivity.this)) {
                        new  IRDDatamenu().execute();



                    } else if (Network.isNetworkAvailable2(IRdMainActivity.this)) {
                        new IRDDatamenu().execute();

                    }
                    else{
                        if (sessionManager.getIsINternet().equals("false")) {
                            Intent intent = new Intent(IRdMainActivity.this, Internetconnection.class);
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

            hearderforird.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);


            irdlayout.setBackgroundColor(getResources().getColor(R.color.blue));
            dashboard.setTextColor(getResources().getColor(R.color.white));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, IRdFreagment.newInstance())
                    .commitNow();
              word="IRD";

        }


    }

    @Override
    public void onClick(View v) {

         if(v.getId()== R.id.irdlayout){
            isvisisble=1;
             imgBell.setVisibility(View.GONE);

            word="IRD";

            hearderforird.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);




             layout.setBackgroundColor(getResources().getColor(R.color.white));
             history.setTextColor(getResources().getColor(R.color.gray));
             irdlayout.setBackgroundColor(getResources().getColor(R.color.blue));
             dashboard.setTextColor(getResources().getColor(R.color.white));


             getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, IRdFreagment.newInstance())
                    .commitNow();
        }

    }






    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
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
                    Toast.makeText(IRdMainActivity.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
            final Dialog dialog = new Dialog(IRdMainActivity.this);
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
                    IRdMainActivity.this.finish();
                    dialog.dismiss();
                }
            });

        }

    public void AnimateBell() {
        Animation shake = AnimationUtils.loadAnimation(IRdMainActivity.this, R.anim.shakeanimation);

        imgBell.setAnimation(shake);

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


    //------------------IRD MENU--------------------------//




    public class IRDAdapter extends  RecyclerView.Adapter<IRDAdapter.ViewHolder>{
        ArrayList<IRD_Data_data> order_items;
        Context context;
        String enabled;
        ArrayList<IRd_SubCategory> iRd_subCategories;
        ArrayList<IRD_Item> iRd_withCategories;

        public IRDAdapter(ArrayList<IRD_Data_data> order_items, Context context) {
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
        public void filterList(ArrayList<IRD_Data_data> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRDAdapter.ViewHolder holder, int position) {
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
            IRD_Data_data mitem;

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
                                final Dialog dialog = new Dialog(IRdMainActivity.this);
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
                                shimmerRecyclerViewcategory = dialog.findViewById(R.id.recyclerview);
                                shimmerRecyclerViewcategory.setLayoutManager(new LinearLayoutManager(IRdMainActivity.this, LinearLayoutManager.VERTICAL, false));
                                TextView title = dialog.findViewById(R.id.hotel);
                                title.setTypeface(font);
                                title.setText(order_items.get(getAdapterPosition()).getName());
                                final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                                final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                                irdcAtegoryAdapter = new IRdMainActivity.IRDCAtegoryAdapter(irdmenuslist, IRdMainActivity.this, order_items.get(getAdapterPosition()).getId());
                                shimmerRecyclerViewcategory.setAdapter(irdcAtegoryAdapter);
                                registerForContextMenu(menubutton);
                                ImageView close = dialog.findViewById(R.id.close);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        if (Network.isNetworkAvailable(IRdMainActivity.this)) {
                                            // new IRDenu().execute();
                                            // dialog.dismiss();

                                        } else if (Network.isNetworkAvailable2(IRdMainActivity.this)) {
                                            //  new IRDenu().execute();

                                        } else {

                                        }
                                    }
                                });

                                menubutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        menudialog = new Dialog(IRdMainActivity.this);
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
                                        recyclerView.setLayoutManager(new LinearLayoutManager(IRdMainActivity.this, LinearLayoutManager.VERTICAL, false));

                                        IRdMainActivity.CartegoryAdapter menupopupadapeter = new IRdMainActivity.CartegoryAdapter(irdmenuslist, IRdMainActivity.this);
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
        ArrayList<IRD_Category> order_items;
        Context context;
        String enabled;
        String id;
        ArrayList<IRd_SubCategory> iRd_subCategories;
        ArrayList<IRD_Item> iRd_withCategories;
        IRdMainActivity.IRD_SubCATegoryAdapter order_itemAdapterdetail;
        public IRDCAtegoryAdapter(ArrayList<IRD_Category> order_items, Context context,String id) {
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
        public void filterList(ArrayList<IRD_Category> filterdNames) {
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


            order_itemAdapterdetail = new IRdMainActivity.IRD_SubCATegoryAdapter(iRd_subCategories, context, holder.mitem.getName(), enabled,id);
            holder.shimmerRecyclerView.setAdapter(order_itemAdapterdetail);
          /*  iRd_subCategories=new ArrayList<>();
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


            IRD_Category mitem;

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
    public class IRD_SubCATegoryAdapter extends  RecyclerView.Adapter<IRdMainActivity.IRD_SubCATegoryAdapter.ViewHolder>{
        ArrayList<IRd_SubCategory> order_items;
        ArrayList<IRd_SubCategory> continentList;
        String menuid;
        Context context;
        String category;
        String enabled;
        String enabled1;
        ArrayList<IRD_Item> ird_itemArrayList;

        public IRD_SubCATegoryAdapter(ArrayList<IRd_SubCategory> order_items, Context context,String category,String enabled,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.menuid=menuid;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public IRdMainActivity.IRD_SubCATegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemsub, parent, false);
            return new IRdMainActivity.IRD_SubCATegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRd_SubCategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRdMainActivity.IRD_SubCATegoryAdapter.ViewHolder holder, int position) {
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
                    iRd_subCategory.setSub_addons(jsonObject.getJSONArray("sub_addons"));
                    iRd_subCategory.setWithout_subaddon_addons(jsonObject.getJSONArray("without_subaddon_addons"));
                    iRd_subCategory.setMenu_sub_category_id(jsonObject.getString("menu_sub_category_id"));
                    ird_itemArrayList.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            IRdMainActivity.IRD_ItemsAdapter order_itemAdapterdetail=new IRdMainActivity.IRD_ItemsAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1,menuid);
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

            IRd_SubCategory mitem;

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
    public class IRD_ItemsAdapter extends  RecyclerView.Adapter<IRdMainActivity.IRD_ItemsAdapter.ViewHolder>{
        ArrayList<IRD_Item> order_items;
        ArrayList<IRD_Addons> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String menuid;

        public IRD_ItemsAdapter(ArrayList<IRD_Item> order_items, Context context,String category,String enabled,String enabled1,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.menuid=menuid;
        }

        @NonNull
        @Override
        public IRdMainActivity.IRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new IRdMainActivity.IRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Item> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRdMainActivity.IRD_ItemsAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            holder.price.setText(getResources().getString(R.string.Rs));
            enabled2=holder.mitem.getEnabled();
            holder.price1.setText(holder.mitem.getPrice());
            if(holder.mitem.getPrice().equals("0")){
                holder.price.setVisibility(View.INVISIBLE);
                holder.price1.setVisibility(View.INVISIBLE);

            }
            else {
                holder.price.setVisibility(View.GONE);
                holder.price1.setVisibility(View.GONE);
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
                IRdMainActivity.TagsAdapter tagsAdapter = new IRdMainActivity.TagsAdapter(namesArray, context);
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
                    IRD_Addons iRd_subCategory=new IRD_Addons();
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
                    IRD_Addons iRd_subCategory=new IRD_Addons();
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

            IRdMainActivity.IRD_SubAddonAdapter order_itemAdapterdetail=new IRdMainActivity.IRD_SubAddonAdapter(continentList,context,holder.mitem.getName(),enabled,enabled1,enabled2,menuid);
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


            IRD_Item mitem;

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
    public class IRD_SubAddonAdapter extends  RecyclerView.Adapter<IRdMainActivity.IRD_SubAddonAdapter.ViewHolder>{
        ArrayList<IRD_Addons> order_items;
        ArrayList<IRD_AddonsItem> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        String menuid;
        ArrayList<IRD_AddonsItem> ird_itemArrayList;

        public IRD_SubAddonAdapter(ArrayList<IRD_Addons> order_items, Context context,String category,String enabled,String enabled1,String enabled2,String menuid) {
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
        public IRdMainActivity.IRD_SubAddonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenusubaddon, parent, false);
            return new IRdMainActivity.IRD_SubAddonAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Addons> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRdMainActivity.IRD_SubAddonAdapter.ViewHolder holder, int position) {
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
                    IRD_AddonsItem iRd_subCategory=new IRD_AddonsItem();
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

            IRdMainActivity.IRD_ItemsWithoutAdapter order_itemAdapterdetail=new IRdMainActivity.IRD_ItemsWithoutAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1,enabled2,enabled3,menuid);
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

            IRD_Addons mitem;

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

    public class IRD_ItemsWithoutAdapter extends  RecyclerView.Adapter<IRdMainActivity.IRD_ItemsWithoutAdapter.ViewHolder>{
        ArrayList<IRD_AddonsItem> order_items;
        ArrayList<Menu.Items> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        String enabled4;
        String menuid;

        public IRD_ItemsWithoutAdapter(ArrayList<IRD_AddonsItem> order_items, Context context,String category,String enabled,String enabled1,String enabled2,String enabled3,String menuid) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            this.enabled3=enabled3;
            this.menuid=menuid;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public IRdMainActivity.IRD_ItemsWithoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.addon_item, parent, false);
            return new IRdMainActivity.IRD_ItemsWithoutAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_AddonsItem> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRdMainActivity.IRD_ItemsWithoutAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            holder.price.setText(getResources().getString(R.string.Rs));
            holder.price1.setText(holder.mitem.getPrice());
            if(holder.mitem.getPrice().equals("0")){
                holder.price.setVisibility(View.INVISIBLE);
                holder.price1.setVisibility(View.INVISIBLE);
            }
            else{
                holder.price.setVisibility(View.VISIBLE);
                holder.price1.setVisibility(View.VISIBLE);
            }
            if(!holder.mitem.getDescription().equals("null")){
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


            IRD_AddonsItem mitem;

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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);


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
                        IRD_Data_data ird_data = new IRD_Data_data();
                        ird_data.setId(data.getString("id"));
                        ird_data.setName(data.getString("name"));
                        ird_data.setHotel_id(data.getString("hotel_id"));
                        ird_data.setDescription(data.getString("description"));
                        ird_data.setEnabled(data.getString("enabled"));
                        ird_data.setCreated_at(data.getString("created_at"));
                        ird_data.setUpdated_at(data.getString("updated_at"));
                        JSONArray jsonArray1 = data.getJSONArray("categories");
                        ird_data.setCategories(jsonArray1);


                        irddataenulist.add(ird_data);


                    }
                    final Dialog dialog = new Dialog(IRdMainActivity.this);
                    // Include dialog.xml file

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.menu_popuplist);
                    int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.55);
                    int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);
                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog.getWindow().setLayout(width1, height1);

                    dialog.setCancelable(false);
                    // Set dialog title
                    dialog.setTitle("");
                    dialog.show();
                    shimmerRecyclerView = dialog.findViewById(R.id.recyclerview);
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(IRdMainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("MENU");
                    irdAdapter = new IRDAdapter(irddataenulist, IRdMainActivity.this);
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
                    ImageView close = dialog.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            if (Network.isNetworkAvailable(IRdMainActivity.this)) {
                                //  new IRDDatamenu().execute();

                            } else if (Network.isNetworkAvailable2(IRdMainActivity.this)) {
                                // new IRDDatamenu().execute();
                                dialog.dismiss();

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
            irddataenulist.clear();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result.replaceAll("\t", "").trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject data = jsonArray.getJSONObject(i);
                        IRD_Data_data ird_data = new IRD_Data_data();
                        ird_data.setId(data.getString("id"));
                        ird_data.setName(data.getString("name"));
                        ird_data.setHotel_id(data.getString("hotel_id"));
                        ird_data.setDescription(data.getString("description"));
                        ird_data.setEnabled(data.getString("enabled"));
                        ird_data.setCreated_at(data.getString("created_at"));
                        ird_data.setUpdated_at(data.getString("updated_at"));
                        JSONArray jsonArray1 = data.getJSONArray("categories");
                        ird_data.setCategories(jsonArray1);


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
                        IRD_Data_data ird_data = new IRD_Data_data();
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
                        irddataenulist.add(ird_data);
                        irdAdapter =new IRdMainActivity.IRDAdapter(irddataenulist,IRdMainActivity.this);
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
                    JSONArray data=jsonObject.getJSONArray("data");

                    for(int j=0;j<data.length();j++) {
                        JSONObject jsonObject2 = data.getJSONObject(j);
                        if(jsonObject2.getString("id").equals(id)){

                            for (int i = 0; i < jsonObject2.getJSONArray("categories").length(); i++) {
                                JSONObject jsonObject1 = jsonObject2.getJSONArray("categories").getJSONObject(i);
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
                        IRD_Category ird_category=new IRD_Category();
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

                    // irdcAtegoryAdapter =new IRDCAtegoryAdapter(irdcategorylist,IRdMainActivity.this,);
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemDisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.menu_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getdisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.disable_menu+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategoryEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.enable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategorydisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.disable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonitemenable("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.addon_itemenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonitemdisable("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.addon_itemdisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonenable("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.addon_categoryenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddondisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.addon_categorydisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubcategoryEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.enable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(IRdMainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubcategorydisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.disable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(IRdMainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
                        new IRdMainActivity.IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    public class TagsAdapter extends  RecyclerView.Adapter<IRdMainActivity.TagsAdapter.ViewHolder>{
        String[] order_items;
        Context context;
        String enabled;

        public TagsAdapter(String[] order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public IRdMainActivity.TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout, parent, false);
            return new IRdMainActivity.TagsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final IRdMainActivity.TagsAdapter.ViewHolder holder, int position) {


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

    public class CartegoryAdapter extends  RecyclerView.Adapter<IRdMainActivity.CartegoryAdapter.ViewHolder>{
        ArrayList<IRD_Category> order_items;
        Context context;

        public CartegoryAdapter(ArrayList<IRD_Category> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public IRdMainActivity.CartegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item, parent, false);
            return new IRdMainActivity.CartegoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IRdMainActivity.CartegoryAdapter.ViewHolder holder, final int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            //   holder.category1.setText(String.valueOf(holder.mitem.getSub_categories().length()));


        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView category1;



            IRD_Category mitem;

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


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isvisisble==1) {
                imgBell.setVisibility(View.VISIBLE);
                AnimateBell();
                word="IRD";

                Fragment fragment1 = new IRdFreagment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
            }
             else if(isvisisble==2) {
                imgBell.setVisibility(View.VISIBLE);
                AnimateBell();
                word="IRD";

                Fragment fragment1 = new AcceptedMainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
            }  else if(isvisisble==3) {
                imgBell.setVisibility(View.VISIBLE);
                AnimateBell();
                word="IRD";

                Fragment fragment1 = new DispatchedMainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
            }

            else {
                isvisisble=4;
                imgBell.setVisibility(View.VISIBLE);
                AnimateBell();
                if(Network.isNetworkAvailable(IRdMainActivity.this)){
                    GetHeader();
                }
                else if(Network.isNetworkAvailable2(IRdMainActivity.this)){
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

        (RetrofitClientInstance.getApiService().getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header login = response.body();
                    availabletrolley.setText(String.valueOf(login.getData().getNew_order()));
                    availabletray.setText(String.valueOf(login.getData().getAccepted_order()));
                    availableassciate.setText(String.valueOf(login.getData().getDispatched_order()));
                    totalorders.setText(String.valueOf(login.getData().getNew_order()+login.getData().getAccepted_order()+login.getData().getCleared_order()));

                }
                else if(response.code()==401){
                    Header login = response.body();
                    Toast.makeText(IRdMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(IRdMainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

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

}
