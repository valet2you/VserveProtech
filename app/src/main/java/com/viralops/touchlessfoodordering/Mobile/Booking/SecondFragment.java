package com.viralops.touchlessfoodordering.Mobile.Booking;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;
import com.viralops.touchlessfoodordering.Support.SessionManagerFCM;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {
    SessionManager sessionManager;
    SessionManagerFCM sessionManagerFCM;
    ShimmerRecyclerView shimmerRecyclerView;
    TextView norecord;
    ArrayList<Booking.Data> bookingArrayList=new ArrayList<>();
    ArrayList<Booking.Data> bookingArrayListnew=new ArrayList<>();
    ArrayList<Booking> secondArraylist=new ArrayList<>();
    AutoCompleteTextView searchView;
    HomeAdapter homeAdapter;
    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        sessionManager=new SessionManager(getActivity());
        sessionManagerFCM=new SessionManagerFCM(getActivity());
        shimmerRecyclerView=view.findViewById(R.id.recycler);
        shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        norecord=view.findViewById(R.id.norecord);
        searchView=view.findViewById(R.id.searchView);
        homeAdapter=new HomeAdapter(getActivity(),bookingArrayListnew);
        Booking_Activity.isVisible=false;

        if(Network.isNetworkAvailable(getActivity())){
            if(sessionManager.getFilterValue().equals("Fitness Center Booking")){
                GetBookingServices("gym");

            }
            else if(sessionManager.getFilterValue().equals("Pool Booking")){
                GetBookingServices("pool");


            } else if(sessionManager.getFilterValue().equals("Meeting Booking")){
                GetBookingServices("meeting");


            }else if(sessionManager.getFilterValue().equals("Conference Booking")){
                GetBookingServices("conference");


            }
        }
        else if(Network.isNetworkAvailable2(getActivity())){
            if(sessionManager.getFilterValue().equals("Fitness Center Booking")){
                GetBookingServices("gym");

            }
            else if(sessionManager.getFilterValue().equals("Pool Booking")){
                GetBookingServices("pool");


            } else if(sessionManager.getFilterValue().equals("Meeting Booking")){
                GetBookingServices("meeting");


            }else if(sessionManager.getFilterValue().equals("Conference Booking")){
                GetBookingServices("conference");


            }
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
        return  view;

    }
    private void GetBookingServices(String type) {
        // display a progress dialog
        shimmerRecyclerView.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
        shi
*/

        (RetrofitClientInstance.getApiService().Booking_getorders("Bearer "+sessionManager.getACCESSTOKEN(),type)).enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(@NonNull Call<Booking> call, @NonNull Response<Booking> response) {

                if(response.code()==202||response.code()==200){
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date tomorrow = calendar.getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    String formattedDate1 = df.format(tomorrow);
                    Booking  login = response.body();
                    if(login.getData().size()!=0) {
                        for (int i = 0; i < login.getData().size(); i++) {
                            String datenew=getDate1(login.getData().get(i).getOrder_detail().getPickup_slot());
                            if (datenew.equals(formattedDate1)) {
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
                                bookingArrayList.add(booking);


                            }

                        }
                        List<String> al = new ArrayList<String>();
                        for (int i = 0; i < bookingArrayList.size(); i++) {
                            al.add(bookingArrayList.get(i).getBookingslot());
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

                        for (int i = 0; i < al.size(); i++) {
                            Booking.Data booking = new Booking.Data();
                            booking.setBookingslot(al.get(i));
                            booking.setOpensttaus("0");
                            bookingArrayListnew.add(booking);
                        }

                        for (int j = 0; j < bookingArrayListnew.size(); j++) {
                            ArrayList<Booking.Data> arrayList = new ArrayList<>();

                            for (int i = 0; i < bookingArrayList.size(); i++) {


                                if (bookingArrayList.get(i).getBookingslot().equals(bookingArrayListnew.get(j).getBookingslot())) {
                                    Booking.Data booking = new Booking.Data();
                                    booking.setBookingslot(bookingArrayList.get(i).getBookingslot());
                                    booking.setBookingslotid(bookingArrayList.get(i).getOrder_detail().getBsodt_id());
                                    booking.setOrder_detail(bookingArrayList.get(i).getOrder_detail());
                                    booking.setBill_pdf(bookingArrayList.get(i).getBill_pdf());
                                    booking.setCreated_at(bookingArrayList.get(i).getCreated_at());
                                    booking.setDescription(bookingArrayList.get(i).getDescription());
                                    booking.setGuest(bookingArrayList.get(i).getGuest());
                                    booking.setGuest_id(bookingArrayList.get(i).getGuest_id());
                                    booking.setGuest_signature(bookingArrayList.get(i).getGuest_signature());
                                    booking.setHotel_id(bookingArrayList.get(i).getHotel_id());
                                    booking.setId(bookingArrayList.get(i).getId());
                                    booking.setIs_complementary(bookingArrayList.get(i).getIs_complementary());
                                    booking.setMembership_no(bookingArrayList.get(i).getMembership_no());
                                    booking.setOrder_booking_services(bookingArrayList.get(i).getOrder_booking_services());
                                    booking.setNo_of_guest(bookingArrayList.get(i).getNo_of_guest());
                                    booking.setOutlet_id(bookingArrayList.get(i).getOutlet_id());
                                    booking.setPayment_receipt(bookingArrayList.get(i).getPayment_receipt());
                                    booking.setPayment_status(bookingArrayList.get(i).getPayment_status());
                                    booking.setPrimises(bookingArrayList.get(i).getPrimises());
                                    booking.setPrimises_id(bookingArrayList.get(i).getPrimises_id());
                                    booking.setStatus(bookingArrayList.get(i).getStatus());
                                    booking.setTable_id(bookingArrayList.get(i).getTable_id());
                                    booking.setType(bookingArrayList.get(i).getType());
                                    booking.setUpdated_at(bookingArrayList.get(i).getUpdated_at());
                                    arrayList.add(booking);

                                }
                            }
                            bookingArrayListnew.get(j).setData1ArrayList(arrayList);

                        }
                        if(bookingArrayListnew.size()!=0){

                        }
                        if(bookingArrayListnew.size()!=0){
                            norecord.setVisibility(View.GONE);
                            shimmerRecyclerView.setAdapter(homeAdapter);


                        }
                        else{
                            norecord.setVisibility(View.VISIBLE);
                            shimmerRecyclerView.setAdapter(homeAdapter);


                        }
                    }
                    else{
                        norecord.setVisibility(View.VISIBLE);
                    }






                }
                else if(response.code()==401){
                    Booking login = response.body();
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
            public void onFailure(@NonNull Call<Booking> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                shimmerRecyclerView.hideShimmer();

            }
        });

    }


    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewholder> {
        ArrayList<Booking.Data> homeViewModels;
        Context context;

        HomeAdapter1 homeAdapter1;

        public HomeAdapter(Context context,ArrayList<Booking.Data> homeViewModels) {
            this.context=context;
            this.homeViewModels=homeViewModels;

        }

        @NonNull
        @Override
        public HomeAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.booking_parent_list, parent, false);

            return new HomeAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HomeAdapter.viewholder holder, final int position) {
            holder.mitem=homeViewModels.get(position);
            String[] part=holder.mitem.getBookingslot().split("-");
            holder.timeslot.setText(getDate2(part[0])+" - "+getDate2(part[1]));
            if(holder.mitem.getOpensttaus().equals("1")){
                holder.hide.setVisibility(View.VISIBLE);
                holder.mitem.setOpensttaus("1");
                holder.arrow.setImageResource(R.mipmap.arrowup);

            }
            else{
                holder.hide.setVisibility(View.GONE);
                holder.mitem.setOpensttaus("0");
                holder.arrow.setImageResource(R.mipmap.arrowdown);

            }

            holder.count.setText("( "+holder.mitem.getData1ArrayList().size()+" )" );
            homeAdapter1=new HomeAdapter1(context,holder.mitem.getData1ArrayList());

            holder.shimmerRecyclerView.setAdapter(homeAdapter1);

            holder.top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.mitem.getOpensttaus().equals("0")){
                        holder.hide.setVisibility(View.VISIBLE);
                        holder.mitem.setOpensttaus("1");
                        holder.arrow.setImageResource(R.mipmap.arrowup);
                    }
                    else{
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
        public void filterList(ArrayList<Booking.Data> filterdNames,String text) {
            for(int i=0;i<filterdNames.size();i++){
                ArrayList<Booking.Data> arrayList = new ArrayList<>();

                for(int j=0;j<filterdNames.get(i).getData1ArrayList().size();j++){
                    if(filterdNames.get(i).getData1ArrayList().get(j).getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())||
                            filterdNames.get(i).getData1ArrayList().get(j).getGuest().getName().toLowerCase().contains(text.toLowerCase())){
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
                timeslot=itemView.findViewById(R.id.timeslot);
                top=itemView.findViewById(R.id.top);
                count=itemView.findViewById(R.id.count);
                shimmerRecyclerView=itemView.findViewById(R.id.bookinglist);
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                arrow=itemView.findViewById(R.id.arrow);
                hide=itemView.findViewById(R.id.hide);


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




        public HomeAdapter1(Context context,ArrayList<Booking.Data> homeViewModels) {
            this.context=context;
            this.homeViewModels=homeViewModels;

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
            holder.mitem=homeViewModels.get(position);
            holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
            holder.guests.setText(holder.mitem.getNo_of_guest());
            holder.lastname.setText(holder.mitem.getGuest().getName());
            holder.bookedat.setText(getDate3(holder.mitem.getOrder_detail().getCreated_at()));







        }

        @Override
        public int getItemCount() {
            return homeViewModels.size();
        }
        public void filterList(ArrayList<Booking.Data> filterdNames) {
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
            Booking.Data mitem;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno=itemView.findViewById(R.id.roomno);
                lastname=itemView.findViewById(R.id.lastname);
                guests=itemView.findViewById(R.id.guests);
                bookedat=itemView.findViewById(R.id.bookedat);


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
    private void filter(String text) {
        try {
            //new array list that will hold the filtered data
            if(text.equals("")){
                for(int j=0;j<bookingArrayListnew.size();j++) {
                    ArrayList<Booking.Data> arrayList = new ArrayList<>();

                    for (int i = 0; i < bookingArrayList.size(); i++) {


                        if (bookingArrayList.get(i).getBookingslot().equals(bookingArrayListnew.get(j).getBookingslot())) {
                            Booking.Data booking = new Booking.Data();
                            booking.setBookingslot(bookingArrayList.get(i).getBookingslot());
                            booking.setBookingslotid(bookingArrayList.get(i).getOrder_detail().getBsodt_id());
                            booking.setOrder_detail(bookingArrayList.get(i).getOrder_detail());
                            booking.setBill_pdf(bookingArrayList.get(i).getBill_pdf());
                            booking.setCreated_at(bookingArrayList.get(i).getCreated_at());
                            booking.setDescription(bookingArrayList.get(i).getDescription());
                            booking.setGuest(bookingArrayList.get(i).getGuest());
                            booking.setGuest_id(bookingArrayList.get(i).getGuest_id());
                            booking.setGuest_signature(bookingArrayList.get(i).getGuest_signature());
                            booking.setHotel_id(bookingArrayList.get(i).getHotel_id());
                            booking.setId(bookingArrayList.get(i).getId());
                            booking.setIs_complementary(bookingArrayList.get(i).getIs_complementary());
                            booking.setMembership_no(bookingArrayList.get(i).getMembership_no());
                            booking.setOrder_booking_services(bookingArrayList.get(i).getOrder_booking_services());
                            booking.setNo_of_guest(bookingArrayList.get(i).getNo_of_guest());
                            booking.setOutlet_id(bookingArrayList.get(i).getOutlet_id());
                            booking.setPayment_receipt(bookingArrayList.get(i).getPayment_receipt());
                            booking.setPayment_status(bookingArrayList.get(i).getPayment_status());
                            booking.setPrimises(bookingArrayList.get(i).getPrimises());
                            booking.setPrimises_id(bookingArrayList.get(i).getPrimises_id());
                            booking.setStatus(bookingArrayList.get(i).getStatus());
                            booking.setTable_id(bookingArrayList.get(i).getTable_id());
                            booking.setType(bookingArrayList.get(i).getType());
                            booking.setUpdated_at(bookingArrayList.get(i).getUpdated_at());
                            arrayList.add(booking);

                        }
                    }
                    bookingArrayListnew.get(j).setData1ArrayList(arrayList);

                }
                homeAdapter.filterList(bookingArrayListnew,text);


            }
            else {
                ArrayList<Booking.Data> filterdNames = new ArrayList<>();

                //looping through existing elements
                for (Booking.Data s : bookingArrayListnew) {
                    for (Booking.Data s1 : s.getData1ArrayList()) {
                        //if the existing elements contains the search input
                        if (s1.getPrimises().getPremise_no().toLowerCase().contains(text.toLowerCase())||
                                s1.getGuest().getName().toLowerCase().contains(text.toLowerCase())) {
                            //adding the element to filtered list
                            if(filterdNames.contains(s)){

                            }
                            else {
                                filterdNames.add(s);
                            }
                        }
                    }

                }
                homeAdapter.filterList(filterdNames,text);


            }


            //calling a method of the adapter class and passing the filtered list
        }catch (Exception e){

        }
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
}
