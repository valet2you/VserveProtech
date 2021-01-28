package com.viralops.touchlessfoodordering.Model;

import java.util.ArrayList;

public class Revenue {
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

    public class Data
    {
        private String addon_revenue;

        private String total_revenue;

        private String item_revenue;

        public String getAddon_revenue ()
        {
            return addon_revenue;
        }

        public void setAddon_revenue (String addon_revenue)
        {
            this.addon_revenue = addon_revenue;
        }

        public String getTotal_revenue ()
        {
            return total_revenue;
        }

        public void setTotal_revenue (String total_revenue)
        {
            this.total_revenue = total_revenue;
        }

        public String getItem_revenue ()
        {
            return item_revenue;
        }

        public void setItem_revenue (String item_revenue)
        {
            this.item_revenue = item_revenue;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [addon_revenue = "+addon_revenue+", total_revenue = "+total_revenue+", item_revenue = "+item_revenue+"]";
        }
    }


}
