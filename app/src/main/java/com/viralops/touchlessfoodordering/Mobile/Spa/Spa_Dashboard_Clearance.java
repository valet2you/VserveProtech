package com.viralops.touchlessfoodordering.Mobile.Spa;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.Mobile.IRD.Associate_Dashboard_Clearance;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Dashboard1;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.Spa_dashboard;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.database.AlarmReceiver;
import com.viralops.touchlessfoodordering.database.AlarmReceiver1;

import com.viralops.touchlessfoodordering.database.DbHelper1;


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

public class Spa_Dashboard_Clearance extends Fragment {

ShimmerRecyclerView recyclerView;
SessionManager sessionManager;
TextView norecord;
HomeAdapter homeAdapter;
String text="";
     public TextView standardsrvice;
     public TextView standtotal;
     public TextView express;
     public TextView totalexpress;
     public TextView totallaudry;
     public TextView totalorderslaundry;
    private Dialog dialognew;
    ArrayList<Spa_dashboard.Data> queuelist=new ArrayList<>();
    ArrayList<Spa_dashboard.Data> queuelistnew=new ArrayList<>();
    ArrayList<Spa_dashboard.Data> localarray=new ArrayList<>();

    AutoCompleteTextView searchView;
    SQLiteDatabase db;
    DbHelper1 mDbHelper;
    public static Associate_Dashboard_Clearance newInstance() {
        return new Associate_Dashboard_Clearance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.assciate_dashboard, container, false);
        final Typeface font = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Regular.ttf");
        Typeface font1 = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Thin.ttf");
        searchView =  view.findViewById(R.id.searchView);
        mDbHelper = new DbHelper1(getActivity());
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

