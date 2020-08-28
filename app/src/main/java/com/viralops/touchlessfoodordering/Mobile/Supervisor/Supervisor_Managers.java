package com.viralops.touchlessfoodordering.Mobile.Supervisor;

import java.util.ArrayList;

public class Supervisor_Managers {
    private ArrayList<Result> result;

    private String message;

    public ArrayList<Result> getResult ()
    {
        return result;
    }

    public void setResult (ArrayList<Result> result)
    {
        this.result = result;
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
        return "ClassPojo [result = "+result+", message = "+message+"]";
    }


    public static class Result
    {
        private String zip;

        private String loggedin_at;

        private String thumbnail;

        private String address;

        private String is_active;

        private String mobile_verified_at;

        private String city;

        private String hotel_id;

        private String mobile;

        private String created_at;

        private String email_verified_at;

        private String type;

        private String entity_relationship;

        private String updated_at;

        private String name;

        private Hotel hotel;

        private String id;

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

        public String getLoggedin_at ()
        {
            return loggedin_at;
        }

        public void setLoggedin_at (String loggedin_at)
        {
            this.loggedin_at = loggedin_at;
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

        public String getIs_active ()
        {
            return is_active;
        }

        public void setIs_active (String is_active)
        {
            this.is_active = is_active;
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

        public Hotel getHotel ()
        {
            return hotel;
        }

        public void setHotel (Hotel hotel)
        {
            this.hotel = hotel;
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
            return "ClassPojo [zip = "+zip+", loggedin_at = "+loggedin_at+", thumbnail = "+thumbnail+", address = "+address+", is_active = "+is_active+", mobile_verified_at = "+mobile_verified_at+", city = "+city+", hotel_id = "+hotel_id+", mobile = "+mobile+", created_at = "+created_at+", email_verified_at = "+email_verified_at+", type = "+type+", entity_relationship = "+entity_relationship+", updated_at = "+updated_at+", name = "+name+", hotel = "+hotel+", id = "+id+", state = "+state+", email = "+email+", country_id = "+country_id+", username = "+username+"]";
        }
    }
    public class Hotel
    {
        private String zip;

        private String address;

        private String city;

        private String encrypted_id;

        private String paytm_link;

        private String latitude;

        private String mobile;

        private String created_at;

        private String banner;

        private Restaurant_property restaurant_property;

        private String updated_at;

        private String contact_no;

        private String name;

        private String logo;

        private String id;

        private String state;

        private String email;

        private String country_id;

        private String longitude;

        public String getZip ()
        {
            return zip;
        }

        public void setZip (String zip)
        {
            this.zip = zip;
        }

        public String getAddress ()
        {
            return address;
        }

        public void setAddress (String address)
        {
            this.address = address;
        }

        public String getCity ()
        {
            return city;
        }

        public void setCity (String city)
        {
            this.city = city;
        }

        public String getEncrypted_id ()
        {
            return encrypted_id;
        }

        public void setEncrypted_id (String encrypted_id)
        {
            this.encrypted_id = encrypted_id;
        }

        public String getPaytm_link ()
    {
        return paytm_link;
    }

        public void setPaytm_link (String paytm_link)
        {
            this.paytm_link = paytm_link;
        }

        public String getLatitude ()
        {
            return latitude;
        }

        public void setLatitude (String latitude)
        {
            this.latitude = latitude;
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

        public String getBanner ()
    {
        return banner;
    }

        public void setBanner (String banner)
        {
            this.banner = banner;
        }

        public Restaurant_property getRestaurant_property ()
        {
            return restaurant_property;
        }

        public void setRestaurant_property (Restaurant_property restaurant_property)
        {
            this.restaurant_property = restaurant_property;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public String getContact_no ()
    {
        return contact_no;
    }

        public void setContact_no (String contact_no)
        {
            this.contact_no = contact_no;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getLogo ()
    {
        return logo;
    }

        public void setLogo (String logo)
        {
            this.logo = logo;
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

        public String getLongitude ()
        {
            return longitude;
        }

        public void setLongitude (String longitude)
        {
            this.longitude = longitude;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [zip = "+zip+", address = "+address+", city = "+city+", encrypted_id = "+encrypted_id+", paytm_link = "+paytm_link+", latitude = "+latitude+", mobile = "+mobile+", created_at = "+created_at+", banner = "+banner+", restaurant_property = "+restaurant_property+", updated_at = "+updated_at+", contact_no = "+contact_no+", name = "+name+", logo = "+logo+", id = "+id+", state = "+state+", email = "+email+", country_id = "+country_id+", longitude = "+longitude+"]";
        }
    }

    public class Restaurant_property
    {
        private String assets;

        private String updated_at;

        private String hotel_id;

        private String name;

        private String description;

        private String logo;

        private String banner;

        private String created_at;

        private String id;

        public String getAssets ()
        {
            return assets;
        }

        public void setAssets (String assets)
        {
            this.assets = assets;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
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

        public String getDescription ()
        {
            return description;
        }

        public void setDescription (String description)
        {
            this.description = description;
        }

        public String getLogo ()
        {
            return logo;
        }

        public void setLogo (String logo)
        {
            this.logo = logo;
        }

        public String getBanner ()
        {
            return banner;
        }

        public void setBanner (String banner)
        {
            this.banner = banner;
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
            return "ClassPojo [assets = "+assets+", updated_at = "+updated_at+", hotel_id = "+hotel_id+", name = "+name+", description = "+description+", logo = "+logo+", banner = "+banner+", created_at = "+created_at+", id = "+id+"]";
        }
    }


}
