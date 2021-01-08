
package com.railways.ecsoket.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Remakr {

    @SerializedName("remarkId")
    @Expose
    private String remarkId;
    @SerializedName("remark")
    @Expose
    private String remark;

    public String getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