        (RetrofitClientInstance.getApiService().Spa_getOrders("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Spa_dashboard>() {
            @Override
            public void onResponse(@NonNull Call<Spa_dashboard> call, @NonNull Response<Spa_dashboard> response) {

                if(response.code()==201||response.code()==200){
                    Spa_dashboard  login = response.body();
                    queuelist=new ArrayList<>();
                    queuelistnew=new ArrayList<>();

                    queuelist=login.getData();
                    if(queuelist.size()!=0){
                        for(int i=0;i<queuelist.size();i++){
                            if(!queuelist.get(i).getStatus().equals("new_order")){
                                Spa_dashboard.Data data=new Spa_dashboard.Data();
                                data.setCreated_at(queuelist.get(i).getCreated_at());
                                data.setDescription(queuelist.get(i).getDescription());
                                data.setHotel_id(queuelist.get(i).getHotel_id());
                                data.setId(queuelist.get(i).getId());
                                data.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                data.setOrder_detail(queuelist.get(i).getOrder_detail());
                                data.setOrder_spa_items(queuelist.get(i).getOrder_spa_items());
                                data.setPayment_status(queuelist.get(i).getPayment_status());
                                data.setPrimises(queuelist.get(i).getPrimises());
                                data.setPrimises_id(queuelist.get(i).getPrimises_id());
                                data.setStatus(queuelist.get(i).getStatus());
                                data.setType(queuelist.get(i).getType());
                                data.setGuest(queuelist.get(i).getGuest());
                                data.setUpdated_at(queuelist.get(i).getUpdated_at());


                                queuelistnew.add(data);
                            }

                        }
                        if(queuelistnew.size()!=0) {
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                            norecord.setVisibility(View.GONE);
                        }
                        else{

                                homeAdapter=new HomeAdapter(getActivity(),queuelistnew);
                                recyclerView.setAdapter(homeAdapter);
                                norecord.setVisibility(View.VISIBLE);

                        }
                    }
                    else{
                        homeAdapter=new HomeAdapter(getActivity(),queuelistnew);
                        recyclerView.setAdapter(homeAdapter);
                        norecord.setVisibility(View.VISIBLE);
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
                    Spa_dashboard login = response.body();
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
            public void onFailure(@NonNull Call<Spa_dashboard> call, @NonNull Throwable t) {
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

        (RetrofitClientInstance.getApiService().Spa_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Header>() {
            @Override
            public void onResponse(@NonNull Call<Header> call, @NonNull Response<Header> response) {

                if(response.code()==202||response.code()==200){
                    Header  login = response.body();
                    Spa_Mobile.newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                    totalorderslaundry.setText(String.valueOf(login.getData().getDispatched_order()+login.getData().getAccepted_order()+login.getData().getNew_order()+login.getData().getCleared_order()));
                    Spa_Mobile.newcountevent.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");



                }
                else if(response.code()==401){
                    Header login = response.body();
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
            public void onFailure(@NonNull Call<Header> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
    private void setAccept(String id) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Spa_OrderAccepted(BuildConfig.spa_order_accept+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action login = response.body();
                    Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
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

        (RetrofitClientInstance.getApiService().Spa_OrderDispatched(BuildConfig.spa_order_dispatch+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
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
    private void setClear(final String id, final int position, String text1, String reason) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Spa_OrderCleared(BuildConfig.spa_order_clear+id,"Bearer "+sessionManager.getACCESSTOKEN(),text1,reason)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action login = response.body();
                    Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                     text="";
                    int alarmid=0;
                    try{
                        alarmid=Integer.parseInt(id);
                    }catch (Exception e){

                    }
                    for(int i=0;i<localarray.size();i++){
                        if(localarray.get(i).getO_id().equals(id)){
                            localarray.remove(i);
                            db.delete(DbHelper1.TABLE_NAME, DbHelper1.O_ID + "=" + id, null);
                            Intent intent  = new Intent(getActivity(), AlarmReceiver1.class);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), alarmid, intent, 0);
                            AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                            if(pendingIntent != null) {
                                alarmManager.cancel(pendingIntent);
                            }
                            // db.close();
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
                holder.dispatch.setText("TAKE ACTION");
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
                        dispatchbutton.setText("TAKE ACTION");
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

        public class Order_ItemAdapter1 extends  RecyclerView.Adapter<Order_ItemAdapter1.ViewHolder>{
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
                                dispatchbutton.setText("TAKE ACTION");
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
        public class AddonsAdatpter extends  RecyclerView.Adapter<AddonsAdatpter.ViewHolder>{
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
        public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<Order_ItemAdapterdetail.ViewHolder>{
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
    ArrayList<Spa_dashboard.Data> homeViewModels;
    Context context;





    public HomeAdapter(Context context,ArrayList<Spa_dashboard.Data> homeViewModels) {
        this.context=context;
        this.homeViewModels=homeViewModels;

    }

    @NonNull
    @Override
    public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spaitemlist, parent, false);

        return new HomeAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
        holder.mitem=homeViewModels.get(position);
        holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
        holder.guests.setText(holder.mitem.getNo_of_guest());

        // holder.orderrecived.setText(holder.mitem.getOrderreceived())



        holder.since.setText(getDate1(holder.mitem.getCreated_at()));
        holder.orderrecived.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));
        if(holder.mitem.getStatus().equals("new_order")){

            holder.dispatch.setText("ACCEPT");
            holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
            holder.since.setTextColor(context.getResources().getColor(R.color.gray));
            holder.roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

        }
        else{
            holder.dispatch.setText("TAKE ACTION");

            holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
            holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
            holder.since.setTextColor(context.getResources().getColor(R.color.gray));
            holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


        }
        if(holder.mitem.getOrder_spa_items().size()>4){
            holder.more.setVisibility(View.VISIBLE);
            holder.more.setText(String.valueOf(holder.mitem.getOrder_spa_items().size()-4)+" More");
        }
        else{
            holder.more.setVisibility(View.GONE);

        }

        HomeAdapter.Order_ItemAdapter1 order_itemAdapter1=new HomeAdapter.Order_ItemAdapter1(holder.mitem.getPrimises().getPremise_no(),holder.mitem.getNo_of_guest(),holder.mitem.getOrder_detail().getCreated_at(),holder.mitem.getOrder_detail().getRequest_schedule_at(),holder.mitem.getOrder_detail().getAccepted_at(),holder.mitem.getOrder_detail().getOrder_id(),position,holder.mitem.getStatus(),holder.mitem.getOrder_spa_items(),holder.mitem.getGuest().getName(),
                context);
        holder.recyclerView.setAdapter(order_itemAdapter1);

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
                    dialog.setContentView(R.layout.spaactionpopup);
                    int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                    int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog.getWindow().setLayout(width1, height1);

                    dialog.setCancelable(false);
                    // Set dialog title
                    dialog.setTitle("");
                    dialog.show();
                    // TextView textView=dialog.findViewById(R.id.text) ;
                    // textView.setText("Send to History?");
                    // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                    ImageView confirm=dialog.findViewById(R.id.close) ;
                    TextView cancel1=dialog.findViewById(R.id.dispatch);
                    final RadioGroup radioGroup=dialog.findViewById(R.id.radiogroup);
                    final EditText editText=dialog.findViewById(R.id.reason);

                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            int selectedid=radioGroup.getCheckedRadioButtonId();

                            RadioButton radioButton=(RadioButton)dialog.findViewById(selectedid);
                            text= String.valueOf(radioButton.getTag());
                            // Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                            if(text.equals("other")){
                                editText.setVisibility(View.VISIBLE);
                            }
                            else{
                                editText.setVisibility(View.GONE);

                            }

                        }
                    });

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });
                    cancel1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(text.equals("")){
                                Toast.makeText(getActivity(),"Please select atleast one",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(text.equals("other")){
                                    if(editText.getText().toString().equals("")){
                                        Toast.makeText(getActivity(),"Please write your reason",Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(Network.isNetworkAvailable(getActivity())){

                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                            setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,editText.getText().toString());
                                            dialog.dismiss();

                                        }
                                        else if(Network.isNetworkAvailable2(getActivity())){
                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                            setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,editText.getText().toString());

                                            dialog.dismiss();

                                        }
                                        else{

                                        }
                                    }

                                }
                                else{
                                    if(Network.isNetworkAvailable(getActivity())){
                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,"");
                                        dialog.dismiss();

                                    }
                                    else if(Network.isNetworkAvailable2(getActivity())){
                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,"");

                                        dialog.dismiss();

                                    }
                                    else{

                                    }
                                }
                            }


                        }
                    });

                }




            }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mitem.getOrder_detail()!=null) {
                    if (holder.mitem.getGuest().getName().equals("Guest")) {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.spadetail_popupwithoutguest);
                        int width1 = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.99);
                        int height1 = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.99);
                        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                        dialog.getWindow().setLayout(width1, height1);

                        dialog.setCancelable(false);
                        // Set dialog title
                        dialog.setTitle("");
                        dialog.show();
                        ImageView back = dialog.findViewById(R.id.back);
                        TextView title = dialog.findViewById(R.id.title);
                        title.setTypeface(holder.font);
                        TextView roomno = dialog.findViewById(R.id.roomno);
                        TextView guests = dialog.findViewById(R.id.guest);
                        TextView name = dialog.findViewById(R.id.name);
                        TextView since = dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext = dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins = dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails = dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat = dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted = dialog.findViewById(R.id.accepted);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        name.setText(holder.mitem.getGuest().getName());
                        if (holder.mitem.getStatus().equals("new_order")) {
                            dispatchbutton.setText("ACCEPT");


                        } else {
                            dispatchbutton.setText("TAKE ACTION");


                        }
                        guests.setText(holder.mitem.getNo_of_guest());


                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getCreated_at()));

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));


                        HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail = new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_spa_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if (holder.mitem.getStatus().equals("new_order")) {
                            accepted.setText("-");
                        } else {
                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));

                        }

                        if (holder.mitem.getStatus().equals("new_order")) {
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        } else {
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
                                if (holder.mitem.getStatus().equals("new_order")) {
                                    if (Network.isNetworkAvailable(getActivity())) {

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();
                                    } else if (Network.isNetworkAvailable2(getActivity())) {

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();

                                    } else {

                                    }

                                } else {

                                    //super.onBackPressed();
                                    final Dialog dialog1 = new Dialog(getActivity());
                                    // Include dialog.xml file

                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog1.setContentView(R.layout.spaactionpopup);
                                    int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                                    int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);
                                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                    dialog1.getWindow().setLayout(width1, height1);

                                    dialog1.setCancelable(false);
                                    // Set dialog title
                                    dialog1.setTitle("");
                                    dialog1.show();
                                    // TextView textView=dialog.findViewById(R.id.text) ;
                                    // textView.setText("Send to History?");
                                    // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                    ImageView confirm = dialog1.findViewById(R.id.close);
                                    TextView cancel1 = dialog1.findViewById(R.id.dispatch);
                                    final RadioGroup radioGroup = dialog1.findViewById(R.id.radiogroup);
                                    final EditText editText = dialog1.findViewById(R.id.reason);

                                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                            int selectedid = radioGroup.getCheckedRadioButtonId();

                                            RadioButton radioButton = (RadioButton) dialog1.findViewById(selectedid);
                                            text = String.valueOf(radioButton.getTag());
                                            //  Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                                            if (text.equals("other")) {
                                                editText.setVisibility(View.VISIBLE);
                                            } else {
                                                editText.setVisibility(View.GONE);

                                            }

                                        }
                                    });

                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog1.dismiss();
                                        }
                                    });
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (text.equals("")) {
                                                Toast.makeText(getActivity(), "Please select atleast one", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (text.equals("other")) {
                                                    if (editText.getText().toString().equals("")) {
                                                        Toast.makeText(getActivity(), "Please write your reason", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (Network.isNetworkAvailable(getActivity())) {

                                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                            setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, editText.getText().toString());
                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        } else if (Network.isNetworkAvailable2(getActivity())) {
                                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                            setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, editText.getText().toString());

                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        } else {

                                                        }
                                                    }

                                                } else {
                                                    if (Network.isNetworkAvailable(getActivity())) {
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, "");
                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    } else if (Network.isNetworkAvailable2(getActivity())) {
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, "");

                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    } else {

                                                    }
                                                }
                                            }


                                        }
                                    });

                                }
                            }
                        });
                    } else {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.spadetail_popup);
                        int width1 = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.99);
                        int height1 = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.99);
                        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                        dialog.getWindow().setLayout(width1, height1);

                        dialog.setCancelable(false);
                        // Set dialog title
                        dialog.setTitle("");
                        dialog.show();
                        ImageView back = dialog.findViewById(R.id.back);
                        TextView title = dialog.findViewById(R.id.title);
                        title.setTypeface(holder.font);
                        TextView roomno = dialog.findViewById(R.id.roomno);
                        TextView guests = dialog.findViewById(R.id.guest);
                        TextView name = dialog.findViewById(R.id.name);
                        TextView since = dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext = dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins = dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails = dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat = dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted = dialog.findViewById(R.id.accepted);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        name.setText(holder.mitem.getGuest().getName());
                        if (holder.mitem.getStatus().equals("new_order")) {
                            dispatchbutton.setText("ACCEPT");


                        } else {
                            dispatchbutton.setText("TAKE ACTION");


                        }
                        guests.setText(holder.mitem.getNo_of_guest());


                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getCreated_at()));

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));


                        HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail = new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_spa_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if (holder.mitem.getStatus().equals("new_order")) {
                            accepted.setText("-");
                        } else {
                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));

                        }

                        if (holder.mitem.getStatus().equals("new_order")) {
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        } else {
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
                                if (holder.mitem.getStatus().equals("new_order")) {
                                    if (Network.isNetworkAvailable(getActivity())) {

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();
                                    } else if (Network.isNetworkAvailable2(getActivity())) {

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();

                                    } else {

                                    }

                                } else {

                                    //super.onBackPressed();
                                    final Dialog dialog1 = new Dialog(getActivity());
                                    // Include dialog.xml file

                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog1.setContentView(R.layout.spaactionpopup);
                                    int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                                    int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);
                                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                    dialog1.getWindow().setLayout(width1, height1);

                                    dialog1.setCancelable(false);
                                    // Set dialog title
                                    dialog1.setTitle("");
                                    dialog1.show();
                                    // TextView textView=dialog.findViewById(R.id.text) ;
                                    // textView.setText("Send to History?");
                                    // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                    ImageView confirm = dialog1.findViewById(R.id.close);
                                    TextView cancel1 = dialog1.findViewById(R.id.dispatch);
                                    final RadioGroup radioGroup = dialog1.findViewById(R.id.radiogroup);
                                    final EditText editText = dialog1.findViewById(R.id.reason);

                                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                            int selectedid = radioGroup.getCheckedRadioButtonId();

                                            RadioButton radioButton = (RadioButton) dialog1.findViewById(selectedid);
                                            text = String.valueOf(radioButton.getTag());
                                            //  Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                                            if (text.equals("other")) {
                                                editText.setVisibility(View.VISIBLE);
                                            } else {
                                                editText.setVisibility(View.GONE);

                                            }

                                        }
                                    });

                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog1.dismiss();
                                        }
                                    });
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (text.equals("")) {
                                                Toast.makeText(getActivity(), "Please select atleast one", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (text.equals("other")) {
                                                    if (editText.getText().toString().equals("")) {
                                                        Toast.makeText(getActivity(), "Please write your reason", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (Network.isNetworkAvailable(getActivity())) {

                                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                            setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, editText.getText().toString());
                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        } else if (Network.isNetworkAvailable2(getActivity())) {
                                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                            setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, editText.getText().toString());

                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        } else {

                                                        }
                                                    }

                                                } else {
                                                    if (Network.isNetworkAvailable(getActivity())) {
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, "");
                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    } else if (Network.isNetworkAvailable2(getActivity())) {
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, "");

                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    } else {

                                                    }
                                                }
                                            }


                                        }
                                    });

                                }
                            }
                        });
                    }
                }
                else{
                    {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.spadetail_popupwithoutguest);
                        int width1 = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.99);
                        int height1 = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.99);
                        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                        dialog.getWindow().setLayout(width1, height1);

                        dialog.setCancelable(false);
                        // Set dialog title
                        dialog.setTitle("");
                        dialog.show();
                        ImageView back = dialog.findViewById(R.id.back);
                        TextView title = dialog.findViewById(R.id.title);
                        title.setTypeface(holder.font);
                        TextView roomno = dialog.findViewById(R.id.roomno);
                        TextView guests = dialog.findViewById(R.id.guest);
                        TextView name = dialog.findViewById(R.id.name);
                        TextView since = dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext = dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins = dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails = dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat = dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted = dialog.findViewById(R.id.accepted);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        if (holder.mitem.getStatus().equals("new_order")) {
                            dispatchbutton.setText("ACCEPT");


                        } else {
                            dispatchbutton.setText("TAKE ACTION");


                        }
                        guests.setText(holder.mitem.getNo_of_guest());


                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getCreated_at()));

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));


                        HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail = new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_spa_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if (holder.mitem.getStatus().equals("new_order")) {
                            accepted.setText("-");
                        } else {
                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));

                        }

                        if (holder.mitem.getStatus().equals("new_order")) {
                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        } else {
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
                                if (holder.mitem.getStatus().equals("new_order")) {
                                    if (Network.isNetworkAvailable(getActivity())) {

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();
                                    } else if (Network.isNetworkAvailable2(getActivity())) {

                                        setAccept(holder.mitem.getOrder_detail().getOrder_id());
                                        // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                        dialog.dismiss();

                                    } else {

                                    }

                                } else {

                                    //super.onBackPressed();
                                    final Dialog dialog1 = new Dialog(getActivity());
                                    // Include dialog.xml file

                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog1.setContentView(R.layout.spaactionpopup);
                                    int width1 = (int) (getResources().getDisplayMetrics().widthPixels * 0.99);
                                    int height1 = (int) (getResources().getDisplayMetrics().heightPixels * 0.99);
                                    dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                    dialog1.getWindow().setLayout(width1, height1);

                                    dialog1.setCancelable(false);
                                    // Set dialog title
                                    dialog1.setTitle("");
                                    dialog1.show();
                                    // TextView textView=dialog.findViewById(R.id.text) ;
                                    // textView.setText("Send to History?");
                                    // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                    ImageView confirm = dialog1.findViewById(R.id.close);
                                    TextView cancel1 = dialog1.findViewById(R.id.dispatch);
                                    final RadioGroup radioGroup = dialog1.findViewById(R.id.radiogroup);
                                    final EditText editText = dialog1.findViewById(R.id.reason);

                                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                            int selectedid = radioGroup.getCheckedRadioButtonId();

                                            RadioButton radioButton = (RadioButton) dialog1.findViewById(selectedid);
                                            text = String.valueOf(radioButton.getTag());
                                            //  Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                                            if (text.equals("other")) {
                                                editText.setVisibility(View.VISIBLE);
                                            } else {
                                                editText.setVisibility(View.GONE);

                                            }

                                        }
                                    });

                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog1.dismiss();
                                        }
                                    });
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (text.equals("")) {
                                                Toast.makeText(getActivity(), "Please select atleast one", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (text.equals("other")) {
                                                    if (editText.getText().toString().equals("")) {
                                                        Toast.makeText(getActivity(), "Please write your reason", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (Network.isNetworkAvailable(getActivity())) {

                                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                            setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, editText.getText().toString());
                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        } else if (Network.isNetworkAvailable2(getActivity())) {
                                                            setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                            setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, editText.getText().toString());

                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        } else {

                                                        }
                                                    }

                                                } else {
                                                    if (Network.isNetworkAvailable(getActivity())) {
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, "");
                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    } else if (Network.isNetworkAvailable2(getActivity())) {
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(), position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(), position, text, "");

                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    } else {

                                                    }
                                                }
                                            }


                                        }
                                    });

                                }
                            }
                        });
                    }
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return homeViewModels.size();
    }
    public void filterList(ArrayList<Spa_dashboard.Data> filterdNames) {
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
        Spa_dashboard.Data mitem;
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
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));


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
        ArrayList<Spa_dashboard.Order_spa_items> order_items;
        Context context;
        ArrayList<Spa_dashboard.Order_spa_items> menuItems;
        String room;
        String guests1;
        String ordercreated;
        String description;
        String orderaccepted;
        String id;
        String status;
        String guestname;
        int position1;
        public Order_ItemAdapter1(String room,String guests1,String ordercreated,String description,String orderaccepted,String id,int position1,String status,ArrayList<Spa_dashboard.Order_spa_items> order_items,String guestname, Context context) {
            this.order_items = order_items;
            this.context = context;
            this.room=room;
            this.guests1=guests1;
            this.ordercreated=ordercreated;
            this.orderaccepted=orderaccepted;
            this.description=description;
            this.status=status;
            this.guestname=guestname;
            this.position1=position1;
            this.id=id;
        }

        @NonNull
        @Override
        public HomeAdapter.Order_ItemAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spaorder_itemvalue, parent, false);
            return new HomeAdapter.Order_ItemAdapter1.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapter1.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.name.setText(holder.mitem.getItem().getName());
            holder.categorylist.setText(holder.mitem.getItem().getSpa_category().getName());


        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView categorylist;
            Spa_dashboard.Order_spa_items mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                categorylist=itemView.findViewById(R.id.categorylist);
                final Typeface font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
                name.setTypeface(font);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(guestname.equals("Guest")){
                            final Dialog dialog = new Dialog(context);
                            // Include dialog.xml file

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.spadetail_popupwithoutguest);
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
                            if(status.equals("new_order")){
                                dispatchbutton.setText("ACCEPT");


                            }
                            else{
                                dispatchbutton.setText("TAKE ACTION");


                            }


                            RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                            orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                            roomno.setText(room);
                            guests.setText(guests1);
                            since.setText(getDate1(ordercreated));

                            orderinsdetails.setText(getDatenew(description));


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
                                            //setDispatch(id,position1);

                                            dialog.dismiss();
                                        }
                                        else if(Network.isNetworkAvailable2(getActivity())){

                                            setAccept(id);
                                            //  setDispatch(id,position1);

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
                                        dialog1.setContentView(R.layout.spaactionpopup);
                                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                                        dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                        dialog1.getWindow().setLayout(width1, height1);

                                        dialog1.setCancelable(false);
                                        // Set dialog title
                                        dialog1.setTitle("");
                                        dialog1.show();
                                        // Tex1tView textView=dialog.findViewById(R.id.text) ;
                                        // textView.setText("Send to History?");
                                        // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                        ImageView confirm=dialog1.findViewById(R.id.close) ;
                                        TextView cancel1=dialog1.findViewById(R.id.dispatch);
                                        final RadioGroup radioGroup=dialog1.findViewById(R.id.radiogroup);
                                        final EditText editText=dialog1.findViewById(R.id.reason);

                                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                int selectedid=radioGroup.getCheckedRadioButtonId();

                                                RadioButton radioButton=(RadioButton)dialog1.findViewById(selectedid);
                                                text= String.valueOf(radioButton.getTag());
                                                // Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                                                if(text.equals("other")){
                                                    editText.setVisibility(View.VISIBLE);
                                                }
                                                else{
                                                    editText.setVisibility(View.GONE);

                                                }

                                            }
                                        });

                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                dialog1.dismiss();
                                            }
                                        });
                                        cancel1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(text.equals("")){
                                                    Toast.makeText(getActivity(),"Please select atleast one",Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    if(text.equals("other")){
                                                        if(editText.getText().toString().equals("")){
                                                            Toast.makeText(getActivity(),"Please write your reason",Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            if(Network.isNetworkAvailable(getActivity())){

                                                                setDispatch(id,position1);

                                                                setClear(id,position1,text,editText.getText().toString());
                                                                dialog.dismiss();
                                                                dialog1.dismiss();

                                                            }
                                                            else if(Network.isNetworkAvailable2(getActivity())){
                                                                setDispatch(id,position1);

                                                                setClear(id,position1,text,editText.getText().toString());

                                                                dialog.dismiss();
                                                                dialog1.dismiss();

                                                            }
                                                            else{

                                                            }
                                                        }

                                                    }
                                                    else{
                                                        if(Network.isNetworkAvailable(getActivity())){
                                                            setDispatch(id,position1);

                                                            setClear(id,position1,text,"");
                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        }
                                                        else if(Network.isNetworkAvailable2(getActivity())){
                                                            setDispatch(id,position1);

                                                            setClear(id,position1,text,"");

                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        }
                                                        else{

                                                        }
                                                    }
                                                }


                                            }
                                        });

                                    }
                                }
                            });
                        }else{
                            final Dialog dialog = new Dialog(context);
                            // Include dialog.xml file

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.spadetail_popup);
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
                            if(status.equals("new_order")){
                                dispatchbutton.setText("ACCEPT");


                            }
                            else{
                                dispatchbutton.setText("TAKE ACTION");


                            }


                            RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                            orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
                            roomno.setText(room);
                            guests.setText(guests1);
                            since.setText(getDate1(ordercreated));

                            orderinsdetails.setText(getDatenew(description));


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
                                            //setDispatch(id,position1);

                                            dialog.dismiss();
                                        }
                                        else if(Network.isNetworkAvailable2(getActivity())){

                                            setAccept(id);
                                            //  setDispatch(id,position1);

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
                                        dialog1.setContentView(R.layout.spaactionpopup);
                                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                                        dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                        dialog1.getWindow().setLayout(width1, height1);

                                        dialog1.setCancelable(false);
                                        // Set dialog title
                                        dialog1.setTitle("");
                                        dialog1.show();
                                        // Tex1tView textView=dialog.findViewById(R.id.text) ;
                                        // textView.setText("Send to History?");
                                        // String textstring="Do you confirm that room is cleared and trolley is back to IRD operation? or"+<b>
                                        ImageView confirm=dialog1.findViewById(R.id.close) ;
                                        TextView cancel1=dialog1.findViewById(R.id.dispatch);
                                        final RadioGroup radioGroup=dialog1.findViewById(R.id.radiogroup);
                                        final EditText editText=dialog1.findViewById(R.id.reason);

                                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                int selectedid=radioGroup.getCheckedRadioButtonId();

                                                RadioButton radioButton=(RadioButton)dialog1.findViewById(selectedid);
                                                text= String.valueOf(radioButton.getTag());
                                                // Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                                                if(text.equals("other")){
                                                    editText.setVisibility(View.VISIBLE);
                                                }
                                                else{
                                                    editText.setVisibility(View.GONE);

                                                }

                                            }
                                        });

                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                dialog1.dismiss();
                                            }
                                        });
                                        cancel1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if(text.equals("")){
                                                    Toast.makeText(getActivity(),"Please select atleast one",Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    if(text.equals("other")){
                                                        if(editText.getText().toString().equals("")){
                                                            Toast.makeText(getActivity(),"Please write your reason",Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            if(Network.isNetworkAvailable(getActivity())){

                                                                setDispatch(id,position1);

                                                                setClear(id,position1,text,editText.getText().toString());
                                                                dialog.dismiss();
                                                                dialog1.dismiss();

                                                            }
                                                            else if(Network.isNetworkAvailable2(getActivity())){
                                                                setDispatch(id,position1);

                                                                setClear(id,position1,text,editText.getText().toString());

                                                                dialog.dismiss();
                                                                dialog1.dismiss();

                                                            }
                                                            else{

                                                            }
                                                        }

                                                    }
                                                    else{
                                                        if(Network.isNetworkAvailable(getActivity())){
                                                            setDispatch(id,position1);

                                                            setClear(id,position1,text,"");
                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        }
                                                        else if(Network.isNetworkAvailable2(getActivity())){
                                                            setDispatch(id,position1);

                                                            setClear(id,position1,text,"");

                                                            dialog.dismiss();
                                                            dialog1.dismiss();

                                                        }
                                                        else{

                                                        }
                                                    }
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
        ArrayList<Spa_dashboard.Order_spa_items> order_items;
        Context context;

        public Order_ItemAdapterdetail(ArrayList<Spa_dashboard.Order_spa_items> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public HomeAdapter.Order_ItemAdapterdetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spaorder_items, parent, false);
            return new HomeAdapter.Order_ItemAdapterdetail.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapterdetail.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.name.setText(holder.mitem.getItem().getName());
            holder.categorylist.setText(holder.mitem.getItem().getSpa_category().getName());
        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView categorylist;
            Spa_dashboard.Order_spa_items mitem;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
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
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Spa_dashboard.Data> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Spa_dashboard.Data s :queuelist) {
            //if the existing elements contains the search input
            if (s.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        try {
            homeAdapter.filterList(filterdNames);
        }
        catch (Exception e){

        }
    }
    public void tryAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        if (view != null)
            view.startAnimation(animation);
    }









}
