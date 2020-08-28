package com.viralops.touchlessfoodordering.Tablet.AYS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.Mobile.AYS.AYSMain_Mobile;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Connect_Header;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;


import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AYSMainActivity extends AppCompatActivity implements View.OnClickListener {



    static public TextView connectnew;
    static public TextView newtotalconnect;
    static public TextView inprogress;
    static public TextView inprogresstotal;
    static public TextView totalconnect;
    static public TextView totalorderconnect;

    private TextView text;
    RecyclerView recyclerView;
     Dialog menudialog;
    private TextView text1;
    private CardView order;
    private CardView menu;
    private  TextView history;

    private  TextView connect;

    private ImageView logout;
    private SessionManager sessionManager;
    private SessionManagerFCM sessionManagerFCM;


    static public boolean isvisisble=true;

    static  public ImageView bellconnect;

    static public LinearLayout newordersconnect;

     public String word="";

     LinearLayout hearderforconnect;

     LinearLayout ayslayout;
     LinearLayout layout;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));


        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.ays_activity);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        sessionManager=new SessionManager(AYSMainActivity.this);
        sessionManagerFCM=new SessionManagerFCM(AYSMainActivity.this);
        sessionManager.setIsINternet("false");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Crashlytics.setUserIdentifier(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        FirebaseCrashlytics.getInstance().setUserId(sessionManager.getPorchName()+" "+sessionManager.getNAME());
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(sessionManager.getPorchName());
        mFirebaseAnalytics.setUserProperty("Id",sessionManager.getPorchName()+" "+sessionManager.getNAME());


        ayslayout=  findViewById(R.id.ayslayout);



         bellconnect=  findViewById(R.id.bellconnect);
         bellconnect.setImageResource(R.mipmap.calling);
         bellconnect.setVisibility(View.INVISIBLE);


        newordersconnect=findViewById(R.id.newordersconnect);

        final Typeface font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
        Typeface font1 = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Thin.ttf");




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

        order=findViewById(R.id.order);

        hearderforconnect=findViewById(R.id.hearderforconnect);

        text=findViewById(R.id.text);
        text.setTypeface(font);
        text1=findViewById(R.id.text1);
        text.setText(sessionManager.getPorchName());
        history=findViewById(R.id.history);
        layout=findViewById(R.id.layout);

        connect=findViewById(R.id.connect);
        ayslayout.setOnClickListener(this);
        text1.setTypeface(font1);
        text1.setText("");
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AYSMainActivity.this);
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
                        if(Network.isNetworkAvailable(AYSMainActivity.this)){
                            Logout();
                            dialog.dismiss();
                        } if(Network.isNetworkAvailable2(AYSMainActivity.this)){
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




                ayslayout.setBackgroundColor(getResources().getColor(R.color.white));
                connect.setTextColor(getResources().getColor(R.color.gray));
                layout.setBackgroundColor(getResources().getColor(R.color.blue));
                history.setTextColor(getResources().getColor(R.color.white));
                isvisisble=false;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_view, ConnectHistoryOrderHistory.newInstance())
                            .commitNow();

            }
        });

        if (savedInstanceState == null) {





            ayslayout.setBackgroundColor(getResources().getColor(R.color.blue));
            connect.setTextColor(getResources().getColor(R.color.white));
            layout.setBackgroundColor(getResources().getColor(R.color.white));
            history.setTextColor(getResources().getColor(R.color.gray));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, ConnectSingle_Fragment.newInstance())
                    .commitNow();
            word="connect";


        }


         }

        @Override
        public void onClick(View v) {

            if(v.getId()== R.id.ayslayout){
                isvisisble=true;
            word="connect";
            bellconnect.setVisibility(View.GONE);
            ayslayout.setBackgroundColor(getResources().getColor(R.color.blue));
            connect.setTextColor(getResources().getColor(R.color.white));
            layout.setBackgroundColor(getResources().getColor(R.color.white));
            history.setTextColor(getResources().getColor(R.color.gray));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, ConnectSingle_Fragment.newInstance())
                    .commitNow();
        }

    }






    private void Logout() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(AYSMainActivity.this);
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
                    Toast.makeText(AYSMainActivity.this,login.getMessage(),Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                    finish();


                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(AYSMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
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
            final Dialog dialog = new Dialog(AYSMainActivity.this);
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
                    AYSMainActivity.this.finish();
                    dialog.dismiss();
                }
            });

        }

    public void AnimateBell() {
        Animation shake = AnimationUtils.loadAnimation(AYSMainActivity.this, R.anim.shakeanimation);

         bellconnect.setAnimation(shake);

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



    //------------------------------------------------------//
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           if(isvisisble==true) {
                bellconnect.setVisibility(View.VISIBLE);
                AnimateBell();
                word="IRD";

                Fragment fragment1 = new ConnectSingle_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_view, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
            }
            else {
                isvisisble=false;
                bellconnect.setVisibility(View.VISIBLE);
                AnimateBell();
                if(Network.isNetworkAvailable(AYSMainActivity.this)){
                    GetHeader();
                }
                else if(Network.isNetworkAvailable2(AYSMainActivity.this)){
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

        (RetrofitClientInstance.getApiService().Connect_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Connect_Header>() {
            @Override
            public void onResponse(@NonNull Call<Connect_Header> call, @NonNull Response<Connect_Header> response) {

                if(response.code()==202||response.code()==200){
                    Connect_Header  login = response.body();
                    newtotalconnect.setText(String.valueOf(login.getData().getNew_order()));
                    totalorderconnect.setText(String.valueOf(login.getData().getToday_order()));
                    inprogresstotal.setText(String.valueOf(login.getData().getAccepted_order()));


                }
                else if(response.code()==401){
                    Connect_Header login = response.body();
                    Toast.makeText(AYSMainActivity.this, "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(AYSMainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

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
