package com.viralops.touchlessfoodordering.Model;

import com.viralops.touchlessfoodordering.Mobile.Booking.Booking;

import java.util.ArrayList;
import java.util.Date;

public class GetBookingServices {
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
        private String image;
        private String noofdays;

        private ArrayList<Booking_service_components> booking_service_components;

        private String hotel_id;

        private String description;

        private String created_at;

        private String type;

        private String priority;

        private String deleted_at;

        private ArrayList<Booking_service_opening_days> booking_service_opening_days;

        private String enabled;

        private String updated_at;

        private String price;

        private String name;

        private String id;

        public String getImage ()
        {
            return image;
        }

        public void setImage (String image)
        {
            this.image = image;
        }

        public ArrayList<Booking_service_components> getBooking_service_components ()
        {
            return booking_service_components;
        }

        public void setBooking_service_components (ArrayList<Booking_service_components> booking_service_components)
        {
            this.booking_service_components = booking_service_components;
        }

        public String getHotel_id ()
        {
            return hotel_id;
        }

        public void setHotel_id (String hotel_id)
        {
            this.hotel_id = hotel_id;
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

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getPriority ()
        {
            return priority;
        }

        public void setPriority (String priority)
        {
            this.priority = priority;
        }

        public String getDeleted_at ()
    {
        return deleted_at;
    }

        public void setDeleted_at (String deleted_at)
        {
            this.deleted_at = deleted_at;
        }

        public ArrayList<Booking_service_opening_days> getBooking_service_opening_days ()
        {
            return booking_service_opening_days;
        }

        public void setBooking_service_opening_days (ArrayList<Booking_service_opening_days> booking_service_opening_days)
        {
            this.booking_service_opening_days = booking_service_opening_days;
        }

        public String getEnabled ()
        {
            return enabled;
        }

        public void setEnabled (String enabled)
        {
            this.enabled = enabled;
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
        public String getNoofdays() {
            return noofdays;
        }

        public void setNoofdays(String noofdays) {
            this.noofdays = noofdays;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [image = "+image+", booking_service_components = "+booking_service_components+", hotel_id = "+hotel_id+", description = "+description+", created_at = "+created_at+", type = "+type+", priority = "+priority+", deleted_at = "+deleted_at+", booking_service_opening_days = "+booking_service_opening_days+", enabled = "+enabled+", updated_at = "+updated_at+", price = "+price+", name = "+name+", id = "+id+"]";
        }
    }

    public static class Booking_service_opening_days
    {
        private String updated_at;
        private String date;
        private Date date1;
        String count;
        ArrayList<Booking.Data> datalist;
        private String closing_time;

        private String opening_time;

        private ArrayList<Booking_service_opening_day_timings> booking_service_opening_day_timings;

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

        public String getDate() {
            return date;
        }

        public ArrayList<Booking.Data> getDatalist() {
            return datalist;
        }

        public void setDatalist(ArrayList<Booking.Data> datalist) {
            this.datalist = datalist;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public Date getDate1() {
            return date1;
        }

        public void setDate1(Date date1) {
            this.date1 = date1;
        }

        public void setDate(String date) {
            this.date = date;
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

        public ArrayList<Booking_service_opening_day_timings> getBooking_service_opening_day_timings ()
        {
            return booking_service_opening_day_timings;
        }

        public void setBooking_service_opening_day_timings (ArrayList<Booking_service_opening_day_timings> booking_service_opening_day_timings)
        {
            this.booking_service_opening_day_timings = booking_service_opening_day_timings;
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
            return "ClassPojo [updated_at = "+updated_at+", closing_time = "+closing_time+", opening_time = "+opening_time+", booking_service_opening_day_timings = "+booking_service_opening_day_timings+", booking_service_id = "+booking_service_id+", created_at = "+created_at+", id = "+id+", day = "+day+", enabled = "+enabled+"]";
        }
    }
    public class Booking_service_opening_day_timings
    {
        private String is_slot_booked_by_me;

        private String max_request;

        private String updated_at;

        private String total_active_guest_count;

        private String bs_opening_day_id;

        private String closing_time;

        private String opening_time;

        private String total_active_booking_count;

        private String created_at;

        private String id;

        private String enabled;

        public String getIs_slot_booked_by_me ()
        {
            return is_slot_booked_by_me;
        }

        public void setIs_slot_booked_by_me (String is_slot_booked_by_me)
        {
            this.is_slot_booked_by_me = is_slot_booked_by_me;
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

        public String getTotal_active_guest_count ()
        {
            return total_active_guest_count;
        }

        public void setTotal_active_guest_count (String total_active_guest_count)
        {
            this.total_active_guest_count = total_active_guest_count;
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
            return "ClassPojo [is_slot_booked_by_me = "+is_slot_booked_by_me+", max_request = "+max_request+", updated_at = "+updated_at+", total_active_guest_count = "+total_active_guest_count+", bs_opening_day_id = "+bs_opening_day_id+", closing_time = "+closing_time+", opening_time = "+opening_time+", total_active_booking_count = "+total_active_booking_count+", created_at = "+created_at+", id = "+id+", enabled = "+enabled+"]";
        }
    }


    public class Booking_service_components
    {
        private String image;

        private String updated_at;

        private String price;

        private String name;

        private String booking_service_id;

        private String description;

        private String created_at;

        private String id;

        private String priority;

        private String deleted_at;

        private String enabled;

        public String getImage ()
    {
        return image;
    }

        public void setImage (String image)
        {
            this.image = image;
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

        public String getBooking_service_id ()
        {
            return booking_service_id;
        }

        public void setBooking_service_id (String booking_service_id)
        {
            this.booking_service_id = booking_service_id;
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

        public String getPriority ()
        {
            return priority;
        }

        public void setPriority (String priority)
        {
            this.priority = priority;
        }

        public String getDeleted_at ()
    {
        return deleted_at;
    }

        public void setDeleted_at (String deleted_at)
        {
            this.deleted_at = deleted_at;
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
            return "ClassPojo [image = "+image+", updated_at = "+updated_at+", price = "+price+", name = "+name+", booking_service_id = "+booking_service_id+", description = "+description+", created_at = "+created_at+", id = "+id+", priority = "+priority+", deleted_at = "+deleted_at+", enabled = "+enabled+"]";
        }
    }



}
