package com.viralops.touchlessfoodordering;

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
import android.text.Html;
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
import android.widget.RelativeLayout;
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
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Connect_Header;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.IRD_Addons;
import com.viralops.touchlessfoodordering.Model.IRD_AddonsItem;
import com.viralops.touchlessfoodordering.Model.IRD_Category;
import com.viralops.touchlessfoodordering.Model.IRD_Data;
import com.viralops.touchlessfoodordering.Model.IRD_Data_data;
import com.viralops.touchlessfoodordering.Model.IRD_Item;
import com.viralops.touchlessfoodordering.Model.IRD_Menu;
import com.viralops.touchlessfoodordering.Model.IRd_SubCategory;
import com.viralops.touchlessfoodordering.Model.Laundry_Category;
import com.viralops.touchlessfoodordering.Model.Laundry_Header;
import com.viralops.touchlessfoodordering.Model.Laundry_item;
import com.viralops.touchlessfoodordering.Model.Menu;
import com.viralops.touchlessfoodordering.Model.Minibar_WithoutSubcategory;
import com.viralops.touchlessfoodordering.Model.Revenue;
import com.viralops.touchlessfoodordering.Model.Spa_Categories;
import com.viralops.touchlessfoodordering.Model.Spa_Data;
import com.viralops.touchlessfoodordering.Model.Spa_withoutCategory;
import com.viralops.touchlessfoodordering.Support.Internetconnection;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;
import com.viralops.touchlessfoodordering.Tablet.combine.AcceptedMainFragmentcombine;
import com.viralops.touchlessfoodordering.Tablet.combine.Connect_Fragmentcombine;
import com.viralops.touchlessfoodordering.Tablet.combine.DispatchedMainFragmentcombine;
import com.viralops.touchlessfoodordering.Tablet.combine.Laundry_fragment_combine;
import com.viralops.touchlessfoodordering.Tablet.combine.MainFragmentcombine;
import com.viralops.touchlessfoodordering.Tablet.combine.Minibar_fragment_combine;
import com.viralops.touchlessfoodordering.Tablet.combine.Order_History_Screen;
import com.viralops.touchlessfoodordering.Tablet.combine.Spa_fragmentcombine;


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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static public TextView busytray;
    static public TextView busytrolley;
    static public TextView mtd;
    static public TextView revenue;
    static public TextView busyassciate;
    static public TextView availabletray;
    static public TextView availabletrolley;
    static public TextView availableassciate;
    static public TextView totaltext;
    static public TextView totalorders;
    ShimmerRecyclerView shimmerRecyclerViewcategory;

    static public TextView minibarnew;
    static public TextView newtotal;
    static public TextView totalminibar;
    static public TextView totalordersminivar;

    static public TextView standardsrvice;
    static public TextView standtotal;
    static public TextView express;
    static public TextView totalexpress;
    static public TextView totallaudry;
    static public TextView totalorderslaundry;

    static public TextView spanew;
    static public TextView newtotalspa;
    static public TextView inprogressspa;
    static public TextView inprogresstotalspa;
    static public TextView totalspa;
    static public TextView totalorderspa;


    static public TextView connectnew;
    static public TextView newtotalconnect;
    static public TextView inprogress;
    static public TextView inprogresstotal;
    static public TextView totalconnect;
    static public TextView totalorderconnect;
    IRDAdapter   irdAdapter;

    private TextView text;
    RecyclerView recyclerView;
     Dialog menudialog;
    private TextView text1;
    private CardView order;
    private CardView menu;
    private  TextView history;
    private  TextView laundry;
    private  TextView spa;
    private  TextView connect;
    private  TextView minibarorder;
    private  TextView dashboard;
    private ImageView logout;
    ArrayList<IRD_Data_data> irddataenulist=new ArrayList<>();

    private SessionManager sessionManager;
    private SessionManagerFCM sessionManagerFCM;
    ArrayList<IRD_Category> categorylist=new ArrayList<>();
    ArrayList<IRD_Category> irdcategorylist=new ArrayList<>();
    ArrayList<Menu.menu_data> menuslist=new ArrayList<>();
    ArrayList<IRD_Category> irdmenuslist=new ArrayList<>();
    ArrayList<IRD_Category> minibarmenulist=new ArrayList<>();
    ArrayList<Minibar_WithoutSubcategory> minibarwhithoutmenulist=new ArrayList<>();
    ArrayList<Laundry_Category> laundrymenulist=new ArrayList<>();
    ArrayList<Laundry_Category> laundrycategorylistlist=new ArrayList<>();
    ArrayList<Spa_Categories> spa_categoriesArrayList=new ArrayList<>();
    ArrayList<Spa_Categories> spamenulist=new ArrayList<>();
     Dialog dialog1;
    ShimmerRecyclerView shimmerRecyclerView;
    ShimmerRecyclerView shimmerRecyclerView1;
    static public int isvisisble=1;
    static  public ImageView imgBell;
    static  public ImageView belllaundry;
    static  public ImageView bellminibar;
    static  public ImageView bellconnect;
    static  public ImageView bellspa;
    static public LinearLayout neworderss;
    static public LinearLayout newordersconnect;
    static public LinearLayout newordersspa;
    static public LinearLayout newordersminibar;
    static public LinearLayout startndard;
    static public LinearLayout revenuelayout;
     public String word="";
     LinearLayout hearderforird;
     LinearLayout hearderforlaundry;
     LinearLayout hearderforminibar;
     LinearLayout hearderforconnect;
     LinearLayout hearderforspa;

     LinearLayout irdlayout;
     LinearLayout laundrylayout;
     LinearLayout minibarlayout;
     LinearLayout spalayout;
     LinearLayout ayslayout;

     TextView dashboardcount;
     TextView laundrycount;
     TextView minibarcount;
     TextView spacount;
     TextView ayscount;
