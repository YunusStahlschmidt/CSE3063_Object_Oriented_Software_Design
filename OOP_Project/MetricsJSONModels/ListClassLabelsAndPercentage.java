package OOP_Project.MetricsJSONModels;

import java.text.DecimalFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListClassLabelsAndPercentage {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("percentage")
    @Expose
    private Double percentage;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = Double.parseDouble(new DecimalFormat("##.###").format(percentage * 100));
    }

}
