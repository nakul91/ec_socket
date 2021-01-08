
package com.railways.ecsoket.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Block {

    @SerializedName("blockSectionId")
    @Expose
    private String blockSectionId;
    @SerializedName("blockSectionName")
    @Expose
    private String blockSectionName;
    @SerializedName("sockets")
    @Expose
    private List<Socket> sockets = null;

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

    public List<Socket> getSockets() {
        return sockets;
    }

    public void setSockets(List<Socket> sockets) {
        this.sockets = sockets;
    }

}
