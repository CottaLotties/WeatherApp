
package com.example.tarakanovae.weatherapp.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Channel {

    @SerializedName("units")
    @Expose
    private Units units;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("lastBuildDate")
    @Expose
    private String lastBuildDate;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("item")
    @Expose
    private Item item;

    /**
     * 
     * @return
     *     The units
     */
    public Units getUnits() {
        return units;
    }

    /**
     * 
     * @param units
     *     The units
     */
    public void setUnits(Units units) {
        this.units = units;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The lastBuildDate
     */
    public String getLastBuildDate() {
        return lastBuildDate;
    }

    /**
     *
     * @param lastBuildDate
     *     The lastBuildDate
     */
    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    /**
     * 
     * @return
     *     The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The item
     */
    public Item getItem() {
        return item;
    }

    /**
     * 
     * @param item
     *     The item
     */
    public void setItem(Item item) {
        this.item = item;
    }

}
