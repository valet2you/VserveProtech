package com.viralops.touchlessfoodordering.Mobile.Laundry;

import java.util.ArrayList;

public class Laundry_Dashboard1 {
    private ArrayList<Data> data;

    private String message;

    public ArrayList<Data> getData ()
    {
        return data;
    }

    public void setData (ArrayList<Data> data)
    {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", message = "+message+"]";
    }
    public static class Data
    {
        private String hotel_id;

        private String payment_status;
        private String orderid;
        private String roomno;
        private String detail;
        private String O_id;
        private String timestring;
        private String datenew;
        private int date;
        private int month;
        private int year;
        private Order_stage order_stage;

        private int hour;
        private int minute;
        private String datestring;
        private String typenotify;

        private String created_at;

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public void setRoomno(String roomno) {
            this.roomno = roomno;
        }

        public String getOrderid() {
            return orderid;
        }

        public String getRoomno() {
            return roomno;
        }

        public String getDetail() {
            return detail;
        }

        public String getO_id() {
            return O_id;
        }

        public String getTimestring() {
            return timestring;
        }

        public Order_stage getOrder_stage() {
            return order_stage;
        }

        public void setOrder_stage(Order_stage order_stage) {
            this.order_stage = order_stage;
        }

        public String getDatenew() {
            return datenew;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setO_id(String o_id) {
            O_id = o_id;
        }

        public void setTimestring(String timestring) {
            this.timestring = timestring;
        }

        public void setDatenew(String datenew) {
            this.datenew = datenew;
        }

        private String description;

        private String payment_receipt;

        private String type;

        private String table_id;

        private Order_detail order_detail;

        private String no_of_guest;

        private String bill_pdf;

        private String guest_signature;

        private String updated_at;

        private String guest_id;

        private ArrayList<Order_laundry_items> order_laundry_items;

        private Primises primises;

        private String primises_id;

        private Guest guest;

        private String id;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public String getDatestring() {
            return datestring;
        }

        public void setDatestring(String datestring) {
            this.datestring = datestring;
        }

        public String getTypenotify() {
            return typenotify;
        }

        public void setTypenotify(String typenotify) {
            this.typenotify = typenotify;
        }

        private String status;

        public String getHotel_id ()
        {
            return hotel_id;
        }

        public void setHotel_id (String hotel_id)
        {
            this.hotel_id = hotel_id;
        }

        public String getPayment_status ()
        {
            return payment_status;
        }

        public void setPayment_status (String payment_status)
        {
            this.payment_status = payment_status;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getDescription ()
    {
        return description;
    }

        public void setDescription (String description)
        {
            this.description = description;
        }

        public String getPayment_receipt ()
    {
        return payment_receipt;
    }

        public void setPayment_receipt (String payment_receipt)
        {
            this.payment_receipt = payment_receipt;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getTable_id ()
    {
        return table_id;
    }

        public void setTable_id (String table_id)
        {
            this.table_id = table_id;
        }

        public Order_detail getOrder_detail ()
        {
            return order_detail;
        }

        public void setOrder_detail (Order_detail order_detail)
        {
            this.order_detail = order_detail;
        }

        public String getNo_of_guest ()
    {
        return no_of_guest;
    }

        public void setNo_of_guest (String no_of_guest)
        {
            this.no_of_guest = no_of_guest;
        }

        public String getBill_pdf ()
    {
        return bill_pdf;
    }

        public void setBill_pdf (String bill_pdf)
        {
            this.bill_pdf = bill_pdf;
        }

        public String getGuest_signature ()
    {
        return guest_signature;
    }

        public void setGuest_signature (String guest_signature)
        {
            this.guest_signature = guest_signature;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getGuest_id ()
        {
            return guest_id;
        }

        public void setGuest_id (String guest_id)
        {
            this.guest_id = guest_id;
        }

        public ArrayList<Order_laundry_items> getOrder_laundry_items ()
        {
            return order_laundry_items;
        }

        public void setOrder_laundry_items (ArrayList<Order_laundry_items> order_laundry_items)
        {
            this.order_laundry_items = order_laundry_items;
        }

        public Primises getPrimises ()
        {
            return primises;
        }

        public void setPrimises (Primises primises)
        {
            this.primises = primises;
        }

        public String getPrimises_id ()
        {
            return primises_id;
        }

        public void setPrimises_id (String primises_id)
        {
            this.primises_id = primises_id;
        }

        public Guest getGuest ()
        {
            return guest;
        }

        public void setGuest (Guest guest)
        {
            this.guest = guest;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [hotel_id = "+hotel_id+", payment_status = "+payment_status+", created_at = "+created_at+", description = "+description+", payment_receipt = "+payment_receipt+", type = "+type+", table_id = "+table_id+", order_detail = "+order_detail+", order_stage = "+order_stage+", no_of_guest = "+no_of_guest+", bill_pdf = "+bill_pdf+", guest_signature = "+guest_signature+", updated_at = "+updated_at+", guest_id = "+guest_id+", order_laundry_items = "+order_laundry_items+", primises = "+primises+", primises_id = "+primises_id+", guest = "+guest+", id = "+id+", status = "+status+"]";
        }
    }


    public class Order_detail
    {
        private String updated_at;

        private String dispatched_at;

        private String requested_pickup_at;

        private String created_at;

        private String cleared_at;

        private String canceled_on;

        private String id;

        private String order_id;

        private String accepted_at;

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getDispatched_at ()
        {
            return dispatched_at;
        }

        public void setDispatched_at (String dispatched_at)
        {
            this.dispatched_at = dispatched_at;
        }

        public String getRequested_pickup_at ()
        {
            return requested_pickup_at;
        }

        public void setRequested_pickup_at (String requested_pickup_at)
        {
            this.requested_pickup_at = requested_pickup_at;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getCleared_at ()
        {
            return cleared_at;
        }

        public void setCleared_at (String cleared_at)
        {
            this.cleared_at = cleared_at;
        }

        public String getCanceled_on ()
        {
            return canceled_on;
        }

        public void setCanceled_on (String canceled_on)
        {
            this.canceled_on = canceled_on;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getOrder_id ()
        {
            return order_id;
        }

        public void setOrder_id (String order_id)
        {
            this.order_id = order_id;
        }

        public String getAccepted_at ()
        {
            return accepted_at;
        }

        public void setAccepted_at (String accepted_at)
        {
            this.accepted_at = accepted_at;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [updated_at = "+updated_at+", dispatched_at = "+dispatched_at+", requested_pickup_at = "+requested_pickup_at+", created_at = "+created_at+", cleared_at = "+cleared_at+", canceled_on = "+canceled_on+", id = "+id+", order_id = "+order_id+", accepted_at = "+accepted_at+"]";
        }
    }

    public class Order_laundry_items
    {
        private Item item;

        private String laundry_item_id;

        private String quantity;

        private String updated_at;

        private String price;

        private String description;

        private String created_at;

        private String id;

        private String order_id;

        private String is_express_delivery;

        public Item getItem ()
        {
            return item;
        }

        public void setItem (Item item)
        {
            this.item = item;
        }

        public String getLaundry_item_id ()
        {
            return laundry_item_id;
        }

        public void setLaundry_item_id (String laundry_item_id)
        {
            this.laundry_item_id = laundry_item_id;
        }

        public String getQuantity ()
        {
            return quantity;
        }

        public void setQuantity (String quantity)
        {
            this.quantity = quantity;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getPrice ()
        {
            return price;
        }

        public void setPrice (String price)
        {
            this.price = price;
        }

        public String getDescription ()
        {
            return description;
        }

        public void setDescription (String description)
        {
            this.description = description;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getOrder_id ()
        {
            return order_id;
        }

        public void setOrder_id (String order_id)
        {
            this.order_id = order_id;
        }

        public String getIs_express_delivery ()
        {
            return is_express_delivery;
        }

        public void setIs_express_delivery (String is_express_delivery)
        {
            this.is_express_delivery = is_express_delivery;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [item = "+item+", laundry_item_id = "+laundry_item_id+", quantity = "+quantity+", updated_at = "+updated_at+", price = "+price+", description = "+description+", created_at = "+created_at+", id = "+id+", order_id = "+order_id+", is_express_delivery = "+is_express_delivery+"]";
        }
    }


    public class Item
    {
        private String thumbnail;

        private String description;

        private String created_at;

        private String laundry_category_id;

        private String enabled;

        private String tags;

        private String updated_at;

        public Laundry_category getLaundry_category() {
            return laundry_category;
        }

        public void setLaundry_category(Laundry_category laundry_category) {
            this.laundry_category = laundry_category;
        }

        private String price;
        private Laundry_category laundry_category;

        private String name;

        private String laundry_id;

        private String express_delivery_price;

        private String id;

        private String laundry_sub_category_id;

        private String is_express_delivery;

        public String getThumbnail ()
        {
            return thumbnail;
        }

        public void setThumbnail (String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public String getDescription ()
        {
            return description;
        }

        public void setDescription (String description)
        {
            this.description = description;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getLaundry_category_id ()
        {
            return laundry_category_id;
        }

        public void setLaundry_category_id (String laundry_category_id)
        {
            this.laundry_category_id = laundry_category_id;
        }

        public String getEnabled ()
        {
            return enabled;
        }

        public void setEnabled (String enabled)
        {
            this.enabled = enabled;
        }

        public String getTags ()
        {
            return tags;
        }

        public void setTags (String tags)
        {
            this.tags = tags;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getPrice ()
        {
            return price;
        }

        public void setPrice (String price)
        {
            this.price = price;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getLaundry_id ()
        {
            return laundry_id;
        }

        public void setLaundry_id (String laundry_id)
        {
            this.laundry_id = laundry_id;
        }

        public String getExpress_delivery_price ()
        {
            return express_delivery_price;
        }

        public void setExpress_delivery_price (String express_delivery_price)
        {
            this.express_delivery_price = express_delivery_price;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getLaundry_sub_category_id ()
        {
            return laundry_sub_category_id;
        }

        public void setLaundry_sub_category_id (String laundry_sub_category_id)
        {
            this.laundry_sub_category_id = laundry_sub_category_id;
        }

        public String getIs_express_delivery ()
        {
            return is_express_delivery;
        }

        public void setIs_express_delivery (String is_express_delivery)
        {
            this.is_express_delivery = is_express_delivery;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [thumbnail = "+thumbnail+", description = "+description+", created_at = "+created_at+", laundry_category_id = "+laundry_category_id+", enabled = "+enabled+", tags = "+tags+", updated_at = "+updated_at+", price = "+price+", name = "+name+", laundry_id = "+laundry_id+", express_delivery_price = "+express_delivery_price+", id = "+id+", laundry_sub_category_id = "+laundry_sub_category_id+", is_express_delivery = "+is_express_delivery+"]";
        }
    }



    public class Primises
    {
        private String hotel_floor_id;

        private String updated_at;

        private String premise_no;

        private String hotel_id;

        private String name;

        private String created_at;

        private String id;

        public String getHotel_floor_id ()
        {
            return hotel_floor_id;
        }

        public void setHotel_floor_id (String hotel_floor_id)
        {
            this.hotel_floor_id = hotel_floor_id;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getPremise_no ()
        {
            return premise_no;
        }

        public void setPremise_no (String premise_no)
        {
            this.premise_no = premise_no;
        }

        public String getHotel_id ()
        {
            return hotel_id;
        }

        public void setHotel_id (String hotel_id)
        {
            this.hotel_id = hotel_id;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [hotel_floor_id = "+hotel_floor_id+", updated_at = "+updated_at+", premise_no = "+premise_no+", hotel_id = "+hotel_id+", name = "+name+", created_at = "+created_at+", id = "+id+"]";
        }
    }

    public class Laundry_category
    {
        private String updated_at;

        private String name;

        private String laundry_id;

        private String description;

        private String created_at;

        private String id;

        private String enabled;

        private String tags;

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getLaundry_id ()
        {
            return laundry_id;
        }

        public void setLaundry_id (String laundry_id)
        {
            this.laundry_id = laundry_id;
        }

        public String getDescription ()
        {
            return description;
        }

        public void setDescription (String description)
        {
            this.description = description;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getEnabled ()
        {
            return enabled;
        }

        public void setEnabled (String enabled)
        {
            this.enabled = enabled;
        }

        public String getTags ()
        {
            return tags;
        }

        public void setTags (String tags)
        {
            this.tags = tags;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [updated_at = "+updated_at+", name = "+name+", laundry_id = "+laundry_id+", description = "+description+", created_at = "+created_at+", id = "+id+", enabled = "+enabled+", tags = "+tags+"]";
        }
    }
    public class Guest
    {
        private String zip;

        private String thumbnail;

        private String address;

        private String mobile_verified_at;

        private String city;


        private String mobile;

        private String created_at;

        private String email_verified_at;

        private String type;

        private String updated_at;

        private String name;



        private String state;

        private String email;

        private String country_id;

        private String username;

        public String getZip ()
        {
            return zip;
        }

        public void setZip (String zip)
        {
            this.zip = zip;
        }

        public String getThumbnail ()
        {
            return thumbnail;
        }

        public void setThumbnail (String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public String getAddress ()
        {
            return address;
        }

        public void setAddress (String address)
        {
            this.address = address;
        }

        public String getMobile_verified_at ()
        {
            return mobile_verified_at;
        }

        public void setMobile_verified_at (String mobile_verified_at)
        {
            this.mobile_verified_at = mobile_verified_at;
        }

        public String getCity ()
        {
            return city;
        }

        public void setCity (String city)
        {
            this.city = city;
        }



        public String getMobile ()
        {
            return mobile;
        }

        public void setMobile (String mobile)
        {
            this.mobile = mobile;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getEmail_verified_at ()
        {
            return email_verified_at;
        }

        public void setEmail_verified_at (String email_verified_at)
        {
            this.email_verified_at = email_verified_at;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }



        public String getState ()
        {
            return state;
        }

        public void setState (String state)
        {
            this.state = state;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getCountry_id ()
        {
            return country_id;
        }

        public void setCountry_id (String country_id)
        {
            this.country_id = country_id;
        }

        public String getUsername ()
        {
            return username;
        }

        public void setUsername (String username)
        {
            this.username = username;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [zip = "+zip+", thumbnail = "+thumbnail+", address = "+address+", mobile_verified_at = "+mobile_verified_at+", city = "+city+", mobile = "+mobile+", created_at = "+created_at+", email_verified_at = "+email_verified_at+", type = "+type+", updated_at = "+updated_at+", name = "+name+", state = "+state+", email = "+email+", country_id = "+country_id+", username = "+username+"]";
        }
    }
    public class Order_stage
    {
        private String stage_text;

        private String stage;

        private String updated_at;

        private String created_at;

        private String id;

        private String order_id;

        public String getStage_text ()
        {
            return stage_text;
        }

        public void setStage_text (String stage_text)
        {
            this.stage_text = stage_text;
        }

        public String getStage ()
        {
            return stage;
        }

        public void setStage (String stage)
        {
            this.stage = stage;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getOrder_id ()
        {
            return order_id;
        }

        public void setOrder_id (String order_id)
        {
            this.order_id = order_id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [stage_text = "+stage_text+", stage = "+stage+", updated_at = "+updated_at+", created_at = "+created_at+", id = "+id+", order_id = "+order_id+"]";
        }
    }
}
