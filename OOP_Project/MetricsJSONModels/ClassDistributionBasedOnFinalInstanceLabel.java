package OOP_Project.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDistributionBasedOnFinalInstanceLabel {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("distributionPercentage")
    @Expose
    private Integer distributionPercentage;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getdistributionPercentage() {
        return distributionPercentage;
    }

    public void setDistribution(Integer distributionPercentage) {
        this.distributionPercentage = distributionPercentage;
    }

}
