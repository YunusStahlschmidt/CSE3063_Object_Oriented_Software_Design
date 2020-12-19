package OOP_Project.MetricsModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfUsersAssignedAndTheirConsistencyPercentage {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("consistencyPercentage")
    @Expose
    private Integer consistencyPercentage;

    public Integer getUserId() {
    return userId;
    }

    public void setUserId(Integer userId) {
    this.userId = userId;
    }

    public Integer getConsistencyPercentage() {
    return consistencyPercentage;
    }

    public void setConsistencyPercentage(Integer consistencyPercentage) {
    this.consistencyPercentage = consistencyPercentage;
    }

}
