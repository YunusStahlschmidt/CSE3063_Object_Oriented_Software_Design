package OOP_Project.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListClassLabelsAndPercentage {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("label")
    @Expose
    private String label;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
