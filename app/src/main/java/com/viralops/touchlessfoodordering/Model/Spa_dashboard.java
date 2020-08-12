package com.viralops.touchlessfoodordering.Model;

import java.util.ArrayList;

public class Spa_dashboard {

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

        private ArrayList<Order_spa_items> order_spa_items;
        private Order_stage order_stage;

        private String created_at;
        private Guest guest;

        private String description;

        private String type;

        private Order_detail order_detail;

        private String no_of_guest;

        private String updated_at;

        private Primises primises;

        private String primises_id;

        private String id;

        private String status;

        public String getHotel_id ()
        {
            return hotel_id;
        }

        public void setHotel_id (String hotel_id)
        {
            this.hotel_id = hotel_id;
        }

        public Guest getGuest() {
            return guest;
        }

        public void setGuest(Guest guest) {
            this.guest = guest;
        }

        public String getPayment_status ()
        {
            return payment_status;
        }

        public void setPayment_status (String payment_status)
        {
            this.payment_status = payment_status;
        }

        public ArrayList<Order_spa_items> getOrder_spa_items ()
        {
            return order_spa_items;
        }

        public Order_stage getOrder_stage() {
            return order_stage;
        }

        public void setOrder_stage(Order_stage order_stage) {
            this.order_stage = order_stage;
        }

        public void setOrder_spa_items (ArrayList<Order_spa_items> order_spa_items)
        {
            this.order_spa_items = order_spa_items;
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

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
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

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
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
            return "ClassPojo [hotel_id = "+hotel_id+", payment_status = "+payment_status+", order_spa_items = "+order_spa_items+", created_at = "+created_at+", description = "+description+", type = "+type+", order_detail = "+order_detail+", order_stage = "+order_stage+", no_of_guest = "+no_of_guest+", updated_at = "+updated_at+", primises = "+primises+", guest = "+guest+", primises_id = "+primises_id+", id = "+id+", status = "+status+"]";
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

    public class Order_detail
    {
        private String request_schedule_at;

        private String updated_at;

        private String dispatched_at;

        private String created_at;

        private String cleared_at;

        private String canceled_on;

        private String id;

        private String order_id;

        private String accepted_at;

        public String getRequest_schedule_at ()
        {
            return request_schedule_at;
        }

        public void setRequest_schedule_at (String request_schedule_at)
        {
            this.request_schedule_at = request_schedule_at;
        }

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
            return "ClassPojo [request_schedule_at = "+request_schedule_at+", updated_at = "+updated_at+", dispatched_at = "+dispatched_at+", created_at = "+created_at+", cleared_at = "+cleared_at+", canceled_on = "+canceled_on+", id = "+id+", order_id = "+order_id+", accepted_at = "+accepted_at+"]";
        }
    }

    public class Item
    {
        private String thumbnail;

        private String spa_id;

        private String spa_sub_category_id;

        private String description;

        private String created_at;

        private String enabled;

        private Spa_category spa_category;

        private String tags;

        private String spa_category_id;

        private String updated_at;

        private String price;

        private String name;

        private String id;

        public String getThumbnail ()
        {
            return thumbnail;
        }

        public void setThumbnail (String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public String getSpa_id ()
        {
            return spa_id;
        }

        public void setSpa_id (String spa_id)
        {
            this.spa_id = spa_id;
        }

        public String getSpa_sub_category_id ()
        {
            return spa_sub_category_id;
        }

        public void setSpa_sub_category_id (String spa_sub_category_id)
        {
            this.spa_sub_category_id = spa_sub_category_id;
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

        public String getEnabled ()
        {
            return enabled;
        }

        public void setEnabled (String enabled)
        {
            this.enabled = enabled;
        }

        public Spa_category getSpa_category ()
        {
            return spa_category;
        }

        public void setSpa_category (Spa_category spa_category)
        {
            this.spa_category = spa_category;
        }

        public String getTags ()
        {
            return tags;
        }

        public void setTags (String tags)
        {
            this.tags = tags;
        }

        public String getSpa_category_id ()
        {
            return spa_category_id;
        }

        public void setSpa_category_id (String spa_category_id)
        {
            this.spa_category_id = spa_category_id;
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
            return "ClassPojo [thumbnail = "+thumbnail+", spa_id = "+spa_id+", spa_sub_category_id = "+spa_sub_category_id+", description = "+description+", created_at = "+created_at+", enabled = "+enabled+", spa_category = "+spa_category+", tags = "+tags+", spa_category_id = "+spa_category_id+", updated_at = "+updated_at+", price = "+price+", name = "+name+", id = "+id+"]";
        }
    }
    public class Spa_category
    {
        private String spa_id;

        private String updated_at;

        private String name;

        private String description;

        private String created_at;

        private String id;

        private String enabled;

        private String tags;

        public String getSpa_id ()
        {
            return spa_id;
        }

        public void setSpa_id (String spa_id)
        {
            this.spa_id = spa_id;
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
            return "ClassPojo [spa_id = "+spa_id+", updated_at = "+updated_at+", name = "+name+", description = "+description+", created_at = "+created_at+", id = "+id+", enabled = "+enabled+", tags = "+tags+"]";
        }
    }

    public class Order_spa_items
    {
        private String spa_item_id;

        private Item item;

        private String updated_at;

        private String price;

        private String description;

        private String created_at;

        private String id;

        private String order_id;

        public String getSpa_item_id ()
        {
            return spa_item_id;
        }

        public void setSpa_item_id (String spa_item_id)
        {
            this.spa_item_id = spa_item_id;
        }

        public Item getItem ()
        {
            return item;
        }

        public void setItem (Item item)
        {
            this.item = item;
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

        @Override
        public String toString()
        {
            return "ClassPojo [spa_item_id = "+spa_item_id+", item = "+item+", updated_at = "+updated_at+", price = "+price+", description = "+description+", created_at = "+created_at+", id = "+id+", order_id = "+order_id+"]";
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
