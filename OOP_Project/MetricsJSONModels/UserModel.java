package OOP_Project.MetricsJSONModels;

import java.text.DecimalFormat;
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
    private Double consistencyPercentage;
    @SerializedName("Average time spent in labeling an instance in seconds")
    @Expose
    private Double averageTimeSpentInLabelingAnInstanceInSeconds;
    @SerializedName("Std. dev. of time spent in labeling an instance in seconds")
    @Expose
    private Double stdDevOfTimeSpentInLabelingAnInstanceInSeconds;

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

    public void setListOfAllDatasetsWithTheirCompletenessPercentage(
            List<ListOfAllDatasetsWithTheirCompletenessPercentage> listOfAllDatasetsWithTheirCompletenessPercentage) {
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

    public Double getConsistencyPercentage() {
        return consistencyPercentage;
    }

    public void setConsistencyPercentage(Double consistencyPercentage) {
        this.consistencyPercentage = Double
                .parseDouble(new DecimalFormat("##.###").format(consistencyPercentage * 100));
    }

    public Double getAverageTimeSpentInLabelingAnInstanceInSeconds() {
        return averageTimeSpentInLabelingAnInstanceInSeconds;
    }

    public void setAverageTimeSpentInLabelingAnInstanceInSeconds(Double avgTime) {
        this.averageTimeSpentInLabelingAnInstanceInSeconds = Double
                .parseDouble(new DecimalFormat("##.###").format(avgTime));
    }

    public Double getStdDevOfTimeSpentInLabelingAnInstanceInSeconds() {
        return stdDevOfTimeSpentInLabelingAnInstanceInSeconds;
    }

    public void setStdDevOfTimeSpentInLabelingAnInstanceInSeconds(Double stdOfTime) {
        this.stdDevOfTimeSpentInLabelingAnInstanceInSeconds = Double
                .parseDouble(new DecimalFormat("##.###").format(stdOfTime));
    }

}
