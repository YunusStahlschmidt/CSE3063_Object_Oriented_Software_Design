package Iteration_3;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatasetParsingModel {
    @SerializedName("dataset id")
    @Expose
    private Integer datasetId;
    @SerializedName("dataset name")
    @Expose
    private String datasetName;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("assignedUserIds")
    @Expose
    private List<Integer> assignedUserIds;

    // Getters
    public Integer getDatasetId() {
        return datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getPath() {
        return path;
    }

    public List<Integer> getAssignedUserIds() {
        return this.assignedUserIds;
    }

    // Setters
    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAssignedUserIds(List<Integer> assignedUserIds) {
        this.assignedUserIds = assignedUserIds;
    }
}
