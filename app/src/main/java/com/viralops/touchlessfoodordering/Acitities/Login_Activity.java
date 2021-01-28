package com.viralops.touchlessfoodordering.Acitities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Mobile.AYS.AYSMain_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Booking.Booking_Activity;
import com.viralops.touchlessfoodordering.Mobile.IRD.MainActivity_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Restaurant.RestaurantMain;
import com.viralops.touchlessfoodordering.Mobile.Spa.Spa_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Supervisor.Supervisor_mainactivity;
import com.viralops.touchlessfoodordering.Model.Login;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;
import com.viralops.touchlessfoodordering.Tablet.AYS.AYSMainActivity;
import com.viralops.touchlessfoodordering.Tablet.IRD.IRdMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Laundry.LaundryMainActivity;
import com.viralops.touchlessfoodordering.Tablet.Restaurant.Resturant_Tablet_MainActivity;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;


import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
private TextView text;
private TextView button;
private EditText username;
private EditText password;
private ImageView show_pass_btn;
SessionManager sessionManager;
boolean click=false;
SessionManagerFCM sessionManagerFCM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(isTablet(Login_Activity.this)){
            setContentView(R.layout.activity_login_);

        }
        else{
            setContentView(R.layout.activity_login);

        }
       /* if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_login_);
        } else {
            setContentView(R.layout.activity_login);
        }*/
         sessionManager=new SessionManager(Login_Activity.this);
         sessionManagerFCM=new SessionManagerFCM(Login_Activity.this);

        Typeface font = Typeface.createFromAsset(
                getAssets(),
                "font/Roboto-Regular.ttf");
       // text=findViewById(R.id.text);
        username=findViewById(R.id.username);
        username.setTypeface(font);
        password=findViewById(R.id.password);
        password.setTypeface(font);

        button=findViewById(R.id.button);
        button.setTypeface(font);
        show_pass_btn=findViewById(R.id.show_pass_btn);


        button.setOnClickListener(this);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        show_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    show_pass_btn.setImageResource(R.mipmap.hide1);

                    //Show Password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    show_pass_btn.setImageResource(R.mipmap.show1);

                    //Hide Password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            text.setLetterSpacing((float) 0.2);

        }*/
    }


    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.button){
            if(username.getText().toString().equals("")){
                Toast.makeText(Login_Activity.this,"Please enter a username.",Toast.LENGTH_SHORT).show();

                username.setError("Please enter a username");
            }
            else if(password.getText().toString().equals("")){
                Toast.makeText(Login_Activity.this,"Please enter a password.",Toast.LENGTH_SHORT).show();

                password.setError("Please enter a password");

            }
            else {

                if (Network.isNetworkAvailable(Login_Activity.this)) {
                    SignIn();
                } else if (Network.isNetworkAvailable2(Login_Activity.this)) {
                    SignIn();

                } else {

                }
            }
        }
    }

    private void SignIn() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(Login_Activity.this);

        progressDialog.setCancelable(false); // set cancelable to falsez
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog


        (RetrofitClientInstance.getApiService().SignIn(username.getText().toString().trim(),password.getText().toString().trim(),sessionManagerFCM.getToken(),"android")).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {

                if(response.code()==201||response.code()==200){
                    Login login = response.body();
                    sessionManager.setNAME(login.getUser_type());




                    sessionManager.setACCESSTOKEN(login.getAccess_token());
                   if(isTablet(Login_Activity.this)){
                       Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                       if(sessionManager.getNAME().equals("ird_manager")){

                           sessionManager.setPorchName(login.getHotel().getName());

                           Intent intent = new Intent(Login_Activity.this, IRdMainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(sessionManager.getNAME().equals("restaurant_manager")){
                           sessionManager.setPorchName(login.getHotel().getName());

                           Intent intent = new Intent(Login_Activity.this, Resturant_Tablet_MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else if(sessionManager.getNAME().equals("hotel_admin")){
                           sessionManager.setPorchName(login.getHotel().getName());

                           Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else if(sessionManager.getNAME().equals("laundry_manager")){
                            sessionManager.setPorchName(login.getHotel().getName());


                           Intent intent = new Intent(Login_Activity.this, LaundryMainActivity.class);
                            startActivity(intent);
                            finish();

                        } else if(sessionManager.getNAME().equals("spa_manager")){
                           sessionManager.setPorchName(login.getHotel().getName());

                           Intent intent = new Intent(Login_Activity.this, SpaMainActivitytablet.class);
                            startActivity(intent);
                            finish();

                        } else if(sessionManager.getNAME().equals("connect_manager")){
                           sessionManager.setPorchName(login.getHotel().getName());

                           Intent intent = new Intent(Login_Activity.this, AYSMainActivity.class);
                            startActivity(intent);
                            finish();

                        }

                        else if(sessionManager.getNAME().equals("mini_bar_manager")){
                           sessionManager.setPorchName(login.getHotel().getName());

                           //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                          //  startActivity(intent);
                        }


                   }
                    else{

                       if(sessionManager.getNAME().equals("restaurant_manager")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            Intent intent = new Intent(Login_Activity.this, RestaurantMain.class);
                            startActivity(intent);
                            finish();

                        }


                       else if(sessionManager.getNAME().equals("booking_service_manager")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            Intent intent = new Intent(Login_Activity.this, Booking_Activity.class);
                            startActivity(intent);
                            finish();

                        }  else if(sessionManager.getNAME().equals("global_supervisor")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName("none");

                            Intent intent = new Intent(Login_Activity.this, Supervisor_mainactivity.class);
                            startActivity(intent);
                            finish();

                        }else if(sessionManager.getNAME().equals("ird_manager")) {
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            Intent intent = new Intent(Login_Activity.this, MainActivity_Mobile.class);
                            startActivity(intent);
                            finish();

                        }
                        else if(sessionManager.getNAME().equals("hotel_admin")){
                            sessionManager.setPorchName("");
                           Toast.makeText(Login_Activity.this,"Not supported! Please login via laptop or desktop.",Toast.LENGTH_SHORT).show();

                            //      Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                        //    startActivity(intent);
                        } else if(sessionManager.getNAME().equals("laundry_manager")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            Intent intent = new Intent(Login_Activity.this, Laundry_Main_Mobile.class);
                            startActivity(intent);
                            finish();

                        } else if(sessionManager.getNAME().equals("spa_manager")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            Intent intent = new Intent(Login_Activity.this, Spa_Mobile.class);
                           startActivity(intent);
                            finish();

                        } else if(sessionManager.getNAME().equals("connect_manager")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            Intent intent = new Intent(Login_Activity.this, AYSMain_Mobile.class);
                            startActivity(intent);
                            finish();

                        }

                        else if(sessionManager.getNAME().equals("mini_bar_manager")){
                           Toast.makeText(Login_Activity.this,login.getMessage(),Toast.LENGTH_SHORT).show();

                           sessionManager.setPorchName(login.getHotel().getName());

                            //  Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                          //  startActivity(intent);
                        }

                   }


                    progressDialog.dismiss();


                }
                else if(response.code()==401){
                    Login login = response.body();
                   Toast.makeText(Login_Activity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else if(response.code()==500){
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                progressDialog.dismiss();

            }
        });

    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
