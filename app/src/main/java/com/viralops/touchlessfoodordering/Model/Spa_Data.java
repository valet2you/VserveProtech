package com.viralops.touchlessfoodordering.Model;

import org.json.JSONArray;

public class Spa_Data {
    private String updated_at;

    private String hotel_id;

    private String name;

    private String description;

    private String created_at;

    private String id;

    private JSONArray categories;

    private String[] without_category_items;

    private String enabled;

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

    public JSONArray getCategories ()
    {
        return categories;
    }

    public void setCategories (JSONArray categories)
    {
        this.categories = categories;
    }

    public String[] getWithout_category_items ()
    {
        return without_category_items;
    }

    public void setWithout_category_items (String[] without_category_items)
    {
        this.without_category_items = without_category_items;
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
        return "ClassPojo [updated_at = "+updated_at+", hotel_id = "+hotel_id+", name = "+name+", description = "+description+", created_at = "+created_at+", id = "+id+", categories = "+categories+", without_category_items = "+without_category_items+", enabled = "+enabled+"]";
    }
}
