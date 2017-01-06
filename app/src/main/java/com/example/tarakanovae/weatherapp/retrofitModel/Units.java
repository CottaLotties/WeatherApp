
package com.example.tarakanovae.weatherapp.retrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Units {

    @SerializedName("temperature")
    @Expose
    private String temperature;

    /**
     * 
     * @return
     *     The temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * 
     * @param temperature
     *     The temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
