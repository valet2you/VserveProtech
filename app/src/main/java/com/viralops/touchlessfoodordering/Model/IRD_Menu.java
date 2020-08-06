package com.viralops.touchlessfoodordering.Model;

import java.util.ArrayList;

public class IRD_Menu {
    private ArrayList<IRD_Data> data;

    private String message;

    public ArrayList<IRD_Data> getData ()
    {
        return data;
    }

    public void setData (ArrayList<IRD_Data> data)
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







}
