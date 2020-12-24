package Iteration_2.MetricsJSONModels;

import java.text.DecimalFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfAllDatasetsWithTheirCompletenessPercentage {

    @SerializedName("datasetId")
    @Expose
    private Integer datasetId;
    @SerializedName("completenessPercentage")
    @Expose
    private Double completenessPercentage;

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public Double getCompletenessPercentage() {
        return completenessPercentage;
    }

    public void setCompletenessPercentage(Double completenessPercentage) {
        this.completenessPercentage = Double
                .parseDouble(new DecimalFormat("##.###").format(completenessPercentage * 100));
    }

}