SpaAdapter spaAdapter;
Typeface font;
Typeface font1;

   
    ArrayList<Spa_Data> spadataenulist=new ArrayList<>();


    IRDCAtegoryAdapter   irdcAtegoryAdapter;
    LaundryCAtegoryAdapter   laundryCartegoryAdapter;
    SpaCAtegoryAdapter   spaCartegoryAdapter;
    MinibarCAtegoryAdapter minibarCartegoryAdapter;
    MinibarIRD_ItemsWithoutactegoryAdapter minibarIRD_itemsWithoutactegoryAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.main_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        sessionManager=new SessionManager(MainActivity.this);
        sessionManagerFCM=new SessionManagerFCM(MainActivity.this);
        sessionManager.setIsINternet("false");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Crashlytics.setUserIdentifier(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        FirebaseCrashlytics.getInstance().setUserId(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(sessionManager.getPorchName());
        mFirebaseAnalytics.setUserProperty("Id",sessionManager.getPorchName()+" "+sessionManager.getNAME());

        dashboardcount=  findViewById(R.id.dashboardcount);
        laundrycount=  findViewById(R.id.laundrycount);
        minibarcount=  findViewById(R.id.minibarcount);
        spacount=  findViewById(R.id.spacount);
        ayscount=  findViewById(R.id.ayscount);

        irdlayout=  findViewById(R.id.irdlayout);
        laundrylayout=  findViewById(R.id.laundrylayout);
        minibarlayout=  findViewById(R.id.minibarlayout);
        spalayout=  findViewById(R.id.spalayout);
        ayslayout=  findViewById(R.id.ayslayout);
        revenuelayout=  findViewById(R.id.revenuelayout);
        revenue=  findViewById(R.id.revenue);
        mtd=  findViewById(R.id.mtd);

        imgBell=  findViewById(R.id.bell);
         imgBell.setImageResource(R.mipmap.calling);
         imgBell.setVisibility(View.INVISIBLE);

         bellminibar=  findViewById(R.id.bellminibar);
         bellminibar.setImageResource(R.mipmap.calling);
         bellminibar.setVisibility(View.INVISIBLE);

         bellconnect=  findViewById(R.id.bellconnect);
         bellconnect.setImageResource(R.mipmap.calling);
         bellconnect.setVisibility(View.INVISIBLE);

         belllaundry=  findViewById(R.id.belllaundry);
         belllaundry.setImageResource(R.mipmap.calling);
         belllaundry.setVisibility(View.INVISIBLE);

         bellspa=  findViewById(R.id.bellspa);
         bellspa.setImageResource(R.mipmap.calling);
         bellspa.setVisibility(View.INVISIBLE);

         neworderss=findViewById(R.id.neworderss);
        newordersconnect=findViewById(R.id.newordersconnect);
        newordersspa=findViewById(R.id.newordersspa);
        newordersminibar=findViewById(R.id.newordersminibar);
        startndard=findViewById(R.id.startndard);
         font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
         font1 = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Thin.ttf");

        minibarnew=findViewById(R.id.minibarnew);
        minibarnew.setTypeface(font);
        totalminibar=findViewById(R.id.totalminibar);
        totalminibar.setTypeface(font);
        standardsrvice=findViewById(R.id.standardsrvice);
        standardsrvice.setTypeface(font);
        express=findViewById(R.id.express);
        express.setTypeface(font);
        totallaudry=findViewById(R.id.totallaudry);
        totallaudry.setTypeface(font);

        busytray=findViewById(R.id.busytray);
        busytray.setTypeface(font);
        busytrolley=findViewById(R.id.busytrolley);
        busytrolley.setTypeface(font);
        busyassciate=findViewById(R.id.busyassociate);
        busyassciate.setTypeface(font);


        spanew=findViewById(R.id.spanew);
        spanew.setTypeface(font);
        newtotalspa=findViewById(R.id.newtotalspa);
        newtotalspa.setTypeface(font1);
        inprogressspa=findViewById(R.id.inprogressspa);
        inprogressspa.setTypeface(font);
        inprogresstotalspa=findViewById(R.id.inprogresstotalspa);
        inprogresstotalspa.setTypeface(font1);
        totalspa=findViewById(R.id.totalspa);
        totalspa.setTypeface(font);
        totalorderspa=findViewById(R.id.totalorderspa);
        totalorderspa.setTypeface(font1);


        connectnew=findViewById(R.id.connectnew);
        connectnew.setTypeface(font);
        newtotalconnect=findViewById(R.id.newtotalconnect);
        newtotalconnect.setTypeface(font1);
        inprogress=findViewById(R.id.inprogress);
        inprogress.setTypeface(font);
        inprogresstotal=findViewById(R.id.inprogresstotal);
        inprogresstotal.setTypeface(font1);
        totalconnect=findViewById(R.id.totalconnect);
        totalconnect.setTypeface(font);
        totalorderconnect=findViewById(R.id.totalorderconnect);
        totalorderconnect.setTypeface(font1);
        menu=findViewById(R.id.menu);

        newtotal=findViewById(R.id.newtotal);
        newtotal.setTypeface(font1);
        totalordersminivar=findViewById(R.id.totalordersminivar);
        totalordersminivar.setTypeface(font1);
        standtotal=findViewById(R.id.standtotal);
        standtotal.setTypeface(font1);
        totalexpress=findViewById(R.id.totalexpress);
        totalexpress.setTypeface(font);
  totalorderslaundry=findViewById(R.id.totalorderslaundry);
        totalorderslaundry.setTypeface(font);

        availabletray=findViewById(R.id.avalbletray);
        availabletray.setTypeface(font1);
        availabletrolley=findViewById(R.id.avalbletrollet);
        availabletrolley.setTypeface(font1);
        availableassciate=findViewById(R.id.avalbleassociate);
        availableassciate.setTypeface(font1);
        totaltext=findViewById(R.id.total);
        totaltext.setTypeface(font);
        totalorders=findViewById(R.id.totalorders);
        order=findViewById(R.id.order);
        hearderforird=findViewById(R.id.hearderforird);
        hearderforspa=findViewById(R.id.hearderforspa);
        hearderforconnect=findViewById(R.id.hearderforconnect);
        hearderforlaundry=findViewById(R.id.hearderforlaundry);
        hearderforminibar=findViewById(R.id.hearderforminibar);
        totalorders.setTypeface(font1);
        text=findViewById(R.id.text);
        text.setTypeface(font);
        text1=findViewById(R.id.text1);
        text.setText(sessionManager.getPorchName());
        history=findViewById(R.id.history);
        laundry=findViewById(R.id.laundry);
        laundrylayout.setOnClickListener(this);
        minibarorder=findViewById(R.id.minibar);
        minibarlayout.setOnClickListener(this);
        dashboard=findViewById(R.id.dashboard);
        irdlayout.setOnClickListener(this);
        spa=findViewById(R.id.spa);
        spalayout.setOnClickListener(this);
        connect=findViewById(R.id.connect);
        ayslayout.setOnClickListener(this);
        text1.setTypeface(font1);
        text1.setText("");
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
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
                        if(Network.isNetworkAvailable(MainActivity.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(MainActivity.this)){
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
                hearderforlaundry.setVisibility(View.GONE);
                hearderforminibar.setVisibility(View.GONE);
                hearderforconnect.setVisibility(View.GONE);
                hearderforspa.setVisibility(View.GONE);
                hearderforird.setVisibility(View.GONE);
                menu.setVisibility(View.GONE);

                laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
                laundry.setTextColor(getResources().getColor(R.color.gray));
                spalayout.setBackgroundColor(getResources().getColor(R.color.white));
                spa.setTextColor(getResources().getColor(R.color.gray));
                minibarlayout.setBackgroundColor(getResources().getColor(R.color.white));
                minibarorder.setTextColor(getResources().getColor(R.color.gray));

                irdlayout.setBackgroundColor(getResources().getColor(R.color.white));
                dashboard.setTextColor(getResources().getColor(R.color.gray));

                ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
                connect.setTextColor(getResources().getColor(R.color.gray));
                isvisisble=6;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_view, Order_History_Screen.newInstance())
                            .commitNow();

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(word.equals("IRD")) {
                    if (Network.isNetworkAvailable(MainActivity.this)) {
                        new  IRDDatamenu().execute();



                    } else if (Network.isNetworkAvailable2(MainActivity.this)) {
                        new IRDDatamenu().execute();



                    }
                    else{
                        if (sessionManager.getIsINternet().equals("false")) {
                            Intent intent = new Intent(MainActivity.this, Internetconnection.class);
                            startActivity(intent);

                            sessionManager.setIsINternet("true");
                            finish();

                        } else {

                        }
                    }


                }


               else if(word.equals("Laundry")) {
                    if (Network.isNetworkAvailable(MainActivity.this)) {

                        new LaundryIRDenu().execute();



                    } else if (Network.isNetworkAvailable2(MainActivity.this)) {

                        new LaundryIRDenu().execute();



                    }
                    else{
                        if (sessionManager.getIsINternet().equals("false")) {
                            Intent intent = new Intent(MainActivity.this, Internetconnection.class);
                            startActivity(intent);

                            sessionManager.setIsINternet("true");
                            finish();

                        } else {

                        }
                    }


                }
               else if(word.equals("SPA")) {
                    if (Network.isNetworkAvailable(MainActivity.this)) {

                        new SpaDatamenu().execute();


                    } else if (Network.isNetworkAvailable2(MainActivity.this)) {

                        new SpaDatamenu().execute();


                    }
                    else{
                        if (sessionManager.getIsINternet().equals("false")) {
                            Intent intent = new Intent(MainActivity.this, Internetconnection.class);
                            startActivity(intent);

                            sessionManager.setIsINternet("true");
                            finish();

                        } else {

                        }
                    }


                }
                else if(word.equals("MIN")) {

                    final Dialog dialog = new Dialog(MainActivity.this);
                    // Include dialog.xml file

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.minibarmenulist);
                    int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.55);
                    int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.95);
                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog.getWindow().setLayout(width1, height1);

                    dialog.setCancelable(false);
                    // Set dialog title
                    dialog.setTitle("");
                    dialog.show();
                    shimmerRecyclerView = dialog.findViewById(R.id.recyclerview);
                    shimmerRecyclerView1 = dialog.findViewById(R.id.recyclerview1);
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                    shimmerRecyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("REFRESHMENT MENU");
                    final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                    final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                    if(categorylist.size()!=0){
                        menubutton.setVisibility(View.VISIBLE);
                    }
                    else{
                        menubutton.setVisibility(View.GONE);

                    }
                    minibarCartegoryAdapter = new MinibarCAtegoryAdapter(minibarmenulist, MainActivity.this);
                    minibarIRD_itemsWithoutactegoryAdapter = new MinibarIRD_ItemsWithoutactegoryAdapter(minibarwhithoutmenulist, MainActivity.this);
                    shimmerRecyclerView.setAdapter(minibarCartegoryAdapter);
                    shimmerRecyclerView1.setAdapter(minibarIRD_itemsWithoutactegoryAdapter);

                   // registerForContextMenu(menubutton);
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
                            menudialog = new Dialog(MainActivity.this);
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
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                            MinibarCartegoryAdapter menupopupadapeter = new MinibarCartegoryAdapter(categorylist, MainActivity.this);
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


            }
        });
        if (savedInstanceState == null) {
            hearderforlaundry.setVisibility(View.GONE);
            hearderforminibar.setVisibility(View.GONE);
            hearderforconnect.setVisibility(View.GONE);
            hearderforspa.setVisibility(View.GONE);
            hearderforird.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);


            laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
            laundry.setTextColor(getResources().getColor(R.color.gray));
            spalayout.setBackgroundColor(getResources().getColor(R.color.white));
            spa.setTextColor(getResources().getColor(R.color.gray));
            minibarlayout.setBackgroundColor(getResources().getColor(R.color.white));
            minibarorder.setTextColor(getResources().getColor(R.color.gray));

            irdlayout.setBackgroundColor(getResources().getColor(R.color.blue));
            dashboard.setTextColor(getResources().getColor(R.color.white));

            ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
            connect.setTextColor(getResources().getColor(R.color.gray));
            revenuelayout.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, MainFragmentcombine.newInstance())
                    .commitNow();
              word="IRD";

        }

        if (Network.isNetworkAvailable(MainActivity.this)) {
            new MinibarIRDenu().execute();


        } else if (Network.isNetworkAvailable2(MainActivity.this)) {
            new MinibarIRDenu().execute();



        }
        else{
            if (sessionManager.getIsINternet().equals("false")) {
                Intent intent = new Intent(MainActivity.this, Internetconnection.class);
                startActivity(intent);

                sessionManager.setIsINternet("true");
                finish();

            } else {

            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.laundrylayout){

            laundrycount.setText("");

            word="Laundry";
            hearderforlaundry.setVisibility(View.VISIBLE);
            hearderforminibar.setVisibility(View.GONE);
            hearderforconnect.setVisibility(View.GONE);
            hearderforspa.setVisibility(View.GONE);
            hearderforird.setVisibility(View.GONE);
            menu.setVisibility(View.VISIBLE);

            isvisisble=2;


            laundrylayout.setBackgroundColor(getResources().getColor(R.color.blue));
            laundry.setTextColor(getResources().getColor(R.color.white));
            spalayout.setBackgroundColor(getResources().getColor(R.color.white));
            spa.setTextColor(getResources().getColor(R.color.gray));
            minibarlayout.setBackgroundColor(getResources().getColor(R.color.white));
            minibarorder.setTextColor(getResources().getColor(R.color.gray));

            irdlayout.setBackgroundColor(getResources().getColor(R.color.white));
            dashboard.setTextColor(getResources().getColor(R.color.gray));

            ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
            connect.setTextColor(getResources().getColor(R.color.gray));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, Laundry_fragment_combine.newInstance())
                    .commitNow();
        }
        else if(v.getId()== R.id.minibarlayout){
            isvisisble=3;

            minibarcount.setText("");

            word="MIN";
            hearderforlaundry.setVisibility(View.GONE);
            hearderforminibar.setVisibility(View.VISIBLE);
            hearderforconnect.setVisibility(View.GONE);
            hearderforspa.setVisibility(View.GONE);
            hearderforird.setVisibility(View.GONE);
            menu.setVisibility(View.VISIBLE);



            laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
            laundry.setTextColor(getResources().getColor(R.color.gray));
            spalayout.setBackgroundColor(getResources().getColor(R.color.white));
            spa.setTextColor(getResources().getColor(R.color.gray));
            minibarlayout.setBackgroundColor(getResources().getColor(R.color.blue));
            minibarorder.setTextColor(getResources().getColor(R.color.white));

            irdlayout.setBackgroundColor(getResources().getColor(R.color.white));
            dashboard.setTextColor(getResources().getColor(R.color.gray));

            ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
            connect.setTextColor(getResources().getColor(R.color.gray));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, Minibar_fragment_combine.newInstance())
                    .commitNow();
        }

        else if(v.getId()== R.id.irdlayout){
            isvisisble=1;


            dashboardcount.setText("");

            word="IRD";
            hearderforlaundry.setVisibility(View.GONE);
            hearderforminibar.setVisibility(View.GONE);
            hearderforconnect.setVisibility(View.GONE);
            hearderforspa.setVisibility(View.GONE);
            hearderforird.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);



            laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
            laundry.setTextColor(getResources().getColor(R.color.gray));
            spalayout.setBackgroundColor(getResources().getColor(R.color.white));
            spa.setTextColor(getResources().getColor(R.color.gray));
            minibarlayout.setBackgroundColor(getResources().getColor(R.color.white));
            minibarorder.setTextColor(getResources().getColor(R.color.gray));

            irdlayout.setBackgroundColor(getResources().getColor(R.color.blue));
            dashboard.setTextColor(getResources().getColor(R.color.white));

            ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
            connect.setTextColor(getResources().getColor(R.color.gray));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, MainFragmentcombine.newInstance())
                    .commitNow();
        }
        else if(v.getId()== R.id.spalayout){

            spacount.setText("");

            isvisisble=4;
            word="SPA";
            hearderforlaundry.setVisibility(View.GONE);
            hearderforminibar.setVisibility(View.GONE);
            hearderforconnect.setVisibility(View.GONE);
            hearderforspa.setVisibility(View.VISIBLE);
            hearderforird.setVisibility(View.GONE);
            menu.setVisibility(View.VISIBLE);



            laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
            laundry.setTextColor(getResources().getColor(R.color.gray));
            spalayout.setBackgroundColor(getResources().getColor(R.color.blue));
            spa.setTextColor(getResources().getColor(R.color.white));
            minibarlayout.setBackgroundColor(getResources().getColor(R.color.white));
            minibarorder.setTextColor(getResources().getColor(R.color.gray));

            irdlayout.setBackgroundColor(getResources().getColor(R.color.white));
            dashboard.setTextColor(getResources().getColor(R.color.gray));

            ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
            connect.setTextColor(getResources().getColor(R.color.gray));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, Spa_fragmentcombine.newInstance())
                    .commitNow();
        }
        else if(v.getId()== R.id.ayslayout){
            isvisisble=5;
            word="connect";
            hearderforlaundry.setVisibility(View.GONE);
            hearderforminibar.setVisibility(View.GONE);
            hearderforconnect.setVisibility(View.VISIBLE);
            hearderforspa.setVisibility(View.GONE);
            hearderforird.setVisibility(View.GONE);
            menu.setVisibility(View.GONE);


            ayscount.setText("");

            laundrylayout.setBackgroundColor(getResources().getColor(R.color.white));
            laundry.setTextColor(getResources().getColor(R.color.gray));
            spalayout.setBackgroundColor(getResources().getColor(R.color.white));
            spa.setTextColor(getResources().getColor(R.color.gray));
            minibarlayout.setBackgroundColor(getResources().getColor(R.color.white));
            minibarorder.setTextColor(getResources().getColor(R.color.gray));

            irdlayout.setBackgroundColor(getResources().getColor(R.color.white));
            dashboard.setTextColor(getResources().getColor(R.color.gray));

            ayslayout.setBackgroundColor(getResources().getColor(R.color.blue));
            connect.setTextColor(getResources().getColor(R.color.white));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, Connect_Fragmentcombine.newInstance())
                    .commitNow();
        }

    }




    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
            final Dialog dialog = new Dialog(MainActivity.this);
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
                    MainActivity.this.finish();
                    dialog.dismiss();
                }
            });

        }

    public void AnimateBell() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanimation);

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
                                final Dialog dialog = new Dialog(MainActivity.this);
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
                                shimmerRecyclerViewcategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                                TextView title = dialog.findViewById(R.id.hotel);
                                title.setTypeface(font);
                                title.setText(order_items.get(getAdapterPosition()).getName());
                                final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                                final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                                irdcAtegoryAdapter = new IRDCAtegoryAdapter(irdmenuslist, MainActivity.this, order_items.get(getAdapterPosition()).getId());
                                shimmerRecyclerViewcategory.setAdapter(irdcAtegoryAdapter);
                                registerForContextMenu(menubutton);
                                ImageView close = dialog.findViewById(R.id.close);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        if (Network.isNetworkAvailable(MainActivity.this)) {
                                            // new IRDenu().execute();
                                            // dialog.dismiss();

                                        } else if (Network.isNetworkAvailable2(MainActivity.this)) {
                                            //  new IRDenu().execute();

                                        } else {

                                        }
                                    }
                                });

                                menubutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        menudialog = new Dialog(MainActivity.this);
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
                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

                                        CartegoryAdapter   menupopupadapeter = new CartegoryAdapter(irdmenuslist, MainActivity.this);
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
        IRD_SubCATegoryAdapter order_itemAdapterdetail;
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


            order_itemAdapterdetail = new IRD_SubCATegoryAdapter(iRd_subCategories, context, holder.mitem.getName(), enabled,id);
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
    public class IRD_SubCATegoryAdapter extends  RecyclerView.Adapter<IRD_SubCATegoryAdapter.ViewHolder>{
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
        public IRD_SubCATegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemsub, parent, false);
            return new IRD_SubCATegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRd_SubCategory> filterdNames) {
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
    public class IRD_ItemsAdapter extends  RecyclerView.Adapter<IRD_ItemsAdapter.ViewHolder>{
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
        public IRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new IRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Item> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRD_ItemsAdapter.ViewHolder holder, int position) {
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
    public class IRD_SubAddonAdapter extends  RecyclerView.Adapter<IRD_SubAddonAdapter.ViewHolder>{
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
        public IRD_SubAddonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenusubaddon, parent, false);
            return new IRD_SubAddonAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Addons> filterdNames) {
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

    public class IRD_ItemsWithoutAdapter extends  RecyclerView.Adapter<IRD_ItemsWithoutAdapter.ViewHolder>{
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
        public IRD_ItemsWithoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.addon_item, parent, false);
            return new IRD_ItemsWithoutAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_AddonsItem> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final IRD_ItemsWithoutAdapter.ViewHolder holder, int position) {
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);


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


                    }
                    final Dialog dialog = new Dialog(MainActivity.this);
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
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("MENU");
                    irdAdapter = new IRDAdapter(irddataenulist, MainActivity.this);
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

                            if (Network.isNetworkAvailable(MainActivity.this)) {
                                //  new IRDDatamenu().execute();

                            } else if (Network.isNetworkAvailable2(MainActivity.this)) {
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


                    }
                    final Dialog dialog = new Dialog(MainActivity.this);
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
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("MENU");
                    irdAdapter = new IRDAdapter(irddataenulist, MainActivity.this);
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

                            if (Network.isNetworkAvailable(MainActivity.this)) {
                                //  new IRDDatamenu().execute();

                            } else if (Network.isNetworkAvailable2(MainActivity.this)) {
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
                        irdAdapter =new IRDAdapter(irddataenulist, MainActivity.this);
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

                    // irdcAtegoryAdapter =new IRDCAtegoryAdapter(irdcategorylist,MainActivity.this,);
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDDatamenu1().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new IRDenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        ArrayList<IRD_Category> order_items;
        Context context;

        public CartegoryAdapter(ArrayList<IRD_Category> order_items, Context context) {
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
   //-----------------------------Minibar-----------------------------------//

    private void GetMinibarMenu() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getIRDMenu("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<IRD_Menu>() {
            @Override
            public void onResponse(@NonNull Call<IRD_Menu> call, @NonNull Response<IRD_Menu> response) {

                if(response.code()==201||response.code()==200){
                    IRD_Menu  login = response.body();
                    irdmenuslist=new ArrayList<>();
                   // irdmenuslist=login.getData().getCategories();
                    irdcategorylist =new ArrayList<>();
                    for(int i=0;i<irdmenuslist.size();i++){
                        IRD_Category menucategory=new IRD_Category();
                        menucategory.setId(irdmenuslist.get(i).getId());
                        menucategory.setName(irdmenuslist.get(i).getName());
                        menucategory.setSub_categories(irdmenuslist.get(i).getSub_categories());
                        irdcategorylist.add(menucategory);

                    }

                }
                else if(response.code()==401){
                    IRD_Menu login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
            public void onFailure(@NonNull Call<IRD_Menu> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                progressDialog.dismiss();

            }
        });

    }
    //-----------------MinubarAdpater-----------------------------------//

    public class MinibarCAtegoryAdapter extends  RecyclerView.Adapter<MinibarCAtegoryAdapter.ViewHolder>{
        ArrayList<IRD_Category> order_items;
        Context context;
        String enabled;
        ArrayList<IRd_SubCategory> iRd_subCategories;
        ArrayList<IRD_Item> iRd_withCategories;

        public MinibarCAtegoryAdapter(ArrayList<IRD_Category> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public MinibarCAtegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenuparent, parent, false);
            return new MinibarCAtegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Category> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MinibarCAtegoryAdapter.ViewHolder holder, int position) {
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
                            MinibarGetMenucategoryenable(holder.mitem.getId());
                            enabled="1";
                        }  else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenucategoryenable(holder.mitem.getId());
                            enabled="1";

                        }
                        else{

                        }
                    }
                    else{
                        if (Network.isNetworkAvailable(context)) {
                            MinibarGetMenucategorydisable(holder.mitem.getId());
                            enabled="0";

                        }  else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenucategorydisable(holder.mitem.getId());
                            enabled="0";

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


            MinibarIRD_SubCATegoryAdapter order_itemAdapterdetail = new MinibarIRD_SubCATegoryAdapter(iRd_subCategories, context, holder.mitem.getName(), enabled);
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
    public class MinibarIRD_SubCATegoryAdapter extends  RecyclerView.Adapter<MinibarIRD_SubCATegoryAdapter.ViewHolder>{
        ArrayList<IRd_SubCategory> order_items;
        ArrayList<IRd_SubCategory> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        ArrayList<IRD_Item> ird_itemArrayList;

        public MinibarIRD_SubCATegoryAdapter(ArrayList<IRd_SubCategory> order_items, Context context,String category,String enabled) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public MinibarIRD_SubCATegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemsub, parent, false);
            return new MinibarIRD_SubCATegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRd_SubCategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MinibarIRD_SubCATegoryAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            if(holder.mitem.getName().equals("")){
                holder.parent.setVisibility(View.GONE);
            }
            else{
                holder.parent.setVisibility(View.VISIBLE);
                holder.title.setText(holder.mitem.getName()+" ( "+String.valueOf(holder.mitem.getItems().length()+" ) "));


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
                            MinibarGetMenuSUbcategoryenable(holder.mitem.getId());
                            enabled1="1";

                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuSUbcategoryenable(holder.mitem.getId());
                            enabled1="1";

                        }
                        else{

                        }
                    }
                    else{


                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuSUbcategorydisable(holder.mitem.getId());
                            enabled1="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuSUbcategorydisable(holder.mitem.getId());
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
                    iRd_subCategory.setMenu_sub_category_id(jsonObject.getString("menu_sub_category_id"));
                    ird_itemArrayList.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            MinibarIRD_ItemsAdapter order_itemAdapterdetail=new MinibarIRD_ItemsAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1);
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
            RelativeLayout parent;

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
    public class MinibarIRD_ItemsAdapter extends  RecyclerView.Adapter<MinibarIRD_ItemsAdapter.ViewHolder>{
        ArrayList<IRD_Item> order_items;
        ArrayList<IRD_Addons> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;

        public MinibarIRD_ItemsAdapter(ArrayList<IRD_Item> order_items, Context context,String category,String enabled,String enabled1) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
        }

        @NonNull
        @Override
        public MinibarIRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new MinibarIRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Item> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MinibarIRD_ItemsAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(category);
            holder.price.setText(getResources().getString(R.string.Rs));
            enabled2=holder.mitem.getEnabled();
            holder.price1.setText(holder.mitem.getPrice());
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
                MinibarTagsAdapter tagsAdapter = new MinibarTagsAdapter(namesArray, context);
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
                            MinibarGetMenuitemenab(holder.mitem.getId());
                            enabled2="1";
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuitemenab(holder.mitem.getId());
                            enabled2="1";

                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuitemdisable(holder.mitem.getId());
                            enabled2="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuitemdisable(holder.mitem.getId());
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
            MinibarIRD_SubAddonAdapter order_itemAdapterdetail=new MinibarIRD_SubAddonAdapter(continentList,context,holder.mitem.getName(),enabled,enabled1,enabled2);
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
    public class MinibarIRD_SubAddonAdapter extends  RecyclerView.Adapter<MinibarIRD_SubAddonAdapter.ViewHolder>{
        ArrayList<IRD_Addons> order_items;
        ArrayList<IRD_AddonsItem> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        ArrayList<IRD_AddonsItem> ird_itemArrayList;

        public MinibarIRD_SubAddonAdapter(ArrayList<IRD_Addons> order_items, Context context,String category,String enabled,String enabled1,String enabled2) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public MinibarIRD_SubAddonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenusubcategory, parent, false);
            return new MinibarIRD_SubAddonAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Addons> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MinibarIRD_SubAddonAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            if(holder.mitem.getName().equals("")){
                holder.parent.setVisibility(View.GONE);
            }
            else{
                holder.parent.setVisibility(View.VISIBLE);
                holder.title.setText(holder.mitem.getName()+" ( "+String.valueOf(holder.mitem.getAddons().length()+" ) "));


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
                            MinibarGetMenuaddoncategoryenable(holder.mitem.getId());
                            enabled3="1";

                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuaddoncategoryenable(holder.mitem.getId());
                            enabled3="1";

                        }
                        else{

                        }
                    }
                    else{


                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuaddoncategorydisable(holder.mitem.getId());
                            enabled3="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuaddoncategorydisable(holder.mitem.getId());
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

            MinibarIRD_ItemsWithoutAdapter order_itemAdapterdetail=new MinibarIRD_ItemsWithoutAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1,enabled2,enabled3);
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
            RelativeLayout parent;

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

    public class MinibarIRD_ItemsWithoutAdapter extends  RecyclerView.Adapter<MinibarIRD_ItemsWithoutAdapter.ViewHolder>{
        ArrayList<IRD_AddonsItem> order_items;
        ArrayList<Menu.Items> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        String enabled4;

        public MinibarIRD_ItemsWithoutAdapter(ArrayList<IRD_AddonsItem> order_items, Context context,String category,String enabled,String enabled1,String enabled2,String enabled3) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            this.enabled3=enabled3;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public MinibarIRD_ItemsWithoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.addon_item, parent, false);
            return new MinibarIRD_ItemsWithoutAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_AddonsItem> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MinibarIRD_ItemsWithoutAdapter.ViewHolder holder, int position) {
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
                            MinibarGetMenuaddenab(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuaddenab(holder.mitem.getId());
                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuadddisable(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuadddisable(holder.mitem.getId());

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


    public class MinibarTagsAdapter extends  RecyclerView.Adapter<MinibarTagsAdapter.ViewHolder>{
        String[] order_items;
        Context context;
        String enabled;

        public MinibarTagsAdapter(String[] order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public MinibarTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout, parent, false);
            return new MinibarTagsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MinibarTagsAdapter.ViewHolder holder, int position) {


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

    public class MinibarCartegoryAdapter extends  RecyclerView.Adapter<MinibarCartegoryAdapter.ViewHolder>{
        ArrayList<IRD_Category> order_items;
        Context context;

        public MinibarCartegoryAdapter(ArrayList<IRD_Category> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public MinibarCartegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item, parent, false);
            return new MinibarCartegoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MinibarCartegoryAdapter.ViewHolder holder, final int position) {
            holder.mitem=order_items.get(position);
            holder.title.setText(holder.mitem.getName());
            holder.category1.setText(String.valueOf(holder.mitem.getSub_categories().length()));


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
                        shimmerRecyclerView.smoothScrollToPosition(getAdapterPosition());
                        menudialog.dismiss();

                    }
                });

            }
        }



    }
    public class MinibarIRD_ItemsWithoutactegoryAdapter extends  RecyclerView.Adapter<MinibarIRD_ItemsWithoutactegoryAdapter.ViewHolder>{
        ArrayList<Minibar_WithoutSubcategory> order_items;
        ArrayList<IRD_Addons> continentList;
        Context context;
        String category;


        public MinibarIRD_ItemsWithoutactegoryAdapter(ArrayList<Minibar_WithoutSubcategory> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;

        }

        @NonNull
        @Override
        public MinibarIRD_ItemsWithoutactegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new MinibarIRD_ItemsWithoutactegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Minibar_WithoutSubcategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MinibarIRD_ItemsWithoutactegoryAdapter.ViewHolder holder, int position) {
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
                MinibarTagsAdapter tagsAdapter = new MinibarTagsAdapter(namesArray, context);
                holder.spicy.setAdapter(tagsAdapter);
                holder.spicy.setVisibility(View.VISIBLE);

                // byte[] decodedString = Base64.decode(holder.mitem.getImage(), Base64.DEFAULT);
            }
            else{
                holder.spicy.setVisibility(View.GONE);
            }
      if(holder.mitem.getEnabled().equals("1")){
          holder.toggleButton1.setChecked(true);
          holder.toggleButton1.setOnCheckedChangeListener(null);

      }
      else{
          holder.toggleButton1.setChecked(false);
          holder.toggleButton1.setOnCheckedChangeListener(null);

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
                            MinibarGetMenuinab(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuinab(holder.mitem.getId());

                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenudisable(holder.mitem.getId());

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenudisable(holder.mitem.getId());


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


            Minibar_WithoutSubcategory mitem;

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
                customise.setVisibility(View.GONE);
                customiselist.setVisibility(View.GONE);
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


    //-------------End id minibaradapter------------------------------//

    public class MinibarIRDenu extends AsyncTask<String, String, String> {


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
                        .url(BuildConfig.BASE_URL + BuildConfig.minibar_menu)
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
            minibarmenulist.clear();
            categorylist.clear();
            minibarwhithoutmenulist.clear();

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
                    JSONArray jsonArray1=data.getJSONArray("without_category_items");
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
                        minibarmenulist.add(ird_category);
                        categorylist.add(ird_category);
                    }
                    for(int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject1=jsonArray1.getJSONObject(i);
                        Minibar_WithoutSubcategory ird_category=new Minibar_WithoutSubcategory();
                        ird_category.setId(jsonObject1.getString("id"));
                        ird_category.setCreated_at(jsonObject1.getString("created_at"));
                        ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                        ird_category.setMenu_id(jsonObject1.getString("menu_id"));
                        ird_category.setDescription(jsonObject1.getString("description"));
                        ird_category.setEnabled(jsonObject1.getString("enabled"));
                        ird_category.setName(jsonObject1.getString("name"));
                        ird_category.setTags(jsonObject1.getString("tags"));
                        ird_category.setType(jsonObject1.getString("type"));
                        ird_category.setThumbnail(jsonObject1.getString("thumbnail"));
                        ird_category.setPrice(jsonObject1.getString("price"));
                        ird_category.setMax_per_order(jsonObject1.getString("max_per_order"));
                        ird_category.setMin_per_order(jsonObject1.getString("min_per_order"));
                        ird_category.setMenu_id(jsonObject1.getString("menu_id"));

                        minibarwhithoutmenulist.add(ird_category);
                    }


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    public class MinibarIRDenunew extends AsyncTask<String, String, String> {


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
                        .url(BuildConfig.BASE_URL + BuildConfig.minibar_menu)
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
            minibarmenulist.clear();
            categorylist.clear();
            minibarwhithoutmenulist.clear();

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
                    JSONArray jsonArray1=data.getJSONArray("without_category_items");
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
                        minibarmenulist.add(ird_category);
                        categorylist.add(ird_category);
                    }
                    for(int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject1=jsonArray1.getJSONObject(i);
                        Minibar_WithoutSubcategory ird_category=new Minibar_WithoutSubcategory();
                        ird_category.setId(jsonObject1.getString("id"));
                        ird_category.setCreated_at(jsonObject1.getString("created_at"));
                        ird_category.setUpdated_at(jsonObject1.getString("updated_at"));
                        ird_category.setMenu_id(jsonObject1.getString("menu_id"));
                        ird_category.setDescription(jsonObject1.getString("description"));
                        ird_category.setEnabled(jsonObject1.getString("enabled"));
                        ird_category.setName(jsonObject1.getString("name"));
                        ird_category.setTags(jsonObject1.getString("tags"));
                        ird_category.setType(jsonObject1.getString("type"));
                        ird_category.setThumbnail(jsonObject1.getString("thumbnail"));
                        ird_category.setPrice(jsonObject1.getString("price"));
                        ird_category.setMax_per_order(jsonObject1.getString("max_per_order"));
                        ird_category.setMin_per_order(jsonObject1.getString("min_per_order"));
                        ird_category.setMenu_id(jsonObject1.getString("menu_id"));


                        minibarwhithoutmenulist.add(ird_category);
                    }
                    minibarCartegoryAdapter =new MinibarCAtegoryAdapter(minibarmenulist, MainActivity.this);
                    shimmerRecyclerView.setAdapter(minibarCartegoryAdapter);
                    minibarIRD_itemsWithoutactegoryAdapter =new MinibarIRD_ItemsWithoutactegoryAdapter(minibarwhithoutmenulist, MainActivity.this);
                    shimmerRecyclerView1.setAdapter(minibarIRD_itemsWithoutactegoryAdapter);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    private void MinibarGetMenuitemenab(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemEnabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuitemdisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemDisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuinab(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getEnabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenudisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenucategoryenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategoryEnabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_enable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenucategorydisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategorydisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_disable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuaddenab(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonitemenableMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_itemenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuadddisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonitemdisableMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_itemdisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuaddoncategoryenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonenableMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_categoryenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuaddoncategorydisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddondisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_categorydisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuSUbcategoryenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubcategoryEnabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_enable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void MinibarGetMenuSUbcategorydisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubcategorydisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_disable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

    //------------End of Minibar--------------------------------------//



    //-------------Laundry------------------------------//

    public class LaundryIRDenu extends AsyncTask<String, String, String> {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);


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
                    final Dialog dialog = new Dialog(MainActivity.this);
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
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("Laundry Menu");
                    final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                    final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                    laundryCartegoryAdapter = new LaundryCAtegoryAdapter(laundrymenulist, MainActivity.this);
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
                            menudialog = new Dialog(MainActivity.this);
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
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                            LaundryCartegoryAdapter menupopupadapeter = new LaundryCartegoryAdapter(laundrycategorylistlist, MainActivity.this);
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new LaundryIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryMinibarGetMenuaddenab(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonitemenableMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_itemenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenuadddisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonitemdisableMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_itemdisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();


                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenuaddoncategoryenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddonenableMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_categoryenable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenuaddoncategorydisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubaddondisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_addon_categorydisable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenuSUbcategoryenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubcategoryEnabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_enable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void LaundryGetMenuSUbcategorydisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getSubcategorydisabledMinibar("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.minibar_disable_subcategory+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MinibarIRDenunew().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        ArrayList<IRD_Item> iRd_withCategories;

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
    public class LaundryIRD_SubCATegoryAdapter extends  RecyclerView.Adapter<LaundryIRD_SubCATegoryAdapter.ViewHolder>{
        ArrayList<IRd_SubCategory> order_items;
        ArrayList<IRd_SubCategory> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        ArrayList<IRD_Item> ird_itemArrayList;

        public LaundryIRD_SubCATegoryAdapter(ArrayList<IRd_SubCategory> order_items, Context context,String category,String enabled) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public LaundryIRD_SubCATegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemsub, parent, false);
            return new LaundryIRD_SubCATegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRd_SubCategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final LaundryIRD_SubCATegoryAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            if(holder.mitem.getName().equals("")){
                holder.parent.setVisibility(View.GONE);
            }
            else{
                holder.parent.setVisibility(View.VISIBLE);
                holder.title.setText(holder.mitem.getName()+" ( "+String.valueOf(holder.mitem.getItems().length()+" ) "));


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
                            MinibarGetMenuSUbcategoryenable(holder.mitem.getId());
                            enabled1="1";

                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuSUbcategoryenable(holder.mitem.getId());
                            enabled1="1";

                        }
                        else{

                        }
                    }
                    else{


                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuSUbcategorydisable(holder.mitem.getId());
                            enabled1="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuSUbcategorydisable(holder.mitem.getId());
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
                    iRd_subCategory.setMenu_sub_category_id(jsonObject.getString("menu_sub_category_id"));
                    ird_itemArrayList.add(iRd_subCategory);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            MinibarIRD_ItemsAdapter order_itemAdapterdetail=new MinibarIRD_ItemsAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1);
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
            RelativeLayout parent;

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
    public class LaundryIRD_ItemsAdapter extends  RecyclerView.Adapter<LaundryIRD_ItemsAdapter.ViewHolder>{
        ArrayList<Laundry_item> order_items;
        ArrayList<IRD_Addons> continentList;
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
    public class LaundryIRD_SubAddonAdapter extends  RecyclerView.Adapter<LaundryIRD_SubAddonAdapter.ViewHolder>{
        ArrayList<IRD_Addons> order_items;
        ArrayList<IRD_AddonsItem> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        ArrayList<IRD_AddonsItem> ird_itemArrayList;

        public LaundryIRD_SubAddonAdapter(ArrayList<IRD_Addons> order_items, Context context,String category,String enabled,String enabled1,String enabled2) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public LaundryIRD_SubAddonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenusubcategory, parent, false);
            return new LaundryIRD_SubAddonAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_Addons> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final LaundryIRD_SubAddonAdapter.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            if(holder.mitem.getName().equals("")){
                holder.parent.setVisibility(View.GONE);
            }
            else{
                holder.parent.setVisibility(View.VISIBLE);
                holder.title.setText(holder.mitem.getName()+" ( "+String.valueOf(holder.mitem.getAddons().length()+" ) "));


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
                            MinibarGetMenuaddoncategoryenable(holder.mitem.getId());
                            enabled3="1";

                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuaddoncategoryenable(holder.mitem.getId());
                            enabled3="1";

                        }
                        else{

                        }
                    }
                    else{


                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuaddoncategorydisable(holder.mitem.getId());
                            enabled3="0";

                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuaddoncategorydisable(holder.mitem.getId());
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

            MinibarIRD_ItemsWithoutAdapter order_itemAdapterdetail=new MinibarIRD_ItemsWithoutAdapter(ird_itemArrayList,context,holder.mitem.getName(),enabled,enabled1,enabled2,enabled3);
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
            RelativeLayout parent;

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

    public class LaundryIRD_ItemsWithoutAdapter extends  RecyclerView.Adapter<LaundryIRD_ItemsWithoutAdapter.ViewHolder>{
        ArrayList<IRD_AddonsItem> order_items;
        ArrayList<Menu.Items> continentList;
        Context context;
        String category;
        String enabled;
        String enabled1;
        String enabled2;
        String enabled3;
        String enabled4;

        public LaundryIRD_ItemsWithoutAdapter(ArrayList<IRD_AddonsItem> order_items, Context context,String category,String enabled,String enabled1,String enabled2,String enabled3) {
            this.order_items = order_items;
            this.context = context;
            this.category=category;
            this.enabled=enabled;
            this.enabled1=enabled1;
            this.enabled2=enabled2;
            this.enabled3=enabled3;
            continentList=new ArrayList<>();
        }

        @NonNull
        @Override
        public LaundryIRD_ItemsWithoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.addon_item, parent, false);
            return new LaundryIRD_ItemsWithoutAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<IRD_AddonsItem> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final LaundryIRD_ItemsWithoutAdapter.ViewHolder holder, int position) {
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
                            MinibarGetMenuaddenab(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {

                            MinibarGetMenuaddenab(holder.mitem.getId());
                        }
                        else{

                        }
                    }
                    else{

                        holder.gradeout.setVisibility(View.VISIBLE);
                        if (Network.isNetworkAvailable(context)){
                            MinibarGetMenuadddisable(holder.mitem.getId());
                        }
                        else if (Network.isNetworkAvailable2(context)) {
                            MinibarGetMenuadddisable(holder.mitem.getId());

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
            holder.category1.setText(String.valueOf(holder.mitem.getSub_categories().length()));


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

    // -------------Spa------------------------------//
    public class SpaDatamenu extends AsyncTask<String, String, String> {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);


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
                String credentials = okhttp3.Credentials.basic("admin", "LetsValet2You");

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
                    final Dialog dialog1 = new Dialog(MainActivity.this);
                    // Include dialog.xml file

                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.menu_popuplist);
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.55);
                    int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);
                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog1.getWindow().setLayout(width, height);

                    dialog1.setCancelable(false);
                    // Set dialog title
                    dialog1.setTitle("");
                    dialog1.show();
                    shimmerRecyclerView = dialog1.findViewById(R.id.recyclerview);
                    shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                    TextView title = dialog1.findViewById(R.id.hotel);
                    title.setTypeface(font);
                    title.setText("MENU");
                    spaAdapter = new MainActivity.SpaAdapter(spadataenulist, MainActivity.this);
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
                            if (Network.isNetworkAvailable(MainActivity.this)) {
                             //   new MainActivity.SpaDatamenu().execute();

//

                            } else if (Network.isNetworkAvailable2(MainActivity.this)) {
                              //  new MainActivity.SpaDatamenu().execute();


                            } else {
           /* if (sessionManager.getIsINternet().equals("false")) {
                Intent intent = new Intent(MainActivity.this, Internetconnection.class);
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
                String credentials = okhttp3.Credentials.basic("admin", "LetsValet2You");

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
                String credentials = okhttp3.Credentials.basic("admin", "LetsValet2You");

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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemEnabledspa("Bearer "+sessionManager.getACCESSTOKEN(),BuildConfig.Spa_item_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MainActivity.SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MainActivity.SPAenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getItemDisabledspa("Bearer "+sessionManager.getACCESSTOKEN(),BuildConfig.spa_disable_item+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MainActivity.SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MainActivity.SPAenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==404){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategoryEnabledspa("Bearer "+sessionManager.getACCESSTOKEN(),BuildConfig.spa_enable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new SPAenu().execute(menuid);

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcategorydisabledspa("Bearer "+sessionManager.getACCESSTOKEN(),BuildConfig.spa_disable_category+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new MainActivity.SPAenu().execute(menuid);

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new MainActivity.SPAenu().execute(menuid);

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

    public class SpaCAtegoryAdapter extends  RecyclerView.Adapter<MainActivity.SpaCAtegoryAdapter.ViewHolder>{
        ArrayList<Spa_Categories> order_items;
        Context context;
        String enabled;
        ArrayList<Spa_withoutCategory> iRd_subCategories;
        ArrayList<IRD_Item> iRd_withCategories;
        String id;

        public SpaCAtegoryAdapter(ArrayList<Spa_Categories> order_items, Context context,String id) {
            this.order_items = order_items;
            this.context = context;
            this.id=id;
        }

        @NonNull
        @Override
        public MainActivity.SpaCAtegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenuparent, parent, false);
            return new MainActivity.SpaCAtegoryAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Spa_Categories> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MainActivity.SpaCAtegoryAdapter.ViewHolder holder, int position) {
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

            MainActivity.SpaIRD_ItemsAdapter order_itemAdapterdetail=new MainActivity.SpaIRD_ItemsAdapter(iRd_subCategories,context,holder.mitem.getName(),enabled,id);
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
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

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
    public class SpaIRD_ItemsAdapter extends  RecyclerView.Adapter<MainActivity.SpaIRD_ItemsAdapter.ViewHolder>{
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
        public MainActivity.SpaIRD_ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spa_item, parent, false);
            return new MainActivity.SpaIRD_ItemsAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Spa_withoutCategory> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MainActivity.SpaIRD_ItemsAdapter.ViewHolder holder, int position) {
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
                MainActivity.LaundryTagsAdapter tagsAdapter = new MainActivity.LaundryTagsAdapter(namesArray, context);
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
                spicy.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
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



    public class SpaTagsAdapter extends  RecyclerView.Adapter<MainActivity.SpaTagsAdapter.ViewHolder>{
        String[] order_items;
        Context context;
        String enabled;

        public SpaTagsAdapter(String[] order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public MainActivity.SpaTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout, parent, false);
            return new MainActivity.SpaTagsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MainActivity.SpaTagsAdapter.ViewHolder holder, int position) {


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

    public class SpaCartegoryAdapter extends  RecyclerView.Adapter<MainActivity.SpaCartegoryAdapter.ViewHolder>{
        ArrayList<Spa_Categories> order_items;
        Context context;

        public SpaCartegoryAdapter(ArrayList<Spa_Categories> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public MainActivity.SpaCartegoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_item, parent, false);
            return new MainActivity.SpaCartegoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity.SpaCartegoryAdapter.ViewHolder holder, final int position) {
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
    public class SpaAdapter extends  RecyclerView.Adapter<MainActivity.SpaAdapter.ViewHolder>{
        ArrayList<Spa_Data> order_items;
        Context context;
        String enabled;

        public SpaAdapter(ArrayList<Spa_Data> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public MainActivity.SpaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.irdmenu, parent, false);
            return new MainActivity.SpaAdapter.ViewHolder(view);
        }
        public void filterList(ArrayList<Spa_Data> filterdNames) {
            this.order_items = filterdNames;
            notifyDataSetChanged();
        }
        @Override
        public void onBindViewHolder(@NonNull final MainActivity.SpaAdapter.ViewHolder holder, int position) {
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
                            GetSPAMenuenable(holder.mitem.getId());
                            enabled="1";
                            holder.gradeout.setVisibility(View.GONE);
                        }  else if (Network.isNetworkAvailable2(context)) {
                            GetSPAMenuenable(holder.mitem.getId());
                            enabled="1";
                            holder.gradeout.setVisibility(View.GONE);


                        }
                        else{

                        }
                    }
                    else{
                        if (Network.isNetworkAvailable(context)) {
                            GetSPAMenudisable(holder.mitem.getId());
                            enabled="0";
                            holder.gradeout.setVisibility(View.VISIBLE);


                        }  else if (Network.isNetworkAvailable2(context)) {
                            GetSPAMenudisable(holder.mitem.getId());
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
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

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
                                final Dialog dialog = new Dialog(MainActivity.this);
                                // Include dialog.xml file

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.menulist);
                                int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.55);
                                int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);
                                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog.getWindow().setLayout(width1, height1);

                                dialog.setCancelable(false);
                                // Set dialog title
                                dialog.setTitle("");
                                dialog.show();
                                shimmerRecyclerViewcategory = dialog.findViewById(R.id.recyclerview);
                                shimmerRecyclerViewcategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                                TextView title = dialog.findViewById(R.id.hotel);
                                title.setTypeface(font);
                                title.setText(order_items.get(getAdapterPosition()).getName());
                                final MaterialButton menubutton = dialog.findViewById(R.id.menubutton);
                                final MaterialButton backbutton = dialog.findViewById(R.id.closebutton);
                                spaCartegoryAdapter = new SpaCAtegoryAdapter(spamenulist, MainActivity.this, order_items.get(getAdapterPosition()).getId());
                                shimmerRecyclerViewcategory.setAdapter(spaCartegoryAdapter);
                                registerForContextMenu(menubutton);
                                ImageView close = dialog.findViewById(R.id.close);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        if (Network.isNetworkAvailable(MainActivity.this)) {
                                            // new IRDenu().execute();
                                            // dialog.dismiss();

                                        } else if (Network.isNetworkAvailable2(MainActivity.this)) {
                                            //  new IRDenu().execute();

                                        } else {

                                        }
                                    }
                                });

                                menubutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        menudialog = new Dialog(MainActivity.this);
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
                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

                                        MainActivity.SpaCartegoryAdapter menupopupadapeter = new MainActivity.SpaCartegoryAdapter(spamenulist, MainActivity.this);
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
    private void GetSPAMenuenable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getEnabledspa("Bearer "+sessionManager.getACCESSTOKEN(),BuildConfig.spa_menu_enable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new SpaDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new SpaDatamenu1().execute();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
    private void GetSPAMenudisable(String id) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getDisablespa("Bearer "+sessionManager.getACCESSTOKEN(),BuildConfig.spa_menu_disable+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(MainActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        new SpaDatamenu1().execute();

                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        new SpaDatamenu1().execute();

                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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

    //---------HeaderApis---------------------------------------//
    private void LaundryGetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Laundry_Header>() {
            @Override
            public void onResponse(@NonNull Call<Laundry_Header> call, @NonNull Response<Laundry_Header> response) {

                if(response.code()==202||response.code()==200){
                    Laundry_Header  login = response.body();
                    laundrycount.setText(" ( "+String.valueOf(login.getData().getStandard_order()+login.getData().getExpress_order())+" )");


                }
                else if(response.code()==401){
                    Laundry_Header login = response.body();
                  //  Toast.makeText(MainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                  //  sessionManager.logoutsession();
                }
                else if(response.code()==500){
                   // Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void MInibarGetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Minibar_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header  login = response.body();
                    minibarcount.setText(" ( "+String.valueOf(login.getData().getNew_order())+" )");


                }
                else if(response.code()==401){
                    Header login = response.body();
                    //Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                   // sessionManager.logoutsession();
                }
                else if(response.code()==500){
                   // Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void SpaGetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Spa_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header  login = response.body();
                    spacount.setText(" ( "+String.valueOf(login.getData().getNew_order())+" )");


                }
                else if(response.code()==401){
                    Header login = response.body();
                  //  Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                   // Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void IRDGetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header  login = response.body();
                    dashboardcount.setText(" ( "+String.valueOf(login.getData().getNew_order())+" )");


                }
                else if(response.code()==401){
                    Header login = response.body();
                   // Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                   // sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    //Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void ConnectGetHeader() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Connect_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Connect_Header>() {
            @Override
            public void onResponse(@NonNull Call<Connect_Header> call, @NonNull Response<Connect_Header> response) {

                if(response.code()==202||response.code()==200){
                    Connect_Header  login = response.body();
                    ayscount.setText(" ( "+String.valueOf(login.getData().getNew_order())+" )");


                }
                else if(response.code()==401){
                    Connect_Header login = response.body();
                   // Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                  //  Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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

    //------------------------------------------------------//
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           /* if(isvisisble==true) {
                imgBell.setVisibility(View.VISIBLE);
                AnimateBell();
                word="IRD";

                Fragment fragment1 = new MainFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
            }
            else {
                isvisisble=false;
                imgBell.setVisibility(View.VISIBLE);
                AnimateBell();
            }
*/



            if(isvisisble==1) {
                if(sessionManager.getDesign_Style().equals("ird")) {

                    imgBell.setVisibility(View.VISIBLE);
                    AnimateBell();
                    word = "IRD";

                    Fragment fragment1 = new MainFragmentcombine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                }
                if(sessionManager.getDesign_Style().equals("spa")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }


                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("laundry")) {
                  if(Network.isNetworkAvailable(MainActivity.this)){
                      LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                      LaundryGetHeader();
                    }
                  else{

                  }

                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }

            }
            if(isvisisble==2) {
                if(sessionManager.getDesign_Style().equals("laundry")) {

                    belllaundry.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanimation);

                    belllaundry.setAnimation(shake);
                    word = "Laundry";

                    Fragment fragment1 = new Laundry_fragment_combine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                }
                if(sessionManager.getDesign_Style().equals("spa")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        MInibarGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("ird")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        IRDGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        IRDGetHeader();
                    }
                    else{

                    }
                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }

            }
            if(isvisisble==3) {
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    bellminibar.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanimation);

                    bellminibar.setAnimation(shake);
                    word = "MIN";

                    Fragment fragment1 = new Minibar_fragment_combine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                }
                if(sessionManager.getDesign_Style().equals("spa")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("ird")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        IRDGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        IRDGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("laundry")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        LaundryGetHeader();
                    }
                    else{

                    }

                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("other")) {

                }

            }
            if(isvisisble==4) {
                if(sessionManager.getDesign_Style().equals("spa")) {

                    bellspa.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanimation);

                    bellspa.setAnimation(shake);
                    word = "SPA";

                    Fragment fragment1 = new Spa_fragmentcombine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                }
                if(sessionManager.getDesign_Style().equals("ird")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        IRDGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        IRDGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("laundry")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        LaundryGetHeader();
                    }
                    else{

                    }
                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }

            }
            if(isvisisble==5) {
                if(sessionManager.getDesign_Style().equals("connect")) {

                    bellconnect.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanimation);

                    bellconnect.setAnimation(shake);
                    word = "Connect";

                    Fragment fragment1 = new Connect_Fragmentcombine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                }
                if(sessionManager.getDesign_Style().equals("spa")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if (Network.isNetworkAvailable(MainActivity.this)) {
                        MInibarGetHeader();
                    }
                    else if (Network.isNetworkAvailable2(MainActivity.this)) {
                        MInibarGetHeader();
                    }
                    else {

                    }
                }
                if(sessionManager.getDesign_Style().equals("laundry")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        LaundryGetHeader();
                    }
                    else{

                    }

                }

                if(sessionManager.getDesign_Style().equals("ird")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        IRDGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        IRDGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    bellconnect.setVisibility(View.VISIBLE);
                    Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shakeanimation);

                    bellconnect.setAnimation(shake);
                    word = "Connect";

                    Fragment fragment1 = new Connect_Fragmentcombine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
                }

            }
            if(isvisisble==7) {
                if(sessionManager.getDesign_Style().equals("laundry")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        LaundryGetHeader();
                    }
                    else{

                    }       }
                if(sessionManager.getDesign_Style().equals("spa")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        MInibarGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("ird")) {

                    imgBell.setVisibility(View.VISIBLE);
                    AnimateBell();
                    word = "IRD";

                    Fragment fragment1 = new AcceptedMainFragmentcombine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();

                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }

            }
            if(isvisisble==8) {
                if(sessionManager.getDesign_Style().equals("laundry")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        LaundryGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("spa")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        MInibarGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("ird")) {

                    imgBell.setVisibility(View.VISIBLE);
                    AnimateBell();
                    word = "IRD";

                    Fragment fragment1 = new DispatchedMainFragmentcombine();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();

                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }

            }
            if(isvisisble==6) {
                if(sessionManager.getDesign_Style().equals("laundry")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        LaundryGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        LaundryGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("spa")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        SpaGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        SpaGetHeader();
                    }
                    else{

                    }

                }
                if(sessionManager.getDesign_Style().equals("minibar")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        MInibarGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        MInibarGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("ird")) {

                    if(Network.isNetworkAvailable(MainActivity.this)){
                        IRDGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        IRDGetHeader();
                    }
                    else{

                    }
                }

                if(sessionManager.getDesign_Style().equals("connect")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }
                if(sessionManager.getDesign_Style().equals("other")) {
                    if(Network.isNetworkAvailable(MainActivity.this)){
                        ConnectGetHeader();
                    } else if(Network.isNetworkAvailable2(MainActivity.this)){
                        ConnectGetHeader();
                    }
                    else{

                    }
                }

            }

            else {
              //  imgBell.setVisibility(View.VISIBLE);
                //AnimateBell();
            }




        }
    };

}
