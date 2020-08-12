package com.viralops.touchlessfoodordering.Mobile.Laundry;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Laundry_Header;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.database.AlarmReceiver;
import com.viralops.touchlessfoodordering.database.DbHelper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Laundry_Mobile_Dashboard extends Fragment {

ShimmerRecyclerView recyclerView;
SessionManager sessionManager;
TextView norecord;
HomeAdapter homeAdapter;
     public TextView standardsrvice;
     public TextView standtotal;
     public TextView express;
     public TextView totalexpress;
     public TextView totallaudry;
     public TextView totalorderslaundry;
    private Dialog dialognew;

    AutoCompleteTextView searchView;
    SQLiteDatabase db;
    DbHelper mDbHelper;
    ArrayList<Laundry_Dashboard1.Data> queuelist=new ArrayList<>();
    ArrayList<Laundry_Dashboard1.Data> queuelistnew=new ArrayList<>();
    ArrayList<Laundry_Dashboard1.Data> localarray=new ArrayList<>();
    public static Laundry_Mobile_Dashboard newInstance() {
        return new Laundry_Mobile_Dashboard();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.assciate_dashboard, container, false);
        mDbHelper = new DbHelper(getActivity());



        final Typeface font = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Regular.ttf");
        Typeface font1 = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Thin.ttf");
        searchView =  view.findViewById(R.id.searchView);
     /*   mDbHelper = new DbHelper(getActivity());
        db = mDbHelper.getWritableDatabase();*/
          localarray.clear();
        db= mDbHelper.getWritableDatabase();
        localarray=mDbHelper.getAllCotacts();
        System.out.println("this is a local array"+localarray.size());


        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        sessionManager=new SessionManager(getActivity());
        norecord=view.findViewById(R.id.norecord);
        standardsrvice=view.findViewById(R.id.standardsrvice);
        standardsrvice.setTypeface(font);
        express=view.findViewById(R.id.express);
        express.setTypeface(font);
        totallaudry=view.findViewById(R.id.totallaudry);
        totallaudry.setTypeface(font);
        standtotal=view.findViewById(R.id.standtotal);
        standtotal.setTypeface(font);
        totalexpress=view.findViewById(R.id.totalexpress);
        totalexpress.setTypeface(font);
        totalorderslaundry=view.findViewById(R.id.totalorderslaundry);
        totalorderslaundry.setTypeface(font);
        homeAdapter=new HomeAdapter(getActivity(),queuelistnew);

        if (Network.isNetworkAvailable2(getActivity())) {

            GetMenu();
        }
        else if(Network.isNetworkAvailable(getActivity())){
            GetMenu();
        }
        else{

        }
       /* singleCheckAdapter = new SingleCheckAdapter(getActivity(),fordceclose);
        singleCheckAdapter.setOnItemClickListener(this);
*/
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


  /*      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(Laundry_Main_Mobile.homeicon.getVisibility()==View.VISIBLE) {
                    GridLayoutManager layoutManager = GridLayoutManager.class.cast(recyclerView.getLayoutManager());
                    int lastItem = homeAdapter.getItemCount() - 1;
                    tryAnimation(layoutManager.findViewByPosition(lastItem));
                }
                else{

                }
            }
        });*/
        return  view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void GetMenu() {
        // display a progress dialog
        recyclerView.showShimmer();
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
                                //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
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

                        if(queuelistnew.size()!=0) {
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                            norecord.setVisibility(View.GONE);
                        }
                        else{
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                            norecord.setVisibility(View.VISIBLE);
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
                        homeAdapter=new HomeAdapter(getActivity(),queuelistnew);
                        recyclerView.setAdapter(homeAdapter);
                        norecord.setVisibility(View.VISIBLE);
                        Laundry_Main_Mobile.newcountevent.setText("( 0 )");
                        Laundry_Main_Mobile.newcount.setText("( 0 )");
                    }



                    if(Network.isNetworkAvailable(getActivity())){
                        GetHeader();
                    }else if(Network.isNetworkAvailable2(getActivity())){
                        GetHeader();
                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    Laundry_Dashboard1 login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }

                recyclerView.hideShimmer();



            }

            @Override
            public void onFailure(@NonNull Call<Laundry_Dashboard1> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                recyclerView.hideShimmer();

            }
        });

    }
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
                    totalorderslaundry.setText(String.valueOf(login.getData().getToday_order()));
                  // Laundry_Main_Mobile.newcountevent.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");


                }
                else if(response.code()==401){
                    Laundry_Header login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void setAccept(String id) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_OrderAccepted(BuildConfig.laundry_order_accept+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action login = response.body();
                    Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                    if(Laundry_Main_Mobile.homeicon.getVisibility()==View.VISIBLE){
                        if(Laundry_Main_Mobile.shake!=null){
                            Laundry_Main_Mobile.shake.cancel();
                            Laundry_Main_Mobile.homeicon.setVisibility(View.GONE);
                        }
                    }
                    if(Network.isNetworkAvailable(getActivity())){
                        GetMenu();
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenu();
                    }
                    else{

                    }

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Action> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
    private void setDispatch(String id, final int position) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_OrderDispatched(BuildConfig.laundry_order_dispatch+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200||response.code()==201){
                    Action login = response.body();
                    //  Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Action> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
    private void setClear(String id, final int position) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_OrderCleared(BuildConfig.laundry_order_clear+id,"Bearer "+sessionManager.getACCESSTOKEN(),"","")).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action login = response.body();
                    Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                    queuelistnew.remove(position);
                    // homeAdapter.notifyItemRemoved(getAdapterPosition());
                    homeAdapter.notifyDataSetChanged();

                }
                else if(response.code()==401){
                    Action login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<Action> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }

/*
    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
        ArrayList<Dashboard.Data> homeViewModels;
        Context context;





        public HomeAdapter(Context context,ArrayList<Dashboard.Data> homeViewModels) {
            this.context=context;
            this.homeViewModels=homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dashboarditem, parent, false);

            return new HomeAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
            holder.mitem=homeViewModels.get(position);
            holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());

            // holder.orderrecived.setText(holder.mitem.getOrderreceived())

            holder.since.setText(getDate1(holder.mitem.getCreated_at()));
            holder.orderrecived.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
            if(holder.mitem.getStatus().equals("new_order")){
                holder.guests.setText("New Order");

                holder.dispatch.setText("ACCEPT");
                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mehroon));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mehroon));

            }
            else{
                holder.dispatch.setText("PICKED UP");
                holder.guests.setText("Accepted");

                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mogiya));


            }


            holder.dispatch.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    if(holder.mitem.getStatus().equals("new_order")) {
                        if(Network.isNetworkAvailable(getActivity())){
                            //    holder.stop();
                            setAccept(holder.mitem.getOrder_detail().getOrder_id());
                           // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                        }else if(Network.isNetworkAvailable2(getActivity())){
                            //   holder.stop();

                            setAccept(holder.mitem.getOrder_detail().getOrder_id());
                           // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);


                        }
                        else{

                        }

                    }
                    else{

                        //super.onBackPressed();
                        final Dialog dialog = new Dialog(getActivity());
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
                        textView.setText("Send to History?");
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
                                if(Network.isNetworkAvailable(getActivity())){
                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                    dialog.dismiss();

                                }
                                else if(Network.isNetworkAvailable2(getActivity())){
                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    setClear(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    dialog.dismiss();

                                }
                                else{

                                }
                            }
                        });

                    }




                }
            });
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   */
/* Intent intent=new Intent(getActivity(),Order_Detail.class);
                    intent.putExtra("roomno",holder.mitem.getPrimises().getPremise_no());
                    intent.putExtra("status",holder.mitem.getStatus());
                    intent.putExtra("id",holder.mitem.getOrder_detail().getOrder_id());
                    intent.putExtra("created",holder.mitem.getOrder_detail().getCreated_at());
                    intent.putExtra("pickuptime",holder.mitem.getOrder_detail().getRequested_pickup_at());
                    intent.putExtra("accepted",holder.mitem.getOrder_detail().getAccepted_at());

                    intent.putExtra("orderitems",holder.mitem.getOrder_laundry_items().toString());
                    getActivity().startActivity(intent);*//*

                    final Dialog dialog = new Dialog(context);
                    // Include dialog.xml file

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.ordee_detail);
                    int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
                    int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.99);
                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog.getWindow().setLayout(width1, height1);

                    dialog.setCancelable(false);
                    // Set dialog title
                    dialog.setTitle("");
                    dialog.show();
                    ImageView back=dialog.findViewById(R.id.back);
                    TextView title=dialog.findViewById(R.id.title);
                    title.setTypeface(holder.font);
                    TextView roomno=dialog.findViewById(R.id.roomno);
                    TextView guests=dialog.findViewById(R.id.guest);
                    TextView since=dialog.findViewById(R.id.since);
                    TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                    orderitemsdetailtext.setTypeface(holder.font);
                    TextView orderins=dialog.findViewById(R.id.orderins);
                    orderins.setTypeface(holder.font);
                    TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                    TextView acceptedat=dialog.findViewById(R.id.accepttext);
                    acceptedat.setTypeface(holder.font);
                    TextView accepted=dialog.findViewById(R.id.accepted);
                    TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                    if(holder.mitem.getStatus().equals("new_order")){
                        dispatchbutton.setText("ACCEPT");
                        guests.setText("New Order");
                        guests.setTextColor(context.getResources().getColor(R.color.mehroon));


                    }
                    else{
                        dispatchbutton.setText("PICKED UP");
                        guests.setText("Accepted");
                        guests.setTextColor(context.getResources().getColor(R.color.mogiya));

                    }


                    RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                    orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                    roomno.setText(holder.mitem.getPrimises().getPremise_no());
                    since.setText(getDate1(holder.mitem.getCreated_at()));

                    orderinsdetails.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));


                    HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_menu_items(),context);
                    orderitemsdetail.setAdapter(order_itemAdapterdetail);
                    LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                    if(holder.mitem.getStatus().equals("new_order")){
                        accepted.setText("-");
                    }
                    else{
                        accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));

                    }

                    if(holder.mitem.getStatus().equals("new_order")){
                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                    }
                    else{
                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                    }
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dispatchbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(holder.mitem.getStatus().equals("new_order")){
                                if(Network.isNetworkAvailable(getActivity())){

                                    setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    dialog.dismiss();
                                }
                                else if(Network.isNetworkAvailable2(getActivity())){

                                    setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    dialog.dismiss();

                                }
                                else{

                                }

                            }
                            else{

                                //super.onBackPressed();
                                final Dialog dialog1 = new Dialog(getActivity());
                                // Include dialog.xml file

                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog1.setContentView(R.layout.delet_dialog);
                                int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                                int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.80);
                                dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog1.getWindow().setLayout(width1, height1);

                                dialog1.setCancelable(false);
                                // Set dialog title
                                dialog1.setTitle("");
                                dialog1.show();
                                TextView textView=dialog1.findViewById(R.id.text) ;
                                textView.setText("Send to History?");
                                // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                TextView confirm=dialog1.findViewById(R.id.cancel) ;
                                TextView cancel1=dialog1.findViewById(R.id.confirm);

                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog1.dismiss();
                                    }
                                });
                                cancel1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(Network.isNetworkAvailable(getActivity())){
                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                            setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                            dialog1.dismiss();
                                            dialog.dismiss();

                                        }
                                        else if(Network.isNetworkAvailable2(getActivity())){

                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                            setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                            dialog1.dismiss();
                                            dialog.dismiss();

                                        }
                                        else{

                                        }
                                    }
                                });


                            }
                        }
                    });
                }
            });



        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }
        public void filterList(ArrayList<Dashboard.Data> filterdNames) {
            this.homeViewModels = filterdNames;
            notifyDataSetChanged();
        }
        public class viewholder extends RecyclerView.ViewHolder {
            public TextView roomno;
            public TextView guests;
            private Animation anim;
            public TextView orderrecived;
            public TextView since;
            private TextView dispatch;
            LinearLayout parent;
            LinearLayout colorimage;
            Dashboard.Data mitem;
           // private TextView more;
            final Typeface font1;
            final Typeface font;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno=itemView.findViewById(R.id.roomno);
                guests=itemView.findViewById(R.id.guest);
                orderrecived=itemView.findViewById(R.id.orderreceived);
                anim = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
                since=itemView.findViewById(R.id.since);
                colorimage=itemView.findViewById(R.id.colorimage);
                dispatch=itemView.findViewById(R.id.dispatch);
                parent=itemView.findViewById(R.id.parent);
                //more=itemView.findViewById(R.id.more);


                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");
                dispatch.setTypeface(font);

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
             //   more.setTypeface(font1);
                since.setTypeface(font1);


            }


        }

        public class Order_ItemAdapter1 extends  RecyclerView.Adapter<HomeAdapter.Order_ItemAdapter1.ViewHolder>{
            ArrayList<Dashboard.Order_menu_items> order_items;
            Context context;
            ArrayList<Dashboard.order_menu_addons> menuItems;
            String room;
            String guests1;
            String ordercreated;
            String description;
            String orderaccepted;
            String id;
            String status;
            int position1;
            public Order_ItemAdapter1(String room,String guests1,String ordercreated,String description,String orderaccepted,String id,int position1,String status,ArrayList<Dashboard.Order_menu_items> order_items, Context context) {
                this.order_items = order_items;
                this.context = context;
                this.room=room;
                this.guests1=guests1;
                this.ordercreated=ordercreated;
                this.orderaccepted=orderaccepted;
                this.description=description;
                this.status=status;
                this.position1=position1;
                this.id=id;
            }

            @NonNull
            @Override
            public HomeAdapter.Order_ItemAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.laundryorder_itemvalue, parent, false);
                return new HomeAdapter.Order_ItemAdapter1.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapter1.ViewHolder holder, int position) {
                holder.mitem=order_items.get(position);
                holder.name.setText(holder.mitem.getItem().getName());
                holder.quantity.setText(holder.mitem.getQuantity());
              */
/*  holder.categorylist.setText(holder.mitem.getItem().getLaundry_category().getName());

                if(holder.mitem.getIs_express_delivery().equals("1")){
                    holder.addonslist.setText("Express");
                    holder.addonslist.setTextColor(Color.parseColor("#4CAF50"));
                }
                else{
                    holder.addonslist.setText("Standard");
                    holder.addonslist.setTextColor(context.getResources().getColor(R.color.redblood));

                }*//*

            }

            @Override
            public int getItemCount() {
                return order_items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView name;
                TextView quantity;
                TextView addonslist;
                TextView categorylist;
                Dashboard.Order_menu_items mitem;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    name=itemView.findViewById(R.id.name);
                    quantity=itemView.findViewById(R.id.quantity);
                    addonslist=itemView.findViewById(R.id.addonslist);
                    categorylist=itemView.findViewById(R.id.categorylist);
                    final Typeface font = Typeface.createFromAsset(
                            context.getAssets(),
                            "font/verdana.ttf");
                    name.setTypeface(font);
                    quantity.setTypeface(font);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(context);
                            // Include dialog.xml file

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.laudrydetail_popup);
                            int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.48);
                            int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.90);
                            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                            dialog.getWindow().setLayout(width1, height1);

                            dialog.setCancelable(false);
                            // Set dialog title
                            dialog.setTitle("");
                            dialog.show();
                            ImageView back=dialog.findViewById(R.id.back);
                            TextView title=dialog.findViewById(R.id.title);
                            title.setTypeface(font);
                            TextView roomno=dialog.findViewById(R.id.roomno);
                            TextView guests=dialog.findViewById(R.id.guest);
                            TextView since=dialog.findViewById(R.id.since);
                            TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                            orderitemsdetailtext.setTypeface(font);
                            TextView orderins=dialog.findViewById(R.id.orderins);
                            orderins.setTypeface(font);
                            TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                            TextView acceptedat=dialog.findViewById(R.id.accepttext);
                            acceptedat.setTypeface(font);
                            TextView accepted=dialog.findViewById(R.id.accepted);
                            TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                            if(status.equals("new_order")){
                                dispatchbutton.setText("ACCEPT");
                                guests.setText("New Order");
                                guests.setTextColor(context.getResources().getColor(R.color.mehroon));

                            }
                            else{
                                dispatchbutton.setText("PICKED UP");
                                guests.setText("Accepted");
                                guests.setTextColor(context.getResources().getColor(R.color.mogiya));

                            }


                            RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                            orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                            roomno.setText(room);
                            since.setText(getDate1(ordercreated));

                            orderinsdetails.setText(getDate1(description));


                            HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(order_items,context);
                            orderitemsdetail.setAdapter(order_itemAdapterdetail);
                            LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                            if(status.equals("new_order")){
                                accepted.setText("-");
                            }
                            else{
                                accepted.setText(getDate1(orderaccepted));

                            }

                            if(status.equals("new_order")){
                                colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                                since.setTextColor(context.getResources().getColor(R.color.gray));
                                roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                            }
                            else{
                                colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                                since.setTextColor(context.getResources().getColor(R.color.gray));
                                roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                            }
                            back.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });


                            dispatchbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(status.equals("new_order")){
                                        if(Network.isNetworkAvailable(getActivity())){

                                            setAccept(id);
                                            setDispatch(id,position1);

                                            dialog.dismiss();
                                        }
                                        else if(Network.isNetworkAvailable2(getActivity())){

                                            setAccept(id);
                                            setDispatch(id,position1);

                                            dialog.dismiss();

                                        }
                                        else{

                                        }

                                    }
                                    else{

                                        //super.onBackPressed();
                                        final Dialog dialog1 = new Dialog(getActivity());
                                        // Include dialog.xml file

                                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog1.setContentView(R.layout.delet_dialog);
                                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.80);
                                        dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                        dialog1.getWindow().setLayout(width1, height1);

                                        dialog1.setCancelable(false);
                                        // Set dialog title
                                        dialog1.setTitle("");
                                        dialog1.show();
                                        TextView textView=dialog1.findViewById(R.id.text) ;
                                        textView.setText("Send to History?");
                                        // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                        TextView confirm=dialog1.findViewById(R.id.cancel) ;
                                        TextView cancel1=dialog1.findViewById(R.id.confirm);

                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                dialog1.dismiss();
                                            }
                                        });
                                        cancel1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(Network.isNetworkAvailable(getActivity())){
                                                    setDispatch(id,position1);

                                                    setClear(id,position1);
                                                    dialog1.dismiss();
                                                    dialog.dismiss();

                                                }
                                                else if(Network.isNetworkAvailable2(getActivity())){

                                                    setDispatch(id,position1);

                                                    setClear(id,position1);
                                                    dialog1.dismiss();
                                                    dialog.dismiss();

                                                }
                                                else{

                                                }
                                            }
                                        });


                                    }
                                }
                            });
                        }
                    });
                }
            }



        }
        public class AddonsAdatpter extends  RecyclerView.Adapter<HomeAdapter.AddonsAdatpter.ViewHolder>{
            ArrayList<Dashboard.Order_addons> order_items;
            Context context;

            public AddonsAdatpter(ArrayList<Dashboard.Order_addons> order_items, Context context) {
                this.order_items = order_items;
                this.context = context;
            }

            @NonNull
            @Override
            public HomeAdapter.AddonsAdatpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.addonitems, parent, false);
                return new HomeAdapter.AddonsAdatpter.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull HomeAdapter.AddonsAdatpter.ViewHolder holder, int position) {
                holder.mitem=order_items.get(position);
                if(holder.mitem.getItem().getItem_subaddon()!=null) {
                    holder.custom.setText((holder.mitem.getItem().getItem_subaddon().getName())+" : ");
                }
                else{
                    holder.custom.setText("Custom : ");
                }
                holder.customitems.setText(holder.mitem.getItem().getName());

            }

            @Override
            public int getItemCount() {
                return order_items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView custom;
                TextView customitems;
                Dashboard.Order_addons mitem;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    custom=itemView.findViewById(R.id.custom);
                    customitems=itemView.findViewById(R.id.customitems);
                    Typeface font = Typeface.createFromAsset(
                            context.getAssets(),
                            "font/verdana.ttf");
                    custom.setTypeface(font);
                    customitems.setTypeface(font);
                }
            }



        }
        public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<HomeAdapter.Order_ItemAdapterdetail.ViewHolder>{
            ArrayList<Dashboard.Order_menu_items> order_items;
            Context context;

            public Order_ItemAdapterdetail(ArrayList<Dashboard.Order_menu_items> order_items, Context context) {
                this.order_items = order_items;
                this.context = context;
            }

            @NonNull
            @Override
            public HomeAdapter.Order_ItemAdapterdetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.laundryorder_items, parent, false);
                return new HomeAdapter.Order_ItemAdapterdetail.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapterdetail.ViewHolder holder, int position) {
                holder.mitem=order_items.get(position);
                holder.name.setText(holder.mitem.getQuantity()+" X "+holder.mitem.getItem().getName());
               */
/* holder.categorylist.setText(holder.mitem.getItem().getLaundry_category().getName());
                if(holder.mitem.getIs_express_delivery().equals("1")){
                    holder.addonslist.setText("Express");
                    holder.addonslist.setTextColor(Color.parseColor("#4CAF50"));
                }
                else{
                    holder.addonslist.setText("Standard");
                    holder.addonslist.setTextColor(context.getResources().getColor(R.color.redblood));

                }*//*


            }

            @Override
            public int getItemCount() {
                return order_items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView name;
                TextView categorylist;
                Dashboard.Order_menu_items mitem;
                TextView addonslist;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    name=itemView.findViewById(R.id.name);
                    addonslist=itemView.findViewById(R.id.addonslist);
                    categorylist=itemView.findViewById(R.id.categorylist);

                    Typeface font = Typeface.createFromAsset(
                            context.getAssets(),
                            "font/verdana.ttf");
                    name.setTypeface(font);

                }
            }



        }
        private String getDate(long time) {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(time * 1000);
            String date = DateFormat.format("HH:mm", cal).toString();
            return date;
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
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }
*/

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
        ArrayList<Laundry_Dashboard1.Data> homeViewModels;
        Context context;





        public HomeAdapter(Context context,ArrayList<Laundry_Dashboard1.Data> homeViewModels) {
            this.context=context;
            this.homeViewModels=homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.laundrydashboarditem, parent, false);

            return new HomeAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
            holder.mitem=homeViewModels.get(position);
            holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());

            // holder.orderrecived.setText(holder.mitem.getOrderreceived())

            if (holder.mitem.getDescription() != null) {
                holder.instaruction.setText(holder.mitem.getDescription());

            }
            else{
                holder.instaruction.setText("-");

            }

            holder.since.setText(getDate1(holder.mitem.getCreated_at()));
            holder.orderrecived.setText(getDatenew(holder.mitem.getOrder_detail().getRequested_pickup_at()));
            if(holder.mitem.getStatus().equals("New Order")){
                holder.guests.setText("New Order");

                holder.dispatch.setText("ACCEPT");
                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mehroon));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mehroon));

            }
            else{
                holder.dispatch.setText("PICKED UP");
                holder.guests.setText("Accepted");

                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mogiya));


            }
            if(holder.mitem.getOrder_laundry_items().size()>4){
                holder.more.setVisibility(View.VISIBLE);
                holder.more.setText(String.valueOf(holder.mitem.getOrder_laundry_items().size()-4)+" More");
            }
            else{
                holder.more.setVisibility(View.GONE);

            }

            HomeAdapter.Order_ItemAdapter1 order_itemAdapter1=new HomeAdapter.Order_ItemAdapter1(holder.mitem.getPrimises().getPremise_no(),holder.mitem.getStatus(),holder.mitem.getOrder_detail().getCreated_at(),holder.mitem.getOrder_detail().getRequested_pickup_at(),holder.mitem.getOrder_detail().getAccepted_at(),holder.mitem.getOrder_detail().getOrder_id(),position,holder.mitem.getStatus(),holder.mitem.getOrder_laundry_items(),holder.mitem.getGuest().getName(),context);
            holder.recyclerView.setAdapter(order_itemAdapter1);

            holder.dispatch.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    if(holder.mitem.getStatus().equals("New Order")) {
                        if(Network.isNetworkAvailable(getActivity())){
                            //    holder.stop();
                            setAccept(holder.mitem.getOrder_detail().getOrder_id());
                            // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                        }else if(Network.isNetworkAvailable2(getActivity())){
                            //   holder.stop();

                            setAccept(holder.mitem.getOrder_detail().getOrder_id());
                            // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);


                        }
                        else{

                        }

                    }
                    else{

                        //super.onBackPressed();
                        final Dialog dialog = new Dialog(getActivity());
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
                        textView.setText("Send to History?");
                        // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                        TextView confirm=dialog.findViewById(R.id.cancel) ;
                        CardView cancel1=dialog.findViewById(R.id.confirm);

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });
                        cancel1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(Network.isNetworkAvailable(getActivity())){
                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                    dialog.dismiss();

                                }
                                else if(Network.isNetworkAvailable2(getActivity())){
                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    setClear(holder.mitem.getOrder_detail().getOrder_id(),position);

                                    dialog.dismiss();

                                }
                                else{

                                }
                            }
                        });

                    }




                }
            });
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.mitem.getGuest().getName().equals("Guest")){
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.laudrydetail_popupwithoutguets);
                        int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
                        int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.99);
                        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                        dialog.getWindow().setLayout(width1, height1);

                        dialog.setCancelable(false);
                        // Set dialog title
                        dialog.setTitle("");
                        dialog.show();
                        ImageView back=dialog.findViewById(R.id.back);
                        TextView title=dialog.findViewById(R.id.title);
                        title.setTypeface(holder.font);
                        TextView roomno=dialog.findViewById(R.id.roomno);
                        TextView name=dialog.findViewById(R.id.name);
                        TextView guests=dialog.findViewById(R.id.guest);
                        TextView since=dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins=dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat=dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                        name.setText(holder.mitem.getGuest().getName());
                        if(holder.mitem.getStatus().equals("New Order")){
                            dispatchbutton.setText("ACCEPT");
                            guests.setText("New Order");
                            guests.setTextColor(context.getResources().getColor(R.color.mehroon));


                        }
                        else{
                            dispatchbutton.setText("PICKED UP");
                            guests.setText("Accepted");
                            guests.setTextColor(context.getResources().getColor(R.color.mogiya));

                        }


                        RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getCreated_at()));

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequested_pickup_at()));


                        HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_laundry_items(),context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                        if(holder.mitem.getStatus().equals("New Order")){
                            accepted.setText("-");
                        }
                        else{
                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));

                        }

                        if(holder.mitem.getStatus().equals("New Order")){
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        }
                        else{
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                        }
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        dispatchbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(holder.mitem.getStatus().equals("New Order")){
                                    if(Network.isNetworkAvailable(getActivity())){

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        ///  setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();
                                    }
                                    else if(Network.isNetworkAvailable2(getActivity())){

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();

                                    }
                                    else{

                                    }

                                }
                                else{

                                    //super.onBackPressed();
                                    final Dialog dialog1 = new Dialog(getActivity());
                                    // Include dialog.xml file

                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog1.setContentView(R.layout.delet_dialog);
                                    int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                                    int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
                                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                    dialog1.getWindow().setLayout(width1, height1);

                                    dialog1.setCancelable(false);
                                    // Set dialog title
                                    dialog1.setTitle("");
                                    dialog1.show();
                                    TextView textView=dialog1.findViewById(R.id.text) ;
                                    textView.setText("Send to History?");
                                    // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                    TextView confirm=dialog1.findViewById(R.id.cancel) ;
                                    CardView cancel1=dialog1.findViewById(R.id.confirm);

                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog1.dismiss();
                                        }
                                    });
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(Network.isNetworkAvailable(getActivity())){
                                                setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                                dialog1.dismiss();
                                                dialog.dismiss();

                                            }
                                            else if(Network.isNetworkAvailable2(getActivity())){

                                                setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                                dialog1.dismiss();
                                                dialog.dismiss();

                                            }
                                            else{

                                            }
                                        }
                                    });


                                }
                            }
                        });
                    }
                    else{
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.laudrydetail_popup);
                        int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
                        int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.99);
                        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                        dialog.getWindow().setLayout(width1, height1);

                        dialog.setCancelable(false);
                        // Set dialog title
                        dialog.setTitle("");
                        dialog.show();
                        ImageView back=dialog.findViewById(R.id.back);
                        TextView title=dialog.findViewById(R.id.title);
                        title.setTypeface(holder.font);
                        TextView roomno=dialog.findViewById(R.id.roomno);
                        TextView name=dialog.findViewById(R.id.name);
                        TextView guests=dialog.findViewById(R.id.guest);
                        TextView since=dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins=dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat=dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                        name.setText(holder.mitem.getGuest().getName());
                        if(holder.mitem.getStatus().equals("New Order")){
                            dispatchbutton.setText("ACCEPT");
                            guests.setText("New Order");
                            guests.setTextColor(context.getResources().getColor(R.color.mehroon));


                        }
                        else{
                            dispatchbutton.setText("PICKED UP");
                            guests.setText("Accepted");
                            guests.setTextColor(context.getResources().getColor(R.color.mogiya));

                        }


                        RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getCreated_at()));

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequested_pickup_at()));


                        HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_laundry_items(),context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                        if(holder.mitem.getStatus().equals("New Order")){
                            accepted.setText("-");
                        }
                        else{
                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));

                        }

                        if(holder.mitem.getStatus().equals("New Order")){
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        }
                        else{
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                        }
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        dispatchbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(holder.mitem.getStatus().equals("New Order")){
                                    if(Network.isNetworkAvailable(getActivity())){

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        ///  setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();
                                    }
                                    else if(Network.isNetworkAvailable2(getActivity())){

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();

                                    }
                                    else{

                                    }

                                }
                                else{

                                    //super.onBackPressed();
                                    final Dialog dialog1 = new Dialog(getActivity());
                                    // Include dialog.xml file

                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog1.setContentView(R.layout.delet_dialog);
                                    int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                                    int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
                                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                    dialog1.getWindow().setLayout(width1, height1);

                                    dialog1.setCancelable(false);
                                    // Set dialog title
                                    dialog1.setTitle("");
                                    dialog1.show();
                                    TextView textView=dialog1.findViewById(R.id.text) ;
                                    textView.setText("Send to History?");
                                    // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                    TextView confirm=dialog1.findViewById(R.id.cancel) ;
                                    CardView cancel1=dialog1.findViewById(R.id.confirm);

                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog1.dismiss();
                                        }
                                    });
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(Network.isNetworkAvailable(getActivity())){
                                                setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                                dialog1.dismiss();
                                                dialog.dismiss();

                                            }
                                            else if(Network.isNetworkAvailable2(getActivity())){

                                                setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                setClear(holder.mitem.getOrder_detail().getOrder_id(),position);
                                                dialog1.dismiss();
                                                dialog.dismiss();

                                            }
                                            else{

                                            }
                                        }
                                    });


                                }
                            }
                        });
                    }

                }
            });




        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }
        public void filterList(ArrayList<Laundry_Dashboard1.Data> filterdNames) {
            this.homeViewModels = filterdNames;
            notifyDataSetChanged();
        }
        public class viewholder extends RecyclerView.ViewHolder {
            public TextView roomno;
            public TextView guests;
            private  Animation anim;
            public TextView orderrecived;
            public  TextView orderstatus;
            public TextView since;
            private TextView dispatch;
            private TextView instaruction;
            RecyclerView recyclerView;
            LinearLayout parent;
            LinearLayout colorimage;
            Laundry_Dashboard1.Data mitem;
            private TextView more;
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(getContext(), alert);
            final Typeface font1;
            final Typeface font;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno=itemView.findViewById(R.id.roomno);
                guests=itemView.findViewById(R.id.guest);
                orderrecived=itemView.findViewById(R.id.orderreceived);
                orderstatus=itemView.findViewById(R.id.status);
                anim = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
                since=itemView.findViewById(R.id.since);
                colorimage=itemView.findViewById(R.id.colorimage);
                dispatch=itemView.findViewById(R.id.dispatch);
                recyclerView=itemView.findViewById(R.id.ordeitems);
                parent=itemView.findViewById(R.id.parent);
                more=itemView.findViewById(R.id.more);
                instaruction=itemView.findViewById(R.id.instrctions);
                recyclerView.setLayoutManager(new GridLayoutManager(context,2));


                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");
                dispatch.setTypeface(font);

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
                more.setTypeface(font1);
                since.setTypeface(font1);


            }


        }

        public class Order_ItemAdapter1 extends  RecyclerView.Adapter<HomeAdapter.Order_ItemAdapter1.ViewHolder>{
            ArrayList<Laundry_Dashboard1.Order_laundry_items> order_items;
            Context context;

            String room;
            String guests1;
            String ordercreated;
            String description;
            String orderaccepted;
            String id;
            String guestname;
            String status;
            int position1;
            public Order_ItemAdapter1(String room,String guests1,String ordercreated,String description,String orderaccepted,String id,int position1,String status,ArrayList<Laundry_Dashboard1.Order_laundry_items> order_items,String guestname, Context context) {
                this.order_items = order_items;
                this.context = context;
                this.room=room;
                this.guests1=guests1;
                this.ordercreated=ordercreated;
                this.orderaccepted=orderaccepted;
                this.description=description;
                this.status=status;
                this.position1=position1;
                this.guestname=guestname;
                this.id=id;
            }

            @NonNull
            @Override
            public HomeAdapter.Order_ItemAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.laundryorder_itemvalue, parent, false);
                return new HomeAdapter.Order_ItemAdapter1.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapter1.ViewHolder holder, int position) {
                holder.mitem=order_items.get(position);
                holder.name.setText(holder.mitem.getItem().getName());
                holder.quantity.setText(holder.mitem.getQuantity());
                holder.categorylist.setText(holder.mitem.getItem().getLaundry_category().getName());

                if(holder.mitem.getItem().getIs_express_delivery().equals("1")){
                    holder.addonslist.setText("Express");
                    holder.addonslist.setTextColor(Color.parseColor("#4CAF50"));
                }
                else{
                    holder.addonslist.setText("Standard");
                    holder.addonslist.setTextColor(context.getResources().getColor(R.color.redblood));

                }
            }

            @Override
            public int getItemCount() {
                return order_items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView name;
                TextView quantity;
                TextView addonslist;
                TextView categorylist;
                Laundry_Dashboard1.Order_laundry_items mitem;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    name=itemView.findViewById(R.id.name);
                    quantity=itemView.findViewById(R.id.quantity);
                    addonslist=itemView.findViewById(R.id.addonslist);
                    categorylist=itemView.findViewById(R.id.categorylist);
                    final Typeface font = Typeface.createFromAsset(
                            context.getAssets(),
                            "font/verdana.ttf");
                    name.setTypeface(font);
                    quantity.setTypeface(font);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(guestname.equals("Guest")){
                                final Dialog dialog = new Dialog(context);
                                // Include dialog.xml file

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.laudrydetail_popupwithoutguets);
                                int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
                                int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.99);
                                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog.getWindow().setLayout(width1, height1);

                                dialog.setCancelable(false);
                                // Set dialog title
                                dialog.setTitle("");
                                dialog.show();
                                ImageView back=dialog.findViewById(R.id.back);
                                TextView title=dialog.findViewById(R.id.title);
                                title.setTypeface(font);
                                TextView roomno=dialog.findViewById(R.id.roomno);
                                TextView name=dialog.findViewById(R.id.name);
                                TextView guests=dialog.findViewById(R.id.guest);
                                TextView since=dialog.findViewById(R.id.since);
                                TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                                orderitemsdetailtext.setTypeface(font);
                                TextView orderins=dialog.findViewById(R.id.orderins);
                                orderins.setTypeface(font);
                                TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                                TextView acceptedat=dialog.findViewById(R.id.accepttext);
                                acceptedat.setTypeface(font);
                                TextView accepted=dialog.findViewById(R.id.accepted);
                                TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                                name.setText(guestname);
                                if(status.equals("New Order")){
                                    dispatchbutton.setText("ACCEPT");
                                    guests.setText("New Order");
                                    guests.setTextColor(context.getResources().getColor(R.color.mehroon));

                                }
                                else{
                                    dispatchbutton.setText("PICKED UP");
                                    guests.setText("Accepted");
                                    guests.setTextColor(context.getResources().getColor(R.color.mogiya));

                                }


                                RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                                orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                                roomno.setText(room);
                                since.setText(getDate1(ordercreated));

                                orderinsdetails.setText(getDatenew(description));


                                HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(order_items,context);
                                orderitemsdetail.setAdapter(order_itemAdapterdetail);
                                LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                                if(status.equals("New Order")){
                                    accepted.setText("-");
                                }
                                else{
                                    accepted.setText(getDate1(orderaccepted));

                                }

                                if(status.equals("New Order")){
                                    colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                                    since.setTextColor(context.getResources().getColor(R.color.gray));
                                    roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                                }
                                else{
                                    colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                                    since.setTextColor(context.getResources().getColor(R.color.gray));
                                    roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                                }
                                back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });


                                dispatchbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(status.equals("New Order")){
                                            if(Network.isNetworkAvailable(getActivity())){

                                                setAccept(id);
                                                // setDispatch(id,position1);

                                                dialog.dismiss();
                                            }
                                            else if(Network.isNetworkAvailable2(getActivity())){

                                                setAccept(id);
                                                // setDispatch(id,position1);

                                                dialog.dismiss();

                                            }
                                            else{

                                            }

                                        }
                                        else{

                                            //super.onBackPressed();
                                            final Dialog dialog1 = new Dialog(getActivity());
                                            // Include dialog.xml file

                                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog1.setContentView(R.layout.delet_dialog);
                                            int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                                            int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
                                            dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                            dialog1.getWindow().setLayout(width1, height1);

                                            dialog1.setCancelable(false);
                                            // Set dialog title
                                            dialog1.setTitle("");
                                            dialog1.show();
                                            TextView textView=dialog1.findViewById(R.id.text) ;
                                            textView.setText("Send to History?");
                                            // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                            TextView confirm=dialog1.findViewById(R.id.cancel) ;
                                            CardView cancel1=dialog1.findViewById(R.id.confirm);

                                            confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    dialog1.dismiss();
                                                }
                                            });
                                            cancel1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if(Network.isNetworkAvailable(getActivity())){
                                                        setDispatch(id,position1);

                                                        setClear(id,position1);
                                                        dialog1.dismiss();
                                                        dialog.dismiss();

                                                    }
                                                    else if(Network.isNetworkAvailable2(getActivity())){

                                                        setDispatch(id,position1);

                                                        setClear(id,position1);
                                                        dialog1.dismiss();
                                                        dialog.dismiss();

                                                    }
                                                    else{

                                                    }
                                                }
                                            });


                                        }
                                    }
                                });
                            }
                            else{
                                final Dialog dialog = new Dialog(context);
                                // Include dialog.xml file

                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.laudrydetail_popup);
                                int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.99);
                                int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.99);
                                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog.getWindow().setLayout(width1, height1);

                                dialog.setCancelable(false);
                                // Set dialog title
                                dialog.setTitle("");
                                dialog.show();
                                ImageView back=dialog.findViewById(R.id.back);
                                TextView title=dialog.findViewById(R.id.title);
                                title.setTypeface(font);
                                TextView roomno=dialog.findViewById(R.id.roomno);
                                TextView name=dialog.findViewById(R.id.name);
                                TextView guests=dialog.findViewById(R.id.guest);
                                TextView since=dialog.findViewById(R.id.since);
                                TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                                orderitemsdetailtext.setTypeface(font);
                                TextView orderins=dialog.findViewById(R.id.orderins);
                                orderins.setTypeface(font);
                                TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                                TextView acceptedat=dialog.findViewById(R.id.accepttext);
                                acceptedat.setTypeface(font);
                                TextView accepted=dialog.findViewById(R.id.accepted);
                                TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                                name.setText(guestname);
                                if(status.equals("New Order")){
                                    dispatchbutton.setText("ACCEPT");
                                    guests.setText("New Order");
                                    guests.setTextColor(context.getResources().getColor(R.color.mehroon));

                                }
                                else{
                                    dispatchbutton.setText("PICKED UP");
                                    guests.setText("Accepted");
                                    guests.setTextColor(context.getResources().getColor(R.color.mogiya));

                                }


                                RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                                orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                                roomno.setText(room);
                                since.setText(getDate1(ordercreated));

                                orderinsdetails.setText(getDatenew(description));


                                HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(order_items,context);
                                orderitemsdetail.setAdapter(order_itemAdapterdetail);
                                LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                                if(status.equals("New Order")){
                                    accepted.setText("-");
                                }
                                else{
                                    accepted.setText(getDate1(orderaccepted));

                                }

                                if(status.equals("New Order")){
                                    colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                                    since.setTextColor(context.getResources().getColor(R.color.gray));
                                    roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                                }
                                else{
                                    colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                                    since.setTextColor(context.getResources().getColor(R.color.gray));
                                    roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                                }
                                back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });


                                dispatchbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(status.equals("New Order")){
                                            if(Network.isNetworkAvailable(getActivity())){

                                                setAccept(id);
                                                // setDispatch(id,position1);

                                                dialog.dismiss();
                                            }
                                            else if(Network.isNetworkAvailable2(getActivity())){

                                                setAccept(id);
                                                // setDispatch(id,position1);

                                                dialog.dismiss();

                                            }
                                            else{

                                            }

                                        }
                                        else{

                                            //super.onBackPressed();
                                            final Dialog dialog1 = new Dialog(getActivity());
                                            // Include dialog.xml file

                                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog1.setContentView(R.layout.delet_dialog);
                                            int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                                            int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
                                            dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                            dialog1.getWindow().setLayout(width1, height1);

                                            dialog1.setCancelable(false);
                                            // Set dialog title
                                            dialog1.setTitle("");
                                            dialog1.show();
                                            TextView textView=dialog1.findViewById(R.id.text) ;
                                            textView.setText("Send to History?");
                                            // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                            TextView confirm=dialog1.findViewById(R.id.cancel) ;
                                            CardView cancel1=dialog1.findViewById(R.id.confirm);

                                            confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    dialog1.dismiss();
                                                }
                                            });
                                            cancel1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if(Network.isNetworkAvailable(getActivity())){
                                                        setDispatch(id,position1);

                                                        setClear(id,position1);
                                                        dialog1.dismiss();
                                                        dialog.dismiss();

                                                    }
                                                    else if(Network.isNetworkAvailable2(getActivity())){

                                                        setDispatch(id,position1);

                                                        setClear(id,position1);
                                                        dialog1.dismiss();
                                                        dialog.dismiss();

                                                    }
                                                    else{

                                                    }
                                                }
                                            });


                                        }
                                    }
                                });
                            }

                        }
                    });
                }
            }



        }
        public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<HomeAdapter.Order_ItemAdapterdetail.ViewHolder>{
            ArrayList<Laundry_Dashboard1.Order_laundry_items> order_items;
            Context context;

            public Order_ItemAdapterdetail(ArrayList<Laundry_Dashboard1.Order_laundry_items> order_items, Context context) {
                this.order_items = order_items;
                this.context = context;
            }

            @NonNull
            @Override
            public HomeAdapter.Order_ItemAdapterdetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.laundryorder_items, parent, false);
                return new HomeAdapter.Order_ItemAdapterdetail.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapterdetail.ViewHolder holder, int position) {
                holder.mitem=order_items.get(position);
                holder.name.setText(holder.mitem.getQuantity()+" X "+holder.mitem.getItem().getName());
                holder.categorylist.setText(holder.mitem.getItem().getLaundry_category().getName());
                if(holder.mitem.getItem().getIs_express_delivery().equals("1")){
                    holder.addonslist.setText("Express");
                    holder.addonslist.setTextColor(Color.parseColor("#4CAF50"));
                }
                else{
                    holder.addonslist.setText("Standard");
                    holder.addonslist.setTextColor(context.getResources().getColor(R.color.redblood));

                }

            }

            @Override
            public int getItemCount() {
                return order_items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView name;
                TextView categorylist;
                Laundry_Dashboard1.Order_laundry_items mitem;
                TextView addonslist;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    name=itemView.findViewById(R.id.name);
                    addonslist=itemView.findViewById(R.id.addonslist);
                    categorylist=itemView.findViewById(R.id.categorylist);

                    Typeface font = Typeface.createFromAsset(
                            context.getAssets(),
                            "font/verdana.ttf");
                    name.setTypeface(font);

                }
            }



        }
        private String getDate(long time) {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(time * 1000);
            String date = DateFormat.format("HH:mm", cal).toString();
            return date;
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
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
        private String getDatenew(String time) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
           // dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        try {
            ArrayList<Laundry_Dashboard1.Data> filterdNames = new ArrayList<>();

            //looping through existing elements
            for (Laundry_Dashboard1.Data s : queuelistnew) {
                //if the existing elements contains the search input
                if (s.getOrder_detail() != null) {
                    if (s.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())) {
                        //adding the element to filtered list
                        filterdNames.add(s);
                    }
                } else {
                    if (s.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())) {
                        //adding the element to filtered list
                        filterdNames.add(s);
                    }
                }
            }

            //calling a method of the adapter class and passing the filtered list
            homeAdapter.filterList(filterdNames);
        }catch (Exception e){

        }
    }
    public void tryAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        if (view != null)
            view.startAnimation(animation);
    }



    private void setAlarm1(Laundry_Dashboard1.Data laundry_dashboard) {
        mDbHelper = new DbHelper(getActivity());

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

            AlarmManager alarmMgr = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlarmReceiver.class);
             long time=60*1000;

            //String alertTitle = mTitleText.getText().toString();
            intent.putExtra("title", laundry_dashboard.getPrimises().getPremise_no());
            intent.putExtra("time", timeString);
            intent.putExtra("alramid",alarmid);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), alarmid, intent, 0);

            alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

            cv.put(mDbHelper.TIME, timeString);
            cv.put(mDbHelper.DATE, dateString);
            db.insert(mDbHelper.TABLE_NAME, null, cv);

        }catch (Exception e){

        }

    }



}
