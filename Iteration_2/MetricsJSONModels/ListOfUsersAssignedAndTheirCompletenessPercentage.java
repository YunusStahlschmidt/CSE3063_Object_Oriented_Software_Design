package Iteration_2.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfUsersAssignedAndTheirCompletenessPercentage {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("completenessPercentage")
    @Expose
    private Double completenessPercentage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getCompletenessPercentage() {
        return completenessPercentage;
    }

    public void setCompletenessPercentage(Double completenessPercentage) {
        this.completenessPercentage = completenessPercentage;
    }

}
