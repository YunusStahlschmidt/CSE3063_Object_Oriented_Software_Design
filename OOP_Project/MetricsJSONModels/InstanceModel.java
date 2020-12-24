package OOP_Project.MetricsJSONModels;

import java.text.DecimalFormat;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstanceModel {

    @SerializedName("datasetId")
    @Expose
    private Integer datasetId;
    @SerializedName("instanceId")
    @Expose
    private Integer instanceId;
    @SerializedName("Total number of label assignments")
    @Expose
    private Integer totalNumberOfLabelAssignments;
    @SerializedName("Number of unique label assignments")
    @Expose
    private Integer numberOfUniqueLabelAssignments;
    @SerializedName("Number of unique users")
    @Expose
    private Integer numberOfUniqueUsers;
    @SerializedName("Most frequent class label and percentage")
    @Expose
    private List<ListClassLabelsAndPercentage> mostFrequentClassLabelAndPercentage;
    @SerializedName("List class labels and percentages")
    @Expose
    private List<ListClassLabelsAndPercentage> listClassLabelsAndPercentages = null;
    @SerializedName("Entropy")
    @Expose
    private Double entropy;

    public Integer getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Integer datasetId) {
        this.datasetId = datasetId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getTotalNumberOfLabelAssignments() {
        return totalNumberOfLabelAssignments;
    }

    public void setTotalNumberOfLabelAssignments(Integer totalNumberOfLabelAssignments) {
        this.totalNumberOfLabelAssignments = totalNumberOfLabelAssignments;
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

    public List<ListClassLabelsAndPercentage> getMostFrequentClassLabelAndPercentage() {
        return mostFrequentClassLabelAndPercentage;
    }

    public void setMostFrequentClassLabelAndPercentage(
            List<ListClassLabelsAndPercentage> mostFrequentClassLabelAndPercentage) {
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
        this.entropy = Double.parseDouble(new DecimalFormat("##.###").format(entropy));

    }

}