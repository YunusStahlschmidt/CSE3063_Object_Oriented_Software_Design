package OOP_Project.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListClassLabelsAndPercentage {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

}
