package com.viralops.touchlessfoodordering.Tablet.combine;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Dashboard;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.Order_Item;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Internetconnection;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragmentcombine extends Fragment implements View.OnClickListener {
        private Ringtone r;
        ShimmerRecyclerView shimmerRecyclerView;
        ArrayList<Dashboard.Data> queuelist=new ArrayList<>();
        ArrayList<Dashboard.Data> queuelistnew=new ArrayList<>();
        ArrayList<Order_Item> orderlist;
        SessionManager sessionManager;
        LinearLayout neworder,acceptedorder,dispatchedorder;
        TextView newordertext,newcount,acceptedtext,acceptedcount,disptachedtexxt,disptachedcount;
        TextView norecord;
        AutoCompleteTextView searchView;
        HomeAdapter homeAdapter;
        Animation animation;
        private SearchView.OnQueryTextListener queryTextListener;

        public static MainFragmentcombine newInstance() {
            return new MainFragmentcombine();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view =inflater.inflate(R.layout.main_fragmentnew, container, false);
            setHasOptionsMenu(true);

            homeAdapter=new HomeAdapter(getActivity(),queuelist);

            searchView =  view.findViewById(R.id.searchView);
            sessionManager=new SessionManager(getActivity());
            sessionManager.setIsINternet("false");

            shimmerRecyclerView=view.findViewById(R.id.recyclerview);
            shimmerRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
            norecord=view.findViewById(R.id.norecord);
            neworder=view.findViewById(R.id.neworder);
            neworder.setOnClickListener(this);
            newcount=view.findViewById(R.id.newordercount);
            newordertext=view.findViewById(R.id.newordertext);

            acceptedorder=view.findViewById(R.id.acceptedorder);
            acceptedorder.setOnClickListener(this);
            acceptedcount=view.findViewById(R.id.acceptedcount);
            acceptedtext=view.findViewById(R.id.acceptedtexttext);

            dispatchedorder=view.findViewById(R.id.diaptachedorders);
            dispatchedorder.setOnClickListener(this);
            disptachedcount=view.findViewById(R.id.dispatchedcount);
            disptachedtexxt=view.findViewById(R.id.dispatchedtext);

            neworder.setBackgroundResource(R.drawable.bluebackgroudblack);
            newordertext.setTextColor(getResources().getColor(R.color.white));
            newcount.setTextColor(getResources().getColor(R.color.white));

            acceptedorder.setBackgroundResource(R.drawable.whitebackgroudblack1);
            acceptedtext.setTextColor(getResources().getColor(R.color.gray));
            acceptedcount.setTextColor(getResources().getColor(R.color.gray));

            dispatchedorder.setBackgroundResource(R.drawable.whitebackgroudblack1);
            disptachedtexxt.setTextColor(getResources().getColor(R.color.gray));
            disptachedcount.setTextColor(getResources().getColor(R.color.gray));
            if(Network.isNetworkAvailable(getActivity())){
                GetMenu();

            }
            else if(Network.isNetworkAvailable2(getActivity())){
                GetMenu();

            }
            else{
                if (sessionManager.getIsINternet().equals("false")) {
                    Intent intent = new Intent(getActivity(), Internetconnection.class);
                    startActivity(intent);

                    sessionManager.setIsINternet("true");
                    this.getActivity().finish();

                } else {

                }
            }

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
            try {
                MainActivity.neworderss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (queuelist.size() != 0) {
                            try {
                                if(MainActivity.imgBell.getVisibility()==v.VISIBLE) {
                                    shimmerRecyclerView.smoothScrollToPosition(queuelist.size() - 1);
                                    MainActivity.imgBell.setVisibility(View.GONE);
                                }
                                else{

                                }
                            } catch (Exception e) {

                            }
                        }
                        else{
                            MainActivity.imgBell.setVisibility(View.GONE);

                        }
                    }
                });
            }
            catch (Exception e){

            }
            shimmerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if(MainActivity.imgBell.getVisibility()==View.GONE) {
                        GridLayoutManager layoutManager = GridLayoutManager.class.cast(recyclerView.getLayoutManager());
                        int lastItem = homeAdapter.getItemCount() - 1;
                        tryAnimation(layoutManager.findViewByPosition(lastItem));
                    }
                    else{

                    }
                }
            });


            return view;

        }


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            //mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            // TODO: Use the ViewModel
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.main, menu);
            MenuItem searchItem = menu.findItem(R.id.search);
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main:
                    // Not implemented here
                    return false;
                default:
                    break;
            }

            return false;
        }

        private void GetMenu() {
            // display a progress dialog
            shimmerRecyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

            (RetrofitClientInstance.getApiService().getOrders1("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Dashboard>() {
                @Override
                public void onResponse(@NonNull Call<Dashboard> call, @NonNull Response<Dashboard> response) {

                    if(response.code()==201||response.code()==200){
                        Dashboard login = response.body();
                        queuelist=new ArrayList<>();
                        queuelistnew=new ArrayList<>();
                        queuelist=login.getData();
                        if(queuelist.size()!=0){
                            for(int i=0;i<queuelist.size();i++){
                                if(queuelist.get(i).getStatus().equals("new_order")){
                                    Dashboard.Data data=new Dashboard.Data();
                                    data.setCreated_at(queuelist.get(i).getCreated_at());
                                    data.setDescription(queuelist.get(i).getDescription());
                                    data.setHotel_id(queuelist.get(i).getHotel_id());
                                    data.setId(queuelist.get(i).getId());
                                    data.setNo_of_guest(queuelist.get(i).getNo_of_guest());
                                    data.setOrder_detail(queuelist.get(i).getOrder_detail());
                                    data.setGuest(queuelist.get(i).getGuest());
                                    data.setOrder_menu_items(queuelist.get(i).getOrder_menu_items());
                                    data.setPayment_status(queuelist.get(i).getPayment_status());
                                    data.setIs_complementary(queuelist.get(i).getIs_complementary());
                                    data.setPrimises(queuelist.get(i).getPrimises());
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
                                    String date23 = DateFormat.format("dd MMM yyyy hh:mm a",dt ).toString();
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
                            if(queuelistnew.size()!=0) {
                                homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                                shimmerRecyclerView.setAdapter(homeAdapter);
                                norecord.setVisibility(View.GONE);
                            }
                            else{
                                homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                                shimmerRecyclerView.setAdapter(homeAdapter);
                                norecord.setVisibility(View.VISIBLE);
                            }
                       /* if(getArguments().getString("visible").equals("1")){
                            homeAdapter = new HomeAdapter(getActivity(), queuelistnew);
                            recyclerView.setAdapter(homeAdapter);
                           // recyclerView.smoothScrollToPosition(queuelistnew.size() - 1);

                            norecord.setVisibility(View.GONE);
                            AssociateMain.homeicon.setVisibility(View.GONE);
                            AssociateMain.homeicon.setVisibility(View.GONE);

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
                            shimmerRecyclerView.setAdapter(homeAdapter);
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
                        Dashboard login = response.body();
                        Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                        sessionManager.logoutsession();

                    }
                    else if(response.code()==500){
                        Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                    }
                    else{

                    }

                    shimmerRecyclerView.hideShimmer();



                }

                @Override
                public void onFailure(@NonNull Call<Dashboard> call, @NonNull Throwable t) {
                    Log.d("response", Arrays.toString(t.getStackTrace()));
                    shimmerRecyclerView.hideShimmer();

                }
            });

        }
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
                        MainActivity.availabletrolley.setText(String.valueOf(login.getData().getNew_order()));
                        MainActivity.availabletray.setText(String.valueOf(login.getData().getAccepted_order()));
                        MainActivity.availableassciate.setText(String.valueOf(login.getData().getDispatched_order()));
                        MainActivity.totalorders.setText(String.valueOf(login.getData().getNew_order()+login.getData().getAccepted_order()+login.getData().getCleared_order()));
                        newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                        acceptedcount.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");
                        disptachedcount.setText("( "+String.valueOf(login.getData().getDispatched_order())+" )");

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

            (RetrofitClientInstance.getApiService().OrderAccepted(BuildConfig.order_accept+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
                @Override
                public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                    if(response.code()==202||response.code()==200){
                        Action login = response.body();
                        Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                        searchView.setText("");
                        MainActivity.imgBell.setVisibility(View.GONE);

                        if(Network.isNetworkAvailable(getActivity())){
                            GetMenu();
                        }
                        else if(Network.isNetworkAvailable2(getActivity())){
                            GetMenu();
                        }
                        else{

                        }
                        MainActivity.imgBell.setVisibility(View.GONE);

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

            (RetrofitClientInstance.getApiService().OrderDispatched(BuildConfig.order_dispatch+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
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

            (RetrofitClientInstance.getApiService().OrderCleared(BuildConfig.order_clear+id,"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Action>() {
                @Override
                public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                    if(response.code()==202||response.code()==200){
                        Action login = response.body();
                        Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                        queuelist.remove(position);
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


    @Override
    public void onClick(View v) {
            if(v.getId()== R.id.neworder){
                MainActivity.isvisisble=1;
                neworder.setBackgroundResource(R.drawable.bluebackgroudblack);
                newordertext.setTextColor(getResources().getColor(R.color.white));
                newcount.setTextColor(getResources().getColor(R.color.white));

                acceptedorder.setBackgroundResource(R.drawable.whitebackgroudblack1);
                acceptedtext.setTextColor(getResources().getColor(R.color.gray));
                acceptedcount.setTextColor(getResources().getColor(R.color.gray));

                dispatchedorder.setBackgroundResource(R.drawable.whitebackgroudblack1);
                disptachedtexxt.setTextColor(getResources().getColor(R.color.gray));
                disptachedcount.setTextColor(getResources().getColor(R.color.gray));
                if(Network.isNetworkAvailable(getActivity())){
                   GetMenu();
                }
                else if(Network.isNetworkAvailable2(getActivity())){
                  GetMenu();
                }
                else{

                }
              /*  getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, MainFragment.newInstance())
                        .commitNow();*/
            }
            if(v.getId()== R.id.acceptedorder){
                MainActivity.isvisisble=7;
                neworder.setBackgroundResource(R.drawable.whitebackgroudblack1);
                newordertext.setTextColor(getResources().getColor(R.color.gray));
                newcount.setTextColor(getResources().getColor(R.color.gray));

                acceptedorder.setBackgroundResource(R.drawable.bluebackgroudblack);
                acceptedtext.setTextColor(getResources().getColor(R.color.white));
                acceptedcount.setTextColor(getResources().getColor(R.color.white));

                dispatchedorder.setBackgroundResource(R.drawable.whitebackgroudblack1);
                disptachedtexxt.setTextColor(getResources().getColor(R.color.gray));
                disptachedcount.setTextColor(getResources().getColor(R.color.gray));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, AcceptedMainFragmentcombine.newInstance())
                        .commitNow();
            }
            if(v.getId()== R.id.diaptachedorders){
                MainActivity.isvisisble=7;

                neworder.setBackgroundResource(R.drawable.whitebackgroudblack1);
                newordertext.setTextColor(getResources().getColor(R.color.gray));
                newcount.setTextColor(getResources().getColor(R.color.gray));

                acceptedorder.setBackgroundResource(R.drawable.whitebackgroudblack1);
                acceptedtext.setTextColor(getResources().getColor(R.color.gray));
                acceptedcount.setTextColor(getResources().getColor(R.color.gray));

                dispatchedorder.setBackgroundResource(R.drawable.bluebackgroudblack);
                disptachedtexxt.setTextColor(getResources().getColor(R.color.white));
                disptachedcount.setTextColor(getResources().getColor(R.color.white));


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_view, DispatchedMainFragmentcombine.newInstance())
                        .commitNow();
            }

    }

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
    ArrayList<Dashboard.Data> homeViewModels;
    Context context;
    private Handler mHandler1;

    private Handler mHandler = new Handler();
    ArrayList<viewholder> lstHolders;


    Timer tmr;
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            synchronized (lstHolders) {
                long currentTime = SystemClock.elapsedRealtime();
                for (HomeAdapter.viewholder holder : lstHolders) {

                    holder.updateTimeRemaining(currentTime);


                }
            }
        }
    };

    private void startUpdateTimer() {
        tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }
    private void stoptimer() {
        tmr.cancel();

    }

    public HomeAdapter(Context context,ArrayList<Dashboard.Data> homeViewModels) {
        this.context=context;
        this.homeViewModels=homeViewModels;
        mHandler1 = new Handler();

        lstHolders = new ArrayList<>();

        startUpdateTimer();
    }

    @NonNull
    @Override
    public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.queuelist, parent, false);

        return new HomeAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
        holder.mitem=homeViewModels.get(position);
        holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
        holder.guests.setText(holder.mitem.getNo_of_guest());
        holder.complimentary.setOnCheckedChangeListener(null);
        if(holder.mitem.getIs_complementary().equals("1")){
           holder.complimentary.setChecked(true);
        }
        else{
            holder.complimentary.setChecked(false);
        }
        // holder.orderrecived.setText(holder.mitem.getOrderreceived())
        holder.complimentary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.complimentary.setChecked(true);
                    if(Network.isNetworkAvailable(getActivity())){
                        GetMenuitemenab(holder.mitem.getOrder_detail().getOrder_id(), String.valueOf(position));
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenuitemenab(holder.mitem.getOrder_detail().getOrder_id(), String.valueOf(position));

                    }
                    else{

                    }
                }
                else{
                    holder.complimentary.setChecked(false);

                    if(Network.isNetworkAvailable(getActivity())){
                        GetMenuitemdis(holder.mitem.getOrder_detail().getOrder_id(), String.valueOf(position));
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenuitemdis(holder.mitem.getOrder_detail().getOrder_id(), String.valueOf(position));

                    }
                    else{

                    }
                }
            }
        });

        if (holder.mitem.getDescription() != null) {
            holder.instaruction.setText(holder.mitem.getDescription());

        }
        else{
            holder.instaruction.setText("-");

        }
        if(holder.mitem.getPayment_status().equals("offline")){
            holder.orderstatuspayemet.setText("Settle Later");

        }else {
            holder.orderstatuspayemet.setText(holder.mitem.getPayment_status());
        }


        holder.since.setText(getDate1(holder.mitem.getCreated_at()));
            holder.dispatch.setText("ACCEPT");
            holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
            holder.since.setTextColor(context.getResources().getColor(R.color.gray));
            holder.roomno.setTextColor(context.getResources().getColor(R.color.mehroon));


        if(holder.mitem.getOrder_menu_items().size()>4){
            holder.more.setVisibility(View.VISIBLE);
            holder.more.setText(String.valueOf(holder.mitem.getOrder_menu_items().size()-4)+" More");
        }
        else{
            holder.more.setVisibility(View.GONE);

        }
        if(holder.mitem.getGuest()!=null) {
            HomeAdapter.Order_ItemAdapter1 order_itemAdapter1 = new HomeAdapter.Order_ItemAdapter1(holder.mitem.getPayment_status(), holder.mitem.getPrimises().getPremise_no(), holder.mitem.getNo_of_guest(), holder.mitem.getOrder_detail().getCreated_at(), holder.mitem.getDescription(), holder.mitem.getOrder_detail().getAccepted_at(), holder.mitem.getOrder_detail().getOrder_id(), position, holder.mitem.getStatus(), holder.mitem.getOrder_menu_items(),holder.mitem.getGuest().getName(), context,holder.mitem.getOrder_detail().getDispatched_at());
            holder.recyclerView.setAdapter(order_itemAdapter1);
        }
        else{
            HomeAdapter.Order_ItemAdapter1 order_itemAdapter1 = new HomeAdapter.Order_ItemAdapter1(holder.mitem.getPayment_status(), holder.mitem.getPrimises().getPremise_no(), holder.mitem.getNo_of_guest(), holder.mitem.getOrder_detail().getCreated_at(), holder.mitem.getDescription(), holder.mitem.getOrder_detail().getAccepted_at(), holder.mitem.getOrder_detail().getOrder_id(), position, holder.mitem.getStatus(), holder.mitem.getOrder_menu_items(),"Guest", context,holder.mitem.getOrder_detail().getDispatched_at());
            holder.recyclerView.setAdapter(order_itemAdapter1);
        }

        holder.dispatch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                if(Network.isNetworkAvailable(getActivity())){
                    //    holder.stop();
                    setAccept(holder.mitem.getOrder_detail().getOrder_id());
                    //setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                }else if(Network.isNetworkAvailable2(getActivity())){
                    //   holder.stop();

                    setAccept(holder.mitem.getOrder_detail().getOrder_id());
                    // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);


                }
                else{

                }
            }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                // Include dialog.xml file

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.detail_popup_detail);
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
                TextView status=dialog.findViewById(R.id.status);
                TextView statustext=dialog.findViewById(R.id.statustext);
                statustext.setTypeface(holder.font);
                statustext.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);
                TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                    dispatchbutton.setText("ACCEPT");



                RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                roomno.setText(holder.mitem.getPrimises().getPremise_no());
                if(holder.mitem.getPayment_status().equals("offline")) {
                    status.setText("Settle Later");
                }
                else{
                    status.setText(holder.mitem.getPayment_status());

                }
                TextView dispacthtext=dialog.findViewById(R.id.dispacthtext);
                TextView dispachedatvaleue=dialog.findViewById(R.id.dispachedatvaleue);
                dispacthtext.setTypeface(holder.font);
                if(holder.mitem.getOrder_detail().getDispatched_at()!=null){
                    dispachedatvaleue.setText(getDate1(holder.mitem.getOrder_detail().getDispatched_at()));
                }
                else{
                    dispachedatvaleue.setText("-");
                }
                guests.setText(holder.mitem.getNo_of_guest());
                since.setText(getDate1(holder.mitem.getCreated_at()));
                LinearLayout signature=dialog.findViewById(R.id.signature);
                TextView name1=dialog.findViewById(R.id.name);
                signature.setVisibility(View.GONE);
                if(holder.mitem.getGuest()!=null){
                    name1.setText(holder.mitem.getGuest().getName());

                }
                else{
                    name1.setText("-");

                }



