
package com.railways.ecsoket.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("failureId")
    @Expose
    private String failureId;
    @SerializedName("testId")
    @Expose
    private String testId;
    @SerializedName("failureReportDate")
    @Expose
    private String failureReportDate;
    @SerializedName("fixDeadline")
    @Expose
    private String fixDeadline;
    @SerializedName("sectionInchargeId")
    @Expose
    private String sectionInchargeId;
    @SerializedName("sectionInchargeName")
    @Expose
    private String sectionInchargeName;
    @SerializedName("sectionId")
    @Expose
    private String sectionId;
    @SerializedName("sectionName")
    @Expose
    private String sectionName;
    @SerializedName("blockSectionId")
    @Expose
    private String blockSectionId;
    @SerializedName("blockSectionName")
    @Expose
    private String blockSectionName;
    @SerializedName("ecId")
    @Expose
    private String ecId;
    @SerializedName("ecLocation")
    @Expose
    private String ecLocation;
    @SerializedName("ecType")
    @Expose
    private String ecType;
    @SerializedName("boxCondition")
    @Expose
    private String boxCondition;
    @SerializedName("socketCondition")
    @Expose
    private String socketCondition;
    @SerializedName("autoPairCondition")
    @Expose
    private String autoPairCondition;
    @SerializedName("EMCStatus")
    @Expose
    private String eMCStatus;
    @SerializedName("testedBy")
    @Expose
    private String testedBy;
    @SerializedName("remarks")
    @Expose
    private List<Remark> remarks = null;

    public String getFailureId() {
        return failureId;
    }

    public void setFailureId(String failureId) {
        this.failureId = failureId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getFailureReportDate() {
        return failureReportDate;
    }

    public void setFailureReportDate(String failureReportDate) {
        this.failureReportDate = failureReportDate;
    }

    public String getFixDeadline() {
        return fixDeadline;
    }

    public void setFixDeadline(String fixDeadline) {
        this.fixDeadline = fixDeadline;
    }

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

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getBlockSectionId() {
        return blockSectionId;
    }

    public void setBlockSectionId(String blockSectionId) {
        this.blockSectionId = blockSectionId;
    }

    public String getBlockSectionName() {
        return blockSectionName;
    }

    public void setBlockSectionName(String blockSectionName) {
        this.blockSectionName = blockSectionName;
    }

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

    public String getEcType() {
        return ecType;
    }

    public void setEcType(String ecType) {
        this.ecType = ecType;
    }

    public String getBoxCondition() {
        return boxCondition;
    }

    public void setBoxCondition(String boxCondition) {
        this.boxCondition = boxCondition;
    }

    public String getSocketCondition() {
        return socketCondition;
    }

    public void setSocketCondition(String socketCondition) {
        this.socketCondition = socketCondition;
    }

    public String getAutoPairCondition() {
        return autoPairCondition;
    }

    public void setAutoPairCondition(String autoPairCondition) {
        this.autoPairCondition = autoPairCondition;
    }

    public String getEMCStatus() {
        return eMCStatus;
    }

    public void setEMCStatus(String eMCStatus) {
        this.eMCStatus = eMCStatus;
    }

    public String getTestedBy() {
        return testedBy;
    }

    public void setTestedBy(String testedBy) {
        this.testedBy = testedBy;
    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

}
