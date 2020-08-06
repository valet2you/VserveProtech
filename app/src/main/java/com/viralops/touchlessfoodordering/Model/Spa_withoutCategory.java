package com.viralops.touchlessfoodordering.Model;

public class Spa_withoutCategory {
    private String spa_category_id;

    private String thumbnail;

    private String spa_id;

    private String spa_sub_category_id;

    private String updated_at;

    private String price;

    private String name;

    private String description;

    private String created_at;

    private String id;

    private String enabled;

    private String tags;

    public String getSpa_category_id ()
    {
        return spa_category_id;
    }

    public void setSpa_category_id (String spa_category_id)
    {
        this.spa_category_id = spa_category_id;
    }

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
        return "ClassPojo [spa_category_id = "+spa_category_id+", thumbnail = "+thumbnail+", spa_id = "+spa_id+", spa_sub_category_id = "+spa_sub_category_id+", updated_at = "+updated_at+", price = "+price+", name = "+name+", description = "+description+", created_at = "+created_at+", id = "+id+", enabled = "+enabled+", tags = "+tags+"]";
    }
}
