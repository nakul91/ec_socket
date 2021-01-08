
package com.railways.ecsoket.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReasonsResponse {

    @SerializedName("section")
    @Expose
    private List<RemarkSection> section = null;

    public List<RemarkSection> getSection() {
        return section;
    }

    public void setSection(List<RemarkSection> section) {
        this.section = section;
    }
}
