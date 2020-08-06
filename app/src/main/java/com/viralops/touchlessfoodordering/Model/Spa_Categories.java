package com.viralops.touchlessfoodordering.Model;

import org.json.JSONArray;

public class Spa_Categories {

    private JSONArray sub_categories;

    private String spa_id;

    private String updated_at;

    private JSONArray without_sub_category_items;

    private String name;

    private String description;

    private String created_at;

    private String id;

    private String enabled;

    private String tags;

    public JSONArray getSub_categories ()
    {
        return sub_categories;
    }

    public void setSub_categories (JSONArray sub_categories)
    {
        this.sub_categories = sub_categories;
    }

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

    public JSONArray getWithout_sub_category_items ()
    {
        return without_sub_category_items;
    }

    public void setWithout_sub_category_items (JSONArray without_sub_category_items)
    {
        this.without_sub_category_items = without_sub_category_items;
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
        return "ClassPojo [sub_categories = "+sub_categories+", spa_id = "+spa_id+", updated_at = "+updated_at+", without_sub_category_items = "+without_sub_category_items+", name = "+name+", description = "+description+", created_at = "+created_at+", id = "+id+", enabled = "+enabled+", tags = "+tags+"]";
    }
}
