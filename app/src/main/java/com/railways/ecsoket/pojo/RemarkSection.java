package com.railways.ecsoket.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemarkSection {

    @SerializedName("remarkSection")
    @Expose
    private String remarkSection;
    @SerializedName("remakrs")
    @Expose
    private List<Remakr> remakrs = null;

    public String getRemarkSection() {
        return remarkSection;
    }

    public void setRemarkSection(String remarkSection) {
        this.remarkSection = remarkSection;
    }

    public List<Remakr> getRemakrs() {
        return remakrs;
    }

    public void setRemakrs(List<Remakr> remakrs) {
        this.remakrs = remakrs;
    }

}