package com.viralops.touchlessfoodordering.Mobile.Supervisor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.shimmer.ShimmerRecyclerView;
import com.viralops.touchlessfoodordering.API.RetrofitClientInstance;
import com.viralops.touchlessfoodordering.Mobile.IRD.Associate_Dashboard;
import com.viralops.touchlessfoodordering.Model.Dashboard;
import com.viralops.touchlessfoodordering.Model.Loginuser;
import com.viralops.touchlessfoodordering.R;
import com.viralops.touchlessfoodordering.Support.Network;
import com.viralops.touchlessfoodordering.Support.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Supervisor_Dashboard_accepted extends Fragment {
    ArrayList<Supervior_Model.Result> supervisor_dashboards=new ArrayList<Supervior_Model.Result>();
    ArrayList<Supervisor_Managers.Result> supervisor_managers=new ArrayList<Supervisor_Managers.Result>();
    ShimmerRecyclerView recyclerview;
    SessionManager sessionManager;

    public Supervisor_Dashboard_accepted() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Supervisor_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Supervisor_Dashboard_accepted newInstance(String param1, String param2) {
        Supervisor_Dashboard_accepted fragment = new Supervisor_Dashboard_accepted();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_supervisor__dashboard, container, false);
        sessionManager=new SessionManager(getActivity());
        recyclerview=view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        if(Network.isNetworkAvailable(getActivity())){
            GetMenu();

        }else if(Network.isNetworkAvailable2(getActivity())){
            GetMenu();

        }else{

        }




        return view;
    }



    private void GetMenu() {
        // display a progress dialog
        recyclerview.showShimmer();
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/

        (RetrofitClientInstance.getApiService().Supervisor_getorders("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Supervior_Model>() {
            @Override
            public void onResponse(@NonNull Call<Supervior_Model> call, @NonNull Response<Supervior_Model> response) {

                if(response.code()==201||response.code()==200){
                    Supervior_Model  login = response.body();
                    supervisor_dashboards=login.getResult();
                    Supervisor_Adapter supervisor_adapter=new Supervisor_Adapter(supervisor_dashboards,getActivity());
                    recyclerview.setAdapter(supervisor_adapter);

                }
                else if(response.code()==401){
                    Supervior_Model login = response.body();
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
            public void onFailure(@NonNull Call<Supervior_Model> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
                recyclerview.hideShimmer();

            }
        });

    }
    private void GetMenumanagers(final String id,final String irdname) {
        // display a progress dialog
/*
        String credentials = Credentials.basic("admin", "LetsValet2You");
*/
       supervisor_managers.clear();
        (RetrofitClientInstance.getApiService().Supervisor_getManagers("Bearer "+sessionManager.getACCESSTOKEN())).enqueue(new Callback<Supervisor_Managers>() {
            @Override
            public void onResponse(@NonNull Call<Supervisor_Managers> call, @NonNull Response<Supervisor_Managers> response) {

                if(response.code()==201||response.code()==200){
                    Supervisor_Managers  login = response.body();
                    supervisor_managers=login.getResult();
                    ArrayList<Supervisor_Managers.Result> supervisor_managersnew=new ArrayList<>();
                    for(int i=0;i<supervisor_managers.size();i++){
                        if(irdname.equals("ird")) {
                            if (supervisor_managers.get(i).getHotel_id().equals(id) && supervisor_managers.get(i).getType().equals("ird_manager")&&supervisor_managers.get(i).getIs_active().equals("1")) {
                                Supervisor_Managers.Result result1=new Supervisor_Managers.Result();
                                result1.setName(supervisor_managers.get(i).getName());
                                if(supervisor_managers.get(i).getLoggedin_at()!=null) {
                                    result1.setLoggedin_at(supervisor_managers.get(i).getLoggedin_at());
                                }
                                else{

                                }
                                supervisor_managersnew.add(result1);

                            }



                        }
                        else if(irdname.equals("ird_restaurant")){
                            if (supervisor_managers.get(i).getHotel_id().equals(id) && supervisor_managers.get(i).getType().equals("restaurant_manager")&&supervisor_managers.get(i).getIs_active().equals("1")) {
                                Supervisor_Managers.Result result1=new Supervisor_Managers.Result();
                                result1.setName(supervisor_managers.get(i).getName());
                                if(supervisor_managers.get(i).getLoggedin_at()!=null) {
                                    result1.setLoggedin_at(supervisor_managers.get(i).getLoggedin_at());
                                }
                                else{

                                }
                                supervisor_managersnew.add(result1);
                            }
                        }

                    }
                    final Dialog dialog = new Dialog(getActivity());
                    // Include dialog.xml file

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.loggedinusers);
                    int width1 = (int)(getActivity().getResources().getDisplayMetrics().widthPixels*0.90);
                    int height1 = (int)(getActivity().getResources().getDisplayMetrics().heightPixels*0.70);
                    dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                    dialog.getWindow().setLayout(width1, height1);

                    dialog.setCancelable(false);
                    // Set dialog title
                    dialog.setTitle("");
                    dialog.show();
                    ImageView close=dialog.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    RecyclerView recyclerView=dialog.findViewById(R.id.recycler);
                    TextView norecord=dialog.findViewById(R.id.norecordfound);
                    if(supervisor_managersnew.size()!=0){
                        norecord.setVisibility(View.GONE);
                    }
                    else{
                        norecord.setVisibility(View.VISIBLE);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                    Login_Adapter1 login_adapter1 =new Login_Adapter1(supervisor_managersnew,getActivity());
                    recyclerView.setAdapter(login_adapter1);

                }
                else if(response.code()==401){
                    Supervisor_Managers login = response.body();
                    Toast.makeText(getActivity(), "Unauthorised", Toast.LENGTH_SHORT).show();
                    sessionManager.logoutsession();

                }
                else if(response.code()==500){
                    Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
                else{

                }

              //  recyclerview.hideShimmer();



            }

            @Override
            public void onFailure(@NonNull Call<Supervisor_Managers> call, @NonNull Throwable t) {
                Log.d("response", Arrays.toString(t.getStackTrace()));
               // recyclerview.hideShimmer();

            }
        });

    }
//-------------------Supervisor Adaapter----------------------------------------------//


    public class Supervisor_Adapter extends RecyclerView.Adapter<Supervisor_Adapter.viewholder>{
        ArrayList<Supervior_Model.Result> supervior_models;
        ArrayList<Loginuser> loginusers=new ArrayList<>();
        Context context;



        public Supervisor_Adapter(ArrayList<Supervior_Model.Result> supervior_models, Context context) {
            this.supervior_models=supervior_models;
            this.context=context;
            loginusers.clear();

        }

        @NonNull
        @Override
        public Supervisor_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            return new Supervisor_Adapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Supervisor_Adapter.viewholder holder, int position) {
            holder.supervior_model=supervior_models.get(position);
            ArrayList<Supervior_Model.Hotel_orders> ordersArrayList=new ArrayList<>();
            ArrayList<Supervior_Model.Restaurant_orders> ordersArrayList1=new ArrayList<>();
           // holder.name.setText(holder.supervior_model.getName());
            if(!holder.supervior_model.getHotel_menu_count().equals("0")) {
                if (holder.supervior_model.getOrders().size() > 0) {
                    for (int i = 0; i < holder.supervior_model.getOrders().size(); i++) {
                        if (holder.supervior_model.getOrders().get(i).getStatus().equals("accepted")) {
                            Supervior_Model.Hotel_orders hotel_orders = new Supervior_Model.Hotel_orders();
                            hotel_orders.setCreated_at(holder.supervior_model.getOrders().get(i).getCreated_at());
                            hotel_orders.setDescription(holder.supervior_model.getOrders().get(i).getDescription());
                            hotel_orders.setGuest(holder.supervior_model.getOrders().get(i).getGuest());
                            hotel_orders.setGuest_id(holder.supervior_model.getOrders().get(i).getGuest_id());
                            hotel_orders.setGuest_signature(holder.supervior_model.getOrders().get(i).getGuest_signature());
                            hotel_orders.setHotel_id(holder.supervior_model.getOrders().get(i).getHotel_id());
                            hotel_orders.setId(holder.supervior_model.getOrders().get(i).getId());
                            hotel_orders.setIs_complementary(holder.supervior_model.getOrders().get(i).getIs_complementary());
                            hotel_orders.setNo_of_guest(holder.supervior_model.getOrders().get(i).getNo_of_guest());
                            hotel_orders.setOrder_detail(holder.supervior_model.getOrders().get(i).getOrder_detail());
                            hotel_orders.setOrder_menu_items(holder.supervior_model.getOrders().get(i).getOrder_menu_items());
                            hotel_orders.setDate(getDate1(holder.supervior_model.getOrders().get(i).getOrder_detail().getAccepted_at()));
                            hotel_orders.setPayment_status(holder.supervior_model.getOrders().get(i).getPayment_status());
                            hotel_orders.setPrimises(holder.supervior_model.getOrders().get(i).getPrimises());
                            hotel_orders.setPrimises_id(holder.supervior_model.getOrders().get(i).getPrimises_id());
                            hotel_orders.setStatus(holder.supervior_model.getOrders().get(i).getStatus());
                            hotel_orders.setTable_id(holder.supervior_model.getOrders().get(i).getTable_id());
                            hotel_orders.setType(holder.supervior_model.getOrders().get(i).getType());
                            hotel_orders.setUpdated_at(holder.supervior_model.getOrders().get(i).getUpdated_at());
                            long timeDiff = 0;
                            Date date1 = null;
                            Date date2 = null;
                            final Calendar calendar = Calendar.getInstance();
                            String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                            System.out.println("current date" + timeStamp + " " + holder.supervior_model.getOrders().get(i).getOrder_detail().getAccepted_at());
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                            long epoch = 0;

                            try {
                                dt = dateFormat.parse(holder.supervior_model.getOrders().get(i).getOrder_detail().getAccepted_at());
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
                            timeDiff = (date1.getTime() - date2.getTime()) - SystemClock.elapsedRealtime();
                            System.out.println(" time diff"
                                    + timeDiff);

                            hotel_orders.setTimedifference(timeDiff);
                            ordersArrayList.add(hotel_orders);
                        }
                    }

                    Collections.sort(ordersArrayList, new Comparator<Supervior_Model.Hotel_orders>() {
                        SimpleDateFormat f = new SimpleDateFormat("MMM dd, hh:mm a");

                        // SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy '@'hh:mm a");
                        @Override
                        public int compare(Supervior_Model.Hotel_orders lhs, Supervior_Model.Hotel_orders rhs) {
                            try {
                                return f.parse(rhs.getDate()).compareTo(f.parse(lhs.getDate()));
                            } catch (ParseException e) {
                                throw new IllegalArgumentException(e);
                            }
                        }
                    });

                    // holder.typename.setText("In Room Dinning");
                    IRD_Adapter1 ird_adapter1 = new IRD_Adapter1(ordersArrayList, context, holder.supervior_model.getName(), holder.supervior_model.getId());
                    holder.shimmerRecyclerView.setAdapter(ird_adapter1);
                } else {

                    ordersArrayList = holder.supervior_model.getOrders();
                    Collections.reverse(ordersArrayList);

                    IRD_Adapter1 ird_adapter1 = new IRD_Adapter1(ordersArrayList, context, holder.supervior_model.getName(), holder.supervior_model.getId());
                    holder.shimmerRecyclerView.setAdapter(ird_adapter1);
                }
            }
            else{

            }

      if(!holder.supervior_model.getRestaurant_menu_count().equals("0")) {
          if (holder.supervior_model.getRestaurant_property() != null) {

              if (holder.supervior_model.getRestaurant_orders().size() > 0) {
                  for (int i = 0; i < holder.supervior_model.getRestaurant_orders().size(); i++) {
                      if (holder.supervior_model.getRestaurant_orders().get(i).getStatus().equals("accepted")) {
                          Supervior_Model.Restaurant_orders hotel_orders = new Supervior_Model.Restaurant_orders();
                          hotel_orders.setCreated_at(holder.supervior_model.getRestaurant_orders().get(i).getCreated_at());
                          hotel_orders.setDescription(holder.supervior_model.getRestaurant_orders().get(i).getDescription());
                          hotel_orders.setGuest(holder.supervior_model.getRestaurant_orders().get(i).getGuest());
                          hotel_orders.setGuest_id(holder.supervior_model.getRestaurant_orders().get(i).getGuest_id());
                          hotel_orders.setGuest_signature(holder.supervior_model.getRestaurant_orders().get(i).getGuest_signature());
                          hotel_orders.setHotel_id(holder.supervior_model.getRestaurant_orders().get(i).getHotel_id());
                          hotel_orders.setId(holder.supervior_model.getRestaurant_orders().get(i).getId());
                          hotel_orders.setIs_complementary(holder.supervior_model.getRestaurant_orders().get(i).getIs_complementary());
                          hotel_orders.setNo_of_guest(holder.supervior_model.getRestaurant_orders().get(i).getNo_of_guest());
                          hotel_orders.setDate(getDate1(holder.supervior_model.getRestaurant_orders().get(i).getOrder_detail().getAccepted_at()));
                          hotel_orders.setOrder_detail(holder.supervior_model.getRestaurant_orders().get(i).getOrder_detail());
                          hotel_orders.setOrder_menu_items(holder.supervior_model.getRestaurant_orders().get(i).getOrder_menu_items());
                          hotel_orders.setPayment_status(holder.supervior_model.getRestaurant_orders().get(i).getPayment_status());
                          hotel_orders.setPrimises(holder.supervior_model.getRestaurant_orders().get(i).getPrimises());
                          hotel_orders.setPrimises_id(holder.supervior_model.getRestaurant_orders().get(i).getPrimises_id());
                          hotel_orders.setStatus(holder.supervior_model.getRestaurant_orders().get(i).getStatus());
                          hotel_orders.setTable_id(holder.supervior_model.getRestaurant_orders().get(i).getTable_id());
                          hotel_orders.setType(holder.supervior_model.getRestaurant_orders().get(i).getType());
                          hotel_orders.setUpdated_at(holder.supervior_model.getRestaurant_orders().get(i).getUpdated_at());
                          long timeDiff = 0;
                          Date date1 = null;
                          Date date2 = null;
                          final Calendar calendar = Calendar.getInstance();
                          String timeStamp = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(new Date());
                          System.out.println("current date" + timeStamp + " " + holder.supervior_model.getRestaurant_orders().get(i).getOrder_detail().getAccepted_at());
                          SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                          dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                          Date dt = null;//You will get date object relative to server/client timezone wherever it is parsed
                          long epoch = 0;

                          try {
                              dt = dateFormat.parse(holder.supervior_model.getRestaurant_orders().get(i).getOrder_detail().getAccepted_at());
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
                          timeDiff = (date1.getTime() - date2.getTime()) - SystemClock.elapsedRealtime();
                          System.out.println(" time diff"
                                  + timeDiff);

                          hotel_orders.setTimedifference(timeDiff);
                          ordersArrayList1.add(hotel_orders);
                      }
                  }
                  Collections.sort(ordersArrayList1, new Comparator<Supervior_Model.Restaurant_orders>() {
                      SimpleDateFormat f = new SimpleDateFormat("MMM dd, hh:mm a");

                      // SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy '@'hh:mm a");
                      @Override
                      public int compare(Supervior_Model.Restaurant_orders lhs, Supervior_Model.Restaurant_orders rhs) {
                          try {
                              return f.parse(rhs.getDate()).compareTo(f.parse(lhs.getDate()));
                          } catch (ParseException e) {
                              throw new IllegalArgumentException(e);
                          }
                      }
                  });

                  Restaurant_Adapter1 restaurant_adapter1 = new Restaurant_Adapter1(ordersArrayList1, getActivity(), holder.supervior_model.getRestaurant_property().getName(), holder.supervior_model.getId());
                  holder.shimmerRecyclerView2.setAdapter(restaurant_adapter1);

              } else {
                  Collections.reverse(ordersArrayList1);

                  Restaurant_Adapter1 restaurant_adapter1 = new Restaurant_Adapter1(ordersArrayList1, getActivity(), holder.supervior_model.getRestaurant_property().getName(), holder.supervior_model.getId());
                  holder.shimmerRecyclerView2.setAdapter(restaurant_adapter1);

              }
          }
      }







        }

        @Override
        public int getItemCount() {
            return supervior_models.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {

            TextView name;
            TextView typename;
            TextView typename1;
            TextView typename2;
            TextView irdlogin;
            TextView irdlogin1;
            TextView irdlogin2;
            ShimmerRecyclerView shimmerRecyclerView;
            ShimmerRecyclerView shimmerRecyclerView1;
            ShimmerRecyclerView shimmerRecyclerView2;
            Supervior_Model.Result supervior_model;
            TextView login;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.outletname);
                typename=itemView.findViewById(R.id.typename);
                typename1=itemView.findViewById(R.id.typename1);
                typename2=itemView.findViewById(R.id.typename2);
                irdlogin=itemView.findViewById(R.id.irdlogin);
                irdlogin1=itemView.findViewById(R.id.irdlogin1);
                irdlogin2=itemView.findViewById(R.id.irdlogin2);
                login=itemView.findViewById(R.id.login);
                shimmerRecyclerView=itemView.findViewById(R.id.recyclerview);
                shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                shimmerRecyclerView1=itemView.findViewById(R.id.recyclerview1);
                shimmerRecyclerView1.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                shimmerRecyclerView2=itemView.findViewById(R.id.recyclerview2);
                shimmerRecyclerView2.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            }
        }
    }

    class IRD_Adapter1 extends RecyclerView.Adapter<IRD_Adapter1.viewholder>{
        ArrayList<Supervior_Model.Hotel_orders> supervior_models;
        String name;
        String id;
        Context context;
        IRD_Adapter2 ird_adapter2;



        public IRD_Adapter1(ArrayList<Supervior_Model.Hotel_orders> supervior_models, Context context,String name,String id) {
            this.supervior_models=supervior_models;
            this.context=context;
            this.name=name;
            this.id=id;
             ird_adapter2=new IRD_Adapter2(supervior_models,context);
        }

        @NonNull
        @Override
        public IRD_Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.secondlist, parent, false);

            return new IRD_Adapter1.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IRD_Adapter1.viewholder holder, int position) {
           // holder.item=supervior_models.get(position);
                holder.outletname.setText("In Room Dining (" + name + " )");
            holder.typename.setText("Total Orders - ( "+supervior_models.size()+" )");

            holder.nofound.setText("No delayed order for dispatch.");
            if(supervior_models.size()!=0){
                holder.nofound.setVisibility(View.GONE);
            }
            else{
                holder.nofound.setVisibility(View.VISIBLE);
            }

                holder.irdlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Network.isNetworkAvailable(context)) {
                            GetMenumanagers(id, "ird");

                        } else if (Network.isNetworkAvailable2(context)) {
                            GetMenumanagers(id, "ird");

                        } else {

                        }
                    }
                });

                holder.recyclerView.setAdapter(ird_adapter2);


        }

        @Override
        public int getItemCount() {
                return 1;

        }

        public class viewholder extends RecyclerView.ViewHolder {

            TextView outletname;
            TextView nofound;
            TextView typename;
            TextView irdlogin;
            RecyclerView recyclerView;


            Supervior_Model.Hotel_orders item;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                irdlogin=itemView.findViewById(R.id.irdlogin);
                nofound=itemView.findViewById(R.id.nofound);
                typename=itemView.findViewById(R.id.typename);
                outletname=itemView.findViewById(R.id.outletname);
                recyclerView=itemView.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
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
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }
    class IRD_Adapter2 extends RecyclerView.Adapter<IRD_Adapter2.viewholder>{
        ArrayList<Supervior_Model.Hotel_orders> supervior_models;
        String name;
        String id;
        Context context;

        private Handler mHandler = new Handler();
        ArrayList<viewholder> lstHolders;
        Timer tmr;
        private Runnable updateRemainingTimeRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                synchronized (lstHolders) {
                    long currentTime = SystemClock.elapsedRealtime();
                    for (IRD_Adapter2.viewholder holder : lstHolders) {

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



        public IRD_Adapter2(ArrayList<Supervior_Model.Hotel_orders> supervior_models, Context context) {
            this.supervior_models=supervior_models;
            this.context=context;
            this.name=name;
            this.id=id;
            mHandler = new Handler();

            lstHolders = new ArrayList<>();

            startUpdateTimer();

        }

        @NonNull
        @Override
        public IRD_Adapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item1, parent, false);

            return new IRD_Adapter2.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final IRD_Adapter2.viewholder holder, int position) {
            holder.item=supervior_models.get(position);
            if(holder.item.getPrimises()!=null){
                holder.roomno.setText(holder.item.getPrimises().getPremise_no());

            }
            else{

            }
            if(holder.item.getIs_complementary().equals("1")){
                holder.gift.setVisibility(View.VISIBLE);
            }
            else{
                holder.gift.setVisibility(View.GONE);

            }
            holder.stage.setText("DISPATCH");
            holder.ordertime.setText(getDate1(holder.item.getCreated_at()));

            holder.setData(holder.item);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
            holder.updateTimeRemaining(SystemClock.elapsedRealtime());

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.item.getGuest().getName().equals("Guest")) {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.supervisor_detailwithoutguest);
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
                        TextView dispatchedtext=dialog.findViewById(R.id.dispatchedtext);
                        acceptedat.setTypeface(holder.font);
                        dispatchedtext.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView dispatched=dialog.findViewById(R.id.dispatched);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        TextView statustext = dialog.findViewById(R.id.statustext);
                        statustext.setTypeface(holder.font);
                        TextView status = dialog.findViewById(R.id.status);
                        if (holder.item.getPayment_status().equals("offline")) {
                            status.setText("Settle Later");

                        } else {
                            status.setText(holder.item.getPayment_status());

                        }
                        dispatchbutton.setText("ACCEPT");
                        dispatchbutton.setVisibility(View.GONE);
                        guests.setText(holder.item.getNo_of_guest());
                        guests.setTextColor(context.getResources().getColor(R.color.mehroon));
                        name.setText(holder.item.getGuest().getName());

                        if(holder.item.getOrder_detail().getDispatched_at()!=null){
                            dispatched.setText(getDate1(holder.item.getOrder_detail().getDispatched_at()));
                        }
                        else{
                            dispatched.setText("-");

                        }

                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.item.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.item.getCreated_at()));

                        orderinsdetails.setText(holder.item.getDescription());


                     Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.item.getOrder_menu_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if(holder.item.getOrder_detail().getAccepted_at()!=null){
                            accepted.setText(getDate1(holder.item.getOrder_detail().getAccepted_at()));

                        }
                        else{
                            accepted.setText("-");

                        }


                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });



                    }
                    else {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.supervisor_detail);
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
                        TextView dispatchedtext=dialog.findViewById(R.id.dispatchedtext);
                        acceptedat.setTypeface(holder.font);
                        dispatchedtext.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView dispatched=dialog.findViewById(R.id.dispatched);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        dispatchbutton.setVisibility(View.INVISIBLE);
                        TextView statustext = dialog.findViewById(R.id.statustext);
                        statustext.setTypeface(holder.font);
                        TextView status = dialog.findViewById(R.id.status);
                        if (holder.item.getPayment_status().equals("offline")) {
                            status.setText("Settle Later");

                        } else {
                            status.setText(holder.item.getPayment_status());

                        }
                        dispatchbutton.setText("ACCEPT");
                        guests.setText(holder.item.getNo_of_guest());
                        guests.setTextColor(context.getResources().getColor(R.color.mehroon));
                        name.setText(holder.item.getGuest().getName());


                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.item.getPrimises().getPremise_no());
                        since.setText(getDate1(holder.item.getCreated_at()));

                        orderinsdetails.setText(holder.item.getDescription());


                        Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.item.getOrder_menu_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if(holder.item.getOrder_detail().getAccepted_at()!=null){
                            accepted.setText(getDate1(holder.item.getOrder_detail().getAccepted_at()));

                        }
                        else{
                            accepted.setText("-");

                        }
                        if(holder.item.getOrder_detail().getDispatched_at()!=null){
                            dispatched.setText(getDate1(holder.item.getOrder_detail().getDispatched_at()));
                        }
                        else{
                            dispatched.setText("-");

                        }



                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        back.setOnClickListener(new View.OnClickListener() {
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
            return supervior_models.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView roomno;
            CardView parent;
            TextView ordertime;
            TextView roomtext;
            TextView stage;
            TextView outletname;
            ImageView gift;
            TextView irdlogin;
            TextView delayed;
            private Animation anim;
            final Typeface font1;
            final Typeface font;

            Supervior_Model.Hotel_orders item;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno=itemView.findViewById(R.id.roomt);
                roomtext=itemView.findViewById(R.id.roomtext);
                ordertime=itemView.findViewById(R.id.ordertime);
                parent=itemView.findViewById(R.id.parent);
                gift=itemView.findViewById(R.id.gift);
                stage=itemView.findViewById(R.id.stage);
                anim = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
                delayed=itemView.findViewById(R.id.counter);

                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
            }
            public void setData(Supervior_Model.Hotel_orders item) {
                this.item= item;
                updateTimeRemaining(SystemClock.elapsedRealtime());



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
                    long newtime = item.getTimedifference() + currentTime;
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
                            if(item.getStatus().equals("new_order")){
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

                                delayed.setText(String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");


                            }else{

                                delayed.setText(String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            }






                        } else if (hours == 01 || hours == 1) {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);

                                delayed.setText(String.valueOf(hours) + " hr : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");


                            } else {

                                delayed.setText(String.valueOf(hours) + " hr : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");

                            }



                        } else {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);





                                delayed.setText(String.valueOf(hours) + " hrs : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");


                            } else {

                                delayed.setText(String.valueOf(hours) + " hrs : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");

                            }

                        }
                    } else {
                        if (elapsedDays == 1 || elapsedDays == 01) {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);





                                delayed.setText(String.valueOf(elapsedDays) + " Day");


                            } else {

                                delayed.setText(String.valueOf(elapsedDays) + " Day");

                            }

                        } else {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);





                                delayed.setText(String.valueOf(elapsedDays) + " Days");


                            } else {

                                delayed.setText(String.valueOf(elapsedDays) + " Days");

                            }

                        }
                    }
                }catch (Exception e){

                }
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
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }
    class Restaurant_Adapter1 extends RecyclerView.Adapter<Restaurant_Adapter1.viewholder>{
        ArrayList<Supervior_Model.Restaurant_orders> supervior_models;
        Context context;
        String name;
        String id;
        Restaurant_Adapter2 ird_adapter2;






        public Restaurant_Adapter1(ArrayList<Supervior_Model.Restaurant_orders> supervior_models, Context context,String name,String id) {
            this.supervior_models=supervior_models;
            this.context=context;
            this.name=name;
            this.id=id;
            ird_adapter2=new Restaurant_Adapter2(supervior_models,getActivity());

        }

        @NonNull
        @Override
        public Restaurant_Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.secondlist, parent, false);

            return new Restaurant_Adapter1.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Restaurant_Adapter1.viewholder holder, int position) {
           // holder.item=supervior_models.get(position);
                holder.outletname.setText(name);
                holder.nofound.setText("No delayed order for dispatch.");
            holder.typename.setText("Total Orders - ( "+supervior_models.size()+" )");

            if(supervior_models.size()!=0){
                holder.nofound.setVisibility(View.GONE);
            }
            else{
                holder.nofound.setVisibility(View.VISIBLE);
            }

                holder.irdlogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Network.isNetworkAvailable(context)) {
                            GetMenumanagers(id, "ird_restaurant");

                        } else if (Network.isNetworkAvailable2(context)) {
                            GetMenumanagers(id, "ird_restaurant");

                        } else {

                        }
                    }
                });

                holder.recyclerView.setAdapter(ird_adapter2);


        }

        @Override
        public int getItemCount() {
                return 1;

        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView outletname;
            TextView irdlogin;
            TextView typename;
            TextView nofound;
            RecyclerView recyclerView;



            Supervior_Model.Restaurant_orders item;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                irdlogin=itemView.findViewById(R.id.irdlogin);
                typename=itemView.findViewById(R.id.typename);
                nofound=itemView.findViewById(R.id.nofound);
                outletname=itemView.findViewById(R.id.outletname);
                recyclerView=itemView.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

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
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }
    class Restaurant_Adapter2 extends RecyclerView.Adapter<Restaurant_Adapter2.viewholder>{
        ArrayList<Supervior_Model.Restaurant_orders> supervior_models;
        String name;
        String id;
        Context context;

        private Handler mHandler = new Handler();
        ArrayList<viewholder> lstHolders;
        Timer tmr;
        private Runnable updateRemainingTimeRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                synchronized (lstHolders) {
                    long currentTime = SystemClock.elapsedRealtime();
                    for (Restaurant_Adapter2.viewholder holder : lstHolders) {

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



        public Restaurant_Adapter2(ArrayList<Supervior_Model.Restaurant_orders> supervior_models, Context context) {
            this.supervior_models=supervior_models;
            this.context=context;
            this.name=name;
            this.id=id;
            mHandler = new Handler();

            lstHolders = new ArrayList<>();

            startUpdateTimer();

        }

        @NonNull
        @Override
        public Restaurant_Adapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item1, parent, false);

            return new Restaurant_Adapter2.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final Restaurant_Adapter2.viewholder holder, int position) {
            holder.item=supervior_models.get(position);
            holder.roomtext.setText("Table No.");
            if(holder.item.getPrimises()!=null){
                holder.roomno.setText(holder.item.getPrimises().getTable_no());

            }
            else{

            }

            holder.stage.setText("DISPATCH");
            holder.ordertime.setText(getDate1(holder.item.getCreated_at()));

            holder.setData(holder.item);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
            holder.updateTimeRemaining(SystemClock.elapsedRealtime());

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.item.getGuest().getName().equals("Guest")) {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.supervisor_detailwithoutguest);
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
                        TextView dispatchedtext=dialog.findViewById(R.id.dispatchedtext);
                        acceptedat.setTypeface(holder.font);
                        dispatchedtext.setTypeface(holder.font);
                        dispatchedtext.setText("ORDER SERVED AT");

                        TextView accepted=dialog.findViewById(R.id.accepted);
                        TextView dispatched=dialog.findViewById(R.id.dispatched);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        TextView statustext = dialog.findViewById(R.id.statustext);
                        statustext.setTypeface(holder.font);
                        TextView status = dialog.findViewById(R.id.status);
                        if (holder.item.getPayment_status().equals("offline")) {
                            status.setText("Settle Later");

                        } else {
                            status.setText(holder.item.getPayment_status());

                        }
                        dispatchbutton.setText("ACCEPT");
                        dispatchbutton.setVisibility(View.GONE);
                        guests.setText(holder.item.getNo_of_guest());
                        guests.setTextColor(context.getResources().getColor(R.color.mehroon));
                        name.setText(holder.item.getGuest().getName());

                        if(holder.item.getOrder_detail().getDispatched_at()!=null){
                            dispatched.setText(getDate1(holder.item.getOrder_detail().getDispatched_at()));
                        }
                        else{
                            dispatched.setText("-");

                        }

                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.item.getPrimises().getTable_no());
                        since.setText(getDate1(holder.item.getCreated_at()));

                        orderinsdetails.setText(holder.item.getDescription());


                        Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.item.getOrder_menu_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if(holder.item.getOrder_detail().getAccepted_at()!=null){
                            accepted.setText(getDate1(holder.item.getOrder_detail().getAccepted_at()));

                        }
                        else{
                            accepted.setText("-");

                        }


                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });



                    }
                    else {
                        final Dialog dialog = new Dialog(context);
                        // Include dialog.xml file

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.supervisor_detail);
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
                        TextView dispatchedtext=dialog.findViewById(R.id.dispatchedtext);
                        acceptedat.setTypeface(holder.font);
                        dispatchedtext.setTypeface(holder.font);
                        TextView accepted=dialog.findViewById(R.id.accepted);
                        dispatchedtext.setText("ORDER SERVED AT");

                        TextView dispatched=dialog.findViewById(R.id.dispatched);
                        TextView dispatchbutton = dialog.findViewById(R.id.dispatch);
                        dispatchbutton.setVisibility(View.INVISIBLE);
                        TextView statustext = dialog.findViewById(R.id.statustext);
                        statustext.setTypeface(holder.font);
                        TextView status = dialog.findViewById(R.id.status);
                        if (holder.item.getPayment_status().equals("offline")) {
                            status.setText("Settle Later");

                        } else {
                            status.setText(holder.item.getPayment_status());

                        }
                        dispatchbutton.setText("ACCEPT");
                        guests.setText(holder.item.getNo_of_guest());
                        guests.setTextColor(context.getResources().getColor(R.color.mehroon));
                        name.setText(holder.item.getGuest().getName());


                        RecyclerView orderitemsdetail = dialog.findViewById(R.id.orderitemsdetail);
                        orderitemsdetail.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        roomno.setText(holder.item.getPrimises().getTable_no());
                        since.setText(getDate1(holder.item.getCreated_at()));

                        orderinsdetails.setText(holder.item.getDescription());


                        Order_ItemAdapterdetail order_itemAdapterdetail = new Order_ItemAdapterdetail(holder.item.getOrder_menu_items(), context);
                        orderitemsdetail.setAdapter(order_itemAdapterdetail);
                        LinearLayout colorimage = dialog.findViewById(R.id.colorimage);
                        if(holder.item.getOrder_detail().getAccepted_at()!=null){
                            accepted.setText(getDate1(holder.item.getOrder_detail().getAccepted_at()));

                        }
                        else{
                            accepted.setText("-");

                        }
                        if(holder.item.getOrder_detail().getDispatched_at()!=null){
                            dispatched.setText(getDate1(holder.item.getOrder_detail().getDispatched_at()));
                        }
                        else{
                            dispatched.setText("-");

                        }



                        colorimage.setBackgroundColor(context.getResources().getColor(R.color.red));
                        since.setTextColor(context.getResources().getColor(R.color.gray));
                        roomno.setTextColor(context.getResources().getColor(R.color.mehroon));

                        back.setOnClickListener(new View.OnClickListener() {
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
            return supervior_models.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView roomno;
            TextView ordertime;
            TextView roomtext;
            CardView parent;
            TextView stage;
            TextView outletname;
            TextView irdlogin;
            TextView delayed;
            private Animation anim;
            final Typeface font1;
            final Typeface font;

            Supervior_Model.Restaurant_orders item;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno=itemView.findViewById(R.id.roomt);
                roomtext=itemView.findViewById(R.id.roomtext);
                ordertime=itemView.findViewById(R.id.ordertime);
                stage=itemView.findViewById(R.id.stage);
                parent=itemView.findViewById(R.id.parent);
                anim = AnimationUtils.loadAnimation(context, R.anim.fade_anim);
                delayed=itemView.findViewById(R.id.counter);

                font = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/Roboto-Regular.ttf");

                font1 = Typeface.createFromAsset(
                        context.getAssets(),
                        "font/verdana.ttf");
            }
            public void setData(Supervior_Model.Restaurant_orders item) {
                this.item= item;
                updateTimeRemaining(SystemClock.elapsedRealtime());



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
                    long newtime = item.getTimedifference() + currentTime;
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
                            if(item.getStatus().equals("new_order")){
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

                                delayed.setText(String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");


                            }else{

                                delayed.setText(String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");
                            }






                        } else if (hours == 01 || hours == 1) {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);

                                delayed.setText(String.valueOf(hours) + " hr : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");


                            } else {

                                delayed.setText(String.valueOf(hours) + " hr : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");

                            }



                        } else {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);





                                delayed.setText(String.valueOf(hours) + " hrs : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");


                            } else {

                                delayed.setText(String.valueOf(hours) + " hrs : " + String.valueOf(minutes) + " m : " + String.valueOf(seconds) + " s");

                            }

                        }
                    } else {
                        if (elapsedDays == 1 || elapsedDays == 01) {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);





                                delayed.setText(String.valueOf(elapsedDays) + " Day");


                            } else {

                                delayed.setText(String.valueOf(elapsedDays) + " Day");

                            }

                        } else {
                            if (item.getStatus().equals("new_order")) {
                                roomno.startAnimation(anim);





                                delayed.setText(String.valueOf(elapsedDays) + " Days");


                            } else {

                                delayed.setText(String.valueOf(elapsedDays) + " Days");

                            }

                        }
                    }
                }catch (Exception e){

                }
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
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, hh:mm a");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            return dateStr;
        }
    }



    /*
    class Supervisor_Adapter1 extends RecyclerView.Adapter<Supervisor_Adapter1.viewholder>{
        ArrayList<Supervior_Model.Orders> supervior_models;
        Context context;

        public Supervisor_Adapter1(ArrayList<Supervior_Model.Orders> supervior_models, Context context) {
            this.supervior_models=supervior_models;
            this.context=context;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.secondlist, parent, false);

            return new viewholder(view);    }

        @Override
        public void onBindViewHolder(@NonNull Supervisor_Adapter1.viewholder holder, int position) {
            holder.item=supervior_models.get(position);

        }

        @Override
        public int getItemCount() {
            return supervior_models.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView roomno;
            TextView ordertime;
            TextView roomtext;
            TextView stage;
            TextView delayed;
            Supervior_Model.Orders item;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                roomno=itemView.findViewById(R.id.roomt);
                roomtext=itemView.findViewById(R.id.roomtext);
                ordertime=itemView.findViewById(R.id.ordertime);
                stage=itemView.findViewById(R.id.stage);
                delayed=itemView.findViewById(R.id.counter);
            }
        }
    }
*/
    class Login_Adapter1 extends RecyclerView.Adapter<Login_Adapter1.viewholder>{
        ArrayList<Supervisor_Managers.Result> supervior_models;
        Context context;
        public  final SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

        public  final SimpleDateFormat EEEddMMMyyyy = new SimpleDateFormat("EEE dd MMM, hh:mm a", Locale.US);
        public Login_Adapter1(ArrayList<Supervisor_Managers.Result> supervior_models, Context context) {
            this.supervior_models=supervior_models;
            this.context=context;
        }

        @NonNull
        @Override
        public Login_Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shift_layout, parent, false);

            return new Login_Adapter1.viewholder(view);    }

        @Override
        public void onBindViewHolder(@NonNull Login_Adapter1.viewholder holder, int position) {
            holder.item=supervior_models.get(position);
            holder.name.setText(holder.item.getName());
            holder.shift.setText(parseDate(holder.item.getLoggedin_at(),ymdFormat,EEEddMMMyyyy));


        }

        @Override
        public int getItemCount() {
            return supervior_models.size();
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView name;
            TextView shift;

            Supervisor_Managers.Result item;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.user);
                shift=itemView.findViewById(R.id.time);

            }
        }

    }

    public static String parseDate(String inputDateString, SimpleDateFormat inputDateFormat, SimpleDateFormat outputDateFormat) {
        Date date = null;
        String outputDateString = null;
        try {
            date = inputDateFormat.parse(inputDateString);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }
    //--------------End----------------------------------------------------------------//
    public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<Order_ItemAdapterdetail.ViewHolder>{
        ArrayList<Supervior_Model.Order_menu_items> order_items;
        Context context;

        public Order_ItemAdapterdetail(ArrayList<Supervior_Model.Order_menu_items> order_items, Context context) {
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
            Supervior_Model.Order_menu_items mitem;
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
    public class AddonsAdatpter extends  RecyclerView.Adapter<AddonsAdatpter.ViewHolder>{
        ArrayList<Supervior_Model.Order_addons> order_items;
        Context context;

        public AddonsAdatpter(ArrayList<Supervior_Model.Order_addons> order_items, Context context) {
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
            Supervior_Model.Order_addons mitem;

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
    }    }
