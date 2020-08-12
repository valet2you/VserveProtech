package com.viralops.touchlessfoodordering.Model;

import java.util.ArrayList;

public class ConnectHistory {
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
        private Guest guest;

        private String created_at;

        private String description;

        private String type;

        private Order_detail order_detail;

        private String no_of_guest;

        private ArrayList<Order_connect_items> order_connect_items;

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

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public Guest getGuest() {
            return guest;
        }

        public void setGuest(Guest guest) {
            this.guest = guest;
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

        public ArrayList<Order_connect_items> getOrder_connect_items ()
        {
            return order_connect_items;
        }

        public void setOrder_connect_items (ArrayList<Order_connect_items> order_connect_items)
        {
            this.order_connect_items = order_connect_items;
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
            return "ClassPojo [hotel_id = "+hotel_id+", payment_status = "+payment_status+", created_at = "+created_at+", description = "+description+", type = "+type+", order_detail = "+order_detail+", no_of_guest = "+no_of_guest+",guest = "+guest+", order_connect_items = "+order_connect_items+", updated_at = "+updated_at+", primises = "+primises+", primises_id = "+primises_id+", id = "+id+", status = "+status+"]";
        }
    }


    public class Order_detail
    {
        private String updated_at;

        private String dispatched_at;

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
            return "ClassPojo [updated_at = "+updated_at+", dispatched_at = "+dispatched_at+", created_at = "+created_at+", cleared_at = "+cleared_at+", canceled_on = "+canceled_on+", id = "+id+", order_id = "+order_id+", accepted_at = "+accepted_at+"]";
        }
    }


    public class Order_connect_items
    {
        private Item item;

        private String quantity;

        private String updated_at;

        private String description;

        private String created_at;

        private String id;

        private String order_id;

        private String connect_item_id;

        public Item getItem ()
        {
            return item;
        }

        public void setItem (Item item)
        {
            this.item = item;
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

        public String getConnect_item_id ()
        {
            return connect_item_id;
        }

        public void setConnect_item_id (String connect_item_id)
        {
            this.connect_item_id = connect_item_id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [item = "+item+", quantity = "+quantity+", updated_at = "+updated_at+", description = "+description+", created_at = "+created_at+", id = "+id+", order_id = "+order_id+", connect_item_id = "+connect_item_id+"]";
        }
    }

    public class Item
    {
        private String connect_id;

        private String updated_at;

        private String name;

        private String description;

        private String created_at;

        private String id;

        private String type;

        private String connect_category_id;

        private String enabled;

        private String tags;

        public String getConnect_id ()
        {
            return connect_id;
        }

        public void setConnect_id (String connect_id)
        {
            this.connect_id = connect_id;
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

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getConnect_category_id ()
    {
        return connect_category_id;
    }

        public void setConnect_category_id (String connect_category_id)
        {
            this.connect_category_id = connect_category_id;
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
            return "ClassPojo [connect_id = "+connect_id+", updated_at = "+updated_at+", name = "+name+", description = "+description+", created_at = "+created_at+", id = "+id+", type = "+type+", connect_category_id = "+connect_category_id+", enabled = "+enabled+", tags = "+tags+"]";
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

}
