package com.viralops.touchlessfoodordering.Model;

public class Spa_Menu {
    private Spa_Data data;

    private String message;

    public Spa_Data getData ()
    {
        return data;
    }

    public void setData (Spa_Data data)
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
