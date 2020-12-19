package OOP_Project.MetricsJSONModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("consistencyCheckProbability")
    @Expose
    private String consistencyCheckProbability;
    @SerializedName("number of datasets assigned")
    @Expose
    private Integer numberOfDatasetsAssigned;
    @SerializedName("list of all datasets with their completeness percentage")
    @Expose
    private List<ListOfAllDatasetsWithTheirCompletenessPercentage> listOfAllDatasetsWithTheirCompletenessPercentage = null;
    @SerializedName("total number of instances labeled")
    @Expose
    private Integer totalNumberOfInstancesLabeled;
    @SerializedName("total number of unique instances labeled")
    @Expose
    private Integer totalNumberOfUniqueInstancesLabeled;
    @SerializedName("consistency percentage")
    @Expose
    private Integer consistencyPercentage;
    @SerializedName("Average time spent in labeling an instance in seconds")
    @Expose
    private Integer averageTimeSpentInLabelingAnInstanceInSeconds;
    @SerializedName("Std. dev. of time spent in labeling an instance in seconds")
    @Expose
    private Integer stdDevOfTimeSpentInLabelingAnInstanceInSeconds;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }

    public void setConsistencyCheckProbability(String consistencyCheckProbability) {
        this.consistencyCheckProbability = consistencyCheckProbability;
    }

    public Integer getNumberOfDatasetsAssigned() {
        return numberOfDatasetsAssigned;
    }

    public void setNumberOfDatasetsAssigned(Integer numberOfDatasetsAssigned) {
        this.numberOfDatasetsAssigned = numberOfDatasetsAssigned;
    }

    public List<ListOfAllDatasetsWithTheirCompletenessPercentage> getListOfAllDatasetsWithTheirCompletenessPercentage() {
        return listOfAllDatasetsWithTheirCompletenessPercentage;
    }

    public void setListOfAllDatasetsWithTheirCompletenessPercentage(List<ListOfAllDatasetsWithTheirCompletenessPercentage> listOfAllDatasetsWithTheirCompletenessPercentage) {
        this.listOfAllDatasetsWithTheirCompletenessPercentage = listOfAllDatasetsWithTheirCompletenessPercentage;
    }

    public Integer getTotalNumberOfInstancesLabeled() {
        return totalNumberOfInstancesLabeled;
    }

    public void setTotalNumberOfInstancesLabeled(Integer totalNumberOfInstancesLabeled) {
        this.totalNumberOfInstancesLabeled = totalNumberOfInstancesLabeled;
    }

    public Integer getTotalNumberOfUniqueInstancesLabeled() {
        return totalNumberOfUniqueInstancesLabeled;
    }

    public void setTotalNumberOfUniqueInstancesLabeled(Integer totalNumberOfUniqueInstancesLabeled) {
        this.totalNumberOfUniqueInstancesLabeled = totalNumberOfUniqueInstancesLabeled;
    }

    public Integer getConsistencyPercentage() {
        return consistencyPercentage;
    }

    public void setConsistencyPercentage(Integer consistencyPercentage) {
        this.consistencyPercentage = consistencyPercentage;
    }

    public Integer getAverageTimeSpentInLabelingAnInstanceInSeconds() {
        return averageTimeSpentInLabelingAnInstanceInSeconds;
    }

    public void setAverageTimeSpentInLabelingAnInstanceInSeconds(Integer averageTimeSpentInLabelingAnInstanceInSeconds) {
        this.averageTimeSpentInLabelingAnInstanceInSeconds = averageTimeSpentInLabelingAnInstanceInSeconds;
    }

    public Integer getStdDevOfTimeSpentInLabelingAnInstanceInSeconds() {
        return stdDevOfTimeSpentInLabelingAnInstanceInSeconds;
    }

    public void setStdDevOfTimeSpentInLabelingAnInstanceInSeconds(Integer stdDevOfTimeSpentInLabelingAnInstanceInSeconds) {
        this.stdDevOfTimeSpentInLabelingAnInstanceInSeconds = stdDevOfTimeSpentInLabelingAnInstanceInSeconds;
    }

}
