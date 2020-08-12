package com.viralops.touchlessfoodordering.Tablet.IRD;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.viralops.touchlessfoodordering.BuildConfig;
import com.viralops.touchlessfoodordering.Model.OrderHistory;
import com.viralops.touchlessfoodordering.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewholder>{
    ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Data> homeViewModels;
    Context context;

    public HistoryAdapter(Context context, ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Data> homeViewModels) {
        this.context=context;
        this.homeViewModels=homeViewModels;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_historylits, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {
        holder.mitem=homeViewModels.get(position);
        holder.roomno.setText(holder.mitem.getPrimises().getPremise_no());
        holder.guests.setText(holder.mitem.getNo_of_guest());
        holder.orderrecived.setText(getDate1(holder.mitem.getOrder_detail().getCreated_at()));
        holder.orderstatus.setText(getDate1(holder.mitem.getOrder_detail().getCleared_at()));
        holder.acceptedat.setText(getDate1(holder.mitem.getOrder_detail().getAccepted_at()));
        if(holder.mitem.getIs_complementary().equals("1")){
            holder.gift.setVisibility(View.VISIBLE);
        }
        else{
            holder.gift.setVisibility(View.INVISIBLE);
        }





            holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
            holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
            holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
            holder.guests.setTextColor(context.getResources().getColor(R.color.mogiya));







    }
    public void filterList(ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Data> filterdNames) {
        this.homeViewModels = filterdNames;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return homeViewModels.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
         public TextView roomno;
         public TextView guests;
         public TextView orderrecived;
         public  TextView orderstatus;
         public  TextView acceptedat;
         public ImageView gift;

        LinearLayout colorimage;
        com.viralops.touchlessfoodordering.Model.OrderHistory.Data mitem;



         public viewholder(@NonNull View itemView) {
             super(itemView);
             roomno=itemView.findViewById(R.id.roomno);
             guests=itemView.findViewById(R.id.guest);
             orderrecived=itemView.findViewById(R.id.orderreceivedtext);
             orderstatus=itemView.findViewById(R.id.deliveredat);
             gift=itemView.findViewById(R.id.filter);
             acceptedat=itemView.findViewById(R.id.acceptedat);
             colorimage=itemView.findViewById(R.id.colorimage);

             final Typeface font = Typeface.createFromAsset(
                     context.getAssets(),
                     "font/Roboto-Regular.ttf");

             Typeface font1 = Typeface.createFromAsset(
                     context.getAssets(),
                     "font/Roboto-Thin.ttf");
             roomno.setTypeface(font1);
             guests.setTypeface(font1);
             orderrecived.setTypeface(font1);
             orderstatus.setTypeface(font1);
             acceptedat.setTypeface(font1);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     final Dialog dialog = new Dialog(context);
                     // Include dialog.xml file

                     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                     // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                     dialog.setContentView(R.layout.history_detail_item_name);
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
                     TextView distpatchtext=dialog.findViewById(R.id.distpatchtext);
                     distpatchtext.setTypeface(font);
                     TextView dispatcg=dialog.findViewById(R.id.dispactch);
                     TextView statustext=dialog.findViewById(R.id.statustext);
                     statustext.setTypeface(font);
                     TextView status=dialog.findViewById(R.id.status);
                     TextView name=dialog.findViewById(R.id.name);
                     LinearLayout Signature=dialog.findViewById(R.id.signature);
                     RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                     orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                     roomno.setText(homeViewModels.get(getAdapterPosition()).getPrimises().getPremise_no());
                     guests.setText(homeViewModels.get(getAdapterPosition()).getNo_of_guest());
                     since.setText(getDate1(homeViewModels.get(getAdapterPosition()).getOrder_detail().getCreated_at()));
                     if (homeViewModels.get(getAdapterPosition()).getDescription() != null) {
                         orderinsdetails.setText(homeViewModels.get(getAdapterPosition()).getDescription());

                     }
                     else{
                         orderinsdetails.setText("-");

                     }

                      if(homeViewModels.get(getAdapterPosition()).getPayment_status().equals("offline")){
                          status.setText("SETTLE LATER");
                      }
                      else{
                          status.setText(homeViewModels.get(getAdapterPosition()).getPayment_status());

                      }
                          if(homeViewModels.get(getAdapterPosition()).getGuest()!=null){
                              name.setText(homeViewModels.get(getAdapterPosition()).getGuest().getName());


                          }
                          else{
                              name.setText("Guest");

                          }



                     if(homeViewModels.get(getAdapterPosition()).getGuest_signature()==null){
                         Signature.setVisibility(View.GONE);

                     }
                     else{
                         Signature.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 final Dialog dialog1 = new Dialog(context);
                                 // Include dialog.xml file

                                 dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                 // dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                 dialog1.setContentView(R.layout.signature_layout);
                                 int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.48);
                                 int height = (int)(context.getResources().getDisplayMetrics().heightPixels*0.90);
                                 dialog1.getWindow().setGravity(Gravity.CENTER_VERTICAL);

                                 dialog1.getWindow().setLayout(width, height);

                                 dialog1.setCancelable(false);
                                 // Set dialog title
                                 dialog1.setTitle("");
                                 dialog1.show();

                                 final ImageView image=dialog1.findViewById(R.id.image);
                                 ImageView close =dialog1.findViewById(R.id.close);
                                 Picasso.with(context).load(BuildConfig.imageurlsig+homeViewModels.get(getAdapterPosition()).getGuest_signature()).into(image);

                                 close.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         dialog1.dismiss();
                                     }
                                 });

                             }
                         });



                     }
                   Order_ItemAdapterdetail order_itemAdapterdetail=new Order_ItemAdapterdetail(homeViewModels.get(getAdapterPosition()).getOrder_menu_items(),context);
                     orderitemsdetail.setAdapter(order_itemAdapterdetail);
                     LinearLayout colorimage=dialog.findViewById(R.id.colorimage);
                     guests.setText(homeViewModels.get(getAdapterPosition()).getNo_of_guest());

                         accepted.setText(getDate1(homeViewModels.get(getAdapterPosition()).getOrder_detail().getAccepted_at()));
                         dispatcg.setText(getDate1(homeViewModels.get(getAdapterPosition()).getOrder_detail().getCleared_at()));




                         colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
                         since.setTextColor(context.getResources().getColor(R.color.gray));
                         roomno.setTextColor(context.getResources().getColor(R.color.mogiya));



                     back.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             dialog.dismiss();
                         }
                     });

                 }
             });
         }
        

     }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }
    public class AddonsAdatpter extends  RecyclerView.Adapter<AddonsAdatpter.ViewHolder>{
        ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Order_addons> order_items;
        Context context;

        public AddonsAdatpter(ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Order_addons> order_items, Context context) {
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
    public class Order_ItemAdapterdetail extends  RecyclerView.Adapter<Order_ItemAdapterdetail.ViewHolder>{
        ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Order_menu_items> order_items;
        Context context;

        public Order_ItemAdapterdetail(ArrayList<com.viralops.touchlessfoodordering.Model.OrderHistory.Order_menu_items> order_items, Context context) {
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
            com.viralops.touchlessfoodordering.Model.OrderHistory.Order_menu_items mitem;
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
