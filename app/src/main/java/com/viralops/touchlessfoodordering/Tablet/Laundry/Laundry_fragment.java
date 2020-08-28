package com.viralops.touchlessfoodordering.Tablet.Laundry;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.Mobile.Laundry.Laundry_Main_Mobile;
import com.viralops.touchlessfoodordering.Mobile.Spa.Spa_Mobile;
import com.viralops.touchlessfoodordering.Model.Action;
import com.viralops.touchlessfoodordering.Model.Dashboard;
import com.viralops.touchlessfoodordering.Model.Laundry_Dashboard;
import com.viralops.touchlessfoodordering.Model.Laundry_Header;
import com.viralops.touchlessfoodordering.Model.Order_Item;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Internetconnection;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Tablet.Spa.SpaMainActivitytablet;


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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Laundry_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Laundry_fragment extends Fragment {
    private Ringtone r;
    ShimmerRecyclerView shimmerRecyclerView;
    ArrayList<Laundry_Dashboard.Data> queuelist=new ArrayList<>();
    ArrayList<Order_Item> orderlist;
    SessionManager sessionManager;
    TextView norecord;
    AutoCompleteTextView searchView;
    HomeAdapter homeAdapter;
    Animation animation;
    String text="";
    private SearchView.OnQueryTextListener queryTextListener;

    public static Laundry_fragment newInstance() {
        return new Laundry_fragment();
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
        View view =inflater.inflate(R.layout.main_fragment, container, false);
        setHasOptionsMenu(true);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        homeAdapter=new HomeAdapter(getActivity(),queuelist);

        searchView =  view.findViewById(R.id.searchView);
        sessionManager=new SessionManager(getActivity());
        sessionManager.setIsINternet("false");

        shimmerRecyclerView=view.findViewById(R.id.recyclerview);
        shimmerRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        norecord=view.findViewById(R.id.norecord);

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
            LaundryMainActivity.startndard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (queuelist.size() != 0) {
                        try {
                            if(LaundryMainActivity.belllaundry.getVisibility()==v.VISIBLE) {
                                shimmerRecyclerView.smoothScrollToPosition(queuelist.size() - 1);
                                LaundryMainActivity.belllaundry.setVisibility(View.GONE);
                            }
                            else{

                            }
                        } catch (Exception e) {

                        }
                    }
                }
            });
        }
        catch (Exception e){

        }
        shimmerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(LaundryMainActivity.belllaundry.getVisibility()==View.GONE) {
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

        (RetrofitClientInstance.getApiService().Laundry_getOrders("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Laundry_Dashboard>() {
            @Override
            public void onResponse(@NonNull Call<Laundry_Dashboard> call, @NonNull Response<Laundry_Dashboard> response) {

                if(response.code()==201||response.code()==200){
                    Laundry_Dashboard  login = response.body();
                    queuelist=new ArrayList<>();
                    queuelist=login.getData();
                    if(queuelist.size()!=0){
                        homeAdapter=new HomeAdapter(getActivity(),queuelist);
                        shimmerRecyclerView.setAdapter(homeAdapter);
                        norecord.setVisibility(View.GONE);
                    }
                    else{
                        homeAdapter=new HomeAdapter(getActivity(),queuelist);
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
                    Laundry_Dashboard login = response.body();
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
            public void onFailure(@NonNull Call<Laundry_Dashboard> call, @NonNull Throwable t) {
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

        (RetrofitClientInstance.getApiService().Laundry_getHeader("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Laundry_Header>() {
            @Override
            public void onResponse(@NonNull Call<Laundry_Header> call, @NonNull Response<Laundry_Header> response) {

                if(response.code()==202||response.code()==200){
                    Laundry_Header  login = response.body();
                    LaundryMainActivity.standtotal.setText(String.valueOf(login.getData().getStandard_order()));
                    LaundryMainActivity.totalorderslaundry.setText(String.valueOf(login.getData().getToday_order()));
                    LaundryMainActivity.totalexpress.setText(String.valueOf(login.getData().getExpress_order()));


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
                    Action  login = response.body();
                    Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                    searchView.setText("");
                        LaundryMainActivity.belllaundry.setVisibility(View.GONE);

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
                    Action  login = response.body();
                    //  Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                    /*if(Network.isNetworkAvailable(getActivity())){
                        GetMenu();
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetMenu();
                    }
                    else{

                    }*/

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
    private void setClear(String id, final int position,String text1,String reason) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Laundry_OrderCleared(BuildConfig.laundry_order_clear+id,"Bearer "+sessionManager.getACCESSTOKEN(),text1,reason)).enqueue(new Callback<Action>() {
            @Override
            public void onResponse(@NonNull Call<Action> call, @NonNull Response<Action> response) {

                if(response.code()==202||response.code()==200){
                    Action  login = response.body();
                    Toast.makeText(getActivity(),login.getMessage(),Toast.LENGTH_SHORT).show();
                    text="";
                    searchView.setText("");


                  /*  queuelist.remove(position);
                    // homeAdapter.notifyItemRemoved(getAdapterPosition());
                    homeAdapter.notifyDataSetChanged();*/
                  if(Network.isNetworkAvailable(getActivity())){
                      GetMenu();
                  }
                  else if(Network.isNetworkAvailable2(getActivity())){
                      GetMenu();

                  }else{

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

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
        ArrayList<Laundry_Dashboard.Data> homeViewModels;
        Context context;





        public HomeAdapter(Context context,ArrayList<Laundry_Dashboard.Data> homeViewModels) {
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
            if(holder.mitem.getStatus().equals("new_order")){
                holder.guests.setText("New Order");

                holder.dispatch.setText("ACCEPT");
                holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
                holder.since.setTextColor(context.getResources().getColor(R.color.gray));
                holder.roomno.setTextColor(context.getResources().getColor(R.color.mehroon));
                holder.guests.setTextColor(context.getResources().getColor(R.color.mehroon));

            }
            else{
                holder.dispatch.setText("TAKE ACTION");
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

                         dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                         //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.actionpopup);
                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.45);
                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                       // dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


                        //dialog.getWindow().setLayout(width1, height1);


                        dialog.setCancelable(false);
                        // Set dialog title
                        dialog.setTitle("");
                        final RelativeLayout myview =  dialog.findViewById(R.id.viewstudff);


                        final View activityRootView = dialog.findViewById(R.id.viewstudff);
                        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                                if (heightDiff > dpToPx(getActivity(), 200)) { // if more than 200 dp, it's probably a keyboard...
                                    // ... do something here

                                }
                            }
                        });
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
                              //  Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
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
                    name.setText(capitailizeWord(holder.mitem.getGuest().getName()));
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

                        orderinsdetails.setText(getDatenew(holder.mitem.getOrder_detail().getRequested_pickup_at()));


                    HomeAdapter.Order_ItemAdapterdetail order_itemAdapterdetail=new HomeAdapter.Order_ItemAdapterdetail(holder.mitem.getOrder_laundry_items(),context);
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
                                final Dialog dialog1= new Dialog(getActivity());
                                // Include dialog.xml file

                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                 dialog1.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                                 //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog1.setContentView(R.layout.actionpopup);
                                int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.45);
                                int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                                //dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


                               // dialog1.getWindow().setLayout(width1, height1);


                                dialog1.setCancelable(false);
                                // Set dialog title
                                dialog1.setTitle("");
                                final RelativeLayout myview =  dialog1.findViewById(R.id.viewstudff);


                                final View activityRootView = dialog1.findViewById(R.id.viewstudff);
                                activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                                        if (heightDiff > dpToPx(getActivity(), 200)) { // if more than 200 dp, it's probably a keyboard...
                                            // ... do something here

                                        }
                                    }
                                });

                                dialog1.show();
                                // TextView textView=dialog.findViewById(R.id.text) ;
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

                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,editText.getText().toString());
                                                        dialog.dismiss();
                                                        dialog1.dismiss();

                                                    }
                                                    else if(Network.isNetworkAvailable2(getActivity())){
                                                        setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                        setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,editText.getText().toString());

                                                        dialog.dismiss();
                                                        dialog1.dismiss();

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
                                                    dialog1.dismiss();

                                                }
                                                else if(Network.isNetworkAvailable2(getActivity())){
                                                    setDispatch(holder.mitem.getOrder_detail().getOrder_id(),position);

                                                    setClear(holder.mitem.getOrder_detail().getOrder_id(),position,text,"");

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
            });



        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }
        public void filterList(ArrayList<Laundry_Dashboard.Data> filterdNames) {
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
            Laundry_Dashboard.Data mitem;
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
            ArrayList<Laundry_Dashboard.Order_laundry_items> order_items;
            Context context;
            ArrayList<Dashboard.order_menu_addons> menuItems;
            String room;
            String guests1;
            String ordercreated;
            String description;
            String orderaccepted;
            String guestname;
            String id;
            String status;
            int position1;
            public Order_ItemAdapter1(String room,String guests1,String ordercreated,String description,String orderaccepted,String id,int position1,String status,ArrayList<Laundry_Dashboard.Order_laundry_items> order_items,String guestname, Context context) {
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

                if(holder.mitem.getIs_express_delivery().equals("1")){
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
                Laundry_Dashboard.Order_laundry_items mitem;

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
                            name.setText(capitailizeWord(guestname));
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
                                        final Dialog dialog1 = new Dialog(getActivity());
                                        // Include dialog.xml file

                                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                         dialog1.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                                        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog1.setContentView(R.layout.actionpopup);
                                        int width1 = (int)(getResources().getDisplayMetrics().widthPixels*0.45);
                                        int height1 = (int)(getResources().getDisplayMetrics().heightPixels*0.99);
                                       // dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


                                       // dialog1.getWindow().setLayout(width1, height1);


                                        dialog1.setCancelable(false);
                                        // Set dialog title
                                        dialog1.setTitle("");
                                        final RelativeLayout myview =  dialog1.findViewById(R.id.viewstudff);


                                        final View activityRootView = dialog1.findViewById(R.id.viewstudff);
                                        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                            @Override
                                            public void onGlobalLayout() {
                                                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                                                if (heightDiff > dpToPx(getActivity(), 200)) { // if more than 200 dp, it's probably a keyboard...
                                                    // ... do something here

                                                }
                                            }
                                        });

                                        dialog1.show();
                                        // TextView textView=dialog.findViewById(R.id.text) ;
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
                                             //  Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
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
                                                        }
                                                        else{
                                                            if(Network.isNetworkAvailable(getActivity())){
                                                                setDispatch(id,position1);

                                                                setClear(id,position1,text,editText.getText().toString());
                                                                dialog1.dismiss();
                                                                dialog.dismiss();

                                                            }
                                                            else if(Network.isNetworkAvailable2(getActivity())){

                                                                setDispatch(id,position1);

                                                                setClear(id,position1,text,editText.getText().toString());
                                                                dialog1.dismiss();
                                                                dialog.dismiss();

                                                            }
                                                            else{

                                                            }
                                                        }

                                                    }
                                                    else{
                                                        if(Network.isNetworkAvailable(getActivity())){
                                                            setDispatch(id,position1);

                                                            setClear(id,position1,text,"");
                                                            dialog1.dismiss();
                                                            dialog.dismiss();

                                                        }
                                                        else if(Network.isNetworkAvailable2(getActivity())){

                                                            setDispatch(id,position1);

                                                            setClear(id,position1,text,"");
                                                            dialog1.dismiss();
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
            ArrayList<Laundry_Dashboard.Order_laundry_items> order_items;
            Context context;

            public Order_ItemAdapterdetail(ArrayList<Laundry_Dashboard.Order_laundry_items> order_items, Context context) {
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
                if(holder.mitem.getIs_express_delivery().equals("1")){
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
                Laundry_Dashboard.Order_laundry_items mitem;
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
        ArrayList<Laundry_Dashboard.Data> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Laundry_Dashboard.Data s :queuelist) {
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
    public  float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
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

}
