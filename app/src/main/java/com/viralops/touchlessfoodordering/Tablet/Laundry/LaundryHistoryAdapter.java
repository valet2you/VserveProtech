package com.viralops.touchlessfoodordering.Tablet.Laundry;

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
import androidx.recyclerview.widget.RecyclerView;

import com.viralops.touchlessfoodordering.Model.LaundryOrderHistory1;
import com.viralops.touchlessfoodordering.Model.OrderHistory;
import com.viralops.touchlessfoodordering.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LaundryHistoryAdapter extends RecyclerView.Adapter<LaundryHistoryAdapter.viewholder>{
    ArrayList<LaundryOrderHistory1.Data> homeViewModels;
    Context context;

    public LaundryHistoryAdapter(Context context, ArrayList<LaundryOrderHistory1.Data> homeViewModels) {
        this.context=context;
        this.homeViewModels=homeViewModels;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.laundr_order_historylits, parent, false);

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
        if(holder.mitem.getOrder_stage()!=null){

            if(holder.mitem.getOrder_stage().getStage().equals("other")){
                holder.guests.setText(capitailizeWord(holder.mitem.getOrder_stage().getStage().replaceAll("_"," ")));
            }
            else {
                holder.guests.setText(capitailizeWord(holder.mitem.getOrder_stage().getStage().replaceAll("_"," ")));
            }
        }
        else{
            holder.guests.setText("-");

        }


            holder.colorimage.setBackgroundColor(context.getResources().getColor(R.color.light_green));
            holder.orderstatus.setTextColor(context.getResources().getColor(R.color.gray));
            holder.roomno.setTextColor(context.getResources().getColor(R.color.mogiya));
            holder.guests.setTextColor(context.getResources().getColor(R.color.mogiya));







    }
    public void filterList(ArrayList<LaundryOrderHistory1.Data> filterdNames) {
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

        LinearLayout colorimage;
        LaundryOrderHistory1.Data mitem;



         public viewholder(@NonNull View itemView) {
             super(itemView);
             roomno=itemView.findViewById(R.id.roomno);
             guests=itemView.findViewById(R.id.guest);
             orderrecived=itemView.findViewById(R.id.orderreceivedtext);
             orderstatus=itemView.findViewById(R.id.deliveredat);
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
                     dialog.setContentView(R.layout.laundryhistory_detail);
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
                     TextView statustext=dialog.findViewById(R.id.statustext);
                     TextView status=dialog.findViewById(R.id.status);
                     TextView reason=dialog.findViewById(R.id.other);
                     TextView distpatchtext=dialog.findViewById(R.id.distpatchtext);
                     distpatchtext.setTypeface(font);
                     statustext.setTypeface(font);
                     TextView dispatcg=dialog.findViewById(R.id.dispactch);
                     RecyclerView orderitemsdetail=dialog.findViewById(R.id.orderitemsdetail);
                     orderitemsdetail.setLayoutManager(new GridLayoutManager(context,2));
                     name.setText(capitailizeWord(homeViewModels.get(getAdapterPosition()).getGuest().getName()));
                     roomno.setText(homeViewModels.get(getAdapterPosition()).getPrimises().getPremise_no());
                     guests.setText("Cleared");
                     if(homeViewModels.get(getAdapterPosition()).getOrder_stage()!=null){
                         statustext.setVisibility(View.VISIBLE);
                         status.setVisibility(View.VISIBLE);
                         if(homeViewModels.get(getAdapterPosition()).getOrder_stage().getStage().equals("other")){
                             reason.setVisibility(View.VISIBLE);
                             status.setText(capitailizeWord(homeViewModels.get(getAdapterPosition()).getOrder_stage().getStage().replaceAll("_"," ")));
                              reason.setText(capitailizeWord(homeViewModels.get(getAdapterPosition()).getOrder_stage().getStage_text()));
                         }
                         else {
                             reason.setVisibility(View.GONE);
                             status.setText(capitailizeWord(homeViewModels.get(getAdapterPosition()).getOrder_stage().getStage().replaceAll("_"," ")));
                         }
                     }
                     else{
                         statustext.setVisibility(View.GONE);
                         status.setVisibility(View.GONE);
                         reason.setVisibility(View.GONE);
                     }
                     since.setText(getDate1(homeViewModels.get(getAdapterPosition()).getOrder_detail().getCreated_at()));

                         orderinsdetails.setText(getDatenew(homeViewModels.get(getAdapterPosition()).getOrder_detail().getRequested_pickup_at()));


                     Order_ItemAdapterdetail order_itemAdapterdetail=new Order_ItemAdapterdetail(homeViewModels.get(getAdapterPosition()).getOrder_laundry_items(),context);
                     orderitemsdetail.setAdapter(order_itemAdapterdetail);
                     LinearLayout colorimage=dialog.findViewById(R.id.colorimage);

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
        ArrayList<OrderHistory.Order_addons> order_items;
        Context context;

        public AddonsAdatpter(ArrayList<OrderHistory.Order_addons> order_items, Context context) {
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
        ArrayList<LaundryOrderHistory1.Order_laundry_items> order_items;
        Context context;

        public Order_ItemAdapterdetail(ArrayList<LaundryOrderHistory1.Order_laundry_items> order_items, Context context) {
            this.order_items = order_items;
            this.context = context;
        }

        @NonNull
        @Override
        public Order_ItemAdapterdetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.laundryorder_items, parent, false);
            return new Order_ItemAdapterdetail.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Order_ItemAdapterdetail.ViewHolder holder, int position) {
            holder.mitem=order_items.get(position);
            holder.name.setText(holder.mitem.getQuantity()+" X "+holder.mitem.getItem().getName());
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
            LaundryOrderHistory1.Order_laundry_items mitem;
            TextView addonslist;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                addonslist=itemView.findViewById(R.id.addonslist);
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
