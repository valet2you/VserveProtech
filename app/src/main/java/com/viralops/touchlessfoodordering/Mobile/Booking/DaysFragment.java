package com.viralops.touchlessfoodordering.Mobile.Booking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.Model.GetBookingServices;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;

import java.net.Inet4Address;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaysFragment extends Fragment {
    String title;
    int position;
    ArrayList<GetBookingServices.Data> getbookingdtata=new ArrayList<>();
    ArrayList<GetBookingServices.Booking_service_opening_days> getbookingdtata1=new ArrayList<>();
    SessionManager sessionManager;
    SessionManagerFCM sessionManagerFCM;
    ShimmerRecyclerView shimmerRecyclerView;
    HomeAdapter homeAdapter;
    TextView norecord;
    TextView count;
    TextView refresh;
    String type;
    ArrayList<Booking.Data> bookingArrayList = new ArrayList<>();
    ArrayList<Booking.Data> bookingArrayListnew = new ArrayList<>();
    public static DaysFragment newInstance(String title1, int postition,String type) {
        DaysFragment fragment = new DaysFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title1);
        bundle.putInt("page", postition);
        bundle.putString("type",type);
        fragment.setArguments(bundle);


        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());

        title = getArguments().getString("title");
        position = getArguments().getInt("page");
        type=getArguments().getString("type");
        //sessionManager.setFilterValue(title);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        sessionManager = new SessionManager(getActivity());
        sessionManagerFCM = new SessionManagerFCM(getActivity());
        shimmerRecyclerView = view.findViewById(R.id.recycler);
        shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        norecord = view.findViewById(R.id.norecord);
        homeAdapter=new HomeAdapter(getActivity(),getbookingdtata1);
        refresh=view.findViewById(R.id.refresh);
      //  sessionManager.setFilterValue(title);
       // Toast.makeText(getActivity(),"hello : "+sessionManager.getFilterValue(),Toast.LENGTH_SHORT).show();

       // searchView = view.findViewById(R.id.searchView);
        if(Network.isNetworkAvailable(getActivity())){
            GetServices();
        }
        else if(Network.isNetworkAvailable2(getActivity())){
            GetServices();

        }
        else{

        }
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Network.isNetworkAvailable(getActivity())){
                    GetServices1();
                }
                else if(Network.isNetworkAvailable2(getActivity())){
                    GetServices1();

                }
                else{

                }
            }
        });
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sessionManager=new SessionManager(getActivity());
        title = getArguments().getString("title");
        position = getArguments().getInt("page");
        type=getArguments().getString("type");
       // sessionManager.setFilterValue(title);
        //Toast.makeText(getActivity(), title + " ," + position, Toast.LENGTH_SHORT).show();
    }

    public void onAttach(final Context context) {
        super.onAttach(context);
        Booking_Activity_new mActivity = (Booking_Activity_new) context;


        mActivity.getCurrentPageIdx().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer data) {
                if (data == position) {

                    // have focus
                  //  Toast.makeText(getActivity(), String.valueOf(data), Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(getActivity(), String.valueOf(data), Toast.LENGTH_SHORT).show();
                    if(Network.isNetworkAvailable(getActivity())){
                        GetServices();
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetServices();

                    }
                    else{

                    }

                } else {
                    // not in front
                }
            }
        });

    }
    private void GetServices() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Booking_Services_Days(BuildConfig.booking_services_deatails+sessionManager.getHotelId(),"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<GetBookingServices>() {
            @Override
            public void onResponse(@NonNull Call<GetBookingServices> call, @NonNull Response<GetBookingServices> response) {
                if(response.code()==202||response.code()==200){
                    GetBookingServices  login = response.body();
                    getbookingdtata=login.getData();
                    getbookingdtata1.clear();

                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    SimpleDateFormat df1 = new SimpleDateFormat("EEEE", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df1.format(c);


                    for(int i=0;i<getbookingdtata.size();i++){

                        if(i==position){
                            for(int j=0;j<getbookingdtata.get(i).getBooking_service_opening_days().size();j++){
                                if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().contains(formattedDate1)){
                                    GetBookingServices.Booking_service_opening_days data=new GetBookingServices.Booking_service_opening_days();
                                    data.setDate(formattedDate);
                                    data.setDay(formattedDate1);
                                    data.setCount("0");
                                    data.setDate1(c);
                                    data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                    data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                    data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                    getbookingdtata1.add(data);

                                }
                            }
                            for(int j=0;j<getbookingdtata.get(i).getBooking_service_opening_days().size();j++){


                                    Date c1= Calendar.getInstance().getTime();
                                    System.out.println("Current time => " + c1);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    Date tomorrow = calendar.getTime();

                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.add(Calendar.DAY_OF_YEAR, 2);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.add(Calendar.DAY_OF_YEAR, 3);
                                    Calendar calendar4 = Calendar.getInstance();
                                    calendar4.add(Calendar.DAY_OF_YEAR, 4);
                                    Calendar calendar5 = Calendar.getInstance();
                                    calendar5.add(Calendar.DAY_OF_YEAR, 5);
                                    Calendar calendar6 = Calendar.getInstance();
                                    calendar6.add(Calendar.DAY_OF_YEAR, 6);
                                    Date one1 = calendar2.getTime();
                                    Date two1 = calendar3.getTime();
                                    Date three1 = calendar4.getTime();
                                    Date four1 = calendar5.getTime();
                                    Date five1 = calendar6.getTime();

                                    SimpleDateFormat dfnew = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                    SimpleDateFormat df1new = new SimpleDateFormat("EEEE", Locale.getDefault());
                                    String formattedDatenew = dfnew.format(c);
                                    String formattedDate01new = df1new.format(c);
                                    // home.setText(formattedDate01);
                                    String formattedDate1new = dfnew.format(tomorrow);
                                    String formattedDate10new = df1new.format(tomorrow);
                                    //orders.setText(formattedDate10);
                                    String foratteddate2=dfnew.format(one1);
                                    String foratteddate20=df1new.format(one1);
                                    //one.setText(foratteddate20);
                                    String foratteddate3=dfnew.format(two1);
                                    String foratteddate30=df1new.format(two1);
                                    // two.setText(foratteddate30);
                                    String foratteddate4=dfnew.format(three1);
                                    String foratteddate40=df1new.format(three1);
                                    // three.setText(foratteddate40);
                                    String foratteddate5=dfnew.format(four1);
                                    String foratteddate50=df1new.format(four1);
                                    // four.setText(foratteddate50);
                                    String foratteddate6=dfnew.format(five1);
                                    String foratteddate60=df1new.format(five1);
                                    if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(formattedDate10new)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(formattedDate1new);
                                        data.setDate1(tomorrow);
                                        data.setDay(formattedDate10new);
                                        data.setCount("0");

                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate20)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate2);
                                        data.setDay(foratteddate20);
                                        data.setCount("0");

                                        data.setDate1(one1);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate30)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate3);
                                        data.setDay(foratteddate30);
                                        data.setCount("0");

                                        data.setDate1(two1);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }
                                    else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate40)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate4);
                                        data.setDay(foratteddate40);
                                        data.setCount("0");

                                        data.setDate1(three1);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate50)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate5);
                                        data.setDate1(four1);
                                        data.setCount("0");

                                        data.setDay(foratteddate50);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }
                                    else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate60)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate6);
                                        data.setDay(foratteddate60);
                                        data.setDate1(five1);
                                        data.setCount("0");

                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }




                            }

                              // getbookingdtata1=getbookingdtata.get(i).getBooking_service_opening_days();



                        }

                    }


                    Collections.sort(getbookingdtata1, new Comparator<GetBookingServices.Booking_service_opening_days>() {
                        public int compare(GetBookingServices.Booking_service_opening_days o1, GetBookingServices.Booking_service_opening_days o2) {
                            if (o1.getDate1() == null || o2.getDate1() == null)
                                return 0;
                            return o1.getDate1().compareTo(o2.getDate1());
                        }
                    });
                  /*  shimmerRecyclerView.setAdapter(homeAdapter);
                    homeAdapter.notifyDataSetChanged();*/
                   // homeAdapter=new HomeAdapter(getActivity(),getbookingdtata1);
                    if(Network.isNetworkAvailable(getActivity())){
                        GetBookingServices();
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetBookingServices();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    GetBookingServices login = response.body();
                    Toast.makeText( getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<GetBookingServices> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }
    private void GetServices1() {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Booking_Services_Days(BuildConfig.booking_services_deatails+sessionManager.getHotelId(),"Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<GetBookingServices>() {
            @Override
            public void onResponse(@NonNull Call<GetBookingServices> call, @NonNull Response<GetBookingServices> response) {
                if(response.code()==202||response.code()==200){
                    GetBookingServices  login = response.body();
                    getbookingdtata=login.getData();
                    getbookingdtata1.clear();

                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    SimpleDateFormat df1 = new SimpleDateFormat("EEEE", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df1.format(c);


                    for(int i=0;i<getbookingdtata.size();i++){

                        if(i==position){
                            for(int j=0;j<getbookingdtata.get(i).getBooking_service_opening_days().size();j++){
                                if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().contains(formattedDate1)){
                                    GetBookingServices.Booking_service_opening_days data=new GetBookingServices.Booking_service_opening_days();
                                    data.setDate(formattedDate);
                                    data.setDay(formattedDate1);
                                    data.setCount("0");
                                    data.setDate1(c);
                                    data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                    data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                    data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                    getbookingdtata1.add(data);

                                }
                            }
                            for(int j=0;j<getbookingdtata.get(i).getBooking_service_opening_days().size();j++){


                                    Date c1= Calendar.getInstance().getTime();
                                    System.out.println("Current time => " + c1);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                                    Date tomorrow = calendar.getTime();

                                    Calendar calendar2 = Calendar.getInstance();
                                    calendar2.add(Calendar.DAY_OF_YEAR, 2);
                                    Calendar calendar3 = Calendar.getInstance();
                                    calendar3.add(Calendar.DAY_OF_YEAR, 3);
                                    Calendar calendar4 = Calendar.getInstance();
                                    calendar4.add(Calendar.DAY_OF_YEAR, 4);
                                    Calendar calendar5 = Calendar.getInstance();
                                    calendar5.add(Calendar.DAY_OF_YEAR, 5);
                                    Calendar calendar6 = Calendar.getInstance();
                                    calendar6.add(Calendar.DAY_OF_YEAR, 6);
                                    Date one1 = calendar2.getTime();
                                    Date two1 = calendar3.getTime();
                                    Date three1 = calendar4.getTime();
                                    Date four1 = calendar5.getTime();
                                    Date five1 = calendar6.getTime();

                                    SimpleDateFormat dfnew = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                                    SimpleDateFormat df1new = new SimpleDateFormat("EEEE", Locale.getDefault());
                                    String formattedDatenew = dfnew.format(c);
                                    String formattedDate01new = df1new.format(c);
                                    // home.setText(formattedDate01);
                                    String formattedDate1new = dfnew.format(tomorrow);
                                    String formattedDate10new = df1new.format(tomorrow);
                                    //orders.setText(formattedDate10);
                                    String foratteddate2=dfnew.format(one1);
                                    String foratteddate20=df1new.format(one1);
                                    //one.setText(foratteddate20);
                                    String foratteddate3=dfnew.format(two1);
                                    String foratteddate30=df1new.format(two1);
                                    // two.setText(foratteddate30);
                                    String foratteddate4=dfnew.format(three1);
                                    String foratteddate40=df1new.format(three1);
                                    // three.setText(foratteddate40);
                                    String foratteddate5=dfnew.format(four1);
                                    String foratteddate50=df1new.format(four1);
                                    // four.setText(foratteddate50);
                                    String foratteddate6=dfnew.format(five1);
                                    String foratteddate60=df1new.format(five1);
                                    if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(formattedDate10new)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(formattedDate1new);
                                        data.setDate1(tomorrow);
                                        data.setDay(formattedDate10new);
                                        data.setCount("0");

                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate20)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate2);
                                        data.setDay(foratteddate20);
                                        data.setCount("0");

                                        data.setDate1(one1);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate30)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate3);
                                        data.setDay(foratteddate30);
                                        data.setCount("0");

                                        data.setDate1(two1);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }
                                    else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate40)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate4);
                                        data.setDay(foratteddate40);
                                        data.setCount("0");

                                        data.setDate1(three1);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate50)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate5);
                                        data.setDate1(four1);
                                        data.setCount("0");

                                        data.setDay(foratteddate50);
                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }
                                    else if(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getDay().equals(foratteddate60)) {


                                        GetBookingServices.Booking_service_opening_days data = new GetBookingServices.Booking_service_opening_days();
                                        data.setDate(foratteddate6);
                                        data.setDay(foratteddate60);
                                        data.setDate1(five1);
                                        data.setCount("0");

                                        data.setEnabled(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getEnabled());
                                        data.setClosing_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getClosing_time());
                                        data.setOpening_time(getbookingdtata.get(i).getBooking_service_opening_days().get(j).getOpening_time());
                                        getbookingdtata1.add(data);
                                    }




                            }

                              // getbookingdtata1=getbookingdtata.get(i).getBooking_service_opening_days();



                        }

                    }


                    Collections.sort(getbookingdtata1, new Comparator<GetBookingServices.Booking_service_opening_days>() {
                        public int compare(GetBookingServices.Booking_service_opening_days o1, GetBookingServices.Booking_service_opening_days o2) {
                            if (o1.getDate1() == null || o2.getDate1() == null)
                                return 0;
                            return o1.getDate1().compareTo(o2.getDate1());
                        }
                    });
                  /*  shimmerRecyclerView.setAdapter(homeAdapter);
                    homeAdapter.notifyDataSetChanged();*/
                   // homeAdapter=new HomeAdapter(getActivity(),getbookingdtata1);
                    if(Network.isNetworkAvailable(getActivity())){
                        GetBookingServices1();
                    }
                    else if(Network.isNetworkAvailable2(getActivity())){
                        GetBookingServices1();

                    }
                    else{

                    }


                }
                else if(response.code()==401){
                    GetBookingServices login = response.body();
                    Toast.makeText( getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }




            }

            @Override
            public void onFailure(@NonNull Call<GetBookingServices> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));

            }
        });

    }

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
        ArrayList<GetBookingServices.Booking_service_opening_days> homeViewModels;
        Context context;


        public HomeAdapter(Context context, ArrayList<GetBookingServices.Booking_service_opening_days> homeViewModels) {
            this.context = context;
            this.homeViewModels = homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.daysformat, parent, false);

            return new HomeAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position1) {
            holder.mitem = homeViewModels.get(position1);


 // holder.count.setText(String.valueOf(count));

   if (holder.mitem.getEnabled().equals("1")){
       holder.fade.setVisibility(View.GONE);
   }
   else{
       holder.fade.setVisibility(View.VISIBLE);

   }

            holder.days.setText(holder.mitem.getDay()+", "+holder.mitem.getDate());
   //holder.count.setText(holder.mitem.getCount());
            holder.hidelayout.setVisibility(View.GONE);
         //   holder.count.setText(String.valueOf(holder.mitem.getDatalist().size()));


            List<String> al = new ArrayList<String>();
            try {
                for (int i = 0; i < holder.mitem.getDatalist().size(); i++) {
                    al.add(holder.mitem.getDatalist().get(i).getBookingslot());
                }
            }
            catch (Exception e){

            }


            // Displaying ArrayList elements
            System.out.println("Before:");
            System.out.println("ArrayList contains: " + al);

            // Creating LinkedHashSet
            LinkedHashSet<String> lhs = new LinkedHashSet<String>();

            /* Adding ArrayList elements to the LinkedHashSet
             * in order to remove the duplicate elements and
             * to preserve the insertion order.
             */
            lhs.addAll(al);

            // Removing ArrayList elements
            al.clear();

            // Adding LinkedHashSet elements to the ArrayList
            al.addAll(lhs);

            // Displaying ArrayList elements
            System.out.println("After:");
            System.out.println("ArrayList contains: " + al);
            ArrayList<Booking.Data> bookingArrayListnew=new ArrayList<>();

            for (int i = 0; i < al.size(); i++) {
                Booking.Data booking = new Booking.Data();
                booking.setBookingslot(al.get(i));
                booking.setOpensttaus("0");
                bookingArrayListnew.add(booking);
            }

            for (int j = 0; j < bookingArrayListnew.size(); j++) {
                ArrayList<Booking.Data> arrayList = new ArrayList<>();

                for (int i = 0; i < holder.mitem.getDatalist().size(); i++) {


                    if (holder.mitem.getDatalist().get(i).getBookingslot().equals(bookingArrayListnew.get(j).getBookingslot())) {
                        Booking.Data booking = new Booking.Data();
                        booking.setBookingslot(holder.mitem.getDatalist().get(i).getBookingslot());
                        booking.setBookingslotid(holder.mitem.getDatalist().get(i).getOrder_detail().getBsodt_id());
                        booking.setOrder_detail(holder.mitem.getDatalist().get(i).getOrder_detail());
                        booking.setBill_pdf(holder.mitem.getDatalist().get(i).getBill_pdf());
                        booking.setCreated_at(holder.mitem.getDatalist().get(i).getCreated_at());
                        booking.setDescription(holder.mitem.getDatalist().get(i).getDescription());
                        booking.setGuest(holder.mitem.getDatalist().get(i).getGuest());
                        booking.setGuest_id(holder.mitem.getDatalist().get(i).getGuest_id());
                        booking.setGuest_signature(holder.mitem.getDatalist().get(i).getGuest_signature());
                        booking.setHotel_id(holder.mitem.getDatalist().get(i).getHotel_id());
                        booking.setId(holder.mitem.getDatalist().get(i).getId());
                        booking.setIs_complementary(holder.mitem.getDatalist().get(i).getIs_complementary());
                        booking.setMembership_no(holder.mitem.getDatalist().get(i).getMembership_no());
                        booking.setOrder_booking_services(holder.mitem.getDatalist().get(i).getOrder_booking_services());
                        booking.setNo_of_guest(holder.mitem.getDatalist().get(i).getNo_of_guest());
                        booking.setOutlet_id(holder.mitem.getDatalist().get(i).getOutlet_id());
                        booking.setPayment_receipt(holder.mitem.getDatalist().get(i).getPayment_receipt());
                        booking.setPayment_status(holder.mitem.getDatalist().get(i).getPayment_status());
                        booking.setPrimises(holder.mitem.getDatalist().get(i).getPrimises());
                        booking.setPrimises_id(holder.mitem.getDatalist().get(i).getPrimises_id());
                        booking.setStatus(holder.mitem.getDatalist().get(i).getStatus());
                        booking.setTable_id(holder.mitem.getDatalist().get(i).getTable_id());
                        booking.setType(holder.mitem.getDatalist().get(i).getType());
                        booking.setUpdated_at(holder.mitem.getDatalist().get(i).getUpdated_at());
                        arrayList.add(booking);

                    }
                }
                bookingArrayListnew.get(j).setData1ArrayList(arrayList);


            }
            int temp=0;
            for(int i=0;i<bookingArrayListnew.size();i++){
                if(i==0) {
                    temp = bookingArrayListnew.get(i).getData1ArrayList().size();
                }
                else{
                    temp=temp+bookingArrayListnew.get(i).getData1ArrayList().size();
                }


            }
            holder.count.setText(String.valueOf(temp));


          /*  if(Network.isNetworkAvailable(getActivity())){
                GetBookingServices(type,holder.mitem.getDate(),holder.bookinglist);
            }
            else if(Network.isNetworkAvailable2(getActivity())){
                GetBookingServices(type,holder.mitem.getDate(),holder.bookinglist);

            }
            else{

            }
*/

          try {
              if (holder.mitem.getDatalist().size() != 0) {
                  HomeAdapter2 homeAdapter2 = new HomeAdapter2(getActivity(),bookingArrayListnew);
                  holder.bookinglist.setAdapter(homeAdapter2);
              }
          }catch (Exception e){

          }





            holder.top.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(getActivity(),FirstActivity.class);
              intent.putExtra("title",title);
              intent.putExtra("date",holder.mitem.getDate());
              intent.putExtra("type",type);
              startActivity(intent);

          }
      });
        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }


        public class viewholder extends RecyclerView.ViewHolder {

            final Typeface font1;
            final Typeface font;
            TextView days;
            TextView count;
            ShimmerRecyclerView bookinglist;
            LinearLayout hidelayout;

            GetBookingServices.Booking_service_opening_days mitem;
            FrameLayout top;
            RelativeLayout fade;

            ShimmerRecyclerView shimmerRecyclerView;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                days = itemView.findViewById(R.id.days);
                top = itemView.findViewById(R.id.clickevent);
                fade=itemView.findViewById(R.id.fade);
                count=itemView.findViewById(R.id.count);
                hidelayout=itemView.findViewById(R.id.hidelayout);
                bookinglist=itemView.findViewById(R.id.bookinglist);
                bookinglist.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));




                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");


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

        private String getDate2(String time) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }
    public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.viewholder> {
        ArrayList<Booking.Data> homeViewModels;
        Context context;

        HomeAdapter1 homeAdapter1;

        public HomeAdapter2(Context context, ArrayList<Booking.Data> homeViewModels) {
            this.context = context;
            this.homeViewModels = homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.booking_parent_list, parent, false);

            return new HomeAdapter2.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter2.viewholder holder, final int position) {
            holder.mitem = homeViewModels.get(position);
            String[] part = holder.mitem.getBookingslot().split("-");
            holder.timeslot.setText(getDate2(part[0]) + " - " + getDate2(part[1]));
            if (holder.mitem.getOpensttaus().equals("1")) {
                holder.hide.setVisibility(View.VISIBLE);
                holder.mitem.setOpensttaus("1");
                holder.arrow.setImageResource(R.mipmap.arrowup);

            } else {
                holder.hide.setVisibility(View.GONE);
                holder.mitem.setOpensttaus("0");
                holder.arrow.setImageResource(R.mipmap.arrowdown);

            }
             holder.count.setText("( " + holder.mitem.getData1ArrayList().size() + " )");
            homeAdapter1 = new HomeAdapter1(context, holder.mitem.getData1ArrayList());

            holder.shimmerRecyclerView.setAdapter(homeAdapter1);

            holder.top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mitem.getOpensttaus().equals("0")) {
                        holder.hide.setVisibility(View.VISIBLE);
                        holder.mitem.setOpensttaus("1");
                        holder.arrow.setImageResource(R.mipmap.arrowup);
                    } else {
                        holder.hide.setVisibility(View.GONE);
                        holder.mitem.setOpensttaus("0");
                        holder.arrow.setImageResource(R.mipmap.arrowdown);

                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }

        public void filterList(ArrayList<Booking.Data> filterdNames, String text) {
            for (int i = 0; i < filterdNames.size(); i++) {
                ArrayList<Booking.Data> arrayList = new ArrayList<>();

                for (int j = 0; j < filterdNames.get(i).getData1ArrayList().size(); j++) {
                    if (filterdNames.get(i).getData1ArrayList().get(j).getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase()) ||
                            filterdNames.get(i).getData1ArrayList().get(j).getGuest().getName().toLowerCase().contains(text.toLowerCase())) {
                        Booking.Data booking = new Booking.Data();
                        booking.setBookingslot(filterdNames.get(i).getData1ArrayList().get(j).getBookingslot());
                        booking.setBookingslotid(filterdNames.get(i).getData1ArrayList().get(j).getBookingslotid());
                        booking.setOrder_detail(filterdNames.get(i).getData1ArrayList().get(j).getOrder_detail());
                        booking.setBill_pdf(filterdNames.get(i).getData1ArrayList().get(j).getBill_pdf());
                        booking.setCreated_at(filterdNames.get(i).getData1ArrayList().get(j).getCreated_at());
                        booking.setDescription(filterdNames.get(i).getData1ArrayList().get(j).getDescription());
                        booking.setGuest(filterdNames.get(i).getData1ArrayList().get(j).getGuest());
                        booking.setGuest_id(filterdNames.get(i).getData1ArrayList().get(j).getGuest_id());
                        booking.setGuest_signature(filterdNames.get(i).getData1ArrayList().get(j).getGuest_signature());
                        booking.setHotel_id(filterdNames.get(i).getData1ArrayList().get(j).getHotel_id());
                        booking.setId(filterdNames.get(i).getData1ArrayList().get(j).getId());
                        booking.setIs_complementary(filterdNames.get(i).getData1ArrayList().get(j).getIs_complementary());
                        booking.setMembership_no(filterdNames.get(i).getData1ArrayList().get(j).getMembership_no());
                        booking.setOrder_booking_services(filterdNames.get(i).getData1ArrayList().get(j).getOrder_booking_services());
                        booking.setNo_of_guest(filterdNames.get(i).getData1ArrayList().get(j).getNo_of_guest());
                        booking.setOutlet_id(filterdNames.get(i).getData1ArrayList().get(j).getOutlet_id());
                        booking.setPayment_receipt(filterdNames.get(i).getData1ArrayList().get(j).getPayment_receipt());
                        booking.setPayment_status(filterdNames.get(i).getData1ArrayList().get(j).getPayment_status());
                        booking.setPrimises(filterdNames.get(i).getData1ArrayList().get(j).getPrimises());
                        booking.setPrimises_id(filterdNames.get(i).getData1ArrayList().get(j).getPrimises_id());
                        booking.setStatus(filterdNames.get(i).getData1ArrayList().get(j).getStatus());
                        booking.setTable_id(filterdNames.get(i).getData1ArrayList().get(j).getTable_id());
                        booking.setType(filterdNames.get(i).getData1ArrayList().get(j).getType());
                        booking.setUpdated_at(filterdNames.get(i).getData1ArrayList().get(j).getUpdated_at());
                        arrayList.add(booking);

                    }

                }

                filterdNames.get(i).setData1ArrayList(arrayList);
            }
            this.homeViewModels = filterdNames;
            notifyDataSetChanged();

        }

        public class viewholder extends RecyclerView.ViewHolder {

            final Typeface font1;
            final Typeface font;
            TextView timeslot;
            TextView count;
            ImageView arrow;
            Booking.Data mitem;
            LinearLayout top;
            LinearLayout hide;
            ShimmerRecyclerView shimmerRecyclerView;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                timeslot = itemView.findViewById(R.id.timeslot);
                top = itemView.findViewById(R.id.top);
                count = itemView.findViewById(R.id.count);
                shimmerRecyclerView = itemView.findViewById(R.id.bookinglist);
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                arrow = itemView.findViewById(R.id.arrow);
                hide = itemView.findViewById(R.id.hide);


                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");


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

        private String getDate2(String time) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }

    public class HomeAdapter1 extends RecyclerView.Adapter<HomeAdapter1.viewholder> {
        ArrayList<Booking.Data> homeViewModels;
        Context context;


        public HomeAdapter1(Context context, ArrayList<Booking.Data> homeViewModels) {
            this.context = context;
            this.homeViewModels = homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.booking_child_list, parent, false);

            return new HomeAdapter1.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter1.viewholder holder, final int position) {
            holder.mitem = homeViewModels.get(position);
            holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
            holder.guests.setText(holder.mitem.getNo_of_guest());
            holder.lastname.setText(holder.mitem.getGuest().getName());
            holder.bookedat.setText(getDate3(holder.mitem.getOrder_detail().getCreated_at()));
            holder.venue.setText(toTitleCase(holder.mitem.getOrder_booking_services().get(0).getBooking_service_name()));


        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }

        public void filterList(ArrayList<Booking.Data> filterdNames, String text) {
            this.homeViewModels = filterdNames;
            notifyDataSetChanged();
        }

        public class viewholder extends RecyclerView.ViewHolder {

            final Typeface font1;
            final Typeface font;
            TextView roomno;
            TextView lastname;
            TextView bookedat;
            TextView guests;
            TextView venue;

            Booking.Data mitem;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno = itemView.findViewById(R.id.roomno);
                lastname = itemView.findViewById(R.id.lastname);
                bookedat = itemView.findViewById(R.id.bookedat);
                guests = itemView.findViewById(R.id.guests);
                venue = itemView.findViewById(R.id.venue);


                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");


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

        private String getDate3(String time) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM \nhh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }

        private String getDate2(String time) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            try {
                date = dateFormat.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }
    private void GetBookingServices() {
        // display a progress dialog
       // shimmerRecyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
        shi
*/


        (RetrofitClientInstance.getApiService().Booking_getorders("Bearer " + sessionManager.getACCESSTOKEN(), type)).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(@NonNull Call<Booking> call, @NonNull Response<Booking> response) {

                if (response.code() == 202 || response.code() == 200) {
                 /*   Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df.format(tomorrow);*/
                    Booking login = response.body();
                    for(int k=0;k<getbookingdtata1.size();k++) {
                        if (login.getData().size() != 0) {
                    ArrayList<Booking.Data> bookinglist=new ArrayList<>();
                            for (int i = 0; i < login.getData().size(); i++) {

                                if (login.getData().get(i).getOrder_booking_services().get(0).getBooking_service_name().equals(title)) {

                                    String datenew = getDate1(login.getData().get(i).getOrder_detail().getPickup_slot());
                                    if (datenew.equals(getbookingdtata1.get(k).getDate())) {
                                        Booking.Data booking = new Booking.Data();
                                        booking.setBookingslot(login.getData().get(i).getOrder_detail().getBooking_slot());
                                        booking.setBookingslotid(login.getData().get(i).getOrder_detail().getBsodt_id());
                                        booking.setOrder_detail(login.getData().get(i).getOrder_detail());
                                        booking.setBill_pdf(login.getData().get(i).getBill_pdf());
                                        booking.setCreated_at(login.getData().get(i).getCreated_at());
                                        booking.setDescription(login.getData().get(i).getDescription());
                                        booking.setGuest(login.getData().get(i).getGuest());
                                        booking.setGuest_id(login.getData().get(i).getGuest_id());
                                        booking.setGuest_signature(login.getData().get(i).getGuest_signature());
                                        booking.setHotel_id(login.getData().get(i).getHotel_id());
                                        booking.setId(login.getData().get(i).getId());
                                        booking.setIs_complementary(login.getData().get(i).getIs_complementary());
                                        booking.setMembership_no(login.getData().get(i).getMembership_no());
                                        booking.setOrder_booking_services(login.getData().get(i).getOrder_booking_services());
                                        booking.setNo_of_guest(login.getData().get(i).getNo_of_guest());
                                        booking.setOutlet_id(login.getData().get(i).getOutlet_id());
                                        booking.setPayment_receipt(login.getData().get(i).getPayment_receipt());
                                        booking.setPayment_status(login.getData().get(i).getPayment_status());
                                        booking.setPrimises(login.getData().get(i).getPrimises());
                                        booking.setPrimises_id(login.getData().get(i).getPrimises_id());
                                        booking.setStatus(login.getData().get(i).getStatus());
                                        booking.setTable_id(login.getData().get(i).getTable_id());
                                        booking.setType(login.getData().get(i).getType());
                                        booking.setUpdated_at(login.getData().get(i).getUpdated_at());
                                        booking.setOpensttaus("0");
                                        bookinglist.add(booking);


                                    }
                                }

                            }
                            getbookingdtata1.get(k).setDatalist(bookinglist);



                        } else {
                         //   norecord.setVisibility(View.VISIBLE);
                            ArrayList<Booking.Data> bookinglist=new ArrayList<>();

                            getbookingdtata1.get(k).setDatalist(bookinglist);

                        }
                        shimmerRecyclerView.setAdapter(homeAdapter);
                        homeAdapter.notifyDataSetChanged();


                    }



                } else if (response.code() == 401) {
                    Booking login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                } else if (response.code() == 500) {
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                } else {

                }


              //  shimmerRecyclerView.hideShimmer();


            }

            @Override
            public void onFailure(@NonNull Call<Booking> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
             //  shimmerRecyclerView.hideShimmer();

            }
        });

    }
    private void GetBookingServices1() {
        // display a progress dialog
        shimmerRecyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
        shi
*/


        (RetrofitClientInstance.getApiService().Booking_getorders("Bearer " + sessionManager.getACCESSTOKEN(), type)).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(@NonNull Call<Booking> call, @NonNull Response<Booking> response) {

                if (response.code() == 202 || response.code() == 200) {
                 /*   Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df.format(tomorrow);*/
                    Booking login = response.body();
                    for(int k=0;k<getbookingdtata1.size();k++) {
                        if (login.getData().size() != 0) {
                    ArrayList<Booking.Data> bookinglist=new ArrayList<>();
                            for (int i = 0; i < login.getData().size(); i++) {

                                if (login.getData().get(i).getOrder_booking_services().get(0).getBooking_service_name().equals(title)) {

                                    String datenew = getDate1(login.getData().get(i).getOrder_detail().getPickup_slot());
                                    if (datenew.equals(getbookingdtata1.get(k).getDate())) {
                                        Booking.Data booking = new Booking.Data();
                                        booking.setBookingslot(login.getData().get(i).getOrder_detail().getBooking_slot());
                                        booking.setBookingslotid(login.getData().get(i).getOrder_detail().getBsodt_id());
                                        booking.setOrder_detail(login.getData().get(i).getOrder_detail());
                                        booking.setBill_pdf(login.getData().get(i).getBill_pdf());
                                        booking.setCreated_at(login.getData().get(i).getCreated_at());
                                        booking.setDescription(login.getData().get(i).getDescription());
                                        booking.setGuest(login.getData().get(i).getGuest());
                                        booking.setGuest_id(login.getData().get(i).getGuest_id());
                                        booking.setGuest_signature(login.getData().get(i).getGuest_signature());
                                        booking.setHotel_id(login.getData().get(i).getHotel_id());
                                        booking.setId(login.getData().get(i).getId());
                                        booking.setIs_complementary(login.getData().get(i).getIs_complementary());
                                        booking.setMembership_no(login.getData().get(i).getMembership_no());
                                        booking.setOrder_booking_services(login.getData().get(i).getOrder_booking_services());
                                        booking.setNo_of_guest(login.getData().get(i).getNo_of_guest());
                                        booking.setOutlet_id(login.getData().get(i).getOutlet_id());
                                        booking.setPayment_receipt(login.getData().get(i).getPayment_receipt());
                                        booking.setPayment_status(login.getData().get(i).getPayment_status());
                                        booking.setPrimises(login.getData().get(i).getPrimises());
                                        booking.setPrimises_id(login.getData().get(i).getPrimises_id());
                                        booking.setStatus(login.getData().get(i).getStatus());
                                        booking.setTable_id(login.getData().get(i).getTable_id());
                                        booking.setType(login.getData().get(i).getType());
                                        booking.setUpdated_at(login.getData().get(i).getUpdated_at());
                                        booking.setOpensttaus("0");
                                        bookinglist.add(booking);


                                    }
                                }

                            }
                            getbookingdtata1.get(k).setDatalist(bookinglist);



                        } else {
                         //   norecord.setVisibility(View.VISIBLE);
                            ArrayList<Booking.Data> bookinglist=new ArrayList<>();

                            getbookingdtata1.get(k).setDatalist(bookinglist);

                        }
                        shimmerRecyclerView.setAdapter(homeAdapter);
                        homeAdapter.notifyDataSetChanged();


                    }



                } else if (response.code() == 401) {
                    Booking login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();
                } else if (response.code() == 500) {
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                } else {

                }


                shimmerRecyclerView.hideShimmer();


            }

            @Override
            public void onFailure(@NonNull Call<Booking> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
               shimmerRecyclerView.hideShimmer();

            }
        });

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
        String dateStr = formatter.format(date);
        return dateStr;
    }
    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

  /*  @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            sessionManager = new SessionManager(getActivity());
            if (Network.isNetworkAvailable(getActivity())) {
                GetServices();
            } else if (Network.isNetworkAvailable2(getActivity())) {
                GetServices();

            } else {

            }
        }
    }*/
}
