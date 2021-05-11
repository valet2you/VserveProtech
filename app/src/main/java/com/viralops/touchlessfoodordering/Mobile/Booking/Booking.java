package com.viralops.touchlessfoodordering.Mobile.Booking;

import java.util.ArrayList;

public class Booking {


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
        private  String bookingslot;
        private String opensttaus;
        private ArrayList<Data> data1ArrayList;
        private String count;
        private String bookingslotid;

        private String payment_status;

        private String created_at;

        private String description;

        private String payment_receipt;

        private String membership_no;

        private String type;

        private String table_id;

        private Order_detail order_detail;

        private String is_complementary;

        private ArrayList<Order_booking_services> order_booking_services;

        private String no_of_guest;

        private String bill_pdf;

        private String guest_signature;

        private String updated_at;

        private String guest_id;

        private String outlet_id;

        private Primises primises;

        private String primises_id;

        public String getBookingslotid() {
            return bookingslotid;
        }

        public void setBookingslotid(String bookingslotid) {
            this.bookingslotid = bookingslotid;
        }

        private Guest guest;

        private String id;

        private String status;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

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

        public String getOpensttaus() {
            return opensttaus;
        }

        public void setOpensttaus(String opensttaus) {
            this.opensttaus = opensttaus;
        }

        public String getBookingslot() {
            return bookingslot;
        }

        public void setBookingslot(String bookingslot) {
            this.bookingslot = bookingslot;
        }

        public ArrayList<Data> getData1ArrayList() {
            return data1ArrayList;
        }

