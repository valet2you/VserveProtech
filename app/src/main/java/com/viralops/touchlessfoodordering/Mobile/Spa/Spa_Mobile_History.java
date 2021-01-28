package com.viralops.touchlessfoodordering.Mobile.Spa;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.Model.Header;
import com.viralops.touchlessfoodordering.Model.Spa_dashboard;
import com.viralops.touchlessfoodordering.R;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Spa_Mobile_History extends Fragment {

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
    ImageView filter;

    ArrayList<Spa_dashboard.Data> queuelist=new ArrayList<>();
    public static Spa_Mobile_History newInstance() {
        return new Spa_Mobile_History();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.assciate_history, container, false);
        final Typeface font = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Regular.ttf");
        Typeface font1 = Typeface.createFromAsset(
                getActivity().getAssets(),
                "font/Roboto-Thin.ttf");
        searchView =  view.findViewById(R.id.searchView);
        filter=view.findViewById(R.id.filter);

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        sessionManager=new SessionManager(getActivity());
        norecord=view.findViewById(R.id.norecord);
        standardsrvice=view.findViewById(R.id.standardsrvice);
        standardsrvice.setTypeface(font);
        standardsrvice.setVisibility(View.GONE);
        express=view.findViewById(R.id.express);
        express.setTypeface(font);
        express.setVisibility(View.GONE);
        totallaudry=view.findViewById(R.id.totallaudry);
        totallaudry.setTypeface(font);

        standtotal=view.findViewById(R.id.standtotal);
        standtotal.setTypeface(font);
        standtotal.setVisibility(View.GONE);
        totalexpress=view.findViewById(R.id.totalexpress);
        totalexpress.setTypeface(font);
        totalexpress.setVisibility(View.GONE);
        totalorderslaundry=view.findViewById(R.id.totalorderslaundry);
        totalorderslaundry.setTypeface(font);

        if (Network.isNetworkAvailable2(getActivity())) {

            GetMenu("today");
        }
        else if(Network.isNetworkAvailable(getActivity())){
            GetMenu("today");
        }
        else{

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

        registerForContextMenu(filter);
        filter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().openContextMenu(v);
            }
        });

        return view;
    }



    private void GetMenu(String show) {
        // display a progress dialog
        recyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Spa_getHistory("Bearer "+sessionManager.getACCESSTOKEN(),show)).enqueue(new Callback<Spa_dashboard>() {
            @Override
            public void onResponse(@NonNull Call<Spa_dashboard> call, @NonNull Response<Spa_dashboard> response) {

                if(response.code()==201||response.code()==200){
                    Spa_dashboard  login = response.body();
                    queuelist=new ArrayList<>();
                    queuelist=login.getData();
                    if(queuelist.size()!=0){
                        homeAdapter=new HomeAdapter(getActivity(),queuelist);
                        recyclerView.setAdapter(homeAdapter);
                        norecord.setVisibility(View.GONE);
                    }
                    else{
                        homeAdapter=new HomeAdapter(getActivity(),queuelist);
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
                    Header login = response.body();
                    Spa_Mobile.newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                    totalorderslaundry.setText(String.valueOf(login.getData().getAccepted_order()+login.getData().getNew_order()+login.getData().getDispatched_order()+login.getData().getCleared_order()));
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
                    .inflate(R.layout.spahistory_item_new, parent, false);

            return new HomeAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
            holder.mitem=homeViewModels.get(position);
            if(holder.mitem.getPrimises()!=null) {
                holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
            }
            holder.guests.setText(holder.mitem.getNo_of_guest());
            if(holder.mitem.getOrder_detail()!=null){
                holder.orderid.setText(holder.mitem.getOrder_detail().getOrder_id());

            }

            // holder.orderrecived.setText(holder.mitem.getOrderreceived())

            holder.since.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));

                holder.orderrecived.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));



                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mogiya));






            holder.parent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.mitem.getGuest()!=null) {
                        if (holder.mitem.getGuest().getName().equals("Guest")) {

                            final Dialog dialog = new Dialog(context);
                            // Include dialog.xml file

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.spahistory_detailwithourguest);
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
                            TextView name = dialog.findViewById(R.id.name);
                            TextView guests = dialog.findViewById(R.id.guest);
                            TextView since = dialog.findViewById(R.id.since);
                            TextView orderitemsdetailtext = dialog.findViewById(R.id.orderitemsdetailtext);
                            orderitemsdetailtext.setTypeface(holder.font);
                            TextView orderins = dialog.findViewById(R.id.orderins);
                            orderins.setTypeface(holder.font);
                            TextView orderinsdetails = dialog.findViewById(R.id.orderinsdetails);
                            TextView acceptedat = dialog.findViewById(R.id.accepttext);
                            acceptedat.setTypeface(holder.font);
                            TextView accepted = dialog.findViewById(R.id.accepted);
                            TextView distpatchtext = dialog.findViewById(R.id.distpatchtext);
                            TextView reason = dialog.findViewById(R.id.other);
                            TextView statustext = dialog.findViewById(R.id.statustext);
                            TextView status = dialog.findViewById(R.id.status);
                            distpatchtext.setTypeface(holder.font);
                            statustext.setTypeface(holder.font);
                            TextView dispatcg = dialog.findViewById(R.id.dispactch);
                            RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                            orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            roomno.setText(holder.mitem.getPrimises().getPremise_no());
                            guests.setText(holder.mitem.getNo_of_guest());
                            since.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
                            name.setText(holder.mitem.getGuest().getName());

                            orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));


                            Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.mitem.getOrder_spa_items(), context);
                            orderitemsdetail.setAdapter(order_itemAdapterdetail);
                            LinearLayout colorimage = dialog.findViewById(R.id.colorimage);

                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));
                            dispatcg.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));


                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
                            if (holder.mitem.getOrder_stage() != null) {
                                statustext.setVisibility(View.VISIBLE);
                                status.setVisibility(View.VISIBLE);
                                if (holder.mitem.getOrder_stage().getStage().equals("other")) {
                                    reason.setVisibility(View.VISIBLE);
                                    status.setText(holder.mitem.getOrder_stage().getStage());
                                    reason.setText(holder.mitem.getOrder_stage().getStage_text());
                                } else {
                                    reason.setVisibility(View.GONE);
                                    status.setText(holder.mitem.getOrder_stage().getStage());
                                }
                            } else {
                                statustext.setVisibility(View.GONE);
                                status.setVisibility(View.GONE);
                                reason.setVisibility(View.GONE);
                            }


                            back.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        } else {

                            final Dialog dialog = new Dialog(context);
                            // Include dialog.xml file

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.spahistory_detail);
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
                            TextView name = dialog.findViewById(R.id.name);
                            TextView guests = dialog.findViewById(R.id.guest);
                            TextView since = dialog.findViewById(R.id.since);
                            TextView orderitemsdetailtext = dialog.findViewById(R.id.orderitemsdetailtext);
                            orderitemsdetailtext.setTypeface(holder.font);
                            TextView orderins = dialog.findViewById(R.id.orderins);
                            orderins.setTypeface(holder.font);
                            TextView orderinsdetails = dialog.findViewById(R.id.orderinsdetails);
                            TextView acceptedat = dialog.findViewById(R.id.accepttext);
                            acceptedat.setTypeface(holder.font);
                            TextView accepted = dialog.findViewById(R.id.accepted);
                            TextView distpatchtext = dialog.findViewById(R.id.distpatchtext);
                            TextView reason = dialog.findViewById(R.id.other);
                            TextView statustext = dialog.findViewById(R.id.statustext);
                            TextView status = dialog.findViewById(R.id.status);
                            distpatchtext.setTypeface(holder.font);
                            statustext.setTypeface(holder.font);
                            TextView dispatcg = dialog.findViewById(R.id.dispactch);
                            RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                            orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            roomno.setText(holder.mitem.getPrimises().getPremise_no());
                            guests.setText(holder.mitem.getNo_of_guest());
                            since.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
                            name.setText(holder.mitem.getGuest().getName());

                            orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));


                            Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.mitem.getOrder_spa_items(), context);
                            orderitemsdetail.setAdapter(order_itemAdapterdetail);
                            LinearLayout colorimage = dialog.findViewById(R.id.colorimage);

                            accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));
                            dispatcg.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));
                            if (holder.mitem.getOrder_stage() != null) {
                                statustext.setVisibility(View.VISIBLE);
                                status.setVisibility(View.VISIBLE);
                                if (holder.mitem.getOrder_stage().getStage().equals("other")) {
                                    reason.setVisibility(View.VISIBLE);
                                    status.setText(holder.mitem.getOrder_stage().getStage());
                                    reason.setText(holder.mitem.getOrder_stage().getStage_text());
                                } else {
                                    reason.setVisibility(View.GONE);
                                    status.setText(holder.mitem.getOrder_stage().getStage());
                                }
                            } else {
                                statustext.setVisibility(View.GONE);
                                status.setVisibility(View.GONE);
                                reason.setVisibility(View.GONE);
                            }
                            if (holder.mitem.getOrder_stage() != null) {
                                statustext.setVisibility(View.VISIBLE);
                                status.setVisibility(View.VISIBLE);
                                if (holder.mitem.getOrder_stage().getStage().equals("other")) {
                                    reason.setVisibility(View.VISIBLE);
                                    status.setText(holder.mitem.getOrder_stage().getStage().replaceAll("_", " "));
                                    reason.setText(holder.mitem.getOrder_stage().getStage_text());
                                } else {
                                    reason.setVisibility(View.GONE);
                                    status.setText(holder.mitem.getOrder_stage().getStage().replaceAll("_", " "));
                                }
                            } else {
                                statustext.setVisibility(View.GONE);
                                status.setVisibility(View.GONE);
                                reason.setVisibility(View.GONE);
                            }


                            colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                            since.setTextColor(context.getResources().getColor(R.color.gray));
                            roomno.setTextColor(context.getResources().getColor(R.color.mogiya));


                            back.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                    else
                    {

                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.spahistory_detailwithourguest);
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
                        TextView name = dialog.findViewById(R.id.name);
                        TextView guests = dialog.findViewById(R.id.guest);
                        TextView since = dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext = dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins = dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails = dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat = dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted = dialog.findViewById(R.id.accepted);
                        TextView distpatchtext = dialog.findViewById(R.id.distpatchtext);
                        TextView reason = dialog.findViewById(R.id.other);
                        TextView statustext = dialog.findViewById(R.id.statustext);
                        TextView status = dialog.findViewById(R.id.status);
                        distpatchtext.setTypeface(holder.font);
                        statustext.setTypeface(holder.font);
                        TextView dispatcg = dialog.findViewById(R.id.dispactch);
                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        guests.setText(holder.mitem.getNo_of_guest());
                        since.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
                       // name.setText(holder.mitem.getGuest().getName());

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequest_schedule_at()));


                        Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.mitem.getOrder_spa_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);

                        accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));
                        dispatcg.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));


                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
                        if (holder.mitem.getOrder_stage() != null) {
                            statustext.setVisibility(View.VISIBLE);
                            status.setVisibility(View.VISIBLE);
                            if (holder.mitem.getOrder_stage().getStage().equals("other")) {
                                reason.setVisibility(View.VISIBLE);
                                status.setText(holder.mitem.getOrder_stage().getStage());
                                reason.setText(holder.mitem.getOrder_stage().getStage_text());
                            } else {
                                reason.setVisibility(View.GONE);
                                status.setText(holder.mitem.getOrder_stage().getStage());
                            }
                        } else {
                            statustext.setVisibility(View.GONE);
                            status.setVisibility(View.GONE);
                            reason.setVisibility(View.GONE);
                        }


                        back.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
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
        public void filterList(ArrayList<Spa_dashboard.Data> filterdNames) {
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
            private TextView orderid;
            private TextView orderidtext;
            LinearLayout parent;
            LinearLayout colorimage;
            Spa_dashboard.Data mitem;
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
                orderid=itemView.findViewById(R.id.orderid);
                orderidtext=itemView.findViewById(R.id.ordertext);
                parent=itemView.findViewById(R.id.parent);
                //more=itemView.findViewById(R.id.more);


                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");
                dispatch.setTypeface(font);
                orderidtext.setTypeface(font);

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
             //   more.setTypeface(font1);
               // since.setTypeface(font);


            }


        }

        public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<Order_ItemAdapterdetail.ViewHolder>{
            ArrayList<Spa_dashboard.Order_spa_items> order_items;
            Context context;

            public Order_ItemAdapterdetail(ArrayList<Spa_dashboard.Order_spa_items> order_items, Context context) {
                this.order_items = order_items;
                this.context = context;
            }

            @NonNull
            @Override
            public Order_ItemAdapterdetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.spaorder_items, parent, false);
                return new Order_ItemAdapterdetail.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull Order_ItemAdapterdetail.ViewHolder holder, int position) {
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
                Spa_dashboard.Order_spa_items mitem;
                TextView categorylist;

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
            if (s.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())||s.getOrder_detail().getOrder_id().contains(text)) {
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
