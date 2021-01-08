
package com.railways.ecsoket.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Socket {

    @SerializedName("ecId")
    @Expose
    private String ecId;
    @SerializedName("ecLocation")
    @Expose
    private String ecLocation;
    @SerializedName("ecLattitude")
    @Expose
    private String ecLattitude;
    @SerializedName("ecLongitude")
    @Expose
    private String ecLongitude;

    public String getEcId() {
        return ecId;
    }

    public void setEcId(String ecId) {
        this.ecId = ecId;
    }

    public String getEcLocation() {
        return ecLocation;
    }

    public void setEcLocation(String ecLocation) {
        this.ecLocation = ecLocation;
    }

    public String getEcLattitude() {
        return ecLattitude;
    }

    public void setEcLattitude(String ecLattitude) {
        this.ecLattitude = ecLattitude;
    }

    public String getEcLongitude() {
        return ecLongitude;
    }

    public void setEcLongitude(String ecLongitude) {
        this.ecLongitude = ecLongitude;
    }

}
