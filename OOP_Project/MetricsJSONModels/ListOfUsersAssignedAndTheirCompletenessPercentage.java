package OOP_Project.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfUsersAssignedAndTheirCompletenessPercentage {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("completenessPercentage")
    @Expose
    private Integer completenessPercentage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompletenessPercentage() {
        return completenessPercentage;
    }

    public void setCompletenessPercentage(Integer completenessPercentage) {
        this.completenessPercentage = completenessPercentage;
    }

}
