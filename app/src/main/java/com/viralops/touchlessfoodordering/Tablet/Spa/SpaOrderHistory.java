package com.viralops.touchlessfoodordering.Tablet.Spa;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.Model.Spa_dashboard;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Internetconnection;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;


import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpaOrderHistory extends Fragment {
    ShimmerRecyclerView recyclerview;
    TextView roomno;
    TextView guest;
    TextView orderreceivedtext;
    TextView deliveredat;
    TextView orderacceptedtext;
    TextView norecord;
    ArrayList<Spa_dashboard.Data> orderslist;
    SessionManager sessionManager;
    AutoCompleteTextView searchView;
    SpaHistoryAdapter homeAdapter;
    ImageView filter;
    TextView reason;

    public static SpaOrderHistory newInstance() {
        return new SpaOrderHistory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.spa_history_fragment, container, false);
        searchView =  view.findViewById(R.id.searchView);

        sessionManager=new SessionManager(getActivity());
        sessionManager.setIsINternet("false");

        recyclerview=view.findViewById(R.id.sidelist);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        filter=view.findViewById(R.id.filter);
        roomno=view.findViewById(R.id.roomno);
        guest=view.findViewById(R.id.guest);
        orderreceivedtext=view.findViewById(R.id.orderreceivedtext);
        deliveredat=view.findViewById(R.id.deliveredat);
        orderacceptedtext=view.findViewById(R.id.orderacceptedtext);
        norecord=view.findViewById(R.id.norecord);
        reason=view.findViewById(R.id.reason);


        final Typeface font = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Regular.ttf");
        roomno.setTypeface(font);
        guest.setTypeface(font);
        orderreceivedtext.setTypeface(font);
        deliveredat.setTypeface(font);
        reason.setTypeface(font);
        orderacceptedtext.setTypeface(font);

        Typeface font1 = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/verdana.ttf");
        orderslist=new ArrayList<>();
        homeAdapter=new SpaHistoryAdapter(getActivity(),orderslist);

        if(Network.isNetworkAvailable(getActivity())){
            GetMenu("today");
        }
        else if(Network.isNetworkAvailable2(getActivity())){
            GetMenu("today");
        }
        else {
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
     // registerForContextMenu(filter);
/*
       filter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getActivity().openContextMenu(v);
           }
       });
*/
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                // Include dialog.xml file

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.contextmenu);
                int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
                int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
                dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                dialog.getWindow().setLayout(width1, height1);

                dialog.setCancelable(true);
                // Set dialog title
                dialog.setTitle("");
                dialog.show();
                TextView today=dialog.findViewById(R.id.today);
                TextView week=dialog.findViewById(R.id.weekly);
                TextView month=dialog.findViewById(R.id.month);
                today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(getActivity())){
                            GetMenu("today");
                            dialog.dismiss();

                        }
                        else if(Network.isNetworkAvailable2(getActivity())){
                            GetMenu("today");
                            dialog.dismiss();

                        }
                        else {
                        }
                    }
                });  week.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(getActivity())){
                            GetMenu("weekly");
                            dialog.dismiss();

                        }
                        else if(Network.isNetworkAvailable2(getActivity())){
                            GetMenu("weekly");
                            dialog.dismiss();

                        }
                        else {
                        }
                    }
                });  month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Network.isNetworkAvailable(getActivity())){
                            GetMenu("monthly");
                            dialog.dismiss();

                        }
                        else if(Network.isNetworkAvailable2(getActivity())){
                            GetMenu("monthly");
                            dialog.dismiss();

                        }
                        else {
                        }
                    }
                });

            }
        });
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  drawer_adapter=new ReleaseCarByToken.MyItemRecyclerViewAdapter(dashboards,getActivity());
        //recyclerView.setAdapter(drawer_adapter);
    }



    private void GetMenu(String show) {
        // display a progress dialog
        recyclerview.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Spa_getHistory("Bearer "+sessionManager.getACCESSTOKEN(),show)).enqueue(new Callback<Spa_dashboard>() {
            @Override
            public void onResponse(@NonNull Call<Spa_dashboard> call, @NonNull Response<Spa_dashboard> response) {

                if(response.code()==201||response.code()==200){
                    Spa_dashboard login = response.body();
                    orderslist=new ArrayList<>();
                    orderslist=login.getData();
                    if(orderslist.size()!=0){

                         homeAdapter=new SpaHistoryAdapter(getActivity(),orderslist);
                        recyclerview.setAdapter(homeAdapter);
                        norecord.setVisibility(View.GONE);
                    }
                    else{
                         homeAdapter=new SpaHistoryAdapter(getActivity(),orderslist);
                        recyclerview.setAdapter(homeAdapter);
                        norecord.setVisibility(View.VISIBLE);
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

                recyclerview.hideShimmer();



            }

            @Override
            public void onFailure(@NonNull Call<Spa_dashboard> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                recyclerview.hideShimmer();

            }
        });

    }

    private void filter(String text) {
        try {
            //new array list that will hold the filtered data
            ArrayList<Spa_dashboard.Data> filterdNames = new ArrayList<>();

            //looping through existing elements
            for (Spa_dashboard.Data s : orderslist) {
                //if the existing elements contains the search input
                if (s.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }

            //calling a method of the adapter class and passing the filtered list
            homeAdapter.filterList(filterdNames);
        }catch (Exception e){

        }
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        menu.setHeaderTitle("");

        menu.add(1,1,1,"Today");
        menu.add(1,2,2,"Last Week");
        menu.add(1,3,3,"Last Month");


    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case 1:
                // your first action code
                    if(Network.isNetworkAvailable(getActivity())){
                        GetMenu("today");
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenu("today");
                    }
                    else {
                    }

                return true;

                case 2:
                // your second action code
                    if(Network.isNetworkAvailable(getActivity())){
                        GetMenu("weekly");
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenu("weekly");
                    }
                    else {
                    }
                return true;
                case 3:
                    if(Network.isNetworkAvailable(getActivity())){
                        GetMenu("monthly");
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenu("monthly");
                    }
                    else {
                    }
                // your second action code
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
