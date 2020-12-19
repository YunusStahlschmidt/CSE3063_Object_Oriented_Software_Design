package OOP_Project.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassDistributionBasedOnFinalInstanceLabel {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("distribution")
    @Expose
    private Integer distribution;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getDistribution() {
        return distribution;
    }

    public void setDistribution(Integer distribution) {
        this.distribution = distribution;
    }

}
