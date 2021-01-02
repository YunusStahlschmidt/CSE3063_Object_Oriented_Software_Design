package Iteration_3.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDistributionBasedOnFinalInstanceLabel {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("distributionPercentage")
    @Expose
    private Double distributionPercentage;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getdistributionPercentage() {
        return distributionPercentage;
    }

    public void setDistribution(Double distributionPercentage) {
        this.distributionPercentage =(double) Math.round(distributionPercentage* 100);
    }

}
