
package com.railways.ecsoket.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadSocketResponse {

    @SerializedName("sectionInchargeId")
    @Expose
    private String sectionInchargeId;
    @SerializedName("sectionInchargeName")
    @Expose
    private String sectionInchargeName;
    @SerializedName("section")
    @Expose
    private List<Section> section = null;

    public String getSectionInchargeId() {
        return sectionInchargeId;
    }

    public void setSectionInchargeId(String sectionInchargeId) {
        this.sectionInchargeId = sectionInchargeId;
    }

    public String getSectionInchargeName() {
        return sectionInchargeName;
    }

    public void setSectionInchargeName(String sectionInchargeName) {
        this.sectionInchargeName = sectionInchargeName;
    }

    public List<Section> getSection() {
        return section;
    }

    public void setSection(List<Section> section) {
        this.section = section;
    }

}