        public void setData1ArrayList(ArrayList<Data> data1ArrayList) {
            this.data1ArrayList = data1ArrayList;
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

        public String getMembership_no ()
    {
        return membership_no;
    }

        public void setMembership_no (String membership_no)
        {
            this.membership_no = membership_no;
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

        public String getIs_complementary ()
        {
            return is_complementary;
        }

        public void setIs_complementary (String is_complementary)
        {
            this.is_complementary = is_complementary;
        }

        public ArrayList<Order_booking_services> getOrder_booking_services ()
        {
            return order_booking_services;
        }

        public void setOrder_booking_services (ArrayList<Order_booking_services> order_booking_services)
        {
            this.order_booking_services = order_booking_services;
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

        public String getOutlet_id ()
    {
        return outlet_id;
    }

        public void setOutlet_id (String outlet_id)
        {
            this.outlet_id = outlet_id;
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
            return "ClassPojo [hotel_id = "+hotel_id+", payment_status = "+payment_status+", created_at = "+created_at+", description = "+description+", payment_receipt = "+payment_receipt+", membership_no = "+membership_no+", type = "+type+", table_id = "+table_id+", order_detail = "+order_detail+", is_complementary = "+is_complementary+", order_booking_services = "+order_booking_services+", no_of_guest = "+no_of_guest+", bill_pdf = "+bill_pdf+", guest_signature = "+guest_signature+", updated_at = "+updated_at+", guest_id = "+guest_id+", outlet_id = "+outlet_id+", primises = "+primises+", primises_id = "+primises_id+", guest = "+guest+", id = "+id+", status = "+status+"]";
        }
    }
    public static class Data1
    {
        private String hotel_id;
        private  String bookingslot;
        private String bookingslotid;

        private String payment_status;

        private String created_at;

        private String description;

        private String payment_receipt;

        private String membership_no;

        private String type;

        private String table_id;

        private Order_detail order_detail;

        private String is_complementary;

        private ArrayList<Order_booking_services> order_booking_services;

        private String no_of_guest;

        private String bill_pdf;

        private String guest_signature;

        private String updated_at;

        private String guest_id;

        private String outlet_id;

        private Primises primises;

        private String primises_id;

        public String getBookingslotid() {
            return bookingslotid;
        }

        public void setBookingslotid(String bookingslotid) {
            this.bookingslotid = bookingslotid;
        }

        private Guest guest;

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

        public String getBookingslot() {
            return bookingslot;
        }

        public void setBookingslot(String bookingslot) {
            this.bookingslot = bookingslot;
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

        public String getMembership_no ()
    {
        return membership_no;
    }

        public void setMembership_no (String membership_no)
        {
            this.membership_no = membership_no;
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

        public String getIs_complementary ()
        {
            return is_complementary;
        }

        public void setIs_complementary (String is_complementary)
        {
            this.is_complementary = is_complementary;
        }

        public ArrayList<Order_booking_services> getOrder_booking_services ()
        {
            return order_booking_services;
        }

        public void setOrder_booking_services (ArrayList<Order_booking_services> order_booking_services)
        {
            this.order_booking_services = order_booking_services;
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

        public String getOutlet_id ()
    {
        return outlet_id;
    }

        public void setOutlet_id (String outlet_id)
        {
            this.outlet_id = outlet_id;
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
            return "ClassPojo [hotel_id = "+hotel_id+", payment_status = "+payment_status+", created_at = "+created_at+", description = "+description+", payment_receipt = "+payment_receipt+", membership_no = "+membership_no+", type = "+type+", table_id = "+table_id+", order_detail = "+order_detail+", is_complementary = "+is_complementary+", order_booking_services = "+order_booking_services+", no_of_guest = "+no_of_guest+", bill_pdf = "+bill_pdf+", guest_signature = "+guest_signature+", updated_at = "+updated_at+", guest_id = "+guest_id+", outlet_id = "+outlet_id+", primises = "+primises+", primises_id = "+primises_id+", guest = "+guest+", id = "+id+", status = "+status+"]";
        }
    }

    public class Order_detail
    {
        private Booking_service_timing booking_service_timing;

        private String updated_at;

        private String created_at;

        private String cleared_at;

        private String canceled_on;

        private String id;

        private String pickup_slot;

        private String order_id;

        private String accepted_at;

        private String booking_slot;

        private String bsodt_id;

        public Booking_service_timing getBooking_service_timing ()
        {
            return booking_service_timing;
        }

        public void setBooking_service_timing (Booking_service_timing booking_service_timing)
        {
            this.booking_service_timing = booking_service_timing;
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

        public String getPickup_slot ()
        {
            return pickup_slot;
        }

        public void setPickup_slot (String pickup_slot)
        {
            this.pickup_slot = pickup_slot;
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

        public String getBooking_slot ()
        {
            return booking_slot;
        }

        public void setBooking_slot (String booking_slot)
        {
            this.booking_slot = booking_slot;
        }

        public String getBsodt_id ()
        {
            return bsodt_id;
        }

        public void setBsodt_id (String bsodt_id)
        {
            this.bsodt_id = bsodt_id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [booking_service_timing = "+booking_service_timing+", updated_at = "+updated_at+", created_at = "+created_at+", cleared_at = "+cleared_at+", canceled_on = "+canceled_on+", id = "+id+", pickup_slot = "+pickup_slot+", order_id = "+order_id+", accepted_at = "+accepted_at+", booking_slot = "+booking_slot+", bsodt_id = "+bsodt_id+"]";
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

    public class Booking_service_timing
    {
        private Booking_service_opening_day booking_service_opening_day;

        private String max_request;

        private String updated_at;

        private String bs_opening_day_id;

        private String closing_time;

        private String opening_time;

        private String total_active_booking_count;

        private String created_at;

        private String id;

        private String enabled;

        public Booking_service_opening_day getBooking_service_opening_day ()
        {
            return booking_service_opening_day;
        }

        public void setBooking_service_opening_day (Booking_service_opening_day booking_service_opening_day)
        {
            this.booking_service_opening_day = booking_service_opening_day;
        }

        public String getMax_request ()
        {
            return max_request;
        }

        public void setMax_request (String max_request)
        {
            this.max_request = max_request;
        }

        public String getUpdated_at ()
    {
        return updated_at;
    }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getBs_opening_day_id ()
        {
            return bs_opening_day_id;
        }

        public void setBs_opening_day_id (String bs_opening_day_id)
        {
            this.bs_opening_day_id = bs_opening_day_id;
        }

        public String getClosing_time ()
        {
            return closing_time;
        }

        public void setClosing_time (String closing_time)
        {
            this.closing_time = closing_time;
        }

        public String getOpening_time ()
        {
            return opening_time;
        }

        public void setOpening_time (String opening_time)
        {
            this.opening_time = opening_time;
        }

        public String getTotal_active_booking_count ()
        {
            return total_active_booking_count;
        }

        public void setTotal_active_booking_count (String total_active_booking_count)
        {
            this.total_active_booking_count = total_active_booking_count;
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

        @Override
        public String toString()
        {
            return "ClassPojo [booking_service_opening_day = "+booking_service_opening_day+", max_request = "+max_request+", updated_at = "+updated_at+", bs_opening_day_id = "+bs_opening_day_id+", closing_time = "+closing_time+", opening_time = "+opening_time+", total_active_booking_count = "+total_active_booking_count+", created_at = "+created_at+", id = "+id+", enabled = "+enabled+"]";
        }
    }

    public class Guest
    {
        private String zip;

        private String thumbnail;

        private String address;

        private String mobile_verified_at;

        private String city;

        private String hotel_id;

        private String mobile;

        private String created_at;

        private String last_name;

        private String email_verified_at;

        private String type;

        private String entity_relationship;

        private String opt_in_marketing;

        private String is_available;

        private String updated_at;

        private String outlet_id;

        private String name;

        private String id;

        private String state;

        private String first_name;

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

        public String getHotel_id ()
        {
            return hotel_id;
        }

        public void setHotel_id (String hotel_id)
        {
            this.hotel_id = hotel_id;
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

        public String getLast_name ()
    {
        return last_name;
    }

        public void setLast_name (String last_name)
        {
            this.last_name = last_name;
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

        public String getEntity_relationship ()
    {
        return entity_relationship;
    }

        public void setEntity_relationship (String entity_relationship)
        {
            this.entity_relationship = entity_relationship;
        }

        public String getOpt_in_marketing ()
        {
            return opt_in_marketing;
        }

        public void setOpt_in_marketing (String opt_in_marketing)
        {
            this.opt_in_marketing = opt_in_marketing;
        }

        public String getIs_available ()
        {
            return is_available;
        }

        public void setIs_available (String is_available)
        {
            this.is_available = is_available;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getOutlet_id ()
    {
        return outlet_id;
    }

        public void setOutlet_id (String outlet_id)
        {
            this.outlet_id = outlet_id;
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

        public String getState ()
    {
        return state;
    }

        public void setState (String state)
        {
            this.state = state;
        }

        public String getFirst_name ()
    {
        return first_name;
    }

        public void setFirst_name (String first_name)
        {
            this.first_name = first_name;
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
            return "ClassPojo [zip = "+zip+", thumbnail = "+thumbnail+", address = "+address+", mobile_verified_at = "+mobile_verified_at+", city = "+city+", hotel_id = "+hotel_id+", mobile = "+mobile+", created_at = "+created_at+", last_name = "+last_name+", email_verified_at = "+email_verified_at+", type = "+type+", entity_relationship = "+entity_relationship+", opt_in_marketing = "+opt_in_marketing+", is_available = "+is_available+", updated_at = "+updated_at+", outlet_id = "+outlet_id+", name = "+name+", id = "+id+", state = "+state+", first_name = "+first_name+", email = "+email+", country_id = "+country_id+", username = "+username+"]";
        }
    }

    public class Booking_service_opening_day
    {
        private String updated_at;

        private String closing_time;

        private String opening_time;

        private String booking_service_id;

        private String created_at;

        private String id;

        private String day;

        private String enabled;

        public String getUpdated_at ()
    {
        return updated_at;
    }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getClosing_time ()
        {
            return closing_time;
        }

        public void setClosing_time (String closing_time)
        {
            this.closing_time = closing_time;
        }

        public String getOpening_time ()
        {
            return opening_time;
        }

        public void setOpening_time (String opening_time)
        {
            this.opening_time = opening_time;
        }

        public String getBooking_service_id ()
        {
            return booking_service_id;
        }

        public void setBooking_service_id (String booking_service_id)
        {
            this.booking_service_id = booking_service_id;
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

        public String getDay ()
        {
            return day;
        }

        public void setDay (String day)
        {
            this.day = day;
        }

        public String getEnabled ()
        {
            return enabled;
        }

        public void setEnabled (String enabled)
        {
            this.enabled = enabled;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [updated_at = "+updated_at+", closing_time = "+closing_time+", opening_time = "+opening_time+", booking_service_id = "+booking_service_id+", created_at = "+created_at+", id = "+id+", day = "+day+", enabled = "+enabled+"]";
        }
    }

    public class Order_booking_services
    {
        private String booking_service_image;

        private String booking_service_name;

        private String updated_at;

        private String booking_service_price;

        private String booking_service_type;

        private String booking_service_id;

        private String created_at;

        private String[] order_booking_service_components;

        private String id;

        private String order_id;

        private String booking_service_description;

        public String getBooking_service_image ()
        {
            return booking_service_image;
        }

        public void setBooking_service_image (String booking_service_image)
        {
            this.booking_service_image = booking_service_image;
        }

        public String getBooking_service_name ()
        {
            return booking_service_name;
        }

        public void setBooking_service_name (String booking_service_name)
        {
            this.booking_service_name = booking_service_name;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getBooking_service_price ()
        {
            return booking_service_price;
        }

        public void setBooking_service_price (String booking_service_price)
        {
            this.booking_service_price = booking_service_price;
        }

        public String getBooking_service_type ()
        {
            return booking_service_type;
        }

        public void setBooking_service_type (String booking_service_type)
        {
            this.booking_service_type = booking_service_type;
        }

        public String getBooking_service_id ()
        {
            return booking_service_id;
        }

        public void setBooking_service_id (String booking_service_id)
        {
            this.booking_service_id = booking_service_id;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String[] getOrder_booking_service_components ()
        {
            return order_booking_service_components;
        }

        public void setOrder_booking_service_components (String[] order_booking_service_components)
        {
            this.order_booking_service_components = order_booking_service_components;
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

        public String getBooking_service_description ()
        {
            return booking_service_description;
        }

        public void setBooking_service_description (String booking_service_description)
        {
            this.booking_service_description = booking_service_description;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [booking_service_image = "+booking_service_image+", booking_service_name = "+booking_service_name+", updated_at = "+updated_at+", booking_service_price = "+booking_service_price+", booking_service_type = "+booking_service_type+", booking_service_id = "+booking_service_id+", created_at = "+created_at+", order_booking_service_components = "+order_booking_service_components+", id = "+id+", order_id = "+order_id+", booking_service_description = "+booking_service_description+"]";
        }
    }


}
