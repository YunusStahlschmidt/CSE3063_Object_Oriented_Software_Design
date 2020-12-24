package OOP_Project.MetricsJSONModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfUsersAssignedAndTheirConsistencyPercentage {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("consistencyPercentage")
    @Expose
    private Double consistencyPercentage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getConsistencyPercentage() {
        return consistencyPercentage;
    }

    public void setConsistencyPercentage(Double consistencyPercentage) {
        this.consistencyPercentage = consistencyPercentage * 100;
    }

}
