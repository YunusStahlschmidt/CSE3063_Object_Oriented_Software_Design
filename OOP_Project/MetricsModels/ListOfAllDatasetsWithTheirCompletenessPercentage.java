package OOP_Project.MetricsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfAllDatasetsWithTheirCompletenessPercentage {

    @SerializedName("datasetId")
    @Expose
    private Integer datasetId;
    @SerializedName("completenessPercentage")
    @Expose
    private Integer completenessPercentage;

    public Integer getDatasetId() {
    return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
    this.datasetId = datasetId;
    }

    public Integer getCompletenessPercentage() {
    return completenessPercentage;
    }

    public void setCompletenessPercentage(Integer completenessPercentage) {
    this.completenessPercentage = completenessPercentage;
    }

}