/*
                    signature.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(holder.mitem.getGuest_signature()!=null){

                            }
                            else{
                                final Dialog dialog1 = new Dialog(context);
                                // Include dialog.xml file

                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog1.setContentView(R.layout.signature_layout);
                                int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.48);
                                int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.50);
                                dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog1.getWindow().setLayout(width1, height1);

                                dialog1.setCancelable(false);
                                // Set dialog title
                                dialog1.setTitle("");
                                dialog1.show();
                                ImageView close=dialog1.findViewById(R.id.close);
                                ImageView image=dialog1.findViewById(R.id.image);
                                Picasso.with(getActivity()).load(holder.mitem.getGuest_signature()).into(image);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog1.dismiss();
                                    }
                                });

                            }
                        }
                    });
*/

                if (holder.mitem.getDescription() != null) {
                    orderinsdetails.setText(holder.mitem.getDescription());

                }
                else{
                    orderinsdetails.setText("-");

                }
                HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_menu_items(),context);
                orderitemsdetail.setAdapter(order_itemAdapterdetail);
                LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                guests.setText(holder.mitem.getNo_of_guest());
                    accepted.setText("-");


                    colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                    since.setTextColor(context.getResources().getColor(R.color.gray));
                    roomno.setTextColor(context.getResources().getColor(R.color.mehroon));


                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dispatchbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(getActivity())){
                            //    holder.stop();
                            setAccept(holder.mitem.getOrder_detail().getOrder_id());
                            dialog.dismiss();
                            //setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                        }else if(Network.isNetworkAvailable2(getActivity())){
                            //   holder.stop();

                            setAccept(holder.mitem.getOrder_detail().getOrder_id());
                            dialog.dismiss();
                            // setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);


                        }
                        else{

                        }
                    }
                });
            }
        });


        holder.setData(holder.mitem);
        synchronized (lstHolders) {
            lstHolders.add(holder);
        }
        holder.updateTimeRemaining(SystemClock.elapsedRealtime());
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
        public  TextView orderrecived;
        public  TextView orderstatus;
        public  TextView since;
        private TextView dispatch;
        private ImageView gift;
        private CheckBox complimentary;
        private TextView orderstatustext;
        private TextView orderstatuspayemet;
        private TextView instaruction;
        RecyclerView recyclerView;
        LinearLayout parent;
        LinearLayout colorimage;
        Dashboard.Data mitem;
        private TextView more;
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getContext(), alert);
        final Typeface font1;
        final Typeface font;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            roomno=itemView.findViewById(R.id.roomno);
            gift=itemView.findViewById(R.id.gift);
            complimentary=itemView.findViewById(R.id.complimentary);
            orderstatustext=itemView.findViewById(R.id.orderstatustext);
            orderstatuspayemet=itemView.findViewById(R.id.orderstatus);
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
        public void setData(Dashboard.Data item) {
            mitem = item;
            updateTimeRemaining(SystemClock.elapsedRealtime());



        }
        private void playRingtone(int newRingtoneType) {
            Ringtone newRingtone = RingtoneManager.getRingtone(
                    getActivity(), RingtoneManager
                            .getDefaultUri(newRingtoneType));
            if (null != r && r.isPlaying())
                r.stop();
            r = newRingtone;
            if (null != newRingtone) {
                r.play();
                postStopRingtoneMessage();
            }
        }
        public void stop() {
            if (r != null) {
                r.stop();
            }
        }
        // Stop any ringtones playing in 5 seconds
        private void postStopRingtoneMessage() {
            final Runnable r1 = new Runnable() {
                public void run() {
                    if (null != r) {
                        r.stop();
                    }
                }
            };
            mHandler1.postDelayed(r1, 5000);
        }
        public void updateTimeRemaining(long currentTime) {
            try {

                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                long newtime = mitem.getTimedifference() + currentTime;
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                int elapsedDays = (int) (newtime / daysInMilli);
                newtime = newtime % daysInMilli;

                int hours = (int) (newtime / hoursInMilli);
                newtime = newtime % hoursInMilli;

                int minutes = (int) (newtime / minutesInMilli);
                newtime = newtime % minutesInMilli;

                int seconds = (int) (newtime / secondsInMilli);
                if (elapsedDays == 0) {

                    if (hours == 00 || hours == 0) {
                        if(mitem.getStatus().equals("New Order")){
                            if(minutes==0||minutes==00){
                                if(seconds>20){
                                    roomno.startAnimation(anim);
                                    Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

                                    //  playRingtone(RingtoneManager.TYPE_NOTIFICATION);

                                      /*  if (r == null) {

                                            r = RingtoneManager.getRingtone(getContext(), alert);
                                           *//* if (r == null) {
                                                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                                                r = RingtoneManager.getRingtone(getContext(), alert);
                                            }*//*
                                        }
                                        if (r != null)
                                            r.play();
                                        else
                                            r.stop();*/
                                }
                            }
                            else{
                                roomno.startAnimation(anim);

                            }

                            orderrecived.setText(String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mehroon));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());

                        }else{

                            orderrecived.setText(String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mogiya));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());}






                    } else if (hours == 01 || hours == 1) {
                        if (mitem.getStatus().equals("New Order")) {
                            roomno.startAnimation(anim);

                            orderrecived.setText(String.valueOf(hours) + " hr : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mehroon));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());

                        } else {

                            orderrecived.setText(String.valueOf(hours) + " hr : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mogiya));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());
                        }



                    } else {
                        if (mitem.getStatus().equals("New Order")) {
                            roomno.startAnimation(anim);





                            orderrecived.setText(String.valueOf(hours) + " hrs : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mehroon));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());

                        } else {

                            orderrecived.setText(String.valueOf(hours) + " hrs : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mogiya));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());
                        }

                    }
                } else {
                    if (elapsedDays == 1 || elapsedDays == 01) {
                        if (mitem.getStatus().equals("New Order")) {
                            roomno.startAnimation(anim);





                            orderrecived.setText(String.valueOf(elapsedDays) + " Day");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mehroon));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());

                        } else {

                            orderrecived.setText(String.valueOf(elapsedDays) + " Day");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mogiya));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());
                        }

                    } else {
                        if (mitem.getStatus().equals("New Order")) {
                            roomno.startAnimation(anim);





                            orderrecived.setText(String.valueOf(elapsedDays) + " Days");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mehroon));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());

                        } else {

                            orderrecived.setText(String.valueOf(elapsedDays) + " Days");
                            orderstatus.setTextColor(context.getResources().getColor(R.color.mogiya));
                            orderrecived.setTextColor(Color.parseColor("#000000"));
                            orderstatus.setText(mitem.getStatus());
                        }

                    }
                }
            }catch (Exception e){

            }
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
        String statuspayemnt;
        String id;
        String status;
        String signature;
        String guestname;
        String dispactchedString;

        int position1;
        public Order_ItemAdapter1(String statuspayemnt, String room, String guests1, String ordercreated, String description, String orderaccepted, String id, int position1, String status, ArrayList<Dashboard.Order_menu_items> order_items, String name, Context context,String dispactchedString) {
            this.order_items = order_items;
            this.context = context;
            this.room=room;
            this.guests1=guests1;
            this.ordercreated=ordercreated;
            this.orderaccepted=orderaccepted;
            this.description=description;
            this.dispactchedString=dispactchedString;
            this.guestname=name;
            this.status=status;
            this.position1=position1;
            this.statuspayemnt=statuspayemnt;
            this.id=id;
        }

        @NonNull
        @Override
        public HomeAdapter.Order_ItemAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_itemvalue, parent, false);
            return new HomeAdapter.Order_ItemAdapter1.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapter1.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.name.setText(holder.mitem.getItem().getName());
            holder.quantity.setText(holder.mitem.getQuantity());


        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView quantity;
            Dashboard.Order_menu_items mitem;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                quantity=itemView.findViewById(R.id.quantity);
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
                        dialog.setContentView(R.layout.detail_popup_detail);
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
                        TextView statustext=dialog.findViewById(R.id.statustext);
                        statustext.setTypeface(font);
                        statustext.setVisibility(View.VISIBLE);
                        TextView status1=dialog.findViewById(R.id.status);
                        status1.setVisibility(View.VISIBLE);

                        TextView dispatchbutton=dialog.findViewById(R.id.dispatch);
                        TextView name=dialog.findViewById(R.id.name);
                        LinearLayout signature=dialog.findViewById(R.id.signature);
                        signature.setVisibility(View.GONE);
                        if(guestname!=null){
                            name.setText(guestname);

                        }
                        else{

                        }
                        TextView dispacthtext=dialog.findViewById(R.id.dispacthtext);
                        TextView dispachedatvaleue=dialog.findViewById(R.id.dispachedatvaleue);
                        dispacthtext.setTypeface(font);
                        if(dispactchedString!=null){
                            dispachedatvaleue.setText(getDate1(dispactchedString));
                        }
                        else{
                            dispachedatvaleue.setText("-");
                        }
                         /*   signature.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(holder.mitem.getGuest_signature()!=null){

                                    }
                                    else{
                                        final Dialog dialog1 = new Dialog(context);
                                        // Include dialog.xml file

                                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog1.setContentView(R.layout.signature_layout);
                                        int width1 = (int)(context.getResources().getDisplayMetrics().widthPixels*0.48);
                                        int height1 = (int)(context.getResources().getDisplayMetrics().heightPixels*0.50);
                                        dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                        dialog1.getWindow().setLayout(width1, height1);

                                        dialog1.setCancelable(false);
                                        // Set dialog title
                                        dialog1.setTitle("");
                                        dialog1.show();
                                        ImageView close=dialog1.findViewById(R.id.close);
                                        ImageView image=dialog1.findViewById(R.id.image);
                                        Picasso.with(getActivity()).load(.mitem.getGuest_signature()).into(image);
                                        close.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog1.dismiss();
                                            }
                                        });

                                    }
                                }
                            });*/
                            dispatchbutton.setText("ACCEPT");

                        if(statuspayemnt.equals("offline")){
                            status1.setText("Settle Later");

                        }else {
                            status1.setText(statuspayemnt);
                        }

                        RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                        roomno.setText(room);
                        guests.setText(guests1);
                        since.setText(getDate1(ordercreated));

                        if (description!= null) {
                            orderinsdetails.setText(description);

                        }
                        else{
                            orderinsdetails.setText("-");

                        }
                        HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(order_items,context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                        guests.setText(guests1);
                            accepted.setText("-");


                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mehroon));


                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        dispatchbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(Network.isNetworkAvailable(getActivity())){

                                    setAccept(id);
                                    dialog.dismiss();
                                    //setDispatch(id,position1);


                                }
                                else if(Network.isNetworkAvailable2(getActivity())){

                                    setAccept(id);
                                    dialog.dismiss();
                                    // setDispatch(id,position1);



                                }
                                else{

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
        public AddonsAdatpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.addonitems, parent, false);
            return new AddonsAdatpter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AddonsAdatpter.ViewHolder holder, int position) {
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
                    .inflate(R.layout.order_items, parent, false);
            return new HomeAdapter.Order_ItemAdapterdetail.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.Order_ItemAdapterdetail.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.name.setText(holder.mitem.getQuantity()+" X "+holder.mitem.getItem().getName());
            AddonsAdatpter addonsAdatpter=new AddonsAdatpter(holder.mitem.getOrder_addons(),context);
            holder.addonslist.setAdapter(addonsAdatpter);

        }

        @Override
        public int getItemCount() {
            return order_items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            Dashboard.Order_menu_items mitem;
            RecyclerView addonslist;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                addonslist=itemView.findViewById(R.id.addonslist);
                addonslist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
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
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Dashboard.Data> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Dashboard.Data s :queuelistnew) {
            //if the existing elements contains the search input
            if (s.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        homeAdapter.filterList(filterdNames);
    }
    public void tryAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        if (view != null)
            view.startAnimation(animation);
    }
    private void GetMenuitemenab(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcomplimentaryEnabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.enable_complimentary+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action login = response.body();
                    Toast.makeText(getActivity(), login.getMessage(), Toast.LENGTH_SHORT).show();
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
                    getActivity().finish();
                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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
    private void GetMenuitemdis(String id, final String menuid) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().getcomplimentaryDisabled("Bearer "+sessionManager.getACCESSTOKEN(), BuildConfig.disable_complimentary+id)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action login = response.body();
                    Toast.makeText(getActivity(), login.getMessage(), Toast.LENGTH_SHORT).show();
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
                    getActivity().finish();
                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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

}
