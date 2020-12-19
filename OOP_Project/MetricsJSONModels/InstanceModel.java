package OOP_Project.MetricsJSONModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstanceModel {

    @SerializedName("instanceId")
    @Expose
    private Integer instanceId;
    @SerializedName("Total number of label assignments (e.g. for labeling assignments of user")
    @Expose
    private Integer totalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser;
    @SerializedName("Number of unique label assignments")
    @Expose
    private Integer numberOfUniqueLabelAssignments;
    @SerializedName("Number of unique users")
    @Expose
    private Integer numberOfUniqueUsers;
    @SerializedName("Most frequent class label and percentage")
    @Expose
    private Integer mostFrequentClassLabelAndPercentage;
    @SerializedName("List class labels and percentages")
    @Expose
    private List<ListClassLabelsAndPercentage> listClassLabelsAndPercentages = null;
    @SerializedName("Entropy")
    @Expose
    private Double entropy;

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getTotalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser() {
        return totalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser;
    }

    public void setTotalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser(Integer totalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser) {
        this.totalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser = totalNumberOfLabelAssignmentsEGForLabelingAssignmentsOfUser;
    }

    public Integer getNumberOfUniqueLabelAssignments() {
        return numberOfUniqueLabelAssignments;
    }

    public void setNumberOfUniqueLabelAssignments(Integer numberOfUniqueLabelAssignments) {
        this.numberOfUniqueLabelAssignments = numberOfUniqueLabelAssignments;
    }

    public Integer getNumberOfUniqueUsers() {
        return numberOfUniqueUsers;
    }

    public void setNumberOfUniqueUsers(Integer numberOfUniqueUsers) {
        this.numberOfUniqueUsers = numberOfUniqueUsers;
    }

    public Integer getMostFrequentClassLabelAndPercentage() {
        return mostFrequentClassLabelAndPercentage;
    }

    public void setMostFrequentClassLabelAndPercentage(Integer mostFrequentClassLabelAndPercentage) {
        this.mostFrequentClassLabelAndPercentage = mostFrequentClassLabelAndPercentage;
    }

    public List<ListClassLabelsAndPercentage> getListClassLabelsAndPercentages() {
        return listClassLabelsAndPercentages;
    }

    public void setListClassLabelsAndPercentages(List<ListClassLabelsAndPercentage> listClassLabelsAndPercentages) {
        this.listClassLabelsAndPercentages = listClassLabelsAndPercentages;
    }

    public Double getEntropy() {
        return entropy;
    }

    public void setEntropy(Double entropy) {
        this.entropy = entropy;
    }

}