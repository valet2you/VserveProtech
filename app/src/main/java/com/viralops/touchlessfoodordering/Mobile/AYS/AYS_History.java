package com.viralops.touchlessfoodordering.Mobile.AYS;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.Mobile.IRD.Associate_History;
import com.viralops.touchlessfoodordering.Model.ConnectHistory;
import com.viralops.touchlessfoodordering.Model.Connect_Header;
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

public class AYS_History extends Fragment {

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
    ImageView filter;
    AutoCompleteTextView searchView;
    ArrayList<ConnectHistory.Data> queuelist=new ArrayList<>();
    public static AYS_History newInstance() {
        return new AYS_History();
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

        (RetrofitClientInstance.getApiService().Connect_getHistory("Bearer "+sessionManager.getACCESSTOKEN(),show)).enqueue(new Callback<ConnectHistory>() {
            @Override
            public void onResponse(@NonNull Call<ConnectHistory> call, @NonNull Response<ConnectHistory> response) {

                if(response.code()==201||response.code()==200){
                    ConnectHistory login = response.body();
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




                }
                else if(response.code()==401){
                    ConnectHistory login = response.body();
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
            public void onFailure(@NonNull Call<ConnectHistory> call, @NonNull Throwable t) {
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

        (RetrofitClientInstance.getApiService().Connect_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Connect_Header>() {
            @Override
            public void onResponse(@NonNull Call<Connect_Header> call, @NonNull Response<Connect_Header> response) {

                if(response.code()==202||response.code()==200){
                    Connect_Header  login = response.body();
                    AYSMain_Mobile.newcount.setText("( "+String.valueOf(login.getData().getNew_order())+" )");
                    totalorderslaundry.setText(String.valueOf(login.getData().getToday_order()));
                    AYSMain_Mobile.newcountevent.setText("( "+String.valueOf(login.getData().getAccepted_order())+" )");

                }
                else if(response.code()==401){
                    Connect_Header login = response.body();
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
            public void onFailure(@NonNull Call<Connect_Header> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
        ArrayList<ConnectHistory.Data> homeViewModels;
        Context context;





        public HomeAdapter(Context context,ArrayList<ConnectHistory.Data> homeViewModels) {
            this.context=context;
            this.homeViewModels=homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item_new, parent, false);

            return new HomeAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
            holder.mitem=homeViewModels.get(position);
            if(holder.mitem.getPrimises()!=null) {
                holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
            }
            holder.guests.setText(holder.mitem.getNo_of_guest());


            // holder.orderrecived.setText(holder.mitem.getOrderreceived())

            holder.since.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));

                holder.orderrecived.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));



                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mogiya));






            holder.parent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.mitem.getGuest().getName().equals("Guest")){
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.history_detail_itemwithoutguest);
                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
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
                        TextView name=dialog.findViewById(R.id.name);
                        TextView since=dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins=dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat=dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView distpatchtext=dialog.findViewById(R.id.distpatchtext);
                        distpatchtext.setTypeface(holder.font);
                        TextView dispatcg=dialog.findViewById(R.id.dispactch);
                        RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
                        guests.setText(holder.mitem.getNo_of_guest());
                        name.setText(holder.mitem.getGuest().getName());
                        if (holder.mitem.getDescription() != null) {
                            orderinsdetails.setText(holder.mitem.getDescription());

                        }
                        else{
                            orderinsdetails.setText("None");

                        }
                        Order_ItemAdapterdetail order_itemAdapterdetail=new Order_ItemAdapterdetail(holder.mitem.getOrder_connect_items(),context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage=dialog.findViewById(R.id.colorimage);

                        accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));
                        dispatcg.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));




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
                    else{
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.history_detail_item);
                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
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
                        TextView name=dialog.findViewById(R.id.name);
                        TextView since=dialog.findViewById(R.id.since);
                        TextView orderitemsdetailtext=dialog.findViewById(R.id.orderitemsdetailtext);
                        orderitemsdetailtext.setTypeface(holder.font);
                        TextView orderins=dialog.findViewById(R.id.orderins);
                        orderins.setTypeface(holder.font);
                        TextView orderinsdetails=dialog.findViewById(R.id.orderinsdetails);
                        TextView acceptedat=dialog.findViewById(R.id.accepttext);
                        acceptedat.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView distpatchtext=dialog.findViewById(R.id.distpatchtext);
                        distpatchtext.setTypeface(holder.font);
                        TextView dispatcg=dialog.findViewById(R.id.dispactch);
                        RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                        roomno.setText(holder.mitem.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
                        guests.setText(holder.mitem.getNo_of_guest());
                        name.setText(holder.mitem.getGuest().getName());
                        if (holder.mitem.getDescription() != null) {
                            orderinsdetails.setText(holder.mitem.getDescription());

                        }
                        else{
                            orderinsdetails.setText("None");

                        }
                        Order_ItemAdapterdetail order_itemAdapterdetail=new Order_ItemAdapterdetail(holder.mitem.getOrder_connect_items(),context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage=dialog.findViewById(R.id.colorimage);

                        accepted.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));
                        dispatcg.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));




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
            });



        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }
        public void filterList(ArrayList<ConnectHistory.Data> filterdNames) {
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
           // private TextView orderidtext;
            LinearLayout parent;
            LinearLayout colorimage;
            ConnectHistory.Data mitem;
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
               // orderidtext=itemView.findViewById(R.id.ordertext);
                parent=itemView.findViewById(R.id.parent);
                //more=itemView.findViewById(R.id.more);


                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");
                dispatch.setTypeface(font);
             //   orderidtext.setTypeface(font);

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
             //   more.setTypeface(font1);
               // since.setTypeface(font);


            }


        }

/*
        public class AddonsAdatpter extends  RecyclerView.Adapter<AddonsAdatpter.ViewHolder>{
            ArrayList<OrderHistory.Order_addons> order_items;
            Context context;

            public AddonsAdatpter(ArrayList<OrderHistory.Order_addons> order_items, Context context) {
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
                OrderHistory.Order_addons mitem;

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
*/
        public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<Order_ItemAdapterdetail.ViewHolder>{
            ArrayList<ConnectHistory.Order_connect_items> order_items;
            Context context;

            public Order_ItemAdapterdetail(ArrayList<ConnectHistory.Order_connect_items> order_items, Context context) {
                this.order_items = order_items;
                this.context = context;
            }

            @NonNull
            @Override
            public Order_ItemAdapterdetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_items, parent, false);
                return new Order_ItemAdapterdetail.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull Order_ItemAdapterdetail.ViewHolder holder, int position) {
                holder.mitem=order_items.get(position);
                if(holder.mitem.getItem().getType().equals("simple")){
                    holder.name.setText(holder.mitem.getItem().getName());
                }
                else if(holder.mitem.getItem().getType().equals("with-count")){
                    holder.name.setText(holder.mitem.getQuantity()+" X "+holder.mitem.getItem().getName());

                }
                else {
                    holder.name.setText(holder.mitem.getItem().getName());
                }        }

            @Override
            public int getItemCount() {
                return order_items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView name;
                ConnectHistory.Order_connect_items mitem;
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
        ArrayList<ConnectHistory.Data> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ConnectHistory.Data s :queuelist) {
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
